package com.demo.security.presentation.controllers;

import com.demo.security.presentation.dto.request.ClienteCreateDTO;
import com.demo.security.presentation.dto.response.ClientDTO;
import com.demo.security.service.interfaces.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;
    @PostMapping("/create")
    public ResponseEntity<ClientDTO> createClient(@Valid @RequestBody ClienteCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clientService.createClient(dto));
    }
    @GetMapping("get-by-id/{id}")
    public ResponseEntity<ClientDTO> getClient(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClient(id));
    }
    @GetMapping("/get-all")
    public ResponseEntity<Page<ClientDTO>> listClients(
            @PageableDefault(sort = "id") Pageable pageable
    ) {
        return ResponseEntity.ok(clientService.listClient(pageable));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ClientDTO> updateClient(
            @PathVariable Long id,
            @Valid @RequestBody ClienteCreateDTO dto
    ) {
        return ResponseEntity.ok(clientService.updateClient(id, dto));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
