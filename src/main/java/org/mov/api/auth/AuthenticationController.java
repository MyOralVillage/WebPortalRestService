package org.mov.api.auth;

import org.mov.config.auth.WebSecurityConfig;
import org.mov.entity.User;
import org.mov.exception.auth.InvalidJwtToken;
import org.mov.model.auth.UserContext;
import org.mov.model.auth.jwt.JwtSettings;
import org.mov.model.auth.jwt.token.JwtToken;
import org.mov.model.auth.jwt.token.JwtTokenFactory;
import org.mov.model.auth.jwt.token.RawAccessJwtToken;
import org.mov.model.auth.jwt.token.RefreshJwtToken;
import org.mov.model.auth.jwt.token.extractor.TokenExtractor;
import org.mov.model.auth.jwt.verifier.TokenVerifier;
import org.mov.service.MOVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final JwtTokenFactory tokenFactory;
    private final JwtSettings jwtSettings;
    private final MOVService movService;
    private final TokenVerifier tokenVerifier;
    private final TokenExtractor tokenExtractor;
    private final PasswordEncoder encoder;

    @Autowired
    public AuthenticationController(JwtTokenFactory tokenFactory,
                                    JwtSettings jwtSettings,
                                    MOVService movService,
                                    TokenVerifier tokenVerifier,
                                    @Qualifier("jwtTokenExtractor") TokenExtractor tokenExtractor,
                                    PasswordEncoder encoder) {
        this.tokenFactory = tokenFactory;
        this.jwtSettings = jwtSettings;
        this.movService = movService;
        this.tokenVerifier = tokenVerifier;
        this.tokenExtractor = tokenExtractor;
        this.encoder = encoder;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void registerUser(@RequestBody User user) {
        if (movService.findUserByEmail(user.getEmail()) != null ||
                movService.findUserByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("User is already registered");
        }

        user.setPassword(encoder.encode(user.getPassword()));
        movService.saveUser(user);
    }

    @RequestMapping(value = "/token", method = RequestMethod.GET)
    public JwtToken refreshToken(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String tokenPayload = tokenExtractor.extract(request.getHeader(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM));
        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
        RefreshJwtToken refreshJwtToken = RefreshJwtToken.create(rawToken, jwtSettings.getTokenSigningKey())
                .orElseThrow(InvalidJwtToken::new);

        String jti = refreshJwtToken.getJti();
        if (!tokenVerifier.verify(jti)) {
            throw new InvalidJwtToken();
        }

        String subject = refreshJwtToken.getSubject();
        User user = movService.findUserByUsername(subject);
        if (user == null) throw new UsernameNotFoundException("User not found: " + subject);

        if (user.getRoles() == null)
            throw new InsufficientAuthenticationException("User has no roles assigned");

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRole().authority()))
                .collect(Collectors.toList());

        UserContext userContext = UserContext.create(user.getUsername(), authorities);

        return tokenFactory.createAccessJwtToken(userContext);
    }
}
