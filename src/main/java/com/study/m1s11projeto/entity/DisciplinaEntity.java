package com.study.m1s11projeto.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "disciplina")
public class DisciplinaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nome", length = 150)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "professor_id", referencedColumnName = "id")
    @JsonBackReference
    private ProfessorEntity professor;

    @JsonIgnore
    @OneToMany(mappedBy = "disciplina")
    private List<DisciplinaMatriculaEntity> matriculas;

    @Override
    public String toString() {
        return "DisciplinaEntity{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }

}
