package com.recipe.service.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.recipe.dto.CommentsDto;

@FeignClient(name = "COMMENTS-SERVICE")
public interface CommentsClient {
	
	@GetMapping("comments/get-by-recipe/{recipeId}")
	public List<CommentsDto> fetchByRecipeId(@PathVariable Long recipeId);
	
	@DeleteMapping("/comments/delete-by-id/{recipeId}")
	public void deleteCommentsByRecipeId(@PathVariable Long recipeId);
	
}
