package com.study.m1s11projeto.service.impl;

import com.study.m1s11projeto.entity.ProfessorEntity;
import com.study.m1s11projeto.exception.NotFoundException;
import com.study.m1s11projeto.repository.ProfessorRepository;
import com.study.m1s11projeto.service.ProfessorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorServiceImpl implements ProfessorService {

    private static final Logger logger = LoggerFactory.getLogger(ProfessorServiceImpl.class);
    private final ProfessorRepository professorRepository;

    public ProfessorServiceImpl(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    @Override
    public List<ProfessorEntity> listarProfessores() {
        logger.info("Listando todos os Professores");
        return professorRepository.findAll();
    }

    @Override
    public ProfessorEntity obterProfessorPorId(Long id) {
        logger.info("Obtendo Professor por ID: {}", id);
        Optional<ProfessorEntity> ProfessorOptional = professorRepository.findById(id);
        if (ProfessorOptional.isPresent()) {
            return ProfessorOptional.get();
        } else {
            logger.warn("Professor não encontrado com o ID: {}", id);
            throw new NotFoundException("Professor não encontrado com o ID: " + id);
        }
    }

    @Override
    public ProfessorEntity adicionarProfessor(ProfessorEntity professor) {
        logger.info("Adicionando novo Professor: {}", professor);
        return professorRepository.save(professor);
    }

    @Override
    public ProfessorEntity atualizarProfessor(Long id, ProfessorEntity professorAtualizado) {
        logger.info("Atualizando Professor com o ID: {}", id);
        if (!professorRepository.existsById(id)) {
            logger.warn("Professor não encontrado com o ID: {}", id);
            throw new NotFoundException("Professor não encontrado com o ID: " + id);
        }
        professorAtualizado.setId(id);
        return professorRepository.save(professorAtualizado);
    }

    @Override
    public void deletarProfessor(Long id) {
        logger.info("Deletando Professor com o ID: {}", id);
        if (!professorRepository.existsById(id)) {
            logger.warn("Professor não encontrado com o ID: {}", id);
            throw new NotFoundException("Professor não encontrado com o ID: " + id);
        }
        professorRepository.deleteById(id);
    }
}

