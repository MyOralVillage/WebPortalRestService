package org.mov.api;

import org.mov.model.Theme;
import org.mov.service.MOVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/theme")
public class ThemeController {
    private MOVService movService;

    @Autowired
    public ThemeController(MOVService movService) {
        this.movService = movService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Theme> showThemes() {
        return new ArrayList<>(movService.findAllThemes());
    }
}
