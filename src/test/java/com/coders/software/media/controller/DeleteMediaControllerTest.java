package com.coders.software.media.controller;

import static org.junit.Assert.assertFalse;
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

import com.coders.software.media.model.Media;
import com.coders.software.media.repository.MediaRepository;

//@ActiveProfiles("test")
@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class DeleteMediaControllerTest {

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
		mediaRepository.saveAll(List.of(
				new Media(1, "The Smurfs", "DVD", "Family", "PG", new Decimal128((long) 4.99)),
				new Media(2, "Jaws", "DVD", "Horror", "18", new Decimal128((long) 5.99)),
				new Media(3, "Jaws 2", "BLURAY", "Horror", "18", new Decimal128((long) 5.99)),
				new Media(4, "Jaws 3", "DVD", "Horror", "18", new Decimal128((long) 5.99)),
				new Media(5, "Halo", "XBOX_ONE", "FPS", "15", new Decimal128((long) 29.99)))
		);
	}
	
	@Test
    @DisplayName("Display Media By ID") 
	public void deleteMediaById() throws Exception {
		assertTrue(mediaRepository.findById(4).isPresent());
		mediaRepository.deleteById(4);	
		assertFalse(mediaRepository.findById(4).isPresent());
	}
	
	@Test
	@DisplayName("Delete All Media") 
	public void deleteAllMedia() throws Exception {
		assertTrue(mediaRepository.findAll().size() > 0);
		mediaRepository.deleteAll();	
		assertTrue(mediaRepository.findAll().size() == 0);
	}
}
