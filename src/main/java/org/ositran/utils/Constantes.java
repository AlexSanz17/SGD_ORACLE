package org.ositran.utils;

import java.text.SimpleDateFormat;

public class Constantes{
        public static final String AREAS_ACCESO_GENERAL="AREAS_ACCESO_GENERAL";
	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////
	public static final String AUDITORIA_OPCION_GUARDAR="Guardar";
	public static final String AUDITORIA_TIPO_REGISTRO="Registro";
	public static final String AUDITORIA_TIPO_ACTUALIZACION="Actualizacion";
        
        public static final String COLOR_DOCUMENTO_ANEXO="#58ACFA";
        public static final String COLOR_DOCUMENTO_PRINCIPAL="#2E2EFE";
        public static final String COLOR_DOCUMENTO_CARGO = "#FAAC58";
        public static final String COLOR_DOCUMENTO_MP_CARGO = "#8850ac";
	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////
	public static final String ACCION_ARCHIVAR="archivar";
	public static final String ACCION_ANULAR="anular";
	public static final String ACCION_ATENDER="atender";
	public static final String ACCION_RESPONDER="responder";
	public static final String ACCION_CONSULTAR="consultar";
	public static final String ACCION_REENVIAR = "reenviar";
	public static final String ACCION_DERIVAR="reenviar";
	public static final String ACCION_DERIVAR2="derivar";
	public static final String ACCION_APROBAR_QAS="aprobarqas";
	public static final String ACCION_DERIVAR_KEY_PROPERTY="accion.derivar.id";
	public static final String ACCION_RECHAZAR_KEY_PROPERTY="accion.rechazar.id";
	public static final String ACCION_RECHAZAR="rechazar";
	public static final String ACCION_RECHAZARUSER = "rechazaruser";
	public static final String ACCION_RECHAZARQAS = "rechazarqas";
	public static final String ACCION_REGISTRAR="registrar";
	public static final String ACCION_APROBAR="aprobar";
	public static final String ACCION_PARA_APROBAR="paraaprobar";
	public static final String ACCION_DIGITALIZAR="digitalizar";
	public static final String ACCION_INTALIO_SAS_APROBADO="Aprobado";
	public static final String ACCION_INTALIO_SAS_DESAPROBADO="Desaprobado";
	public static final String ACCION_REABRIR="reabrir";
	public static final String ACCION_OEFA="oefa";
	public static final String ACCION_COPIAR="copia";
	public static final String ACCION_APOYO="apoyo";
	public static final String ACCION_DELEGAR="delegar";
	public static final String ACCION_ENVIO_MENSAJERIA="envio_mensajeria";
	public static final String ACCION_RECEPCION_CARGO="recepcion_cargo";
	public static final String PUEDE_RECHAZAR="S";
	public static final String NO_PUEDE_RECHAZAR="N";
	public static final String ACCION_DESPACHAR="despachar";
	public static final String ACCION_NOTIFICAR="notificar";
        public static final String CONCESIONARIA_INI= "999998";
        public static final String SERIE_INI= "1";
        
