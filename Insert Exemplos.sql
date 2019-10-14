/*  Inserts iniciais, é necessario ter as tabelas limpas  */
	
--insere categorias 
INSERT INTO public.categorias(nome) VALUES ('FPS');
INSERT INTO public.categorias(nome) VALUES ('Card Game');
INSERT INTO public.categorias(nome) VALUES ('MOBA');

--insere jogos
INSERT INTO public.jogos(nome, id_categoria) VALUES ('Counter-Strike: Global Offensive', 1);
INSERT INTO public.jogos(nome, id_categoria) VALUES ('Hearthstone: Heroes of Warcraft', 2);
INSERT INTO public.jogos(nome, id_categoria) VALUES ('League of Legends', 3);

--insere times
INSERT INTO public.times (nome) VALUES ('Mibr');
INSERT INTO public.times (nome) VALUES ('Disguised Toast');
INSERT INTO public.times (nome) VALUES ('ChocoTaco');
INSERT INTO public.times (nome) VALUES ('Shroud');
INSERT INTO public.times (nome) VALUES ('Astralis');
INSERT INTO public.times (nome) VALUES ('SK Gaming');
INSERT INTO public.times (nome) VALUES ('Team Liquid');
INSERT INTO public.times (nome) VALUES ('FaZe Clan');
INSERT INTO public.times (nome) VALUES ('fnatic');
INSERT INTO public.times (nome) VALUES ('Luminosity Gaming');
INSERT INTO public.times (nome) VALUES ('G2 Esports');
INSERT INTO public.times (nome) VALUES ('Natus Vincere');
INSERT INTO public.times (nome) VALUES ('Cloud9');
INSERT INTO public.times (nome) VALUES ('Virtus.pro');
INSERT INTO public.times (nome) VALUES ('NRG eSports');
INSERT INTO public.times (nome) VALUES ('Vitality');

--insere torneio
INSERT INTO public.torneios(nome, inicio, fim, id_jogo)	VALUES ('torneio internacional', '2019-11-01', '2019-11-15', 2);

--insere times torneios
INSERT INTO public.torneio_times(id_torneio, id_time) VALUES (1, 1);
INSERT INTO public.torneio_times(id_torneio, id_time) VALUES (1, 2);
INSERT INTO public.torneio_times(id_torneio, id_time) VALUES (1, 3);
INSERT INTO public.torneio_times(id_torneio, id_time) VALUES (1, 4);
INSERT INTO public.torneio_times(id_torneio, id_time) VALUES (1, 5);
INSERT INTO public.torneio_times(id_torneio, id_time) VALUES (1, 6);
INSERT INTO public.torneio_times(id_torneio, id_time) VALUES (1, 7);
INSERT INTO public.torneio_times(id_torneio, id_time) VALUES (1, 8);
INSERT INTO public.torneio_times(id_torneio, id_time) VALUES (1, 9);
INSERT INTO public.torneio_times(id_torneio, id_time) VALUES (1, 10);
INSERT INTO public.torneio_times(id_torneio, id_time) VALUES (1, 11);
INSERT INTO public.torneio_times(id_torneio, id_time) VALUES (1, 12);
INSERT INTO public.torneio_times(id_torneio, id_time) VALUES (1, 13);
INSERT INTO public.torneio_times(id_torneio, id_time) VALUES (1, 14);
INSERT INTO public.torneio_times(id_torneio, id_time) VALUES (1, 15);
INSERT INTO public.torneio_times(id_torneio, id_time) VALUES (1, 16);