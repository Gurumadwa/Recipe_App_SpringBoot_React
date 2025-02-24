package com.userservice.service;

import java.util.List;

import org.apache.hc.client5.http.auth.InvalidCredentialsException;

import com.userservice.dto.RecipeDto;
import com.userservice.dto.UserCredentialsDto;
import com.userservice.entity.User;

public interface UserService {

	public void registerUser(User user);

	public RecipeDto fetchRecipeByRecipeId(Long recipeId);

	public User fetchUserByUserId(Long userId);

	public List<RecipeDto> fetchBookmarkedRecipesByUser(Long userId);

	public String addBookmark(Long recipeId,Long userId);

//	public String loginUser(UserCredentialsDto user) throws InvalidCredentialsException;

}
