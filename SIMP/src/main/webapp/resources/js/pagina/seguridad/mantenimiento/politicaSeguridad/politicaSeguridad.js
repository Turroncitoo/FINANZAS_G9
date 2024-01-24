$(document).ready(function() {

	/* Variables globales */
	$formMantenimiento = $("#formMantenimiento");

	/* Variables locales */
	var $local = {
		$modalMantenimiento : $("#modalMantenimiento"),
		$tablaMantenimiento : $("#tablaMantenimiento"),
		tablaMantenimiento : "",
		$filaSeleccionada : "",
		// Botones de Formulario de Mantenimiento
		$actualizarMantenimiento : $("#actualizarMantenimiento"),
		// Filtros de Tabla Mantenimiento
		arregloSiNo : [ "1", "0" ],
		filtrosSeleccionables : {},
		// Permisos
		permisoActualizacion : false
	};

	$.fn.dataTable.ext.errMode = 'none';

	$local.$tablaMantenimiento.on('xhr.dt', function(e, settings, json, xhr) {
		if (xhr.status == 500) {
			$local.tablaMantenimiento.clear().draw();
		}
	});

	$local.permisoActualizacion = $local.$tablaMantenimiento.attr("data-permiso-actualizacion") || false;

	$local.tablaMantenimiento = $local.$tablaMantenimiento.DataTable({
		"ajax" : {
			"url" : $variableUtil.root + "seguridad/politicaSeguridad?accion=buscarTodos",
			"dataSrc" : ""
		},
		"language" : {
			"emptyTable" : "No hay politicas de seguridad registradas."
		},
		"initComplete" : function() {
			$local.$tablaMantenimiento.wrap("<div class='table-responsive'></div>");
		},
		"columnDefs" : [ {
			"targets" : 1,
			"className" : "all seleccionable data-no-definida",
			"render" : function(data, type, row) {
				return $funcionUtil.insertarEtiquetaSiNo(row.complejidadContrasenia);
			}
		}, {
			"targets" : 4,
			"className" : "all seleccionable data-no-definida",
			"render" : function(data, type, row) {
				return $funcionUtil.insertarEtiquetaSiNo(row.autenticacionActiveDirectory);
			}
		}, {
			"targets" : 5,
			"className" : "all dt-center",
			"render" : function() {
				return "" + ($local.permisoActualizacion == "true" ? $variableUtil.botonActualizar : "");
			}
		} ],
		"columns" : [ {
			"data" : 'numeroMaximoIntentos',
			"title" : 'N\u00famero de Intentos'
		}, {
			"data" : null,
			"title" : 'Complejidad Contrase\u00f1a'
		}, {
			"data" : 'cantidadDiasParaCaducidadContrasenia',
			"title" : 'Cantidad D\u00edas'
		}, {
			"data" : 'longitudMinimaContrasenia',
			"title" : 'Longitud M\u00ednima'
		}, {
			"data" : null,
			"title" : 'Active Directory'
		}, {
			"data" : null,
			"title" : 'Acci\u00f3n'
		} ]
	});

	$local.$modalMantenimiento.PopupWindow({
		title : "Mantenimiento de Pol\u00edticas de Seguridad",
		autoOpen : false,
		modal : false,
		height : 340,
		width : 640,
	});

	$local.$modalMantenimiento.on("open.popupwindow", function() {
		$formMantenimiento.find("input:not([disabled]):first").focus();
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
		var politica = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		$funcionUtil.llenarFormulario(politica, $formMantenimiento);
		$local.$actualizarMantenimiento.removeClass("hidden");
		$local.$modalMantenimiento.PopupWindow("open");
	});

	$local.$actualizarMantenimiento.on("click", function() {
		if (!$formMantenimiento.valid()) {
			return;
		}
		var politica = $formMantenimiento.serializeJSON();
		$.ajax({
			type : "PUT",
			url : $variableUtil.root + "seguridad/politicaSeguridad",
			data : JSON.stringify(politica),
			beforeSend : function(xhr) {
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
				$local.$actualizarMantenimiento.attr("disabled", true).find("i").removeClass("fa-pencil-square").addClass("fa-spinner fa-pulse fa-fw");
			},
			statusCode : {
				400 : function(response) {
					$funcionUtil.limpiarMensajesDeError($formMantenimiento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formMantenimiento);
				}
			},
			success : function(response) {
				$funcionUtil.notificarException(response, "fa-check", "Aviso", "success");
				if ($local.$tablaMantenimiento.length > 0) {
					var row = $local.tablaMantenimiento.row($local.$filaSeleccionada).data(politica).draw();
					row.show().draw(false);
					$(row.node()).animateHighlight();
				}
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