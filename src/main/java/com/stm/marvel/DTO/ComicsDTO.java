package com.stm.marvel.DTO;

import com.stm.marvel.Entities.Character;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
@Data
@Accessors(chain = true)
public class ComicsDTO {
    private Integer id;
    private String title;
    private List<Character> characters;
}
