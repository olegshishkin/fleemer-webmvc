-- persons table
create table if not exists `persons`
(
  `id`            bigint unsigned auto_increment unique primary key,
  `first_name`    varchar(255)    not null,
  `last_name`     varchar(255)    null,
  `email`         varchar(255)    not null unique,
  `hash`          varchar(255)    not null
) engine = InnoDB;

-- accounts table
create table if not exists `accounts`
(
  `id`            bigint unsigned auto_increment unique primary key,
  `type`          tinyint         not null,
  `currency`      tinyint         not null,
  `name`          varchar(255)    not null,
  `sum`           decimal(20, 10) not null,
  `person_id`     bigint unsigned not null,
  foreign key (`person_id`) references `persons` (`id`),
  index (`person_id`)
) engine = InnoDB;

-- categories table
create table if not exists `categories`
(
  `id`            bigint unsigned auto_increment unique primary key,
  `name`          varchar(255)    not null,
  `type`          tinyint         not null,
  `person_id`     bigint unsigned not null,
  foreign key (`person_id`) references `persons` (`id`),
  index (`person_id`)
) engine = InnoDB;

-- operations table
create table if not exists `operations`
(
  `id`            bigint unsigned auto_increment unique primary key,
  `time`          timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
  `account_id`    bigint unsigned                     not null,
  `category_id`   bigint unsigned                     not null,
  `sum`           decimal(20, 10)                     not null,
  `result`        decimal(20, 10)                     not null,
  `uuid`          varchar(32)                         null,
  `comment`       varchar(255)                        null,
  foreign key (`account_id`) references `accounts` (`id`),
  foreign key (`category_id`) references `categories` (`id`),
  index (`account_id`),
  index (`category_id`)
) engine = InnoDB;
