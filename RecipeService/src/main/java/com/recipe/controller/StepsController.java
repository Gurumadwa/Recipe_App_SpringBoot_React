/*
 * Author : Gurumadwa Koushik
 * Desc   : StepsController manages all the controllers related to Steps
 */

package com.recipe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recipe.entity.Steps;
import com.recipe.service.StepsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/recipes")
public class StepsController {
	
	@Autowired
	private StepsService stepsService;
	
	@PutMapping("/{recipeId}/step/{stepId}")
	public Steps updateSteps(@PathVariable Long recipeId, @PathVariable Long stepId,@Valid @RequestBody Steps steps) {
		return stepsService.updateSteps(recipeId,stepId,steps);
	}
	
	@DeleteMapping("/{recipeId}/steps/{stepId}/deleteStep")
	public String deleteSteps(@PathVariable Long recipeId, @PathVariable Long stepId) {
		return stepsService.deleteSteps(recipeId,stepId);
	}
	
	@GetMapping("/step/{stepId}")
	public Steps fetchStepById(@PathVariable Long stepId) {
		return stepsService.fetchStepById(stepId);
	}
	
}
