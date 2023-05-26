CREATE TABLE `users`
(
    `id` int not null auto_increment,
    `login` varchar(64) not null,
    `password` varchar(64) not null,
    PRIMARY KEY (`id`)
);
