package com.coders.software.media.helper;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.bson.types.Decimal128;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.coders.software.media.model.Media;

public class ValidationHelperTest {

	private Media media;
	private ValidationHelper helper = new ValidationHelper();
	
	@BeforeEach
	public void setupData() {
		media = new Media(1, "The Smurfs", "DVD", "Family", "PG", new Decimal128((long) 4.99));
	}

	@Test
    @DisplayName("Should Pass Validation on Type") 
	public void shouldPassTypeValidation() {
		assertTrue(helper.valid(media));
	}

	@Test
    @DisplayName("Should Fail Validation on Type") 
	public void shouldFailTypeValidation() {
		media.setType("WRONG");
		assertFalse(helper.valid(media));
	}

	@Test
    @DisplayName("Should Fail Validation on Genre") 
	public void shouldPassGenreValidation() {
		assertTrue(helper.valid(media));
	}

	@Test
    @DisplayName("Should Pass Validation on Genre") 
	public void shouldFailGenreValidation() {
		media.setGenre("WRONG");
		assertFalse(helper.valid(media));
	}

}
