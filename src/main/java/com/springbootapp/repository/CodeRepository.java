package com.springbootapp.repository;

import java.util.List;

import com.springbootapp.model.Code;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeRepository extends JpaRepository<Code, Long> {
  List<Code> findByPhoneNumber(String phoneNumber);
}
