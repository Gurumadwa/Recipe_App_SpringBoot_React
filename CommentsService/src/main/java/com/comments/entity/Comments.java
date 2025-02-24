package com.comments.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Comments_table")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @NotBlank(message = "Content is mandatory")
    @Size(min = 1, max = 500, message = "Content must be between 1 and 500 characters")
    private String content;

    @NotNull(message = "Recipe ID is mandatory")
    private Long recipeId;

    @NotNull(message = "User ID is mandatory")
    private Long userId;
}
