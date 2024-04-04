package com.study.m1s11projeto.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "professor")
public class ProfessorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150)
    private String nome;
}
