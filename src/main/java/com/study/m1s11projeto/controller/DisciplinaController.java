package com.study.m1s11projeto.controller;

import com.study.m1s11projeto.entity.DisciplinaEntity;
import com.study.m1s11projeto.entity.ProfessorEntity;
import com.study.m1s11projeto.service.DisciplinaService;
import com.study.m1s11projeto.service.ProfessorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/disciplinas")
public class DisciplinaController {

    private final DisciplinaService disciplinaService;

    public DisciplinaController(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    @GetMapping
    public ResponseEntity<List<DisciplinaEntity>> listarDisciplinas() {
        log.info("GET /api/disciplinas -> Listando todas as disciplinas");
        List<DisciplinaEntity> disciplinas = disciplinaService.listarDisciplinas();
        log.debug("GET /api/disciplinas -> Total de disciplinas encontradas: {}", disciplinas.size());
        return ResponseEntity.status(HttpStatus.OK).body(disciplinas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisciplinaEntity> obterDisciplinaPorId(@PathVariable Long id) {
        log.info("GET /api/disciplinas/{} -> Obtendo disciplina por ID", id);
        DisciplinaEntity disciplina = disciplinaService.obterDisciplinaPorId(id);
        log.debug("GET /api/disciplinas/{} -> Disciplina encontrada: {}", id, disciplina);
        return ResponseEntity.status(HttpStatus.OK).body(disciplina);
    }

    @PostMapping
    public ResponseEntity<DisciplinaEntity> adicionarDisciplina(@RequestBody DisciplinaEntity disciplina) {
        log.info("POST /api/disciplinas -> Adicionando nova disciplina: {}", disciplina);
        DisciplinaEntity novaDisciplina = disciplinaService.adicionarDisciplina(disciplina);
        log.info("POST /api/disciplinas -> Nova disciplina adicionada: {}", novaDisciplina);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaDisciplina);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisciplinaEntity> atualizarDisciplina(@PathVariable Long id, @RequestBody DisciplinaEntity disciplinaAtualizada) {
        log.info("PUT /api/disciplinas/{} -> Atualizando disciplina com o ID: {}", id, id);
        DisciplinaEntity disciplina = disciplinaService.atualizarDisciplina(id, disciplinaAtualizada);
        log.info("PUT /api/disciplinas/{} -> Disciplina atualizada: {}", id, disciplina);
        return ResponseEntity.status(HttpStatus.OK).body(disciplina);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarDisciplina(@PathVariable Long id) {
        log.info("DELETE /api/disciplinas/{} -> Deletando disciplina com o ID: {}", id, id);
        disciplinaService.deletarDisciplina(id);
        log.info("DELETE /api/disciplinas/{} -> Disciplina deletada com sucesso", id);
        return ResponseEntity.noContent().build();
    }
}
