$(document).ready(function() {

	var $local = {
		$tablaMantenimiento: $("#tablaMantenimiento"),
		tablaMantenimiento: "",
		$modalMantenimiento: $("#modalMantenimiento"),
		$aniadirMantenimento: $("#aniadirMantenimiento"),
		$registrarMantenimiento: $("#registrarMantenimiento"),
		$filaSeleccionada: "",
		$actualizarMantenimiento: $("#actualizarMantenimiento"),
		codigoOrigenSeleccionado: 0,
		$selectComercio: $("#selectComercio"),
		$selectEmpresa: $("#selectEmpresa"),
		$selectCliente: $("#selectCliente"),
		$selectOperacion: $("#selectOperacion"),
		$selectMoneda: $("#selectMoneda"),
		comercioSeleccionado: '',
		empresaSeleccionado: '',
		clienteSeleccionado: '',
		operacionSeleccionado: '',
		monedaSeleccionado: '',
		// Permisos
		permisoActualizacion: false,
		permisoEliminacion: false
	};

	$formMantenimiento = $("#formMantenimiento");
	$funcionUtil.crearSelect2($local.$selectComercio);
	$funcionUtil.crearSelect2($local.$selectEmpresa);
	$funcionUtil.crearSelect2($local.$selectCliente);
	$funcionUtil.crearSelect2($local.$selectOperacion);
	$funcionUtil.crearSelect2($local.$selectMoneda);

	$local.$selectEmpresa.on('change', function(event, opcionSeleccionada) {
		var codigoEmpresa = $(this).val();
		if (codigoEmpresa == null || codigoEmpresa == undefined) {
			$local.$selectCliente.find('option:not(:eq(0))').remove();
			return;
		}
		$.ajax({
			type: 'GET',
			url: $variableUtil.root + '/cliente/empresa/' + codigoEmpresa,
			beforeSend: function(xhr) {
				$local.$selectCliente.find('option:not(:eq(0))').remove();
				$local.$selectCliente.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando clientes...</span>")
			},
			statusCode: {
				400: function(response) {
					$funciionUtil.limpiarMensajesDeError($formMantenimiento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formMantenimiento);
				}
			},
			success: function(response) {
				$.each(response, function(i, claseServicio) {
					$local.$selectCliente.append($('<option />').val(this.idCliente).text(this.idCliente + ' - ' + this.descripcion));
				});
				if (opcionSeleccionada != null && opcionSeleccionada != undefined) {
					$local.$selectCliente.val(opcionSeleccionada).trigger('change.select2');
				}
			},
			complete: function() {
				$local.$selectCliente.parent().find('.cargando').remove();
			}
		});
	})

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
			"url": $variableUtil.root + "mantenimientos/recaudacionRecargas?accion=buscarTodos",
			"dataSrc": ""
		},
		"language": {
			"emptyTable": "No hay origenes registrados"
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
		"columns": [
			{
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.idComercio, row.descComercio);
				},
				"title": "Comercio"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.idEmpresa, row.descEmpresa);
				},
				"title": "Empresa"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.idCliente, row.descCliente);
				},
				"title": "Cliente"
			}, {
				"data": "operacion",
				"title": "Tipo Operaci&oacute;n"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.monedaRecarga, row.descMonedaRecarga);
				},
				"title": "Moneda"
			}, {
				"data": 'cuentaCargo',
				"title": "Cuenta Cargo"
			}, {
				"data": 'cuentaAbono',
				"title": "Cuenta Abono"
			}, {
				"data": null,
				"title": 'Acci&oacute;n'
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
		title: "Mantenimiento de Recaudaci\u00F3n Recargas",
		autoOpen: false,
		modal: false,
		height: 550,
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
		$local.codigoOrigenSeleccionado = 0;
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
		var reglaContable = $formMantenimiento.serializeJSON();
		$.ajax({
			type: "POST",
			url: $variableUtil.root + "mantenimientos/recaudacionRecargas",
			data: JSON.stringify(reglaContable),
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
		var reglaContable = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		$local.comercioSeleccionado = reglaContable.idComercio;
		$local.clienteSeleccionado = reglaContable.idCliente;
		$local.empresaSeleccionado = reglaContable.idEmpresa;
		$local.operacionSeleccionado = reglaContable.operacion;
		$local.monedaSeleccionado = reglaContable.monedaRecarga;
		$funcionUtil.llenarFormulario(reglaContable, $formMantenimiento);
		$local.$selectEmpresa.trigger('change', [reglaContable.idCliente]);
		$local.$actualizarMantenimiento.removeClass("hidden");
		$local.$registrarMantenimiento.addClass("hidden");
		$local.$modalMantenimiento.PopupWindow("open");
	});

	$local.$actualizarMantenimiento.on("click", function() {
		if (!$formMantenimiento.valid()) {
			return;
		}
		var reglaContable = $formMantenimiento.serializeJSON();
		reglaContable.idComercio = $local.comercioSeleccionado;
		reglaContable.idCliente = $local.clienteSeleccionado;
		reglaContable.idEmpresa = $local.empresaSeleccionado;
		reglaContable.operacion = $local.operacionSeleccionado;
		reglaContable.monedaRecarga = $local.monedaSeleccionado;
		$.ajax({
			type: "PUT",
			url: $variableUtil.root + "mantenimientos/recaudacionRecargas",
			data: JSON.stringify(reglaContable),
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
					$local.tablaMantenimiento.row($local.$filaSeleccionada).remove().draw(false);
					var r = response[0];
					var row = $local.tablaMantenimiento.row.add(r).draw();
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
		var reglaContable = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		$.confirm({
			icon: "fa fa-info-circle",
			title: "Aviso",
			content: "\u00BFDesea eliminar la Regla Contable <b>'" + reglaContable.idComercio + " " + reglaContable.descComercio + " - " + reglaContable.idCliente + " " + reglaContable.descCliente + "'</b>?",
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
									url: $variableUtil.root + "mantenimientos/recaudacionRecargas",
									data: JSON.stringify(reglaContable),
									autoclose: true,
									beforeSend: function(xhr) {
										xhr.setRequestHeader('Content-Type', 'application/json');
										xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
									}
								}).done(function(response) {
									$funcionUtil.notificarException(response, "fa-check", "Aviso", "success");
									$local.tablaMantenimiento.row($local.$filaSeleccionada).remove().draw(false);
									confirmar.close();
								}).fail(function(xhr) {
									confirmar.close();
									switch (xhr.status) {
										case 400:
											$funcionUtil.notificarException($funcionUtil.obtenerMensajeErrorEnCadena(xhr.responseJSON), "fa-warning", "Aviso", "warning");
											break;
										case 409:
											var mensaje = $funcionUtil.obtenerMensajeError(" La Regla Contable <b>" + reglaContable.idComercio + " - " + reglaContable.idCliente + "</b>", xhr.responseJSON, $variableUtil.accionEliminado);
											$funcionUtil.notificarException(mensaje, "fa-warning", "Aviso", "warning");
											break;
									}
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