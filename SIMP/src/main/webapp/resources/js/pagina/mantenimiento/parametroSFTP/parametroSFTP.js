$(document).ready(function() {

	var $local = {
		$tablaMantenimiento : $("#tablaMantenimiento"),
		tablaMantenimiento : "",
		$modalMantenimiento : $("#modalMantenimiento"),
		$aniadirMantenimento : $("#aniadirMantenimiento"),
		$registrarMantenimiento : $("#registrarMantenimiento"),
		$filaSeleccionada : "",
		$actualizarMantenimiento : $("#actualizarMantenimiento"),
		// Permisos
		permisoActualizacion : false,
		
		$verSFTP : $('#verSFTP'),
		$modalVerSFTP: $('#modalVerSFTP'),
		jstree: {
			core: {
			      data: {
			        url: $variableUtil.root + "parametroSFTP?accion=ver-tree-sftp",
			        data: function (node) {
			          return { "id" : node.id, "text": node.text, "children": node.children };
			        }
			      }
				}
		}
	};

	$formMantenimiento = $("#formMantenimiento");

	$.fn.dataTable.ext.errMode = 'none';

	$local.$tablaMantenimiento.on('xhr.dt', function(e, settings, json, xhr) {
		switch (xhr.status) {
		case 500:
			$local.tablaMantenimiento.clear().draw();
			break;
		}
	});
	
	$local.permisoActualizacion = $local.$tablaMantenimiento.attr("data-permiso-actualizacion") || false;

	$local.tablaMantenimiento = $local.$tablaMantenimiento.DataTable({
		"ajax" : {
			"url" : $variableUtil.root + "parametroSFTP?accion=buscarTodos",
			"dataSrc" : ""
		},
		"language" : {
			"emptyTable" : "No hay Par\u00E1metros SFTP registrados"
		},
		"initComplete" : function() {
			$local.$tablaMantenimiento.wrap("<div class='table-responsive'></div>");
		},
		"columnDefs" : [ {
			"targets" : [ 0, 1, 2 ],
			"className" : "all dt-center",
		}, {
			"targets" : 3,
			"className" : "all dt-center",
			"width" : "10%",
			"render" : function() {
				return "" + ($local.permisoActualizacion == "true" ? ($variableUtil.botonActualizar + " " + $variableUtil.botonTestSFTP + " " + $variableUtil.botonVerContenidoSFTP) : "");
			}
		} ],

		"columns" : [ {
			"data" : "usuario",
			"title" : 'Usuario SFTP'
		}, {
			"data" : 'host',
			"title" : "Host"
		},{
			"data": 'puerto',
			"title":"Puerto"
		} ]
	});

	$local.$tablaMantenimiento.find("thead").on('keyup', 'input', function() {
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$modalMantenimiento.PopupWindow({
		title : "Mantenimiento de Par\u00E1metros SFTP",
		autoOpen : false,
		modal : false,
		height : 440,
		width : 670,
	});
	
	$local.$modalVerSFTP.PopupWindow({
		title : "Ver contenido SFTP",
		autoOpen : false,
		modal : false,
		height : 200,
		width : 300,
	});

	$local.$modalMantenimiento.on("open.popupwindow", function() {
		$formMantenimiento.find("input:not([readonly]):first").focus();
	});

	$local.$modalMantenimiento.on("close.popupwindow", function() {
		$local.idparametroGeneralSeleccionado = "";
	});

	$formMantenimiento.find("input").keypress(function(event) {
		if (event.which == 13) {
			$local.$actualizarMantenimiento.trigger("click");
			return false;
		}
	});

	$local.$tablaMantenimiento.children("tbody").on("click", ".actualizar", function() {
		$funcionUtil.prepararFormularioActualizacion($formMantenimiento);
		$local.$filaSeleccionada = $(this).parents("tr");
		var parametroSFTP = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		$funcionUtil.llenarFormulario(parametroSFTP, $formMantenimiento);
		$local.$actualizarMantenimiento.removeClass("hidden");
		$local.$registrarMantenimiento.addClass("hidden");
		$local.$modalMantenimiento.PopupWindow("open");
	});
		
	$local.$tablaMantenimiento.children("tbody").on("click", ".test-sftp", function() {
		var $btn = $(this);
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "parametroSFTP?accion=probar-conexion-sftp",
			beforeSend : function(xhr) {
				$btn.attr("disabled", true).find("i").removeClass("fa-check").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			success : function(response) {
				if(response != undefined || response != null){
					if(response.exito == 1){
						$funcionUtil.notificarException(response.mensaje, "fa-check", "Aviso", "success");
					}else{
						$funcionUtil.notificarException(response.mensaje, "fa-warning", "Aviso", "warning");
					}
				}
			},
			error : function(response) {
			},
			complete : function(response) {
				$btn.attr("disabled", false).find("i").addClass("fa-check").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});
	
	$local.$tablaMantenimiento.children("tbody").on("click", ".ver-sftp", function() {
		var $btn = $(this);
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "parametroSFTP?accion=probar-conexion-sftp",
			beforeSend : function(xhr) {
				$btn.attr("disabled", true).find("i").removeClass("fa-eye").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			success : function(response) {
				if(response != undefined || response != null){
					if(response.exito == 1){
						$local.$verSFTP.jstree($local.jstree);
						$local.$modalVerSFTP.PopupWindow("open");
					}else{
						$funcionUtil.notificarException(response.mensaje, "fa-warning", "Aviso", "warning");
					}
				}
			},
			error : function(response) {
			},
			complete : function(response) {
				$btn.attr("disabled", false).find("i").addClass("fa-eye").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$actualizarMantenimiento.on("click", function() {
		if (!$formMantenimiento.valid()) {
			return;
		}
		var parametroSFTP = $formMantenimiento.serializeJSON();
		$.ajax({
			type : "PUT",
			url : $variableUtil.root + "parametroSFTP",
			data : JSON.stringify(parametroSFTP),
			beforeSend : function(xhr) {
				$local.$actualizarMantenimiento.attr("disabled", true).find("i").removeClass("fa-pencil-square").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode : {
				400 : function(response) {
					$funcionUtil.limpiarMensajesDeError($formMantenimiento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formMantenimiento);
				}
			},
			success : function(parametrosGenerales) {
				$funcionUtil.obtenerFechaProceso();
				$funcionUtil.notificarException("Actualizaci\u00F3n Exitosa", "fa-check", "Aviso", "success");
				$local.tablaMantenimiento.row($local.$filaSeleccionada).remove().draw(false);
				var row = $local.tablaMantenimiento.row.add(parametrosGenerales[0]).draw();
				row.show().draw(false);
				$(row.node()).animateHighlight();
				$local.$modalMantenimiento.PopupWindow("close");
			},
			error : function(response) {
			},
			complete : function(response) {
				$local.$actualizarMantenimiento.attr("disabled", false).find("i").addClass("fa-pencil-square").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});
});