package com.example.test.board.controller;

import com.example.test.Common.payload.SuccessResponse;
import com.example.test.board.dto.UserDTO;
import com.example.test.board.entity.User;
import com.example.test.board.service.UserSerivce;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserSerivce userSerivce;

    @PostMapping("/user")
    @ResponseStatus(value = HttpStatus.CREATED)
    public SuccessResponse<UserDTO> creatUser(@RequestBody @Valid UserDTO userDTO) {
        User user = userSerivce.creatUser(userDTO);
        return SuccessResponse.success(userDTO.builder().id(user.getId()).build());
    }


}
