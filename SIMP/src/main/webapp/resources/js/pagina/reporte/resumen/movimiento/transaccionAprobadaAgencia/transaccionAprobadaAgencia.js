$(document).ready(function() {

	var $local = {
		$sexos : $("#sexos"),
		$agencias : $("#agencias"),
		$estadosCivil : $("#estadosCivil"),
		$rangoFechaBusqueda : $("#rangoFechaBusqueda"),
		$buscar : $("#buscar"),
		$tablaResumenMovimiento : $("#tablaResumenMovimiento"),
		tablaResumenMovimiento : "",
		$resultadoBusquedaResumenMovimiento : $("#resultadoBusquedaResumenMovimiento"),
		$exportarXlsx : ""
	};

	$funcionUtil.crearSelect2($local.$sexos);
	$funcionUtil.crearSelect2($local.$agencias);
	$funcionUtil.crearSelect2($local.$estadosCivil);
	$funcionUtil.crearDateRangePickerConsulta($local.$rangoFechaBusqueda);

	$formCriterioBusquedaReporte = $("#formCriterioBusquedaReporte");

	$local.tablaResumenMovimiento = $local.$tablaResumenMovimiento.DataTable({
		"language" : {
			"emptyTable" : "No hay transacciones encontradas"
		},
		"columnDefs" : [ {
			"targets" : [ 0, 1, 2 ],
			"className" : "all",
		}, {
			"targets" : 8,
			"className" : "all dt-right"
		}, {
			"targets" : 9,
			"className" : "all dt-right monto"
		} ],
		"columns" : [ {
			"data" : 'fechaTransaccion',
			"title" : "Fecha de Transacci\u00F3n"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idAgencia, row.descripcionAgencia);
			},
			"title" : "Agencia"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idSexo, row.descripcionSexo);
			},
			"title" : "Sexo"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idEstadoCivil, row.descripcionEstadoCivil);
			},
			"title" : "Estado Civil"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idCanal, row.descripcionCanal);
			},
			"title" : "Canal"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoProceso, row.descripcionCodigoProceso);
			},
			"title" : "Transacci\u00F3n"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoGiroNegocio, row.descripcionGiroNegocio);
			},
			"title" : "Giro de Negocio"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoMoneda, row.descripcionMoneda);
			},
			"title" : "Moneda"
		}, {
			"data" : 'cantidadTransaccion',
			"title" : "Cantidad"
		}, {
			"data" : function(row) {
				return parseFloat(row.montoTransaccion).toFixed(4);
			},
			"title" : "Monto"
		} ],
		"footerCallback" : function(row, data, start, end, display) {
			var tieneData = $local.tablaResumenMovimiento == "" ? false : $local.tablaResumenMovimiento.data().any();
			var api = this.api(), data;
			if (tieneData) {
				montoTotal = api.column(9).data().reduce(function(a, b) {
					return parseFloat(a) + parseFloat(b);
				}, 0);
				cantidadTotal = api.column(8).data().reduce(function(a, b) {
					return parseInt(a) + parseInt(b);
				}, 0);
				$(api.column(9).footer()).html(montoTotal.toFixed(4));
				$(api.column(8).footer()).html(cantidadTotal);
			} else {
				$(api.column(9).footer()).html("");
				$(api.column(8).footer()).html("");
			}
		},
		"createdRow" : function(row, data, dataIndex) {
			if (data.montoTransaccion > 0) {
				$(row).find(".monto").addClass("color-blue");
			} else if (data.montoTransaccion == 0) {
				$(row).find(".monto").addClass("color-inherit");
			} else {
				$(row).find(".monto").addClass("color-red");
			}
		}
	});

	$local.$tablaResumenMovimiento.wrap("<div class='table-responsive'></div>");
	$tablaFuncion.aniadirBotonEnTabla($(".dataTables_filter"), $variableUtil.tableBotonExportarXlsx, $variableUtil.posDerecho);
	$local.$exportarXlsx = $("#exportarXlsx");

	$local.$buscar.on("click", function() {
		var criterioBusqueda = $formCriterioBusquedaReporte.serializeJSON();
		var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaBusqueda);
		criterioBusqueda.fechaInicio = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaFin = rangoFechaBusqueda.fechaFin;
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "reporte/resumen/movimiento/transaccionAprobadaAgencia?accion=buscar",
			data : criterioBusqueda,
			beforeSend : function() {
				$local.tablaResumenMovimiento.clear().draw();
				$local.$buscar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success : function(reporteResumenTransaccionAprobadaAgencia) {
				if (reporteResumenTransaccionAprobadaAgencia.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				$local.tablaResumenMovimiento.rows.add(reporteResumenTransaccionAprobadaAgencia).draw();
				$tablaFuncion.pintarMontosComisiones($local.$tablaResumenMovimiento, "tfoot td.monto");
			},
			complete : function() {
				$local.$buscar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$exportarXlsx.on("click", function() {
		var criterioBusqueda = $formCriterioBusquedaReporte.serializeJSON();
		var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaBusqueda);
		criterioBusqueda.fechaInicio = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaFin = rangoFechaBusqueda.fechaFin;

		criterioBusqueda.descripcionAgencia = $local.$agencias.find("option:selected").text();
		criterioBusqueda.descripcionSexo = $local.$sexos.find("option:selected").text();
		criterioBusqueda.descripcionEstadoCivil = $local.$estadosCivil.find("option:selected").text();
		var descripcionRangoFechas = $local.$rangoFechaBusqueda.val();
		criterioBusqueda.descripcionRangoFechas = descripcionRangoFechas == "" || descripcionRangoFechas == undefined ? "TODOS" : descripcionRangoFechas;

		var paramCriterioBusqueda = $.param(criterioBusqueda);
		window.location.href = $variableUtil.root + "reporte/resumen/movimiento/transaccionAprobadaAgencia?accion=exportar&" + paramCriterioBusqueda;
	});
});