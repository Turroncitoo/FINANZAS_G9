$(document).ready(function() {

	var $local = {
		$btnBuscarResumen: $("#btnBuscarResumen"),
		$btnBuscarDetalle: $("#btnBuscarDetalle"),
		$btnExportarResumen: $("#btnExportarResumen"),
		$btnExportarDetalle: $("#btnExportarDetalle"),
		$btnBuscarResumenPorPeriodo: $("#btnBuscarResumenPorPeriodo"),
		$btnExportarResumenPorPeriodo: $("#btnExportarResumenPorPeriodo"),

		$rangoFechaResumen: $("#rangoFechaProcesoResumen"),
		$rangoFechaDetalle: $("#rangoFechaProcesoDetalle"),
		$rangoFechaResumenPorPeriodo: $("#rangoFechaProcesoResumenPorPeriodo"),

		$institucionesResumen: $('#institucionesResumen'),
		$institucionesDetalle: $('#institucionesDetalle'),
		$institucionesResumenPorPeriodo: $('#institucionesResumenPorPeriodo'),

		$tipoBusqueda: $("input[type='radio'][name='tipoBusqueda']"),
		$tipoVisualizacionDetalle: $("input[type='radio'][name='tipoVisualizacion']"),

		$resumenContent: $('#resumenContent'),
		$detalleContent: $('#detalleContent'),
		$resumenPorPeriodoContent: $('#resumenPorPeriodoContent'),

		$rolesDetalle: $("input[type='checkbox'][name='rol']"),
		$tiposDetalle: $("input[type='checkbox'][name='tipoDetalle']"),

		$resultadoBusquedaResumenContent: $('#resultadoBusquedaResumen'),
		$resultadoBusquedaResumenPorPeriodoContent: $('#resultadoBusquedaResumenPorPeriodo'),
		$resultadoBusquedaDetalleContent: $('#resultadoBusquedaDetalle'),
		$resultadoBusquedaDetalleDisgregadoContent: $('#resultadoBusquedaDetalle-D'),
		$resultadoBusquedaDetalleJuntoContent: $('#resultadoBusquedaDetalle-J'),

		$tablaResultados: $("table.tablaResultadosResumen"),
		$tablaResultadosPorPeriodo: $("table.tablaResultadosResumenPorPeriodo"),
		$tablaResultadosDetalle: $("table.tablaResultadosDetalle"),
		tablaResultados: "",
		tablaResultadosDetalle: "",
		tablasResultados: {},
		tablasResultadosPorPeriodo: {},
		tablasResultadosDetalle: {},
		filtrosSeleccionables: [],
	};

	$formTipoReporteResumen = $('#formTipoReporteResumen');
	$formTipoReporteResumenPorPeriodo = $("#formTipoReporteResumenPorPeriodo");
	$formTipoReporteDetalle = $('#formTipoReporteDetalle');

	$funcionUtil.crearDateRangePickerSimple($local.$rangoFechaResumen);
	$funcionUtil.crearDateRangePickerSimple($local.$rangoFechaResumenPorPeriodo);
	$funcionUtil.crearDateRangePickerSimple($local.$rangoFechaDetalle);
	$funcionUtil.crearSelect2($local.$institucionesResumen, "Seleccione una instituci\u00F3n");
	$funcionUtil.crearSelect2($local.$institucionesDetalle, "Seleccione una instituci\u00F3n");
	$funcionUtil.crearSelect2($local.$institucionesResumenPorPeriodo, "Seleccione una instituci\u00F3n");

	$('a[data-toggle="tab"]').on('shown.bs.tab', function(e) {
		ajustarColumnas();
	});

	$local.$tablaResultados.filter(function() {
		var tabla = $(this);
		var idTabla = tabla.attr("idTabla");
		$local.tablasResultados[idTabla] = tabla.DataTable({
			"language": {
				"emptyTable": "No hay transacciones encontradas."
			},
			"initComplete": function() {
				tabla.wrap("<div class='table-responsive'></div>");
			},
			"columnDefs": [{
				"targets": [0, 1, 2],
				"className": "all filtrable",
			}, {
				"targets": [3, 4],
				"className": "all filtrable dt-right monto"
			}],
			"columns": [{
				"data": 'fechaProceso',
				"title": 'Fecha Proceso'
			},
			{
				"data": 'cuentaContable',
				"title": "Cuenta Contable"
			}, {
				"data": 'descripcionCuenta',
				"title": "Descripci\u00F3n Cuenta"
			}, {
				"data": function(row) {
					return row.montoDebito.formatMoney(2);
				},
				"title": "Monto D\u00E9bito"
			}, {
				"data": function(row) {
					return row.montoCredito.formatMoney(2);
				},
				"title": "Monto Cr\u00E9dito"
			}],
			"footerCallback": function(row, data, start, end, display) {
				var api = this.api();
				api.columns('.monto').every(function() {
					var index = this.selector.cols;
					var dataCol = this.data();
					var suma = 0;
					data.forEach(function(e) {
						var key = index === 3 ? 'montoDebito' : 'montoCredito';
						//suma += (Math.round(e[key]*100)/100);
						suma += e[key];
					});
					if (dataCol.length > 0) {
						$(api.column(index).footer()).html(suma.formatMoney(2));
						$(api.column(index).footer()).removeClassRegex("color-");
						if (suma > 0) {
							$(api.column(index).footer()).addClass("color-blue");
						} else if (suma < 0) {
							$(api.column(index).footer()).addClass("color-red");
						} else {
							$(api.column(index).footer()).addClass("color-inherit");
						}
					} else {
						$(api.column(index).footer()).html("");
					}
				});
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
			},
			"order": [
				[0, "desc"]
			]
		});
	});

	$local.$tablaResultadosDetalle.filter(function() {
		var tabla = $(this);
		var idTabla = tabla.attr("idTabla");
		if (idTabla != 'tablaDetalleRol-M' && idTabla != 'tablaDetalleTipo-M') {
			$local.tablasResultadosDetalle[idTabla] = tabla.DataTable({
				"language": {
					"emptyTable": "No hay registros encontrados."
				},
				"initComplete": function() {
					tabla.wrap("<div class='table-responsive'></div>");
					var filtrosSeleccionables = {};
					filtrosSeleccionables["0"] = $('.monedas-filtroParaTablaDetalle').html();
					$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, tabla, filtrosSeleccionables);
				},
				"columnDefs": [{
					"targets": [0],
					"className": "all seleccionable select2 insertable-opciones-html",
				}, {
					"targets": [1, 2, 3, 4, 5, 6, 7, 8, 9],
					"className": "all filtrable",
				}, {
					"targets": [10, 11],
					"className": "all filtrable dt-right monto"
				}],
				"columns": [{
					"data": function(row) {
						return $funcionUtil.unirCodigoDescripcion(row.codigoMoneda, row.descripcionMoneda);
					},
					"title": "Moneda",
				}, {
					"data": 'fechaProceso',
					"title": 'Fecha Proceso'
				}, {
					"data": 'cuentaContable',
					"title": "Cuenta Contable"
				}, {
					"data": 'descripcionCuenta',
					"title": "Descripci\u00F3n Cuenta"
				}, {
					"data": 'descripcionMembresia',
					"title": "Membres\u00EDa"
				}, {
					"data": 'descripcionServicio',
					"title": "Servicio"
				}, {
					"data": 'descripcionOrigen',
					"title": "Origen"
				}, {
					"data": function(row) {
						return $funcionUtil.unirCodigoDescripcion(row.idClaseTransaccion, row.descripcionClaseTransaccion);
					},
					"title": "Clase Transacci\u00F3n"
				}, {
					"data": function(row) {
						return $funcionUtil.unirCodigoDescripcion(row.idCodigoTransaccion, row.descripcionCodigoTransaccion);
					},
					"title": "C\u00F3digo Transacci\u00F3n"
				}, {
					"data": 'descripcionComision',
					"title": "Descripci\u00F3n Comisi\u00F3n"
				}, {
					"data": function(row) {
						return $funcionUtil.formatMoney(row.montoDebito);
					},
					"title": "Monto D\u00E9bito"
				}, {
					"data": function(row) {
						return $funcionUtil.formatMoney(row.montoCredito);
					},
					"title": "Monto Cr\u00E9dito"
				}],
				"footerCallback": function(row, data, start, end, display) {
					var api = this.api();
					api.columns('.monto', { search: 'applied' }).every(function() {
						var index = this.selector.cols;
						var dataCol = this.data();
						if (dataCol.length > 0) {
							var suma = this.data().reduce(function(a, b) {
								return $funcionUtil.formatMoneyToNumber(a) + $funcionUtil.formatMoneyToNumber(b);
							});
							$(api.column(index).footer()).html($funcionUtil.formatMoney(suma, 2));
							$(api.column(index).footer()).removeClassRegex("color-");
							if (suma > 0) {
								$(api.column(index).footer()).addClass("color-blue");
							} else if (suma < 0) {
								$(api.column(index).footer()).addClass("color-red");
							} else {
								$(api.column(index).footer()).addClass("color-inherit");
							}
						} else {
							$(api.column(index).footer()).html("");
						}
					});
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
		} else {
			$local.tablasResultadosDetalle[idTabla] = tabla.DataTable({
				"language": {
					"emptyTable": "No hay registros encontrados."
				},
				"initComplete": function() {
					tabla.wrap("<div class='table-responsive'></div>");
					var filtrosSeleccionables = {};
					filtrosSeleccionables["0"] = $('.monedas-filtroParaTablaDetalle').html();
					$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, tabla, filtrosSeleccionables);
				},
				"columnDefs": [{
					"targets": [0],
					"className": "all seleccionable select2 insertable-opciones-html",
				}, {
					"targets": [1, 2, 3, 4, 5, 6, 7, 8, 9],
					"className": "all filtrable",
				}, {
					"targets": [10, 11, 15, 19],
					"className": "all filtrable dt-right monto",
				}, {
					"targets": [12, 13, 14, 16, 17, 18, 20],
					"className": "all filtrable dt-right",
				}],
				"columns": [{
					"data": function(row) {
						return $funcionUtil.unirCodigoDescripcion(row.codigoMoneda, row.descripcionMoneda);
					},
					"title": "Moneda"
				}, {
					"data": 'fechaProceso',
					"title": 'Fecha Proceso'
				}, {
					"data": 'cuentaContable',
					"title": "Cuenta Contable"
				}, {
					"data": 'descripcionCuenta',
					"title": "Descripci\u00F3n Cuenta"
				}, {
					"data": 'descripcionMembresia',
					"title": "Membres\u00EDa"
				}, {
					"data": 'descripcionServicio',
					"title": "Servicio"
				}, {
					"data": 'descripcionOrigen',
					"title": "Origen"
				}, {
					"data": function(row) {
						return $funcionUtil.unirCodigoDescripcion(row.idClaseTransaccion, row.descripcionClaseTransaccion);
					},
					"title": "Clase Transacci\u00F3n"
				}, {
					"data": function(row) {
						return $funcionUtil.unirCodigoDescripcion(row.idCodigoTransaccion, row.descripcionCodigoTransaccion);
					},
					"title": "C\u00F3digo Transacci\u00F3n"
				}, {
					"data": 'descripcionComision',
					"title": "Descripci\u00F3n Comisi\u00F3n"
				}, {
					"data": function(row) {
						return $funcionUtil.formatMoney(row.montoDebito);
					},
					"title": "Monto D\u00E9bito"
				}, {
					"data": function(row) {
						return $funcionUtil.formatMoney(row.montoCredito);
					},
					"title": "Monto Cr\u00E9dito"
				}, { // AGREGADOS
					"data": function(row) {
						return row.codigoEventoMarcaInternacional;
					},
					"title": "C\u00F3digo Evento Marca Internacional"
				}, {
					"data": function(row) {
						return row.distribucionCobroMarcaInternacional;
					},
					"title": "Distribuci\u00F3n Cobro Marca Internacional"
				}, {
					"data": function(row) {
						return row.numeroFacturaMarca;
					},
					"title": "N\u00FAmero Factura Marca"
				}, {
					"data": function(row) {
						return $funcionUtil.formatMoney(row.tarifaMarcaInternacional);
					},
					"title": "Tarifa Marca Internacional"
				}, {
					"data": function(row) {
						return row.totalUnidades;
					},
					"title": "Total Unidades"
				}, {
					"data": function(row) {
						return row.indicadorDistribucionCobro;
					},
					"title": "Indicador Distribuci\u00F3n Cobro"
				}, {
					"data": function(row) {
						return row.indicadorUnidades;
					},
					"title": "Indicador Unidades"
				}, {
					"data": function(row) {
						return $funcionUtil.formatMoney(row.valorFacturaMarcaInternacional);
					},
					"title": "Valor Factura Marca Internacional"
				}, {
					"data": function(row) {
						return row.secuenciaRegistroFacturaMarca;
					},
					"title": "Secuencia Registro Factura Marca"
				}],
				"footerCallback": function(row, data, start, end, display) {
					var api = this.api();
					api.columns('.monto', { search: 'applied' }).every(function() {
						var index = this.selector.cols;
						var dataCol = this.data();
						if (dataCol.length > 0) {
							var suma = this.data().reduce(function(a, b) {
								return $funcionUtil.formatMoneyToNumber(a) + $funcionUtil.formatMoneyToNumber(b);
							});
							$(api.column(index).footer()).html($funcionUtil.formatMoney(suma, 2));
							$(api.column(index).footer()).removeClassRegex("color-");
							if (suma > 0) {
								$(api.column(index).footer()).addClass("color-blue");
							} else if (suma < 0) {
								$(api.column(index).footer()).addClass("color-red");
							} else {
								$(api.column(index).footer()).addClass("color-inherit");
							}
						} else {
							$(api.column(index).footer()).html("");
						}
					});
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
		}


	});

	$local.$tablaResultadosPorPeriodo.filter(function() {
		var tabla = $(this);
		var idTabla = tabla.attr("idTabla");

		$local.tablasResultadosPorPeriodo[idTabla] = tabla.DataTable({
			"language": {
				"emptyTable": "No hay registros encontrados."
			},
			"initComplete": function() {
				tabla.wrap("<div class='table-responsive'></div>");
			},
			"columnDefs": [{
				"targets": [0, 1],
				"className": "all filtrable",
			}, {
				"targets": [2, 3],
				"className": "all filtrable dt-right monto"
			}],
			"columns": [
				{
					"data": 'cuentaContable',
					"title": "Cuenta Contable"
				}, {
					"data": 'descripcionCuenta',
					"title": "Descripci\u00F3n Cuenta"
				}, {
					"data": function(row) {
						return $funcionUtil.formatMoney(row.montoDebito)//row.montoDebito.formatMoney(2);
					},
					"title": "Monto D\u00E9bito"
				}, {
					"data": function(row) {
						return $funcionUtil.formatMoney(row.montoCredito)//row.montoCredito.formatMoney(2);
					},
					"title": "Monto Cr\u00E9dito"
				}],
			"footerCallback": function(row, data, start, end, display) {
				var api = this.api();
				api.columns('.monto').every(function() {
					var index = this.selector.cols;
					var dataCol = this.data();
					var suma = 0;
					data.forEach(function(e) {
						var key = index === 2 ? 'montoDebito' : 'montoCredito';
						suma += e[key];
					});
					if (dataCol.length > 0) {
						$(api.column(index).footer()).html(suma.formatMoney(2));
						$(api.column(index).footer()).removeClassRegex("color-");
						if (suma > 0) {
							$(api.column(index).footer()).addClass("color-blue");
						} else if (suma < 0) {
							$(api.column(index).footer()).addClass("color-red");
						} else {
							$(api.column(index).footer()).addClass("color-inherit");
						}
					} else {
						$(api.column(index).footer()).html("");
					}
				});
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

	$local.$tablaResultadosDetalle.find("thead").on('keyup', 'input.filtrable', function() {
		var $tabla = $(this).parents('table');
		var idTabla = $tabla.attr("idTabla");
		var tabla = $local.tablasResultadosDetalle[idTabla];
		tabla.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaResultadosDetalle.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		var $tabla = $(this).parents('table');
		var idTabla = $tabla.attr("idTabla");
		var tabla = $local.tablasResultadosDetalle[idTabla];
		tabla.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
		tabla.columns('.monto', { search: 'applied' }).every(function() {
			var index = this.selector.cols;
			var dataCol = this.data();
			if (val !== '') {
				if (dataCol.length > 0) {
					var suma = this.data().reduce(function(a, b) {
						return parseFloat(a) + parseFloat(b);
					});
					$(tabla.column(index).footer()).html(suma.toFixed(2));
					$(tabla.column(index).footer()).removeClassRegex("color-");
					if (suma > 0) {
						$(tabla.column(index).footer()).addClass("color-blue");
					} else if (suma < 0) {
						$(tabla.column(index).footer()).addClass("color-red");
					} else {
						$(tabla.column(index).footer()).addClass("color-inherit");
					}
				} else {
					$(tabla.column(index).footer()).html("");
				}
			} else {
				$(tabla.column(index).footer()).html("");
			}

		});
	});

	$local.$tipoBusqueda.on('change', function() {
		var val = $(this).val();
		switch (val) {
			case 'resumen':
				$local.$resumenContent.removeClass("hidden");
				$local.$detalleContent.addClass("hidden");
				$local.$resumenPorPeriodoContent.addClass("hidden");
				$local.$resultadoBusquedaResumenContent.removeClass("hidden");
				$local.$resultadoBusquedaDetalleContent.addClass("hidden");
				$local.$resultadoBusquedaResumenPorPeriodoContent.addClass("hidden");
				break;
			case 'detalle':
				$local.$detalleContent.removeClass("hidden");
				$local.$resumenContent.addClass("hidden");
				$local.$resumenPorPeriodoContent.addClass("hidden");
				$local.$resultadoBusquedaDetalleContent.removeClass("hidden");
				$local.$resultadoBusquedaResumenPorPeriodoContent.addClass("hidden");
				$local.$resultadoBusquedaResumenContent.addClass("hidden");
				mostrarContenidoVisualizacion($local.$tipoVisualizacionDetalle);
				break;
			case 'porPeriodo':
				$local.$resumenPorPeriodoContent.removeClass("hidden");
				$local.$detalleContent.addClass("hidden");
				$local.$resumenContent.addClass("hidden");
				$local.$resultadoBusquedaResumenPorPeriodoContent.removeClass("hidden");
				$local.$resultadoBusquedaDetalleContent.addClass("hidden");
				$local.$resultadoBusquedaResumenContent.addClass("hidden");
				break;
		}
		ajustarColumnas();
	});

	$local.$tipoVisualizacionDetalle.on('change', function() {
		mostrarContenidoVisualizacion($(this));
		$.each($local.tablasResultadosDetalle, function(idx, table) {
			table.rows().invalidate('data').draw(false);
		});
	});

	$local.$tiposDetalle.on('change', function() {
		var tiposSeleccionados = obtenerItemsSeleccionados($local.$tiposDetalle);
		var rolesSeleccionados = obtenerItemsSeleccionados($local.$rolesDetalle);
		if (tiposSeleccionados.length === 0) {
			$funcionUtil.notificarException("Debe seleccionar al menos un tipo", "fa-warning", "Aviso", "warning");
			$(this).prop('checked', true);
			return;
		}
		var criterios = obtenerCriterios(rolesSeleccionados, tiposSeleccionados, 'value', '');
		var visualizacion = obtenerItemsSeleccionados($local.$tipoVisualizacionDetalle)[0].value;
		mostrarTabs(visualizacion === 'D' ? criterios : obtenerArraySimple(rolesSeleccionados, 'value'), visualizacion);
	});

	$local.$rolesDetalle.on('change', function() {
		var tiposSeleccionados = obtenerItemsSeleccionados($local.$tiposDetalle);
		var rolesSeleccionados = obtenerItemsSeleccionados($local.$rolesDetalle);
		if (rolesSeleccionados.length === 0) {
			$funcionUtil.notificarException("Debe seleccionar al menos un rol", "fa-warning", "Aviso", "warning");
			$(this).prop('checked', true);
			return;
		}
		var criterios = obtenerCriterios(rolesSeleccionados, tiposSeleccionados, 'value', '');
		var visualizacion = obtenerItemsSeleccionados($local.$tipoVisualizacionDetalle)[0].value;
		mostrarTabs(visualizacion === 'D' ? criterios : obtenerArraySimple(rolesSeleccionados, 'value'), visualizacion);
	});

	$local.$btnBuscarResumen.on('click', function() {
		if (!$formTipoReporteResumen.valid()) {
			return;
		}
		var criterioBusqueda = $formTipoReporteResumen.serializeJSON();
		var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaResumen);
		criterioBusqueda.fechaInicio = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaFin = rangoFechaBusqueda.fechaFin;
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "reporte/contable/resumen?accion=buscar",
			contentType: "application/json",
			dataType: "json",
			data: criterioBusqueda,
			beforeSend: function() {
				$.each($local.tablasResultados, function(i, tabla) {
					$local.tablasResultados[i].clear().draw();
				});
				$local.$btnBuscarResumen.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success: function(response) {
				if (response.length === 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				$.each(response, function(idx, value) {
					$local.tablasResultados["tablaResumen-" + value.codigoMoneda].rows.add(value.cuentas).draw();
				});
			},
			error: function(response) {
			},
			complete: function() {
				$local.$btnBuscarResumen.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$btnExportarResumen.on('click', function() {
		if (!$formTipoReporteResumen.valid()) {
			return;
		}
		var criterioBusqueda = $formTipoReporteResumen.serializeJSON();
		var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaResumen);
		criterioBusqueda.fechaInicio = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaFin = rangoFechaBusqueda.fechaFin;
		criterioBusqueda.descripcionRangoFechas = $local.$rangoFechaResumen.val();
		criterioBusqueda.descripcionInstitucion = $local.$institucionesResumen.find('option:selected').text();
		var paramCriterioBusqueda = $.param(criterioBusqueda);
		$funcionUtil.webSocketMethod('reporteContable');
		window.location.href = $variableUtil.root + "reporte/contable/resumen?accion=exportar&" + paramCriterioBusqueda
			+ "&rutaEnSidebar=" + $variableUtil.rutaEnSidebar;
	});

	$local.$btnBuscarDetalle.on('click', function() {
		if (!$formTipoReporteDetalle.valid()) {
			return;
		}
		var criterioBusqueda = {};
		var rolesSeleccionados = obtenerItemsSeleccionados($local.$rolesDetalle);
		var tiposSeleccionados = obtenerItemsSeleccionados($local.$tiposDetalle);
		var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaDetalle);
		criterioBusqueda.criterios = obtenerCriterios(rolesSeleccionados, tiposSeleccionados, 'value', '').join(',');
		criterioBusqueda.descripcionCriterios = obtenerCriterios(rolesSeleccionados, tiposSeleccionados, 'text', ' ').join(',');
		criterioBusqueda.fechaInicio = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaFin = rangoFechaBusqueda.fechaFin;
		criterioBusqueda.idInstitucion = $local.$institucionesDetalle.find('option:selected').val();
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "reporte/contable/detalle?accion=buscar",
			contentType: "application/json",
			dataType: "json",
			data: criterioBusqueda,
			beforeSend: function() {
				$.each($local.tablasResultadosDetalle, function(i, tabla) {
					$local.tablasResultadosDetalle[i].clear().draw();
				});
				$local.$btnBuscarDetalle.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success: function(response) {
				if (response.length === 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				$.each(response, function(idx, value) {
					if (value.reporteMoneda.length > 0) {
						$.each(value.reporteMoneda, function(i, rm) {
							if (rm.cuentasDetalle.length > 0) {
								rm.cuentasDetalle = rm.cuentasDetalle.map(function(v) {
									v.codigoMoneda = rm.codigoMoneda;
									v.descripcionMoneda = rm.descripcionMoneda;
									return v;
								});
								$local.tablasResultadosDetalle["tablaDetalleRol-" + value.criterio].rows.add(rm.cuentasDetalle).draw();
								var primeraLetra = value.criterio[0];
								$local.tablasResultadosDetalle["tablaDetalleTipo-" + primeraLetra].rows.add(rm.cuentasDetalle).draw();
							}
						});
					}
				});
			},
			error: function(response) {
			},
			complete: function() {
				$local.$btnBuscarDetalle.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$btnExportarDetalle.on('click', function() {
		if (!$formTipoReporteDetalle.valid()) {
			return;
		}
		var criterioBusqueda = {};
		var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaDetalle);
		var rolesSeleccionados = obtenerItemsSeleccionados($local.$rolesDetalle);
		var tiposSeleccionados = obtenerItemsSeleccionados($local.$tiposDetalle);
		criterioBusqueda.criterios = obtenerCriterios(rolesSeleccionados, tiposSeleccionados, 'value', '').join(',');
		criterioBusqueda.descripcionCriterios = obtenerCriterios(rolesSeleccionados, tiposSeleccionados, 'text', ' ').join(',');
		criterioBusqueda.fechaInicio = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaFin = rangoFechaBusqueda.fechaFin;
		criterioBusqueda.descripcionRangoFechas = $local.$rangoFechaDetalle.val();
		criterioBusqueda.tipoVisualizacion = obtenerItemsSeleccionados($local.$tipoVisualizacionDetalle)[0].value;
		criterioBusqueda.idInstitucion = $local.$institucionesDetalle.find('option:selected').val();
		criterioBusqueda.descripcionInstitucion = $local.$institucionesDetalle.find('option:selected').text();
		var paramCriterioBusqueda = $.param(criterioBusqueda);
		$funcionUtil.webSocketMethod('reporteContable');
		window.location.href = $variableUtil.root + "reporte/contable/detalle?accion=exportar&" + paramCriterioBusqueda
			+ "&rutaEnSidebar=" + $variableUtil.rutaEnSidebar;
	});

	$local.$btnBuscarResumenPorPeriodo.on('click', function() {
		if (!$formTipoReporteResumenPorPeriodo.valid()) {
			return;
		}
		var criterioBusqueda = $formTipoReporteResumenPorPeriodo.serializeJSON();
		var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaResumenPorPeriodo);
		criterioBusqueda.fechaInicio = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaFin = rangoFechaBusqueda.fechaFin;
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "reporte/contable/resumenPorPeriodo?accion=buscar",
			contentType: "application/json",
			dataType: "json",
			data: criterioBusqueda,
			beforeSend: function() {
				$.each($local.tablasResultadosPorPeriodo, function(i, tabla) {
					$local.tablasResultadosPorPeriodo[i].clear().draw();
				});
				$local.$btnBuscarResumenPorPeriodo.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success: function(response) {
				if (response.length === 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				$.each(response, function(idx, value) {
					$local.tablasResultadosPorPeriodo["tablaResumenPorPeriodo-" + value.codigoMoneda].rows.add(value.cuentas).draw();
				});
			},
			error: function(response) {
			},
			complete: function() {
				$local.$btnBuscarResumenPorPeriodo.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$btnExportarResumenPorPeriodo.on('click', function() {
		if (!$formTipoReporteResumenPorPeriodo.valid()) {
			return;
		}
		var criterioBusqueda = $formTipoReporteResumenPorPeriodo.serializeJSON();;
		var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaResumenPorPeriodo);
		criterioBusqueda.fechaInicio = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaFin = rangoFechaBusqueda.fechaFin;
		criterioBusqueda.descripcionRangoFechas = $local.$rangoFechaResumenPorPeriodo.val();
		criterioBusqueda.descripcionInstitucion = $local.$institucionesResumenPorPeriodo.find('option:selected').text();
		var paramCriterioBusqueda = $.param(criterioBusqueda);
		window.location.href = $variableUtil.root + "reporte/contable/resumenPorPeriodo?rutaEnSidebar=" + $variableUtil.rutaEnSidebar + "&accion=exportar&" + paramCriterioBusqueda;
	});

	function mostrarTabs(criterios, visualizacion) {
		$("#resultadoBusquedaDetalle-" + visualizacion + " ul li").removeClass('active');
		$("#resultadoBusquedaDetalle-" + visualizacion + " ul li a").hide();
		$(".detalle-content-" + visualizacion).removeClass('active');
		$.each(criterios, function(i, e) {
			$("#resultadoBusquedaDetalle-" + visualizacion + " ul li a[href='#" + e + "-detalle-" + visualizacion + "']").show();
		});
		$("#" + criterios[0] + "-detalle-" + visualizacion).addClass('active in');
		$("#resultadoBusquedaDetalle-" + visualizacion + " ul li a[href='#" + criterios[0] + "-detalle-" + visualizacion + "']").parent().addClass('active');
	}

	function mostrarContenidoVisualizacion(input) {
		var val = input.val();
		var tiposSeleccionados = obtenerItemsSeleccionados($local.$tiposDetalle);
		var rolesSeleccionados = obtenerItemsSeleccionados($local.$rolesDetalle);
		var criterios = obtenerCriterios(rolesSeleccionados, tiposSeleccionados, 'value', '');
		switch (val) {
			case 'D':
				$local.$resultadoBusquedaDetalleDisgregadoContent.removeClass("hidden");
				$local.$resultadoBusquedaDetalleJuntoContent.addClass("hidden");
				mostrarTabs(criterios, 'D');
				break;
			case 'J':
				$local.$resultadoBusquedaDetalleJuntoContent.removeClass("hidden");
				$local.$resultadoBusquedaDetalleDisgregadoContent.addClass("hidden");
				mostrarTabs(obtenerArraySimple(rolesSeleccionados, 'value'), 'J');
				break;
		}
	}

	function obtenerItemsSeleccionados(inputs) {
		var arr = [];
		inputs.each(function() {
			if ($(this).is(':checked')) {
				var obj = { value: $(this).val(), text: $(this).parent().text().trim() };
				arr.push(obj);
			}
		});
		return arr;
	}

	function obtenerCriterios(arrayRoles, arrayTipos, key, separator) {
		var criterios = [];
		$.each(arrayRoles, function(i, rol) {
			if (rol.value === 'M') {
				criterios.push(rol[key]);
			} else {
				var currRol = rol[key];
				$.each(arrayTipos, function(j, tipo) {
					criterios.push(currRol + separator + tipo[key]);
				});
			}
		});
		return criterios;
	}

	function obtenerArraySimple(arrObjs, key) {
		var arr = [];
		$.each(arrObjs, function(i, e) {
			arr.push(e[key]);
		});
		return arr;
	}

	function ajustarColumnas() {
		$.fn.dataTable.tables({
			visible: true,
			api: true
		}).columns.adjust().draw();
	}

});
