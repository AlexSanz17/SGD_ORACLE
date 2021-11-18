
-- Paquete 2 - Tarea 25, 26 , 27. Firma de documentos
INSERT INTO parametro(idparametro,tipo,valor,descripcion) VALUES(PARAMETRO_SEQ.nextval,'bookmarkFirma','osifirma','Bookmark para la firma de documentos de tipo .doc');

-- Visualizacion de reportes para el usuario supervisor. Se requiere crear nuevo rol, perfil, recurso
INSERT INTO perfil(idperfil,nombre,descripcion,estado) VALUES(perfil_seq.nextval,'supervisor','Supervisor','A');
INSERT INTO recurso(idrecurso,idmodulo,nombre,codigo,actionurl,descripcion,estado) VALUES(recurso_seq.nextval,1,'Mantenimiento - Reporte','MantMnuRep',null,null,'A');
INSERT INTO recursoxperfil(idrecurso,idperfil) VALUES(IDRECURSO,IDPERFIL); -- IDRECURSO es el recurso creado 'MantMnuRep'. IDPERFIL es el perfil 'supervisor'
INSERT INTO recursoxperfil(idrecurso,idperfil) VALUES(83,IDPERFIL); -- 83 es el recurso Auditoria. IDPERFIL es el perfil 'supervisor'
INSERT INTO rol(idrol,idperfil,nombre,descripcion,estado) VALUES(rol_seq.nextval,IDPERFIL,'Siged - Administrador Supervisor','Siged - Administrador Supervisor','A'); -- IDPERFIL es el perfil 'supervisor'

-- Paquete 3 - Tarea 34. Autogeneracion del numero de identificacion para el tipo OTRO en caso no se ingrese uno
CREATE SEQUENCE tipoidentificacionotro_seq MINVALUE 1 MAXVALUE 9999999 INCREMENT BY 1 START WITH 1 NOCACHE ORDER CYCLE;

-- Paquete 3. Tarea 48. Envio de correos en la derivacion con copia
INSERT INTO parametro(idparametro,tipo,valor) VALUES(parametro_seq.nextval,'MAIL_FILEPATH_DERIVACION_CONCOPIA','derivacion_concopia.txt');

-- Paquete 5. Tarea 60, 61, 62. Clasificacion de los procesos: Piloto/Produccion. Para el caso de produccion se usara un nuevo sequence
ALTER TABLE proceso ADD (
   produccion CHAR(1)
);
CREATE SEQUENCE expedienteproduccion_seq MINVALUE 1 MAXVALUE 9999999 INCREMENT BY 1 START WITH 1 NOCACHE ORDER CYCLE;
INSERT INTO parametro(idparametro,tipo,valor,descripcion) VALUES(parametro_seq.nextval,'prefijonroexpedienteproduccion','PROD201000000000','Prefijo usado para la generacion de nro de expediente en produccion');

-- Paquete 5. Tarea 64. Envio de correos y generacion de notificacion cuando un expediente es archivado
INSERT INTO parametro(idparametro,tipo,valor,descripcion) VALUES(parametro_seq.nextval,'MAIL_FILEPATH_DOCUMENTO_ARCHIVADO','documento_archivado.txt','Correo que se envia cuando se archiva un documento');
INSERT INTO parametro(idparametro,tipo,valor,descripcion) VALUES(parametro_seq.nextval,'tiponotificacion','1012','Expediente Archivado');

-- Actualizacion de rutas de mails
UPDATE parametro SET valor='alarma_amarilla.txt' WHERE tipo='MAIL_FILEPATH_ALARMA_AMARILLA';
UPDATE parametro SET valor='alarma_roja.txt' WHERE tipo='MAIL_FILEPATH_ALARMA_ROJA';
UPDATE parametro SET valor='aprobar.txt' WHERE tipo='MAIL_FILEPATH_APROBAR';
UPDATE parametro SET valor='creacion_expediente.txt' WHERE tipo='MAIL_FILEPATH_CREACION_EXPEDIENTE';
UPDATE parametro SET valor='derivar.txt' WHERE tipo='MAIL_FILEPATH_DERIVAR';
UPDATE parametro SET valor='ingreso_documento_qas.txt' WHERE tipo='MAIL_FILEPATH_INGRESO_DOCUMENTO_QAS';
UPDATE parametro SET valor='ingreso_documento_user.txt' WHERE tipo='MAIL_FILEPATH_INGRESO_DOCUMENTO_USERFINAL';

-- Nuevo recurso Imprimir Cargo
INSERT INTO recurso(idrecurso,idmodulo,nombre,codigo,actionurl,descripcion,estado) VALUES(recurso_seq.nextval,1,'Mesa de Partes - Imprimir Cargo','MPImpCargo',null,null,'A');

-- Campo para mantener en la tabla documento el ultimo asunto y no traerlo de trazabalidaddocumento. Necesario correr los updates siguientes para actualizar la data actual
ALTER TABLE documento ADD (
   ultimoasunto VARCHAR2(500)
);

update documento d
set d.ultimoasunto = (
    select t.asunto
    from trazabilidaddocumento t
    where t.documento=d.iddocumento and t.nroregistro=(
        select max(tt.nroregistro)
        from trazabilidaddocumento tt
        where tt.documento=d.iddocumento
    )
);

update documento d
set d.remitente = (
    select r.nombres || ' ' || r.apellidos
    from trazabilidaddocumento t, usuario r
    where t.remitente=r.idusuario and t.documento=d.iddocumento and t.nroregistro=(
        select max(tt.nroregistro)
        from trazabilidaddocumento tt
        where tt.documento=d.iddocumento
    )
)
where d.remitente is null;

--Paquete 6 Tarea 81 - Mensaje para el tipo de proceso Ambiente Piloto / Produccion
INSERT INTO parametro (idparametro, TIPO, VALOR) VALUES (parametro_seq.nextval, 'tipoProcesoPiloto', '* Ambiente Piloto');
INSERT INTO parametro (idparametro, TIPO, VALOR) VALUES (parametro_seq.nextval, 'tipoProcesoProduccion', '* Ambiente Producción');

--Paquete 6 Tarea 86 - Etapas x Proceso
ALTER TABLE etapa ADD (
   idproceso NUMBER(38,0)
);
ALTER TABLE etapa ADD (
   orden NUMBER(38,0)
);
--Paquete 6 Tarea 87 - Creacion de estados de prueba para los estados de los procesos "tipo---> proceso"
INSERT INTO estado (idestado, descripcion, tipo, estado) VALUES (estado_seq.nextval, 'Estado prueba 1', 'proceso', 'A');
INSERT INTO estado (idestado, descripcion, tipo, estado) VALUES (estado_seq.nextval, 'Estado prueba 2', 'proceso', 'A');
INSERT INTO estado (idestado, descripcion, tipo, estado) VALUES (estado_seq.nextval, 'Estado prueba 3', 'proceso', 'A');
ALTER TABLE proceso ADD (
   idestado NUMBER(38,0)
);
ALTER TABLE estado ADD (
   idproceso NUMBER(38,0)
);

--Mensajeria Actualizacion de tipo de destinatario Interno (I) Externo (E) Sindestinatario (S)
update numeracion set destinatario='I' where destinatario='C';

--Para Mensajeria
--Creacion de la tabla para almacenar destinatario y con copia para los clientes externos de un documento
  CREATE TABLE "DOCUMENTOXCLIENTE" 
   (  "IDCLIENTE" NUMBER(38,0), 
      "IDDOCUMENTO" NUMBER(38,0), 	
	   "TIPO" CHAR(1)
   ) ;
  CREATE UNIQUE INDEX "PKINDICEDXC" ON "DOCUMENTOXCLIENTE" ("IDDOCUMENTO", "IDCLIENTE");

-- Favoritos
CREATE TABLE favorito (
   idfavorito NUMBER(38,0) NOT NULL,
   idpropietario NUMBER(38,0),
   idcontacto NUMBER(38,0),
   tipocontacto CHAR(1),
   fechacreacion TIMESTAMP (6),
   estado CHAR(1)
);

