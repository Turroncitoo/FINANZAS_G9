$(document).ready(function() {

	var $local = {
		$btnBuscarResumen: $("#btnBuscarResumen"),
		$btnBuscarDetalle: $("#btnBuscarDetalle"),
		$btnBuscarAutDia: $("#btnBuscarAutDia"),
		$btnBuscarAutDetalle: $("#btnBuscarAutDetalle"),
		$btnExportarResumen: $("#btnExportarResumen"),
		$btnExportarDetalle: $("#btnExportarDetalle"),
		$btnExportarAutDia: $("#btnExportarAutDia"),
		$btnExportarAutDetalle: $("#btnExportarAutDetalle"),

		$fechaResumen: $("#fechaProcesoResumen"),
		$fechaDetalle: $("#fechaProcesoDetalle"),
		$fechaAutDia: $("#fechaProcesoAutDia"),
		$fechaAutDetalle: $("#fechaProcesoAutDetalle"),
		$selectIndicadorConciliacion: $("#selectIndicadorConciliacion"),
		$selectIndicadorConciliacionAutResumen: $("#selectIndicadorConciliacionAutResumen"),
		$fechaTxnDetalle: $("#fechaTxnDetalle"),
		$tipoBusqueda: $("input[type='radio'][name='tipoBusqueda']"),
		$institucionesResumen: $("#institucionesResumen"),
		$institucionesDetalle: $("#institucionesDetalle"),
		$institucionesAutResumen: $("#institucionesAutResumen"),
		$institucionesAutDetalle: $("#institucionesAutDetalle"),

		$resumenContent: $('#resumenContent'),
		$detalleContent: $('#detalleContent'),
		$autDiaContent: $('#autDiaContent'),
		$autDetalleContent: $('#autDetalleContent'),
		$resultadoBusquedaResumenContent: $('#resultadoBusquedaResumen'),
		$resultadoBusquedaDetalleContent: $('#resultadoBusquedaDetalle'),
		$resultadoBusquedaAutDiaContent: $('#resultadoBusquedaAutDia'),
		$resultadoBusquedaAutDetalleContent: $('#resultadoBusquedaAutDetalle'),
		$tablaResultados: $("table.tablaResultadosResumen"),
		$tablaResultadosDetalle: $("table.tablaResultadosDetalle"),
		$tablaResultadosAutDia: $("table.tablaResultadosAutDia"),
		$tablaResultadosAutDetalle: $("table.tablaResultadosAutDetalle"),
		tablaResultados: "",
		tablaResultadosDetalle: "",
		tablasResultados: {},
		tablasResultadosDetalle: {},
		tablasResultadosAutDia: {},
		tablasResultadosAutDetalle: {},
		filtrosSeleccionables: [],

		numFiltrosMin: 1,
		mensajeFiltros: function() {
			return "Debe seleccionar al menos " + this.numFiltrosMin + " filtro(s) para porder realizar la B\u00FAsqueda";
		}
	};

	$formTipoReporteResumen = $("#formTipoReporteResumen");
	$formTipoReporteDetalle = $("#formTipoReporteDetalle");
	$formTipoReporteAutDia = $("#formTipoReporteAutDia");
	$formTipoReporteAutDetalle = $("#formTipoReporteAutDetalle");

	$funcionUtil.crearDateRangePickerConsulta($local.$fechaResumen);
	$funcionUtil.crearDateRangePickerConsulta($local.$fechaDetalle);
	$funcionUtil.crearDateRangePickerConsulta($local.$fechaAutDia);
	$funcionUtil.crearDateRangePickerConsulta($local.$fechaAutDetalle);
	$funcionUtil.crearSelect2($local.$selectIndicadorConciliacion);
	$funcionUtil.crearSelect2($local.$selectIndicadorConciliacionAutResumen);
	$funcionUtil.crearSelect2($local.$institucionesResumen, "Seleccione una Instituci\u00F3n");
	$funcionUtil.crearSelect2($local.$institucionesDetalle, "Seleccione una Instituci\u00F3n");
	$funcionUtil.crearSelect2($local.$institucionesAutResumen, "Seleccione una Instituci\u00F3n");
	$funcionUtil.crearSelect2($local.$institucionesAutDetalle, "Seleccione una Instituci\u00F3n");
	$('a[data-toggle="tab"]').on('shown.bs.tab', function(e) {
		ajustarColumnas();
	});

	$.fn.dataTable.moment('DD/MM/YYYY');

	$local.$tablaResultados.filter(function() {
		var tabla = $(this);
		var idTabla = tabla.attr("idTabla");
		$local.tablasResultados[idTabla] = tabla.DataTable({
			"language": {
				"emptyTable": "No hay registros encontrados."
			},
			"initComplete": function() {
				tabla.wrap("<div class='table-responsive'></div>");
				$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, tabla);
			},
			"columnDefs": [{
				"targets": [0, 1, 2, 3, 4, 9],
				"className": "all filtrable",
			}, {
				"targets": [5, 6],
				"className": "all filtrable dt-right cantidad"
			}, {
				"targets": [7, 8],
				"className": "all filtrable dt-right monto"
			}],
			"columns": [{
				"data": "fechaProceso",
				"title": "Fecha Proceso"
			}, {
				"data": "fechaTransaccion",
				"title": "Fecha Transacci\u00F3n"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.codigoClaseTransaccion, row.descripcionClaseTransaccion);
				},
				"title": "Clase Transacci\u00F3n"
			}, {
				"data": 'cuentaCargo',
				"title": "Cuenta Cargo"
			}, {
				"data": 'cuentaAbono',
				"title": "Cuenta Abono"
			}, {
				"data": 'cantidadCargo',
				"title": "Cantidad Cargo"
			}, {
				"data": 'cantidadAbono',
				"title": "Cantidad Abono"
			}, {
				"data" : function(row) {
					return row.totalCargo == null ? '0.00' : row.totalCargo;
				},
				"render" : $tablaFuncion.formatoMonto(),
				"title": "Total Cargo"
			}, {
				"data" : function(row) {
					return row.totalAbono == null ? '0.00' : row.totalAbono;
				},
				"render" : $tablaFuncion.formatoMonto(),
				"title": "Total Abono"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.codigoInstitucion, row.descripcionInstitucion);
				},
				"title": "Instituci\u00F3n"
			}],
			"footerCallback": function(row, data, start, end, display) {
				var api = this.api();
				api.columns('.cantidad').every(function() {
					var index = this.selector.cols;
					var dataCol = this.data();
					if (dataCol.length > 0) {
						var suma = this.data().reduce(function(a, b) {
							return parseFloat(a) + parseFloat(b);
						}, 0);
						$(api.column(index).footer()).html(suma.toFixed(0));
					} else {
						$(api.column(index).footer()).html("");
					}
				});
				api.columns('.monto').every(function() {
					var index = this.selector.cols;
					var dataCol = this.data();
					if (dataCol.length > 0) {
						var suma = this.data().reduce(function(a, b) {
							return $funcionUtil.formatMoneyToNumber(a) + $funcionUtil.formatMoneyToNumber(b);
						}, 0);
						$(api.column(index).footer()).html($funcionUtil.formatMoney(suma,2));
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
		$local.tablasResultadosDetalle[idTabla] = tabla.DataTable({
			"language": {
				"emptyTable": "No hay registros encontrados."
			},
			"initComplete": function() {
				tabla.wrap("<div class='table-responsive'></div>");
				$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, tabla);
			},
			"columnDefs": [{
				"targets": [0, 1, 2, 3, 4, 5, 6, 11],
				"className": "all filtrable",
			}, {
				"targets": [7, 8],
				"className": "all filtrable dt-right cantidad"
			}, {
				"targets": [9, 10],
				"className": "all filtrable dt-right monto"
			}],
			"columns": [{
				"data": "fechaProceso",
				"title": "Fecha Proceso"
			}, {
				"data": "fechaTransaccion",
				"title": "Fecha Transacci\u00F3n"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.codigoOrigenTransaccion, row.descripcionOrigenTransaccion);
				},
				"title": "Origen Transacci\u00F3n"
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
				"data": 'cuentaCargo',
				"title": "Cuenta Cargo"
			}, {
				"data": 'cuentaAbono',
				"title": "Cuenta Abono"
			}, {
				"data": 'cantidadCargo',
				"title": "Cantidad Cargo"
			}, {
				"data": 'cantidadAbono',
				"title": "Cantidad Abono"
			}, {
				"data" : function(row) {
					return row.totalCargo == null ? '0.00' : row.totalCargo;
				},
				"render" : $tablaFuncion.formatoMonto(),
				"title": "Total Cargo"
			}, {
				"data" : function(row) {
					return row.totalAbono == null ? '0.00' : row.totalAbono;
				},
				"render" : $tablaFuncion.formatoMonto(),
				"title": "Total Abono"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.codigoInstitucion, row.descripcionInstitucion);
				},
				"title": "Instituci\u00F3n"
			}],
			"footerCallback": function(row, data, start, end, display) {
				var api = this.api();
				api.columns('.cantidad').every(function() {
					var index = this.selector.cols;
					var dataCol = this.data();
					if (dataCol.length > 0) {
						var suma = this.data().reduce(function(a, b) {
							return parseFloat(a) + parseFloat(b);
						}, 0);
						$(api.column(index).footer()).html(suma.toFixed(0));
					} else {
						$(api.column(index).footer()).html("");
					}
				});
				api.columns('.monto').every(function() {
					var index = this.selector.cols;
					var dataCol = this.data();
					if (dataCol.length > 0) {
						var suma = this.data().reduce(function(a, b) {
							return $funcionUtil.formatMoneyToNumber(a) + $funcionUtil.formatMoneyToNumber(b);
						});
						$(api.column(index).footer()).html($funcionUtil.formatMoney(suma,2));
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

	$local.$tablaResultadosAutDia.filter(function() {
		var tabla = $(this);
		var idTabla = tabla.attr("idTabla");
		$local.tablasResultadosAutDia[idTabla] = tabla.DataTable({
			"language": {
				"emptyTable": "No hay registros encontrados."
			},
			"initComplete": function() {
				tabla.wrap("<div class='table-responsive'></div>");
				$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, tabla);
			},
			"columnDefs": [{
				"targets": [0, 1, 2, 3, 4, 5, 10],
				"className": "all filtrable",
			}, {
				"targets": [6, 7],
				"className": "all filtrable dt-right cantidad"
			}, {
				"targets": [8, 9],
				"className": "all filtrable dt-right monto"
			}],
			"columns": [{
				"data": "fechaProceso",
				"title": "Fecha Proceso"
			}, {
				"data": "fechaTransaccion",
				"title": "Fecha Transacci\u00F3n"
			}, {
				"data": "tipoMensaje",
				"title": "Tipo Mensaje"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.codigoConciliacion, row.descripcionConciliacion);
				},
				"title": "Conciliaci\u00F3n"
			}, {
				"data": 'cuentaCargo',
				"title": "Cuenta Cargo"
			}, {
				"data": 'cuentaAbono',
				"title": "Cuenta Abono"
			}, {
				"data": 'cantidadCargo',
				"title": "Cantidad Cargo"
			}, {
				"data": 'cantidadAbono',
				"title": "Cantidad Abono"
			}, {
				"data" : function(row) {
					return row.totalCargo == null ? '0.00' : row.totalCargo;
				},
				"render" : $tablaFuncion.formatoMonto(),
				"title": "Total Cargo"
			}, {
				"data" : function(row) {
					return row.totalAbono == null ? '0.00' : row.totalAbono;
				},
				"render" : $tablaFuncion.formatoMonto(),
				"title": "Total Abono"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.codigoInstitucion, row.descripcionInstitucion);
				},
				"title": "Instituci\u00F3n"
			}],
			"footerCallback": function(row, data, start, end, display) {
				var api = this.api();
				api.columns('.cantidad').every(function() {
					var index = this.selector.cols;
					var dataCol = this.data();
					if (dataCol.length > 0) {
						var suma = this.data().reduce(function(a, b) {
							return parseFloat(a) + parseFloat(b);
						}, 0);
						$(api.column(index).footer()).html(suma.toFixed(0));
					} else {
						$(api.column(index).footer()).html("");
					}
				});
				api.columns('.monto').every(function() {
					var index = this.selector.cols;
					var dataCol = this.data();
					if (dataCol.length > 0) {
						var suma = this.data().reduce(function(a, b) {
							return $funcionUtil.formatMoneyToNumber(a) + $funcionUtil.formatMoneyToNumber(b);
						}, 0);
						$(api.column(index).footer()).html($funcionUtil.formatMoney(suma,2));
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

	$local.$tablaResultadosAutDetalle.filter(function() {
		var tabla = $(this);
		var idTabla = tabla.attr("idTabla");
		$local.tablasResultadosAutDetalle[idTabla] = tabla.DataTable({
			"language": {
				"emptyTable": "No hay registros encontrados."
			},
			"initComplete": function() {
				tabla.wrap("<div class='table-responsive'></div>");
				$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, tabla);
			},
			"columnDefs": [{
				"targets": [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 18],
				"className": "all filtrable",
			}, {
				"targets": [17],
				"className": "all filtrable dt-right monto"
			}],
			"columns": [{
				"data": "fechaProceso",
				"title": "Fecha Proceso"
			}, {
				"data": "tipoMensaje",
				"title": "Tipo Mensaje"
			}, {
				"data": 'numeroTarjeta',
				"title": "N\u00B0 Tarjeta"
			}, {
				"data": 'numeroCuenta',
				"title": "N\u00B0 Cuenta"
			}, {
				"data": 'fechaAdquirente',
				"title": "Fecha Adquirente"
			}, {
				"data": 'horaAdquirente',
				"title": "Hora Adquirente"
			}, {
				"data": "fechaLocal",
				"title": "Fecha Local"
			}, {
				"data": "horaLocal",
				"title": "Hora Local"
			}, {
				"data": "numeroTrace",
				"title": "Trace"
			}, {
				"data": "codigoAutorizacion",
				"title": "C\u00F3d. Autorizaci\u00F3n"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.codigoProceso, row.descripcionProceso);
				},
				"title": "Proceso"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.codigoCanal, row.descripcionCanal);
				},
				"title": "Canal"
			}, {
				"data": 'descripcionAdquirente',
				"title": 'Adquirente'
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.binAdquirente, row.descripcionBinAdquirente);
				},
				"title": "BIN Adquirente"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.codigoRespuesta, row.descripcionRespuesta);
				},
				"title": "Respuesta"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.codigoConciliacion, row.descripcionConciliacion);
				},
				"title": "Conciliaci\u00F3n"
			}, {
				"data": 'fechaConciliacionLog',
				"title": "Fecha Concilia Log"
			}, {
				"data" : function(row) {
					return row.monto == null ? '0.00' : row.monto;
				},
				"render" : $tablaFuncion.formatoMonto(),
				"title": "Monto"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.codigoInstitucion, row.descripcionInstitucion);
				},
				"title": "Instituci\u00F3n"
			}],
			"footerCallback": function(row, data, start, end, display) {
				var api = this.api();
				api.columns('.monto').every(function() {
					var index = this.selector.cols;
					var dataCol = this.data();
					if (dataCol.length > 0) {
						var suma = this.data().reduce(function(a, b) {
							return $funcionUtil.formatMoneyToNumber(a) + $funcionUtil.formatMoneyToNumber(b);
						}, 0);
						$(api.column(index).footer()).html($funcionUtil.formatMoney(suma,2));
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

	$local.$tablaResultados.find("thead").on('keyup', 'input.filtrable', function() {
		var $tabla = $(this).parents('table');
		var idTabla = $tabla.attr("idTabla");
		var tabla = $local.tablasResultados[idTabla];
		tabla.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaResultadosDetalle.find("thead").on('keyup', 'input.filtrable', function() {
		var $tabla = $(this).parents('table');
		var idTabla = $tabla.attr("idTabla");
		var tabla = $local.tablasResultadosDetalle[idTabla];
		tabla.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaResultadosAutDia.find("thead").on('keyup', 'input.filtrable', function() {
		var $tabla = $(this).parents('table');
		var idTabla = $tabla.attr("idTabla");
		var tabla = $local.tablasResultadosAutDia[idTabla];
		tabla.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaResultadosAutDetalle.find("thead").on('keyup', 'input.filtrable', function() {
		var $tabla = $(this).parents('table');
		var idTabla = $tabla.attr("idTabla");
		var tabla = $local.tablasResultadosAutDetalle[idTabla];
		tabla.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tipoBusqueda.on('change', function() {
		var val = $(this).val();
		switch (val) {
			case 'resumen':
				$local.$resumenContent.removeClass("hidden");
				$local.$detalleContent.addClass("hidden");
				$local.$autDiaContent.addClass("hidden");
				$local.$autDetalleContent.addClass("hidden");
				$local.$resultadoBusquedaResumenContent.removeClass("hidden");
				$local.$resultadoBusquedaDetalleContent.addClass("hidden");
				$local.$resultadoBusquedaAutDiaContent.addClass("hidden");
				$local.$resultadoBusquedaAutDetalleContent.addClass("hidden");
				$local.$resultadoBusquedaAutDetalleContent.addClass("hidden");
				break;
			case 'detalle':
				$local.$detalleContent.removeClass("hidden");
				$local.$resumenContent.addClass("hidden");
				$local.$autDiaContent.addClass("hidden");
				$local.$autDetalleContent.addClass("hidden");
				$local.$resultadoBusquedaDetalleContent.removeClass("hidden");
				$local.$resultadoBusquedaResumenContent.addClass("hidden");
				$local.$resultadoBusquedaAutDiaContent.addClass("hidden");
				$local.$resultadoBusquedaAutDetalleContent.addClass("hidden");
				break;
			case 'autDia':
				$local.$autDiaContent.removeClass("hidden");
				$local.$detalleContent.addClass("hidden");
				$local.$resumenContent.addClass("hidden");
				$local.$autDetalleContent.addClass("hidden");
				$local.$resultadoBusquedaAutDiaContent.removeClass("hidden");
				$local.$resultadoBusquedaDetalleContent.addClass("hidden");
				$local.$resultadoBusquedaResumenContent.addClass("hidden");
				$local.$resultadoBusquedaAutDetalleContent.addClass("hidden");
				break;
			case 'autDetalle':
				$local.$autDetalleContent.removeClass("hidden");
				$local.$autDiaContent.addClass("hidden");
				$local.$detalleContent.addClass("hidden");
				$local.$resumenContent.addClass("hidden");
				$local.$resultadoBusquedaAutDetalleContent.removeClass("hidden");
				$local.$resultadoBusquedaAutDiaContent.addClass("hidden");
				$local.$resultadoBusquedaDetalleContent.addClass("hidden");
				$local.$resultadoBusquedaResumenContent.addClass("hidden");
				break;
		}
	});

	$local.$btnBuscarResumen.on('click', function() {
		if (!$formTipoReporteResumen.valid()) {
			return;
		}
		if ($funcionUtil.camposVacios2($formTipoReporteResumen, $local.numFiltrosMin)) {
			$funcionUtil.notificarException($local.mensajeFiltros(), "fa-exclamation-circle", "Informaci\u00F3n", "info");
			return;
		}
		var criterioBusqueda = $formTipoReporteResumen.serializeJSON();
		var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$fechaResumen);
		criterioBusqueda.fechaInicio = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaFin = rangoFechaBusqueda.fechaFin;
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "reporte/cuadreCuentasPorPagar/resumen?accion=buscar",
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
					$local.tablasResultados["tablaResumen-" + value.codigoMoneda].rows.add(value.cuentasPorPagarResumen).draw();
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
		if ($funcionUtil.camposVacios2($formTipoReporteResumen, $local.numFiltrosMin)) {
			$funcionUtil.notificarException($local.mensajeFiltros(), "fa-exclamation-circle", "Informaci\u00F3n", "info");
			return;
		}
		var criterioBusqueda = $formTipoReporteResumen.serializeJSON();
		var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$fechaResumen);
		criterioBusqueda.fechaInicio = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaFin = rangoFechaBusqueda.fechaFin;
		criterioBusqueda.descripcionInstitucion = $local.$institucionesResumen.find('option:selected').text();
		criterioBusqueda.descripcionRangoFechas = $local.$fechaResumen.val() || 'TODOS';
		var paramCriterioBusqueda = $.param(criterioBusqueda);
		$funcionUtil.webSocketMethod('cuadreCuentasPorPagar');
		window.location.href = $variableUtil.root + "reporte/cuadreCuentasPorPagar/resumen?rutaEnSidebar=" + $variableUtil.rutaEnSidebar + "&accion=exportar&" + paramCriterioBusqueda;
	});

	$local.$btnBuscarDetalle.on('click', function() {
		if (!$formTipoReporteDetalle.valid()) {
			return;
		}
		if ($funcionUtil.camposVacios2($formTipoReporteDetalle, $local.numFiltrosMin)) {
			$funcionUtil.notificarException($local.mensajeFiltros(), "fa-exclamation-circle", "Informaci\u00F3n", "info");
			return;
		}
		var criterioBusqueda = $formTipoReporteDetalle.serializeJSON();
		var rangoFechaBusquedaProceso = $funcionUtil.obtenerFechasDateRangePicker($local.$fechaDetalle);
		criterioBusqueda.fechaInicio = rangoFechaBusquedaProceso.fechaInicio;
		criterioBusqueda.fechaFin = rangoFechaBusquedaProceso.fechaFin;
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "reporte/cuadreCuentasPorPagar/detalle?accion=buscar",
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
					$local.tablasResultadosDetalle["tablaDetalle-" + value.codigoMoneda].rows.add(value.cuentasPorPagarDetalle).draw();
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
		if ($funcionUtil.camposVacios2($formTipoReporteDetalle, $local.numFiltrosMin)) {
			$funcionUtil.notificarException($local.mensajeFiltros(), "fa-exclamation-circle", "Informaci\u00F3n", "info");
			return;
		}
		var criterioBusqueda = $formTipoReporteDetalle.serializeJSON();
		var rangoFechaBusquedaProceso = $funcionUtil.obtenerFechasDateRangePicker($local.$fechaDetalle);
		criterioBusqueda.fechaInicio = rangoFechaBusquedaProceso.fechaInicio;
		criterioBusqueda.fechaFin = rangoFechaBusquedaProceso.fechaFin;
		criterioBusqueda.descripcionInstitucion = $local.$institucionesDetalle.find('option:selected').text();
		criterioBusqueda.descripcionRangoFechas = $local.$fechaDetalle.val() || 'TODOS';
		var paramCriterioBusqueda = $.param(criterioBusqueda);
		$funcionUtil.webSocketMethod('cuadreCuentasPorPagar');
		window.location.href = $variableUtil.root + "reporte/cuadreCuentasPorPagar/detalle?rutaEnSidebar=" + $variableUtil.rutaEnSidebar + "&accion=exportar&" + paramCriterioBusqueda;
	});

	$local.$btnBuscarAutDia.on('click', function() {
		if (!$formTipoReporteAutDia.valid()) {
			return;
		}
		if ($funcionUtil.camposVacios2($formTipoReporteAutDia, $local.numFiltrosMin)) {
			$funcionUtil.notificarException($local.mensajeFiltros(), "fa-exclamation-circle", "Informaci\u00F3n", "info");
			return;
		}
		var criterioBusqueda = $formTipoReporteAutDia.serializeJSON();
		var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$fechaAutDia);
		criterioBusqueda.fechaInicio = rangoFechaBusqueda.fechaInicio;

		criterioBusqueda.fechaFin = rangoFechaBusqueda.fechaFin;

		$.ajax({
			type: "GET",
			url: $variableUtil.root + "reporte/cuadreCuentasPorPagar/autDia?accion=buscar",
			contentType: "application/json",
			dataType: "json",
			data: criterioBusqueda,
			beforeSend: function() {
				$.each($local.tablasResultadosAutDia, function(i, tabla) {
					$local.tablasResultadosAutDia[i].clear().draw();
				});
				$local.$btnBuscarAutDia.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success: function(response) {
				if (response.length === 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				$.each(response, function(idx, value) {
					$local.tablasResultadosAutDia["tablaAutDia-" + value.codigoMoneda].rows.add(value.cuentasPorPagarResumen).draw();
				});
			},
			error: function(response) {
			},
			complete: function() {
				$local.$btnBuscarAutDia.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$btnExportarAutDia.on('click', function() {
		if (!$formTipoReporteAutDia.valid()) {
			return;
		}
		if ($funcionUtil.camposVacios2($formTipoReporteAutDia, $local.numFiltrosMin)) {
			$funcionUtil.notificarException($local.mensajeFiltros(), "fa-exclamation-circle", "Informaci\u00F3n", "info");
			return;
		}
		var criterioBusqueda = $formTipoReporteAutDia.serializeJSON();
		var rangoFechaBusquedaProceso = $funcionUtil.obtenerFechasDateRangePicker($local.$fechaAutDia);
		criterioBusqueda.fechaInicio = rangoFechaBusquedaProceso.fechaInicio;
		criterioBusqueda.fechaFin = rangoFechaBusquedaProceso.fechaFin;
		criterioBusqueda.descripcionInstitucion = $local.$institucionesAutResumen.find('option:selected').text();
		criterioBusqueda.descripcionRangoFechas = $local.$fechaAutDia.val() || 'TODOS';
		criterioBusqueda.descripcionConciliacion = $formTipoReporteAutDia.find('[name="indicadorConciliacion"] option:selected').text();

		var paramCriterioBusqueda = $.param(criterioBusqueda);
		$funcionUtil.webSocketMethod('cuadreCuentasPorPagar');
		window.location.href = $variableUtil.root + "reporte/cuadreCuentasPorPagar/autDia?rutaEnSidebar=" + $variableUtil.rutaEnSidebar + "&accion=exportar&" + paramCriterioBusqueda;
	});


	$local.$btnBuscarAutDetalle.on('click', function() {
		if (!$formTipoReporteAutDetalle.valid()) {
			return;
		}
		if ($funcionUtil.camposVacios2($formTipoReporteAutDetalle, $local.numFiltrosMin)) {
			$funcionUtil.notificarException($local.mensajeFiltros(), "fa-exclamation-circle", "Informaci\u00F3n", "info");
			return;
		}
		var criterioBusqueda = $formTipoReporteAutDetalle.serializeJSON();
		var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$fechaAutDetalle);
		criterioBusqueda.fechaInicio = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaFin = rangoFechaBusqueda.fechaFin;

		$.ajax({
			type: "GET",
			url: $variableUtil.root + "reporte/cuadreCuentasPorPagar/autDetalle?accion=buscar",
			contentType: "application/json",
			dataType: "json",
			data: criterioBusqueda,
			beforeSend: function() {
				$.each($local.tablasResultadosAutDetalle, function(i, tabla) {
					$local.tablasResultadosAutDetalle[i].clear().draw();
				});
				$local.$btnBuscarAutDetalle.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success: function(response) {
				if (response.length === 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				$.each(response, function(idx, value) {
					$local.tablasResultadosAutDetalle["tablaAutDetalle-" + value.codigoMoneda].rows.add(value.cuentasPorPagarAutDetalle).draw();
				});
			},
			error: function(response) {
			},
			complete: function() {
				$local.$btnBuscarAutDetalle.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$btnExportarAutDetalle.on('click', function() {
		if (!$formTipoReporteAutDetalle.valid()) {
			return;
		}
		if ($funcionUtil.camposVacios2($formTipoReporteAutDetalle, $local.numFiltrosMin)) {
			$funcionUtil.notificarException($local.mensajeFiltros(), "fa-exclamation-circle", "Informaci\u00F3n", "info");
			return;
		}
		var criterioBusqueda = $formTipoReporteAutDetalle.serializeJSON();
		var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$fechaAutDetalle);
		criterioBusqueda.fechaInicio = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaFin = rangoFechaBusqueda.fechaFin;
		criterioBusqueda.descripcionInstitucion = $local.$institucionesAutDetalle.find('option:selected').text();
		criterioBusqueda.descripcionRangoFechas = $local.$fechaAutDetalle.val() || 'TODOS';
		criterioBusqueda.descripcionConciliacion = $formTipoReporteAutDetalle.find('[name="indicadorConciliacion"] option:selected').text();

		var paramCriterioBusqueda = $.param(criterioBusqueda);
		$funcionUtil.webSocketMethod('cuadreCuentasPorPagar');
		window.location.href = $variableUtil.root + "reporte/cuadreCuentasPorPagar/autDetalle?rutaEnSidebar=" + $variableUtil.rutaEnSidebar + "&accion=exportar&" + paramCriterioBusqueda;
	});

	function ajustarColumnas() {
		$.fn.dataTable.tables({
			visible: true,
			api: true
		}).columns.adjust().draw();
	}

});