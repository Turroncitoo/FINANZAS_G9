$(window).on('load', function() {
	// when window is refreshed
	$("input[type=radio][name=tipoBusqueda][value=operaciones]").prop("checked", true);
});

$(document).ready(function() {

	var $local = {
		$tablaMantenimiento: $("#tablaConsulta"),
		tablaMantenimiento: "",
		$filaSeleccionada: "",
		filtrosSeleccionables: [],
		$tipoBusqueda: $("input[type=radio][name=tipoBusqueda]"),
		$tipoDocumento: $("#tipoDocumento"),
		$btnBuscarPorDocumentoCliente: $("#btnBuscarPorDocumentoCliente"),
		$selectTipoDocumento: $("#selectTipoDocumento"),
		$selectMotivo: $("#selectMotivo"),
		$txtNumDocumentoCliente: $("#txtNumDocumentoCliente"),
		$canalFiltroParaTablaConsulta: $("#canalFiltroParaTablaConsulta"),
		$codigoProcesoFiltroParaTablaConsulta: $("#codigoProcesoFiltroParaTablaConsulta"),
		$buscarCriterios: $("#buscarCriterios"),
		$rangoFechasTransaccion: $("#rangoFechaMovimientos"),
		$criterios: $("#criterios"),
		$operaciones: $("#operaciones"),

		$modalDetalleOperacion: $("#modalDetalleOperacion"),
		$modalAutenticacion: $("#modalAutenticacion"),

		$activar: $("#activar"),
		$recargar: $("#recargar"),
		$reasignar: $("#reasignar"),
		$bloquear: $("#bloquear"),
		$consultar: $("#consultar"),


		$btnReasignar: $("#btnReasignar"),
		$btnRecargar: $("#btnRecargar"),
		$btnActivar: $("#btnActivar"),
		$btnBloquear: $("#btnBloquear"),
		$btnAutenticar: $("#btnAutenticar"),

		botonSeleccionado: '',

		$tablaTarjetas: $("#tablaTarjetas"),
		tablaTarjetas: ""
	};

	var criterioBusqueda;

	$formBusquedaCriterios = $("#formBusquedaCriterios");
	$formBusquedaTipoDocumento = $("#formParamIniciales");
	$formBusquedaOperaciones = $("#formBusquedaOperaciones");

	$formActivar = $("#form-activar");
	$formRecargar = $("#form-recargar");
	$formReasignar = $("#form-reasignar");
	$formBloquear = $("#form-bloquear");
	$formConsultar = $("#form-consultar");
	$formAutenticacion = $("#form-autenticacion");

	$funcionUtil.crearSelect2($local.$selectTipoDocumento, "Seleccione un Tipo de Documento");
	$funcionUtil.crearSelect2($local.$selectMotivo, "Seleccione un Motivo");

	$.fn.dataTable.ext.errMode = 'none';
	$funcionUtil.crearSelect2($local.$canalFiltroParaTablaConsulta);
	$funcionUtil.crearSelect2($local.$codigoProcesoFiltroParaTablaConsulta);
	$funcionUtil.crearDateRangePickerConsulta($local.$rangoFechasTransaccion);


	$local.$modalDetalleOperacion.PopupWindow({
		title: "Operaci\u00F3n",
		autoOpen: false,
		modal: false,
		height: 450,
		width: 600,
	});

	$local.$modalAutenticacion.PopupWindow({
		title: "Autenticaci\u00F3n",
		autoOpen: false,
		modal: false,
		height: 210,
		width: 400,
	});

	$local.$modalDetalleOperacion.on("open.popupwindow", function() {
		$formActivar.find("input:not([disabled]):first").focus();
		$formRecargar.find("input:not([disabled]):first").focus();
		$formReasignar.find("input:not([disabled]):first").focus();
		$formBloquear.find("input:not([disabled]):first").focus();
	});

	$local.$modalAutenticacion.on("open.popupwindow", function() {
		$formAutenticacion.find("input:not([disabled]):first").focus();
	});


	$local.$tablaMantenimiento.on('xhr.dt', function(e, settings, json, xhr) {
		switch (xhr.status) {
			case 500:
				$local.tablaMantenimiento.clear().draw();
				$funcionUtil.notificarException(xhr.responseText, "Error Interno", "danger");
				break;
		}
	});

	$local.tablaMantenimiento = $local.$tablaMantenimiento.DataTable({
		"language": {
			"emptyTable": "No se han encontrado Movimientos con los criterios definidos.",
			"processing": "<b><span><i class='fa fa-spinner fa-pulse fa-fw'></i></span>&emsp;Procesando...</b>",
		},
		"initComplete": function() {
			$local.$tablaMantenimiento.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaMantenimiento);
		},
		"columnDefs": [{
			"targets": [0, 1, 2, 3, 4, 5, 6, 7, 8],
			"className": "all filtrable",
		}],
		"columns": [{
			"data": 'secuencia',
			"title": "Secuencia"
		}, {
			"data": 'tipo',
			"title": "Tipo"
		}, {
			"data": 'monto',
			"title": "Monto"
		}, {
			"data": 'costo',
			"title": "Costo"
		}, {
			"data": 'hora',
			"title": "Hora"
		}, {
			"data": 'fecha',
			"title": "Fecha"
		}, {
			"data": 'comercio',
			"title": "Comercio"
		}, {
			"data": 'codigoOperacion',
			"title": "C\u00F3digo de Operaci\u00F3n"
		}, {
			"data": 'tarjetaTruncada',
			"title": "Tarjeta truncada"
		}]
	});

	$local.$tablaMantenimiento.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaMantenimiento.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	$local.tablaTarjetas = $local.$tablaTarjetas.DataTable({
		"language": {
			"emptyTable": "No hay tarjetas registradas",
			"processing": "<b><span><i class='fa fa-spinner fa-pulse fa-fw'></i></span>&emsp;Procesando...</b>",
		},
		"initComplete": function() {
			$local.$tablaTarjetas.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaTarjetas);
		},
		"columnDefs": [{
			"targets": [0, 1, 2],
			"className": "all filtrable"
		}],
		"columns": [{
			"data": "numeroTarjetaTruncado",
			"title": "Nro. Tarjeta"
		}, {
			"data": "estado",
			"title": "Estado"
		}, {
			"data": "codigoSeguimiento",
			"title": "C\u00F3d. Seg."
		}]
	});

	$local.$tablaTarjetas.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaTarjetas.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tipoBusqueda.on("change", function() {
		var tipoBusqueda = $(this).val();
		switch (tipoBusqueda) {
			case "tipoDocumento":
				$local.$tipoDocumento.removeClass("hidden");
				$local.$criterios.addClass("hidden");
				$local.$operaciones.addClass("hidden");
				break;
			case "criterios":
				$local.$tipoDocumento.addClass("hidden");
				$local.$criterios.removeClass("hidden");
				$local.$operaciones.addClass("hidden");
				break;
			case "operaciones":
				$local.$tipoDocumento.addClass("hidden");
				$local.$criterios.addClass("hidden");
				$local.$operaciones.removeClass("hidden");
				break;
			default:
				$funcionUtil.notificarException("Seleccione un Tipo de B\u00FAsqueda v\u00E1lido", "fa-warning", "Aviso", "warning");
		}
	});

	$('.btnExportarSaldosMovimientos').on('click', function() {
		var id = $(this).attr('id');
		var consulta = id.substring(id.indexOf('-') + 1, id.length);
		var criterioBusqueda;
		switch (consulta) {
			case 'TipoDocumento':
				criterioBusqueda = $formBusquedaTipoDocumento.serializeJSON();
				criterioBusqueda.descripcionTipoDocumento = $local.$selectTipoDocumento.find('option:selected').text();
				criterioBusqueda.tipoBusqueda = "Por Tipo de Documento";
				break;
			case 'Criterios':
				criterioBusqueda = $formBusquedaCriterios.serializeJSON();
				var rangoFechaTxn = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechasTransaccion);
				criterioBusqueda.fechaInicioTransaccion = rangoFechaTxn.fechaInicio;
				criterioBusqueda.fechaFinTransaccion = rangoFechaTxn.fechaFin;
				criterioBusqueda.descripcionRangoFechas = $local.$rangoFechasTransaccion.val();
				criterioBusqueda.tipoBusqueda = "Por Criterios";
				break;
		}
		var paramCriterioBusqueda = $.param(criterioBusqueda);
		window.location.href = $variableUtil.root + "reporte/ws/saldosMovimientos?accion=exportarPor" + consulta + "&" + paramCriterioBusqueda;
	});

	$formBusquedaTipoDocumento.find("input").keypress(function(event) {
		if (!$formBusquedaTipoDocumento.valid()) {
			return;
		}
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
		obtenerSaldo();
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "prepago/wshub/consultaMovimientos?accion=buscarPorDocumento",
			data: criterioBusqueda,
			statusCode: {
				400: function(response) {
					$funcionUtil.limpiarMensajesDeError($formBusquedaTipoDocumento);
					$funcionUtil.limpiarMensajesDeError($formBusquedaCriterios);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaCriterios);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaTipoDocumento);
				},
				409: function(response) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
				}
			},
			beforeSend: function() {
				$local.tablaMantenimiento.clear().draw();
				$local.$btnBuscarPorDocumentoCliente.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success: function(consultaMovimientos) {
				if (consultaMovimientos.movimientos != undefined && consultaMovimientos.movimientos != null) {
					if (consultaMovimientos.movimientos.length == 0) {
						$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
						return;
					}

					$local.tablaMantenimiento.rows.add(consultaMovimientos.movimientos).draw();
				} else {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
				}
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
			var rangoFechaTxn = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechasTransaccion);
			criterioBusqueda.fechaInicioTransaccion = rangoFechaTxn.fechaInicio;
			criterioBusqueda.fechaFinTransaccion = rangoFechaTxn.fechaFin;
			obtenerSaldo(criterioBusqueda);
			obtenerMovimientos(criterioBusqueda);
		}
	});


	function obtenerMovimientos(criterioBusqueda) {
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "prepago/wshub/consultaMovimientos?accion=buscarPorFiltro",
			data: criterioBusqueda,
			statusCode: {
				400: function(response) {
					$funcionUtil.limpiarMensajesDeError($formBusquedaTipoDocumento);
					$funcionUtil.limpiarMensajesDeError($formBusquedaCriterios);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaCriterios);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaTipoDocumento);
				},
				409: function(response) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
				}
			},
			beforeSend: function() {
				$local.tablaMantenimiento.clear().draw();
				$local.$buscarCriterios.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");

				$(".dataTables_processing").show();

			},
			success: function(consultaMovimientos) {
				if (consultaMovimientos == null) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}

				if (consultaMovimientos.movimientos != null) {
					$local.tablaMantenimiento.rows.add(consultaMovimientos.movimientos).draw();
				}
			},
			complete: function() {
				$local.$buscarCriterios.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
				$(".dataTables_processing").hide();
			}
		});


	}

	function obtenerSaldo(criterioBusqueda) {

		$.ajax({
			type: "GET",
			url: $variableUtil.root + "prepago/wshub/consultaSaldo?accion=buscarPorFiltro",
			data: criterioBusqueda,
			statusCode: {
				400: function(response) {
					$funcionUtil.limpiarMensajesDeError($formBusquedaTipoDocumento);
					$funcionUtil.limpiarMensajesDeError($formBusquedaCriterios);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaCriterios);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaTipoDocumento);
				},
				409: function(response) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
				}
			},
			success: function(consultaSaldos) {
				if (consultaSaldos != null) {
					if (consultaSaldos.moneda != null && consultaSaldos.monto != null) {
						$("#datoAdicional").text("Saldo a la fecha/ Moneda: " + consultaSaldos.moneda + " / Monto: " + consultaSaldos.monto)
					} else {
						$("#datoAdicional").text("Saldo no disponible. Verifique que la tarjeta no este bloqueada.");
					}
				}
			}
		});
	}



	$local.$activar.on("click", function() {
		if (!$formBusquedaOperaciones.valid()) {
			return;
		}
		if ($funcionUtil.camposVacios($formBusquedaOperaciones)) {
			$funcionUtil.notificarException($variableUtil.camposVacios, "fa-exclamation-circle", "Informaci\u00F3n", "info");
		} else {
			$local.botonSeleccionado = 'activar';
			$funcionUtil.limpiarCamposFormulario($formAutenticacion);
			$local.$modalAutenticacion.PopupWindow("open");
		}
	});

	$local.$recargar.on("click", function() {
		if (!$formBusquedaOperaciones.valid()) {
			return;
		}
		if ($funcionUtil.camposVacios($formBusquedaOperaciones)) {
			$funcionUtil.notificarException($variableUtil.camposVacios, "fa-exclamation-circle", "Informaci\u00F3n", "info");
		} else {
			$local.botonSeleccionado = 'recargar';
			$funcionUtil.limpiarCamposFormulario($formAutenticacion);
			$local.$modalAutenticacion.PopupWindow("open");
		}
	});

	$local.$reasignar.on("click", function() {
		if (!$formBusquedaOperaciones.valid()) {

			return;
		}
		if ($funcionUtil.camposVacios($formBusquedaOperaciones)) {
			$funcionUtil.notificarException($variableUtil.camposVacios, "fa-exclamation-circle", "Informaci\u00F3n", "info");
		} else {
			$local.botonSeleccionado = 'reasignar';
			$funcionUtil.limpiarCamposFormulario($formAutenticacion);
			$local.$modalAutenticacion.PopupWindow("open");
		}
	});


	$local.$bloquear.on("click", function() {
		if (!$formBusquedaOperaciones.valid()) {
			return;
		}
		if ($funcionUtil.camposVacios($formBusquedaOperaciones)) {
			$funcionUtil.notificarException($variableUtil.camposVacios, "fa-exclamation-circle", "Informaci\u00F3n", "info");
		} else {
			$local.botonSeleccionado = 'bloquear';
			$funcionUtil.limpiarCamposFormulario($formAutenticacion);
			$local.$modalAutenticacion.PopupWindow("open");
		}
	});

	function mostrarModalOperacion(botonSeleccionado) {
		switch (botonSeleccionado) {
			case 'activar':
				$formActivar.find("input[name='numeroTarjeta']").val($("#tarjetaCriterio").val());
				$formActivar.removeClass("hidden");
				$formRecargar.addClass("hidden");
				$formReasignar.addClass("hidden");
				$formBloquear.addClass("hidden");
				$formConsultar.addClass("hidden");
				$local.botonSeleccionado = '';
				$local.$modalDetalleOperacion.PopupWindow("open");
				break;
			case 'recargar':
				$formRecargar.find("input[name='numeroTarjeta']").val($("#tarjetaCriterio").val());
				$formActivar.addClass("hidden");
				$formRecargar.removeClass("hidden");
				$formReasignar.addClass("hidden");
				$formBloquear.addClass("hidden");
				$formConsultar.addClass("hidden");
				$local.botonSeleccionado = '';
				$local.$modalDetalleOperacion.PopupWindow("open");
				break;
			case 'reasignar':
				$formReasignar.find("input[name='numeroTarjetaAnterior']").val($("#tarjetaCriterio").val());
				$formActivar.addClass("hidden");
				$formRecargar.addClass("hidden");
				$formReasignar.removeClass("hidden");
				$formBloquear.addClass("hidden");
				$formConsultar.addClass("hidden");
				$local.botonSeleccionado = '';
				$local.$modalDetalleOperacion.PopupWindow("open");
				break;
			case 'bloquear':
				$formBloquear.find("input[name='numeroTarjetaAnterior']").val($("#tarjetaCriterio").val());
				$formActivar.addClass("hidden");
				$formRecargar.addClass("hidden");
				$formReasignar.addClass("hidden");
				$formBloquear.removeClass("hidden");
				$formConsultar.addClass("hidden");
				$local.botonSeleccionado = '';
				$local.$modalDetalleOperacion.PopupWindow("open");
				break;
		}
	}

	$local.$consultar.on("click", function() {
		if (!$formBusquedaOperaciones.valid()) {
			return;
		}
		if ($funcionUtil.camposVacios($formBusquedaOperaciones)) {
			$funcionUtil.notificarException($variableUtil.camposVacios, "fa-exclamation-circle", "Informaci\u00F3n", "info");
		} else {
			var numeroTarjeta = $("#tarjetaCriterio").val();
			var criterioBusqueda = {};
			criterioBusqueda.numeroTarjeta = numeroTarjeta;
			$.ajax({
				type: "GET",
				url: $variableUtil.root + "prepago/wshub/consultarClientePorTarjeta",
				data: criterioBusqueda,
				statusCode: {
					400: function(response) {
						$funcionUtil.limpiarMensajesDeError($formBusquedaTipoDocumento);
						$funcionUtil.limpiarMensajesDeError($formBusquedaCriterios);
						$funcionUtil.limpiarMensajesDeError($formBusquedaOperaciones);
						$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaCriterios);
						$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaTipoDocumento);
						$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaOperaciones);
					},
					409: function(response) {
						$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					}
				},
				beforeSend: function() {
					$local.$consultar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
					$local.tablaTarjetas.clear().draw();
				},
				success: function(respuesta) {
					if (respuesta == null) {
						$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
						return;
					}
					if (respuesta.id != null) {
						if (respuesta.id == "0") {
							if (respuesta.idCliente != null) {
								respuesta.nombres = (respuesta.nombre1 || '') + " " + (respuesta.nombre2 || '');
								respuesta.apellidos = (respuesta.apellidoPaterno || '') + " " + (respuesta.apellidoMaterno || '');
								respuesta.numeroTarjeta = numeroTarjeta;
								$funcionUtil.prepararFormularioActualizacion($formConsultar);
								$funcionUtil.llenarFormulario(respuesta, $formConsultar);
								$local.tablaTarjetas.rows.add(respuesta.tarjetas).draw();
								$formActivar.addClass("hidden");
								$formRecargar.addClass("hidden");
								$formReasignar.addClass("hidden");
								$formBloquear.addClass("hidden");
								$formConsultar.removeClass("hidden");
								$local.$modalDetalleOperacion.PopupWindow("open");
							}
						} else {
							$funcionUtil.notificarException("Se obtuvo la siguiente respuesta del WS >  " + respuesta.id + "- " + respuesta.descripcion, "fa-exclamation-circle", "Informaci\u00F3n", "warning");

						}

					} else {
						$funcionUtil.notificarException("No se obtuvo respuesta del WS.", "fa-exclamation-circle", "Informaci\u00F3n", "warning");

					}

				},
				complete: function() {
					$local.$consultar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
				}
			});
		}
	});

	$local.$btnAutenticar.on("click", function() {
		if (!$formAutenticacion.valid()) {
			return;
		}
		var data = $formAutenticacion.serializeJSON();

		$.ajax({
			type: "POST",
			url: $variableUtil.root + "seguridad/usuario/auth",
			data: JSON.stringify(data),
			statusCode: {
				400: function(response) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
				},
				409: function(response) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
				}
			},
			beforeSend: function(xhr) {
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
				$local.$btnAutenticar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success: function(autenticado) {
				if (autenticado === null) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				if (autenticado) {
					mostrarModalOperacion($local.botonSeleccionado);
				} else {
					$funcionUtil.notificarException("No puede realizar la operaci\u00F3n.", "fa-exclamation-triangle", "Autenticaci\u00F3n", "warning");
				}
				$local.$modalAutenticacion.PopupWindow("close");
			},
			complete: function() {
				$local.$btnAutenticar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});


	});

	$local.$btnReasignar.on("click", function() {
		criterioBusqueda = $formReasignar.serializeJSON();
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "prepago/wshub/reasignarTarjeta",
			data: criterioBusqueda,
			statusCode: {
				400: function(response) {
					$funcionUtil.limpiarMensajesDeError($formBusquedaTipoDocumento);
					$funcionUtil.limpiarMensajesDeError($formBusquedaCriterios);
					$funcionUtil.limpiarMensajesDeError($formBusquedaOperaciones);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaCriterios);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaTipoDocumento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaOperaciones);
				},
				409: function(response) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
				}
			},
			beforeSend: function() {
				$local.$btnReasignar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success: function(respuesta) {
				if (respuesta == null) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				if (respuesta.id != null) {
					if (respuesta.id == "0") {
						$funcionUtil.notificarException("Se reasign\u00F3 con \u00E9xito.", "fa-exclamation-circle", "Informaci\u00F3n", "info");
					} else {
						$funcionUtil.notificarException("No se reasign\u00F3 con \u00E9xito. " + respuesta.id + "- " + respuesta.descripcion, "fa-exclamation-circle", "Informaci\u00F3n", "warning");

					}

				} else {
					$funcionUtil.notificarException("No se obtuvo respuesta del WS.", "fa-exclamation-circle", "Informaci\u00F3n", "warning");

				}
				$local.$modalDetalleOperacion.PopupWindow("close");
			},
			complete: function() {
				$local.$btnReasignar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});

	});

	$local.$btnRecargar.on("click", function() {
		var formRecarga = $formRecargar.serializeJSON();

		$.ajax({
			type: "GET",
			url: $variableUtil.root + "prepago/wshub/recargarTarjeta",
			data: formRecarga,
			statusCode: {
				400: function(response) {
					$funcionUtil.limpiarMensajesDeError($formBusquedaTipoDocumento);
					$funcionUtil.limpiarMensajesDeError($formBusquedaCriterios);
					$funcionUtil.limpiarMensajesDeError($formBusquedaOperaciones);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaCriterios);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaTipoDocumento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaOperaciones);
				},
				409: function(response) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
				}
			},
			beforeSend: function() {
				$local.$btnRecargar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success: function(respuesta) {

				if (respuesta == null) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				if (respuesta.id != null) {
					if (respuesta.id == "0") {
						$funcionUtil.notificarException("Se recarg\u00F3 con \u00E9xito.", "fa-exclamation-circle", "Informaci\u00F3n", "info");
						obtenerSaldo(formRecarga);
						obtenerMovimientos(formRecarga);

					} else {
						$funcionUtil.notificarException("No se recarg\u00F3 con \u00E9xito. " + respuesta.id + "- " + respuesta.descripcion, "fa-exclamation-circle", "Informaci\u00F3n", "warning");

					}

				} else {
					$funcionUtil.notificarException("No se obtuvo respuesta del WS", "fa-exclamation-circle", "Informaci\u00F3n", "warning");

				}
				$local.$modalDetalleOperacion.PopupWindow("close");

			},
			complete: function() {
				$local.$btnRecargar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});

	});

	$local.$btnActivar.on("click", function() {
		var criterioBusqueda = $formActivar.serializeJSON();

		$.ajax({
			type: "GET",
			url: $variableUtil.root + "prepago/wshub/activarTarjeta",
			data: criterioBusqueda,
			statusCode: {
				400: function(response) {
					$funcionUtil.limpiarMensajesDeError($formBusquedaTipoDocumento);
					$funcionUtil.limpiarMensajesDeError($formBusquedaCriterios);
					$funcionUtil.limpiarMensajesDeError($formBusquedaOperaciones);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaCriterios);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaTipoDocumento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaOperaciones);

				},
				409: function(response) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
				}
			},
			beforeSend: function() {
				$local.$btnActivar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success: function(respuesta) {

				if (respuesta == null) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				//5339820100000019
				//5339820200005223
				if (respuesta.id != null) {
					if (respuesta.id == "0") {
						$funcionUtil.notificarException("Se activ\u00F3 con \u00E9xito.", "fa-exclamation-circle", "Informaci\u00F3n", "info");
					} else {
						$funcionUtil.notificarException("No se activ\u00F3 con \u00E9xito. " + respuesta.id + "- " + respuesta.descripcion, "fa-exclamation-circle", "Informaci\u00F3n", "info");

					}

				} else {
					$funcionUtil.notificarException("No se obtuvo respuesta del WS", "fa-exclamation-circle", "Informaci\u00F3n", "warning");

				}
				$local.$modalDetalleOperacion.PopupWindow("close");
			},
			complete: function() {
				$local.$btnActivar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});

	});

	$local.$btnBloquear.on("click", function() {
		var criterioBusqueda = $formBloquear.serializeJSON();

		$.ajax({
			type: "GET",
			url: $variableUtil.root + "prepago/wshub/bloquearTarjeta",
			data: criterioBusqueda,
			statusCode: {
				400: function(response) {
					$funcionUtil.limpiarMensajesDeError($formBusquedaTipoDocumento);
					$funcionUtil.limpiarMensajesDeError($formBusquedaCriterios);
					$funcionUtil.limpiarMensajesDeError($formBusquedaOperaciones);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaCriterios);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaTipoDocumento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaOperaciones);
				},
				409: function(response) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
				}
			},
			beforeSend: function() {
				$local.$btnBloquear.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success: function(respuesta) {

				if (respuesta.id != null) {
					if (respuesta.id == "0") {
						$funcionUtil.notificarException("Se bloque\u00F3 con \u00E9xito.", "fa-exclamation-circle", "Informaci\u00F3n", "info");
					} else {
						$funcionUtil.notificarException("No se bloque\u00F3 con \u00E9xito. " + respuesta.id + "- " + respuesta.descripcion, "fa-exclamation-circle", "Informaci\u00F3n", "info");

					}

				} else {
					$funcionUtil.notificarException("No se obtuvo respuesta del WS", "fa-exclamation-circle", "Informaci\u00F3n", "warning");

				}
				$local.$modalDetalleOperacion.PopupWindow("close");
			},
			complete: function() {
				$local.$btnBloquear.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});

	});

	$local.$
});