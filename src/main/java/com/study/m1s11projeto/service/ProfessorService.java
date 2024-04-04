package com.study.m1s11projeto.service;

import com.study.m1s11projeto.entity.ProfessorEntity;

import java.util.List;

public interface ProfessorService {
    List<ProfessorEntity> listarProfessores();
    ProfessorEntity obterProfessorPorId(Long id);
    ProfessorEntity adicionarProfessor(ProfessorEntity professor);
    ProfessorEntity atualizarProfessor(Long id, ProfessorEntity professorAtualizado);
    void deletarProfessor(Long id);
}
