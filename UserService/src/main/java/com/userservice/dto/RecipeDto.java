package com.userservice.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDto {
	
	private Long recipeId;
	
	private String recipeTitle;
	
	private String  recipeDescription;
	
	private int cookingTime;
	
	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL,orphanRemoval = true)
	@JsonManagedReference
	List<StepsDto> steps;
	
	private String image;
	
	private Long userId;
	
	@Transient
	private List<CommentsDto> comments;
	
	
}

