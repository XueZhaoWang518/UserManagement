use USER;
create table if not exists `users`(
    `id` int(11) auto_increment primary key not null ,
    `username` varchar(200) not null,
    `password` varchar(200) not null,
    `email` varchar(200) not null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table if not exists `roles`(
    `id` int(11) auto_increment primary key not null ,
    `name` varchar(200) not null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table  if not exists  `user_roles`(
    `id` int(11) auto_increment primary key not null,
    `user_id` int(11) not null ,
    `role_id` int(11) not null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