        public static final String TAB_BUSQUEDA = "TAB_BUSQUEDA";

	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////
	public static final char ARCHIVO_ESTADO_DIGITALIZACION_ALFRESCO='A';
	public static final char ARCHIVO_ESTADO_DIGITALIZACION_INACTIVO='I';
	public static final char ARCHIVO_ESTADO_DIGITALIZACION_NO='N';
	public static final char ARCHIVO_ESTADO_DIGITALIZACION_RECHAZADO='R';
	public static final char ARCHIVO_ESTADO_DIGITALIZACION_YES='Y';
	public static final char ARCHIVO_BRACKET_INICIO='[';
	public static final char ARCHIVO_BRACKET_FIN=']';
	public static final Character ARCHIVO_PRINCIPAL='S';
	public static final Character ARCHIVO_NOPRINCIPAL='N';
	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////
	public static final char DOCUMENTO_DISPONIBLE='S';
	public static final char DOCUMENTO_PRINCIPAL='S';
	public static final String DOCUMENTO_DEL_EXPEDIENTE="S";
	public static final char ES_USUARIO_FINAL='S';
	public static final char DOCUMENTO_NO_DISPONIBLE='N';
	public static final char DOCUMENTO_NO_PRINCIPAL='N';
	public static final String DOCUMENTO_NO_DEL_EXPEDIENTE="N";
	public static final char NO_ES_USUARIO_FINAL='N';
	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////
	public static final char ESTADO_ACTIVO='A';
	public static final char ESTADO_ANULADO='N';
	public static final char ESTADO_INACTIVO='I';
	public static final char ESTADO_RECHAZADO='R';
	public static final char ESTADO_CERRADO='C';
	public static final char ESTADO_ATENDER='T';
        public static final char ESTADO_PENDIENTE='P';
	// ///////////////////////////////////////////////////////////////////////////
	public static final char ESTADO_LEIDO='L';
	public static final char ESTADO_NO_LEIDO='N';
	// ///////////////////////////////////////////////////////////////////////////
	public static final char ESTADO_DESPACHADO='S';
	public static final char ESTADO_NO_DESPACHADO='N';
	// ///////////////////////////////////////////////////////////////////////////
	public static final String ESTADO_CODIGO_PENDIENTE = "pendiente";
	public static final String ESTADO_CODIGO_APROBADO = "aprobado";
	public static final String ESTADO_CODIGO_RECHAZADO = "rechazado";
	public static final String ESTADO_CODIGO_DELEGADO = "delegado";
	public static final String ESTADO_CODIGO_CONCLUIDO = "concluido";
	public static final String ESTADO_CODIGO_RESPONDIDO = "respondido";
	public static final String ESTADO_CODIGO_ARCHIVADO = "archivado";
	public static final String ESTADO_CODIGO_ANULADO = "anulado";
	public static final String ESTADO_CODIGO_ATENDER = "atendido";
	// ///////////////////////////////////////////////////////////////////////////
	public static final char FLAG_ENVIO_CORREO_N='N';
	public static final char FLAG_ENVIO_CORREO_S='S';
	public static final char FLAG_USUARIO_FINAL_N='N';
	public static final char FLAG_USUARIO_FINAL_S='S';
	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////
	public static final String GENERAL_BACK_SLASH="\\";
	public static final String GENERAL_CAMBIO_LINEA="\n";
	public static final String GENERAL_SLASH="/";
	public static final String GENERAL_N="N";
	public static final String GENERAL_S="S";
	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////
	public static final String GRUPOPROCESO_ANT="ANT";
	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////
	public static final String PERFIL_MP="mp";
	public static final String PERFIL_DIG="dig";
	public static final String PERFIL_QAS="qas";
	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////
	public static final char PROCESO_PERMITE_METADATA_N='N';
	public static final char PROCESO_PERMITE_METADATA_S='S';
	public static final String PROCESO_STOR="stor";
	public static final String PROCESO_STOR_CODIGO_APELACION="apelacion";
	public static final String PROCESO_STOR_CODIGO_QUEJA="queja";
	public static final String PROCESO_STOR_CODIGO_MEDIDACAUTELAR="medida cautelar";
	public static final String PROCESO_USUARIO_STOR="stor\\";
	public static final String PROCESO_ACCESO_1="ProcAcc1";
	public static final String PROCESO_ACCESO_2="ProcAcc2";
	public static final String PROCESO_ACCESO_3="ProcAcc3";
	public static final String PROCESO_ACCESO_4="ProcAcc4";
	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////
	public static final String ROL_ADMINISTRADOR="Siged - Administrador";
	public static final String ROL_DIGITALIZADOR="Siged - Digitalizador";
	public static final String ROL_MESA_PARTES="Siged - Mesa de Partes";
	//@Danna Autor mesa de partes
	public static final String MESA_PARTES="Mesa de Partes";
	public static final String AUTOR_USUARIO_FINAL = "Usuario Final";
	//
	public static final String ROL_QAS="Siged - Control de Calidad";
	public static final String ROL_ADMINISTRADOR_SUPERVISOR="Siged - Administrador Supervisor";
	public static final String ROL_MENSAJERIA="Siged - Mensajeria";
	public static final String ROL_USUARIO_FINAL="Siged - Usuario Final";
	public static final String ROL_USUARIO_FINAL_STOR="userstor";
	public static final String DERIVAR_NORMAL="normal";
	public static final String DERIVAR_MASIVO="masivo";
        public static final String DERIVAR_MASIVO_DOC="masivoDoc";
	public static final String RECHAZAR="rechazar";
	public static final String ROL_USUARIO_SALFE_SAS="Siged - SA";
	public static final String ROL_USUARIO_ANALISTA_SAS="Siged - AL";
	public static final String ROL_USUARIO_ASESOR_SAS="Siged - ASL";
	public static final String ROL_USUARIO_SGFE_SAS="Siged - SGFE";
	public static final String ROL_USUARIO_JU_SAS="Siged - JU";
	public static final String ROL_USUARIO_JU_GA_SAS="ju-ga";
	public static final String ROL_USUARIO_JU_GS_SAS="ju-gs";
	public static final String ROL_USUARIO_JU_CO_SAS="ju-co";
	public static final String ROL_USUARIO_JU_CA_SAS="ju-ca";
	public static final String ROL_USUARIO_JU_AC_SAS="ju-ac";
	public static final String ROL_USUARIO_JU_TR_SAS="ju-tr";
	public static final String ROL_USUARIO_JU_DI_SAS="ju-di";
	public static final String ROL_USUARIO_JU_AM_SAS="ju-am";
	public static final String ROL_USUARIO_GERENTEGFE_SAS="ggfe";
	public static final String ROL_USUARIO_FINAL_SAS="sas";
	public static final String ROL_USUARIO_SCALIFICADOR_STOR="Siged - scalif";
	public static final String ROL_USUARIO_ANALISTA_STOR="Siged - ajaru";
	public static final String ROL_USUARIO_REVTEC_STOR="Siged - revtec";
	public static final String ROL_USUARIO_REVLEG_STOR="Siged - revleg";
	public static final String ROL_USUARIO_SGENERAL_STOR="Siged - sgeneral";
	public static final String ROL_USUARIO_ASALA_STOR="Siged - asala";
	public static final Character ROL_ES_JEFE='S';
	public static final Character ROL_NO_JEFE='N';
	public static final Character ROL_ENUM_AREA_SI ='S';
	public static final Character ROL_ENUM_AREA_NO ='N';
	public static final Character ROL_ENUM_GERENCIA_SI ='S';
	public static final Character ROL_ENUM_GERENCIA_NO ='N';
	public static final Character ROL_ENUM_PRESIDENCIA_SI ='S';
	public static final Character ROL_ENUM_PRESIDENCIA_NO ='N';
	public static final String ID_AUTOR_NUMERACION_GERENCIA = "ID_AUTOR_NUMERACION_GERENCIA";
	public static final String ID_AUTOR_NUMERACION_PRESIDENCIA = "ID_AUTOR_NUMERACION_PRESIDENCIA";
	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////
	public static final int NUMERO_HORAS_LABORABLESXDIA=8;
	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////
	public static final String SESSION_ALFRESCO="objAD";
	public static final String SESSION_AUDITABLE="_AUDITABLE";
	public static final String SESSION_IDDOCUMENTO="_IDDOCUMENTO";
        public static final String SESSION_IDDOCUMENTO_EMAIL="_IDDOCUMENTOEMAIL";
	public static final String SESSION_RECURSO="_RECURSO";
        public static final String SESSION_ROLCARGO="_ROLCARGO";
	public static final String SESSION_RECURSO_SAS="_RECURSO_SAS";
	public static final String SESSION_UPLOAD_LIST="_UPLOADLIST";
        public static final String SESSION_UPLOAD_LIST_EMAIL="_UPLOADLISTEMAIL";
	public static final String SESSION_USUARIO="_USUARIO";
        public static final String SESSION_USUARIO_EMAIL="_USUARIOEMAIL";
	public static final String SESSION_FORWARD_TO_URL="_URL";
	public static final String SESSION_INTALIO="tokenIntalio";
	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////
	public static final String TIPO_IDENTIFICACION_RUC="RUC";
	public static final String TIPO_IDENTIFICACION_DNI="DNI";
        public static final String TIPO_IDENTIFICACION_CE="CE";
	public static final String TIPO_IDENTIFICACION_OTRO="Otro";
	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////
	public static final String TIPO_DOCUMENTO_OTROS="Otros";
	public static final String TIPO_DOCUMENTO_MEMO="MM";
	public static final String TIPO_DOCUMENTO_MEMOAPEL="MMA";
	public static final String TIPO_DOCUMENTO_OFICIO="OF";
	public static final String TIPO_DOCUMENTO_INFTEC="IT";
	public static final String TIPO_DOCUMENTO_INFTECDESC="ITD";
	public static final String TIPO_DOCUMENTO_INFLEGDESC="ILD";
	public static final String TIPO_DOCUMENTO_PROYRES="PRS";
	public static final String TIPO_DOCUMENTO_RECRES="RR";
	public static final String TIPO_DOCUMENTO_INFTECREC="ITR";
	public static final String TIPO_DOCUMENTO_INFLEGRR="ILRR";
	public static final String TIPO_DOCUMENTO_PROYRRR="PRRR";
	public static final String TIPO_DOCUMENTO_PROYRRA="PRRA";
	public static final String TIPO_DOCUMENTO_RECAPEL="RA";
	public static final String TIPO_DOCUMENTO_INFLEGAPE="ILA";
	public static final String TIPO_DOCUMENTO_INFTECAPE="ITA";
	public static final String TIPO_DOCUMENTO_STOR_APELACION="RecApelacion";
	public static final String TIPO_DOCUMENTO_STOR_QUEJA="RecQueja";
	public static final String TIPO_DOCUMENTO_STOR_MEDIDACAUTELAR="RecMedidaCautelar";
	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////
	public static final String USUARIO_ASESORLEGAL="Siged - ASL";
	public static final String USUARIO_ANALISTALEGAL ="Siged - AL";
	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////
	public static final String FECHA_FORMATO_DIA="dd";
	public static final String FECHA_FORMATO_MES="MM";
	public static final String FECHA_FORMATO_ANIO="yyyy";
	public static final String FECHA_FORMATO_FULL="yyyy-dd-MM";
	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////
	public static final String TIPO_ACCESO_1="ProcAcc1";
	public static final String TIPO_ACCESO_2="ProcAcc2";
	public static final String TIPO_ACCESO_3="ProcAcc3";
	public static final String TIPO_ACCESO_4="ProcAcc4";
	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////
	public static final String TIPO_PROCESO_ANTIFLUJO="Flujo Documentario";
	public static final String TIPO_PROCESO_STOR="stor";
	public static final String TIPO_PROCESO_INTALIO="Intalio";
	public static final String TIPO_PROCESO_TI="ti";
	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////
	public static final String USUARIO_DIGITALIZADOR="dig";
	public static final String USUARIO_QAS="qas";
	public static final char USUARIO_STOR_RESPONSABLE_NO='N';
	public static final char USUARIO_STOR_RESPONSABLE_YES='Y';
	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////
	public static final String INICIOHORARIO="INICIOHORARIO";
	public static final String FINHORARIO="FINHORARIO";
	public static final String ACCION_DERIVAR_ID="AccionDerivarId";
	// Agregado por Germán Enríquez para el horario de envio
	public static final String INICIO_ENVIO="horaInicioEnvio";//deprecado 08-11-2011
	public static final String FIN_ENVIO="horaFinEnvio";//deprecado 08-11-2011
	public static final String INICIO_ENVIO_AREA="horaInicioEnvioArea";
	public static final String FIN_ENVIO_AREA="horaFinEnvioArea";
	public static final String DOC_PENDIENTE_NUEVO_DOCUMENTO="ND";
	public static final String DOC_PENDIENTE_DOCUMENTO_EXISTENTE="ED";
	public static final String NUEVO_DOC_MARCA_DE_AGUA="MARCADEAGUA";
	public static final Integer TIPO_NOTIFICACION_AMARILLA=1;
	public static final Integer TIPO_NOTIFICACION_ROJA=2;
	public static final Integer TIPO_NOTIFICACION_USUARIO=3;
	// Por Germán Enríquez
	public static final Integer TIPO_NOTIFICACION_INFOADICIONAL=1001;
	public static final Integer TIPO_NOTIFICACION_INSPECCIONCAMPO=1002;
	public static final Integer TIPO_NOTIFICACION_AUDIENCIACONCILIACION=1003;
	// Notificaciones STOR
	public static final Integer TIPO_NOTIFICACION_RECHAZOTECNICO=1004;
	public static final Integer TIPO_NOTIFICACION_RECHAZOLEGAL=1005;
	public static final Integer TIPO_NOTIFICACION_RECHAZO_VBRESOLUCIONST=1006;
	public static final Integer TIPO_NOTIFICACION_RECHAZO_VBRESOLUCIONSG=1007;
	public static final Integer TIPO_NOTIFICACION_RECHAZO_CAMBIOSALA=1008;
	public static final Integer TIPO_NOTIFICACION_DUENO_EXPEDIENTE=1009;
	public static final Integer TIPO_NOTIFICACION_NUEVO_DOCUMENTO=1010;
	public static final Integer TIPO_NOTIFICACION_REFERENCIA_DOCUMENTO=1011;
	public static final Integer TIPO_NOTIFICACION_DOCUMENTO_ARCHIVADO=1012;
	public static final Integer TIPO_NOTIFICACION_DOCUMENTO_OEFA=1013;
	public static final Integer TIPO_NOTIFICACION_DERIVACION=666;
	public static final Integer TIPO_NOTIFICACION_MULTIPLE= 670;
	public static final Integer TIPO_NOTIFICACION_NUMERACION_DESTINATARIO=667;
	public static final Integer TIPO_NOTIFICACION_NUMERACION_DOCUMENTOCONCOPIA=668;
	public static final Integer TIPO_NOTIFICACION_DERIVACIONCONCOPIA=669;
        public static final Integer TIPO_NOTIFICACION_DERIVACIONMULTIPLECONCOPIA=777;
	public static final Integer TIPO_NOTIFICACION_RECHAZO=314159;
	public static final Integer TIPO_NOTIFICACION_ENVIO_MENSAJERIA=665;
	public static final int TIPO_NOTIFICACION_APROBACION_QAS=555;
	public static final Integer TIPO_NOTIFICACION_FIN_APOYO = 2000;
	public static final String PARAMETRO_TIPO_NOTIFICACION="tiponotificacion";
	public static final String PARAMETRO_TIPO_MONEDA="moneda";
	public static final String PARAMETRO_TIPO_PRIORIDAD="prioridad";
	public static final String PARAMETRO_TIPO_FECHA_DESDE_BUSQUEDA_RAPIDA="fechaDesdeBusquedaRapida";
	public static final String PARAMETRO_TIPO_FECHA_HASTA_BUSQUEDA_RAPIDA="fechaHastaBusquedaRapida";
	public static final String PARAMETRO_TIPO_USUARIO_MENSAJERIA="usuario_mensajeria";
	// Asuntos STOR
	public static final String ASUNTO_APROBAR_EXPEDIENTE="RE: Expediente Aprobado";
	public static final String ASUNTO_RECHAZO_TECNICO="RE: Rechazo Tecnico";
	public static final String ASUNTO_RECHAZO_LEGAL="RE: Rechazo Legal";
	public static final String ASUNTO_CAMBIO_SALA="RE: Solicitud de Cambio de Sala";
	public static final String ASUNTO_RECHAZO_VB="RE: Visto Bueno Inconforme";
	public static final String ASUNTO_RECHAZO_CAMBIO_SALA="RE: Cambio Sala No Aprobado";
	public static final String ASUNTO_REFORMULACION_TECNICA="RE: Requiere Reformulacion Tecnica";
	public static final String ASUNTO_REFORMULACION_LEGAL="RE: Requiere Reformulacion Legal";
	public static final String ASUNTO_REFERENCIA_DOCUMENTO="RE: Documento Referenciado";
	//
	public static final String PARAMETRO_NOTIFICACION_AMARILLA="porcentajeAlerta1";
	public static final String PARAMETRO_NOTIFICACION_ROJA="porcentajeAlerta2";
	/*************************************/
	// Auditoria
	/*************************************/
	public static final String TipoDoc_RUC="RUC";
	public static final String TipoDoc_DNI="DNI";
	public static final String TipoDoc_OTRO="Otro";
	/*************************************/
	// Auditoria
	/*************************************/
	public static final String TO_Registrar="Registrar";
	public static final String TO_Modificar="Modificar";
	public static final String TO_Eliminar="Eliminar";
	public static final String TA_Adjuntar="Adjuntar Archivos";
	public static final String TM_Digitalizador="Modulo Digitalizador";
	public static final String TA_RegistrarNvoDoc_MP="Registrar Nuevo Documento";
	public static final String TM_MesaPartes="Modulo Mesa Partes";
	public static final String TA_RegistrarNvoDoc_UserFinal="Registrar Nuevo Documento";
	public static final String TM_UserFinal="Modulo Usuario Final";
	public static final String TA_AccionQAS="Actualizar Documento QAS";
	public static final String TM_QAS="Modulo QAS";
	public static final String TA_ArchivarDocs="Archivar Documentos";
	public static final String TA_ClasificarDocs="Clasificar Documentos";
	public static final String TA_AnexarUserFinal="Anexar Documentos";
	public static final String TO_Archivar="Archivar";
	public static final String TO_Clasificar="Clasificar";
	public static final String TC_Archivo="Archivo";
	public static final String TO_Reabrir = "Reabrir";
	public static final String MODULO_USUARIO_FINAL=TM_UserFinal;
	public static final String OPCION_SUBIR_REPOSITORIO="Subir al repositorio";
	public static final String OPCION_DERIVAR="Derivar";
	public static final String TO_Anexar="Anexar";
	public static final String TO_AdjuntarMetadata="Adjuntar Con Metadata";
	public static final String TM_DocAdicionales="Modulo Doc. Adic.";
	public static final String TA_RegistrarNuevaCarpeta="Nueva Carpeta";
	public static final String TA_MoverDocCarpeta="Mover Docs. Carpeta";
	public static final String TO_Mover="Mover";
	/*************************************/
	// Auditoria
	/*************************************/
	public static final String TA_DerivarUserFinal="Derivar Documento";
	public static final String TO_Derivar="derivar";
	public static final String TA_RegistrarCarpetaBusq="Nueva Carpeta Busqueda";
	public static final String TA_ActualizarCarpetaBusq="Actualizar Carpeta Busqueda";
	public static final String TA_RegistrarReemplazo="Registrar Reemplazo";
	public static final String TO_Enviar="Enviar";
	public static final String TA_RegistrarDatosDoc="Registrar Datos Doc";
	public static final String TM_Mensajeria="Modulo de Mensajeria";
	public static final String TA_DatosEnvio="Resgistrar Datos Envio";
	public static final String TA_RegistrarCargo="Registrar Cargo";
	public static final String MIS_PROCESOS="mio";
	public static final String TODOS_LOS_PROCESOS="todos";
	public static final String TODOS_MIS_PROCESOS="TODOS";
	public static final String ID_TODOS_MIS_PROCESOS="0";
	/***************************************/
	// Resuelto
	/***************************************/
	public static final int RESUELTO_ARCHIVO=1;
	public static final int RESUELTO_AMONESTACION=2;
	public static final int RESUELTO_MULTA=3;
	public static final int RESUELTO_FUNDADO=4;
	public static final int RESUELTO_INFUNDADO=5;
	public static final int RESUELTO_IMPROCEDENTE=6;
	/***************************************/
	// carpetas
	/***************************************/
	public static final String ID_CARPETA_EXPEDIENTE="0";
	// //////////////////// para numeracion interna por expdiente
	public static final String NUMERACION_NINGUNA="N";
	public static final String NUMERACION_POR_PROCESO="P";
	public static final String NUMERACION_POR_GRUPO_PROCESO="G";
	// Envio de correo electronico

