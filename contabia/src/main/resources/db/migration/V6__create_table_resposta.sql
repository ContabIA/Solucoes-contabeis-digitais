CREATE TABLE resposta (
    id BIGINT  AUTO_INCREMENT NOT NULL PRIMARY KEY,
    status TINYINT NOT NULL,
    data DATE NOT NULL,
    novo BIT(1) NOT NULL,
    id_consulta BIGINT NOT NULL,
    CONSTRAINT fk_consulta_resposta FOREIGN KEY (id_consulta) REFERENCES consulta(id) ON DELETE CASCADE ON UPDATE CASCADE
);