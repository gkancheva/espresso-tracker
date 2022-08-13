CREATE TABLE IF NOT EXISTS bakeries (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` text NOT NULL,
  `img_src` text,
  `name` varchar(255) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `web_site` text NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS coffees (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` int NOT NULL,
  `date_created` date NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `origin` varchar(255) NOT NULL,
  `roasted_on_date` date NOT NULL,
  `bakery_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS coffee_tools (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `coffee_tool_type` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS espresso_settings (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `brewing_pressure` float NOT NULL,
  `brewing_temperature` float NOT NULL,
  `dose` float NOT NULL,
  `extract_duration_sec` int NOT NULL,
  `grinding_fineness` varchar(255) NOT NULL,
  `volume` float NOT NULL,
  `coffee_id` bigint DEFAULT NULL,
  `coffee_machine_id` bigint DEFAULT NULL,
  `grinder_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS users (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `user_role` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `coffee_machine_id` bigint DEFAULT NULL,
  `grinder_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS users_coffees (
  `user_id` bigint NOT NULL,
  `coffee_id` bigint NOT NULL
);



