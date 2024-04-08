package com.tokioschool.filmotokio.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String requestToken = request.getHeader("Authorization");
        System.out.println("requestToken: " + requestToken);
        String username = null;
        String jwtToken = null;

        if (requestToken != null && requestToken.startsWith("Bearer ")) {


            jwtToken = requestToken.substring(7);



            System.out.println("jwtToken LOGIN: " + jwtToken);
            try {
                username = jwtTokenUtil.getUsername(jwtToken);
            } catch (IllegalArgumentException iae) {
                logger.error("Não se pode Obter o JWT TOKEN");
            } catch (ExpiredJwtException eje) {
                logger.error("Token Expirado");
            }
        } else {
            logger.warn("TOKEN SEM \"Bearer \"");
        }



        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {


                jwtToken = "Bearer " + jwtToken;
//
                // Adicione o cabeçalho "Authorization" com o token modificado
                response.addHeader("Authorization", jwtToken);

                // Adicionao ao request header
                request.setAttribute("Authorization", jwtToken);


                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }


        chain.doFilter(request, response);
    }
}
