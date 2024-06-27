CREATE SCHEMA IF NOT EXISTS filmo_tokio;

/*
User: user
Password: user
*/
INSERT INTO filmo_tokio.user
    (active, birth_date, creation_date, email, name, password, surname, username)
VALUES
    (1, '1990-01-01', '2024-01-01', 'admin@filmotokio.com', 'tokioschool', '$2a$10$pnp.gfZQtGKC89ynCCHLjup8e3EQo0EipcrYvRYT3qRZ6z.URaHwi', 'tokioschool', 'tokioschool'),
    (1, '1990-01-01', '2024-01-01', 'nunes@filmotokio.com', 'User', '$2a$10$MCKo6Oi26HkzJP/An./ENeeAsB.ycy8QgkIUL26uclDrpuTbWD2q6', 'User', 'user');


INSERT INTO filmo_tokio.role
    (role)
VALUES
    ('ROLE_USER'), ('ROLE_ADMIN');


INSERT INTO filmo_tokio.user_role VALUES
(1, 1), (1, 2), (2, 1), (2, 2);


INSERT INTO filmo_tokio.person VALUES
(1,'Edward','Zwick','DIRETOR'),
(2,'Leonardo','DiCaprio','ACTOR'),
(3,'Djimon','Hounsou','ACTOR'),
(4,'Jennifer','Connelly','ACTOR'),
(5,'Eduardo','Serra','FOTOGRAFO'),
(6,'Charles','Leavitt','GUIONISTA'),
(7,'James','Newton Howard','MUSICO'),
(8,'Jim','Carrey','ACTOR'),
(9,'Jeff','Daniels','ACTOR'),
(10,'Lauren','Holly','ACTOR'),
(11,'Peter','Farrelly','DIRETOR'),
(12,'Mark','Irwin','FOTOGRAFO'),
(13,'Peter','Farrelly','GUIONISTA'),
(14,'Todd','Rundgren','MUSICO'),
(15,'Marlon','Brando','ACTOR'),
(16,'Al','Pacino','ACTOR'),
(17,'James','Caan','ACTOR'),
(18,'Francis','Ford Coppola','DIRETOR'),
(19,'Gordon','Willis','FOTOGRAFO'),
(20,'Mario','Puzo','GUIONISTA'),
(22,'Tom','Hagen','ACTOR'),
(23,'Diane','Keaton','ACTOR'),
(24,'Nino','Rota','MUSICO'),
(25,'Talia','Shire','ACTOR'),
(26,'Carmine','Coppola','MUSICO'),
(27,'Joseph','Gordon-Levitt','ACTOR'),
(28,'Elliot','Page','ACTOR'),
(29,'Christopher','Nolan','DIRETOR'),
(30,'Wally','Pfister','FOTOGRAFO'),
(31,'Christopher','Nolan','GUIONISTA'),
(32,'Hans','Zimmer','MUSICO'),
(33,'Ellen','Burstyn','ACTOR'),
(34,'Matthew','McConaughey','ACTOR'),
(35,'Mackenzie','Foy','ACTOR'),
(36,'Hoyte','Van Hoytema','FOTOGRAFO'),
(37,'Jonathan','Nolan','GUIONISTA'),
(38,'Robert','Duvall','ACTOR'),
(39,'George','Lucas','DIRETOR'),
(40,'Liam','Neeson','ACTOR'),
(41,'Ewan','McGregor','ACTOR'),
(42,'Natalie','Portman','ACTOR'),
(43,'George','Lucas','GUIONISTA'),
(44,'David','Tattersall','FOTOGRAFO'),
(45,'John','Williams','MUSICO'),
(46,'Hayden','Christensen','ACTOR');

