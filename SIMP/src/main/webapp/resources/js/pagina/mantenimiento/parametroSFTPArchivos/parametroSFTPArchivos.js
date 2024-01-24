$(document).ready(function() {

	var $local = {
		$tablaMantenimiento: $("#tablaMantenimiento"),
		tablaMantenimiento: "",
		$modalMantenimiento: $("#modalMantenimiento"),
		$aniadirMantenimento: $("#aniadirMantenimiento"),
		$registrarMantenimiento: $("#registrarMantenimiento"),
		$filaSeleccionada: "",
		$actualizarMantenimiento: $("#actualizarMantenimiento"),
		codigoSeleccionado: 0,
		$selectDirectorio: $("#selectDirectorio"),
		arregloSiNo: ["0", "1"],
		filtrosSeleccionables: [],
		// Permisos
		permisoActualizacion: false,
		permisoEliminacion: false
	};

	$formMantenimiento = $("#formMantenimiento");

	$funcionUtil.crearSelect2($local.$selectDirectorio, "Seleccione proceso - operaci\u00f3n");

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
			"url": $variableUtil.root + "parametroSFTPArchivos?accion=buscarTodos",
			"dataSrc": ""
		},
		"language": {
			"emptyTable": "No hay archivos registrados"
		},
		"initComplete": function() {
			$local.$tablaMantenimiento.wrap("<div class='table-responsive'></div>");
			$local.filtrosSeleccionables["7"] = $local.arregloSiNo;
			$local.filtrosSeleccionables["8"] = $local.arregloSiNo;
			$local.filtrosSeleccionables["9"] = $local.arregloSiNo;
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaMantenimiento, $local.filtrosSeleccionables);
		},
		"columnDefs": [{
			"targets": [0, 1, 2, 3, 4, 5, 6],
			"className": "all filtrable",
		}, {
			"targets": 7,
			"className": "all seleccionable select2 data-no-definida",
			"render": function(data, type, row) {
				return $funcionUtil.insertarEtiquetaSiNo(row.habilitado);
			}
		}, {
			"targets": 8,
			"className": "all seleccionable select2 data-no-definida",
			"render": function(data, type, row) {
				return $funcionUtil.insertarEtiquetaSiNo(row.validaHeader);
			}
		}, {
			"targets": 9,
			"className": "all seleccionable select2 data-no-definida",
			"render": function(data, type, row) {
				return $funcionUtil.insertarEtiquetaSiNo(row.cargaIncremental);
			}
		}, {
			"targets": 10,
			"className": "all dt-center",
			"width": "10%",
			"render": function() {
				return "" + ($local.permisoActualizacion == "true" ? $variableUtil.botonActualizar : "") + " " + ($local.permisoEliminacion == "true" ? $variableUtil.botonEliminar : "");
			}
		}],
		"columns": [{
			"data": 'codigoArchivo',
			"title": "C\u00f3digo"
		}, {
			"data": "descripcion",
			"title": "Descripci\u00f3n"
		}, {
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.descripcionProceso, row.descripcionTipoOperacion);
			},
			"title": "Proceso - Operaci\u00f3n"
		}, {
			"data": 'origenOriginal',
			"title": "Nombre"
		}, {
			"data": 'extensionOrigenOriginal',
			"title": "Extensi\u00f3n"
		}, {
			"data": "numeroBytes",
			"title": "Bytes"
		}, {
			"data": "diasAumentoRebajoFechaProceso",
			"title": "D\u00edas de env\u00edo"
		}, {
			"data": null,
			"title": "Habilitado"
		}, {
			"data": null,
			"title": "Valida Header"
		}, {
			"data": null,
			"title": "Carga Incremental"
		}, {
			"data": null,
			"title": 'Acci\u00f3n'
		}],
		"headerCallback": function(thead, data, start, end, display) {
			$(thead).find('th').eq(7).attr({
				"data-tooltip": "tooltip",
				"title": "Indicador de si el archivo estar\u00e1 habilitado para descarga."
			});
			$(thead).find('th').eq(8).attr({
				"data-tooltip": "tooltip",
				"title": "Indicador de si har\u00e1 validaci\u00F3n de header y trailer."
			});
			$(thead).find('th').eq(9).attr({
				"data-tooltip": "tooltip",
				"title": "Al cargarse el archivo en una tabla temporal se cargar\u00e1 incluyendo el n\u00famero de l\u00ednea o n\u00FAmero de registro."
			});
		}
	});

	$local.$tablaMantenimiento.find("thead").on('keyup', 'input', function() {
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaMantenimiento.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	$local.$modalMantenimiento.PopupWindow({
		title: "Mantenimiento de Archivos",
		autoOpen: false,
		modal: false,
		height: 520,
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
		$local.codigoSeleccionado = 0;
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
		var parametroSFTP = $formMantenimiento.serializeJSON();
		var directorio = parametroSFTP.codigoDirectorio.split(',');
		parametroSFTP.codigoProceso = directorio[0];
		parametroSFTP.tipoOperacion = directorio[1];
		$.ajax({
			type: "POST",
			url: $variableUtil.root + "parametroSFTPArchivos",
			data: JSON.stringify(parametroSFTP),
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
		var parametroSFTP = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		$local.codigoSeleccionado = parametroSFTP.codigoArchivo;
		parametroSFTP.codigoDirectorio = parametroSFTP.codigoProceso + ',' + parametroSFTP.tipoOperacion;
		$funcionUtil.llenarFormulario(parametroSFTP, $formMantenimiento);
		$local.$actualizarMantenimiento.removeClass("hidden");
		$local.$registrarMantenimiento.addClass("hidden");
		$local.$modalMantenimiento.PopupWindow("open");
	});

	$local.$actualizarMantenimiento.on("click", function() {
		if (!$formMantenimiento.valid()) {
			return;
		}
		var parametroSFTP = $formMantenimiento.serializeJSON();
		parametroSFTP.codigoArchivo = $local.codigoSeleccionado;
		var directorio = parametroSFTP.codigoDirectorio.split(',');
		parametroSFTP.codigoProceso = directorio[0];
		parametroSFTP.tipoOperacion = directorio[1];
		$.ajax({
			type: "PUT",
			url: $variableUtil.root + "parametroSFTPArchivos",
			data: JSON.stringify(parametroSFTP),
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
					var row = $local.tablaMantenimiento.row.add(response[0]).draw();
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
		var parametroSFTP = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		$.confirm({
			icon: "fa fa-info-circle",
			title: "Aviso",
			content: "\u00BFDesea eliminar el archivo <b>'" + parametroSFTP.codigoArchivo + "'</b>?",
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
									url: $variableUtil.root + "parametroSFTPArchivos",
									data: JSON.stringify(parametroSFTP),
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
											var mensaje = $funcionUtil.obtenerMensajeError(" El tipo de archivo <b>" + parametroSFTP.codigo + " - " + parametroSFTP.descripcion + "</b>", xhr.responseJSON, $variableUtil.accionEliminado);
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