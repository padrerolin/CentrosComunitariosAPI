/*
package com.api.centroscomunitarios.security;

import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final PasswordEncoder encoder;

    public CustomUserDetailsService(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        // Exemplo estático; em produção, buscar de um banco
        if ("admin".equals(username)) {
            return User.builder()
                    .username("admin")
                    .password(encoder.encode("senha123"))
                    .authorities(List.of())
                    .build();
        }
        throw new UsernameNotFoundException("Usuário não encontrado");
    }
}
*/

