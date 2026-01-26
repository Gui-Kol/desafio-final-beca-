package com.nttdata.infra.presentation.dtos.security;

public record AuthenticationDto (
        String password,
        String username

){
}
