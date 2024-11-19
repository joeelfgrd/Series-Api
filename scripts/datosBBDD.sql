INSERT INTO SERIES (NOMBRE, FECHA_ESTRENO, TEMATICA, DIRECTOR, RATING, IDIOMA, ESTADO, CADENA)
VALUES ('Breaking Bad', '2008-01-20', 'Drama', 'Vince Gilligan', 9, 'English', 'TERMINADA', 'AMC');

INSERT INTO SERIES (NOMBRE, FECHA_ESTRENO, TEMATICA, DIRECTOR, RATING, IDIOMA, ESTADO, CADENA)
VALUES ('Stranger Things', '2016-07-15', 'Sci-Fi', 'The Duffer Brothers', 8, 'English', 'TERMINADA', 'Netflix');

INSERT INTO SERIES (NOMBRE, FECHA_ESTRENO, TEMATICA, DIRECTOR, RATING, IDIOMA, ESTADO, CADENA)
VALUES ('La Casa de Papel', '2017-05-02', 'Crime', 'Álex Pina', 8, 'Spanish', 'EN EMISION', 'Antena 3');

INSERT INTO Episodios (numero, temporada, nombre, serie, FECHA_SALIDA, duracion) VALUES
-- Breaking Bad
(1, 1, 'Pilot', 1, '2008-01-20', 58),
(2, 1, 'Cats in the Bag...', 1, '2008-01-27', 48),
(3, 1, '...And the Bags in the River', 1, '2008-02-10', 47),
(4, 2, 'Seven Thirty-Seven', 1, '2009-03-08', 47),
(5, 2, 'Grilled', 1, '2009-03-15', 47),

-- Stranger Things
(1, 1, 'The Vanishing of Will Byers', 2, '2016-07-15', 49),
(2, 1, 'The Weirdo on Maple Street', 2, '2016-07-15', 55),
(3, 1, 'Holly, Jolly', 2, '2016-07-15', 51),
(4, 2, 'MADMAX', 2, '2017-10-27', 48),
(5, 2, 'Trick or Treat, Freak', 2, '2017-10-27', 56),

-- La Casa de Papel
(1, 1, 'Efectuar lo acordado', 3, '2017-05-02', 50),
(2, 1, 'Imprudencias letales', 3, '2017-05-09', 41),
(3, 1, 'Errar al disparar', 3, '2017-05-16', 44),
(4, 2, 'La cabeza del plan', 3, '2017-10-16', 47),
(5, 2, 'A contrarreloj', 3, '2017-10-23', 50);