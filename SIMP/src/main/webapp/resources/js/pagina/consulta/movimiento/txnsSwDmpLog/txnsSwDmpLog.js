$(document).ready(function() {
	let $local = {
		$tablaMantenimiento: $("#tablaConsulta"),
		tablaMantenimiento: "",
		$filaSeleccionada: "",
		$tipoBusqueda: $("input[type=radio][name=tipoBusqueda]"),
		$tipoDocumento: $("#tipoDocumento"),
		$btnBuscarPorDocumentoCliente: $("#btnBuscarPorDocumentoCliente"),
		$selectTipoDocumento: $("#selectTipoDocumento"),
		$buscarCriterios: $("#buscarCriterios"),
		$criterios: $("#criterios"),
		$btnVerDetalle: $(".verDetalle"),
		$modalDetalleConsulta: $("#modalDetalleConsulta"),
		$detalleSwDmpLog: $("#detalleSwDmpLog"),
		$detalleCliente: $("#detalleCliente"),
		$btnPrimero: $(".btnPrimero"),
		$btnSiguiente: $(".btnSiguiente"),
		$btnAnterior: $(".btnAnterior"),
		$btnUltimo: $(".btnUltimo"),

		//Selects form criterios
		$selectInstitucionTxnsSwDmpLog: $("#selectInstitucionTxnsSwDmpLog"),
		$selectEmpresaTxnsSwDmpLog: $("#selectEmpresaTxnsSwDmpLog"),
		$selectClienteTxnsSwDmpLog: $("#selectClienteTxnsSwDmpLog"),
		$selectRolTransaccionTxnsSwDmpLog: $("#selectRolTransaccionTxnsSwDmpLog"),
		$selectCodigoProcesoTxnsSwDmpLog: $("#selectCodigoProcesoTxnsSwDmpLog"),
		$selectCodigoRespuestaTxnsSwDmpLog: $("#selectCodigoRespuestaTxnsSwDmpLog"),
		$selectCanalTxnsSwDmpLog: $("#selectCanalTxnsSwDmpLog"),

		//Rango Fechas
		$rangoFechasProceso: $("#fechaProcesoTxnsSwDmpLog"),
		$rangoFechasTransaccion: $("#fechaTransaccionTxnsSwDmpLog"),

		// Exportacion
		$exportarPorCriterios: $("#exportarPorCriterio"),
		$exportarPorTipoDocumento: $("#exportarPorTipoDocumento"),

		// Permiso
		permisoDetalle: false
	};

	//Desestimaci\u00f3n de funci\u00f3n de Exportaci\u00f3n Resumen
	$local.$exportarPorCriterios.hide()

	$formBusquedaCriterios = $("#formBusquedaCriterios");
	$formBusquedaTipoDocumento = $("#formParamIniciales");

	$funcionUtil.crearDateRangePickerConsulta($local.$rangoFechasProceso, "right");
	$funcionUtil.crearDateRangePickerSinValorPorDefecto($local.$rangoFechasTransaccion);

	//Crear selects
	$funcionUtil.crearSelect2($local.$selectTipoDocumento);
	$funcionUtil.crearSelect2($local.$selectInstitucionTxnsSwDmpLog);
	$funcionUtil.crearSelect2($local.$selectEmpresaTxnsSwDmpLog);
	$funcionUtil.crearSelect2Multiple($local.$selectClienteTxnsSwDmpLog, "-1", "TODOS");
	$funcionUtil.crearSelect2Multiple($local.$selectRolTransaccionTxnsSwDmpLog, "-1", "TODOS");
	$funcionUtil.crearSelect2Multiple($local.$selectCodigoProcesoTxnsSwDmpLog, "-1", "TODOS");
	$funcionUtil.crearSelect2Multiple($local.$selectCodigoRespuestaTxnsSwDmpLog, "-1", "TODOS");
	$funcionUtil.crearSelect2Multiple($local.$selectCanalTxnsSwDmpLog, "-1", "TODOS");

	let datatableInicializado = false;
	let buscando = false;

	$local.$selectEmpresaTxnsSwDmpLog.on("change", () => {
		const opcionSeleccionada = $local.$selectEmpresaTxnsSwDmpLog.val();
		if (!opcionSeleccionada) {
			$local.$selectClienteTxnsSwDmpLog.find("option").remove();
			return;
		}
		$.ajax({
			type: "GET",
			url: `${$variableUtil.root}cliente/empresa/${opcionSeleccionada}`,
			beforeSend: () => {
				$local.$selectClienteTxnsSwDmpLog.find("option").remove();
				$local.$selectClienteTxnsSwDmpLog.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Clientes...</span>")
			},
			success: (response) => {
				$.each(response, (i) => {
					$local.$selectClienteTxnsSwDmpLog.append($("<option/>").val(response[i].idCliente).text(response[i].idCliente + " - " + response[i].descripcion));
				});
			},
			complete: () => {
				$local.$selectClienteTxnsSwDmpLog.parent().find(".cargando").remove();
			}
		});
	});

	$("#divBotonesCriterios").append("<button id=\"exportarDetallado\" type=\"button\" class=\"btn btn-success\">\n" +
		"\t\t\t\t\t\t\t\t\t\t<i class=\"fa fa-download\"></i> Exportaci&oacuten Detallada\n" +
		"\t\t\t\t\t\t\t\t\t</button>");

	$local.$modalDetalleConsulta.PopupWindow({
		title: "Detalle de SwDmpLog",
		autoOpen: false,
		modal: false,
		height: 400,
		width: 600,
	});

	$.fn.dataTable.ext.errMode = 'none';

	function determinarAccion($tipoBusqueda) {
		const tipoBusqueda = $tipoBusqueda.filter('input:checked').eq(0).val();
		let accion = "";
		switch (tipoBusqueda) {
			case "tipoDocumento":
				accion = "buscarPorDocumento";
				break;
			case "criterios":
				accion = "buscarPorFiltro"
				break;
		}
		return accion;
	}

	function obtenerCriterioBusqueda($tipoBusqueda) {
		const tipoBusqueda = $tipoBusqueda.filter('input:checked').eq(0).val();
		let criterioBusqueda;
		switch (tipoBusqueda) {
			case "tipoDocumento":
				criterioBusqueda = $formBusquedaTipoDocumento.serializeJSON();
				criterioBusqueda.modo = "CONSULTA";
				break;
			case "criterios":
				const rangoFechasProceso = $funcionUtil.obtenerFechasDateRangePickerFormatDDMMYYY($local.$rangoFechasProceso);
				const rangoFechasTransaccion = $funcionUtil.obtenerFechasDateRangePickerFormatDDMMYYY($local.$rangoFechasTransaccion);
				criterioBusqueda = $formBusquedaCriterios.serializeJSON();
				criterioBusqueda.fechaProcesoInicio = rangoFechasProceso.fechaInicio;
				criterioBusqueda.fechaProcesoFin = rangoFechasProceso.fechaFin;
				if (rangoFechasTransaccion.fechaInicio !== "" && rangoFechasTransaccion.fechaFin !== "") {
					criterioBusqueda.fechaTransaccionInicio = rangoFechasTransaccion.fechaInicio;
					criterioBusqueda.fechaTransaccionFin = rangoFechasTransaccion.fechaFin;
				}
				criterioBusqueda.modo = "CONSULTA";
				criterioBusqueda.clientes = $local.$selectClienteTxnsSwDmpLog.val();
				criterioBusqueda.rolesTransaccion = $local.$selectRolTransaccionTxnsSwDmpLog.val();
				criterioBusqueda.codigosProceso = $local.$selectCodigoProcesoTxnsSwDmpLog.val();
				criterioBusqueda.codigosRespuesta = $local.$selectCodigoRespuestaTxnsSwDmpLog.val();
				criterioBusqueda.canales = $local.$selectCanalTxnsSwDmpLog.val();
				break;
		}
		return criterioBusqueda;
	}

	$local.$selectTipoDocumento.on("change", function() {
		const val = $(this).val();
		if (val === '0') {
			$formBusquedaTipoDocumento.find('input[name="numeroDocumento"]').attr('disabled', true);
		} else {
			$formBusquedaTipoDocumento.find('input[name="numeroDocumento"]').removeAttr('disabled');
		}
	});

	$local.permisoDetalle = $local.$tablaMantenimiento.attr("data-permiso-detalle") || false;

	$local.tablaMantenimiento = $local.$tablaMantenimiento.DataTable({
		"processing": true,
		"serverSide": true,
		"language": {
			"emptyTable": "No se han encontrado Transacciones SwDmpLog con los criterios definidos.",
			"processing": "<b><span><i class='fa fa-spinner fa-pulse fa-fw'></i></span>&emsp;Cargando...</b>",
		},
		"initComplete": function() {
			$local.$tablaMantenimiento.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaMantenimiento);
			datatableInicializado = true;
		},
		"columnDefs": [{
			"targets": [1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 23, 24, 25, 26, 27, 28, 30, 31, 32, 33, 34, 35],
			"className": "all filtrable",
		}, {
			"targets": [0, 10, 29, 30],
			"className": "all filtrable dt-center",
		}, {
			"targets": 22,
			"className": "all filtrable dt-right monto"
		}, {
			"targets": 36,
			"className": "all dt-center",
			"width": "10%",
			"render": function() {
				return "" + ($local.permisoDetalle ? $variableUtil.botonVerDetalle : "");
			}
		}],
		"columns": [{
			"data": "fechaProceso",
			"title": "Fecha Proceso"
		}, {
			"data": "codigoInstitucion",
			"render": (data, type, row) => {
				return $funcionUtil.unirCodigoDescripcion(row.codigoInstitucion, row.descripcionInstitucion);
			},
			"title": "Instituci\u00f3n"
		}, {
			"data": "idEmpresa",
			"render": (data, type, row) => {
				return $funcionUtil.unirCodigoDescripcion(row.idEmpresa, row.descEmpresa);
			},
			"title": "Empresa"
		}, {
			"data": "idCliente",
			"render": (data, type, row) => {
				return $funcionUtil.unirCodigoDescripcion(row.idCliente, row.descCliente);
			},
			"title": "Cliente"
		}, {
			"data": "idLogo",
			"render": (data, type, row) => {
				return `${row.descripcionLogoCompleto}` ;
			},
			"title": "Logo"
		}, {
			"data": "idProducto",
			"render": (data, type, row) => {
				return $funcionUtil.unirCodigoDescripcion(row.codigoProducto, row.descProducto);
			},
			"title": "Producto"
		}, {
			"data": "rolTransaccion",
			"render": (data, type, row) => {
				return $funcionUtil.unirCodigoDescripcion(row.rolTransaccion, row.descripcionRol);
			},
			"title": "Rol Transacci\u00f3n"
		}, {
			"data": "idProceso",
			"render": (data, type, row) => {
				return $funcionUtil.unirCodigoDescripcion(row.idProceso, row.descripcionProceso);
			},
			"title": "C\u00f3digo Proceso"
		}, {
			"data": "codigoRespuesta",
			"render": (data, type, row) => {
				return $funcionUtil.unirCodigoDescripcion(row.codigoRespuesta, row.descripcionRespuesta);
			},
			"title": "C\u00f3digo Respuesta"
		}, {
			"data": "idCanal",
			"render": (data, type, row) => {
				return $funcionUtil.unirCodigoDescripcion(row.idCanal, row.descripcionCanal);
			},
			"title": "Canal"
		}, {
			"data": "fechaTransmision",
			"render": (data, type, row) => {
				return $funcionUtil.unirCodigoDescripcion(row.fechaTransmisionCadena, row.horaTransmision);
			},
			"title": "Fecha Transacci\u00f3n"
		}, {
			"data": "tipoMensaje",
			"title": "Tipo Mensaje"
		}, {
			"data": "tipoDocumento",
			"render": (data, type, row) => {
				return $funcionUtil.unirCodigoDescripcion(row.tipoDocumento, row.descTipoDocumento);
			},
			"title": "Tipo Documento"
		}, {
			"data": "numeroDocumento",
			"title": "N\u00famero Documento"
		}, {
			"data": "nombres",
			"title": "Nombres"
		}, {
			"data": "apellidoPaterno",
			"title": "Apellido Paterno"
		}, {
			"data": "apellidoMaterno",
			"title": "Apellido Materno"
		}, {
			"data": "numeroTarjeta",
			"title": "N\u00famero Tarjeta"
		}, {
			"data": "codigoSeguimiento",
			"title": "C\u00f3digo Seguimiento"
		}, {
			"data": "identificadorCuenta",
			"title": "Cuenta"
		}, {
			"data": "estadoTarjeta",
			"title": "Estado Tarjeta"
		}, {
			"data": "moneda",
			"render": (data, type, row) => {
				return $funcionUtil.unirCodigoDescripcion(row.moneda, row.descMoneda);
			},
			"title": "Moneda Transacci\u00f3n"
		}, {
			"data": "importe",
			"render": $tablaFuncion.formatoMonto(),
			"title": "Importe Transacci\u00f3n"
		}, {
			"data": "giroNegocio",
			"render": (data, type, row) => {
				return row.descripcionGiroNegocioUnido;
			},
			"title": "Giro Negocio"
		}, {
			"data": "trace",
			"title": "Trace"
		}, {
			"data": "codigoAutorizacion",
			"title": "C\u00f3digo Autorizaci\u00f3n"
		}, {
			"data": "descripcionAdquirente",
			"title": "Descripci\u00f3n Adquirente"
		}, {
			"data": "institucionAdquirente",
			"render": (data, type, row) => {
				return $funcionUtil.unirCodigoDescripcion(row.institucionAdquirente, row.descripcionInstitucionAdquirente);
			},
			"title": "Instituci\u00f3n Adquirente"
		}, {
			"data": "institucionSolicitante",
			"render": (data, type, row) => {
				return $funcionUtil.unirCodigoDescripcion(row.institucionSolicitante, row.descripcionInstitucionSolicitante);
			},
			"title": "Instituci\u00f3n Respuesta"
		}, {
			"data": "fechaSwitch",
			"title": "Fecha SW"
		}, {
			"data": "fechaTransaccionLocal",
			"render": (data, type, row) => {
				return $funcionUtil.unirCodigoDescripcion(row.fechaTransaccionLocalCadena, row.horaTransaccionLocal);
			},
			"title": "Fecha Transacci\u00f3n Local"
		}, {
			"data": "panEntryMode",
			"render": (data, type, row) => {
				return $funcionUtil.unirCodigoDescripcion(row.panEntryMode, row.descripcionPANEntryMode);
			},
			"title": "Modo Ingreso PAN"
		}, {
			"data": "pinEntryCapability",
			"render": (data, type, row) => {
				return $funcionUtil.unirCodigoDescripcion(row.pinEntryCapability, row.descripcionPINEntryCapability);
			},
			"title": "Capacidad Ingreso PAN"
		}, {
			"data": "cuentaCargo",
			"title": "Cta. Cargo"
		}, {
			"data": "cuentaAbono",
			"title": "Cta. Abono"
		}, {
			"data": "codigoAnalitico",
			"title": "C\u00f3digo Anal\u00edtico"
		}, {
			"data": null,
			"title": 'Ver detalle'
		}],
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
			if (data.estadoTarjeta === "ACTIVED (ACTIVA)") {
				$(row).css("background-color", "Green");
				$(row).addClass("success");
			} else {
				$(row).css("background-color", "Red");
				$(row).addClass("danger");
			}
		},
		"order": [],
		"ajax": function(data, callback, settings) {
			if (!datatableInicializado) {
				callback({ recordsTotal: 0, recordsFiltered: 0, data: [] });
			} else {
				const accion = determinarAccion($local.$tipoBusqueda);
				data.criterioBusqueda = obtenerCriterioBusqueda($local.$tipoBusqueda);
				$.ajax({
					type: "POST",
					url: `${$variableUtil.root}txnsSwDmpLog?accion=${accion}`,
					data: JSON.stringify(data),
					statusCode: {
						400: function(response) {
							if (accion === 'buscarPorDocumento') {
								$funcionUtil.limpiarMensajesDeError($formBusquedaTipoDocumento);
								$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaTipoDocumento);
							} else if (accion === 'buscarPorFiltro') {
								$funcionUtil.limpiarMensajesDeError($formBusquedaCriterios);
								$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaCriterios);
							}
						}
					},
					beforeSend: function(xhr) {
						xhr.setRequestHeader('Content-Type', 'application/json');
						xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
						if (buscando) {
							$local.$buscarCriterios.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
							$local.$btnBuscarPorDocumentoCliente.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
						}
					},
					success: function(result) {
						if (result.data.length === 0) {
							$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00f3n", "info");
						}
						callback(result);
					},
					complete: function() {
						if (buscando) {
							$local.$buscarCriterios.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
							$local.$btnBuscarPorDocumentoCliente.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
						}
						buscando = false;
					}
				});
			}
		}
	});

	$local.tablaMantenimiento.buttons().container().appendTo($('.col-sm-6:eq(0)', $local.tablaMantenimiento.table().container()));

	$local.$tablaMantenimiento.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaMantenimiento.find("thead").on('change', 'select', function() {
		let val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	$local.$tipoBusqueda.on("change", function() {
		const tipoBusqueda = $(this).val();
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
				$funcionUtil.notificarException("Seleccione un Tipo de B\u00fasqueda v\u00e1lido", "fa-warning", "Aviso", "warning");
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
		buscando = true;
		$local.tablaMantenimiento.ajax.reload();
	});

	$local.$buscarCriterios.on("click", function() {
		if (!$formBusquedaCriterios.valid()) {
			return;
		}
		let criterioBusqueda = $formBusquedaCriterios.serializeJSON();
		if (criterioBusqueda.codigoSeguimiento.length == 0 && criterioBusqueda.numeroTarjeta.length == 0 && criterioBusqueda.nombreCompleto.length == 0) {
			if (criterioBusqueda.fechaProceso.length == 0) {
				$funcionUtil.notificarException($variableUtil.camposRequeridosSwDmpLog, "fa-exclamation-circle", "Informaci\u00F3n", "info");
				return;
			}
		}
		if ($funcionUtil.camposVacios($formBusquedaCriterios)) {
			$funcionUtil.notificarException($variableUtil.camposVacios, "fa-exclamation-circle", "Informaci\u00f3n", "info");
		} else {
			buscando = true;
			$local.tablaMantenimiento.ajax.reload();
		}
	});

	$local.$tablaMantenimiento.children("tbody").on("click", ".ver-detalle", function() {
		let $botonVerDetalle = $(this);
		$local.$btnPrimero.removeClass("hidden");
		$local.$btnSiguiente.removeClass("hidden");
		$local.$btnAnterior.removeClass("hidden");
		$local.$btnUltimo.removeClass("hidden");
		$local.$filaSeleccionada = $(this).parents("tr");
		let swDmpLog = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		let fechaTransmision = $funcionUtil.convertirDeFormatoAFormato(swDmpLog.fechaTransmision, "DD/MM/YYYY", "YYYY-MM-DD");
		let data = {
			"fechaTransmision": fechaTransmision,
			"numeroRastreo": swDmpLog.trace,
			"tipoMensaje": swDmpLog.tipoMensaje
		}
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "txnsSwDmpLog?accion=buscarDetalle",
			data: data,
			beforeSend: function(xhr) {
				$botonVerDetalle.attr("disabled", true).find("i").removeClass("fa-eye").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			success: function(detalleSwDmpLog) {
				if (detalleSwDmpLog.length === 0) {
					$funcionUtil.notificarException("La B\u00fasqueda con los par\u00e1metros n\u00fameroTarjeta: " + swDmpLog.numeroTarjeta + " ,fechaTransmisi\u00f3n: " +
						swDmpLog.fechaTransmision + " ,n\u00fameroTrace: " + swDmpLog.trace + " ,tipoMensaje: " + swDmpLog.tipoMensaje + " no encontr\u00f3 ninguna coincidencia en la Base de Datos.", "fa-exclamation-circle", "Informaci\u00f3n", "info");
				} else {
					if (detalleSwDmpLog.length === 1) {
						$local.$modalDetalleConsulta.PopupWindow("open");
						$local.$modalDetalleConsulta.PopupWindow("maximize");
						$funcionUtil.llenarFormulario(detalleSwDmpLog[0], $local.$detalleSwDmpLog);
						$funcionUtil.llenarFormulario(detalleSwDmpLog[0], $local.$detalleCliente);
						$funcionUtil.descripcionLarga(detalleSwDmpLog[0].descripcionGiroNegocio);
					} else {
						$funcionUtil.notificarException("La B\u00fasqueda con los par\u00e1metros n\u00fameroTarjeta: " + swDmpLog.numeroTarjeta + " ,fechaTransmisi\u00f3n: " +
							swDmpLog.fechaTransmision + " ,n\u00fameroTrace: " + swDmpLog.trace + " ,tipoMensaje: " + swDmpLog.tipoMensaje + " retorn\u00f3 m\u00e1s de un resultado", "fa-exclamation-circle", "Mensaje", "danger");
					}
				}
			},
			complete: function() {
				$botonVerDetalle.attr("disabled", false).find("i").addClass("fa-eye").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$btnSiguiente.on("click", function() {
		$local.$btnAnterior.removeClass("hidden");
		let indexes = $local.tablaMantenimiento.rows().indexes();
		let currentIndex = $local.tablaMantenimiento.row($local.$filaSeleccionada).index();
		let currentPosition = indexes.indexOf(currentIndex);
		if (currentPosition < indexes.length - 1) {
			let swDmpLogSgte = $local.tablaMantenimiento.row(indexes[currentPosition + 1]).data();
			let fechaTransmision = $funcionUtil.convertirDeFormatoAFormato(swDmpLogSgte.fechaTransmision, "DD/MM/YYYY", "YYYY-MM-DD");
			let data = {
				"fechaTransmision": fechaTransmision,
				"numeroRastreo": swDmpLogSgte.trace,
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
					if (detalleSwDmpLog.length === 0) {
						$local.$modalDetalleConsulta.PopupWindow("close");
						$funcionUtil.notificarException("La B\u00fasqueda con los par\u00e1metros n\u00fameroTarjeta: " + swDmpLogSgte.numeroTarjeta + " ,fechaTransmisi\u00f3n: " +
							swDmpLogSgte.fechaTransmision + " ,n\u00fameroTrace: " + swDmpLogSgte.trace + " ,tipoMensaje: " + swDmpLogSgte.tipoMensaje + " no encontr\u00f3 ninguna coincidencia en la Base de Datos.", "fa-exclamation-circle", "Informaci\u00f3n", "info");
					} else {
						if (detalleSwDmpLog.length === 1) {
							$local.$modalDetalleConsulta.PopupWindow("open");
							$local.$modalDetalleConsulta.PopupWindow("maximize");
							$funcionUtil.llenarFormularioDeLectura(detalleSwDmpLog[0], $local.$detalleSwDmpLog);
							$funcionUtil.llenarFormularioDeLectura(detalleSwDmpLog[0], $local.$detalleCliente);
						} else {
							$local.$modalDetalleConsulta.PopupWindow("close");
							$funcionUtil.notificarException("La B\u00fasqueda con los par\u00e1metros n\u00fameroTarjeta: " + swDmpLogSgte.numeroTarjeta + " ,fechaTransmisi\u00f3n: " +
								swDmpLogSgte.fechaTransmision + " ,n\u00fameroTrace: " + swDmpLogSgte.trace + " ,tipoMensaje: " + swDmpLogSgte.tipoMensaje + " retorn\u00f3 m\u00e1s de un resultado", "fa-exclamation-circle", "Mensaje", "danger");
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
		let indexes = $local.tablaMantenimiento.rows().indexes();
		let currentIndex = $local.tablaMantenimiento.row($local.$filaSeleccionada).index();
		let currentPosition = indexes.indexOf(currentIndex);
		if (currentPosition >= 0) {
			let swDmpLogAnt = $local.tablaMantenimiento.row(indexes[currentPosition - 1]).data();
			let fechaTransmision = $funcionUtil.convertirDeFormatoAFormato(swDmpLogAnt.fechaTransmision, "DD/MM/YYYY", "YYYY-MM-DD");
			let data = {
				"fechaTransmision": fechaTransmision,
				"numeroRastreo": swDmpLogAnt.trace,
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
					if (detalleSwDmpLog.length === 0) {
						$local.$modalDetalleConsulta.PopupWindow("close");
						$funcionUtil.notificarException("La B\u00fasqueda con los par\u00e1metros n\u00fameroTarjeta: " + swDmpLogAnt.numeroTarjeta + " ,fechaTransmisi\u00f3n: " +
							swDmpLogAnt.fechaTransmision + " ,n\u00fameroTrace: " + swDmpLogAnt.trace + " ,tipoMensaje: " + swDmpLogAnt.tipoMensaje + " no encontr\u00f3 ninguna coincidencia en la Base de Datos.", "fa-exclamation-circle", "Informaci\u00f3n", "info");
					} else {
						if (detalleSwDmpLog.length === 1) {
							$local.$modalDetalleConsulta.PopupWindow("open");
							$local.$modalDetalleConsulta.PopupWindow("maximize");
							$funcionUtil.llenarFormularioDeLectura(detalleSwDmpLog[0], $local.$detalleSwDmpLog);
							$funcionUtil.llenarFormularioDeLectura(detalleSwDmpLog[0], $local.$detalleCliente);
						} else {
							$local.$modalDetalleConsulta.PopupWindow("close");
							$funcionUtil.notificarException("La B\u00fasqueda con los par\u00e1metros n\u00fameroTarjeta: " + swDmpLogAnt.numeroTarjeta + " ,fechaTransmisi\u00f3n: " +
								swDmpLogAnt.fechaTransmision + " ,n\u00fameroTrace: " + swDmpLogAnt.trace + " ,tipoMensaje: " + swDmpLogAnt.tipoMensaje + " retorn\u00f3 m\u00e1s de un resultado", "fa-exclamation-circle", "Mensaje", "danger");
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
		let indexes = $local.tablaMantenimiento.rows().indexes();
		let currentIndex = $local.tablaMantenimiento.row($local.$filaSeleccionada).index();
		let swDmpLogPrimero = $local.tablaMantenimiento.row(indexes[0]).data();
		let fechaTransmision = $funcionUtil.convertirDeFormatoAFormato(swDmpLogPrimero.fechaTransmision, "DD/MM/YYYY", "YYYY-MM-DD");
		let data = {
			"fechaTransmision": fechaTransmision,
			"numeroRastreo": swDmpLogPrimero.trace,
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
				if (detalleSwDmpLog.length === 0) {
					$local.$modalDetalleConsulta.PopupWindow("close");
					$funcionUtil.notificarException("La B\u00fasqueda con los par\u00e1metros n\u00fameroTarjeta: " + swDmpLogPrimero.numeroTarjeta + " ,fechaTransmisi\u00f3n: " +
						swDmpLogPrimero.fechaTransmision + " ,n\u00fameroTrace: " + swDmpLogPrimero.trace + " ,tipoMensaje: " + swDmpLogPrimero.tipoMensaje + " no encontr\u00f3 ninguna coincidencia en la Base de Datos.", "fa-exclamation-circle", "Informaci\u00f3n", "info");
				} else {
					if (detalleSwDmpLog.length === 1) {
						$local.$modalDetalleConsulta.PopupWindow("open");
						$local.$modalDetalleConsulta.PopupWindow("maximize");
						$funcionUtil.llenarFormularioDeLectura(detalleSwDmpLog[0], $local.$detalleSwDmpLog);
						$funcionUtil.llenarFormularioDeLectura(detalleSwDmpLog[0], $local.$detalleCliente);
					} else {
						$local.$modalDetalleConsulta.PopupWindow("close");
						$funcionUtil.notificarException("La B\u00fasqueda con los par\u00e1metros n\u00fameroTarjeta: " + swDmpLogPrimero.numeroTarjeta + " ,fechaTransmisi\u00f3n: " +
							swDmpLogPrimero.fechaTransmision + " ,n\u00fameroTrace: " + swDmpLogPrimero.trace + " ,tipoMensaje: " + swDmpLogPrimero.tipoMensaje + " retorn\u00f3 m\u00e1s de un resultado", "fa-exclamation-circle", "Mensaje", "danger");
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
		let indexes = $local.tablaMantenimiento.rows().indexes();
		let currentIndex = $local.tablaMantenimiento.row($local.$filaSeleccionada).index();
		let swDmpLogUltimo = $local.tablaMantenimiento.row(indexes[indexes.length - 1]).data();
		let fechaTransmision = $funcionUtil.convertirDeFormatoAFormato(swDmpLogUltimo.fechaTransmision, "DD/MM/YYYY", "YYYY-MM-DD");
		let data = {
			"fechaTransmision": fechaTransmision,
			"numeroRastreo": swDmpLogUltimo.trace,
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
				if (detalleSwDmpLog.length === 0) {
					$local.$modalDetalleConsulta.PopupWindow("close");
					$funcionUtil.notificarException("La B\u00fasqueda con los par\u00e1metros n\u00fameroTarjeta: " + swDmpLogUltimo.numeroTarjeta + " ,fechaTransmisi\u00f3n: " +
						swDmpLogUltimo.fechaTransmision + " ,n\u00fameroTrace: " + swDmpLogUltimo.trace + " ,tipoMensaje: " + swDmpLogUltimo.tipoMensaje + " no encontr\u00f3 ninguna coincidencia en la Base de Datos.", "fa-exclamation-circle", "Informaci\u00f3n", "info");
				} else {
					if (detalleSwDmpLog.length === 1) {
						$local.$modalDetalleConsulta.PopupWindow("open");
						$local.$modalDetalleConsulta.PopupWindow("maximize");
						$funcionUtil.llenarFormularioDeLectura(detalleSwDmpLog[0], $local.$detalleSwDmpLog);
						$funcionUtil.llenarFormularioDeLectura(detalleSwDmpLog[0], $local.$detalleCliente);
					} else {
						$local.$modalDetalleConsulta.PopupWindow("close");
						$funcionUtil.notificarException("La B\u00fasqueda con los par\u00e1metros n\u00fameroTarjeta: " + swDmpLogUltimo.numeroTarjeta + " ,fechaTransmisi\u00f3n: " +
							swDmpLogUltimo.fechaTransmision + " ,n\u00fameroTrace: " + swDmpLogUltimo.trace + " ,tipoMensaje: " + swDmpLogUltimo.tipoMensaje + " retorn\u00f3 m\u00e1s de un resultado", "fa-exclamation-circle", "Mensaje", "danger");
					}
				}
			}
		})
		$local.tablaMantenimiento.row(currentIndex).deselect();
		$local.$filaSeleccionada = $local.tablaMantenimiento.row(indexes[indexes.length - 1]).select();
	});

	function parametrosExportacion() {
		if (!$formBusquedaCriterios.valid()) {
			return 'NOT_VALID';
		}
		let criterioBusqueda = $formBusquedaCriterios.serializeJSON();
		if (criterioBusqueda.codigoSeguimiento.length == 0 && criterioBusqueda.numeroTarjeta.length == 0 && criterioBusqueda.nombreCompleto.length == 0) {
			if (criterioBusqueda.fechaProceso.length == 0) {
				$funcionUtil.notificarException($variableUtil.camposRequeridosSwDmpLog, "fa-exclamation-circle", "Informaci\u00F3n", "info");
				return 'NOT_VALID';
			}
		}
		if ($funcionUtil.camposVacios($formBusquedaCriterios)) {
			$funcionUtil.notificarException($variableUtil.camposVacios, "fa-exclamation-circle", "Informaci\u00f3n", "info");
			return 'NOT_VALID';
		}
		let rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechasProceso);
		criterioBusqueda.fechaProcesoInicio = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaProcesoFin = rangoFechaBusqueda.fechaFin;

		let rangoFechasTransaccion = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechasTransaccion);
		criterioBusqueda.fechaTransaccionInicio = rangoFechasTransaccion.fechaInicio;
		criterioBusqueda.fechaTransaccionFin = rangoFechasTransaccion.fechaFin;

		criterioBusqueda.modo = "EXPORT";
		criterioBusqueda.descripcionRangoFechasProceso = $local.$rangoFechasProceso.val();
		criterioBusqueda.descripcionRangoFechasTransaccion = $local.$rangoFechasTransaccion.val();
		criterioBusqueda.descripcionInstitucion = $local.$selectInstitucionTxnsSwDmpLog.find("option:selected").text();
		criterioBusqueda.descripcionEmpresa = $local.$selectEmpresaTxnsSwDmpLog.find("option:selected").val() === "" ? "" : $local.$selectEmpresaTxnsSwDmpLog.find("option:selected").text();
		criterioBusqueda.descripcionCliente = !!$local.$selectClienteTxnsSwDmpLog.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$selectClienteTxnsSwDmpLog, "; ") : "TODOS";
		criterioBusqueda.descripcionRolTransaccion = !!$local.$selectRolTransaccionTxnsSwDmpLog.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$selectRolTransaccionTxnsSwDmpLog, "; ") : "TODOS";
		criterioBusqueda.descripcionCodigoProceso = !!$local.$selectCodigoProcesoTxnsSwDmpLog.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$selectCodigoProcesoTxnsSwDmpLog, "; ") : "TODOS";
		criterioBusqueda.descripcionCodigoRespuesta = !!$local.$selectCodigoRespuestaTxnsSwDmpLog.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$selectCodigoRespuestaTxnsSwDmpLog, "; ") : "TODOS";
		criterioBusqueda.descripcionCanal = !!$local.$selectCanalTxnsSwDmpLog.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$selectCanalTxnsSwDmpLog, "; ") : "TODOS";
		let paramCriterioBusqueda = $.param(criterioBusqueda);
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectClienteTxnsSwDmpLog, "clientes");
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectRolTransaccionTxnsSwDmpLog, "rolesTransaccion");
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectCodigoProcesoTxnsSwDmpLog, "codigosProceso");
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectCodigoRespuestaTxnsSwDmpLog, "codigosRespuesta");
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectCanalTxnsSwDmpLog, "canales");
		return paramCriterioBusqueda
	}

	//Desestimado
	$local.$exportarPorCriterios.on("click", function() {
		var params = parametrosExportacion();
		if (params == 'NOT_VALID') {
			return;
		}
		$funcionUtil.webSocketMethod('exportacionPOI');
		window.location.href = `${$variableUtil.root}prepago/consulta/txnsSwDmpLog?rutaEnSidebar=${$variableUtil.rutaEnSidebar}&accion=exportarPorCriterios&${params}`;
	});

	$("#exportarDetallado").on("click", function() {
		var params = parametrosExportacion();
		if (params == 'NOT_VALID') {
			return;
		}
		$funcionUtil.webSocketMethod('exportacionPOI');
		window.location.href = `${$variableUtil.root}exportacion/txnsSwDmpLog/detallado?rutaEnSidebar=${$variableUtil.rutaEnSidebar}&accion=buscarPorFiltro&${params}`;
	});

	$local.$exportarPorTipoDocumento.on("click", function() {
		if (!$formBusquedaTipoDocumento.valid()) {
			return;
		}
		let criterioBusqueda = $formBusquedaTipoDocumento.serializeJSON();
		criterioBusqueda.descripcionTipoDocumento = $local.$selectTipoDocumento.find("option:selected").val() === "" ? "" : $local.$selectTipoDocumento.find("option:selected").text();
		criterioBusqueda.modo = "EXPORT";
		const paramCriterioBusqueda = $.param(criterioBusqueda);
		$funcionUtil.webSocketMethod('exportacionPOI');
		window.location.href = `${$variableUtil.root}prepago/consulta/txnsSwDmpLog?rutaEnSidebar=${$variableUtil.rutaEnSidebar}&accion=exportarPorTipoDocumento&${paramCriterioBusqueda}`;
	});

});