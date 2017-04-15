package org.mov.api;

import org.mov.entity.Country;
import org.mov.service.MOVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/countries")
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

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public void addCountry(@RequestBody Country country) {
        movService.saveCountry(country);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public void saveCountry(@PathVariable("id") Long id,
                            @RequestBody Country country) {
        country.setId(id);
        movService.saveCountry(country);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void removeCountry(@PathVariable("id") Long id) {
        Country country = new Country();
        country.setId(id);
        movService.removeCountry(country);
    }
}
