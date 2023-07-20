package com.stm.marvel.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@Entity
@Table(name = "characters")
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="name")
    private String name;
    @Column(name="description")
    private String description;
    @Column(name = "comics")
    @ManyToMany
    @JoinColumn()
    private List<Comics> comics;
    @Column(name="thumbnail_uri")
    private String thumbnailUri;
}
