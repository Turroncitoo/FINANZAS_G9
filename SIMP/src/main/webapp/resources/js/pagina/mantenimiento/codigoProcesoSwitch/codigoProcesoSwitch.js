$(document).ready(function() {

	var $local = {
		$tablaMantenimiento : $("#tablaMantenimiento"),
		tablaMantenimiento : "",
		$modalMantenimiento : $("#modalMantenimiento"),
		$aniadirMantenimento : $("#aniadirMantenimiento"),
		$registrarMantenimiento : $("#registrarMantenimiento"),
		$actualizarMantenimiento : $("#actualizarMantenimiento"),
		$claseTransacciones : $("#claseTransacciones"),
		$codigoTransacciones : $("#codigoTransacciones"),
		codigoProcesoSwitchSeleccionado : "",
		$filaSeleccionada : "",
		$claseTransaccionesFiltroParaTableMantenimiento : $("#claseTransacciones-filtroParaTablaMantenimiento"),
		filtrosSeleccionables : {},
		arregloSiNo : [ "1", "0" ],
		$agregableClaseTransaccion : "",
		$agregableCodigoTransaccion : "",
		// Permisos
		permisoActualizacion : false,
		permisoEliminacion : false
	};

	$formMantenimiento = $("#formMantenimiento");

	$funcionUtil.crearSelect2($local.$claseTransacciones, "Selecciona una Clase de Transacci\u00F3n");
	$funcionUtil.crearSelect2($local.$codigoTransacciones, "Seleccione una C\u00F3digo de Transacci\u00F3n");

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
		"ajax" : {
			"url" : $variableUtil.root + "codigoProcesoSwitch?accion=buscarTodos",
			"dataSrc" : ""
		},
		"language" : {
			"emptyTable" : "No hay C\u00F3digo de Proceso Switch registrado"
		},
		"initComplete" : function() {
			$local.$tablaMantenimiento.wrap("<div class='table-responsive'></div>");
			$local.filtrosSeleccionables["2"] = $local.arregloSiNo;
			$local.filtrosSeleccionables["3"] = $local.arregloSiNo;
			$local.filtrosSeleccionables["4"] = $local.$claseTransaccionesFiltroParaTableMantenimiento.html();
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaMantenimiento, $local.filtrosSeleccionables);
			$local.$agregableClaseTransaccion = $local.$tablaMantenimiento.find("thead").find("select.agregable-clase-transaccion");
			$local.$agregableCodigoTransaccion = $local.$tablaMantenimiento.find("thead").find("select.agregable-codigo-transaccion");
		},
		"columnDefs" : [ {
			"targets" : [ 0, 1 ],
			"className" : "all filtrable",
		}, {
			"targets" : 2,
			"className" : "all seleccionable data-no-definida dt-center",
			"width" : "10%",
			"render" : function(data, type, row) {
				return $funcionUtil.insertarEtiquetaSiNo(row.transaccionMonetaria);
			}
		}, {
			"targets" : 3,
			"className" : "all seleccionable data-no-definida dt-center",
			"width" : "10%",
			"render" : function(data, type, row) {
				return $funcionUtil.insertarEtiquetaSiNo(row.aplicaInteres);
			}
		}, {
			"targets" : 6,
			"className" : "all dt-center",
			"render" : function() {
				return "" + ($local.permisoActualizacion == "true" ? $variableUtil.botonActualizar : "") + " " + ($local.permisoEliminacion == "true" ? $variableUtil.botonEliminar : "");
			}
		} ],
		"columns" : [ {
			"data" : 'codigoProcesoSwitch',
			"title" : "C\u00F3digo de Proceso Switch"
		}, {
			"data" : 'descripcion',
			"title" : "Descripci\u00F3n"
		}, {
			"data" : 'transaccionMonetaria',
			"title" : "Transacci\u00F3n Monetaria"
		}, {
			"data" : 'aplicaInteres',
			"title" : "Aplica Inter\u00E9s"
		}, {
			"className" : "all seleccionable select2 agregable-clase-transaccion insertable-opciones-html",
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoClaseTransaccion, row.descripcionClaseTransaccion);
			},
			"title" : "Clase de Transacci\u00F3n"
		}, {
			"className" : "all seleccionable select2 agregable-codigo-transaccion insertable-opciones-html",
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoTransaccion, row.descripcionCodigoTransaccion);
			},
			"title" : "C\u00F3digo de Transacci\u00F3n"
		}, {
			"data" : null,
			"title" : 'Acci\u00F3n'
		} ]
	});

	$local.$tablaMantenimiento.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
		if ($(this).hasClass("agregable-clase-transaccion")) {
			var codigoClaseTransaccion = $(this).find(":selected").attr("data-value");
			if (codigoClaseTransaccion == "" || codigoClaseTransaccion == null) {
				$local.$agregableCodigoTransaccion.find("option:not(:contains('Todos'))").remove();
				$local.$agregableCodigoTransaccion.trigger("change");
				return;
			}
			$.ajax({
				type : "GET",
				url : $variableUtil.root + "codigoTransaccion/claseTransaccion/" + codigoClaseTransaccion,
				beforeSend : function(xhr) {
					$local.$agregableCodigoTransaccion.find("option:not(:contains('Todos'))").remove();
					$local.$agregableCodigoTransaccion.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando C\u00F3digos de Transacci\u00F3n</span>");
				},
				statusCode : {
					400 : function(response) {
						$funcionUtil.notificarException($funcionUtil.obtenerMensajeErrorEnCadena(response.responseJSON), "fa-warning", "Aviso", "warning");
					}
				},
				success : function(codigoTransacciones) {
					$.each(codigoTransacciones, function(i, codigoTransaccion) {
						var descripcionCodigoTransaccion = $funcionUtil.unirCodigoDescripcion(this.codigoTransaccion, this.descripcion);
						var $opcionCodigoTransaccion = $("<option />").val(descripcionCodigoTransaccion).text(descripcionCodigoTransaccion).attr("data-value", this.codigoTransaccion);
						$local.$agregableCodigoTransaccion.append($opcionCodigoTransaccion);
					});
				},
				complete : function() {
					$local.$agregableCodigoTransaccion.parent().find(".cargando").remove();
					$local.$agregableCodigoTransaccion.trigger("change");
				}
			});
		}
	});

	$local.$claseTransacciones.on("change", function(event, opcionSeleccionada) {
		var codigoClaseTransaccion = $(this).val();
		if (codigoClaseTransaccion == null || codigoClaseTransaccion == undefined) {
			$local.$codigoTransacciones.find("option:not(:eq(0))").remove();
			return;
		}
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "codigoTransaccion/claseTransaccion/" + codigoClaseTransaccion,
			beforeSend : function(xhr) {
				$local.$codigoTransacciones.find("option:not(:eq(0))").remove();
				$local.$codigoTransacciones.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando C\u00F3digo de Transacciones</span>")
				xhr.setRequestHeader('Content-Type', 'application/json');
			},
			statusCode : {
				400 : function(response) {
					$funcionUtil.limpiarMensajesDeError($formMantenimiento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formMantenimiento);
				}
			},
			success : function(codigoTransacciones) {
				$.each(codigoTransacciones, function(i, codigoTransaccion) {
					$local.$codigoTransacciones.append($("<option />").val(this.codigoTransaccion).text($funcionUtil.unirCodigoDescripcion(this.codigoTransaccion, this.descripcion)));
				});
				if (opcionSeleccionada != null && opcionSeleccionada != undefined) {
					$local.$codigoTransacciones.val(opcionSeleccionada).trigger("change.select2");
				}
			},
			error : function(response) {
			},
			complete : function(response) {
				$local.$codigoTransacciones.parent().find(".cargando").remove();
			}
		});
	});

	$local.$tablaMantenimiento.find("thead").on('keyup', 'input', function() {
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$modalMantenimiento.PopupWindow({
		title : "Mantenimiento de C\u00F3digo de Proceso de Switch",
		autoOpen : false,
		modal : false,
		height : 491,
		width : 626,
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
		$local.codigoProcesoSwitchSeleccionado = "";
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
		var codigoProcesoSwitch = $formMantenimiento.serializeJSON();
		$.ajax({
			type : "POST",
			url : $variableUtil.root + "codigoProcesoSwitch",
			data : JSON.stringify(codigoProcesoSwitch),
			beforeSend : function(xhr) {
				$local.$registrarMantenimiento.attr("disabled", true).find("i").removeClass("fa-floppy-o").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode : {
				400 : function(response) {
					$funcionUtil.limpiarMensajesDeError($formMantenimiento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formMantenimiento);
				}
			},
			success : function(codigosProcesoSwitch) {
				$funcionUtil.notificarException($variableUtil.registroExitoso, "fa-check", "Aviso", "success");
				if ($local.$tablaMantenimiento.length > 0) {
					var row = $local.tablaMantenimiento.row.add(codigosProcesoSwitch[0]).draw();
					row.show().draw(false);
					$(row.node()).animateHighlight();
				}
				$local.$modalMantenimiento.PopupWindow("close");
			},
			error : function(response) {
			},
			complete : function(response) {
				$local.$registrarMantenimiento.attr("disabled", false).find("i").addClass("fa-floppy-o").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$tablaMantenimiento.children("tbody").on("click", ".actualizar", function() {
		$funcionUtil.prepararFormularioActualizacion($formMantenimiento);
		$local.$filaSeleccionada = $(this).parents("tr");
		var codigoProcesoSwitch = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		$local.codigoProcesoSwitchSeleccionado = codigoProcesoSwitch.codigoProcesoSwitch;
		$funcionUtil.llenarFormulario(codigoProcesoSwitch, $formMantenimiento);
		$local.$claseTransacciones.trigger("change", [ codigoProcesoSwitch.codigoTransaccion ]);
		$local.$actualizarMantenimiento.removeClass("hidden");
		$local.$registrarMantenimiento.addClass("hidden");
		$local.$modalMantenimiento.PopupWindow("open");
	});

	$local.$actualizarMantenimiento.on("click", function() {
		if (!$formMantenimiento.valid()) {
			return;
		}
		var codigoProcesoSwitch = $formMantenimiento.serializeJSON();
		codigoProcesoSwitch.codigoProcesoSwitch = $local.codigoProcesoSwitchSeleccionado;
		$.ajax({
			type : "PUT",
			url : $variableUtil.root + "codigoProcesoSwitch",
			data : JSON.stringify(codigoProcesoSwitch),
			beforeSend : function(xhr) {
				$local.$actualizarMantenimiento.attr("disabled", true).find("i").removeClass("fa-pencil-square").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode : {
				400 : function(response) {
					$funcionUtil.limpiarMensajesDeError($formMantenimiento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formMantenimiento);
				}
			},
			success : function(codigoProcesoSwitch) {
				$funcionUtil.notificarException($variableUtil.registroExitoso, "fa-check", "Aviso", "success");
				if ($local.$tablaMantenimiento.length > 0) {
					$local.tablaMantenimiento.row($local.$filaSeleccionada).remove().draw(false);
					var row = $local.tablaMantenimiento.row.add(codigoProcesoSwitch[0]).draw();
					row.show().draw(false);
					$(row.node()).animateHighlight();
				}
				$local.$modalMantenimiento.PopupWindow("close");
			},
			error : function(response) {
			},
			complete : function(response) {
				$local.$actualizarMantenimiento.attr("disabled", false).find("i").addClass("fa-pencil-square").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$tablaMantenimiento.children("tbody").on("click", ".eliminar", function() {
		$local.$filaSeleccionada = $(this).parents("tr");
		var codigoProcesoSwitch = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		$.confirm({
			icon : "fa fa-info-circle",
			title : "Aviso",
			content : "&iquest;Desea eliminar el C\u00F3digo de Proceso Switch  <b>'" + codigoProcesoSwitch.codigoProcesoSwitch + " - " + codigoProcesoSwitch.descripcion + "'</b>?",
			buttons : {
				Aceptar : {
					action : function() {
						var confirmar = $.confirm({
							icon : 'fa fa-spinner fa-pulse fa-fw',
							title : "Eliminando...",
							content : function() {
								var self = this;
								self.buttons.close.hide();
								$.ajax({
									type : "DELETE",
									url : $variableUtil.root + "codigoProcesoSwitch",
									data : JSON.stringify(codigoProcesoSwitch),
									autoclose : true,
									beforeSend : function(xhr) {
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
										var mensaje = $funcionUtil.obtenerMensajeError("El C\u00F3digo de Proceso Switch <b>" + codigoProcesoSwitch.codigoProcesoSwitch + " - " + codigoProcesoSwitch.descripcion + "</b>", xhr.responseJSON, $variableUtil.accionEliminado);
										$funcionUtil.notificarException(mensaje, "fa-warning", "Aviso", "warning");
										break;
									}
								});
							},
							buttons : {
								close : {
									text : 'Aceptar'
								}
							}
						});
					},
					keys : [ 'enter' ],
					btnClass : "btn-primary"
				},
				Cancelar : {
					keys : [ 'esc' ]
				},
			}
		});
	});

});