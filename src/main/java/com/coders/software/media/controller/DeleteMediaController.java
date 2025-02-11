package com.coders.software.media.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coders.software.media.repository.MediaRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "MongoDB Delete Media Controller")
@Validated
@RestController
@RequestMapping("/v1/media/service")
public class DeleteMediaController {
	
	@Autowired
	private MediaRepository mediaRepository;

	@Operation
	@DeleteMapping("/deleteMedia/{id}")
	public String deleteMediaById(@PathVariable int id) {
		if (id > 0) {
			mediaRepository.deleteById(id);
			return "Deleted Successfully";
		} else {
			return "Error id must be valid";
		}
	}
	
	@Operation
	@DeleteMapping("/deleteAllMedia")
	public void deleteMediaById() {
		mediaRepository.deleteAll();
	}
}