INSERT INTO filmo_tokio.film VALUES
(1,NULL,143, 0,'/user1/blod-diamond.jpg','Um pescador, um contrabandista e um sindicato de empresários se enfrentam sobre a posse de um diamante de valor inestimável.','Diamante de Sangue',2006,1,5,1),
(2,NULL,107, 0,'/user1/doidos-solta.jpg','Depois que uma mulher deixa uma maleta no terminal do aeroporto, um motorista bobo de limusine e sua amigo bobo embarcam em uma divertida viagem por Aspen para devolver a maleta ao proprietário.','Dumb and Dumber',1994,11,12,1),
(3,NULL,175, 0,'/user1/godfather1.jpg','O patriarca idoso de uma dinastia do crime organizado transfere o controle de seu império clandestino para seu filho relutante.','Godfather I',1972,18,19,1),
(4,NULL,202, 0,'/user1/godfather2.jpg','Em 1950, Michael Corleone, agora à frente da família, tenta expandir o negócio do crime a Las Vegas, Los Angeles e Cuba. Paralelamente, é revelada a história de Vito Corleone, e de como saiu da Sicília e chegou a Nova Iorque.','Godfather II',1974,18,19,1),
(5,NULL,162, 0,'/user1/godfather3.jpg','No meio de tentar legitimar seus negócios na cidade de Nova York e na Itália em 1979, o velho mafioso Don Michael Corleone procura confessar seus pecados, enquanto tomava seu sobrinho Vincent Mancini baixo sua proteção.','Godfather III',1990,18,19,1),
(6,NULL,148, 0,'/user1/inception.jpg','Um ladrão que rouba segredos corporativos através da tecnologia de entrar no subconsciente recebe a tarefa inversa de plantar uma idéia na mente do diretor de uma grande empresa.','Inception',2010,29,30,1),
(7,NULL,169, 0,'/user1/interstellar.jpg','Uma equipe de exploradores viaja através de um buraco de minhoca no espaço, na tentativa de garantir a sobrevivência da humanidade.','Interestelar',2014,29,36,1),
(8,NULL,136, 0,'/user1/star-wars-i.jpg','Dois cavaleiros Jedi escapam de um bloco hostil para procurar aliados e encontrar um jovem que possa restaurar o equilíbrio na Força. Mas os anteriormente inativos Sith ressurgem para reivindicar sua antiga glória','Star Wars: Episode I',1999,39,44,1),
(9,NULL,142, 0,'/user1/star-wars-ii.jpg','Dez anos depois de seu primeiro encontro, o Anakin Skywalker e a Padmé Admidala curtem um romance proibido. Enquanto Obi-wan Kenobi investiga uma tentativa de assassinato de um senador.','Star Wars: Episode II',2002,39,44,1),
(10,NULL,140, 0,'/user1/star-wars-iii.jpg','As Guerras Clônicas começaram há 3 anos. Os Jedi resgatam Palpatine, do Conde Dooku, e Obi-wan mantém o controle de uma nova ameaça, enquanto Anakin atua como um agente duplo.','Star Wars, Episódio III',2005,39,44,1);

INSERT INTO filmo_tokio.film_actores
VALUES (1,2),(6,2),(1,3),(1,4),(2,8),(2,9),(2,10),(3,15),(3,16),(4,16),(5,16),(3,17),(4,23),(5,23),(5,25),(6,27),(6,28),(7,33),(7,34),(7,35),(4,38),(8,40),(8,41),(9,41),(10,41),(8,42),(9,42),(10,42),(9,46),(10,46);

INSERT INTO filmo_tokio.film_guionistas
VALUES (1,6),(2,13),(3,20),(4,20),(5,20),(6,31),(7,37),(8,43),(9,43),(10,43);

INSERT INTO filmo_tokio.film_musicos VALUES (1,7),(2,14),(3,24),(4,24),(5,26),(6,32),(7,32),(8,45),(9,45),(10,45);

INSERT INTO filmo_tokio.review (date, text_review, title, film_id, user_id)
values
    ('2024-03-22 13:26:00.000000', 'Melhor filme de Sempre', 'Top', 1, 1),
    ('2024-03-20 14:31:00.000000', 'Muito Bom', 'Gostei', 1, 2),
    ('2024-03-25 11:04:00.000000', 'Só rir', 'Divertido', 2, 1),
    ('2024-03-24 12:15:00.000000', 'Grande comédia', 'Comédia', 2, 2)

INSERT INTO filmo_tokio.score (value, film_id, user_id)
values
    (8, 1, 1),
    (9, 1, 2),
    (10, 2, 1),
    (8, 2, 2);
