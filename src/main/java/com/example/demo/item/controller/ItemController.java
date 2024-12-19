package com.example.demo.item.controller;

import com.example.demo.item.dto.ItemRequestDto;
import com.example.demo.item.dto.ItemResponseDto;
import com.example.demo.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    
    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemResponseDto> createItem(@RequestBody ItemRequestDto itemRequestDto) {
        ItemResponseDto itemResponseDto = itemService.createItem(
                itemRequestDto.getName(),
                itemRequestDto.getDescription(),
                itemRequestDto.getOwnerId(),
                itemRequestDto.getManagerId());

        return ResponseEntity.ok(itemResponseDto);
    }
}
