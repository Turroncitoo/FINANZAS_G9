$(document).ready(function() {

	var $local = {
		$tablaMantenimiento : $("#tablaMantenimiento"),
		tablaMantenimiento : "",
		$modalMantenimiento : $("#modalMantenimiento"),
		$aniadirMantenimento : $("#aniadirMantenimiento"),
		$registrarMantenimiento : $("#registrarMantenimiento"),
		$filaSeleccionada : "",
		$actualizarMantenimiento : $("#actualizarMantenimiento"),
		$rangoFechaBusqueda : $("#rangoFechaBusqueda"),
		$empresas : $("#empresas"),
		$instituciones : $("#instituciones"),
		$codigoInstitucion : $("#instituciones"),
		// Permisos
		permisoActualizacion : false
	};

	$funcionUtil.crearDatePickerSimple($local.$rangoFechaBusqueda, "DD/MM/YYYY");
	$funcionUtil.crearSelect2($local.$instituciones, "Seleccione una Instituci\u00f3n");
	$funcionUtil.crearSelect2($local.$empresas, "Seleccione una Empresa");

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
			"url" : $variableUtil.root + "parametroGeneral?accion=buscarTodos",
			"dataSrc" : ""
		},
		"language" : {
			"emptyTable" : "No hay Par\u00E1metros generales registrados"
		},
		"initComplete" : function() {
			$local.$tablaMantenimiento.wrap("<div class='table-responsive'></div>");
		},
		"columnDefs" : [ {
			"targets" : [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
			"className" : "all dt-center",
		}, {
			"targets" : 11,
			"className" : "all dt-center",
			"width" : "10%",
			"render" : function() {
				return "" + ($local.permisoActualizacion == "true" ? $variableUtil.botonActualizar : "");
			}
		} ],

		"columns" : [ {
			"data" : function(row) {
				return $funcionUtil.convertirDeFormatoAFormato(row.fechaProceso, "YYYY-MM-DD", "DD/MM/YYYY");
			},
			"title" : "Fecha Proceso"
		}, {
			"data" : 'binRuteoSwitch',
			"title" : "Bin Ruteo Switch"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoInstitucion, row.descripcionInstitucion);
			},
			"title" : "Instituci\u00f3n"
		}, {
			"data" : function(row) {
				if (row.surchargeSoles == null) {
					return "";
				}
				return row.surchargeSoles.toFixed(2);
			},
			"title" : "Surchage Soles"
		}, {
			"data" : function(row) {
				if (row.surchargeDolares == null) {
					return "";
				}
				return row.surchargeDolares.toFixed(2);
			},
			"title" : 'Surchage D\u00f3lares'
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idEmpresa, row.descripcionEmpresa);
			},
			"title" : 'Empresa'
		}, {
			"data" : function(row) {
				return row.porcentajeIgv;
			},
			"title" : 'I.G.V.'
		}, {
			"data" : function(row) {
				return $funcionUtil.insertarEtiquetaSiNo(row.activoSimpBatch);
			},
			"title" : 'Activo SimpBatch'
		}, {
			"data" : function(row) {
				return row.tiempoReprogramacion + " minutos";
			},
			"title" : 'Reprogramaci\u00f3n'
		}, {
			"data" : function(row) {
				return $funcionUtil.insertarEtiquetaSiNo(row.procesaObservadasManual);
			},
			"title" : 'Procesa Transacciones Obs. Manual.'
		}, {
			"data" : "repositorioArchivoContable",
			"title" : 'Ruta Archivo Contable'
		}, {
			"data" : null,
			"title" : "Acci\u00f3n"
		} ]
	});

	$local.$tablaMantenimiento.find("thead").on('keyup', 'input', function() {
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$modalMantenimiento.PopupWindow({
		title : "Mantenimiento de Par\u00e1metros generales",
		autoOpen : false,
		modal : false,
		height : 645,
		width : 700,
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
		var parametroGeneral = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		$funcionUtil.llenarFormulario(parametroGeneral, $formMantenimiento);
		var fechaProceso = $funcionUtil.convertirDeFormatoAFormato(parametroGeneral.fechaProceso, "YYYY-MM-DD", "DD/MM/YYYY");
		$local.$rangoFechaBusqueda.data("daterangepicker").setStartDate(fechaProceso);
		$local.$rangoFechaBusqueda.data("daterangepicker").setEndDate(fechaProceso);
		$local.$actualizarMantenimiento.removeClass("hidden");
		$local.$registrarMantenimiento.addClass("hidden");
		$local.$modalMantenimiento.PopupWindow("open");
	});

	$local.$actualizarMantenimiento.on("click", function() {
		if (!$formMantenimiento.valid()) {
			return;
		}
		var parametroGeneral = $formMantenimiento.serializeJSON();
		var fechaProcesoFormato = $local.$rangoFechaBusqueda.data("daterangepicker").startDate.format('YYYY-MM-DD');
		parametroGeneral.fechaProceso = fechaProcesoFormato;
		parametroGeneral.sFechaProceso = fechaProcesoFormato;
		parametroGeneral["codigoInstitucion"] = $("#instituciones").val();
		$.ajax({
			type : "PUT",
			url : $variableUtil.root + "parametroGeneral",
			data : JSON.stringify(parametroGeneral),
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
				$funcionUtil.notificarException($variableUtil.actualizacionExitosa, "fa-check", "Aviso", "success");
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