package com.study.m1s11projeto.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "disciplina_matricula")
public class DisciplinaMatriculaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aluno_id", referencedColumnName = "id")
    private AlunoEntity aluno;

    @ManyToOne
    @JoinColumn(name = "disciplina_id", referencedColumnName = "id")
    private DisciplinaEntity disciplina;

    @Column(name = "data_matricula", nullable = false)
    private LocalDate dataMatricula;

    @Column(name = "media_final", nullable = false, precision = 5, scale = 2)
    private BigDecimal mediaFinal;
}