ALTER TABLE favorito ADD CONSTRAINT favorito_pk PRIMARY KEY(idfavorito);
CREATE SEQUENCE favorito_seq MINVALUE 1 MAXVALUE 9999999 INCREMENT BY 1 START WITH 25 NOCACHE ORDER CYCLE;

-- Nuevas columnas para la bandeja Mis Expedientes
INSERT INTO gridxgridcolumna(idgrid,idgridcolumna,id) VALUES(7,14,98*dbms_random.random); -- estado
INSERT INTO gridcolumna(idgridcolumna,field,name,noresize,width,hidden,formater,position) VALUES(GRIDCOLUMNA_SEQ.nextval,'propietario','Propietario',0,'150px',0,null,25);
INSERT INTO gridxgridcolumna(idgrid,idgridcolumna,id) VALUES(7,25,175*dbms_random.random); -- propietario
INSERT INTO gridcolumna(idgridcolumna,field,name,noresize,width,hidden,formater,position) VALUES(GRIDCOLUMNA_SEQ.nextval,'responsable','Responsable',0,'150px',0,null,26);
INSERT INTO gridxgridcolumna(idgrid,idgridcolumna,id) VALUES(7,26,182*dbms_random.random); -- responsable

-- UPDATE gridxgridcolumna SET id=-1*id WHERE id<0;
-- UPDATE gridxgridcolumna SET id=CEIL(id/10) WHERE id>1000000 AND idgrid=7 AND idgridcolumna=14;

-- Nueva accion 'reabrir'
INSERT INTO accion(idaccion,nombre,descripcion,estado) VALUES(ACCION_SEQ.nextval,'reabrir','Reabrir','A');


--Para el manejo de mensajeria adicional de la columna docprincipal
ALTER TABLE MENSAJERIA ADD(DOCPRINCIPAL char(1));   
ALTER TABLE MENSAJERIA ADD(IDMENSAJERIAPRINCIPAL NUMBER(38,0));

--El grid de mensajeria ya no lleva la columna asunto verificar si idgridcolumna 4 es asunto
DELETE FROM gridxgridcolumna where idgrid=4 and idgridcolumna=4;

--Creacion de los items del menu para el usuario mensajeria... en dojo
INSERT INTO GRID (idgrid, nombre, codigo, descripcion, rol)
VALUES (16, 'Doc. Recibidos', '16','',24);
INSERT INTO GRID (idgrid, nombre, codigo, descripcion, rol)
VALUES (17, 'Doc. Cerrados', '17','',24);

--Creacion de los recursos 
INSERT INTO RECURSO (idrecurso, idmodulo, nombre, codigo, actionurl, descripcion, estado)
 VALUES(RECURSO_SEQ.nextval, 6, 'Mensajeria - Grid Mensajeria Recibidos','MensGridRec','','','A');
INSERT INTO RECURSO (idrecurso, idmodulo, nombre, codigo, actionurl, descripcion, estado)
 VALUES(RECURSO_SEQ.nextval, 6, 'Mensajeria - Grid Mensajeria Cerrados','MensGridCer','','','A');
 
INSERT INTO RECURSO (idrecurso, idmodulo, nombre, codigo, actionurl, descripcion, estado)
 VALUES(RECURSO_SEQ.nextval, 6, 'Mensajeria - Menu Documentos Recibidos','MensMnuDocRec','','','A');
INSERT INTO RECURSO (idrecurso, idmodulo, nombre, codigo, actionurl, descripcion, estado)
 VALUES(RECURSO_SEQ.nextval, 6, 'Mensajeria - Menu Documentos Cerrados','MensMnuDocCer','','','A');
 
 --Agregar los recursos al perfil de mensajeria
 INSERT INTO RECURSOXPERFIL(idrecurso, idperfil) VALUES(idRecursoNuevo1,idPerfilMensajeria);
 INSERT INTO RECURSOXPERFIL(idrecurso, idperfil) VALUES(idRecursoNuevo2,idPerfilMensajeria);
 INSERT INTO RECURSOXPERFIL(idrecurso, idperfil) VALUES(idRecursoNuevo3,idPerfilMensajeria);
 INSERT INTO RECURSOXPERFIL(idrecurso, idperfil) VALUES(idRecursoNuevo4,idPerfilMensajeria);
 
INSERT INTO GRIDXGRIDCOLUMNA (idgrid, idgridcolumna, id) VALUES(16, 5, 28*dbms_random.random);
INSERT INTO GRIDXGRIDCOLUMNA (idgrid, idgridcolumna, id) VALUES(16, 1, 28*dbms_random.random);
INSERT INTO GRIDXGRIDCOLUMNA (idgrid, idgridcolumna, id) VALUES(16, 18, 28*dbms_random.random);
INSERT INTO GRIDXGRIDCOLUMNA (idgrid, idgridcolumna, id) VALUES(16, 15, 28*dbms_random.random);
INSERT INTO GRIDXGRIDCOLUMNA (idgrid, idgridcolumna, id) VALUES(16, 17, 28*dbms_random.random);
INSERT INTO GRIDXGRIDCOLUMNA (idgrid, idgridcolumna, id) VALUES(16, 16, 28*dbms_random.random);

INSERT INTO GRIDXGRIDCOLUMNA (idgrid, idgridcolumna, id) VALUES(17, 5, 28*dbms_random.random);
INSERT INTO GRIDXGRIDCOLUMNA (idgrid, idgridcolumna, id) VALUES(17, 1, 28*dbms_random.random);
INSERT INTO GRIDXGRIDCOLUMNA (idgrid, idgridcolumna, id) VALUES(17, 18, 28*dbms_random.random);
INSERT INTO GRIDXGRIDCOLUMNA (idgrid, idgridcolumna, id) VALUES(17, 15, 28*dbms_random.random);
INSERT INTO GRIDXGRIDCOLUMNA (idgrid, idgridcolumna, id) VALUES(17, 17, 28*dbms_random.random);
INSERT INTO GRIDXGRIDCOLUMNA (idgrid, idgridcolumna, id) VALUES(17, 16, 28*dbms_random.random); 

-- UPDATE gridxgridcolumna SET id=-1*id WHERE id<0;
-- UPDATE gridxgridcolumna SET id=CEIL(id/10) WHERE id>1000000 ;

--Para la modificacion de los datos en el tab envio
ALTER TABLE ENVIO ADD(PESODCMTO VARCHAR2(10));
ALTER TABLE ENVIO ADD(UNIDADPESO VARCHAR2(40));

--Eliminar las columnas de la tabla cargo
ALTER TABLE CARGO DROP (PESODCMTO, UNIDADPESO);

--Agregar la columna estado en el grid doc. cerrados
INSERT INTO GRIDXGRIDCOLUMNA (idgrid, idgridcolumna, id) VALUES(17, 14, 28*dbms_random.random);

--Agregar la columna tipoenvio a la tabla mensajeria
ALTER TABLE MENSAJERIA ADD(IDTIPOENVIO NUMBER(38,0));

--Modificacion de la tabla datos ya no tiene la columna tipoenvio
ALTER TABLE DATOS DROP (IDTIPOENVIO);

--Modificacion de la tabla tipo envio para que solo soporte el TIPO normal/express/etc
ALTER TABLE TIPOENVIO DROP (CODIGO, TIPOENVIO1);

--Creacion de una nueva tabla para que soporte el ambito de envio mensajeria local/nacional/etc
CREATE TABLE "AMBITOENVIO" 
   (  "IDAMBITOENVIO" NUMBER(38,0), 
      "NOMAMBITOENVIO" varchar(70)
   ) ;
  CREATE UNIQUE INDEX "PKAMBITOENVIO" ON "AMBITOENVIO" ("IDAMBITOENVIO");
  CREATE SEQUENCE ambitoenvio_seq MINVALUE 1 MAXVALUE 9999999 INCREMENT BY 1 START WITH 25 NOCACHE ORDER CYCLE;

