$(document).ready(function() {

	var $local = {
		$tablaMantenimiento : $("#tablaConsulta"),
		tablaMantenimiento : "",
		$filaSeleccionada : "",
		codigo_membresiaSeleccionado : "",
		$membresias : $("#membresias"),
		$membresiasFiltroParaTableMantenimiento : $("#membresias-filtroParaTablaMantenimiento"),
		filtrosSeleccionables : [],
		$tipoBusqueda : $("input[type=radio][name=tipoBusqueda]"),
		$tipoDocumento : $("#tipoDocumento"),
		$filtrosTxns : $("#filtrosTxns"),
		$btnBuscarPorDocumentoCliente : $("#btnBuscarPorDocumentoCliente"),
		$selectTipoDocumento : $("#selectTipoDocumento"),
		$txtNumDocumentoCliente : $("#txtNumDocumentoCliente"),
		$canalFiltroParaTablaConsulta : $("#canalFiltroParaTablaConsulta"),
		$codigoProcesoFiltroParaTablaConsulta : $("#codigoProcesoFiltroParaTablaConsulta"),
		$buscarCriterios : $("#buscarCriterios"),
		$rangoFechasTransaccion : $("#rangoFechasTransaccion"),
		$criterios : $("#criterios"),
		$btnVerDetalle : $(".verDetalle"),
		$modalDetalleConsulta : $("#modalDetalleConsulta"),
		$detalleAutorizacion : $("#detalleAutorizacion"),
		$modalDetalleComisionAutorizada : $("#modalDetalleComisionAutorizada"),
		$detalleCliente : $("#detalleCliente"),
		$tablaDetalleComision : $("#tablaDetalleComisionAutorizada"),
		$btnPrimero : $(".btnPrimero"),
		$btnSiguiente : $(".btnSiguiente"),
		$btnAnterior : $(".btnAnterior"),
		$btnUltimo : $(".btnUltimo")
	};

	$funcionUtil.crearSelect2($local.$selectTipoDocumento, "Seleccion un Tipo de Documento");

	$formBusquedaTipoDocumento = $("#formParamIniciales");
	$formBusquedaCriterios = $("#formBusquedaCriterios");

	$local.$modalDetalleConsulta.PopupWindow({
		title : "Detalle de Autorizaciones",
		autoOpen : false,
		modal : false,
		height : 400,
		width : 600,
	});

	$local.$modalDetalleComisionAutorizada.PopupWindow({
		title : "Detalle Comisiones",
		autoOpen : false,
		modal : false,
		height : 400,
		width : 1100,
	});

	$funcionUtil.crearSelect2($local.$canalFiltroParaTablaConsulta);
	$funcionUtil.crearSelect2($local.$codigoProcesoFiltroParaTablaConsulta);
	$funcionUtil.crearDateRangePickerConsulta($local.$rangoFechasTransaccion);
	$formMantenimiento = $("#formMantenimiento");

	$.fn.dataTable.ext.errMode = 'none';

	$local.$tablaMantenimiento.on('xhr.dt', function(e, settings, json, xhr) {
		switch (xhr.status) {
		case 500:
			$local.tablaMantenimiento.clear().draw();
			$funcionUtil.notificarException(xhr.responseText, "Error Interno", "danger");
			break;
		}
	});

	$local.tablaMantenimiento = $local.$tablaMantenimiento.DataTable({
		"language" : {
			"emptyTable" : "No se han encontrado Transacciones Autorizadas con los criterios definidos."
		},
		"initComplete" : function() {
			$local.$tablaMantenimiento.wrap("<div class='table-responsive'></div>");
			$local.filtrosSeleccionables["0"] = $local.$membresiasFiltroParaTableMantenimiento.html();
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaMantenimiento, $local.filtrosSeleccionables);
		},
		"columnDefs" : [ {
			"targets" : [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 ],
			"className" : "all filtrable",
		}, {
			"targets" : 15,
			"className" : "all dt-center",
			"width" : "10%",
			"defaultContent" : $variableUtil.botonVerDetalle + " " + $variableUtil.botonVerComision
		} ],
		"columns" : [ {
			"data" : 'numeroCuenta',
			"title" : "N° Tarjeta"
		}, {
			"data" : 'descripcionRol',
			"title" : "Rol"
		}, {
			"data" : 'descripcionCanal',
			"title" : "Canal"
		}, {
			"data" : 'descripcionProceso',
			"title" : "Proceso"
		}, {
			"data" : 'identificadorCuenta',
			"title" : "Cuenta"
		}, {
			"data" : 'fechaTransmision',
			"title" : "Fecha Txn"
		}, {
			"data" : 'horaTransmision',
			"title" : "Hora Txn"
		}, {
			"data" : 'codigoAutorizacion',
			"title" : "Autorizaci\u00F3n"
		}, {
			"data" : 'numeroRastreo',
			"title" : "Trace"
		}, {
			"data" : 'descripcionRespuesta',
			"title" : "Respuesta"
		}, {
			"data" : 'transaccionMonetaria',
			"title" : "Moneda"
		}, {
			"data" : 'cantidadTransaccion',
			"title" : "Monto"
		}, {
			"data" : 'ubicacionTarjeta',
			"title" : "Descripcion Adquirente"
		}, {
			"data" : 'estadoTarjeta',
			"title" : "Estado Tarjeta"
		}, {
			"data" : 'numeroDocumentoAutorizacion',
			"title" : "N\u00FAmero Documento"
		}, {
			"data" : null,
			"title" : 'Ver detalle'
		} ],
		"createdRow" : function(row, data, dataIndex) {
			if (data.estadoTarjeta == "ACTIVA") {
				$(row).css("background-color", "Green");
				$(row).addClass("success");
			} else {
				$(row).css("background-color", "Red");
				$(row).addClass("danger");
			}
		}
	});

	$local.tablaDetalleComision = $local.$tablaDetalleComision.DataTable({
		"ajax" : {
			"url" : "",
			"dataSrc" : ""
		},
		"language" : {
			"emptyTable" : "No hay Comisiones registradas"
		},
		"columns" : [ {
			"data" : 'numeroTarjeta',
			"title" : 'N\u00FAmero Tarjeta'
		}, {
			"data" : 'fechaTransaccion',
			"title" : 'Fecha Transacci\u00F3n'
		}, {
			"data" : 'codigoProcesamiento',
			"title" : 'C\u00F3digo Procesamiento'
		}, {
			"data" : 'numeroTrace',
			"title" : 'N\u00FAmero Trace'
		}, {
			"data" : 'monedaComision',
			"title" : 'Moneda Comisi\u00F3n'
		}, {
			"data" : 'valorComision',
			"title" : 'Valor Comisi\u00F3n'
		}, {
			"data" : 'tipoPago',
			"title" : 'Tipo de Pago'
		} ]
	});

	$local.$tablaMantenimiento.children("tbody").on("click", ".ver-comisiones", function() {
		$local.$modalDetalleComisionAutorizada.PopupWindow("open");
		$local.$filaSeleccionada = $(this).parents("tr");
		var autorizacion = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		$local.tablaDetalleComision.ajax.url($variableUtil.root + "txnsAutorizacion?accion=buscarComision&numeroDocumento=" + autorizacion.numeroDocumentoAutorizacion).load();
	});

	$local.$tablaMantenimiento.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaMantenimiento.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	$local.$tablaMantenimiento.children("tbody").on("click", ".ver-detalle", function() {
		$local.$btnPrimero.removeClass("hidden");
		$local.$btnSiguiente.removeClass("hidden");
		$local.$btnAnterior.removeClass("hidden");
		$local.$btnUltimo.removeClass("hidden");
		$local.$filaSeleccionada = $(this).parents("tr");
		var autorizacion = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		var fechaTransmision = $funcionUtil.convertirDeFormatoAFormato(autorizacion.fechaTransmision, "DD/MM/YYYY", "YYYY-MM-DD");
		var data = {
			"numeroCuenta" : autorizacion.numeroCuenta,
			"fechaTransmision" : fechaTransmision,
			"numeroRastreo" : autorizacion.numeroRastreo,
			"tipoMensaje" : autorizacion.tipoMensaje
		}
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "txnsAutorizacion?accion=buscarDetalle",
			data : data,
			beforeSend : function(xhr) {
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			success : function(detalleAutorizacion) {
				if (detalleAutorizacion.length == 0) {
					$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros N\u00FAmero de Tarjeta: " + autorizacion.numeroCuenta + " ,fechaTransmisi\u00F3n: " + autorizacion.fechaTransmision + " ,n\u00FAmeroTrace: " + autorizacion.numeroRastreo + " ,tipoMensaje: " + autorizacion.tipoMensaje + " no encontr\u00F3 ninguna coincidencia en la Base de Datos.", "fa-exclamation-circle", "Informaci\u00F3n", "info");
				} else {
					if (detalleAutorizacion.length == 1) {
						$local.$modalDetalleConsulta.PopupWindow("open");
						$local.$modalDetalleConsulta.PopupWindow("maximize");
						$funcionUtil.llenarFormulario(detalleAutorizacion[0], $local.$detalleCliente);
						$funcionUtil.llenarFormulario(detalleAutorizacion[0], $local.$detalleAutorizacion);
						$funcionUtil.descripcionLarga(detalleAutorizacion[0].descripcionGiroNegocio);
					} else {
						$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros N\u00FAmero de Tarjeta: " + autorizacion.numeroCuenta + " ,fechaTransmisi\u00F3n: " + autorizacion.fechaTransmision + " ,n\u00FAmeroTrace: " + autorizacion.numeroRastreo + " ,tipoMensaje: " + autorizacion.tipoMensaje + " retorn\u00F3 m\u00E1s de un resultado", "fa-exclamation-circle", "Mensaje", "danger");
					}
				}
			}
		});
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
			type : "GET",
			url : $variableUtil.root + "txnsAutorizacion?accion=buscarPorTipoDocumento",
			data : criterioBusqueda,
			statusCode : {
				400 : function(response) {
					$funcionUtil.limpiarMensajesDeError($formBusquedaTipoDocumento);
					$funcionUtil.limpiarMensajesDeError($formBusquedaCriterios);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaCriterios);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaTipoDocumento);
				}
			},
			beforeSend : function() {
				$local.tablaMantenimiento.clear().draw();
				$local.$btnBuscarPorDocumentoCliente.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success : function(transaccionAutorizada) {
				if (transaccionAutorizada.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				$local.tablaMantenimiento.rows.add(transaccionAutorizada).draw();
			},
			complete : function() {
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
			var rangoFechaTxn = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechasTransaccion);
			criterioBusqueda.fechaInicioTransaccion = rangoFechaTxn.fechaInicio;
			criterioBusqueda.fechaFinTransaccion = rangoFechaTxn.fechaFin;
			$.ajax({
				type : "GET",
				url : $variableUtil.root + "txnsAutorizacion?accion=buscarPorFiltros",
				data : criterioBusqueda,
				statusCode : {
					400 : function(response) {
						$funcionUtil.limpiarMensajesDeError($formBusquedaTipoDocumento);
						$funcionUtil.limpiarMensajesDeError($formBusquedaCriterios);
						$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaCriterios);
						$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaTipoDocumento);
					}
				},
				beforeSend : function() {
					$local.tablaMantenimiento.clear().draw();
					$local.$buscarCriterios.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
				},
				success : function(transaccionAutorizacion) {
					if (transaccionAutorizacion.length == 0) {
						$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
						return;
					}
					$local.tablaMantenimiento.rows.add(transaccionAutorizacion).draw();
				},
				complete : function() {
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
			var autorizacionSgte = $local.tablaMantenimiento.row(indexes[currentPosition + 1]).data();
			var fechaTransmision = $funcionUtil.convertirDeFormatoAFormato(autorizacionSgte.fechaTransmision, "DD/MM/YYYY", "YYYY-MM-DD");
			var data = {
				"numeroCuenta" : autorizacionSgte.numeroCuenta,
				"fechaTransmision" : fechaTransmision,
				"numeroRastreo" : autorizacionSgte.numeroRastreo,
				"tipoMensaje" : autorizacionSgte.tipoMensaje
			};
			$.ajax({
				type : "GET",
				url : $variableUtil.root + "txnsAutorizacion?accion=buscarDetalle",
				data : data,
				beforeSend : function(xhr) {
					xhr.setRequestHeader('Content-Type', 'application/json');
					xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
				},
				success : function(detalleAutorizacion) {
					if (detalleAutorizacion.length == 0) {
						$local.$modalDetalleConsulta.PopupWindow("close");
						$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros N\u00FAmero de Tarjeta: " + autorizacionSgte.numeroCuenta + " ,fechaTransmisi\u00F3n: " + autorizacionSgte.fechaTransmision + " ,n\u00FAmeroTrace: " + autorizacionSgte.numeroRastreo + " ,tipoMensaje: " + autorizacionSgte.tipoMensaje + " no encontr\u00F3 ninguna coincidencia en la Base de Datos.", "fa-exclamation-circle", "Informaci\u00F3n", "info");
					} else {
						if (detalleAutorizacion.length == 1) {
							$local.$modalDetalleConsulta.PopupWindow("open");
							$local.$modalDetalleConsulta.PopupWindow("maximize");
							$funcionUtil.llenarFormulario(detalleAutorizacion[0], $local.$detalleCliente);
							$funcionUtil.llenarFormulario(detalleAutorizacion[0], $local.$detalleAutorizacion);
							$funcionUtil.descripcionLarga(detalleAutorizacion[0].descripcionGiroNegocio);
						} else {
							$local.$modalDetalleConsulta.PopupWindow("close");
							$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros N\u00FAmero de Tarjeta: " + autorizacionSgte.numeroCuenta + " ,fechaTransmisi\u00F3n: " + autorizacionSgte.fechaTransmision + " ,n\u00FAmeroTrace: " + autorizacionSgte.numeroRastreo + " ,tipoMensaje: " + autorizacionSgte.tipoMensaje + " retorn\u00F3 m\u00E1s de un resultado", "fa-exclamation-circle", "Mensaje", "danger");
						}
					}
				}
			});

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
			var autorizacionAnt = $local.tablaMantenimiento.row(indexes[currentPosition - 1]).data();
			var fechaTransmision = $funcionUtil.convertirDeFormatoAFormato(autorizacionAnt.fechaTransmision, "DD/MM/YYYY", "YYYY-MM-DD");
			var data = {
				"numeroCuenta" : autorizacionAnt.numeroCuenta,
				"fechaTransmision" : fechaTransmision,
				"numeroRastreo" : autorizacionAnt.numeroRastreo,
				"tipoMensaje" : autorizacionAnt.tipoMensaje
			}
			$.ajax({
				type : "GET",
				url : $variableUtil.root + "txnsAutorizacion?accion=buscarDetalle",
				data : data,
				beforeSend : function(xhr) {
					xhr.setRequestHeader('Content-Type', 'application/json');
					xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
				},
				success : function(detalleAutorizacion) {
					if (detalleAutorizacion.length == 0) {
						$local.$modalDetalleConsulta.PopupWindow("close");
						$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros N\u00FAmero de Tarjeta: " + autorizacionAnt.numeroCuenta + " ,fechaTransmisi\u00F3n: " + autorizacionAnt.fechaTransmision + " ,n\u00FAmeroTrace: " + autorizacionAnt.numeroRastreo + " ,tipoMensaje: " + autorizacionAnt.tipoMensaje + " no encontr\u00F3 ninguna coincidencia en la Base de Datos.", "fa-exclamation-circle", "Informaci\u00F3n", "info");
					} else {
						if (detalleAutorizacion.length == 1) {
							$local.$modalDetalleConsulta.PopupWindow("open");
							$local.$modalDetalleConsulta.PopupWindow("maximize");
							$funcionUtil.llenarFormulario(detalleAutorizacion[0], $local.$detalleCliente);
							$funcionUtil.llenarFormulario(detalleAutorizacion[0], $local.$detalleAutorizacion);
							$funcionUtil.descripcionLarga(detalleAutorizacion[0].descripcionGiroNegocio);
						} else {
							$local.$modalDetalleConsulta.PopupWindow("close");
							$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros N\u00FAmero de Tarjeta: " + autorizacionAnt.numeroCuenta + " ,fechaTransmisi\u00F3n: " + autorizacion.fechaTransmision + " ,n\u00FAmeroTrace: " + autorizacionAnt.numeroRastreo + " ,tipoMensaje: " + autorizacionAnt.tipoMensaje + " retorn\u00F3 m\u00E1s de un resultado", "fa-exclamation-circle", "Mensaje", "danger");
						}
					}
				}
			});
			$local.tablaMantenimiento.row(currentIndex).deselect();
			$local.$filaSeleccionada = $local.tablaMantenimiento.row(indexes[currentPosition - 1]).select();
		}
	});

	$local.$btnPrimero.on("click", function() {
		$local.$btnAnterior.addClass("hidden");
		$local.$btnSiguiente.removeClass("hidden");
		var indexes = $local.tablaMantenimiento.rows().indexes();
		var currentIndex = $local.tablaMantenimiento.row($local.$filaSeleccionada).index();
		var autorizacionPrimero = $local.tablaMantenimiento.row(indexes[0]).data();
		var fechaTransmision = $funcionUtil.convertirDeFormatoAFormato(autorizacionPrimero.fechaTransmision, "DD/MM/YYYY", "YYYY-MM-DD");
		var data = {
			"numeroCuenta" : autorizacionPrimero.numeroCuenta,
			"fechaTransmision" : fechaTransmision,
			"numeroRastreo" : autorizacionPrimero.numeroRastreo,
			"tipoMensaje" : autorizacionPrimero.tipoMensaje
		}
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "txnsAutorizacion?accion=buscarDetalle",
			data : data,
			beforeSend : function(xhr) {
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			success : function(detalleAutorizacion) {
				if (detalleAutorizacion.length == 0) {
					$local.$modalDetalleConsulta.PopupWindow("close");
					$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros N\u00FAmero de Tarjeta: " + autorizacionPrimero.numeroCuenta + " ,fechaTransmisi\u00F3n: " + autorizacionPrimero.fechaTransmision + " ,n\u00FAmeroTrace: " + autorizacionPrimero.numeroRastreo + " ,tipoMensaje: " + autorizacionPrimero.tipoMensaje + " no encontr\u00F3 ninguna coincidencia en la Base de Datos.", "fa-exclamation-circle", "Informaci\u00F3n", "info");
				} else {
					if (detalleAutorizacion.length == 1) {
						$local.$modalDetalleConsulta.PopupWindow("open");
						$local.$modalDetalleConsulta.PopupWindow("maximize");
						$funcionUtil.llenarFormulario(detalleAutorizacion[0], $local.$detalleCliente);
						$funcionUtil.llenarFormulario(detalleAutorizacion[0], $local.$detalleAutorizacion);
						$funcionUtil.descripcionLarga(detalleAutorizacion[0].descripcionGiroNegocio);
					} else {
						$local.$modalDetalleConsulta.PopupWindow("close");
						$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros N\u00FAmero de Tarjeta: " + autorizacionPrimero.numeroCuenta + " ,fechaTransmisi\u00F3n: " + autorizacion.fechaTransmision + " ,n\u00FAmeroTrace: " + autorizacionPrimero.numeroRastreo + " ,tipoMensaje: " + autorizacionPrimero.tipoMensaje + " retorn\u00F3 m\u00E1s de un resultado", "fa-exclamation-circle", "Mensaje", "danger");
					}
				}
			}
		});

		$local.tablaMantenimiento.row(currentIndex).deselect();
		$local.$filaSeleccionada = $local.tablaMantenimiento.row(indexes[0]).select();
	});

	$local.$btnUltimo.on("click", function() {
		$local.$btnSiguiente.addClass("hidden");
		$local.$btnAnterior.removeClass("hidden");
		var indexes = $local.tablaMantenimiento.rows().indexes();
		var currentIndex = $local.tablaMantenimiento.row($local.$filaSeleccionada).index();
		var autorizacionUltimo = $local.tablaMantenimiento.row(indexes[indexes.length - 1]).data();
		var fechaTransmision = $funcionUtil.convertirDeFormatoAFormato(autorizacionUltimo.fechaTransmision, "DD/MM/YYYY", "YYYY-MM-DD");
		var data = {
			"numeroCuenta" : autorizacionUltimo.numeroCuenta,
			"fechaTransmision" : fechaTransmision,
			"numeroRastreo" : autorizacionUltimo.numeroRastreo,
			"tipoMensaje" : autorizacionUltimo.tipoMensaje
		}
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "txnsAutorizacion?accion=buscarDetalle",
			data : data,
			beforeSend : function(xhr) {
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			success : function(detalleAutorizacion) {
				if (detalleAutorizacion.length == 0) {
					$local.$modalDetalleConsulta.PopupWindow("close");
					$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros n\u00FAmeroTarjeta: " + autorizacionUltimo.numeroCuenta + " ,fechaTransmisi\u00F3n: " + autorizacionUltimo.fechaTransmision + " ,n\u00FAmeroTrace: " + autorizacionUltimo.numeroRastreo + " ,tipoMensaje: " + autorizacionUltimo.tipoMensaje + " no encontr\u00F3 ninguna coincidencia en la Base de Datos.", "fa-exclamation-circle", "Informaci\u00F3n", "info");
				} else {
					if (detalleAutorizacion.length == 1) {
						$local.$modalDetalleConsulta.PopupWindow("open");
						$local.$modalDetalleConsulta.PopupWindow("maximize");
						$funcionUtil.llenarFormulario(detalleAutorizacion[0], $local.$detalleCliente);
						$funcionUtil.llenarFormulario(detalleAutorizacion[0], $local.$detalleAutorizacion);
						$funcionUtil.descripcionLarga(detalleAutorizacion[0].descripcionGiroNegocio);
					} else {
						$local.$modalDetalleConsulta.PopupWindow("close");
						$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros n\u00FAmeroTarjeta: " + autorizacionUltimo.numeroCuenta + " ,fechaTransmisi\u00F3n: " + autorizacion.fechaTransmision + " ,n\u00FAmeroTrace: " + autorizacionUltimo.numeroRastreo + " ,tipoMensaje: " + autorizacionUltimo.tipoMensaje + " retorn\u00F3 m\u00E1s de un resultado", "fa-exclamation-circle", "Mensaje", "danger");
					}
				}
			}
		});

		$local.tablaMantenimiento.row(currentIndex).deselect();
		$local.$filaSeleccionada = $local.tablaMantenimiento.row(indexes[indexes.length - 1]).select();
	})
});