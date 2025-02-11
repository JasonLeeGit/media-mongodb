package com.coders.software.media.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coders.software.media.helper.ValidationHelper;
import com.coders.software.media.model.Media;
import com.coders.software.media.repository.MediaRepository;
import com.coders.software.media.service.SequenceGeneratorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import static java.util.stream.Collectors.toList;

@Tag(name = "MongoDB Save Media Controller")
@Validated
@RestController
@RequestMapping("/v1/media/service")
public class SaveMediaController {

	@Autowired
	private MediaRepository mediaRepository;
	@Autowired
	private SequenceGeneratorService sequenceGenerator;
	@Autowired
	private ValidationHelper validationHelper;

	@Operation
	@PostMapping("/addSingleMedia")	
	public ResponseEntity<Media> saveMedia(@RequestBody @Valid Media media) {
		if (validationHelper.valid(media)) {
			media.setId(sequenceGenerator.generateSequence(Media.SEQUENCE_NAME));	
			return ResponseEntity.ok(mediaRepository.save(media));
		} else {
			MultiValueMap<String, String> headers = new HttpHeaders();
		 	headers.add("Error message", "Error failed to save new artist");
		 	return new ResponseEntity<Media>(headers, HttpStatus.BAD_REQUEST);
		}
	}
	
	@Operation
	@PostMapping("/addAllMedia")	
	public ResponseEntity<List<Media>> saveAllMedia(@RequestBody @Valid List<Media> media) {
		List<Media> validList = media.stream()
				.filter(m -> validationHelper.valid(m))
				.collect(toList());
		
		validList.stream().forEach(
			m -> m.setId(sequenceGenerator.generateSequence(Media.SEQUENCE_NAME))
		);
		
		List<Media> savedMedia = mediaRepository.saveAll(validList);
		
		if(savedMedia.size() == validList.size()) {
			return ResponseEntity.ok(savedMedia);
		} else {
			MultiValueMap<String, String> headers = new HttpHeaders();
		 	headers.add("Error message", "Error failed to save new artist");
		 	return new ResponseEntity<List<Media>>(headers, HttpStatus.BAD_REQUEST);
		}
	}

}