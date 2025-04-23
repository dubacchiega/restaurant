package com.bacchiega.Restaurant.service.client;


import com.bacchiega.Restaurant.config.JWTUserData;
import com.bacchiega.Restaurant.dto.client.ClientRequestDto;
import com.bacchiega.Restaurant.dto.client.ClientResponseDto;
import com.bacchiega.Restaurant.dto.client.LoginRequestDto;
import com.bacchiega.Restaurant.entity.Client;
import com.bacchiega.Restaurant.exception.ClientDuplicateException;
import com.bacchiega.Restaurant.mapper.client.ClientRequestMapper;
import com.bacchiega.Restaurant.mapper.client.ClientResponseMapper;
import com.bacchiega.Restaurant.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientAuthService implements UserDetailsService {

    private final ClientRepository clientRepository;
    private final ClientRequestMapper clientRequestMapper;
    private final ClientResponseMapper clientResponseMapper;
    private final PasswordEncoder passwordEncoder;

    public ClientResponseDto registerClient(ClientRequestDto clientRequestDto){
        clientRepository.findByEmail(clientRequestDto.email()).ifPresent(
                client -> {throw new ClientDuplicateException("Existing costumer.");}
        );
        Client client = clientRequestMapper.toClient(clientRequestDto);

        client.setPassword(passwordEncoder.encode(client.getPassword()));
        return clientResponseMapper.toDto(clientRepository.save(client));
    }

    public static JWTUserData getUser(){
        return (JWTUserData) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return clientRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Email/password incorrect"));
    }


}
