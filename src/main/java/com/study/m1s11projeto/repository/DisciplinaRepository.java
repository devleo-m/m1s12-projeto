package com.study.m1s11projeto.repository;

import com.study.m1s11projeto.entity.DisciplinaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface DisciplinaRepository extends JpaRepository<DisciplinaEntity, Long> {
}
