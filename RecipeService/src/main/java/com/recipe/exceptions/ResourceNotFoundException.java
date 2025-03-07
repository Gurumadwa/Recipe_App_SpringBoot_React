package com.recipe.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String RECIPE_NOT_FOUND = "Recipe Not Found!!";

	public ResourceNotFoundException(String message) {
        super(message);
    }
}

