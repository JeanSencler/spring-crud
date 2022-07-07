package com.crud.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.crud.demo.model.Country;
import com.crud.demo.service.CountryBadRequestException;
import com.crud.demo.service.CountryNotFoundException;
import com.crud.demo.service.ICountryService;

@RestController
@CrossOrigin(allowedHeaders = "*")
@RequestMapping("/api/v1/countries")
public class CountryController {

	private final ICountryService countryService;

	@Autowired
	public CountryController(ICountryService countryService) {
		this.countryService = countryService;
	}

	@GetMapping("/all")
	public ResponseEntity<List<Country>> getAll() {
		return ResponseEntity.ok(this.countryService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Country> getById(final @PathVariable long id) {
		try {
			return ResponseEntity.ok(this.countryService.getById(id));
		} catch (CountryNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					CountryNotFoundException.COUNTRY_NOT_FOUND.concat(String.valueOf(id)), e);
		}
	}

	@PostMapping
	public ResponseEntity<Country> create(final @RequestBody Country country) {
		try {
			return ResponseEntity.ok(this.countryService.create(country));
		} catch (CountryBadRequestException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					CountryBadRequestException.COUNTRY_VERIFY_REQUEST_BODY, e);
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Country> update(final @RequestBody Country country, final @PathVariable long id) {
		try {
			return ResponseEntity.ok(this.countryService.update(country, id));
		} catch (CountryNotFoundException | CountryBadRequestException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					CountryBadRequestException.COUNTRY_VERIFY_REQUEST_BODY, e);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deletetById(final @PathVariable long id) {
		try {
			return ResponseEntity.ok(this.countryService.delete(id));
		} catch (CountryNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					CountryNotFoundException.COUNTRY_NOT_FOUND.concat(String.valueOf(id)), e);
		}
	}
}
