#drop database app_canchafacil;
create database if not exists app_canchafacil;
use app_canchafacil;
#SELECT NOW() AS fecha_hora_actual;
#SELECT CURRENT_TIMESTAMP AS fecha_hora_actual;
#SELECT sysdate() AS fecha_hora_actual;
#SELECT @@session.time_zone;
SET lc_time_names = 'es_ES'; #CAMBIAR A ESPAÑOL EL IDIOMA

create table Cliente(
Dni_Cli char(8) primary key,
Nomb_Cli varchar(20) not null,
Ape_Cli varchar(20) not null,
Correo_Cli varchar(30) unique not null,
Contra_Cli varchar(60) not null,
Cel_Cli varchar(15) unique not null,
Fecha_registro datetime default current_timestamp
);

insert into cliente (dni_cli, nomb_cli, ape_cli, correo_cli, contra_cli, cel_cli) values
('72673554', 'Milhos', 'Sihuay', 'milhos@gmail.com', '$2a$10$iaG6KXDK2RuTt5KEciOON.WF/KYHkgOGmP7zO.YARdpgREp0TUlqG', '997653086' ), #123
('70829460', 'Luiggi', 'Rebatta', 'luiggi@gmail.com', '$2a$10$Sxsf9v9K1njQI4bFtKdQUOACUJ3AmKc6eg1kUe0ABTi7X.3PsT1RW', '969599087' ), #123
('12345677', 'Marcelo', 'Yabar', 'marcelo@gmail.com', '$2a$10$Ed9u0YSX4MDvqISy5ueHHOAkF79I5XGK1SBz5QB1haGDA8NQlvsZ6', '986389628' ), #123
('72647015', 'Michell', 'Del Pino', 'michell@gmail.com', '$2a$10$3NeDSeeUSdqwYR39pnCC9.TkMGbMWFbjWNVbXS/nSbCqa7EB9i/Xu', '913428693'); #123


create procedure sp_ListarCLI()#--------
select * from Cliente;


create procedure sp_InsertarCLI(#--------
Dni char(8),
Nombre varchar(20),
Apellido varchar(20) ,
Correo varchar(30),
Contrasena varchar(60),
Celular varchar(10)
) insert into Cliente (dni_cli, nomb_cli, ape_cli, correo_cli, contra_cli, cel_cli)
values (Dni,Nombre,Apellido,Correo,Contrasena,Celular);

create procedure sp_EliminarCLI( Dni char(8) )
delete from Cliente where Dni_Cli=Dni;

create procedure sp_ConsultarCLI(Correo varchar(30),Pass varchar(60))
select * from Cliente where Correo_Cli = Correo and Contra_Cli = Pass;

create procedure sp_EditarPassCLI(Dni char(8) , Contra varchar(60))
update Cliente set Contra_Cli=Contra where Dni_Cli=Dni;

create procedure sp_ConsultarDniCLI(Dni char(8))
select * from Cliente where Dni_Cli=Dni;

create procedure sp_ConsultarCorreoCLI(Correo char(20))
select * from Cliente where Correo_Cli=Correo;

create procedure sp_ConsultarCelularCLI(Celular varchar(15))
select * from Cliente where Cel_cli = celular;

create procedure sp_EditarDatosCLI(#-----------------------
Dni char(8) ,
Correo varchar(53),
Celular varchar(15))
update Cliente set Correo_cli=correo , cel_cli=celular where Dni_Cli=Dni;

#-------------------------ADMIN--------
create table Admin(
Dni_Adm char(8) primary key,
Nomb_Adm varchar(20) not null,
Ape_Adm varchar(20) not null,
Correo_Adm varchar(30) unique not null,
Contra_Adm varchar(60) not null,
Cel_Admin varchar(15) unique not null
);

insert into admin values
('72673554', 'Milhos', 'Sihuay', 'mi_adm@g.com', '$2a$10$5MqF4e5ag7JMFW0FjufjTuPv4smMPjL63y57lmazNVTYDC38DvN2K', '997653086' ), #tiff
('60766704', 'Keyra', 'Sihuay', 'ke_adm@gmail.com', '$2a$10$uexQP1A5y7tyxDFXa1Z8JuZiP2NNSwSvD.r92Lqujjp/2Nn9jPb2y', '976185877' ), #5284Ke67391
('70829460', 'Luiggi', 'Rebatta', 'lu_adm@g.com', '$2a$10$eEl8TUFEMfRhNnr8L58ZwelazBOyn1ugI6O5yKM.9gaBNMmiL2rVm', '969599087' ); #321

create procedure sp_ConsultarADM(
Correo varchar(30),
Pass varchar(60))
select * from Admin where Correo_Adm = Correo and Contra_Adm = Pass;

create procedure sp_ConsultarDniADM(Dni char(9))
select * from Admin where Dni_Adm=Dni;

create procedure sp_ConsultarCorreoADM(Correo char(30))
select * from Admin where Correo_Adm=Correo;

