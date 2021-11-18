$(document).ready(function(){
	$.blockUI.defaults.applyPlatformOpacityRules = false;
	
	$("#sincronizar").click(function(){
		$.blockUI({
			message:	$("#mensaje"),
			padding:	"5px"
		});
		$.ajax({
			type:	"GET",
			url:	"sincronizarUsuarios.action",
			success: function(){
				$.unblockUI();
				location.reload(true);
			}
		});
	});
	
	$("#guardar").click(function(){
		$.blockUI({
			message:	$("#guardando"),
			padding:	"5px"
		});
		$.ajax({
			type:	"GET",
			url:	"guardarUsuariosLDAP.action",
			success: function(){
				$.unblockUI();
			}
		});
	});
});