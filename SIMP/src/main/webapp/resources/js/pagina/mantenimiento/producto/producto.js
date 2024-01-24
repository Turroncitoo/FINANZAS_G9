$(document).ready(function() {
	let $local = {
		// Constantes
		$MAX_CODIGO_INSTITUCION: 3,

		// Variables
		codigoProductoSeleccionado: "",
		codigoBinSeleccionado: "",
		codigoLogoSeleccionado: "",

		$tablaMantenimiento: $("#tablaMantenimiento"),
		tablaMantenimiento: "",
		$modalMantenimiento: $("#modalMantenimiento"),
		$filaSeleccionada: "",

		$formularioAdicionalMantenimiento: $("#formularioAdicionalMantenimiento"),
		$tablaClienteProductoAsociados: $("#tablaClienteProductoAsociados"),
		tablaClienteProductoAsociados: "",

		$asociarClienteProducto: $("#asociarClienteProducto"),

		// Selects e imputs
		$logo: $("#logo"),
		$codigoInstitucion: $("#codigoInstitucion"),
		$empresas: $("#empresas"),
		$cliente: $("#cliente"),
		$moneda: $("#moneda"),
		$longitudBin: $("#longitudBin"),

		$legend: $("#legend"),



		// Botones de Mantenimiento
		$aniadirMantenimento: $("#aniadirMantenimiento"),
		$registrarMantenimiento: $("#registrarMantenimiento"),
		$actualizarMantenimiento: $("#actualizarMantenimiento"),

		// Filtros para Tabla Mantenimiento
		filtrosSeleccionables: {},
		$codigoInstitucionIdentificador: $("#instituciones-filtroParaTablaMantenimiento").val().split(" - ")[0],

		// Permisos
		permisoActualizacion: false,
		permisoEliminacion: false,
	};

	let formMantenimiento = $("#formMantenimiento");

	$formAsociacionClienteProducto = $("#formAsociacionClienteProducto");

	$funcionUtil.crearSelect2($local.$logo, "Seleccione un Logo");
	$funcionUtil.crearSelect2($local.$empresas, "Seleccione una Empresa");
	$funcionUtil.crearSelect2($local.$cliente, "Seleccione un Cliente");
	$funcionUtil.crearSelect2($local.$moneda, "Seleccione una Moneda");

	$.fn.dataTable.ext.errMode = 'none';

	$local.$tablaMantenimiento.on('xhr.dt', function(e, settings, json, xhr) {
		if (xhr.status == 500) {
			$local.tablaMantenimiento.clear().draw();
			$funcionUtil.notificarException(xhr.responseText, "Error Interno", "danger");
		}
	});

	$local.permisoActualizacion = $local.$tablaMantenimiento.attr("data-permiso-actualizacion") || false;
	$local.permisoEliminacion = $local.$tablaMantenimiento.attr("data-permiso-eliminacion") || false;

	$local.tablaMantenimiento = $local.$tablaMantenimiento.DataTable({
		"ajax": {
			"url": $variableUtil.root + "producto?accion=buscarTodos",
			"dataSrc": ""
		},
		"language": {
			"emptyTable": "No hay Prodcutos registrados."
		},
		"initComplete": function() {
			$local.$tablaMantenimiento.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaMantenimiento);
		},
		"columnDefs": [
			{
				"targets": [0, 1, 2, 3],
				"className": "all filtrable",
			},
			{
				"targets": 4,
				"className": "all dt-center",
				"render": function() {
					return "" + ($local.permisoActualizacion == "true" ? $variableUtil.botonActualizar : "") + " " + ($local.permisoEliminacion == "true" ? $variableUtil.botonEliminar : "");
				}
			}
		],
		"order": [
			[0, 'asc']
		],
		"columns": [
			{
				"data": function(row) {
					return `${$funcionUtil.unirCodigoDescripcion(row.idLogo, row.descLogo)} (${row.idBin})`;
				},
				"title": "Logo"
			}, {
				"data": 'codigoProducto',
				"title": "C\u00F3digo"
			}, {
				"data": 'descProducto',
				"title": "Descripci\u00F3n"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.codigoMoneda, row.descMoneda);
				},
				"title": "Moneda"
			}, {
				"data": null,
				"title": "Acci\u00F3n"
			}
		]
	});

	$local.$tablaMantenimiento.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.tablaClienteProductoAsociados = $local.$tablaClienteProductoAsociados.DataTable({
		"lengthChange": false,
		"pageLength": 4,
		"language": {
			"emptyTable": "No hay Clientes asociados"
		},
		"initComplete": function() {
			$local.$tablaClienteProductoAsociados.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaClienteProductoAsociados, {});
			$('.dataTables_wrapper .dataTables_filter ').css('display', 'none');
		},
		"columnDefs": [
			{
				"targets": [0, 1],
				"className": "all filtrable",
			}, {
				"targets": 2,
				"className": "all dt-center",
				"render": function() {
					return "" + $variableUtil.botonEliminar + "";
				}
			}
		],
		"order": [
			[0, 'asc'],
			[1, 'asc']
		],
		"columns": [
			{
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.idEmpresa, row.descEmpresa);
				},
				"title": "Empresa"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.idCliente, row.descCliente);
				},
				"title": "Cliente"
			}, {
				"data": null,
				"title": 'Acci\u00F3n'
			}
		]
	});



	$local.$asociarClienteProducto.on("click", function() {
		if (!$formAsociacionClienteProducto.valid()) {
			return;
		}
		var data = $formAsociacionClienteProducto.serializeJSON();
		data.idLogo = $local.codigoLogoSeleccionado;
		data.codigoProducto = $local.codigoProductoSeleccionado;
		$.ajax({
			type: "POST",
			url: $variableUtil.root + "producto/empresa/cliente",
			data: JSON.stringify(data),
			beforeSend: function(xhr) {
				$local.$asociarClienteProducto.attr("disabled", true).find("i").removeClass("fa-floppy-o").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode: {
				400: function(response) {
					$funcionUtil.limpiarMensajesDeError($formAsociacionClienteProducto);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formAsociacionClienteProducto);
				}
			},
			success: function(response) {
				$funcionUtil.notificarException($variableUtil.registroExitoso, "fa-check", "Aviso", "success");
				if (response.length > 0) {
					var clienteAsociado = response[0];
					var row = $local.tablaClienteProductoAsociados.row.add(clienteAsociado).draw();
					row.show().draw(false);
					$(row.node()).animateHighlight();
				}
				$funcionUtil.prepararFormularioRegistro($formAsociacionClienteProducto);
			},
			error: function(response) {
			},
			complete: function(response) {
				$local.$asociarClienteProducto.attr("disabled", false).find("i").addClass("fa-random").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$tablaClienteProductoAsociados.children("tbody").on("click", ".eliminar", function() {
		var $filaSeleccionadaTablaClientesAsociados = $(this).parents("tr");
		var clienteAsociado = $local.tablaClienteProductoAsociados.row($filaSeleccionadaTablaClientesAsociados).data();
		clienteAsociado.codigoProducto = $local.codigoProductoSeleccionado;
		$.confirm({
			icon: "fa fa-info-circle",
			title: "Aviso",
			content: "\u00BFDesea eliminar la asociaci&oacute;n de Producto: <b>'" + clienteAsociado.codigoProducto + "</b> asociado al Cliente: <b>'" + clienteAsociado.descCliente + "'</b>?",
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
									url: $variableUtil.root + "producto/empresa/cliente",
									data: JSON.stringify(clienteAsociado),
									autoclose: true,
									beforeSend: function(xhr) {
										xhr.setRequestHeader('Content-Type', 'application/json');
										xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
									}
								}).done(function(response) {
									$funcionUtil.notificarException(response, "fa-check", "Aviso", "success");
									$local.tablaClienteProductoAsociados.row($filaSeleccionadaTablaClientesAsociados).remove().draw(false);
									confirmar.close();
								}).fail(function(xhr) {
									confirmar.close();
									switch (xhr.status) {
										case 400:
											$funcionUtil.notificarException($funcionUtil.obtenerMensajeErrorEnCadena(xhr.responseJSON), "fa-warning", "Aviso", "warning");
											break;
										case 409:
											var mensaje = $funcionUtil.obtenerMensajeError("La asociaci&oacute;n <b>" + clienteAsociado.descripcion + " asociado al Cliente '" + $local.idClienteSeleccionado + "'</b>", xhr.responseJSON, $variableUtil.accionEliminado);
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

	function rellenarConNumero(numero, relleno = "0") {
		if (numero === null) {
			return '';
		}

		const tamanioNumero = numero.length;
		if (tamanioNumero < $local.$MAX_CODIGO_INSTITUCION) {
			const cerosNecesarios = $local.$MAX_CODIGO_INSTITUCION - tamanioNumero;
			const ceros = relleno.repeat(cerosNecesarios);
			return `${ceros}${numero}`
		}

		return numero;
	}

	$local.$tablaMantenimiento.children("tbody").on("click", ".actualizar", function() {
		const codigo = rellenarConNumero($local.$codigoInstitucionIdentificador)
		$local.$codigoInstitucion.val(codigo)

		$funcionUtil.prepararFormularioActualizacion(formMantenimiento);
		$funcionUtil.prepararFormularioRegistro($formAsociacionClienteProducto);
		$local.$filaSeleccionada = $(this).parents("tr");
		const producto = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		$local.codigoProductoSeleccionado = producto.codigoProducto;
		let restoCodigoProducto = producto.codigoProducto.split(codigo)[1]
		producto.codigoProducto = restoCodigoProducto;
		$local.codigoBinSeleccionado = producto.idBin;
		$local.codigoLogoSeleccionado = producto.idLogo;
		$local.$longitudBin.val(producto.longitudBin)
		$local.tablaClienteProductoAsociados.clear().draw();
		$local.$formularioAdicionalMantenimiento.removeClass("hidden");
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "producto/" + $local.codigoProductoSeleccionado + "/empresa/cliente",
			beforeSend: function(xhr) {
				$local.$legend.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Clientes</span>");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			success: function(response) {
				if (response.length == 0) {
					$funcionUtil.notificarException("No existen Clientes asociados al Producto seleccionado.", "fa-check", "Aviso", "info");
				}
				$local.tablaClienteProductoAsociados.rows.add(response).draw();
			},
			error: function(response) {
			},
			complete: function(response) {
				$local.$legend.parent().find(".cargando").remove();
			}
		});

		$funcionUtil.llenarFormulario(producto, formMantenimiento);
		producto.codigoProducto = $local.codigoProductoSeleccionado
		//$local.$empresas.trigger("change", [producto.idCliente]);
		$local.$actualizarMantenimiento.removeClass("hidden");
		$local.$registrarMantenimiento.addClass("hidden");
		$local.$modalMantenimiento.PopupWindow("open");
	});

	$local.$empresas.on("change", function(event, opcionSeleccionada) {
		const idEmpresa = $(this).val();
		if (idEmpresa == null) {
			$local.$cliente.find("option:not(:eq(0))").remove();
			return;
		}
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "cliente/empresa/" + idEmpresa,
			beforeSend: function(xhr) {
				$local.$cliente.find("option:not(:eq(0))").remove();
				$local.$cliente.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Clientes</span>");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode: {
				400: function(response) {
					$funcionUtil.limpiarMensajesDeError($formAsociacionClienteProducto);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formAsociacionClienteProducto);
				}
			},
			success: function(clientes) {
				$.each(clientes, function(i, cliente) {
					$local.$cliente.append($("<option />").val(this.idCliente).text($funcionUtil.unirCodigoDescripcion(this.idCliente, this.descripcion)));
				});
				if (opcionSeleccionada != null && opcionSeleccionada != undefined) {
					$local.$cliente.val(opcionSeleccionada).trigger("change.select2");
				}
			},
			error: function(response) {
			},
			complete: function(response) {
				$local.$cliente.parent().find(".cargando").remove();
			}
		});
	});

	$local.$modalMantenimiento.PopupWindow({
		title: "Mantenimiento de Producto",
		autoOpen: false,
		modal: false,
		height: 640,
		width: 1100,
	});

	$local.$modalMantenimiento.on("open.popupwindow", function() {
		formMantenimiento.find("input:not([disabled]):first").focus();
	});

	$local.$aniadirMantenimento.on("click", function() {
		$funcionUtil.prepararFormularioRegistro(formMantenimiento);
		$local.$actualizarMantenimiento.addClass("hidden");
		$local.$registrarMantenimiento.removeClass("hidden");
		$local.$formularioAdicionalMantenimiento.addClass("hidden");
		$local.$modalMantenimiento.PopupWindow("open");
		$local.$codigoInstitucion.val(rellenarConNumero($local.$codigoInstitucionIdentificador))
	});

	$local.$modalMantenimiento.on("close.popupwindow", function(e) {
		if ($local.$tablaClienteProductoAsociados.length = 0) {
			$funcionUtil.notificarException('La relación Producto - Cliente es obligatoria.', "fa-warning", "Aviso", "warning");
			return;
		}
		$local.codigoProductoSeleccionado = 0;
		$local.$formularioAdicionalMantenimiento.addClass("hidden");
		$local.$modalMantenimiento.PopupWindow("close");
	});

	$local.$registrarMantenimiento.on("click", function() {
		if (!formMantenimiento.valid()) {
			return;
		}

		const producto = formMantenimiento.serializeJSON();
		producto.codigoProducto = producto.codigoInstitucion + producto.codigoProducto

		$.ajax({
			type: "POST",
			url: $variableUtil.root + "producto",
			data: JSON.stringify(producto),
			beforeSend: function(xhr) {
				$local.$registrarMantenimiento.attr("disabled", true).find("i").removeClass("fa-floppy-o").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode: {
				400: function(response) {
					$funcionUtil.limpiarMensajesDeError(formMantenimiento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, formMantenimiento);
				}
			},
			success: function(productos) {
				$funcionUtil.notificarException($variableUtil.registroExitoso, "fa-check", "Aviso", "success");
				if ($local.$tablaMantenimiento.length > 0) {
					let logoRegistrado = productos[0];
					let row = $local.tablaMantenimiento.row.add(logoRegistrado).draw();
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

	$local.$actualizarMantenimiento.on("click", function() {
		if (!formMantenimiento.valid()) {
			return;
		}

		const producto = formMantenimiento.serializeJSON();
		producto.codigoProducto = $local.codigoProductoSeleccionado;
		producto.idBin = $local.codigoBinSeleccionado;
		producto.idLogo = $local.codigoLogoSeleccionado;
		
		$local.$cliente.empty()

		$.ajax({
			type: "PUT",
			url: $variableUtil.root + "producto",
			data: JSON.stringify(producto),
			beforeSend: function(xhr) {
				$local.$actualizarMantenimiento.attr("disabled", true).find("i").removeClass("fa-pencil-square").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode: {
				400: function(response) {
					$funcionUtil.limpiarMensajesDeError(formMantenimiento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, formMantenimiento);
				}
			},
			success: function(productos) {
				$funcionUtil.notificarException($variableUtil.actualizacionExitosa, "fa-check", "Aviso", "success");
				if ($local.$tablaMantenimiento.length > 0) {
					$local.tablaMantenimiento.row($local.$filaSeleccionada).remove().draw(false);
					let producto = productos[0];
					let row = $local.tablaMantenimiento.row.add(producto).draw();
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
		let producto = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		$.confirm({
			icon: "fa fa-info-circle",
			title: "Aviso",
			content: "&iquest;Desea eliminar el Producto <b>'" + producto.codigoProducto + " - " + producto.descProducto + "'</b>?",
			theme: "bootstrap",
			buttons: {
				Aceptar: {
					action: function() {
						let confirmar = $.confirm({
							icon: 'fa fa-spinner fa-pulse fa-fw',
							title: "Eliminando...",
							content: function() {
								let self = this;
								self.buttons.close.hide();
								$.ajax({
									type: "DELETE",
									url: $variableUtil.root + "producto",
									data: JSON.stringify(producto),
									autoclose: true,
									beforeSend: function(xhr) {
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
											let mensaje = $funcionUtil.obtenerMensajeError("El Producto <b>" + producto.codigoProducto + " - " + bin.descProducto + "</b>", xhr.responseJSON, $variableUtil.accionEliminado);
											$funcionUtil.notificarException(mensaje, "fa-warning", "Aviso", "warning");
											break;
									}
								}).always(function(xhr) {
									confirmar.close();
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