	public static final String PARAMETRO_MAIL_ADMIN = "MAIL_ADMIN";
	public static final String PARAMETRO_MAIL_FILEPATH_CREACION_EXPEDIENTE = "MAIL_FILEPATH_CREACION_EXPEDIENTE";
	public static final String PARAMETRO_MAIL_FILEPATH_INGRESO_DOCUMENTO_QAS = "MAIL_FILEPATH_INGRESO_DOCUMENTO_QAS";
	public static final String PARAMETRO_MAIL_FILEPATH_INGRESO_DOCUMENTO_USERFINAL = "MAIL_FILEPATH_INGRESO_DOCUMENTO_USERFINAL";
	public static final String PARAMETRO_MAIL_FILEPATH_DOCUMENTO_REENVIAR = "MAIL_FILEPATH_DOCUMENTO_REENVIAR";
        public static final String PARAMETRO_MAIL_FILEPATH_DOCUMENTO_PARTICULAR_MTC = "MAIL_FILEPATH_DOCUMENTO_PARTICULAR_MTC";
        public static final String PARAMETRO_MAIL_FILEPATH_DOCUMENTO_PARTICULAR_CR = "MAIL_FILEPATH_DOCUMENTO_PARTICULAR_CR";
        public static final String PARAMETRO_MAIL_FILEPATH_DOCUMENTO_PARTICULAR_PCM = "MAIL_FILEPATH_DOCUMENTO_PARTICULAR_PCM";
	public static final String PARAMETRO_MAIL_FILEPATH_DOCUMENTO_RECHAZAR = "MAIL_FILEPATH_DOCUMENTO_RECHAZAR";
	public static final String PARAMETRO_MAIL_FILEPATH_DOCUMENTO_CCREENVIAR = "MAIL_FILEPATH_DOCUMENTO_CCREENVIAR";
	public static final String PARAMETRO_MAIL_FILEPATH_DOCUMENTO_PORAPROBAR = "MAIL_FILEPATH_DOCUMENTO_PORAPROBAR";
	public static final String PARAMETRO_MAIL_FILEPATH_DOCUMENTO_CCPORAPROBAR = "MAIL_FILEPATH_DOCUMENTO_CCPORAPROBAR";
	public static final String PARAMETRO_MAIL_FILEPATH_DOCUMENTO_APROBAR = "MAIL_FILEPATH_DOCUMENTO_APROBAR";
	public static final String PARAMETRO_MAIL_FILEPATH_DOCUMENTO_CCAPROBAR = "MAIL_FILEPATH_DOCUMENTO_CCAPROBAR";
	public static final String PARAMETRO_MAIL_FILEPATH_ALARMA_AMARILLA = "MAIL_FILEPATH_ALARMA_AMARILLA";
	public static final String PARAMETRO_MAIL_FILEPATH_ALARMA_ROJA = "MAIL_FILEPATH_ALARMA_ROJA";
	public static final String PARAMETRO_MAIL_FILEPATH_DOCUMENTO_ARCHIVADO = "MAIL_FILEPATH_DOCUMENTO_ARCHIVADO";
	public static final String PARAMETRO_MAIL_FILEPATH_ENUMERACION_DESTINATARIO = "MAIL_FILEPATH_ENUMERACION_DESTINATARIO";
	public static final String PARAMETRO_MAIL_FILEPATH_ENUMERACION_COPIA = "MAIL_FILEPATH_ENUMERACION_COPIA";
	public static final String PARAMETRO_MAIL_FILEPATH_LECTURA_EXPEDIENTE = "MAIL_FILEPATH_LECTURA_EXPEDIENTE";

