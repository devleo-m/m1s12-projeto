package com.study.m1s11projeto.service.impl;

import com.study.m1s11projeto.entity.DisciplinaEntity;
import com.study.m1s11projeto.exception.NotFoundException;
import com.study.m1s11projeto.repository.DisciplinaRepository;
import com.study.m1s11projeto.service.DisciplinaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementação do serviço de disciplinas.
 */

@Service
public class DisciplinaServiceImpl implements DisciplinaService {

    private static final Logger logger = LoggerFactory.getLogger(DisciplinaServiceImpl.class);
    private final DisciplinaRepository disciplinaRepository;

    /**
     * Construtor da classe DisciplinaServiceImpl.
     * @param disciplinaRepository Repositório de disciplinas.
     */
    public DisciplinaServiceImpl(DisciplinaRepository disciplinaRepository) {
        this.disciplinaRepository = disciplinaRepository;
    }

    /**
     * Lista todas as disciplinas.
     * @return Lista de disciplinas.
     */
    @Override
    public List<DisciplinaEntity> listarDisciplinas() {
        logger.info("Listando todas as disciplinas");
        return disciplinaRepository.findAll();
    }

    /**
     * Obtém uma disciplina pelo ID.
     * @param id O ID da disciplina a ser obtida.
     * @return A disciplina encontrada.
     * @throws NotFoundException Se a disciplina não for encontrada.
     */
    @Override
    public DisciplinaEntity obterDisciplinaPorId(Long id) {
        logger.info("Obtendo disciplina por ID: {}", id);
        Optional<DisciplinaEntity> disciplinaOptional = disciplinaRepository.findById(id);
        return disciplinaOptional.orElseThrow(() -> {
            logger.warn("Disciplina não encontrada com o ID: {}", id);
            return new NotFoundException("Disciplina não encontrada com o ID: " + id);
        });
    }

    /**
     * Adiciona uma nova disciplina.
     * @param disciplina A disciplina a ser adicionada.
     * @return A disciplina adicionada.
     */
    @Override
    public DisciplinaEntity adicionarDisciplina(DisciplinaEntity disciplina) {
        logger.info("Adicionando nova disciplina: {}", disciplina);
        return disciplinaRepository.save(disciplina);
    }

    /**
     * Atualiza uma disciplina existente.
     * @param id O ID da disciplina a ser atualizada.
     * @param disciplinaAtualizada A disciplina com as informações atualizadas.
     * @return A disciplina atualizada.
     * @throws NotFoundException Se a disciplina não for encontrada.
     */
    @Override
    public DisciplinaEntity atualizarDisciplina(Long id, DisciplinaEntity disciplinaAtualizada) {
        logger.info("Atualizando disciplina com o ID: {}", id);
        verificarExistenciaDisciplina(id);
        disciplinaAtualizada.setId(id);
        return disciplinaRepository.save(disciplinaAtualizada);
    }

    /**
     * Deleta uma disciplina pelo ID.
     * @param id O ID da disciplina a ser deletada.
     * @throws NotFoundException Se a disciplina não for encontrada.
     */
    @Override
    public void deletarDisciplina(Long id) {
        logger.info("Deletando disciplina com o ID: {}", id);
        verificarExistenciaDisciplina(id);
        disciplinaRepository.deleteById(id);
    }

    /**
     * Verifica se uma disciplina existe pelo ID.
     * @param id O ID da disciplina a ser verificada.
     * @throws NotFoundException Se a disciplina não for encontrada.
     */
    private void verificarExistenciaDisciplina(Long id) {
        if (!disciplinaRepository.existsById(id)) {
            logger.warn("Disciplina não encontrada com o ID: {}", id);
            throw new NotFoundException("Disciplina não encontrada com o ID: " + id);
        }
    }
}
