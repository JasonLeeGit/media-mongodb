package com.coders.software.media.helper;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.coders.software.media.model.Genre;
import com.coders.software.media.model.Media;
import com.coders.software.media.model.MediaType;

@Component
public class ValidationHelper {

	public boolean valid(Media media) {
		Optional<MediaType> type = Arrays.stream(MediaType.values())
				.filter(m -> m.name().equals(media.getType().toUpperCase())).findFirst();
	
		Optional<Genre> genre = Arrays.stream(Genre.values())
				.filter(g -> g.name().equals(media.getGenre().toUpperCase())).findFirst();

		if (type.isPresent() && genre.isPresent()) {
			return true;
		} else {
			return false;
		}
	}
}
