package com.tigratius.employeedatastorage.repository;

import com.tigratius.employeedatastorage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByPhoneNumber(String phoneNumber);
}
