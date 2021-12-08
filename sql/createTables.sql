create table `User_roles`
(
    `id` bigint unsigned auto_increment,
    `name` varchar(50) not null,
    primary key (`id`)
);

create table `Users`
(
  `id` bigint unsigned auto_increment,
  `user_role_id` bigint unsigned,
  `name` varchar(50) not null,
  `surname` varchar(50) not null,
  `login` varchar(50) not null,
  `password` varchar(50) not null,
  `reg_date` date not null,
  primary key (`id`),
  foreign key (user_role_id) references User_roles (id)
);

create table `Categories`
(
    `id` bigint unsigned auto_increment,
    `name` varchar(50) not null,
    primary key (`id`)
);

create table `Articles`
(
  `id` bigint unsigned auto_increment,
  `user_id` bigint unsigned,
  `category_id` bigint unsigned,
  `heading` varchar(50) not null,
  `text` text not null,
  `is_published` boolean not null,
  `date` date not null,
  primary key (`id`),
  foreign key (user_id) references Users (id),
  foreign key (category_id) references Categories (id)
);

create table `Comments`
(
  `id` bigint unsigned auto_increment,
  `user_id` bigint unsigned,
  `article_id` bigint unsigned,
  `text` text not null,
  `date` date not null,
  primary key (`id`),
  foreign key (user_id) references Users (id),
  foreign key (article_id) references Articles (id)
);

create table `Ratings`
(
    `id` bigint unsigned auto_increment,
    `user_id` bigint unsigned,
    `article_id` bigint unsigned,
    `rating` tinyint unsigned not null,
    primary key (`id`),
    foreign key (user_id) references Users (id),
    foreign key (article_id) references Articles (id)
);

