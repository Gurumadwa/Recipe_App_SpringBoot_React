package com.userservice.service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.userservice.dto.RecipeDto;


@FeignClient(name = "RECIPE-SERVICE")
public interface RecipeClient {

	@GetMapping("/recipes/{recipeId}")
	public RecipeDto fetchRecipesByRecipeId(@PathVariable Long recipeId);
	
	@GetMapping("/recipes/get-bookmarked-list/{recipeIdList}")
	public List<RecipeDto> fetchRecipesByBookmarkList(@PathVariable List<Long>recipeIdList);
	
}
