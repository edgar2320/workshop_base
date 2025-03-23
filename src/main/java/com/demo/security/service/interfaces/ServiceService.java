package com.demo.security.service.interfaces;

import com.demo.security.presentation.dto.request.ServiceCreateDTO;
import com.demo.security.presentation.dto.response.ServiceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ServiceService {
    ServiceDTO createService(ServiceCreateDTO serviceCreateDTO);
    ServiceDTO updateService(Long id, ServiceCreateDTO serviceCreateDTO);
    void deleteService(Long id);
    Page<ServiceDTO> getServices(Pageable pageable);
    ServiceDTO getService(Long id);
}
