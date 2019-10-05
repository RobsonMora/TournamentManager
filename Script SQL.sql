CREATE TABLE public.torneios
(
    id serial NOT NULL,
    nome text NOT NULL,
    quantidade_times integer NOT NULL,
    CONSTRAINT torneios_pk PRIMARY KEY (id)
);

ALTER TABLE public.torneios
    OWNER to postgres;


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