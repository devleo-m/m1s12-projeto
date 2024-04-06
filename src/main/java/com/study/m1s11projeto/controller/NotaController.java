package com.study.m1s11projeto.controller;

import com.study.m1s11projeto.DTO.NotaDto;
import com.study.m1s11projeto.entity.NotaEntity;
import com.study.m1s11projeto.service.NotaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/notas")
public class NotaController {

    private final NotaService notaService;

    @PostMapping
    public ResponseEntity<NotaEntity> adicionarNota(@RequestBody NotaEntity notaRequest) {
        NotaEntity novaNota = notaService.adicionarNota(notaRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaNota);
    }

    @GetMapping("/matriculas/{id}")
    public ResponseEntity<List<NotaEntity>> listarNotas(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(notaService.notasPorMatricula(id));
    }

    // Em NotaController.java
    @DeleteMapping("{id}")
    public ResponseEntity<Void> excluirNota(@PathVariable Long id) {
        notaService.excluirNotaPorId(id);
        return ResponseEntity.noContent().build();
    }
}
