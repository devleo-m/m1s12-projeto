package com.study.m1s11projeto.service.impl;

import com.study.m1s11projeto.DTO.AlunoDto;
import com.study.m1s11projeto.DTO.DisciplinaDto;
import com.study.m1s11projeto.entity.AlunoEntity;
import com.study.m1s11projeto.entity.DisciplinaEntity;
import com.study.m1s11projeto.entity.DisciplinaMatriculaEntity;
import com.study.m1s11projeto.exception.NotFoundException;
import com.study.m1s11projeto.repository.DisciplinaMatriculaRepository;
import com.study.m1s11projeto.service.AlunoService;
import com.study.m1s11projeto.service.DisciplinaMatriculaService;
import com.study.m1s11projeto.service.DisciplinaService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DisciplinaMatriculaServiceImpl implements DisciplinaMatriculaService {
    private final DisciplinaMatriculaRepository matriculaRepository;
    private final AlunoService alunoService;
    private final DisciplinaService disciplinaService;

    public DisciplinaMatriculaServiceImpl(DisciplinaMatriculaRepository matriculaRepository, AlunoService alunoService, DisciplinaService disciplinaService) {
        this.matriculaRepository = matriculaRepository;
        this.alunoService = alunoService;
        this.disciplinaService = disciplinaService;
    }

    @Override
    public DisciplinaMatriculaEntity matricularAluno(Long idAluno, Long idDisciplina) {
        AlunoEntity aluno = alunoService.obterAlunoPorId(idAluno);
        DisciplinaEntity disciplina = disciplinaService.obterDisciplinaPorId(idDisciplina);

        // Verificar se notas já foram lançadas
        if (notasJaLancadas(aluno, disciplina)) {
            throw new NotFoundException("Notas já foram lançadas para o aluno na disciplina.");
            //Criar futuramente um NotaLancadaException
            //para deixar o tratamento de erro mais bonito ;p
        }

        // Matricular aluno na disciplina
        DisciplinaMatriculaEntity matricula = new DisciplinaMatriculaEntity();
        matricula.setAluno(aluno);
        matricula.setDisciplina(disciplina);

        return matriculaRepository.save(matricula);
    }



    // Método privado para verificar se notas já foram lançadas para um aluno em uma disciplina
    private boolean notasJaLancadas(AlunoEntity aluno, DisciplinaEntity disciplina) {
<<<<<<< Updated upstream
        return false; // Aqui é um exemplo simples - melhorar depois sacarai
=======
        // Se sim, retorne true
        // Se não, retorne false
        return false; // Aqui é um exemplo simples; você deve implementar a lógica real
>>>>>>> Stashed changes
    }

    /*

    private boolean notasJaLancadas(AlunoEntity aluno, DisciplinaEntity disciplina) {
        List<NotaEntity> notas = notaRepository.findByAlunoAndDisciplina(aluno, disciplina);
        return !notas.isEmpty();
    }
     */

    @Override
    public void deletarMatricula(Long id) {
        // Verificar se notas já foram lançadas para a matrícula com o ID especificado
        DisciplinaMatriculaEntity matricula = matriculaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Matrícula não encontrada com o ID: " + id));

        // Verificar se a média final da matrícula é diferente de zero (indicando notas já lançadas)
        if (matricula.getMediaFinal().compareTo(BigDecimal.ZERO) != 0) {
            throw new NotFoundException("Notas já lançadas para a matrícula com o ID: " + id);
        }

        // Se não houver notas lançadas, deletar a matrícula
        matriculaRepository.deleteById(id);
    }

    @Override
    public DisciplinaMatriculaEntity buscarMatriculaPorId(Long id) {
        return matriculaRepository.findById(id).orElse(null);
    }

    @Override
    public List<DisciplinaMatriculaEntity> buscarMatriculasPorAluno(Long idAluno) {
        AlunoEntity aluno = alunoService.obterAlunoPorId(idAluno);
        return matriculaRepository.findByAluno(aluno);
    }

    @Override
    public List<DisciplinaMatriculaEntity> buscarMatriculasPorDisciplina(Long idDisciplina) {
        DisciplinaEntity disciplina = disciplinaService.obterDisciplinaPorId(idDisciplina);
        return matriculaRepository.findByDisciplina(disciplina);
    }
}
