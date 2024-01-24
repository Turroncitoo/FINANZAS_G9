$(document).ready(function() {
	let $local = {
		$tablaMantenimiento: $("#tablaConsulta"),
		tablaMantenimiento: "",
		$filaSeleccionada: "",
		filtrosSeleccionables: {},
		$tipoBusqueda: $("input[type=radio][name=tipoBusqueda]"),
		$tipoDocumento: $("#tipoDocumento"),
		$criterios: $("#criterios"),
		$btnBuscarPorDocumentoCliente: $("#btnBuscarPorDocumentoCliente"),
		$buscarCriterios: $("#buscarCriterios"),
		$modalDetalleConsulta: $("#modalDetalleConsulta"),
		$detalleCompensacion: $("#detalleCompensacion"),
		$modalDetalleComision: $("#modalDetalleComision"),
		$modalDetalleComisionEncabezado: $("#detalleComisionEncabezado"),
		$tablaDetalleComision: $("#tablaDetalleComision"),
		$detalleCliente: $("#detalleCliente"),
		$btnPrimero: $(".btnPrimero"),
		$btnSiguiente: $(".btnSiguiente"),
		$btnAnterior: $(".btnAnterior"),
		$btnUltimo: $(".btnUltimo"),

		//Selects form criterios
		$selectTipoDocumento: $("#selectTipoDocumento"),
		$selectInstitucionTxnsCompensacion: $("#selectInstitucionTxnsCompensacion"),
		$selectEmpresaTxnsCompensacion: $("#selectEmpresaTxnsCompensacion"),
		$selectClienteTxnsCompensacion: $("#selectClienteTxnsCompensacion"),
		$selectRolTransaccionTxnsCompensacion: $("#selectRolTransaccionTxnsCompensacion"),
		$selectMembresiaTxnsCompensacion: $("#selectMembresiaTxnsCompensacion"),
		$selectServicioTxnsCompensacion: $("#selectServicioTxnsCompensacion"),
		$selectOrigenTxnsCompensacion: $("#selectOrigenTxnsCompensacion"),
		$selectClaseTransaccionTxnsCompensacion: $("#selectClaseTransaccionTxnsCompensacion"),
		$selectCodigoTransaccionTxnsCompensacion: $("#selectCodigoTransaccionTxnsCompensacion"),
		$selectLogoTxnsCompensacion: $("#selectLogoTxnsCompensacion"),
		$selectProductosTxnsCompensacion: $("#selectProductoTxnsCompensacion"),
		$selectInstEmisorTxnsCompensacion: $("#selectInstEmisorTxnsCompensacion"),
		$selectInstReceptorTxnsCompensacion: $("#selectInstReceptorTxnsCompensacion"),
		$selectCodigoRespuestaTxnsCompensacion: $("#selectCodigoRespuestaTxnsCompensacion"),
		$selectCanalTxnsCompensacion: $("#selectCanalTxnsCompensacion"),
		$selectMonedaTransaccionTxnsCompensacion: $("#selectMonedaTransaccionTxnsCompensacion"),

		//Rango Fechas
		$rangoFechasProceso: $("#fechaProcesoTxnsCompensacion"),
		$rangoFechasTransaccion: $("#fechaTransaccionTxnsCompensacion"),

		//Exportacion
		$exportar: $("#exportarPorCriterio"),
		$exportarPorTipoDocumento: $('#exportarPorTipoDocumento'),

		//Filtro Si o No
		arregloSiNo: [1, 0],

		// Permiso
		permisoDetalle: false,
		permisoComision: false
	};

	$formBusquedaCriterios = $("#formBusquedaCriterios");
	$formBusquedaTipoDocumento = $("#formParamIniciales");

	$funcionUtil.crearDateRangePickerConsulta($local.$rangoFechasProceso, "right");
	$funcionUtil.crearDateRangePickerSinValorPorDefecto($local.$rangoFechasTransaccion);

	//Crear selects
	$funcionUtil.crearSelect2($local.$selectTipoDocumento);
	$funcionUtil.crearSelect2($local.$selectInstitucionTxnsCompensacion);
	$funcionUtil.crearSelect2($local.$selectEmpresaTxnsCompensacion);
	$funcionUtil.crearSelect2($local.$selectMembresiaTxnsCompensacion);
	$funcionUtil.crearSelect2($local.$selectClaseTransaccionTxnsCompensacion);
	$funcionUtil.crearSelect2($local.$selectLogoTxnsCompensacion);
	$funcionUtil.crearSelect2($local.$selectMonedaTransaccionTxnsCompensacion);
	$funcionUtil.crearSelect2Multiple($local.$selectClienteTxnsCompensacion, "-1", "TODOS");
	$funcionUtil.crearSelect2Multiple($local.$selectRolTransaccionTxnsCompensacion, "-1", "TODOS");
	$funcionUtil.crearSelect2Multiple($local.$selectServicioTxnsCompensacion, "-1", "TODOS");
	$funcionUtil.crearSelect2Multiple($local.$selectOrigenTxnsCompensacion, "-1", "TODOS");
	$funcionUtil.crearSelect2Multiple($local.$selectCodigoTransaccionTxnsCompensacion, "-1", "TODOS");
	$funcionUtil.crearSelect2Multiple($local.$selectProductosTxnsCompensacion, "-1", "TODOS");
	$funcionUtil.crearSelect2Multiple($local.$selectInstEmisorTxnsCompensacion, "-1", "TODOS");
	$funcionUtil.crearSelect2Multiple($local.$selectInstReceptorTxnsCompensacion, "-1", "TODOS");
	$funcionUtil.crearSelect2Multiple($local.$selectCodigoRespuestaTxnsCompensacion, "-1", "TODOS");
	$funcionUtil.crearSelect2Multiple($local.$selectCanalTxnsCompensacion, "-1", "TODOS");

	let datatableInicializado = false;
	let buscando = false;

	$local.$selectEmpresaTxnsCompensacion.on("change", () => {
		const opcionSeleccionada = $local.$selectEmpresaTxnsCompensacion.val();
		if (!opcionSeleccionada) {
			$local.$selectClienteTxnsCompensacion.find("option").remove();
			return;
		}
		$.ajax({
			type: "GET",
			url: `${$variableUtil.root}cliente/empresa/${opcionSeleccionada}`,
			beforeSend: () => {
				$local.$selectClienteTxnsCompensacion.find("option").remove();
				$local.$selectClienteTxnsCompensacion.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Clientes...</span>")
			},
			success: (response) => {
				$.each(response, (i) => {
					$local.$selectClienteTxnsCompensacion.append($("<option/>").val(response[i].idCliente).text(response[i].idCliente + " - " + response[i].descripcion));
				});
			},
			complete: () => {
				$local.$selectClienteTxnsCompensacion.parent().find(".cargando").remove();
			}
		});
	});

	$local.$selectMembresiaTxnsCompensacion.on("change", () => {
		const opcionSeleccionada = $local.$selectMembresiaTxnsCompensacion.val();
		if (!opcionSeleccionada) {
			$local.$selectServicioTxnsCompensacion.find("option").remove();
			return;
		}
		$.ajax({
			type: "GET",
			url: `${$variableUtil.root}claseServicio/membresia/${opcionSeleccionada}`,
			beforeSend: () => {
				$local.$selectServicioTxnsCompensacion.find("option").remove();
				$local.$selectServicioTxnsCompensacion.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Clases Servicio...</span>")
			},
			success: (response) => {
				$.each(response, (i) => {
					$local.$selectServicioTxnsCompensacion.append($("<option/>").val(response[i].codigoClaseServicio).text(response[i].codigoClaseServicio + " - " + response[i].descripcion));
				});
			},
			complete: () => {
				$local.$selectServicioTxnsCompensacion.parent().find(".cargando").remove();
			}
		});
	});

	$local.$selectClaseTransaccionTxnsCompensacion.on("change", () => {
		const opcionSeleccionada = $local.$selectClaseTransaccionTxnsCompensacion.val();
		if (!opcionSeleccionada) {
			$local.$selectCodigoTransaccionTxnsCompensacion.find("option").remove();
			return;
		}
		$.ajax({
			type: "GET",
			url: `${$variableUtil.root}codigoTransaccion/claseTransaccion/${opcionSeleccionada}`,
			beforeSend: () => {
				$local.$selectCodigoTransaccionTxnsCompensacion.find("option").remove();
				$local.$selectCodigoTransaccionTxnsCompensacion.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando C\u00f3digos Transacci\u00f3n...</span>")
			},
			success: (response) => {
				$.each(response, (i) => {
					$local.$selectCodigoTransaccionTxnsCompensacion.append($("<option/>").val(response[i].codigoTransaccion).text(response[i].codigoTransaccion + " - " + response[i].descripcion));
				});
			},
			complete: () => {
				$local.$selectCodigoTransaccionTxnsCompensacion.parent().find(".cargando").remove();
			}
		});
	});

	$local.$selectLogoTxnsCompensacion.on("change", () => {
		const opcionSeleccionada = $local.$selectLogoTxnsCompensacion.val();
		if (!opcionSeleccionada) {
			$local.$selectProductosTxnsCompensacion.find("option").remove();
			return;
		}
		$.ajax({
			type: "GET",
			url: `${$variableUtil.root}producto/logo/${opcionSeleccionada}`,
			beforeSend: () => {
				$local.$selectProductosTxnsCompensacion.find("option").remove();
				$local.$selectProductosTxnsCompensacion.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Productos...</span>")
			},
			success: (response) => {
				$.each(response, (i) => {
					$local.$selectProductosTxnsCompensacion.append($("<option/>").val(response[i].codigoProducto).text(response[i].codigoProducto + " - " + response[i].descProducto));
				});
			},
			complete: () => {
				$local.$selectProductosTxnsCompensacion.parent().find(".cargando").remove();
			}
		});
	});

	$local.$modalDetalleConsulta.PopupWindow({
		title: "Detalle de Compensaci\u00f3n",
		autoOpen: false,
		modal: false,
		height: 800,
		width: 1250,
	});

	$local.$modalDetalleComision.PopupWindow({
		title: "Detalle Comisiones",
		autoOpen: false,
		modal: false,
		height: 600,
		width: 1250,
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
				criterioBusqueda.clientes = $local.$selectClienteTxnsCompensacion.val();
				criterioBusqueda.rolesTransaccion = $local.$selectRolTransaccionTxnsCompensacion.val();
				criterioBusqueda.servicios = $local.$selectServicioTxnsCompensacion.val();
				criterioBusqueda.origenes = $local.$selectOrigenTxnsCompensacion.val();
				criterioBusqueda.codigosTransaccion = $local.$selectCodigoTransaccionTxnsCompensacion.val();
				criterioBusqueda.productos = $local.$selectProductosTxnsCompensacion.val();
				criterioBusqueda.institucionesEmisor = $local.$selectInstEmisorTxnsCompensacion.val();
				criterioBusqueda.institucionesReceptor = $local.$selectInstReceptorTxnsCompensacion.val();
				criterioBusqueda.codigosRespuesta = $local.$selectCodigoRespuestaTxnsCompensacion.val();
				criterioBusqueda.canales = $local.$selectCanalTxnsCompensacion.val();
				break;
		}
		return criterioBusqueda;
	}

	$local.permisoDetalle = $local.$tablaMantenimiento.attr("data-permiso-detalle") || false;
	$local.permisoComision = $local.$tablaMantenimiento.attr("data-permiso-comision") || false;

	$local.tablaMantenimiento = $local.$tablaMantenimiento.DataTable({
		"processing": true,
		"serverSide": true,
		"language": {
			"emptyTable": "No se han encontrado Transacciones Compensaci\u00f3n con los criterios definidos.",
			"processing": "<b><span><i class='fa fa-spinner fa-pulse fa-fw'></i></span>&emsp;Cargando...</b>",
		},
		"initComplete": function() {
			$local.$tablaMantenimiento.wrap("<div class='table-responsive'></div>");
			$local.filtrosSeleccionables["37"] = $local.arregloSiNo;
			$local.filtrosSeleccionables["38"] = $local.arregloSiNo;
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaMantenimiento, $local.filtrosSeleccionables);
			datatableInicializado = true;
		},
		"columnDefs": [{
			"targets": [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 24, 25, 26, 28, 30, 32, 35, 36],
			"className": "all filtrable",
		}, {
			"targets": [0, 23],
			"className": "all filtrable dt-center",
		}, {
			"targets": [27, 29, 31, 33, 34],
			"className": "all filtrable dt-right monto"
		}, {
			"targets": 37,
			"className": "all seleccionable data-no-definida dt-center",
			"render": (data, type, row) => {
				return $funcionUtil.insertarEtiquetaSiNo(row.compensaFondos);
			}
		}, {
			"targets": 38,
			"className": "all seleccionable data-no-definida dt-center",
			"render": (data, type, row) => {
				return $funcionUtil.insertarEtiquetaSiNo(row.compensaComisiones);
			}
		}, {
			"targets": 39,
			"className": "all dt-center",
			"width": "10%",
			"orderable": false,
			"render": function() {
				return "" + ($local.permisoDetalle == "true" ? $variableUtil.botonVerDetalle : "") + " " + ($local.permisoComision == "true" ? $variableUtil.botonVerComision : "");
			}
		}],
		"columns": [{
			"data": "fechaProceso",
			"title": "Fecha Proceso"
		}, {
			"data": 'codigoInstitucion',
			"render": (data, type, row) => {
				return $funcionUtil.unirCodigoDescripcion(row.codigoInstitucion, row.descripcionInstitucion);
			},
			"title": "Instituci\u00f3n"
		}, {
			"data": 'idEmpresa',
			"render": (data, type, row) => {
				return $funcionUtil.unirCodigoDescripcion(row.idEmpresa, row.descEmpresa);
			},
			"title": "Empresa"
		}, {
			"data": 'idCliente',
			"render": (data, type, row) => {
				return $funcionUtil.unirCodigoDescripcion(row.idCliente, row.descCliente);
			},
			"title": "Cliente"
		}, {
			"data": 'idLogo',
			"render": (data, type, row) => {
				return row.descripcionLogoCompleto
			},
			"title": "Logo"
		}, {
			"data": 'idProducto',
			"render": (data, type, row) => {
				return $funcionUtil.unirCodigoDescripcion(row.codigoProducto, row.descProducto);
			},
			"title": "Producto"
		}, {
			"data": 'codigoMembresia',
			"render": (data, type, row) => {
				return $funcionUtil.unirCodigoDescripcion(row.codigoMembresia, row.descMembresia);
			},
			"title": "Membres\u00eda"
		}, {
			"data": 'codigoClaseServicio',
			"render": (data, type, row) => {
				return $funcionUtil.unirCodigoDescripcion(row.codigoClaseServicio, row.descClaseServicio);
			},
			"title": "Servicio"
		}, {
			"data": 'codigoOrigen',
			"render": (data, type, row) => {
				return $funcionUtil.unirCodigoDescripcion(row.codigoOrigen, row.descOrigen);
			},
			"title": "Origen"
		}, {
			"data": 'idClaseTransaccion',
			"render": (data, type, row) => {
				return $funcionUtil.unirCodigoDescripcion(row.idClaseTransaccion, row.descripcionClaseTransaccion);
			},
			"title": "Clase Transacci\u00f3n"
		}, {
			"data": 'idCodigoTransaccion',
			"render": (data, type, row) => {
				return $funcionUtil.unirCodigoDescripcion(row.idCodigoTransaccion, row.descripcionCodigoTransaccion);
			},
			"title": "C\u00f3digo Transacci\u00f3n"
		}, {
			"data": 'rolTransaccion',
			"render": (data, type, row) => {
				return $funcionUtil.unirCodigoDescripcion(row.rolTransaccion, row.descripcionRol);
			},
			"title": "Rol Transacci\u00f3n"
		}, {
			"data": 'codigoInstitucionEmisor',
			"render": (data, type, row) => {
				return $funcionUtil.unirCodigoDescripcion(row.codigoInstitucionEmisor, row.descripcionInstitucionEmisor);
			},
			"title": "Inst. Emisor"
		}, {
			"data": 'codigoInstitucionReceptor',
			"render": (data, type, row) => {
				return $funcionUtil.unirCodigoDescripcion(row.codigoInstitucionReceptor, row.descripcionInstitucionReceptor);
			},
			"title": "Inst. Receptor"
		}, {
			"data": 'codigoRespuestaSwitch',
			"render": (data, type, row) => {
				return $funcionUtil.unirCodigoDescripcion(row.codigoRespuestaSwitch, row.descripcionCodigoRespuesta);
			},
			"title": "C\u00f3digo Respuesta"
		}, {
			"data": 'idCanal',
			"render": (data, type, row) => {
				return $funcionUtil.unirCodigoDescripcion(row.idCanal, row.descripcionCanal);
			},
			"title": "Canal"
		}, {
			"data": 'tipoDocumento',
			"render": (data, type, row) => {
				return $funcionUtil.unirCodigoDescripcion(row.tipoDocumento, row.descTipoDocumento);
			},
			"title": "Tipo Documento"
		}, {
			"data": 'numeroDocumento',
			"title": "N\u00famero Documento"
		}, {
			"data": 'secuenciaTransaccion',
			"title": "Secuencia"
		}, {
			"data": 'codigoSeguimiento',
			"title": "C\u00f3digo Seguimiento"
		}, {
			"data": 'numeroTarjeta',
			"title": "N\u00famero Tarjeta"
		}, {
			"data": 'numeroCuenta',
			"title": "N\u00famero Cuenta"
		}, {
			"data": 'estadoTarjeta',
			"title": "Estado Tarjeta"
		}, {
			"data": 'fechaTransaccion',
			"render": (data, type, row) => {
				return $funcionUtil.unirCodigoDescripcion(row.fechaTransaccionCadena, row.horaTransaccion);
			},
			"title": "Fecha Transacci\u00f3n"
		}, {
			"data": 'numeroVoucher',
			"title": "Trace"
		}, {
			"data": 'codigoAutorizacion',
			"title": "C\u00f3digo Autorizaci\u00f3n"
		}, {
			"data": 'monedaTransaccion',
			"render": (data, type, row) => {
				return $funcionUtil.unirCodigoDescripcion(row.monedaTransaccion, row.descMonedaTransaccion);
			},
			"title": "Moneda Transacci\u00f3n"
		}, {
			"data": 'valorTransaccion',
			"render": $tablaFuncion.formatoMonto(),
			"title": "Importe Transacci\u00f3n"
		}, {
			"data": 'monedaAutorizacion',
			"render": (data, type, row) => {
				return $funcionUtil.unirCodigoDescripcion(row.monedaAutorizacion, row.descMonedaAutorizacion);
			},
			"title": "Moneda Autorizaci\u00f3n"
		}, {
			"data": 'valorAutorizacion',
			"render": $tablaFuncion.formatoMonto(),
			"title": "Importe Autorizaci\u00f3n"
		}, {
			"data": 'monedaCompensacion',
			"render": (data, type, row) => {
				return $funcionUtil.unirCodigoDescripcion(row.monedaCompensacion, row.descMonedaCompensacion);
			},
			"title": "Moneda Compensaci\u00f3n"
		}, {
			"data": 'valorCompensacion',
			"render": $tablaFuncion.formatoMonto(),
			"title": "Importe Compensaci\u00f3n"
		}, {
			"data": 'monedaCargadaThb',
			"render": (data, type, row) => {
				return $funcionUtil.unirCodigoDescripcion(row.monedaCargadaThb, row.descripcionCodigoMonedaCargadaThb);
			},
			"title": "Moneda THB"
		}, {
			"data": 'valorCargadoThb',
			"render": $tablaFuncion.formatoMonto(),
			"title": "Importe THB"
		}, {
			"data": 'tipoDeCambio',
			"render": $tablaFuncion.formatoMonto(4),
			"title": "Tipo Cambio"
		}, {
			"data": 'nombreAfiliado',
			"title": "Afiliado"
		}, {
			"data": 'numeroDocumentoCompensacion',
			"title": "N\u00famero Comprobante"
		}, {
			"data": 'compensaFondos',
			"title": "Comp. Fondos"
		}, {
			"data": 'compensaComisiones',
			"title": "Comp. Comisiones"
		}, {
			"data": null,
			"title": 'Acci\u00f3n'
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
					url: $variableUtil.root + "txnsCompensacion?accion=" + accion,
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
							$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
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

	$local.tablaDetalleComision = $local.$tablaDetalleComision.DataTable({
		"ajax": {
			"url": "",
			"dataSrc": ""
		},
		"language": {
			"emptyTable": "No hay Comisiones registradas",
			"processing": "Cargando Comisiones registradas..."
		},
		"columns": [
			{
				"data": 'cuentaCompensacion',
				"render": function(data, type, row) {
					return $funcionUtil.unirCodigoDescripcion(row.cuentaCompensacion, row.descripcionCuentaCompensacion);
				},
				"title": 'Instituci\u00f3n'
			}, {
				"data": 'idConceptoComision',
				"render": function(data, type, row) {
					return $funcionUtil.unirCodigoDescripcion(row.idConceptoComision, row.descripcionConceptoComision);
				},
				"title": 'Tipo Comisi\u00f3n'
			}, {
				"data": 'registroContable',
				"render": function(data, type, row) {
					return $funcionUtil.unirCodigoDescripcion(row.registroContable, row.descripcionRegistroContable);
				},
				"title": 'Registro Contable'
			}, {
				"data": function(row) {
					return row.valorComision == null ? '' : row.valorComision;
				},
				"render": $tablaFuncion.formatoComision(),
				"title": 'Valor Comisi\u00f3n'
			}, {
				"data": function(row) {
					return row.valorComisionRec == null ? '' : row.valorComisionRec;
				},
				"render": $tablaFuncion.formatoComision(),
				"title": 'Valor Comisi\u00f3n Rec'
			}, {
				"data": function(row) {
					return row.valorComisionRec2 == null ? '' : row.valorComisionRec2;
				},
				"render": $tablaFuncion.formatoComision(),
				"title": 'Valor Comisi\u00f3n Rec2'
			}, {
				"data": 'cuentaCargo',
				"title": 'Cuenta Cargo'
			}, {
				"data": 'cuentaAbono',
				"title": 'Cuenta Abono'
			}, {
				"data": 'codigoAnalitico',
				"title": 'C\u00f3digo Anal\u00EDtico'
			}
		],
		"order": []
	});

	$local.$tablaMantenimiento.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaMantenimiento.find("thead").on('change', 'select', function() {
		let val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(val ? val === "SI" ? 1 : 0 : '', true, false).draw();
	});

	$local.$tablaMantenimiento.children("tbody").on("click", ".ver-detalle", function() {
		$local.$btnPrimero.removeClass("hidden");
		$local.$btnSiguiente.removeClass("hidden");
		$local.$btnAnterior.removeClass("hidden");
		$local.$btnUltimo.removeClass("hidden");
		$local.$filaSeleccionada = $(this).parents("tr");
		let compensacion = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		let fechaTransaccion = $funcionUtil.convertirDeFormatoAFormato(compensacion.fechaTransaccion, "DD/MM/YYYY", "YYYY-MM-DD");
		let data = {
			"fechaTransaccion": fechaTransaccion,
			"numeroVoucher": compensacion.numeroVoucher,
			"claseTransaccion": compensacion.idClaseTransaccion,
			"secuenciaTransaccion": compensacion.secuenciaTransaccion,
			"idInstitucion": compensacion.codigoInstitucion
		}
		let btn = $(this)
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "txnsCompensacion?accion=buscarDetalle",
			data: data,
			beforeSend: function(xhr) {
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
				btn.attr("disabled", true).find("i").removeClass("fa-eye").addClass("fa-spinner fa-pulse fa-fw");
			},
			success: function(detalleCompensacion) {
				if (detalleCompensacion.length === 0) {
					$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros Fecha Transacci\u00F3n: " + compensacion.fechaTransaccion + ", N\u00FAmero Trace: " + compensacion.numeroVoucher + " no encontr\u00F3 ninguna coincidencia en la Base de Datos.", "fa-exclamation-circle", "Informaci\u00F3n", "info");
				} else {
					if (detalleCompensacion.length === 1) {
						$local.$modalDetalleConsulta.PopupWindow("open");
						$local.$modalDetalleConsulta.PopupWindow("maximize");
						$funcionUtil.llenarFormularioDeLectura(detalleCompensacion[0], $local.$detalleCliente);
						$funcionUtil.llenarFormularioDeLectura(detalleCompensacion[0], $local.$detalleCompensacion);
					} else {
						$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros Fecha Transacci\u00F3n: " + compensacion.fechaTransaccion + ", N\u00FAmero Trace: " + compensacion.numeroVoucher + " retorn\u00F3 m\u00E1s de un resultado", "fa-exclamation-circle", "Mensaje", "danger");
					}
				}
			},
			complete: function(response) {
				btn.attr("disabled", false).find("i").addClass("fa-eye").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$tablaMantenimiento.children("tbody").on("click", ".ver-comisiones", function() {
		$local.$modalDetalleComision.PopupWindow("open");
		$local.$tablaDetalleComision.parent().addClass("table-responsive")

		$local.$filaSeleccionada = $(this).parents("tr");
		let compensacion = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		let criterio = {};
		let encabezadoVerComisiones = {
			"secuenciaTransaccion": compensacion.secuenciaTransaccion,
			"fechaTransaccion": compensacion.fechaTransaccion,
			"numeroCuenta": compensacion.numeroCuenta,
			"descripcionRol": compensacion.descripcionRol,
			"descripcionCanal": compensacion.descripcionCanal,
			"descMonedaTransaccion": compensacion.descMonedaTransaccion,
			"codigoAutorizacion": compensacion.codigoAutorizacion,
			"numeroVoucher": compensacion.numeroVoucher
		};
		let btn = $(this);
		btn.attr("disabled", true).find("i").removeClass("fa-money").addClass("fa-spinner fa-pulse fa-fw");
		criterio.secuenciaTransaccion = compensacion.secuenciaTransaccion;
		criterio.codigoInstitucion = compensacion.codigoInstitucionEmisor;
		criterio.fechaProceso = moment(compensacion.fechaProceso, 'DD/MM/YYYY').format('YYYY-MM-DD');
		let params = $.param(criterio);
		$local.$tablaDetalleComision.find('tbody').text('').append("<tr class='odd'><td valign='top' colspan='9' class='dataTables_empty'><span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando comisiones registradas</span></td></tr>");
		$local.tablaDetalleComision.ajax.url($variableUtil.root + "txnsCompensacion?accion=buscarComision&" + params).load();
		$funcionUtil.llenarFormularioDeLectura(encabezadoVerComisiones, $local.$modalDetalleComisionEncabezado);
		btn.attr("disabled", false).find("i").addClass("fa-money").removeClass("fa-spinner fa-pulse fa-fw");

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
		if (event.which === 13) {
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
		if ($funcionUtil.camposVacios($formBusquedaCriterios)) {
			$funcionUtil.notificarException($variableUtil.camposVacios, "fa-exclamation-circle", "Informaci\u00F3n", "info");
		} else {
			buscando = true;
			$local.tablaMantenimiento.ajax.reload();
		}
	});

	$local.$btnSiguiente.on("click", function() {
		$local.$btnAnterior.removeClass("hidden");
		let indexes = $local.tablaMantenimiento.rows().indexes();
		let currentIndex = $local.tablaMantenimiento.row($local.$filaSeleccionada).index();
		let currentPosition = indexes.indexOf(currentIndex);
		if (currentPosition < indexes.length - 1) {
			let compensacionSgte = $local.tablaMantenimiento.row(indexes[currentPosition + 1]).data();
			let fechaTransaccion = $funcionUtil.convertirDeFormatoAFormato(compensacionSgte.fechaTransaccion, "DD/MM/YYYY", "YYYY-MM-DD");
			let data = {
				"fechaTransaccion": fechaTransaccion,
				"numeroVoucher": compensacionSgte.numeroVoucher,
				"claseTransaccion": compensacionSgte.claseTransaccion,
				"secuenciaTransaccion": compensacionSgte.secuenciaTransaccion
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
					if (detalleCompensacion.length === 0) {
						$local.$modalDetalleConsulta.PopupWindow("close");
						$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros fecha Transacci\u00F3n: " + compensacionSgte.fechaTransaccion + ", N\u00FAmero Trace: " + compensacionSgte.numeroVoucher + " no encontr\u00F3 ninguna coincidencia en la Base de Datos.", "fa-exclamation-circle", "Informaci\u00F3n", "info");
					} else {
						if (detalleCompensacion.length === 1) {
							$local.$modalDetalleConsulta.PopupWindow("open");
							$local.$modalDetalleConsulta.PopupWindow("maximize");
							$funcionUtil.llenarFormularioDeLectura(detalleCompensacion[0], $local.$detalleCliente);
							$funcionUtil.llenarFormularioDeLectura(detalleCompensacion[0], $local.$detalleCompensacion);
						} else {
							$local.$modalDetalleConsulta.PopupWindow("close");
							$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros fecha Transacci\u00F3n: " + compensacionSgte.fechaTransaccion + ", N\u00FAmero Trace: " + compensacionSgte.numeroVoucher + " retorn\u00F3 m\u00E1s de un resultado", "fa-exclamation-circle", "Mensaje", "danger");
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
		let indexes = $local.tablaMantenimiento.rows().indexes();
		let currentIndex = $local.tablaMantenimiento.row($local.$filaSeleccionada).index();
		let currentPosition = indexes.indexOf(currentIndex);

		if (currentPosition >= 0) {
			let compensacionAnt = $local.tablaMantenimiento.row(indexes[currentPosition - 1]).data();
			let fechaTransaccion = $funcionUtil.convertirDeFormatoAFormato(compensacionAnt.fechaTransaccion, "DD/MM/YYYY", "YYYY-MM-DD");
			let data = {
				"fechaTransaccion": fechaTransaccion,
				"numeroVoucher": compensacionAnt.numeroVoucher,
				"claseTransaccion": compensacionAnt.claseTransaccion,
				"secuenciaTransaccion": compensacionAnt.secuenciaTransaccion
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
					if (detalleCompensacion.length === 0) {
						$local.$modalDetalleConsulta.PopupWindow("close");
						$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros Fecha Transacci\u00F3n: " + compensacionAnt.fechaTransaccion + ", N\u00FAmero Trace: " + compensacionAnt.numeroVoucher + " no encontr\u00F3 ninguna coincidencia en la Base de Datos.", "fa-exclamation-circle", "Informaci\u00F3n", "info");
					} else {
						if (detalleCompensacion.length === 1) {
							$local.$modalDetalleConsulta.PopupWindow("open");
							$local.$modalDetalleConsulta.PopupWindow("maximize");
							$funcionUtil.llenarFormularioDeLectura(detalleCompensacion[0], $local.$detalleCliente);
							$funcionUtil.llenarFormularioDeLectura(detalleCompensacion[0], $local.$detalleCompensacion);
						} else {
							$local.$modalDetalleConsulta.PopupWindow("close");
							$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros Fecha Transacci\u00F3n: " + compensacionAnt.fechaTransaccion + ", N\u00FAmero Trace: " + compensacionAnt.numeroVoucher + " retorn\u00F3 m\u00E1s de un resultado", "fa-exclamation-circle", "Informaci\u00F3n", "info");
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
		let indexes = $local.tablaMantenimiento.rows().indexes();
		let currentIndex = $local.tablaMantenimiento.row($local.$filaSeleccionada).index();
		let compensacionPrimero = $local.tablaMantenimiento.row(indexes[0]).data();
		let fechaTransaccion = $funcionUtil.convertirDeFormatoAFormato(compensacionPrimero.fechaTransaccion, "DD/MM/YYYY", "YYYY-MM-DD");
		let data = {
			"fechaTransaccion": fechaTransaccion,
			"numeroVoucher": compensacionPrimero.numeroVoucher,
			"claseTransaccion": compensacionPrimero.claseTransaccion,
			"secuenciaTransaccion": compensacionPrimero.secuenciaTransaccion
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
				if (detalleCompensacion.length === 0) {
					$local.$modalDetalleConsulta.PopupWindow("close");
					$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros Fecha Transacci\u00F3n: " + compensacionPrimero.fechaTransaccion + ", N\u00FAmero Trace: " + compensacionPrimero.numeroVoucher + " no encontr\u00F3 ninguna coincidencia en la Base de Datos.", "fa-exclamation-circle", "Informaci\u00F3n", "info");
				} else {

				}
				if (detalleCompensacion.length === 1) {
					$local.$modalDetalleConsulta.PopupWindow("open");
					$local.$modalDetalleConsulta.PopupWindow("maximize");
					$funcionUtil.llenarFormularioDeLectura(detalleCompensacion[0], $local.$detalleCliente);
					$funcionUtil.llenarFormularioDeLectura(detalleCompensacion[0], $local.$detalleCompensacion);
				} else {
					$local.$modalDetalleConsulta.PopupWindow("close");
					$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros Fecha Transacci\u00F3n: " + compensacionPrimero.fechaTransaccion + ", N\u00FAmero Trace: " + compensacionPrimero.numeroVoucher + " retorn\u00F3 m\u00e1s de un resultado", "fa-exclamation-circle", "Mensaje", "danger");
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
		let compensacionUltimo = $local.tablaMantenimiento.row(indexes[indexes.length - 1]).data();
		let fechaTransaccion = $funcionUtil.convertirDeFormatoAFormato(compensacionUltimo.fechaTransaccion, "DD/MM/YYYY", "YYYY-MM-DD");
		let data = {
			"fechaTransaccion": fechaTransaccion,
			"numeroVoucher": compensacionUltimo.numeroVoucher,
			"claseTransaccion": compensacionUltimo.claseTransaccion,
			"secuenciaTransaccion": compensacionUltimo.secuenciaTransaccion
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
				if (detalleCompensacion.length === 0) {
					$local.$modalDetalleConsulta.PopupWindow("close");
					$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros Fecha Transacci\u00F3n: " + compensacionUltimo.fechaTransaccion + ", N\u00FAmero Trace: " + compensacionUltimo.numeroVoucher + " no encontr\u00F3 ninguna coincidencia en la Base de Datos.", "fa-exclamation-circle", "Informaci\u00F3n", "info");
				} else {
					if (detalleCompensacion.length === 1) {
						$local.$modalDetalleConsulta.PopupWindow("open");
						$local.$modalDetalleConsulta.PopupWindow("maximize");
						$funcionUtil.llenarFormularioDeLectura(detalleCompensacion[0], $local.$detalleCliente);
						$funcionUtil.llenarFormularioDeLectura(detalleCompensacion[0], $local.$detalleCompensacion);
					} else {
						$local.$modalDetalleConsulta.PopupWindow("close");
						$funcionUtil.notificarException("La B\u00FAsqueda con los par\u00E1metros Fecha Transacci\u00F3n: " + compensacionUltimo.fechaTransaccion + ", N\u00FAmero Trace: " + compensacionUltimo.numeroVoucher + " retorn\u00F3 m\u00e1s de un resultado", "fa-exclamation-circle", "Mensaje", "danger");
					}
				}
			}
		});
		$local.tablaMantenimiento.row(currentIndex).deselect();
		$local.$filaSeleccionada = $local.tablaMantenimiento.row(indexes[indexes.length - 1]).select();
	});

	$local.$exportar.on("click", function() {
		if (!$formBusquedaCriterios.valid()) {
			return;
		}
		if ($funcionUtil.camposVacios2($formBusquedaCriterios, $local.numFiltrosMin)) {
			$funcionUtil.notificarException($local.mensajeFiltros(), "fa-exclamation-circle", "Informaci\u00F3n", "info");
			return;
		}
		const rangoFechasProceso = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechasProceso);
		const rangoFechasTransaccion = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechasTransaccion);
		let criterioBusqueda = $formBusquedaCriterios.serializeJSON();
		criterioBusqueda.fechaProcesoInicio = new Date(rangoFechasProceso.fechaInicio.replaceAll('-', '/'));
		criterioBusqueda.fechaProcesoFin = new Date(rangoFechasProceso.fechaFin.replaceAll('-', '/'));
		if (rangoFechasTransaccion.fechaInicio !== "" && rangoFechasTransaccion.fechaFin !== "") {
			criterioBusqueda.fechaTransaccionInicio = new Date(rangoFechasTransaccion.fechaInicio.replaceAll('-', '/'));
			criterioBusqueda.fechaTransaccionFin = new Date(rangoFechasTransaccion.fechaFin.replaceAll('-', '/'));
		}
		criterioBusqueda.modo = "EXPORT";
		criterioBusqueda.descripcionRangoFechasProceso = $local.$rangoFechasProceso.val();
		criterioBusqueda.descripcionRangoFechasTransaccion = $local.$rangoFechasTransaccion.val();
		criterioBusqueda.descripcionInstitucion = $local.$selectInstitucionTxnsCompensacion.find("option:selected").text();
		criterioBusqueda.descripcionEmpresa = $local.$selectEmpresaTxnsCompensacion.find("option:selected").val() === "" ? "" : $local.$selectEmpresaTxnsCompensacion.find("option:selected").text();
		criterioBusqueda.descripcionCliente = !!$local.$selectClienteTxnsCompensacion.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$selectClienteTxnsCompensacion, "; ") : "TODOS";
		criterioBusqueda.descripcionRolTransaccion = !!$local.$selectRolTransaccionTxnsCompensacion.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$selectRolTransaccionTxnsCompensacion, "; ") : "TODOS";
		criterioBusqueda.descripcionMembresia = $local.$selectMembresiaTxnsCompensacion.find("option:selected").val() === "" ? "" : $local.$selectMembresiaTxnsCompensacion.find("option:selected").text();
		criterioBusqueda.descripcionServicio = !!$local.$selectServicioTxnsCompensacion.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$selectServicioTxnsCompensacion, "; ") : "TODOS";
		criterioBusqueda.descripcionOrigen = !!$local.$selectOrigenTxnsCompensacion.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$selectOrigenTxnsCompensacion, "; ") : "TODOS";
		criterioBusqueda.descripcionClaseTransaccion = $local.$selectClaseTransaccionTxnsCompensacion.find("option:selected").val() === "" ? "" : $local.$selectClaseTransaccionTxnsCompensacion.find("option:selected").text();
		criterioBusqueda.descripcionCodigoTransaccion = !!$local.$selectCodigoTransaccionTxnsCompensacion.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$selectCodigoTransaccionTxnsCompensacion, "; ") : "TODOS";
		criterioBusqueda.descripcionLogo = $local.$selectLogoTxnsCompensacion.find("option:selected").val() === "" ? "" : $local.$selectLogoTxnsCompensacion.find("option:selected").text();
		criterioBusqueda.descripcionProducto = !!$local.$selectProductosTxnsCompensacion.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$selectProductosTxnsCompensacion, "; ") : "TODOS";
		criterioBusqueda.descripcionInstitucionEmisor = !!$local.$selectInstEmisorTxnsCompensacion.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$selectInstEmisorTxnsCompensacion, "; ") : "TODOS";
		criterioBusqueda.descripcionInstitucionReceptor = !!$local.$selectInstReceptorTxnsCompensacion.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$selectInstReceptorTxnsCompensacion, "; ") : "TODOS";
		criterioBusqueda.descripcionCodigoRespuesta = !!$local.$selectCodigoRespuestaTxnsCompensacion.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$selectCodigoRespuestaTxnsCompensacion, "; ") : "TODOS";
		criterioBusqueda.descripcionCanal = !!$local.$selectCanalTxnsCompensacion.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$selectCanalTxnsCompensacion, "; ") : "TODOS";
		criterioBusqueda.descripcionMonedaTransaccion = $local.$selectMonedaTransaccionTxnsCompensacion.find("option:selected").val() === "" ? "" : $local.$selectMonedaTransaccionTxnsCompensacion.find("option:selected").text();
		let paramCriterioBusqueda = $.param(criterioBusqueda);
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectClienteTxnsCompensacion, "clientes");
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectRolTransaccionTxnsCompensacion, "rolesTransaccion");
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectServicioTxnsCompensacion, "servicios");
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectOrigenTxnsCompensacion, "origenes");
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectCodigoTransaccionTxnsCompensacion, "codigosTransaccion");
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectProductosTxnsCompensacion, "productos");
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectInstEmisorTxnsCompensacion, "institucionesEmisor");
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectInstReceptorTxnsCompensacion, "institucionesReceptor");
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectCodigoRespuestaTxnsCompensacion, "codigosRespuesta");
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectCanalTxnsCompensacion, "canales");
		$funcionUtil.webSocketMethod('exportacionPOI');
		window.location.href = `${$variableUtil.root}reporte/movimientos/txnsCompensacion?rutaEnSidebar=${$variableUtil.rutaEnSidebar}&accion=exportarCriterios&${paramCriterioBusqueda}`;
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
		window.location.href = `${$variableUtil.root}reporte/movimientos/txnsCompensacion?rutaEnSidebar=${$variableUtil.rutaEnSidebar}&accion=exportarTipoDocumento&${paramCriterioBusqueda}`;
	});
});