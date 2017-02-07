DROP SCHEMA  IF EXISTS socketChat;
CREATE SCHEMA socketChat;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS authMessages;
DROP TABLE IF EXISTS regMessages;
DROP TABLE IF EXISTS contactsMessages;
DROP TABLE IF EXISTS logoutMessages;


CREATE TABLE users (
id IDENTITY,
login VARCHAR(15) NOT NULL,
password VARCHAR(15) NOT NULL,
email VARCHAR(25) NOT NULL,


birthday DATE,
name VARCHAR(55),
address VARCHAR(25),
phone VARCHAR(15),
contacts INTEGER,
regTime TIMESTAMP,
FOREIGN KEY (contacts) REFERENCES users(id)
);


CREATE TABLE messages(
id IDENTITY,
sender INTEGER NOT NULL,
receiver INTEGER NOT NULL,
content VARCHAR(200) NOT NULL,
sendTime TIMESTAMP NOT NULL,
FOREIGN KEY (sender) REFERENCES users(id),
FOREIGN KEY(receiver) REFERENCES users(id)
);


CREATE TABLE authMessages(
id IDENTITY,
sendTime TIMESTAMP,
login VARCHAR(15) NOT NULL,
password VARCHAR(15) NOT NULL,
);

CREATE TABLE regMessages(

--mandatory:
id IDENTITY,
sendTime TIMESTAMP,
login VARCHAR(15) NOT NULL,
password VARCHAR(15) NOT NULL,
email VARCHAR(25) NOT NULL,

--optional:
birthday DATE,
name VARCHAR(55),
address VARCHAR(25),
phone VARCHAR (15)
);

CREATE TABLE contactsMessages(
id IDENTITY,
sendTime TIMESTAMP,
user INTEGER NOT NULL,
FOREIGN KEY (user) REFERENCES users(id),
);

CREATE TABLE logoutMessages(
id IDENTITY,
sendTime TIMESTAMP,
user INTEGER NOT NULL,
FOREIGN KEY (user) REFERENCES users(id),
);
