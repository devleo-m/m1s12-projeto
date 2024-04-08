package com.study.m1s11projeto.service;

import com.study.m1s11projeto.entity.NotaEntity;

import java.util.List;

public interface NotaService {
    NotaEntity adicionarNota(NotaEntity novaNota);
    List<NotaEntity> notasPorMatricula(Long idMatricula);
    void excluirNotaPorId(Long idNota);
}
