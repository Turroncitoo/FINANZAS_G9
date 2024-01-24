$(document).ready(function() {

	var $local = {

		$tablaConsulta: $("#tablaConsulta"),
		tablaConsulta: "",
		filtrosSeleccionables: [],

		$buscarPorCriterio: $("#buscarPorCriterio"),
		$fechaProceso: $("#fechaProceso"),

		// Exportacion
		$exportarPorCriterio: $("#exportarPorCriterio"),
	};

	$formCriterioBusquedaConsulta = $("#formCriterioBusquedaConsulta");
	$.fn.dataTable.ext.errMode = 'none';

	$funcionUtil.crearDateRangePickerConsulta($local.$fechaProceso);

	$local.tablaConsulta = $local.$tablaConsulta.DataTable({
		"language": {
			"emptyTable": "No se han encontraron registros con los criterios definidos."
		},
		"initComplete": function() {
			$local.$tablaConsulta.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaConsulta, $local.filtrosSeleccionables);
		},
		"columnDefs": [{
			"targets": [0, 1, 2, 3, 4, 5, 6, 7, 8],
			"className": "all filtrable",
		}],
		"columns": [{
			"data": 'fechaProceso',//0
			"title": "Fecha Proceso"
		}, {
			"data": 'numeroTarjeta',//1
			"title": "N° Tarjeta"
		}, {
			"data": 'codigoSeguimiento',//2
			"title": "C\u00F3digo Seguimiento"
		}, {
			"data": function(row) {//3
				return $funcionUtil.unirCodigoDescripcion(row.tipoDocumento, row.descripciontipoDocumento);
			},
			"title": "Tipo Documento"
		}, {
			"data": "numeroDocumento", //4
			"title": "N° Documento"
		}, {
			"data": "nombres", //5
			"title": "Cliente"
		}, {
			"data": 'informacion',//6
			"title": "Informaci\u00F3n"
		}, {
			"data": 'fechaProcesoUBA',//7
			"title": "Fecha Proceso UBA"
		}, {
			"data": 'horaProcesoUBA',//8
			"title": "Hora Proceso UBA"
		}],
	});

	$local.$tablaConsulta.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaConsulta.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$buscarPorCriterio.on("click", function() {
		if ($local.$fechaProceso.val() == '' || $local.$fechaProceso.val() == null) {
			$funcionUtil.notificarException('Ingrese un intervalo de fechas', "fa-exclamation-circle", "Informaci\u00F3n", "info");
			return;
		}
		var criterioBusqueda = $formCriterioBusquedaConsulta.serializeJSON();
		var rangoFechaTxn = $funcionUtil.obtenerFechasDateRangePicker($local.$fechaProceso);
		criterioBusqueda.fechaInicioProceso = rangoFechaTxn.fechaInicio;
		criterioBusqueda.fechaFinProceso = rangoFechaTxn.fechaFin;
		var paramCriterioBusqueda = $.param(criterioBusqueda);
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "txnsEmbossing/buscar?accion=buscarPorCriterios&" + paramCriterioBusqueda,
			contentType: "application/json",
			dataType: "json",
			statusCode: {
				400: function(response) {
					$funcionUtil.limpiarMensajesDeError($formCriterioBusquedaConsulta);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formCriterioBusquedaConsulta);
				}
			},
			beforeSend: function() {
				$local.tablaConsulta.clear().draw();
				$local.$buscarPorCriterio.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success: function(transaccionWS) {
				if (transaccionWS.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				$local.tablaConsulta.rows.add(transaccionWS).draw();
			},
			complete: function() {
				$local.$buscarPorCriterio.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});


	$local.$exportarPorCriterio.on("click", function() {
		if ($local.$fechaProceso.val() == '' || $local.$fechaProceso.val() == null) {
			$funcionUtil.notificarException('Ingrese un intervalo de fechas', "fa-exclamation-circle", "Informaci\u00F3n", "info");
			return;
		}
		var criterioBusqueda = $formCriterioBusquedaConsulta.serializeJSON();
		var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$fechaProceso);
		criterioBusqueda.fechaInicioProceso = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaFinProceso = rangoFechaBusqueda.fechaFin;
		var paramCriterioBusqueda = $.param(criterioBusqueda);
		window.location.href = $variableUtil.root + "txnsEmbossing/exportar?accion=exportarPorCriterios&" + paramCriterioBusqueda;
	});

});