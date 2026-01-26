package com.forksandflames.api.repository;

import com.forksandflames.api.model.Snack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SnackRepository extends JpaRepository<Snack, Long> {}
