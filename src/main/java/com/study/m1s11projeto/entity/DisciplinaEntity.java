package com.study.m1s11projeto.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "disciplina")
public class DisciplinaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "professor_id", referencedColumnName = "id")
    private ProfessorEntity professor;
}