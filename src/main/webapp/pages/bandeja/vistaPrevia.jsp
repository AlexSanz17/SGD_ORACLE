<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
	<head>
		<title>Tramite Documentario</title>
		<link href="../../css/estilo.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../../js/form.js"> </script>
		<script type="text/javascript" src="../../js/general.js"></script>
		<link rel="stylesheet" type="text/css" media="all"
			href="../../css/calendar-green.css" />
		<script type="text/javascript" src="../../js/calendar.js"></script>
		<script type="text/javascript" src="../../js/calendar-es.js"></script>
		<script type="text/javascript" src="../../js/calendar-setup.js"></script>
		
		<script type="text/javascript">
				function Abrir_ventana (pagina) {
				var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=800, height=700, top=50, left=200";
				window.open(pagina,"",opciones);
				}
				
				function acepta(){
				window.opener.location.href="../../pages/bandeja/docRecibidos.html"
				window.close();
				
				}
				
				</script>
		
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<style type="text/css">
		<!--
		body {
			background-color: #DDEDFF;
		}
		-->
		</style>
	</head>

	<body>
		<form>
			<table width="103%">
				<tr>
					<td height="14" colspan="2"></td>
				</tr>
				<tr>
					<td height="13" colspan="2" class="header">
					<div align="center">Vista Previa</div>
					</td>
				</tr>
			
				<tr>
					<td height="13" colspan="2" class="header"></td>
				</tr>
			
			
				<tr>
					<td height="14" colspan="2">
					<table width="75%" height="148" border="1" style="border-style: solid"
						align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
						<tr class="altRow2" style="cursor: hand">
							<td width="11%" height="9"><center></center></td>
						</tr>
						<tr class="altRow2" style="cursor: hand">
							<td width="11%" height="59"><center> <textarea
								name="comentario" cols="92" rows="20" readonly>
				  Sr. Ricardo Flores 
			      El motivo de este correo es para hacer recordar que el día 
			      de mañana tenemos la reunión pactada sobre Intalio, los puntos 
			      a revisar son:  
				  - Avances que se tiene con Intalio 
				  - Xform como alternativa de construcción de los interfaces
				  - Seguridad de intalio y de APN 
				  - Intalacion del Alfresco en APN  
				  - Seguridad de Alfresco 
				  - Cronograma de trabajo y fijar objetivos puntuales
				  - Revisión de prototipos 
				  Lugar: Sala de Reuniones - 7mo Piso 
				  Hora: 3 pm  
				  Saludos Cordiales
				  Dante Gómez
				  STConsulting SAC.
					   </textarea> </center></td>
						</tr>
			
					</table>
					</td>
				</tr>
			
				<tr>
					<td colspan="2"></td>
				</tr>
				<tr>
					<td height="14" colspan="2"></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="button" name="button"
						value="Aceptar" class="button" onClick="acepta()" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="submit"
						name="button2" value="Cancelar" class="button"
						onClick="window.close()" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				</tr>
			
			
				<tr align="center">
					<td colspan="2" align="center">&nbsp;</td>
				</tr>
			</table>
		</form>
	</body>
</html>