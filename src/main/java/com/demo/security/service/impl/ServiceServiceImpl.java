package com.demo.security.service.impl;

import com.demo.security.persistence.entities.CategoryEntity;
import com.demo.security.persistence.entities.ServiceEntity;
import com.demo.security.persistence.repository.CategoryRepository;
import com.demo.security.persistence.repository.ServiceRepository;
import com.demo.security.presentation.dto.request.ServiceCreateDTO;
import com.demo.security.presentation.dto.response.CategoryDTO;
import com.demo.security.presentation.dto.response.ServiceDTO;
import com.demo.security.presentation.exceptions.BadRequestException;
import com.demo.security.service.interfaces.ServiceService;
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
public class ServiceServiceImpl implements ServiceService {
private final ServiceRepository serviceRepository;
private final CategoryRepository categoryRepository;
    @Override
    public ServiceDTO createService(ServiceCreateDTO serviceCreateDTO) {
        CategoryEntity categoryEntity = categoryRepository.findById(serviceCreateDTO.categoryId())
                .orElseThrow(() -> new BadRequestException(ConstantApplication.CATEGORY_NOT_FOUND));
        if(serviceRepository.existsByName(serviceCreateDTO.name())){
            throw new BadRequestException(ConstantApplication.SERVICE_NAME_EXIST);
        }
        if(categoryEntity==null){
            throw new BadRequestException(ConstantApplication.CATEGORY_NOT_FOUND);
        }
        return mapToServiceDTO(serviceRepository.save(mapToServiceEntity(serviceCreateDTO)));
    }

    @Override
    public ServiceDTO updateService(Long id, ServiceCreateDTO serviceCreateDTO) {
        ServiceEntity serviceEntity = serviceRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ConstantApplication.SERVICE_NOT_FOUND));
        CategoryEntity categoryEntity = categoryRepository.findById(serviceCreateDTO.categoryId())
                .orElseThrow(() -> new BadRequestException(ConstantApplication.CATEGORY_NOT_FOUND));
        if(!serviceRepository.existsById(id)){
            throw new BadRequestException(ConstantApplication.SERVICE_NOT_FOUND);
        }
        if(serviceRepository.existsByNameAndIdNot(serviceCreateDTO.name(), id)){
            throw new BadRequestException(ConstantApplication.SERVICE_NAME_EXIST);
        }
        if(categoryEntity==null){
            throw new BadRequestException(ConstantApplication.CATEGORY_NOT_FOUND);
        }
        serviceEntity.setId(id);
        serviceEntity.setName(serviceCreateDTO.name());
        serviceEntity.setDescription(serviceCreateDTO.description());
        serviceEntity.setPrice(serviceCreateDTO.price());
        serviceEntity.setCategory(categoryEntity);
        serviceEntity.setStatus(serviceCreateDTO.status());
        return mapToServiceDTO(serviceRepository.save(serviceEntity));
    }

    @Override
    public void deleteService(Long id) {
        ServiceEntity serviceEntity = serviceRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ConstantApplication.SERVICE_NOT_FOUND));
        serviceRepository.delete(serviceEntity);

    }

    @Override
    public Page<ServiceDTO> getServices(Pageable pageable) {
        Page<ServiceEntity> serviceEntities = serviceRepository.findAll(pageable);
        if(serviceEntities.isEmpty()){
            throw new BadRequestException(ConstantApplication.SERVICE_NOT_FOUND);
        }
        return serviceEntities.map(this::mapToServiceDTO);
    }

    @Override
    public ServiceDTO getService(Long id) {
        return serviceRepository.findById(id)
                .map(this::mapToServiceDTO)
                .orElseThrow(() -> new BadRequestException(ConstantApplication.SERVICE_NOT_FOUND));
    }
    private ServiceDTO mapToServiceDTO(ServiceEntity serviceEntity) {
        CategoryDTO categoryDTO = categoryRepository.findById(serviceEntity.getCategory().getId())
                .map(categoryEntity -> CategoryDTO.builder()
                        .id(categoryEntity.getId())
                        .name(categoryEntity.getName())
                        .description(categoryEntity.getDescription())
                        .status(categoryEntity.isStatus())
                        .build())
                .orElseThrow(() -> new BadRequestException(ConstantApplication.CATEGORY_NOT_FOUND));
        return ServiceDTO.builder()
                .id(serviceEntity.getId())
                .name(serviceEntity.getName())
                .description(serviceEntity.getDescription())
                .price(serviceEntity.getPrice())
                .category(categoryDTO)
                .status(serviceEntity.isStatus())
                .build();
    }
    private ServiceEntity mapToServiceEntity(ServiceCreateDTO serviceCreateDTO) {
        CategoryEntity categoryEntity = categoryRepository.findById(serviceCreateDTO.categoryId())
                .orElseThrow(() -> new BadRequestException(ConstantApplication.CATEGORY_NOT_FOUND));
        return ServiceEntity.builder()
                .name(serviceCreateDTO.name())
                .description(serviceCreateDTO.description())
                .price(serviceCreateDTO.price())
                .category(categoryEntity)
                .status(serviceCreateDTO.status())
                .build();
    }

}
