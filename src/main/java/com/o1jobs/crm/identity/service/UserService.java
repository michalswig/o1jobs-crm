package com.o1jobs.crm.identity.service;

import com.o1jobs.crm.excpetion.NoSuchUserException;
import com.o1jobs.crm.excpetion.UserAlreadyExistsException;
import com.o1jobs.crm.identity.domain.User;
import com.o1jobs.crm.identity.dto.UserMapper;
import com.o1jobs.crm.identity.dto.UserRequest;
import com.o1jobs.crm.identity.dto.UserResponse;
import com.o1jobs.crm.identity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse createUser(UserRequest userRequest) {

        if (userRepository.existsUserByUsername(userRequest.username())) {
            throw new UserAlreadyExistsException("User with username " + userRequest.username() + " already exists");
        }
        User user = new User(
                userRequest.username(),
                hashedPassword(userRequest.password()),
                userRequest.role(),
                true
        );
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse getUserById(Long userId) {
        return userMapper.toUserResponse(userRepository.findById(userId).orElseThrow(
                () -> new NoSuchUserException("User with id" + userId + "do not exists")
        ));
    }

    public void deactivateUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NoSuchUserException("User with id" + userId + "do not exists")
        );
        user.deactivateUser();
    }

    private String hashedPassword(String password) {
        //TODO hashing function
        return password;
    }
}
