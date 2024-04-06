package com.study.m1s11projeto.controller;

import com.study.m1s11projeto.DTO.MatriculaDto;
import com.study.m1s11projeto.entity.DisciplinaMatriculaEntity;
import com.study.m1s11projeto.service.DisciplinaMatriculaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matriculas")
public class MatriculaController {
    private final DisciplinaMatriculaService disciplinaMatriculaService;

    public MatriculaController(DisciplinaMatriculaService disciplinaMatriculaService) {
        this.disciplinaMatriculaService = disciplinaMatriculaService;
    }

    @PostMapping
    public ResponseEntity<DisciplinaMatriculaEntity> matricularAluno(@RequestBody MatriculaDto matriculaDto) {
        Long idAluno = matriculaDto.getIdAluno();
        Long idDisciplina = matriculaDto.getIdDisciplina();

        // Chame o serviço para matricular o aluno na disciplina
        DisciplinaMatriculaEntity matricula = disciplinaMatriculaService.matricularAluno(idAluno, idDisciplina);

        // Retorne a resposta com o resultado da operação
        return ResponseEntity.status(HttpStatus.CREATED).body(matricula);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMatricula(@PathVariable Long id) {
        disciplinaMatriculaService.deletarMatricula(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisciplinaMatriculaEntity> obterMatriculaPorId(@PathVariable Long id) {
        DisciplinaMatriculaEntity matricula = disciplinaMatriculaService.buscarMatriculaPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(matricula);
    }

    @GetMapping("/aluno/{idAluno}")
    public ResponseEntity<List<DisciplinaMatriculaEntity>> obterMatriculasPorAluno(@PathVariable Long idAluno) {
        List<DisciplinaMatriculaEntity> matriculas = disciplinaMatriculaService.buscarMatriculasPorAluno(idAluno);
        return ResponseEntity.status(HttpStatus.OK).body(matriculas);
    }

    @GetMapping("/disciplina/{idDisciplina}")
    public ResponseEntity<List<DisciplinaMatriculaEntity>> obterMatriculasPorDisciplina(@PathVariable Long idDisciplina) {
        List<DisciplinaMatriculaEntity> matriculas = disciplinaMatriculaService.buscarMatriculasPorDisciplina(idDisciplina);
        return ResponseEntity.status(HttpStatus.OK).body(matriculas);
    }
}