--Agregar la columna idambitoenvio a la tabla Datos
ALTER TABLE DATOS ADD(IDAMBITOENVIO NUMBER(38,0));

--Modificacion de la tabla envio 
ALTER TABLE ENVIO DROP (CODIGOENVIO,TIPOENVIO);
ALTER TABLE ENVIO ADD (AMBITOENVIO VARCHAR2(40));

--Creacion del nuevo recurso de Reporte de Mensajeria
INSERT INTO RECURSO (idrecurso, idmodulo, nombre, codigo, actionurl, descripcion, estado)
 VALUES(RECURSO_SEQ.nextval, 16, 'Usuario Final - Reportes Mensajeria','UsuFinRepMen','','','A');

--Agregar el nuevo recurso al usuario final- perfil 5
INSERT INTO RECURSOXPERFIL(idrecurso, idperfil) VALUES(idRecursoNuevo,5);

--Agregar una nueva columna a la tabla trazabilidaddocumento para que soporte etapa
ALTER TABLE TRAZABILIDADDOCUMENTO ADD(IDETAPA NUMBER(38,0));


----------------------------------------------------------------------------------------------------------------------06/10
--Cambios para el registro de datos adicionales del SGU (STOR)

--Insertamos las monedas
INSERT INTO PARAMETRO (TIPO, VALOR, DESCRIPCION) VALUES ('moneda', 'Nuevos Soles', 'Nuevos Soles');
INSERT INTO PARAMETRO (TIPO, VALOR, DESCRIPCION) VALUES ('moneda', 'Dolares', 'Dolares');

--Nuevos campos para los datos principales

ALTER TABLE  EXPEDIENTESTOR ADD (
  "NRORESOLUCION" VARCHAR2(30 BYTE),
  "IDRECLAMANTE" NUMBER(38,0), 
  "RECLAMANTERAZONSOCIAL" VARCHAR2(100 BYTE), 
	"RECLAMANTEREPRESENTANTELEGAL" VARCHAR2(100 BYTE), 
	"RECLAMANTEDIRECCIONREAL" VARCHAR2(100 BYTE), 
	"RECLAMANTEUBIGEOREAL" NUMBER(38,0), 
	"RECLAMANTEDIRECCIONPROCESAL" VARCHAR2(100 BYTE), 
	"RECLAMANTEUBIGEOPROCESAL" NUMBER(38,0), 
	"RECLAMANTECORREO" VARCHAR2(100 BYTE), 
  "RECLAMANTETELEFONO" VARCHAR2(50 BYTE), 
	"RECLAMANTEFAX" VARCHAR2(50 BYTE), 
  "RECLAMANTENROSUMINISTRO" VARCHAR2(50 BYTE), 
	"RECLAMANTENOMBRES" VARCHAR2(200 BYTE), 
	"RECLAMANTEAPELLIDOPATERNO" VARCHAR2(200 BYTE), 
	"RECLAMANTEAPELLIDOMATERNO" VARCHAR2(200 BYTE)
);

--Creamos la tabla SubmotivoXExpedienteSTOR

CREATE TABLE SUBMOTIVOXEXPEDIENTESTOR (	
  "IDSUBMOTIVO" NUMBER(38,0), 
	"IDEXPEDIENTE" NUMBER(38,0), 
  "MONTO" FLOAT(126),
  "MONEDA" NUMBER(38,0)
); 

--Modificamos Resolucion

ALTER TABLE RESOLUCIONJARU ADD(
  "FECHASESION" DATE, 
	"IDVOCAL" NUMBER(38,0) 
);

--Creamos la tabla Vocal, secuencia y trigger

CREATE SEQUENCE VOCAL_SEQ MINVALUE 1 MAXVALUE 9999999 INCREMENT BY 1 START WITH 1 NOCACHE ORDER CYCLE;

CREATE TABLE VOCAL (
  "IDVOCAL" NUMBER(38,0),
  "NOMBRES" VARCHAR2(200 BYTE), 
	"APELLIDOPATERNO" VARCHAR2(200 BYTE), 
	"APELLIDOMATERNO" VARCHAR2(200 BYTE)
);

CREATE OR REPLACE TRIGGER VOCAL_TRG
BEFORE INSERT ON VOCAL
FOR EACH ROW 
BEGIN
  <<COLUMN_SEQUENCES>>
  BEGIN
    IF :NEW.IDVOCAL IS NULL THEN
      SELECT VOCAL_SEQ.NEXTVAL INTO :NEW.IDVOCAL FROM DUAL;
    END IF;
  END COLUMN_SEQUENCES;
END; 

ALTER TRIGGER VOCAL_TRG ENABLE;

--Agregar columna nroExpediente al grid Enviados
INSERT INTO gridxgridcolumna(idgrid,idgridcolumna,id) VALUES(3,10,30*dbms_random.random); -- expediente

-- Nueva columna actividad en trazaabilidaddocumento
ALTER TABLE TRAZABILIDADDOCUMENTO ADD(IDACTIVIDAD NUMBER(38,0));

-- Desde aqui esto ya esta corrido en produccion
--Nueva columna a la tabla expediente para identificar envios a OEFA
ALTER TABLE expediente ADD (
   oefa CHAR(1)
);

--Actualizacion para la nueva columna de la tabla Expediente
update expediente set oefa='N';

--Creacion de un nuevo tipo de Accion 'Enviar a OEFA'
INSERT INTO accion(idaccion,nombre,descripcion,estado) VALUES(ACCION_SEQ.nextval,'oefa','Enviar a OEFA','A');

--Creacion de nuevo tipo de notificacion para envio en caso de expedientes enviados a OEFA
INSERT INTO parametro(idparametro,tipo,valor,descripcion) 
VALUES(PARAMETRO_SEQ.nextval,'tiponotificacion','1013','Expediente Enviado a OEFA');

-- hasta aqui ya esta corrido en produccion


--------------------------------------------------------------------------------------------------------14/10/10
--Scripts para la trazabilidad de la copia
create table TRAZABILIDADCOPIA(
  idtrazabilidadcopia NUMBER(38,0), 
  idorigen NUMBER(38,0), 
	remitente NUMBER(38,0), 
	destinatario NUMBER(38,0), 
	documento NUMBER(38,0), 
	accion NUMBER(38,0), 
	fechacreacion TIMESTAMP (6), 
  idetapa NUMBER(38,0),
  tipo CHAR(1 BYTE),
  idnotificacion NUMBER(38,0)
);
  
CREATE SEQUENCE TRAZABILIDADCOPIA_SEQ MINVALUE 1 MAXVALUE 9999999 INCREMENT BY 1 START WITH 1 NOCACHE ORDER CYCLE;

CREATE OR REPLACE TRIGGER TRAZABILIDADCOPIA
BEFORE INSERT ON TRAZABILIDADCOPIA
FOR EACH ROW 
BEGIN
  <<COLUMN_SEQUENCES>>
  BEGIN
    IF :NEW.IDTRAZABILIDADCOPIA IS NULL THEN
      SELECT TRAZABILIDADCOPIA_SEQ.NEXTVAL INTO :NEW.IDTRAZABILIDADCOPIA FROM DUAL;
    END IF;
  END COLUMN_SEQUENCES;
END; 

insert into accion (nombre, descripcion, estado) values ('copia', 'Copia', 'A');

