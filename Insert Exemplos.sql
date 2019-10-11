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
INSERT INTO public.times(nome) VALUES ('Mibr');
INSERT INTO public.times(nome) VALUES ('Disguised Toast');
INSERT INTO public.times(nome) VALUES ('ChocoTaco');
INSERT INTO public.times(nome) VALUES ('Shroud');

--insere torneio
INSERT INTO public.torneios(nome, inicio, fim, id_jogo)	VALUES ('torneio internacional', '2019-11-01', '2019-11-15', 2);

--insere times torneios
INSERT INTO public.torneio_times(id_torneio, id_time) VALUES (1, 1);
INSERT INTO public.torneio_times(id_torneio, id_time) VALUES (1, 2);
INSERT INTO public.torneio_times(id_torneio, id_time) VALUES (1, 3);
INSERT INTO public.torneio_times(id_torneio, id_time) VALUES (1, 4);