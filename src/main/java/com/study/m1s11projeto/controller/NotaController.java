package com.study.m1s11projeto.controller;

import com.study.m1s11projeto.entity.NotaEntity;
import com.study.m1s11projeto.service.NotaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/notas")
public class NotaController {

    private final NotaService notaService;

    @PostMapping
    public ResponseEntity<NotaEntity> adicionarNota(@RequestParam Long idMatricula,
                                                    @RequestParam Double nota,
                                                    @RequestParam Double coeficiente) {
        NotaEntity novaNota = notaService.adicionarNota(idMatricula, nota, coeficiente);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaNota);
    }
}
