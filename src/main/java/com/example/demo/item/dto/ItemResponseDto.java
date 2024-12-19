package com.example.demo.item.dto;

import com.example.demo.item.entity.Item;

public class ItemResponseDto {

    private final Long itemId;
    private final String name;
    private final String description;
    private final Long ownerId;
    private final Long managerId;

    public ItemResponseDto(Long itemId, String name, String description, Long ownerId, Long managerId) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.ownerId = ownerId;
        this.managerId = managerId;
    }

    public static ItemResponseDto toDto(Item item) {
        return new ItemResponseDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getOwner().getId(),
                item.getManager().getId()
        );
    }
}
