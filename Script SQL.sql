
CREATE TABLE public.categorias
(
    id serial NOT NULL,
    nome text NOT NULL,
    CONSTRAINT categorias_pk PRIMARY KEY (id)
);

ALTER TABLE public.categorias
    OWNER to postgres;


CREATE TABLE public.jogos
(
    id serial NOT NULL,
    nome text NOT NULL,
    id_categoria integer NOT NULL,
    CONSTRAINT jogos_pk PRIMARY KEY (id),
    CONSTRAINT categoria_jogos_fk FOREIGN KEY (id_categoria)
        REFERENCES public.categorias (id) MATCH SIMPLE
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
);

ALTER TABLE public.jogos
    OWNER to postgres;
	
CREATE TABLE public.torneios
(
    id serial NOT NULL,
    nome text NOT NULL,
    inicio date,
    fim date,
    id_jogo integer NOT NULL,
    CONSTRAINT torneios_pk PRIMARY KEY (id),
    CONSTRAINT torneios_jogos_fk FOREIGN KEY (id_jogo)
        REFERENCES public.jogos (id) MATCH SIMPLE
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
);

ALTER TABLE public.torneios
    OWNER to postgres;
	
CREATE TABLE public.times
(
    id integer NOT NULL DEFAULT nextval('times_id_seq'::regclass),
    nome text COLLATE pg_catalog."default" NOT NULL,
    logo bytea,
    CONSTRAINT time_pk PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.times
    OWNER to postgres;
	
CREATE TABLE public.torneio_times
(
    id_torneio integer NOT NULL,
    id_time integer NOT NULL,
    CONSTRAINT torneio_times_pk PRIMARY KEY (id_torneio, id_time),
    CONSTRAINT id_torneio FOREIGN KEY (id_torneio)
        REFERENCES public.torneios (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT id_time FOREIGN KEY (id_time)
        REFERENCES public.times (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

ALTER TABLE public.torneio_times
    OWNER to postgres;

CREATE TABLE public.torneio_partidas
(
    id serial NOT NULL,
    id_torneio integer NOT NULL,
    id_time_1 integer NOT NULL,
    id_time_2 integer NOT NULL,
    pontos_1 integer,
    pontos_2 integer,
    fase integer NOT NULL,
    CONSTRAINT torneio_partidas_pk PRIMARY KEY (id),
    CONSTRAINT torneio_partidas_fk FOREIGN KEY (id_torneio)
        REFERENCES public.torneios (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT torneio_partidas_time1_fk FOREIGN KEY (id_time_1)
        REFERENCES public.times (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT torneio_partidas_time2_fk FOREIGN KEY (id_time_2)
        REFERENCES public.times (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

ALTER TABLE public.torneio_partidas
    OWNER to postgres;
    
    
    
CREATE FUNCTION public.tpartida_fase()
    RETURNS trigger
    LANGUAGE 'plpgsql'
     NOT LEAKPROOF
AS $BODY$
begin
	if (new.fase is null) then
		new.fase = 1;
	end if;
end;
$BODY$;

ALTER FUNCTION public.tpartida_fase()
    OWNER TO postgres;
    
CREATE TRIGGER tpartidas_bi
    BEFORE INSERT
    ON public.torneio_partidas
    FOR EACH ROW
    EXECUTE PROCEDURE public.tpartida_fase();
	
	
	
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


	