create sequence public.security_id_seq;

alter sequence public.security_id_seq owner to postgres;

create table if not exists public.users
(
    id               bigserial
        constraint users_pk
            primary key,
    firstname        varchar(20),
    second_name      varchar(20),
    age              integer,
    telephone_number varchar(20),
    email            varchar(20),
    created          timestamp default now() not null,
    updated          timestamp default now() not null,
    sex              varchar(1),
    is_deleted       boolean   default false
);

alter table public.users
    owner to postgres;

create table if not exists public.security
(
    id       bigint      default nextval('security_id_seq'::regclass) not null
        constraint security_pk
            primary key,
    login    varchar(20)                                              not null,
    password varchar(20)                                              not null,
    role     varchar(20) default 'USER'::character varying            not null,
    created  timestamp   default now()                                not null,
    updated  timestamp   default now(),
    user_id  bigint                                                   not null
        unique
        constraint security_users_id_fk
            references public.users
            on update cascade on delete cascade
);

alter table public.security
    owner to postgres;

create table if not exists public.product
(
    id      bigserial
        constraint product_pk
            primary key,
    name    varchar(50)      not null,
    price   double precision not null,
    created timestamp default now(),
    updated timestamp default now()
);

alter table public.product
    owner to postgres;

create table if not exists public.l_users_product
(
    id         integer not null
        constraint l_users_product_pk
            primary key,
    user_id    bigint  not null
        constraint l_users_product_users_id_fk
            references public.users
            on update cascade on delete cascade,
    product_id bigint  not null
        constraint l_users_product_product_id_fk
            references public.product
            on update cascade on delete cascade,
    created    timestamp default now(),
    updated    timestamp default now()
);

alter table public.l_users_product
    owner to postgres;