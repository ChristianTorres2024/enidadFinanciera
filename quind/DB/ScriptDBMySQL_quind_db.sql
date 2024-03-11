SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema quind_db
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS quind_db ;
CREATE SCHEMA IF NOT EXISTS quind_db DEFAULT CHARACTER SET utf8mb4 ;

-- Cliente  --------------------------------------------
-- -----------------------------------------------------
USE quind_db ;
DROP TABLE IF EXISTS quind_db.identification_type ;
CREATE TABLE IF NOT EXISTS identification_type (
  id INT NOT NULL,
  type_name VARCHAR(50) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT unique_type_name UNIQUE (type_name)
) ENGINE=InnoDB;


INSERT INTO identification_type (id,type_name) VALUES
(1,'CC'),
(2,'Passport'),
(3,'Driver License'),
(4,'Social Security Number'),
(5,'ID Card');

DROP TABLE IF EXISTS quind_db.client ;
CREATE TABLE IF NOT EXISTS client (
  id INT NOT NULL AUTO_INCREMENT,
  id_type INT NOT NULL,
  id_number BIGINT NOT NULL,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  email_address VARCHAR(80) NOT NULL,
  date_of_birth DATE NOT NULL,
  date_of_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modification_date TIMESTAMP NULL,
  PRIMARY KEY (id),
  CONSTRAINT unique_client_id UNIQUE (id),
  UNIQUE INDEX id_UNIQUE (id_number ASC, id_type ASC),
  CONSTRAINT fk_client_id_type
    FOREIGN KEY (id_type)
    REFERENCES identification_type(id)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT
) ENGINE=InnoDB;



-- Productos -------------------------------------------
-- -----------------------------------------------------
USE quind_db ;
DROP TABLE IF EXISTS quind_db.account_type;

CREATE TABLE IF NOT EXISTS account_type (
  id INT NOT NULL,
  type_name VARCHAR(20) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT unique_type_name UNIQUE (type_name)
) ENGINE=InnoDB;

INSERT INTO account_type (id,type_name) VALUES
(1,'Corriente'),
(2,'Ahorros');

USE quind_db ;
DROP TABLE IF EXISTS quind_db.status;

CREATE TABLE IF NOT EXISTS status (
  id INT NOT NULL ,
  status_name VARCHAR(10) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT unique_status_name UNIQUE (status_name)
) ENGINE=InnoDB;

INSERT INTO status (id,status_name) VALUES
(1,'Activa'),
(2,'Inactiva'),
(3,'Cancelada');

USE quind_db ;
DROP TABLE IF EXISTS quind_db.products;

CREATE TABLE IF NOT EXISTS products (
  id INT NOT NULL AUTO_INCREMENT,
  account_type_id INT NOT NULL,
  account_number BIGINT UNSIGNED,
  status_id INT NOT NULL,
  balance DECIMAL(10,2) NOT NULL,
  GMF_exempt VARCHAR(3) NOT NULL CHECK (GMF_exempt IN ('si', 'no')),
  creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modification_date TIMESTAMP NULL,
  user_account_owner INT NOT NULL,
  PRIMARY KEY (id),
  INDEX fk_products_client_idx (user_account_owner ASC) VISIBLE,
  CONSTRAINT unique_client_id UNIQUE (id),
  CONSTRAINT unique_account_id UNIQUE (account_number),
  CONSTRAINT fk_products_client
    FOREIGN KEY (user_account_owner)
    REFERENCES client (id)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT fk_products_account_type
    FOREIGN KEY (account_type_id)
    REFERENCES account_type (id)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT fk_products_status
    FOREIGN KEY (status_id)
    REFERENCES status (id)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT
) ENGINE = InnoDB;

-- transaccion -----------------------------------------
-- -----------------------------------------------------
USE quind_db ;
DROP TABLE IF EXISTS quind_db.transaction_type;

CREATE TABLE IF NOT EXISTS transaction_type (
  id INT NOT NULL ,
  type_name VARCHAR(45) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT unique_type_name UNIQUE (type_name)
) ENGINE=InnoDB;

INSERT INTO transaction_type (id,type_name) VALUES
(1,'Consignacion'),
(2,'Retiro'),
(3,'Transferencia_entre_cuentas');

USE quind_db ;
DROP TABLE IF EXISTS quind_db.transaction;

