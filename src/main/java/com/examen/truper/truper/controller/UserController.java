package com.examen.truper.truper.controller;

import com.examen.truper.truper.model.security.User;
import com.examen.truper.truper.service.LoginService;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.jsonwebtoken.Jwts;


import java.util.Date;
import java.util.List;

import static com.examen.truper.truper.common.constants.SecurityConstants.BEARER;
import static com.examen.truper.truper.common.constants.SecurityConstants.CLAIM_AUTHORITIES;
import static com.examen.truper.truper.common.constants.SecurityConstants.EXPIRES_24_H;
import static com.examen.truper.truper.common.constants.SecurityConstants.USER_ROLE;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@ComponentScan("com.examen.truper.truper.service")
public class UserController {

    private final LoginService loginService;

    @PostMapping
    public User login(@RequestParam String username,
                      @RequestParam String pwd) {
        loginService.validateLogin(username, pwd);
        return new User(username, loginService.getJwtToken(username));
    }

}
