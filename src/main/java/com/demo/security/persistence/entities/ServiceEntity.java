package com.demo.security.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "servicio")
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", length = 50, nullable = false)
    private String name;
    @Column(name = "descripcion", length = 255, nullable = false)
    private String description;
    @Column(name = "estado", nullable = false)
    private boolean status;
    @Column(name = "precio_base", nullable = false)
    private double price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private CategoryEntity category;
}

