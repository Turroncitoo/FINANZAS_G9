$(document).ready(function() {

	var $local = {
		$tablaTarifario: $("#tablaTarifario"),
		tablaTarifario: "",
		$modalTarifario: $("#modalTarifario"),
		$aniadirTarifario: $("#aniadirTarifario"),
		$registrarTarifario: $("#registrarTarifario"),
		$filaSeleccionada: "",
		$actualizarTarifario: $("#actualizarTarifario"),
		escenarioSeleccionado: {},
		$instituciones: $("#instituciones"),
		$membresias: $("#membresias"),
		$clasesServicio: $("#clasesServicio"),
		$origenes: $("#origenes"),
		$clasesTxns: $("#clasesTxns"),
		$codigoTransacciones: $("#codigoTxns"),
		$tipoTarifas: $("#tipoTarifas"),

		$institucionesFiltroParaTablaTarifario: $("#instituciones-filtroParaTablaTarifario"),
		$membresiasFiltroParaTablaTarifario: $("#membresias-filtroParaTablaTarifario"),
		$origenesFiltroParaTablaTarifario: $("#origenes-filtroParaTablaTarifario"),
		$claseTransaccionesFiltroParaTablaTarifario: $("#claseTransacciones-filtroParaTablaTarifario"),
		$tipoTarifasFiltroParaTablaTarifario: $("#tipoTarifas-filtroParaTablaTarifario"),
		filtrosSeleccionables: {},

		$agregableClaseServicio: "",
		$agregableClaseTransaccion: "",
		$agregableCodigoTransaccion: "",
		$agregableMembresia: ""
	}

	$formTarifario = $("#formTarifario");

	$.fn.dataTable.ext.errMode = 'none';

	$funcionUtil.crearSelect2($local.$instituciones, "Seleccione una institucion");
	$funcionUtil.crearSelect2($local.$membresias, "Seleccione una Membresia");
	$funcionUtil.crearSelect2($local.$clasesServicio, "Seleccione una Clase de Servicio");
	$funcionUtil.crearSelect2($local.$origenes, "Seleccione un Origen");
	$funcionUtil.crearSelect2($local.$clasesTxns, "Seleccione una Clase de Transaccion");
	$funcionUtil.crearSelect2($local.$codigoTransacciones, "Seleccione una C\u00F3digo de Transaccion");
	$funcionUtil.crearSelect2($local.$tipoTarifas, "Seleccione un Tipo de Tarifa");

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
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
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

	$local.$clasesTxns.on("change", function(event, opcionSeleccionada) {
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
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
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
				});
				if (opcionSeleccionada != null && opcionSeleccionada != undefined) {
					$local.$codigoTransacciones.val(opcionSeleccionada).trigger("change.select2");
				}
			},
			error: function(response) {
			},
			complete: function(response) {
				$local.$codigoTransacciones.parent().find(".cargando").remove();
			}
		});
	});
	$local.$tablaTarifario.on('xhr.dt', function(e, settings, json, xhr) {
		switch (xhr.status) {
			case 500:
				$local.tablaTarifario.clear().draw();
				break;
		}
	});

	$local.tablaTarifario = $local.$tablaTarifario.DataTable({
		"ajax": {
			"url": $variableUtil.root + "escenario?accion=buscarTodos",
			"dataSrc": ""
		},
		"language": {
			"emptyTable": "No hay Escenarios registrados"
		},
		"initComplete": function() {
			$local.$tablaTarifario.wrap("<div class='table-responsive'></div>");
			$local.filtrosSeleccionables["0"] = $local.$institucionesFiltroParaTablaTarifario.html();
			$local.filtrosSeleccionables["1"] = $local.$membresiasFiltroParaTablaTarifario.html();
			$local.filtrosSeleccionables["3"] = $local.$origenesFiltroParaTablaTarifario.html();
			$local.filtrosSeleccionables["4"] = $local.$claseTransaccionesFiltroParaTablaTarifario.html();
			$local.filtrosSeleccionables["6"] = $local.$tipoTarifasFiltroParaTablaTarifario.html();
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaTarifario, $local.filtrosSeleccionables);
			$local.$agregableMembresia = $local.$tablaTarifario.find("thead").find("select.agregable-membresia");
			$local.$agregableClaseServicio = $local.$tablaTarifario.find("thead").find("select.agregable-clase-servicio");
			$local.$agregableClaseTransaccion = $local.$tablaTarifario.find("thead").find("select.agregable-clase-transaccion");
			$local.$agregableCodigoTransaccion = $local.$tablaTarifario.find("thead").find("select.agregable-codigo-transaccion");

		},
		"columnDefs": [{
			"targets": [0, 1, 3, 4, 5, 6],
			"className": "all filtrable",
		}, {
			"targets": 7,
			"className": "all dt-center",
			"defaultContent": $variableUtil.botonActualizar + " " + $variableUtil.botonEliminar
		}],
		"columns": [{
			"className": "all seleccionable select2 insertable-opciones-html",
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoInstitucion, row.descripcionInstitucion);
			},
			"title": "Instituci\u00F3n"
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
			"title": "Clase Servicio"
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
			"title": "Clase Transacci\u00F3n"
		}, {
			"className": "all seleccionable select2 agregable-codigo-transaccion insertable-opciones-html",
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoTransaccion, row.descripcionCodigoTransaccion);
			},
			"title": "Codigo Transacci\u00F3n"
		}, {
			"className": "all seleccionable select2 insertable-opciones-html",
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoTipoTarifa, row.descripcionTipoTarifa);
			},
			"title": "Tipo Tarifa"
		}, {
			"data": null,
			"title": 'Acci\u00F3n'
		}]
	});

	$local.$tablaTarifario.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaTarifario.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaTarifario.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaTarifario.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
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
					$local.$agregableClaseServicio.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando clases de servicio</span>")
				},
				statusCode: {
					400: function(response) {
						$funcionUtil.notificarException($funcionUtil.obtenerMensajeErrorEnCadena(response.responseJSON), "fa-warning", "Aviso", "warning");
					}
				},
				success: function(response) {
					$.each(response, function(i, claseServicio) {
						$local.$agregableClaseServicio.append($("<option />").val(this.codigoClaseServicio + " - " + this.descripcion).text(this.codigoClaseServicio + " - " + this.descripcion));
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
						var $opcionCodigoTransaccion = $("<option />").val($funcionUtil.unirCodigoDescripcion(this.codigoTransaccion, this.descripcion)).text($funcionUtil.unirCodigoDescripcion(this.codigoTransaccion, this.descripcion)).attr("data-value", this.codigoTransaccion);
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

	$local.$modalTarifario.PopupWindow({
		title: "Escenario",
		autoOpen: false,
		modal: false,
		height: 500,
		width: 626,
	});

	$local.$aniadirTarifario.on("click", function() {
		$funcionUtil.prepararFormularioRegistro($formTarifario);
		$local.$actualizarTarifario.addClass("hidden");
		$local.$registrarTarifario.removeClass("hidden");
		$local.$modalTarifario.PopupWindow("open");
	});

	$local.$modalTarifario.on("open.popupwindow", function() {
		$formTarifario.find("input:not([disabled]):first").focus();
	});

	$local.$modalTarifario.on("close.popupwindow", function() {
		$local.escenarioSeleccionado = {};
	});

	$formTarifario.find("input").keypress(function(event) {
		if (event.which == 13) {
			if (!$local.$registrarTarifario.hasClass("hidden")) {
				$local.$registrarTarifario.trigger("click");
				return false;
			} else {
				if (!$local.$actualizarTarifario.hasClass("hidden")) {
					$local.$actualizarTarifario.trigger("click");
				}
				return false;
			}
		}
	});

	$local.$registrarTarifario.on("click", function() {
		if (!$formTarifario.valid()) {
			return;
		}
		var escenario = $formTarifario.serializeJSON();
		$.ajax({
			type: "POST",
			url: $variableUtil.root + "escenario",
			data: JSON.stringify(escenario),
			beforeSend: function(xhr) {
				$local.$registrarTarifario.attr("disabled", true).find("i").removeClass("fa-floppy-o").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode: {
				400: function(response) {
					$funcionUtil.limpiarMensajesDeError($formTarifario);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formTarifario);
				}
			},
			success: function(escenarios) {
				$funcionUtil.notificarException($variableUtil.registroExitoso, "fa-check", "Aviso", "success");
				var row = $local.tablaTarifario.row.add(escenarios[0]).draw();
				row.show().draw(false);
				$(row.node()).animateHighlight();
				$local.$modalTarifario.PopupWindow("close");
			},
			error: function(response) {
			},
			complete: function(response) {
				$local.$registrarTarifario.attr("disabled", false).find("i").addClass("fa-floppy-o").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$tablaTarifario.children("tbody").on("click", ".actualizar", function() {
		$funcionUtil.prepararFormularioActualizacion($formTarifario);
		$local.$filaSeleccionada = $(this).parents("tr");
		$local.escenarioSeleccionado = $local.tablaTarifario.row($local.$filaSeleccionada).data();
		$funcionUtil.llenarFormulario($local.escenarioSeleccionado, $formTarifario);
		$local.$membresias.trigger("change", [$local.escenarioSeleccionado.codigoClaseServicio]);
		$local.$clasesTxns.trigger("change", [$local.escenarioSeleccionado.codigoTransaccion]);
		$local.$actualizarTarifario.removeClass("hidden");
		$local.$registrarTarifario.addClass("hidden");
		$local.$modalTarifario.PopupWindow("open");
	});

	$local.$actualizarTarifario.on("click", function() {
		if (!$formTarifario.valid()) {
			return;
		}
		var escenario = $formTarifario.serializeJSON();
		$local.escenarioSeleccionado.codigoTipoTarifa = escenario.codigoTipoTarifa;
		$.ajax({
			type: "PUT",
			url: $variableUtil.root + "escenario",
			data: JSON.stringify($local.escenarioSeleccionado),
			beforeSend: function(xhr) {
				$local.$actualizarTarifario.attr("disabled", true).find("i").removeClass("fa-pencil-square").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode: {
				400: function(response) {
					$funcionUtil.limpiarMensajesDeError($formTarifario);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formTarifario);
				}
			},
			success: function(escenarios) {
				$funcionUtil.notificarException($variableUtil.actualizacionExitosa, "fa-check", "Aviso", "success");
				$local.tablaTarifario.row($local.$filaSeleccionada).remove().draw(false);
				var escenario = escenarios[0];
				var row = $local.tablaTarifario.row.add(escenario).draw();
				row.show().draw(false);
				$(row.node()).animateHighlight();
				$local.$modalTarifario.PopupWindow("close");
			},
			error: function(response) {
			},
			complete: function(response) {
				$local.$actualizarTarifario.attr("disabled", false).find("i").addClass("fa-pencil-square").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$tablaTarifario.children("tbody").on("click", ".eliminar", function() {
		$local.$filaSeleccionada = $(this).parents("tr");
		var escenario = $local.tablaTarifario.row($local.$filaSeleccionada).data();
		$.confirm({
			icon: "fa fa-info-circle",
			title: "Aviso",
			content: "\u00BFDesea eliminar el escenario?",
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
									url: $variableUtil.root + "escenario",
									data: JSON.stringify(escenario),
									autoclose: true,
									beforeSend: function(xhr) {
										xhr.setRequestHeader('Content-Type', 'application/json');
										xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
									}
								}).done(function(response) {
									$funcionUtil.notificarException(response, "fa-check", "Aviso", "success");
									$local.tablaTarifario.row($local.$filaSeleccionada).remove().draw(false);
									confirmar.close();
								}).fail(function(xhr) {
									confirmar.close();
									switch (xhr.status) {
										case 400:
											$funcionUtil.notificarException($funcionUtil.obtenerMensajeErrorEnCadena(xhr.responseJSON), "fa-warning", "Aviso", "warning");
											break;
										case 409:
											var mensaje = $funcionUtil.obtenerMensajeError("El escenario ", xhr.responseJSON, $variableUtil.accionEliminado);
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