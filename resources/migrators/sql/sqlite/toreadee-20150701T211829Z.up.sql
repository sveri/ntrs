CREATE TABLE toread
(id INTEGER CONSTRAINT PK_TOREADEE PRIMARY KEY NOT NULL,
title VARCHAR(300) NOT NULL,
link VARCHAR(1000) NOT NULL,
description TEXT,
done BOOLEAN NOT NULL,
tags TEXT,
author VARCHAR(100),
added_at TEXT NOT NULL,
user VARCHAR(30) NOT NULL,
uuid VARCHAR(43) NOT NULL,
FOREIGN KEY (user) REFERENCES "user"("email"));