	public static final String PARAMETRO_MAIL_FILEPATH_DOCUMENTO_MULTIPLE = "MAIL_FILEPATH_DOCUMENTO_MULTIPLE";
	// Tipo de Notificaciones de SAS
	/***************************************/
	public static final int TIPO_NOTIFICACION_Aprobado = 1020;
	public static final int TIPO_NOTIFICACION_Desaprobado = 1021;
	public static final int TIPO_NOTIFICACION_Resgistrado = 1022;
	// Envio de correo electronico
	/*
	 * public static final String EMAIL_ASUNTO =
	 * "Aviso de Nuevo Documento %s - %s"; public static final String
	 * EMAIL_CONTENIDO =
	 * "El informamos que el documento %s - %s ha sido anexado al expediente %s, perteneciente al proceso %s."
	 * ;
	 */
	// Constantes de Upload
	public static final String UPLOAD_ARCHIVO1 = "upload1";
	public static final String UPLOAD_ARCHIVO2 = "upload2";
	public static final String UPLOAD_ARCHIVO3 = "upload3";
	public static final String UPLOAD_ARCHIVO4 = "upload4";
	public static final String ORIGEN_EXPEDIENTE_NUEVO = "N";
	public static final String ORIGEN_EXPEDIENTE_EXISTENTE = "E";
	public static final String PARAMETRO_ORIGEN_EXPEDIENTE = "TipoPorcesoNuevoDocumento";
	public static final String VER_SEGUIMIENTO_SI = "S";
	public static final String VER_SEGUIMIENTO_NO = "N";
	// Tipos de GRID
	public static final String TIPO_GRID_DOCUMENTO = "0";
	public static final String TIPO_GRID_ARCHIVOPENDIENTE = "1";
	public static final String TIPO_GRID_DOCUMENTOENVIADO = "2";
	public static final String TIPO_GRID_MENSAJERIA = "3";
	public static final String TIPO_GRID_NOTIFICACION = "4";
	public static final String TIPO_GRID_PROCESO = "5";
	public static final String TIPO_GRID_EXPEDIENTE = "6";
	public static final String TIPO_GRID_DOCUMENTO_RECIBIDO = "7";
	public static final String TIPO_GRID_CARPETASBUSQUEDA = "8";
	public static final String TIPO_GRID_INFORMATIVO = "9";
	public static final String TIPO_GRID_REPORTE_RESUMEN = "10";
	public static final String TIPO_GRID_REPORTE_DETALLADO = "11";
	public static final String TIPO_GRID_DETALLES_REPORTE = "12";
	public static final String TIPO_GRID_QAS_DIGITALIZADOS = "14";
	public static final String TIPO_GRID_DIG_DOC_INGRESADOS = "15";
	public static final String TIPO_GRID_MENSAJERIA_RECIBIDOS = "16";
	public static final String TIPO_GRID_MENSAJERIA_CERRADOS = "17";
	public static final String TIPO_GRID_DOCUMENTOS_ARCHIVADOS = "18";
	public static final String TIPO_GRID_SEGUIMIENTO = "22";
        public static final String TIPO_GRID_PENDIENTES = "24";
        public static final String TIPO_GRID_ANULADOS = "25";
        public static final String TIPO_GRID_PARA_FIRMAR = "26";
        public static final String TIPO_GRID_RECEPCION_VIRTUAL = "27";
        public static final String TIPO_GRID_DESPACHO_VIRTUAL = "28";
        public static final String TIPO_GRID_MI_RECEPCION_VIRTUAL = "29";
        public static final String TIPO_GRID_MI_LEGAJO = "30";
        public static final String TIPO_GRID_LEGAJO_COMPARTIDO = "31";
        public static final String TIPO_GRID_RECEPCION_VIRTUAL_OBSERVADOS = "32";

