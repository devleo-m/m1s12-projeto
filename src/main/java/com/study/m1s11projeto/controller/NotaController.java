package com.study.m1s11projeto.controller;

import com.study.m1s11projeto.DTO.NotaDto;
import com.study.m1s11projeto.entity.NotaEntity;
import com.study.m1s11projeto.service.NotaService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/notas")
public class NotaController {

    private final NotaService notaService;
    private static final Logger logger = LoggerFactory.getLogger(NotaController.class);


    @PostMapping
    public ResponseEntity<NotaEntity> adicionarNota(@RequestBody NotaEntity notaRequest) {
        logger.info("Adicionando nova nota: {}", notaRequest);
        NotaEntity novaNota = notaService.adicionarNota(notaRequest);
        logger.info("Nota adicionada com sucesso: {}", novaNota);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaNota);
    }

    @GetMapping("/matriculas/{id}")
    public ResponseEntity<List<NotaEntity>> listarNotas(@PathVariable Long id) {
        logger.info("Listando notas para a matrícula com ID: {}", id);
        List<NotaEntity> notas = notaService.notasPorMatricula(id);
        logger.info("Notas listadas com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(notas);
    }

    // Em NotaController.java
    @DeleteMapping("{id}")
    public ResponseEntity<Void> excluirNota(@PathVariable Long id) {
        logger.info("Excluindo nota com ID: {}", id);
        notaService.excluirNotaPorId(id);
        logger.info("Nota excluída com sucesso");
        return ResponseEntity.noContent().build();
    }
}
