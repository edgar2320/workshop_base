package com.demo.security.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "categoria")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre" , length = 50, nullable = false)
    private String name;
    @Column(name = "descripcion", length = 255, nullable = false)
    private String description;
    @Column(name = "estado",nullable = false)
    private boolean status;
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private Set<ServiceEntity> services= new HashSet<>();
}
