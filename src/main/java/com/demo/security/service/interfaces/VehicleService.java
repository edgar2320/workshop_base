package com.demo.security.service.interfaces;

import com.demo.security.presentation.dto.request.VehicleCreateDTO;
import com.demo.security.presentation.dto.response.VehicleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VehicleService {

    VehicleDTO create(VehicleCreateDTO vehicleCreateDTO);

    VehicleDTO update(Long id, VehicleCreateDTO vehicleUpdateDTO);
    VehicleDTO findById(Long id);
    Page<VehicleDTO> listVehicle(Pageable pageable);
    void delete(Long id);
}
