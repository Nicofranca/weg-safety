-- Usuarios (admins do sistema)
CREATE TABLE usuarios (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha_hash VARCHAR(255) NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'ATIVO',
    tentativas_login INTEGER DEFAULT 0,
    bloqueado_ate TIMESTAMP,
    ultimo_acesso TIMESTAMP,
    data_criacao TIMESTAMP NOT NULL DEFAULT NOW()
);

-- Cargos
CREATE TABLE cargos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(255) NOT NULL UNIQUE,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    data_criacao TIMESTAMP NOT NULL DEFAULT NOW()
);

-- Setores
CREATE TABLE setores (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(255) NOT NULL UNIQUE,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    data_criacao TIMESTAMP NOT NULL DEFAULT NOW()
);

-- Funcionarios
CREATE TABLE funcionarios (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    rg VARCHAR(20),
    matricula VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(255) UNIQUE,
    data_nascimento DATE,
    genero VARCHAR(20),
    cargo_id UUID NOT NULL REFERENCES cargos(id),
    setor_id UUID NOT NULL REFERENCES setores(id),
    data_admissao DATE,
    turno VARCHAR(50),
    status VARCHAR(30) NOT NULL DEFAULT 'ATIVO',
    data_criacao TIMESTAMP NOT NULL DEFAULT NOW(),
    data_atualizacao TIMESTAMP
);

-- Crachas
CREATE TABLE crachas (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    funcionario_id UUID NOT NULL REFERENCES funcionarios(id),
    uid_rfid VARCHAR(100) NOT NULL UNIQUE,
    validade DATE NOT NULL,
    perfil_acesso VARCHAR(50),
    status VARCHAR(30) NOT NULL DEFAULT 'ATIVO',
    data_emissao TIMESTAMP NOT NULL DEFAULT NOW(),
    emitido_por UUID
);

-- EPI Tipos
CREATE TABLE epi_tipos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(255) NOT NULL UNIQUE,
    descricao TEXT,
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);

-- EPI Funcionario
CREATE TABLE epi_funcionarios (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    funcionario_id UUID NOT NULL REFERENCES funcionarios(id),
    epi_tipo_id UUID NOT NULL REFERENCES epi_tipos(id),
    nr_ca VARCHAR(50) NOT NULL,
    data_entrega DATE,
    data_validade DATE NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'VALIDO',
    data_criacao TIMESTAMP NOT NULL DEFAULT NOW(),
    registrado_por UUID
);

-- Areas
CREATE TABLE areas (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(255) NOT NULL,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    tipo VARCHAR(50),
    nivel_risco VARCHAR(30),
    capacidade_maxima INTEGER,
    anti_passback BOOLEAN DEFAULT FALSE,
    verificar_epi BOOLEAN DEFAULT FALSE,
    fail_open BOOLEAN DEFAULT FALSE,
    status VARCHAR(30) NOT NULL DEFAULT 'ATIVA',
    data_criacao TIMESTAMP NOT NULL DEFAULT NOW()
);

-- EPI obrigatório por área (ManyToMany)
CREATE TABLE epi_area (
    area_id UUID NOT NULL REFERENCES areas(id),
    epi_tipo_id UUID NOT NULL REFERENCES epi_tipos(id),
    PRIMARY KEY (area_id, epi_tipo_id)
);

-- Permissões de acesso
CREATE TABLE permissoes_acesso (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    area_id UUID NOT NULL REFERENCES areas(id),
    funcionario_id UUID NOT NULL REFERENCES funcionarios(id),
    tipo VARCHAR(20) NOT NULL,
    motivo TEXT,
    data_criacao TIMESTAMP NOT NULL DEFAULT NOW(),
    criado_por UUID
);

-- Restrições de horário
CREATE TABLE restricoes_horario (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    area_id UUID NOT NULL REFERENCES areas(id),
    hora_inicio TIME NOT NULL,
    hora_fim TIME NOT NULL,
    perfil VARCHAR(100) NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT NOW()
);

-- Dias da semana da restrição
CREATE TABLE restricao_dias (
    restricao_id UUID NOT NULL REFERENCES restricoes_horario(id),
    dia VARCHAR(20) NOT NULL
);

-- Logs de acesso
CREATE TABLE logs_acesso (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    funcionario_id UUID REFERENCES funcionarios(id),
    area_id UUID REFERENCES areas(id),
    resultado VARCHAR(20) NOT NULL,
    motivo VARCHAR(50),
    ts_evento TIMESTAMP NOT NULL DEFAULT NOW()
);

-- Índices para performance
CREATE INDEX idx_funcionarios_status ON funcionarios(status);
CREATE INDEX idx_crachas_funcionario ON crachas(funcionario_id);
CREATE INDEX idx_crachas_status ON crachas(status);
CREATE INDEX idx_epi_funcionario ON epi_funcionarios(funcionario_id);
CREATE INDEX idx_epi_validade ON epi_funcionarios(data_validade);
CREATE INDEX idx_permissoes_area ON permissoes_acesso(area_id);
CREATE INDEX idx_permissoes_funcionario ON permissoes_acesso(funcionario_id);
CREATE INDEX idx_logs_ts_evento ON logs_acesso(ts_evento);
CREATE INDEX idx_logs_area ON logs_acesso(area_id);
CREATE INDEX idx_logs_resultado ON logs_acesso(resultado);
