package org.mov.api;

import org.mov.model.Country;
import org.mov.service.MOVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/country")
public class CountryController {
    private MOVService movService;

    @Autowired
    public CountryController(MOVService movService) {
        this.movService = movService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Country> showCountries() {
        return new ArrayList<>(movService.findAllCountries());
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST, consumes = "application/json")
    public void saveNewDocument(@RequestBody Country country) {
        movService.saveCountry(country);
    }
}
