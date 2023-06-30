package com.dilay.contentserviceapi.repository;

import com.dilay.contentserviceapi.entity.User;
import com.dilay.contentserviceapi.entity.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findFirstByEmail(String email);

    boolean existsByEmail(String email);

    User findUserByEmail(String email);
}
