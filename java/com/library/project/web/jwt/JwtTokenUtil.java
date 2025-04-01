package com.library.project.web.jwt;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.library.project.web.models.Usuario;
import com.library.project.web.utilidades.Fijas;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenUtil {

	private static final long EXPIRE_DURATION=24*60*60*1000;//24 horas
	
	public boolean validateAccessToken(String token) {
		try {
			Jwts.parser().setSigningKey(Fijas.FIRMA).parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException e) {
			System.out.println("JWT Expirado " + e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println("Token es null, está vacío o contiene espacios " + e.getMessage());
		} catch (MalformedJwtException e) {
			System.out.println("JWT es inválido " + e.getMessage());
		} catch (UnsupportedJwtException e) {
			System.out.println("JWT no soportado " + e.getMessage());
		} catch (SignatureException e) {
			System.out.println("Validación de firmas erróneas");
		}
		return false;
	}
	
	public String generarToken(Usuario usuario) 
	{
		return Jwts.builder()
				.setSubject(String.format("%s,%s,%s", usuario.getId(), usuario.getCorreo(), usuario.getUsername()))
				.setIssuer("Sistemas")
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
				.signWith(SignatureAlgorithm.HS512, Fijas.FIRMA)
				.compact();
	}
	
	
	public String getSubject(String token) {
		return parseClaims(token).getSubject();
	}

	private Claims parseClaims(String token) {
		return Jwts.parser().setSigningKey(Fijas.FIRMA).parseClaimsJws(token).getBody();
	}
	
}
