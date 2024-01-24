$(document).ready(function() {

	var $local = {
		$tablaMantenimiento: $("#tablaMantenimiento"),
		tablaMantenimiento: "",
		$modalMantenimiento: $("#modalMantenimiento"),
		$aniadirMantenimento: $("#aniadirMantenimiento"),
		$botonesActualizacion: $("#botonesActualizacion"),
		$registrarMantenimiento: $("#registrarMantenimiento"),
		$actualizarMantenimiento: $("#actualizarMantenimiento"),
		cuentaContableMiscelaneo: {},
		$filaSeleccionada: "",
		$cuentasContables: $(".cuentaContable"),

		$clasesServicio: $("#clasesServicio"),
		$claseTransacciones: $("#claseTransacciones"),
		$codigoTransacciones: $("#codigoTransacciones"),
		$rolesTransaccion: $("#rolesTransaccion"),
		$clientes: $("#clientes"),
		$empresas: $("#empresas"),
		$membresias: $("#membresias"),
		$monedas: $("#monedas"),
		$origenes: $("#origenes"),
		$instituciones: $('#instituciones'),

		$inputsCuentaHibrida: $(".inputCuentaHibrida"),
		$checkCuentaHibrida: $("#checkCuentaHibrida"),

		$claseTransaccionesFiltroParaTablaMantenimiento: $("#claseTransacciones-filtroParaTablaMantenimiento"),
		$rolesTransaccionFiltroParaTablaMantenimiento: $("#rolesTransaccion-filtroParaTablaMantenimiento"),
		$empresasFiltroParaTablaMantenimiento: $("#empresas-filtroParaTablaMantenimiento"),
		$conceptoComisionesFiltroParaTablaMantenimiento: $("#conceptoComisiones-FiltroParaTablaMantenimiento"),
		$monedasFiltroParaTablaMantenimiento: $("#monedas-filtroParaTablaMantenimiento"),
		$membresiasFiltroParaTablaMantenimiento: $("#membresias-filtroParaTablaMantenimiento"),
		$origenesFiltroParaTablaMantenimiento: $("#origenes-filtroParaTablaMantenimiento"),
		filtrosSeleccionables: {},

		$agregableClaseServicio: "",
		$agregableClaseTransaccion: "",
		$agregableCodigoTransaccion: "",
		$agregableMembresia: "",
		$agregableEmpresa: "",
		$agregableCliente: "",
		arregloSiNo: ["0", "1"],

		$exportar: $('#exportar'),

		// Permisos
		permisoActualizacion: false,
		permisoEliminacion: false
	};

	$formMantenimiento = $("#formMantenimiento");

	$funcionUtil.crearSelect2($local.$claseTransacciones, "Selecciona una Clase de Transacci\u00F3n");
	$funcionUtil.crearSelect2($local.$clasesServicio, "Selecciona una Clase de Servicio");
	$funcionUtil.crearSelect2($local.$clientes, "Seleccione un cliente");
	$funcionUtil.crearSelect2($local.$codigoTransacciones, "Seleccione una C\u00F3digo de Transacci\u00F3n");
	$funcionUtil.crearSelect2($local.$rolesTransaccion, "Seleccione un Rol de Transacci\u00F3n");
	$funcionUtil.crearSelect2($local.$empresas, "Seleccione una Empresa");
	$funcionUtil.crearSelect2($local.$membresias, "Seleccione una Membres\u00EDa");
	$funcionUtil.crearSelect2($local.$monedas, "Seleccione una Moneda");
	$funcionUtil.crearSelect2($local.$origenes, "Seleccione un Origen");
	$funcionUtil.crearSelect2($local.$instituciones, "Seleccione una Instituci\u00F3n");

	$local.$empresas.on("change", function(event, opcionSeleccionada) {
		var idEmpresa = $(this).val();
		if (idEmpresa == null || idEmpresa == undefined) {
			$local.$clientes.find("option:not(:eq(0))").remove();
			return;
		}
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "cliente/empresa/" + idEmpresa,
			beforeSend: function(xhr) {
				$local.$clientes.find("option:not(:eq(0))").remove();
				$local.$clientes.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Clientes</span>");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode: {
				400: function(response) {
					$funcionUtil.limpiarMensajesDeError($formMantenimiento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formMantenimiento);
				}
			},
			success: function(clientes) {
				$.each(clientes, function(i, cliente) {
					$local.$clientes.append($("<option />").val(this.idCliente).text($funcionUtil.unirCodigoDescripcion(this.idCliente, this.descripcion)));
				});
				if (opcionSeleccionada != null && opcionSeleccionada != undefined) {
					$local.$clientes.val(opcionSeleccionada).trigger("change.select2");
				}
			},
			error: function(response) {
			},
			complete: function(response) {
				$local.$clientes.parent().find(".cargando").remove();
			}
		});
	});

	$local.$claseTransacciones.on("change", function(event, opcionSeleccionada) {
		var codigoClaseTransaccion = $(this).val();
		if (codigoClaseTransaccion == null || codigoClaseTransaccion == undefined) {
			$local.$codigoTransacciones.find("option:not(:eq(0))").remove();
			return;
		}
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "codigoTransaccion/claseTransaccion/" + codigoClaseTransaccion,
			beforeSend: function(xhr) {
				$local.$codigoTransacciones.find("option:not(:eq(0))").remove();
				$local.$codigoTransacciones.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando C\u00F3digo de Transacciones</span>")
				xhr.setRequestHeader('Content-Type', 'application/json');
			},
			statusCode: {
				400: function(response) {
					$funcionUtil.limpiarMensajesDeError($formMantenimiento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formMantenimiento);
				}
			},
			success: function(codigoTransacciones) {
				$.each(codigoTransacciones, function(i, codigoTransaccion) {
					$local.$codigoTransacciones.append($("<option />").val(this.codigoTransaccion).text(this.codigoTransaccion + " - " + this.descripcion));
				})
				if (opcionSeleccionada != null && opcionSeleccionada != undefined) {
					$local.$codigoTransacciones.val(opcionSeleccionada).trigger("change.select2");
				}
				$local.$codigoTransacciones.trigger("change");
			},
			error: function(response) {
			},
			complete: function(response) {
				$local.$codigoTransacciones.parent().find(".cargando").remove();
			}
		});
	});

	$local.$membresias.on("change", function(event, opcionClaseServicioSeleccionado) {
		var codigoMembresia = $(this).val();
		if (codigoMembresia == null || codigoMembresia == undefined) {
			$local.$clasesServicio.find("option:not(:eq(0))").remove();
			return;
		}
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "claseServicio/membresia/" + codigoMembresia,
			beforeSend: function(xhr) {
				$local.$clasesServicio.find("option:not(:eq(0))").remove();
				$local.$clasesServicio.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Clases de Servicio</span>")
				xhr.setRequestHeader('Content-Type', 'application/json');
			},
			statusCode: {
				400: function(response) {
					$funcionUtil.limpiarMensajesDeError($formMantenimiento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formMantenimiento);
				}
			},
			success: function(clasesServicio) {
				$.each(clasesServicio, function(i, claseServicio) {
					$local.$clasesServicio.append($("<option />").val(this.codigoClaseServicio).text(this.codigoClaseServicio + " - " + this.descripcion));
				});
				if (opcionClaseServicioSeleccionado != null && opcionClaseServicioSeleccionado != undefined) {
					$local.$clasesServicio.val(opcionClaseServicioSeleccionado).trigger("change.select2");
					$local.$clasesServicio.trigger("change");
				} else {
					$local.$clasesServicio.trigger("change");
				}
			},
			error: function(response) {
			},
			complete: function(response) {
				$local.$clasesServicio.parent().find(".cargando").remove();
			}
		});
	});

	$.fn.dataTable.ext.errMode = 'none';

	$local.$tablaMantenimiento.on('xhr.dt', function(e, settings, json, xhr) {
		switch (xhr.status) {
			case 500:
				$local.tablaMantenimiento.clear().draw();
				break;
		}
	});

	$local.permisoActualizacion = $local.$tablaMantenimiento.attr("data-permiso-actualizacion") || false;
	$local.permisoEliminacion = $local.$tablaMantenimiento.attr("data-permiso-eliminacion") || false;

	$local.tablaMantenimiento = $local.$tablaMantenimiento.DataTable({
		"ajax": {
			"url": $variableUtil.root + "cuentaContableMiscelaneo?accion=buscarTodos",
			"dataSrc": ""
		},
		"language": {
			"emptyTable": "No hay Cuentas Contables registrados"
		},
		"initComplete": function() {
			$local.$tablaMantenimiento.wrap("<div class='table-responsive'></div>");
			$local.filtrosSeleccionables["1"] = $local.$empresasFiltroParaTablaMantenimiento.html();
			$local.filtrosSeleccionables["3"] = $local.$monedasFiltroParaTablaMantenimiento.html();
			$local.filtrosSeleccionables["4"] = $local.$membresiasFiltroParaTablaMantenimiento.html();
			$local.filtrosSeleccionables["5"] = $local.$origenesFiltroParaTablaMantenimiento.html();
			$local.filtrosSeleccionables["7"] = $local.$claseTransaccionesFiltroParaTablaMantenimiento.html();
			$local.filtrosSeleccionables["9"] = $local.$rolesTransaccionFiltroParaTablaMantenimiento.html();
			$local.filtrosSeleccionables["13"] = $local.arregloSiNo;
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaMantenimiento, $local.filtrosSeleccionables);
			$local.$agregableEmpresa = $local.$tablaMantenimiento.find("thead").find("select.agregable-empresa");
			$local.$agregableCliente = $local.$tablaMantenimiento.find("thead").find("select.agregable-cliente");
			$local.$agregableMembresia = $local.$tablaMantenimiento.find("thead").find("select.agregable-membresia");
			$local.$agregableClaseServicio = $local.$tablaMantenimiento.find("thead").find("select.agregable-clase-servicio");
			$local.$agregableClaseTransaccion = $local.$tablaMantenimiento.find("thead").find("select.agregable-clase-transaccion");
			$local.$agregableCodigoTransaccion = $local.$tablaMantenimiento.find("thead").find("select.agregable-codigo-transaccion");
		},
		"columnDefs": [{
			"targets": 17,
			"className": "all dt-center",
			"render": function() {
				return "" + ($local.permisoActualizacion == "true" ? $variableUtil.botonActualizar : "") + " " + ($local.permisoEliminacion == "true" ? $variableUtil.botonEliminar : "");
			}
		}],
		"columns": [{
			"className": "all filtrable",
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoInstitucion, row.descripcionInstitucion);
			},
			"title": "Instituci\u00F3n"
		}, {
			"className": "all seleccionable select2 insertable-opciones-html agregable-empresa",
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idEmpresa, row.descripcionEmpresa);
			},
			"title": "Empresa"
		}, {
			"className": "all seleccionable select2 data-vacia agregable-cliente",
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idCliente, row.descripcionCliente);
			},
			"title": "Cliente"
		}, {
			"className": "all seleccionable select2 insertable-opciones-html",
			"data": 'descripcionMoneda',
			"title": "Moneda"
		}, {
			"className": "all seleccionable select2 agregable-membresia insertable-opciones-html",
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoMembresia, row.descripcionMembresia);
			},
			"title": "Membres\u00EDa"
		}, {
			"className": "all seleccionable select2 data-vacia agregable-clase-servicio",
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoClaseServicio, row.descripcionClaseServicio);
			},
			"title": "Servicio"
		}, {
			"className": "all seleccionable select2 insertable-opciones-html",
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoOrigen, row.descripcionOrigen);
			},
			"title": "Origen"
		}, {
			"className": "all seleccionable select2 agregable-clase-transaccion insertable-opciones-html",
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoClaseTransaccion, row.descripcionClaseTransaccion);
			},
			"title": "Clase de Transacci\u00F3n"
		}, {
			"className": "all seleccionable select2 agregable-codigo-transaccion insertable-opciones-html",
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoTransaccion, row.descripcionCodigoTransaccion);
			},
			"title": "C\u00F3digo de Transacci\u00F3n"
		}, {
			"className": "all seleccionable select2 insertable-opciones-html",
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idRolTransaccion, row.descripcionRolTransaccion);
			},
			"title": "Rol"
		}, {
			"className": "all filtrable",
			"data": "cuentaCargo",
			"title": "Cuenta Cargo"
		}, {
			"className": "all filtrable",
			"data": "cuentaAbono",
			"title": "Cuenta Abono"
		}, {
			"className": "all filtrable",
			"data": "codigoAnalitico",
			"title": "C\u00F3digo Anal\u00EDtico"
		}, {
			"className": "all seleccionable data-no-definida",
			"data": function(row) {
				return $funcionUtil.insertarEtiquetaSiNo(row.cuentaHibrida);
			},
			"title": "Cuenta H\u00EDbrida"
		}, {
			"className": "all filtrable",
			"data": "cuentaAbonoH",
			"title": "Cuenta Abono H"
		}, {
			"className": "all filtrable",
			"data": "cuentaCargoH",
			"title": "Cuenta Cargo H"
		}, {
			"className": "all filtrable",
			"data": "codigoAnaliticoH",
			"title": "C\u00F3digo Anal\u00EDtico H"
		}, {
			"data": null,
			"title": "Acci\u00F3n"
		}]
	});

	$local.$tablaMantenimiento.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaMantenimiento.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
		if ($(this).hasClass("agregable-empresa")) {
			var idEmpresa = $(this).find(":selected").attr("data-value");
			if (idEmpresa == "" || idEmpresa == null) {
				$local.$agregableCliente.find("option:not(:contains('Todos'))").remove();
				$local.$agregableCliente.trigger("change");
				return;
			}
			$.ajax({
				type: "GET",
				url: $variableUtil.root + "cliente/empresa/" + idEmpresa,
				beforeSend: function(xhr) {
					$local.$agregableCliente.find("option:not(:contains('Todos'))").remove();
					$local.$agregableCliente.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Clientes</span>")
				},
				statusCode: {
					400: function(response) {
						$funcionUtil.notificarException($funcionUtil.obtenerMensajeErrorEnCadena(response.responseJSON), "fa-warning", "Aviso", "warning");
					}
				},
				success: function(clientes) {
					$.each(clientes, function(i, cliente) {
						var $opcionCliente = $("<option />").val($funcionUtil.unirCodigoDescripcion(this.idCliente, this.descripcion)).text($funcionUtil.unirCodigoDescripcion(this.idCliente, this.descripcion)).attr("data-value", this.idCliente);
						$local.$agregableCliente.append($opcionCliente);
					});
				},
				complete: function() {
					$local.$agregableCliente.parent().find(".cargando").remove();
					$local.$agregableCliente.trigger("change");
				}
			});
		} else if ($(this).hasClass("agregable-membresia")) {
			var codigoMembresia = $(this).find(":selected").attr("data-value");
			if (codigoMembresia == "" || codigoMembresia == null) {
				$local.$agregableClaseServicio.find("option:not(:contains('Todos'))").remove();
				$local.$agregableClaseServicio.trigger("change");
				return;
			}
			$.ajax({
				type: "GET",
				url: $variableUtil.root + "claseServicio/membresia/" + codigoMembresia,
				beforeSend: function(xhr) {
					$local.$agregableClaseServicio.find("option:not(:contains('Todos'))").remove();
					$local.$agregableClaseServicio.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Clases de Servicio</span>")
				},
				statusCode: {
					400: function(response) {
						$funcionUtil.notificarException($funcionUtil.obtenerMensajeErrorEnCadena(response.responseJSON), "fa-warning", "Aviso", "warning");
					}
				},
				success: function(claseServicios) {
					$.each(claseServicios, function(i, claseServicio) {
						var $opcionClaseServicio = $("<option />").val($funcionUtil.unirCodigoDescripcion(this.codigoClaseServicio, this.descripcion)).text($funcionUtil.unirCodigoDescripcion(this.codigoClaseServicio, this.descripcion)).attr("data-value", this.codigoClaseServicio);
						$local.$agregableClaseServicio.append($opcionClaseServicio);
					});
				},
				complete: function() {
					$local.$agregableClaseServicio.parent().find(".cargando").remove();
					$local.$agregableClaseServicio.trigger("change");
				}
			});
		} else if ($(this).hasClass("agregable-clase-transaccion")) {
			var codigoClaseTransaccion = $(this).find(":selected").attr("data-value");
			if (codigoClaseTransaccion == "" || codigoClaseTransaccion == null) {
				$local.$agregableCodigoTransaccion.find("option:not(:contains('Todos'))").remove();
				$local.$agregableCodigoTransaccion.trigger("change");
				return;
			}
			$.ajax({
				type: "GET",
				url: $variableUtil.root + "codigoTransaccion/claseTransaccion/" + codigoClaseTransaccion,
				beforeSend: function(xhr) {
					$local.$agregableCodigoTransaccion.find("option:not(:contains('Todos'))").remove();
					$local.$agregableCodigoTransaccion.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando C\u00F3digos de Transacci\u00F3n</span>");
				},
				statusCode: {
					400: function(response) {
						$funcionUtil.notificarException($funcionUtil.obtenerMensajeErrorEnCadena(response.responseJSON), "fa-warning", "Aviso", "warning");
					}
				},
				success: function(codigoTransacciones) {
					$.each(codigoTransacciones, function(i, codigoTransaccion) {
						var descripcionCodigoTransaccion = $funcionUtil.unirCodigoDescripcion(this.codigoTransaccion, this.descripcion);
						var $opcionCodigoTransaccion = $("<option />").val(descripcionCodigoTransaccion).text(descripcionCodigoTransaccion).attr("data-value", this.codigoTransaccion);
						$local.$agregableCodigoTransaccion.append($opcionCodigoTransaccion);
					});
				},
				complete: function() {
					$local.$agregableCodigoTransaccion.parent().find(".cargando").remove();
					$local.$agregableCodigoTransaccion.trigger("change");
				}
			});
		}
	});

	$local.$modalMantenimiento.PopupWindow({
		title: "Mantenimiento de Cuentas Contables Miscel\u00E1neo",
		autoOpen: false,
		modal: false,
		height: 440,
		width: 626,
	});

	$local.$aniadirMantenimento.on("click", function() {
		$funcionUtil.prepararFormularioRegistro($formMantenimiento);
		$local.$actualizarMantenimiento.addClass("hidden");
		$local.$registrarMantenimiento.removeClass("hidden");
		$local.$modalMantenimiento.PopupWindow("open");
		$local.$checkCuentaHibrida.trigger('change');
		$local.$modalMantenimiento.PopupWindow("maximize");
		$local.$filaSeleccionada = "";
	});

	$local.$modalMantenimiento.on("close.popupwindow", function() {
		$local.$filaSeleccionada = "";
		$local.cuentaContableMiscelaneo = {};
	});

	$formMantenimiento.find("input").keypress(function(event) {
		if (event.which == 13) {
			if (!$local.$registrarMantenimiento.hasClass("hidden")) {
				$local.$registrarMantenimiento.trigger("click");
				return false;
			} else {
				if (!$local.$actualizarMantenimiento.hasClass("hidden")) {
					$local.$actualizarMantenimiento.trigger("click");
				}
				return false;
			}
		}
	});

	$local.$registrarMantenimiento.on("click", function() {
		if (!$formMantenimiento.valid()) {
			return;
		}
		var cuentaContableMiscelaneo = $formMantenimiento.serializeJSON();
		$.ajax({
			type: "POST",
			url: $variableUtil.root + "cuentaContableMiscelaneo",
			data: JSON.stringify(cuentaContableMiscelaneo),
			beforeSend: function(xhr) {
				$local.$registrarMantenimiento.attr("disabled", true).find("i").removeClass("fa-floppy-o").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode: {
				400: function(response) {
					$funcionUtil.limpiarMensajesDeError($formMantenimiento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formMantenimiento);
				},
				409: function(response) {
					$funcionUtil.notificarException(response.responseText, "fa-warning", "Aviso", "warning");
				}
			},
			success: function(cuentasContablesMiscelaneo) {
				$funcionUtil.notificarException($variableUtil.registroExitoso, "fa-check", "Aviso", "success");
				if ($local.$tablaMantenimiento.length > 0) {
					var cuentaContableMiscelaneo = cuentasContablesMiscelaneo[0];
					var row = $local.tablaMantenimiento.row.add(cuentaContableMiscelaneo).draw();
					row.show().draw(false);
					$(row.node()).animateHighlight();
				}
				$local.$modalMantenimiento.PopupWindow("close");
			},
			error: function(response) {
			},
			complete: function(response) {
				$local.$registrarMantenimiento.attr("disabled", false).find("i").addClass("fa-floppy-o").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$tablaMantenimiento.children("tbody").on("click", ".actualizar", function() {
		$funcionUtil.prepararFormularioActualizacion($formMantenimiento);
		$local.$filaSeleccionada = $(this).parents("tr");
		$local.cuentaContableMiscelaneo = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		$funcionUtil.llenarFormulario($local.cuentaContableMiscelaneo, $formMantenimiento);
		$local.$empresas.trigger("change", [$local.cuentaContableMiscelaneo.idCliente]);
		$local.$claseTransacciones.trigger("change", [$local.cuentaContableMiscelaneo.codigoTransaccion]);
		$local.$membresias.trigger("change", [$local.cuentaContableMiscelaneo.codigoClaseServicio]);
		$local.$actualizarMantenimiento.removeClass("hidden");
		$local.$registrarMantenimiento.addClass("hidden");
		activarInputsCuentaHibrida($local.$inputsCuentaHibrida, $local.cuentaContableMiscelaneo.cuentaHibrida);
		$local.$modalMantenimiento.PopupWindow("open");
		$local.$modalMantenimiento.PopupWindow("maximize");
	});

	$local.$actualizarMantenimiento.on("click", function() {
		if (!$formMantenimiento.valid()) {
			return;
		}
		var cuentaContableMiscelaneo = $formMantenimiento.serializeJSON();
		$local.cuentaContableMiscelaneo.cuentaCargo = cuentaContableMiscelaneo.cuentaCargo;
		$local.cuentaContableMiscelaneo.cuentaAbono = cuentaContableMiscelaneo.cuentaAbono;
		$local.cuentaContableMiscelaneo.codigoAnalitico = cuentaContableMiscelaneo.codigoAnalitico;
		$local.cuentaContableMiscelaneo.cuentaHibrida = cuentaContableMiscelaneo.cuentaHibrida;
		$local.cuentaContableMiscelaneo.cuentaCargoH = cuentaContableMiscelaneo.cuentaCargoH;
		$local.cuentaContableMiscelaneo.cuentaAbonoH = cuentaContableMiscelaneo.cuentaAbonoH;
		$local.cuentaContableMiscelaneo.codigoAnaliticoH = cuentaContableMiscelaneo.codigoAnaliticoH;
		$.ajax({
			type: "PUT",
			url: $variableUtil.root + "cuentaContableMiscelaneo",
			data: JSON.stringify($local.cuentaContableMiscelaneo),
			beforeSend: function(xhr) {
				$local.$actualizarMantenimiento.attr("disabled", true).find("i").removeClass("fa-pencil-square").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode: {
				400: function(response) {
					$funcionUtil.limpiarMensajesDeError($formMantenimiento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formMantenimiento);
				},
				409: function(response) {
					$funcionUtil.notificarException(response.responseText, "fa-warning", "Aviso", "warning");
				}
			},
			success: function(cuentasContablesMiscelaneo) {
				$funcionUtil.notificarException($variableUtil.actualizacionExitosa, "fa-check", "Aviso", "success");
				if ($local.$tablaMantenimiento.length > 0) {
					var row = $local.tablaMantenimiento.row($local.$filaSeleccionada).data(cuentasContablesMiscelaneo[0]).draw();
					row.show().draw(false);
					$(row.node()).animateHighlight();
				}
				$local.$modalMantenimiento.PopupWindow("close");
			},
			error: function(response) {
			},
			complete: function(response) {
				$local.$actualizarMantenimiento.attr("disabled", false).find("i").addClass("fa-pencil-square").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$tablaMantenimiento.children("tbody").on("click", ".eliminar", function() {
		$local.$filaSeleccionada = $(this).parents("tr");
		var cuentaContableMiscelaneo = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		$.confirm({
			icon: "fa fa-info-circle",
			title: "Aviso",
			content: "\u00BFDesea eliminar el <b>Escenario de Cuentas Contables Miscel\u00E1neo</b>?",
			theme: "bootstrap",
			buttons: {
				Aceptar: {
					action: function() {
						var confirmar = $.confirm({
							icon: 'fa fa-spinner fa-pulse fa-fw',
							title: "Eliminando...",
							content: function() {
								var self = this;
								self.buttons.close.hide();
								$.ajax({
									type: "DELETE",
									url: $variableUtil.root + "cuentaContableMiscelaneo",
									data: JSON.stringify(cuentaContableMiscelaneo),
									autoclose: true,
									beforeSend: function(xhr) {
										xhr.setRequestHeader('Content-Type', 'application/json');
										xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
									}
								}).done(function(response) {
									$funcionUtil.notificarException(response, "fa-check", "Aviso", "success");
									$local.tablaMantenimiento.row($local.$filaSeleccionada).remove().draw(false);
									confirmar.close();
									$local.$filaSeleccionada = "";
								}).fail(function(xhr) {
									confirmar.close();
									switch (xhr.status) {
										case 400:
											$funcionUtil.notificarException($funcionUtil.obtenerMensajeErrorEnCadena(xhr.responseJSON), "fa-warning", "Aviso", "warning");
											break;
										case 409:
											var mensaje = $funcionUtil.obtenerMensajeError("La Cuenta Contable Emisor ", xhr.responseJSON, $variableUtil.accionEliminado);
											$funcionUtil.notificarException(mensaje, "fa-warning", "Aviso", "warning");
											break;
									}
									$local.$filaSeleccionada = "";
								});
							},
							buttons: {
								close: {
									text: 'Aceptar'
								}
							}
						});
					},
					keys: ['enter'],
					btnClass: "btn-primary"
				},
				Cancelar: {
					keys: ['esc']
				},
			}
		});
	});

	$local.$checkCuentaHibrida.on("change", function() {
		activarInputsCuentaHibrida($local.$inputsCuentaHibrida, true);
		if (!$(this).is(":checked")) {
			activarInputsCuentaHibrida($local.$inputsCuentaHibrida, false);
		}
	});

	$local.$exportar.on('click', function() {
		window.location.href = $variableUtil.root + "cuentaContableMiscelaneo?accion=exportar&rutaEnSidebar=" + $variableUtil.rutaEnSidebar;
	});

	function activarInputsCuentaHibrida(inputs, activo) {
		if (activo) {
			inputs.removeAttr("disabled");
		} else {
			inputs.attr("disabled", "disabled");
		}
	}

});