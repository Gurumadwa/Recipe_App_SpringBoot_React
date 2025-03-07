/*
 * Author : Gurumadwa Koushik
 * Desc   : Manages the controllers for user management and interacts with UserService for 
 *          its business logic
 */

package com.userservice.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userservice.dto.LoginResponseDto;
import com.userservice.dto.RecipeDto;
import com.userservice.dto.UserCredentialsDto;
import com.userservice.entity.User;
import com.userservice.repository.UserRepository;
import com.userservice.service.UserService;
import com.userservice.utils.JwtUtils;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private AuthenticationManager authManager;
    
    @Autowired
    private JwtUtils jwtUtils;
    
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/save-user")
    public String registerUser(@Valid @RequestBody User user) {
        userService.registerUser(user);
        return "User Registered Successfully!!";
    }
    
    @PostMapping("/authenticate")
    public LoginResponseDto authenticateAndGetToken(@RequestBody UserCredentialsDto user) {
    	
    	Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
    	
    	if(authentication.isAuthenticated()) {
    		
    		User foundUser = userRepository.findByUsername(user.getUsername()).orElse(null);
    		
    		String token = jwtUtils.generateToken(foundUser.getUsername(), foundUser.getUserId(), foundUser.getRole());
    		
    		return new LoginResponseDto(token, foundUser.getUsername(),foundUser.getUserId(),foundUser.getRole());
    		
    	}else {
    		throw new UsernameNotFoundException("Invalid User Request!!");
    	}
    }
    
    @GetMapping("/get-user")
    public ResponseEntity<User> fetchUserByUserId(@RequestHeader("userId") String userId) {
    	Long userIdLong = Long.valueOf(userId);
        User user = userService.fetchUserByUserId(userIdLong);
        return ResponseEntity.ok(user);
    }
    
    @GetMapping("/get-user/{userId}")
    public User findUserByUserId(@PathVariable Long userId) {
        return userService.findUserByUserId(userId);
    }
    
    
    @PutMapping("/add-bookmark/{recipeId}")
    public String addBookmark(@PathVariable Long recipeId, @RequestHeader String userId) {
    	Long userIdLong = Long.valueOf(userId);
    	return userService.addBookmark(recipeId,userIdLong);
    }
    
    @DeleteMapping("/delete-bookmark/{recipeId}")
    public String removeBookmarkByUserId(@PathVariable Long recipeId, @RequestHeader("userId") String id) {
    	Long userId = Long.valueOf(id);
    	return userService.removeBookmarkByUserId(recipeId,userId);
    }
    
    @GetMapping("/bookmarked-recipes")
    public List<RecipeDto> fetchBookmarkedRecipesByUser(@RequestHeader("userId") String userId) {
    	Long userIdLong = Long.valueOf(userId);
        return userService.fetchBookmarkedRecipesByUser(userIdLong);
    }


    @GetMapping("/getRecipe/{recipeId}")
    public ResponseEntity<RecipeDto> fetchRecipeByRecipeId(@PathVariable Long recipeId) {
        RecipeDto recipe = userService.fetchRecipeByRecipeId(recipeId);
        return ResponseEntity.ok(recipe);
    }
    
}
