package com.example.spotify.user.domain.repository;

import com.example.spotify.user.domain.entity.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {

    @Query("SELECT * FROM spotify.users WHERE email = :email")
    Optional<User> findByEmail(@Param("email") String email);


    User save(User user);
}