	// Tipos de GRID
	public static final String BUSQUEDA_AVANZADA_ADICIONAL = "frmBusquedaAvanzadaAdicional";
	public static final String BUSQUEDA_AVANZADA_BASICO = "frmBusquedaAvanzadaBasico";
	public static final String MENSAJE_UPLOAD = "msgUpload";
	public static final String RECURSO_DOC_RECIBIDOS = "UsuFinMnuDocRec";
	public static final String RECURSO_MIS_EXPEDIENTES = "UsuFinMnuMisExp";
	public static final String RECURSO_NOTIFICACIONES = "UsuFinMnuNotif";
	public static final String RECURSO_INFORMATIVOS = "UsuFinMnuInfo";
        public static final String RECURSO_FIRMA = "UsuFinMnuFirma";
        public static final String RECURSO_RECEPCION_VIRTUAL_OBSERVADOS = "UsuFinMnuRecObs";
        
        
        public static final String RECURSO_RECEPCION_VIRTUAL = "UsuFinMnuRecVirtual";
        public static final String RECURSO_DESPACHO_VIRTUAL = "UsuFinMnuDesVirtual";
        public static final String RECURSO_MIS_RECEPCION_VIRTUAL = "UsuFinMnuMisRecVirtual";
        
        public static final String RECURSO_DOC_REGISTADOS = "MPMnuDocReg";
	public static final String RECURSO_DOC_DIGITALIZADOS = "QASMnuDigitalizados";
	public static final String RECURSO_DOC_DIGITALIZADOR = "DigMnuDocIng";
	public static final String arrUnreadMenu[] = {RECURSO_DOC_REGISTADOS, RECURSO_NOTIFICACIONES, RECURSO_INFORMATIVOS, RECURSO_DOC_DIGITALIZADOS, RECURSO_DOC_DIGITALIZADOR, RECURSO_DOC_RECIBIDOS, RECURSO_MIS_EXPEDIENTES, RECURSO_FIRMA, RECURSO_MIS_RECEPCION_VIRTUAL, RECURSO_RECEPCION_VIRTUAL_OBSERVADOS};
	public static final String LOCATION_DOCUMENTOS_RECIBIDOS = "LDR";
	public static final String LOCATION_MIS_EXPEDIENTES = "LME";
	public static final char ESTAENFLUJO_S = 'S';
	public static final char ESTAENFLUJO_N = 'N';
	public static final char ENVIADO_OEFA_S = 'S';
	public static final char ENVIADO_OEFA_N = 'N';
	/**
	 * Codigos de error estandar para todo el Siged
	 */
	public static final String ERROR_ALFRESCO = "202";
	public static final String ERROR_INTALIO = "303";
	public static final String UNIDAD_PRESIDENCIA = "Presidencia";
	public static final String NUMERACION_AUTOMATICA = "A";
	public static final String REPORTE_RESUMEN = "Resumen";
	public static final String REPORTE_DETALLE = "Detalle";
	public static final String COMBO_PROCESOCLASIFICACION_PARAMETRO = "procesoClasificacion";
	public static final String COMBO_CLASIFICAR_PARAMETRO = "clasificar";
	public static final String EXPEDIENTE_ESTADO_ACTIVO = "Activo";
	public static final String EXPEDIENTE_ESTADO_ARCHIVADO = "Archivado";
	public static final String EXPEDIENTE_ESTADO_ANULADO = "Anulado";
	public static final String NRODCUMENTO_SIN_NUMERO = "SN";
	public static final String FORMATO_FECHA = "dd/MM/yyyy";
	public static final SimpleDateFormat FORAMATEADOR_FECHA = new SimpleDateFormat(FORMATO_FECHA);
	public static final String NUEVO = "N";
	public static final int CONFIGNOTIFMAIL_MAX_LENGTH = 5;
	public static final int CONFIGNOTIFMAIL_CREACION_EXPEDIENTE = 0;
	public static final int CONFIGNOTIFMAIL_CREACION_EXPEDIENTE_BEGIN = 0;
	public static final int CONFIGNOTIFMAIL_CREACION_EXPEDIENTE_END = 0;
	public static final int CONFIGNOTIFMAIL_INGRESO_DOCUMENTO_QAS = 1;
	public static final int CONFIGNOTIFMAIL_INGRESO_DOCUMENTO_USERFINAL = 2;
	public static final int CONFIGNOTIFMAIL_ALARMA_AMARILLA = 3;
	public static final int CONFIGNOTIFMAIL_ALARMA_AMARILLA_BEGIN = 1;
	public static final int CONFIGNOTIFMAIL_ALARMA_AMARILLA_END = 2;
	public static final int CONFIGNOTIFMAIL_ALARMA_ROJA = 4;
	public static final int CONFIGNOTIFMAIL_ALARMA_ROJA_BEGIN = 3;
	public static final int CONFIGNOTIFMAIL_ALARMA_ROJA_END = 4;
	public static final int CONFIGNOTIFMAIL_DOCUMENTO_ARCHIVADO = 5;
	public static final int CONFIGNOTIFMAIL_DOCUMENTO_OEFA = 6;
	public static final int CONFIGNOTIFMAIL_DOCUMENTO_REENVIAR = 7;
        public static final int CONFIGNOTIFMAIL_DOCUMENTO_PARTICULAR_MTC = 20;
        public static final int CONFIGNOTIFMAIL_DOCUMENTO_PARTICULAR_CR = 21;
        public static final int CONFIGNOTIFMAIL_DOCUMENTO_PARTICULAR_PCM = 22;
	public static final int CONFIGNOTIFMAIL_DOCUMENTO_PORAPROBAR = 8;
	public static final int CONFIGNOTIFMAIL_DOCUMENTO_APROBAR = 9;
	public static final int CONFIGNOTIFMAIL_DOCUMENTO_RECHAZAR = 99;
	public static final int CONFIGNOTIFMAIL_DOCUMENTO_CCREENVIAR = 10;
	public static final int CONFIGNOTIFMAIL_DOCUMENTO_CCPORAPROBAR = 11;
	public static final int CONFIGNOTIFMAIL_DOCUMENTO_CCAPROBAR = 12;
	public static final int CONFIGNOTIFMAIL_ENUMERACION_DESTINATARIO = 13;
	public static final int CONFIGNOTIFMAIL_ENUMERACION_COPIA = 14;
	public static final int CONFIGNOTIFMAIL_LECTURA_EXPEDIENTE = 15;
	public static final int CONFIGNOTIFMAIL_DOCUMENTO_MULTIPLE = 16;
	public enum ConfigNotifMailDestinatario {CreacionExpediente, AlarmaAmarillaRemitente, AlarmaAmarillaDestinatario, AlarmaRojaRemitente, AlarmaRojaDestinatario};

