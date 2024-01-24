$(document).ready(function() {
	var $local = {
		$tablaMantenimiento: $("#tablaMantenimiento"),
		tablaMantenimiento: "",
		$modalMantenimiento: $("#modalMantenimiento"),
		$modalMantenimientoIdCuenta: $("#divIdCuenta"),
		$aniadirMantenimento: $("#aniadirMantenimiento"),
		$registrarMantenimiento: $("#registrarMantenimiento"),
		$filaSeleccionada: "",
		$actualizarMantenimiento: $("#actualizarMantenimiento"),
		$exportar: $('#exportar'),
		codigoCuentaSeleccionada: "",
		$monedas: $("#monedas"),
		$tipoCuenta: $("#cuenta"),
		arregloSiNo: ["0", "1"],
		filtrosSeleccionables: [],
		// Permisos
		permisoActualizacion: false,
		permisoEliminacion: false
	};

	$formMantenimiento = $("#formMantenimiento");

	$funcionUtil.crearSelect2($local.$monedas);
	$funcionUtil.crearSelect2($local.$tipoCuenta);

	$.fn.dataTable.ext.errMode = 'none';

	$local.$tablaMantenimiento.on('xhr.dt', function(e, settings, json, xhr) {
		if (xhr.status == 500) {
			$local.tablaMantenimiento.clear().draw();
		}
	});

	$local.permisoActualizacion = $local.$tablaMantenimiento.attr("data-permiso-actualizacion") || false;
	$local.permisoEliminacion = $local.$tablaMantenimiento.attr("data-permiso-eliminacion") || false;

	$local.tablaMantenimiento = $local.$tablaMantenimiento.DataTable({
		"ajax": {
			"url": $variableUtil.root + "cuentaContable?accion=buscarTodos",
			"dataSrc": ""
		},
		"language": {
			"emptyTable": "No hay Cuentas Contables registradas."
		},
		"initComplete": function() {
			$local.$tablaMantenimiento.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaMantenimiento, $local.filtrosSeleccionables);
		},
		"columnDefs": [{
			"targets": [0, 1, 2, 3, 4],
			"className": "all filtrable",
		}, {
			"targets": 5,
			"className": "all dt-center",
			"width": "10%",
			"render": function() {
				return "" + ($local.permisoActualizacion == "true" ? $variableUtil.botonActualizar : "") + " " + ($local.permisoEliminacion == "true" ? $variableUtil.botonEliminar : "");
			}
		}],
		"columns": [
			{
				"data": 'numeroCuentaContable',
				"title": "Cuenta Contable"
			}, {
				"data": 'descripcion',
				"title": 'Descripci\u00F3n'
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.tipoCuenta, row.descripcionTipoCuenta);
				},
				"title": "Tipo Cuenta"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.codigoMoneda, row.descripcionMoneda);
				},
				"title": "Moneda"
			}, {
				"data": "longitudCuenta",
				"title": "Longitud Cuenta"
			}, {
				"data": null,
				"title": 'Acci\u00F3n'
			}
		]
	});

	$local.$tablaMantenimiento.find("thead").on('keyup', 'input', function() {
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaMantenimiento.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	$local.$modalMantenimiento.PopupWindow({
		title: "Mantenimiento de Cuenta Contable",
		autoOpen: false,
		modal: false,
		height: 500,
		width: 626,
	});

	$local.$aniadirMantenimento.on("click", function() {
		$funcionUtil.prepararFormularioRegistro($formMantenimiento);
		$local.$actualizarMantenimiento.addClass("hidden");
		$local.$modalMantenimientoIdCuenta.addClass("hidden");
		$local.$registrarMantenimiento.removeClass("hidden");
		$local.$modalMantenimiento.PopupWindow("open");
		$formMantenimiento.find("input[name='idCuenta']").parent().parent().hide();
	});

	$local.$modalMantenimiento.on("open.popupwindow", function() {
		$formMantenimiento.find("input:not([disabled]):first").focus();
	});

	$local.$modalMantenimiento.on("close.popupwindow", function() {
		$local.codigoCuentaSeleccionada = "";
		$local.$filaSeleccionada = "";
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
		var cuentaContable = $formMantenimiento.serializeJSON();
		$.ajax({
			type: "POST",
			url: $variableUtil.root + "cuentaContable",
			data: JSON.stringify(cuentaContable),
			beforeSend: function(xhr) {
				$local.$registrarMantenimiento.attr("disabled", true).find("i").removeClass("fa-floppy-o").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode: {
				400: function(response) {
					$funcionUtil.limpiarMensajesDeError($formMantenimiento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formMantenimiento);
				}
			},
			success: function(response) {
				if (response.respuesta == null) {
					$funcionUtil.notificarException($variableUtil.registroExitoso, "fa-check", "Aviso", "success");
					var row = $local.tablaMantenimiento.row.add(response[0]).draw();
					row.show().draw(false);
					$(row.node()).animateHighlight();
					return;
				}
				if (response.respuesta.codigo == "400") {
					$funcionUtil.notificarException($variableUtil.registroRepetido, "fa-warning", "Aviso", "warning");
					return;
				}
				if (response.respuesta.codigo == "201") {
					$funcionUtil.notificarException($variableUtil.registroExitoso, "fa-check", "Aviso", "success");
					var row = $local.tablaMantenimiento.row.add(response[0]).draw();
					row.show().draw(false);
					$(row.node()).animateHighlight();
					return;
				}
				if (response.respuesta.codigo != "400" && response.respuesta.codigo != "201") {
					$funcionUtil.notificarException($variableUtil.errorIntegracion, "fa-warning", "Aviso", "warning");
					return;
				}
				$local.$modalMantenimiento.PopupWindow("close");
			},
			error: function(response) {
				if (response == null) {
					$funcionUtil.notificarException($variableUtil.registroRepetido, "fa-warning", "Aviso", "warning");
					return;
				}
			},
			complete: function(response) {
				$local.$registrarMantenimiento.attr("disabled", false).find("i").addClass("fa-floppy-o").removeClass("fa-spinner fa-pulse fa-fw");
				$local.$modalMantenimiento.PopupWindow("close");
			}
		});
	});

	$local.$tablaMantenimiento.children("tbody").on("click", ".actualizar", function() {
		$funcionUtil.prepararFormularioActualizacion($formMantenimiento);
		$local.$filaSeleccionada = $(this).parents("tr");
		var cuentaContable = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		cuentaContable.tipoCuenta = !!cuentaContable.tipoCuenta ? cuentaContable.tipoCuenta.trim() : cuentaContable.tipoCuenta;
		$local.codigoCuentaSeleccionada = cuentaContable.idCuenta;
		$funcionUtil.llenarFormulario(cuentaContable, $formMantenimiento);
		$local.$modalMantenimientoIdCuenta.removeClass("hidden");
		$local.$actualizarMantenimiento.removeClass("hidden");
		$local.$registrarMantenimiento.addClass("hidden");
		$local.$modalMantenimiento.PopupWindow("open");
		$formMantenimiento.find("input[name='idCuenta']").parent().parent().show();
	});

	$local.$actualizarMantenimiento.on("click", function() {
		if (!$formMantenimiento.valid()) {
			return;
		}
		var cuentaContable = $formMantenimiento.serializeJSON();
		cuentaContable.idCuenta = $local.codigoCuentaSeleccionada;
		var cuentaContableFila = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		cuentaContable.idCuentaAlegra = cuentaContableFila.idCuentaAlegra;
		$.ajax({
			type: "PUT",
			url: $variableUtil.root + "cuentaContable",
			data: JSON.stringify(cuentaContable),
			beforeSend: function(xhr) {
				$local.$actualizarMantenimiento.attr("disabled", true).find("i").removeClass("fa-pencil-square").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode: {
				400: function(response) {
					$funcionUtil.limpiarMensajesDeError($formMantenimiento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formMantenimiento);
				}
			},
			success: function(response) {
				$funcionUtil.notificarException($variableUtil.actualizacionExitosa, "fa-check", "Aviso", "success");
				if ($local.$tablaMantenimiento.length > 0) {
					var row = $local.tablaMantenimiento.row($local.$filaSeleccionada).data(response[0]).draw();
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
		var cuentaContable = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		$.confirm({
			icon: "fa fa-info-circle",
			title: "Aviso",
			content: "\u00BFDesea eliminar la Cuenta Contable <b>'" + cuentaContable.idCuenta + " - " + cuentaContable.numeroCuentaContable + "'</b>?",
			theme: "bootstrap",
			buttons: {
				Aceptar: {
					action: function() {
						var confirmar = $.confirm({
							icon: 'fa fa-spinner fa-pulse fa-fw',
							title: "Eliminando...",
							theme: "bootstrap",
							content: function() {
								var self = this;
								self.buttons.close.hide();
								$.ajax({
									type: "DELETE",
									url: $variableUtil.root + "cuentaContable",
									data: JSON.stringify(cuentaContable),
									autoclose: true,
									beforeSend: function(xhr) {
										xhr.setRequestHeader('Content-Type', 'application/json');
										xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
									}
								}).done(function(response) {
									$funcionUtil.notificarException(response, "fa-check", "Aviso", "success");
									$local.tablaMantenimiento.row($local.$filaSeleccionada).remove().draw(false);
								}).fail(function(xhr) {
									switch (xhr.status) {
										case 400:
											$funcionUtil.notificarException($funcionUtil.obtenerMensajeErrorEnCadena(xhr.responseJSON), "fa-warning", "Aviso", "warning");
											break;
										case 409:
											var mensaje = $funcionUtil.obtenerMensajeError("La cuenta <b>" + cuentaContable.idCuenta + " - " + cuentaContable.numeroCuentaContable + "</b>", xhr.responseJSON, $variableUtil.accionEliminado);
											$funcionUtil.notificarException(mensaje, "fa-warning", "Aviso", "warning");
											break;
									}
								}).always(function(xhr) {
									confirmar.close();
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

	$local.$exportar.on('click', function() {
		window.location.href = $variableUtil.root + "cuentaContable?accion=exportar&rutaEnSidebar=" + $variableUtil.rutaEnSidebar;
	});
});