package com.example.demo.user.repository;

import com.example.demo.item.entity.Item;
import com.example.demo.item.repository.ItemRepository;
import com.example.demo.user.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void dynamicInsertTest() {
        User owner = new User("USER", "test@naver.com", "Owner", "qwe123!");
        User manager = new User("USER", "test@naver.com", "Manager", "asd123!");

        userRepository.save(owner);
        userRepository.save(manager);

        Item item = new Item("Java", "컴퓨터언어", owner, manager);
        Item itemDefault = new Item("query", "컴퓨터 언어", owner, manager, "APPROVED");

        itemRepository.save(item);
        itemRepository.save(itemDefault);
    }

}