#------------TABLA LOSA------------
create table tb_losa(
id int  auto_increment primary key,
nombre_losa varchar(40) not null,
descripcion varchar(80),
horario varchar(50) not null,
direccion varchar (80) not null,
mantenimiento boolean default 0,
precio_hora decimal(10,2) default 0.0,
nombre_tabla varchar(20) not null unique
);


insert into tb_losa (precio_hora,nombre_losa, horario, direccion,nombre_tabla) values
(10.5,'La Bombonerita','L-D', 'Av. Eduardo de Habich, San Martín de Porres 15102','reserva_losa1'),
(11.5,'La Bombonera','L-D', 'Jr. Riobamba 601, San Martín de Porres 15101' ,'reserva_losa2'),
(12.5,'Estadio La 70','L-D', 'San Martín de Porres 15107','reserva_losa3'),
(13.5,'Campo deportivo 27 de noviembre','L-D', 'Av. 27 de Noviembre, San Martín de Porres 15106','reserva_losa4');


create procedure sp_EditarLosas(#-----------------------
Id_losa char(8) ,
mante boolean,
precio decimal)
update tb_losa set mantenimiento=mante, precio_hora=precio where id=Id_losa;


#-----------TABLA PAGO----------------------

CREATE TABLE tb_pago (
    idPago INT AUTO_INCREMENT PRIMARY KEY, -- Clave primaria auto-incrementable
	fechaPago datetime DEFAULT current_timestamp,           -- Fecha y hora del pago
    codPago VARCHAR(50) NOT NULL UNIQUE,       -- Código único del pago
    fecha_reserva VARCHAR(20) not null,
    hora_reserva varchar(10) not null,
    montoTotal DECIMAL(10, 2) NOT NULL,    -- Monto total del pago, con 2 decimales
    igvPago DECIMAL(10, 2) NOT NULL  , -- Monto del IGV asociado al pago
    medio_pago VARCHAR(20) default NULL
);
#drop table tb_pago;

DELIMITER $$
CREATE PROCEDURE sp_ConsultarPago(
    IN FECHA VARCHAR(20),
    IN HORA VARCHAR(10)
)
BEGIN
    SELECT * 
    FROM tb_pago
    WHERE fecha_reserva = FECHA and hora_reserva = HORA;
END $$
DELIMITER ;


#------------TABLA RESERVAS------------

create table reserva_losa1(
id int  auto_increment primary key,
id_losa int,
fecha_rsv char(10) unique, #'2023-01-01'
3pm char(8),
5pm char(8),
7pm char(8),
foreign key(id_losa) references tb_losa(id)
);

create table reserva_losa2(
id int  auto_increment primary key,
id_losa int,
fecha_rsv char(10) unique, #'2023-01-01'
3pm char(8),
5pm char(8),
7pm char(8),
foreign key(id_losa) references tb_losa(id)
);


create table reserva_losa3(
id int  auto_increment primary key,
id_losa int,
fecha_rsv char(10) unique, #'2023-01-01'
3pm char(8),
5pm char(8),
7pm char(8),
foreign key(id_losa) references tb_losa(id)
);


create table reserva_losa4(
id int  auto_increment primary key,
id_losa int,
fecha_rsv char(10) unique, #'2023-01-01'
3pm char(8),
5pm char(8),
7pm char(8),
id_pago int,
foreign key(id_losa) references tb_losa(id)
);

#----SP TABLA RESERVA--------
DELIMITER //
CREATE PROCEDURE LLenarTablaReservas(in id_cancha int)
BEGIN
    DECLARE fecha_inicio DATE;
    DECLARE fecha_fin DATE;
    DECLARE fecha_actual DATE;

    SET fecha_inicio = CONCAT(YEAR(CURDATE()), '-01-01');
    SET fecha_fin = CONCAT(YEAR(CURDATE()), '-12-31');
    SET fecha_actual = fecha_inicio;

    WHILE fecha_actual <= fecha_fin DO
		CASE id_cancha
			when 1 then
				INSERT INTO reserva_losa1 (fecha_rsv, id_losa)  VALUES (fecha_actual,id_cancha);
			when 2 then
				INSERT INTO reserva_losa2 (fecha_rsv, id_losa) VALUES (fecha_actual,id_cancha);
			when 3 then
                INSERT INTO reserva_losa3 (fecha_rsv, id_losa)  VALUES (fecha_actual,id_cancha);
			when 4 then
                INSERT INTO reserva_losa4 (fecha_rsv, id_losa)  VALUES (fecha_actual,id_cancha);
		END CASE;
        SET fecha_actual = DATE_ADD(fecha_actual, INTERVAL 1 DAY);
    END WHILE;

    SELECT 'Tablas llenadas correctamente.' AS mensaje;
END //
DELIMITER ;

call LLenarTablaReservas(1);
call LLenarTablaReservas(2);
call LLenarTablaReservas(3);
call LLenarTablaReservas(4);

###		LISTAR RESERVAS SEMANALES O EN UN RANGO DETERMINADO ###
DELIMITER //
CREATE PROCEDURE sp_ListarRsv(IN tabla varchar(50), in dia_min int, in dia_max int)
BEGIN
	SET @query = CONCAT('SELECT * FROM ',tabla, ' WHERE id between ', dia_min, ' and ', dia_max);
	PREPARE stmt FROM @query;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
