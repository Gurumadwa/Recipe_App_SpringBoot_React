package com.recipe.service;

import com.recipe.entity.Steps;

public interface StepsService {

	public Steps updateSteps(Long recipeId, Long stepId, Steps steps);

	public String deleteSteps(Long recipeId, Long stepId);

	public Steps fetchStepById(Long stepId);

}
