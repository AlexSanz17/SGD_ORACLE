-- mensajeria modificando el atributo DIASDISTRIBUICION por tipo TIMESTAMP
alter table envio modify DIASDEVCARGO timestamp(6);
alter table envio modify DIASDEVOLUCION timestamp(6);
alter table envio modify DIASDISTRIBUCION timestamp(6);
ALTER TABLE ENVIO RENAME COLUMN "DIASDISTRIBUICION" TO "DIASDISTRIBUCION" ;

--agregar el campo observacionDigitalizador el tabla Documento
alter table documento add "OBSERVACIONDIGITALIZADOR" VARCHAR2(500);

-- referencia

alter table mensajeria add "REFERENCIADIRECCIONCLIENTE" VARCHAR2(500);

--para eliminar los campos duplicados en la tabla sala
update sala set nombre='Sala Unipersonal 1' where idsala=1;
update sala set nombre='Sala Unipersonal 2' where idsala=2;
update sala set nombre='Sala Colegiada' where idsala=3;

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
--Insert into AMBITOENVIO (IDAMBITOENVIO,NOMAMBITOENVIO) values (AMBITOENVIO_SEQ.nextval,'LOCAL');
--Insert into AMBITOENVIO (IDAMBITOENVIO,NOMAMBITOENVIO) values (AMBITOENVIO_SEQ.nextval,'NACIONAL');


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
delete from gridxgridcolumna where idgrid=17 and idgridcolumna=1;
delete from gridxgridcolumna where idgrid=17 and idgridcolumna=6;
delete from gridxgridcolumna where idgrid=17 and idgridcolumna=17;

-- eliminacion de salas duplicadas en produccion
update expedientestor set sala=1 where sala=4;
update expedientestor set sala=2 where sala=5;
update expedientestor set sala=3 where sala=6;
delete from sala where idsala in (4,5,6);