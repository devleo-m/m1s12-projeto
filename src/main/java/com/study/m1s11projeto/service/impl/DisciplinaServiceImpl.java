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

@Service
public class DisciplinaServiceImpl implements DisciplinaService {
    private static final Logger logger = LoggerFactory.getLogger(DisciplinaServiceImpl.class);
    private final DisciplinaRepository disciplinaRepository;

    public DisciplinaServiceImpl(DisciplinaRepository disciplinaRepository) {
        this.disciplinaRepository = disciplinaRepository;
    }

    @Override
    public List<DisciplinaEntity> listarDisciplinas() {
        logger.info("Listando todas as disciplina");
        return disciplinaRepository.findAll();
    }

    @Override
    public DisciplinaEntity obterDisciplinaPorId(Long id) {
        logger.info("Obtendo disciplina por ID: {}", id);
        Optional<DisciplinaEntity> DisciplinaOptional = disciplinaRepository.findById(id);
        if (DisciplinaOptional.isPresent()) {
            return DisciplinaOptional.get();
        } else {
            logger.warn("disciplina não encontrada com o ID: {}", id);
            throw new NotFoundException("disciplina não encontrada com o ID: " + id);
        }
    }

    @Override
    public DisciplinaEntity adicionarDisciplina(DisciplinaEntity disciplina) {
        logger.info("Adicionando nova disciplina: {}", disciplina);
        return disciplinaRepository.save(disciplina);
    }

    @Override
    public DisciplinaEntity atualizarDisciplina(Long id, DisciplinaEntity atualizadoDisciplina) {
        logger.info("Atualizando disciplina com o ID: {}", id);
        if (!disciplinaRepository.existsById(id)) {
            logger.warn("disciplina não encontrada com o ID: {}", id);
            throw new NotFoundException("disciplina não encontrada com o ID: " + id);
        }
        atualizadoDisciplina.setId(id);
        return disciplinaRepository.save(atualizadoDisciplina);
    }

    @Override
    public void deletarDisciplina(Long id) {
        logger.info("Deletando aluno com o ID: {}", id);
        if (!disciplinaRepository.existsById(id)) {
            logger.warn("Aluno não encontrado com o ID: {}", id);
            throw new NotFoundException("Aluno não encontrado com o ID: " + id);
        }
        disciplinaRepository.deleteById(id);
    }
}
