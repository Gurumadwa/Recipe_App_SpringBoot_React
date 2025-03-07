package com.recipe.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.recipe.dto.CommentsDto;
import com.recipe.entity.Recipe;
import com.recipe.exceptions.ResourceNotFoundException;
import com.recipe.repository.RecipeRepository;
import com.recipe.service.RecipeService;
import com.recipe.service.clients.CommentsClient;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class RecipeServiceImpl implements RecipeService {

	@Autowired
	private RecipeRepository recipeRepository;

	@Autowired
	private CommentsClient commentsClient;


	@Override
	public Recipe saveRecipe(Recipe recipe, Long userId) {
		recipe.setUserId(userId);
		return recipeRepository.save(recipe);
	}

	@Override
	public List<Recipe> fetchAllRecipes() {
		return recipeRepository.findAll();
	}

	/*
	 * business logic to fetch the bookmarked recipes from the recipe id list
	 * received from user service.
	 */

	@Override
	public List<Recipe> fetchRecipesByBookmarkList(List<Long> recipeIdList) {

		if (recipeIdList.isEmpty()) {
			throw new ResourceNotFoundException("Bookmark list not found!!");
		}

		return recipeRepository.findByRecipeIdIn(recipeIdList);
	}

	/*
	 * Desc : this method fetchRecipesByRecipeId fetches a recipe by its ID, along
	 * with comments associated with it.
	 * 
	 */
	@Override
	public Recipe fetchRecipesByRecipeId(Long recipeId) {

		Optional<Recipe> foundRecipesOptional = recipeRepository.findById(recipeId);

		if (foundRecipesOptional.isPresent()) {

			Recipe foundRecipe = foundRecipesOptional.get();

			List<CommentsDto> comments = commentsClient.fetchByRecipeId(foundRecipe.getRecipeId());

			foundRecipe.setComments(comments);

			return foundRecipe;

		} else {
			throw new ResourceNotFoundException("Recipe Not Found!!");
		}

	}

	@Override
	public Recipe updateRecipe(Long recipeId, Recipe recipe) {
		Optional<Recipe> existingRecipeOptional = recipeRepository.findById(recipeId);
		if (existingRecipeOptional.isPresent()) {
			Recipe existingRecipe = existingRecipeOptional.get();

			if (recipe.getRecipeTitle() != null) {
				existingRecipe.setRecipeTitle(recipe.getRecipeTitle());
			}
			if (recipe.getRecipeDescription() != null) {
				existingRecipe.setRecipeDescription(recipe.getRecipeDescription());
			}
			if (recipe.getCookingTime() != 0) {
				existingRecipe.setCookingTime(recipe.getCookingTime());
			}
			if (recipe.getSteps() != null && !recipe.getSteps().isEmpty()) {
				existingRecipe.setSteps(recipe.getSteps());
			}
			if (recipe.getIngredients() != null) {
				existingRecipe.setIngredients(recipe.getIngredients());
			}
			if (recipe.getImage() != null) {
				existingRecipe.setImage(recipe.getImage());
			}

			return recipeRepository.save(existingRecipe);
		} else {
			throw new ResourceNotFoundException(ResourceNotFoundException.RECIPE_NOT_FOUND + recipeId);
		}
	}

	@Override
	@Transactional
	public String deleteByRecipeId(Long recipeId) {
		if (!recipeRepository.existsById(recipeId)) {
			throw new ResourceNotFoundException(ResourceNotFoundException.RECIPE_NOT_FOUND + recipeId);
		}
		
		String token = extractJwtToken();

		commentsClient.deleteCommentsByRecipeId(recipeId);
		recipeRepository.deleteById(recipeId);
		return "Recipe deleted Successfully!!";
	}

	@Override
	public List<Recipe> fetchRecipeByUserId(Long userId) {
		return recipeRepository.findByUserId(userId);
	}

	@Override
	public Recipe getByRecipeId(Long recipeId) {

		return recipeRepository.findById(recipeId).get();
	}

	private String extractJwtToken() {

		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		if (attributes != null) {
			HttpServletRequest request = attributes.getRequest();
			String token = request.getHeader("Authorization");
			if (token != null && !token.isEmpty()) {
				return token;
			}
		}
		throw new ResourceNotFoundException("Missing Authorization Token...");

	}
}
