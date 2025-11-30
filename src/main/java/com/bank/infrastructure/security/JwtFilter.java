package com.bank.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import org.springframework.stereotype.Component;

@Component // Esta anotación es clave para que Spring registre el filtro
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtProvider;

    public JwtFilter(JwtTokenProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Obtenemos el token de la cabecera de la solicitud
        String header = request.getHeader("Authorization");

        // Comprobamos si el token está presente y tiene el formato correcto
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7); // Extraemos el token

            // Si el token es válido
            if (jwtProvider.validateToken(token)) {
                String username = jwtProvider.getUsername(token); // Extraemos el nombre de usuario

                // Creamos un objeto de autenticación con el nombre de usuario y sin credenciales adicionales
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                new User(username, "", Collections.emptyList()), // Usuario con roles vacíos
                                null,
                                Collections.emptyList() // Roles vacíos, puedes añadir roles aquí si es necesario
                        );

                // Configuramos detalles de la autenticación
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Establecemos la autenticación en el contexto de seguridad de Spring
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // Continuamos con el filtro de la cadena
        chain.doFilter(request, response);
    }
}
