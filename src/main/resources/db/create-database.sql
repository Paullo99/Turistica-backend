DROP DATABASE IF EXISTS `turistica`;
CREATE DATABASE `turistica`;
USE `turistica`;

CREATE TABLE `trip` (
						`id` int NOT NULL AUTO_INCREMENT, 
						`name` varchar(255) NOT NULL, 
						`begin_date` date NOT NULL, 
						`end_date` date NOT NULL, 
						`price_per_person` int,
						`people_limit` int,
						`description` varchar(255), 
						`map` varchar(2000),
                        `trip_type_id` int NOT NULL,
						PRIMARY KEY (`id`)
)AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user` (
						`id` int NOT NULL AUTO_INCREMENT, 
						`email` varchar(255) NOT NULL UNIQUE, 
						`password` varchar(255) NOT NULL, 
						`name` varchar(50) NOT NULL, 
						`last_name` varchar(100) NOT NULL, 
						`address` varchar(150),
						`city` varchar(100),
						`postcode` varchar(20),
						`phone_number` varchar(30), 
						`role_id` int NOT NULL, 
						PRIMARY KEY (`id`)
) AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `role` (
						`id` int NOT NULL AUTO_INCREMENT, 
						`name` varchar(255) NOT NULL, 
						PRIMARY KEY (`id`)
)AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `trip_type` (
						`id` int NOT NULL AUTO_INCREMENT, 
						`name` varchar(100) NOT NULL, 
						PRIMARY KEY (`id`)
)AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE `user` ADD CONSTRAINT FKUser87814 FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);

ALTER TABLE `trip` ADD CONSTRAINT FKTrip88986 FOREIGN KEY (`trip_type_id`) REFERENCES `trip_type` (`id`);

CREATE TABLE `user_trip` (
						`trip_id` int NOT NULL, 
						`user_id` int NOT NULL, 
						PRIMARY KEY (trip_id, user_id));
						
ALTER TABLE `user_trip` ADD CONSTRAINT FKuser_trip60884 FOREIGN KEY (`trip_id`) REFERENCES `trip` (`id`);
ALTER TABLE `user_trip` ADD CONSTRAINT FKuser_trip531011 FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
