$(document).ready(function() {

	var $local = {
		$tablaTarifario : $("#tablaTarifario"),
		tablaTarifario : "",
		$modalTarifario : $("#modalTarifario"),
		$aniadirTarifario : $("#aniadirTarifario"),
		$registrarTarifario : $("#registrarTarifario"),
		$filaSeleccionada : "",
		$actualizarTarifario : $("#actualizarTarifario"),
		tipoTarifaSeleccionado : {},
		$esquemas : $("#esquemas"),
		arregloSiNo : ["0","1"],
		$esquemasFiltroParaTablaTarifario : $("#esquemas-filtroParaTablaTarifario"),
		filtrosSeleccionables : []
	}

	$formTarifario = $("#formTarifario");

	$.fn.dataTable.ext.errMode = 'none';
	
	$funcionUtil.crearSelect2($local.$esquemas, "Seleccione un esquema");

	$local.$tablaTarifario.on('xhr.dt', function(e, settings, json, xhr) {
		switch (xhr.status) {
		case 500:
			$local.tablaTarifario.clear().draw();
			break;
		}
	});

	$local.tablaTarifario = $local.$tablaTarifario.DataTable({
		"ajax" : {
			"url" : $variableUtil.root + "tipoTarifa?accion=buscarTodos",
			"dataSrc" : ""
		},
		"language" : {
			"emptyTable" : "No hay Tipo de Tarifas registrados"
		},
		"initComplete" : function() {
			$local.$tablaTarifario.wrap("<div class='table-responsive'></div>");
			$local.filtrosSeleccionables["4"] = $local.$esquemasFiltroParaTablaTarifario.html();
			$local.filtrosSeleccionables["2"] = $local.arregloSiNo;
			$local.filtrosSeleccionables["3"] = $local.arregloSiNo;
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaTarifario, $local.filtrosSeleccionables);
		},
		"columnDefs" : [ {
			"targets" : [ 0, 1, 4 ],
			"className" : "all filtrable",
		}, {
			"targets" : 2,
			"className" : "all seleccionable data-no-definida",
			"render" : function(data, type, row) {				
				return $funcionUtil.insertarEtiquetaSiNo(row.aplicaBin);
			}
		}, {
			"targets" : 3,
			"className" : "all seleccionable data-no-definida",
			"render" : function(data, type, row) {
				return $funcionUtil.insertarEtiquetaSiNo(row.diferenteTran);
			}
		}, {
			"targets" : 5,
			"className" : "all dt-center",
			"defaultContent" : $variableUtil.botonActualizar + " " + $variableUtil.botonEliminar
		} ],
		"columns" : [ {
			"data" : 'tipoTarifa',
			"title" : "Tipo Tarifa"
		}, {
			"data" : 'descripcion',
			"title" : "Descripci\u00F3n"
		}, {
			"data" : null,
			"title" : "Aplica Grupo BIN"
		}, {
			"data" : null,
			"title" : "Diferencia Transacci\u00F3n"
		}, {
			"className" : "all seleccionable select2 insertable-opciones-html",
			"data" : 'descripcionEsquema',
			"title" : "Esquema"
		}, {
			"data" : null,
			"title" : 'Acci\u00F3n'
		} ]
	});

	$local.$tablaTarifario.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaTarifario.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaTarifario.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaTarifario.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	$local.$modalTarifario.PopupWindow({
		title : "Tipo de Tarifa",
		autoOpen : false,
		modal : false,
		height : 400,
		width : 626,
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
		$local.tipoTarifaSeleccionado = {};
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
		var tipoTarifa = $formTarifario.serializeJSON();
		$.ajax({
			type : "POST",
			url : $variableUtil.root + "tipoTarifa",
			data : JSON.stringify(tipoTarifa),
			beforeSend : function(xhr) {
				$local.$registrarTarifario.attr("disabled", true).find("i").removeClass("fa-floppy-o").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode : {
				400 : function(response) {
					$funcionUtil.limpiarMensajesDeError($formTarifario);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formTarifario);
				}
			},
			success : function(tiposTarifa) {
				$funcionUtil.notificarException($variableUtil.registroExitoso, "fa-check", "Aviso", "success");
				var row = $local.tablaTarifario.row.add(tiposTarifa[0]).draw();
				row.show().draw(false);
				$(row.node()).animateHighlight();
				$local.$modalTarifario.PopupWindow("close");
			},
			error : function(response) {
			},
			complete : function(response) {
				$local.$registrarTarifario.attr("disabled", false).find("i").addClass("fa-floppy-o").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$tablaTarifario.children("tbody").on("click", ".actualizar", function() {
		$funcionUtil.prepararFormularioActualizacion($formTarifario);
		$local.$filaSeleccionada = $(this).parents("tr");		
		$local.tipoTarifaSeleccionado = $local.tablaTarifario.row($local.$filaSeleccionada).data();
		$funcionUtil.llenarFormulario($local.tipoTarifaSeleccionado, $formTarifario);
		$local.$actualizarTarifario.removeClass("hidden");
		$local.$registrarTarifario.addClass("hidden");
		$local.$modalTarifario.PopupWindow("open");
	});

	$local.$actualizarTarifario.on("click", function() {
		if (!$formTarifario.valid()) {
			return;
		}
		var tipoTarifa = $formTarifario.serializeJSON();
		$local.tipoTarifaSeleccionado.descripcion = tipoTarifa.descripcion;
		$local.tipoTarifaSeleccionado.aplicaBin = tipoTarifa.aplicaBin;
		$local.tipoTarifaSeleccionado.diferenteTran = tipoTarifa.diferenteTran;
		$local.tipoTarifaSeleccionado.codigoEsquema = tipoTarifa.codigoEsquema;
		$.ajax({
			type : "PUT",
			url : $variableUtil.root + "tipoTarifa",
			data : JSON.stringify($local.tipoTarifaSeleccionado),
			beforeSend : function(xhr) {
				$local.$actualizarTarifario.attr("disabled", true).find("i").removeClass("fa-pencil-square").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode : {
				400 : function(response) {
					$funcionUtil.limpiarMensajesDeError($formTarifario);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formTarifario);
				}
			},
			success : function(tiposTarifa) {
				$funcionUtil.notificarException($variableUtil.actualizacionExitosa, "fa-check", "Aviso", "success");
				var row = $local.tablaTarifario.row($local.$filaSeleccionada).data(tiposTarifa[0]).draw();
				row.show().draw(false);
				$(row.node()).animateHighlight();
				$local.$modalTarifario.PopupWindow("close");
			},
			error : function(response) {
			},
			complete : function(response) {
				$local.$actualizarTarifario.attr("disabled", false).find("i").addClass("fa-pencil-square").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$tablaTarifario.children("tbody").on("click", ".eliminar", function() {
		$local.$filaSeleccionada = $(this).parents("tr");
		var tipoTarifa = $local.tablaTarifario.row($local.$filaSeleccionada).data();
		$.confirm({
			icon : "fa fa-info-circle",
			title : "Aviso",
			content : "\u00BFDesea eliminar el Tipo de Tarifa <b>'" + tipoTarifa.tipoTarifa + " - " + tipoTarifa.descripcion + "'</b>?",
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
									url : $variableUtil.root + "tipoTarifa",
									data : JSON.stringify(tipoTarifa),
									autoclose : true,
									beforeSend : function(xhr) {
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
										var mensaje = $funcionUtil.obtenerMensajeError("El Tipo de Tarifa <b>" + tipoTarifa.tipoTarifa + " - " + tipoTarifa.descripcion + "</b>", xhr.responseJSON, $variableUtil.accionEliminado);
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