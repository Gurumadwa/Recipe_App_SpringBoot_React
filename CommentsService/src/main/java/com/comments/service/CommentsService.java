package com.comments.service;

import java.util.List;

import com.comments.dto.CommentsResponse;
import com.comments.entity.Comments;

public interface CommentsService {

	public Comments addComment(Comments comments,Long recipeId,Long userId);

	public List<CommentsResponse> fetchByRecipeId(Long recipeId);

	public String updateComment(Long commentId, Comments comments,Long userId);

	public String deleteComment(Long commentId, Long userId);

	public void deleteCommentsByRecipeId(Long recipeId);


}
