/*
 * Author : Gurumadwa Koushik
 * Desc   : Provides implementation of business logic for recipe steps management
 * 			also it interacts with StepsRepository for interacting with the database
 */

package com.recipe.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recipe.entity.Recipe;
import com.recipe.entity.Steps;
import com.recipe.repository.RecipeRepository;
import com.recipe.repository.StepsRepository;
import com.recipe.service.StepsService;
import java.util.Objects;
import com.recipe.exceptions.ResourceNotFoundException;

@Service
public class StepsServiceImpl implements StepsService {

	@Autowired
	private RecipeRepository recipeRepository;

	@Autowired
	private StepsRepository stepsRepository;

	@Override
	public Steps updateSteps(Long recipeId, Long stepId, Steps steps) {
		Recipe foundRecipe = recipeRepository.findById(recipeId)
				.orElseThrow(() -> new ResourceNotFoundException("Recipe not found with id: " + recipeId));

		Steps foundStep = stepsRepository.findById(stepId)
				.orElseThrow(() -> new ResourceNotFoundException("Step not found with id: " + stepId));

		validateStepBelongsToRecipe(foundStep, recipeId);

		updateStepDetails(foundStep, steps);

		return stepsRepository.save(foundStep);
	}

	private void validateStepBelongsToRecipe(Steps foundStep, Long recipeId) {
		if (!Objects.equals(foundStep.getRecipe().getRecipeId(), recipeId)) {
			throw new ResourceNotFoundException("Step does not belong to the specified recipe");
		}
	}

	private void updateStepDetails(Steps foundStep, Steps steps) {
		if (steps.getStepDescription() != null) {
			foundStep.setStepDescription(steps.getStepDescription());
		}
		if (steps.getTimeRequired() != null) {
			foundStep.setTimeRequired(steps.getTimeRequired());
		}
	}

	@Override
	public String deleteSteps(Long recipeId, Long stepId) {
		Optional<Recipe> foundRecipe = recipeRepository.findById(recipeId);

		if (foundRecipe.isPresent()) {
			Optional<Steps> foundStep = stepsRepository.findById(stepId);

			if (foundStep.isPresent()) {
				Steps newStep = foundStep.get();

				if (Objects.equals(newStep.getRecipe().getRecipeId(), recipeId)) {
					stepsRepository.deleteById(stepId);
				}
			}
		}

		return "Step Deleted Successfully!!";
	}

	@Override
	public Steps fetchStepById(Long stepId) {
		Optional<Steps> step = stepsRepository.findById(stepId);

		if (!step.isPresent()) {
			throw new ResourceNotFoundException("Step not found!!");
		}
		return step.get();
	}

}
