package org.mov.api;

import org.mov.entity.Theme;
import org.mov.service.MOVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/themes")
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

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public void addTheme(@RequestBody Theme theme) {
        movService.saveTheme(theme);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public void saveTheme(@PathVariable("id") Long id,
                          @RequestBody Theme theme) {
        theme.setId(id);
        movService.saveTheme(theme);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void removeTheme(@PathVariable("id") Long id) {
        Theme theme = new Theme();
        theme.setId(id);
        movService.removeTheme(theme);
    }
}
