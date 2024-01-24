$(document).ready(function() {

	var $local = {
		$btnBuscarResumen: $("#btnBuscarResumen"),
		$btnBuscarDetalle: $("#btnBuscarDetalle"),
		$btnBuscarMiscelaneos: $("#btnBuscarMiscelaneos"),
		$btnExportarResumen: $("#btnExportarResumen"),
		$btnExportarDetalle: $("#btnExportarDetalle"),
		$btnExportarMiscelaneos: $("#btnExportarMiscelaneos"),

		$fechaResumen: $("#fechaProcesoResumen"),
		$fechaDetalle: $("#fechaProcesoDetalle"),
		$fechaMiscelaneos: $("#fechaProcesoMiscelaneos"),
		$tipoBusqueda: $("input[type='radio'][name='tipoBusqueda']"),

		$institucionesResumen: $('#institucionesResumen'),
		$empresasResumen: $('#empresasResumen'),
		$clientesResumen: $('#clientesResumen'),
		$selectConceptoComision: $('#selectConceptoComision'),

		$institucionesDetalle: $('#institucionesDetalle'),
		$empresasDetalle: $('#empresasDetalle'),
		$clientesDetalle: $('#clientesDetalle'),

		$institucionesMiscelaneos: $('#institucionesMiscelaneos'),
		$empresasMiscelaneos: $('#empresasMiscelaneos'),
		$clientesMiscelaneos: $('#clientesMiscelaneos'),

		$resumenContent: $('#resumenContent'),
		$detalleContent: $('#detalleContent'),
		$miscContent: $('#miscContent'),
		$resultadoBusquedaResumenContent: $('#resultadoBusquedaResumen'),
		$resultadoBusquedaDetalleContent: $('#resultadoBusquedaDetalle'),
		$resultadoBusquedaMiscelaneosContent: $('#resultadoBusquedaMiscelaneos'),
		$tablaResultados: $("table.tablaResultadosResumen"),
		$tablaResultadosDetalle: $("table.tablaResultadosDetalle"),
		$tablaResultadosMiscelaneos: $("table.tablaResultadosMisc"),
		tablaResultados: "",
		tablaResultadosDetalle: "",
		tablasResultados: {},
		tablasResultadosDetalle: {},
		tablasResultadosMiscelaneos: {},
		filtrosSeleccionables: [],

		numFiltrosMin: 1,
		mensajeFiltros: function() {
			return "Debe seleccionar al menos " + this.numFiltrosMin + " filtro(s) para porder realizar la B\u00FAsqueda";
		}
	};

	$formTipoReporteResumen = $("#formTipoReporteResumen");
	$formTipoReporteDetalle = $("#formTipoReporteDetalle");
	$formTipoReporteMiscelaneos = $("#formTipoReporteMiscelaneos");

	// Filtros Resumen Facturaci\u00F3n
	$funcionUtil.crearDateRangePickerConsulta($local.$fechaResumen, "right");
	$funcionUtil.crearSelect2($local.$institucionesResumen);
	$funcionUtil.crearSelect2($local.$empresasResumen, "Seleccione una Empresa");
	$funcionUtil.crearSelect2Multiple($local.$clientesResumen, "-1", "TODOS");
	$funcionUtil.crearSelect2Multiple($local.$selectConceptoComision, "-1", "TODOS");

	// Filtros Detalle Facturaci\u00F3n
	$funcionUtil.crearDateRangePickerConsulta($local.$fechaDetalle, "right");
	$funcionUtil.crearSelect2($local.$institucionesDetalle);
	$funcionUtil.crearSelect2($local.$empresasDetalle, "Seleccione una Empresa");
	$funcionUtil.crearSelect2Multiple($local.$clientesDetalle, "-1", "TODOS");

	// Filtros Cobros Miscelaneos
	$funcionUtil.crearDateRangePickerConsulta($local.$fechaMiscelaneos, "right");
	$funcionUtil.crearSelect2($local.$institucionesMiscelaneos);
	$funcionUtil.crearSelect2($local.$empresasMiscelaneos, "Seleccione una Empresa");
	$funcionUtil.crearSelect2Multiple($local.$clientesMiscelaneos, "-1", "TODOS");

	$('a[data-toggle="tab"]').on('shown.bs.tab', function(e) {
		ajustarColumnas();
	});

	$local.$empresasResumen.on("change", () => {
		const opcionSeleccionada = $local.$empresasResumen.val();
		if (!opcionSeleccionada) {
			$local.$clientesResumen.find("option").remove();
			return;
		}
		$.ajax({
			type: "GET",
			url: `${$variableUtil.root}cliente/empresa/${opcionSeleccionada}`,
			beforeSend: () => {
				$local.$clientesResumen.find("option").remove();
				$local.$clientesResumen.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Clientes...</span>")
			},
			success: (response) => {
				$.each(response, (i) => {
					$local.$clientesResumen.append($("<option/>").val(response[i].idCliente).text(response[i].idCliente + " - " + response[i].descripcion));
				});
			},
			complete: () => {
				$local.$clientesResumen.parent().find(".cargando").remove();
			}
		});
	});

	$local.$empresasDetalle.on("change", () => {
		const opcionSeleccionada = $local.$empresasDetalle.val();
		if (!opcionSeleccionada) {
			$local.$clientesDetalle.find("option").remove();
			return;
		}
		$.ajax({
			type: "GET",
			url: `${$variableUtil.root}cliente/empresa/${opcionSeleccionada}`,
			beforeSend: () => {
				$local.$clientesDetalle.find("option").remove();
				$local.$clientesDetalle.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Clientes...</span>")
			},
			success: (response) => {
				$.each(response, (i) => {
					$local.$clientesDetalle.append($("<option/>").val(response[i].idCliente).text(response[i].idCliente + " - " + response[i].descripcion));
				});
			},
			complete: () => {
				$local.$clientesDetalle.parent().find(".cargando").remove();
			}
		});
	});

	$local.$empresasMiscelaneos.on("change", () => {
		const opcionSeleccionada = $local.$empresasMiscelaneos.val();
		if (!opcionSeleccionada) {
			$local.$clientesMiscelaneos.find("option").remove();
			return;
		}
		$.ajax({
			type: "GET",
			url: `${$variableUtil.root}cliente/empresa/${opcionSeleccionada}`,
			beforeSend: () => {
				$local.$clientesMiscelaneos.find("option").remove();
				$local.$clientesMiscelaneos.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Clientes...</span>")
			},
			success: (response) => {
				$.each(response, (i) => {
					$local.$clientesMiscelaneos.append($("<option/>").val(response[i].idCliente).text(response[i].idCliente + " - " + response[i].descripcion));
				});
			},
			complete: () => {
				$local.$clientesMiscelaneos.parent().find(".cargando").remove();
			}
		});
	});

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
				"targets": 0,
				"className": "all filtrable dt-center"
			}, {
				"targets": [1, 2, 3, 4, 5, 6, 7, 8, 9],
				"className": "all filtrable",
			}, {
				"targets": 10,
				"className": "all filtrable dt-right cantidad"
			}, {
				"targets": [11, 12, 13, 14],
				"className": "all filtrable dt-right comision"
			}],
			"columns": [{
				"data": "fechaProceso",
				"title": "Fecha Proceso"
			}, {
				"data": 'institucion',
				"render": (data, type, row) => {
					return $funcionUtil.unirCodigoDescripcion(row.codigoInstitucion, row.descripcionInstitucion);
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
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.idMembresia, row.descMembresia);
				},
				"title": "Membres\u00EDa"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.idClaseServicio, row.descClaseServicio);
				},
				"title": "Servicio"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.codigoFacturacion, row.descCodigoFacturacion);
				},
				"title": "C\u00F3digo Facturaci\u00F3n"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcionExtra(row.idConceptoComision, row.descConceptoComision, row.aplicaComision);
				},
				"title": "Concepto Comisi\u00F3n"
			}, {
				"data": "cuentaCargo",
				"title": "Cta. Cargo"
			}, {
				"data": "cuentaAbono",
				"title": "Cta. Abono"
			}, {
				"data": "cantidad",
				"render": $tablaFuncion.formatoMonto(0),
				"title": "Cantidad"
			}, {
				"data": 'comisionImporte',
				"render": $tablaFuncion.formatoComision(4),
				"title": "Comisi\u00F3n Importe"
			}, {
				"data": 'comisionIGV',
				"render": $tablaFuncion.formatoComision(4),
				"title": "Comisi\u00F3n IGV"
			}, {
				"data": 'comisionTotal',
				"render": $tablaFuncion.formatoComision(4),
				"title": "Comisi\u00F3n Total"
			}, {
				"data": 'comisionPromedio',
				"render": $tablaFuncion.formatoComision(4),
				"title": "Comisi\u00F3n Promedio"
			}],
			"order": [],
			"footerCallback": function(row, data, start, end, display) {
				var api = this.api();
				api.columns('.cantidad').every(function() {
					var index = this.selector.cols;
					var dataCol = this.data();
					if (dataCol.length > 0) {
						var suma = this.data().reduce(function(a, b) {
							return parseFloat(a) + parseFloat(b);
						}, 0);
						$(api.column(index).footer()).html($funcionUtil.formatMoney(suma, 0));
					} else {
						$(api.column(index).footer()).html("");
					}
				});
				api.columns('.comision').every(function() {
					var index = this.selector.cols;
					var dataCol = this.data();
					if (dataCol.length > 0) {
						var suma = this.data().reduce(function(a, b) {
							return parseFloat(a) + parseFloat(b);
						}, 0);
						$(api.column(index).footer()).html($funcionUtil.formatMoney(suma, 4));
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
				$(row).find(".comision").filter(function() {
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
				"targets": 0,
				"className": "all filtrable dt-center",
			}, {
				"targets": [1, 2, 3, 4, 5, 6, 7, 8, 9],
				"className": "all filtrable"
			}, {
				"targets": 10,
				"className": "all filtrable dt-right cantidad"
			}, {
				"targets": [11, 12, 13, 14],
				"className": "all filtrable dt-right comision"
			}],
			"columns": [{
				"data": "fechaProceso",
				"title": "Fecha Proceso"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.codigoInstitucion, row.descripcionInstitucion);
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
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.rolTransaccion, row.descRolTransaccion);
				},
				"title": "Rol Transacci\u00F3n"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.idMembresia, row.descMembresia);
				},
				"title": "Membres\u00EDa"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.idClaseServicio, row.descClaseServicio);
				},
				"title": "Servicio"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.codigoFacturacion, row.descCodigoFacturacion);
				},
				"title": "C\u00F3digo Facturaci\u00F3n"
			}, {
				"data": 'cuentaCargo',
				"title": "Cta. Cargo"
			}, {
				"data": 'cuentaAbono',
				"title": "Cta. Abono"
			}, {
				"data": 'cantidad',
				"render": $tablaFuncion.formatoMonto(0),
				"title": "Cantidad"
			}, {
				"data": 'comisionImporte',
				"render": $tablaFuncion.formatoComision(4),
				"title": "Comisi\u00F3n Importe"
			}, {
				"data": 'comisionIGV',
				"render": $tablaFuncion.formatoComision(4),
				"title": "Comisi\u00F3n IGV"
			}, {
				"data": 'comisionTotal',
				"render": $tablaFuncion.formatoComision(4),
				"title": "Comisi\u00F3n Total"
			}, {
				"data": 'comisionPromedio',
				"render": $tablaFuncion.formatoComision(4),
				"title": "Comisi\u00F3n Promedio"
			}],
			"order": [],
			"footerCallback": function(row, data, start, end, display) {
				var api = this.api();
				api.columns('.cantidad').every(function() {
					var index = this.selector.cols;
					var dataCol = this.data();
					if (dataCol.length > 0) {
						var suma = this.data().reduce(function(a, b) {
							return parseFloat(a) + parseFloat(b);
						}, 0);
						$(api.column(index).footer()).html($funcionUtil.formatMoney(suma, 0));
					} else {
						$(api.column(index).footer()).html("");
					}
				});
				api.columns('.comision').every(function() {
					var index = this.selector.cols;
					var dataCol = this.data();
					if (dataCol.length > 0) {
						var suma = this.data().reduce(function(a, b) {
							return parseFloat(a) + parseFloat(b);
						}, 0);
						$(api.column(index).footer()).html($funcionUtil.formatMoney(suma, 4));
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
				$(row).find(".comision").filter(function() {
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

	$local.$tablaResultadosMiscelaneos.filter(function() {
		var tabla = $(this);
		var idTabla = tabla.attr("idTabla");
		$local.tablasResultadosMiscelaneos[idTabla] = tabla.DataTable({
			"language": {
				"emptyTable": "No hay registros encontrados."
			},
			"initComplete": function() {
				tabla.wrap("<div class='table-responsive'></div>");
				$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, tabla);
			},
			"columnDefs": [{
				"targets": 0,
				"className": "all filtrable dt-center",
			}, {
				"targets": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18],
				"className": "all filtrable",
			}, {
				"targets": 19,
				"className": "all filtrable dt-right cantidad"
			}, {
				"targets": [20, 21, 22],
				"className": "all filtrable dt-right comision"
			}],
			"columns": [{
				"data": "fechaProceso",
				"title": "Fecha Proceso"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.codigoInstitucion, row.descripcionInstitucion);
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
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.idMembresia, row.descMembresia);
				},
				"title": "Membres\u00EDa"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.idClaseServicio, row.descClaseServicio);
				},
				"title": "Servicio"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.idOrigen, row.descOrigen);
				},
				"title": "Origen"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.idClaseTransaccion, row.descClaseTransaccion);
				},
				"title": "Clase Transacci\u00F3n"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.idCodigoTransaccion, row.descCodigoTransaccion);
				},
				"title": "C\u00F3digo Transacci\u00F3n"
			}, {
				"data": 'secuenciaTransaccion',
				"title": "Secuencia"
			}, {
				"data": 'cuentaCargo',
				"title": "Cta. Cargo"
			}, {
				"data": 'cuentaAbono',
				"title": "Cta. Abono"
			}, {
				"data": "glosaRegularizacion",
				"title": "Glosa Regularizaci\u00F3n"
			}, {
				"data": "secuenciaRegistroFacturaMarca",
				"title": "Secuencia Factura"
			}, {
				"data": "numeroFacturaMarca",
				"title": "N&uacute;mero Factura"
			}, {
				"data": "codigoEventoMarcaInternacional",
				"title": "C\u00f3digo Evento"
			}, {
				"data": "distribucionCobroMarcaInternacional",
				"title": "Distribuci\u00f3n Cobro"
			}, {
				"data": "indicadorDistribucionCobro",
				"title": "Indicador Distribuci\u00f3n Cobro"
			}, {
				"data": "indicadorUnidades",
				"title": "Indicador Unidades"
			}, {
				"data": "totalUnidades",
				"render": $tablaFuncion.formatoComision(0),
				"title": "Total Unidades"
			}, {
				"data": "tarifaMarcaInternacional",
				"render": $tablaFuncion.formatoComision(4),
				"title": "Tarifa Marca Internacional"
			}, {
				"data": "valorFacturaMarcaInternacional",
				"render": $tablaFuncion.formatoComision(4),
				"title": "Importe Factura"
			}, {
				"data": "valorCompensacion",
				"render": $tablaFuncion.formatoComision(4),
				"title": "Importe Compensaci\u00f3n"
			}],
			"order": [],
			"footerCallback": function(row, data, start, end, display) {
				var api = this.api();
				api.columns('.cantidad').every(function() {
					var index = this.selector.cols;
					var dataCol = this.data();
					if (dataCol.length > 0) {
						var suma = this.data().reduce(function(a, b) {
							return parseFloat(a) + parseFloat(b);
						}, 0);
						$(api.column(index).footer()).html(suma.formatMoney(0));
					} else {
						$(api.column(index).footer()).html("");
					}
				});
				api.columns('.comision').every(function() {
					var index = this.selector.cols;
					var dataCol = this.data();
					if (dataCol.length > 0) {
						var suma = this.data().reduce(function(a, b) {
							return parseFloat(a) + parseFloat(b);
						}, 0);
						$(api.column(index).footer()).html($funcionUtil.formatMoney(suma, 4));
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
				$(row).find(".comision").filter(function() {
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

	$local.$tablaResultadosMiscelaneos.find("thead").on('keyup', 'input.filtrable', function() {
		var $tabla = $(this).parents('table');
		var idTabla = $tabla.attr("idTabla");
		var tabla = $local.tablasResultadosMiscelaneos[idTabla];
		tabla.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tipoBusqueda.on('change', function() {
		var val = $(this).val();
		switch (val) {
			case 'resumen':
				$local.$resumenContent.removeClass("hidden");
				$local.$detalleContent.addClass("hidden");
				$local.$miscContent.addClass("hidden");
				$local.$resultadoBusquedaResumenContent.removeClass("hidden");
				$local.$resultadoBusquedaDetalleContent.addClass("hidden");
				$local.$resultadoBusquedaMiscelaneosContent.addClass("hidden");
				break;
			case 'detalle':
				$local.$detalleContent.removeClass("hidden");
				$local.$resumenContent.addClass("hidden");
				$local.$miscContent.addClass("hidden");
				$local.$resultadoBusquedaDetalleContent.removeClass("hidden");
				$local.$resultadoBusquedaResumenContent.addClass("hidden");
				$local.$resultadoBusquedaMiscelaneosContent.addClass("hidden");
				break;
			case 'misc':
				$local.$miscContent.removeClass("hidden");
				$local.$detalleContent.addClass("hidden");
				$local.$resumenContent.addClass("hidden");
				$local.$resultadoBusquedaMiscelaneosContent.removeClass("hidden");
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
		criterioBusqueda.idCliente = $funcionUtil.limpiarCadenaPeticionGetDeSelect2MultiplePaginacion($funcionUtil.crearCadenaPeticionGetDeSelect2MultiplePaginacion($local.$clientesResumen));
		criterioBusqueda.idConceptoComision = $funcionUtil.limpiarCadenaPeticionGetDeSelect2MultiplePaginacion($funcionUtil.crearCadenaPeticionGetDeSelect2MultiplePaginacion($local.$selectConceptoComision));
		$.ajax({
			type: "GET",
			url: `${$variableUtil.root}reporte/facturacionUBA/resumen?accion=buscar`,
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
					$local.tablasResultados["tablaResumen-" + value.codigoMoneda].rows.add(value.facturacionResumen).draw();
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
		criterioBusqueda.descripcionFechaProceso = $local.$fechaResumen.val() || 'TODOS';
		criterioBusqueda.descripcionInstitucion = $local.$institucionesResumen.find('option:selected').text();
		criterioBusqueda.descripcionEmpresa = $local.$empresasResumen.find("option:selected").val() === "-1" ? "TODOS" : $local.$empresasResumen.find("option:selected").text();
		criterioBusqueda.descripcionCliente = !!$local.$clientesResumen.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$clientesResumen, "; ") : "TODOS";
		criterioBusqueda.descripcionConceptoComision = !!$local.$selectConceptoComision.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$selectConceptoComision, "; ") : "TODOS";
		criterioBusqueda.idCliente = $funcionUtil.limpiarCadenaPeticionGetDeSelect2MultiplePaginacion($funcionUtil.crearCadenaPeticionGetDeSelect2MultiplePaginacion($local.$clientesResumen));
		criterioBusqueda.idConceptoComision = $funcionUtil.limpiarCadenaPeticionGetDeSelect2MultiplePaginacion($funcionUtil.crearCadenaPeticionGetDeSelect2MultiplePaginacion($local.$selectConceptoComision));
		var paramCriterioBusqueda = $.param(criterioBusqueda);
		$funcionUtil.webSocketMethod('exportacionPOI');
		window.location.href = `${$variableUtil.root}reporte/facturacionUBA/resumen?rutaEnSidebar=${$variableUtil.rutaEnSidebar}&accion=exportar&${paramCriterioBusqueda}`;
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
		criterioBusqueda.idCliente = $funcionUtil.limpiarCadenaPeticionGetDeSelect2MultiplePaginacion($funcionUtil.crearCadenaPeticionGetDeSelect2MultiplePaginacion($local.$clientesDetalle));
		$.ajax({
			type: "GET",
			url: `${$variableUtil.root}reporte/facturacionUBA/detalle?accion=buscar`,
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
					$local.tablasResultadosDetalle["tablaDetalle-" + value.codigoMoneda].rows.add(value.facturacionDetalle).draw();
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
		criterioBusqueda.descripcionFechaProceso = $local.$fechaDetalle.val() || 'TODOS';
		criterioBusqueda.descripcionInstitucion = $local.$institucionesDetalle.find('option:selected').text();
		criterioBusqueda.descripcionEmpresa = $local.$empresasDetalle.find("option:selected").val() === "-1" ? "TODOS" : $local.$empresasDetalle.find("option:selected").text();
		criterioBusqueda.descripcionCliente = !!$local.$clientesDetalle.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$clientesDetalle, "; ") : "TODOS";
		criterioBusqueda.idCliente = $funcionUtil.limpiarCadenaPeticionGetDeSelect2MultiplePaginacion($funcionUtil.crearCadenaPeticionGetDeSelect2MultiplePaginacion($local.$clientesDetalle));
		var paramCriterioBusqueda = $.param(criterioBusqueda);
		$funcionUtil.webSocketMethod('exportacionPOI');
		window.location.href = `${$variableUtil.root}reporte/facturacionUBA/detalle?rutaEnSidebar=${$variableUtil.rutaEnSidebar}&accion=exportar&${paramCriterioBusqueda}`;
	});

	$local.$btnBuscarMiscelaneos.on('click', function() {
		if (!$formTipoReporteMiscelaneos.valid()) {
			return;
		}
		if ($funcionUtil.camposVacios2($formTipoReporteMiscelaneos, $local.numFiltrosMin)) {
			$funcionUtil.notificarException($local.mensajeFiltros(), "fa-exclamation-circle", "Informaci\u00F3n", "info");
			return;
		}
		var criterioBusqueda = $formTipoReporteMiscelaneos.serializeJSON();
		var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$fechaMiscelaneos);
		criterioBusqueda.fechaInicio = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaFin = rangoFechaBusqueda.fechaFin;
		criterioBusqueda.codigoMoneda = 0
		criterioBusqueda.idCliente = $funcionUtil.limpiarCadenaPeticionGetDeSelect2MultiplePaginacion($funcionUtil.crearCadenaPeticionGetDeSelect2MultiplePaginacion($local.$clientesMiscelaneos));
		$.ajax({
			type: "GET",
			url: `${$variableUtil.root}reporte/facturacionUBA/miscelaneos?accion=buscar`,
			data: criterioBusqueda,
			beforeSend: function() {
				$.each($local.tablasResultadosMiscelaneos, function(i, tabla) {
					$local.tablasResultadosMiscelaneos[i].clear().draw();
				});
				$local.$btnBuscarMiscelaneos.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success: function(response) {
				if (response.length === 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				$.each(response, function(idx, value) {
					$local.tablasResultadosMiscelaneos["tablaMisc-" + value.codigoMoneda].rows.add(value.facturacionMiscelaneos).draw();
				});
			},
			error: function(response) {
			},
			complete: function() {
				$local.$btnBuscarMiscelaneos.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$btnExportarMiscelaneos.on('click', function() {
		if (!$formTipoReporteMiscelaneos.valid()) {
			return;
		}
		if ($funcionUtil.camposVacios2($formTipoReporteMiscelaneos, $local.numFiltrosMin)) {
			$funcionUtil.notificarException($local.mensajeFiltros(), "fa-exclamation-circle", "Informaci\u00F3n", "info");
			return;
		}
		var criterioBusqueda = $formTipoReporteMiscelaneos.serializeJSON();
		var rangoFechaBusquedaProceso = $funcionUtil.obtenerFechasDateRangePicker($local.$fechaMiscelaneos);
		criterioBusqueda.fechaInicio = rangoFechaBusquedaProceso.fechaInicio;
		criterioBusqueda.fechaFin = rangoFechaBusquedaProceso.fechaFin;
		criterioBusqueda.descripcionFechaProceso = $local.$fechaMiscelaneos.val() || 'TODOS';
		criterioBusqueda.descripcionInstitucion = $local.$institucionesMiscelaneos.find('option:selected').text();
		criterioBusqueda.descripcionEmpresa = $local.$empresasMiscelaneos.find("option:selected").val() === "-1" ? "TODOS" : $local.$empresasMiscelaneos.find("option:selected").text();
		criterioBusqueda.descripcionCliente = !!$local.$clientesMiscelaneos.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$clientesMiscelaneos, "; ") : "TODOS";
		criterioBusqueda.idCliente = $funcionUtil.limpiarCadenaPeticionGetDeSelect2MultiplePaginacion($funcionUtil.crearCadenaPeticionGetDeSelect2MultiplePaginacion($local.$clientesMiscelaneos));
		var paramCriterioBusqueda = $.param(criterioBusqueda);
		$funcionUtil.webSocketMethod('exportacionPOI');
		window.location.href = $variableUtil.root + "reporte/facturacionUBA/miscelaneos?rutaEnSidebar=" + $variableUtil.rutaEnSidebar + "&accion=exportar&" + paramCriterioBusqueda;
	});

	function ajustarColumnas() {
		$.fn.dataTable.tables({
			visible: true,
			api: true
		}).columns.adjust().draw();
	}
});