package com.Quokka.Jobhunter.controller;

import com.Quokka.Jobhunter.domain.User;
import com.Quokka.Jobhunter.domain.dto.ResCreateUserDTO;
import com.Quokka.Jobhunter.domain.dto.ResUserDTO;
import com.Quokka.Jobhunter.domain.dto.ResultPaginationDTO;
import com.Quokka.Jobhunter.service.UserService;
import com.Quokka.Jobhunter.util.annotation.ApiMessage;
import com.Quokka.Jobhunter.util.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;
import com.Quokka.Jobhunter.domain.dto.ResUpdateUserDTO;
import jakarta.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/users")
    @ApiMessage("Create new user")
    public ResponseEntity<ResCreateUserDTO> createNewUser(@Valid @RequestBody User postManUser)
            throws IdInvalidException {
        boolean isEmailExist = this.userService.isEmailExits(postManUser.getEmail());
        if (isEmailExist) {
            throw new IdInvalidException(
                    "Email " + postManUser.getEmail() + "đã tồn tại, vui lòng sử dụng email khác...");
        }

        String hashPassword = this.passwordEncoder.encode(postManUser.getPassword());
        postManUser.setPassword(hashPassword);
        User ericUser = this.userService.handleCreateUser(postManUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.convertToResCreateUserDTO(ericUser));
    }

    @DeleteMapping("/users/{id}")
    @ApiMessage("Delete a user")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") long id)
            throws IdInvalidException {
        User currentUser = this.userService.fetchUserById(id);
        if (currentUser == null) {
            throw new IdInvalidException("User với id = " + id + "không tồn tại");
        }
        this.userService.handleDeleteUser(id);
        return ResponseEntity.ok(null);
    }

    // fetch user by id
    @GetMapping("/users/{id}")
    @ApiMessage("Fetch user by id")
    public ResponseEntity<ResUserDTO> getUserById(@PathVariable("id") long id) throws IdInvalidException {
        User fetchUser = this.userService.fetchUserById(id);
        if (fetchUser == null) {
            throw new IdInvalidException("User với id = " + id + "không tồn tại");
        }
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.convertToResUserDTO(fetchUser));
    }

    // fetch all users
    @GetMapping("/users")
    @ApiMessage("fetch all user")
    public ResponseEntity<ResultPaginationDTO> getAllUser(
            @Filter Specification<User> spec, Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK).body(this.userService.fetchAllUser(spec, pageable));
    }

    @PutMapping("/users")
    @ApiMessage("Update a user")
    public ResponseEntity<ResUpdateUserDTO> updateUser(@RequestBody User user) throws IdInvalidException {
        User ericUser = this.userService.handleUpdateUser(user);
        if (ericUser == null) {
            throw new IdInvalidException("User với id = " + user.getId() + "không tồn tại");
        }
        return ResponseEntity.ok(this.userService.convertToResUpdateUserDTO(ericUser));
    }

}
