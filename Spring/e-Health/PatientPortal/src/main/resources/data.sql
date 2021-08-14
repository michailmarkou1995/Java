/* ##### setting up db root user and some sample test Doctors ##### */

use
ehealth_db;

/*DELIMITER $$

DROP FUNCTION IF EXISTS initcap$$

CREATE FUNCTION initcap(x char(30)) RETURNS char(30) CHARSET utf8
    READS SQL DATA
    DETERMINISTIC
BEGIN
    SET @str='';
    SET @l_str='';
    WHILE x REGEXP ' ' DO
SELECT SUBSTRING_INDEX(x, ' ', 1) INTO @l_str;
SELECT SUBSTRING(x, LOCATE(' ', x)+1) INTO x;
SELECT CONCAT(@str, ' ', CONCAT(UPPER(SUBSTRING(@l_str,1,1)),LOWER(SUBSTRING(@l_str,2)))) INTO @str;
END WHILE;
RETURN LTRIM(CONCAT(@str, ' ', CONCAT(UPPER(SUBSTRING(x,1,1)),LOWER(SUBSTRING(x,2)))));
END$$

DELIMITER ;

-- SHOW FUNCTION STATUS;*/

-- 1. start a new transaction
SET
autocommit = 0;
START TRANSACTION;

-- 2. create App roles in database
INSERT INTO role (role_id, name)
SELECT *
FROM (SELECT '0' AS role_id, 'ROLE_USER' AS name) AS tmp
WHERE NOT EXISTS(SELECT name FROM role WHERE name = 'ROLE_USER') LIMIT 1;

INSERT INTO role (role_id, name)
SELECT *
FROM (SELECT '1' AS role_id, 'ROLE_DOCTOR' AS name) AS tmp
WHERE NOT EXISTS(SELECT name FROM role WHERE name = 'ROLE_DOCTOR') LIMIT 1;

INSERT INTO role (role_id, name)
SELECT *
FROM (SELECT '2' AS role_id, 'ROLE_ADMIN' AS name) AS tmp
WHERE NOT EXISTS(SELECT name FROM role WHERE name = 'ROLE_ADMIN') LIMIT 1;


-- 2.1. Create medication account to avoid constraint
INSERT INTO medication_account (medication_account_id, account_number, total_medications_over_year, patient_patient_id)
SELECT *
FROM (SELECT 0    AS medication_account_id,
             1    AS account_number,
             0    AS total_medications_over_year,
             null AS patient_patient_id) AS tmp
WHERE NOT EXISTS(SELECT medication_account_id FROM medication_account WHERE medication_account_id = '0') LIMIT 1;

-- 3. create root user password: root
--username: root password: root
INSERT INTO patient (patient_id, city, date_of_birth, doctor_is, email, enabled, first_name, last_name, password, phone,
                     street_address, username, medication_account_fk)
SELECT *
FROM (SELECT '2'                                                            AS patient_id,
             'Greece'                                                       AS city,
             '2000-01-01'                                                   AS date_of_birth,
             0                                                              AS doctor_is,
             'root@noresponse.com'                                          AS email,
             1                                                              AS enabled,
             'root'                                                         AS first_name,
             'root'                                                         AS last_name,
             '$2a$12$s8Frwx3TBjuiZuIo0RNNW./vMBRwxw3edNOgOZ4ZLxgFrO923lG.K' AS password,
             '0000000000'                                                   AS phone,
             NULL                                                           AS street_address,
             'root'                                                         AS username,
             '0'                                                            AS medication_account_fk) AS tmp
WHERE NOT EXISTS(SELECT patient_id FROM patient WHERE patient_id = '2') LIMIT 1;

-- 4. alter from simple user to root
UPDATE medication_account
SET patient_patient_id = 2
WHERE medication_account_id = 0;

INSERT INTO patient_role (patient_role_id, patient_id, role_id)
SELECT *
FROM (SELECT '1' AS patient_role_id, '2' AS patient_id, '2' AS role_id) AS tmp
WHERE NOT EXISTS(SELECT patient_id FROM patient_role WHERE patient_id = '2') LIMIT 1;

COMMIT;

---------------------Template staff not for Production App below (can comment out)---------------------

START TRANSACTION;

