package com.study.m1s11projeto.controller;

import com.study.m1s11projeto.entity.NotaEntity;
import com.study.m1s11projeto.service.NotaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.study.m1s11projeto.entity.AlunoEntity;
import com.study.m1s11projeto.service.AlunoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

    private static final Logger logger = LoggerFactory.getLogger(AlunoController.class);
    private final AlunoService alunoService;
    private final NotaService notaService;

    public AlunoController(AlunoService alunoService, NotaService notaService) {
        this.alunoService = alunoService;
        this.notaService = notaService;
    }

    @GetMapping
    public ResponseEntity<List<AlunoEntity>> listarAlunos() {
        logger.info("GET /api/alunos -> Listando todos os alunos");
        List<AlunoEntity> alunos = alunoService.listarAlunos();
        logger.debug("GET /api/alunos -> Total de alunos encontrados: {}", alunos.size());
        return ResponseEntity.status(HttpStatus.OK).body(alunos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoEntity> obterAlunoPorId(@PathVariable Long id) {
        logger.info("GET /api/alunos/{} -> Obtendo aluno por ID", id);
        AlunoEntity aluno = alunoService.obterAlunoPorId(id);
        logger.debug("GET /api/alunos/{} -> Aluno encontrado: {}", id, aluno);
        return ResponseEntity.status(HttpStatus.OK).body(aluno);
    }

    @PostMapping
    public ResponseEntity<AlunoEntity> adicionarAluno(@RequestBody AlunoEntity aluno) {
        logger.info("POST /api/alunos -> Adicionando novo aluno: {}", aluno);
        AlunoEntity novoAluno = alunoService.adicionarAluno(aluno);
        logger.info("POST /api/alunos -> Novo aluno adicionado: {}", novoAluno);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAluno);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoEntity> atualizarAluno(@PathVariable Long id, @RequestBody AlunoEntity alunoAtualizado) {
        logger.info("PUT /api/alunos/{} -> Atualizando aluno com o ID: {}", id, id);
        AlunoEntity aluno = alunoService.atualizarAluno(id, alunoAtualizado);
        logger.info("PUT /api/alunos/{} -> Aluno atualizado: {}", id, aluno);
        return ResponseEntity.status(HttpStatus.OK).body(aluno);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAluno(@PathVariable Long id) {
        logger.info("DELETE /api/alunos/{} -> Deletando aluno com o ID: {}", id, id);
        alunoService.deletarAluno(id);
        logger.info("DELETE /api/alunos/{} -> Aluno deletado com sucesso", id);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/{id}/media-geral")
//    public ResponseEntity<Double> calcularMediaGeral(@PathVariable Long id) {
//        // Recupere todas as notas do aluno com o ID fornecido
//        List<NotaEntity> notas = notaService.notasPorAluno(id);
//
//        // Calcule a média geral das notas
//        double somaNotas = 0.0;
//        for (NotaEntity nota : notas) {
//            somaNotas += nota.getNota();
//        }
//        double mediaGeral = notas.isEmpty() ? 0.0 : somaNotas / notas.size();
//
//        // Retorne a média geral como resposta
//        return ResponseEntity.status(HttpStatus.OK).body(mediaGeral);
//    }

}
