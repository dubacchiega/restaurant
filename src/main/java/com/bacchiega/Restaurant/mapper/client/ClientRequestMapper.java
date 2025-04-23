package com.bacchiega.Restaurant.mapper.client;

import com.bacchiega.Restaurant.dto.client.ClientRequestDto;
import com.bacchiega.Restaurant.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClientRequestMapper {

    ClientRequestMapper INSTANCE = Mappers.getMapper(ClientRequestMapper.class);

    Client toClient(ClientRequestDto clientRequestDto);

}
