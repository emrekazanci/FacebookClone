package com.emre.repository;

import com.emre.repository.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAuthRepository extends JpaRepository<Auth, Long> {
    Optional<Auth> findOptionalByUsernameAndPassword(String username, String password);

    Boolean existsByUsername(String username);
    @Query("SELECT COUNT(a)>0 FROM Auth a WHERE a.username = ?1")
    Boolean existsByUsernameQuery(String username);
}
