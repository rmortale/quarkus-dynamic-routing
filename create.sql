-- Table: public.tpl_config

DROP TABLE IF EXISTS public.routing_config;

CREATE TABLE IF NOT EXISTS public.routing_config
(
    id bigserial NOT NULL,
    serviceid character varying(255) COLLATE pg_catalog."default",
    routing_config character varying(2048) COLLATE pg_catalog."default",
    order_num int,
    CONSTRAINT routing_config_pkey PRIMARY KEY (id)
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.routing_config
    OWNER to postgres;