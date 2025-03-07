package com.comments.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.comments.entity.Comments;


@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {
	
	public List<Comments> findByRecipeId(Long recipeId);
	
	public Comments findByUserId(Long userId);

	@Modifying
	@Query("DELETE FROM Comments c WHERE c.recipeId = :recipeId")
	void deleteCommentsByRecipeId(@Param("recipeId") Long recipeId);

	
}
