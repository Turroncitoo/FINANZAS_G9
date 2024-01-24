$(document).ready(function() {

	var $local = {
		$buscar : $("#buscar"),
		$exportar : $("#exportar"),
		$navTabla : $("#navTabla"),
		$resultadoBusqueda : $("#resultadoBusqueda"),
		$rangoFechas : $("#rangoFechaBusqueda"),
		$tablaResultadoBusqueda : $("#tablaResultadoBusqueda"),
		tablaResultadoBusqueda : "",
		arregloSiNo : [ "1", "0" ],
		filtrosSeleccionables : {}
	};

	$local.$tablaResultadoBusquedaCuerpo = $local.$tablaResultadoBusqueda.find("tbody");
	$formCriterioBusqueda = $("#formCriterioBusqueda");
	$funcionUtil.crearDateRangePickerSimple($local.$rangoFechas, "right");

	$local.tablaResultadoBusqueda = $local.$tablaResultadoBusqueda.DataTable({
		"initComplete" : function() {
			$local.$tablaResultadoBusqueda.wrap("<div class='table-responsive'></div>");
			$local.filtrosSeleccionables["16"] = $local.arregloSiNo;
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaResultadoBusqueda, $local.filtrosSeleccionables);
		},
		"language" : {
			"emptyTable" : "No hay transacciones encontradas"
		},
		"columnDefs" : [ {
			"targets" : "_all",
			"className" : "all filtrable",
		} ],
		"columns" : [ {
			"data" : "fechaProceso",
			"title" : "F. de Proceso"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idInstitucionRecep, row.descripcionInstitucionReceptor);
			},
			"title" : "Instituci\u00F3n Receptora"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idInstitucionEmi, row.descripcionInstitucionEmisor);
			},
			"title" : "Instituci\u00F3n Emisor"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idMembresia, row.descripcionMembresia);
			},
			"title" : "Membres\u00EDa"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idClaseServicio, row.descripcionClaseServicio);
			},
			"title" : "Clase de Servicio"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idOrigen, row.descripcionOrigen);
			},
			"title" : "Origen"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.transaccion, row.descripcionCodigoTransaccion);
			},
			"title" : "Transacci\u00F3n"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idMonedaCompensacion, row.descripcionMonedaCompensacion);
			},
			"title" : "Moneda Compensaci\u00F3n"
		}, {
			"data" : "tipoTarifaRecOpe",
			"title" : "Tipo Tarifa"
		}, {
			"data" : "nivelCompensacion",
			"title" : "Nivel Compensaci\u00F3n"
		}, {
			"data" : "nivelRecOpe",
			"title" : "Nivel Rec. OPE"
		}, {
			"className" : "dt-right filtrable",
			"data" : "cantidad",
			"title" : "Cantidad"
		}, {
			"className" : "dt-right filtrable",
			"data" : function(row) {
				return row.sumaValorCompensacion.toFixed(2);
			},
			"title" : "Suma Valor Compensaci\u00F3n"
		}, {
			"className" : "dt-right filtrable",
			"data" : function(row) {
				return row.sumaValorComision.toFixed(4);
			},
			"title" : "Suma Valor Comisi\u00F3n"
		}, {
			"className" : "dt-right filtrable",
			"data" : function(row) {
				return row.sumaValorComisionRec.toFixed(4);
			},
			"title" : "Suma Valor Comisi\u00F3n REC"
		}, {
			"className" : "dt-right filtrable",
			"data" : function(row) {
				return row.sumaValorComisionRec2.toFixed(4);
			},
			"title" : "Suma Valor Comisi\u00F3n REC 2"
		}, {
			"className" : "all seleccionable data-no-definida select2",
			"data" : function(row) {
				return $funcionUtil.insertarEtiquetaSiNo(row.cuadra);
			},
			"title" : "Cuadra"
		} ]
	});
	
	$local.$tablaResultadoBusqueda.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaResultadoBusqueda.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaResultadoBusqueda.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaResultadoBusqueda.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	$local.$buscar.on("click", function() {
		if (!$formCriterioBusqueda.valid()) {
			return true;
		}
		var criterioBusqueda = {};
		criterioBusqueda.fechaInicio = $local.$rangoFechas.data("daterangepicker").startDate.format('YYYY-MM-DD');
		criterioBusqueda.fechaFin = $local.$rangoFechas.data("daterangepicker").endDate.format('YYYY-MM-DD');
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "reporte/tarifario/resumenEmisor?accion=buscar",
			data : criterioBusqueda,
			beforeSend : function() {
				$local.tablaResultadoBusqueda.clear().draw();
				$local.$buscar.attr("disabled", true).find("i").removeClass("fa-floppy-o").addClass("fa-spinner fa-pulse fa-fw");
			},
			success : function(reporteTarifarioResumenEmisor) {
				if (reporteTarifarioResumenEmisor.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				$local.tablaResultadoBusqueda.rows.add(reporteTarifarioResumenEmisor).draw();
			},
			error : function() {
			},
			complete : function() {
				$local.$buscar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$exportar.on("click", function() {
		var criterioBusqueda = {};
		criterioBusqueda.fechaInicio = $local.$rangoFechas.data("daterangepicker").startDate.format('YYYY-MM-DD');
		criterioBusqueda.fechaFin = $local.$rangoFechas.data("daterangepicker").endDate.format('YYYY-MM-DD');
		var descripcionRangoFechas = $local.$rangoFechas.val();
		criterioBusqueda.descripcionRangoFechas = descripcionRangoFechas == "" || descripcionRangoFechas == undefined ? "TODOS" : descripcionRangoFechas;
		var paramCriterioBusqueda = $.param(criterioBusqueda);
		window.location.href = $variableUtil.root + "reporte/tarifario/resumenEmisor?accion=exportar&" + paramCriterioBusqueda;
	});

});