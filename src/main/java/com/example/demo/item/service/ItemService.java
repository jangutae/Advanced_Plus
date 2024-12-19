package com.example.demo.item.service;

import com.example.demo.item.dto.ItemResponseDto;
import com.example.demo.item.entity.Item;
import com.example.demo.item.repository.ItemRepository;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Transactional
    public ItemResponseDto createItem(String name, String description, Long ownerId, Long managerId) {
        User owner = userRepository.findUserByIdOrElseThrow(ownerId);
        User manager = userRepository.findUserByIdOrElseThrow(managerId);

        Item item = new Item(name, description, owner, manager);
        itemRepository.save(item);

        return ItemResponseDto.toDto(item);
    }
}
