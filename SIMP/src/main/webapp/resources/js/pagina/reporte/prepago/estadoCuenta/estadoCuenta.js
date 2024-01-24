$(document).ready(function() {

	var $local = {
		$cmbEmpresas: $("#empresas"),
		$cmbClientes: $("#clientes"),

		$numeroTarjeta: $("#txtNumeroTarjeta"),
		$apellidoPaterno: $("#txtApellidoPaterno"),
		$saldoDisponible: $("#txtSaldoDisponible"),


		$tablaReporteContable: $("#tablaReporteEstadoCuenta"),
		tablaReporteContable: "",
		$resultadoBusquedaReporteContablePrepago: $("#resultadoBusquedaReporteEstadoCuenta"),
		$exportarXlsx: $("#exportarCriterios"),
		$exportarXlsxTipoDocumento: $("#btnExportarPorDocumentoCliente"),
		$exportarDocx: $("#exportarCriteriosDocx"),
		$exportarDocxTipoDocumento: $("#btnExportarPorDocumentoClienteDocx"),

		$tipoBusqueda: $("input[type=radio][name=tipoBusqueda]"),
		$tipoDocumento: $("#tipoDocumento"),
		$btnBuscarPorDocumentoCliente: $("#btnBuscarPorDocumentoCliente"),
		$selectTipoDocumento: $("#selectTipoDocumento"),
		$txtNumDocumentoCliente: $("#txtNumDocumentoCliente"),
		$buscarCriterios: $("#buscarCriterios"),
		$criterios: $("#criterios"),
		$rangoFechaBusqueda: $("#rangoFechaBusqueda"),
		$buscar: $("#buscar"),
		$datosCliente: $("#criterioBusquedaReporteDatosCliente"),

		$btnPrimero: $(".btnPrimero"),
		$btnSiguiente: $(".btnSiguiente"),
		$btnAnterior: $(".btnAnterior"),
		$btnUltimo: $(".btnUltimo")


	};

	$formBusquedaCriterios = $("#formBusquedaCriterios");
	$formCliente = $("#formDatosCliente");
	$formBusquedaTipoDocumento = $("#formParamIniciales");
	$funcionUtil.crearSelect2($local.$selectTipoDocumento, "Seleccion un Tipo de Documento");

	$funcionUtil.crearSelect2($local.$cmbEmpresas, "Seleccione Empresa");
	$funcionUtil.crearSelect2($local.$cmbClientes, "Seleccione Cliente");
	$funcionUtil.crearDateRangePickerConsulta($local.$rangoFechaBusqueda);

	$formCriterioBusquedaReporte = $("#formCriterioBusquedaReporte");

	$.fn.dataTable.ext.errMode = 'none';

	$local.$tablaReporteContable.on('xhr.dt', function(e, settings, json, xhr) {
		switch (xhr.status) {
			case 500:
				$local.tablaReporteContable.clear().draw();
				$funcionUtil.notificarException(xhr.responseText, "Error Interno", "danger");
				break;
		}
	});

	$local.tablaReporteContable = $local.$tablaReporteContable.DataTable({
		"language": {
			"emptyTable": "No hay transacciones encontradas"
		},
		"initComplete": function() {
			$local.$tablaReporteContable.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaReporteContable);
		},
		"columnDefs": [{
			"targets": [0, 1, 2, 3, 4, 5, 6, 7, 8],
			"className": "all filtrable",
		}, {
			"targets": 5,
			"className": "all  dt-right monto filtrable"
		}],
		"columns": [{
			"data": 'fechaTransaccionFmt',
			"title": "Fecha de Transacci\u00F3n"
		}, {
			"data": 'horaTransaccion',
			"title": "Hora Transaccion"
		}, {
			"data": 'trace',
			"title": "Trace"
		}, {
			"data": 'descripcionCodigoTransaccion',
			"title": "Operaci\u00F3n"
		}, {
			"data": 'nombreAfiliado',
			"title": "Descripcion"
		}, {
			"data": 'monedaTransaccion',
			"title": "Moneda Txn"
		}, {
			"data": function(row) {
				return row.valorTransaccionCargo.toFixed(2);
			},
			"title": "Valor Txn Cargo"
		}, {
			"data": function(row) {
				return row.valorTransaccionAbono.toFixed(2);
			},
			"title": "Valor Txn Abono"
		}, {
			"data": "monedaSoles",
			"title": "Moneda Soles"
		}, {
			"data": function(row) {
				return row.valorSoles.toFixed(6);
			},
			"title": "Valor Soles"
		}, {
			"data": function(row) {
				return row.tipoCambio.toFixed(4);
			},
			"title": "Tipo Cambio"
		}

		]
	});

	$local.$tablaReporteContable.wrap("<div class='table-responsive'></div>");
	//$tablaFuncion.aniadirBotonEnTabla($(".dataTables_filter"), $variableUtil.tableBotonExportarXlsx, $variableUtil.posDerecho);

	$local.$tablaReporteContable.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaReporteContable.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaReporteContable.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaReporteContable.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});


	$local.$cmbEmpresas.on("change", function(event, opcionSeleccionada) {
		var idEmpresa = $(this).val();
		if (idEmpresa == null || idEmpresa == undefined) {
			$local.$cmbClientes.find("option:not(:eq(0))").remove();
			return;
		}
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "cliente/empresa/" + idEmpresa,
			beforeSend: function(xhr) {
				$local.$cmbClientes.find("option:not(:eq(0))").remove();
				$local.$cmbClientes.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Clientes</span>")
			},
			statusCode: {
				400: function(response) {
					//$funcionUtil.limpiarMensajesDeError($formMantenimiento);
					//$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formMantenimiento);
				}
			},
			success: function(clientes) {
				$.each(clientes, function(i, cliente) {
					$local.$cmbClientes.append($("<option />").val(this.idCliente).text(this.idCliente + " - " + this.descripcion));
				});
				if (opcionSeleccionada != null && opcionSeleccionada != undefined) {
					$local.$cmbClientes.val(opcionSeleccionada).trigger("change.select2");
				}
			},
			complete: function() {
				$local.$cmbClientes.parent().find(".cargando").remove();
			}
		});
	});

	$local.$exportarXlsx.on("click", function() {
		if (!$formBusquedaCriterios.valid()) {
			return;
		}
		if ($funcionUtil.camposVacios($formBusquedaCriterios)) {
			$funcionUtil.notificarException($variableUtil.camposVacios, "fa-exclamation-circle", "Informaci\u00F3n", "info");
		} else {
			var criterioBusqueda = $formBusquedaCriterios.serializeJSON();
			var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaBusqueda);
			criterioBusqueda.fechaInicio = rangoFechaBusqueda.fechaInicio;
			criterioBusqueda.fechaFin = rangoFechaBusqueda.fechaFin;
			var paramCriterioBusqueda = $.param(criterioBusqueda);
			window.location.href = $variableUtil.root + "reporte/estadoCuenta/prepago/xlsx?accion=exportarCriterios&" + paramCriterioBusqueda;
		}
	});

	$local.$exportarXlsxTipoDocumento.on("click", function() {
		if (!$formBusquedaTipoDocumento.valid()) {
			return;
		} else {
			var criterioBusqueda = $formBusquedaTipoDocumento.serializeJSON();
			var paramCriterioBusqueda = $.param(criterioBusqueda);
			window.location.href = $variableUtil.root + "reporte/estadoCuenta/prepago/xlsx?accion=exportarTipoDocumento&" + paramCriterioBusqueda;
		}
	});

	$local.$exportarDocx.on("click", function() {
		if (!$formBusquedaCriterios.valid()) {
			return;
		}
		if ($funcionUtil.camposVacios($formBusquedaCriterios)) {
			$funcionUtil.notificarException($variableUtil.camposVacios, "fa-exclamation-circle", "Informaci\u00F3n", "info");
		} else {
			var criterioBusqueda = $formBusquedaCriterios.serializeJSON();
			var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaBusqueda);
			criterioBusqueda.fechaInicio = rangoFechaBusqueda.fechaInicio;
			criterioBusqueda.fechaFin = rangoFechaBusqueda.fechaFin;
			var paramCriterioBusqueda = $.param(criterioBusqueda);
			window.location.href = $variableUtil.root + "reporte/estadoCuenta/prepago/pdf?accion=exportarCriterios&" + paramCriterioBusqueda;
		}
	});

	$local.$exportarDocxTipoDocumento.on("click", function() {
		if (!$formBusquedaTipoDocumento.valid()) {
			return;
		} else {
			var criterioBusqueda = $formBusquedaTipoDocumento.serializeJSON();
			var paramCriterioBusqueda = $.param(criterioBusqueda);
			window.location.href = $variableUtil.root + "reporte/estadoCuenta/prepago/pdf?accion=exportarTipoDocumento&" + paramCriterioBusqueda;
		}
	});


	/*Para los filtros*/
	$local.$tipoBusqueda.on("change", function() {
		var tipoBusqueda = $(this).val();
		switch (tipoBusqueda) {
			case "tipoDocumento":
				$local.$tipoDocumento.removeClass("hidden");
				$local.$criterios.addClass("hidden");
				break;
			case "criterios":
				$local.$tipoDocumento.addClass("hidden");
				$local.$criterios.removeClass("hidden");
				break;
			default:
				$funcionUtil.notificarException("Seleccione un Tipo de B\u00FAsqueda v\u00E1lido", "fa-warning", "Aviso", "warning");
		}
	});

	$formBusquedaTipoDocumento.find("input").keypress(function(event) {
		if (event.which == 13) {
			$local.$btnBuscarPorDocumentoCliente.trigger("click");
			return false;
		}
	});

	$local.$btnBuscarPorDocumentoCliente.on("click", function() {
		if (!$formBusquedaTipoDocumento.valid()) {
			return;
		}
		var criterioBusqueda = $formBusquedaTipoDocumento.serializeJSON();
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "reporte/estadoCuenta/prepago?accion=buscarPorNumeroDocumento",
			data: criterioBusqueda,
			statusCode: {
				400: function(response) {
					$funcionUtil.limpiarMensajesDeError($formBusquedaTipoDocumento);
					$funcionUtil.limpiarMensajesDeError($formBusquedaCriterios);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaCriterios);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaTipoDocumento);
				}
			},
			beforeSend: function() {
				$local.tablaReporteContable.clear().draw();
				$local.$btnBuscarPorDocumentoCliente.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success: function(reporteEstadoCuentaPrepago) {
				if (reporteEstadoCuentaPrepago.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}

				$funcionUtil.llenarFormulario(reporteEstadoCuentaPrepago[0], $formCliente);
				if (reporteEstadoCuentaPrepago[0].movimientos != null && reporteEstadoCuentaPrepago[0].movimientos != undefined) {
					$local.tablaReporteContable.rows.add(reporteEstadoCuentaPrepago[0].movimientos).draw();
				}
				$local.$datosCliente.removeClass("hidden");
			},
			complete: function() {
				$local.$btnBuscarPorDocumentoCliente.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$buscarCriterios.on("click", function() {
		if (!$formBusquedaCriterios.valid()) {
			return;
		}
		if ($funcionUtil.camposVacios($formBusquedaCriterios)) {
			$funcionUtil.notificarException($variableUtil.camposVacios, "fa-exclamation-circle", "Informaci\u00F3n", "info");
		} else {
			var criterioBusqueda = $formBusquedaCriterios.serializeJSON();
			var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaBusqueda);
			criterioBusqueda.fechaInicio = rangoFechaBusqueda.fechaInicio;
			criterioBusqueda.fechaFin = rangoFechaBusqueda.fechaFin;
			$.ajax({
				type: "GET",
				url: $variableUtil.root + "/reporte/estadoCuenta/prepago?accion=buscarPorCriterio",
				data: criterioBusqueda,
				statusCode: {
					400: function(response) {
						$funcionUtil.limpiarMensajesDeError($formBusquedaTipoDocumento);
						$funcionUtil.limpiarMensajesDeError($formBusquedaCriterios);
						$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaCriterios);
						$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaTipoDocumento);
					}
				},
				beforeSend: function() {
					$local.tablaReporteContable.clear().draw();
					$local.$buscarCriterios.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
				},
				success: function(reporteEstadoCuentaPrepago) {
					if (reporteEstadoCuentaPrepago.length == 0) {
						$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
						return;
					}

					$funcionUtil.llenarFormulario(reporteEstadoCuentaPrepago[0], $formCliente);
					if (reporteEstadoCuentaPrepago[0].movimientos != null && reporteEstadoCuentaPrepago[0].movimientos != undefined) {
						$local.tablaReporteContable.rows.add(reporteEstadoCuentaPrepago[0].movimientos).draw();
					}
					$local.$datosCliente.removeClass("hidden");
				},
				complete: function() {
					$local.$buscarCriterios.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
				}
			});
		}
	});

	$local.$btnSiguiente.on("click", function() {
		$local.$btnAnterior.removeClass("hidden");
		var indexes = $local.tablaMantenimiento.rows().indexes();
		var currentIndex = $local.tablaMantenimiento.row($local.$filaSeleccionada).index();
		var currentPosition = indexes.indexOf(currentIndex);

		if (currentPosition < indexes.length - 1) {
			var swDmpLogSgte = $local.tablaMantenimiento.row(indexes[currentPosition + 1]).data();
			var fechaTransmision = $funcionUtil.convertirDeFormatoAFormato(swDmpLogSgte.fechaTransmision, "DD/MM/YYYY", "YYYY-MM-DD");
			var data = {
				"numeroCuenta": swDmpLogSgte.numeroCuenta,
				"fechaTransmision": fechaTransmision,
				"numeroRastreo": swDmpLogSgte.numeroRastreo,
				"tipoMensaje": swDmpLogSgte.tipoMensaje
			}
			$.ajax({
				type: "GET",
				url: $variableUtil.root + "txnsSwDmpLog?accion=buscarDetalle",
				data: data,
				beforeSend: function(xhr) {
					xhr.setRequestHeader('Content-Type', 'application/json');
					xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
				},
				success: function(detalleSwDmpLog) {
					if (detalleSwDmpLog.length == 0) {
						$local.$modalDetalleConsulta.PopupWindow("close");
						$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros n\u00FAmeroTarjeta: " + swDmpLogSgte.numeroCuenta + " ,fechaTransmisi\u00F3n: " +
							swDmpLogSgte.fechaTransmision + " ,n\u00FAmeroTrace: " + swDmpLogSgte.numeroRastreo + " ,tipoMensaje: " + swDmpLogSgte.tipoMensaje + " no encontr\u00F3 ninguna coincidencia en la Base de Datos.", "fa-exclamation-circle", "Informaci\u00F3n", "info");
					} else {
						if (detalleSwDmpLog.length == 1) {
							$local.$modalDetalleConsulta.PopupWindow("open");
							$local.$modalDetalleConsulta.PopupWindow("maximize");
							$funcionUtil.llenarFormulario(detalleSwDmpLog[0], $local.$detalleSwDmpLog);
							$funcionUtil.llenarFormulario(detalleSwDmpLog[0], $local.$detalleCliente);
							$funcionUtil.descripcionLarga(detalleSwDmpLog[0].descripcionGiroNegocio);
						} else {
							$local.$modalDetalleConsulta.PopupWindow("close");
							$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros n\u00FAmeroTarjeta: " + swDmpLogSgte.numeroCuenta + " ,fechaTransmisi\u00F3n: " +
								swDmpLogSgte.fechaTransmision + " ,n\u00FAmeroTrace: " + swDmpLogSgte.numeroRastreo + " ,tipoMensaje: " + swDmpLogSgte.tipoMensaje + " retorn\u00F3 m\u00E1s de un resultado", "fa-exclamation-circle", "Mensaje", "danger");
						}
					}
				}
			})

			$local.tablaMantenimiento.row(currentIndex).deselect();
			$local.$filaSeleccionada = $local.tablaMantenimiento.row(indexes[currentPosition + 1]).select();
		}
	});

	$local.$btnAnterior.on("click", function() {
		$local.$btnSiguiente.removeClass("hidden");
		var indexes = $local.tablaMantenimiento.rows().indexes();
		var currentIndex = $local.tablaMantenimiento.row($local.$filaSeleccionada).index();
		var currentPosition = indexes.indexOf(currentIndex);

		if (currentPosition >= 0) {
			var swDmpLogAnt = $local.tablaMantenimiento.row(indexes[currentPosition - 1]).data();
			var fechaTransmision = $funcionUtil.convertirDeFormatoAFormato(swDmpLogAnt.fechaTransmision, "DD/MM/YYYY", "YYYY-MM-DD");

			var data = {
				"numeroCuenta": swDmpLogAnt.numeroCuenta,
				"fechaTransmision": fechaTransmision,
				"numeroRastreo": swDmpLogAnt.numeroRastreo,
				"tipoMensaje": swDmpLogAnt.tipoMensaje
			}
			$.ajax({
				type: "GET",
				url: $variableUtil.root + "txnsSwDmpLog?accion=buscarDetalle",
				data: data,
				beforeSend: function(xhr) {
					xhr.setRequestHeader('Content-Type', 'application/json');
					xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
				},
				success: function(detalleSwDmpLog) {
					if (detalleSwDmpLog.length == 0) {
						$local.$modalDetalleConsulta.PopupWindow("close");
						$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros n\u00FAmeroTarjeta: " + swDmpLogAnt.numeroCuenta + " ,fechaTransmisi\u00F3n: " +
							swDmpLogAnt.fechaTransmision + " ,n\u00FAmeroTrace: " + swDmpLogAnt.numeroRastreo + " ,tipoMensaje: " + swDmpLogAnt.tipoMensaje + " no encontr\u00F3 ninguna coincidencia en la Base de Datos.", "fa-exclamation-circle", "Informaci\u00F3n", "info");
					} else {
						if (detalleSwDmpLog.length == 1) {
							$local.$modalDetalleConsulta.PopupWindow("open");
							$local.$modalDetalleConsulta.PopupWindow("maximize");
							$funcionUtil.llenarFormulario(detalleSwDmpLog[0], $local.$detalleSwDmpLog);
							$funcionUtil.llenarFormulario(detalleSwDmpLog[0], $local.$detalleCliente);
							$funcionUtil.descripcionLarga(detalleSwDmpLog[0].descripcionGiroNegocio);
						} else {
							$local.$modalDetalleConsulta.PopupWindow("close");
							$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros n\u00FAmeroTarjeta: " + swDmpLogAnt.numeroCuenta + " ,fechaTransmisi\u00F3n: " +
								swDmpLogAnt.fechaTransmision + " ,n\u00FAmeroTrace: " + swDmpLogAnt.numeroRastreo + " ,tipoMensaje: " + swDmpLogAnt.tipoMensaje + " retorn\u00F3 m\u00E1s de un resultado", "fa-exclamation-circle", "Mensaje", "danger");
						}
					}
				}
			})
			$local.tablaMantenimiento.row(currentIndex).deselect();
			$local.$filaSeleccionada = $local.tablaMantenimiento.row(indexes[currentPosition - 1]).select();
		}
	});

	$local.$btnPrimero.on("click", function() {
		$local.$btnAnterior.addClass("hidden");
		$local.$btnSiguiente.removeClass("hidden");
		var indexes = $local.tablaMantenimiento.rows().indexes();
		var currentIndex = $local.tablaMantenimiento.row($local.$filaSeleccionada).index();
		var swDmpLogPrimero = $local.tablaMantenimiento.row(indexes[0]).data();
		var fechaTransmision = $funcionUtil.convertirDeFormatoAFormato(swDmpLogPrimero.fechaTransmision, "DD/MM/YYYY", "YYYY-MM-DD");
		var data = {
			"numeroCuenta": swDmpLogPrimero.numeroCuenta,
			"fechaTransmision": fechaTransmision,
			"numeroRastreo": swDmpLogPrimero.numeroRastreo,
			"tipoMensaje": swDmpLogPrimero.tipoMensaje
		}
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "txnsSwDmpLog?accion=buscarDetalle",
			data: data,
			beforeSend: function(xhr) {
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			success: function(detalleSwDmpLog) {
				if (detalleSwDmpLog.length == 0) {
					$local.$modalDetalleConsulta.PopupWindow("close");
					$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros n\u00FAmeroTarjeta: " + swDmpLogPrimero.numeroCuenta + " ,fechaTransmisi\u00F3n: " +
						swDmpLogPrimero.fechaTransmision + " ,n\u00FAmeroTrace: " + swDmpLogPrimero.numeroRastreo + " ,tipoMensaje: " + swDmpLogPrimero.tipoMensaje + " no encontr\u00F3 ninguna coincidencia en la Base de Datos.", "fa-exclamation-circle", "Informaci\u00F3n", "info");
				} else {
					if (detalleSwDmpLog.length == 1) {
						$local.$modalDetalleConsulta.PopupWindow("open");
						$local.$modalDetalleConsulta.PopupWindow("maximize");
						$funcionUtil.llenarFormulario(detalleSwDmpLog[0], $local.$detalleSwDmpLog);
						$funcionUtil.llenarFormulario(detalleSwDmpLog[0], $local.$detalleCliente);
						$funcionUtil.descripcionLarga(detalleSwDmpLog[0].descripcionGiroNegocio);
					} else {
						$local.$modalDetalleConsulta.PopupWindow("close");
						$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros n\u00FAmeroTarjeta: " + swDmpLogPrimero.numeroCuenta + " ,fechaTransmisi\u00F3n: " +
							swDmpLogPrimero.fechaTransmision + " ,n\u00FAmeroTrace: " + swDmpLogPrimero.numeroRastreo + " ,tipoMensaje: " + swDmpLogPrimero.tipoMensaje + " retorn\u00F3 m\u00E1s de un resultado", "fa-exclamation-circle", "Mensaje", "danger");
					}
				}
			}
		})

		$local.tablaMantenimiento.row(currentIndex).deselect();
		$local.$filaSeleccionada = $local.tablaMantenimiento.row(indexes[0]).select();
	});

	$local.$btnUltimo.on("click", function() {
		$local.$btnSiguiente.addClass("hidden");
		$local.$btnAnterior.removeClass("hidden");
		var indexes = $local.tablaMantenimiento.rows().indexes();
		var currentIndex = $local.tablaMantenimiento.row($local.$filaSeleccionada).index();
		var swDmpLogUltimo = $local.tablaMantenimiento.row(indexes[indexes.length - 1]).data();
		var fechaTransmision = $funcionUtil.convertirDeFormatoAFormato(swDmpLogUltimo.fechaTransmision, "DD/MM/YYYY", "YYYY-MM-DD");
		var data = {
			"numeroCuenta": swDmpLogUltimo.numeroCuenta,
			"fechaTransmision": fechaTransmision,
			"numeroRastreo": swDmpLogUltimo.numeroRastreo,
			"tipoMensaje": swDmpLogUltimo.tipoMensaje
		}
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "txnsSwDmpLog?accion=buscarDetalle",
			data: data,
			beforeSend: function(xhr) {
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			success: function(detalleSwDmpLog) {
				if (detalleSwDmpLog.length == 0) {
					$local.$modalDetalleConsulta.PopupWindow("close");
					$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros n\u00FAmeroTarjeta: " + swDmpLogUltimo.numeroCuenta + " ,fechaTransmisi\u00F3n: " +
						swDmpLogUltimo.fechaTransmision + " ,n\u00FAmeroTrace: " + swDmpLogUltimo.numeroRastreo + " ,tipoMensaje: " + swDmpLogUltimo.tipoMensaje + " no encontr\u00F3 ninguna coincidencia en la Base de Datos.", "fa-exclamation-circle", "Informaci\u00F3n", "info");
				} else {
					if (detalleSwDmpLog.length == 1) {
						$local.$modalDetalleConsulta.PopupWindow("open");
						$local.$modalDetalleConsulta.PopupWindow("maximize");
						$funcionUtil.llenarFormulario(detalleSwDmpLog[0], $local.$detalleSwDmpLog);
						$funcionUtil.llenarFormulario(detalleSwDmpLog[0], $local.$detalleCliente);
						$funcionUtil.descripcionLarga(detalleSwDmpLog[0].descripcionGiroNegocio);
					} else {
						$local.$modalDetalleConsulta.PopupWindow("close");
						$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros n\u00FAmeroTarjeta: " + swDmpLogUltimo.numeroCuenta + " ,fechaTransmisi\u00F3n: " +
							swDmpLogUltimo.fechaTransmision + " ,n\u00FAmeroTrace: " + swDmpLogUltimo.numeroRastreo + " ,tipoMensaje: " + swDmpLogUltimo.tipoMensaje + " retorn\u00F3 m\u00E1s de un resultado", "fa-exclamation-circle", "Mensaje", "danger");
					}
				}
			}
		})

		$local.tablaMantenimiento.row(currentIndex).deselect();
		$local.$filaSeleccionada = $local.tablaMantenimiento.row(indexes[indexes.length - 1]).select();
	});
});