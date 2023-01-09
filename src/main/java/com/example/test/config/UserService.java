package com.example.test.config;

import com.example.test.board.dto.UserDTO;
import com.example.test.board.entity.User;
import com.example.test.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public User createUser(UserDTO userDTO) {
        User user = User.builder().email(userDTO.getEmail()).nickName(userDTO.getNickName()).creatdBy(LocalDateTime.now()).enabled(true).build();
        user.encryptPassword(userDTO.getPassword());
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> byEmail = userRepository.findByEmail(email);
        User user = byEmail.orElseThrow(() -> new UsernameNotFoundException("아이디나 비밀번호가 틀립니다."));
        log.debug(String.valueOf(user.isEnabled()));
        return User.builder().email(user.getEmail()).password(user.getPassword()).nickName(user.getNickName()).authority(user.getRole()).enabled(user.isEnabled()).build();
    }
}