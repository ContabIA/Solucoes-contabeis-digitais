CREATE TABLE notas (
    id BIGINT PRIMARY KEY,
    data DATE NOT NULL,
    data_insercao DATE NOT NULL,
    serie INT NOT NULL,
    nome_emitente VARCHAR(255) NOT NULL,
    situacao VARCHAR(255) NOT NULL,
    valor VARCHAR(255) NOT NULL,
    novo BIT(1) NOT NULL,
    id_empresa BIGINT NOT NULL,
    FOREIGN KEY (id_empresa) REFERENCES empresa(id) ON DELETE CASCADE
);
