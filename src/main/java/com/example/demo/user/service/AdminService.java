package com.example.demo.user.service;

import com.example.demo.user.entity.User;
import com.example.demo.user.entity.UserStatus;
import com.example.demo.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class AdminService {

    private final UserRepository userRepository;

    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // TODO: 4. find or save 예제 개선
    @Transactional
    public List<User> reportUsers(List<Long> userIds) {

        List<User> blockedUserList = userRepository.findByIdIn(userIds, UserStatus.BLOCKED);

//        for (Long userId : userIds) {
//            User user = userRepository.findUserByIdOrElseThrow(userId);
//            user.updateStatusToBlocked();
//
//        }
        userRepository.saveAll(blockedUserList);

        return blockedUserList;
    }
}
