package com.forksandflames.api.repository;

import com.forksandflames.api.model.Snack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import com.forksandflames.api.model.Company;

public interface SnackRepository extends JpaRepository<Snack, Long> {
	List<Snack> findByCompany_Id(Long companyId);
}
