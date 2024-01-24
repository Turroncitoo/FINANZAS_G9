$(document).ready(function() {

	var $local = {
		$buscar: $("#buscar"),
		$exportar: $("#exportar"),
		$navTabla: $("#navTabla"),
		$resultadoBusqueda: $("#resultadoBusqueda"),
		$rangoFechas: $("#rangoFechaBusqueda"),
		$tablaComisiones: $("table.tablaComisiones"),
		tablaComisiones: "",
		tablasComisiones: {},
		$tipoMoneda: $(".tipoMoneda"),
		$codigoRespuestaTransaccion: $(".codigoRespuestaTransaccion")

	};

	$formCriterioBusquedaCompensacion = $("#formCriterioBusquedaReporte");

	$funcionUtil.crearDateRangePickerSimple($local.$rangoFechas);

	$('a[data-toggle="tab"]').on('shown.bs.tab', function(e) {
		$.fn.dataTable.tables({
			visible: true,
			api: true
		}).columns.adjust();
	});

	$local.$tablaComisiones.filter(function() {
		var tablaComision = $(this);
		$local.$encabezados = tablaComision.find("th.conceptoComision");
		var idTabla = tablaComision.attr("idTabla");
		$local.tablasComisiones[idTabla] = tablaComision.DataTable({
			"scrollX": true,
			"scrollCollapse": true,
			"language": {
				"emptyTable": "No hay transacciones encontradas."
			},
			"fixedColumns": {
				"leftColumns": 1
			},
			"columnDefs": [{
				"targets": 0,
				"className": "all",
			}, {
				"targets": 1,
				"className": "all dt-right"
			}, {
				"targets": [2, 3, 4, 5, 6, 7],
				"className": "all dt-right comision monto"
			}],
			"columns": [{
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.idAtm, row.descripcionAtm);
				},
			}, {
				"data": "cantidad",
			}, {
				"data": function(row) {
					return row.monto.toFixed(2);
				}
			}, {
				"data": function(row) {
					return row.comisionInt.toFixed(4);
				}
			}, {
				"data": function(row) {
					return row.comisionGas.toFixed(4);
				}
			}, {
				"data": function(row) {
					return row.comisionOpe.toFixed(4);
				}
			}, {
				"data": function(row) {
					return row.comisionSur.toFixed(4);
				}
			}, {
				"data": function(row) {
					return row.totalComision.toFixed(4);
				}
			}],
			"footerCallback": function(row, data, start, end, display) {
				var api = this.api(), data;
				var tieneData = false;
				api.columns('.comision').every(function() {
					var index = this.selector.cols;
					if (this.data().length > 0) {
						tieneData = true;
						var suma = this.data().reduce(function(a, b) {
							return parseFloat(a) + parseFloat(b);
						}, 0);
						$(api.column(index).footer()).html(suma.toFixed(4));
						$(api.column(index).footer()).removeClassRegex("color-");
						if (parseFloat(suma) > 0) {
							$(api.column(index).footer()).addClass("color-blue");
						} else if (parseFloat(suma) < 0) {
							$(api.column(index).footer()).addClass("color-red");
						} else {
							$(api.column(index).footer()).addClass("color-inherit");
						}
					} else {
						$(api.column(index).footer()).html("");
					}
				});
				if (tieneData) {
					cantidadTotal = api.column(1).data().reduce(function(a, b) {
						return parseInt(a) + parseInt(b);
					}, 0);
					monto = api.column(2).data().reduce(function(a, b) {
						return parseFloat(a) + parseFloat(b);
					}, 0);
					$(api.column(1).footer()).html(cantidadTotal);
					$(api.column(2).footer()).html(monto.toFixed(2));
					$(api.column(2).footer()).removeClassRegex("color-");
					if (monto > 0) {
						$(api.column(2).footer()).addClass("color-blue");
					} else if (monto < 0) {
						$(api.column(2).footer()).addClass("color-red");
					} else {
						$(api.column(2).footer()).addClass("color-inherit");
					}
				} else {
					$(api.column(1).footer()).html("");
					$(api.column(2).footer()).html("");
				}
			},
			"createdRow": function(row, data, dataIndex) {
				$(row).find(".monto").filter(function() {
					var celda = $(this);
					var valor = parseFloat(celda.text());
					if (valor > 0) {
						celda.addClass("color-blue");
					} else if (valor < 0) {
						celda.addClass("color-red");
					} else {
						celda.addClass("color-inherit");
					}
				});
			}
		});
	});

	$local.$buscar.on("click", function() {
		if (!$formCriterioBusquedaCompensacion.valid()) {
			return true;
		}
		var criterioBusqueda = $formCriterioBusquedaCompensacion.serializeJSON();
		criterioBusqueda.fechaInicio = $local.$rangoFechas.data("daterangepicker").startDate.format('YYYY-MM-DD');
		criterioBusqueda.fechaFin = $local.$rangoFechas.data("daterangepicker").endDate.format('YYYY-MM-DD');
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "reporte/compensacion/receptor?accion=buscar",
			data: criterioBusqueda,
			beforeSend: function() {
				$.each($local.tablasComisiones, function(i, tablaComision) {
					$local.tablasComisiones[i].clear().draw();
				});
				$local.$buscar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success: function(reporteCompensaciones) {
				if (reporteCompensaciones.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				$.each(reporteCompensaciones, function(i, reporteCompensacion) {
					var tabla = $local.tablasComisiones["tabla" + reporteCompensacion.idMembresia];
					tabla.rows.add(reporteCompensacion.atms).draw();
				});
			},
			error: function() {
			},
			complete: function() {
				$local.$buscar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$exportar.on("click", function() {
		var criterioBusqueda = $formCriterioBusquedaCompensacion.serializeJSON();
		criterioBusqueda.fechaInicio = $local.$rangoFechas.data("daterangepicker").startDate.format('YYYY-MM-DD');
		criterioBusqueda.fechaFin = $local.$rangoFechas.data("daterangepicker").endDate.format('YYYY-MM-DD');
		var descripcionRangoFechas = $local.$rangoFechas.val();
		criterioBusqueda.descripcionRangoFechas = descripcionRangoFechas == "" || descripcionRangoFechas == undefined ? "TODOS" : descripcionRangoFechas;
		criterioBusqueda.descripcionCodigoRespuestaTransaccion = $local.$codigoRespuestaTransaccion.filter(":checked").parent("label").text().trim();
		criterioBusqueda.descripcionTipoMoneda = $local.$tipoMoneda.filter(":checked").parent("label").text().trim();
		var paramCriterioBusqueda = $.param(criterioBusqueda);
		window.location.href = $variableUtil.root + "reporte/compensacion/receptor?accion=exportar&" + paramCriterioBusqueda;
	});

});