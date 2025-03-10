INSERT INTO cozinha (id, nome) VALUES (1, 'Tailandesa');
INSERT INTO cozinha (id, nome) VALUES (2, 'Indiana');
INSERT INTO cozinha (id, nome) VALUES (3, 'Italiana');
INSERT INTO cozinha (id, nome) VALUES (4, 'Mexicana');
INSERT INTO cozinha (id, nome) VALUES (5, 'Chinesa');

INSERT INTO restaurante (nome, taxa_frete, cozinha_id) VALUES ('Lisboa', 6.31, 1);
INSERT INTO restaurante (nome, taxa_frete, cozinha_id) VALUES ('Penina', 4.22, 2);
INSERT INTO restaurante (nome, taxa_frete) VALUES ('Cheiro Verde', 4.22);
INSERT INTO restaurante (nome, taxa_frete) VALUES ('Zetel', 4.22);
INSERT INTO restaurante (nome, taxa_frete) VALUES ('Miranda', 0.0);

INSERT INTO forma_pagamento (descricao) VALUES ("dinheiro");
INSERT INTO forma_pagamento (descricao) VALUES ("pix");
INSERT INTO forma_pagamento (descricao) VALUES ("credito");

INSERT INTO estado (id, nome) VALUES (1, "Santa Catarina");
INSERT INTO estado (id, nome) VALUES (2, "São Paulo");
INSERT INTO estado (id, nome) VALUES (3, "Rio de Janeiro");
INSERT INTO estado (id, nome) VALUES (4, "Pernambuco");
INSERT INTO estado (id, nome) VALUES (5, "Paraná");

INSERT INTO cidade (nome, estado_id) VALUES ("Florianópolis", 1);
INSERT INTO cidade (nome, estado_id) VALUES ("São José dos Campos", 2);
INSERT INTO cidade (nome, estado_id) VALUES ("Palhoça", 1);

INSERT INTO permissao (id, nome, descricao) VALUES (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
INSERT INTO permissao (id, nome, descricao) VALUES (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

INSERT INTO restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) VALUES (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3);