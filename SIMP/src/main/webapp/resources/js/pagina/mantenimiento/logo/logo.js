$(document).ready(function() {
	const $local = {
		// Variables
		codigoInstitucionSeleccionado: "",
		codigoLogoSeleccionado: "",
		codigoBinSeleccionado: "",

		$tablaMantenimiento: $("#tablaMantenimiento"),
		tablaMantenimiento: "",
		$modalMantenimiento: $("#modalMantenimiento"),
		$filaSeleccionada: "",

		// Selects e imputs
		$instituciones: $("#instituciones"),
		$membresias: $("#membresias"),
		$clasesServicio: $("#clasesServicio"),
		$idBin: $("#idBin"),
		$segundoIdBin: $("#segundoIdBin"),

		// Botones de Mantenimiento
		$aniadirMantenimento: $("#aniadirMantenimiento"),
		$registrarMantenimiento: $("#registrarMantenimiento"),
		$actualizarMantenimiento: $("#actualizarMantenimiento"),

		// Filtros para Tabla Mantenimiento
		filtrosSeleccionables: {},

		// Permisos
		permisoActualizacion: false,
		permisoEliminacion: false,
	};

	const $formMantenimiento = $("#formMantenimiento");

	$funcionUtil.crearSelect2($local.$instituciones, "Seleccione una Instituci\u00F3n");
	$funcionUtil.crearSelect2($local.$membresias, "Seleccione una Membres\u00EDa");
	$funcionUtil.crearSelect2($local.$clasesServicio, "Seleccione una Clase Servicio");

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
			"url": $variableUtil.root + "logo?accion=buscarTodosInstitucion",
			"dataSrc": ""
		},
		"language": {
			"emptyTable": "No hay Logos registrados."
		},
		"initComplete": function() {
			$local.$tablaMantenimiento.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezadoTablaPersonalizado(this, $local.$tablaMantenimiento, $local.filtrosSeleccionables, 2);
		},
		"order": [[0, 'asc'], [1, 'asc']],
		"columnDefs": [
			{
				"targets": [0, 1, 2, 3, 4, 7, 8, 9],
				"className": "all filtrable",
			},
			{
				"targets": [5, 6],
				"className": "all dt-right filtrable"
			},
			{
				"targets": 10,
				"className": "all dt-center",
				"render": function() {
					return "" + ($local.permisoActualizacion == "true" ? $variableUtil.botonActualizar : "") + " " + ($local.permisoEliminacion == "true" ? $variableUtil.botonEliminar : "");
				}
			}
		],
		"columns": [
			{
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.idInstitucion, row.descInstitucion); // 0
				}
			},
			{
				"data": 'idLogo', // 1
			},
			{
				"data": 'descripcion' // 2
			},
			{
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.idMembresia, row.descMembresia); // 3
				}
			},
			{
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.idClaseServicio, row.descClaseServicio); // 4
				}
			},
			{
				"data": 'longitudBin' // 5
			},
			{
				"data": 'longitudPan' // 6
			},
			{
				"data": 'idBin' // 7
			},
			{
				"data": 'rangoInicialTarjeta' // 8
			},
			{
				"data": 'rangoFinalTarjeta' // 9
			},
			{
				"data": null, // 10
			}
		]
	});

	function obtenerRestoRango(rango, longitudBin, longitudPan) {
		if (rango === null || rango === undefined || rango === '') {
			return '';
		}
		const rangoString = rango.toString();
		return rangoString.substring(longitudBin, longitudPan)
	}

	$local.$tablaMantenimiento.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaMantenimiento.children("tbody").on("click", ".actualizar", function() {
		$funcionUtil.prepararFormularioActualizacion($formMantenimiento);
		$local.$filaSeleccionada = $(this).parents("tr");
		const logo = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		logo.restoRangoInicial = obtenerRestoRango(logo.rangoInicialTarjeta, logo.longitudBin, logo.longitudPan)
		logo.restoRangoFinal = obtenerRestoRango(logo.rangoFinalTarjeta, logo.longitudBin, logo.longitudPan)
		logo.segundoIdBin = logo.idBin;
		$local.codigoLogoSeleccionado = logo.idLogo;
		$local.codigoBinSeleccionado = logo.idBin;
		$local.codigoInstitucionSeleccionado = logo.idInstitucion;
		$funcionUtil.llenarFormulario(logo, $formMantenimiento);
		$local.$membresias.trigger("change", [logo.idClaseServicio]);
		$local.$actualizarMantenimiento.removeClass("hidden");
		$local.$registrarMantenimiento.addClass("hidden");
		$local.$modalMantenimiento.PopupWindow("open");
	});

	$local.$membresias.on("change", function(event, opcionSeleccionada) {
		const membresia = $(this).val();
		if (membresia === undefined) {
			$local.$clasesServicio.find("option:not(:eq(0))").remove();
			return;
		}
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "claseServicio/membresia/" + membresia,
			beforeSend: function(xhr) {
				$local.$clasesServicio.find("option:not(:eq(0))").remove();
				$local.$clasesServicio.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando clases de servicio</span>")
			},
			statusCode: {
				400: function(response) {
					$funcionUtil.limpiarMensajesDeError($formMantenimiento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formMantenimiento);
				}
			},
			success: function(response) {
				$.each(response, function(i, claseServicio) {
					$local.$clasesServicio.append($("<option />").val(this.codigoClaseServicio).text(this.codigoClaseServicio + " - " + this.descripcion));
				});
				if (opcionSeleccionada != null && opcionSeleccionada != undefined) {
					$local.$clasesServicio.val(opcionSeleccionada).trigger("change.select2");
				}
			},
			complete: function() {
				$local.$clasesServicio.parent().find(".cargando").remove();
			}
		});
	});

	$local.$idBin.on("keyup", function(event, opcionSeleccionada) {
		$local.$segundoIdBin.val($(this).val())
	})

	$local.$segundoIdBin.on("keyup", function(event, opcionSeleccionada) {
		$local.$idBin.val($(this).val())
	})

	$local.$registrarMantenimiento.on("click", function() {
		if (!$formMantenimiento.valid()) {
			return;
		}

		const logo = $formMantenimiento.serializeJSON();
		logo.rangoInicialTarjeta = logo.idBin + logo.restoRangoInicial
		logo.rangoFinalTarjeta = logo.idBin + logo.restoRangoFinal
		$.ajax({
			type: "POST",
			url: $variableUtil.root + "logo",
			data: JSON.stringify(logo),
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
			success: function(logos) {
				$funcionUtil.notificarException($variableUtil.registroExitoso, "fa-check", "Aviso", "success");
				if ($local.$tablaMantenimiento.length > 0) {
					let logoRegistrado = logos[0];
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

	$local.$modalMantenimiento.PopupWindow({
		title: "Mantenimiento Logo",
		autoOpen: false,
		modal: false,
		height: 640,
		width: 640,
	});

	$local.$modalMantenimiento.on("open.popupwindow", function() {
		$formMantenimiento.find("input:not([disabled]):first").focus();
	});

	$local.$aniadirMantenimento.on("click", function() {
		$funcionUtil.prepararFormularioRegistro($formMantenimiento);
		$local.$actualizarMantenimiento.addClass("hidden");
		$local.$registrarMantenimiento.removeClass("hidden");
		$local.$modalMantenimiento.PopupWindow("open");
	});

	$local.$modalMantenimiento.on("close.popupwindow", function() {
		$local.codigoLogoSeleccionado = 0;
	});

	$local.$actualizarMantenimiento.on("click", function() {
		if (!$formMantenimiento.valid()) {
			return;
		}
		const logo = $formMantenimiento.serializeJSON();
		logo.idBin = $local.codigoBinSeleccionado;
		logo.idLogo = $local.codigoLogoSeleccionado
		logo.idInstitucion = $local.codigoInstitucionSeleccionado
		logo.rangoInicialTarjeta = logo.idBin + logo.restoRangoInicial
		logo.rangoFinalTarjeta = logo.idBin + logo.restoRangoFinal

		$.ajax({
			type: "PUT",
			url: $variableUtil.root + "logo",
			data: JSON.stringify(logo),
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
			success: function(logos) {
				$funcionUtil.notificarException($variableUtil.actualizacionExitosa, "fa-check", "Aviso", "success");
				if ($local.$tablaMantenimiento.length > 0) {
					$local.tablaMantenimiento.row($local.$filaSeleccionada).remove().draw(false);
					let logo = logos[0];
					let row = $local.tablaMantenimiento.row.add(logo).draw();
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
		const logo = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		$.confirm({
			icon: "fa fa-info-circle",
			title: "Aviso",
			content: "&iquest;Desea eliminar el Logo <b>'" + logo.idLogo + " - " + logo.descripcion + "'</b> y todos sus Productos?",
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
									url: $variableUtil.root + "logo",
									data: JSON.stringify(logo),
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
											let mensaje = $funcionUtil.obtenerMensajeError("El BIN <b>" + bin.idBin + " - " + bin.descripcion + "</b>", xhr.responseJSON, $variableUtil.accionEliminado);
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