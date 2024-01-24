$(document).ready(function() {

	var $local = {
		$buscar: $("#buscar"),
		$indicadorContabilizacion: $(".indicarContabilizacion"),
		$exportar: $('#exportar'),
		$tablaContabilizacion: $("#tablaContabilizacion"),
		tablaContabilizacion: "",
		encabezadoComisionesArreglo: [],
		
		// Filtros de b\u00FAsqueda
		$rangoFechaBusqueda: $("#rangoFechaBusquedaComisiones"),
		$instituciones: $("#selectInstitucionComisiones"),
		$empresas: $("#selectEmpresaComisiones"),
		$clientes: $("#selectClienteComisiones"),
		$indicadores: $("#selectIndicadoresComisiones")
	};
	
	$funcionUtil.crearDateRangePickerSimple($local.$rangoFechaBusqueda,"right");
	$funcionUtil.crearSelect2($local.$instituciones);
	$funcionUtil.crearSelect2($local.$empresas, "Seleccione una Empresa");
    $funcionUtil.crearSelect2Multiple($local.$clientes, "-1", "TODOS");
	$funcionUtil.crearSelect2Multiple($local.$indicadores, "-1", "TODOS");
	
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

	$local.$encabezados = $local.$tablaContabilizacion.find("th.conceptoComision");
	
	for (var i = 0; i < $local.$encabezados.length; i++) {
		var conceptoComision = $local.$encabezados.filter("[name=" + i + "]").attr("idConceptoComision");
		$local.encabezadoComisionesArreglo.push(conceptoComision);
	}

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

	$local.tablaContabilizacion = $local.$tablaContabilizacion.DataTable({
		"initComplete": function() {
			$local.$tablaContabilizacion.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaContabilizacion);
		},
		"language": {
			"emptyTable": "No se encontraron transacciones"
		},
		"columnDefs": [{
			"targets": 0, 
			"className": "all filtrable dt-center",
		}, {
			"targets": '_all', 
			"className": "all filtrable",
		}],		
		"footerCallback": function(row, data, start, end, display) {
			var tieneData = $local.tablaContabilizacion == "" ? false : $local.tablaContabilizacion.data().any();
			var api = this.api(), data;
			api.columns(".comision").every(function() {
				var index = this.selector.cols;
				if (tieneData) {
					var suma = this.data().reduce(function(a, b) {
						return parseFloat($funcionUtil.formatMoneyToNumber(a)) + parseFloat($funcionUtil.formatMoneyToNumber(b));
					}, 0);
					$(api.column(index).footer()).html(suma.formatMoney(4));
				} else {
					$(api.column(index).footer()).html("");
				}
			});
			if (tieneData) {
				cantidadTotal = api.column(16).data().reduce(function(a, b) {
					return parseInt($funcionUtil.formatMoneyToNumber(a)) + parseInt($funcionUtil.formatMoneyToNumber(b));
				}, 0);
				$(api.column(16).footer()).html(cantidadTotal.formatMoney(0));
			} else {
				$(api.column(16).footer()).html("");
			}
		},
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
		},
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
		criterioBusqueda.fechaFinProceso = rangoFechaBusqueda.fechaFin;
		let paramCriterioBusqueda = $.param(criterioBusqueda);
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$clientes, "clientes");
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$indicadores, "indicadoresContabilizacion");
		$.ajax({
			type: "GET",
			url: `${$variableUtil.root}reporte/contabilizacion/comisiones?accion=buscar&${paramCriterioBusqueda}`,
			contentType: "application/json",
			dataType: "json",
			beforeSend: function() {
				$local.tablaContabilizacion.clear().draw();
				$local.$buscar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success: function(reporteContabilizacionMovimento) {
				if (reporteContabilizacionMovimento.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				var filas = new Array();
				var j = -1;
				var finInicioCelda = "</td><td>";
				$.each(reporteContabilizacionMovimento, function(i, reporte) {
					filas[++j] = "<tr><td>";
					filas[++j] = reporte.fechaProceso;
					filas[++j] = finInicioCelda;
					filas[++j] = $funcionUtil.unirCodigoDescripcion(reporte.codigoInstitucion, reporte.descripcionInstitucion);
					filas[++j] = finInicioCelda;
					filas[++j] = $funcionUtil.unirCodigoDescripcion(reporte.idEmpresa, reporte.descripcionEmpresa);
					filas[++j] = finInicioCelda;
					filas[++j] = $funcionUtil.unirCodigoDescripcion(reporte.idCliente, reporte.descripcionCliente);
					filas[++j] = finInicioCelda;
					filas[++j] = $funcionUtil.unirCodigoDescripcion(reporte.idLogo, reporte.descLogoBin);
					filas[++j] = finInicioCelda;
					filas[++j] = $funcionUtil.unirCodigoDescripcion(reporte.codigoProducto, reporte.descProducto);
					filas[++j] = finInicioCelda;
					filas[++j] = $funcionUtil.unirCodigoDescripcion(reporte.codigoMembresia, reporte.descripcionMembresia);
					filas[++j] = finInicioCelda;
					filas[++j] = $funcionUtil.unirCodigoDescripcion(reporte.codigoClaseServicio, reporte.descripcionClaseServicio);
					filas[++j] = finInicioCelda;
					filas[++j] = $funcionUtil.unirCodigoDescripcion(reporte.codigoOrigen, reporte.descripcionOrigen); 
					filas[++j] = finInicioCelda;
					filas[++j] = $funcionUtil.unirCodigoDescripcion(reporte.codigoClaseTransaccion, reporte.descripcionClaseTransaccion);
					filas[++j] = finInicioCelda;
					filas[++j] = $funcionUtil.unirCodigoDescripcion(reporte.codigoTransaccion, reporte.descripcionCodigoTransaccion);
					filas[++j] = finInicioCelda;
					filas[++j] = $funcionUtil.unirCodigoDescripcion(reporte.indicadorContabilizacion, reporte.descripcionIndicadorContabilizacion)
					filas[++j] = finInicioCelda;
					filas[++j] = reporte.cuentaCargo
					filas[++j] = finInicioCelda;
					filas[++j] = reporte.cuentaAbono;
					filas[++j] = finInicioCelda;
					filas[++j] = reporte.codigoAnalitico;
					filas[++j] = finInicioCelda;
					filas[++j] = $funcionUtil.unirCodigoDescripcion(reporte.codigoMonedaCompensacion, reporte.descripcionMonedaCompensacion);
					filas[++j] = "</td><td class='dt-right'>";
					filas[++j] = reporte.cantidad.formatMoney(0);
					filas[++j] = "</td><td class='dt-right monto comision'>";
					var k = -1;
					var subTotal = 0;
					for (var k = 0; k < $local.encabezadoComisionesArreglo.length; k++) {
						var montoComision = 0;
						var idConceptoComision = $local.encabezadoComisionesArreglo[k];
						$.each(reporte.comisiones, function(k, comision) {
							if (comision.idConceptoComision == idConceptoComision) {
								montoComision = comision.comision || 0;
								var signo = "";
								if (montoComision != 0) {
									signo = comision.registroContable == "C" ? "-" : "";
								}
								montoComision = parseFloat(signo + montoComision.formatMoney(4));
								return false;
							}
						});
						filas[++j] = montoComision.formatMoney(4);
						filas[++j] = "</td><td class='dt-right monto comision'>";
						subTotal = parseFloat(subTotal) + parseFloat(montoComision);
					}
					filas[++j] = parseFloat(subTotal).formatMoney(4);
					filas[++j] = "</td></tr>";
				});
				$local.tablaContabilizacion.rows.add($(filas.join(''))).draw();
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
		window.location.href = `${$variableUtil.root}reporte/contabilizacion/comisiones?rutaEnSidebar=${$variableUtil.rutaEnSidebar}&accion=exportar&${paramCriterioBusqueda}`;
	});
});