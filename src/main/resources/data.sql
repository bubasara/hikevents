USE `db_hikevents`;
INSERT IGNORE INTO `db_hikevents`.`roles` (`name`) VALUES ("ROLE_ADMIN");
INSERT IGNORE INTO `db_hikevents`.`roles` (`name`) VALUES ("ROLE_HIKINGCLUB");
INSERT IGNORE INTO `db_hikevents`.`roles` (`name`) VALUES ("ROLE_HIKER");
INSERT IGNORE INTO `db_hikevents`.`users` (`city`,`email`, `name`, `password`, `year`, `role_id`)
	VALUES ("Nis", "admin@hikevents.com", "Admin", "$2y$12$pRpZcT6RL7YjNK4Ml2zg2uf2WIQb4bU49fMmuZY2soNGd2KKIqVFi", "1996", 1);
	
	