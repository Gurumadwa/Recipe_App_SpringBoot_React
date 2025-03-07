//package com.recipe.service.impl;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import com.recipe.RecipeServiceApplication;
//import com.recipe.dto.CommentsDto;
//import com.recipe.entity.Recipe;
//import com.recipe.exceptions.ResourceNotFoundException;
//import com.recipe.repository.RecipeRepository;
//import com.recipe.service.clients.CommentsClient;
//
//@SpringBootTest(classes = RecipeServiceApplication.class)
//public class RecipeServiceImplTest {
//
//	@MockBean
//	private RecipeRepository recipeRepository;
//
//	@MockBean
//	private CommentsClient commentsClient;
//
//	@Autowired
//	private RecipeServiceImpl recipeService;
//
//	private Recipe recipe;
//
//	@BeforeEach
//	void setUp() {
//		recipe = new Recipe();
//		recipe.setRecipeId(1L);
//		recipe.setRecipeTitle("Test Recipe");
//		recipe.setRecipeDescription("Test Description");
//		recipe.setCookingTime(30);
//	}
//
//	@Test
//	void testSaveRecipe() {
//		when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
//
//		Recipe savedRecipe = recipeService.saveRecipe(recipe);
//
//		assertNotNull(savedRecipe);
//		assertEquals(recipe.getRecipeTitle(), savedRecipe.getRecipeTitle());
//		verify(recipeRepository, times(1)).save(recipe);
//	}
//
//	@Test
//	void testFetchAllRecipes() {
//		when(recipeRepository.findAll()).thenReturn(Collections.singletonList(recipe));
//
//		List<Recipe> recipes = recipeService.fetchAllRecipes();
//
//		assertFalse(recipes.isEmpty());
//		assertEquals(1, recipes.size());
//		verify(recipeRepository, times(1)).findAll();
//	}
//
//	@Test
//	void testFetchRecipesByBookmarkList() {
//		List<Long> recipeIdList = Arrays.asList(1L, 2L, 3L);
//		when(recipeRepository.findByRecipeIdIn(recipeIdList)).thenReturn(Collections.singletonList(recipe));
//
//		List<Recipe> recipes = recipeService.fetchRecipesByBookmarkList(recipeIdList);
//
//		assertFalse(recipes.isEmpty());
//		verify(recipeRepository, times(1)).findByRecipeIdIn(recipeIdList);
//	}
//
//	@Test
//	void testFetchRecipesByBookmarkList_EmptyList() {
//		List<Long> recipeIdList = Collections.emptyList();
//
//		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
//			recipeService.fetchRecipesByBookmarkList(recipeIdList);
//		});
//
//		assertEquals("Bookmark list not found!!", exception.getMessage());
//	}
//
//	@Test
//	void testFetchRecipesByRecipeId() {
//		when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
//		when(commentsClient.fetchByRecipeId(1L)).thenReturn(Collections.singletonList(new CommentsDto()));
//
//		Recipe foundRecipe = recipeService.fetchRecipesByRecipeId(1L);
//
//		assertNotNull(foundRecipe);
//		assertEquals(recipe.getRecipeTitle(), foundRecipe.getRecipeTitle());
//		verify(recipeRepository, times(1)).findById(1L);
//		verify(commentsClient, times(1)).fetchByRecipeId(1L);
//	}
//
//	@Test
//	void testFetchRecipesByRecipeId_NotFound() {
//		when(recipeRepository.findById(1L)).thenReturn(Optional.empty());
//
//		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
//			recipeService.fetchRecipesByRecipeId(1L);
//		});
//
//		assertEquals("Recipe Not Found!!", exception.getMessage());
//	}
//
//	@Test
//	void testUpdateRecipe() {
//		Recipe updatedRecipe = new Recipe();
//		updatedRecipe.setRecipeTitle("Updated Title");
//
//		when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
//		when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
//
//		Recipe result = recipeService.updateRecipe(1L, updatedRecipe);
//
//		assertEquals("Updated Title", result.getRecipeTitle());
//		verify(recipeRepository, times(1)).findById(1L);
//		verify(recipeRepository, times(1)).save(any(Recipe.class));
//	}
//
//	@Test
//	void testDeleteByRecipeId() {
//		when(recipeRepository.existsById(1L)).thenReturn(true);
//		doNothing().when(recipeRepository).deleteById(1L);
//
//		String result = recipeService.deleteByRecipeId(1L);
//
//		assertEquals("Recipe deleted Successfully!!", result);
//		verify(recipeRepository, times(1)).existsById(1L);
//		verify(recipeRepository, times(1)).deleteById(1L);
//	}
//
//	@Test
//	void testDeleteByRecipeId_NotFound() {
//		when(recipeRepository.existsById(1L)).thenReturn(false);
//
//		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
//			recipeService.deleteByRecipeId(1L);
//		});
//
//		assertEquals("Recipe Not Found!!1", exception.getMessage());
//	}
//
//	@Test
//	void testFetchRecipeByUserId() {
//		when(recipeRepository.findByUserId(1L)).thenReturn(Collections.singletonList(recipe));
//
//		List<Recipe> recipes = recipeService.fetchRecipeByUserId(1L);
//
//		assertFalse(recipes.isEmpty());
//		verify(recipeRepository, times(1)).findByUserId(1L);
//	}
//}
