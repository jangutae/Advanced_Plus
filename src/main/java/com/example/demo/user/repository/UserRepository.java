package com.example.demo.user.repository;

import com.example.demo.user.entity.User;
import com.example.demo.user.entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    default User findUserByIdOrElseThrow(Long userId) {
        return findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 ID에 맞는 값이 존재하지 않습니다."));
    }

    List<User> findByIdInAndStatus(Long userId, UserStatus status);

    User findByEmail(String email);

    List<User> findByIdIn(List<Long> userIdList, UserStatus status);



}
