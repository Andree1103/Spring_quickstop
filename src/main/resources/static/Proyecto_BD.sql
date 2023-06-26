-- borra la bd si existe
DROP DATABASE IF EXISTS quickstop;
-- creamos la bd
CREATE DATABASE quickstop;
-- activamos la bd
USE quickstop;
--
--
/**************************INICIO DE TABLAS DE CATEGORIA**************************/
create table tb_categorias(
	id int auto_increment primary key,
	nombre varchar(100),
    descripcion varchar(200)
);
--
insert into tb_categorias values(1,'Bebidas','Bebidas de diversos sabores');
insert into tb_categorias values(2,'Snacks','Snacks de diversos sabores y gustos');

/**************************FIN DE TABLAS DE CATEGORIA**************************/
--
--
/**************************INICIO DE TABLAS DE MARCAS**************************/
create table tb_marcas(
	id int auto_increment primary key,
	nombre varchar(100),
	descripcion varchar(200)
);
--
insert into tb_marcas values(null,'COCA COLA COMPANY','Marca de procedencia extranjera');
insert into tb_marcas values(null,'PEPSI CORPORATION','Marca de procedencia extranjera');
insert into tb_marcas values(null,'KARINTO','Empresa con 20 años de experiencia');
insert into tb_marcas values(null,'FRITOLAY','Empresa con 10 años de experiencia');

/**************************FIN DE TABLAS DE MARCAS**************************/
--
--
/**************************INICIO DE TABLAS DE PRODUCTOS**************************/
create table tb_productos(
	id int auto_increment primary key,
	nombre varchar(80),
    descripcion varchar(200),
    imagen varchar(255) null,
	precio double,
    stock int,
    estado tinyint,
    id_categoria int references tb_categorias(id),
    id_marca int references tb_marcas(id)
);
--
insert into tb_productos values(null,'Coca Cola', 'Bebida Gasificada','Productos.jpg',null, 5.50, 50, 1, 1,1);
insert into tb_productos values(null,'Mani Crocante', 'Mani cocido y frito por dentro','Manicrocante.jpg',null, 2.50,50, 1, 2, 3);
insert into tb_productos values(null, 'Triangulo Donofrio', 'Barra de chocolate', 'Triangulodonofrio.jpg', '2.5', '20', '1', '2', '4');

/**************************FIN DE TABLAS DE PRODUCTOS**************************/
--
--
/**************************INICIO DE TABLAS DE TIPO DE USUARIO**************************/
create table tb_tipo_de_usuario(
	id int auto_increment primary key,
	nombre varchar(100)
);
insert into tb_tipo_de_usuario values(null,'ADMIN'), (null,'CLIENTE');
/**************************FIN DE TABLAS DE TIPO DE USUARIO*********************/
--
--
/**************************INICIO DE TABLAS DE ROLES*********************/
create table tb_roles(
	id int auto_increment primary key,
	nombre varchar(100),
    descripcion varchar(200)
);
insert into tb_roles values (null,'ADMIN', 'Encargado de todos los procesos'),(null,'CLIENTE', 'Accede a partes parciales');
/**************************FIN DE TABLAS DE ROLES*********************/
--
--
/**************************INICIO DE TABLAS DE URLs*********************/
create table tb_url(
	id int auto_increment primary key,
    descripcion varchar(80),
    ruta varchar(80)
);

insert into tb_url values (1,"Productos","/Productos/Crud_Productos");
insert into tb_url values (2,"Usuarios","/Usuarios/Crud_Usuarios");
insert into tb_url values (3,"Marcas y Categorias","/CategoriasMarcas/CrudMarcas_Categoria");
insert into tb_url values (4,"Clientes","/Clientes/Crud_Clientes");
insert into tb_url values (5,"Pedidos","/Pedidos/Venta_Pedido");
/**************************FIN DE TABLAS DE URLs*********************/
--
--
/**************************INICIO DE TABLAS DE ROL DE URLs*********************/
create table tb_rol_url (
  id int not null,
  idenlace_url int not null,
  PRIMARY KEY (id,idenlace_url), KEY fk25 (idenlace_url),
  CONSTRAINT fk24 FOREIGN KEY (id) REFERENCES tb_roles (id),
  CONSTRAINT fk25 FOREIGN KEY (idenlace_url) REFERENCES tb_url (id)
);