------------------------------------------------------------------------------------------------------------------------
--Vista para las grillas de usuario final.
 CREATE VIEW VISTAGRIDUSUARIOFINAL AS 
  select 
  (SELECT eta.descripcion from Etapa eta where ex.idetapa = eta.idetapa) as ETAPA, 
  d.iddocumento  AS IDDOCUMENTO,
  d.nroMesaPartes AS NROMESAPARTES,
  ex.historico  AS HISTORICO,
  ex.estado  AS ESTADOEXPEDIENTE,
  ex.actividad AS ACTIVIDAD,
  d.leido AS LEIDO,
  d.ultimoAsunto  AS ULTIMOASUNTO,
  d.fechaCreacion   AS FECHACREACION,
  d.fechaAccion AS FECHAACCION,
  d.accion AS IDACCION,
  d.fechaLimiteAtencion AS FECHALIMITEATENCION, 
  d.principal AS PRINCIPAL,
  concat( concat((SELECT td.nombre from tipodocumento td where td.idtipodocumento = d.tipodocumento),' - '), d.nroDocumento ) as Documento, 
  ex.nroexpediente  AS NROEXPEDIENTE, 
  pro.idproceso AS IDPROCESO, 
  pro.porcentajealerta1 AS PORCENTAJEALERTA1,  
  pro.porcentajealerta2 AS PORCENTAJEALERTA2, 
  pro.nombre  AS NOMBREPROCESO,
  (SELECT co.razonSocial from Concesionario co where ex.concesionario = co.idconcesionario ) as concesionario, 
  concat ( concat( concat( concat ( cl.nombres ,' ' ), cl.apellidoPaterno ),' ' ) , cl.apellidoMaterno ) as Cliente, 
  cl.razonSocial AS RAZONSOCIALCLIENTE,
  (SELECT ti.nombre from TipoIdentificacion ti where cl.tipoidentificacion= ti.idtipoidentificacion)as tipoidentificacion, 
  d.remitente AS REMITENTE, 
  d.estado AS ESTADODOCUMENTO,
  (SELECT us.idusuario from Usuario us where d.propietario = us.idusuario) as idpropietario,
  (SELECT un.sede from Unidad un where un.idunidad = (SELECT us.idunidad from Usuario us where d.propietario = us.idusuario)) as sede
FROM  Documento d  LEFT JOIN Expediente ex  on d.expediente = ex.idexpediente LEFT JOIN Proceso pro on ex.proceso = pro.idproceso
LEFT JOIN Cliente cl on ex.cliente = cl.idcliente order by d.fechaAccion;

--Para agregar columna tipo de documento
Insert into GRIDCOLUMNA (IDGRIDCOLUMNA,FIELD,NAME,NORESIZE,WIDTH,HIDDEN,FORMATER,POSITION) values (27,'tipodocumento','Tipo',0,'50px',0,'formatterTipoDocumento',27);
Insert into GRIDXGRIDCOLUMNA (IDGRID,IDGRIDCOLUMNA,ID) values (9,27,9032829);

-- Integracion con SAS
ALTER TABLE expediente ADD (
   actividad VARCHAR2(1000 BYTE)
);

ALTER TABLE trazabilidaddocumento ADD (
   actividad VARCHAR2(1000 BYTE)
);

INSERT INTO gridcolumna values(gridcolumna_seq.nextval,'actividad','Actividad',1,'150px',1,null,28);
INSERT INTO gridxgridcolumna(idgrid,idgridcolumna,id) VALUES(1,29,28*dbms_random.random); -- actividad

INSERT INTO parametro(idparametro,tipo,valor,descripcion) VALUES(parametro_seq.nextval,'MAIL_FILEPATH_DOCUMENTO_REENVIAR','reenviar.txt','Correo que se envia al reenviar un documento');
INSERT INTO parametro(idparametro,tipo,valor,descripcion) VALUES(parametro_seq.nextval,'MAIL_FILEPATH_DOCUMENTO_PORAPROBAR','poraprobar.txt','Correo que se envia al reenviar un documento por aprobar');
INSERT INTO parametro(idparametro,tipo,valor,descripcion) VALUES(parametro_seq.nextval,'MAIL_FILEPATH_DOCUMENTO_APROBAR','aprobar.txt','Correo que se envia al aprobar un documento');
INSERT INTO parametro(idparametro,tipo,valor,descripcion) VALUES(parametro_seq.nextval,'MAIL_FILEPATH_DOCUMENTO_CCREENVIAR','ccreenviar.txt','Correo que se envia al reenviar un documento con copia');
INSERT INTO parametro(idparametro,tipo,valor,descripcion) VALUES(parametro_seq.nextval,'MAIL_FILEPATH_DOCUMENTO_CCPORAPROBAR','ccporaprobar.txt','Correo que se envia al reenviar un documento por aprobar con copia');
INSERT INTO parametro(idparametro,tipo,valor,descripcion) VALUES(parametro_seq.nextval,'MAIL_FILEPATH_DOCUMENTO_CCAPROBAR','ccaprobar.txt','Correo que se envia al aprobar un documento con copia');
INSERT INTO parametro(idparametro,tipo,valor,descripcion) VALUES(parametro_seq.nextval,'MAIL_FILEPATH_ENUMERACION_DESTINATARIO','enumeracion_destinatario.txt','Correo que se envia al enumerar un documento con destinatario');
INSERT INTO parametro(idparametro,tipo,valor,descripcion) VALUES(parametro_seq.nextval,'MAIL_FILEPATH_ENUMERACION_COPIA','enumeracion_copia.txt','Correo que se envia al enumerar un documento con copia');

-- Asignando el recurso de bandeja compartida

	insert into recursoxperfil(idperfil,idrecurso) values(1,207);

-- Agregando tablas para Bandeja Compartida
  CREATE TABLE "ADMINISTRADORXUSUARIO" 
   (	"USUARIO" NUMBER, 
	"ADMINISTRADOR" NUMBER
   ) ;

  CREATE TABLE "COMPARTIDOXUSUARIO" 
   (	"USUARIO" NUMBER, 
	"ASIGNADO" NUMBER
   ) ;

  ALTER TABLE "ADMINISTRADORXUSUARIO" ADD CONSTRAINT "ADMINISTRADORXUSUARIO_PK" PRIMARY KEY ("USUARIO", "ADMINISTRADOR")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "TBSD_DATA"  ENABLE;

  ALTER TABLE "COMPARTIDOXUSUARIO" ADD CONSTRAINT "COMPARTIDOXUSUARIO_PK" PRIMARY KEY ("USUARIO", "ASIGNADO")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "TBSD_DATA"  ENABLE;

  ALTER TABLE "ADMINISTRADORXUSUARIO" ADD CONSTRAINT "ADMINISTRADORXUSUARIO_USU_FK1" FOREIGN KEY ("ADMINISTRADOR")
	  REFERENCES "USUARIO" ("IDUSUARIO") ENABLE;
 
  ALTER TABLE "ADMINISTRADORXUSUARIO" ADD CONSTRAINT "ADMINISTRADORXUSUARIO_USU_FK2" FOREIGN KEY ("USUARIO")
	  REFERENCES "USUARIO" ("IDUSUARIO") ENABLE;

  ALTER TABLE "COMPARTIDOXUSUARIO" ADD CONSTRAINT "COMPARTIDOXUSUARIO_USUARI_FK1" FOREIGN KEY ("USUARIO")
	  REFERENCES "USUARIO" ("IDUSUARIO") DISABLE;
 
  ALTER TABLE "COMPARTIDOXUSUARIO" ADD CONSTRAINT "COMPARTIDOXUSUARIO_USUARI_FK2" FOREIGN KEY ("ASIGNADO")
	  REFERENCES "USUARIO" ("IDUSUARIO") ENABLE;
	  
-- Parametro para Enviar correos al Rechzar. 	  
Insert into parametro (IDPARAMETRO,TIPO,VALOR,DESCRIPCION) values (parametro_seq.nextval,'MAIL_FILEPATH_DOCUMENTO_RECHAZAR','rechazar.txt','Plantilla para rechazar en correos electronicos');

//Cambia el tama�o maximo permitido para subir un archivo en la bandeja del digitalizador.
update parametro set valor='20 Mb', descripcion='20 Mb' where idparametro=36

-- VISTA VISTAGRIDUSUARIOFINAL

