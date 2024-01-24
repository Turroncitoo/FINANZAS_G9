$(document).ready(function() {

	var $local = {
		$buscar: $("#buscar"),
		tablaContabilizacionEnCuentaDefault: "",
		$tablaContabilizacionEnCuentaDefault: $("#tablaContabilizacionEnCuentaDefault"),
		$exportar: $('#exportar'),

		// Filtros de b\u00FAsqueda
		$rangoFechaBusqueda: $("#rangoFechaBusquedaCuentaDefault"),
		$instituciones: $('#selectInstitucionCuentaDefault'),
		$empresas: $("#selectEmpresaCuentaDefault"),
		$clientes: $("#selectClienteCuentaDefault")
	}
	
	$funcionUtil.crearDateRangePickerSimple($local.$rangoFechaBusqueda, "right");
	$funcionUtil.crearSelect2($local.$instituciones);
    $funcionUtil.crearSelect2($local.$empresas, "Seleccione una Empresa");
    $funcionUtil.crearSelect2Multiple($local.$clientes, "-1", "TODOS");
	
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

	$local.tablaContabilizacionEnCuentaDefault = $local.$tablaContabilizacionEnCuentaDefault.DataTable({
		"initComplete": function() {
			$local.$tablaContabilizacionEnCuentaDefault.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaContabilizacionEnCuentaDefault);
		},
		"language": {
			"emptyTable": "No se encontraron comisiones."
		},
		"columnDefs": [{
			"targets": 0,
			"className": "all filtrable dt-center"
		}, {
			"targets": [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22 ],
			"className": "all filtrable"
		}, {
			"targets": 23,
			"className": "all filtrable dt-right monto"
		}],
		"columns": [{
			"data": "fechaProceso",
			"title": "Fecha Proceso"
		}, {
			"data": function(row){
				return $funcionUtil.unirCodigoDescripcion(row.codigoInstitucion, row.descripcionInstitucion);
			},
			"title": "Instituci\u00F3n"
		}, {
			"data": function(row){
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
				return $funcionUtil.unirCodigoDescripcion(row.idLogo, row.descLogoBin);
			},
			"title": "Logo"
		}, {
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoProducto, row.descProducto);
			},
			"title": "Producto"
		}, {
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idRolTransaccion, row.descripcionRolTransaccion);
			},
			"title": "Rol Transacci\u00f3n"
		}, {
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoMembresia, row.descripcionMembresia);
			},
			"title": "Membres\u00EDa"
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
			"data": "secuenciaTransaccion",
			"title": "Secuencia"
		}, {
			"data": "numeroTarjeta",
			"title": "N\u00FAmero Tarjeta"
		}, {
			"data": "numeroCuenta",
			"title": "N\u00FAmero Cuenta"
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
				return $funcionUtil.unirCodigoDescripcion(row.tipoTransaccion, row.descripcionTransaccion);
			},
			"title": "Tipo Transacci\u00F3n"
		}, {
			"data": "cuentaCargo",
			"title": "Cuenta Cargo"
		}, {
			"data": "cuentaAbono",
			"title": "Cuenta Abono"
		}, {
			"data": function(row) {
				var conceptoComision = "";
				if (row.idConceptoComision != "") {
					conceptoComision = $funcionUtil.unirCodigoDescripcion(row.idConceptoComision, row.descripcionConceptoComision);
				}
				return conceptoComision;
			},
			"title": "Concepto Comisi\u00f3n"
		}, {
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.registroContable, row.descripcionRegistroContable);
			},
			"title": "Registro Contable"
		}, {
			"data": function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoMoneda, row.descripcionMoneda);
			},
			"title": "Moneda"
		}, {
			"data": function(row) {
				var signo = (row.registroContable == "C") ? -1 : 1;
				var decimales = (row.tipoTransaccion == "F") ? 2 : 4;
				return (parseFloat(row.montoOComis) * signo).formatMoney(decimales);
			},
			"title": "Monto / Comisi\u00f3n",
		}],
		"order": [],
		"createdRow" : function(row, data, dataIndex) {
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
		}
	});

	$local.$tablaContabilizacionEnCuentaDefault.wrap("<div class='table-responsive'></div>");

	$local.$tablaContabilizacionEnCuentaDefault.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaContabilizacionEnCuentaDefault.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaContabilizacionEnCuentaDefault.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaContabilizacionEnCuentaDefault.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
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
		$.ajax({
			type: "GET",
			url: `${$variableUtil.root}reporte/contabilizacion/cuentaDefault?accion=buscar&${paramCriterioBusqueda}`,
			contentType: "application/json",
			dataType: "json",
			beforeSend: function() {
				$local.tablaContabilizacionEnCuentaDefault.clear().draw();
				$local.$buscar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success: function(reporteContabilizacionEnCuentaDefaults) {
				if (reporteContabilizacionEnCuentaDefaults.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00f3n", "info");
					return;
				}
				$local.tablaContabilizacionEnCuentaDefault.rows.add(reporteContabilizacionEnCuentaDefaults).draw();
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
		let criterioBusqueda = $formCriterioBusquedaReporte.serializeJSON();
		let rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaBusqueda);
		criterioBusqueda.fechaInicioProceso = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaFinProceso = rangoFechaBusqueda.fechaFin;
		let descripcionRangoFechas = $local.$rangoFechaBusqueda.val();
		criterioBusqueda.descripcionFechaProceso = descripcionRangoFechas == "" || descripcionRangoFechas == undefined ? "TODOS" : descripcionRangoFechas;
		criterioBusqueda.descripcionInstitucion = $local.$instituciones.find('option:selected').text();
		criterioBusqueda.descripcionEmpresa = $local.$empresas.find("option:selected").val() === "-1" ? "TODOS" : $local.$empresas.find("option:selected").text();
		criterioBusqueda.descripcionCliente = !!$local.$clientes.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$clientes, "; ") : "TODOS";
		let paramCriterioBusqueda = $.param(criterioBusqueda);
		paramCriterioBusqueda += $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$clientes, "clientes");
		$funcionUtil.webSocketMethod('exportacionPOI');
		window.location.href = `${$variableUtil.root}reporte/contabilizacion/cuentaDefault?rutaEnSidebar=${$variableUtil.rutaEnSidebar}&accion=exportar&${paramCriterioBusqueda}`;
	});
});