/*
 * Author : Gurumadwa Koushik
 * Desc   : RecipeController manages all the controllers related to recipe
 * 			and it interacts with RecipeService for business logic
 */

package com.recipe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recipe.entity.Recipe;
import com.recipe.service.RecipeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

	@Autowired
	private RecipeService recipeService;

	@PostMapping("/save-recipe")
	public Recipe saveRecipe(@Valid @RequestBody Recipe recipe) {
		return recipeService.saveRecipe(recipe);
	}

	@GetMapping
	public List<Recipe> fetchAllRecipes() {
		return recipeService.fetchAllRecipes();
	}

	@GetMapping("/{recipeId}")
	public Recipe fetchRecipesByRecipeId(@PathVariable Long recipeId) {
		return recipeService.fetchRecipesByRecipeId(recipeId);
	}

	@PutMapping("/update-recipe/{recipeId}")
	public Recipe updateRecipe(@PathVariable Long recipeId,@Valid @RequestBody Recipe recipe) {
		return recipeService.updateRecipe(recipeId, recipe);
	}

	@DeleteMapping("/{recipeId}")
	public String deleteByRecipeId(@PathVariable Long recipeId) {
		return recipeService.deleteByRecipeId(recipeId);
	}

	@GetMapping("/getRecipeByUserId/{userId}")
	public List<Recipe> fetchRecipeByUserId(@PathVariable Long userId) {
		return recipeService.fetchRecipeByUserId(userId);
	}

	/*
	 * Desc : To fetch the bookmarked recipes via the list of recipe id received
	 * from the user service
	 */
	@GetMapping("/get-bookmarked-list/{recipeIdList}")
	public List<Recipe> fetchRecipesByBookmarkList(@PathVariable List<Long> recipeIdList) {
		return recipeService.fetchRecipesByBookmarkList(recipeIdList);
	}

}
