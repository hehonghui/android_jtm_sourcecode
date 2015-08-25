-- 注意,每条sql语句必须独占一行,否则会出错.
CREATE TABLE classes (
	Id	INTEGER PRIMARY KEY AUTOINCREMENT,
	major	TEXT,
	year	INTEGER not null
 );
