package com.example.test;

import com.example.test.board.dto.UserDTO;
import com.example.test.board.entity.User;
import com.example.test.board.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestUser {

    @Autowired
    UserRepository userRepository;

    @Test
    public void addUser() {
        final User user = User.builder()
                .email("email")
                .password("password")
                .nickName("nickName")
                .build();
        
         final User result =userRepository.save(user);

        UserDTO userDTO = UserDTO.builder()
                .email(result.getEmail())
                .password(result.getPassword())
                .nickName(result.getNickName())
                .build();

        System.out.println("userDTO = " + userDTO);
    }


}
