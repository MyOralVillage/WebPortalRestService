package org.mov.repository;

import org.mov.entity.Country;

import java.util.Collection;

public interface CountryRepository {
    void saveCountry(Country country);

    void removeCountry(Country country);

    Country findCountryById(Long id);

    Country findCountryByName(String name);

    Collection<Country> findAllCountries();
}
