$(document).ready(function() {

	var $local = {
		$rangoFechaBusqueda: $("#rangoFechaBusquedaCanal"),
		$exportar: $("#exportar"),
		$buscar: $("#buscar"),
		$navTabla: $("#navTabla"),
		$resultadoBusqueda: $("#resultadoBusqueda"),
		$tablaComisiones: $("table.tablaComisiones"),
		$tablaResultadoBusqueda: $("#tablaResultadoBusqueda"),
		tablaResultadoBusqueda: "",
		$tablaResultadoBusquedaCuerpo: "",
		tablaComisiones: "",
		$rolTransaccion: $(".rolTransaccion"),
		$codigoRespuestaTransaccion: $(".codigoRespuestaTransaccion"),
		$tipoMoneda: $(".tipoMoneda"),
		$instituciones: $("#selectInstitucionCanal"),
		$empresas: $("#selectEmpresaCanal"),
		$clientes: $("#selectClienteCanal")
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
					return $funcionUtil.unirCodigoDescripcion(row.idCanal, row.descripcionCanal);
				},
				"title": "Canal"
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
				"data": "cantidadCanal",
				"title": "Cantidad"
			}, {
				"data": 'montoCanal',
				"render": $tablaFuncion.formatoMonto(),
				"title": "Monto"
			}, {
				"data": 'comisionTHB',
				"render": $tablaFuncion.formatoComision(),
				"title": "THB"
			}, {
				"data": 'comisionEST',
				"render": $tablaFuncion.formatoComision(),
				"title": "EST"
			}, {
				"data": 'comisionREC',
				"render": $tablaFuncion.formatoComision(),
				"title": "REC"
			}, {
				"data": 'comisionOPE',
				"render": $tablaFuncion.formatoComision(),
				"title": "OPE"
			}, {
				"data": 'comisionISA',
				"render": $tablaFuncion.formatoComision(),
				"title": "ISA"
			}, {
				"data": 'comisionOIF',
				"render": $tablaFuncion.formatoComision(),
				"title": "OIF"
			}, {
				"data": 'comisionING',
				"render": $tablaFuncion.formatoComision(),
				"title": "ING"
			}, {
				"data": 'comisionCOI',
				"render": $tablaFuncion.formatoComision(),
				"title": "COI"
			}, {
				"data": 'comisionTIC',
				"render": $tablaFuncion.formatoComision(),
				"title": "TIC"
			}, {
				"data": 'comisionINT',
				"render": $tablaFuncion.formatoComision(),
				"title": "INT"
			}, {
				"data": 'comisionTOTAL',
				"render": $tablaFuncion.formatoComision(),
				"title": "Total"
			},
		],
		"footerCallback": function(row, data, start, end, display) {
			var tieneData = $local.tablaResultadoBusqueda == "" ? false : $local.tablaResultadoBusqueda.data().any();
			var api = this.api(), data;
			api.columns(".comision").every(function() {
				var index = this.selector.cols;
				var suma = this.data().reduce(function(a, b) {
					return parseFloat(a) + parseFloat(b);
				}, 0);
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
				const valorMonto = parseFloat(celda.text());
				if (valorMonto > 0) {
					celda.addClass("color-blue");
				} else if (valorMonto < 0) {
					celda.addClass("color-red");
				} else {
					celda.addClass("color-inherit");
				}
			});
			$(row).find(".comision").filter(function() {
				let celda = $(this);
				const valorComision = parseFloat(celda.text());
				if (valorComision > 0) {
					celda.addClass("color-blue");
				} else if (valorComision < 0) {
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
			url: $variableUtil.root + "reporte/compensacion/emisor/canal?accion=buscar",
			data: criterioBusqueda,
			beforeSend: function(xhr) {
				$local.tablaResultadoBusqueda.clear().draw();
				$local.$buscar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success: function(reporteCompensacionEmisor) {
				if (reporteCompensacionEmisor.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				$local.tablaResultadoBusqueda.rows.add(reporteCompensacionEmisor).draw();
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
		window.location.href = `${$variableUtil.root}reporte/compensacion/emisor/canal?rutaEnSidebar=${$variableUtil.rutaEnSidebar}&accion=exportar&${paramCriterioBusqueda}`;
	});

});