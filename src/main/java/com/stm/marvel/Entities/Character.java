package com.stm.marvel.Entities;

import com.stm.marvel.DTO.CharacterDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
@Data
@NoArgsConstructor
@Entity
@Accessors(chain = true)
@Table(name = "characters")
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="name")
    private String name;
    @Column(name="description")
    private String description;
    @ManyToMany
    @JoinTable(name="characters_comics",
            joinColumns= @JoinColumn(name="character_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name= "comics_id",referencedColumnName = "id") )
    private List<Comics> comics;
    @Column(name="thumbnail_uri")
    private String thumbnailUri;


}
