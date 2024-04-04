package com.study.m1s11projeto.service;

import com.study.m1s11projeto.entity.DisciplinaMatriculaEntity;

import java.util.List;

public interface DisciplinaMatriculaService {
    DisciplinaMatriculaEntity matricularAluno(Long idAluno, Long idDisciplina);

    void deletarMatricula(Long id);

    DisciplinaMatriculaEntity buscarMatriculaPorId(Long id);

    List<DisciplinaMatriculaEntity> buscarMatriculasPorAluno(Long idAluno);

    List<DisciplinaMatriculaEntity> buscarMatriculasPorDisciplina(Long idDisciplina);
}
