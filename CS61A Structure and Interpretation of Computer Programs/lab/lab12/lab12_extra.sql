.read lab12.sql

-- Q5
CREATE TABLE greatstudents AS
  SELECT a.date, a.color, a.pet, a.number, b.number
    FROM fa17students as b, students as a
    WHERE a.date = b.date AND a.color = B.color AND a.pet = b.pet

;

-- Q6
CREATE TABLE sevens AS
  SELECT seven
    FROM students, checkboxes
    WHERE students.time = checkboxes.time AND students.number = 7 AND checkboxes."7" = "True";

-- Q7
CREATE TABLE fa17favnum AS
  SELECT number, count(*) as count 
    FROM fa17students
    GROUP BY number
    ORDER BY count DESC 
    LIMIT 1
;


CREATE TABLE fa17favpets AS
  SELECT pet, count(*) as count 
    FROM fa17students
    GROUP BY pet
    ORDER BY count DESC 
    LIMIT 10
;


CREATE TABLE sp18favpets AS
  SELECT pet, count(*) as count 
    FROM students
    GROUP BY pet
    ORDER BY count DESC 
    LIMIT 10
;


CREATE TABLE sp18dog AS
    SELECT pet, count(*) as count
    FROM students
    WHERE pet = "dog"
    ;


CREATE TABLE sp18alldogs AS
    SELECT pet, count(*) as count
    FROM students
    WHERE pet LIKE '%dog%'
    ;


CREATE TABLE obedienceimages AS
    SELECT seven, denero, count(*) AS count
    FROM students
    WHERE seven = "7"
    GROUP BY denero
    ;

-- Q8

CREATE TABLE smallest_int_count AS
    SELECT smallest, count(*) AS count
    FROM students
    GROUP BY smallest
    ORDER BY smallest
    ;
