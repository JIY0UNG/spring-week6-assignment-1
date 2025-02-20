package com.codesoom.assignment.application;

import com.codesoom.assignment.errors.UserNotFoundException;
import com.codesoom.assignment.utils.JwtUtil;
import org.springframework.stereotype.Service;

/**
 * JWT 관련 비즈니스를 처리하는 Service 클래스
 */
@Service
public class AuthenticationService {
    private JwtUtil jwtUtil;

    private final UserService userService;

    public AuthenticationService(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    /**
     * userId로 토큰을 만들고, 이를 반환한다.
     * 
     * @return 토큰
     * @throws UserNotFoundException
     *          id로 User를 찾지 못한 경우
     */
    public String login(Long userId) {
        try {
            userService.getUser(userId);
        } catch (NullPointerException e) {
            throw new UserNotFoundException(userId);
        }

        return jwtUtil.encode(userId);
    }
}
