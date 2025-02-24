//package com.userservice.service.impl;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import org.apache.hc.client5.http.auth.InvalidCredentialsException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import com.userservice.dto.RecipeDto;
//import com.userservice.dto.UserCredentialsDto;
//import com.userservice.entity.User;
//import com.userservice.exceptions.ResourceNotFoundException;
//import com.userservice.repository.UserRepository;
//import com.userservice.service.client.RecipeClient;
//
//@SpringBootTest
//class UserServiceImplTest {
//
//    @MockBean
//    private UserRepository userRepository;
//
//    @MockBean
//    private RecipeClient recipeClient;
//
//    @Autowired
//    private UserServiceImpl userService;
//
//    private User user;
//
//    @BeforeEach
//    void setUp() {
//        user = new User();
//        user.setUserId(1L);
//        user.setUsername("testuser");
//        user.setPassword("password");
//        user.setBookmarks(new ArrayList<>(Collections.singletonList(1L)));
//    }
//
//    @Test
//    void testRegisterUser() {
//        userService.registerUser(user);
//        verify(userRepository, times(1)).save(user);
//    }
//
////    @Test
////    void testLoginUser_Success() throws InvalidCredentialsException {
////        UserCredentialsDto userCredentials = new UserCredentialsDto();
////        userCredentials.setUsername("testuser");
////        userCredentials.setPassword("password");
////
////        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
////
////        String result = userService.loginUser(userCredentials);
////        assertEquals("User Logged In Successfully!!", result);
////    }
//
//    @Test
//    void testLoginUser_UserNotFound() {
//        UserCredentialsDto userCredentials = new UserCredentialsDto();
//        userCredentials.setUsername("testuser");
//        userCredentials.setPassword("password");
//
//        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
//
//        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
//            userService.loginUser(userCredentials);
//        });
//
//        assertEquals("User Not found!!", exception.getMessage());
//    }
//
//    @Test
//    void testLoginUser_InvalidPassword() {
//        UserCredentialsDto userCredentials = new UserCredentialsDto();
//        userCredentials.setUsername("testuser");
//        userCredentials.setPassword("wrongpassword");
//
//        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
//
//        Exception exception = assertThrows(InvalidCredentialsException.class, () -> {
//            userService.loginUser(userCredentials);
//        });
//
//        assertEquals("Password is Incorrect!!", exception.getMessage());
//    }
//
//    @Test
//    void testFetchRecipeByRecipeId_Success() {
//        RecipeDto recipe = new RecipeDto();
//        recipe.setRecipeId(1L);
//
//        when(recipeClient.fetchRecipesByRecipeId(1L)).thenReturn(recipe);
//
//        RecipeDto fetchedRecipe = userService.fetchRecipeByRecipeId(1L);
//        assertNotNull(fetchedRecipe);
//        assertEquals(1L, fetchedRecipe.getRecipeId());
//    }
//
//    @Test
//    void testFetchRecipeByRecipeId_NotFound() {
//        when(recipeClient.fetchRecipesByRecipeId(1L)).thenReturn(null);
//
//        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
//            userService.fetchRecipeByRecipeId(1L);
//        });
//
//        assertEquals("Recipe not found for ID: 1", exception.getMessage());
//    }
//
//    @Test
//    void testFetchUserByUserId() {
//        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//
//        User fetchedUser = userService.fetchUserByUserId(1L);
//        assertNotNull(fetchedUser);
//        assertEquals(1L, fetchedUser.getUserId());
//    }
//
//    @Test
//    void testFetchBookmarkedRecipesByUser() {
//        RecipeDto recipe = new RecipeDto();
//        recipe.setRecipeId(1L);
//
//        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//        when(recipeClient.fetchRecipesByBookmarkList(user.getBookmarks())).thenReturn(Collections.singletonList(recipe));
//
//        List<RecipeDto> recipes = userService.fetchBookmarkedRecipesByUser(1L);
//        assertNotNull(recipes);
//        assertFalse(recipes.isEmpty());
//        assertEquals(1, recipes.size());
//        assertEquals(1L, recipes.get(0).getRecipeId());
//    }
//
//    @Test
//    void testAddBookmark() {
//        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//
//        String result = userService.addBookmark(2L, 1L);
//
//        assertEquals("Bookmarked Added!!", result);
//        verify(userRepository, times(1)).save(user);
//        assertTrue(user.getBookmarks().contains(2L));
//    }
//}