CREATE TABLE IF NOT EXISTS transaction (
  id INT NOT NULL AUTO_INCREMENT,
  transaction_type_id INT NOT NULL,
  amount DECIMAL(10,2) NOT NULL,
  transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  description VARCHAR(100) NULL,
  successful INT NULL CHECK (successful IN (1, 0)) COMMENT '1 = True - 0 = false',
  source_account_id INT NULL,
  destination_account_id INT NULL,
  PRIMARY KEY (id),
  CONSTRAINT unique_transaction_type UNIQUE (id),
  INDEX fk_transaction_products1_idx (source_account_id ASC) VISIBLE,
  INDEX fk_transaction_products2_idx (destination_account_id ASC) VISIBLE,
  CONSTRAINT fk_transaction_products1
    FOREIGN KEY (source_account_id)
    REFERENCES products (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_transaction_products2
    FOREIGN KEY (destination_account_id)
    REFERENCES products (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_transaction_type
    FOREIGN KEY (transaction_type_id)
    REFERENCES transaction_type (id)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT
) ENGINE = InnoDB;


-- -- ----TRIGGERSS CLIENTE ------------
-- -------------------------------------

DELIMITER $$
USE quind_db $$
DROP TRIGGER IF EXISTS quind_db.NAMES_EMAIL_AGE_BEFORE_INSERT_2 $$

CREATE DEFINER = CURRENT_USER TRIGGER quind_db.NAMES_EMAIL_AGE_BEFORE_INSERT_2 BEFORE INSERT ON client FOR EACH ROW
BEGIN    
    IF LENGTH(NEW.first_name) < 2 OR NOT NEW.first_name REGEXP '^[a-zA-Z]+$' THEN
        CALL raise_error('El nombre debe contener al menos 2 caracteres y solo caracteres alfabéticos.');
    END IF;
    
    IF LENGTH(NEW.last_name) < 2 OR NOT NEW.last_name REGEXP '^[a-zA-Z]+$' THEN
        CALL raise_error('El apellido debe contener al menos 2 caracteres y solo caracteres alfabéticos.');
    END IF;
    IF NEW.email_address NOT REGEXP '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$' THEN
        CALL raise_error('El campo de correo electrónico debe ser una dirección de correo electrónico válida.');
    END IF;
    IF NEW.date_of_birth > DATE_SUB(NOW(), INTERVAL 18 YEAR) THEN
        CALL raise_error('No se puede insertar el cliente, la fecha de nacimiento indica que es menor de 18 años.');
    END IF;    
END$$


DELIMITER $$
USE quind_db$$
DROP TRIGGER IF EXISTS quind_db.NAMES_EMAIL_AGE_BEFORE_UPDATE_2 $$

CREATE DEFINER = CURRENT_USER TRIGGER quind_db.NAMES_EMAIL_AGE_BEFORE_UPDATE_2 BEFORE UPDATE ON client FOR EACH ROW
BEGIN    
    IF LENGTH(NEW.first_name) < 2 OR NOT NEW.first_name REGEXP '^[a-zA-Z]+$' THEN
        CALL raise_error('El nombre debe contener al menos 2 caracteres y solo caracteres alfabéticos.');
    END IF;
    
    IF LENGTH(NEW.last_name) < 2 OR NOT NEW.last_name REGEXP '^[a-zA-Z]+$' THEN
        CALL raise_error('El apellido debe contener al menos 2 caracteres y solo caracteres alfabéticos.');
    END IF;
    IF NEW.email_address NOT REGEXP '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$' THEN
        CALL raise_error('El campo de correo electrónico debe ser una dirección de correo electrónico válida.');
    END IF;
    
    IF NEW.date_of_birth > DATE_SUB(NOW(), INTERVAL 18 YEAR) THEN
        CALL raise_error('No se puede insertar el cliente, la fecha de nacimiento indica que es menor de 18 años.');
    END IF;
END$$

DELIMITER $$
USE quind_db$$
DROP TRIGGER IF EXISTS quind_db.DATEUPD_BEFORE_UPDATE $$
USE quind_db$$
CREATE DEFINER = CURRENT_USER TRIGGER quind_db.DATEUPD_BEFORE_UPDATE BEFORE UPDATE ON client FOR EACH ROW
BEGIN
    SET NEW.modification_date = CURRENT_TIMESTAMP;
END;$$

-- TRIGGERS PRODUCTOS -----------------
-- ------------------------------------

DELIMITER $$
USE quind_db$$
DROP TRIGGER IF EXISTS quind_db.BALANCE_BEFORE_INSERT $$

CREATE DEFINER = CURRENT_USER TRIGGER quind_db.BALANCE_BEFORE_INSERT BEFORE INSERT ON products FOR EACH ROW
BEGIN
    IF NEW.balance < 0 THEN
        CALL raise_error('El saldo inicial de la cuenta no puede ser menor que $0.');
    END IF;
END$$

DELIMITER $$
USE quind_db$$
DROP TRIGGER IF EXISTS quind_db.BALANCE_BEFORE_UPDATE $$
CREATE DEFINER = CURRENT_USER TRIGGER quind_db.BALANCE_BEFORE_UPDATE BEFORE UPDATE ON products FOR EACH ROW
BEGIN
	IF NEW.account_type_id IN (2) THEN
		IF NEW.balance < 0 THEN
			CALL raise_error('El saldo de la cuenta de Ahorros no puede ser menor que $0.');
		END IF;
	END IF;
END$$

DELIMITER $$
USE quind_db$$
DROP TRIGGER IF EXISTS quind_db.ACCOUNTTYPE_STATUS_BEFORE_INSERT_1 $$

CREATE DEFINER = CURRENT_USER TRIGGER quind_db.ACCOUNTTYPE_STATUS_BEFORE_INSERT_1 BEFORE INSERT ON products FOR EACH ROW
BEGIN
    IF NEW.account_type_id IN (2) THEN
        IF NEW.status_id NOT IN (1) THEN
            CALL raise_error('El estado de la cuenta debe ser "Activa" para cuentas  de ahorros.');
        END IF;
    END IF;  
    
    IF NEW.status_id IN (3) THEN
            CALL raise_error('El estado de la cuenta debe ser "Activa" o "Inactiva", para CREAR cuentas  de ahorros o corrientes.');

    END IF; 
END$$


DELIMITER $$
USE quind_db$$
DROP TRIGGER IF EXISTS quind_db.GENERATE_ACCOUNTNUMBER_BEFORE_INSERT_2 $$

CREATE DEFINER = CURRENT_USER TRIGGER quind_db.GENERATE_ACCOUNTNUMBER_BEFORE_INSERT_2 BEFORE INSERT ON products FOR EACH ROW
BEGIN
	IF NEW.account_type_id = 2 THEN
        SET NEW.account_number = CONCAT('53', LPAD((SELECT COUNT(id) + 1 FROM products WHERE account_type_id = 2), 8, '0'));
    ELSEIF NEW.account_type_id = 1 THEN
        SET NEW.account_number = CONCAT('33', LPAD((SELECT COUNT(id) + 1 FROM products WHERE account_type_id = 1), 8, '0'));
    END IF;
END$$

DELIMITER $$
USE quind_db$$
DROP TRIGGER IF EXISTS quind_db.prevent_account_number_update $$

CREATE TRIGGER quind_db.prevent_account_number_type_update BEFORE UPDATE ON products
FOR EACH ROW
BEGIN
    IF OLD.account_number != NEW.account_number THEN
        CALL raise_error('No se permite actualizar el campo account_number.');
    END IF;
    
    IF OLD.account_type_id != NEW.account_type_id THEN
		 CALL raise_error('No se permite actualizar el campo account_type.');
    END IF;
END$$

DELIMITER $$
USE quind_db$$
DROP TRIGGER IF EXISTS quind_db.STATUS_BEFORE_UPDATE $$

CREATE DEFINER = CURRENT_USER TRIGGER quind_db.STATUS_BEFORE_UPDATE BEFORE UPDATE ON products FOR EACH ROW
BEGIN
		IF NEW.status_id IN (3) THEN
			IF OLD.balance != 0 THEN
				CALL raise_error('No se puede cancelar la cuenta porque el saldo no es igual a $0.');
			END IF;
        END IF;    
END$$

DELIMITER $$
USE quind_db$$
DROP TRIGGER IF EXISTS quind_db.DATEMOD_BEFORE_UPDATE $$

CREATE DEFINER = CURRENT_USER TRIGGER quind_db.DATEMOD_BEFORE_UPDATE BEFORE UPDATE ON products FOR EACH ROW
BEGIN
    SET NEW.modification_date = CURRENT_TIMESTAMP;
END;$$

-- TRIGGERS TRANSACCION ----------------
-- --------------------------------------
DELIMITER $$
USE quind_db$$
DROP TRIGGER IF EXISTS quind_db.BALANCE_AMOUNTPRODUCT_BEFORE_CREATE $$

DELIMITER $$
CREATE DEFINER = CURRENT_USER TRIGGER quind_db.BALANCE_AMOUNTPRODUCT_BEFORE_CREATE BEFORE INSERT ON quind_db.transaction FOR EACH ROW
BEGIN
    DECLARE source_balance DECIMAL(10, 2);
    DECLARE destination_balance DECIMAL(10, 2);
    DECLARE source_status INT;
    DECLARE destination_status INT;

    IF NEW.successful = 1 THEN  
        -- Obtener el estado de la cuenta de origen si existe
        IF NEW.source_account_id IS NOT NULL THEN
            SELECT status_id INTO source_status
            FROM quind_db.products
            WHERE id = NEW.source_account_id;
        END IF;

        -- Obtener el estado de la cuenta de destino si existe
        IF NEW.destination_account_id IS NOT NULL THEN
            SELECT status_id INTO destination_status
            FROM quind_db.products
            WHERE id = NEW.destination_account_id;
        END IF;

        -- Actualizar el saldo de acuerdo al tipo de transacción y verificar el estado de las cuentas
        CASE NEW.transaction_type_id
            WHEN 1 THEN
                IF destination_status != 3 THEN
                    SELECT balance INTO destination_balance
                    FROM quind_db.products
                    WHERE id = NEW.destination_account_id;

                    -- Consignación: Sumar al saldo de la cuenta de destino
                    UPDATE quind_db.products
                    SET balance = destination_balance + NEW.amount
                    WHERE id = NEW.destination_account_id;
                ELSE                   
                    CALL raise_error('1 : No se puede realizar la consignación porque la cuenta de destino está cancelada o no existe');
                END IF;
                
            WHEN 2 THEN
                IF source_status != 3 THEN
                    SELECT balance INTO source_balance
                    FROM quind_db.products
                    WHERE id = NEW.source_account_id;

                    -- Retiro: Restar del saldo de la cuenta de origen
                    UPDATE quind_db.products
                    SET balance = source_balance - NEW.amount
                    WHERE id = NEW.source_account_id;
                ELSE
                     CALL raise_error('2 : No se puede realizar la consignación porque la cuenta de Origen está cancelada o no existe');
                END IF;
                
            WHEN 3 THEN
                IF source_status != 3 AND destination_status != 3 THEN
                    SELECT balance INTO source_balance
                    FROM quind_db.products
                    WHERE id = NEW.source_account_id;

                    SELECT balance INTO destination_balance
                    FROM quind_db.products
                    WHERE id = NEW.destination_account_id;

                    -- Transferencia: Restar del saldo de la cuenta de origen y sumar al saldo de la cuenta de destino
                    UPDATE quind_db.products
                    SET balance = source_balance - NEW.amount
                    WHERE id = NEW.source_account_id;

                    UPDATE quind_db.products
                    SET balance = destination_balance + NEW.amount
                    WHERE id = NEW.destination_account_id;
                ELSE
					CALL raise_error('3 :No se puede realizar la consignación porque la cuenta de destino y/o Origen está cancelada  o no existe');                   
                END IF;
        END CASE;
    END IF;
END$$

DELIMITER $$
USE quind_db$$
DROP TRIGGER IF EXISTS quind_db.prevent_update_transaction $$

CREATE TRIGGER prevent_update_transaction
BEFORE UPDATE ON quind_db.transaction
FOR EACH ROW
BEGIN
		CALL raise_error('No se permite la actualización en la tabla transaction.');
END$$

DELIMITER ;



DELIMITER ;
-- PROCEDIMIENTO ALMACENADO ------------------------------
-- -------------------------------------------------------
DELIMITER ;
USE quind_db ;
DROP PROCEDURE IF EXISTS raise_error;

DELIMITER //
CREATE PROCEDURE raise_error(IN error_message TEXT)
BEGIN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = error_message;
END //

-- RESTORE VALUES
DELIMITER ;
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
