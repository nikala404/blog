package com.example.blog.repository;

import com.example.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.userName = :userName")

    boolean existsByUserName(@Param("userName") String userName);

//    Optional<User> findUserByUserName(@Param("userName") String userName);


    Optional<User> findUserByUserName(String userName);


    User findByUserName(String userName);

    boolean deleteUserByUserName(@RequestParam String userName);

}

