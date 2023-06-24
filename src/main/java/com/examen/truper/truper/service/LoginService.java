package com.examen.truper.truper.service;

import com.examen.truper.truper.common.exception.TruperPurchaseException;
import com.examen.truper.truper.model.security.SingletonKey;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import static com.examen.truper.truper.common.constants.SecurityConstants.BEARER;
import static com.examen.truper.truper.common.constants.SecurityConstants.BYTES_256;
import static com.examen.truper.truper.common.constants.SecurityConstants.CLAIM_AUTHORITIES;
import static com.examen.truper.truper.common.constants.SecurityConstants.EXPIRES_24_H;
import static com.examen.truper.truper.common.constants.SecurityConstants.HMAC_SHA_256;
import static com.examen.truper.truper.common.constants.SecurityConstants.USER_ROLE;
import static com.examen.truper.truper.common.constants.TruperPurchaseConstants.CREDENCIALES_ERRONEAS;
import static com.examen.truper.truper.common.constants.TruperPurchaseConstants.ERROR_SERVER;
import static com.examen.truper.truper.common.constants.TruperPurchaseConstants.NO_AUTORIZADO;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class LoginService {

    @Value("${appUser.username}")
    private String approvedUser;

    @Value("${appUser.password}")
    private String approvedPwd;

    public String getJwtToken(String username) {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(USER_ROLE);
        try {
            return BEARER + Jwts
                    .builder()
                    .setId("truperJWT")
                    .setSubject(username)
                    .claim(CLAIM_AUTHORITIES,
                            grantedAuthorities.stream()
                                    .map(GrantedAuthority::getAuthority)
                                    .toList())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRES_24_H))
                    .signWith(getGeneratedKey(HMAC_SHA_256, BYTES_256))
                    .compact();
        } catch (NoSuchAlgorithmException e){
            throw new TruperPurchaseException(ERROR_SERVER, "Error interno "+e.getMessage());
        }
    }

    public void validateLogin(String username, String pwd) {
        if (!approvedUser.equals(username) &&
                !approvedPwd.equals(pwd)) {
            throw new TruperPurchaseException(NO_AUTORIZADO, CREDENCIALES_ERRONEAS);
        }
    }

    private Key getGeneratedKey(String cipher, int keySize) throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance(cipher);
        keyGen.init(keySize);
        Key signingKey = keyGen.generateKey();
        SingletonKey singletonKey = SingletonKey.getInstance();
        singletonKey.setSigningKey(signingKey);
        return signingKey;
    }


}
