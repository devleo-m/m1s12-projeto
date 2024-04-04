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

@Service
public class AlunoServiceImpl implements AlunoService {

    private final AlunoRepository alunoRepository;
    private static final Logger logger = LoggerFactory.getLogger(AlunoServiceImpl.class);

    public AlunoServiceImpl(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    @Override
    public List<AlunoEntity> listarAlunos() {
        logger.info("Listando todos os alunos");
        return alunoRepository.findAll();
    }

    @Override
    public AlunoEntity obterAlunoPorId(Long id) {
        logger.info("Obtendo aluno por ID: {}", id);
        Optional<AlunoEntity> alunoOptional = alunoRepository.findById(id);
        if (alunoOptional.isPresent()) {
            return alunoOptional.get();
        } else {
            logger.warn("Aluno não encontrado com o ID: {}", id);
            throw new NotFoundException("Aluno não encontrado com o ID: " + id);
        }
    }

    @Override
    public AlunoEntity adicionarAluno(AlunoEntity aluno) {
        logger.info("Adicionando novo aluno: {}", aluno);
        return alunoRepository.save(aluno);
    }

    @Override
    public AlunoEntity atualizarAluno(Long id, AlunoEntity alunoAtualizado) {
        logger.info("Atualizando aluno com o ID: {}", id);
        if (!alunoRepository.existsById(id)) {
            logger.warn("Aluno não encontrado com o ID: {}", id);
            throw new NotFoundException("Aluno não encontrado com o ID: " + id);
        }
        alunoAtualizado.setId(id);
        return alunoRepository.save(alunoAtualizado);
    }

    @Override
    public void deletarAluno(Long id) {
        logger.info("Deletando aluno com o ID: {}", id);
        if (!alunoRepository.existsById(id)) {
            logger.warn("Aluno não encontrado com o ID: {}", id);
            throw new NotFoundException("Aluno não encontrado com o ID: " + id);
        }
        alunoRepository.deleteById(id);
    }
}
