package com.stm.marvel.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@Entity
@Table(name = "comics")
public class Comics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name ="title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "cover_uri")
    private String coverUri;
    @Column(name = "characters")
    @ManyToMany
    private List<Character> characters;
}
