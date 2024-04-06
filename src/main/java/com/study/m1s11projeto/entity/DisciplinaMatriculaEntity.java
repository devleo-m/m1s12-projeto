package com.study.m1s11projeto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "disciplina_matricula")
public class DisciplinaMatriculaEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //ALUNO
    @ManyToOne(optional = false)
    @JoinColumn(name = "aluno_id", referencedColumnName = "id")
    private AlunoEntity aluno;

    //DISCIPLINA
    @ManyToOne(optional = false)
    @JoinColumn(name = "disciplina_id", referencedColumnName = "id")
    private DisciplinaEntity disciplina;

    //Data Matricula
    @Column(name = "data_matricula", nullable = false)
    private LocalDate dataMatricula;

    //MEDIA NOTA FINAL
    @Column(name = "media_final", nullable = false, precision = 5, scale = 2)
    @ColumnDefault(value ="0.00")
    private BigDecimal mediaFinal;

    //LISTA DE NOTAS
    @JsonIgnore
    @OneToMany(mappedBy = "matricula")
    private List<NotaEntity> notas;

    @PrePersist
    protected void calcularMediaFinal() {
        // definir a data de matrícula automaticamente antes de salvar
        dataMatricula = LocalDate.now();

        // implementar a lógica para calcular a média final
        // por exemplo, se tiver uma lista de notas, pode calcular a média a partir delas
        // aqui estamos apenas definindo um valor padrão
        if (mediaFinal == null) {
            mediaFinal = BigDecimal.ZERO;
        }
    }
}
