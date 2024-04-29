package org.asodev.securityvalidate.repository;

import org.asodev.securityvalidate.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    @Query("select u.username from User u where u.email = :email")
    String findUsernameByEmail(@Param("email") String email);
}