	public static final String MAX_SIZE_FILE = "MAX_SIZE_FILE";
	//Omar
	/*public static final String RUTA_PADRE_ALFRESCO="Expedientes Osinergmin/";
   public static final String RUTA_PADRE_ALFRESCO_COMPLETA="/Company Home/Expedientes Osinergmin/";*/
	/*public static final String RUTA_PADRE_ALFRESCO="Temp/";
   public static final String RUTA_PADRE_ALFRESCO_COMPLETA="/Company Home/Temp/";*/
	public static final char OPERACION_CREATE = 'C';
	public static final char OPERACION_UPDATE = 'U';
	/*******************************HORAS DE ENTRADA Y SALIDA*********************************/
	public static final long MILISEGUNDOS_DIA = 86400000l;
	public static final int HORA_ENTRADA = 8;
	public static final int MINUTOS_ENTRADA = 30;
	public static final int HORA_SALIDA = 18;
	public static final int MINUTOS_SALIDA = 0;
	/*****************************************************************************************/
	// sancionador
	/*****************************************************************************************/
	public static final char DOCUMENTO_FUERA_DE_PLAZO = 'F';
	public static final char DOCUMENTO_DENTRO_PLAZO = 'D';
	public static final char ACTIVIDAD_DP = 'D';
	public static final char ACTIVIDAD_FP = 'F';
	/*****************************************************************************************/
	public static final String SIN_NUMERACION = "X";
	public static final String TIPO_NUMERACION_MANUAL = "M";
	public static final String TIPO_NUMERACION_AUTOMATICA = "A";
	public static final String TIPO_NUMERACION_SIN_NUMERACION = "S";
	/*****************************************************************************************/
	public static final String SIN_NUMERO = "s/n";
	public static final String TIPO_DOCUMENTO_TICKET = "Ticket";
	public static final String RUC_OSINERG1 = "20376082114";
	public static final String PARAMETRO_TIPO_PREFIJONROEXPEDIENTE = "prefijonroexpediente";
	public static final String PARAMETRO_TIPO_PREFIJONROEXPEDIENTEPRODUCCION = "prefijonroexpedienteproduccion";
        public static final String PARAMETRO_TIPO_PREFIJONROLEGAJOPRODUCCION = "prefijonrolegajoproduccion";
        public static final String PARAMETRO_TIPO_PREFIJONROTRAMITEPRODUCCION = "prefijonrotramiteproduccion";
	public static final Integer EXPEDIENTE_FIRST_ID = 1;
	public static final String PARAMETRO_TIPO_BOOKMARKFIRMA = "bookmarkFirma";
	public static final String Usuario_SECRETARIA_ALFE = "MGUILLEN";
	public static final String TIPO_PROCESO_PILOTO = "tipoProcesoPiloto";
	public static final String TIPO_PROCESO_PRODUCCION = "tipoProcesoProduccion";
	public static final String TIPO_ESTADO_PROCESO = "proceso";
	/*****************************************************************************************/
	//tipos de destinatarios externos: destinatario y concopia
	public static final char TIPO_EXTERNO_DESTINATARIO = 'D';
	public static final char TIPO_EXTERNO_CONCONPIA = 'C';
	public static final char TIPO_EXTERNO = 'E';
	public static final char FAVORITO_TIPOCONTACTO_LISTA = 'L';
	public static final char FAVORITO_TIPOCONTACTO_USUARIO = 'U';
	public static final Integer CLIENTE_OSINERGMIN_ID = 999999;
	/**Tipos de origen para la trazabilidad de copias-----------------------------------------*/
	public static final Character TIPO_ORIGEN_TRAZADOCUMENTO = 'D';
	public static final Character TIPO_ORIGEN_TRAZACOPIA = 'C';
	/**---------------------------------------------------------------------------------------*/
	public static final String ENCRIPTAR = "ENC";
	public static final String DESENCRIPTAR = "DES";
	public static final Character DESTINATARIOEXTERNO = 'E';
	public static final Character DESTINATARIOINTERNO = 'I';
	//aprobaciones
	public static final Character Si = 'S';
	public static final Character No = 'N';

