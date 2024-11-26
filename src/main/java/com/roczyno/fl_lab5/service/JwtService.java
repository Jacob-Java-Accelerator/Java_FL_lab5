package com.roczyno.fl_lab5.service;

import com.roczyno.fl_lab5.model.Role;
import com.roczyno.fl_lab5.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

	@Value("${spring.application.secret_key}")
	private String secretkey;

	// Constructor
	public JwtService() {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
			SecretKey sk = keyGen.generateKey();
			secretkey = Base64.getEncoder().encodeToString(sk.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	// Generate JWT Token
	public String generateToken(User user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("userId",user.getId());
		claims.put("roles",user.getRoles());
		claims.put("permissions",user.getRoles().stream().map(Role::getPermissions).toList());
		return createToken(claims, user.getEmail());
	}

	// Create Token with claims
	private String createToken(Map<String, Object> claims, String subject) {

		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours expiration
				.signWith(getKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	// Extract Username from Token
	public String extractUserName(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	// Extract Specific Claims
	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	// Extract All Claims
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	// Validate Token
	public boolean validateToken(String token, UserDetails userDetails) {
		final String userName = extractUserName(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	// Check if Token is Expired
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	// Extract Expiration from Token
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	// Retrieve Secret Key
	private SecretKey getKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretkey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
