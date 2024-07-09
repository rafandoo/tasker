-- Criação das tabelas
CREATE TABLE IF NOT EXISTS usuarios (
    id               INTEGER PRIMARY KEY AUTOINCREMENT,
    nome             TEXT NOT NULL,
    email            TEXT NOT NULL UNIQUE,
    senha            TEXT NOT NULL,
    data_criacao     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    senha_expirada   BOOLEAN   DEFAULT 0
);

CREATE TABLE IF NOT EXISTS produtos (
    id           INTEGER PRIMARY KEY AUTOINCREMENT,
    nome         TEXT NOT NULL,
    descricao    TEXT,
    preco        REAL NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS pedidos (
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    usuario_id  INTEGER,
    data_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total       REAL NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios (id)
);

CREATE TABLE IF NOT EXISTS itens_pedido (
    id             INTEGER PRIMARY KEY AUTOINCREMENT,
    pedido_id      INTEGER,
    produto_id     INTEGER,
    quantidade     INTEGER NOT NULL,
    preco_unitario REAL    NOT NULL,
    FOREIGN KEY (pedido_id) REFERENCES pedidos (id),
    FOREIGN KEY (produto_id) REFERENCES produtos (id)
);

-- Inserção de dados falsos
INSERT INTO usuarios (nome, email, senha)
VALUES ('Alice', 'alice@example.com', 'alice123');
INSERT INTO usuarios (nome, email, senha)
VALUES ('Bob', 'bob@example.com', 'bob123');

INSERT INTO produtos (nome, descricao, preco)
VALUES ('Produto A', 'Descrição do Produto A', 19.99);
INSERT INTO produtos (nome, descricao, preco)
VALUES ('Produto B', 'Descrição do Produto B', 29.99);

INSERT INTO pedidos (usuario_id, total)
VALUES (1, 49.98);
INSERT INTO pedidos (usuario_id, total)
VALUES (2, 29.99);

INSERT INTO itens_pedido (pedido_id, produto_id, quantidade, preco_unitario)
VALUES (1, 1, 1, 19.99);
INSERT INTO itens_pedido (pedido_id, produto_id, quantidade, preco_unitario)
VALUES (1, 2, 1, 29.99);
INSERT INTO itens_pedido (pedido_id, produto_id, quantidade, preco_unitario)
VALUES (2, 2, 1, 29.99);
