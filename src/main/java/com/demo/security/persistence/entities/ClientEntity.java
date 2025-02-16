package com.demo.security.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "cliente")
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", nullable = false, length = 25)
    private String name;
    @Column(name = "correo", nullable = false, length = 25)
    private String email;
    @Column(name = "telefono", nullable = false, length = 20)
    private String phone;
}
