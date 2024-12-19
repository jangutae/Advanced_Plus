package com.example.demo.item.entity;

import com.example.demo.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.DynamicInsert;


@Entity
@Getter
@DynamicInsert
// TODO: 6. Dynamic Insert
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;


    @Column(name = "status", nullable = false)
    private String status = "PENDING";

    public Item(String name, String description, User manager, User owner) {
        this.name = name;
        this.description = description;
        this.manager = manager;
        this.owner = owner;
    }

    public Item(String name, String description, User manager, User owner, String status) {
        this.name = name;
        this.description = description;
        this.manager = manager;
        this.owner = owner;
        this.status = status;
    }

    @PrePersist
    public void setDefaultStatus() {
        if (status == null) {
            status = "PENDING";
        }
    }

    public Item() {}
}
