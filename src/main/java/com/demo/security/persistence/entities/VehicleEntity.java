package com.demo.security.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "vehiculo")
public class VehicleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "placa", length = 20, nullable = false)
    private String plate;
    @Column(name = "marca", length = 50, nullable = false)
    private String mark;
    @Column(name = "modelo", length = 50, nullable = false)
    private String model;
    @Column(name = "anio",nullable = false)
    private int year;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClientEntity client;
}
