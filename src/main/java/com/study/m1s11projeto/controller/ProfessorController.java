package com.study.m1s11projeto.controller;

import com.study.m1s11projeto.entity.ProfessorEntity;
import com.study.m1s11projeto.service.ProfessorService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/professores")
public class ProfessorController {

    private static final Logger logger = LoggerFactory.getLogger(AlunoController.class);
    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping
    public ResponseEntity<List<ProfessorEntity>> listarProfessores() {
        logger.info("GET /api/professores -> Listando todos os professores");
        List<ProfessorEntity> professores = professorService.listarProfessores();
        logger.debug("GET /api/professores -> Total de professores encontrados: {}", professores.size());
        return ResponseEntity.status(HttpStatus.OK).body(professores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorEntity> obterProfessorPorId(@PathVariable Long id) {
        logger.info("GET /api/professores/{} -> Obtendo professor por ID", id);
        ProfessorEntity professor = professorService.obterProfessorPorId(id);
        logger.debug("GET /api/professores/{} -> Professor encontrado: {}", id, professor);
        return ResponseEntity.status(HttpStatus.OK).body(professor);
    }

    @PostMapping
    public ResponseEntity<ProfessorEntity> adicionarProfessor(@RequestBody ProfessorEntity professor) {
        logger.info("POST /api/professores -> Adicionando novo professor: {}", professor);
        ProfessorEntity novoProfessor = professorService.adicionarProfessor(professor);
        logger.info("POST /api/professores -> Novo professor adicionado: {}", novoProfessor);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProfessor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorEntity> atualizarProfessor(@PathVariable Long id, @RequestBody ProfessorEntity professorAtualizado) {
        logger.info("PUT /api/professores/{} -> Atualizando professor com o ID: {}", id, id);
        ProfessorEntity professor = professorService.atualizarProfessor(id, professorAtualizado);
        logger.info("PUT /api/professores/{} -> Professor atualizado: {}", id, professor);
        return ResponseEntity.status(HttpStatus.OK).body(professor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProfessor(@PathVariable Long id) {
        logger.info("DELETE /api/professores/{} -> Deletando professor com o ID: {}", id, id);
        professorService.deletarProfessor(id);
        logger.info("DELETE /api/professores/{} -> Professor deletado com sucesso", id);
        return ResponseEntity.noContent().build();
    }
}
