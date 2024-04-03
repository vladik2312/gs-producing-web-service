package com.example.producingwebservice;

import io.spring.guides.gs_producing_web_service.Country;
import io.spring.guides.gs_producing_web_service.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

@Component
public class CountryRepository {
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public CountryRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@PostConstruct
	public void initData() {
		// Initialization not needed since data is retrieved from the database
	}

	public Country findCountry(String name) {
		Assert.notNull(name, "The country's name must not be null");

		String sql = "SELECT * FROM Country WHERE name = ?";
		List<Country> countries = jdbcTemplate.query(sql, new Object[]{name}, (rs, rowNum) -> {
			Country country = new Country();
			country.setName(rs.getString("name"));
			country.setCapital(rs.getString("capital"));
			country.setCurrency(Currency.valueOf(rs.getString("currency")));
			country.setPopulation(rs.getInt("population"));
			return country;
		});

		if (countries.isEmpty()) {
			return null;
		}

		return countries.get(0);
	}
}
