package com.study.m1s11projeto.service.impl;

import com.study.m1s11projeto.entity.ProfessorEntity;
import com.study.m1s11projeto.exception.NotFoundException;
import com.study.m1s11projeto.repository.ProfessorRepository;
import com.study.m1s11projeto.service.ProfessorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorServiceImpl implements ProfessorService {

    // Logger para registrar informações e avisos
    private static final Logger logger = LoggerFactory.getLogger(ProfessorServiceImpl.class);

    // Repositório para interagir com o banco de dados
    private final ProfessorRepository professorRepository;

    /**
     * Construtor da classe ProfessorServiceImpl.
     * @param professorRepository Repositório de professores.
     */
    public ProfessorServiceImpl(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    /**
     * Lista todos os professores.
     * @return Lista de professores.
     */
    @Override
    public List<ProfessorEntity> listarProfessores() {
        logger.info("Listando todos os professores");
        return professorRepository.findAll();
    }

    /**
     * Obtém um professor por ID.
     * @param id ID do professor a ser obtido.
     * @return O professor encontrado.
     * @throws NotFoundException Se o professor não for encontrado.
     */
    @Override
    public ProfessorEntity obterProfessorPorId(Long id) {
        logger.info("Obtendo professor por ID: {}", id);
        return professorRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Professor não encontrado com o ID: {}", id);
                    return new NotFoundException("Professor não encontrado com o ID: " + id);
                });
    }

    /**
     * Adiciona um novo professor.
     * @param professor O professor a ser adicionado.
     * @return O novo professor adicionado.
     */
    @Override
    public ProfessorEntity adicionarProfessor(ProfessorEntity professor) {
        logger.info("Adicionando novo professor: {}", professor);
        return professorRepository.save(professor);
    }

    /**
     * Atualizar um professor existente.
     * @param id ID do professor a ser atualizado.
     * @param professorAtualizado O professor com as informações atualizadas.
     * @return O professor atualizado.
     * @throws NotFoundException Se o professor não for encontrado.
     */
    @Override
    public ProfessorEntity atualizarProfessor(Long id, ProfessorEntity professorAtualizado) {
        logger.info("Atualizando professor com o ID: {}", id);
        verifyProfessorExists(id);
        professorAtualizado.setId(id);
        return professorRepository.save(professorAtualizado);
    }

    /**
     * Deleta um professor por ID.
     * @param id ID do professor a ser deletado.
     * @throws NotFoundException Se o professor não for encontrado.
     */
    @Override
    public void deletarProfessor(Long id) {
        logger.info("Deletando professor com o ID: {}", id);
        verifyProfessorExists(id);
        professorRepository.deleteById(id);
    }

    // Método auxiliar para verificar se um professor existe
    private void verifyProfessorExists(Long id) {
        if (!professorRepository.existsById(id)) {
            logger.warn("Professor não encontrado com o ID: {}", id);
            throw new NotFoundException("Professor não encontrado com o ID: " + id);
        }
    }
}
