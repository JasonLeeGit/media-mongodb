package com.coders.software.media.repository;

import java.util.List;

import org.bson.types.Decimal128;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.coders.software.media.model.Media;

@Repository("mediaRepository")
public interface MediaRepository extends MongoRepository<Media, Integer> {

	@Query("{ price: { $gt: ?0 } }")
	List<Media> findByPrice(Decimal128 price);
	
	List<Media> findByGenre(String genre);

	List<Media> findByGenreOrderByTitleAsc(String genre); //findByGenre ordered by title is the query

    List<Media> findByTypeIgnoreCase(String type); //the method name can be the query (findByType and ignoreCase)

    List<Media> findByAgeRating(String ageRateing);
}
