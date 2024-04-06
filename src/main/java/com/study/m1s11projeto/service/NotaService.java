package com.study.m1s11projeto.service;

import com.study.m1s11projeto.entity.NotaEntity;

public interface NotaService {
    NotaEntity adicionarNota(Long idMatricula, Double nota, Double coeficiente);
}
