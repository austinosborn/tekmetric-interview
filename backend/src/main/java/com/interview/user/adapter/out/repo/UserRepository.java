package com.interview.user.adapter.out.repo;

import com.interview.user.adapter.out.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);
}
