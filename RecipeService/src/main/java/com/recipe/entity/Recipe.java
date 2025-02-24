package com.recipe.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.recipe.dto.CommentsDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recipe_table")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId;

    @NotBlank(message = "Recipe title is mandatory")
    @Size(min = 3, max = 100, message = "Recipe title must be between 3 and 100 characters")
    private String recipeTitle;

    @NotBlank(message = "Recipe description is mandatory")
    @Size(min = 10, message = "Recipe description must be at least 10 characters long")
    private String recipeDescription;

    @Min(value = 1, message = "Cooking time must be at least 1 minute")
    private int cookingTime;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    List<Steps> steps;

  
    private Long userId;

    @Transient
    private List<CommentsDto> comments;
}
