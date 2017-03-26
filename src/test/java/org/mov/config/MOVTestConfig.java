package org.mov.config;

import org.mov.repository.CountryRepository;
import org.mov.repository.DocumentRepository;
import org.mov.repository.ThemeRepository;
import org.mov.repository.UserRepository;
import org.mov.repository.jpa.JpaCountryRepository;
import org.mov.repository.jpa.JpaDocumentRepository;
import org.mov.repository.jpa.JpaThemeRepository;
import org.mov.repository.jpa.JpaUserRepository;
import org.mov.service.MOVService;
import org.mov.service.MOVServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MOVTestConfig {
    @Bean
    public UserRepository userRepository() {
        return new JpaUserRepository();
    }

    @Bean
    public DocumentRepository documentRepository() {
        return new JpaDocumentRepository();
    }

    @Bean
    public ThemeRepository themeRepository() {
        return new JpaThemeRepository();
    }

    @Bean
    public CountryRepository countryRepository() {
        return new JpaCountryRepository();
    }

    @Bean
    public MOVService movService() {
        return new MOVServiceImpl(userRepository(), documentRepository(), themeRepository(), countryRepository());
    }
}
