/* Variable Global  */
var $formMantenimiento = "";
var $cuentaContables;

$(document).ready(function() {

	$cuentaContables = {
		compensaComisiones: "0",
		compensaFondos: "0"
	};

	const $local = {
		$tablaMantenimiento: $("#tablaMantenimiento"),
		tablaMantenimiento: "",
		$tablaCuentaComisiones: $("#tablaCuentaComisiones"),
		tablaCuentaComisiones: "",
		$modalMantenimiento: $("#modalMantenimiento"),
		$aniadirMantenimento: $("#aniadirMantenimiento"),
		$aniadirCuentaComision: $("#aniadirCuentaComision"),
		$botonesActualizacion: $("#botonesActualizacion"),
		$actualizarCuentaComision: $("#actualizarCuentaComision"),
		$cancelarCuentaComision: $("#cancelarCuentaComision"),
		$registrarMantenimiento: $("#registrarMantenimiento"),
		$actualizarMantenimiento: $("#actualizarMantenimiento"),
		cuentaContableEmisor: {},
		idConceptoComisionSeleccionado: "",
		$filaSeleccionada: "",
		$filaSeleccionadaCuentaComision: "",
		$compensaComisiones: $(".comisiones"),
		$compensaFondos: $(".fondos"),
		$mensajeCompensaComisiones: $("#mensajeCompensaComisiones"),
		$mensajeCompensaFondos: $("#mensajeCompensaFondos"),
		$ocultable: $(".ocultable"),
		$cuentasContables: $(".cuentaContable"),
		$a_comisiones: $("#a-comisiones"),
		$a_fondos: $("#a-fondos"),

		$instituciones: $("#instituciones"),
		$bines: $("#bines"),
		$clasesServicio: $("#clasesServicio"),
		$claseTransacciones: $("#claseTransacciones"),
		$codigoTransacciones: $("#codigoTransacciones"),
		$rolesTransaccion: $("#rolesTransaccion"),
		$conceptoComisiones: $("#conceptoComisiones"),
		$clientes: $("#clientes"),
		$empresas: $("#empresas"),
		$membresias: $("#membresias"),
		$monedas: $("#monedas"),
		$origenes: $("#origenes"),
		$producto: $("#producto"),
		$logos: $("#logos"),

		$claseTransaccionesFiltroParaTablaMantenimiento: $("#claseTransacciones-filtroParaTablaMantenimiento"),
		$rolesTransaccionFiltroParaTablaMantenimiento: $("#rolesTransaccion-filtroParaTablaMantenimiento"),
		$empresasFiltroParaTablaMantenimiento: $("#empresas-filtroParaTablaMantenimiento"),
		$conceptoComisionesFiltroParaTablaMantenimiento: $("#conceptoComisiones-FiltroParaTablaMantenimiento"),
		$monedasFiltroParaTablaMantenimiento: $("#monedas-filtroParaTablaMantenimiento"),
		$membresiasFiltroParaTablaMantenimiento: $("#membresias-filtroParaTablaMantenimiento"),
		$origenesFiltroParaTablaMantenimiento: $("#origenes-filtroParaTablaMantenimiento"),
		$institucionesFiltroParaTablaMantenimiento: $("#instituciones-filtroParaTablaMantenimiento"),
		$clienteFiltroParaTablaMantenimiento: $("#clientes-FiltroParaTablaMantenimiento"),
		$logosFiltroParaTablaMantenimiento: $("#logos-filtroParaTablaMantenimiento"),
		filtrosSeleccionables: {},
		// Permisos
		permisoActualizacion: false,
		permisoEliminacion: false,
		$exportar: $('#exportar'),

		$agregableClaseServicio: "",
		$agregableClaseTransaccion: "",
		$agregableCodigoTransaccion: "",
		$agregableMembresia: "",
		$agregableSubBin: "",
		$agregableBin: "",
		$agregableEmpresa: "",
		$agregableCliente: "",
		$agregableLogo: "",
		$agregableProducto: "",
		$exportar: $('#exportar'),

		// Permisos
		permisoActualizacion: false,
		permisoEliminacion: false,

		mostrarCuentasContables: function($codigoTransacciones) {
			var $opcionSeleccionada = $codigoTransacciones.find(":selected");
			$cuentaContables.compensaComisiones = $opcionSeleccionada.attr("data-compensaComisiones") || "0";
			$cuentaContables.compensaFondos = $opcionSeleccionada.attr("data-compensaFondos") || "0";
			if ($cuentaContables.compensaComisiones === undefined || $cuentaContables.compensaFondos === undefined) {
				return;
			}
			var esCompensaComisiones = $cuentaContables.compensaComisiones == "1";
			var esCompensaFondos = $cuentaContables.compensaFondos == "1";
			this.$compensaComisiones.toggleClass("hidden", !esCompensaComisiones);
			this.$compensaFondos.toggleClass("hidden", !esCompensaFondos);
			if (esCompensaComisiones) {
				this.$a_comisiones.tab("show");
			} else {
				this.$a_fondos.tab("show");
				this.tablaCuentaComisiones.clear().draw();
			}
			if (!esCompensaFondos) {
				$funcionUtil.prepararFormularioRegistro($formFondo);
			}
		}
	};

	$formMantenimiento = $("#formMantenimiento");
	$formComisiones = $("#formComisiones");
	$formFondo = $("#formFondo");

	$funcionUtil.crearSelect2($local.$logos, "Seleccione un Logos");
	$funcionUtil.crearSelect2($local.$claseTransacciones, "Selecciona una Clase de Transacci\u00F3n");
	$funcionUtil.crearSelect2($local.$clasesServicio, "Selecciona una Clase de Servicio");
	$funcionUtil.crearSelect2($local.$clientes, "Seleccione un cliente");
	$funcionUtil.crearSelect2($local.$codigoTransacciones, "Seleccione una C\u00F3digo de Transacci\u00F3n");
	$funcionUtil.crearSelect2($local.$rolesTransaccion, "Seleccione un Rol de Transacci\u00F3n");
	$funcionUtil.crearSelect2($local.$conceptoComisiones, "Seleccione una Concepto de Comisi\u00F3n");
	$funcionUtil.crearSelect2($local.$empresas, "Seleccione una Empresa");
	$funcionUtil.crearSelect2($local.$membresias, "Seleccione una Membres\u00EDa");
	$funcionUtil.crearSelect2($local.$monedas, "Seleccione una Moneda");
	$funcionUtil.crearSelect2($local.$origenes, "Seleccione un Origen");
	$funcionUtil.crearSelect2($local.$producto, "Seleccione un Producto");
	$funcionUtil.crearSelect2($local.$instituciones, "Seleccione una instituci\u00F3n");

	$local.$empresas.on("change", function(event, opcionSeleccionada) {
		var idEmpresa = $(this).val();
		if (idEmpresa == null) {
			$local.$clientes.find("option:not(:eq(0))").remove();
			return;
		}
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "cliente/empresa/" + idEmpresa,
			beforeSend: function(xhr) {
				$local.$clientes.find("option:not(:eq(0))").remove();
				$local.$clientes.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Clientes</span>");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode: {
				400: function(response) {
					$funcionUtil.limpiarMensajesDeError($formMantenimiento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formMantenimiento);
				}
			},
			success: function(clientes) {
				$.each(clientes, function(i, cliente) {
					$local.$clientes.append($("<option />").val(this.idCliente).text($funcionUtil.unirCodigoDescripcion(this.idCliente, this.descripcion)));
				});
				if (opcionSeleccionada != null && opcionSeleccionada != undefined) {
					$local.$clientes.val(opcionSeleccionada).trigger("change.select2");
				}
			},
			error: function(response) {
			},
			complete: function(response) {
				$local.$clientes.parent().find(".cargando").remove();
			}
		});
	});

	$local.$clientes.on("change", function() {
		$local.$logos.trigger("change");
	});

	$local.$claseTransacciones.on("change", function(event, opcionSeleccionada) {
		var codigoClaseTransaccion = $(this).val();
		if (codigoClaseTransaccion == null) {
			$local.$codigoTransacciones.find("option:not(:eq(0))").remove();
			return;
		}
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "codigoTransaccion/claseTransaccion/" + codigoClaseTransaccion,
			beforeSend: function(xhr) {
				$local.$codigoTransacciones.find("option:not(:eq(0))").remove();
				$local.$codigoTransacciones.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando C\u00F3digo de Transacciones</span>")
				xhr.setRequestHeader('Content-Type', 'application/json');
			},
			statusCode: {
				400: function(response) {
					$funcionUtil.limpiarMensajesDeError($formMantenimiento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formMantenimiento);
				}
			},
			success: function(codigoTransacciones) {
				$.each(codigoTransacciones, function(i, codigoTransaccion) {
					$local.$codigoTransacciones.append($("<option />").val(this.codigoTransaccion).text(this.codigoTransaccion + " - " + this.descripcion).attr({
						"data-compensaComisiones": codigoTransaccion.compensaComisiones,
						"data-compensaFondos": codigoTransaccion.compensaFondos
					}));
				});
				if (opcionSeleccionada != null && opcionSeleccionada != undefined) {
					$local.$codigoTransacciones.val(opcionSeleccionada).trigger("change.select2");
				}
				$local.$codigoTransacciones.trigger("change");
			},
			error: function(response) {
			},
			complete: function(response) {
				$local.$codigoTransacciones.parent().find(".cargando").remove();
			}
		});
	});

	$local.$codigoTransacciones.on("change", function() {
		$local.mostrarCuentasContables($(this));
		$local.$cancelarCuentaComision.trigger("click");
	});

	$local.$membresias.on("change", function(event, opcionClaseServicioSeleccionado, opcionBinSeleccionado, opcionProductoSeleccionado) {
		var codigoMembresia = $(this).val();
		if (codigoMembresia == null) {
			$local.$clasesServicio.find("option:not(:eq(0))").remove();
			return;
		}
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "claseServicio/membresia/" + codigoMembresia,
			beforeSend: function(xhr) {
				$local.$clasesServicio.find("option:not(:eq(0))").remove();
				$local.$clasesServicio.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Clases de Servicio</span>")
				xhr.setRequestHeader('Content-Type', 'application/json');
			},
			statusCode: {
				400: function(response) {
					$funcionUtil.limpiarMensajesDeError($formMantenimiento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formMantenimiento);
				}
			},
			success: function(clasesServicio) {
				$.each(clasesServicio, function(i, claseServicio) {
					$local.$clasesServicio.append($("<option />").val(this.codigoClaseServicio).text(this.codigoClaseServicio + " - " + this.descripcion));
				});
				if (opcionClaseServicioSeleccionado != null && opcionClaseServicioSeleccionado != undefined) {
					$local.$clasesServicio.val(opcionClaseServicioSeleccionado).trigger("change.select2");
					$local.$clasesServicio.trigger("change", [opcionBinSeleccionado, opcionProductoSeleccionado]);
				} else {
					$local.$clasesServicio.trigger("change");
				}
			},
			error: function(response) {
			},
			complete: function(response) {
				$local.$clasesServicio.parent().find(".cargando").remove();
			}
		});


	});

	$local.$clasesServicio.on("change", function(event, opcionBinSeleccionado, opcionSubBinSeleccionado) {
		var codigoMembresia = $local.$membresias.val();
		var codigoClaseServicio = $local.$clasesServicio.val();
		var codigoInstitucion = $local.$instituciones.val();
		if (codigoMembresia == null || codigoClaseServicio == null || codigoInstitucion == null) {
			$local.$logos.find("option:not(:eq(0))").remove();
			return;
		}
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "logo/membresia/" + codigoMembresia + "/claseServicio/" + codigoClaseServicio + "/institucion/" + codigoInstitucion,
			beforeSend: function(xhr) {
				$local.$logos.find("option:not(:eq(0))").remove();
				$local.$logos.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Logos</span>")
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode: {
				400: function(response) {
					$funcionUtil.limpiarMensajesDeError($formMantenimiento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formMantenimiento);
				}
			},
			success: function(logos) {
				$.each(logos, function(i, bin) {
					$local.$logos.append($("<option />").val(this.idLogo).text(this.idLogo + " - " + this.descripcion + " (" + this.idBin + ")"));
				});
				if (opcionBinSeleccionado != null && opcionBinSeleccionado != undefined) {
					$local.$logos.val(opcionBinSeleccionado).trigger("change.select2");
					$local.$logos.trigger("change", [opcionSubBinSeleccionado]);
				} else {
					$local.$logos.trigger("change");
				}
			},
			error: function(response) {
			},
			complete: function(response) {
				$local.$logos.parent().find(".cargando").remove();
			}
		});
	});

	function buscarProductoValidacion(empresaSeleccionada, clienteSeleccionada, logoSeleccionada, opcionSeleccionada) {
		if (
			(empresaSeleccionada == null || empresaSeleccionada == undefined || empresaSeleccionada == '-1') ||
			(clienteSeleccionada == null || clienteSeleccionada == undefined || clienteSeleccionada == '-1') ||
			(logoSeleccionada == null || logoSeleccionada == undefined || logoSeleccionada == '-1')
		) {
			return;
		}
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "producto/empresa/" + empresaSeleccionada + "/cliente/" + clienteSeleccionada + "/logo/" + logoSeleccionada,
			beforeSend: function(xhr) {
				$local.$producto.find("option:not(:eq(0))").remove();
				$local.$producto.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Productos del Logo: " + logoSeleccionada + " </span>")
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
				$.each(response, function(i, producto) {
					$local.$producto.append($("<option/>").val(this.codigoProducto).text(`${$funcionUtil.unirCodigoDescripcion(this.codigoProducto, this.descProducto)}`));
				});
				if (opcionSeleccionada != null && opcionSeleccionada != undefined) {
					$local.$producto.val(opcionSeleccionada).trigger("change.select2");
				}
			},
			error: function(response) {
			},
			complete: function(response) {
				$local.$producto.parent().find(".cargando").remove();
			}
		});
	}

	$local.$logos.on("change", function(event, opcionSeleccionada) {
		var empresaSeleccionada = $local.$empresas.val();
		var clienteSeleccionada = $local.$clientes.val();
		var logoSeleccionada = $local.$logos.val();
		var codigoBIN = $(this).val();
		if (codigoBIN == null) {
			$local.$producto.find("option:not(:eq(0))").remove();
			return;
		}
		buscarProductoValidacion(empresaSeleccionada, clienteSeleccionada, logoSeleccionada, opcionSeleccionada);
	});

	/*$local.$bines.on("change", function(event, opcionSeleccionada) {
		var codigoBIN = $(this).val();
		var idCliente = $local.$clientes.val();
		var idEmpresa = $local.$empresas.val();
		if (codigoBIN == null || idCliente == null || idEmpresa == null) {
			$local.$producto.find("option:not(:eq(0))").remove();
			return;
		}
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "producto/bin/" + codigoBIN,
			//url : $variableUtil.root + "cliente/" + idCliente + "/empresa/" + idEmpresa + "/bin/" + idProducto + "/producto",
			beforeSend : function(xhr) {
				$local.$producto.find("option:not(:eq(0))").remove();
				$local.$producto.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando PRODUCTOS</span>");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode : {
				400 : function(response) {
					$funcionUtil.limpiarMensajesDeError($formMantenimiento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formMantenimiento);
				}
			},
			success : function(producto) {
				$.each(producto, function(i, producto) {
					$local.$producto.append($("<option />").val(this.codigoProducto).text($funcionUtil.unirCodigoDescripcion(this.codigoProducto, this.descripcion)));
				});
				if (opcionSeleccionada != null && opcionSeleccionada != undefined) {
					$local.$producto.val(opcionSeleccionada).trigger("change.select2");
				}
			},
			error : function(response) {
			},
			complete : function(response) {
				$local.$producto.parent().find(".cargando").remove();
			}
		});
	});*/

	$.fn.dataTable.ext.errMode = 'none';

	$local.$tablaMantenimiento.on('xhr.dt', function(e, settings, json, xhr) {
		if (xhr.status) {
			$local.tablaMantenimiento.clear().draw();
		}
	});

	$local.permisoActualizacion = $local.$tablaMantenimiento.attr("data-permiso-actualizacion") || false;
	$local.permisoEliminacion = $local.$tablaMantenimiento.attr("data-permiso-eliminacion") || false;

	$local.tablaMantenimiento = $local.$tablaMantenimiento.DataTable({
		"ajax": {
			"url": $variableUtil.root + "cuentaContableEmisor?accion=buscarTodos",
			"dataSrc": ""
		},
		"language": {
			"emptyTable": "No hay Cuentas Contables registrados"
		},
		"initComplete": function() {
			$local.$tablaMantenimiento.wrap("<div class='table-responsive'></div>");
			$local.filtrosSeleccionables["0"] = $local.$empresasFiltroParaTablaMantenimiento.html();
			$local.filtrosSeleccionables["1"] = $local.$clienteFiltroParaTablaMantenimiento.html();
			$local.filtrosSeleccionables["2"] = $local.$monedasFiltroParaTablaMantenimiento.html();
			$local.filtrosSeleccionables["3"] = $local.$membresiasFiltroParaTablaMantenimiento.html();
			$local.filtrosSeleccionables["5"] = $local.$logosFiltroParaTablaMantenimiento.html();
			$local.filtrosSeleccionables["7"] = $local.$origenesFiltroParaTablaMantenimiento.html();
			$local.filtrosSeleccionables["8"] = $local.$claseTransaccionesFiltroParaTablaMantenimiento.html();
			$local.filtrosSeleccionables["10"] = $local.$rolesTransaccionFiltroParaTablaMantenimiento.html();
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaMantenimiento, $local.filtrosSeleccionables);
			$local.$agregableEmpresa = $local.$tablaMantenimiento.find("thead").find("select.agregable-empresa");
			$local.$agregableCliente = $local.$tablaMantenimiento.find("thead").find("select.agregable-cliente");
			$local.$agregableMembresia = $local.$tablaMantenimiento.find("thead").find("select.agregable-membresia");
			$local.$agregableClaseServicio = $local.$tablaMantenimiento.find("thead").find("select.agregable-clase-servicio");
			// $local.$agregableLogo = $local.$tablaMantenimiento.find("thead").find("select.agregable-logo");
			$local.$agregableSubBin = $local.$tablaMantenimiento.find("thead").find("select.agregable-subbin");
			$local.$agregableClaseTransaccion = $local.$tablaMantenimiento.find("thead").find("select.agregable-clase-transaccion");
			$local.$agregableCodigoTransaccion = $local.$tablaMantenimiento.find("thead").find("select.agregable-codigo-transaccion");
			$local.$agregableProducto = $local.$tablaMantenimiento.find("thead").find("select.agregable-producto");
		},
		"columnDefs": [{
			"targets": 11,
			"className": "all dt-center",
			"render": function() {
				return "" + ($local.permisoActualizacion == "true" ? $variableUtil.botonActualizar : "") + " " + ($local.permisoEliminacion == "true" ? $variableUtil.botonEliminar : "");
			}
		}],
		"order": [
			[0, 'asc'],
			[1, 'asc'],
			[2, 'asc'],
			[3, 'asc'],
			[4, 'asc'],
			[5, 'asc'],
			[6, 'asc'],
			[7, 'asc'],
			[8, 'asc'],
			[9, 'asc'],
			[10, 'asc'],
		],
		"columns": [{
			"className": "all seleccionable select2 insertable-opciones-html agregable-empresa",
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idEmpresa, row.descripcionEmpresa);
			},
			"title": "Empresa"
		}, {
			"className": "all seleccionable select2 insertable-opciones-html agregable-cliente",
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idCliente, row.descripcionCliente);
			},
			"title": "Cliente"
		}, {
			"className": "all seleccionable select2 insertable-opciones-html",
			"data": 'descripcionMoneda',
			"title": "Moneda"
		}, {
			"className": "all seleccionable select2 agregable-membresia insertable-opciones-html",
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoMembresia, row.descripcionMembresia);
			},
			"title": "Membres\u00EDa"
		}, {
			"className": "all seleccionable select2 data-vacia agregable-clase-servicio",
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoClaseServicio, row.descripcionClaseServicio);
			},
			"title": "Servicio"
		}, {
			"className": "all seleccionable select2 insertable-opciones-html agregable-logo",
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idLogo, row.descripcionLogo);
			},
			"title": "Logo"
		}, {
			"className": "all seleccionable select2 data-vacia agregable-producto",
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoProducto, row.descripcionProducto);
			},
			"title": "Producto"
		}, {
			"className": "all seleccionable select2 insertable-opciones-html",
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoOrigen, row.descripcionOrigen);
			},
			"title": "Origen"
		}, {
			"className": "all seleccionable select2 agregable-clase-transaccion insertable-opciones-html",
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoClaseTransaccion, row.descripcionClaseTransaccion);
			},
			"title": "Clase Transacci\u00F3n"
		}, {
			"className": "all seleccionable select2 agregable-codigo-transaccion insertable-opciones-html",
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoTransaccion, row.descripcionCodigoTransaccion);
			},
			"title": "C\u00F3digo Transacci\u00F3n"
		}, {
			"className": "all seleccionable select2 insertable-opciones-html",
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idRolTransaccion, row.descripcionRolTransaccion);
			},
			"title": "Rol"
		}, {
			"data": null,
			"title": 'Acci\u00F3n'
		}]
	});

	$local.$tablaMantenimiento.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaMantenimiento.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
		if ($(this).hasClass("agregable-empresa")) {
			var idEmpresa = $(this).find(":selected").attr("data-value");
			if (idEmpresa == "" || idEmpresa == null) {
				$local.$agregableCliente.find("option:not(:contains('Todos'))").remove();
				$local.$agregableCliente.trigger("change");
				return;
			}
			$.ajax({
				type: "GET",
				url: $variableUtil.root + "cliente/empresa/" + idEmpresa,
				beforeSend: function(xhr) {
					$local.$agregableCliente.find("option:not(:contains('Todos'))").remove();
					$local.$agregableCliente.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Clientes</span>")
				},
				statusCode: {
					400: function(response) {
						$funcionUtil.notificarException($funcionUtil.obtenerMensajeErrorEnCadena(response.responseJSON), "fa-warning", "Aviso", "warning");
					}
				},
				success: function(clientes) {
					$.each(clientes, function(i, cliente) {
						var $opcionCliente = $("<option />").val($funcionUtil.unirCodigoDescripcion(this.idCliente, this.descripcion)).text($funcionUtil.unirCodigoDescripcion(this.idCliente, this.descripcion)).attr("data-value", this.idCliente);
						$local.$agregableCliente.append($opcionCliente);
					});
				},
				complete: function() {
					$local.$agregableCliente.parent().find(".cargando").remove();
					$local.$agregableCliente.trigger("change");
				}
			});
		} else if ($(this).hasClass("agregable-membresia")) {
			var codigoMembresia = $(this).find(":selected").attr("data-value");
			if (codigoMembresia == "" || codigoMembresia == null) {
				$local.$agregableClaseServicio.find("option:not(:contains('Todos'))").remove();
				$local.$agregableClaseServicio.trigger("change");
				return;
			}
			$.ajax({
				type: "GET",
				url: $variableUtil.root + "claseServicio/membresia/" + codigoMembresia,
				beforeSend: function(xhr) {
					$local.$agregableClaseServicio.find("option:not(:contains('Todos'))").remove();
					$local.$agregableClaseServicio.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Clases de Servicio</span>")
				},
				statusCode: {
					400: function(response) {
						$funcionUtil.notificarException($funcionUtil.obtenerMensajeErrorEnCadena(response.responseJSON), "fa-warning", "Aviso", "warning");
					}
				},
				success: function(claseServicios) {
					$.each(claseServicios, function(i, claseServicio) {
						var $opcionClaseServicio = $("<option />").val($funcionUtil.unirCodigoDescripcion(this.codigoClaseServicio, this.descripcion)).text($funcionUtil.unirCodigoDescripcion(this.codigoClaseServicio, this.descripcion)).attr("data-value", this.codigoClaseServicio);
						$local.$agregableClaseServicio.append($opcionClaseServicio);
					});
				},
				complete: function() {
					$local.$agregableClaseServicio.parent().find(".cargando").remove();
					$local.$agregableClaseServicio.trigger("change");
				}
			});
		} /*else if ($(this).hasClass("agregable-clase-servicio")) {
			var codigoMembresiaSeleccionado = $local.$agregableMembresia.find(":selected").attr("data-value");
			var codigoClaseServicio = $(this).find(":selected").attr("data-value");
			if ((codigoMembresiaSeleccionado == null || codigoMembresiaSeleccionado == "") || (codigoClaseServicio == null || codigoClaseServicio == "")) {
				$local.$agregableBin.find("option:not(:contains('Todos'))").remove();
				$local.$agregableBin.trigger("change");
				return;
			}
			$.ajax({
				type : "GET",
				url : $variableUtil.root + "bin/membresia/" + codigoMembresiaSeleccionado + "/claseServicio/" + codigoClaseServicio,
				beforeSend : function(xhr) {
					$local.$agregableBin.find("option:not(:contains('Todos'))").remove();
					$local.$agregableBin.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Bines</span>")
					xhr.setRequestHeader('Content-Type', 'application/json');
					xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
				},
				statusCode : {
					400 : function(response) {
						$funcionUtil.limpiarMensajesDeError($formMantenimiento);
						$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formMantenimiento);
					}
				},
				success : function(bines) {
					$.each(bines, function(i, bin) {
						var descripcionBIN = $funcionUtil.unirCodigoDescripcion(this.codigoBIN, this.descripcion);
						var $opcionBin = $("<option />").val(descripcionBIN).text(descripcionBIN).attr("data-value", this.codigoBIN);
						$local.$agregableBin.append($opcionBin);
					});
				},
				error : function(response) {
				},
				complete : function(response) {
					$local.$agregableBin.parent().find(".cargando").remove();
					$local.$agregableBin.parent().trigger("change");
				}
			});
		}*/ else if ($(this).hasClass("agregable-bin")) {
			var codigoBIN = $(this).find(":selected").attr("data-value");
			if (codigoBIN == "" || codigoBIN == null) {
				$local.$agregableSubBin.find("option:not(:contains('Todos'))").remove();
				$local.$agregableSubBin.trigger("change");
				return;
			}
			$.ajax({
				type: "GET",
				url: $variableUtil.root + "subBin/bin/" + codigoBIN,
				beforeSend: function(xhr) {
					$local.$agregableSubBin.find("option:not(:contains('Todos'))").remove();
					$local.$agregableSubBin.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando SubBINES</span>")
				},
				statusCode: {
					400: function(response) {
						$funcionUtil.notificarException($funcionUtil.obtenerMensajeErrorEnCadena(response.responseJSON), "fa-warning", "Aviso", "warning");
					}
				},
				success: function(subBines) {
					$.each(subBines, function(i, subBin) {
						var $opcionSubBin = $("<option />").val($funcionUtil.unirCodigoDescripcion(this.codigoSubBIN, this.descripcion)).text($funcionUtil.unirCodigoDescripcion(this.codigoSubBIN, this.descripcion)).attr("data-value", this.codigoSubBIN);
						$local.$agregableSubBin.append($opcionSubBin);
					});
				},
				complete: function() {
					$local.$agregableSubBin.parent().find(".cargando").remove();
					$local.$agregableSubBin.trigger("change");
				}
			});
		} else if ($(this).hasClass("agregable-clase-transaccion")) {
			var codigoClaseTransaccion = $(this).find(":selected").attr("data-value");
			if (codigoClaseTransaccion == "" || codigoClaseTransaccion == null) {
				$local.$agregableCodigoTransaccion.find("option:not(:contains('Todos'))").remove();
				$local.$agregableCodigoTransaccion.trigger("change");
				return;
			}
			$.ajax({
				type: "GET",
				url: $variableUtil.root + "codigoTransaccion/claseTransaccion/" + codigoClaseTransaccion,
				beforeSend: function(xhr) {
					$local.$agregableCodigoTransaccion.find("option:not(:contains('Todos'))").remove();
					$local.$agregableCodigoTransaccion.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando C\u00F3digos de Transacci\u00F3n</span>");
				},
				statusCode: {
					400: function(response) {
						$funcionUtil.notificarException($funcionUtil.obtenerMensajeErrorEnCadena(response.responseJSON), "fa-warning", "Aviso", "warning");
					}
				},
				success: function(codigoTransacciones) {
					$.each(codigoTransacciones, function(i, codigoTransaccion) {
						var descripcionCodigoTransaccion = $funcionUtil.unirCodigoDescripcion(this.codigoTransaccion, this.descripcion);
						var $opcionCodigoTransaccion = $("<option />").val(descripcionCodigoTransaccion).text(descripcionCodigoTransaccion).attr("data-value", this.codigoTransaccion);
						$local.$agregableCodigoTransaccion.append($opcionCodigoTransaccion);
					});
				},
				complete: function() {
					$local.$agregableCodigoTransaccion.parent().find(".cargando").remove();
					$local.$agregableCodigoTransaccion.trigger("change");
				}
			});
		} else if ($(this).hasClass("agregable-logo")) {
			var codigoLogo = $(this).find(":selected").attr("data-value");
			if (codigoLogo == "" || codigoLogo == null) {
				$local.$agregableProducto.find("option:not(:contains('Todos'))").remove();
				$local.$agregableProducto.trigger("change");
				return;
			}
			$.ajax({
				type: "GET",
				url: $variableUtil.root + "producto/logo/" + codigoLogo,
				beforeSend: function(xhr) {
					$local.$agregableProducto.find("option:not(:contains('Todos'))").remove();
					$local.$agregableProducto.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Productos</span>");
				},
				statusCode: {
					400: function(response) {
						$funcionUtil.notificarException($funcionUtil.obtenerMensajeErrorEnCadena(response.responseJSON), "fa-warning", "Aviso", "warning");
					}
				},
				success: function(productos) {
					$.each(productos, function(i, codigoTransaccion) {
						var descripcionCodigoTransaccion = $funcionUtil.unirCodigoDescripcion(this.codigoProducto, this.descProducto);
						var $opcionCodigoTransaccion = $("<option />").val(descripcionCodigoTransaccion).text(descripcionCodigoTransaccion).attr("data-value", this.codigoProducto);
						$local.$agregableProducto.append($opcionCodigoTransaccion);
					});
				},
				complete: function() {
					$local.$agregableProducto.parent().find(".cargando").remove();
					$local.$agregableProducto.trigger("change");
				}
			});
		}
	});

	$local.tablaCuentaComisiones = $local.$tablaCuentaComisiones.DataTable({
		"lengthChange": false,
		"pageLength": 4,
		"language": {
			"emptyTable": "No hay Comisiones registradas"
		},
		"columnDefs": [{
			"targets": 0,
			"className": "all",
			"render": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idConceptoComision, row.descripcionConceptoComision);
			}
		}, {
			"targets": 4,
			"className": "all dt-center",
			"defaultContent": $variableUtil.botonActualizar + " " + $variableUtil.botonEliminar
		}],
		"columns": [{
			"data": null,
			"title": "Comisi\u00F3n"
		}, {
			"data": 'cuentaCargo',
			"title": "Cargo"
		}, {
			"data": 'cuentaAbono',
			"title": "Abono"
		}, {
			"data": 'codigoAnalitico',
			"title": "Anal\u00EDtico"
		}, {
			"data": null,
			"title": 'Acci\u00F3n'
		}]
	});

	$local.$tablaCuentaComisiones.wrap("<div class='table-responsive'></div>");

	$local.$aniadirCuentaComision.on("click", function() {
		if (!$formComisiones.valid()) {
			return;
		}
		var contabConceptoCuenta = $formComisiones.serializeJSON();
		contabConceptoCuenta.descripcionConceptoComision = $local.$conceptoComisiones.find(":selected").attr("descripcion");
		contabConceptoCuenta.tipoCompensacion = $variableUtil.tipoCompensacionComision;
		var row = $local.tablaCuentaComisiones.row.add(contabConceptoCuenta).draw();
		row.show().draw(false);
		$(row.node()).animateHighlight();
		$funcionUtil.prepararFormularioRegistro($formComisiones);
	});

	$local.$tablaCuentaComisiones.children("tbody").on("click", ".actualizar", function() {
		var contabConceptoCuenta = "";
		if ($local.$filaSeleccionadaCuentaComision == "") {
			//$local.$conceptoComisiones.removeClass("elemento-desactivable");
			$local.$filaSeleccionadaCuentaComision = $(this).parents("tr");
			contabConceptoCuenta = $local.tablaCuentaComisiones.row($local.$filaSeleccionadaCuentaComision).data();
			$funcionUtil.prepararFormularioActualizacion($formComisiones);
			$funcionUtil.llenarFormulario(contabConceptoCuenta, $formComisiones);
			$local.idConceptoComisionSeleccionado = contabConceptoCuenta.idConceptoComision;
			$local.$aniadirCuentaComision.addClass("hidden");
			$local.$botonesActualizacion.removeClass("hidden");
			//$local.$conceptoComisiones.addClass("elemento-desactivable");
		} else {
			$funcionUtil.prepararFormularioActualizacion($formComisiones);
			$local.$filaSeleccionadaCuentaComision = $(this).parents("tr");
			contabConceptoCuenta = $local.tablaCuentaComisiones.row($local.$filaSeleccionadaCuentaComision).data();
			$local.idConceptoComisionSeleccionado = contabConceptoCuenta.idConceptoComision;
			$funcionUtil.llenarFormulario(contabConceptoCuenta, $formComisiones);
			$local.$aniadirCuentaComision.addClass("hidden");
			$local.$botonesActualizacion.removeClass("hidden");
		}
	});

	$local.$actualizarCuentaComision.on("click", function() {
		if (!$formComisiones.valid()) {
			return;
		}
		var contabConceptoCuenta = $formComisiones.serializeJSON();
		contabConceptoCuenta.descripcionConceptoComision = $local.$conceptoComisiones.find(":selected").attr("descripcion");
		contabConceptoCuenta.tipoCompensacion = $variableUtil.tipoCompensacionComision;
		contabConceptoCuenta.idConceptoComision = $local.idConceptoComisionSeleccionado;
		$local.tablaCuentaComisiones.row($local.$filaSeleccionadaCuentaComision).remove().draw(false);
		var row = $local.tablaCuentaComisiones.row.add(contabConceptoCuenta).draw();
		row.show().draw(false);
		$(row.node()).animateHighlight();
		$local.$cancelarCuentaComision.trigger("click");
	});

	$local.$cancelarCuentaComision.on("click", function() {
		$local.$aniadirCuentaComision.removeClass("hidden");
		$local.$botonesActualizacion.addClass("hidden");
		$funcionUtil.prepararFormularioRegistro($formComisiones);
		$local.$filaSeleccionadaCuentaComision = "";
		$local.idConceptoComisionSeleccionado = "";
	});

	$local.$tablaCuentaComisiones.children("tbody").on("click", ".eliminar", function() {
		$local.$filaSeleccionadaCuentaComision = $(this).parents("tr");
		var contabConceptoCuenta = $local.tablaCuentaComisiones.row($local.$filaSeleccionadaCuentaComision).data();
		if (contabConceptoCuenta.idConceptoComision == $local.$conceptoComisiones.val() && !$local.$botonesActualizacion.hasClass("hidden")) {
			$funcionUtil.notificarException("La Comisi\u00F3n no puede ser eliminada hasta que finalize la actualizaci\u00F3n.", "fa-warning", "Aviso", "warning");
			return;
		}
		$.confirm({
			icon: "fa fa-info-circle",
			title: "Aviso",
			content: "\u00BFDesea eliminar la Cuenta Comisi\u00F3n <b>" + $funcionUtil.unirCodigoDescripcion(contabConceptoCuenta.idConceptoComision, contabConceptoCuenta.descripcionConceptoComision) + "</b> ?",
			theme: "bootstrap",
			type: "orange",
			buttons: {
				Aceptar: {
					action: function() {
						$local.tablaCuentaComisiones.row($local.$filaSeleccionadaCuentaComision).remove().draw(false);
						$local.$filaSeleccionadaCuentaComision = "";
					},
					keys: ['enter'],
					btnClass: "btn-warning"
				},
				Cancelar: {
					keys: ['esc']
				},
			}
		});
	});

	$formComisiones.find("input").keypress(function(event) {
		if (event.which == 13) {
			if (!$local.$aniadirCuentaComision.hasClass("hidden")) {
				$local.$aniadirCuentaComision.trigger("click");
				return false;
			} else {
				if (!$local.$botonesActualizacion.hasClass("hidden")) {
					$local.$actualizarCuentaComision.trigger("click");
				}
				return false;
			}
		}
	});

	$local.$modalMantenimiento.PopupWindow({
		title: "Mantenimiento de Cuentas Contables Emisor",
		autoOpen: false,
		modal: false,
		height: 440,
		width: 626,
	});

	$local.$aniadirMantenimento.on("click", function() {
		$local.tablaCuentaComisiones.clear().draw();
		$funcionUtil.prepararFormularioRegistro($formMantenimiento);
		$local.$actualizarMantenimiento.addClass("hidden");
		$local.$registrarMantenimiento.removeClass("hidden");
		$local.$modalMantenimiento.PopupWindow("open");
		$local.$modalMantenimiento.PopupWindow("maximize");
		$local.$filaSeleccionada = "";
	});

	$local.$modalMantenimiento.on("close.popupwindow", function() {
		$local.$filaSeleccionada = "";
		$local.cuentaContableEmisor = {};
		$local.$ocultable.addClass("hidden");
	});

	$local.$registrarMantenimiento.on("click", function() {
		var esValido = true;
		var contabConceptosCuentas = [];
		if (!$formMantenimiento.valid()) {
			esValido = false;
		}
		if ($cuentaContables.compensaComisiones == "0" && $cuentaContables.compensaFondos == "0") {
			$funcionUtil.notificarException("La Cuenta Contable debe tener asociado un <b>C\u00F3digo de Transacci\u00F3n</b> con al menos un <b>Tipo de Compensaci\u00F3n</b>", "fa-warning", "Aviso", "warning");
			esValido = false;
		}
		if ($cuentaContables.compensaFondos == "1" && !$formFondo.valid()) {
			$local.$a_fondos.tab("show");
			$local.$a_comisiones.tab("hide");
			esValido = false;
		}
		if ($cuentaContables.compensaComisiones == "1" && $local.tablaCuentaComisiones.rows().data().length === 0) {
			$funcionUtil.notificarException("A\u00F1ada al menos una Cuenta de Comisi\u00F3n", "fa-warning", "Aviso", "warning");
			$local.$a_comisiones.tab("show");
			$local.$a_fondos.tab("hide");
			esValido = false;
		}
		if (!esValido) {
			return;
		}
		var cuentaContableEmisor = $formMantenimiento.serializeJSON();
		if ($cuentaContables.compensaComisiones == "1") {
			contabConceptosCuentas = $local.tablaCuentaComisiones.rows({
				selected: true
			}).data().toArray();
		}
		if ($cuentaContables.compensaFondos == "1") {
			var contabConceptoCuenta = $formFondo.serializeJSON();
			contabConceptoCuenta.idConceptoComision = "0";
			contabConceptoCuenta.tipoCompensacion = $variableUtil.tipoCompensacionFondo;
			contabConceptosCuentas.push(contabConceptoCuenta);
		}
		cuentaContableEmisor.contabConceptosCuentas = contabConceptosCuentas;
		$.ajax({
			type: "POST",
			url: $variableUtil.root + "cuentaContableEmisor",
			data: JSON.stringify(cuentaContableEmisor),
			beforeSend: function(xhr) {
				$local.$registrarMantenimiento.attr("disabled", true).find("i").removeClass("fa-floppy-o").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode: {
				400: function(response) {
					$funcionUtil.limpiarMensajesDeError($formMantenimiento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formMantenimiento);
				},
				409: function(response) {
					$funcionUtil.notificarException(response.responseText, "fa-warning", "Aviso", "warning");
				}
			},
			success: function(cuentasContablesEmisor) {
				$funcionUtil.notificarException($variableUtil.registroExitoso, "fa-check", "Aviso", "success");
				if ($local.$tablaMantenimiento.length > 0) {
					var cuentaContableEmisor = cuentasContablesEmisor[0];
					var row = $local.tablaMantenimiento.row.add(cuentaContableEmisor).draw();
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
		$local.$ocultable.addClass("hidden");
		$funcionUtil.prepararFormularioActualizacion($formMantenimiento);
		$local.$filaSeleccionada = $(this).parents("tr");
		$local.cuentaContableEmisor = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		$funcionUtil.llenarFormulario($local.cuentaContableEmisor, $formMantenimiento);
		$local.$empresas.trigger("change", [$local.cuentaContableEmisor.idCliente]);
		$local.$claseTransacciones.trigger("change", [$local.cuentaContableEmisor.codigoTransaccion]);
		$local.$membresias.trigger("change", [$local.cuentaContableEmisor.codigoClaseServicio, $local.cuentaContableEmisor.idLogo, $local.cuentaContableEmisor.codigoProducto]);
		// $local.$logos.trigger("change",[$local.cuentaContableEmisor.codigoMembresia, $local.cuentaContableEmisor.codigoInstitucion, $local.cuentaContableEmisor.codigoClaseServicio])
		$local.$actualizarMantenimiento.removeClass("hidden");
		$local.$registrarMantenimiento.addClass("hidden");
		$local.$modalMantenimiento.PopupWindow("open");
		$local.$modalMantenimiento.PopupWindow("maximize");
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "cuentaContableEmisor?accion=buscarPorEscenario&" + $.param($local.cuentaContableEmisor),
			beforeSend: function() {
				$local.tablaCuentaComisiones.clear().draw();
			},
			success: function(cuentasContablesEmisor) {
				var cuentaContableEmisor = cuentasContablesEmisor[0];
				if (cuentaContableEmisor.contabConceptosCuentas.length == 0) {
					$funcionUtil.notificarException("No existen Cuentas Comisiones o Fondos asociados a este Escenario Contable.", "fa-check", "Aviso", "success");
					return;
				}
				$.each(cuentaContableEmisor.contabConceptosCuentas, function(i, contabConceptoCuenta) {
					if (contabConceptoCuenta.tipoCompensacion == $variableUtil.tipoCompensacionComision) {
						$local.tablaCuentaComisiones.row.add(contabConceptoCuenta).draw();
					} else {
						$funcionUtil.prepararFormularioActualizacion($formFondo);
						$funcionUtil.llenarFormulario(contabConceptoCuenta, $formFondo);
					}
				});
			}
		});
	});

	$local.$actualizarMantenimiento.on("click", function() {
		var esValido = true;
		var contabConceptosCuentas = [];
		if ($cuentaContables.compensaComisiones == "0" && $cuentaContables.compensaFondos == "0") {
			$funcionUtil.notificarException("La Cuenta Contable debe tener asociado un <b>C\u00F3digo de Transacci\u00F3n</b> con al menos un <b>Tipo de Compensaci\u00F3n</b>", "fa-warning", "Aviso", "warning");
			esValido = false;
		}
		if ($cuentaContables.compensaFondos == "1" && !$formFondo.valid()) {
			$local.$a_fondos.tab("show");
			esValido = false;
		}
		if ($cuentaContables.compensaComisiones == "1" && $local.tablaCuentaComisiones.rows().data().length === 0) {
			$funcionUtil.notificarException("A\u00F1ada al menos una Cuenta de Comisi\u00F3n", "fa-warning", "Aviso", "warning");
			$local.$a_comisiones.tab("show");
			esValido = false;
		}
		if (!esValido) {
			return;
		}
		if ($cuentaContables.compensaComisiones == "1") {
			contabConceptosCuentas = $local.tablaCuentaComisiones.rows({
				selected: true
			}).data().toArray();
		}
		if ($cuentaContables.compensaFondos == "1") {
			var contabConceptoCuenta = $formFondo.serializeJSON();
			contabConceptoCuenta.idConceptoComision = "0";
			contabConceptoCuenta.tipoCompensacion = $variableUtil.tipoCompensacionFondo;
			contabConceptosCuentas.push(contabConceptoCuenta);
		}
		$local.cuentaContableEmisor.contabConceptosCuentas = contabConceptosCuentas;
		$.ajax({
			type: "PUT",
			url: $variableUtil.root + "cuentaContableEmisor",
			data: JSON.stringify($local.cuentaContableEmisor),
			beforeSend: function(xhr) {
				$local.$actualizarMantenimiento.attr("disabled", true).find("i").removeClass("fa-pencil-square").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode: {
				400: function(response) {
					$funcionUtil.limpiarMensajesDeError($formMantenimiento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formMantenimiento);
				},
				409: function(response) {
					$funcionUtil.notificarException(response.responseText, "fa-warning", "Aviso", "warning");
				}
			},
			success: function(cuentasContablesEmisor) {
				$funcionUtil.notificarException($variableUtil.actualizacionExitosa, "fa-check", "Aviso", "success");
				if ($local.$tablaMantenimiento.length > 0) {
					var row = $local.tablaMantenimiento.row($local.$filaSeleccionada).data(cuentasContablesEmisor[0]).draw();
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
		var cuentaContableEmisor = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		$.confirm({
			icon: "fa fa-info-circle",
			title: "Aviso",
			content: "\u00BFDesea eliminar el <b>Escenario y las Cuentas Contables Emisor asociadas</b>?",
			theme: "bootstrap",
			type: "orange",
			buttons: {
				Aceptar: {
					action: function() {
						var confirmar = $.confirm({
							icon: 'fa fa-spinner fa-pulse fa-fw',
							title: "Eliminando...",
							theme: "bootstrap",
							type: "blue",
							content: function() {
								var self = this;
								self.buttons.close.hide();
								$.ajax({
									type: "DELETE",
									url: $variableUtil.root + "cuentaContableEmisor",
									data: JSON.stringify(cuentaContableEmisor),
									autoclose: true,
									beforeSend: function(xhr) {
										xhr.setRequestHeader('Content-Type', 'application/json');
										xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
									}
								}).done(function(response) {
									$funcionUtil.notificarException(response, "fa-check", "Aviso", "success");
									$local.tablaMantenimiento.row($local.$filaSeleccionada).remove().draw(false);
									$local.$filaSeleccionada = "";
								}).fail(function(xhr) {
									switch (xhr.status) {
										case 400:
											$funcionUtil.notificarException($funcionUtil.obtenerMensajeErrorEnCadena(xhr.responseJSON), "fa-warning", "Aviso", "warning");
											break;
										case 409:
											var mensaje = $funcionUtil.obtenerMensajeError("La Cuenta Contable Emisor ", xhr.responseJSON, $variableUtil.accionEliminado);
											$funcionUtil.notificarException(mensaje, "fa-warning", "Aviso", "warning");
											break;
									}
									$local.$filaSeleccionada = "";
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
					btnClass: "btn-warning"
				},
				Cancelar: {
					keys: ['esc']
				},
			}
		});
	});

	$local.$exportar.on('click', function() {
		window.location.href = $variableUtil.root + "cuentaContableEmisor?accion=exportar&rutaEnSidebar=" + $variableUtil.rutaEnSidebar;
	});

});