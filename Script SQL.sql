
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