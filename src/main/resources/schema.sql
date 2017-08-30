create table links
(
  id serial not null
    constraint links_pkey
    primary key,
  url varchar,
  description varchar
)
;

create table token
(
  id serial not null
    constraint token_pkey
    primary key,
  value varchar not null
)
;

