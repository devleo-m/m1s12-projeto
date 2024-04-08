package com.study.m1s11projeto.service.impl;

import com.study.m1s11projeto.DTO.MediaGeralAlunoDTO;
import com.study.m1s11projeto.entity.AlunoEntity;
import com.study.m1s11projeto.entity.DisciplinaEntity;
import com.study.m1s11projeto.entity.DisciplinaMatriculaEntity;
import com.study.m1s11projeto.exception.NotFoundException;
import com.study.m1s11projeto.repository.DisciplinaMatriculaRepository;
import com.study.m1s11projeto.service.AlunoService;
import com.study.m1s11projeto.service.DisciplinaMatriculaService;
import com.study.m1s11projeto.service.DisciplinaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Implementação do serviço de matrícula em disciplinas.
 */

@Service
public class DisciplinaMatriculaServiceImpl implements DisciplinaMatriculaService {
    private final DisciplinaMatriculaRepository matriculaRepository;
    private final AlunoService alunoService;
    private final DisciplinaService disciplinaService;

    private static final Logger logger = LoggerFactory.getLogger(DisciplinaMatriculaServiceImpl.class);

    /**
     * Construtor da classe DisciplinaMatriculaServiceImpl.
     * @param matriculaRepository Repositório de matrículas em disciplinas.
     * @param alunoService Serviço de alunos.
     * @param disciplinaService Serviço de disciplinas.
     */
    public DisciplinaMatriculaServiceImpl(DisciplinaMatriculaRepository matriculaRepository, AlunoService alunoService, DisciplinaService disciplinaService) {
        this.matriculaRepository = matriculaRepository;
        this.alunoService = alunoService;
        this.disciplinaService = disciplinaService;
    }

    /**
     * Matricula um aluno em uma disciplina.
     * @param idAluno ID do aluno.
     * @param idDisciplina ID da disciplina.
     * @return A entidade de matrícula criada.
     * @throws NotFoundException Se as notas já foram lançadas para o aluno na disciplina.
     */
    @Override
    public DisciplinaMatriculaEntity matricularAluno(Long idAluno, Long idDisciplina) {
        logger.info("Matriculando aluno na disciplina - Aluno ID: {}, Disciplina ID: {}", idAluno, idDisciplina);
        AlunoEntity aluno = alunoService.obterAlunoPorId(idAluno);
        DisciplinaEntity disciplina = disciplinaService.obterDisciplinaPorId(idDisciplina);

        // Matricular aluno na disciplina
        DisciplinaMatriculaEntity matricula = new DisciplinaMatriculaEntity();
        matricula.setAluno(aluno);
        matricula.setDisciplina(disciplina);

        return matriculaRepository.save(matricula);
    }

    /**
     * Deleta a matrícula de uma disciplina.
     * @param id ID da matrícula a ser deletada.
     * @throws NotFoundException Se as notas já foram lançadas para a matrícula.
     */
    @Override
    public void deletarMatricula(Long id) {
        logger.info("Deletando matrícula com ID: {}", id);
        // Verificar se notas já foram lançadas para a matrícula com o ID especificado
        DisciplinaMatriculaEntity matricula = matriculaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Matrícula não encontrada com o ID: " + id));

        // Verificar se a média final da matrícula é diferente de zero (indicando notas já lançadas)
        if (matricula.getMediaFinal().compareTo(BigDecimal.ZERO) != 0) {
            throw new NotFoundException("Notas já lançadas para a matrícula com o ID: " + id);
        }

        // Se não houver notas lançadas, deletar a matrícula
        matriculaRepository.deleteById(id);
    }

    /**
     * Busca uma matrícula por seu ID.
     * @param id ID da matrícula a ser buscada.
     * @return A matrícula encontrada, ou null se não encontrada.
     */
    @Override
    public DisciplinaMatriculaEntity buscarMatriculaPorId(Long id) {
        logger.info("Buscando matrícula com ID: {}", id);
        return matriculaRepository.findById(id).orElse(null);
    }

    /**
     * Busca todas as matrículas de um aluno.
     * @param idAluno ID do aluno.
     * @return Lista de matrículas do aluno.
     */
    @Override
    public List<DisciplinaMatriculaEntity> buscarMatriculasPorAluno(Long idAluno) {
        logger.info("Buscando matrículas do aluno com ID: {}", idAluno);
        AlunoEntity aluno = alunoService.obterAlunoPorId(idAluno);
        return matriculaRepository.findByAluno(aluno);
    }

    /**
     * Busca todas as matrículas de uma disciplina.
     * @param idDisciplina ID da disciplina.
     * @return Lista de matrículas da disciplina.
     */
    @Override
    public List<DisciplinaMatriculaEntity> buscarMatriculasPorDisciplina(Long idDisciplina) {
        logger.info("Buscando matrículas da disciplina com ID: {}", idDisciplina);
        DisciplinaEntity disciplina = disciplinaService.obterDisciplinaPorId(idDisciplina);
        return matriculaRepository.findByDisciplina(disciplina);
    }

    /**
     * Metodo final do card 7!
     * Calcula a média geral do aluno com base nas notas de todas as disciplinas matriculadas.
     * @param idAluno ID do aluno.
     * @return Objeto MediaGeralAlunoDTO contendo a média geral do aluno.
     */
    @Override
    public MediaGeralAlunoDTO calcularMediaGeralDoAluno(Long idAluno) {
        logger.info("Calculando média geral do aluno com ID: {}", idAluno);
        List<DisciplinaMatriculaEntity> matriculas = matriculaRepository.findByAluno_Id(idAluno);

        double somaDasMedias = 0.0;
        int quantidadeDeDisciplinas = matriculas.size();

        for (DisciplinaMatriculaEntity matricula : matriculas) {
            somaDasMedias += matricula.getMediaFinal().doubleValue();
        }

        double mediaGeral = quantidadeDeDisciplinas > 0 ? somaDasMedias / quantidadeDeDisciplinas : 0.0;

        MediaGeralAlunoDTO mediaGeralAlunoDTO = new MediaGeralAlunoDTO();
        mediaGeralAlunoDTO.setMediaGeral(mediaGeral);

        return mediaGeralAlunoDTO;
    }
}
