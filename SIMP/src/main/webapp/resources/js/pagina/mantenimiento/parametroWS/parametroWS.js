$(document).ready(function() {

	var $local = {
		$tablaMantenimiento: $("#tablaMantenimiento"),
		tablaMantenimiento: "",
		$modalMantenimiento: $("#modalMantenimiento"),
		$filaSeleccionada: "",
		$actualizarMantenimiento: $("#actualizarMantenimiento"),
		$registrarMantenimiento: $('#registrarMantenimiento'),
		$rangoFechaBusqueda: $("#rangoFechaBusqueda"),

		$txtTokenParaHabilitarRestWS: $('#txtTokenParaHabilitarRestWS'),

		arregloSiNo: ["1", "0"],
		// Permisos
		permisoActualizacion: false
	};

	$funcionUtil.crearDatePickerSimple($local.$rangoFechaBusqueda, "DD/MM/YYYY");

	$formMantenimiento = $("#formMantenimiento");

	$("#ipsPermitidas").select2({
		tags: true,
		tokenSeparators: [';'],
		IPChecker: true
	})

	$(".select2-container").css("width", "100%");
	$.fn.dataTable.ext.errMode = 'none';

	$local.$tablaMantenimiento.on('xhr.dt', function(e, settings, json, xhr) {
		switch (xhr.status) {
			case 500:
				$local.tablaMantenimiento.clear().draw();
				break;
		}
	});

	$local.permisoActualizacion = $local.$tablaMantenimiento.attr("data-permiso-actualizacion") || false;

	$local.tablaMantenimiento = $local.$tablaMantenimiento.DataTable({
		"ajax": {
			"url": $variableUtil.root + "parametroWS?accion=buscarTodos",
			"dataSrc": ""
		},
		"language": {
			"emptyTable": "No hay Par\u00E1metros de Web Services registrados"
		},
		"initComplete": function() {
			$local.$tablaMantenimiento.wrap("<div class='table-responsive'></div>");
		},
		"columnDefs": [
			{
				"targets": [0, 1, 2, 3, 4, 5, 6, 7, 8, 9],
				"className": "all dt-center",
			}, {
				"targets": 8,
				"className": "all seleccionable data-no-definida",
				"render": function(data, type, row) {
					return $funcionUtil.insertarEtiquetaSiNo(row.filtrarIps);
				}
			}, {
				"targets": 10,
				"className": "all dt-center",
				"width": "10%",
				"render": function() {
					return "" + ($local.permisoActualizacion == "true" ? $variableUtil.botonActualizar : "");
				}
			}
		],
		"columns": [
			{
				"data": function(row) {
					if (row.pathHostWS == null) {
						return "";
					}
					return row.pathHostWS;
				},
				"title": "Servidor WST UBA"
			}, {
				"data": function(row) {
					if (row.llaveInstalacion == null) {
						return "";
					}
					return row.llaveInstalacion;
				},
				"title": "Llave de instalaci\u00F3n"
			}, {
				"data": function(row) {
					if (row.llaveTransporteZMK == null) {
						return "";
					}
					return row.llaveTransporteZMK;
				},
				"title": "Llave Transporte (ZMK)"
			}, {
				"data": function(row) {
					if (row.llaveTrabajoZPK == null) {
						return "";
					}
					return row.llaveTrabajoZPK;
				},
				"title": "Llave Trabajo (ZPK)"
			}, {
				"data": function(row) {
					if (row.tokenParaHabilitarRestWS == null) {
						return "";
					}
					return row.tokenParaHabilitarRestWS;
				},
				"title": 'Token de habilitaci\u00F3n'
			}, {
				"data": function(row) {
					return row.tiempoExpiracionTokenEnMinutos + " minutos";
				},
				"title": 'Expiracion Token Auth SIMPHub'
			}, {
				"data": function(row) {
					if (row.pathBaseParaConsultaDesdeSIMPWeb == null) {
						return "";
					}
					return row.pathBaseParaConsultaDesdeSIMPWeb;
				},
				"title": 'Sevidor SIMPHub'
			}, {
				"data": function(row) {
					if (row.servidorLogWS == null) {
						return "";
					}
					return row.servidorLogWS;
				},
				"title": 'Sevidor Log WS'
			}, {
				"data": null,
				"title": "Filtrar IPs"
			}, {
				"data": function(row) {
					if (row.ipsPermitidas == null) {
						return "";
					}
					return row.ipsPermitidas;
				},
				"title": "IP's Permitidas"
			}, {
				"data": null,
				"title": "Acci\u00F3n"
			}
		]
	});

	$local.$tablaMantenimiento.find("thead").on('keyup', 'input', function() {
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaMantenimiento.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	$local.$modalMantenimiento.PopupWindow({
		title: "Mantenimiento de Par\u00E1metros WebServices",
		autoOpen: false,
		modal: false,
		height: 440,
		width: 670,
	});

	$local.$modalMantenimiento.on("open.popupwindow", function() {
		$formMantenimiento.find("input:not([readonly]):first").focus();
	});

	$local.$modalMantenimiento.on("close.popupwindow", function() {
		$local.idparametroWSSeleccionado = "";
	});

	$formMantenimiento.find("input").keypress(function(event) {
		if (event.which == 13) {
			$local.$actualizarMantenimiento.trigger("click");
			return false;
		}
	});

	$local.$tablaMantenimiento.children("tbody").on("click", ".actualizar", function() {
		$funcionUtil.prepararFormularioActualizacion($formMantenimiento);
		$local.$filaSeleccionada = $(this).parents("tr");
		$local.$registrarMantenimiento.addClass('hidden');
		$('#ipsPermitidas').children().remove();
		let dataIps = $local.tablaMantenimiento.row($local.$filaSeleccionada).data()["ipsPermitidas"].split(";");
		$.each(dataIps, function(index, value) {
			if (value != "") {
				$('#ipsPermitidas').append("<option selected>" + value + "</option>").change();
			}
		});
		var parametroWS = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		$funcionUtil.llenarFormulario(parametroWS, $formMantenimiento);
		$local.$txtTokenParaHabilitarRestWS.val(parametroWS.tokenParaHabilitarRestWS);
		$local.$actualizarMantenimiento.removeClass("hidden");
		$local.$modalMantenimiento.PopupWindow("open");
	});

	$local.$actualizarMantenimiento.on("click", function() {
		if (!$formMantenimiento.valid()) return;
		var parametroWS = $formMantenimiento.serializeJSON(), valido = true;
		let ipsLista = "";
		$.each($("#ipsPermitidas").val(), function(index, value) {
			if (!$funcionUtil.ValidarIPaddress(value)) {
				$funcionUtil.notificarException("Direcci\u00F3n IP invalida!", "fa-exclamation-triangle", "Aviso", "warning");
				valido = false;
				return;
			}
			ipsLista = value + ";" + ipsLista;
		});
		if (!valido) return;
		ipsLista.replace(";undefined", "")
		parametroWS.ipsPermitidas = ipsLista
		$.ajax({
			type: "PUT",
			url: $variableUtil.root + "parametroWS",
			data: JSON.stringify(parametroWS),
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
			success: function(parametroWS) {
				$funcionUtil.notificarException("Actualizaci\u00F3n Exitosa", "fa-check", "Aviso", "success");
				$local.tablaMantenimiento.row($local.$filaSeleccionada).remove().draw(false);
				var row = $local.tablaMantenimiento.row.add(parametroWS[0]).draw();
				row.show().draw(false);
				$(row.node()).animateHighlight();
				$local.$modalMantenimiento.PopupWindow("close");
			},
			error: function(response) {
			},
			complete: function(response) {
				$local.$actualizarMantenimiento.attr("disabled", false).find("i").addClass("fa-pencil-square").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});
});