package com.study.m1s11projeto.service.impl;

import com.study.m1s11projeto.entity.DisciplinaMatriculaEntity;
import com.study.m1s11projeto.entity.NotaEntity;
import com.study.m1s11projeto.entity.ProfessorEntity;
import com.study.m1s11projeto.exception.NotFoundException;
import com.study.m1s11projeto.repository.DisciplinaMatriculaRepository;
import com.study.m1s11projeto.repository.NotaRepository;
import com.study.m1s11projeto.repository.ProfessorRepository;
import com.study.m1s11projeto.service.DisciplinaMatriculaService;
import com.study.m1s11projeto.service.NotaService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class NotaServiceImpl implements NotaService {

    private final NotaRepository notaRepository;
    private final DisciplinaMatriculaRepository disciplinaMatriculaRepository;
    private final ProfessorRepository professorRepository;

    public NotaServiceImpl(NotaRepository notaRepository, DisciplinaMatriculaRepository disciplinaMatriculaRepository, ProfessorRepository professorRepository) {
        this.notaRepository = notaRepository;
        this.disciplinaMatriculaRepository = disciplinaMatriculaRepository;
        this.professorRepository = professorRepository;
    }
    @Override
    public NotaEntity adicionarNota(NotaEntity novaNota) {
        // Verificar se os parâmetros são válidos
        if (novaNota.getMatricula() == null || novaNota.getNota() == null || novaNota.getCoeficiente() == null || novaNota.getProfessor() == null) {
            throw new NotFoundException("Os campos 'matricula', 'nota', 'coeficiente' e 'professor' são obrigatórios para adicionar uma nota");
        }

        // Verificar se a nota está dentro do intervalo válido (0.0 - 10.0)
        if (novaNota.getNota() < 0.0 || novaNota.getNota() > 10.0) {
            throw new RuntimeException("A nota do aluno deve estar no intervalo de 0.0 a 10.0");
        }

        // Salvar a nova nota no banco de dados
        novaNota = notaRepository.save(novaNota);

        // Atualizar a média final da matrícula
        atualizarMediaFinal(novaNota.getMatricula().getId());

        // Retornar a nova nota
        return novaNota;
    }

    private void atualizarMediaFinal(Long idMatricula) {
        // Buscar a matrícula no banco de dados
        DisciplinaMatriculaEntity matricula = disciplinaMatriculaRepository.findById(idMatricula)
                .orElseThrow(() -> new NotFoundException("Matrícula não encontrada com o ID fornecido"));

        // Calcular a média final da matrícula com base nas notas e coeficientes das disciplinas
        Double mediaFinal = calcularMediaFinal(matricula);

        // Atualizar a média final da matrícula
        BigDecimal mediaFinalBigDecimal = BigDecimal.valueOf(mediaFinal);
        matricula.setMediaFinal(mediaFinalBigDecimal);
        disciplinaMatriculaRepository.save(matricula);

    }

    private Double calcularMediaFinal(DisciplinaMatriculaEntity matricula) {
        // Verificar se a matrícula possui disciplinas associadas
        List<NotaEntity> notas = matricula.getNotas();
        if (notas.isEmpty()) {
            return 0.0; // Se não houver notas associadas, a média final será 0.0
        }

        // Inicializar variáveis para calcular a média final
        double somaNotasPonderadas = 0.0;
        double somaCoeficientes = 0.0;

        // Calcular a soma das notas ponderadas e dos coeficientes
        for (NotaEntity nota : notas) {
            somaNotasPonderadas += nota.getNota() * nota.getCoeficiente();
            somaCoeficientes += nota.getCoeficiente();
        }

        // Calcular a média final
        double mediaFinal = somaNotasPonderadas / somaCoeficientes;

        // Retornar a média final calculada
        return mediaFinal;
    }

    @Override
    public List<NotaEntity> notasPorMatricula(Long idMatricula) {
        List<NotaEntity> notas = notaRepository.findByMatriculaId(idMatricula);
        return notas;
    }

    @Override
    public List<NotaEntity> notasPorAluno(Long idAluno) {
        return List.of();
    }

    @Override
    public void excluirNotaPorId(Long idNota) {
        // Verificar se a nota existe
        NotaEntity nota = notaRepository.findById(idNota)
                .orElseThrow(() -> new NotFoundException("Nota não encontrada com o ID fornecido"));

        // Excluir a nota do banco de dados
        notaRepository.delete(nota);

        // Atualizar a média final da matrícula associada
        atualizarMediaFinal(nota.getMatricula().getId());
    }



}
