package com.bacchiega.Restaurant.controller.client;

import com.bacchiega.Restaurant.config.TokenService;
import com.bacchiega.Restaurant.dto.client.ClientRequestDto;
import com.bacchiega.Restaurant.dto.client.ClientResponseDto;
import com.bacchiega.Restaurant.dto.client.LoginRequestDto;
import com.bacchiega.Restaurant.dto.client.LoginResponseDto;
import com.bacchiega.Restaurant.entity.Client;
import com.bacchiega.Restaurant.service.client.ClientAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class ClientAuthController {

    private final ClientAuthService clientAuthService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody ClientRequestDto clientRequestDto){
        ClientResponseDto clientResponseDto = clientAuthService.registerClient(clientRequestDto);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Client registered successfully");
        response.put("data", clientResponseDto);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequestDto loginRequestDto){
        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(loginRequestDto.email(), loginRequestDto.password());
        Authentication authentication = authenticationManager.authenticate(userAndPass);

        Client client = (Client) authentication.getPrincipal();
        String token = tokenService.generateToken(client);

        LoginResponseDto loginResponseDto = new LoginResponseDto(token);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Client logged in successfully");
        response.put("data", loginResponseDto);

        return ResponseEntity.ok().body(response);
    }
}
