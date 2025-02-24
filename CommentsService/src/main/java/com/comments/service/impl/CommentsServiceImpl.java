/*
 * Author : Gurumadwa Koushik
 * Desc   : Provides implementation of business logic for comments management
 * 			also it interacts with CommentsRepository for interacting with the database
 */

package com.comments.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comments.entity.Comments;
import com.comments.exceptions.ResourceNotFoundException;
import com.comments.repository.CommentsRepository;
import com.comments.service.CommentsService;

@Service
public class CommentsServiceImpl implements CommentsService {

	@Autowired
	private CommentsRepository commentsRepository;

	@Override
	public Comments addComment(Comments comments) {
		return commentsRepository.save(comments);
	}

	@Override
	public List<Comments> fetchByRecipeId(Long recipeId) {

		List<Comments> foundComment = commentsRepository.findByRecipeId(recipeId);
		
		if(foundComment.isEmpty()) {
			throw new ResourceNotFoundException("Comment Not Found!!");
		}
		
		return foundComment;
	}

	@Override
	public Comments updateComment(Long commentId, Comments comments, Long userId) {
	
		Optional<Comments> commentOpt = commentsRepository.findById(commentId);

		if (!commentOpt.isPresent()) {
			throw new ResourceNotFoundException("Comment not found!!");
		}

		Comments existingComment = commentOpt.get();

		if (!Objects.equals(userId, existingComment.getUserId())) {

			throw new ResourceNotFoundException("Unauthorized user");
		}

		if (comments.getContent() != null) {
			existingComment.setContent(comments.getContent());
		}

		return commentsRepository.save(existingComment);
	}

	@Override
	public String deleteComment(Long commentId, Long userId) {

		Optional<Comments> optionalComment = commentsRepository.findById(commentId);
		if (!optionalComment.isPresent()) {
			throw new ResourceNotFoundException("Comment Not found");
		}

		Comments existingComment = optionalComment.get();

		if (Objects.equals(userId, existingComment.getUserId())) {
			commentsRepository.deleteById(commentId);
		} else {
			throw new ResourceNotFoundException("Unauthorized user");
		}

		return "Comment Deleted Successfully !!";
	}

}
