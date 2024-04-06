package com.study.m1s11projeto.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "nota")
public class NotaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long notaId;

    @Column(nullable = false)
    @ColumnDefault(value = "0.00")
    private Double nota;

    @Column(nullable = false)
    @ColumnDefault(value = "0.00")
    private Double coeficiente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "professor_id")
    private ProfessorEntity professor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "disciplina_matricula_id", nullable = false)
    private DisciplinaMatriculaEntity matricula;

    // Método para configurar o ID da matrícula
    public void setIdMatricula(Long idMatricula) {
        if (this.matricula == null) {
            this.matricula = new DisciplinaMatriculaEntity(); // Crie uma nova instância se ainda não estiver definida
        }
        this.matricula.setId(idMatricula);
    }

}
