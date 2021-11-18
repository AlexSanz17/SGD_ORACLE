<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax"%>

<html>
   <head>
      <title>Tramite Documentario</title>


      <link href="css/estilo.css" rel="stylesheet" type="text/css">

      <script language="javascript" src="js/form.js"> </script>
      <script language="Javascript" src="js/general.js"></script>

      <script type='text/javascript' src='./dwr/engine.js'> </script>
      <script type='text/javascript' src='./dwr/util.js'> </script>
      <script type='text/javascript' src='./dwr/interface/toolDwr.js'> </script>

      <!-- Begin Scrips STOR-->
      <script language="JavaScript">
         function reformulacionTecnicaLegal(){
            document.forms[0].action = '/siged/doStor_reformulacionTecnicaLegal.action' ;
            document.forms[0].submit();
            window.close();
         }

         function enabledReformulacionTecnica(){
            var destinatario;
            var revisortecnico;
            //txtRefTec = document.getElementById('revisortecnico');

            chkRefTec = document.getElementById('reformulaciontecnica');
            /*if(txtRefTec.disabled){
          txtRefTec.disabled=false;
          //txtRefTec.readonly=false;
       }else{
          txtRefTec.disabled=true;
          //txtRefTec.readonly=true;
       }*/
            if(!chkRefTec.checked){
               //alert("jajaja");
               //chkRefTec.value='si';
               destinatario = "";

            }else{
               //chkRefTec.value='no';

               destinatario = dwr.util.getValue("destinatario");

               if(destinatario.valueOf().length==0){
                  destinatario = dwr.util.getValue("revisortecnico");
                  dwr.util.setValue("destinatario",destinatario);
               }else{
                  destinatario = dwr.util.getValue("destinatario");
                  revisortecnico = dwr.util.getValue("revisortecnico");
                  destinatario = destinatario+","+revisortecnico;
                  dwr.util.setValue("destinatario",destinatario);
               }
            }


            //alert(destinatario);

         }

         function enabledReformulacionLegal(){

            //txtRefLeg = document.getElementById('revisorlegal');
            /*chkRefLeg = document.getElementById('reformulacionlegal');
       if(txtRefLeg.disabled){
          txtRefLeg.disabled=false;
          txtRefLeg.readonly=false;
       }else{
          txtRefLeg.disabled=true;
          txtRefLeg.readonly=true;
       }*/
            /*if(chkRefLeg.checked){
          chkRefLeg.value='si';
       }else{
          chkRefLeg.value='no';
       }*/
            var destinatario;
            var revisorlegal;
            destinatario = dwr.util.getValue("destinatario");
            if(destinatario.valueOf().length==0){
               destinatario = dwr.util.getValue("revisorlegal");
               dwr.util.setValue("destinatario",destinatario);
            }else{
               destinatario = dwr.util.getValue("destinatario");
               revisorlegal = dwr.util.getValue("revisorlegal");
               destinatario = destinatario+", "+revisorlegal;
               dwr.util.setValue("destinatario",destinatario);
            }
            //alert(destinatario);
         }

         function refrescar() {
            //alert('refrescar ');
            window.opener.parent.location.href ='/siged/inicio.action?sTipoFrame=grid';
            window.opener.parent.frames["mainFrame"].location.href = "/siged/doBody.action";
            window.close();
         }
      </script>
      <!-- End Scrips STOR-->

   </head>


   <body rigthmargin="0" leftmargin="0" topmargin="0"
         <s:if test="refrescar!=null">
            onload="refrescar()"
         </s:if>
         >

      <form>
         <s:hidden name="idDocumento" />
         <s:hidden id="revisortecnico" name="revisortecnico" />
         <s:hidden id="revisorlegal" name="revisorlegal" />

         <table width="100%">
            <tbody>
               <tr>
                  <td colspan="2" height="14"> </td>
               </tr>
               <tr>
                  <td colspan="2" class="header" height="13">
                     <div align="center">El expediente sera rechazado</div>
                  </td>
               </tr>
               <tr>
                  <td colspan="2" class="header" height="13"></td>
               </tr>
               <tr>
                  <td colspan="2" height="14">
                     <table style="border-style: solid;" align="center" bgcolor="#bfd9f1" border="1" bordercolor="#669bcd" height="283" width="58%">

                        <tbody>

                           <tr class="altRow2" style="">

                              <td height="9" width="11%">
                                 <center> Destinatario </center>

                              </td>

                           </tr>

                           <tr class="altRow2" style="">

                              <td height="68" width="11%">

                                 <table align="center" border="0">

                                    <tbody>

                                       <tr>

                                          <td width="60">Para: </td>

                                          <td>
                                             <!--<input name="para" class="cajaMontoTotal" value="Francisco Roda" size="40" disabled="disabled" type="text">-->
                                             <s:textfield id="destinatario" name="destinatario" cssClass="cajaMontoTotal" size="40" readonly="true" disabled="true" />
                                          </td>

                                       </tr>

                                       <tr>

                                          <td width="60">Asunto: </td>
                                          <td>
                                             <!--<input name="asunto" class="cajaMontoTotal" value="Re Carta dirigida a Gerente de Sistemas/Coordinaci&oacute;n" size="80" type="text">-->
                                             <s:textfield name="asunto" cssClass="cajaMontoTotal" size="80"/>
                                          </td>

                                       </tr>
                                       <tr>

                                          <td width="60">Motivo: </td>
                                          <td>
                                             <!--<input name="asunto" class="cajaMontoTotal" value="Re Carta dirigida a Gerente de Sistemas/Coordinaci&oacute;n" size="80" type="text">-->
                                             <s:checkbox id="reformulaciontecnica" name="reformulaciontecnica" onclick="enabledReformulacionTecnica()">Reformulacion Técnica</s:checkbox>
                                             &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                                             <s:checkbox id="reformulacionlegal" name="reformulacionlegal" onclick="enabledReformulacionLegal()">Reformulacion Legal</s:checkbox>
                                          </td>

                                       </tr>

                                    </tbody>
                                 </table>



                              </td>

                           </tr>

                           <tr class="altRow2" style="">

                              <td height="9" width="11%">
                                 <center>Observaciones</center>

                              </td>

                           </tr>

                           <tr class="altRow2" style="">

                              <td height="59" width="11%">
                                 <center>
                                    <!--<textarea name="comentario" cols="80" rows="12"></textarea>-->
                                    <s:textarea name="observacionrechazar" cols="80" rows="12" />
                                 </center>

                              </td>

                           </tr>

                        </tbody>
                     </table>

                  </td>

               </tr>

               <tr>

                  <td colspan="2"> </td>

               </tr>

               <tr>

                  <td colspan="2" height="14"></td>

               </tr>

               <tr>

                  <td colspan="2" align="center">
                     <input name="button" value="Aceptar" class="button" onclick="rechazarExpedienteStor()" type="button">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                     <input name="button2" value="Cancelar" class="button" onclick="window.close()" type="submit">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  </td>

               </tr>

               <tr align="center">

                  <td colspan="2" align="center">&nbsp;</td>

               </tr>

            </tbody>
         </table>

      </form>


   </body>
</html>