select 
  (SELECT eta.descripcion from Etapa eta where ex.idetapa = eta.idetapa) as ETAPA, 
  d.iddocumento  AS IDDOCUMENTO,
  d.nroMesaPartes AS NROMESAPARTES,
  ex.historico  AS HISTORICO,
  ex.estado  AS ESTADOEXPEDIENTE,
  ex.actividad AS ACTIVIDAD,
  d.leido AS LEIDO,
  d.ultimoAsunto  AS ULTIMOASUNTO,
  d.fechaCreacion   AS FECHACREACION,
  d.fechaAccion AS FECHAACCION,
  d.accion AS IDACCION,
  d.fechaLimiteAtencion AS FECHALIMITEATENCION, 
  d.principal AS PRINCIPAL,
  concat( concat((SELECT td.nombre from tipodocumento td where td.idtipodocumento = d.tipodocumento),' - '), d.nroDocumento ) as Documento, 
  ex.nroexpediente  AS NROEXPEDIENTE, 
  pro.idproceso AS IDPROCESO, 
  pro.porcentajealerta1 AS PORCENTAJEALERTA1,  
  pro.porcentajealerta2 AS PORCENTAJEALERTA2, 
  pro.nombre  AS NOMBREPROCESO,
  (SELECT co.razonSocial from Concesionario co where ex.concesionario = co.idconcesionario ) as concesionario, 
  concat ( concat( concat( concat ( cl.nombres ,' ' ), cl.apellidoPaterno ),' ' ) , cl.apellidoMaterno ) as Cliente, 
  cl.razonSocial AS RAZONSOCIALCLIENTE,
  (SELECT ti.nombre from TipoIdentificacion ti where cl.tipoidentificacion= ti.idtipoidentificacion)as tipoidentificacion, 
  d.remitente AS REMITENTE, 
  d.estado AS ESTADODOCUMENTO,
  (SELECT us.idusuario from Usuario us where d.propietario = us.idusuario) as idpropietario,
  (SELECT un.sede from Unidad un where un.idunidad = (SELECT us.idunidad from Usuario us where d.propietario = us.idusuario)) as sede,
  td.nombre as tipodocumento,
  cl.nombres as nombrescliente, 
  cl.apellidopaterno as apcliente, 
  cl.apellidomaterno as amcliente,
  ex.clientenombres as exnombresclie, 
  ex.clienteapellidopaterno as exapclie, 
  ex.clienteapellidomaterno as examclie 
FROM  Documento d  
LEFT JOIN tipodocumento td  on td.idtipodocumento=d.tipodocumento 
LEFT JOIN Expediente ex  on d.expediente = ex.idexpediente LEFT JOIN Proceso pro on ex.proceso = pro.idproceso
LEFT JOIN Cliente cl on ex.cliente = cl.idcliente
-- Registro de recurso.

Insert into recurso (IDRECURSO,IDMODULO,NOMBRE,CODIGO,ACTIONURL,DESCRIPCION,ESTADO) values (RECURSO_SEQ.nextval,1,'Compartidos','MnuCompartido',null,null,'A');

-- Agregando el usuario de control en la trazabilidad.

ALTER TABLE TRAZABILIDADDOCUMENTO 
ADD ("COMPARTIDO" NUMBER(38, 0))
;

ALTER TABLE TRAZABILIDADDOCUMENTO 
ADD CONSTRAINT COMPARTIDO_USU_FK1 FOREIGN KEY 
(
"COMPARTIDO"
) REFERENCES USUARIO 
(
"IDUSUARIO"
) ENABLE
;

--Agrega a la tabla SUBMOTIVOXEXPEDIENTESTOR su clave primaria, ya que se encuentra mapeada
--de esa manera en su Clase Java y permite el registro de datos duplicados
ALTER TABLE SUBMOTIVOXEXPEDIENTESTOR ADD PRIMARY KEY (IDSUBMOTIVO, IDEXPEDIENTE);


--INSERCION DE ESTADOS PARA EL PROCESO STOR
Insert into ESTADO (IDESTADO,DESCRIPCION,CODIGO,TIPO,ESTADO,IDPROCESO) values (17,'Proyecto','pry','stor','A',null);
Insert into ESTADO (IDESTADO,DESCRIPCION,CODIGO,TIPO,ESTADO,IDPROCESO) values (18,'Concluido por resolucion','cpr','stor','A',null);
Insert into ESTADO (IDESTADO,DESCRIPCION,CODIGO,TIPO,ESTADO,IDPROCESO) values (19,'Concluido por conciliac.','cpc','stor','A',null);
Insert into ESTADO (IDESTADO,DESCRIPCION,CODIGO,TIPO,ESTADO,IDPROCESO) values (20,'Archivadas','arc','stor','A',null);
Insert into ESTADO (IDESTADO,DESCRIPCION,CODIGO,TIPO,ESTADO,IDPROCESO) values (21,'Pendiente','pndt','stor','A',null);
Insert into ESTADO (IDESTADO,DESCRIPCION,CODIGO,TIPO,ESTADO,IDPROCESO) values (22,'Cancelado','can','stor','A',null);
Insert into ESTADO (IDESTADO,DESCRIPCION,CODIGO,TIPO,ESTADO,IDPROCESO) values (23,'Anulado','anl','stor','A',null);
Insert into ESTADO (IDESTADO,DESCRIPCION,CODIGO,TIPO,ESTADO,IDPROCESO) values (24,'Conciliacion','cnc','stor','A',null);
Insert into ESTADO (IDESTADO,DESCRIPCION,CODIGO,TIPO,ESTADO,IDPROCESO) values (25,'Concluida por carta stor','ccs','stor','A',null);
Insert into ESTADO (IDESTADO,DESCRIPCION,CODIGO,TIPO,ESTADO,IDPROCESO) values (16,'Análisis','anls','stor','A',null);


--INSERCION DE VOCALES
Insert into VOCAL (IDVOCAL,NOMBRES,APELLIDOPATERNO,APELLIDOMATERNO) values (3,'ELOY ESPINOSA','SALDA�A','BARRERA');
Insert into VOCAL (IDVOCAL,NOMBRES,APELLIDOPATERNO,APELLIDOMATERNO) values (4,'FABRICIO','OROZCO','VALEZ ');
Insert into VOCAL (IDVOCAL,NOMBRES,APELLIDOPATERNO,APELLIDOMATERNO) values (5,'RICARDO','BRASHI','O''HARA');
Insert into VOCAL (IDVOCAL,NOMBRES,APELLIDOPATERNO,APELLIDOMATERNO) values (6,'JOSÉ LUIS','SARDAN','DE TABOADA');
Insert into VOCAL (IDVOCAL,NOMBRES,APELLIDOPATERNO,APELLIDOMATERNO) values (7,'PEDRO','VILLA','DURAND');
Insert into VOCAL (IDVOCAL,NOMBRES,APELLIDOPATERNO,APELLIDOMATERNO) values (8,'CLAUDIA','DIAZ','DIAZ');
Insert into VOCAL (IDVOCAL,NOMBRES,APELLIDOPATERNO,APELLIDOMATERNO) values (9,'JORGE','CARDENAS','BUSTIOS');

-- Agregando columna propietario en la grid usuario final
INSERT INTO GRIDXGRIDCOLUMNA (IDGRID, IDGRIDCOLUMNA, ID) VALUES (1, 25, 1);

-- Agregando Accion para rechazo de digitalizacion 
INSERT INTO ACCION (IDACCION,NOMBRE, DESCRIPCION, ESTADO) VALUES (ACCION_SEQ.nextval,'rechazarqas', 'Rechazo QAS', 'A');

--SECUENCIA DE GENERACION DE NRO DE EXPEDIENTES PARA EL ENTORNO DESARROLLO
CREATE SEQUENCE  "SIGED_PREPROD"."EXPEDIENTE_SEQ"  MINVALUE 1 MAXVALUE 9999999 INCREMENT BY 1 START WITH 10000 NOCACHE  ORDER  CYCLE ;

