package com.stm.marvel.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Сущность ответа серевера на запрос о создании нового пользователя")
public class CreateUserResponse {
    @Schema(description = "Имя созданного пользователя")
    public String username;

}
