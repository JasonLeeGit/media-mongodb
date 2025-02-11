package com.coders.software.media.controller;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.bson.types.Decimal128;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.coders.software.media.helper.ValidationHelper;
import com.coders.software.media.model.Media;
import com.coders.software.media.repository.MediaRepository;

@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SaveMediaControllerTest {

	private List<Media> validList;
	@Autowired
	private MediaRepository mediaRepository;
	@Autowired
	private ValidationHelper validationHelper;
	// Spring bug commented out @Container and starting container manually in beforeAll()
	@Autowired
	public static MongoDBContainer mongoTestContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.4.3"));

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongodb.uri", mongoTestContainer::getReplicaSetUrl);
	}

	@BeforeAll
	static public void beforeAll() {
		mongoTestContainer.start();
	}

	@BeforeEach
	public void dataSetup() {
		validList = List.of(
				new Media(1, "The Smurfs", "DVD", "Family", "PG", new Decimal128((long) 4.99)),
				new Media(2, "Jaws", "DVD", "Horror", "18", new Decimal128((long) 5.99)),
				new Media(3, "Jaws 2", "BLURAY", "Horror", "18", new Decimal128((long) 5.99)),
				new Media(4, "Jaws 3", "DVD", "Horror", "18", new Decimal128((long) 5.99)),
				new Media(5, "Halo", "XBOX_ONE", "FPS", "15", new Decimal128((long) 29.99)));
	}

	@Test
    @DisplayName("Save Single Media") 
	public void saveSingleMedia() {
		Media media = new Media(5, "Halo", "XBOX_ONE", "FPS", "15", new Decimal128((long) 29.99));
		if (validationHelper.valid(media)) {
			mediaRepository.save(media);
			assertTrue(mediaRepository.findById(5).isPresent());
		}
	}

	@Test
    @DisplayName("Save All Media") 
	public void saveAllMedia() {
		List<Media> savedMedia = mediaRepository.saveAll(validList);
		assertTrue(savedMedia.size() == validList.size());
	}
}
