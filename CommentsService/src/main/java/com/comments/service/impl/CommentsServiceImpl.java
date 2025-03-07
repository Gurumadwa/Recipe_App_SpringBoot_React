/*
 * Author : Gurumadwa Koushik
 * Desc   : Provides implementation of business logic for comments management
 * 			also it interacts with CommentsRepository for interacting with the database
 */

package com.comments.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.comments.dto.CommentsResponse;
import com.comments.dto.UserResponse;
import com.comments.entity.Comments;
import com.comments.exceptions.ResourceNotFoundException;
import com.comments.repository.CommentsRepository;
import com.comments.service.CommentsService;
import com.comments.service.client.UserClient;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CommentsServiceImpl implements CommentsService {

	@Autowired
	private CommentsRepository commentsRepository;
	
	@Autowired
	private UserClient userClient;


	@Override
	public Comments addComment(Comments comments,Long recipeId, Long userId) {
		comments.setRecipeId(recipeId);
		comments.setUserId(userId);
		return commentsRepository.save(comments);
	}

	@Override
	public List<CommentsResponse> fetchByRecipeId(Long recipeId) {

		List<Comments> foundComment = commentsRepository.findByRecipeId(recipeId);
		
		if(foundComment.isEmpty()) {
			throw new ResourceNotFoundException("Comment Not Found!!");
		} 
		
		String jwtToken = extractJwtToken();
		
//		return foundComment;
		
		return foundComment.stream().map(comment -> {
			UserResponse user = userClient.findUserByUserId(comment.getUserId(),jwtToken);
			return new CommentsResponse(comment.getCommentId(),user.getUserId(),comment.getContent(),user.getUsername());
		}).collect(Collectors.toList());
	}

	private String extractJwtToken() {
		
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		
		if(attributes!=null) {
			HttpServletRequest request = attributes.getRequest();
			String token = request.getHeader("Authorization");
			if(token != null && !token.isEmpty()) {
				return token;
			}
		}
		throw new ResourceNotFoundException("Missing Authorization Token...");
		
	}

	@Override
	public String updateComment(Long commentId, Comments comments, Long userId) {
	
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

		commentsRepository.save(existingComment);
		return "comment updated";
	}

	@Override
	public String deleteComment(Long commentId, Long userId) {

//		Optional<Comments> optionalComment = commentsRepository.findById(commentId);
//		if (!optionalComment.isPresent()) {
//			throw new ResourceNotFoundException("Comment Not found");
//		}
//
//		Comments existingComment = optionalComment.get();
//
//		if (Objects.equals(userId, existingComment.getUserId())) {
//			commentsRepository.deleteById(commentId);
//		} else {
//			throw new ResourceNotFoundException("Unauthorized user");
//		}
//
//		return "Comment Deleted Successfully !!";
		
		commentsRepository.deleteById(commentId);
		return "comment deleted";
	}

	@Override
	public void deleteCommentsByRecipeId(Long recipeId) {
		commentsRepository.deleteCommentsByRecipeId(recipeId);
	}
	
	

}
