package com.coders.software.media.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class FindMediaControllerRepoTest {

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
    @DisplayName("Find All Media") 
	public void findAllMedia() throws Exception {
		List<Media> mediaResult = mediaRepository.findAll();
		assertEquals(mediaResult.size(), 5);
	}

	@Test
    @DisplayName("Find Media By Type") 
	public void findMediaByType() throws Exception {
		List<Media> mediaResult = mediaRepository.findByTypeIgnoreCase("XBOX_ONE");
		assertThat(mediaResult).isNotEmpty();
		assertEquals(mediaResult.size(), 1);
		assertThat(mediaResult).extracting("id").anyMatch(media -> media.equals(5));
		assertThat(mediaResult).extracting("title").anyMatch(media -> media.equals("Halo"));
		assertThat(mediaResult).extracting("type").anyMatch(media -> media.equals("XBOX_ONE"));
		assertThat(mediaResult).extracting("genre").anyMatch(media -> media.equals("FPS"));
		assertThat(mediaResult).extracting("ageRating").anyMatch(media -> media.equals("15"));
	}

	@Test
    @DisplayName("Find Media By Genre") 
	public void findMediaByGenre() throws Exception {
		List<Media> mediaResult = mediaRepository.findByGenre("Horror");
		assertThat(mediaResult).isNotEmpty();
		assertEquals(mediaResult.size(), 3);
		assertEquals(mediaResult.get(0).getId(), 2);
		assertEquals(mediaResult.get(0).getTitle(), "Jaws");
		assertEquals(mediaResult.get(0).getType(), "DVD");
		assertEquals(mediaResult.get(0).getGenre(), "Horror");
		assertEquals(mediaResult.get(0).getAgeRating(), "18");
	}

	@Test
    @DisplayName("Find Media By Genre Ordered By Title Asc") 
	public void findByGenreOrderByTitleAsc() {
		List<Media> mediaResult = mediaRepository.findByGenreOrderByTitleAsc("Horror");
		assertThat(mediaResult).isNotEmpty();
		assertEquals(mediaResult.size(), 3);
		assertEquals(mediaResult.get(0).getId(), 2);
		assertEquals(mediaResult.get(0).getTitle(), "Jaws");
		assertEquals(mediaResult.get(0).getType(), "DVD");
		assertEquals(mediaResult.get(0).getGenre(), "Horror");
		assertEquals(mediaResult.get(0).getAgeRating(), "18");
	}

	@Test
    @DisplayName("Find Media By Age Rating") 
	public void findByAgeRating() {
		List<Media> mediaResult = mediaRepository.findByAgeRating("PG");
		assertThat(mediaResult).isNotEmpty();
		assertEquals(mediaResult.size(), 1);
		assertEquals(mediaResult.get(0).getId(), 1);
		assertEquals(mediaResult.get(0).getTitle(), "The Smurfs");
		assertEquals(mediaResult.get(0).getType(), "DVD");
		assertEquals(mediaResult.get(0).getGenre(), "Family");
		assertEquals(mediaResult.get(0).getAgeRating(), "PG");
	}

	@Test
    @DisplayName("Find Media By Price") 
	public void findByPrice() {
		List<Media> mediaResult = mediaRepository.findByPrice(new Decimal128((long) 5.99));
		assertThat(mediaResult).isNotEmpty();
		assertEquals(mediaResult.size(), 1);
		assertEquals(mediaResult.get(0).getId(), 5);
		assertEquals(mediaResult.get(0).getPrice(), new Decimal128((long) 29.99));
	}
}