package com.demo.security.persistence.repository;

import com.demo.security.persistence.entities.ClientEntity;
import com.demo.security.persistence.entities.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {
    boolean existsByPlate(String plate);
    boolean existsByPlateAndIdNot(String plate, Long id);

    boolean existsByClient(ClientEntity client);
}
