package com.mavinici.linguagens.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LanguageController {

	@Autowired
	private LanguageRepository repository;

	@GetMapping("/languages")
	@ResponseStatus(HttpStatus.OK)
	public List<Language> getLanguages() {
		List<Language> languages = repository.findAll(
			Sort.by(Sort.Direction.ASC, "ranking")
		);
		return languages;
	}

	@PostMapping("/languages")
	@ResponseStatus(HttpStatus.CREATED)
	public Language registerLanguage(@RequestBody Language language) {
		Language newLanguage = repository.save(language);
		return newLanguage;
	}

	@PutMapping("/languages/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Language> updateLanguage(@RequestBody Language language, @PathVariable String id) {
		return ResponseEntity.ok(repository.findById(id)
			.map(newLanguage -> {
				newLanguage.setTitle(language.getTitle());
				newLanguage.setImage(language.getImage());
				newLanguage.setRanking(language.getRanking());
				return repository.save(newLanguage);
			})
			.orElseGet(() -> {
				language.setId(id);
				return repository.save(language);
			}));
	}

	@DeleteMapping("/languages/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> deleteLanguage(@PathVariable String id) {
		repository.deleteById(id);
		return ResponseEntity.ok("Sucess delete");
	}
}
