package com.comments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentsResponse {
	private Long commentId;
	private Long userId;
	private String content;
	private String commentorsName;
}
