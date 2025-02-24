package com.comments.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.comments.entity.Comments;


@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {
	
	public List<Comments> findByRecipeId(Long recipeId);
	
	public Comments findByUserId(Long userId);
	
}
