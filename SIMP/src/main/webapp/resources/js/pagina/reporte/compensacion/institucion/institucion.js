$(document).ready(function() {

	var $local = {
		$rangoFechaBusqueda: $("#rangoFechaBusqueda"),
		$exportar: $("#exportar"),
		$buscar: $("#buscar"),
		$navTabla: $("#navTabla"),
		$resultadoBusqueda: $("#resultadoBusqueda"),
		$tablaComisiones: $("table.tablaComisiones"),
		$instituciones: $('#instituciones'),
		$tablaResultadoBusqueda: $("#tablaResultadoBusqueda"),
		tablaResultadoBusqueda: "",
		$tablaResultadoBusquedaCuerpo: "",
		tablaComisiones: "",
		$rolTransaccion: $(".rolTransaccion"),
		$codigoRespuestaTransaccion: $(".codigoRespuestaTransaccion"),
		$tipoMoneda: $(".tipoMoneda"),

		$empresas: $('#empresas'),
		$clientes: $('#clientes'),
	};

	$local.$tablaResultadoBusquedaCuerpo = $local.$tablaResultadoBusqueda.find("tbody");
	$formCriterioBusquedaReporte = $("#formCriterioBusquedaReporte");
	$funcionUtil.crearDateRangePickerSimple($local.$rangoFechaBusqueda);
	$funcionUtil.crearSelect2($local.$instituciones);
	$funcionUtil.crearSelect2($local.$empresas, "Seleccione una Empresa");
	$funcionUtil.crearSelect2Multiple($local.$clientes, "-1", "TODOS");

	$.fn.dataTable.moment('DD/MM/YYYY');

	$local.$empresas.on("change", () => {
		const opcionSeleccionada = $local.$empresas.val();
		if (!opcionSeleccionada) {
			$local.$clientes.find("option").remove();
			return;
		}
		$.ajax({
			type: "GET",
			url: `${$variableUtil.root}cliente/empresa/${opcionSeleccionada}`,
			beforeSend: () => {
				$local.$clientes.find("option").remove();
				$local.$clientes.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Clientes...</span>")
			},
			success: (response) => {
				$.each(response, (i) => {
					$local.$clientes.append($("<option/>").val(response[i].idCliente).text(response[i].idCliente + " - " + response[i].descripcion));
				});
			},
			complete: () => {
				$local.$clientes.parent().find(".cargando").remove();
			}
		});
	});

	$local.tablaResultadoBusqueda = $local.$tablaResultadoBusqueda.DataTable({
		"initComplete": function() {
			$local.$tablaResultadoBusqueda.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaResultadoBusqueda);
		},
		"language": {
			"emptyTable": "No hay transacciones encontradas."
		},
		"order": [],
		"columnDefs": [
			{
				"targets": [0, 1, 2],
				"className": "all filtrable",
			}, {
				"targets": 3,
				"className": "all filtrable dt-right"
			}, {
				"targets": 4,
				"className": "all filtrable dt-right monto"
			}, {
				"targets": [5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15],
				"className": "all filtrable dt-right comision"
			}
		],
		"columns": [
			{
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.idInstitucion, row.descripcionInstitucion);
				},
				"title": "Instituci\u00F3n"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.idEmpresa, row.descEmpresa);
				},
				"title": "Empresa"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.idCliente, row.descCliente);
				},
				"title": "Cliente"
			}, {
				"data": 'cantidadInstitucion',
				"title": "Cantidad"
			}, {
				"data": 'montoInstitucion',
				"render": $tablaFuncion.formatoMonto(),
				"title": "Monto"
			}, {
				"data": function(row) {
					return row.comisionTHB == null ? "0.0000" : row.comisionTHB;
				},
				"render": $tablaFuncion.formatoComision(),
				"title": "THB"
			}, {
				"data": function(row) {
					return row.comisionEST == null ? "0.0000" : row.comisionEST;
				},
				"render": $tablaFuncion.formatoComision(),
				"title": "EST"
			}, {
				"data": function(row) {
					return row.comisionREC == null ? "0.0000" : row.comisionREC;
				},
				"render": $tablaFuncion.formatoComision(),
				"title": "REC"
			}, {
				"data": function(row) {
					return row.comisionOPE == null ? "0.0000" : row.comisionOPE;
				},
				"render": $tablaFuncion.formatoComision(),
				"title": "OPE"
			}, {
				"data": function(row) {
					return row.comisionISA == null ? "0.0000" : row.comisionISA;
				},
				"render": $tablaFuncion.formatoComision(),
				"title": "ISA"
			}, {
				"data": function(row) {
					return row.comisionOIF == null ? "0.0000" : row.comisionOIF;
				},
				"render": $tablaFuncion.formatoComision(),
				"title": "OIF"
			}, {
				"data": function(row) {
					return row.comisionING == null ? "0.0000" : row.comisionING;
				},
				"render": $tablaFuncion.formatoComision(),
				"title": "ING"
			}, {
				"data": function(row) {
					return row.comisionCOI == null ? "0.0000" : row.comisionCOI;
				},
				"render": $tablaFuncion.formatoComision(),
				"title": "COI"
			}, {
				"data": function(row) {
					return row.comisionTIC == null ? "0.0000" : row.comisionTIC;
				},
				"render": $tablaFuncion.formatoComision(),
				"title": "TIC"
			}, {
				"data": function(row) {
					return row.comisionINT == null ? "0.0000" : row.comisionINT;
				},
				"render": $tablaFuncion.formatoComision(),
				"title": "INT"
			}, {
				"data": function(row) {
					return row.comisionTOTAL == null ? "0.0000" : row.comisionTOTAL;
				},
				"render": $tablaFuncion.formatoComision(),
				"title": "Total"
			}
		],
		"footerCallback": function(row, data, start, end, display) {
			var tieneData = $local.tablaResultadoBusqueda == "" ? false : $local.tablaResultadoBusqueda.data().any();
			var api = this.api(), data;
			api.columns(".comision").every(function() {
				var suma = this.data().reduce(function(a, b) {
					return parseFloat(a) + parseFloat(b);
				}, 0);
				var index = this.selector.cols;
				$(api.column(index).footer()).html(tieneData ? $funcionUtil.formatMoney(suma, 4) : "");
			});
			if (tieneData) {
				cantidadTotal = api.column(3).data().reduce(function(a, b) {
					return parseInt(a) + parseInt(b);
				}, 0);
				monto = api.column(4).data().reduce(function(a, b) {
					return parseFloat(a) + parseFloat(b);
				}, 0);
				$(api.column(3).footer()).html(cantidadTotal);
				$(api.column(4).footer()).html($funcionUtil.formatMoney(monto, 2));
			} else {
				$(api.column(3).footer()).html("");
				$(api.column(4).footer()).html("");
			}
		},
		"createdRow": function(row, data, dataIndex) {
			$(row).find(".monto").filter(function() {
				let celda = $(this);
				let valor = parseFloat(celda.text());
				if (valor > 0) {
					celda.addClass("color-blue");
				} else if (valor < 0) {
					celda.addClass("color-red");
				} else {
					celda.addClass("color-inherit");
				}
			});
			$(row).find(".comision").filter(function() {
				let celda = $(this);
				let valor = parseFloat(celda.text());
				if (valor > 0) {
					celda.addClass("color-blue");
				} else if (valor < 0) {
					celda.addClass("color-red");
				} else {
					celda.addClass("color-inherit");
				}
			});
		},
		"headerCallback": function(thead, data, start, end, display) {
			$(thead).find('th').eq(5).attr({
				"data-tooltip": "tooltip",
				"title": $variableUtil.comisionTarjetahabiente
			});
			$(thead).find('th').eq(6).attr({
				"data-tooltip": "tooltip",
				"title": $variableUtil.comisionEstablecimiento
			});
			$(thead).find('th').eq(7).attr({
				"data-tooltip": "tooltip",
				"title": $variableUtil.comisionReceptor
			});
			$(thead).find('th').eq(8).attr({
				"data-tooltip": "tooltip",
				"title": $variableUtil.comisionOperador
			});
			$(thead).find('th').eq(9).attr({
				"data-tooltip": "tooltip",
				"title": $variableUtil.comisionIsa
			});
			$(thead).find('th').eq(10).attr({
				"data-tooltip": "tooltip",
				"title": $variableUtil.comisionOif
			});
			$(thead).find('th').eq(11).attr({
				"data-tooltip": "tooltip",
				"title": $variableUtil.comisionIng
			});
			$(thead).find('th').eq(12).attr({
				"data-tooltip": "tooltip",
				"title": $variableUtil.comisionCobroInteres
			});
			$(thead).find('th').eq(13).attr({
				"data-tooltip": "tooltip",
				"title": $variableUtil.comisionTipoC
			});
			$(thead).find('th').eq(14).attr({
				"data-tooltip": "tooltip",
				"title": $variableUtil.comisionIntercambio
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
		if (!$formCriterioBusquedaReporte.valid()) {
			return;
		}
		var criterioBusqueda = $formCriterioBusquedaReporte.serializeJSON();
		criterioBusqueda.fechaInicio = $local.$rangoFechaBusqueda.data("daterangepicker").startDate.format('YYYY-MM-DD');
		criterioBusqueda.fechaFin = $local.$rangoFechaBusqueda.data("daterangepicker").endDate.format('YYYY-MM-DD');

		criterioBusqueda.idCliente = $funcionUtil.limpiarCadenaPeticionGetDeSelect2MultiplePaginacion($funcionUtil.crearCadenaPeticionGetDeSelect2MultiplePaginacion($local.$clientes));

		$.ajax({
			type: "GET",
			url: $variableUtil.root + "reporte/compensacion/emisor/institucion?accion=buscar",
			data: criterioBusqueda,
			beforeSend: function(xhr) {
				$local.tablaResultadoBusqueda.clear().draw();
				$local.$buscar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success: function(reporteCompensacionInstitucion) {
				if (reporteCompensacionInstitucion.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				$local.tablaResultadoBusqueda.rows.add(reporteCompensacionInstitucion).draw();
				$tablaFuncion.pintarMontosComisiones($local.$tablaResultadoBusqueda, "tfoot td.monto");
			},
			error: function() {
			},
			complete: function() {
				$local.$buscar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$exportar.on("click", function() {
		if (!$formCriterioBusquedaReporte.valid()) {
			return;
		}
		var criterioBusqueda = $formCriterioBusquedaReporte.serializeJSON();
		criterioBusqueda.fechaInicio = $local.$rangoFechaBusqueda.data("daterangepicker").startDate.format('YYYY-MM-DD');
		criterioBusqueda.fechaFin = $local.$rangoFechaBusqueda.data("daterangepicker").endDate.format('YYYY-MM-DD');
		var descripcionRangoFechas = $local.$rangoFechaBusqueda.val();
		criterioBusqueda.descripcionRangoFechas = descripcionRangoFechas == "" || descripcionRangoFechas == undefined ? "TODOS" : descripcionRangoFechas;
		criterioBusqueda.descripcionRolTransaccion = $local.$rolTransaccion.filter(":checked").parent("label").text().trim();
		criterioBusqueda.descripcionCodigoRespuestaTransaccion = $local.$codigoRespuestaTransaccion.filter(":checked").parent("label").text().trim();
		criterioBusqueda.descripcionTipoMoneda = $local.$tipoMoneda.filter(":checked").parent("label").text().trim();
		criterioBusqueda.descripcionInstitucion = $local.$instituciones.find('option:selected').text();
		criterioBusqueda.descripcionEmpresa = $local.$empresas.find("option:selected").val() === "-1" ? "TODOS" : $local.$empresas.find("option:selected").text();
		
		criterioBusqueda.idCliente = $funcionUtil.limpiarCadenaPeticionGetDeSelect2MultiplePaginacion($funcionUtil.crearCadenaPeticionGetDeSelect2MultiplePaginacion($local.$clientes));
		criterioBusqueda.descripcionCliente = !!$local.$clientes.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$clientes, "; ") : "TODOS";

		
		var paramCriterioBusqueda = $.param(criterioBusqueda);

		$funcionUtil.webSocketMethod('exportacionPOI');
		window.location.href = `${$variableUtil.root}reporte/compensacion/emisor/institucion?rutaEnSidebar=${$variableUtil.rutaEnSidebar}&accion=exportar&${paramCriterioBusqueda}`;
	});
});
