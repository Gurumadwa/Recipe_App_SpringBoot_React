/*
 * Author : Gurumadwa Koushik
 * Desc   : CommentsController manages all the controllers related to Comments added by the user
 * 			and it interacts with CommentsService for business logic related to the comments.
 */


package com.comments.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comments.dto.CommentsResponse;
import com.comments.entity.Comments;
import com.comments.service.CommentsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/comments")
public class CommentsController {
	
	@Autowired
	private CommentsService commentsService;
	
	@PostMapping("/save/{recipeId}")
	public Comments addComment(@Valid @RequestBody Comments comments,@PathVariable Long recipeId, @RequestHeader("userId") String id) {
		Long userId = Long.valueOf(id);
		return commentsService.addComment(comments,recipeId,userId);
	}
	
	@GetMapping("/get-by-recipe/{recipeId}")
	public List<CommentsResponse> fetchByRecipeId(@PathVariable Long recipeId){
		return commentsService.fetchByRecipeId(recipeId);
	}
	
	@PutMapping("/update-comment/{commentId}")
	public String updateComment(@PathVariable Long commentId,@Valid @RequestBody Comments comments,@RequestHeader("userId") String id) {
		Long userId = Long.valueOf(id);
		return commentsService.updateComment(commentId,comments,userId);
	}
	
	@DeleteMapping("/delete/{commentId}")
	public String deleteComment(@PathVariable Long commentId, @RequestHeader("userId") String id) {
		Long userId = Long.valueOf(id);
		return commentsService.deleteComment(commentId,userId);
	}
	
	@DeleteMapping("/delete-by-id/{recipeId}")
	public void deleteCommentsByRecipeId(@PathVariable Long recipeId) {
		commentsService.deleteCommentsByRecipeId(recipeId);
	}

}
