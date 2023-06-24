package com.examen.truper.truper.common.filter;

import com.examen.truper.truper.common.exception.TruperPurchaseException;
import com.examen.truper.truper.model.security.SingletonKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.examen.truper.truper.common.constants.SecurityConstants.AUTHORIZATION;
import static com.examen.truper.truper.common.constants.SecurityConstants.BEARER;
import static com.examen.truper.truper.common.constants.SecurityConstants.CLAIM_AUTHORITIES;
import static com.examen.truper.truper.common.constants.TruperPurchaseConstants.EMPTY_STRING;
import static com.examen.truper.truper.common.constants.TruperPurchaseConstants.ERROR_SERVER;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final List<String> exclusionList;

    private List<Pattern> urlsToExcludePatterns;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.debug("entro al filtro");
        try {
            if(jwtTokenIsPresent(request)) {
                Claims claims = validateToken(request);
                if(null != claims.get(CLAIM_AUTHORITIES)){
                    setupSpringAuth(claims);
                } else {
                    SecurityContextHolder.clearContext();
                }
            } else {
                SecurityContextHolder.clearContext();
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | NoSuchAlgorithmException e) {
            throw new TruperPurchaseException(ERROR_SERVER, "Error interno "+ e.getMessage());
        }
    }

    private void setupSpringAuth(Claims claims) {
        List<String> authorities = (List) claims.get(CLAIM_AUTHORITIES);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                claims.getSubject(),
                null,
                authorities.stream().map(SimpleGrantedAuthority::new).toList()
        ));
    }

    @Override
    protected void initFilterBean() throws ServletException {
        urlsToExcludePatterns = exclusionList
                .stream()
                .map(Pattern::compile)
                .toList();
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return urlsToExcludePatterns
                .stream()
                .map(pattern -> pattern.matcher(path))
                .anyMatch(Matcher::find);
    }
    private Claims validateToken(HttpServletRequest request) throws NoSuchAlgorithmException {
        String jwtToken = request.getHeader(AUTHORIZATION).replace(BEARER, EMPTY_STRING);
        return Jwts
                .parserBuilder()
                .setSigningKey(SingletonKey.getInstance().getSigningKey()).build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private boolean jwtTokenIsPresent(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTHORIZATION);
        return !Objects.isNull(authHeader) && authHeader.startsWith(BEARER);
    }
}
