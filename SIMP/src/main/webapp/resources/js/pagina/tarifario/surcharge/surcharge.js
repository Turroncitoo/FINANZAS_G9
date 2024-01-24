$(document).ready(function() {

	var $local = {
		$tablaTarifario: $("#tablaTarifario"),
		tablaTarifario: "",
		$modalTarifario: $("#modalTarifario"),
		$aniadirTarifario: $("#aniadirTarifario"),
		$registrarTarifario: $("#registrarTarifario"),
		$filaSeleccionada: "",
		$actualizarTarifario: $("#actualizarTarifario"),
		surchargeSeleccionado: {},
		$instituciones: $("#instituciones"),
		$monedas: $("#monedas"),

		$institucionesFiltroParaTablaTarifario: $("#instituciones-filtroParaTablaTarifario"),
		$monedasFiltroParaTablaTarifario: $("#monedas-filtroParaTablaTarifario"),
		arregloSiNo: ["0", "1"],
		filtrosSeleccionables: [],

	}

	$formTarifario = $("#formTarifario");

	$.fn.dataTable.ext.errMode = 'none';

	$funcionUtil.crearSelect2($local.$instituciones, "Seleccione una institucion");
	$funcionUtil.crearSelect2($local.$monedas, "Seleccione un tipo de moneda");

	$local.$tablaTarifario.on('xhr.dt', function(e, settings, json, xhr) {
		switch (xhr.status) {
			case 500:
				$local.tablaTarifario.clear().draw();
				break;
		}
	});

	$local.tablaTarifario = $local.$tablaTarifario.DataTable({
		"ajax": {
			"url": $variableUtil.root + "surcharge?accion=buscarTodos",
			"dataSrc": ""
		},
		"language": {
			"emptyTable": "No hay Surcharge registrados"
		},
		"initComplete": function() {
			$local.$tablaTarifario.wrap("<div class='table-responsive'></div>");
			$local.filtrosSeleccionables["0"] = $local.$institucionesFiltroParaTablaTarifario.html();
			$local.filtrosSeleccionables["4"] = $local.$monedasFiltroParaTablaTarifario.html();
			$local.filtrosSeleccionables["7"] = $local.arregloSiNo;
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaTarifario, $local.filtrosSeleccionables);

		},
		"columnDefs": [{
			"targets": [0, 4],
			"className": "all filtrable",
		}, {
			"targets": [1, 2, 3, 5, 6],
			"className": "filtrable",
		}, {
			"targets": 7,
			"className": "all seleccionable data-no-definida",
			"render": function(data, type, row) {
				return $funcionUtil.insertarEtiquetaSiNo(row.aplicaIgv);
			}
		}, {
			"targets": 8,
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
			"data": 'nivel',
			"title": "Nivel"
		}, {
			"data": 'rangoInicial',
			"title": "Rango Inicial"
		}, {
			"data": 'rangoFinal',
			"title": "Rango Final"
		}, {
			"className": "all seleccionable select2 insertable-opciones-html",
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoMoneda, row.descripcionMoneda);
			},
			"title": "Moneda"
		}, {
			"data": function(row) {
				return parseFloat(row.surchargeFlat).toFixed(4);
			},
			"title": "Surcharge Flat"
		}, {
			"data": function(row) {
				return parseFloat(row.surchargePorc).toFixed(4);
			},
			"title": "Surcharge %"
		}, {
			"data": null,
			"title": "Aplica IGV"
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
	});

	$local.$modalTarifario.PopupWindow({
		title: "Surcharge",
		autoOpen: false,
		modal: false,
		height: 450,
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
		$local.surchargeSeleccionado = {};
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
		var surcharge = $formTarifario.serializeJSON();
		surcharge.tarifaFija = (surcharge.surchargeFlat == null
			|| surcharge.tarifaFlat == 0) ? 0 : 1;

		$.ajax({
			type: "POST",
			url: $variableUtil.root + "surcharge",
			data: JSON.stringify(surcharge),
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
			success: function(surcharges) {
				$funcionUtil.notificarException($variableUtil.registroExitoso, "fa-check", "Aviso", "success");
				var row = $local.tablaTarifario.row.add(surcharges[0]).draw();
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
		$local.surchargeSeleccionado = $local.tablaTarifario.row($local.$filaSeleccionada).data();
		$funcionUtil.llenarFormulario($local.surchargeSeleccionado, $formTarifario);
		$local.$actualizarTarifario.removeClass("hidden");
		$local.$registrarTarifario.addClass("hidden");
		$local.$modalTarifario.PopupWindow("open");
	});

	$local.$actualizarTarifario.on("click", function() {
		if (!$formTarifario.valid()) {
			return;
		}
		var surcharge = $formTarifario.serializeJSON();
		$local.surchargeSeleccionado.rangoInicial = surcharge.rangoInicial;
		$local.surchargeSeleccionado.rangoFinal = surcharge.rangoFinal;
		$local.surchargeSeleccionado.codigoMoneda = surcharge.codigoMoneda;
		$local.surchargeSeleccionado.surchargeFlat = surcharge.surchargeFlat;
		$local.surchargeSeleccionado.surchargePorc = surcharge.surchargePorc;
		$local.surchargeSeleccionado.aplicaIgv = surcharge.aplicaIgv;
		$local.surchargeSeleccionado.tarifaFija = (surcharge.surchargeFlat == null
			|| surcharge.surchargeFlat == 0) ? 0 : 1;
		$.ajax({
			type: "PUT",
			url: $variableUtil.root + "surcharge",
			data: JSON.stringify($local.surchargeSeleccionado),
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
			success: function(surcharges) {
				$funcionUtil.notificarException($variableUtil.actualizacionExitosa, "fa-check", "Aviso", "success");
				$local.tablaTarifario.row($local.$filaSeleccionada).remove().draw(false);
				var surcharge = surcharges[0];
				var row = $local.tablaTarifario.row.add(surcharge).draw();
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
		var surcharge = $local.tablaTarifario.row($local.$filaSeleccionada).data();
		$.confirm({
			icon: "fa fa-info-circle",
			title: "Aviso",
			content: "\u00BFDesea eliminar el surcharge? ",
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
									url: $variableUtil.root + "surcharge",
									data: JSON.stringify(surcharge),
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
											var mensaje = $funcionUtil.obtenerMensajeError("El surcharge", xhr.responseJSON, $variableUtil.accionEliminado);
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