package com.recipe.service;

import java.util.List;

import com.recipe.entity.Recipe;

public interface RecipeService {

	Recipe saveRecipe(Recipe recipe,Long userId);

	List<Recipe> fetchAllRecipes();

	Recipe fetchRecipesByRecipeId(Long recipeId);

	Recipe updateRecipe(Long recipeId, Recipe recipe);

	String deleteByRecipeId(Long recipeId);

	List<Recipe> fetchRecipeByUserId(Long userId);
	
	public List<Recipe> fetchRecipesByBookmarkList(List<Long> recipeIdList);

	Recipe getByRecipeId(Long recipeId);


}
