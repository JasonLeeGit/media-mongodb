package com.coders.software.media.controller;

import java.math.BigDecimal;
import java.util.List;

import org.bson.types.Decimal128;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coders.software.media.model.Media;
import com.coders.software.media.repository.MediaRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "MongoDB Find Media Controller")
@Validated
@RestController
@RequestMapping("/v1/media/service")
public class FindMediaController {
	
	@Autowired
	private MediaRepository mediaRepository;

	@Operation
	@GetMapping("/findAllMedia")
	public List<Media> getMedia() {
		return mediaRepository.findAll();
	}
	
	@Operation
	@GetMapping("/findMediaByType/{type}")
	public List<Media> getMediaByType(@PathVariable String type) {
		return mediaRepository.findByTypeIgnoreCase(type);
	}
	
	@Operation
	@GetMapping("/findMediaByGenre/{genre}")
	public List<Media> getMediaByGenre(@PathVariable String genre) {
		System.out.println("HERE GENRE = "+genre);
		return mediaRepository.findByGenre(genre);
	}
	@Operation
	@GetMapping("/findMediaByGenreOrderedByTitle/{genre}")
	public List<Media> findByGenreOrderByTitleAsc(@PathVariable String genre) {
		return mediaRepository.findByGenreOrderByTitleAsc(genre);
	}
	
	@Operation
	@GetMapping("/findMediaByAgeRating/{ageRating}")
	public List<Media> getByAgeRating(@PathVariable String ageRating) {
		return mediaRepository.findByAgeRating(ageRating);
	}
	
	@Operation
	@GetMapping("/findMediaWherePriceGreaterThan/{price}")
	public List<Media> getMediaWherePriveGreaterThan(@PathVariable BigDecimal price) {
		return mediaRepository.findByPrice(new Decimal128(price));
	}
}