---DOCTOR 1----
INSERT INTO medication_account (medication_account_id, account_number, total_medications_over_year, patient_patient_id)
SELECT *
FROM (SELECT 1    AS medication_account_id,
             2    AS account_number,
             0    AS total_medications_over_year,
             null AS patient_patient_id) AS tmp
WHERE NOT EXISTS(SELECT medication_account_id FROM medication_account WHERE medication_account_id = '1') LIMIT 1;


-- Template Doctor for testing the app Live on Development Server
-- username: TemplateDoctor password: root
INSERT INTO patient (patient_id, city, date_of_birth, doctor_is, email, enabled, first_name, last_name, password, phone,
                     street_address, username, medication_account_fk)
SELECT *
FROM (SELECT '5'                                                            AS patient_id,
             'Athens'                                                       AS city,
             '2000-01-01'                                                   AS date_of_birth,
             1                                                              AS doctor_is,
             'drStrange@multiverseofmadness.gov'                            AS email,
             1                                                              AS enabled,
             'drStrange'                                                    AS first_name,
             'mrDoctor'                                                     AS last_name,
             '$2a$12$s8Frwx3TBjuiZuIo0RNNW./vMBRwxw3edNOgOZ4ZLxgFrO923lG.K' AS password,
             '0000000000'                                                   AS phone,
             NULL                                                           AS street_address,
             'TemplateDoctor'                                               AS username,
             '1'                                                            AS medication_account_fk) AS tmp
WHERE NOT EXISTS(SELECT patient_id FROM patient WHERE patient_id = '5') LIMIT 1;

UPDATE medication_account
SET patient_patient_id = 5
WHERE medication_account_id = 1;

INSERT INTO patient_role (patient_role_id, patient_id, role_id)
SELECT *
FROM (SELECT '2' AS patient_role_id, '5' AS patient_id, '1' AS role_id) AS tmp
WHERE NOT EXISTS(SELECT patient_id FROM patient_role WHERE patient_id = '5') LIMIT 1;

INSERT INTO doctor (doctor_id, category_doctor, city, first_name, last_name, rank, patient_role_id_fk)
SELECT *
FROM (SELECT '5'           AS doctor_id,
             'Neurologist' AS category_doctor,
             'Athens'      AS city,
             'drStrange'   AS first_name,
             'mrDoctor'    AS last_name,
             null          AS rank,
             '2'           AS patient_role_id_fk) AS tmp
WHERE NOT EXISTS(SELECT doctor_id FROM doctor WHERE doctor_id = '5') LIMIT 1;

INSERT INTO dates_doctor_available (date_id, date_available, time_available, doctor_account_doctor_id)
SELECT *
FROM (SELECT '0'                                       AS date_id,
             DATE_ADD(CURRENT_DATE(), INTERVAL 10 DAY) AS date_available,
             '09:00'                                   AS time_available,
             '5'                                       AS doctor_account_doctor_id) AS tmp
WHERE NOT EXISTS(SELECT date_id FROM dates_doctor_available WHERE date_id = '0') LIMIT 1;

INSERT INTO dates_doctor_available (date_id, date_available, time_available, doctor_account_doctor_id)
SELECT *
FROM (SELECT '1'                                       AS date_id,
             DATE_ADD(CURRENT_DATE(), INTERVAL 10 DAY) AS date_available,
             '12:00'                                   AS time_available,
             '5'                                       AS doctor_account_doctor_id) AS tmp
WHERE NOT EXISTS(SELECT date_id FROM dates_doctor_available WHERE date_id = '1') LIMIT 1;

INSERT INTO dates_doctor_available (date_id, date_available, time_available, doctor_account_doctor_id)
SELECT *
FROM (SELECT '2'                                      AS date_id,
             DATE_ADD(CURRENT_DATE(), INTERVAL 5 DAY) AS date_available,
             '09:00'                                  AS time_available,
             '5'                                      AS doctor_account_doctor_id) AS tmp
WHERE NOT EXISTS(SELECT date_id FROM dates_doctor_available WHERE date_id = '2') LIMIT 1;

