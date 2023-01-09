package com.example.test.board.service;

import com.example.test.board.dto.UserDTO;
import com.example.test.board.entity.User;
import com.example.test.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserSerivce {

    private final UserRepository userRepository;

@Transactional
    public User creatUser(UserDTO userDTO) {
        User user = User.builder()
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .nickName(userDTO.getNickName())
                .creatdBy(userDTO.getCreateBy())
                .build();

          return  userRepository.save(user);
    }
}
