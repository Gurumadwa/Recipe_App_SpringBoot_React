package com.comments.service;

import java.util.List;

import com.comments.entity.Comments;

public interface CommentsService {

	public Comments addComment(Comments comments);

	public List<Comments> fetchByRecipeId(Long recipeId);

	public Comments updateComment(Long commentId, Comments comments,Long userId);

	public String deleteComment(Long commentId, Long userId);

}
