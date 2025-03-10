package com.iess.guidelines.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iess.guidelines.entity.Guideline;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@Repository
public interface GuidelinesRepo extends JpaRepository<Guideline, Integer> {
    // Métodos adicionales personalizados si son necesarios
	
	
	
}
