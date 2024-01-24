$(document).ready(function() {

	var $local = {
		$tablaMantenimiento : $("#tablaMantenimiento"),
		tablaMantenimiento : "",
		$modalMantenimiento : $("#modalMantenimiento"),
		$aniadirMantenimento : $("#aniadirMantenimiento"),
		$registrarMantenimiento : $("#registrarMantenimiento"),
		$actualizarMantenimiento : $("#actualizarMantenimiento"),
		$filaSeleccionada : "",
		tipoTransaccionSeleccionado : 0,
		codigoCanalEmisorSeleccionado : "",
		codTransaccionSeleccionado : "",
		$canalesEmisoresFiltroParaTableMantenimiento : $("#canalesEmisores-filtroParaTablaMantenimiento"),
		filtrosSeleccionables : {},
		$canalesEmisores : $("#canalesEmisores"),
		$tiposTransacciones : $("#tiposTransacciones")
	};

	$funcionUtil.crearSelect2($local.$canalesEmisores, "Seleccione un Canal Emisor");
	$funcionUtil.crearSelect2($local.$tiposTransacciones, "Seleccione un Tipo de Transaccion");

	$formMantenimiento = $("#formMantenimiento");

	$local.$canalesEmisores.on("change", function(event, opcionSeleccionada) {
		var codigoCanalEmisor = $(this).val();
		if (codigoCanalEmisor == null || codigoCanalEmisor == undefined) {
			$local.$canalesEmisores.find("option:not(:eq(0))").remove();
			return;
		}
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "codigoProcBevertec/canalEmisor/" + codigoCanalEmisor,
			beforeSend : function(xhr) {
				$local.$tiposTransacciones.find("option:not(:eq(0))").remove();
				$local.$tiposTransacciones.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Tipos de Transacciones</span>")
			},
			statusCode : {
				400 : function(response) {
					$funcionUtil.limpiarMensajesDeError($formMantenimiento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formMantenimiento);
				}
			},
			success : function(codigosProcBevertec) {
				$.each(codigosProcBevertec, function(i, codigoProcBevertec) {
					$local.$tiposTransacciones.append($("<option />").val(this.tipoTransaccion).text($funcionUtil.unirCodigoDescripcion(this.tipoTransaccion, this.descripcion)));
				});
				if (opcionSeleccionada != null && opcionSeleccionada != undefined) {
					$local.$tiposTransacciones.val(opcionSeleccionada).trigger("change.select2");
				}
			},
			complete : function() {
				$local.$tiposTransacciones.parent().find(".cargando").remove();
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
		"ajax" : {
			"url" : $variableUtil.root + "codigoTxnBevertec?accion=buscarTodos",
			"dataSrc" : ""
		},
		"language" : {
			"emptyTable" : "No hay C\u00F3digos de Txn. Bevertec registrados"
		},
		"initComplete" : function() {
			$local.$tablaMantenimiento.wrap("<div class='table-responsive'></div>");
			$local.filtrosSeleccionables["0"] = $local.$canalesEmisoresFiltroParaTableMantenimiento.html();
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaMantenimiento, $local.filtrosSeleccionables);
		},
		"columnDefs" : [ {
			"targets" : [ 1, 2, 3 ],
			"className" : "all filtrable",
		}, {
			"targets" : 4,
			"className" : "all dt-center",
			"width" : "10%",
			"defaultContent" : $variableUtil.botonActualizar + " " + $variableUtil.botonEliminar
		} ],
		"columns" : [ {
			"className" : "all seleccionable select2 insertable-opciones-html",
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoCanalEmisor, row.descripcionCanalEmisor);
			},
			"title" : "Canal Emisor"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.tipoTransaccion, row.descripcionTipoTransaccion);
			},
			"title" : "Tipo de Transacci\u00F3n"
		}, {
			"data" : 'codTransaccion',
			"title" : "Cod. de Transacci\u00F3n"
		}, {
			"data" : 'descripcion',
			"title" : "Descripci\u00F3n"
		}, {
			"data" : null,
			"title" : 'Acci\u00F3n'
		} ]
	});

	$local.$tablaMantenimiento.find("thead").on('keyup', 'input', function() {
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaMantenimiento.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	$local.$modalMantenimiento.PopupWindow({
		title : "Mantenimiento de Codigo de Transacci\u00F3n Bevertec",
		autoOpen : false,
		modal : false,
		height : 380,
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
		$local.tipoTransaccionSeleccionado = 0;
		$local.codigoCanalEmisorSeleccionado = "";
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
		var codigoTxnBevertec = $formMantenimiento.serializeJSON();
		$.ajax({
			type : "POST",
			url : $variableUtil.root + "codigoTxnBevertec",
			data : JSON.stringify(codigoTxnBevertec),
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
			success : function(codigosTxnBevertec) {
				$funcionUtil.notificarException($variableUtil.registroExitoso, "fa-check", "Aviso", "success");
				var row = $local.tablaMantenimiento.row.add(codigosTxnBevertec[0]).draw();
				row.show().draw(false);
				$(row.node()).animateHighlight();
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
		var codigoTxnBevertec = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		$local.tipoTransaccionSeleccionado = codigoTxnBevertec.tipoTransaccion;
		$local.codigoCanalEmisorSeleccionado = codigoTxnBevertec.codigoCanalEmisor;
		$local.codTransaccionSeleccionado = codigoTxnBevertec.codTransaccion;
		$funcionUtil.llenarFormulario(codigoTxnBevertec, $formMantenimiento);
		$local.$canalesEmisores.trigger("change", [codigoTxnBevertec.tipoTransaccion]);
		$local.$actualizarMantenimiento.removeClass("hidden");
		$local.$registrarMantenimiento.addClass("hidden");
		$local.$modalMantenimiento.PopupWindow("open");
	});

	$local.$actualizarMantenimiento.on("click", function() {
		if (!$formMantenimiento.valid()) {
			return;
		}
		var codigoTxnBevertec = $formMantenimiento.serializeJSON();
		codigoTxnBevertec.tipoTransaccion = $local.tipoTransaccionSeleccionado;
		codigoTxnBevertec.codigoCanalEmisor = $local.codigoCanalEmisorSeleccionado;
		codigoTxnBevertec.codTransaccion = $local.codTransaccionSeleccionado;
		$.ajax({
			type : "PUT",
			url : $variableUtil.root + "codigoTxnBevertec",
			data : JSON.stringify(codigoTxnBevertec),
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
			success : function(codigosTxnBevertec) {
				$funcionUtil.notificarException($variableUtil.actualizacionExitosa, "fa-check", "Aviso", "success");
				$local.tablaMantenimiento.row($local.$filaSeleccionada).remove().draw(false);
				var row = $local.tablaMantenimiento.row($local.$filaSeleccionada).data(codigosTxnBevertec[0]).draw();
				row.show().draw(false);
				$(row.node()).animateHighlight();
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
		var codigoTxnBevertec = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		$.confirm({
			icon : "fa fa-info-circle",
			title : "Aviso",
			content : "\u00BFDesea eliminar el C\u00F3digo de Transacci\u00F3n Bevertec <b>'" + codigoTxnBevertec.codigoCanalEmisor + " - " + codigoTxnBevertec.tipoTransaccion + " - " + codigoTxnBevertec.descripcion + "'</b>?",
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
									url : $variableUtil.root + "codigoTxnBevertec",
									data : JSON.stringify(codigoTxnBevertec),
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
										var mensaje = $funcionUtil.obtenerMensajeError("El C\u00F3digo Transacci\u00F3n Bevertec <b>" + codigoTxnBevertec.codigoCanalEmisor + " - " + codigoTxnBevertec.tipoTransaccion + " - " + codigoTxnBevertec.descripcion + "</b>", xhr.responseJSON, $variableUtil.accionEliminado);
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