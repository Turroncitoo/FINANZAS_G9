$(document).ready(function() {

	var $local = {
		$tablaMantenimiento: $("#tablaConsulta"),
		tablaMantenimiento: "",
		$filaSeleccionada: "",
		filtrosSeleccionables: [],
		$tipoBusqueda: $("input[type=radio][name=tipoBusqueda]"),
		$tipoDocumento: $("#tipoDocumento"),
		$btnBuscarPorDocumentoCliente: $("#btnBuscarPorDocumentoCliente"),
		$selectTipoDocumento: $("#selectTipoDocumento3"),
		$txtNumDocumentoCliente: $("#txtNumDocumentoCliente"),
		$canalFiltroParaTablaConsulta: $("#canalFiltroParaTablaConsulta"),
		$codigoProcesoFiltroParaTablaConsulta: $("#codigoProcesoFiltroParaTablaConsulta"),
		$buscarCriterios: $("#buscarCriterios"),
		$rangoFechasTransaccion: $("#rangoFechaMovimientos"),
		$criterios: $("#criterios"),
		$operaciones: $("#operaciones"),

		$modalDetalleOperacion: $("#modalDetalleOperacion"),
		$selectTipoDocumento2: $("#selectTipoDocumento2"),
		$asociar: $("#asociar"),
		$registrar: $("#registrar"),

		$btnAsociar: $("#btnAsociar"),
		$btnRegistrar: $("#btnRegistrar"),
		$fechaCumple: $("#fechaCumpleanios"),

	};

	$formBusquedaCriterios = $("#formBusquedaCriterios");
	$formBusquedaTipoDocumento = $("#formParamIniciales");
	$formBusquedaOperaciones = $("#formBusquedaOperaciones");

	$formAsociar = $("#form-asociar-tarjeta");
	$formRegistrar = $("#form-registrar-cliente");

	$funcionUtil.crearSelect2($local.$selectTipoDocumento, "Seleccione un Tipo de Documento");
	$funcionUtil.crearSelect2($local.$selectTipoDocumento2, "Seleccione un Tipo de Documento");
	$funcionUtil.crearDatePickerSimple($local.$fechaCumple, "YYYY-MM-DD");

	$.fn.dataTable.ext.errMode = 'none';

	$local.$modalDetalleOperacion.PopupWindow({
		title: "Operaci\u00F3n",
		autoOpen: false,
		modal: false,
		height: 500,
		width: 500,
	});

	$local.$modalDetalleOperacion.on("open.popupwindow", function() {
		$formAsociar.find("input:not([disabled]):first").focus();
		$formRegistrar.find("input:not([disabled]):first").focus();
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
		"ajax": {
			"url": $variableUtil.root + "consulta/administrativa/personaPP?accion=buscarTodos",
			"dataSrc": ""
		},
		"language": {
			"emptyTable": "No hay Personas registradas."
		},
		"initComplete": function() {
			$local.$tablaMantenimiento.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaMantenimiento);
		},
		"columnDefs": [{
			"targets": [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14],
			"className": "all filtrable",
			"defaultContent": "*"
		}],

		"columns": [{
			"data": "descripcionTipoDocumento",
			"title": "Tipo Doc."
		}, {
			"data": "numeroDocumento",
			"title": "Num. Doc."
		}, {
			"data": "nombres",
			"title": "Nombres"
		}, {
			"data": "apellidoPaterno",
			"title": "Ap. Paterno"
		}, {
			"data": "apellidoMaterno",
			"title": "Ap. Materno"
		}, {
			"data": "alias",
			"title": "Alias"
		}, {
			"data": "direccion",
			"title": "Direccion"
		}, {
			"data": "telefono",
			"title": "Tel\u00E9fono"
		}, {
			"data": "fechaNacimiento",
			"title": "Fecha Nacimiento"
		}, {
			"data": "telefonoFijo",
			"title": "Tel\u00E9fono Fijo"
		}, {
			"data": "telefonoMovil",
			"title": "Tel\u00E9fono M\u00F3vil"
		}, {
			"data": "codPulsera",
			"title": "C\u00F3digo Pulsera"
		}, {
			"data": "codigoUBA",
			"title": "C\u00F3digo UBA"
		}, {
			"data": "correoElectronico",
			"title": "Correo Electr\u00F3nico"
		}, {
			"data": "fechaRegistro",
			"title": "Fecha Registro"
		}]
	});

	$local.$tablaMantenimiento.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaMantenimiento.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
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
				if (consultaMovimientos.movimientos.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}

				$local.tablaMantenimiento.rows.add(consultaMovimientos.movimientos).draw();
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
				}
			});
		}
	});


	$local.$asociar.on("click", function() {
		$formAsociar.removeClass("hidden");
		$formRegistrar.addClass("hidden");
		$local.$modalDetalleOperacion.PopupWindow("open");
	});

	$local.$registrar.on("click", function() {
		$formAsociar.addClass("hidden");
		$formRegistrar.removeClass("hidden");
		$local.$modalDetalleOperacion.PopupWindow("open");

	});



	$local.$btnAsociar.on("click", function() {
		var criterioBusqueda = $formAsociar.serializeJSON();
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "prepago/wshub/asociarTarjeta",
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
				$local.$btnAsociar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success: function(respuesta) {
				if (respuesta == null) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				if (respuesta.id != null) {
					if (respuesta.id == "0") {
						$funcionUtil.notificarException("Se asoci\u00F3 con \u00E9xito.", "fa-exclamation-circle", "Informaci\u00F3n", "info");
					} else {
						$funcionUtil.notificarException("No se asoci\u00F3 con \u00E9xito. " + respuesta.id + "- " + respuesta.descripcion, "fa-exclamation-circle", "Informaci\u00F3n", "warning");

					}

				} else {
					$funcionUtil.notificarException("No se obtuvo respuesta del WS.", "fa-exclamation-circle", "Informaci\u00F3n", "warning");

				}
				$local.$modalDetalleOperacion.PopupWindow("close");
			},
			complete: function() {
				$local.$btnAsociar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});

	});

	$local.$btnRegistrar.on("click", function() {
		var formRegistrarCliente = $formRegistrar.serializeJSON();

		formRegistrarCliente.fechaCumpleanios = $local.$fechaCumple.data("daterangepicker").startDate.format('YYYY-MM-DD');

		$.ajax({
			type: "GET",
			url: $variableUtil.root + "prepago/wshub/registrarCliente",
			data: formRegistrarCliente,
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
				$local.$btnRegistrar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success: function(respuesta) {
				if (respuesta == null) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				if (respuesta.respuestaWS.id != null) {
					if (respuesta.respuestaWS.id === "0") {
						$funcionUtil.notificarException("Se registr\u00F3 con \u00E9xito.", "fa-exclamation-circle", "Informaci\u00F3n", "success");
					} else {
						$funcionUtil.notificarException("No se recarg\u00F3 con \u00E9xito. " + respuesta.id + "- " + respuesta.descripcion, "fa-exclamation-circle", "Informaci\u00F3n", "warning");

					}

				} else {
					$funcionUtil.notificarException("No se obtuvo respuesta del WS", "fa-exclamation-circle", "Informaci\u00F3n", "warning");

				}
				$local.$modalDetalleOperacion.PopupWindow("close");

			},
			complete: function() {
				$local.$btnRegistrar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});

	});

});