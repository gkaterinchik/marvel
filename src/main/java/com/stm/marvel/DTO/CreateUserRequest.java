package com.stm.marvel.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Тело запроса для создания нового пользователя в БД")
public class CreateUserRequest {
    @Schema(description = "Имя пользователя")
    public String username;
    @Schema(description = "Пароль")
    public String password;
    @Schema(description = "Адрес электронной почты")
    public String email;
}
