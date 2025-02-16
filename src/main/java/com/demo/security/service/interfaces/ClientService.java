package com.demo.security.service.interfaces;

import com.demo.security.presentation.dto.request.ClienteCreateDTO;
import com.demo.security.presentation.dto.response.ClientDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientService {
    ClientDTO createClient(ClienteCreateDTO dto);
    ClientDTO getClient(Long id);
    ClientDTO updateClient(Long id, ClienteCreateDTO dto);
    void deleteClient(Long id);
    Page<ClientDTO> listClient(Pageable pageable);
}
