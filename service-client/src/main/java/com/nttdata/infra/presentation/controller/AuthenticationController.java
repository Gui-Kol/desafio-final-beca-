package com.nttdata.infra.presentation.controller;

import com.nttdata.application.usecase.client.ValidLogin;
import com.nttdata.application.usecase.client.VerifyClientActive;
import com.nttdata.domain.exception.JwtException;
import com.nttdata.domain.exception.ValidException;
import com.nttdata.infra.gateway.client.ClientMapper;
import com.nttdata.infra.persistence.client.ClientEntity;
import com.nttdata.infra.presentation.dtos.security.AuthenticationDto;
import com.nttdata.infra.presentation.dtos.security.TokenJWTDto;
import com.nttdata.infra.service.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {
    private final ValidLogin validLogin;

    @Autowired
    ClientMapper clientMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public AuthenticationController(ValidLogin validLogin) {
        this.validLogin = validLogin;
    }

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid AuthenticationDto dto) {
        try {
            validLogin.valid(dto.username());

            var token = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
            var authentication = authenticationManager.authenticate(token);
            var tokenJWT = tokenService.generateToken(clientMapper.toClient((ClientEntity) authentication.getPrincipal()));

            validLogin.validOk(dto.username());

            return ResponseEntity.ok(new TokenJWTDto(tokenJWT));
        } catch (AuthenticationException e) {
            try {
                validLogin.validFail(dto.username());
                return ResponseEntity.internalServerError().body("Validation error");
            }catch (ValidException ex) {
                return ResponseEntity.badRequest().body(ex.getMessage());
            }
        } catch (JwtException | ValidException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
