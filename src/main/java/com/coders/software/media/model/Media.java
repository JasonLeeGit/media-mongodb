package com.coders.software.media.model;

import org.bson.types.Decimal128;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "MediaCollection")
public class Media {

	@Transient
	public static final String SEQUENCE_NAME = "database_sequence";

	@Id
	@Min(value=0)
	private int id;
	@NotEmpty(message = "title cannot be null or empty")
	private String title;
	@NotEmpty(message = "media type cannot be null or empty")
	private String type;
	@NotEmpty(message = "genre cannot be null or empty")
	private String genre;
	@NotEmpty(message = "age rating cannot be null or empty")
	private String ageRating;
	@DecimalMin(value = "0.0", inclusive = false)
	@Digits(integer=3, fraction=2)
	@Field(targetType = FieldType.DECIMAL128)
	private Decimal128 price;
}
