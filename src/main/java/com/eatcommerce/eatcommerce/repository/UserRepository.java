package com.eatcommerce.eatcommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eatcommerce.eatcommerce.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    Boolean existsByUserName(String userName);
    Optional<User> findByUserName(String userName);
}
