package com.demo.security.service.impl;

import com.demo.security.persistence.entities.ClientEntity;
import com.demo.security.persistence.repository.ClientRepository;
import com.demo.security.presentation.dto.request.ClienteCreateDTO;
import com.demo.security.presentation.dto.response.ClientDTO;
import com.demo.security.presentation.exceptions.BadRequestException;
import com.demo.security.presentation.exceptions.NotFoundException;
import com.demo.security.service.interfaces.ClientService;
import com.demo.security.util.ConstantApplication;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    @Override
    public ClientDTO createClient(ClienteCreateDTO dto) {
        if(clientRepository.existsByEmail(dto.email())){
            throw new BadRequestException(ConstantApplication.CLIENT_EMAIL_EXIST);
        }
        if (clientRepository.existsByPhone(dto.phone())){
            throw new BadRequestException(ConstantApplication.USER_PHONE_EXIST);
        }
            return mapToClientDTO(clientRepository.save(mapToClientEntity(dto)));
    }



    @Override
    public ClientDTO getClient(Long id) {
        return clientRepository.findById(id)
                .map(this::mapToClientDTO)
                .orElseThrow(() -> new NotFoundException(ConstantApplication.CLIENT_NOT_FOUND));
    }

    @Override
    public ClientDTO updateClient(Long id, ClienteCreateDTO dto) {
        ClientEntity client = clientRepository.findById(id).orElseThrow(() -> new NotFoundException(ConstantApplication.CLIENT_NOT_FOUND));
        if(!clientRepository.existsById(id)){
            throw new NotFoundException(ConstantApplication.CLIENT_NOT_FOUND);
        }
        if (clientRepository.existsByEmailAndIdNot(dto.email(), id)){
            throw new BadRequestException(ConstantApplication.USER_PHONE_EXIST);
        }
        if (clientRepository.existsByPhoneAndIdNot(dto.phone(), id)){
            throw new BadRequestException(ConstantApplication.USER_PHONE_EXIST);
        }
        client.setEmail(dto.email());
        client.setName(dto.name());
        client.setPhone(dto.phone());
        return mapToClientDTO(clientRepository.save(client));
    }

    @Override
    public void deleteClient(Long id) {
        if(!clientRepository.existsById(id)){
            throw new NotFoundException(ConstantApplication.CLIENT_NOT_FOUND);
        }
        clientRepository.deleteById(id);
    }

    @Override
    public Page<ClientDTO> listClient(Pageable pageable) {
        Page <ClientEntity> listClients= clientRepository.findAll(pageable);
        if(listClients.isEmpty()){
            throw new NotFoundException(ConstantApplication.CLIENT_NOT_FOUND);
        }
        return listClients.map(this::mapToClientDTO);
    }
    private ClientDTO mapToClientDTO(ClientEntity client) {
        return ClientDTO.builder()
                .id(client.getId())
                .name(client.getName())
                .email(client.getEmail())
                .phone(client.getPhone())
                .build();
    }
    private ClientEntity mapToClientEntity(ClienteCreateDTO dto) {
        return ClientEntity.builder()
                .name(dto.name())
                .email(dto.email())
                .phone(dto.phone())
                .build();
    }
}
