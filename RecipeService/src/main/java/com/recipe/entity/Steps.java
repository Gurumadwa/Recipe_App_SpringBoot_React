package com.recipe.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
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
@Table(name = "steps_table")
public class Steps {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stepsId;

    @NotBlank(message = "Step description is mandatory")
    @Size(min = 5, max = 500, message = "Step description must be between 5 and 500 characters")
    private String stepDescription;

    @Min(value = 1, message = "Time required must be at least 1 minute")
    private Long timeRequired;

    @NotNull(message = "Recipe is mandatory")
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    @JsonBackReference
    private Recipe recipe;
}
