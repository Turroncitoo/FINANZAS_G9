$(document).ready(function() {
	var $local = {
		$tablaConsolidada: $("#tablaConsulta"),
		tablaConsolidada: "",
		filtrosSeleccionables: [],
		$buscarCriterios: $("#buscarCriterios"),
		$rangoFechasTxn: $("#rangoFechasTransaccion"),
		$membresiasFiltroParaTableMantenimiento: $("#membresias-filtroParaTablaMantenimiento"),
		$detalleCompensacion: $("#detalleCompensacion"),
		$detalleSwDmpLog: $("#detalleSwDmpLog"),
		$detalleAutorizada: $("#detalleAutorizacion"),
		$modalDetalleConsulta: $("#modalDetalleConsulta"),
		$detalleCliente: $("#detalleCliente"),
		$tipoBusqueda: $("input[type=radio][name=tipoBusqueda]"),
		$tipoDocumento: $("#tipoDocumento"),
		$criterios: $("#criterios"),
		$selectTipoDocumento: $("#selectTipoDocumento"),
		$btnBuscarPorDocumentoCliente: $("#btnBuscarPorDocumentoCliente"),
		$canalFiltroParaTablaConsulta: $("#canalFiltroParaTablaConsulta"),
		$origenesArchivo: $("#origenesArchivo"),
		$txtNumDocumentoCliente: $("#txtNumDocumentoCliente"),
		$btnPrimero: $(".btnPrimero"),
		$btnSiguiente: $(".btnSiguiente"),
		$btnAnterior: $(".btnAnterior"),
		$btnUltimo: $(".btnUltimo"),
		$instituciones: $('#instituciones'),
		// Permiso
		permisoDetalle: false
	};

	$formBusquedaCriterios = $("#formBusquedaCriterios");
	$formBusquedaTipoDocumento = $("#formParamIniciales");
	$funcionUtil.crearSelect2($local.$selectTipoDocumento, "Seleccion un Tipo de Documento");
	$funcionUtil.crearSelect2($local.$canalFiltroParaTablaConsulta);
	$funcionUtil.crearSelect2($local.$origenesArchivo);
	$funcionUtil.crearSelect2($local.$instituciones);
	$funcionUtil.crearDateRangePickerConsulta($local.$rangoFechasTxn);

	$local.$modalDetalleConsulta.PopupWindow({
		title: "Detalle Consolidada",
		autoOpen: false,
		modal: false,
		height: 400,
		width: 600
	});

	$local.permisoDetalle = $local.$tablaConsolidada.attr("data-permiso-detalle") || false;

	$local.tablaConsolidada = $local.$tablaConsolidada.DataTable({
		"language": {
			"emptyTable": "No se han encontrado Transacciones Consolidadas con los criterios definidos."
		},
		"initComplete": function() {
			$local.$tablaConsolidada.wrap("<div class='table-responsive'></div>");
			$local.filtrosSeleccionables["0"] = $local.$membresiasFiltroParaTableMantenimiento.html();
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaConsolidada, $local.filtrosSeleccionables);
		},
		"columnDefs": [{
			"targets": [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16],
			"className": "all filtrable",
		}, {
			"targets": 17,
			"className": "all dt-center",
			"width": "10%",
			"render": function() {
				return "" + ($local.permisoDetalle == "true" ? $variableUtil.botonVerDetalle : "");
			}
		}],
		"columns": [{
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoInstitucion, row.descripcionInstitucion);
			},
			"title": "Instituci\u00F3n"
		}, {
			"data": 'numeroTarjeta',
			"title": 'N\u00FAmero Tarjeta'
		}, {
			"data": 'numeroCuenta',
			"title": 'N\u00FAmero Cuenta'
		}, {
			"data": 'origenArchivo',
			"title": 'Origen Archivo'
		}, {
			"data": 'canal',
			"title": 'Canal'
		}, {
			"data": 'tipoTransaccion',
			"title": 'Tipo Transacci\u00F3n'
		}, {
			"data": 'fechaTransaccion',
			"title": 'Fecha Transacci\u00F3n'
		}, {
			"data": 'horaTransaccion',
			"title": 'Hora Transacci\u00F3n'
		}, {
			"data": 'fechaCompensacion',
			"title": 'Fecha Compensaci\u00F3n'
		}, {
			"data": 'numeroTrace',
			"title": 'N\u00FAmero Trace'
		}, {
			"data": 'autorizacion',
			"title": 'Autorizaci\u00F3n'
		}, {
			"data": 'adquirente',
			"title": 'Adquirente'
		}, {
			"data": 'monedaTransaccion',
			"title": 'Moneda'
		}, {
			"data": 'valorTransaccion',
			"title": 'Monto Transacci\u00F3n'
		}, {
			"data": 'monedaCompensacion',
			"title": 'Moneda'
		}, {
			"data": 'valorCompensacion',
			"title": 'Monto Compensaci\u00F3n'
		}, {
			"data": 'codigoRespuesta',
			"title": 'C\u00F3digo Respuesta'
		}, {
			"data": null,
			"title": 'Acci\u00F3n'
		}],
		"createdRow": function(row, data, dataIndex) {
			if (data.estadoTarjeta == "ACTIVA") {
				$(row).css("background-color", "Green");
				$(row).addClass("success");
			} else {
				$(row).css("background-color", "Red");
				$(row).addClass("danger");
			}
		}
	});

	$local.$tablaConsolidada.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaConsolidada.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaConsolidada.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaConsolidada.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	$local.$buscarCriterios.on("click", function() {
		if (!$formBusquedaCriterios.valid()) {
			return;
		}
		if ($funcionUtil.camposVacios($formBusquedaCriterios)) {
			$funcionUtil.notificarException($variableUtil.camposVacios, "fa-exclamation-circle", "Informaci\u00F3n", "info");
		} else {
			var criterioBusqueda = $formBusquedaCriterios.serializeJSON();
			var rangoFechasTxn = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechasTxn);
			criterioBusqueda.fechaInicioTxn = rangoFechasTxn.fechaInicio;
			criterioBusqueda.fechaFinTxn = rangoFechasTxn.fechaFin;
			$.ajax({
				type: "GET",
				url: $variableUtil.root + "txnsConsolidada?accion=buscarPorFiltro",
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
					$local.tablaConsolidada.clear().draw();
					$local.$buscarCriterios.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
				},
				success: function(transaccionConsolidada) {
					if (transaccionConsolidada.length == 0) {
						$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
						return;
					}
					$local.tablaConsolidada.rows.add(transaccionConsolidada).draw();
				},
				complete: function() {
					$local.$buscarCriterios.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
				}
			});
		}
	});

	$local.$tablaConsolidada.children("tbody").on("click", ".ver-detalle", function() {
		$local.$btnPrimero.removeClass("hidden");
		$local.$btnSiguiente.removeClass("hidden");
		$local.$btnAnterior.removeClass("hidden");
		$local.$btnUltimo.removeClass("hidden");
		$local.$filaSeleccionada = $(this).parents("tr");
		var consolidada = $local.tablaConsolidada.row($local.$filaSeleccionada).data();
		var idOrigenArchivo = consolidada.idOrigenArchivo;
		switch (idOrigenArchivo) {
			case "2":
				var fechaTransmision = $funcionUtil.convertirDeFormatoAFormato(consolidada.fechaTransaccion, "DD/MM/YYYY", "YYYY-MM-DD");
				var data = {
					"numeroCuenta": consolidada.numeroTarjeta,
					"fechaTransmision": fechaTransmision,
					"numeroRastreo": consolidada.numeroTrace,
					"tipoMensaje": consolidada.tipoMensaje,
					"idSecuencia": consolidada.idSecuencia,
					"codigoInstitucion": consolidada.codigoInstitucion
				}
				$.ajax({
					type: "GET",
					url: $variableUtil.root + "txnsAutorizacion?accion=buscarDetalle",
					data: data,
					beforeSend: function(xhr) {
						xhr.setRequestHeader('Content-Type', 'application/json');
						xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
					},
					success: function(detalleAutorizacion) {
						if (detalleAutorizacion.length == 0) {
							$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros N\u00FAmero de Tarjeta: " + consolidada.numeroTarjeta + " ,fechaTransmisi\u00F3n: " + consolidada.fechaTransaccion + " ,n\u00FAmeroTrace: " + consolidada.numeroTrace + " ,tipoMensaje: " + consolidada.tipoMensaje + " no encontr\u00F3 ninguna coincidencia en la Base de Datos.", "fa-exclamation-circle", "Informaci\u00F3n", "info");
						} else {
							if (detalleAutorizacion.length == 1) {
								$local.$modalDetalleConsulta.PopupWindow("open");
								$local.$modalDetalleConsulta.PopupWindow("maximize");
								$funcionUtil.limpiarCamposFormulario($local.$detalleCompensacion);
								$funcionUtil.limpiarCamposFormulario($local.$detalleSwDmpLog);
								$local.$detalleAutorizada.removeClass("hidden");
								$local.$detalleCompensacion.addClass("hidden");
								$local.$detalleSwDmpLog.addClass("hidden");
								$funcionUtil.llenarFormulario(detalleAutorizacion[0], $local.$detalleCliente);
								$funcionUtil.llenarFormulario(detalleAutorizacion[0], $local.$detalleAutorizacion);
							} else {
								$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros N\u00FAmero de Tarjeta: " + consolidada.numeroTarjeta + " ,fechaTransmisi\u00F3n: " + consolidada.fechaTransaccion + " ,n\u00FAmeroTrace: " + consolidada.numeroTrace + " ,tipoMensaje: " + consolidada.tipoMensaje + " retorn\u00F3 m\u00E1s de un resultado", "fa-exclamation-circle", "Mensaje", "danger");
							}
						}
					}
				});
				break;
			case "3":
				var fechaTransaccion = $funcionUtil.convertirDeFormatoAFormato(consolidada.fechaTransaccion, "DD/MM/YYYY", "YYYY-MM-DD");
				var data = {
					"fechaTransmision": fechaTransaccion,
					"numeroRastreo": consolidada.numeroTrace,
					"tipoMensaje": consolidada.tipoMensaje,
					"codigoSeguimiento": consolidada.idSecuencia,
					"codigoInstitucion": consolidada.codigoInstitucion
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
							$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros N\u00FAmero de Tarjeta: " + consolidada.numeroTarjeta + " ,fechaTransmisi\u00F3n: " + consolidada.fechaTransaccion + " ,n\u00FAmeroTrace: " + consolidada.numeroTrace + " ,tipoMensaje: " + consolidada.tipoMensaje + " no encontr\u00F3 ninguna coincidencia en la Base de Datos.", "fa-exclamation-circle", "Informaci\u00F3n", "info");
						} else {
							if (detalleSwDmpLog.length == 1) {
								$local.$modalDetalleConsulta.PopupWindow("open");
								$local.$modalDetalleConsulta.PopupWindow("maximize");
								$funcionUtil.limpiarCamposFormulario($local.$detalleCompensacion);
								$funcionUtil.limpiarCamposFormulario($local.$detalleAutorizada);
								$local.$detalleAutorizada.addClass("hidden");
								$local.$detalleCompensacion.addClass("hidden");
								$local.$detalleSwDmpLog.removeClass("hidden");
								$funcionUtil.llenarFormulario(detalleSwDmpLog[0], $local.$detalleSwDmpLog);
								$funcionUtil.llenarFormulario(detalleSwDmpLog[0], $local.$detalleCliente);
							} else {
								$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros N\u00FAmero de Tarjeta: " + consolidada.numeroTarjeta + " ,fechaTransmisi\u00F3n: " + consolidada.fechaTransaccion + " ,n\u00FAmeroTrace: " + consolidada.numeroTrace + " ,tipoMensaje: " + consolidada.tipoMensaje + " retorn\u00F3 m\u00E1s de un resultado", "fa-exclamation-circle", "Mensaje", "danger");
							}

						}
					}
				});
				break;
			case "4":
				var fechaTransaccion = $funcionUtil.convertirDeFormatoAFormato(consolidada.fechaTransaccion, "DD/MM/YYYY", "YYYY-MM-DD");
				var data = {
					"fechaTransaccion": fechaTransaccion,
					"numeroVoucher": consolidada.numeroTrace,
					"secuenciaTransaccion": consolidada.idSecuencia,
					"idInstitucion": consolidada.codigoInstitucion
				}
				$.ajax({
					type: "GET",
					url: $variableUtil.root + "txnsCompensacion?accion=buscarDetalle",
					data: data,
					beforeSend: function(xhr) {
						xhr.setRequestHeader('Content-Type', 'application/json');
						xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
					},
					success: function(detalleCompensacion) {
						if (detalleCompensacion.length == 0) {
							$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros n\u00FAmeroTarjeta: " + consolidada.numeroTarjeta + " ,fechaTransmisi\u00F3n: " + consolidada.fechaTransaccion + " ,n\u00FAmeroTrace: " + consolidada.numeroTrace + " no encontr\u00F3 ninguna coincidencia en la Base de Datos.", "fa-exclamation-circle", "Informaci\u00F3n", "info");
						} else {
							if (detalleCompensacion.length == 1) {
								$local.$modalDetalleConsulta.PopupWindow("open");
								$local.$modalDetalleConsulta.PopupWindow("maximize");
								$funcionUtil.limpiarCamposFormulario($local.$detalleSwDmpLog);
								$local.$detalleAutorizada.addClass("hidden");
								$local.$detalleSwDmpLog.addClass("hidden");
								$local.$detalleCompensacion.removeClass("hidden");
								$funcionUtil.llenarFormulario(detalleCompensacion[0], $local.$detalleCliente);
								$funcionUtil.llenarFormulario(detalleCompensacion[0], $local.$detalleCompensacion);
							} else {
								$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros n\u00FAmeroTarjeta: " + consolidada.numeroTarjeta + " ,fechaTransmisi\u00F3n: " + consolidada.fechaTransaccion + " ,n\u00FAmeroTrace: " + consolidada.numeroTrace + " retorn\u00F3 m\u00E1s de un resultado", "fa-exclamation-circle", "Mensaje", "danger");
							}
						}
					}
				});
				break;
			case "5":
				$funcionUtil.notificarException("Las Transacciones Liberadas no tienen Detalle", "fa-exclamation-circle", "Informaci\u00F3n", "info");
				break;
		}
	});

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
			url: $variableUtil.root + "txnsConsolidada?accion=buscarPorDocumento",
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
				$local.tablaConsolidada.clear().draw();
				$local.$btnBuscarPorDocumentoCliente.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success: function(transaccionConsolidada) {
				if (transaccionConsolidada.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				$local.tablaConsolidada.rows.add(transaccionConsolidada).draw();
			},
			complete: function() {
				$local.$btnBuscarPorDocumentoCliente.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$btnPrimero.on("click", function() {
		$local.$btnAnterior.addClass("hidden");
		$local.$btnSiguiente.removeClass("hidden");
		var indexes = $local.tablaConsolidada.rows().indexes();
		var currentIndex = $local.tablaConsolidada.row($local.$filaSeleccionada).index();
		var consolidadaPrimero = $local.tablaConsolidada.row(indexes[0]).data();
		var idOrigenArchivo = consolidadaPrimero.idOrigenArchivo;
		switch (idOrigenArchivo) {
			case "2":
				var fechaTransmision = $funcionUtil.convertirDeFormatoAFormato(consolidadaPrimero.fechaTransaccion, "DD/MM/YYYY", "YYYY-MM-DD");
				var data = {
					"numeroCuenta": consolidadaPrimero.numeroTarjeta,
					"fechaTransmision": fechaTransmision,
					"numeroRastreo": consolidadaPrimero.numeroTrace,
					"tipoMensaje": consolidadaPrimero.tipoMensaje,
					"idSecuencia": consolidadaPrimero.idSecuencia,
					"codigoInstitucion": consolidadaPrimero.codigoInstitucion
				}
				$.ajax({
					type: "GET",
					url: $variableUtil.root + "txnsAutorizacion?accion=buscarDetalle",
					data: data,
					beforeSend: function(xhr) {
						xhr.setRequestHeader('Content-Type', 'application/json');
						xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
					},
					success: function(detalleAutorizacion) {
						if (detalleAutorizacion.length == 0) {
							$local.$modalDetalleConsulta.PopupWindow("close");
							$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros n\u00FAmeroTarjeta: " + consolidadaPrimero.numeroTarjeta + " ,fechaTransmisi\u00F3n: " + consolidadaPrimero.fechaTransaccion + " ,n\u00FAmeroTrace: " + consolidadaPrimero.numeroTrace + " ,tipoMensaje: " + consolidadaPrimero.tipoMensaje + " no encontr\u00F3 ninguna coincidencia en la Base de Datos.", "fa-exclamation-circle", "Informaci\u00F3n", "info");
						} else {
							if (detalleAutorizacion.length == 1) {
								$local.$modalDetalleConsulta.PopupWindow("open");
								$local.$modalDetalleConsulta.PopupWindow("maximize");
								$funcionUtil.limpiarCamposFormulario($local.$detalleCompensacion);
								$funcionUtil.limpiarCamposFormulario($local.$detalleSwDmpLog);
								$local.$detalleAutorizada.removeClass("hidden");
								$local.$detalleCompensacion.addClass("hidden");
								$local.$detalleSwDmpLog.addClass("hidden");
								$funcionUtil.llenarFormulario(detalleAutorizacion[0], $local.$detalleCliente);
								$funcionUtil.llenarFormulario(detalleAutorizacion[0], $local.$detalleAutorizacion);
							} else {
								$local.$modalDetalleConsulta.PopupWindow("close");
								$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros n\u00FAmeroTarjeta: " + consolidadaPrimero.numeroTarjeta + " ,fechaTransmisi\u00F3n: " + consolidadaPrimero.fechaTransaccion + " ,n\u00FAmeroTrace: " + consolidadaPrimero.numeroTrace + " ,tipoMensaje: " + consolidada.tipoMensaje + " retorn\u00F3 m\u00E1s de un resultado", "fa-exclamation-circle", "Mensaje", "danger");
							}
						}
					}
				});
				$local.tablaConsolidada.row(currentIndex).deselect();
				$local.$filaSeleccionada = $local.tablaConsolidada.row(indexes[0]).select();
				break;
			case "3":
				var fechaTransaccion = $funcionUtil.convertirDeFormatoAFormato(consolidadaPrimero.fechaTransaccion, "DD/MM/YYYY", "YYYY-MM-DD");
				var data = {
					"fechaTransmision": fechaTransaccion,
					"numeroRastreo": consolidadaPrimero.numeroTrace,
					"tipoMensaje": consolidadaPrimero.tipoMensaje,
					"codigoSeguimiento": consolidadaPrimero.idSecuencia,
					"codigoInstitucion": consolidadaPrimero.codigoInstitucion
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
							$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros n\u00FAmeroTarjeta: " + consolidadaPrimero.numeroTarjeta + " ,fechaTransmisi\u00F3n: " + consolidadaPrimero.fechaTransaccion + " ,n\u00FAmeroTrace: " + consolidadaPrimero.numeroTrace + " ,tipoMensaje: " + consolidadaPrimero.tipoMensaje + " no encontr\u00F3 ninguna coincidencia en la Base de Datos.", "fa-exclamation-circle", "Informaci\u00F3n", "info");
						} else {
							if (detalleSwDmpLog.length == 1) {
								$local.$modalDetalleConsulta.PopupWindow("open");
								$local.$modalDetalleConsulta.PopupWindow("maximize");
								$funcionUtil.limpiarCamposFormulario($local.$detalleCompensacion);
								$funcionUtil.limpiarCamposFormulario($local.$detalleAutorizada);
								$local.$detalleAutorizada.addClass("hidden");
								$local.$detalleCompensacion.addClass("hidden");
								$local.$detalleSwDmpLog.removeClass("hidden");
								$funcionUtil.llenarFormulario(detalleSwDmpLog[0], $local.$detalleSwDmpLog);
								$funcionUtil.llenarFormulario(detalleSwDmpLog[0], $local.$detalleCliente);
							} else {
								$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros n\u00FAmeroTarjeta: " + consolidadaPrimero.numeroTarjeta + " ,fechaTransmisi\u00F3n: " + consolidadaPrimero.fechaTransaccion + " ,n\u00FAmeroTrace: " + consolidadaPrimero.numeroTrace + " ,tipoMensaje: " + consolidada.tipoMensaje + " retorn\u00F3 m\u00E1s de un resultado", "fa-exclamation-circle", "Mensaje", "danger");
							}
						}
					}
				});
				$local.tablaConsolidada.row(currentIndex).deselect();
				$local.$filaSeleccionada = $local.tablaConsolidada.row(indexes[0]).select();
				break;
			case "4":
				var fechaTransaccion = $funcionUtil.convertirDeFormatoAFormato(consolidadaPrimero.fechaTransaccion, "DD/MM/YYYY", "YYYY-MM-DD");
				var data = {
					"fechaTransaccion": fechaTransaccion,
					"numeroVoucher": consolidadaPrimero.numeroTrace,
					"secuenciaTransaccion": consolidadaPrimero.idSecuencia,
					"idInstitucion": consolidadaPrimero.codigoInstitucion
				}
				$.ajax({
					type: "GET",
					url: $variableUtil.root + "txnsCompensacion?accion=buscarDetalle",
					data: data,
					beforeSend: function(xhr) {
						xhr.setRequestHeader('Content-Type', 'application/json');
						xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
					},
					success: function(detalleCompensacion) {
						if (detalleCompensacion.length == 0) {
							$local.$modalDetalleConsulta.PopupWindow("close");
							$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros n\u00FAmeroTarjeta: " + consolidadaPrimero.numeroTarjeta + " ,fechaTransmisi\u00F3n: " + consolidadaPrimero.fechaTransaccion + " ,n\u00FAmeroTrace: " + consolidadaPrimero.numeroTrace + " no encontr\u00F3 ninguna coincidencia en la Base de Datos.", "fa-exclamation-circle", "Informaci\u00F3n", "info");
						} else {
							if (detalleCompensacion.length == 1) {
								$local.$modalDetalleConsulta.PopupWindow("open");
								$local.$modalDetalleConsulta.PopupWindow("maximize");
								$funcionUtil.limpiarCamposFormulario($local.$detalleSwDmpLog);
								$local.$detalleAutorizada.addClass("hidden");
								$local.$detalleSwDmpLog.addClass("hidden");
								$local.$detalleCompensacion.removeClass("hidden");
								$funcionUtil.llenarFormulario(detalleCompensacion[0], $local.$detalleCliente);
								$funcionUtil.llenarFormulario(detalleCompensacion[0], $local.$detalleCompensacion);
							} else {
								$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros n\u00FAmeroTarjeta: " + consolidadaPrimero.numeroTarjeta + " ,fechaTransmisi\u00F3n: " + consolidadaPrimero.fechaTransaccion + " ,n\u00FAmeroTrace: " + consolidadaPrimero.numeroTrace + " retorn\u00F3 m\u00E1s de un resultado", "fa-exclamation-circle", "Mensaje", "danger");
							}
						}
					}
				});
				$local.tablaConsolidada.row(currentIndex).deselect();
				$local.$filaSeleccionada = $local.tablaConsolidada.row(indexes[0]).select();
				break;
			case "5":
				$local.$modalDetalleConsulta.PopupWindow("close");
				$funcionUtil.notificarException("Las Transacciones Liberadas no tienen Detalle", "fa-exclamation-circle", "Informaci\u00F3n", "info");
				break;
		}
	});

	$local.$btnUltimo.on("click", function() {
		$local.$btnSiguiente.addClass("hidden");
		$local.$btnAnterior.removeClass("hidden");
		var indexes = $local.tablaConsolidada.rows().indexes();
		var currentIndex = $local.tablaConsolidada.row($local.$filaSeleccionada).index();
		var consolidadaUltimo = $local.tablaConsolidada.row(indexes[indexes.length - 1]).data();
		var idOrigenArchivo = consolidadaUltimo.idOrigenArchivo;
		switch (idOrigenArchivo) {
			case "2":
				var fechaTransmision = $funcionUtil.convertirDeFormatoAFormato(consolidadaUltimo.fechaTransaccion, "DD/MM/YYYY", "YYYY-MM-DD");
				var data = {
					"numeroCuenta": consolidadaUltimo.numeroTarjeta,
					"fechaTransmision": fechaTransmision,
					"numeroRastreo": consolidadaUltimo.numeroTrace,
					"tipoMensaje": consolidadaUltimo.tipoMensaje,
					"idSecuencia": consolidadaUltimo.idSecuencia,
					"codigoInstitucion": consolidadaUltimo.codigoInstitucion
				}
				$.ajax({
					type: "GET",
					url: $variableUtil.root + "txnsAutorizacion?accion=buscarDetalle",
					data: data,
					beforeSend: function(xhr) {
						xhr.setRequestHeader('Content-Type', 'application/json');
						xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
					},
					success: function(detalleAutorizacion) {
						if (detalleAutorizacion.lentgh == 0) {
							$local.$modalDetalleConsulta.PopupWindow("close");
							$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros n\u00FAmeroTarjeta: " + consolidadaUltimo.numeroTarjeta + " ,fechaTransmisi\u00F3n: " + consolidadaUltimo.fechaTransaccion + " ,n\u00FAmeroTrace: " + consolidadaUltimo.numeroTrace + " ,tipoMensaje: " + consolidadaUltimo.tipoMensaje + " no encontr\u00F3 ninguna coincidencia en la Base de Datos.", "fa-exclamation-circle", "Informaci\u00F3n", "info");
						} else {
							if (detalleAutorizacion.length == 1) {
								$local.$modalDetalleConsulta.PopupWindow("open");
								$local.$modalDetalleConsulta.PopupWindow("maximize");
								$funcionUtil.limpiarCamposFormulario($local.$detalleCompensacion);
								$funcionUtil.limpiarCamposFormulario($local.$detalleSwDmpLog);
								$local.$detalleAutorizada.removeClass("hidden");
								$local.$detalleCompensacion.addClass("hidden");
								$local.$detalleSwDmpLog.addClass("hidden");
								$funcionUtil.llenarFormulario(detalleAutorizacion[0], $local.$detalleCliente);
								$funcionUtil.llenarFormulario(detalleAutorizacion[0], $local.$detalleAutorizacion);
							} else {
								$local.$modalDetalleConsulta.PopupWindow("close");
								$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros n\u00FAmeroTarjeta: " + consolidadaUltimo.numeroTarjeta + " ,fechaTransmisi\u00F3n: " + consolidadaUltimo.fechaTransaccion + " ,n\u00FAmeroTrace: " + consolidadaUltimo.numeroTrace + " ,tipoMensaje: " + consolidada.tipoMensaje + " retorn\u00F3 m\u00E1s de un resultado", "fa-exclamation-circle", "Mensaje", "danger");
							}
						}
					}
				});
				$local.tablaConsolidada.row(currentIndex).deselect();
				$local.$filaSeleccionada = $local.tablaConsolidada.row(indexes[indexes.length - 1]).select();
				break;
			case "3":
				var fechaTransaccion = $funcionUtil.convertirDeFormatoAFormato(consolidadaUltimo.fechaTransaccion, "DD/MM/YYYY", "YYYY-MM-DD");
				var data = {
					"fechaTransmision": fechaTransaccion,
					"numeroRastreo": consolidadaUltimo.numeroTrace,
					"tipoMensaje": consolidadaUltimo.tipoMensaje,
					"codigoSeguimiento": consolidadaUltimo.idSecuencia,
					"codigoInstitucion": consolidadaUltimo.codigoInstitucion
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
							$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros N\u00FAmero de Tarjeta: " + consolidadaUltimo.numeroTarjeta + " ,fechaTransmisi\u00F3n: " + consolidadaUltimo.fechaTransaccion + " ,n\u00FAmeroTrace: " + consolidadaUltimo.numeroTrace + " ,tipoMensaje: " + consolidadaUltimo.tipoMensaje + " no encontr\u00F3 ninguna coincidencia en la Base de Datos.", "fa-exclamation-circle", "Informaci\u00F3n", "info");
						} else {
							if (detalleSwDmpLog.length == 1) {
								$local.$modalDetalleConsulta.PopupWindow("open");
								$local.$modalDetalleConsulta.PopupWindow("maximize");
								$funcionUtil.limpiarCamposFormulario($local.$detalleCompensacion);
								$funcionUtil.limpiarCamposFormulario($local.$detalleAutorizada);
								$local.$detalleAutorizada.addClass("hidden");
								$local.$detalleCompensacion.addClass("hidden");
								$local.$detalleSwDmpLog.removeClass("hidden");
								$funcionUtil.llenarFormulario(detalleSwDmpLog[0], $local.$detalleSwDmpLog);
								$funcionUtil.llenarFormulario(detalleSwDmpLog[0], $local.$detalleCliente);
							} else {
								$local.$modalDetalleConsulta.PopupWindow("close");
								$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros N\u00FAmero de Tarjeta: " + consolidadaUltimo.numeroTarjeta + " ,fechaTransmisi\u00F3n: " + consolidadaUltimo.fechaTransaccion + " ,n\u00FAmeroTrace: " + consolidadaUltimo.numeroTrace + " ,tipoMensaje: " + consolidada.tipoMensaje + " retorn\u00F3 m\u00E1s de un resultado", "fa-exclamation-circle", "Mensaje", "danger");
							}
						}
					}
				});
				$local.tablaConsolidada.row(currentIndex).deselect();
				$local.$filaSeleccionada = $local.tablaConsolidada.row(indexes[indexes.length - 1]).select();
				break;
			case "4":
				var fechaTransaccion = $funcionUtil.convertirDeFormatoAFormato(consolidadaUltimo.fechaTransaccion, "DD/MM/YYYY", "YYYY-MM-DD");
				var data = {
					"fechaTransaccion": fechaTransaccion,
					"numeroVoucher": consolidadaUltimo.numeroTrace,
					"secuenciaTransaccion": consolidadaUltimo.idSecuencia,
					"idInstitucion": consolidadaUltimo.codigoInstitucion
				}
				$.ajax({
					type: "GET",
					url: $variableUtil.root + "txnsCompensacion?accion=buscarDetalle",
					data: data,
					beforeSend: function(xhr) {
						xhr.setRequestHeader('Content-Type', 'application/json');
						xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
					},
					success: function(detalleCompensacion) {
						if (detalleCompensacion.length == 0) {
							$local.$modalDetalleConsulta.PopupWindow("close");
							$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros N\u00FAmero de Tarjeta: " + consolidadaUltimo.numeroTarjeta + " ,fechaTransmisi\u00F3n: " + consolidadaUltimo.fechaTransaccion + " ,n\u00FAmeroTrace: " + consolidadaUltimo.numeroTrace + " no encontr\u00F3 ninguna coincidencia en la Base de Datos.", "fa-exclamation-circle", "Informaci\u00F3n", "info");
						} else {
							if (detalleCompensacion.length == 1) {
								$local.$modalDetalleConsulta.PopupWindow("open");
								$local.$modalDetalleConsulta.PopupWindow("maximize");
								$funcionUtil.limpiarCamposFormulario($local.$detalleSwDmpLog);
								$local.$detalleAutorizada.addClass("hidden");
								$local.$detalleSwDmpLog.addClass("hidden");
								$local.$detalleCompensacion.removeClass("hidden");
								$funcionUtil.llenarFormulario(detalleCompensacion[0], $local.$detalleCliente);
								$funcionUtil.llenarFormulario(detalleCompensacion[0], $local.$detalleCompensacion);
							} else {
								$local.$modalDetalleConsulta.PopupWindow("close");
								$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros N\u00FAmero de Tarjeta: " + consolidadaUltimo.numeroTarjeta + " ,fechaTransmisi\u00F3n: " + consolidadaUltimo.fechaTransaccion + " ,n\u00FAmeroTrace: " + consolidadaUltimo.numeroTrace + " retorn\u00F3 m\u00E1s de un resultado", "fa-exclamation-circle", "Mensaje", "danger");
							}
						}
					}
				});
				$local.tablaConsolidada.row(currentIndex).deselect();
				$local.$filaSeleccionada = $local.tablaConsolidada.row(indexes[indexes.length - 1]).select();
				break;
			case "5":
				$local.$modalDetalleConsulta.PopupWindow("close");
				$funcionUtil.notificarException("Las Transacciones Liberadas no tienen Detalle", "fa-exclamation-circle", "Informaci\u00F3n", "info");
				break;
		}
	});

	$local.$btnAnterior.on("click", function() {
		$local.$btnSiguiente.removeClass("hidden");
		var indexes = $local.tablaConsolidada.rows().indexes();
		var currentIndex = $local.tablaConsolidada.row($local.$filaSeleccionada).index();
		var currentPosition = indexes.indexOf(currentIndex);

		if (currentPosition >= 0) {
			var consolidadaAnt = $local.tablaConsolidada.row(indexes[currentPosition - 1]).data();
			var idOrigenArchivo = consolidadaAnt.idOrigenArchivo;
			switch (idOrigenArchivo) {
				case "2":
					var fechaTransmision = $funcionUtil.convertirDeFormatoAFormato(consolidadaAnt.fechaTransaccion, "DD/MM/YYYY", "YYYY-MM-DD");
					var data = {
						"numeroCuenta": consolidadaAnt.numeroTarjeta,
						"fechaTransmision": fechaTransmision,
						"numeroRastreo": consolidadaAnt.numeroTrace,
						"tipoMensaje": consolidadaAnt.tipoMensaje,
						"idSecuencia": consolidadaAnt.idSecuencia,
						"codigoInstitucion": consolidadaAnt.codigoInstitucion
					}
					$.ajax({
						type: "GET",
						url: $variableUtil.root + "txnsAutorizacion?accion=buscarDetalle",
						data: data,
						beforeSend: function(xhr) {
							xhr.setRequestHeader('Content-Type', 'application/json');
							xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
						},
						success: function(detalleAutorizacion) {
							if (detalleAutorizacion.length == 0) {
								$local.$modalDetalleConsulta.PopupWindow("close");
								$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros N\u00FAmero de Tarjeta: " + consolidadaAnt.numeroTarjeta + " ,fechaTransmisi\u00F3n: " + consolidadaAnt.fechaTransaccion + " ,n\u00FAmeroTrace: " + consolidadaAnt.numeroTrace + " ,tipoMensaje: " + consolidadaAnt.tipoMensaje + " no encontr\u00F3 ninguna coincidencia en la Base de Datos.", "fa-exclamation-circle", "Informaci\u00F3n", "info");
							} else {
								if (detalleAutorizacion.length == 1) {
									$local.$modalDetalleConsulta.PopupWindow("open");
									$local.$modalDetalleConsulta.PopupWindow("maximize");
									$funcionUtil.limpiarCamposFormulario($local.$detalleCompensacion);
									$funcionUtil.limpiarCamposFormulario($local.$detalleSwDmpLog);
									$local.$detalleAutorizada.removeClass("hidden");
									$local.$detalleCompensacion.addClass("hidden");
									$local.$detalleSwDmpLog.addClass("hidden");
									$funcionUtil.llenarFormulario(detalleAutorizacion[0], $local.$detalleCliente);
									$funcionUtil.llenarFormulario(detalleAutorizacion[0], $local.$detalleAutorizacion);
								} else {
									$local.$modalDetalleConsulta.PopupWindow("close");
									$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros N\u00FAmero de Tarjeta: " + consolidadaAnt.numeroTarjeta + " ,fechaTransmisi\u00F3n: " + consolidadaAnt.fechaTransaccion + " ,n\u00FAmeroTrace: " + consolidadaAnt.numeroTrace + " ,tipoMensaje: " + consolidada.tipoMensaje + " retorn\u00F3 m\u00E1s de un resultado", "fa-exclamation-circle", "Mensaje", "danger");
								}
							}
						}
					});
					$local.tablaConsolidada.row(currentIndex).deselect();
					$local.$filaSeleccionada = $local.tablaConsolidada.row(indexes[currentPosition - 1]).select();
					break;
				case "3":
					var fechaTransaccion = $funcionUtil.convertirDeFormatoAFormato(consolidadaAnt.fechaTransaccion, "DD/MM/YYYY", "YYYY-MM-DD");
					var data = {
						"fechaTransmision": fechaTransaccion,
						"numeroRastreo": consolidadaAnt.numeroTrace,
						"tipoMensaje": consolidadaAnt.tipoMensaje,
						"codigoSeguimiento": consolidadaAnt.idSecuencia,
						"codigoInstitucion": consolidadaAnt.codigoInstitucion
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
								$local.$modalDetalleConsulta.PopupWindow("open");
								$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros N\u00FAmero de Tarjeta: " + consolidadaAnt.numeroTarjeta + " ,fechaTransmisi\u00F3n: " + consolidadaAnt.fechaTransaccion + " ,n\u00FAmeroTrace: " + consolidadaAnt.numeroTrace + " ,tipoMensaje: " + consolidadaAnt.tipoMensaje + " no encontr\u00F3 ninguna coincidencia en la Base de Datos.", "fa-exclamation-circle", "Informaci\u00F3n", "info");
							} else {
								if (detalleSwDmpLog.length == 1) {
									$local.$modalDetalleConsulta.PopupWindow("open");
									$local.$modalDetalleConsulta.PopupWindow("maximize");
									$funcionUtil.limpiarCamposFormulario($local.$detalleCompensacion);
									$funcionUtil.limpiarCamposFormulario($local.$detalleAutorizada);
									$local.$detalleAutorizada.addClass("hidden");
									$local.$detalleCompensacion.addClass("hidden");
									$local.$detalleSwDmpLog.removeClass("hidden");
									$funcionUtil.llenarFormulario(detalleSwDmpLog[0], $local.$detalleSwDmpLog);
									$funcionUtil.llenarFormulario(detalleSwDmpLog[0], $local.$detalleCliente);
								} else {
									$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros N\u00FAmero de Tarjeta: " + consolidadaAnt.numeroTarjeta + " ,fechaTransmisi\u00F3n: " + consolidadaAnt.fechaTransaccion + " ,n\u00FAmeroTrace: " + consolidadaAnt.numeroTrace + " ,tipoMensaje: " + consolidada.tipoMensaje + " retorn\u00F3 m\u00E1s de un resultado", "fa-exclamation-circle", "Mensaje", "danger");
								}
							}
						}
					});
					$local.tablaConsolidada.row(currentIndex).deselect();
					$local.$filaSeleccionada = $local.tablaConsolidada.row(indexes[currentPosition - 1]).select();
					break;
				case "4":
					var fechaTransaccion = $funcionUtil.convertirDeFormatoAFormato(consolidadaAnt.fechaTransaccion, "DD/MM/YYYY", "YYYY-MM-DD");
					var data = {
						"fechaTransaccion": fechaTransaccion,
						"numeroVoucher": consolidadaAnt.numeroTrace,
						"secuenciaTransaccion": consolidadaAnt.idSecuencia,
						"idInstitucion": consolidadaAnt.codigoInstitucion
					}
					$.ajax({
						type: "GET",
						url: $variableUtil.root + "txnsCompensacion?accion=buscarDetalle",
						data: data,
						beforeSend: function(xhr) {
							xhr.setRequestHeader('Content-Type', 'application/json');
							xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
						},
						success: function(detalleCompensacion) {
							if (detalleCompensacion.lentgh == 0) {
								$local.$modalDetalleConsulta.PopupWindow("close");
								$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros N\u00FAmero de Tarjeta: " + consolidadaAnt.numeroTarjeta + " ,fechaTransmisi\u00F3n: " + consolidadaAnt.fechaTransaccion + " ,n\u00FAmeroTrace: " + consolidadaAnt.numeroTrace + " no encontr\u00F3 ninguna coincidencia en la Base de Datos.", "fa-exclamation-circle", "Informaci\u00F3n", "info");
							} else {
								if (detalleCompensacion.length == 1) {
									$local.$modalDetalleConsulta.PopupWindow("open");
									$local.$modalDetalleConsulta.PopupWindow("maximize");
									$funcionUtil.limpiarCamposFormulario($local.$detalleSwDmpLog);
									$local.$detalleAutorizada.addClass("hidden");
									$local.$detalleSwDmpLog.addClass("hidden");
									$local.$detalleCompensacion.removeClass("hidden");
									$funcionUtil.llenarFormulario(detalleCompensacion[0], $local.$detalleCliente);
									$funcionUtil.llenarFormulario(detalleCompensacion[0], $local.$detalleCompensacion);
								} else {
									$local.$modalDetalleConsulta.PopupWindow("close");
									$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros N\u00FAmero de Tarjeta: " + consolidadaAnt.numeroTarjeta + " ,fechaTransmisi\u00F3n: " + consolidadaAnt.fechaTransaccion + " ,n\u00FAmeroTrace: " + consolidadaAnt.numeroTrace + " retorn\u00F3 m\u00E1s de un resultado", "fa-exclamation-circle", "Mensaje", "danger");
								}
							}
						}
					});
					$local.tablaConsolidada.row(currentIndex).deselect();
					$local.$filaSeleccionada = $local.tablaConsolidada.row(indexes[currentPosition - 1]).select();
					break;
				case "5":
					$local.$modalDetalleConsulta.PopupWindow("close");
					$funcionUtil.notificarException("Las Transacciones Liberadas no tienen Detalle", "fa-exclamation-circle", "Informaci\u00F3n", "info");
					break;
			}
		}
	});

	$local.$btnSiguiente.on("click", function() {
		$local.$btnAnterior.removeClass("hidden");
		var indexes = $local.tablaConsolidada.rows().indexes();
		var currentIndex = $local.tablaConsolidada.row($local.$filaSeleccionada).index();
		var currentPosition = indexes.indexOf(currentIndex);

		if (currentPosition < indexes.length - 1) {
			var consolidadaSgte = $local.tablaConsolidada.row(indexes[currentPosition + 1]).data();
			var idOrigenArchivo = consolidadaSgte.idOrigenArchivo;

			switch (idOrigenArchivo) {
				case "2":
					var fechaTransmision = $funcionUtil.convertirDeFormatoAFormato(consolidadaSgte.fechaTransaccion, "DD/MM/YYYY", "YYYY-MM-DD");
					var data = {
						"numeroCuenta": consolidadaSgte.numeroTarjeta,
						"fechaTransmision": fechaTransmision,
						"numeroRastreo": consolidadaSgte.numeroTrace,
						"tipoMensaje": consolidadaSgte.tipoMensaje,
						"idSecuencia": consolidadaSgte.idSecuencia,
						"codigoInstitucion": consolidadaSgte.codigoInstitucion
					}
					$.ajax({
						type: "GET",
						url: $variableUtil.root + "txnsAutorizacion?accion=buscarDetalle",
						data: data,
						beforeSend: function(xhr) {
							xhr.setRequestHeader('Content-Type', 'application/json');
							xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
						},
						success: function(detalleAutorizacion) {
							if (detalleAutorizacion.length == 0) {
								$local.$modalDetalleConsulta.PopupWindow("close");
								$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros N\u00FAmero de Tarjeta: " + consolidadaSgte.numeroTarjeta + " ,fechaTransmisi\u00F3n: " + consolidadaSgte.fechaTransaccion + " ,n\u00FAmeroTrace: " + consolidadaSgte.numeroTrace + " ,tipoMensaje: " + consolidadaSgte.tipoMensaje + " no encontr\u00F3 ninguna coincidencia en la Base de Datos.", "fa-exclamation-circle", "Informaci\u00F3n", "info");
							} else {
								if (detalleAutorizacion.length == 1) {
									$local.$modalDetalleConsulta.PopupWindow("open");
									$local.$modalDetalleConsulta.PopupWindow("maximize");
									$funcionUtil.limpiarCamposFormulario($local.$detalleCompensacion);
									$funcionUtil.limpiarCamposFormulario($local.$detalleSwDmpLog);
									$local.$detalleAutorizada.removeClass("hidden");
									$local.$detalleCompensacion.addClass("hidden");
									$local.$detalleSwDmpLog.addClass("hidden");
									$funcionUtil.llenarFormulario(detalleAutorizacion[0], $local.$detalleCliente);
									$funcionUtil.llenarFormulario(detalleAutorizacion[0], $local.$detalleAutorizacion);
								} else {
									$local.$modalDetalleConsulta.PopupWindow("close");
									$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros N\u00FAmero de Tarjeta: " + consolidadaSgte.numeroTarjeta + " ,fechaTransmisi\u00F3n: " + consolidadaSgte.fechaTransaccion + " ,n\u00FAmeroTrace: " + consolidadaSgte.numeroTrace + " ,tipoMensaje: " + consolidada.tipoMensaje + " retorn\u00F3 m\u00E1s de un resultado", "fa-exclamation-circle", "Mensaje", "danger");
								}
							}
						}
					});
					$local.tablaConsolidada.row(currentIndex).deselect();
					$local.$filaSeleccionada = $local.tablaConsolidada.row(indexes[currentPosition + 1]).select();
					break;
				case "3":
					var fechaTransaccion = $funcionUtil.convertirDeFormatoAFormato(consolidadaSgte.fechaTransaccion, "DD/MM/YYYY", "YYYY-MM-DD");
					var data = {
						"fechaTransmision": fechaTransaccion,
						"numeroRastreo": consolidadaSgte.numeroTrace,
						"tipoMensaje": consolidadaSgte.tipoMensaje,
						"codigoSeguimiento": consolidadaSgte.idSecuencia,
						"codigoInstitucion": consolidadaSgte.codigoInstitucion
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
								$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros N\u00FAmero de Tarjeta: " + consolidadaSgte.numeroTarjeta + " ,fechaTransmisi\u00F3n: " + consolidadaSgte.fechaTransaccion + " ,n\u00FAmeroTrace: " + consolidadaSgte.numeroTrace + " ,tipoMensaje: " + consolidadaSgte.tipoMensaje + " no encontr\u00F3 ninguna coincidencia en la Base de Datos.", "fa-exclamation-circle", "Informaci\u00F3n", "info");
							} else {
								if (detalleSwDmpLog.length == 1) {
									$local.$modalDetalleConsulta.PopupWindow("open");
									$local.$modalDetalleConsulta.PopupWindow("maximize");
									$funcionUtil.limpiarCamposFormulario($local.$detalleCompensacion);
									$funcionUtil.limpiarCamposFormulario($local.$detalleAutorizada);
									$local.$detalleAutorizada.addClass("hidden");
									$local.$detalleCompensacion.addClass("hidden");
									$local.$detalleSwDmpLog.removeClass("hidden");
									$funcionUtil.llenarFormulario(detalleSwDmpLog[0], $local.$detalleSwDmpLog);
									$funcionUtil.llenarFormulario(detalleSwDmpLog[0], $local.$detalleCliente);
								} else {
									$local.$modalDetalleConsulta.PopupWindow("close");
									$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros N\u00FAmero de Tarjeta: " + consolidadaSgte.numeroTarjeta + " ,fechaTransmisi\u00F3n: " + consolidadaSgte.fechaTransaccion + " ,n\u00FAmeroTrace: " + consolidadaSgte.numeroTrace + " ,tipoMensaje: " + consolidada.tipoMensaje + " retorn\u00F3 m\u00E1s de un resultado", "fa-exclamation-circle", "Mensaje", "danger");
								}

							}
						}
					});
					$local.tablaConsolidada.row(currentIndex).deselect();
					$local.$filaSeleccionada = $local.tablaConsolidada.row(indexes[currentPosition + 1]).select();
					break;
				case "4":
					var fechaTransaccion = $funcionUtil.convertirDeFormatoAFormato(consolidadaSgte.fechaTransaccion, "DD/MM/YYYY", "YYYY-MM-DD");
					var data = {
						"fechaTransaccion": fechaTransaccion,
						"numeroVoucher": consolidadaSgte.numeroTrace,
						"secuenciaTransaccion": consolidadaSgte.idSecuencia,
						"idInstitucion": consolidadaSgte.codigoInstitucion
					}
					$.ajax({
						type: "GET",
						url: $variableUtil.root + "txnsCompensacion?accion=buscarDetalle",
						data: data,
						beforeSend: function(xhr) {
							xhr.setRequestHeader('Content-Type', 'application/json');
							xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
						},
						success: function(detalleCompensacion) {
							if (detalleCompensacion.length == 0) {
								$local.$modalDetalleConsulta.PopupWindow("close");
								$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros N\u00FAmero de Tarjeta: " + consolidadaSgte.numeroTarjeta + " ,fechaTransmisi\u00F3n: " + consolidadaSgte.fechaTransaccion + " ,n\u00FAmeroTrace: " + consolidadaSgte.numeroTrace + " no encontr\u00F3 ninguna coincidencia en la Base de Datos.", "fa-exclamation-circle", "Informaci\u00F3n", "info");
							} else {
								if (detalleCompensacion.length == 1) {
									$local.$modalDetalleConsulta.PopupWindow("open");
									$local.$modalDetalleConsulta.PopupWindow("maximize");
									$funcionUtil.limpiarCamposFormulario($local.$detalleSwDmpLog);
									$local.$detalleAutorizada.addClass("hidden");
									$local.$detalleSwDmpLog.addClass("hidden");
									$local.$detalleCompensacion.removeClass("hidden");
									$funcionUtil.llenarFormulario(detalleCompensacion[0], $local.$detalleCliente);
									$funcionUtil.llenarFormulario(detalleCompensacion[0], $local.$detalleCompensacion);
								} else {
									$local.$modalDetalleConsulta.PopupWindow("close");
									$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros N\u00FAmero de Tarjeta: " + consolidadaSgte.numeroTarjeta + " ,fechaTransmisi\u00F3n: " + consolidadaSgte.fechaTransaccion + " ,n\u00FAmeroTrace: " + consolidadaSgte.numeroTrace + " retorn\u00F3 m\u00E1s de un resultado", "fa-exclamation-circle", "Mensaje", "danger");
								}
							}
						}
					});
					$local.tablaConsolidada.row(currentIndex).deselect();
					$local.$filaSeleccionada = $local.tablaConsolidada.row(indexes[currentPosition + 1]).select();
					break;
				case "5":
					$local.$modalDetalleConsulta.PopupWindow("close");
					$funcionUtil.notificarException("Las Transacciones Liberadas no tienen Detalle", "fa-exclamation-circle", "Informaci\u00F3n", "info");
					break;
			}
		}
	});
});