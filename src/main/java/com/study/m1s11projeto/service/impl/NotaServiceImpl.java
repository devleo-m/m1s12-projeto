package com.study.m1s11projeto.service.impl;

import com.study.m1s11projeto.entity.DisciplinaMatriculaEntity;
import com.study.m1s11projeto.entity.NotaEntity;
import com.study.m1s11projeto.exception.NotFoundException;
import com.study.m1s11projeto.repository.DisciplinaMatriculaRepository;
import com.study.m1s11projeto.repository.NotaRepository;
import com.study.m1s11projeto.service.NotaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Implementação do serviço de notas.
 */

@Service
public class NotaServiceImpl implements NotaService {

    private final NotaRepository notaRepository;
    private final DisciplinaMatriculaRepository disciplinaMatriculaRepository;

    private static final Logger logger = LoggerFactory.getLogger(NotaServiceImpl.class);

    /**
     * Construtor da classe NotaServiceImpl.
     * @param notaRepository Repositório de notas.
     * @param disciplinaMatriculaRepository Repositório de matrículas em disciplinas.
     */
    public NotaServiceImpl(NotaRepository notaRepository, DisciplinaMatriculaRepository disciplinaMatriculaRepository) {
        this.notaRepository = notaRepository;
        this.disciplinaMatriculaRepository = disciplinaMatriculaRepository;
    }

    /**
     * Adiciona uma nova nota para uma matrícula.
     * @param novaNota A nota a ser adicionada.
     * @return A nova nota adicionada.
     * @throws NotFoundException Se os campos obrigatórios não forem fornecidos.
     * @throws RuntimeException Se a nota estiver fora do intervalo válido.
     */
    @Override
    public NotaEntity adicionarNota(NotaEntity novaNota) {
        logger.info("Adicionando nova nota para a matrícula com ID: {}", novaNota.getMatricula().getId());
        validarNota(novaNota);
        novaNota = notaRepository.save(novaNota);
        atualizarMediaFinal(novaNota.getMatricula().getId());

        logger.info("Nota adicionada com sucesso");
        return novaNota;
    }

    private void validarNota(NotaEntity novaNota) {
        if (novaNota.getMatricula() == null || novaNota.getNota() == null || novaNota.getCoeficiente() == null) {
            throw new NotFoundException("Os campos 'matricula', 'nota' e 'coeficiente' são obrigatórios para adicionar uma nota");
        }

        if (novaNota.getNota() < 0.0 || novaNota.getNota() > 10.0) {
            throw new RuntimeException("A nota do aluno deve estar no intervalo de 0.0 a 10.0");
        }
    }

    /**
     * Atualiza a média final de uma matrícula após adicionar uma nova nota.
     * @param idMatricula O ID da matrícula a ser atualizada.
     */
    private void atualizarMediaFinal(Long idMatricula) {
        logger.info("Atualizando a média final para a matrícula com ID: {}", idMatricula);

        DisciplinaMatriculaEntity matricula = disciplinaMatriculaRepository.findById(idMatricula)
                .orElseThrow(() -> new NotFoundException("Matrícula não encontrada com o ID fornecido"));

        Double mediaFinal = calcularMediaFinal(matricula);
        matricula.setMediaFinal(BigDecimal.valueOf(mediaFinal));
        disciplinaMatriculaRepository.save(matricula);

        logger.info("Média final da matrícula atualizada com sucesso");
    }

    /**
     * Calcula a média final de uma matrícula com base nas notas e coeficientes das disciplinas.
     * @param matricula A matrícula para a qual calcular a média final.
     * @return A média final calculada.
     */
    private Double calcularMediaFinal(DisciplinaMatriculaEntity matricula) {
        List<NotaEntity> notas = matricula.getNotas();
        if (notas.isEmpty()) {
            return 0.0;
        }

        double somaNotasPonderadas = 0.0;
        double somaCoeficientes = 0.0;

        for (NotaEntity nota : notas) {
            somaNotasPonderadas += nota.getNota() * nota.getCoeficiente();
            somaCoeficientes += nota.getCoeficiente();
        }

        return somaNotasPonderadas / somaCoeficientes;
    }

    @Override
    public List<NotaEntity> notasPorMatricula(Long idMatricula) {
        logger.info("Listando notas para a matrícula com ID: {}", idMatricula);
        return notaRepository.findByMatriculaId(idMatricula);
    }


    @Override
    public void excluirNotaPorId(Long idNota) {
        logger.info("Excluindo nota com ID: {}", idNota);
        NotaEntity nota = notaRepository.findById(idNota)
                .orElseThrow(() -> new NotFoundException("Nota não encontrada com o ID fornecido"));

        notaRepository.delete(nota);
        atualizarMediaFinal(nota.getMatricula().getId());

        logger.info("Nota excluída com sucesso");
    }
}