--Consultas para separar el campo suministro de la tabla expedientestor en una tabla aparte llamada suministro
truncate table suministro;
alter table suministro add expedientestor number(38) default 0 not null;
alter table suministro add constraint fk_suministro_exped foreign key(expedientestor) references expediente(idexpediente);

--============================ETAPAS DEL PROCESO STOR===========================
-- Proceso STOR Apelacion
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Ingreso de expedientes',null,'A',null,3,1);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Analistas',null,'A',null,3,2);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Revisión Legal',null,'A',null,3,5);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Revisión Técnica',null,'A',null,3,5);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Ingreso a sesión JARU',null,'A',null,3,6);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'En inspección',null,'A',null,3,7);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Notificación',null,'A',null,3,8);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Vº Bº final',null,'A',null,3,8);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Cumplimiento',null,'A',null,3,9);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'IPS',null,'A',null,3,11);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Impresión',null,'A',null,3,27);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Analisis externo',null,'A',null,3,30);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Comunicaciones SG',null,'A',null,3,31);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Requerimiento de información',null,'A',null,3,32);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Atención usuario',null,'A',null,3,33);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Información Oral',null,'A',null,3,34);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Archivo',null,'A',null,3,35);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Requerimiento Poder Judicial',null,'A',null,3,36);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Fedatación de Expediente',null,'A',null,3,37);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Multa Coercitiva',null,'A',null,3,38);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Verificación',null,'A',null,3,39);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Analisis de Expediente',null,'A',null,3,40);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Evaluación Legal del Informe',null,'A',null,3,41);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Revisión Proyecto de GG',null,'A',null,3,43);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Visto Bueno Proyecto de GG',null,'A',null,3,44);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Gerencia Legal',null,'A',null,3,45);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Prestado',null,'A',null,3,46);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Medida Cautelar',null,'A',null,3,47);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Multa Notificada',null,'A',null,3,48);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Multa Impugnada',null,'A',null,3,49);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Multa Consentida',null,'A',null,3,50);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Ejecutoría Coactiva',null,'A',null,3,51);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Solicitud de Rectificación',null,'A',null,3,52);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Multa emitida',null,'A',null,3,55);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Resolución Archivamiento de GG',null,'A',null,3,56);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Informe Alta Dirección',null,'A',null,3,58);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Comunicaciones GL',null,'A',null,3,59);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Comunicaciones STA - IPS',null,'A',null,3,60);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Revisión de Proyectos PS',null,'A',null,3,61);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Visto Bueno Proyectos PS',null,'A',null,3,62);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Impresión de Proyecto PS',null,'A',null,3,63);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Resolución Archivamiento PS.',null,'A',null,3,64);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Envio a Tastem',null,'A',null,3,65);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Suspendido',null,'A',null,3,65);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Fiscalización Posterior PR ADM',null,'A',null,3,67);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Revisión de Oficio',null,'A',null,3,70);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Rev. Proy. Multa Coercitiva',null,'A',null,3,71);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Vº Bº Rev. Proy. Multa Coercitiva',null,'A',null,3,72);

-- Proceso STOR Queja
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Ingreso de expedientes',null,'A',null,4,1);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Analistas',null,'A',null,4,2);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Revisión Legal',null,'A',null,4,5);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Revisión Técnica',null,'A',null,4,5);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Ingreso a sesión JARU',null,'A',null,4,6);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'En inspección',null,'A',null,4,7);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Notificación',null,'A',null,4,8);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Vº Bº final',null,'A',null,4,8);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Cumplimiento',null,'A',null,4,9);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'IPS',null,'A',null,4,11);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Impresión',null,'A',null,4,27);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Analisis externo',null,'A',null,4,30);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Comunicaciones SG',null,'A',null,4,31);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Requerimiento de información',null,'A',null,4,32);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Atención usuario',null,'A',null,4,33);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Información Oral',null,'A',null,4,34);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Archivo',null,'A',null,4,35);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Requerimiento Poder Judicial',null,'A',null,4,36);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Fedatación de Expediente',null,'A',null,4,37);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Multa Coercitiva',null,'A',null,4,38);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Verificación',null,'A',null,4,39);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Analisis de Expediente',null,'A',null,4,40);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Evaluación Legal del Informe',null,'A',null,4,41);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Revisión Proyecto de GG',null,'A',null,4,43);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Visto Bueno Proyecto de GG',null,'A',null,4,44);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Gerencia Legal',null,'A',null,4,45);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Prestado',null,'A',null,4,46);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Medida Cautelar',null,'A',null,4,47);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Multa Notificada',null,'A',null,4,48);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Multa Impugnada',null,'A',null,4,49);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Multa Consentida',null,'A',null,4,50);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Ejecutoría Coactiva',null,'A',null,4,51);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Solicitud de Rectificación',null,'A',null,4,52);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Multa emitida',null,'A',null,4,55);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Resolución Archivamiento de GG',null,'A',null,4,56);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Informe Alta Dirección',null,'A',null,4,58);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Comunicaciones GL',null,'A',null,4,59);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Comunicaciones STA - IPS',null,'A',null,4,60);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Revisión de Proyectos PS',null,'A',null,4,61);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Visto Bueno Proyectos PS',null,'A',null,4,62);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Impresión de Proyecto PS',null,'A',null,4,63);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Resolución Archivamiento PS.',null,'A',null,4,64);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Envio a Tastem',null,'A',null,4,65);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Suspendido',null,'A',null,4,65);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Fiscalización Posterior PR ADM',null,'A',null,4,67);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Revisión de Oficio',null,'A',null,4,70);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Rev. Proy. Multa Coercitiva',null,'A',null,4,71);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Vº Bº Rev. Proy. Multa Coercitiva',null,'A',null,4,72);

-- Proceso STOR Medida Cautelar
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Ingreso de expedientes',null,'A',null,5,1);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Analistas',null,'A',null,5,2);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Revisión Legal',null,'A',null,5,5);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Revisión Técnica',null,'A',null,5,5);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Ingreso a sesión JARU',null,'A',null,5,6);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'En inspección',null,'A',null,5,7);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Notificación',null,'A',null,5,8);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Vº Bº final',null,'A',null,5,8);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Cumplimiento',null,'A',null,5,9);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'IPS',null,'A',null,5,11);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Impresión',null,'A',null,5,27);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Analisis externo',null,'A',null,5,30);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Comunicaciones SG',null,'A',null,5,31);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Requerimiento de información',null,'A',null,5,32);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Atención usuario',null,'A',null,5,33);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Información Oral',null,'A',null,5,34);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Archivo',null,'A',null,5,35);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Requerimiento Poder Judicial',null,'A',null,5,36);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Fedatación de Expediente',null,'A',null,5,37);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Multa Coercitiva',null,'A',null,5,38);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Verificación',null,'A',null,5,39);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Analisis de Expediente',null,'A',null,5,40);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Evaluación Legal del Informe',null,'A',null,5,41);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Revisión Proyecto de GG',null,'A',null,5,43);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Visto Bueno Proyecto de GG',null,'A',null,5,44);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Gerencia Legal',null,'A',null,5,45);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Prestado',null,'A',null,5,46);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Medida Cautelar',null,'A',null,5,47);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Multa Notificada',null,'A',null,5,48);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Multa Impugnada',null,'A',null,5,49);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Multa Consentida',null,'A',null,5,50);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Ejecutoría Coactiva',null,'A',null,5,51);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Solicitud de Rectificación',null,'A',null,5,52);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Multa emitida',null,'A',null,5,55);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Resolución Archivamiento de GG',null,'A',null,5,56);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Informe Alta Dirección',null,'A',null,5,58);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Comunicaciones GL',null,'A',null,5,59);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Comunicaciones STA - IPS',null,'A',null,5,60);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Revisión de Proyectos PS',null,'A',null,5,61);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Visto Bueno Proyectos PS',null,'A',null,5,62);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Impresión de Proyecto PS',null,'A',null,5,63);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Resolución Archivamiento PS.',null,'A',null,5,64);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Envio a Tastem',null,'A',null,5,65);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Suspendido',null,'A',null,5,65);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Fiscalización Posterior PR ADM',null,'A',null,5,67);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Revisión de Oficio',null,'A',null,5,70);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Rev. Proy. Multa Coercitiva',null,'A',null,5,71);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Vº Bº Rev. Proy. Multa Coercitiva',null,'A',null,5,72);

