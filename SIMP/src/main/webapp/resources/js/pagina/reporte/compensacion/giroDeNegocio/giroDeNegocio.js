$(document).ready(function() {

	var $local = {
		$rangoFechaBusqueda: $("#rangoFechaBusquedaGiroNegocio"),
		$buscar: $("#buscar"),
		$tablaContabilizacion: $("#tablaContabilizacion"),
		tablaContabilizacion: "",
		$exportarXlsx: $('#exportar'),
		$instituciones: $('#selectInstitucionGiroNegocio'),
		$empresas: $('#selectEmpresaGiroNegocio'),
		$clientes: $('#selectClienteGiroNegocio')
	};
	
	/* Variable Global */
	$formCriterioBusquedaReporte = $("#formCriterioBusquedaReporte");
	$funcionUtil.crearDateRangePickerSimple($local.$rangoFechaBusqueda, "right");
	$funcionUtil.crearSelect2($local.$instituciones);
	$funcionUtil.crearSelect2($local.$empresas, "Seleccione una Empresa");
	$funcionUtil.crearSelect2Multiple($local.$clientes, "-1", "TODOS");
	
	$.fn.dataTable.moment('DD/MM/YYYY');

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

	$local.tablaContabilizacion = $local.$tablaContabilizacion.DataTable({
		"initComplete": function() {
			$local.$tablaContabilizacion.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaContabilizacion);
		},
		"language": {
			"emptyTable": "No se encontraron comisiones."
		},
		"columnDefs": [{
			"targets": 0,
			"className": "all filtrable dt-center"
		}, {
			"targets": [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ],
			"className": 'all filtrable'
		}, {
			"targets": 11,
			"className": "all filtrable dt-right cantidad"
		}, {
			"targets": 12,
			"className": "all filtrable dt-right monto"
		}, {
			"targets": 13,
			"className": "all filtrable dt-right comision"
		} ],
		"columns": [{
			"data": 'fechaProceso',
			"title": 'Fecha Proceso'
		}, {
			"data": function(row){
				return $funcionUtil.unirCodigoDescripcion(row.idInstitucion, row.descripcionInstitucion);
			},
			"title": "Instituci\u00F3n"
		}, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.idEmpresa, row.descEmpresa);
            },
            "title": "Empresa"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.idCliente, row.descCliente);
            },
            "title": "Cliente"
        }, {
			"data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.giroNegocio, row.descGiroNegocio);
            },
			"title": 'Giro Negocio'
		}, {
            "data": (row) => {
                return `${$funcionUtil.unirCodigoDescripcion(row.idLogo, row.descLogo)} (${row.idBin})`;
            },
            "title": "Logo"
        }, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.codigoProducto, row.descProducto);
            },
            "title": "Producto"
        },{
			"data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.idMembresia, row.descMembresia);
            },
			"title": 'Membres\u00EDa'
		}, {
			"data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.idClaseServicio, row.descClaseServicio);
            },
			"title": 'Servicio'
		}, {
			"data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.idOrigen, row.descOrigen);
            },
			"title": 'Origen'
		}, {
            "data": (row) => {
                return $funcionUtil.unirCodigoDescripcion(row.monedaCompensacion, row.descMonedaCompensacion);
            },
            "title": "Moneda"
        }, {
			"data": 'cantidad',
			"title": 'Cantidad'
		}, {
			"data": 'monto',
			"render": $tablaFuncion.formatoMonto(),
			"title": 'Monto'
		}, {
			"data": 'comision',
			"render": $tablaFuncion.formatoComision(),
			"title": 'Comisi\u00F3n'
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

	$local.$tablaContabilizacion.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaContabilizacion.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaContabilizacion.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaContabilizacion.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	$local.$buscar.on("click", function() {
		if (!$formCriterioBusquedaReporte.valid()) {
			return;
		}
		var criterioBusqueda = $formCriterioBusquedaReporte.serializeJSON();
		var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaBusqueda);
		criterioBusqueda.fechaInicioProceso = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaFinProceso = rangoFechaBusqueda.fechaFin;
		let paramCriterioBusqueda = $.param(criterioBusqueda);
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$clientes, "clientes");
		$.ajax({
			type: "GET",
			url: `${$variableUtil.root}reporte/compensacion/emisor/giroDeNegocio?accion=buscar&${paramCriterioBusqueda}`,
			contentType: "application/json",
            dataType: "json",
			beforeSend: function() {
				$local.tablaContabilizacion.clear().draw();
				$local.$buscar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success: function(reporteContabilizacionComisionesGiroDeNegocio) {
				if (reporteContabilizacionComisionesGiroDeNegocio.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00f3n", "info");
					return;
				}
				$local.tablaContabilizacion.rows.add(reporteContabilizacionComisionesGiroDeNegocio).draw();
			},
			complete: function() {
				$local.$buscar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$exportarXlsx.on("click", function() {
		if (!$formCriterioBusquedaReporte.valid()) {
			return;
		}
		var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaBusqueda);
		var criterioBusqueda = $formCriterioBusquedaReporte.serializeJSON();
		criterioBusqueda.fechaInicioProceso = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaFinProceso = rangoFechaBusqueda.fechaFin;
		criterioBusqueda.descripcionInstitucion = $local.$instituciones.find('option:selected').text();
		criterioBusqueda.descripcionEmpresa = $local.$empresas.find("option:selected").val() === "-1" ? "TODOS" : $local.$empresas.find("option:selected").text();
		var descripcionRangoFechas = $local.$rangoFechaBusqueda.val();
		criterioBusqueda.descripcionRangoDeFechas = descripcionRangoFechas == "" || descripcionRangoFechas == undefined ? "TODOS" : descripcionRangoFechas;
		criterioBusqueda.descripcionCliente = !!$local.$clientes.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$clientes, "; ") : "TODOS";
		var paramCriterioBusqueda = $.param(criterioBusqueda);
		paramCriterioBusqueda = paramCriterioBusqueda + $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$clientes, "clientes");
		$funcionUtil.webSocketMethod('exportacionPOI');
		window.location.href = `${$variableUtil.root}reporte/compensacion/emisor/giroDeNegocio?rutaEnSidebar=${$variableUtil.rutaEnSidebar}&accion=exportar&${paramCriterioBusqueda}`;
	});
});