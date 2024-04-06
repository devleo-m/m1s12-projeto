package com.study.m1s11projeto.repository;

import com.study.m1s11projeto.entity.AlunoEntity;
import com.study.m1s11projeto.entity.DisciplinaEntity;
import com.study.m1s11projeto.entity.DisciplinaMatriculaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisciplinaMatriculaRepository extends JpaRepository<DisciplinaMatriculaEntity, Long> {
    @Query("SELECT dm FROM DisciplinaMatriculaEntity dm WHERE dm.aluno = :aluno")
    List<DisciplinaMatriculaEntity> findByAluno(@Param("aluno") AlunoEntity aluno);
    List<DisciplinaMatriculaEntity> findByDisciplina(DisciplinaEntity disciplina);

    List<DisciplinaMatriculaEntity> findByAluno_Id(Long alunoId); // Alteração Card7
}
