create table tbl_relation
(
    part_id    int not null
        constraint fk_part_id
            references tbl_part
            on delete cascade,
    visibility tinyint,
    spec       varchar(45),
    value      varchar(45)
);