	public static final int DO_ENUMERAR = 1;
	public static final int DO_FIRMAR = 2;
	public static final int DO_ENUMERAR_FIRMAR = 3;

	// ///////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////
	public static final String CODIGO_COMUNICACIONES_INT = "CI";
        public static final String CODIGO_COMUNICACIONES_DE = "DE";
        public static final String CODIGO_COMUNICACIONES_TUPA = "TUPA";
	public static final String CODIGO_FLUJO_DOC = "ANT";

	public static final String CODIGO_PROVEIDO_TRANSFERENCIA = "transferir";
        
        public static final String CODIGO_PROVEIDO_FIRMAR = "9";

	/*************************************/
	// Filtros Reportes y Consulta APN
	/*************************************/
	public static final String TODOS_TIPO_DOCUMENTO="TODOS";
	public static final String ID_TIPO_DOCUMENTO="0";
	public static final String TODOS_AREA="TODOS";
	public static final String ID_AREA="0";
	public static final String TODOS_PRIORIDAD="TODOS";
	public static final String ID_PRIORIDAD="-1";
        public static final String PRIORIDAD_NORMAL= "1";

	public static final Integer CODIGO_AREA_GERENCIA = 18;
	public static final Integer CODIGO_AREA_PRESIDENCIA = 13;
	public static final Integer CODIGO_AREA_SECRETARIA_DIRECTORIO = 275;
        public static final Integer CODIGO_AREA_GAU = 64;
	/***********************/
	//Tipo de Envio: TABLA DOCUMENTOENVIO
	/***********************/
	public static final String TIPO_ENVIO_TRANSFERIR="T";
	public static final String TIPO_ENVIO_MULTIPLE="M";
	public static final String TIPO_ENVIO_NOTIFICAR="N";
	public static final String SIN_PLAZO = "sinPlazo";

	/*********************/
	//Interfaz Gerencial
	/*********************/
	public static final String SOPCION_TRANSFERIR="transferir";
	public static final String SOPCION_MULTIPLE="multiple";
	public static final String ROL_INTERFAZ_GERENCIAL="Visor Gerencial";

	public static final String PARAMETRO_TIPO_DESCARGAR_DOCUMENTOS_PENDIENTES_AREA = "DESCARGAR_DOCUMENTOS_PENDIENTES_AREA";
	public static final String DOCUMENTOS_PENDIENTES_GRUPO_AREA = "DOCUMENTOS_PENDIENTES_GRUPO_AREA";

