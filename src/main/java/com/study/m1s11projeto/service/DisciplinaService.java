package com.study.m1s11projeto.service;

import com.study.m1s11projeto.entity.AlunoEntity;
import com.study.m1s11projeto.entity.DisciplinaEntity;

import java.util.List;

public interface DisciplinaService {
    List<DisciplinaEntity> listarDisciplinas();
    DisciplinaEntity obterDisciplinaPorId(Long id);
    DisciplinaEntity adicionarDisciplina(DisciplinaEntity disciplina);
    DisciplinaEntity atualizarDisciplina(Long id, DisciplinaEntity atualizadoDisciplina);
    void deletarDisciplina(Long id);
}
