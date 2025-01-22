package com.example.morningstar.repo;

import com.example.morningstar.entity.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuardianRepo extends JpaRepository<Guardian, Long> {

}
