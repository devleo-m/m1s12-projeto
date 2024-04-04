package com.study.m1s11projeto.exception.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Erro {
    private String codigo;
    private String mensagem;
}