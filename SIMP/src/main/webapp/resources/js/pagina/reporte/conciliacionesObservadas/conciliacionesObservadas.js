$(document).ready(function() {

	var $local = {
		$tablaTransaccionObservadas : $("#tablaObservadas"),
		tablaTransaccionObservadas : "",
		$buscarCriterios : $("#buscarCriterios"),
		$exportar : $("#exportar"),
		$rangoFechaBusqueda : $("#rangoFechaBusqueda"),
		$origenesArchivo : $("#origenesArchivo")
	};

	$formBusquedaCriterios = $("#formCriterioBusqueda");
	$funcionUtil.crearDateRangePickerConsulta($local.$rangoFechaBusqueda);
	$funcionUtil.crearSelect2($local.$origenesArchivo);

	$local.tablaTransaccionObservadas = $local.$tablaTransaccionObservadas.DataTable({
		"language" : {
			"emptyTable" : "No se han encontrado Transacciones Observadas con los criterios definidos."
		},
		"initComplete" : function() {
			$local.$tablaTransaccionObservadas.wrap("<div class='table-responsive'></div>");
		},
		"columns" : [ {
			"data" : 'idCliente',
			"title" : "Id Cliente"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoTipoDocumento, row.descripcionTipoDocumento);
			},
			"title" : "Tipo Documento"
		}, {
			"data" : 'numeroDocumento',
			"title" : "N° Documento"
		}, {
			"data" : 'nombreCompleto',
			"title" : "Nombre Cliente"
		}, {
			"data" : 'fechaProceso',
			"title" : "Fecha Proceso"
		}, {
			"data" : 'numeroTarjeta',
			"title" : "N° Tarjeta"
		}, {
			"data" : 'numeroCuenta',
			"title" : "N° Cuenta"
		}, {
			"data" : 'fechaTransaccion',
			"title" : "Fecha Transacci\u00F3n"
		}, {
			"data" : 'horaTransaccion',
			"title" : "Hora Transacci\u00F3n"
		}, {
			"data" : 'fechaSwitch',
			"title" : "Fecha Switch"
		}, {
			"data" : 'autorizacion',
			"title" : "Autorizaci\u00F3n"
		}, {
			"data" : 'numeroTrace',
			"title" : "N° Trace"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idCanal, row.descripcionCanal);
			},
			"title" : "Canal"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoOrigen, row.descripcionOrigen);
			},
			"title" : "Origen"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoProcesoSwitch, row.descripcionProcesoSwitch);
			},
			"title" : "Proceso"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoTransaccion, row.descripcionCodigoTransaccion);
			},
			"title" : "Transacci\u00F3n"
		}, {
			"data" : 'nombreAdquirente',
			"title" : "Nombre Adquirente"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoRespuestaSwitch, row.descripcionCodigoRespuestaSwitch);
			},
			"title" : "Respuesta Transacci\u00F3n"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoMonedaTransaccion, row.descripcionMonedaTransaccion);
			},
			"title" : "Moneda Transacci\u00F3n"
		}, {
			"className" : "dt-right",
			"data" : function(row) {
				return row.valorTransaccion.toFixed(2);
			},
			"title" : "Valor de Transacci\u00F3n"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idIndicadorConciliacion, row.descripcionIndicadorConciliacion);
			},
			"title" : "Indicador Conciliaci\u00F3n"
		}, {
			"data" : function(row) {
				if (row.idOrigenArchivo == 4) {
					return $funcionUtil.unirCodigoDescripcion(row.idIndicadorDevolucion, row.descripcionIndicadorDevolucion);
				} else {
					return $funcionUtil.unirCodigoDescripcion(row.idIndicadorExtorno, row.descripcionIndicadorExtorno);
				}
			},
			"title" : "Indicador Extorno/Devoluci\u00F3n"
		}, {
			"data" : 'fechaTransmisionAutorizacion',
			"title" : "Fecha de Transmisi\u00F3n Autorizaci\u00F3n"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoRespuestaSwitchAutorizacion, row.descripcionCodigoRespuestaSwitchAutorizacion);
			},
			"title" : "Respuesta Autorizaci\u00F3n"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoMonedaAutorizacion, row.descripcionMonedaAutorizacion);
			},
			"title" : "Moneda Autorizaci\u00F3n"
		}, {
			"className" : "dt-right",
			"data" : function(row) {
				return row.montoAutorizacion.toFixed(2);
			},
			"title" : "Monto Autorizaci\u00F3n"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idOrigenArchivo, row.descripcionOrigenArchivo);
			},
			"title" : "Origen Archivo"
		} ]
	});

	$local.$tablaTransaccionObservadas.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaTransaccionObservadas.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaTransaccionObservadas.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaTransaccionObservadas.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	$local.$buscarCriterios.on("click", function() {
		if (!$formBusquedaCriterios.valid()) {
			return;
		}
		var rangoFecha = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaBusqueda);
		var criterioBusquedaTransaccionObservada = $formBusquedaCriterios.serializeJSON();
		criterioBusquedaTransaccionObservada.fechaInicio = rangoFecha.fechaInicio;
		criterioBusquedaTransaccionObservada.fechaFin = rangoFecha.fechaFin;
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "reporte/conciliacionesObservadas?accion=buscar",
			data : criterioBusquedaTransaccionObservada,
			statusCode : {
				400 : function(response) {
					$funcionUtil.limpiarMensajesDeError($formBusquedaCriterios);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaCriterios);
				}
			},
			beforeSend : function(xhr) {
				$local.tablaTransaccionObservadas.clear().draw();
				$local.$buscarCriterios.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success : function(conciliacionesObservadas) {
				if (conciliacionesObservadas.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				$local.tablaTransaccionObservadas.rows.add(conciliacionesObservadas).draw();
			},
			error : function() {
			},
			complete : function() {
				$local.$buscarCriterios.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$exportar.on("click", function() {
		if (!$formBusquedaCriterios.valid()) {
			return;
		}
		var rangoFecha = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaBusqueda);
		var criterioBusqueda = $formBusquedaCriterios.serializeJSON();
		criterioBusqueda.fechaInicio = rangoFecha.fechaInicio;
		criterioBusqueda.fechaFin = rangoFecha.fechaFin;
		var descripcionRangoFechas = $local.$rangoFechaBusqueda.val();
		criterioBusqueda.descripcionRangoFechas = descripcionRangoFechas == "" || descripcionRangoFechas == undefined ? "TODOS" : descripcionRangoFechas;
		criterioBusqueda.descripcionOrigenArchivo = $local.$origenesArchivo.find("option:selected").text();
		var paramCriterioBusqueda = $.param(criterioBusqueda);
		window.location.href = $variableUtil.root + "reporte/conciliacionesObservadas?accion=exportar&" + paramCriterioBusqueda;
	});

});