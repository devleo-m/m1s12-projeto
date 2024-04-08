package com.study.m1s11projeto.repository;

import com.study.m1s11projeto.entity.NotaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotaRepository extends JpaRepository<NotaEntity, Long> {
    List<NotaEntity> findByMatriculaId(Long idMatricula);
}
