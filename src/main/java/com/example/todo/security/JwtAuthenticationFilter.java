package com.example.todo.security;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationFilter  extends OncePerRequestFilter {

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request ,
                                    HttpServletResponse response ,
                                    FilterChain filterChain)
            throws ServletException, IOException {
                try{
                    String token = parseBearerToken(request);
                    log.info("Filter is running...");

                    if(token != null &&!token.equalsIgnoreCase("null")){
                        String userId = tokenProvider.validateAndGetUserId(token);
                        log.info("Authenticated user Id : " + userId);
                        AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userId,null, AuthorityUtils.NO_AUTHORITIES);

                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                        securityContext.setAuthentication(authentication);
                        SecurityContextHolder.setContext(securityContext);
                    }
                }catch (Exception ex){
                    logger.error("could not set user authentication in security context, ex");
                }

                filterChain.doFilter(request,response);
    }

    private String parseBearerToken(HttpServletRequest request) {
        // "Authorization" 헤더에서 Bearer 토큰 추출
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // "Bearer " 부분을 제외한 토큰 반환
            return authorizationHeader.substring(7);
        }

        // Bearer 토큰이 없는 경우 또는 잘못된 형식인 경우 null 반환
        return null;
    }

}
