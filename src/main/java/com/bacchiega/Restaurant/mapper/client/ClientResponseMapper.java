package com.bacchiega.Restaurant.mapper.client;

import com.bacchiega.Restaurant.dto.client.ClientResponseDto;
import com.bacchiega.Restaurant.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClientResponseMapper {

    ClientResponseMapper INSTANCE = Mappers.getMapper(ClientResponseMapper.class);

    ClientResponseDto toDto(Client client);
}