-- Proceso STOR-Verificacion de Cumplimiento
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Ingreso de expedientes',null,'A',null,6,1);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Analistas',null,'A',null,6,2);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Revisión Legal',null,'A',null,6,5);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Revisión Técnica',null,'A',null,6,5);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Ingreso a sesión JARU',null,'A',null,6,6);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'En inspección',null,'A',null,6,7);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Notificación',null,'A',null,6,8);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Vº Bº final',null,'A',null,6,8);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Cumplimiento',null,'A',null,6,9);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'IPS',null,'A',null,6,11);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Impresión',null,'A',null,6,27);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Analisis externo',null,'A',null,6,30);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Comunicaciones SG',null,'A',null,6,31);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Requerimiento de información',null,'A',null,6,32);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Atención usuario',null,'A',null,6,33);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Información Oral',null,'A',null,6,34);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Archivo',null,'A',null,6,35);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Requerimiento Poder Judicial',null,'A',null,6,36);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Fedatación de Expediente',null,'A',null,6,37);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Multa Coercitiva',null,'A',null,6,38);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Verificación',null,'A',null,6,39);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Analisis de Expediente',null,'A',null,6,40);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Evaluación Legal del Informe',null,'A',null,6,41);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Revisión Proyecto de GG',null,'A',null,6,43);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Visto Bueno Proyecto de GG',null,'A',null,6,44);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Gerencia Legal',null,'A',null,6,45);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Prestado',null,'A',null,6,46);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Medida Cautelar',null,'A',null,6,47);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Multa Notificada',null,'A',null,6,48);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Multa Impugnada',null,'A',null,6,49);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Multa Consentida',null,'A',null,6,50);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Ejecutoría Coactiva',null,'A',null,6,51);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Solicitud de Rectificación',null,'A',null,6,52);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Multa emitida',null,'A',null,6,55);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Resolución Archivamiento de GG',null,'A',null,6,56);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Informe Alta Dirección',null,'A',null,6,58);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Comunicaciones GL',null,'A',null,6,59);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Comunicaciones STA - IPS',null,'A',null,6,60);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Revisión de Proyectos PS',null,'A',null,6,61);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Visto Bueno Proyectos PS',null,'A',null,6,62);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Impresión de Proyecto PS',null,'A',null,6,63);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Resolución Archivamiento PS.',null,'A',null,6,64);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Envio a Tastem',null,'A',null,6,65);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Suspendido',null,'A',null,6,65);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Fiscalización Posterior PR ADM',null,'A',null,6,67);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Revisión de Oficio',null,'A',null,6,70);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Rev. Proy. Multa Coercitiva',null,'A',null,6,71);
Insert into ETAPA (IDETAPA,DESCRIPCION,CODIGO,ESTADO,DURACION,IDPROCESO,ORDEN) values (ETAPA_SEQ.NEXTVAL,'Vº Bº Rev. Proy. Multa Coercitiva',null,'A',null,6,72);


--SALAS 
Insert into SALA (IDSALA,NOMBRE,NROAPELACIONES,NROQUEJAS,NROCAUTELARES) values (SALA_SEQ.NEXTVAL,'Sala Unipersonal 1',0,0,0);
Insert into SALA (IDSALA,NOMBRE,NROAPELACIONES,NROQUEJAS,NROCAUTELARES) values (SALA_SEQ.NEXTVAL,'Sala Unipersonal 2',0,0,0);
Insert into SALA (IDSALA,NOMBRE,NROAPELACIONES,NROQUEJAS,NROCAUTELARES) values (SALA_SEQ.NEXTVAL,'Sala Colegiada',0,0,0);

--mensajeria

--Doc Recibidos

INSERT INTO gridxgridcolumna (idgrid,idgridcolumna, id)
VALUES (16,10, (select max(id)+1 from gridxgridcolumna));

INSERT INTO gridxgridcolumna (idgrid  , idgridcolumna,id)
VALUES (16,6,(select max(id)+1 from gridxgridcolumna));

--Doc cerrados
INSERT INTO gridxgridcolumna (idgrid,idgridcolumna, id)
VALUES (17,10, (select max(id)+1 from gridxgridcolumna));

INSERT INTO gridxgridcolumna (idgrid  , idgridcolumna,id)
VALUES (17,6,(select max(id)+1 from gridxgridcolumna));

--ACTUALIZACION DE LA SECUENCIA Y PARAMETROS, PARA LA NUMERACION DE EXPEDIENTE EN AMBIENTE DESARROLLO, EN EL AÑO 2011
update parametro set valor='201100000000' where tipo='prefijonroexpediente';
drop sequence exp_seq;
CREATE SEQUENCE exp_seq
MINVALUE 1
MAXVALUE 999999
START WITH 1
INCREMENT BY 1;

--ACTUALIZACION DE LA SECUENCIA Y PARAMETROS, PARA LA NUMERACION DE EXPEDIENTE EN AMBIENTE PRODUCCION, EN EL AÑO 2011
update parametro set valor='OSI201100000000' where tipo='prefijonroexpedienteproduccion';
drop sequence expedienteproduccion_seq;
CREATE SEQUENCE expedienteproduccion_seq
MINVALUE 1
MAXVALUE 999999
START WITH 1
INCREMENT BY 1;

-- query para modificar todos lo procesos al produccion.
update proceso set produccion='S';

-- elimina dato repetido en tipoenvio en mensajeria.
delete from tipoenvio where idtipoenvio in (3,4);

-- actualiza los documento recibidos de digitalizador, en el caso q ha sido rechazado por
-- QAS y tengan el estado 4
update documento d set d.accion=28 where 
iddocumento in( select d.iddocumento from documento d 
inner join usuario u on u.idusuario=d.propietario
inner join usuarioxrol ur on ur.idusuario= u.idusuario
inner join rol r on r.idrol= ur.idrol
where 3 in (r.idrol) and d.accion=4);

-- actualiza los documentos recibidos de usuario final, en el caso que sido rechazado desde 
-- otro usuario final que tengan el estado 4

update documento d set d.accion=7 
where 
d.iddocumento not in (select d.iddocumento from documento d 
                  inner join usuario u on u.idusuario=d.propietario
                  inner join usuarioxrol ur on ur.idusuario= u.idusuario
                  inner join rol r on r.idrol= ur.idrol
                  where 4 in (r.idrol) and d.accion=4) 

--agregar el campo observacionDigitalizador el tabla Documento
alter table documento add "OBSERVACIONDIGITALIZADOR" VARCHAR2(500)

--delete from mensajeria where idmensajeria not in (select idmensajeria from datos);

-- referencia

alter table mensajeria add "REFERENCIADIRECCIONCLIENTE" VARCHAR2(500);
--


--para eliminar los campos duplicados en la tabla sala
update sala set nombre='Sala Unipersonal 1' where idsala=1;
update sala set nombre='Sala Unipersonal 2' where idsala=2;
update sala set nombre='Sala Colegiada' where idsala=3;
delete from sala where idsala in(4,5,6);

-- un nuevo campo  en Mensajeria
alter table mensajeria add "IDAMBITOENVIO" NUMBER(38,0);

-- Incidencia 0000222: AJUSTE - STOR: Modificar la longitud del campo Resolución en la pestaña Información Principal
ALTER TABLE EXPEDIENTESTOR MODIFY NRORESOLUCION Varchar2(50);

