package com.study.m1s11projeto.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.NotFound;

import java.util.Date;

@Data
@Entity
@Table(name = "aluno")
public class AlunoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @NotBlank(message = "O nome do aluno é obrigatório")
//    @Size(max = 100, message = "O nome do aluno deve ter no máximo 100 caracteres")
    private String nome;

    @Temporal(TemporalType.DATE)
    private Date nascimento;

}

