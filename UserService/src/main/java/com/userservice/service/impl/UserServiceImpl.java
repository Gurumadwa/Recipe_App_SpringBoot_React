package com.userservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.userservice.dto.RecipeDto;
import com.userservice.entity.User;
import com.userservice.exceptions.ResourceNotFoundException;
import com.userservice.repository.UserRepository;
import com.userservice.service.UserService;
import com.userservice.service.client.RecipeClient;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RecipeClient recipeClient;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void registerUser(User user) {
		
		Optional<User> foundUser = userRepository.findByEmail(user.getEmail());
		
		if(foundUser.isPresent()) {
			throw new ResourceNotFoundException("User with this Email Already Exists...");
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	/*
	 * This method fetchRecipeByRecipeId connects with the recipe service and help
	 * in getting the recipe details with comments by the recipe id.
	 * 
	 */
	@Override
	public RecipeDto fetchRecipeByRecipeId(Long recipeId) {
		RecipeDto recipe = recipeClient.fetchRecipesByRecipeId(recipeId);
		if (recipe == null) {
			throw new ResourceNotFoundException("Recipe not found for ID: " + recipeId);
		}
		return recipe;
	}

	@Override
	public User fetchUserByUserId(Long userId) {
		return userRepository.findById(userId).get();
	}

	@Override
	public List<RecipeDto> fetchBookmarkedRecipesByUser(Long userId) {

		Optional<User> foundUserOpt = userRepository.findById(userId);

		if (!foundUserOpt.isPresent()) {
			throw new ResourceNotFoundException("User Not Found!!");
		}

		User user = foundUserOpt.get();
		return recipeClient.fetchRecipesByBookmarkList(user.getBookmarks());
	}

	@Override
	public String addBookmark(Long recipeId, Long userId) {

		Optional<User> foundUserOpt = userRepository.findById(userId);

		if (!foundUserOpt.isPresent()) {
			throw new ResourceNotFoundException("User Not found!!");
		}

		User user = foundUserOpt.get();

		List<Long> bookmarks = user.getBookmarks();

		bookmarks.add(recipeId);

		user.setBookmarks(bookmarks);

		userRepository.save(user);

		return "Bookmarked Added!!";
	}

	@Override
	public String removeBookmarkByUserId(Long recipeId, Long userId) {

		Optional<User> userOptional = userRepository.findById(userId);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			user.getBookmarks().remove(recipeId);
			userRepository.save(user);
		} else {
			throw new ResourceNotFoundException("User not found with ID: " + userId);
		}
		return "Bookmarked Removed Successfully !!";
	}

	@Override
	public User findUserByUserId(Long userId) {
		
		return userRepository.findById(userId).get();
	}
	

}
