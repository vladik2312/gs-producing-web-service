package com.example.producingwebservice;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import io.spring.guides.gs_producing_web_service.Country;

public interface CountryRepository extends JpaRepository<Country, Integer> {

    Country findCountriesByName(String name);
}
