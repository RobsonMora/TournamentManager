CREATE TABLE public.torneios
(
    id serial NOT NULL,
    nome text NOT NULL,
    quantidade_times integer NOT NULL,
    CONSTRAINT torneios_pk PRIMARY KEY (id)
);

ALTER TABLE public.torneios
    OWNER to postgres;