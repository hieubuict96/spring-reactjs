package com.springbootapp.repository;

import java.util.List;

import com.springbootapp.model.RequestAuthenticateCode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestAuthenticateCodeRepository extends JpaRepository<RequestAuthenticateCode, Long> {
  List<RequestAuthenticateCode> findByPhoneNumber(String phoneNumber);
}
