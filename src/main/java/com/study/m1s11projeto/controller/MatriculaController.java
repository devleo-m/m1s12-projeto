package com.study.m1s11projeto.controller;

import com.study.m1s11projeto.DTO.MatriculaDto;
import com.study.m1s11projeto.DTO.MediaGeralAlunoDTO;
import com.study.m1s11projeto.entity.DisciplinaMatriculaEntity;
import com.study.m1s11projeto.service.DisciplinaMatriculaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matriculas")
public class MatriculaController {
    private final DisciplinaMatriculaService disciplinaMatriculaService;
    private static final Logger logger = LoggerFactory.getLogger(MatriculaController.class);

    public MatriculaController(DisciplinaMatriculaService disciplinaMatriculaService) {
        this.disciplinaMatriculaService = disciplinaMatriculaService;
    }

    @PostMapping
    public ResponseEntity<DisciplinaMatriculaEntity> matricularAluno(@RequestBody MatriculaDto matriculaDto) {
        logger.info("Recebida requisição para matricular aluno na disciplina");
        Long idAluno = matriculaDto.getIdAluno();
        Long idDisciplina = matriculaDto.getIdDisciplina();
        DisciplinaMatriculaEntity matricula = disciplinaMatriculaService.matricularAluno(idAluno, idDisciplina);

        logger.info("Aluno matriculado na disciplina - Aluno ID: {}, Disciplina ID: {}", idAluno, idDisciplina);
        return ResponseEntity.status(HttpStatus.CREATED).body(matricula);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMatricula(@PathVariable Long id) {
        logger.info("Recebida requisição para deletar matrícula - Matrícula ID: {}", id);
        disciplinaMatriculaService.deletarMatricula(id);

        logger.info("Matrícula deletada com sucesso - Matrícula ID: {}", id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisciplinaMatriculaEntity> obterMatriculaPorId(@PathVariable Long id) {
        logger.info("Recebida requisição para obter matrícula por ID - Matrícula ID: {}", id);
        DisciplinaMatriculaEntity matricula = disciplinaMatriculaService.buscarMatriculaPorId(id);

        logger.info("Matrícula encontrada - Matrícula ID: {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(matricula);
    }

    @GetMapping("/aluno/{idAluno}")
    public ResponseEntity<List<DisciplinaMatriculaEntity>> obterMatriculasPorAluno(@PathVariable Long idAluno) {
        logger.info("Recebida requisição para obter matrículas por aluno - Aluno ID: {}", idAluno);
        List<DisciplinaMatriculaEntity> matriculas = disciplinaMatriculaService.buscarMatriculasPorAluno(idAluno);

        logger.info("Matrículas encontradas para o aluno - Aluno ID: {}", idAluno);
        return ResponseEntity.status(HttpStatus.OK).body(matriculas);
    }

    @GetMapping("/disciplina/{idDisciplina}")
    public ResponseEntity<List<DisciplinaMatriculaEntity>> obterMatriculasPorDisciplina(@PathVariable Long idDisciplina) {
        logger.info("Recebida requisição para obter matrículas por disciplina - Disciplina ID: {}", idDisciplina);
        List<DisciplinaMatriculaEntity> matriculas = disciplinaMatriculaService.buscarMatriculasPorDisciplina(idDisciplina);

        logger.info("Matrículas encontradas para a disciplina - Disciplina ID: {}", idDisciplina);
        return ResponseEntity.status(HttpStatus.OK).body(matriculas);
    }

    @GetMapping("/aluno/{id}/media-geral")
    public ResponseEntity<MediaGeralAlunoDTO> calcularMediaGeralDoAluno(@PathVariable Long id) {
        logger.info("Recebida requisição para calcular média geral do aluno - Aluno ID: {}", id);
        MediaGeralAlunoDTO mediaGeralAlunoDTO = disciplinaMatriculaService.calcularMediaGeralDoAluno(id);

        logger.info("Média geral calculada para o aluno - Aluno ID: {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(mediaGeralAlunoDTO);
    }
}
