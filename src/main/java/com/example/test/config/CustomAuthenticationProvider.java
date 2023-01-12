package com.example.test.config;


import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

//customAuthenticationProvider(): 직접 커스텀한 provider 생성
@Component

public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;

    public CustomAuthenticationProvider(UserService userService) {
        this.userService = userService;
    }

    /*
    *UsernamePasswordAuthenticationToken는 Authentication 인터페이스의 구현 객체입니다. 인증 정보를 담는 DTO 역할의 객체라고 생각하시면 됩니다.
    AuthenticationManager 는 UsernamePasswordAuthenticationToken에 담김 정보를 이용해 인증을 수행할 수행할 객체들을 담는 컨테이너입니다. 이 인증을 수행할 객체들을 AuthenticationProvider 라고 합니다.
    *
    * */
    /**
     * authenticate(): DB에 저장된 정보와 파라미터로 받은 정보가 일치하는지 체크
     *
     */
    @Override
    public UsernamePasswordAuthenticationToken authenticate(org.springframework.security.core.Authentication authentication) {
       // 화면에서 입력한 아이디를 email 담는다.
        String email = authentication.getName();
        // 화면에서 입력한 비밀번호를 password에 담는다.
        String password = (String) authentication.getCredentials();
        //면에서 입력한 아이디(username)로 DB에 있는 사용자의 정보를 userService 가져와 userDetails 담는다.
        UserDetails userDetails = userService.loadUserByUsername(email);
        //계정 활성화 여부를 확인하는 로직이다. AuthenticationProvider 인터페이스를 구현하게 되면 계정 잠금 여부나 활성화 여부등은 여기에서 확인해야 한다.
        //로그인로직
        if (!checkPassword(password, userDetails.getPassword()) || !userDetails.isEnabled()) {
            throw new BadCredentialsException(email);
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }


    /*하지만 이 메소드의 경우 타입을 체크해주기
    때문에 내가 사용하는 클래스 혹은 UsernamePasswordAuthenticationToken 인지 아닌지 유무를 체크를 해준다.*/
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private boolean checkPassword(String loginPassword, String dbPassword) {
        return BCrypt.checkpw(loginPassword, dbPassword);
    }


}
