$(document).ready(function() {

	var $local = {
		$buscar : $("#buscar"),
		$exportar : $("#exportar"),
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
			$local.filtrosSeleccionables["22"] = $local.arregloSiNo;
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
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idMonedaTransaccion, row.descripcionMonedaTransaccion);
			},
			"title" : "Moneda Transacci\u00F3n"
		}, {
			"data" : "tipoTarifaRecOpe",
			"title" : "T. Tarifa Rec. OPE"
		}, {
			"data" : "nivelCompensacion",
			"title" : "Nivel Compensaci\u00F3n"
		}, {
			"data" : "nivelRecOpe",
			"title" : "Nivel Rec. OPE"
		}, {
			"data" : "secuenciaTransaccion",
			"title" : "Secuencia Transacci\u00F3n"
		}, {
			"className" : "dt-right filtrable",
			"data" : function(row) {
				return row.valorCompensacion.toFixed(2);
			},
			"title" : "Valor Compensaci\u00F3n"
		}, {
			"className" : "dt-right filtrable",
			"data" : function(row) {
				return row.surcharge.toFixed(4);
			},
			"title" : "SUR"
		}, {
			"className" : "dt-right filtrable",
			"data" : function(row) {
				return row.surchargeRec.toFixed(4);
			},
			"title" : "SUR REC"
		}, {
			"className" : "dt-right filtrable",
			"data" : function(row) {
				return row.ctValorComision.toFixed(4);
			},
			"title" : "THB"
		}, {
			"className" : "dt-right filtrable",
			"data" : function(row) {
				return row.ciValorComision.toFixed(4);
			},
			"title" : "INT"
		}, {
			"className" : "dt-right filtrable",
			"data" : function(row) {
				return row.cgValorComision.toFixed(4);
			},
			"title" : "GAS"
		}, {
			"className" : "dt-right filtrable",
			"data" : function(row) {
				return row.coValorComision.toFixed(4);
			},
			"title" : "OPE"
		}, {
			"className" : "dt-right filtrable",
			"data" : function(row) {
				return row.coValorComisionRec.toFixed(4);
			},
			"title" : "OPE REC"
		}, {
			"className" : "dt-right filtrable",
			"data" : function(row) {
				return row.coValorComisionRec2.toFixed(4);
			},
			"title" : "OPE REC 2"
		}, {
			"className" : "all seleccionable data-no-definida select2",
			"data" : function(row) {
				return $funcionUtil.insertarEtiquetaSiNo(row.cuadra);
			},
			"title" : "Cuadra"
		} ],
		"headerCallback" : function(thead, data, start, end, display) {
			$(thead).find('th').eq(14).attr({
				"data-tooltip" : "tooltip",
				"title" : $variableUtil.comisionSur
			});
			$(thead).find('th').eq(15).attr({
				"data-tooltip" : "tooltip",
				"title" : $variableUtil.comisionSurRec
			});
			$(thead).find('th').eq(16).attr({
				"data-tooltip" : "tooltip",
				"title" : $variableUtil.comisionTarjetahabiente
			});
			$(thead).find('th').eq(17).attr({
				"data-tooltip" : "tooltip",
				"title" : $variableUtil.comisionIntercambio
			});
			$(thead).find('th').eq(18).attr({
				"data-tooltip" : "tooltip",
				"title" : $variableUtil.comisionGastos
			});
			$(thead).find('th').eq(19).attr({
				"data-tooltip" : "tooltip",
				"title" : $variableUtil.comisionOperador
			});
			$(thead).find('th').eq(20).attr({
				"data-tooltip" : "tooltip",
				"title" : $variableUtil.comisionOperadorRec
			});
			$(thead).find('th').eq(21).attr({
				"data-tooltip" : "tooltip",
				"title" : $variableUtil.comisionOperadorRec2
			});
		}
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
			url : $variableUtil.root + "reporte/tarifario/detalleAdquirente?accion=buscar",
			data : criterioBusqueda,
			beforeSend : function() {
				$local.tablaResultadoBusqueda.clear().draw();
				$local.$buscar.attr("disabled", true).find("i").removeClass("fa-floppy-o").addClass("fa-spinner fa-pulse fa-fw");
			},
			success : function(reporteTarifarioDetalleAdquirente) {
				if (reporteTarifarioDetalleAdquirente.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				$local.tablaResultadoBusqueda.rows.add(reporteTarifarioDetalleAdquirente).draw();
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
		window.location.href = $variableUtil.root + "reporte/tarifario/detalleAdquirente?accion=exportar&" + paramCriterioBusqueda;
	});

});