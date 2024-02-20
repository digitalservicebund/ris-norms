CREATE TABLE IF NOT EXISTS
    amending_law
(
    id                               uuid         NOT NULL PRIMARY KEY,
    eli                              character varying(255) NOT NULL,
    print_announcement_gazette       character varying(15)  ,
    digital_announcement_medium      character varying(255) ,
    publication_date                 date         ,
    printed_announcement_page        character varying(15)   ,
    digital_announcement_edition     character varying(15)
);

CREATE TABLE IF NOT EXISTS
    article
(
    id                              uuid         NOT NULL PRIMARY KEY,
    enumeration                     character varying(255) NOT NULL,
    eli                             character varying(255) NOT NULL,
    title                           character varying(255) ,
    amending_law_id                 uuid       REFERENCES amending_law (id)
);
