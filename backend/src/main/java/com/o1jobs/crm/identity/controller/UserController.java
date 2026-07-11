package com.o1jobs.crm.identity.controller;

import com.o1jobs.crm.identity.dto.UserRequest;
import com.o1jobs.crm.identity.dto.UserResponse;
import com.o1jobs.crm.identity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Validated @RequestBody UserRequest userRequest) {
        UserResponse userResponse = userService.createUser(userRequest);
        URI uri = URI.create("api/v1/users/" + userResponse.id());
        return ResponseEntity.created(uri).body(userResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @PatchMapping("{id}/deactivate")
    public ResponseEntity<Void> deactivateUser(@PathVariable Long id) {
        userService.deactivateUser(id);
        return ResponseEntity.noContent().build();
    }

}