--mensajeria
ALTER TABLE TIPOENVIO RENAME COLUMN TIPOENVIO2 TO NOMBRE; 
ALTER TABLE MENSAJERIA ADD (IDCLIENTE number(38,0));
ALTER TABLE MENSAJERIA ADD (IDAMBITOENVIO number(38,0));


---mensajeria  ::
Insert into GRIDCOLUMNA (IDGRIDCOLUMNA,FIELD,NAME,NORESIZE,WIDTH,HIDDEN,FORMATER,POSITION) values (GRIDCOLUMNA_SEQ.nextval,'tipoEnvio','Tipo Envio',0,'150px',0,null,30);
INSERT INTO gridxgridcolumna (idgrid,idgridcolumna, id) VALUES (16,30, (select max(id)+1 from gridxgridcolumna));
INSERT INTO gridxgridcolumna (idgrid,idgridcolumna, id) VALUES (17,30, (select max(id)+1 from gridxgridcolumna));
Insert into AMBITOENVIO (IDAMBITOENVIO,NOMAMBITOENVIO) values (AMBITOENVIO_SEQ.nextval,'LOCAL');
Insert into AMBITOENVIO (IDAMBITOENVIO,NOMAMBITOENVIO) values (AMBITOENVIO_SEQ.nextval,'NACIONAL');


-- Incidencia 0000226: Al archivar la observacion se almacena en el campo Observacion del Expediente, crearle un nuevo campo en la BD
alter table expediente add OBSERVACIONARCHIVAR VARCHAR2(500);
--mensajeria 

Insert into GRIDCOLUMNA (IDGRIDCOLUMNA,FIELD,NAME,NORESIZE,WIDTH,HIDDEN,FORMATER,POSITION) values (GRIDCOLUMNA_SEQ.nextval,'nrodocumento','Numero Documento',0,'150px',0,null,31);
INSERT INTO gridxgridcolumna (idgrid,idgridcolumna, id) VALUES (16,31, (select max(id)+1 from gridxgridcolumna));
INSERT INTO gridxgridcolumna (idgrid  , idgridcolumna,id) VALUES (17,31,(select max(id)+1 from gridxgridcolumna));
--mensajeria verificar la data  si  estan creadas  eliminar   .
delete from gridcolumnaxusuario where idgridcolumna=520947;
delete from gridxgridcolumna where idgrid=16 and idgridcolumna=6;
delete from gridxgridcolumna where idgrid=16 and idgridcolumna=17;
DELETE  from gridxgridcolumna where idgrid=17 and idgridcolumna=1;
delete from gridxgridcolumna where idgrid=17 and idgridcolumna=6;
delete from gridxgridcolumna where idgrid=17 and idgridcolumna=17;

------------------

alter table envio modify DIASDEVCARGO timestamp(6);
alter table envio modify DIASDEVOLUCION timestamp(6);
alter table envio modify DIASDISTRIBUCION timestamp(6);

---
--mensajeria usuario final 
DELETE  from gridcolumnaxusuario WHERE idgridcolumna=759200;
DELETE  from gridcolumnaxusuario WHERE idgridcolumna=145828;
select * from siged.gridxgridcolumna where idgrid=4 AND idgridcolumna=17;
select *  from siged.gridxgridcolumna where idgrid=4 AND idgridcolumna=1;
insert into  gridxgridcolumna (idgrid,idgridcolumna, id) values (4,10,(select max(id)+1 from gridxgridcolumna));
insert into  gridxgridcolumna (idgrid,idgridcolumna, id) values (4,30,(select max(id)+1 from gridxgridcolumna));

--
insert into  gridxgridcolumna (idgrid,idgridcolumna, id) values (9,32,(select max(id)+1 from gridxgridcolumna));
Insert into GRIDCOLUMNA (IDGRIDCOLUMNA,FIELD,NAME,NORESIZE,WIDTH,HIDDEN,FORMATER,POSITION) values (GRIDCOLUMNA_SEQ.nextval,'iddocumento','ID Documento',1,'150px',0,null,1);


-- Incidencia 0000226: Al archivar la observacion se almacena en el campo Observacion del Expediente, crearle un nuevo campo en la BD
alter table expediente add OBSERVACIONARCHIVAR VARCHAR2(500);

-- Incidenncia 0000266: MEJORA: Crear bandeja de Expedientes Archivados y con la opción para reabrir
alter table expediente add fechaarchivar timestamp(6);
Insert into GRID (IDGRID,NOMBRE,DESCRIPCION,CODIGO,ROL) values (18,'Expedientes Archivados',null,'18',5);
Insert into GRIDXGRIDCOLUMNA (IDGRID,IDGRIDCOLUMNA,ID) values (18,3, (SELECT MAX(ID)+1 FROM GRIDXGRIDCOLUMNA));
Insert into GRIDXGRIDCOLUMNA (IDGRID,IDGRIDCOLUMNA,ID) values (18,4, (SELECT MAX(ID)+1 FROM GRIDXGRIDCOLUMNA));
Insert into GRIDXGRIDCOLUMNA (IDGRID,IDGRIDCOLUMNA,ID) values (18,5, (SELECT MAX(ID)+1 FROM GRIDXGRIDCOLUMNA));
Insert into GRIDXGRIDCOLUMNA (IDGRID,IDGRIDCOLUMNA,ID) values (18,6, (SELECT MAX(ID)+1 FROM GRIDXGRIDCOLUMNA));
Insert into GRIDXGRIDCOLUMNA (IDGRID,IDGRIDCOLUMNA,ID) values (18,10, (SELECT MAX(ID)+1 FROM GRIDXGRIDCOLUMNA));
Insert into GRIDXGRIDCOLUMNA (IDGRID,IDGRIDCOLUMNA,ID) values (18,11, (SELECT MAX(ID)+1 FROM GRIDXGRIDCOLUMNA));
Insert into GRIDXGRIDCOLUMNA (IDGRID,IDGRIDCOLUMNA,ID) values (18,12, (SELECT MAX(ID)+1 FROM GRIDXGRIDCOLUMNA));
Insert into GRIDXGRIDCOLUMNA (IDGRID,IDGRIDCOLUMNA,ID) values (18,14, (SELECT MAX(ID)+1 FROM GRIDXGRIDCOLUMNA));
Insert into GRIDXGRIDCOLUMNA (IDGRID,IDGRIDCOLUMNA,ID) values (18,25, (SELECT MAX(ID)+1 FROM GRIDXGRIDCOLUMNA));
Insert into GRIDCOLUMNA (IDGRIDCOLUMNA,FIELD,NAME,NORESIZE,WIDTH,HIDDEN,FORMATER,POSITION) values (33,'fechaarchivar','Fecha de Archivamiento',0,'150px',0,'formatterDate',13);
insert into gridxgridcolumna(id, idgrid, idgridcolumna) values((select max(id)+1 from gridxgridcolumna), 18, 33);

-- Incidencia 0000270
alter table documento add estaenflujo CHAR(1);

ALTER TABLE DOCUMENTO ADD ("ENUMERADOR" NUMBER(38, 0));
ALTER TABLE DOCUMENTO ADD CONSTRAINT DOCUMENTO_USUARIO_FK1 FOREIGN KEY ("ENUMERADOR") REFERENCES SIGED_PREPROD1.USUARIO ("IDUSUARIO") ENABLE;
COMMENT ON COLUMN DOCUMENTO."ENUMERADOR" IS 'persona encargada de enumerar documento';
ALTER TABLE DOCUMENTO ADD ("FIRMADO" CHAR(1 BYTE));
ALTER TABLE DOCUMENTO ADD ("ENUMERADO" CHAR(1 BYTE));
COMMENT ON COLUMN DOCUMENTO."FIRMADO" IS 'Se utilizapara controlar si un documento esta firmado.';
COMMENT ON COLUMN DOCUMENTO."ENUMERADO" IS 'se utiliza para controlar si un documento esta enumerado.';

-- Incidencia 0000250
alter table suministro modify nrosuministro varchar2(15);