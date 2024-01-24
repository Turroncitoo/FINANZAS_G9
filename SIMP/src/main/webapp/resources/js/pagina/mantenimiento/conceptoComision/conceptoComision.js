$(document).ready(function() {

	var $local = {
		$tablaMantenimiento : $("#tablaMantenimiento"),
		tablaMantenimiento : "",
		$modalMantenimiento : $("#modalMantenimiento"),
		$aniadirMantenimento : $("#aniadirMantenimiento"),
		$registrarMantenimiento : $("#registrarMantenimiento"),
		$actualizarMantenimiento : $("#actualizarMantenimiento"),
		$filaSeleccionada : "",
		conceptoComisionSeleccionado : "",
		$tipoRespuestasFiltroParaTableMantenimiento : $("#tipoRespuesta-filtroParaTablaMantenimiento"),
		arregloSiNo : [ "1", "0" ],
		filtrosSeleccionables : {},
		// Permisos
		permisoActualizacion : false,
		
		//Inputs
		$conComi : $("#conComi"),
		$tipoComi : $("#tipoComi")
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
			"url" : $variableUtil.root + "conceptoComision?accion=buscarTodos",
			"dataSrc" : ""
		},
		"language" : {
			"emptyTable" : "No hay Conceptos Comisiones registrados"
		},
		"initComplete" : function() {
			$local.$tablaMantenimiento.wrap("<div class='table-responsive'></div>");
			$local.filtrosSeleccionables["5"] = $local.arregloSiNo;
			$local.filtrosSeleccionables["4"] = $local.arregloSiNo;
			$local.filtrosSeleccionables["3"] = $local.arregloSiNo;
			$local.filtrosSeleccionables["2"] = $local.$tipoRespuestasFiltroParaTableMantenimiento.html();
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaMantenimiento, $local.filtrosSeleccionables);
		},
		"columnDefs" : [ {
			"targets" : [ 0, 1, 2 ],
			"className" : "all filtrable",
		}, {
			"targets" : 3,
			"className" : "all seleccionable data-no-definida dt-center select2",
			"width" : "10%",
			"render" : function(data, type, row) {
				return $funcionUtil.insertarEtiquetaSiNo(row.rolEmisor);
			}
		}, {
			"targets" : 4,
			"className" : "all seleccionable data-no-definida dt-center select2",
			"width" : "10%",
			"render" : function(data, type, row) {
				return $funcionUtil.insertarEtiquetaSiNo(row.rolReceptor);
			}
		}, {
			"targets" : 5,
			"className" : "all seleccionable data-no-definida dt-center select2",
			"width" : "10%",
			"render" : function(data, type, row) {
				return $funcionUtil.insertarEtiquetaSiNo(row.activado);
			}
		}, {
			"targets" : 6,
			"className" : "all dt-center",
			"width" : "10%",
			"render" : function() {
				return "" + ($local.permisoActualizacion == "true" ? $variableUtil.botonActualizar : "");
			}
		} ],
		"columns" : [ {
			"data" : 'idConceptoComision',
			"title" : "Concepto Comisi\u00f3n"
		}, {
			"data" : 'tipoComision',
			"title" : "Tipo Comisi\u00f3n"
		}, {
			"data" : 'descripcion',
			"title" : "Descripci\u00f3n"
		}, {
			"data" : 'rolEmisor',
			"title" : "Rol Emisor"
		}, {
			"data" : 'rolReceptor',
			"title" : "Rol Receptor"
		}, {
			"data" : 'activado',
			"title" : "Activado"
		}, {
			"data" : null,
			"title" : 'Acci\u00f3n'
		} ],
		"createdRow": function(row, data, dataIndex) {
			if (data.activado == "1") {
				$(row).css("background-color", "Green");
				$(row).addClass("success");
			} else {
				$(row).css("background-color", "Red");
				$(row).addClass("danger");
			}
		},
	});

	$local.$tablaMantenimiento.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(this.value).draw();
	});
	
	$local.$tablaMantenimiento.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	$local.$modalMantenimiento.PopupWindow({
		title : "Mantenimiento de Concepto Comisi\u00f3n",
		autoOpen : false,
		modal : false,
		height : 450,
		width : 626,
	});

	$local.$aniadirMantenimento.on("click", function() {
		$funcionUtil.prepararFormularioRegistro($formMantenimiento);
		$local.$actualizarMantenimiento.addClass("hidden");
		$local.$registrarMantenimiento.removeClass("hidden");
		$local.$modalMantenimiento.PopupWindow("open");
	});

	$local.$modalMantenimiento.on("open.popupwindow", function() {
		$formMantenimiento.find("input:not([disabled]):first").focus();
		$local.$tipoComi.attr('disabled','disabled');
	});

	$local.$modalMantenimiento.on("close.popupwindow", function() {
		$local.codigoRespuestaSwitchSeleccionado = "";
		$local.$tipoComi.removeAttr('disabled');
	});

	$formMantenimiento.find("input").keypress(function(event) {
		if (event.which == 13) {
			if (!$local.$registrarMantenimiento.hasClass("hidden")) {
				$local.$registrarMantenimiento.trigger("click");
				return false;
			} else {
				if (!$local.$actualizarMantenimiento.hasClass("hidden")) {
					$local.$actualizarMantenimiento.trigger("click");
				}
				return false;
			}
		}
	});
	
	$local.$conComi.keyup(function() {
	  	$local.$tipoComi.val($local.$conComi.val());
	});

	$local.$registrarMantenimiento.on("click", function() {
		if (!$formMantenimiento.valid()) {
			return;
		}
		var conceptoComision = $formMantenimiento.serializeJSON();
		conceptoComision.tipoComision = conceptoComision.idConceptoComision;
		$.ajax({
			type : "POST",
			url : $variableUtil.root + "conceptoComision",
			data : JSON.stringify(conceptoComision),
			beforeSend : function(xhr) {
				$local.$registrarMantenimiento.attr("disabled", true).find("i").removeClass("fa-floppy-o").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode : {
				400 : function(response) {
					$funcionUtil.limpiarMensajesDeError($formMantenimiento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formMantenimiento);
				}
			},
			success : function(conceptosComision) {
				conceptosComision[0].tipoComision = conceptosComision[0].idConceptoComision;
				$funcionUtil.notificarException($variableUtil.registroExitoso, "fa-check", "Aviso", "success");
				if ($local.$tablaMantenimiento.length > 0) {
					var row = $local.tablaMantenimiento.row.add(conceptosComision[0]).draw();
					row.show().draw(false);
					$(row.node()).animateHighlight();
				}
				$local.$modalMantenimiento.PopupWindow("close");
			},
			error : function(response) {
			},
			complete : function(response) {
				$local.$registrarMantenimiento.attr("disabled", false).find("i").addClass("fa-floppy-o").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$tablaMantenimiento.children("tbody").on("click", ".actualizar", function() {
		$funcionUtil.prepararFormularioActualizacion($formMantenimiento);
		$local.$filaSeleccionada = $(this).parents("tr");
		var conceptoComision = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		$local.conceptoComisionSeleccionado = conceptoComision.idConceptoComision;
		$funcionUtil.llenarFormulario(conceptoComision, $formMantenimiento);
		$local.$actualizarMantenimiento.removeClass("hidden");
		$local.$registrarMantenimiento.addClass("hidden");
		$local.$modalMantenimiento.PopupWindow("open");
	});

	$local.$actualizarMantenimiento.on("click", function() {
		if (!$formMantenimiento.valid()) {
			return;
		}
		var conceptoComision = $formMantenimiento.serializeJSON();
		conceptoComision.idConceptoComision = $local.conceptoComisionSeleccionado;
		conceptoComision.tipoComision = $local.conceptoComisionSeleccionado;
		$.ajax({
			type : "PUT",
			url : $variableUtil.root + "conceptoComision",
			data : JSON.stringify(conceptoComision),
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
			success : function(conceptosComision) {
				conceptosComision[0].tipoComision = conceptosComision[0].idConceptoComision;
				$funcionUtil.notificarException($variableUtil.actualizacionExitosa, "fa-check", "Aviso", "success");
				if ($local.$tablaMantenimiento.length > 0) {
					$local.tablaMantenimiento.row($local.$filaSeleccionada).remove().draw(false);
					var row = $local.tablaMantenimiento.row.add(conceptosComision[0]).draw();
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