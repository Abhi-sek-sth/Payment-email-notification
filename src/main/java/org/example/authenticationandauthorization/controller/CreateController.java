package org.example.authenticationandauthorization.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.authenticationandauthorization.dto.request.UsersRequestDto;
import org.example.authenticationandauthorization.dto.response.ApiResponse;
import org.example.authenticationandauthorization.dto.response.UsersResponseDto;
import org.example.authenticationandauthorization.service.UserRegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class CreateController {
    private final UserRegisterService userRegisterService;
    @PostMapping("/user")
    public ResponseEntity<ApiResponse<UsersResponseDto>> createUser(@Valid @RequestBody UsersRequestDto usersRequestDto){
        UsersResponseDto usersResponseDto = userRegisterService.createUser(usersRequestDto);
        return ResponseEntity.ok(ApiResponse.success("successfully saved new user",usersResponseDto));
    }
}
