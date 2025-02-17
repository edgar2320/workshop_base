package com.demo.security.service.impl;

import com.demo.security.persistence.entities.ClientEntity;
import com.demo.security.persistence.entities.VehicleEntity;
import com.demo.security.persistence.repository.ClientRepository;
import com.demo.security.persistence.repository.VehicleRepository;
import com.demo.security.presentation.dto.request.VehicleCreateDTO;
import com.demo.security.presentation.dto.response.ClientDTO;
import com.demo.security.presentation.dto.response.VehicleDTO;
import com.demo.security.presentation.exceptions.BadRequestException;
import com.demo.security.presentation.exceptions.NotFoundException;
import com.demo.security.service.interfaces.VehicleService;
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
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final ClientRepository clientRepository;
    @Override
    public VehicleDTO create(VehicleCreateDTO vehicleCreateDTO) {
        if(vehicleRepository.existsByPlate(vehicleCreateDTO.plate())){
            throw new BadRequestException(ConstantApplication.VEHICLE_PLATE_EXIST);
        }

        return mapToVehicleDTO(vehicleRepository.save(mapToVehicleEntity(vehicleCreateDTO)));
    }

    @Override
    public VehicleDTO update(Long id, VehicleCreateDTO vehicleUpdateDTO) {
        VehicleEntity vehicleEntity = vehicleRepository.findById(id).orElseThrow(() -> new BadRequestException(ConstantApplication.VEHICLE_NOT_FOUND));
        ClientEntity client = clientRepository.findById(vehicleUpdateDTO.clientId()).orElseThrow(() -> new BadRequestException(ConstantApplication.CLIENT_NOT_FOUND));
        if(!vehicleRepository.existsById(id)){
            throw new BadRequestException(ConstantApplication.VEHICLE_NOT_FOUND);
        }
        if(vehicleRepository.existsByPlateAndIdNot(vehicleUpdateDTO.plate(), id)){
            throw new BadRequestException(ConstantApplication.VEHICLE_PLATE_EXIST);
        }
        if(client==null){
            throw new BadRequestException(ConstantApplication.CLIENT_NOT_FOUND);
        }
        vehicleEntity.setId(id);
        vehicleEntity.setPlate(vehicleUpdateDTO.plate());
        vehicleEntity.setModel(vehicleUpdateDTO.model());
        vehicleEntity.setMark(vehicleUpdateDTO.mark());
        vehicleEntity.setYear(vehicleUpdateDTO.year());
        vehicleEntity.setClient(client);
        return mapToVehicleDTO(vehicleRepository.save(vehicleEntity));

    }

    @Override
    public VehicleDTO findById(Long id) {
        return vehicleRepository.findById(id)
                .map(this::mapToVehicleDTO)
                .orElseThrow(() -> new BadRequestException(ConstantApplication.VEHICLE_NOT_FOUND));
    }

    @Override
    public Page<VehicleDTO> listVehicle(Pageable pageable) {
        Page<VehicleEntity> listVehicle = vehicleRepository.findAll(pageable);
        if (listVehicle.isEmpty()) {
            throw new NotFoundException(ConstantApplication.VEHICLE_NOT_LIST);
        }
        return listVehicle.map(this::mapToVehicleDTO);
    }

    @Override
    public void delete(Long id) {
        if(!vehicleRepository.existsById(id)){
            throw new NotFoundException(ConstantApplication.VEHICLE_NOT_FOUND);
        }
        vehicleRepository.deleteById(id);
    }

    private VehicleDTO mapToVehicleDTO(VehicleEntity vehicleEntity) {
        ClientDTO clientDTO = clientRepository.findById(vehicleEntity.getClient().getId())
                .map(clientEntity -> ClientDTO.builder()
                        .id(clientEntity.getId())
                        .name(clientEntity.getName())
                        .email(clientEntity.getEmail())
                        .phone(clientEntity.getPhone())
                        .build())
                .orElseThrow(() -> new BadRequestException(ConstantApplication.CLIENT_NOT_FOUND));
        return VehicleDTO.builder()
                .id(vehicleEntity.getId())
                .plate(vehicleEntity.getPlate())
                .model(vehicleEntity.getModel())
                .mark(vehicleEntity.getMark())
                .year(vehicleEntity.getYear())
                .client(clientDTO)
                .build();
    }
    private VehicleEntity mapToVehicleEntity(VehicleCreateDTO vehicleDTO) {
        ClientEntity client = clientRepository.findById(vehicleDTO.clientId()).orElseThrow(() -> new BadRequestException(ConstantApplication.CLIENT_NOT_FOUND));
        return VehicleEntity.builder()
                .plate(vehicleDTO.plate())
                .model(vehicleDTO.model())
                .mark(vehicleDTO.mark())
                .year(vehicleDTO.year())
                .client(client)
                .build();
    }

}
