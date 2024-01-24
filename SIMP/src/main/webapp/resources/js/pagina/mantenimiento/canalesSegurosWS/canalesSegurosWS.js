$(document).ready(function() {

	var $local = {
		$tablaMantenimiento : $("#tablaMantenimiento"),
		tablaMantenimiento : "",
		$modalMantenimiento : $("#modalMantenimiento"),
		$filaSeleccionada : "",
		// Botones de Mantenimiento
		$aniadirMantenimento : $("#aniadirMantenimiento"),
		$registrarMantenimiento : $("#registrarMantenimiento"),
		$actualizarMantenimiento : $("#actualizarMantenimiento"),
		// Select de formulario de Mantenimiento
		$idCanal : $("#idCanal"),
		$contrasenia : $("#contrasenia"),
		$password2  : $("#password2"),
		arregloSiNo : [ "1", "0" ],
		idCanalSeleccionado : "",
		// Filtros para Tabla Mantenimiento
		filtrosSeleccionables : {},
		$canalesSegurosWSFiltroParaTableMantenimiento : $("#canalesSegurosWS-filtroParaTablaMantenimiento"),
		// Permisos
		permisoActualizacion : false,
		permisoEliminacion : false,
		
		$empresaSelect: $('#empresaSelect'),
		$clienteSelect: $('#clienteSelect')
	};

	$formMantenimiento = $("#formMantenimiento");

	$funcionUtil.crearSelect2($local.$empresaSelect, "Seleccione una Empresa");
	$funcionUtil.crearSelect2($local.$clienteSelect, "Seleccione un Cliente");
	
	$.fn.dataTable.ext.errMode = 'none';

	$local.$tablaMantenimiento.on('xhr.dt', function(e, settings, json, xhr) {
		if (xhr.status == 500) {
			$local.tablaMantenimiento.clear().draw();
			$funcionUtil.notificarException(xhr.responseText, "Error Interno", "danger");
		}
	});
	
	$local.$empresaSelect.on("change", function(event, opcionSeleccionada) {
		var opcionSeleccionada = $local.$empresaSelect.val();
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "cliente/empresa/"+opcionSeleccionada,
			beforeSend : function(xhr) {
				$local.$clienteSelect.find("option:not(:eq(0))").remove();
				$local.$clienteSelect.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Clientes</span>")
			},
			success : function(response) {
				$.each(response, function(i, sector) {
					$local.$clienteSelect.append($("<option/>").val(this.idCliente).text(this.idCliente + " - " + this.descripcion));
				});
			},
			complete : function() {
				$local.$clienteSelect.parent().find(".cargando").remove();
			}
		});
	});

	$local.permisoActualizacion = $local.$tablaMantenimiento.attr("data-permiso-actualizacion") || false;
	$local.permisoEliminacion = $local.$tablaMantenimiento.attr("data-permiso-eliminacion") || false;

	$local.tablaMantenimiento = $local.$tablaMantenimiento.DataTable({
		"ajax" : {
			"url" : $variableUtil.root + "canalesSegurosWS?accion=buscarTodos",
			"dataSrc" : ""
		},
		"language" : {
			"emptyTable" : "No hay Canales Seguros registrados."
		},
		"initComplete" : function() {
			$local.filtrosSeleccionables["0"] = $local.$canalesSegurosWSFiltroParaTableMantenimiento.html();
			$local.filtrosSeleccionables["1"] = $local.arregloSiNo;
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaMantenimiento, $local.filtrosSeleccionables);
		},
		"columnDefs" : [ {
			"targets" : [0,2,3],
			"className" : "all filtrable",
		}, {
			"targets" : 4,
			"className" : "all dt-center",
			"render" : function() {
				return "" + ($local.permisoActualizacion == "true" ? $variableUtil.botonActualizar : "") + " " + ($local.permisoEliminacion == "true" ? $variableUtil.botonEliminar : "");
			}
		}, {
			"targets" : 1,
			"className" : "all dt-center seleccionable data-no-definida",
			"render" : function(data, type, row) {
				return $funcionUtil.insertarEtiquetaSiNo(row.activo);
			}
		}],
		"columns" : [ {
			"className" : "all  seleccionable select2",
			"data" : "idCanal",
			"title" : "Canales Seguros"
		},{
			"data" : null,
			"title": "Activo"
		},{
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idEmpresa, row.descripcionEmpresa);
			},
			"title": "Empresa"
		},{
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idCliente, row.descripcionCliente);
			},
			"title": "Cliente"
		},{
			"data" : null,
			"title" : 'Acci&oacute;n'
		}]
	});

	

	$local.$tablaMantenimiento.wrap("<div class='table-responsive'></div>");

	$local.$tablaMantenimiento.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaMantenimiento.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	$local.$modalMantenimiento.PopupWindow({
		title : "Mantenimiento de Canales Seguros Servicios Web",
		autoOpen : false,
		modal : false,
		height : 440,
		width : 626,
	});
	
	$local.$modalMantenimiento.on("open.popupwindow", function() {
		$formMantenimiento.find("input:not([disabled]):first").focus();
	});

	$local.$aniadirMantenimento.on("click", function() {
		$funcionUtil.prepararFormularioRegistro($formMantenimiento);
		$local.$actualizarMantenimiento.addClass("hidden");
		$local.$registrarMantenimiento.removeClass("hidden");
		$local.$contrasenia.val("");
		$local.$password2.val("");
		$local.$clienteSelect.empty()
		$local.$modalMantenimiento.PopupWindow("open");
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
		var contrasenia = $local.$contrasenia.val();
		var password2 = $local.$password2.val();
		if (contrasenia != password2){
			$funcionUtil.notificarException("Contrase\u00F1as no son iguales", "fa-warning", "Aviso", "warning");
            return; 
        }
		var canalWS = $formMantenimiento.serializeJSON();
		canalWS.contrasenia = $local.$contrasenia.val();
		canalWS.password2 = $local.$password2.val();
		$.ajax({
			type : "POST",
			url : $variableUtil.root + "canalesSegurosWS",
			data : JSON.stringify(canalWS),
			beforeSend : function(xhr) {
				$local.$registrarMantenimiento.attr("disabled", true).find("i").removeClass("fa-floppy-o").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode : {
				400 : function(response) {
					$funcionUtil.limpiarMensajesDeError($formMantenimiento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formMantenimiento);
				},
				409 : function(response) {
					$funcionUtil.notificarException("Hubo un problema en el registro del canal seguro. Revisar si existe un canal para la empresa ("+canalWS.idEmpresa+")"+" y cliente ("+canalWS.idCliente+") y canal: "+canalWS.idCanal, "fa-warning", "Aviso", "warning");
				}
			},
			success : function(response) {
				$funcionUtil.notificarException($variableUtil.registroExitoso, "fa-check", "Aviso", "success");
				if ($local.$tablaMantenimiento.length > 0) {
					var canalWS = response[0];
					var row = $local.tablaMantenimiento.row.add(canalWS).draw();
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
		var canalWS = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		$local.idCanalSeleccionado = canalWS.idCanal;
		$funcionUtil.llenarFormulario(canalWS, $formMantenimiento);
		$local.$clienteSelect.find('option').remove().end().append($("<option></option>").attr("value", canalWS.idCliente).text(canalWS.idCliente + ' - ' + canalWS.descripcionCliente)).prop('selected', true); 
		$local.$actualizarMantenimiento.removeClass("hidden");
		$local.$registrarMantenimiento.addClass("hidden");
		$local.$contrasenia.val("");
		$local.$password2.val("");
		$local.$modalMantenimiento.PopupWindow("open");
	});

	$local.$actualizarMantenimiento.on("click", function() {
		if (!$formMantenimiento.valid()) {
			return;
		}
		var contrasenia = $local.$contrasenia.val();
		var password2 = $local.$password2.val();
		if (contrasenia != password2){
			$funcionUtil.notificarException("Contrase\u00F1as no son iguales", "fa-warning", "Aviso", "warning");
            return; 
        }
		var canalWS = $formMantenimiento.serializeJSON();
		canalWS.contrasenia = $local.$contrasenia.val();
		canalWS.password2 = $local.$password2.val();
		canalWS.idEmpresa = $local.$empresaSelect.val();
		canalWS.idCliente = $local.$clienteSelect.val();
		canalWS.idCanal = $local.idCanalSeleccionado;
		$.ajax({
			type : "PUT",
			url : $variableUtil.root + "canalesSegurosWS",
			data : JSON.stringify(canalWS),
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
			success : function(response) {
				$funcionUtil.notificarException($variableUtil.actualizacionExitosa, "fa-check", "Aviso", "success");
				if ($local.$tablaMantenimiento.length > 0) {
					$local.tablaMantenimiento.row($local.$filaSeleccionada).remove().draw(false);
					var row = $local.tablaMantenimiento.row.add(response[0]).draw();
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
		var canalWS = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		$.confirm({
			icon : "fa fa-info-circle",
			title : "Aviso",
			content : "&iquest;Desea eliminar el Canal <b>'" + canalWS.idCanal + "'</b>?",
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
									url : $variableUtil.root + "canalesSegurosWS",
									data : JSON.stringify(canalWS),
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
										var mensaje = $funcionUtil.obtenerMensajeError("El Canal <b>" + canalWS.idCanal +  "</b>", xhr.responseJSON, $variableUtil.accionEliminado);
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