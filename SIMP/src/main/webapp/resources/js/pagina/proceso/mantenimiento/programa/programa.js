$(document).ready(function() {

	var $local = {
		$tablaMantenimiento : $("#tablaMantenimiento"),
		tablaMantenimiento : "",
		$modalMantenimiento : $("#modalMantenimiento"),
		$aniadirMantenimiento : $("#aniadirMantenimiento"),
		$registrarMantenimiento : $("#registrarMantenimiento"),
		$filaSeleccionada : "",
		$actualizarMantenimiento : $("#actualizarMantenimiento"),
		programaSeleccionado : {},
		$grupos : $("#grupos"),
		$subModulos : $("#subModulos"),
		$programasFiltroParaTablaMantenimiento: $("#programas-filtroParaTablaMantenimiento"),
		$nombresProcedimientos : $("#nombresProcedimientos"),
		$gruposFiltroParaTablaMantenimiento : $("#grupos-filtroParaTablaMantenimiento"),
		$subModulosFiltroParaTablaMantenimiento : $("#subModulos-filtroParaTablaMantenimiento"),
		arregloSiNo : [ "0", "1" ],
		filtrosSeleccionables : [],
		// Permisos
		permisoActualizacion : false,
		permisoEliminacion : false
	}

	$formMantenimiento = $("#formMantenimiento");

	$.fn.dataTable.ext.errMode = 'none';

	$funcionUtil.crearSelect2($local.$grupos, "Seleccione un Grupo");
	$funcionUtil.crearSelect2($local.$subModulos, "Seleccione un Sub M\u00f3dulo");

	$local.$tablaMantenimiento.on('xhr.dt', function(e, settings, json, xhr) {
		if (xhr.status == 500) {
			$local.tablaMantenimiento.clear().draw();
			$funcionUtil.notificarException(xhr.responseText, "Error Interno", "danger");
		}
	});

	$local.permisoActualizacion = $local.$tablaMantenimiento.attr("data-permiso-actualizacion") || false;
	$local.permisoEliminacion = $local.$tablaMantenimiento.attr("data-permiso-eliminacion") || false;

	$local.tablaMantenimiento = $local.$tablaMantenimiento.DataTable({
		"ajax" : {
			"url" : $variableUtil.root + "proceso/mantenimiento/programa?accion=buscarTodos",
			"dataSrc" : ""
		},
		"language" : {
			"emptyTable" : "No hay Programas registrados"
		},
		"initComplete" : function() {
			$local.$tablaMantenimiento.wrap("<div class='table-responsive'></div>");
			$local.filtrosSeleccionables["0"] = $local.$gruposFiltroParaTablaMantenimiento.html();
			$local.filtrosSeleccionables["1"] = $local.$programasFiltroParaTablaMantenimiento.html();
			$local.filtrosSeleccionables["2"] = $local.$subModulosFiltroParaTablaMantenimiento.html();
			$local.filtrosSeleccionables["7"] = $local.arregloSiNo;
			$local.filtrosSeleccionables["9"] = $local.arregloSiNo;
			$local.filtrosSeleccionables["10"] = $local.arregloSiNo;
			$local.filtrosSeleccionables["11"] = $local.arregloSiNo;
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaMantenimiento, $local.filtrosSeleccionables);

		},
		"ordering" : false,
		"order" : [ [ 0, 'asc' ], [ 5, 'asc' ] ],
		"columnDefs" : [ {
			"targets" : [ 3, 4, 5, 6, 8 ],
			"className" : "filtrable",
		}, {
			"targets" : 12,
			"className" : "all dt-center",
			"render" : function() {
				return "" + ($local.permisoActualizacion == "true" ? $variableUtil.botonActualizar : "") + " " + ($local.permisoEliminacion == "true" ? $variableUtil.botonEliminar : "");
			}
		} ],
		"columns" : [ {
			"className" : "all seleccionable select2 insertable-opciones-html",
			"data" : function(row) {
				return row.ordenEjecucionGrupo + " - " + $funcionUtil.unirCodigoDescripcion(row.codigoGrupo, row.descripcionGrupo);
			},
			"title" : "Grupo"
		}, {
			"className": "all seleccionable select2 insertable-opciones-html",
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoPrograma, row.descripcion);
			},
			"title" : "Programa"
		}, {
			"className" : "all seleccionable select2 insertable-opciones-html",
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoSubModulo, row.descripcionSubModulo);
			},
			"title" : "Sub M\u00f3dulo"
		}, {
			"data" : 'procedimiento',
			"title" : "Procedimiento"
		}, {
			"data" : 'archivo',
			"title" : "Archivo"
		}, {
			"data" : 'ordenEjecucion',
			"title" : "Orden de Ejecuci\u00f3n"
		}, {
			"data" : 'periodoEjecucion',
			"title" : "Periodo de Ejecuci\u00f3n"
		}, {
			"className" : "all seleccionable select2 data-no-definida",
			"data" : function(row) {
				return $funcionUtil.insertarEtiquetaSiNo(row.procesaSabado);
			},
			"title" : "Procesa S\u00e1bado"
		}, {
			"data" : 'longitud',
			"title" : "Longitud"
		}, {
			"className" : "all seleccionable select2 data-no-definida",
			"data" : function(row) {
				return $funcionUtil.insertarEtiquetaSiNo(row.ejecucionDetallada);
			},
			"title" : "E. detallada"
		}, {
			"className" : "all seleccionable select2 data-no-definida",
			"data" : function(row) {
				return $funcionUtil.insertarEtiquetaSiNo(row.ejecucionObligatoria);
			},
			"title" : "E. obligatoria"
		}, {
			"className" : "all seleccionable select2 data-no-definida",
			"data" : function(row) {
				return $funcionUtil.insertarEtiquetaSiNo(row.ejecucionPorInstitucion);
			},
			"title" : "E. Por instituci\u00f3n"
		}, {
			"data" : null,
			"title" : 'Acci\u00f3n'
		} ],
		"headerCallback" : function(thead, data, start, end, display) {
			$(thead).find('th').eq(9).attr({
				"data-tooltip" : "tooltip",
				"title" : "Ejecuci\u00f3n detallada"
			});
			$(thead).find('th').eq(10).attr({
				"data-tooltip" : "tooltip",
				"title" : "Ejecuci\u00f3n obligatoria"
			});
			$(thead).find('th').eq(11).attr({
				"data-tooltip" : "tooltip",
				"title" : "Ejecuci\u00f3n por instituci\u00F3n. Si se marca este campo, el programa se ejecutar\u00E1 para todas las instituciones marcadas como instituci\u00F3n empresa."
			});
		}
	});

	$local.$tablaMantenimiento.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaMantenimiento.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	$local.$modalMantenimiento.PopupWindow({
		title : "Programa",
		autoOpen : false,
		modal : false,
		height : 600,
		width : 626,
	});

	$local.$aniadirMantenimiento.on("click", function() {
		$funcionUtil.prepararFormularioRegistro($formMantenimiento);
		$local.$actualizarMantenimiento.addClass("hidden");
		$local.$registrarMantenimiento.removeClass("hidden");
		$local.$modalMantenimiento.PopupWindow("open");
	});

	$local.$modalMantenimiento.on("open.popupwindow", function() {
		$formMantenimiento.find("input:not([disabled]):first").focus();
	});

	$local.$modalMantenimiento.on("close.popupwindow", function() {
		$local.programaSeleccionado = {};
		$local.$filaSeleccionada = "";
	});
	
	
	$local.$tablaMantenimiento.find("thead").on('change', 'select:eq(0)', function() {
		var codigoGrupo = $(this).find('option:selected').attr('data-value');
		var selectProgramas = $local.$tablaMantenimiento.find("thead select:eq(1)");
		if(codigoGrupo) {
			$.ajax({
				url: $variableUtil.root + "/proceso/mantenimiento/programa/grupo/"+ codigoGrupo,
				method: "GET",
				beforeSend: function(xhr){
					selectProgramas.find("option:not(:eq(0))").remove();
					xhr.setRequestHeader('Content-Type', 'application/json');
					xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
				},
				success: function(programas){
					$.each(programas, function(i, programa) {
						selectProgramas.append($("<option />").val(this.codigoPrograma + " - " + this.descripcion).text(this.codigoPrograma + " - " + this.descripcion));
					});
				}
			});
		} else {
			selectProgramas.find("option:not(:eq(0))").remove();
		}
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
	
	$local.$grupos.on('change', function(){
		var codigoGrupo = $(this).val();
		var activar = codigoGrupo !== 'TAB_CTRL_PROC_RES' && codigoGrupo !== 'AVFP';
		$funcionUtil.activarCheckbox($formMantenimiento.find('input[name="ejecucionPorInstitucion"]'), activar);
	});

	$local.$registrarMantenimiento.on("click", function() {
		if (!$formMantenimiento.valid()) {
			return;
		}
		var programa = $formMantenimiento.serializeJSON();
		$.ajax({
			type : "POST",
			url : $variableUtil.root + "proceso/mantenimiento/programa",
			data : JSON.stringify(programa),
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
			success : function(programas) {
				$funcionUtil.notificarException($variableUtil.registroExitoso, "fa-check", "Aviso", "success");
				if ($local.$tablaMantenimiento.length > 0) {
					var row = $local.tablaMantenimiento.row.add(programas[0]).draw();
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
		$local.programaSeleccionado = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		$funcionUtil.llenarFormulario($local.programaSeleccionado, $formMantenimiento);
		$local.$actualizarMantenimiento.removeClass("hidden");
		$local.$registrarMantenimiento.addClass("hidden");
		$local.$modalMantenimiento.PopupWindow("open");
		$local.$grupos.trigger('change');
	});

	$local.$actualizarMantenimiento.on("click", function() {
		if (!$formMantenimiento.valid()) {
			return;
		}
		var programa = $formMantenimiento.serializeJSON();
		programa.codigoGrupo = $local.programaSeleccionado.codigoGrupo;
		programa.codigoPrograma = $local.programaSeleccionado.codigoPrograma;
		programa.codigoSubModulo = $local.programaSeleccionado.codigoSubModulo;
		$.ajax({
			type : "PUT",
			url : $variableUtil.root + "proceso/mantenimiento/programa",
			data : JSON.stringify(programa),
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
			success : function(programas) {
				$funcionUtil.notificarException($variableUtil.actualizacionExitosa, "fa-check", "Aviso", "success");
				if ($local.$tablaMantenimiento.length > 0) {
					var programa = programas[0];
					var row = $local.tablaMantenimiento.row($local.$filaSeleccionada).data(programa).draw();
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
		var programa = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		$.confirm({
			icon : "fa fa-info-circle",
			title : "Aviso",
			content : "\u00BFDesea eliminar el programa <b>" + programa.codigoPrograma + "-" + programa.descripcion + "</b>? ",
			theme : "bootstrap",
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
									url : $variableUtil.root + "proceso/mantenimiento/programa",
									data : JSON.stringify(programa),
									autoclose : true,
									beforeSend : function(xhr) {
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
										var mensaje = $funcionUtil.obtenerMensajeError("El Programa", xhr.responseJSON, $variableUtil.accionEliminado);
										$funcionUtil.notificarException(mensaje, "fa-warning", "Aviso", "warning");
										break;
									}
								}).always(function(xhr) {
									confirmar.close();
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