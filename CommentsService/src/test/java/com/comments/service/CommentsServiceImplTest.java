//package com.comments.service;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import com.comments.entity.Comments;
//import com.comments.exceptions.ResourceNotFoundException;
//import com.comments.repository.CommentsRepository;
//import com.comments.service.impl.CommentsServiceImpl;
//
//@SpringBootTest
//class CommentsServiceImplTest {
//
//    @Autowired
//    private CommentsServiceImpl commentsService;
//
//    @MockBean
//    private CommentsRepository commentsRepository;
//
//    private Comments comment;
//    private List<Comments> commentsList;
//    private Long recipeId = 1L;
//    private Long commentId = 1L;
//    private Long userId = 1L;
//
//    @BeforeEach
//    void setUp() {
//        comment = new Comments();
//        comment.setContent("Test Comment");
//        comment.setUserId(userId);
//
//        commentsList = new ArrayList<>();
//        commentsList.add(comment);
//    }
//
//    @Test
//    void testAddComment() {
//        // Arrange
//        when(commentsRepository.save(comment)).thenReturn(comment);
//
//        // Act
//        Comments savedComment = commentsService.addComment(comment);
//
//        // Assert
//        assertNotNull(savedComment);
//        assertEquals("Test Comment", savedComment.getContent());
//        verify(commentsRepository, times(1)).save(comment);
//    }
//
//    @Test
//    void testFetchByRecipeId() {
//        // Arrange
//        when(commentsRepository.findByRecipeId(recipeId)).thenReturn(commentsList);
//
//        // Act
//        List<Comments> fetchedComments = commentsService.fetchByRecipeId(recipeId);
//
//        // Assert
//        assertNotNull(fetchedComments);
//        assertFalse(fetchedComments.isEmpty());
//        assertEquals("Test Comment", fetchedComments.get(0).getContent());
//        verify(commentsRepository, times(1)).findByRecipeId(recipeId);
//    }
//
//    @Test
//    void testUpdateComment() {
//        // Arrange
//        Comments updateComment = new Comments();
//        updateComment.setContent("Updated Content");
//        updateComment.setUserId(userId);
//
//        Comments existingComment = new Comments();
//        existingComment.setCommentId(commentId);
//        existingComment.setUserId(userId);
//        existingComment.setContent("Old Content");
//
//        when(commentsRepository.findById(commentId)).thenReturn(Optional.of(existingComment));
//        when(commentsRepository.save(existingComment)).thenReturn(existingComment);
//
//        // Act
//        Comments updatedComment = commentsService.updateComment(commentId, updateComment, userId);
//
//        // Assert
//        assertNotNull(updatedComment);
//        assertEquals("Updated Content", updatedComment.getContent());
//        verify(commentsRepository, times(1)).findById(commentId);
//        verify(commentsRepository, times(1)).save(existingComment);
//    }
//
//    @Test
//    void testDeleteComment() {
//        // Arrange
//        Comments existingComment = new Comments();
//        existingComment.setCommentId(commentId);
//        existingComment.setUserId(userId);
//
//        when(commentsRepository.findById(commentId)).thenReturn(Optional.of(existingComment));
//
//        // Act
//        String result = commentsService.deleteComment(commentId, userId);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals("Comment Deleted Successfully !!", result);
//        verify(commentsRepository, times(1)).findById(commentId);
//        verify(commentsRepository, times(1)).deleteById(commentId);
//    }
//
//    @Test
//    void testFetchByRecipeId_ResourceNotFound() {
//        // Arrange
//        when(commentsRepository.findByRecipeId(recipeId)).thenReturn(new ArrayList<>());
//
//        // Act & Assert
//        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
//            commentsService.fetchByRecipeId(recipeId);
//        });
//
//        assertEquals("Comment Not Found!!", exception.getMessage());
//        verify(commentsRepository, times(1)).findByRecipeId(recipeId);
//    }
//}
