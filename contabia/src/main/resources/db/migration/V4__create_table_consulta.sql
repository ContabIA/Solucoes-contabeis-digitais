CREATE TABLE consulta (
    id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    tipo_consulta INT NOT NULL,
    frequencia INT NOT NULL,
    id_empresa BIGINT NOT NULL,
    FOREIGN KEY (id_empresa) REFERENCES empresa(id) ON DELETE CASCADE
);
