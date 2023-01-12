package com.example.test.board.controller;

import com.example.test.Common.payload.SuccessResponse;
import com.example.test.board.dto.LoginDTO;
import com.example.test.board.dto.TokenDTO;
import com.example.test.board.dto.UserDTO;
import com.example.test.board.entity.User;
import com.example.test.board.service.UserSerivce;
import com.example.test.config.JwtFilter;
import com.example.test.config.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@Slf4j
public class UserController {

    private final UserSerivce userSerivce;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    @PostMapping("/user")
    @ResponseStatus(value = HttpStatus.CREATED)
    public SuccessResponse<UserDTO> creatUser(@RequestBody @Valid UserDTO userDTO) {
        User user = userSerivce.creatUser(userDTO);
        return SuccessResponse.success(userDTO.builder().id(user.getId()).build());
    }

    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<TokenDTO>> login(@RequestBody @Valid LoginDTO userLoginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("authentication = " + authentication);
        log.info("authentication",authentication);

        String token = tokenProvider.createToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + token);
        SuccessResponse<TokenDTO> successResponse = SuccessResponse.success(TokenDTO.builder().token(token).build());
        log.debug("successResponse",successResponse);
        return new ResponseEntity<>(successResponse, httpHeaders, HttpStatus.OK);
    }


}
