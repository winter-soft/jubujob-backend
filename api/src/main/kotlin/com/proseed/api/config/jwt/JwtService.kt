package com.proseed.api.config.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*
import kotlin.collections.HashMap

@Service
class JwtService(
    @Value("\${jwt.secretKey}}") private val secretKey: String // jwt 시크릿 키
) {

    // Username 추출하기
    fun extractUsername(token: String): String {
        return extractClaim(token, Claims::getSubject)
    }

    // token에 맞는 Claims 가져와 claimsResolver 람다함수에 넣어 리턴하기
    fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims: Claims = extractAllClaims(token) // Claims에서 token을 통해 현재 token의 Claims를 찾고

        return claimsResolver(claims) // claimsResolver 함수에 매개변수로 넣어 실행시킨다.
    }

    // 토큰 생성
    fun generateToken(userDetails: UserDetails): String {
        return generateToken(HashMap(), userDetails)
    }

    fun generateToken(
        extraClaims: HashMap<String, String>,
        userDetails: UserDetails
    ): String {
        return Jwts.builder() // Jwts 빌더를 사용해 JWT 토큰을 발급한다.
            .setClaims(extraClaims) // claims 설정
            .setSubject(userDetails.username) // subject에 username을 넣어준다.
            .setIssuedAt(Date(System.currentTimeMillis())) // 발급날짜는 현재 시간
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 만료날짜 설정 (30분)
            .signWith(getSignInKey(), SignatureAlgorithm.HS256) // 디코딩된 시크릿키 가져오고, SignatureAlgorithm은 HS256 명시
            .compact() // 압축
    }

    // 유효한 토큰인지 확인하는 과정
    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username: String = extractUsername(token) // token을 통해 username을 가져오고

        return (username == userDetails.username) && !isTokenExpired(token) // userDetails의 username과 찾은 username이 같은지를 확인하고, 유효기간인지 확인한다.
    }

    // 토큰 유효기간 체크
    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date()) // 가져온 유효기간과 현재 날짜를 비교해본다.
    }

    // 유효기간 가져오기
    private fun extractExpiration(token: String): Date {
        return extractClaim(token, Claims::getExpiration) // claim에서 유효기간을 가져온다.
    }

    // Claims 가져오기
    fun extractAllClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(getSignInKey()) // 시크릿 키를 통해
            .build()
            .parseClaimsJws(token) // 클래임을 가져온다.
            .body
    }

    // 디코딩된 시크릿 키 가져오기
    private fun getSignInKey(): Key {
        var keyBytes = Decoders.BASE64.decode(secretKey)

        return Keys.hmacShaKeyFor(keyBytes)
    }
}