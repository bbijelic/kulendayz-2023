package com.infobip.kulendayz.auth.tokens.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {

    public static final String CLAIMS_ISSUER = "iss";
    public static final String CLAIMS_AUTHORITIES = "authorities";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.validity}")
    private Integer validity;

    @Value("${jwt.claims.issuer}")
    private String issuer;

    public String createToken(final Map<String, Object> claims, final String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (validity * 1000)))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getSubject(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date getExpiration(final String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(final String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(final String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public UserDetails toUserDetails(final String token) {
        return new JwtUserDetails(token, this);
    }

    @SuppressWarnings("unchecked")
    public Collection<GrantedAuthority> toGrantedAuthorities(final String token) {
        return extractClaim(token, claims -> AuthorityUtils.createAuthorityList(
                (ArrayList<String>) claims.get(JwtService.CLAIMS_AUTHORITIES)));
    }

    public Map<String, Object> toClaims(final Collection<GrantedAuthority> grantedAuthorities) {
        // Authorities
        final Set<String> authorities = grantedAuthorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        // Claims
        final Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIMS_ISSUER, issuer);
        claims.put(CLAIMS_AUTHORITIES, authorities);

        return claims;
    }
}