---DOCTOR 2----
INSERT INTO medication_account (medication_account_id, account_number, total_medications_over_year, patient_patient_id)
SELECT *
FROM (SELECT 2    AS medication_account_id,
             3    AS account_number,
             0    AS total_medications_over_year,
             null AS patient_patient_id) AS tmp
WHERE NOT EXISTS(SELECT medication_account_id FROM medication_account WHERE medication_account_id = '2') LIMIT 1;


-- Template Doctor for testing the app Live on Development Server
-- username: psalidoxeris password: root
INSERT INTO patient (patient_id, city, date_of_birth, doctor_is, email, enabled, first_name, last_name, password, phone,
                     street_address, username, medication_account_fk)
SELECT *
FROM (SELECT '6'                                                            AS patient_id,
             'Crete'                                                        AS city,
             '2000-01-01'                                                   AS date_of_birth,
             1                                                              AS doctor_is,
             'psalidoxeris@psalidoxeris.com'                                AS email,
             1                                                              AS enabled,
             'psalidoxeris'                                                 AS first_name,
             'MrHandyCrop'                                                  AS last_name,
             '$2a$12$s8Frwx3TBjuiZuIo0RNNW./vMBRwxw3edNOgOZ4ZLxgFrO923lG.K' AS password,
             '0000000000'                                                   AS phone,
             NULL                                                           AS street_address,
             'psalidoxeris'                                                 AS username,
             '2'                                                            AS medication_account_fk) AS tmp
WHERE NOT EXISTS(SELECT patient_id FROM patient WHERE patient_id = '6') LIMIT 1;

UPDATE medication_account
SET patient_patient_id = 6
WHERE medication_account_id = 2;

INSERT INTO patient_role (patient_role_id, patient_id, role_id)
SELECT *
FROM (SELECT '3' AS patient_role_id, '6' AS patient_id, '1' AS role_id) AS tmp
WHERE NOT EXISTS(SELECT patient_id FROM patient_role WHERE patient_id = '6') LIMIT 1;

INSERT INTO doctor (doctor_id, category_doctor, city, first_name, last_name, rank, patient_role_id_fk)
SELECT *
FROM (SELECT '6'            AS doctor_id,
             'Pshyco'       AS category_doctor,
             'Crete'        AS city,
             'Psalidoxeris' AS first_name,
             'MrHandyCrop'  AS last_name,
             null           AS rank,
             '3'            AS patient_role_id_fk) AS tmp
WHERE NOT EXISTS(SELECT doctor_id FROM doctor WHERE doctor_id = '6') LIMIT 1;

INSERT INTO dates_doctor_available (date_id, date_available, time_available, doctor_account_doctor_id)
SELECT *
FROM (SELECT '3'                                       AS date_id,
             DATE_ADD(CURRENT_DATE(), INTERVAL 10 DAY) AS date_available,
             '09:00'                                   AS time_available,
             '6'                                       AS doctor_account_doctor_id) AS tmp
WHERE NOT EXISTS(SELECT date_id FROM dates_doctor_available WHERE date_id = '3') LIMIT 1;

INSERT INTO dates_doctor_available (date_id, date_available, time_available, doctor_account_doctor_id)
SELECT *
FROM (SELECT '4'                                       AS date_id,
             DATE_ADD(CURRENT_DATE(), INTERVAL 10 DAY) AS date_available,
             '12:00'                                   AS time_available,
             '6'                                       AS doctor_account_doctor_id) AS tmp
WHERE NOT EXISTS(SELECT date_id FROM dates_doctor_available WHERE date_id = '4') LIMIT 1;

INSERT INTO dates_doctor_available (date_id, date_available, time_available, doctor_account_doctor_id)
SELECT *
FROM (SELECT '5'                                      AS date_id,
             DATE_ADD(CURRENT_DATE(), INTERVAL 5 DAY) AS date_available,
             '09:00'                                  AS time_available,
             '6'                                      AS doctor_account_doctor_id) AS tmp
WHERE NOT EXISTS(SELECT date_id FROM dates_doctor_available WHERE date_id = '5') LIMIT 1;

COMMIT;
SET
autocommit = 1;
-- case sensitive? names? see img uploads + jwt + docker + create schema apo pu? + travis CI
-- thymeleaf message box popup apo plugin cdn prwta OK meta sto controller stelne message mesa ap to Template HTML
