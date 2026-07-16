package org.example.authenticationandauthorization.service;

import lombok.RequiredArgsConstructor;
import org.example.authenticationandauthorization.dto.request.UsersRequestDto;
import org.example.authenticationandauthorization.dto.response.UsersResponseDto;
import org.example.authenticationandauthorization.entity.Role;
import org.example.authenticationandauthorization.entity.Users;
import org.example.authenticationandauthorization.exception.AlreadyExistException;
import org.example.authenticationandauthorization.exception.ResourceNotFound;
import org.example.authenticationandauthorization.mapper.UsersMapper;
import org.example.authenticationandauthorization.repository.RoleRepository;
import org.example.authenticationandauthorization.repository.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserRegisterServiceImpl implements UserRegisterService{
    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    private final UsersMapper usersMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UsersResponseDto createUser(UsersRequestDto requestDto) {
        if (usersRepository.existsByEmail(requestDto.email())) {
            throw new AlreadyExistException("Email already exists");
        }

        if (usersRepository.findByUsername(requestDto.username()).isPresent()) {
            throw new AlreadyExistException("Username already exists");
        }

        if (usersRepository.existsByPhoneNumber(requestDto.phoneNumber())) {
            throw new AlreadyExistException("Phone number already exists");
        }

        Set<Role> roles = requestDto.roles()
                .stream()
                .map(roleName -> roleRepository.findByRoleName(roleName)
                        .orElseThrow(() ->
                                new ResourceNotFound("Role not found: " + roleName)))
                .collect(Collectors.toSet());

        Users user = new Users();
        user.setName(requestDto.name());
        user.setEmail(requestDto.email());
        user.setUsername(requestDto.username());
        user.setPhoneNumber(requestDto.phoneNumber());
        user.setPassword(passwordEncoder.encode(requestDto.password()));
        user.setRoles(roles);

        Users savedUser = usersRepository.save(user);

        return usersMapper.toDto(savedUser) ;   }

    @Override
    public UsersResponseDto getUserById(Long id) {
        return null;
    }

    @Override
    public List<UsersResponseDto> getAllUsers() {
        return List.of();
    }

    @Override
    public void deleteUser(Long id) {

    }
}
