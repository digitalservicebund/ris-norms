CREATE TABLE IF NOT EXISTS
    procedure
(
    id                         uuid         NOT NULL PRIMARY KEY,
    state                      VARCHAR(50)  NOT NULL,
    eli                        VARCHAR(255) NOT NULL,
    print_announcement_gazette VARCHAR(10)  NOT NULL,
    print_announcement_year    VARCHAR(4)   NOT NULL,
    print_announcement_page    VARCHAR(5)   NOT NULL
);
