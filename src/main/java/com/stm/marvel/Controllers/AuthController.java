package com.stm.marvel.Controllers;

import com.stm.marvel.DTO.CreateUserRequest;
import com.stm.marvel.DTO.CreateUserResponse;
import com.stm.marvel.DTO.JwtRequest;
import com.stm.marvel.DTO.JwtResponse;
import com.stm.marvel.Exceptions.AppError;
import com.stm.marvel.Services.UserServiceImpl;
import com.stm.marvel.Utils.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.stm.marvel.Entities.User;


@RestController
@RequestMapping("/v1/public")
@RequiredArgsConstructor
@Tag(name="Регистрация и аутентификация")
public class AuthController {
    private final UserServiceImpl userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final DaoAuthenticationProvider authenticationManager;

    @Operation(
            summary = "Аутентификация",
            description = "Аутентификация пользователя.  \n" +
                    "username: user, \n" +
                    "password: 100, \n " +
                    "for ROLE_USER"
    )
    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody @Parameter(description = "Тело запроса для аутентификации") JwtRequest authRequest) {
       try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        }
        catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Incorrect username or password"), HttpStatus.UNAUTHORIZED);
        }
        catch (AuthenticationException e){
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Incorrect username or password"), HttpStatus.BAD_REQUEST);

        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @Operation(
            summary = "Регистрация",
            description = "Регистрация нового пользователя"
    )
        @PostMapping(value="/registration",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        public CreateUserResponse tryToCreateUser(@RequestBody @Parameter(description = "Тело запроса для создания нового пользователя") CreateUserRequest request) {

            User user = userService.createUser(request.getUsername(), request.getPassword());
            CreateUserResponse response = new CreateUserResponse();
            response.setUsername(user.getUsername());
            return response;

        }
    }
