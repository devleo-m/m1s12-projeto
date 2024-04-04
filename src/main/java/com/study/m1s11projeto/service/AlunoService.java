package com.study.m1s11projeto.service;

import com.study.m1s11projeto.entity.AlunoEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface AlunoService {
    List<AlunoEntity> listarAlunos();
    AlunoEntity obterAlunoPorId(Long id);
    AlunoEntity adicionarAluno(AlunoEntity aluno);
    AlunoEntity atualizarAluno(Long id, AlunoEntity alunoAtualizado);
    void deletarAluno(Long id);
}

