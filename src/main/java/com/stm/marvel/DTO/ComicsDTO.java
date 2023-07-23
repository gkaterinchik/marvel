package com.stm.marvel.DTO;

import com.stm.marvel.Entities.Character;
import com.stm.marvel.Entities.Comics;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ComicsDTO {
    private Integer id;
    private String title;
    private String creator;
    private String coverUri;
    private List<Character> characters;
}
