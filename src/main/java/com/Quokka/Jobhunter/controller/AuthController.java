package com.Quokka.Jobhunter.controller;

import java.net.http.HttpHeaders;

import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Quokka.Jobhunter.domain.dto.LoginDTO;
import com.Quokka.Jobhunter.domain.dto.ResLoginDTO;
import com.Quokka.Jobhunter.util.SecurityUtil;
import com.Quokka.Jobhunter.util.error.IdInvalidException;

import jakarta.validation.Valid;

@RestController
public class AuthController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityUtil securityUtil;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder,
            SecurityUtil securityUtil) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil = securityUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<ResLoginDTO> login(@Valid @RequestBody LoginDTO loginDto) {
        // Nạp input gồm username/password vào Security
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword());

        // xác thực người dùng => cần viết hàm loadUserByUsername
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // create a token
        String access_token = this.securityUtil.createAccessToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ResLoginDTO res = new ResLoginDTO();
        res.setAccessToken(access_token);
        return ResponseEntity.ok().body(res);
    }

    // @PostMapping("/auth/logout")
    // public ResponseEntity<Void> logout() throws IdInvalidException {
    // String email = SecurityUtil.getCurrentUserLogin().isPresent() ?
    // SecurityUtil.getCurrentUserLogin().get() : "";

    // if(email.equals("")){
    // throw new IdInvalidException("Access Token khong hop le...");
    // }

    // //update refresh token = null
    // this.userService.updateUserToken(null, email);

    // //remove refresh token cookie
    // ResponseCookie deleteSpringCookie = ResponseCookie.from("refresh_token",
    // null)
    // .httpOnly(true)
    // .secure(true)
    // .path("/")
    // .maxAge(0)
    // .build();

    // return ResponseEntity.ok().header(HttpHeaders.SET_CoOKIE,
    // deleteSpringCookie.toString())
    // .body(null);

    // }
}
