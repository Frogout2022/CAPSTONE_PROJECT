#drop database app_canchafacil;
create database if not exists app_canchafacil;
use app_canchafacil;
SELECT NOW() AS fecha_hora_actual;
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
('72673554', 'Milhos', 'Sihuay', 'mi@gmail.com', '$2a$10$iaG6KXDK2RuTt5KEciOON.WF/KYHkgOGmP7zO.YARdpgREp0TUlqG', '997653086' ),
('70829460', 'Luiggi', 'Rebatta', 'lu@gmail.com', '$2a$10$Sxsf9v9K1njQI4bFtKdQUOACUJ3AmKc6eg1kUe0ABTi7X.3PsT1RW', '969599087' ),
('12345677', 'Marcelo', 'Yabar', 'ma@gmail.com', '$2a$10$Ed9u0YSX4MDvqISy5ueHHOAkF79I5XGK1SBz5QB1haGDA8NQlvsZ6', '986389628' ),
('72647015', 'Michell', 'Del Pino', 'mi_dp@gmail.com', '$2a$10$3NeDSeeUSdqwYR39pnCC9.TkMGbMWFbjWNVbXS/nSbCqa7EB9i/Xu', '913428693');


create procedure sp_ListarCLI()#--------
select * from Cliente;

create procedure sp_InsertarCLI(#--------
Dni char(8) ,
Nombre varchar(20),
Apellido varchar(20) ,
Correo varchar(30) ,
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
('72673554', 'Milhos', 'Sihuay', 'mi_adm@g.com', '$2a$10$9qCHnNkbUuWqVU8C.gkX3uo4xzb4eZfHL7zyZHxTJ0Y2igkjrdAGG', '997653086' ), #321
('70829460', 'Luiggi', 'Rebatta', 'lu_adm@g.com', '$2a$10$zeTmdX5EE7wCFGAmIaGdY.2t/Pm9YB8802bnDSNu1DS9PCd6rbKF2', '969599087' ), #321
('12345677', 'Marcelo', 'Yabar', 'ma_adm@g.com', '$2a$10$oI58ZbDgq7BvmX6GOgJoRulSNGYVcnPeaMTth.jGH8oNB9iRtFdf.', '986389628' ); #321

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
precio_hora decimal default 0,
nombre_tabla varchar(20) not null unique
);


insert into tb_losa (precio_hora,nombre_losa, horario, direccion,nombre_tabla) values
(10.5,'La Bombonerita','L-D', 'Av. Eduardo de Habich, San Martín de Porres 15102','reserva_losa1'),
(20.5,'La Bombonera','L-D', 'Jr. Riobamba 601, San Martín de Porres 15101' ,'reserva_losa2'),
(30.5,'Estadio La 70','L-D', 'San Martín de Porres 15107','reserva_losa3'),
(40.5,'Campo deportivo 27 de noviembre','L-D', 'Av. 27 de Noviembre, San Martín de Porres 15106','reserva_losa4');



create procedure sp_EditarLosas(#-----------------------
Id_losa char(8) ,
mante boolean,
precio decimal)
update tb_losa set mantenimiento=mante, precio_hora=precio where id=Id_losa;


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

select * from reserva_losa3;
call sp_ListarRsv('reserva_losa3',316,321);

### REALIZAR UNA RESERVA ### -> CLIENTE COMPRA
DELIMITER //
CREATE PROCEDURE sp_RESERVAR(IN tabla varchar(50), IN dia CHAR(10),IN hora char(5), IN dni_user CHAR(8) )
BEGIN
	SET @query =
		CONCAT(
        'UPDATE ', tabla, ' SET ', hora, ' = ',
        IF(dni_user IS NULL, 'NULL', CONCAT('\'', dni_user, '\'')),
        ' WHERE fecha_rsv = \'', dia, '\''
    );
	PREPARE stmt FROM @query;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SELECT 'Reserva insertada correctamente.' AS mensaje;
END //
DELIMITER ;


### LISTAR RESERVAS INDIVIDUAL POR CLIENTE ###
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


