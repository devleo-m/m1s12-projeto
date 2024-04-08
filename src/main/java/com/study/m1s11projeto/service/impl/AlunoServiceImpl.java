package com.study.m1s11projeto.service.impl;

import com.study.m1s11projeto.entity.AlunoEntity;
import com.study.m1s11projeto.exception.NotFoundException;
import com.study.m1s11projeto.repository.AlunoRepository;
import com.study.m1s11projeto.service.AlunoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementação do serviço de alunos.
 */

@Service
public class AlunoServiceImpl implements AlunoService {

    private final AlunoRepository alunoRepository;
    private static final Logger logger = LoggerFactory.getLogger(AlunoServiceImpl.class);

    /**
     * Construtor da classe AlunoServiceImpl.
     * @param alunoRepository Repositório de alunos.
     */
    public AlunoServiceImpl(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    /**
     * Lista todos os alunos.
     * @return Lista de alunos.
     */
    @Override
    public List<AlunoEntity> listarAlunos() {
        logger.info("Listando todos os alunos");
        return alunoRepository.findAll();
    }

    /**
     * Obtém um aluno por seu ID.
     * @param id ID do aluno a ser obtido.
     * @return O aluno encontrado.
     * @throws NotFoundException Se o aluno não for encontrado.
     */
    @Override
    public AlunoEntity obterAlunoPorId(Long id) {
        logger.info("Obtendo aluno por ID: {}", id);
        Optional<AlunoEntity> alunoOptional = alunoRepository.findById(id);
        return alunoOptional.orElseThrow(() -> {
            logger.warn("Aluno não encontrado com o ID: {}", id);
            return new NotFoundException("Aluno não encontrado com o ID: " + id);
        });
    }

    /**
     * Adiciona um novo aluno.
     * @param aluno O aluno a ser adicionado.
     * @return O aluno adicionado.
     */
    @Override
    public AlunoEntity adicionarAluno(AlunoEntity aluno) {
        logger.info("Adicionando novo aluno: {}", aluno);
        return alunoRepository.save(aluno);
    }

    /**
     * Atualiza um aluno existente.
     * @param id ID do aluno a ser atualizado.
     * @param alunoAtualizado Novos dados do aluno.
     * @return O aluno atualizado.
     * @throws NotFoundException Se o aluno não for encontrado.
     */
    @Override
    public AlunoEntity atualizarAluno(Long id, AlunoEntity alunoAtualizado) {
        logger.info("Atualizando aluno com o ID: {}", id);
        verificarExistenciaAluno(id);
        alunoAtualizado.setId(id);
        return alunoRepository.save(alunoAtualizado);
    }

    /**
     * Deleta um aluno existente.
     * @param id ID do aluno a ser deletado.
     * @throws NotFoundException Se o aluno não for encontrado.
     */
    @Override
    public void deletarAluno(Long id) {
        logger.info("Deletando aluno com o ID: {}", id);
        verificarExistenciaAluno(id);
        alunoRepository.deleteById(id);
    }

    /**
     * Verifica se um aluno existe.
     * @param id ID do aluno a ser verificado.
     * @throws NotFoundException Se o aluno não for encontrado.
     */
    private void verificarExistenciaAluno(Long id) {
        if (!alunoRepository.existsById(id)) {
            logger.warn("Aluno não encontrado com o ID: {}", id);
            throw new NotFoundException("Aluno não encontrado com o ID: " + id);
        }
    }
}
