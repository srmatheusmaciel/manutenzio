INSERT INTO usuario (nome, email, role, avatar, password) VALUES
('Chefe Wile', 'admin@acme.com', 'ADMIN', 'https://ui-avatars.com/api/?name=Admin', '$2a$10$j8.Z.y.j.u.K.u.L.m.N.o.P.q.R.s.T.u.V.w.x.y.z.1.2.3.4.5.6'),
('Mecânico Road Runner', 'funcionario@acme.com', 'FUNCIONARIO', 'https://ui-avatars.com/api/?name=Func', '$2a$10$j8.Z.y.j.u.K.u.L.m.N.o.P.q.R.s.T.u.V.w.x.y.z.1.2.3.4.5.6');
-- 123456 para ambos

INSERT INTO carro (fabricante, modelo, ano, cor, placa, status) VALUES
('Volkswagen', 'Golf', 2020, 'Preto', 'GOLF-2020', 'DISPONIVEL'),
('Ford', 'Fiesta', 2019, 'Prata', 'FIESTA-19', 'EM_MANUTENCAO'),
('Fiat', 'Uno', 2010, 'Branco', 'UNO-2010', 'INATIVO'),
('Ford', 'Ka', 2020, 'Branco', 'KA-2020', 'DISPONIVEL');