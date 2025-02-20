package com.codesoom.assignment.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * JwtUtil에 대한 테스트 클래스
 */
class JwtUtilTest {
    private static final String SECRET = "12345678901234567890123456789010";
    private static final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9." +
            "eyJ1c2VySWQiOjF9.neCsyNLzy3lQ4o2yliotWT06FwSGZagaHpKdAkjnGGw";
    private static final String INVALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9." +
            "eyJ1c2VySWQiOjF9.neCsyNLzy3lQ4o2yliotWT06FwSGZagaHpKdAkjnGGe";

    private JwtUtil jwtUtil;
    private String token;
    private Claims claims;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil(SECRET);
    }

    @Nested
    @DisplayName("encode 메서드는")
    class Describe_encode_method {
        @Nested
        @DisplayName("userId로 1L이 주어졌을 경우")
        class Context_if_1L_given_as_userId {
            @BeforeEach
            void setUp() {
                token = jwtUtil.encode(1L);
            }

            @Test
            @DisplayName("인코딩된 userId 문자열을 반환한다")
            void It_returns_encoded_userId_string() {
                assertThat(token).isEqualTo(VALID_TOKEN);
            }
        }
    }

    @Nested
    @DisplayName("decode 메서드는")
    class Describe_decode_method {
        @Nested
        @DisplayName("유효한 토큰이 주어졌을 경우")
        class Context_if_valid_token_given {
            @BeforeEach
            void setUp() {
                claims = jwtUtil.decode(VALID_TOKEN);
            }

            @Test
            @DisplayName("디코딩된 토큰의 Claims를 반환한다")
            void It_returns_decoded_token_claims() {
                assertThat(claims.get("userId", Long.class)).isEqualTo(1L);
            }
        }

        @Nested
        @DisplayName("유효하지 않은 토큰이 주어졌을 경우")
        class Context_if_invalid_token_given {
            @Nested
            @DisplayName("SignatureException 예외를 던진다")
            class It_throws_signatureException {
                Claims claims() {
                    return jwtUtil.decode(INVALID_TOKEN);
                }

                @Test
                void test() {
                    assertThatThrownBy(() -> claims())
                            .isInstanceOf(SignatureException.class);
                }
            }
        }
    }
}
