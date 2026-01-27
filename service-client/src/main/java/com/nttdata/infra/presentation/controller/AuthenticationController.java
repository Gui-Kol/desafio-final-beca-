package com.nttdata.infra.presentation.controller;

import com.nttdata.infra.exception.newexception.JwtException;
import com.nttdata.infra.gateway.client.ClientMapper;
import com.nttdata.infra.persistence.client.ClientEntity;
import com.nttdata.infra.presentation.dtos.security.AuthenticationDto;
import com.nttdata.infra.presentation.dtos.security.TokenJWTDto;
import com.nttdata.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    ClientMapper clientMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid AuthenticationDto dto){
        try {
            var token = new UsernamePasswordAuthenticationToken(dto.username(),dto.password());
            var authentication = authenticationManager.authenticate(token);
            var tokenJWT = tokenService.generateToken(clientMapper.toClient((ClientEntity) authentication.getPrincipal()));
            return ResponseEntity.ok(new TokenJWTDto(tokenJWT));
        }catch (JwtException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
