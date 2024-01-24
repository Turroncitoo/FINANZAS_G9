$(document).ready(function() {

	/* Variable Global */
	$formMantenimiento = $("#formMantenimiento");

	/* Variable Local */
	var $local = {
		$tablaMantenimiento : $("#tablaMantenimiento"),
		tablaMantenimiento : "",
		$modalMantenimiento : $("#modalMantenimiento"),
		$filaSeleccionada : "",
		idUsuarioSeleccionado : "",
		// Botones de formulario Mantenimiento
		$aniadirMantenimento : $("#aniadirMantenimiento"),
		$registrarMantenimiento : $("#registrarMantenimiento"),
		$actualizarMantenimiento : $("#actualizarMantenimiento"),
		$generarContrasenia : $("#generarContrasenia"),
		// Campos de formulario de Mantenimiento
		$idUsuario : $("#idUsuario"),
		$contrasenias : $(".contrasenia"),
		$divCambiarContrasenia : $("#div-cambiarContrasenia"),
		$checkboxCambioContrasenia : $("#checkbox-cambioContrasenia"),
		// Label de formulario de Mantenimiento
		$labelNuevaContrasenia : $("#label-nuevaContrasenia"),
		$labelContrasenia : $("#label-contrasenia"),
		// Select de formulario de Mantenimiento
		$perfiles : $("#perfiles"),
		// Check visualiza PAN en formulario
		checkVisualizarTarjeta: $("#checkVisualizarTarjeta"),
		// Filtros para Tabla Mantenimiento
		filtrosSeleccionables : {},
		$perfilesFiltroParaTableMantenimiento : $("#perfiles-filtroParaTablaMantenimiento"),
		arregloSiNo : [ "1", "0" ],
		// Permisos
		permisoActualizacion : false,
		permisoEliminacion : false
	};

	$funcionUtil.crearSelect2($local.$perfiles, "Seleccione un Perfil");

	$.fn.dataTable.ext.errMode = 'none';

	$local.$tablaMantenimiento.on('xhr.dt', function(e, settings, json, xhr) {
		if (xhr.status == 500) {
			$local.tablaMantenimiento.clear().draw();
		}
	});

	$local.permisoActualizacion = $local.$tablaMantenimiento.attr("data-permiso-actualizacion") || false;
	$local.permisoEliminacion = $local.$tablaMantenimiento.attr("data-permiso-eliminacion") || false;

	$local.tablaMantenimiento = $local.$tablaMantenimiento.DataTable({
		"ajax" : {
			"url" : $variableUtil.root + "seguridad/usuario?accion=buscarTodos",
			"dataSrc" : ""
		},
		"language" : {
			"emptyTable" : "No existen Usuarios registrados."
		},
		"initComplete" : function() {
			$local.$tablaMantenimiento.wrap("<div class='table-responsive'></div>");
			$local.filtrosSeleccionables["1"] = $local.$perfilesFiltroParaTableMantenimiento.html();
			$local.filtrosSeleccionables["2"] = $local.arregloSiNo;
			$local.filtrosSeleccionables["3"] = $local.arregloSiNo;
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaMantenimiento, $local.filtrosSeleccionables);
		},
		"columnDefs" : [ {
			"targets" : 2,
			"className" : "all seleccionable select2 data-no-definida",
			"render" : function(data, type, row) {
				return $funcionUtil.insertarEtiquetaSiNo(row.activo);
			}
		}, {
			"targets" : 3,
			"className" : "all seleccionable select2 data-no-definida",
			"render" : function(data, type, row) {
				return $funcionUtil.insertarEtiquetaSiNo(row.visualizaPAN);
			}
		}, {
			"targets" : 4,
			"className" : "all dt-center",
			"render" : function() {
				return "" + ($local.permisoActualizacion == "true" ? $variableUtil.botonActualizar : "") + " " + ($local.permisoEliminacion == "true" ? $variableUtil.botonEliminar : "");
			}
		} ],
		"columns" : [ {
			"className" : "all filtrable",
			"data" : "idUsuario",
			"title" : 'Usuario'
		}, {
			"className" : "all seleccionable select2 insertable-opciones-html",
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idPerfil, row.descripcionPerfil);
			},
			"title" : "Perfil"
		}, {
			"data" : null,
			"title" : "Activo"
		}, {
			"data": null,
			"title": "Visualiza PAN"
		}, {
			"data" : null,
			"title" : "Acci\u00f3n"
		} ]
	});

	$local.$tablaMantenimiento.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	$local.$tablaMantenimiento.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$modalMantenimiento.PopupWindow({
		title : "Mantenimiento de Usuario",
		autoOpen : false,
		modal : false,
		height : 484,
		width : 626,
	});

	$local.$modalMantenimiento.on("open.popupwindow", function() {
		$formMantenimiento.find("input:not([disabled]):first").focus();
	});

	$local.$perfiles.on('change', function(){
		var puedeVerTarjeta = $(this).find('option:selected').attr('data-puedeVisualizarTarjeta');
		activarCheckVisualizarTarjeta($local.checkVisualizarTarjeta, puedeVerTarjeta);
	});

	$local.$generarContrasenia.on("click", function() {
		var idUsuario = $local.$idUsuario.val();
		if (idUsuario == null || idUsuario == "") {
			$funcionUtil.notificarException("Ingrese un Nombre de Usuario.", "fa-check", "Aviso", "info");
			return;
		}
		var contraseniaPorDefecto = idUsuario + "1234aA";
		$local.$contrasenias.val("").val(contraseniaPorDefecto).first().select();
	});

	$local.$checkboxCambioContrasenia.on("change", function() {
		var cambioContrasenia = $(this).prop("checked");
		$local.$contrasenias.prop("disabled", !cambioContrasenia).val("").first().select();
		$local.$generarContrasenia.prop("disabled", !cambioContrasenia);
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

	$local.$aniadirMantenimento.on("click", function() {
		$funcionUtil.prepararFormularioRegistro($formMantenimiento);
		$local.$actualizarMantenimiento.addClass("hidden");
		$local.$registrarMantenimiento.removeClass("hidden");
		$local.$labelNuevaContrasenia.addClass("hidden");
		$local.$labelContrasenia.removeClass("hidden");
		$local.$divCambiarContrasenia.addClass("hidden");
		$local.$contrasenias.prop("disabled", false);
		$local.$generarContrasenia.prop("disabled", false);
		$local.$modalMantenimiento.PopupWindow("open");
	});

	$local.$registrarMantenimiento.on("click", function() {
		if (!$formMantenimiento.valid()) {
			return;
		}
		var usuario = $formMantenimiento.serializeJSON();
		$.ajax({
			type : "POST",
			url : $variableUtil.root + "seguridad/usuario",
			data : JSON.stringify(usuario),
			beforeSend : function(xhr) {
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
				$local.$registrarMantenimiento.attr("disabled", true).find("i").removeClass("fa-floppy-o").addClass("fa-spinner fa-pulse fa-fw");
			},
			statusCode : {
				400 : function(response) {
					$funcionUtil.limpiarMensajesDeError($formMantenimiento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formMantenimiento);
				}
			},
			success : function(usuarios) {
				$funcionUtil.notificarException($variableUtil.registroExitoso, "fa-check", "Aviso", "success");
				if ($local.$tablaMantenimiento.length > 0) {
					var usuario = usuarios[0];
					var row = $local.tablaMantenimiento.row.add(usuario).draw();
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
		$local.$labelContrasenia.addClass("hidden");
		$local.$labelNuevaContrasenia.removeClass("hidden");
		$local.$divCambiarContrasenia.removeClass("hidden");
		$local.$contrasenias.prop("disabled", true);
		$local.$generarContrasenia.prop("disabled", true);
		$funcionUtil.prepararFormularioActualizacion($formMantenimiento);
		$local.$filaSeleccionada = $(this).parents("tr");
		var usuario = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		$local.idUsuarioSeleccionado = usuario.idUsuario;
		$funcionUtil.llenarFormulario(usuario, $formMantenimiento);
		activarCheckVisualizarTarjeta($local.checkVisualizarTarjeta, usuario.visualizaPANPerfil);
		$local.$actualizarMantenimiento.removeClass("hidden");
		$local.$registrarMantenimiento.addClass("hidden");
		$local.$modalMantenimiento.PopupWindow("open");
	});

	$local.$actualizarMantenimiento.on("click", function() {
		if (!$formMantenimiento.valid()) {
			return;
		}
		var usuario = $formMantenimiento.serializeJSON();
		if (usuario.cambioContrasenia == "false") {
			usuario.contrasenia = "-1";
		}
		usuario.idUsuario = $local.idUsuarioSeleccionado;
		$.ajax({
			type : "PUT",
			url : $variableUtil.root + "seguridad/usuario",
			data : JSON.stringify(usuario),
			beforeSend : function(xhr) {
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
				$local.$actualizarMantenimiento.attr("disabled", true).find("i").removeClass("fa-pencil-square").addClass("fa-spinner fa-pulse fa-fw");
			},
			statusCode : {
				400 : function(response) {
					$funcionUtil.limpiarMensajesDeError($formMantenimiento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formMantenimiento);
				}
			},
			success : function(usuarios) {
				$funcionUtil.notificarException($variableUtil.actualizacionExitosa, "fa-check", "Aviso", "success");
				if ($local.$tablaMantenimiento.length > 0) {
					var usuario = usuarios[0];
					var row = $local.tablaMantenimiento.row($local.$filaSeleccionada).data(usuario).draw();
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
		var usuario = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		$.confirm({
			icon : "fa fa-info-circle",
			title : "Aviso",
			content : "\u00bfDesea eliminar el Usuario <b>'" + usuario.idUsuario + "'</b>?",
			theme : "bootstrap",
			type : "orange",
			buttons : {
				Aceptar : {
					action : function() {
						var confirmar = $.confirm({
							icon : 'fa fa-spinner fa-pulse fa-fw',
							title : "Eliminando...",
							theme : "bootstrap",
							type : "blue",
							content : function() {
								var self = this;
								self.buttons.close.hide();
								$.ajax({
									type : "DELETE",
									url : $variableUtil.root + "seguridad/usuario",
									data : JSON.stringify(usuario),
									autoclose : true,
									beforeSend : function(xhr) {
										xhr.setRequestHeader('Content-Type', 'application/json');
										xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
									},
									statusCode : {
										409 : function(response) {
											if (response.responseJSON == undefined) {
												$funcionUtil.notificarException(response.responseText, "fa-warning", "Aviso", "warning");
											} else {
												var mensaje = $funcionUtil.obtenerMensajeError("El Usuario <b>" + usuario.idUsuario + "</b>", response.responseJSON, $variableUtil.accionEliminado);
												$funcionUtil.notificarException(mensaje, "fa-warning", "Aviso", "warning");
											}
										},
										400 : function(response) {
											$funcionUtil.notificarException($funcionUtil.obtenerMensajeErrorEnCadena(response.responseJSON), "fa-warning", "Aviso", "warning");
										}
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
										var mensaje = $funcionUtil.obtenerMensajeError("El Usuario <b>" + usuario.idUsuario + "</b>", xhr.responseJSON, $variableUtil.accionEliminado);
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
					btnClass : "btn-warning"
				},
				Cancelar : {
					keys : [ 'esc' ]
				},
			}
		});
	});

	function activarCheckVisualizarTarjeta(input, permiso){
		input.removeAttr('disabled');
		if(!permiso || permiso == 'false'){
			input.attr('disabled','disabled');
		}
	}

});