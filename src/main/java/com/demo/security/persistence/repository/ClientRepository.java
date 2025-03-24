package com.demo.security.persistence.repository;

import com.demo.security.persistence.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsById(Long id);
    boolean existsByEmailAndIdNot(String email, Long id);
    boolean existsByPhoneAndIdNot(String phone, Long id);

}
