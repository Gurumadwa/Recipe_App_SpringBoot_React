package com.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recipe.entity.Steps;

@Repository
public interface StepsRepository extends JpaRepository<Steps, Long>{

}
