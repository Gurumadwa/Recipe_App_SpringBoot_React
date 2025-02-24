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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comments.entity.Comments;
import com.comments.service.CommentsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/comments")
public class CommentsController {
	
	@Autowired
	private CommentsService commentsService;
	
	@PostMapping("/save")
	public Comments addComment(@Valid @RequestBody Comments comments) {
		return commentsService.addComment(comments);
	}
	
	@GetMapping("/get-by-recipe/{recipeId}")
	public List<Comments> fetchByRecipeId(@PathVariable Long recipeId){
		return commentsService.fetchByRecipeId(recipeId);
	}
	
	@PutMapping("/update-comment/{commentId}/{userId}")
	public Comments updateComment(@PathVariable Long commentId,@Valid @RequestBody Comments comments,@PathVariable Long userId) {
		return commentsService.updateComment(commentId,comments,userId);
	}
	
	@DeleteMapping("/delete/{commentId}/{userId}")
	public String deleteComment(@PathVariable Long commentId, @PathVariable Long userId) {
		return commentsService.deleteComment(commentId,userId);
	}
	

}
