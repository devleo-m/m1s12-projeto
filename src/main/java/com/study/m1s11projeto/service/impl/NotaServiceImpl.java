package com.study.m1s11projeto.service.impl;

import com.study.m1s11projeto.entity.DisciplinaMatriculaEntity;
import com.study.m1s11projeto.entity.NotaEntity;
import com.study.m1s11projeto.exception.NotFoundException;
import com.study.m1s11projeto.repository.DisciplinaMatriculaRepository;
import com.study.m1s11projeto.repository.NotaRepository;
import com.study.m1s11projeto.service.DisciplinaMatriculaService;
import com.study.m1s11projeto.service.NotaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotaServiceImpl implements NotaService {

    private final NotaRepository notaRepository;
    private final DisciplinaMatriculaService disciplinaMatriculaService;
    private final DisciplinaMatriculaRepository disciplinaMatriculaRepository;

    public NotaServiceImpl(NotaRepository notaRepository, DisciplinaMatriculaService disciplinaMatriculaService, DisciplinaMatriculaRepository disciplinaMatriculaRepository) {
        this.notaRepository = notaRepository;
        this.disciplinaMatriculaService = disciplinaMatriculaService;
        this.disciplinaMatriculaRepository = disciplinaMatriculaRepository;
    }
    @Override
    public NotaEntity adicionarNota(Long idMatricula, Double nota, Double coeficiente) {
        // Verificar se os parâmetros são válidos
        if (idMatricula == null || nota == null || coeficiente == null) {
            throw new NotFoundException("Os campos 'idMatricula', 'nota' e 'coeficiente' são obrigatórios para adicionar uma nota");
        }

        // Verificar se a nota está dentro do intervalo válido (0.0 - 10.0)
        if (nota < 0.0 || nota > 10.0) {
            throw new RuntimeException("A nota do aluno deve estar no intervalo de 0.0 a 10.0");
        }

        // Criar uma instância de NotaEntity com os parâmetros recebidos
        NotaEntity novaNota = new NotaEntity();
        novaNota.setIdMatricula(idMatricula);
        novaNota.setNota(nota);
        novaNota.setCoeficiente(coeficiente);

        // Salvar a nova nota no banco de dados
        novaNota = notaRepository.save(novaNota);

        // Atualizar a média final da matrícula
        atualizarMediaFinal(idMatricula);

        // Retornar a nova nota
        return novaNota;
    }

    private void atualizarMediaFinal(Long idMatricula) {
        // Buscar a matrícula no banco de dados
        DisciplinaMatriculaEntity matricula = disciplinaMatriculaRepository.findById(idMatricula)
                .orElseThrow(() -> new NotFoundException("Matrícula não encontrada com o ID fornecido"));

        // Calcular a média final da matrícula com base nas notas e coeficientes das disciplinas
        Double mediaFinal = calcularMediaFinal(matricula);

        // Atualizar a média final da matrícula
        matricula.setMediaFinal(mediaFinal);
        disciplinaMatriculaRepository.save(matricula);
    }

    private Double calcularMediaFinal(DisciplinaMatriculaEntity matricula) {
        // Verificar se a matrícula possui disciplinas associadas
        List<NotaEntity> notas = matricula.getNotas();
        if (notas.isEmpty()) {
            return 0.0; // Se não houver notas associadas, a média final será 0.0
        }

        // Inicializar variáveis para calcular a média final
        double somaNotasPonderadas = 0.0;
        double somaCoeficientes = 0.0;

        // Calcular a soma das notas ponderadas e dos coeficientes
        for (NotaEntity nota : notas) {
            somaNotasPonderadas += nota.getNota() * nota.getCoeficiente();
            somaCoeficientes += nota.getCoeficiente();
        }

        // Calcular a média final
        double mediaFinal = somaNotasPonderadas / somaCoeficientes;

        // Retornar a média final calculada
        return mediaFinal;
    }


}
