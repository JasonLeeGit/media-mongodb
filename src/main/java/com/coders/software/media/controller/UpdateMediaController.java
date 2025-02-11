package com.coders.software.media.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coders.software.media.model.Media;
import com.coders.software.media.repository.MediaRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "MongoDB Update Media Controller")
@Validated
@RestController
@RequestMapping("/v1/media/service")
public class UpdateMediaController {

	@Autowired
	private MediaRepository mediaRepository;

	@Operation
	@PutMapping("/updateMedia/{id}")
	public ResponseEntity<Media> updateMedia(@RequestBody @Valid Media media) {
		Optional<Media> foundMedia = mediaRepository.findById(media.getId());

		if (foundMedia.isPresent()) {
			foundMedia.get().setTitle(media.getTitle());
			foundMedia.get().setType(media.getType());
			foundMedia.get().setGenre(media.getGenre());
			foundMedia.get().setAgeRating(media.getAgeRating());
			foundMedia.get().setPrice(media.getPrice());
			
			return ResponseEntity.ok(mediaRepository.save(foundMedia.get()));
		} else {
			MultiValueMap<String, String> headers = new HttpHeaders();
		 	headers.add("Error message", "Error failed to find existing Media");
			return new ResponseEntity<Media>(headers, HttpStatus.NO_CONTENT);
		}
	}
}
