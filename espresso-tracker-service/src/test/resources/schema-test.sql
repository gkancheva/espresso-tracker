CREATE TABLE bakeries (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` text NOT NULL,
  `img_src` text,
  `name` varchar(255) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `web_site` text NOT NULL
);

CREATE TABLE coffees (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` int NOT NULL,
  `date_created` date NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `origin` varchar(255) NOT NULL,
  `roasted_on_date` date NOT NULL,
  `bakery_id` bigint DEFAULT NULL
);