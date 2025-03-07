package com.userservice.dto;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StepsDto {

	private Long stepsId;
	
	private String stepDescription;
	
	private Long timeRequired;
	
	@ManyToOne
	@JoinColumn(name = "recipe_id")
	@JsonBackReference
	private RecipeDto recipe;

}
