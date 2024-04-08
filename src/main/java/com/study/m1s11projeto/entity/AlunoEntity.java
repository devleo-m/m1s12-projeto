package com.study.m1s11projeto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.NotFound;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "aluno")
public class AlunoEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nome", length = 150)
    private String nome;

    @Temporal(TemporalType.DATE)
    private Date nascimento;

    @JsonIgnore
    @OneToMany(mappedBy = "aluno")
    private List<DisciplinaMatriculaEntity> matriculas;

}

