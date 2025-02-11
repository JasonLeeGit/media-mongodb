package com.coders.software.media.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

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

import com.coders.software.media.model.Media;
import com.coders.software.media.repository.MediaRepository;

@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UpdateMediaControllerTest {
	@Autowired
	private MediaRepository mediaRepository;
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
		mediaRepository.save(new Media(1, "The Smur", "DVD", "Horror", "PG", new Decimal128((long) 4.99)));
	}

	@Test
    @DisplayName("Update Media And Save") 
	public void updateMedia() throws Exception {
		Optional<Media> foundMedia = mediaRepository.findById(1);

		if (foundMedia.isPresent()) {
			foundMedia.get().setTitle("The Smurfs");
			foundMedia.get().setGenre("Family");

			Media updatedMedia = mediaRepository.save(foundMedia.get());
			assertEquals(updatedMedia.getId(), 1);
			assertEquals(updatedMedia.getTitle(), "The Smurfs");
			assertEquals(updatedMedia.getType(), "DVD");
			assertEquals(updatedMedia.getGenre(), "Family");
			assertEquals(updatedMedia.getAgeRating(), "PG");
			assertEquals(updatedMedia.getPrice(), new Decimal128((long) 4.99));
		} else {
			throw new Exception("Failed Test");
		}
	}

//	@AfterAll
//	static public void afterAll() {
//		mongoTestContainer.stop();
//	}
}	
