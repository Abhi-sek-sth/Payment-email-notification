package org.example.authenticationandauthorization.service;

import org.example.authenticationandauthorization.dto.request.UsersRequestDto;
import org.example.authenticationandauthorization.dto.response.UsersResponseDto;

import java.util.List;

public interface UserRegisterService {
    UsersResponseDto createUser(UsersRequestDto requestDto);

    UsersResponseDto getUserById(Long id);

    List<UsersResponseDto> getAllUsers();

    void deleteUser(Long id);
}
