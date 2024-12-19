package com.example.demo.item.repository;

import com.example.demo.item.entity.Item;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class ItemRepositoryTest {

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