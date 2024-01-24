var $cuentaContables;
$(document).ready(function() {

	$cuentaContables = {
		compensaComisiones: "0",
		compensaFondos: "0"
	};

	var $local = {
		$tablaMantenimiento: $("#tablaMantenimiento"),
		tablaMantenimiento: "",
		$tablaCuentaComisiones: $("#tablaCuentaComisiones"),
		tablaCuentaComisiones: "",
		$modalMantenimiento: $("#modalMantenimiento"),
		$aniadirMantenimento: $("#aniadirMantenimiento"),
		$aniadirCuentaComision: $("#aniadirCuentaComision"),
		$botonesActualizacion: $("#botonesActualizacion"),
		$actualizarCuentaComision: $("#actualizarCuentaComision"),
		$cancelarCuentaComision: $("#cancelarCuentaComision"),
		$registrarMantenimiento: $("#registrarMantenimiento"),
		$actualizarMantenimiento: $("#actualizarMantenimiento"),
		cuentaContableReceptor: {},
		idConceptoComisionSeleccionado: "",
		$filaSeleccionada: "",
		$filaSeleccionadaCuentaComision: "",
		$compensaComisiones: $(".comisiones"),
		$compensaFondos: $(".fondos"),
		$mensajeCompensaComisiones: $("#mensajeCompensaComisiones"),
		$mensajeCompensaFondos: $("#mensajeCompensaFondos"),
		$ocultable: $(".ocultable"),
		$cuentasContables: $(".cuentaContable"),
		$a_comisiones: $("#a-comisiones"),
		$a_fondos: $("#a-fondos"),

		$clasesServicio: $("#clasesServicio"),
		$claseTransacciones: $("#claseTransacciones"),
		$codigoTransacciones: $("#codigoTransacciones"),
		$conceptoComisiones: $("#conceptoComisiones"),
		$membresias: $("#membresias"),
		$monedas: $("#monedas"),
		$origenes: $("#origenes"),
		$atms: $("#atms"),

		$claseTransaccionesFiltroParaTablaMantenimiento: $("#claseTransacciones-filtroParaTablaMantenimiento"),
		$clientesFiltroParaTablaMantenimiento: $("#clientes-FiltroParaTablaMantenimiento"),
		$conceptoComisionesFiltroParaTablaMantenimiento: $("#conceptoComisiones-FiltroParaTablaMantenimiento"),
		$monedasFiltroParaTablaMantenimiento: $("#monedas-filtroParaTablaMantenimiento"),
		$atmsFiltroParaTablaMantenimiento: $("#atms-filtroParaTablaMantenimiento"),
		$membresiasFiltroParaTablaMantenimiento: $("#membresias-filtroParaTablaMantenimiento"),
		$origenesFiltroParaTablaMantenimiento: $("#origenes-filtroParaTablaMantenimiento"),
		filtrosSeleccionables: {},

		$agregableClaseServicio: "",
		$agregableClaseTransaccion: "",
		$agregableCodigoTransaccion: "",
		$agregableMembresia: "",
		mostrarCuentasContables: function($codigoTransacciones) {
			var $opcionSeleccionada = $codigoTransacciones.find(":selected");
			$cuentaContables.compensaComisiones = $opcionSeleccionada.attr("data-compensaComisiones") || "0";
			$cuentaContables.compensaFondos = $opcionSeleccionada.attr("data-compensaFondos") || "0";
			if ($cuentaContables.compensaComisiones === undefined || $cuentaContables.compensaFondos === undefined) {
				return;
			}
			var esCompensaComisiones = $cuentaContables.compensaComisiones == "1";
			var esCompensaFondos = $cuentaContables.compensaFondos == "1";
			this.$compensaComisiones.toggleClass("hidden", !esCompensaComisiones);
			this.$compensaFondos.toggleClass("hidden", !esCompensaFondos);
			if (esCompensaComisiones) {
				this.$a_comisiones.tab("show");
			} else {
				this.$a_fondos.tab("show");
				this.tablaCuentaComisiones.clear().draw();
			}
			if (!esCompensaFondos) {
				$funcionUtil.prepararFormularioRegistro($formFondo);
			}
		}
	};

	$formMantenimiento = $("#formMantenimiento");
	$formComisiones = $("#formComisiones");
	$formFondo = $("#formFondo");

	$funcionUtil.crearSelect2($local.$atms, "Selecciona un ATM");
	$funcionUtil.crearSelect2($local.$claseTransacciones, "Selecciona una Clase de Transacci\u00F3n");
	$funcionUtil.crearSelect2($local.$clasesServicio, "Selecciona una Clase de Servicio");
	$funcionUtil.crearSelect2($local.$codigoTransacciones, "Seleccione una C\u00F3digo de Transacci\u00F3n");
	$funcionUtil.crearSelect2($local.$conceptoComisiones, "Seleccione una Concepto de Comisi\u00F3n");
	$funcionUtil.crearSelect2($local.$membresias, "Seleccione una Membres\u00EDa");
	$funcionUtil.crearSelect2($local.$monedas, "Seleccione una Moneda");
	$funcionUtil.crearSelect2($local.$origenes, "Seleccione un Origen");

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
					$local.$codigoTransacciones.append($("<option />").val(this.codigoTransaccion).text(this.codigoTransaccion + " - " + this.descripcion).attr({
						"data-compensaComisiones": codigoTransaccion.compensaComisiones,
						"data-compensaFondos": codigoTransaccion.compensaFondos
					}));
				});
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

	$local.$codigoTransacciones.on("change", function() {
		$local.mostrarCuentasContables($(this));
		$local.$cancelarCuentaComision.trigger("click");
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

	$local.tablaMantenimiento = $local.$tablaMantenimiento.DataTable({
		"ajax": {
			"url": $variableUtil.root + "cuentaContableReceptor?accion=buscarTodos",
			"dataSrc": ""
		},
		"language": {
			"emptyTable": "No hay Cuentas Contables registradas"
		},
		"initComplete": function() {
			$local.$tablaMantenimiento.wrap("<div class='table-responsive'></div>");
			$local.filtrosSeleccionables["0"] = $local.$atmsFiltroParaTablaMantenimiento.html();
			$local.filtrosSeleccionables["1"] = $local.$monedasFiltroParaTablaMantenimiento.html();
			$local.filtrosSeleccionables["2"] = $local.$membresiasFiltroParaTablaMantenimiento.html();
			$local.filtrosSeleccionables["4"] = $local.$origenesFiltroParaTablaMantenimiento.html();
			$local.filtrosSeleccionables["5"] = $local.$claseTransaccionesFiltroParaTablaMantenimiento.html();
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaMantenimiento, $local.filtrosSeleccionables);
			$local.$agregableMembresia = $local.$tablaMantenimiento.find("thead").find("select.agregable-membresia");
			$local.$agregableClaseServicio = $local.$tablaMantenimiento.find("thead").find("select.agregable-clase-servicio");
			$local.$agregableClaseTransaccion = $local.$tablaMantenimiento.find("thead").find("select.agregable-clase-transaccion");
			$local.$agregableCodigoTransaccion = $local.$tablaMantenimiento.find("thead").find("select.agregable-codigo-transaccion");
		},
		"columnDefs": [{
			"targets": 7,
			"className": "all dt-center",
			"defaultContent": $variableUtil.botonActualizar + " " + $variableUtil.botonEliminar
		}],
		"columns": [{
			"className": "all seleccionable select2 insertable-opciones-html",
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idATM, row.descripcionATM);
			},
			"title": "ATM"
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
			"data": null,
			"title": 'Acci\u00F3n'
		}]
	});

	$local.$tablaMantenimiento.find("thead").on("keyup", "input.filtrable", function() {
		$local.tablaMantenimiento.column($(this).parent().index() + ":visible").search(this.value).draw();
	});

	$local.$tablaMantenimiento.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
		if ($(this).hasClass("agregable-membresia")) {
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

	$local.tablaCuentaComisiones = $local.$tablaCuentaComisiones.DataTable({
		"ajax": {
			"dataSrc": ""
		},
		"lengthChange": false,
		"pageLength": 4,
		"language": {
			"emptyTable": "No hay Comisiones registradas"
		},
		"columnDefs": [{
			"targets": 0,
			"className": "all",
			"render": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idConceptoComision, row.descripcionConceptoComision);
			}
		}, {
			"targets": 4,
			"className": "all dt-center",
			"defaultContent": $variableUtil.botonActualizar + " " + $variableUtil.botonEliminar
		}],
		"columns": [{
			"data": null,
			"title": "Comisi\u00F3n"
		}, {
			"data": 'cuentaCargo',
			"title": "Cargo"
		}, {
			"data": 'cuentaAbono',
			"title": "Abono"
		}, {
			"data": 'codigoAnalitico',
			"title": "Anal\u00EDtico"
		}, {
			"data": null,
			"title": 'Acci\u00F3n'
		}]
	});

	$local.$tablaCuentaComisiones.wrap("<div class='table-responsive'></div>");

	$local.$aniadirCuentaComision.on("click", function() {
		if (!$formComisiones.valid()) {
			return;
		}
		var contabConceptoCuenta = $formComisiones.serializeJSON();
		contabConceptoCuenta.descripcionConceptoComision = $local.$conceptoComisiones.find(":selected").attr("descripcion");
		contabConceptoCuenta.tipoCompensacion = $variableUtil.tipoCompensacionComision;
		var row = $local.tablaCuentaComisiones.row.add(contabConceptoCuenta).draw();
		row.show().draw(false);
		$(row.node()).animateHighlight();
		$funcionUtil.prepararFormularioRegistro($formComisiones);
	});

	$local.$tablaCuentaComisiones.children("tbody").on("click", ".actualizar", function() {
		if ($local.$filaSeleccionada == "") {
			$local.$conceptoComisiones.removeClass("elemento-desactivable");
			$funcionUtil.prepararFormularioActualizacion($formComisiones);
			$local.$filaSeleccionadaCuentaComision = $(this).parents("tr");
			var contabConceptoCuenta = $local.tablaCuentaComisiones.row($local.$filaSeleccionadaCuentaComision).data();
			$funcionUtil.llenarFormulario(contabConceptoCuenta, $formComisiones);
			$local.$aniadirCuentaComision.addClass("hidden");
			$local.$botonesActualizacion.removeClass("hidden");
			$local.$conceptoComisiones.addClass("elemento-desactivable");
		} else {
			$funcionUtil.prepararFormularioActualizacion($formComisiones);
			$local.$filaSeleccionadaCuentaComision = $(this).parents("tr");
			var contabConceptoCuenta = $local.tablaCuentaComisiones.row($local.$filaSeleccionadaCuentaComision).data();
			$local.idConceptoComisionSeleccionado = contabConceptoCuenta.idConceptoComision;
			$funcionUtil.llenarFormulario(contabConceptoCuenta, $formComisiones);
			$local.$aniadirCuentaComision.addClass("hidden");
			$local.$botonesActualizacion.removeClass("hidden");
		}
	});

	$local.$actualizarCuentaComision.on("click", function() {
		if (!$formComisiones.valid()) {
			return;
		}
		var contabConceptoCuenta = $formComisiones.serializeJSON();
		contabConceptoCuenta.descripcionConceptoComision = $local.$conceptoComisiones.find(":selected").attr("descripcion");
		contabConceptoCuenta.tipoCompensacion = $variableUtil.tipoCompensacionComision;
		contabConceptoCuenta.idConceptoComision = $local.idConceptoComisionSeleccionado;
		$local.tablaCuentaComisiones.row($local.$filaSeleccionadaCuentaComision).remove().draw(false);
		var row = $local.tablaCuentaComisiones.row.add(contabConceptoCuenta).draw();
		row.show().draw(false);
		$(row.node()).animateHighlight();
		$local.$cancelarCuentaComision.trigger("click");
	});

	$local.$cancelarCuentaComision.on("click", function() {
		$local.$aniadirCuentaComision.removeClass("hidden");
		$local.$botonesActualizacion.addClass("hidden");
		$funcionUtil.prepararFormularioRegistro($formComisiones);
		$local.$filaSeleccionadaCuentaComision = "";
		$local.idConceptoComisionSeleccionado = "";
	});

	$local.$tablaCuentaComisiones.children("tbody").on("click", ".eliminar", function() {
		$local.$filaSeleccionadaCuentaComision = $(this).parents("tr");
		var contabConceptoCuenta = $local.tablaCuentaComisiones.row($local.$filaSeleccionadaCuentaComision).data();
		if (contabConceptoCuenta.idConceptoComision == $local.$conceptoComisiones.val() && !$local.$botonesActualizacion.hasClass("hidden")) {
			$funcionUtil.notificarException("La Comisi\u00F3n no puede ser eliminada hasta que finalize la actualizaci\u00F3n.", "fa-warning", "Aviso", "warning");
			return;
		}
		$.confirm({
			icon: "fa fa-info-circle",
			title: "Aviso",
			content: "\u00BFDesea eliminar la Cuenta Comisi\u00F3n <b>" + $funcionUtil.unirCodigoDescripcion(contabConceptoCuenta.idConceptoComision, contabConceptoCuenta.descripcionConceptoComision) + "</b> ?",
			buttons: {
				Aceptar: {
					action: function() {
						$local.tablaCuentaComisiones.row($local.$filaSeleccionadaCuentaComision).remove().draw(false);
						$local.$filaSeleccionadaCuentaComision = "";
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

	$formComisiones.find("input").keypress(function(event) {
		if (event.which == 13) {
			if (!$local.$aniadirCuentaComision.hasClass("hidden")) {
				$local.$aniadirCuentaComision.trigger("click");
				return false;
			} else {
				if (!$local.$botonesActualizacion.hasClass("hidden")) {
					$local.$actualizarCuentaComision.trigger("click");
				}
				return false;
			}
		}
	});

	$local.$modalMantenimiento.PopupWindow({
		title: "Mantenimiento de Cuentas Contables Receptor",
		autoOpen: false,
		modal: false,
		height: 440,
		width: 626,
	});

	$local.$aniadirMantenimento.on("click", function() {
		$local.tablaCuentaComisiones.clear().draw();
		$funcionUtil.prepararFormularioRegistro($formMantenimiento);
		$local.$actualizarMantenimiento.addClass("hidden");
		$local.$registrarMantenimiento.removeClass("hidden");
		$local.$modalMantenimiento.PopupWindow("open");
		$local.$modalMantenimiento.PopupWindow("maximize");
		$local.$filaSeleccionada = "";
	});

	$local.$modalMantenimiento.on("close.popupwindow", function() {
		$local.$filaSeleccionada = "";
		$local.cuentaContableReceptor = {};
		$local.$ocultable.addClass("hidden");
	});

	$local.$registrarMantenimiento.on("click", function() {
		var esValido = true;
		var contabConceptosCuentas = [];
		if (!$formMantenimiento.valid()) {
			esValido = false;
		}
		if ($cuentaContables.compensaComisiones == "0" && $cuentaContables.compensaFondos == "0") {
			$funcionUtil.notificarException("La Cuenta Contable debe tener asociado un <b>C\u00F3digo de Transacci\u00F3n</b> con al menos un <b>Tipo de Compensaci\u00F3n</b>", "fa-warning", "Aviso", "warning");
			esValido = false;
		}
		if ($cuentaContables.compensaFondos == "1" && !$formFondo.valid()) {
			$funcionUtil.notificarException("A\u00F1ada una Cuenta de Fondo", "fa-warning", "Aviso", "warning");
			$local.$a_fondos.tab("show");
			esValido = false;
		}
		if ($cuentaContables.compensaComisiones == "1" && $local.tablaCuentaComisiones.rows().data().length === 0) {
			$funcionUtil.notificarException("A\u00F1ada al menos una Cuenta de Comisi\u00F3n", "fa-warning", "Aviso", "warning");
			$local.$a_comisiones.tab("show");
			esValido = false;
		}
		if (!esValido) {
			return;
		}
		var cuentaContableReceptor = $formMantenimiento.serializeJSON();
		if ($cuentaContables.compensaComisiones == "1") {
			contabConceptosCuentas = $local.tablaCuentaComisiones.rows({
				selected: true
			}).data().toArray();
		}
		if ($cuentaContables.compensaFondos == "1") {
			var contabConceptoCuenta = $formFondo.serializeJSON();
			contabConceptoCuenta.idConceptoComision = "0";
			contabConceptoCuenta.tipoCompensacion = $variableUtil.tipoCompensacionFondo;
			contabConceptosCuentas.push(contabConceptoCuenta);
		}
		cuentaContableReceptor.contabConceptosCuentas = contabConceptosCuentas;
		$.ajax({
			type: "POST",
			url: $variableUtil.root + "cuentaContableReceptor",
			data: JSON.stringify(cuentaContableReceptor),
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
			success: function(cuentasContablesReceptor) {
				$funcionUtil.notificarException($variableUtil.registroExitoso, "fa-check", "Aviso", "success");
				var cuentaContableReceptor = cuentasContablesReceptor[0];
				var row = $local.tablaMantenimiento.row.add(cuentaContableReceptor).draw();
				row.show().draw(false);
				$(row.node()).animateHighlight();
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
		$local.$ocultable.addClass("hidden");
		$local.tablaCuentaComisiones.clear().draw();
		$funcionUtil.prepararFormularioActualizacion($formMantenimiento);
		$local.$filaSeleccionada = $(this).parents("tr");
		$local.cuentaContableReceptor = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		$funcionUtil.llenarFormulario($local.cuentaContableReceptor, $formMantenimiento);
		$local.$claseTransacciones.trigger("change", [$local.cuentaContableReceptor.codigoTransaccion]);
		$local.$membresias.trigger("change", [$local.cuentaContableReceptor.codigoClaseServicio]);
		$local.$actualizarMantenimiento.removeClass("hidden");
		$local.$registrarMantenimiento.addClass("hidden");
		$local.$modalMantenimiento.PopupWindow("open");
		$local.$modalMantenimiento.PopupWindow("maximize");
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "cuentaContableReceptor?accion=buscarPorEscenario&" + $.param($local.cuentaContableReceptor),
			success: function(cuentasContablesReceptor) {
				var cuentaContableReceptor = cuentasContablesReceptor[0];
				$.each(cuentaContableReceptor.contabConceptosCuentas, function(i, contabConceptoCuenta) {
					if (contabConceptoCuenta.tipoCompensacion == $variableUtil.tipoCompensacionComision) {
						$local.tablaCuentaComisiones.row.add(contabConceptoCuenta).draw();
					} else {
						$funcionUtil.prepararFormularioActualizacion($formFondo);
						$funcionUtil.llenarFormulario(contabConceptoCuenta, $formFondo);
					}
				});
			}
		});
	});

	$local.$actualizarMantenimiento.on("click", function() {
		var esValido = true;
		var contabConceptosCuentas = [];
		if ($cuentaContables.compensaComisiones == "0" && $cuentaContables.compensaFondos == "0") {
			$funcionUtil.notificarException("La Cuenta Contable debe tener asociado un <b>C\u00F3digo de Transacci\u00F3n</b> con al menos un <b>Tipo de Compensaci\u00F3n</b>", "fa-warning", "Aviso", "warning");
			esValido = false;
		}
		if ($cuentaContables.compensaFondos == "1" && !$formFondo.valid()) {
			$local.$a_fondos.tab("show");
			esValido = false;
		}
		if ($cuentaContables.compensaComisiones == "1" && $local.tablaCuentaComisiones.rows().data().length === 0) {
			$funcionUtil.notificarException("A\u00F1ada al menos una Cuenta de Comisi\u00F3n", "fa-warning", "Aviso", "warning");
			$local.$a_comisiones.tab("show");
			esValido = false;
		}
		if (!esValido) {
			return;
		}
		if ($cuentaContables.compensaComisiones == "1") {
			contabConceptosCuentas = $local.tablaCuentaComisiones.rows({
				selected: true
			}).data().toArray();
		}
		if ($cuentaContables.compensaFondos == "1") {
			var contabConceptoCuenta = $formFondo.serializeJSON();
			contabConceptoCuenta.idConceptoComision = "0";
			contabConceptoCuenta.tipoCompensacion = $variableUtil.tipoCompensacionFondo;
			contabConceptosCuentas.push(contabConceptoCuenta);
		}
		$local.cuentaContableReceptor.contabConceptosCuentas = contabConceptosCuentas;
		$.ajax({
			type: "PUT",
			url: $variableUtil.root + "cuentaContableReceptor",
			data: JSON.stringify($local.cuentaContableReceptor),
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
			success: function(cuentasContablesReceptor) {
				$funcionUtil.notificarException($variableUtil.actualizacionExitosa, "fa-check", "Aviso", "success");
				$local.tablaMantenimiento.row($local.$filaSeleccionada).remove().draw(false);
				var cuentaContableReceptor = cuentasContablesReceptor[0];
				var row = $local.tablaMantenimiento.row.add(cuentaContableReceptor).draw();
				row.show().draw(false);
				$(row.node()).animateHighlight();
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
		var cuentaContableReceptor = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		$.confirm({
			icon: "fa fa-info-circle",
			title: "Aviso",
			content: "\u00BFDesea eliminar el <b>Escenario y las Cuentas Contables Emisor asociadas</b>?",
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
									url: $variableUtil.root + "cuentaContableReceptor",
									data: JSON.stringify(cuentaContableReceptor),
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
											var mensaje = $funcionUtil.obtenerMensajeError("La Cuenta Contable Receptor ", xhr.responseJSON, $variableUtil.accionEliminado);
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
});