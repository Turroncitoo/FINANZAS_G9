$(document).ready(function() {

	var $local = {
		$tablaTarifario: $("#tablaTarifario"),
		tablaTarifario: "",
		$modalTarifario: $("#modalTarifario"),
		$aniadirTarifario: $("#aniadirTarifario"),
		$registrarTarifario: $("#registrarTarifario"),
		$filaSeleccionada: "",
		$actualizarTarifario: $("#actualizarTarifario"),
		tarAdquirenteSeleccionado: {},
		$instituciones: $("#instituciones"),
		$tipoTarifas: $("#tipoTarifas"),
		$monedas: $("#monedas"),

		$institucionesFiltroParaTablaTarifario: $("#instituciones-filtroParaTablaTarifario"),
		$monedasFiltroParaTablaTarifario: $("#monedas-filtroParaTablaTarifario"),
		$tipoTarifasFiltroParaTablaTarifario: $("#tipoTarifas-filtroParaTablaTarifario"),
		filtrosSeleccionables: [],
		arregloSiNo: ["0", "1"]
	};

	$formTarifario = $("#formTarifario");

	$.fn.dataTable.ext.errMode = 'none';

	$funcionUtil.crearSelect2($local.$instituciones, "Seleccione una institucion");
	$funcionUtil.crearSelect2($local.$monedas, "Seleccione un tipo de moneda");
	$funcionUtil.crearSelect2($local.$tipoTarifas, "Seleccione un tipo de Tarifa");

	$local.$tablaTarifario.on('xhr.dt', function(e, settings, json, xhr) {
		switch (xhr.status) {
			case 500:
				$local.tablaTarifario.clear().draw();
				break;
		}
	});

	$local.tablaTarifario = $local.$tablaTarifario.DataTable({
		"ajax": {
			"url": $variableUtil.root + "tarifarioAdquirente?accion=buscarTodos",
			"dataSrc": ""
		},
		"language": {
			"emptyTable": "No hay Escenarios registrados"
		},
		"initComplete": function() {
			$local.$tablaTarifario.wrap("<div class='table-responsive'></div>");
			$local.filtrosSeleccionables["0"] = $local.$institucionesFiltroParaTablaTarifario.html();
			$local.filtrosSeleccionables["1"] = $local.$tipoTarifasFiltroParaTablaTarifario.html();
			$local.filtrosSeleccionables["5"] = $local.$monedasFiltroParaTablaTarifario.html();
			$local.filtrosSeleccionables["8"] = $local.arregloSiNo;
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaTarifario, $local.filtrosSeleccionables);
		},
		"columnDefs": [{
			"targets": [0, 1, 5],
			"className": "all filtrable",
		}, {
			"targets": [2, 3, 4, 6, 7],
			"className": "filtrable",
		}, {
			"targets": 8,
			"className": "all seleccionable data-no-definida",
			"render": function(data, type, row) {
				return $funcionUtil.insertarEtiquetaSiNo(row.aplicaIgv);
			}
		}, {
			"targets": 9,
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
			"className": "all seleccionable select2 insertable-opciones-html",
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoTipoTarifa, row.descripcionTipoTarifa);
			},
			"title": "Tipo Tarifa"
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
				return parseFloat(row.tarifaFlat).toFixed(4);
			},
			"title": "Tarifa Flat"
		}, {
			"data": function(row) {
				return parseFloat(row.tarifaPorc).toFixed(4);
			},
			"title": "Tarifa %"
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
		title: "Tarifario Adquirente",
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
		$local.tarAdquirenteSeleccionado = {};
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
		var tarifarioAdquirente = $formTarifario.serializeJSON();
		tarifarioAdquirente.tarifaFija = (tarifarioAdquirente.tarifaFlat == null
			|| tarifarioAdquirente.tarifaFlat == 0) ? 0 : 1;

		$.ajax({
			type: "POST",
			url: $variableUtil.root + "tarifarioAdquirente",
			data: JSON.stringify(tarifarioAdquirente),
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
			success: function(tarifarioAdquirentes) {
				$funcionUtil.notificarException($variableUtil.registroExitoso, "fa-check", "Aviso", "success");
				var row = $local.tablaTarifario.row.add(tarifarioAdquirentes[0]).draw();
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
		$local.tarAdquirenteSeleccionado = $local.tablaTarifario.row($local.$filaSeleccionada).data();
		$funcionUtil.llenarFormulario($local.tarAdquirenteSeleccionado, $formTarifario);
		$local.$actualizarTarifario.removeClass("hidden");
		$local.$registrarTarifario.addClass("hidden");
		$local.$modalTarifario.PopupWindow("open");
	});

	$local.$actualizarTarifario.on("click", function() {
		if (!$formTarifario.valid()) {
			return;
		}
		var tarifarioAdquirente = $formTarifario.serializeJSON();
		$local.tarAdquirenteSeleccionado.rangoInicial = tarifarioAdquirente.rangoInicial;
		$local.tarAdquirenteSeleccionado.rangoFinal = tarifarioAdquirente.rangoFinal;
		$local.tarAdquirenteSeleccionado.codigoMoneda = tarifarioAdquirente.codigoMoneda;
		$local.tarAdquirenteSeleccionado.tarifaFlat = tarifarioAdquirente.tarifaFlat;
		$local.tarAdquirenteSeleccionado.tarifaPorc = tarifarioAdquirente.tarifaPorc;
		$local.tarAdquirenteSeleccionado.aplicaIgv = tarifarioAdquirente.aplicaIgv;
		$local.tarAdquirenteSeleccionado.tarifaFija = (tarifarioAdquirente.tarifaFlat == null
			|| tarifarioAdquirente.tarifaFlat == 0) ? 0 : 1;
		$.ajax({
			type: "PUT",
			url: $variableUtil.root + "tarifarioAdquirente",
			data: JSON.stringify($local.tarAdquirenteSeleccionado),
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
			success: function(tarifarioAdquirentes) {
				$funcionUtil.notificarException($variableUtil.actualizacionExitosa, "fa-check", "Aviso", "success");
				$local.tablaTarifario.row($local.$filaSeleccionada).remove().draw(false);
				var tarifarioAdquirente = tarifarioAdquirentes[0];
				var row = $local.tablaTarifario.row.add(tarifarioAdquirente).draw();
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
		var tarifarioAdquirente = $local.tablaTarifario.row($local.$filaSeleccionada).data();
		$.confirm({
			icon: "fa fa-info-circle",
			title: "Aviso",
			content: "\u00BFDesea eliminar el tarifario adquirente? ",
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
									url: $variableUtil.root + "tarifarioAdquirente",
									data: JSON.stringify(tarifarioAdquirente),
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
											var mensaje = $funcionUtil.obtenerMensajeError("El tarifario emisor", xhr.responseJSON, $variableUtil.accionEliminado);
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