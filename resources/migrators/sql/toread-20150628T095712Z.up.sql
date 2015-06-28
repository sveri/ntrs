CREATE TABLE PUBLIC.toread (id INT AUTO_INCREMENT NOT NULL,
user INT,
title VARCHAR(300) NOT NULL,
link VARCHAR(1000) NOT NULL, description CLOB, done BOOLEAN DEFAULT FALSE NOT NULL, tags CLOB, author VARCHAR(100),
uuid VARCHAR(43) NOT NULL,
FOREIGN KEY (user) REFERENCES "user"("id"),
CONSTRAINT PK_TOREAD PRIMARY KEY (id))