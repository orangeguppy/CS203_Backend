package com.climatechange.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();

    // define a derived query to find user by username

    Optional<User> findByUsername(String username);

    Optional<User> findById(Long id);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    @Modifying
    @Query(value = "insert into Users (firstName, username, email, id, password) values (:name, :username, :email, :id, :password)", nativeQuery = true)
    void insertUser(@Param("name") String name, @Param("username") String username,
            @Param("id") Long id, @Param("email") String email, @Param("password") String password);

    Page<User> findAllByOrderByLatestCarbonScoreAsc(Pageable page);

    List<User> findAllByOrderByLatestCarbonScoreAsc();
}