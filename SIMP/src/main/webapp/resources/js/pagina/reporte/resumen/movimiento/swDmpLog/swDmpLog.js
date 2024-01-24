$(document).ready(function() {

	var $local = {
		$buscar : $("#buscar"),
		$tablaResumenMovimiento : $("#tablaResumenMovimiento"),
		tablaResumenMovimiento : "",
		$resultadoBusquedaResumenMovimiento : $("#resultadoBusquedaResumenMovimiento"),
		$exportar : $("#exportar"),
		
		// Filtros de B\u00FAsqueda
		$rangoFechaBusqueda: $("#rangoFechaBusquedaSwDmpLog"),
		$instituciones: $('#selectInstitucionSwDmpLog'),
		$empresas: $('#selectEmpresaSwDmpLog'),
		$clientes: $('#selectClienteSwDmpLog'),
		$rolesTransaccion: $("#selectRolesTransaccionSwDmpLog"),
		$procesos: $("#selectProcesosSwDmpLog"),
		$canales: $("#selectCanalSwDmpLog")
	};

	$funcionUtil.crearDateRangePickerConsulta($local.$rangoFechaBusqueda, "right");
	$funcionUtil.crearSelect2($local.$instituciones);
	$funcionUtil.crearSelect2($local.$empresas, "Seleccione una Empresa");
	$funcionUtil.crearSelect2Multiple($local.$clientes, "-1", "TODOS");
	$funcionUtil.crearSelect2Multiple($local.$rolesTransaccion, "-1", "TODOS");
	$funcionUtil.crearSelect2Multiple($local.$procesos, "-1", "TODOS");
	$funcionUtil.crearSelect2Multiple($local.$canales,  "-1", "TODOS");

	$formCriterioBusquedaReporte = $("#formCriterioBusquedaReporte");

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

	$local.tablaResumenMovimiento = $local.$tablaResumenMovimiento.DataTable({
		"language": {
			"emptyTable": "No hay transacciones encontradas"
		},
		"initComplete": function() {
			$local.$tablaResumenMovimiento.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaResumenMovimiento);
		},
		"columnDefs": [{
			"targets": 0,
			"className": "all filtrable dt-center",
		}, {
			"targets": [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ],
			"className": "all filtrable"
		}, {
			"targets": 11,
			"className": "all filtrable dt-right"
		}, {
			"targets": 12,
			"className": "all filtrable dt-right monto"
		}],
		"columns": [{
			"data": "fechaProceso",
			"title": "Fecha Proceso"
		}, {
			"data": function(row){
				return $funcionUtil.unirCodigoDescripcion(row.idInstitucion, row.descripcionInstitucion);
			},
			"title": "Instituci\u00F3n"
		}, {
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
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.rolTransaccion, row.descripcionRolTransaccion);
			},
			"title": "Rol Transacci\u00f3n"
		}, {
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoProceso, row.descripcionCodigoProceso);
			},
			"title": "C\u00F3digo Proceso"
		}, {
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoRespuesta, row.descripcionCodigoRespuesta);
			},
			"title": "C\u00F3digo Respuesta"
		}, {
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idCanal, row.descripcionCanal);
			},
			"title": "Canal"
		}, {
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoGiroNegocio, row.descripcionGiroNegocio);
			},
			"title": "Giro Negocio"
		}, {
			"data": "tipoMensaje",
			"title": "Tipo Mensaje"
		}, {
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoMoneda, row.descripcionMoneda);
			},
			"title": "Moneda"
		}, {
			"data": "cantidadTransaccion",
			"render": $tablaFuncion.formatoMonto(0),
			"title": "Cantidad"
		}, {
			"data": "montoTransaccion",
			"render": $tablaFuncion.formatoMonto(),
			"title": "Importe"
		}],
		"order": [],
		"footerCallback": function(row, data, start, end, display) {
			var tieneData = $local.tablaResumenMovimiento == "" ? false : $local.tablaResumenMovimiento.data().any();
			var api = this.api(), data;
			if (tieneData) {
				montoTotal = api.column(12).data().reduce(function(a, b) {
					return parseFloat($funcionUtil.formatMoneyToNumber(a)) + parseFloat($funcionUtil.formatMoneyToNumber(b));
				}, 0);
				cantidadTotal = api.column(11).data().reduce(function(a, b) {
					return parseInt(a) + parseInt(b);
				}, 0);
				$(api.column(12).footer()).html(montoTotal.formatMoney(2));
				$(api.column(11).footer()).html(cantidadTotal.formatMoney(0));
			} else {
				$(api.column(12).footer()).html("");
				$(api.column(11).footer()).html("");
			}
		},
		"createdRow": function(row, data, dataIndex) {
			if (data.montoTransaccion > 0) {
				$(row).find(".monto").addClass("color-blue");
			} else if (data.montoTransaccion == 0) {
				$(row).find(".monto").addClass("color-inherit");
			} else {
				$(row).find(".monto").addClass("color-red");
			}
		}
	});

	$local.$tablaResumenMovimiento.wrap("<div class='table-responsive'></div>");

	$local.$tablaResumenMovimiento.find("thead").on("keyup", "input.filtrable", function() {
		$local.tablaResumenMovimiento.column($(this).parent().index() + ":visible").search(this.value).draw();
	});

	$local.$tablaResumenMovimiento.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaResumenMovimiento.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	$local.$buscar.on("click", function() {
		if (!$formCriterioBusquedaReporte.valid()) {
			return;
		}
		var criterioBusqueda = $formCriterioBusquedaReporte.serializeJSON();
		var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaBusqueda);
		criterioBusqueda.fechaInicio = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaFin = rangoFechaBusqueda.fechaFin;
		let paramCriterioBusqueda = $.param(criterioBusqueda);
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$clientes, "clientes");
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$rolesTransaccion, "rolesTransaccion");
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$procesos, "procesos");
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$canales, "canales");
		$.ajax({
			type: "GET",
			url: `${$variableUtil.root}reporte/resumen/movimiento/swDmpLog?accion=buscar&${paramCriterioBusqueda}`,
			contentType: "application/json",
            dataType: "json",
			beforeSend: function() {
				$local.tablaResumenMovimiento.clear().draw();
				$local.$buscar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success: function(reporteResumenSwDmpLog) {
				if (reporteResumenSwDmpLog.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				$local.tablaResumenMovimiento.rows.add(reporteResumenSwDmpLog).draw();
				$tablaFuncion.pintarMontosComisiones($local.$tablaResumenMovimiento, "tfoot td.monto");
			},
			complete: function() {
				$local.$buscar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$exportar.on("click", function() {
		if (!$formCriterioBusquedaReporte.valid()) {
			return;
		}
		let criterioBusqueda = $formCriterioBusquedaReporte.serializeJSON();
		let rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaBusqueda);
		criterioBusqueda.fechaInicio = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaFin = rangoFechaBusqueda.fechaFin;
		let descripcionRangoFechas = $local.$rangoFechaBusqueda.val();
		criterioBusqueda.descripcionRangoFechas = descripcionRangoFechas == "" || descripcionRangoFechas == undefined ? "TODOS" : descripcionRangoFechas;
		criterioBusqueda.descripcionInstitucion = $local.$instituciones.find('option:selected').text();
		criterioBusqueda.descripcionEmpresa = $local.$empresas.find("option:selected").val() === "-1" ? "TODOS" : $local.$empresas.find("option:selected").text();
		criterioBusqueda.descripcionCliente = !!$local.$clientes.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$clientes, "; ") : "TODOS";
		criterioBusqueda.descripcionRolTransaccion = !!$local.$rolesTransaccion.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$rolesTransaccion, "; ") : "TODOS";
		criterioBusqueda.descripcionCodigoProceso = !!$local.$procesos.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$procesos, "; ") : "TODOS";
		criterioBusqueda.descripcionIdCanal = !!$local.$canales.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$canales, "; ") : "TODOS";
		let paramCriterioBusqueda = $.param(criterioBusqueda);
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$clientes, "clientes");
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$rolesTransaccion, "rolesTransaccion");
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$procesos, "procesos");
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$canales, "canales");
		$funcionUtil.webSocketMethod('exportacionPOI');
		window.location.href = `${$variableUtil.root}reporte/resumen/movimiento/swDmpLog?rutaEnSidebar=${$variableUtil.rutaEnSidebar}&accion=exportar&${paramCriterioBusqueda}`;
	});
});