package com.crackit.SpringSecurityJWT.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm; 
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;


@Service
public class JwtService {

    private static final String SECRET = "9a2f8c4e6b0d71f3e8b925a45747f894a3d6bc70fa8d5e21a15a6d8c3b9a0e7c";
    public String generateToken(UserDetails user) {
    	System.out.println("lllllllllllllllllllllllllllllllll");
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("authorities", populateAuthorities(user.getAuthorities()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    
    
    private Key getSigningKey() {
    	System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMM");
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
    	System.out.println("NNNNNNNNNNNNNNNNNNN");
        Set<String> authoritiesSet = new HashSet<>();
        for(GrantedAuthority authority: authorities) {
            authoritiesSet.add(authority.getAuthority());
            
        }
        System.out.println("===================authoritiesSet==============="+authoritiesSet);

        return String.join(",", authoritiesSet);
    }

    private Claims extractAllClaims(String token) {
    	System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");

        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
    	System.out.println("PPPPPPPPPPPPPPPPPPPPPPPP");
        return extractClaim(token, Claims::getSubject);
    }
  
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    	System.out.println("QQQQQQQQQQQQQQQQQQQQQQ");
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
    	System.out.println("RRRRRRRRRRRRRRRRRRRRRR");
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()));
    }

}
