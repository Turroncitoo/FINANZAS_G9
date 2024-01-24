$(document).ready(function() {
	var $local = {
		$tablaReporte: $("#tablaReporteInterfaceContable"),
		tablaReporte: "",
		$rangoFechaBusqueda: $("#rangoFechaBusqueda"),
		$buscar: $("#buscar"),
		$exportar: $("#exportar"),
		$instituciones: $('#institucionesInterfaceContable'),
		$empresas: $('#empresasInterfaceContable'),
		$clientes: $('#clientesInterfaceContable')
	};

	$funcionUtil.crearDateRangePickerSimple($local.$rangoFechaBusqueda, "right");
	$funcionUtil.crearSelect2($local.$instituciones);
	$funcionUtil.crearSelect2($local.$empresas, "Seleccione una Empresa");
	$funcionUtil.crearSelect2Multiple($local.$clientes, "-1", "TODOS");

	$formCriterioBusquedaReporte = $("#formCriterioBusquedaReporte");

	$local.$empresas.on("change", () => {
        const opcionSeleccionada = $local.$empresas.val();
        if (!opcionSeleccionada) {
            $local.$clientes.find("option").remove();
            return;
        }
        $.ajax({
            type: "GET",
            url: `${$variableUtil.root}cliente/empresa/${opcionSeleccionada}`,
            beforeSend: () => {
                $local.$clientes.find("option").remove();
                $local.$clientes.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Clientes...</span>")
            },
            success: (response) => {
                $.each(response, (i) => {
                    $local.$clientes.append($("<option/>").val(response[i].idCliente).text(response[i].idCliente + " - " + response[i].descripcion));
                });
            },
            complete: () => {
                $local.$clientes.parent().find(".cargando").remove();
            }
        });
    });
	
	$local.tablaReporte = $local.$tablaReporte.DataTable({
		"language": {
			"emptyTable": "No hay transacciones encontradas.",
		},
		"initComplete": function() {
			$local.$tablaReporte.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaReporte);
		},
		"columnDefs": [{
			"targets": [ 0, 14 ],
			"className": "all filtrable dt-center"
		}, {
			"targets": [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 17, 20, 21 ],
			"className": "all filtrable",
		}, {
			"targets": [ 16, 18 ],
			"className": "all filtrable dt-right monto",
		}, {
			"targets": 19,
			"className": "all filtrable dt-right comision",
		}],
		"columns": [{
			"data": "fechaProceso",
			"title": "Fecha Proceso"
		}, {
			"data": function(row){
				return $funcionUtil.unirCodigoDescripcion(row.idInstitucion, row.descInstitucion);
			},
			"title": "Instituci\u00F3n"
		}, {
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idEmpresa, row.descripcionEmpresa);
			},
			"title": "Empresa"
		}, {
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idCliente, row.descripcionCliente);
			},
			"title": "Cliente"
		}, {
			"data": function(row){
				return $funcionUtil.unirCodigoDescripcion(row.membresia, row.descMembresia);
			} ,
			"title": "Membres\u00EDa"
		}, {
			"data": function(row){
				return $funcionUtil.unirCodigoDescripcion(row.claseServicio, row.descClaseServicio);
			} ,
			"title": "Servicio"
		}, {
			"data": function(row){
				return $funcionUtil.unirCodigoDescripcion(row.origen, row.descOrigen);
			},
			"title": "Origen" 
		}, {
			"data": function(row){
				return $funcionUtil.unirCodigoDescripcion(row.claseTransaccion, row.descripcionClaseTransaccion);
			},
			"title": "Clase Transacci\u00F3n"
		}, {
			"data": function(row){
				return $funcionUtil.unirCodigoDescripcion(row.codigoTransaccion, row.descripcionCodigoTransaccion);
			},
			"title": "C\u00F3digo Transacci\u00F3n"
		}, {
			"data": "operacion",
			"title": "Operaci\u00F3n"
		}, {
			"data": function(row){
				return $funcionUtil.unirCodigoDescripcion(row.institucionEmisora, row.descripcionInstitucionEmisora);
			},
			"title": "Inst. Emisora"
		}, {
			"data": function(row){
				return $funcionUtil.unirCodigoDescripcion(row.institucionReceptora, row.descripcionInstitucionReceptora);
			},
			"title": "Inst. Receptora"
		}, {
			"data": "idThb",
			"title": "ID THB"
		}, {
			"data": "numeroTarjeta",
			"title": "N\u00FAmero Tarjeta"
		}, {
			"data": function(row){
				return $funcionUtil.unirCodigoDescripcion(row.fechaTransaccionCadena, row.horaTransaccion);
			},
			"title": "Fecha Transacci\u00F3n"
		}, {
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.monedaTransaccion, row.descMonedaTransaccion);
			},
			"title": "Moneda Transacci\u00F3n"
		}, {
			"data": "montoTransaccion",
			"render": $tablaFuncion.formatoMonto(),
			"title": "Importe Transacci\u00F3n"
		}, {
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.monedaCompensacion, row.descMonedaCompensacion);
			},
			"title": "Moneda Compensaci\u00F3n"
		}, {
			"data": "montoCompensacion",
			"render": $tablaFuncion.formatoMonto(),
			"title": "Importe Compensaci\u00F3n"
		}, {
			"data": function(row) {
				return row.tipoCambioSBS === null ? "-" : parseFloat(row.tipoCambioSBS).toFixed(4);
			},
			"title": "Tipo Cambio SBS"
		}, {
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.cuentaCargo, row.descripcionCuentaCargo);
			},
			"title": "Cta. Cargo"
		}, {
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.cuentaAbono, row.descripcionCuentaAbono);
			},
			"title": "Cta. Abono"
		}],
		"order": [],
		"createdRow": function(row, data, dataIndex) {
			$(row).find(".monto").filter(function() {
				var celda = $(this);
				var valor = parseFloat(celda.text());
				if (valor > 0) {
					celda.addClass("color-blue");
				} else if (valor < 0) {
					celda.addClass("color-red");
				} else {
					celda.addClass("color-inherit");
				}
			});
			$(row).find(".comision").filter(function() {
				var celda = $(this);
				var valor = parseFloat(celda.text());
				if (valor > 0) {
					celda.addClass("color-blue");
				} else if (valor < 0) {
					celda.addClass("color-red");
				} else {
					celda.addClass("color-inherit");
				}
			});
		}
	});

	$local.$tablaReporte.wrap("<div class='table-responsive'></div>");

	$local.$tablaReporte.find("thead").on("keyup", "input.filtrable", function() {
		$local.tablaReporte.column($(this).parent().index() + ":visible").search(this.value).draw();
	});

	$local.$tablaReporte.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaReporte.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	$local.$buscar.on("click", function() {
		if (!$formCriterioBusquedaReporte.valid()) {
			return;
		}
		var criterioBusqueda = $formCriterioBusquedaReporte.serializeJSON();
		var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaBusqueda);
		criterioBusqueda.fechaProcesoInicio = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaProcesoFin = rangoFechaBusqueda.fechaFin;
		let paramCriterioBusqueda = $.param(criterioBusqueda);
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$clientes, "clientes");
		$.ajax({
			type: "GET",
			url: `${$variableUtil.root}reporte/interfaceContable?accion=buscar&${paramCriterioBusqueda}`,
			contentType: "application/json",
            dataType: "json",
			beforeSend: function() {
				$local.tablaReporte.clear().draw();
				$local.$buscar
					.attr("disabled", true)
					.find("i")
					.removeClass("fa-search")
					.addClass("fa-spinner fa-pulse fa-fw");
			},
			success: function(response) {
				if (response.length == 0) {
					$funcionUtil.notificarException(
						$variableUtil.busquedaSinResultados,
						"fa-exclamation-circle",
						"Informaci\u00F3n",
						"info"
					);
					return;
				}
				$local.tablaReporte.rows.add(response).draw();
			},
			complete: function() {
				$local.$buscar
					.attr("disabled", false)
					.find("i")
					.addClass("fa-search")
					.removeClass("fa-spinner fa-pulse fa-fw");
			},
		});
	});
	$local.$exportar.on("click", function() {
		if (!$formCriterioBusquedaReporte.valid()) {
			return;
		}
		var criterioBusqueda = $formCriterioBusquedaReporte.serializeJSON();
		var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaBusqueda);
		criterioBusqueda.fechaProcesoInicio = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaProcesoFin = rangoFechaBusqueda.fechaFin;
		criterioBusqueda.descFechaProceso = $local.$rangoFechaBusqueda.val() || "TODOS";
		criterioBusqueda.descripcionInstitucion = $local.$instituciones.find('option:selected').text();
		criterioBusqueda.descripcionEmpresa = $local.$empresas.find("option:selected").val() === "-1" ? "TODOS" : $local.$empresas.find("option:selected").text();
		criterioBusqueda.descripcionCliente = !!$local.$clientes.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$clientes, "; ") : "TODOS";
		var paramCriterioBusqueda = $.param(criterioBusqueda);
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$clientes, "clientes");
		$funcionUtil.webSocketMethod('exportacionPOI');
		window.location.href = `${$variableUtil.root}reporte/interfaceContable?rutaEnSidebar=${$variableUtil.rutaEnSidebar}&accion=exportar&${paramCriterioBusqueda}`;
	});
});
