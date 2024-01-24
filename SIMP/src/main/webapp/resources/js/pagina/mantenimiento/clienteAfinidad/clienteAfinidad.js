$(document).ready(function() {

	var $local = {

		$tablaMantenimiento: $("#tablaMantenimiento"),
		tablaMantenimiento: "",
		$modalMantenimiento: $("#modalMantenimiento"),
		$formularioAdicionalMantenimiento: $("#formularioAdicionalMantenimiento"),
		$aniadirMantenimento: $("#aniadirMantenimiento"),
		$registrarMantenimiento: $("#registrarMantenimiento"),
		$actualizarMantenimiento: $("#actualizarMantenimiento"),
		$botonesActualizacion: $("#botonesActualizacion"),
		$filaSeleccionada: "",

		$institucionSelect: $("#institucionSelect"),
		$logoSelect: $('#logo'),
		$afinidadSelect: $("#afinidadSelect"),
		$empresaSelect: $("#empresaSelect"),
		$clienteSelect: $("#clienteSelect"),
		$empresasFiltroParaTablaMantenimiento: $("#instituciones-filtroParaTablaMantenimiento"),
		filtrosSeleccionables: [],

		// Permisos
		permisoActualizacion: false,
		permisoEliminacion: false,
	};

	$formMantenimiento = $("#formMantenimiento");
	$formAsociacionSubBin = $("#formAsociacionSubBin");

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

	$funcionUtil.crearSelect2($local.$institucionSelect, "Seleccione una Instituci\u00F3n");
	$funcionUtil.crearSelect2($local.$logoSelect, "Seleccione un Logo");
	$funcionUtil.crearSelect2($local.$afinidadSelect, "Seleccione una Afinidad");
	$funcionUtil.crearSelect2($local.$clienteSelect, "Seleccione un Cliente");
	$funcionUtil.crearSelect2($local.$empresaSelect, "Seleccione una Empresa");

	$local.$institucionSelect.on("change", function(event, opcionSeleccionada) {
		opcionSeleccionada = $local.$institucionSelect.val();
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "logo/dependeInstitucion/" + opcionSeleccionada,
			beforeSend: function(xhr) {
				$local.$logoSelect.find("option:not(:eq(0))").remove();
				$local.$logoSelect.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando logos</span>");
			},
			success: function(response) {
				$.each(response, function(i, sector) {
					$local.$logoSelect.append($("<option/>").val(this.idLogo).text(this.idLogo + " - " + this.descripcion+ " (" + this.idBin + ")"));
				});
			},
			complete: function() {
				$local.$logoSelect.parent().find(".cargando").remove();
			}
		});
	});

	$local.$logoSelect.on("change", function(event, opcionSeleccionada) {
		opcionSeleccionada = $local.$logoSelect.val();
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "afinidad/" + opcionSeleccionada,
			beforeSend: function(xhr) {
				$local.$afinidadSelect.find("option:not(:eq(0))").remove();
				$local.$afinidadSelect.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando afinidades</span>")
			},
			success: function(response) {
				$.each(response, function(i, sector) {
					$local.$afinidadSelect.append($("<option/>").val(this.idAfinidad).text(this.codigo + " - " + this.descripcionAfinidad));
				});
			},
			complete: function() {
				$local.$afinidadSelect.parent().find(".cargando").remove();
			}
		});
	});

	$local.$empresaSelect.on("change", function(event, opcionSeleccionada) {
		opcionSeleccionada = $local.$empresaSelect.val();
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "cliente/empresa/" + opcionSeleccionada,
			beforeSend: function(xhr) {
				$local.$clienteSelect.find("option:not(:eq(0))").remove();
				$local.$clienteSelect.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando clientes</span>")
			},
			success: function(response) {
				$.each(response, function(i, sector) {
					$local.$clienteSelect.append($("<option/>").val(this.idCliente).text(this.idCliente + " - " + this.descripcion ));
				});
			},
			complete: function() {
				$local.$clienteSelect.parent().find(".cargando").remove();
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
			"url": $variableUtil.root + "cliente/empresa/afinidad",
			"dataSrc": ""
		},
		"language": {
			"emptyTable": "No hay relaciones de afinidades con clientes registradas"
		},
		"initComplete": function() {
			$local.$tablaMantenimiento.wrap("<div class='table-responsive'></div>");
			$local.filtrosSeleccionables["0"] = $local.$empresasFiltroParaTablaMantenimiento.html();
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaMantenimiento, $local.filtrosSeleccionables);
		},
		"columnDefs": [{
			"targets": [0, 1, 2, 3, 4],
			"className": "all filtrable",
		}, {
			"targets": 5,
			"className": "all dt-center",
			"render": function() {
				return " " + ($local.permisoEliminacion == "true" ? $variableUtil.botonEliminar : "");
			}
		}],
		"columns": [{
			"className": "all seleccionable select2 insertable-opciones-html",
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoInstitucion, row.descripcionInstitucion);
			},
			"title": "Instituci\u00F3n"
		}, {
			"data": function(row) {
				return `${$funcionUtil.unirCodigoDescripcion(row.idLogoAfinidad, row.descripcionLogoAfinidad)} (${row.idBin})`;
			},
			"title": "Logo"
		}, {
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoAfinidad, row.descripcionAfinidad);
			},
			"title": "Afinidad"
		},  {
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idEmpresa, row.descripcionEmpresa);
			},
			"title": "Empresa"
		}, {
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idCliente, row.descripcionCliente);
			},
			"title": "Cliente"
		},{
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
		title: "Mantenimiento de Cliente Afinidad",
		autoOpen: false,
		modal: false,
		height: 400,
		width: 900,
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
		$local.idCanalSeleccionado = "";
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
		var afinidad = $formMantenimiento.serializeJSON();
		$.ajax({
			type: "POST",
			url: $variableUtil.root + "cliente?accion=asociarClienteAfinidad",
			data: JSON.stringify(afinidad),
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
		var afinidad = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		$local.seleccionado = afinidad;
		$funcionUtil.llenarFormulario(afinidad, $formMantenimiento);
		$local.$actualizarMantenimiento.removeClass("hidden");
		$local.$registrarMantenimiento.addClass("hidden");
		$local.$modalMantenimiento.PopupWindow("open");
	});


	$local.$tablaMantenimiento.children("tbody").on("click", ".eliminar", function() {
		$local.$filaSeleccionada = $(this).parents("tr");
		var afinidad = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		$.confirm({
			icon: "fa fa-info-circle",
			title: "Aviso",
			content: "\u00BFDesea eliminar la Afinidad <b>'" + afinidad.codigoAfinidad + " - " + afinidad.descripcionAfinidad + "'</b> del cliente <b>'" + afinidad.idCliente + " - " + afinidad.descripcionCliente + "'</b> ?",
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
									url: $variableUtil.root + "cliente?accion=removerAsociacionClienteAfinidad",
									data: JSON.stringify(afinidad),
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
											var mensaje = $funcionUtil.obtenerMensajeError("La Afinidad <b>'" + afinidad.codigoAfinidad + " - " + afinidad.descripcionAfinidad + "'</b> del cliente <b>'" + afinidad.idCliente + " - " + afinidad.descripcionCliente + "'</b>", xhr.responseJSON, $variableUtil.accionEliminado);
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