$(document).ready(function () {

	var $local = {
		$buscar: $("#buscar"),
		$exportar: $("#exportar"),
		$tablaResumenLogContableEmisor: $("#tablaResumenLogContableEmisor"),
		tablaResultados: "",
		tablasResultados: {},
		tablaResumenLogContableEmisor: "",
		$encabezados: "",
		encabezadoComisionesArreglo: [],

		// FILTROS
		$rangoFechaBusqueda: $("#rangoFechaBusquedaContEmi"),
		$instituciones: $("#institucionesContEmi"),
		$empresas: $('#empresasContEmi'),
		$clientes: $('#clientesContEmi'),
		$rolesTransaccion: $("#rolesTransaccionContEmi"),
		$membresias: $("#membresiasContEmi"),
		$clasesServicio: $("#clasesServicioContEmi"),
		$origenes: $("#origenesContEmi"),
		$clasesTransacciones: $("#clasesTransaccionesContEmi"),
		$codigosTransacciones: $("#codigosTransaccionesContEmi"),
		$canales: $("#canalesContEmi")
	};
	
	$funcionUtil.crearDateRangePickerSimple($local.$rangoFechaBusqueda, "right");
	$funcionUtil.crearSelect2($local.$instituciones);
	$funcionUtil.crearSelect2($local.$empresas, "Seleccione una Empresa");
	$funcionUtil.crearSelect2Multiple($local.$clientes, "-1", "TODOS");
	$funcionUtil.crearSelect2($local.$rolesTransaccion, "Seleccione un Rol Transacci\u00F3n");
	$funcionUtil.crearSelect2($local.$membresias, "Seleccione una Membres\u00EDa");
	$funcionUtil.crearSelect2Multiple($local.$clasesServicio, "-1", "TODOS");
	$funcionUtil.crearSelect2Multiple($local.$origenes, "-1", "TODOS");
	$funcionUtil.crearSelect2($local.$clasesTransacciones, "Seleccione una Clase Transacci\u00F3n");
	$funcionUtil.crearSelect2Multiple($local.$codigosTransacciones, "-1", "TODOS");
	$funcionUtil.crearSelect2Multiple($local.$canales, "-1", "TODOS");
	
	$.fn.dataTable.moment = function ( format, locale ) {
		var types = $.fn.dataTable.ext.type;
	
		// Add type detection
		types.detect.unshift( function ( d ) {
			return moment( d, format, locale, true ).isValid() ?
				'moment-'+format :
				null;
		} );
	
		// Add sorting method - use an integer for the sorting
		types.order[ 'moment-'+format+'-pre' ] = function ( d ) {
			return moment( d, format, locale, true ).unix();
		};
	};

	$.fn.dataTable.moment('DD/MM/YYYY');

	$local.$encabezados = $local.$tablaResumenLogContableEmisor.find("th.conceptoComision");

	for (var i = 0; i < $local.$encabezados.length; i++) {
		var conceptoComision = $local.$encabezados.filter("[name=" + i + "]").attr("idConceptoComision");
		$local.encabezadoComisionesArreglo.push(conceptoComision);
	}

	/* VARIABLE GLOBAL */
	$formCriterioBusquedaReporte = $("#formCriterioBusquedaReporte");

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

	$local.$membresias.on("change", function () {
		var codigoMembresia = $(this).val();
		if (codigoMembresia == null || codigoMembresia == "") {
			$local.$clasesServicio.find("option").not("[value='']").remove();
			return;
		}
		$.ajax({
			type: "GET",
			url: `${$variableUtil.root}claseServicio/membresia/${codigoMembresia}`,
			beforeSend: function (xhr) {
				$local.$clasesServicio.find("option").not("[value='']").remove();
				$local.$clasesServicio.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Servicios...</span>")
				xhr.setRequestHeader('Content-Type', 'application/json');
				$local.$buscar.attr("disabled", true);
			},
			statusCode: {
				400: function (response) {
					$funcionUtil.limpiarMensajesDeError($formCriterioBusquedaReporte);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formCriterioBusquedaReporte);
				}
			},
			success: function (clasesServicio) {
				$.each(clasesServicio, function (i, claseServicio) {
					$local.$clasesServicio.append($("<option />").val(this.codigoClaseServicio).text($funcionUtil.unirCodigoDescripcion(this.codigoClaseServicio, this.descripcion)));
				});
			},
			error: function (response) {
			},
			complete: function (response) {
				$local.$clasesServicio.parent().find(".cargando").remove();
				$local.$buscar.attr("disabled", false);
			}
		});
	});

	$local.$clasesTransacciones.on("change", function (event, opinionSelections) {
		var claseTransaccion = $(this).val();
		if (claseTransaccion == null || claseTransaccion == "-1" || claseTransaccion === undefined) {
			$local.$codigosTransacciones.find("option").not("[value='-1']").remove();
			return;
		}
		$.ajax({
			type: "GET",
			url: `${$variableUtil.root}codigoTransaccion/claseTransaccion/${claseTransaccion}`,
			beforeSend: function (xhr) {
				$local.$clasesTransacciones.prop('disabled', true);
				$local.$codigosTransacciones.prop('disabled', true);
				$local.$codigosTransacciones.find("option").not("[value='-1']").remove();
				$local.$codigosTransacciones.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando C\u00F3digos Transacci\u00F3n...</span>")
				xhr.setRequestHeader('Content-Type', 'application/json');
				$local.$buscar.attr("disabled", true);
			},
			statusCode: {
				400: function (response) {
					$funcionUtil.limpiarMensajesDeError($formCriterioBusquedaReporte);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formCriterioBusquedaReporte);
				}
			},
			success: function (codigosTransacciones) {
				$.each(codigosTransacciones, function (i, codigoTransaccion) {
					$local.$codigosTransacciones.append($("<option />").val(this.codigoTransaccion).text($funcionUtil.unirCodigoDescripcion(this.codigoTransaccion, this.descripcion)));
				});
				if (opinionSelections != null && opinionSelections != undefined) {
					$local.$codigosTransacciones.val(opinionSelections).trigger("change.select2");
				}
			},
			error: function (response) {
			},
			complete: function (response) {
				$local.$codigosTransacciones.parent().find(".cargando").remove();
				$local.$clasesTransacciones.prop('disabled', false);
				$local.$codigosTransacciones.prop('disabled', false);
				$local.$buscar.attr("disabled", false);
			}
		});
	});

	$local.tablaResumenLogContableEmisor = $local.$tablaResumenLogContableEmisor.DataTable({
		"initComplete": function () {
			$local.$tablaResumenLogContableEmisor.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaResumenLogContableEmisor);
		},
		"language": {
			"emptyTable": "No se encontraron transacciones."
		},
		"columnDefs": [{
			"targets": 0,
			"className": "all filtrable dt-center",
		}, {
			"targets": [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 ],
			"className": "all filtrable",
		}, {
			"targets": [ 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28 ],
			"className": "all filtrable dt-right",
		}],
		"footerCallback": function (row, data, start, end, display) {
			var tieneData = $local.tablaResumenLogContableEmisor == "" ? false : $local.tablaResumenLogContableEmisor.data().any();
			var api = this.api(), data;
			api.columns(".comision").every(function () {
				var index = this.selector.cols;
				if (tieneData) {
					var suma = this.data().reduce(function (a, b) {
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
			if (tieneData) {
				var cantidadTotal = api.column(16).data().reduce(function (a, b) {
					return parseInt(a) + parseInt(b);
				}, 0);
				var monto = api.column(17).data().reduce(function (a, b) {
					return parseFloat($funcionUtil.formatMoneyToNumber(a)) + parseFloat($funcionUtil.formatMoneyToNumber(b));
				}, 0);
				$(api.column(16).footer()).html($funcionUtil.formatMoney(cantidadTotal, 0));
				$(api.column(17).footer()).html($funcionUtil.formatMoney(monto, 2));
				$(api.column(17).footer()).removeClassRegex("color-");
				if (monto > 0) {
					$(api.column(17).footer()).addClass("color-blue");
				} else if (monto < 0) {
					$(api.column(17).footer()).addClass("color-red");
				} else {
					$(api.column(17).footer()).addClass("color-inherit");
				}
			} else {
				$(api.column(16).footer()).html("");
				$(api.column(17).footer()).html("");
			}
		},
		"order": [],
		"createdRow": function (row, data, dataIndex) {
			$(row).find(".monto").filter(function () {
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
		"headerCallback": function (thead, data, start, end, display) {
			$(thead).find('th').eq(18).attr({
				"data-tooltip": "tooltip",
				"title": $variableUtil.comisionTarjetahabiente
			});
			$(thead).find('th').eq(19).attr({
				"data-tooltip": "tooltip",
				"title": $variableUtil.comisionEstablecimiento
			});
			$(thead).find('th').eq(20).attr({
				"data-tooltip": "tooltip",
				"title": $variableUtil.comisionReceptor
			});
			$(thead).find('th').eq(21).attr({
				"data-tooltip": "tooltip",
				"title": $variableUtil.comisionOperador
			});
			$(thead).find('th').eq(22).attr({
				"data-tooltip": "tooltip",
				"title": $variableUtil.comisionIsa
			});
			$(thead).find('th').eq(23).attr({
				"data-tooltip": "tooltip",
				"title": $variableUtil.comisionOif
			});
			$(thead).find('th').eq(24).attr({
				"data-tooltip": "tooltip",
				"title": $variableUtil.comisionIng
			});

			$(thead).find('th').eq(25).attr({
				"data-tooltip": "tooltip",
				"title": $variableUtil.comisionCobroInteres
			});
			$(thead).find('th').eq(26).attr({
				"data-tooltip": "tooltip",
				"title": $variableUtil.comisionTipoC
			});
			$(thead).find('th').eq(27).attr({
				"data-tooltip": "tooltip",
				"title": $variableUtil.comisionIntercambio
			});
		}
	});

	$local.$tablaResumenLogContableEmisor.find("thead").on('keyup', 'input.filtrable', function () {
		$local.tablaResumenLogContableEmisor.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaResumenLogContableEmisor.find("thead").on('change', 'select', function () {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaResumenLogContableEmisor.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	$local.$buscar.on("click", function () {
		if (!$formCriterioBusquedaReporte.valid()) {
			return;
		}
		if ($funcionUtil.camposVacios($formCriterioBusquedaReporte)) {
			$funcionUtil.notificarException($variableUtil.camposVacios, "fa-exclamation-circle", "Informaci\u00F3n", "info");
			return;
		}
		var criterioBusqueda = $formCriterioBusquedaReporte.serializeJSON();
		var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaBusqueda);
		criterioBusqueda.fechaInicioProceso = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaFinProceso = rangoFechaBusqueda.fechaFin;
		criterioBusqueda.fechaFinProceso = rangoFechaBusqueda.fechaFin;
		criterioBusqueda.codigoMembresia = $local.$membresias.find("option:selected").val() === "-1" ? "" : $local.$membresias.find("option:selected").val();
		let paramCriterioBusqueda = $.param(criterioBusqueda);
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$clientes, "clientes");
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$clasesServicio, "servicios");
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$origenes, "origenes");
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$codigosTransacciones, "codigosTransaccion");
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$canales, "canales");
		$.ajax({
			type: "GET",
			url: `${$variableUtil.root}reporte/resumen/movimiento/logContableEmisor?accion=buscar&${paramCriterioBusqueda}`,
			contentType: "application/json",
			dataType: "json",
			beforeSend: function () {
				$local.tablaResumenLogContableEmisor.clear().draw();
				$local.$buscar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success: function (reporteLogContableEmisor) {
				if (reporteLogContableEmisor.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				var filas = new Array();
				var j = -1;
				var finInicioCelda = "</td><td>";
				$.each(reporteLogContableEmisor, function (i, resumenLogContableEmisor) {
					filas[++j] = "<tr><td>";
					filas[++j] = resumenLogContableEmisor.fechaProceso;
					filas[++j] = finInicioCelda;
					filas[++j] = $funcionUtil.unirCodigoDescripcion(resumenLogContableEmisor.idEmpresa, resumenLogContableEmisor.descEmpresa);
					filas[++j] = finInicioCelda;
					filas[++j] = $funcionUtil.unirCodigoDescripcion(resumenLogContableEmisor.idCliente, resumenLogContableEmisor.descCliente);
					filas[++j] = finInicioCelda;
					filas[++j] = $funcionUtil.unirCodigoDescripcion(resumenLogContableEmisor.idLogo, resumenLogContableEmisor.descripcionLogo);
					filas[++j] = finInicioCelda;
					filas[++j] = $funcionUtil.unirCodigoDescripcion(resumenLogContableEmisor.codigoProducto, resumenLogContableEmisor.descProducto);
					filas[++j] = finInicioCelda;
					filas[++j] = $funcionUtil.unirCodigoDescripcion(resumenLogContableEmisor.rolTransaccion, resumenLogContableEmisor.descripcionRolTransaccion);
					filas[++j] = finInicioCelda;
					filas[++j] = $funcionUtil.unirCodigoDescripcion(resumenLogContableEmisor.codigoMembresia, resumenLogContableEmisor.descripcionMembresia);
					filas[++j] = finInicioCelda;
					filas[++j] = $funcionUtil.unirCodigoDescripcion(resumenLogContableEmisor.codigoClaseServicio, resumenLogContableEmisor.descripcionClaseServicio);
					filas[++j] = finInicioCelda;
					filas[++j] = $funcionUtil.unirCodigoDescripcion(resumenLogContableEmisor.codigoOrigen, resumenLogContableEmisor.descripcionOrigen);
					filas[++j] = finInicioCelda;
					filas[++j] = $funcionUtil.unirCodigoDescripcion(resumenLogContableEmisor.claseTransaccion, resumenLogContableEmisor.descripcionClaseTransaccion);
					filas[++j] = finInicioCelda;
					filas[++j] = $funcionUtil.unirCodigoDescripcion(resumenLogContableEmisor.codigoTransaccion, resumenLogContableEmisor.descripcionCodigoTransaccion);
					filas[++j] = finInicioCelda;
					filas[++j] = $funcionUtil.unirCodigoDescripcion(resumenLogContableEmisor.codigoRespuesta, resumenLogContableEmisor.descripcionCodigoRespuesta);
					filas[++j] = finInicioCelda;
					filas[++j] = $funcionUtil.unirCodigoDescripcion(resumenLogContableEmisor.idCanal, resumenLogContableEmisor.descripcionCanal);
					filas[++j] = finInicioCelda;
					filas[++j] = $funcionUtil.unirCodigoDescripcion(resumenLogContableEmisor.codigoGiroNegocio, resumenLogContableEmisor.descripcionGiroNegocio);
					filas[++j] = finInicioCelda;
					filas[++j] = $funcionUtil.unirCodigoDescripcion(resumenLogContableEmisor.codigoInstitucionReceptor, resumenLogContableEmisor.descripcionInstitucionReceptor);
					filas[++j] = finInicioCelda;
					filas[++j] = $funcionUtil.unirCodigoDescripcion(resumenLogContableEmisor.codigoMoneda, resumenLogContableEmisor.descripcionMoneda);
					filas[++j] = "</td><td class='dt-right'>";
					filas[++j] = resumenLogContableEmisor.cantidad.formatMoney(0);
					filas[++j] = "</td><td class='dt-right monto'>";
					filas[++j] = resumenLogContableEmisor.monto.formatMoney(2);
					filas[++j] = "</td><td class='dt-right monto comision'>";
					var k = -1;
					var subTotal = 0;
					for (var k = 0; k < $local.encabezadoComisionesArreglo.length; k++) {
						var montoComision = 0;
						var idConceptoComision = $local.encabezadoComisionesArreglo[k];
						$.each(resumenLogContableEmisor.comisiones, function (k, comision) {
							if (comision.idConceptoComision == idConceptoComision) {
								montoComision = comision.comision || 0;
								var signo = "";
								if (montoComision != 0) {
									signo = comision.registroContable == "C" ? "-" : "";
								}
								montoComision = parseFloat(signo + montoComision.formatMoney(4));
								return false;
							}
						});
						filas[++j] = montoComision.formatMoney(4);
						filas[++j] = "</td><td class='dt-right monto comision'>";
						subTotal = parseFloat(subTotal) + parseFloat(montoComision);
					}
					filas[++j] = parseFloat(subTotal).formatMoney(4);
					filas[++j] = "</td></tr>";
				});
				$local.tablaResumenLogContableEmisor.rows.add($(filas.join(''))).draw();
				$tablaFuncion.pintarMontosComisiones($local.$tablaResumenLogContableEmisor, "tfoot td.monto");
			},
			complete: function () {
				$local.$buscar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$exportar.on("click", function () {
		if (!$formCriterioBusquedaReporte.valid()) {
			return;
		}
		var criterioBusqueda = $formCriterioBusquedaReporte.serializeJSON();
		var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaBusqueda);
		criterioBusqueda.fechaInicioProceso = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaFinProceso = rangoFechaBusqueda.fechaFin;
		criterioBusqueda.descripcionFechas = $local.$rangoFechaBusqueda.val() || 'TODOS';
		criterioBusqueda.descripcionInstitucion = $local.$instituciones.find('option:selected').text();
		criterioBusqueda.descripcionEmpresa = $local.$empresas.find("option:selected").val() === "-1" ? "TODOS" : $local.$empresas.find("option:selected").text();
		criterioBusqueda.descripcionCliente = !!$local.$clientes.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$clientes, "; ") : "TODOS";
		criterioBusqueda.descripcionRolTransaccion = $local.$rolesTransaccion.find("option:selected").val() === "-1" ? "TODOS" : $local.$rolesTransaccion.find("option:selected").text();
		criterioBusqueda.descripcionMembresia = $local.$membresias.find("option:selected").val() === "-1" ? "TODOS" : $local.$membresias.find("option:selected").text();
		criterioBusqueda.descripcionClaseServicio = !!$local.$clasesServicio.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$clasesServicio, "; ") : "TODOS";
		criterioBusqueda.descripcionOrigen = !!$local.$origenes.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$origenes, "; ") : "TODOS";
		criterioBusqueda.descripcionClaseTransaccion = $local.$clasesTransacciones.find("option:selected").val() === "-1" ? "TODOS" : $local.$clasesTransacciones.find("option:selected").text();
		criterioBusqueda.descripcionCodigoTransaccion = !!$local.$codigosTransacciones.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$codigosTransacciones, "; ") : "TODOS";
		criterioBusqueda.descripcionCanal = !!$local.$canales.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$canales, "; ") : "TODOS";
		var paramCriterioBusqueda = $.param(criterioBusqueda);
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$clientes, "clientes");
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$clasesServicio, "servicios");
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$origenes, "origenes");
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$codigosTransacciones, "codigosTransaccion");
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$canales, "canales");
		$funcionUtil.webSocketMethod('exportacionPOI');
		window.location.href = `${$variableUtil.root}reporte/resumen/movimiento/logContableEmisor?rutaEnSidebar=${$variableUtil.rutaEnSidebar}&accion=exportar&${paramCriterioBusqueda}`;
	});
});