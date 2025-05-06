create table oauth_code (
	code varchar(256),
	authentication blob
) engine=InnoDB default charset=utf8mb4;