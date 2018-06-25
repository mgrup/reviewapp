package com.review.repository;

import com.review.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findAllByEmail(String email);

    User findByEmail(String email);

    User findByUsername(String username);
}