	public static final String FORMATO_NUMERACION_GERENCIA = "FORMATO_NUMERACION_GERENCIA";

	public static final String SERVICIO_FEDATARIO = "SERVICIO_FEDATARIO";

	public static final String SERVICIO_CERTIFICACION_DE_FIRMAS = "2";

	public static final String TIPO_DOCUMENTO_CARTA_CLIENTE = "3";
	public static final String TIPO_DOCUMENTO_OFICIO_CLIENTE = "15";
        
        public static final String TIPO_DOCUMENTO_HOJA_RECLAMACIONES = "414";
	public static final String TIPO_DOCUMENTO_SOLICITUD_INFORMACION = "376";
        
	public static final String ID_PROCESO_DOCUMENTOS_EXTERNOS = "855";
	public static final String AREAS_CONSULTA_EXTERNOS_TRAMITE = "AREAS_CONSULTA_EXTERNOS_TRAMITE";
	public static final String DOCUMENTOS_CONSULTA_EXTERNOS_TRAMITE = "DOCUMENTOS_CONSULTA_EXTERNOS_TRAMITE";
	public static final String PROCESOS_CONSULTA_EXTERNOS_TRAMITE = "PROCESOS_CONSULTA_EXTERNOS_TRAMITE";
	public static final String PROCESOS_CONSULTA_INTERNOS_TRAMITE =  "PROCESOS_CONSULTA_INTERNOS_TRAMITE";
	public static final String DOCUMENTOS_CONSULTA_INTERNOS_TRAMITE = "DOCUMENTOS_CONSULTA_INTERNOS_TRAMITE";

	public static final String MTC_TIP_DOCUMENTO = "MTC_TIP_DOCUMENTO";
	public static final String MTC_AREA = "MTC_AREA";
	public static final String CRITERIOS_CONFIDENCIAL = "CRITERIOS_CONFIDENCIAL";
	public static final String CRITERIOS_CONFIDENCIAL_MATERIA_EXCEPTUADA = "05";
        
        public static final String TIPO_INFORME_CONJUNTO = "396";
        public static final String TIPO_MEMORANDO_CONJUNTO = "442";
        public static final String PARAMETRO_UNIDAD_INFORME_CONJUNTO = "UNIDAD_INFORME_CONJUNTO";
        public static final String PARAMETRO_TIPOS_DE_ADJUNTOS_MP = "TIPOS_DE_ADJUNTOS_MP";
        public static final String PARAMETRO_ADJUNTO_COPIA_ORIGINAL = "ADJUNTO_COPIA_ORIGINAL";
        public static final Integer TIPO_INSTITUCION_PERSONA = 999999;
        
        public static final Integer UNIDAD_TRAMITE = 164; 
        public static final String COD_TIPOCLIENTE_INSTITUCION = "0";
        public static final String COD_TIPOCLIENTE_PERSONA_NATUTAL = "1";
        public static final String COD_TIPOCLIENTE_EMPRESA = "2";
        
        public static final String COD_TRAMITE_INTERNO = "0";
        public static final String COD_TRAMITE_EXTERNO = "1";
        
        public static final String COD_TRAMITE_INTERNO_AREAS = "0";
        public static final String COD_TRAMITE_EXTERNO_INSTITUCION = "1";
        public static final String COD_TRAMITE_INTERNO_INSTITUCION = "0";
        
        public static final String COD_PERSONA_JURIDICA_INSTITUCION = "1";
        public static final String COD_PERSONA_JURIDICA_EMPRESA = "2";
        public static final String COD_PERSONA_NATURAL = "3";
        
        public static final String COD_TIPODOCUMENTO_402 = "402";
        public static final String COD_TIPODOCUMENTO_404 = "404";
        public static final String COD_TIPODOCUMENTO_405 = "405";
        
        public static final Integer COD_TIPODOCUMENTO_REQUERIMIENTO_TRIBUTARIO = 460;
        
        public static final String DIAS_LIMITE_AMBAR = "DIAS_LIMITE_AMBAR";
        
        public static final String COD_SICOR_MATERIA = "MATERIA";
        public static final String COD_SICOR_INFRAESTRUCTURA = "INFRAESTRUCTURA";
        
        public static final Integer COD_CARGO_SECRETARIA = 4;
        public static final Integer COD_CARGO_ENCARGADO = 7;
        
        public static final Integer COD_ROL_LOCADOR = 5;
        public static final Integer COD_ROL_TRAMITE = 3;
        public static final Integer COD_ROL_MENSAJERIA = 6;
        
        public static final Integer DOCUMENTO_FINAL = 1;
        public static final Integer DOCUMENTO_PROYECTO = 2;  
        
        public static final String ESTADOS_PIDE = "ESTADOS_PIDE";
        
        public static final String URL_PIDE_TRAMITE_DESARROLLO = "http://200.48.76.125/wsiopidetramite/IOTramite?wsdl";
        public static final String URL_PIDE_TRAMITE_PRODUCCION = "https://ws3.pide.gob.pe/services/PcmIMgdTramite?wsdl";
        public static final String URL_PIDE_TRAMITE_OSITRAN    = "http://100.134.1.191:1050/wsiotramitesgd/Tramite?wsdl"; 
      
        public static final String URL_PIDE_RUC_DESARROLLO = "http://200.48.76.125/wsentidad/Entidad?wsdl";
        public static final String URL_PIDE_RUC_PRODUCCION = "https://ws3.pide.gob.pe/services/PcmIMgdEntidad?wsdl";
        
        public static final String URL_PIDE_CUO_PRODUCCION = "https://ws3.pide.gob.pe/services/PcmCuo?wsdl";
        
        public static final String AMBIENTE_WS_PIDE_RUC = "D";     //D - ORIGINAL
        public static final String AMBIENTE_WS_PIDE_TRAMIE = "O";  //O - ORIGINAL
        
        public static final Integer SOLICITUD_INFORMACION_PUBLICA = 376;
        public static final Integer HOJA_RECLAMACIONES = 414;
        
        public static final String KEY_SGD = "KEY_SGD";
        
        public static final String USUARIO_MENSAJERIA_VIRTUAL = "MENSAJERIAVIRTUAL";
        public static final String COD_TIPODOCUMENTO_OFICIO_CIRCULAR = "327";
}