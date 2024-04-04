-- Tabela Aluno
CREATE TABLE IF NOT EXISTS aluno (
                                     id BIGSERIAL PRIMARY KEY,
                                     nome VARCHAR(150) NOT NULL,
    nascimento DATE
    );

-- Tabela Professor
CREATE TABLE IF NOT EXISTS professor (
                                         id BIGSERIAL PRIMARY KEY,
                                         nome VARCHAR(150) NOT NULL
    );


-- Tabela Disciplina
CREATE TABLE IF NOT EXISTS disciplina (
                                          id BIGSERIAL PRIMARY KEY,
                                          nome VARCHAR(150) NOT NULL,
    professor_id BIGINT REFERENCES professor(id) NOT NULL
    );


-- Tabela Disciplina_Matricula
CREATE TABLE IF NOT EXISTS disciplina_matricula (
                                                    id BIGSERIAL PRIMARY KEY,
                                                    aluno_id BIGINT REFERENCES aluno(id),
    disciplina_id BIGINT REFERENCES disciplina(id),
    data_matricula DATE NOT NULL DEFAULT NOW(),
    media_final NUMERIC(5,2) NOT NULL DEFAULT 0.00
    --CONSTRAINT unique_matricula UNIQUE (aluno_id, disciplina_id)
    );

-- Tabela Notas
CREATE TABLE IF NOT EXISTS notas (
                                     id BIGSERIAL PRIMARY KEY,
                                     disciplina_matricula_id BIGINT REFERENCES disciplina_matricula(id) NOT NULL,
    professor_id BIGINT REFERENCES professor(id) NOT NULL,
    nota NUMERIC(5,2) NOT NULL DEFAULT 0.00,
    coeficiente NUMERIC(19,6) NOT NULL DEFAULT 0.00
    );

