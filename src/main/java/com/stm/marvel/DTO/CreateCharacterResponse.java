package com.stm.marvel.DTO;

import lombok.Data;

@Data
public class CreateCharacterResponse {
    String message;

    public CreateCharacterResponse(Character character) {
        this.message = message;
    }
}
