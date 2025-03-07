package com.recipe.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.recipe.entity.Recipe;
import com.recipe.entity.Steps;
import com.recipe.exceptions.ResourceNotFoundException;
import com.recipe.repository.RecipeRepository;
import com.recipe.repository.StepsRepository;

@SpringBootTest
class StepsServiceImplTest {

    @MockBean
    private RecipeRepository recipeRepository;

    @MockBean
    private StepsRepository stepsRepository;

    @Autowired
    private StepsServiceImpl stepsService;

    private Recipe recipe;
    private Steps step;

    @BeforeEach
    void setUp() {
        recipe = new Recipe();
        recipe.setRecipeId(1L);

        step = new Steps();
        step.setStepsId(1L);
        step.setStepDescription("Test Step");
        step.setTimeRequired(10L);
        step.setRecipe(recipe);
    }

    @Test
    void testUpdateSteps() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(stepsRepository.findById(1L)).thenReturn(Optional.of(step));
        when(stepsRepository.save(any(Steps.class))).thenReturn(step);

        Steps updatedStep = new Steps();
        updatedStep.setStepDescription("Updated Step");

        Steps result = stepsService.updateSteps(1L, 1L, updatedStep);

        assertEquals("Updated Step", result.getStepDescription());
        verify(recipeRepository, times(1)).findById(1L);
        verify(stepsRepository, times(1)).findById(1L);
        verify(stepsRepository, times(1)).save(step);
    }

    @Test
    void testUpdateSteps_RecipeNotFound() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.empty());

        Steps updatedStep = new Steps();
        updatedStep.setStepDescription("Updated Step");

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            stepsService.updateSteps(1L, 1L, updatedStep);
        });

        assertEquals("Recipe not found with id: 1", exception.getMessage());
    }

    @Test
    void testUpdateSteps_StepNotFound() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(stepsRepository.findById(1L)).thenReturn(Optional.empty());

        Steps updatedStep = new Steps();
        updatedStep.setStepDescription("Updated Step");

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            stepsService.updateSteps(1L, 1L, updatedStep);
        });

        assertEquals("Step not found with id: 1", exception.getMessage());
    }

    @Test
    void testDeleteSteps() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(stepsRepository.findById(1L)).thenReturn(Optional.of(step));
        doNothing().when(stepsRepository).deleteById(1L);

        String result = stepsService.deleteSteps(1L, 1L);

        assertEquals("Step Deleted Successfully!!", result);
        verify(recipeRepository, times(1)).findById(1L);
        verify(stepsRepository, times(1)).findById(1L);
        verify(stepsRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFetchStepById() {
        when(stepsRepository.findById(1L)).thenReturn(Optional.of(step));

        Steps foundStep = stepsService.fetchStepById(1L);

        assertNotNull(foundStep);
        assertEquals(step.getStepDescription(), foundStep.getStepDescription());
        verify(stepsRepository, times(1)).findById(1L);
    }
}
