$(document).ready(function() {

	var $local = {
		$indicadorContabilizacion: $(".indicarContabilizacion"),
		$buscar: $("#buscar"),
		$exportar: $("#exportar"),
		$tablaContabilizacion: $("#tablaContabilizacion"),
		tablaContabilizacion: "",
		
		// Filtros de b\u00FAsqueda
		$rangoFechaBusqueda : $("#rangoFechaBusquedaMovimientos"),
		$instituciones: $("#selectInstitucionMovimientos"),
		$empresas: $("#selectEmpresaMovimientos"),
		$clientes: $("#selectClienteMovimientos"),
		$indicadores : $("#selectIndicadoresMovimientos")
	};

	$funcionUtil.crearDateRangePickerSimple($local.$rangoFechaBusqueda, "right");
	$funcionUtil.crearSelect2($local.$instituciones);
	$funcionUtil.crearSelect2($local.$empresas, "Seleccione una Empresa");
    $funcionUtil.crearSelect2Multiple($local.$clientes, "-1", "TODOS");
	$funcionUtil.crearSelect2Multiple($local.$indicadores, "-1", "TODOS");

	$formCriterioBusquedaReporte = $("#formCriterioBusquedaReporte");

	// Ordena las fechas
	$.fn.dataTable.moment = function ( format, locale ) {
		var types = $.fn.dataTable.ext.type;
	
		// Add type detection
		types.detect.unshift( function ( d ) {
			return moment( d, format, locale, true ).isValid() ?
				'moment-'+format :
				null;
		} );
	
		// Add sorting method - use an integer for the sorting
		types.order[ 'moment-'+format+'-pre' ] = function ( d ) {
			return moment( d, format, locale, true ).unix();
		};
	};
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
		"language": {
			"emptyTable": "No hay transacciones encontradas."
		},
		"initComplete": function() {
			$local.$tablaContabilizacion.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaContabilizacion);
		},
		"columnDefs": [{
			"targets": 0,
			"className": "all filtrable dt-center"
		}, {
			"targets": [ 1, 2 , 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17 ],
			"className": "all filtrable",
		}, {
			"targets": 18,
			"className": "all filtrable dt-right cantidad"
		}, {
			"targets": 19,
			"className": "all filtrable dt-right monto"
		}],
		"columns": [{
			"data": 'fechaProceso',
			"title": "Fecha Proceso"
		}, {
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoInstitucion, row.descripcionInstitucion);
			},
			"title": "Instituci\u00f3n"
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
			"data": function(row) {
				return `${$funcionUtil.unirCodigoDescripcion(row.idLogo, row.descLogoBin)}`;
			},
			"title": "Logo"
		}, {
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoProducto, row.descProducto);
			},
			"title": "Producto"
		}, {
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoMembresia, row.descripcionMembresia);
			},
			"title": "Membres\u00eda"
		}, {
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoClaseServicio, row.descripcionClaseServicio);
			},
			"title": "Servicio"
		}, {
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoOrigen, row.descripcionOrigen);
			},
			"title": "Origen"
		}, {
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoClaseTransaccion, row.descripcionClaseTransaccion);
			},
			"title": "Clase Transacci\u00f3n"
		}, {
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoTransaccion, row.descripcionCodigoTransaccion);
			},
			"title": "C\u00f3digo Transacci\u00f3n"
		}, {
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.fondoCargo, row.descFondoCargo);
			},
			"title": "Fondo Cargo"
		}, {
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.fondoAbono, row.descFondoAbono);
			},
			"title": "Fondo Abono"
		}, {
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.indicadorContabilizacion, row.descripcionIndicadorContabilizacion);
			},
			"title": "Indicador Contabilizaci\u00F3n"
		}, {
			"data": "cuentaCargo",
			"title": "Cuenta Cargo"
		}, {
			"data": "cuentaAbono",
			"title": "Cuenta Abono"
		}, {
			"data": "codigoAnalitico",
			"title": "C\u00f3digo Anal\u00edtico"
		}, {
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoMonedaCompensacion, row.descripcionMonedaCompensacion);
			},
			"title": "Moneda Compensaci\u00f3n"
		}, {
			"data": "cantidad",
			"title": "Cantidad"
		}, {
			"data": "monto",
			"render": $tablaFuncion.formatoMonto(),
			"title": "Importe Compensaci\u00F3n"
		}],
		"order": [],
		"footerCallback": function(row, data, start, end, display) {
			var tieneData = $local.tablaContabilizacion == "" ? false : $local.tablaContabilizacion.data().any();
			var api = this.api(), data;
			if (tieneData) {
				montoTotal = api.column(19).data().reduce(function(a, b) {
					return parseFloat($funcionUtil.formatMoneyToNumber(a)) + parseFloat($funcionUtil.formatMoneyToNumber(b));
				}, 0);
				cantidadTotal = api.column(18).data().reduce(function(a, b) {
					return parseInt($funcionUtil.formatMoneyToNumber(a)) + parseInt($funcionUtil.formatMoneyToNumber(b));
				}, 0);
				$(api.column(19).footer()).html(montoTotal.formatMoney(2));
				$(api.column(18).footer()).html(cantidadTotal.formatMoney(0));
			} else {
				$(api.column(19).footer()).html("");
				$(api.column(18).footer()).html("");
			}
		},
		"createdRow": function(row, data, dataIndex) {
			if (data.monto > 0) {
				$(row).find(".monto").addClass("color-blue");
			} else if (data.montoTransaccion == 0) {
				$(row).find(".monto").addClass("color-inherit");
			} else {
				$(row).find(".monto").addClass("color-red");
			}
		}
	});
	
	$local.$tablaContabilizacion.wrap("<div class='table-responsive'></div>");
	
	$local.$tablaContabilizacion.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaContabilizacion.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaContabilizacion.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaContabilizacion.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});
	
	$local.$buscar.on("click", function() {
		if(!$formCriterioBusquedaReporte.valid()){
			return;
		}
		var criterioBusqueda = $formCriterioBusquedaReporte.serializeJSON();
		var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaBusqueda);
		criterioBusqueda.fechaInicioProceso = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaFinProceso = rangoFechaBusqueda.fechaFin;
		let paramCriterioBusqueda = $.param(criterioBusqueda);
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$clientes, "clientes");
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$indicadores, "indicadoresContabilizacion");
		$.ajax({
			type: "GET",
			url: `${$variableUtil.root}reporte/contabilizacion/movimiento?accion=buscar&${paramCriterioBusqueda}`,
			contentType: "application/json",
			dataType: "json",
			beforeSend: function() {
				$local.tablaContabilizacion.clear().draw();
				$local.$buscar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success: function(reporteContabilizacionMovimento) {
				if (reporteContabilizacionMovimento.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00f3n", "info");
					return;
				}
				$local.tablaContabilizacion.rows.add(reporteContabilizacionMovimento).draw();
				$tablaFuncion.pintarMontosComisiones($local.$tablaContabilizacion, "tfoot td.monto");
			},
			complete: function() {
				$local.$buscar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$exportar.on("click", function() {
		if(!$formCriterioBusquedaReporte.valid()){
			return;
		}
		var criterioBusqueda = $formCriterioBusquedaReporte.serializeJSON();
		var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaBusqueda);
		criterioBusqueda.fechaInicioProceso = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaFinProceso = rangoFechaBusqueda.fechaFin;
		let descripcionRangoFechas = $local.$rangoFechaBusqueda.val();
		criterioBusqueda.descripcionFechaProceso = descripcionRangoFechas == "" || descripcionRangoFechas == undefined ? "TODOS" : descripcionRangoFechas;
		criterioBusqueda.descripcionInstitucion = $local.$instituciones.find('option:selected').text();
		criterioBusqueda.descripcionEmpresa = $local.$empresas.find("option:selected").val() === "-1" ? "TODOS" : $local.$empresas.find("option:selected").text();
		criterioBusqueda.descripcionCliente = !!$local.$clientes.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$clientes, "; ") : "TODOS";
		criterioBusqueda.descripcionIndicador = !!$local.$indicadores.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$indicadores, "; ") : "TODOS";
		let paramCriterioBusqueda = $.param(criterioBusqueda);
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$clientes, "clientes");
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$indicadores, "indicadoresContabilizacion");
		$funcionUtil.webSocketMethod('exportacionPOI');
		window.location.href = `${$variableUtil.root}reporte/contabilizacion/movimientos?rutaEnSidebar=${$variableUtil.rutaEnSidebar}&accion=exportar&${paramCriterioBusqueda}`;
	});
});