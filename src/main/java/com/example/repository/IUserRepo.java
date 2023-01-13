package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.model.UserModel;

@Repository
public interface IUserRepo extends JpaRepository<UserModel, Long> {

	   @Query(value = "SELECT * FROM user WHERE email=:email", nativeQuery = true)

	   UserModel findByEmail(String email);

}
