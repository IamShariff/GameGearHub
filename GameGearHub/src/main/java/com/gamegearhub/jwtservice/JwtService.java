package com.gamegearhub.jwtservice;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.gamegearhub.config.SpringUserDetailsService;
import com.gamegearhub.util.JwtRequest;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.*;

/**
 * This class have all services related to JWT
 * 
 * @author mdsharif
 *
 */
@Component
public class JwtService {

	@Autowired
	private SpringUserDetailsService userService;
	
	@Value("${SECRET_KEY}")
	public String SECRET;

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public String generateToken(JwtRequest jwtRequest) {

		UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getEmail());//spring user details
		String newGeneratedToken = createToken(userDetails);//token
		return newGeneratedToken;
	}

	private String createToken(UserDetails userDetails) {
		Map<String, String> claims = new HashMap<>();
		claims.put("role", userDetails.getAuthorities().toString().substring(1, userDetails.getAuthorities().toString().length()-1));
		return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
