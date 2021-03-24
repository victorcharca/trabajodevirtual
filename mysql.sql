

CREATE DATABASE proyectomedicoventas;

USE proyectomedicoventas;

/*Table structure for table `table_cliente` */


CREATE TABLE table_cliente (
  idCliente int(11) NOT NULL,
  Nombre_Cliente varchar(45) NOT NULL,
  Apellido_Cliente varchar(45) NOT NULL,
  Cedula_Cliente decimal(10,0) NOT NULL,
  PRIMARY KEY (idCliente),
  UNIQUE KEY `idCliente_UNIQUE` (`idCliente`)
);

/*Data for the table `table_cliente` */

insert  into table_cliente(idCliente,Nombre_Cliente,Apellido_Cliente,Cedula_Cliente) values (1,'Jorge','Mendoza','2367'),(2,'Lorena','Palco','7892'),(3,'Lopez','Martinez','4571'),(4,'Rosa','Palacios','1489');

/*Table structure for table `table_facturas` */

CREATE TABLE table_facturas (
  No_Facturas int(11) NOT NULL,
  cliente int(11) NOT NULL,
  fecha date NOT NULL,
  vendedor int(11) NOT NULL,
  totals int(11) NOT NULL,
  
  FOREIGN KEY (cliente) REFERENCES table_cliente (idCliente),
  FOREIGN KEY (vendedor) REFERENCES table_vendedor (idVendedor),
  PRIMARY KEY (No_Facturas)
  
  UNIQUE KEY `No_Facturas_UNIQUE` (`No_Facturas`),
  KEY `llavesForaneas_Vendedor_idx` (`vendedor`),
  KEY `llavesForaneas_Cliente_idx` (`cliente`),

);
CREATE INDEX cliente_1 ON table_facturas (cliente);
CREATE INDEX vendedor_1 ON table_facturas (vendedor);

/*Data for the table `table_facturas` */

/*Table structure for table `table_productos` */


CREATE TABLE table_productos (
  idProductos varchar(45) NOT NULL,
  nombreProductos varchar(45) NOT NULL,
  preciosProductos int(11) NOT NULL,
  PRIMARY KEY (idProductos),
  UNIQUE KEY `idtProductos_UNIQUE` (`idProductos`)
);

/*Data for the table `table_productos` */

insert  into table_productos(idProductos,nombreProductos,preciosProductos) values ('1','Impresora Laser',800),('2','Equipo medición Oxigeno',1000),('3','Tensiometro',340),('4','Rayos x',2000),('6','Linterna',12);

/*Table structure for table `table_vendedor` */


CREATE TABLE table_vendedor (
  idVendedor int(11) NOT NULL,
  nombreVendedor varchar(55) NOT NULL,
  PRIMARY KEY (idVendedor),
  UNIQUE KEY `idVendedor_UNIQUE` (`idVendedor`)
);

/*Data for the table `table_vendedor` */

insert  into table_vendedor(idVendedor,nombreVendedor) values (1,'Romero Luis'),(2,'Maria Perez'),(3,'Maria Luisa Elsa');

/*Table structure for table `table_ventas` */


CREATE TABLE table_ventas (
  idVentas int(11) NOT NULL,
  No_Facturas int(11) NOT NULL,
  Productos varchar(45) NOT NULL,
  cantidad int(11) NOT NULL,
  importe int(11) NOT NULL,
  
  FOREIGN KEY (No_Facturas) REFERENCES table_facturas (No_Facturas),
  FOREIGN KEY (Productos) REFERENCES table_productos (idProductos),
  PRIMARY KEY (idVentas)
  
  UNIQUE KEY `idVentas_UNIQUE` (`idVentas`),
  KEY `llavesForaneas_Facturas_idx` (`No_Facturas`),
  KEY `llavesForaneas_Productos_idx` (`Productos`),

);

