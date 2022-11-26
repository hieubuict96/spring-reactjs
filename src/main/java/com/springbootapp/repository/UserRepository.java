package com.springbootapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import com.springbootapp.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  List<User> findByPhoneNumber(String phoneNumber);
  Optional<User> findOneByPhoneNumber(String phoneNumber);
  Optional<User> findOneByEmail(String email);
  Optional<User> findOneByUserIdFacebook(String userId);
}
