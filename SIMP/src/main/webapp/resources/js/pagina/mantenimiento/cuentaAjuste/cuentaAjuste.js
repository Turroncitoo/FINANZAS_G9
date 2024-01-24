$(document).ready(function() {

	var $local = {
		$tablaMantenimiento: $("#tablaMantenimiento"),
		tablaMantenimiento: "",
		$modalMantenimiento: $("#modalMantenimiento"),
		$aniadirMantenimento: $("#aniadirMantenimiento"),
		$registrarMantenimiento: $("#registrarMantenimiento"),
		$filaSeleccionada: "",
		$actualizarMantenimiento: $("#actualizarMantenimiento"),
		cuentaSeleccionada: {},
		$rolTransaccionSelect: $("#rolTransaccionSelect"),
		$registroContableSelect: $("#registroContableSelect"),
		$monedaSelect: $("#monedaSelect"),
		$tipoMovimientoSelect: $("#tipoMovimientoSelect"),
		$exportar: $('#exportar'),
		// Permisos
		permisoActualizacion: false,
		permisoEliminacion: false
	};

	$formMantenimiento = $("#formMantenimiento");

	$funcionUtil.crearSelect2($local.$rolTransaccionSelect, "Seleccione un Rol");
	$funcionUtil.crearSelect2($local.$registroContableSelect, "Seleccione un Registro Contable");
	$funcionUtil.crearSelect2($local.$monedaSelect, "Seleccione una Moneda");
	$funcionUtil.crearSelect2($local.$tipoMovimientoSelect, "Seleccione un Tipo");

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
			"url": $variableUtil.root + "cuentaAjuste?accion=buscarTodos",
			"dataSrc": ""
		},
		"language": {
			"emptyTable": "No hay Cuentas Ajustes registradas."
		},
		"initComplete": function() {
			$local.$tablaMantenimiento.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaMantenimiento);
		},
		"columnDefs": [{
			"targets": [0, 1, 2, 3, 4, 5, 6],
			"className": "all filtrable",
		}, {
			"targets": 7,
			"className": "all dt-center",
			"width": "10%",
			"render": function() {
				return "" + ($local.permisoActualizacion == "true" ? $variableUtil.botonActualizar : "") + " " + ($local.permisoEliminacion == "true" ? $variableUtil.botonEliminar : "");
			}
		}],
		"order": [[0, 'asc'], [1, 'asc'], [2, 'asc'], [3, 'asc']],
		"columns": [{
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.rolTransaccion, row.descripcionRolTransaccion);
			},
			"title": "Rol"
		}, {
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.monedaCompensacion, row.descripcionMonedaCompensacion);
			},
			"title": "Moneda Compensaci\u00F3n"
		}, {
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.tipoMovimiento, row.descripcionTipoMovimiento);
			},
			"title": "Tipo Movimiento"
		}, {
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.registroContable, row.descripcionRegistroContable);
			},
			"title": "Registro Contable"
		}, {
			"data": 'cuentaCargo',
			"title": "Cuenta Cargo"
		}, {
			"data": 'cuentaAbono',
			"title": 'Cuenta Abono'
		}, {
			"data": 'codigoAnalitico',
			"title": 'C\u00F3digo Anal\u00EDtico'
		}, {
			"data": null,
			"title": 'Acci\u00F3n'
		}]
	});

	$local.$tablaMantenimiento.find("thead").on('keyup', 'input', function() {
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaMantenimiento.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	$local.$modalMantenimiento.PopupWindow({
		title: "Mantenimiento de Cuenta Ajuste",
		autoOpen: false,
		modal: false,
		height: 540,
		width: 626,
	});

	$local.$aniadirMantenimento.on("click", function() {
		$funcionUtil.prepararFormularioRegistro($formMantenimiento);
		$local.$actualizarMantenimiento.addClass("hidden");
		$local.$registrarMantenimiento.removeClass("hidden");
		$local.$modalMantenimiento.PopupWindow("open");
	});

	$local.$modalMantenimiento.on("open.popupwindow", function() {
		$formMantenimiento.find("input:not([disabled]):first").focus();
	});

	$local.$modalMantenimiento.on("close.popupwindow", function() {
		$local.cuentaSeleccionada = {};
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
		var cuentaAjuste = $formMantenimiento.serializeJSON();
		$.ajax({
			type: "POST",
			url: $variableUtil.root + "cuentaAjuste",
			data: JSON.stringify(cuentaAjuste),
			beforeSend: function(xhr) {
				$local.$registrarMantenimiento.attr("disabled", true).find("i").removeClass("fa-floppy-o").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode: {
				400: function(response) {
					$funcionUtil.limpiarMensajesDeError($formMantenimiento);
					$funcionUtil.notificarException($funcionUtil.obtenerMensajeErrorEnCadena(response.responseJSON), "fa-warning", "Aviso", "warning");
				}
			},
			success: function(response) {
				$funcionUtil.notificarException($variableUtil.registroExitoso, "fa-check", "Aviso", "success");
				if ($local.$tablaMantenimiento.length > 0) {
					var row = $local.tablaMantenimiento.row.add(response[0]).draw();
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
		var cuentaAjuste = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		$local.cuentaSeleccionada = cuentaAjuste;
		$funcionUtil.llenarFormulario(cuentaAjuste, $formMantenimiento);
		$local.$actualizarMantenimiento.removeClass("hidden");
		$local.$registrarMantenimiento.addClass("hidden");
		$local.$modalMantenimiento.PopupWindow("open");
	});

	$local.$actualizarMantenimiento.on("click", function() {
		if (!$formMantenimiento.valid()) {
			return;
		}
		var cuentaAjuste = $formMantenimiento.serializeJSON();
		cuentaAjuste.nuevoRolTransaccion = cuentaAjuste.rolTransaccion;
		cuentaAjuste.nuevoMonedaCompensacion = cuentaAjuste.monedaCompensacion;
		cuentaAjuste.nuevoTipoMovimiento = cuentaAjuste.tipoMovimiento;
		cuentaAjuste.nuevoRegistroContable = cuentaAjuste.registroContable;
		cuentaAjuste.rolTransaccion = $local.cuentaSeleccionada.rolTransaccion;
		cuentaAjuste.monedaCompensacion = $local.cuentaSeleccionada.monedaCompensacion;
		cuentaAjuste.tipoMovimiento = $local.cuentaSeleccionada.tipoMovimiento;
		cuentaAjuste.registroContable = $local.cuentaSeleccionada.registroContable;
		$.ajax({
			type: "PUT",
			url: $variableUtil.root + "cuentaAjuste",
			data: JSON.stringify(cuentaAjuste),
			beforeSend: function(xhr) {
				$local.$actualizarMantenimiento.attr("disabled", true).find("i").removeClass("fa-pencil-square").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode: {
				400: function(response) {
					$funcionUtil.limpiarMensajesDeError($formMantenimiento);
					$funcionUtil.notificarException($funcionUtil.obtenerMensajeErrorEnCadena(response.responseJSON), "fa-warning", "Aviso", "warning");
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
		var cuentaAjuste = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		$.confirm({
			icon: "fa fa-info-circle",
			title: "Aviso",
			content: "\u00BFDesea eliminar la Cuenta de Ajuste <b>'" + cuentaAjuste.descripcionRolTransaccion + " - " + cuentaAjuste.descripcionMonedaCompensacion + " - " + cuentaAjuste.descripcionTipoMovimiento + " - " + cuentaAjuste.descripcionRegistroContable + "'</b>?",
			theme: "bootstrap",
			type: "orange",
			buttons: {
				Aceptar: {
					action: function() {
						var confirmar = $.confirm({
							icon: 'fa fa-spinner fa-pulse fa-fw',
							title: "Eliminando...",
							theme: "bootstrap",
							type: "blue",
							content: function() {
								var self = this;
								self.buttons.close.hide();
								$.ajax({
									type: "DELETE",
									url: $variableUtil.root + "cuentaAjuste",
									data: JSON.stringify(cuentaAjuste),
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
											var mensaje = $funcionUtil.obtenerMensajeError("La Cuenta de Ajuste <b>" + cuentaAjuste.descripcionRolTransaccion + " - " + cuentaAjuste.descripcionMonedaCompensacion + " - " + cuentaAjuste.descripcionTipoMovimiento + " - " + cuentaAjuste.descripcionRegistroContable + "</b>", xhr.responseJSON, $variableUtil.accionEliminado);
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
					btnClass: "btn-warning"
				},
				Cancelar: {
					keys: ['esc']
				},
			}
		});
	});

	$local.$exportar.on('click', function() {
		window.location.href = $variableUtil.root + "cuentaAjuste?accion=exportar&rutaEnSidebar=" + $variableUtil.rutaEnSidebar;
	});

});