END //
DELIMITER ;


### ------------ REALIZAR UNA RESERVA e INSERCCION DE PAGO ### -> CLIENTE COMPRA
DELIMITER //
CREATE PROCEDURE sp_RESERVAR2(IN tabla varchar(50), IN dia CHAR(10),IN hora char(5), IN dni_user CHAR(8) )
BEGIN
	DECLARE mensaje varchar(255);
    -- Crear la consulta dinámica
    SET @query =
		CONCAT(
        'UPDATE ', tabla, ' SET ', hora, ' = ',
        IF(dni_user IS NULL, 'NULL', CONCAT('\'', dni_user, '\'')),
        ' WHERE fecha_rsv = \'', dia, '\''
    );
	PREPARE stmt FROM @query;
	EXECUTE stmt;
    SELECT ROW_COUNT() AS filas_afectadas;
	DEALLOCATE PREPARE stmt;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE sp_RESERVAR(
    IN tabla VARCHAR(50), 
    IN dia CHAR(10), 
    IN hora CHAR(5), 
    IN dni_user CHAR(8), 
    IN montoTotal DECIMAL(10, 2), -- Nuevo parámetro para el monto total
    IN estado VARCHAR(20), -- Nuevo parámetro para el estado
    IN medioPago VARCHAR(20)
)
BEGIN
    DECLARE mensaje VARCHAR(255);
    DECLARE igvPago DECIMAL(10, 2);
    DECLARE nuevoCodigo VARCHAR(20);
    DECLARE ultimoCodigo VARCHAR(20) default NULL;
    DECLARE numero INT;
    DECLARE filas_afectadas_update INT;
    DECLARE filas_afectadas_insert INT default 0;

    -- Calcular el IGV (18% del monto total)
    SET igvPago = montoTotal * 0.18;

    -- Crear la consulta dinámica para actualizar la reserva
    SET @query = CONCAT(
        'UPDATE ', tabla, 
        ' SET ', hora, ' = ', 
        IF(dni_user IS NULL, 'NULL', CONCAT('\'', dni_user, '\'')), 
        ' WHERE fecha_rsv = \'', dia, '\''
    );
    
    -- Ejecutar la actualización dinámica
    PREPARE stmt FROM @query;
    EXECUTE stmt;
    
    -- Obtener el número de filas afectadas por el UPDATE
    SET filas_afectadas_update = ROW_COUNT();
    DEALLOCATE PREPARE stmt;
    
    -- Solo insertar en tb_pago si el estado es 'aprobado'
    IF estado = 'aprobado' THEN
    
		-- Obtener el último código de pago insertado
		SELECT codPago INTO ultimoCodigo
		FROM tb_pago
		ORDER BY codPago DESC
		LIMIT 1;
        
		-- Si no hay códigos previos, iniciar con 'COD001'
		IF ultimoCodigo IS NULL THEN
			SET nuevoCodigo = 'COD001';
		ELSE
			-- Extraer la parte numérica del último código de pago (eliminando el 'COD')
			SET numero = CAST(SUBSTRING(ultimoCodigo, 4) AS UNSIGNED);
			SET numero = numero + 1;  -- Incrementar el número
			-- Generar el nuevo código de pago con formato 'COD' seguido del número con 3 dígitos
			SET nuevoCodigo = CONCAT('COD', LPAD(numero, 3, '0'));
		END IF;
    
        -- Insertar una fila en la tabla de pagos
        INSERT INTO tb_pago (codPago, fecha_reserva, hora_reserva, montoTotal, igvPago,medio_pago)
        VALUES (nuevoCodigo, dia, hora, montoTotal, igvPago,medioPago);
        
	 -- Obtener el número de filas afectadas por el INSERT
        SET filas_afectadas_insert = ROW_COUNT();
    END IF;

    -- Devolver los resultados de las filas afectadas
    SELECT filas_afectadas_update AS filas_afectadas_update, 
           filas_afectadas_insert AS filas_afectadas_insert;
    
END //

DELIMITER ;


#--------------------## LISTAR RESERVAS INDIVIDUAL POR CLIENTE ###
DELIMITER //
CREATE PROCEDURE sp_ConsultarRsvCLI(IN tabla VARCHAR(30),IN dni char(8) )
BEGIN
    SET @sql = CONCAT('SELECT * FROM ', tabla, ' WHERE 3pm = ', dni ,' OR 5pm= ',dni,' OR 7pm = ',dni);
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END //
DELIMITER ;

### LISTAR TODAS LAS RESERVAS COMPRADAS ### -> PARA EL ADMINISTRADOR
DELIMITER //
CREATE PROCEDURE sp_ListarReservasCLI(IN tabla VARCHAR(30))
BEGIN
    SET @sql = CONCAT('SELECT *
                       FROM ', tabla, '
                       WHERE
                       3pm IS NOT NULL OR
                       5pm IS NOT NULL OR
                       7pm IS NOT NULL');
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END //
DELIMITER ;


SELECT 'FINISH' AS mensaje;
##############<----------------->###############


