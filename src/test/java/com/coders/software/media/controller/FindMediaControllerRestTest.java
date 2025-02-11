package com.coders.software.media.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.bson.types.Decimal128;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.coders.software.media.model.Media;
import com.coders.software.media.repository.MediaRepository;

@AutoConfigureMockMvc
@AutoConfigureDataMongo
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class FindMediaControllerRestTest {
	
	private List<Media> mediaList;
	@Autowired
	private MediaRepository mediaRepository;
	@Autowired
	private MockMvc mvc;
	
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
		mediaList = List.of(new Media(1, "The Smurfs", "DVD", "Family", "PG", new Decimal128((long) 4.99)),
				new Media(2, "Jaws", "DVD", "Horror", "18", new Decimal128((long) 5.99)),
				new Media(3, "Jaws 2", "BLURAY", "Horror", "18", new Decimal128((long) 5.99)),
				new Media(4, "Jaws 3", "DVD", "Horror", "18", new Decimal128((long) 5.99)),
				new Media(5, "Halo", "XBOX_ONE", "FPS", "15", new Decimal128((long) 29.99)));

		mediaRepository.saveAll(mediaList);
	}

	@Test
	public void findAllMedia() throws Exception {
		mvc.perform(get("/v1/media/service/findAllMedia")).andExpect(status().isOk());
		List<Media> mediaResult = mediaRepository.findAll();
		assertEquals(mediaResult.size(), 5);
	}
	
	@Test
	public void findMediaByType() throws Exception {
		//mvc.perform(get("/v1/media/service/findMediaByType/").param("type", "XBOX_ONE")).andExpect(status().isOk());
		List<Media> mediaResult = mediaRepository.findByTypeIgnoreCase("XBOX_ONE");
		assertThat(mediaResult).isNotEmpty();
		assertEquals(mediaResult.size(), 1);
		assertThat(mediaResult).extracting("id").anyMatch(media -> media.equals(5));
		assertThat(mediaResult).extracting("title").anyMatch(media -> media.equals("Halo"));
		assertThat(mediaResult).extracting("type").anyMatch(media -> media.equals("XBOX_ONE"));
		assertThat(mediaResult).extracting("genre").anyMatch(media -> media.equals("FPS"));
		assertThat(mediaResult).extracting("ageRating").anyMatch(media -> media.equals("15"));
	}

}