-- ADMIN DE LA EMPRESA (TIENE ACCESO A TODO EL PROYECTO)
insert into tb_rol_url values (1,1);
insert into tb_rol_url values (1,2);
insert into tb_rol_url values (1,3);
insert into tb_rol_url values (1,4);
insert into tb_rol_url values (1,5);
-- CLIENTE NORMAL (SOLO TIENE ACCESO A LA VENTA)
insert into tb_rol_url values (2,5);
/**************************FIN DE TABLAS DE ROL DE URLs*********************/
--
--
/***************************INICIO DE TABLAS DE DISTRITOS*************************/
create table tb_distritos(
	id int auto_increment primary key,
	nombre_dis varchar(80)
);
insert into tb_distritos values(null,'VILLA EL SALVADOR');
INSERT INTO tb_distritos VALUES (null, 'BARRANCO');
insert into tb_distritos values(null,'CERCADO DE LIMA');
insert into tb_distritos values(null,'LA MOLINA');
INSERT INTO tb_distritos VALUES (null, 'LIMA');
INSERT INTO tb_distritos VALUES (null, 'MIRAFLORES');
insert into tb_distritos values(null,'SURCO');
insert into tb_distritos values(null,'SAN JUAN DE MIRAFLORES');
insert into tb_distritos values(null,'SAN JUAN DE LURIGANCHO');
insert into tb_distritos values(null,'SAN ISIDRO');
insert into tb_distritos values(null,'SAN LUIS');
insert into tb_distritos values(null,'SAN MIGUEL');
insert into tb_distritos values(null,'SAN BORJA');
insert into tb_distritos values(null,'RIMAC');
INSERT INTO tb_distritos VALUES (null, 'SAN BORJA');

/****************************FIN DE TABLAS DE DISTRITOS***********************/
--
--
/****************************INICIO DE TABLAS DE USUARIOS***********************/
create table tb_usuarios(
	id int auto_increment primary key,
	nombre varchar(80),
	apellido varchar(80),
	usser varchar(255),
	contrasenia varchar(255),
	celular varchar(9),
    dni varchar(8),
	direccion varchar(150),
	fecha_nac_usuario datetime,
	estado tinyint,
	id_tipo_usuario int references tb_tipo_usuario(id),
	id_rol int references tb_roles(id),
	id_distrito int references tb_distritos(id)
);
--  usser andre123  Contraseña mysql

insert into tb_usuarios values (null,'Andree','Andres Suazo','andre123','$2a$12$0PriLGj3XL/XbeB3xpgx3uwsQTqEA7XIJo4jU5f9RaO2.I6siMNwS','922147474','70104410','Jr. Las flores','2000-08-25',1,1,1,1);
insert into tb_usuarios values (null,'Victor','Abanto Flores','victor123','$2a$12$0PriLGj3XL/XbeB3xpgx3uwsQTqEA7XIJo4jU5f9RaO2.I6siMNwS','921414147','73102547','Jr. Central','2002-08-25',1,1,1,2);
insert into tb_usuarios values (null,'Gustavo','Villavicencio','gustavo123','mysql','922748552','77524115','Jr. Santa Rosa','2000-08-25',1,1,1,3);
insert into tb_usuarios values (null,'Leonardo','Lombardi Chavez','leonardo123','mysql','912348800','75672547','Jr. Banchero','2001-01-21',1,2,2,4);

/****************************FIN DE TABLAS DE USUARIOS***********************/
--
--
/****************************INICIO DE TABLAS DE CLIENTES***********************/

create table tb_clientes(
	id int auto_increment primary key,
	nombre_cli varchar(80),
	apellido_cli varchar(80),
	usser varchar(80),
	celular_cli varchar(9),
	direccion_cli varchar(200),
	fecha_nac_cli datetime,
	estado tinyint,
	id_dis int references tb_distritos(id)
);
--
insert into tb_clientes values (1,'Victor','Abanto','victor123','955874574','Jr. Central 123','2004-02-15',1,1);
/****************************FIN DE TABLAS DE CLIENTES***********************/

--
--
/****************************INICIO DE TABLAS DE VENTA***********************/
create table tb_ventas(
	id int auto_increment primary key,
    fecha_pedido datetime,
	fecha_entrega datetime,
	precio_envio double,
    estado tinyint,
    suma_total double,
    id_usu int references tb_usuario(id),
	id_cli int references tb_cliente(id)
);
--
/*insert into tb_ventas values(null,'2023/08/06','2022/08/26',3.00,1,1,1);
--
/****************************FIN DE TABLAS DE VENTA***********************/
--
--
/*************************** INICIO DE TABLAS DE DETALLE DE LA VENTA***********************/
create table tb_detalle_ventas(
	id int auto_increment primary key,
    cantidad int,
	precio_unidad double,
    total double,
    id_ventas int default 1 references tb_ventas(id)  ,
	id_pro int references tb_producto(id)
);

/*insert into tb_detalle_ventas values(null,5,2.50,1,1);
/****************************FIN DE TABLAS DE DETALLE DE LA VENTA***********************/

select * from tb_url;
select * from  tb_usuarios;
select * from  tb_roles;
select * from  tb_tipo_de_usuario;
select * from  tb_productos;
select * from tb_ventas;
select * from  tb_detalle_ventas;
select * from tb_categorias;

select * from  tb_clientes;
select * from  tb_ventas;

SELECT v.id AS cod_venta, c.nombre_cli, p.nombre AS nomproductos, dv.cantidad, dv.precio_unidad, dv.total, v.suma_total
FROM tb_ventas v
INNER JOIN tb_clientes c ON v.id_cli = c.id
INNER JOIN tb_detalle_ventas dv ON v.id = dv.id_ventas
INNER JOIN tb_productos p ON dv.id_pro = p.id
WHERE v.id = 3



