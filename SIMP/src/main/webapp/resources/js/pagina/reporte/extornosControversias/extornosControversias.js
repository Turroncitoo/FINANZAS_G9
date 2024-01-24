$(document).ready(function(){
	var $local = {
		$selectRol: $("#selectRolesTransaccion"),
		$buscar: $("#btnBuscar"),
		$exportar: $("#btnExportar"),
		$tablaExtornosControversias: $("#tablaExtornosControversias"),
		tablaExtornosControversias: "",
		$fechaProceso: $('#fechaProceso'),
		$instituciones: $('#institucionesExtControversias'),
		$empresas: $('#empresasExtControversias'),
		$clientes: $('#clientesExtControversias'),
		$selectCodigoRespuesta: $("#selectCodigoRespuestaExtControversias")
	};
	
	$formCriterios = $("#formCriterios");
	
	$funcionUtil.crearDateRangePickerConsulta($local.$fechaProceso, "right");
	$funcionUtil.crearSelect2($local.$instituciones);
	$funcionUtil.crearSelect2($local.$empresas, "Seleccione una Empresa");
	$funcionUtil.crearSelect2Multiple($local.$clientes, "-1", "TODOS");
	$funcionUtil.crearSelect2Multiple($local.$selectRol, "-1", "TODOS");
	$funcionUtil.crearSelect2Multiple($local.$selectCodigoRespuesta, "-1", "TODOS");
	
	$.fn.dataTable.ext.errMode = 'none';

	$local.$tablaExtornosControversias.on('xhr.dt', function(e, settings, json, xhr) {
	});

	// $.fn.dataTable.moment('DD/MM/YYYY');

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
	
	$local.tablaExtornosControversias = $local.$tablaExtornosControversias.DataTable({
		"language": {
			"emptyTable": "No se han encontrado extornos/controversias con los criterios definidos."
		},
		"initComplete": function() {
			$local.$tablaExtornosControversias.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaExtornosControversias)
		},
		"columnDefs": [{
			"targets": [ 0, 16 ],
			"className": "all filtrable dt-center"
		}, {
			"targets": [  1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 19, 20, 21, 22, 23, 24, 25, 26 ],
			"className": "all filtrable"
		}, {
			"targets": 18,
			"className": "all filtrable dt-right monto"
		}],
		"columns": [{
			"data": 'fechaProceso',
			"title": "Fecha Proceso"
		}, {
			"data": function(row){
				return $funcionUtil.unirCodigoDescripcion(row.codigoInstitucion, row.descInstitucion);
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
			"data":  function(row){
				return $funcionUtil.unirCodigoDescripcion(row.codigoRolTransaccion, row.descripcionRol);
			},
			"title": "Rol Transacci\u00F3n"
		}, {
			"data": function(row){
				return $funcionUtil.unirCodigoDescripcion(row.codigoMembresia, row.descripcionMembresia);
			} ,
			"title": "Membres\u00EDa"
		}, {
			"data": function(row){
				return $funcionUtil.unirCodigoDescripcion(row.codigoClaseServicio, row.descripcionServicio);
			} ,
			"title": "Servicio"
		}, {
			"data": function(row){
				return $funcionUtil.unirCodigoDescripcion(row.codigoOrigen, row.descripcionOrigen);
			},
			"title": "Origen"
		}, {
			"data": function(row){
				return $funcionUtil.unirCodigoDescripcion(row.idClaseTransaccion, row.descripcionClaseTransaccion);
			} ,
			"title": "Clase Transacci\u00F3n"
		}, {
			"data": function(row){
				return $funcionUtil.unirCodigoDescripcion(row.idCodigoTransaccion, row.descripcionCodigoTransaccion);
			} ,
			"title": "C\u00F3digo Transacci\u00F3n"
		}, {
			"data": function(row){
				return $funcionUtil.unirCodigoDescripcion(row.codigoRespuesta, row.descripcionRespuesta);
			} ,
			"title": "C\u00F3digo Respuesta"
		}, {
			"data": function(row){
				return $funcionUtil.unirCodigoDescripcion(row.codigoInstitucionEmisora, row.descripcionEmisor);
			} ,
			"title": "Inst. Emisora"
		}, {
			"data": function(row){
				return $funcionUtil.unirCodigoDescripcion(row.codigoInstitucionReceptora, row.descripcionReceptor);
			},
			"title": "Inst. Receptora"
		}, {
			"data": "secuenciaTransaccion",
			"title": "Secuencia"
		}, {
			"data": 'numeroTarjeta',
			"title": "N\u00famero Tarjeta"
		},  {
			"data": 'numeroCuenta',
			"title": "N\u00famero Cuenta"
		}, {
			"data": function(row){
				return $funcionUtil.unirCodigoDescripcion(row.fechaTransaccionCadena, row.horaTransaccion);
			},
			"title": "Fecha Transacci\u00F3n"
		},  {
			"data": function(row){
				return $funcionUtil.unirCodigoDescripcion(row.codigoMoneda, row.descripcionMoneda);
			},
			"title": "Moneda"
		}, {
			"data": 'valorCompensacion',
			"render": $tablaFuncion.formatoMonto(2),
			"title": "Importe"
		}, {
			"data": 'referenciaIntercambio',
			"title": "Referencia Intercambio"
		}, {
			"data": 'nombreAfiliado',
			"title": "Nombre Afiliado"
		}, {
			"data": 'ciudadAfiliado',
			"title": "Ciudad Afiliado"
		}, {
			"data": function(row){
				return $funcionUtil.unirCodigoDescripcion(row.fondosCargo, row.descFondosCargo);
			},
			"title": "Fondo Cargo"
		}, {
			"data": function(row){
				return $funcionUtil.unirCodigoDescripcion(row.fondosAbono, row.descFondosAbono);
			},
			"title": "Fondo Abono"
		}, {
			"data": function(row){
				return $funcionUtil.unirCodigoDescripcion(row.contadorFondos, row.descripcionEstadoContable);
			},
			"title": "Estado Contable"
		}, {
			"data": 'cuentaCargo',
			"title": "Cta. Cargo"
		}, {
			"data": 'cuentaAbono',
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
		}
	});

	$local.$tablaExtornosControversias.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaExtornosControversias.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaExtornosControversias.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaExtornosControversias.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	$formCriterios.find("input").keypress(function(event) {
		if (event.which == 13) {
			$local.$buscar.trigger("click");
			return false;
		}
	});

	$local.$buscar.on("click", function() {
		if (!$formCriterios.valid()) {
			return;
		}
		var criterioBusqueda = $formCriterios.serializeJSON();
		var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$fechaProceso);
		criterioBusqueda.fechaInicioProceso = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaFinProceso = rangoFechaBusqueda.fechaFin;
		criterioBusqueda.idCliente = $funcionUtil.limpiarCadenaPeticionGetDeSelect2MultiplePaginacion($funcionUtil.crearCadenaPeticionGetDeSelect2MultiplePaginacion($local.$clientes));
		criterioBusqueda.codigoRolTransaccion = $funcionUtil.limpiarCadenaPeticionGetDeSelect2MultiplePaginacion($funcionUtil.crearCadenaPeticionGetDeSelect2MultiplePaginacion($local.$selectRol));
		criterioBusqueda.codigoRespuesta = $funcionUtil.limpiarCadenaPeticionGetDeSelect2MultiplePaginacion($funcionUtil.crearCadenaPeticionGetDeSelect2MultiplePaginacion($local.$selectCodigoRespuesta));
		$.ajax({
			type: "GET",
			url: `${$variableUtil.root}reporte/extornosControversias?accion=buscar`,
			data: criterioBusqueda,
			beforeSend: function() {
				$local.tablaExtornosControversias.clear().draw();
				$local.$buscar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success: function(response) {
				if (response.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				$local.tablaExtornosControversias.rows.add(response).draw();
			},
			complete: function() {
				$local.$buscar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});
	
	$local.$exportar.on("click", function() {
		if (!$formCriterios.valid()) {
			return;
		}
		var criterioBusqueda = $formCriterios.serializeJSON();
		var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$fechaProceso);
		criterioBusqueda.fechaInicioProceso = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaFinProceso = rangoFechaBusqueda.fechaFin;	
		criterioBusqueda.descripcionRangoFechas = $local.$fechaProceso.val() || "TODOS";
		criterioBusqueda.descripcionInstitucion = $local.$instituciones.find('option:selected').text();
		criterioBusqueda.descripcionEmpresa = $local.$empresas.find("option:selected").val() === "-1" ? "TODOS" : $local.$empresas.find("option:selected").text();
		
		criterioBusqueda.idCliente = $funcionUtil.limpiarCadenaPeticionGetDeSelect2MultiplePaginacion($funcionUtil.crearCadenaPeticionGetDeSelect2MultiplePaginacion($local.$clientes));
		criterioBusqueda.descripcionCliente = !!$local.$clientes.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$clientes, "; ") : "TODOS";
		criterioBusqueda.codigoRolTransaccion = $funcionUtil.limpiarCadenaPeticionGetDeSelect2MultiplePaginacion($funcionUtil.crearCadenaPeticionGetDeSelect2MultiplePaginacion($local.$selectRol));
		criterioBusqueda.descripcionRolTransaccion = !!$local.$selectRol.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$selectRol, "; ") : "TODOS";
		criterioBusqueda.codigoRespuesta = $funcionUtil.limpiarCadenaPeticionGetDeSelect2MultiplePaginacion($funcionUtil.crearCadenaPeticionGetDeSelect2MultiplePaginacion($local.$selectCodigoRespuesta));
		criterioBusqueda.descripcionCodigoRespuesta = !!$local.$selectCodigoRespuesta.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$selectCodigoRespuesta, "; ") : "TODOS";
		let paramCriterioBusqueda = $.param(criterioBusqueda);
		$funcionUtil.webSocketMethod('exportacionPOI');
		window.location.href = `${$variableUtil.root}reporte/extornosControversias?rutaEnSidebar=${$variableUtil.rutaEnSidebar}&accion=exportar&${paramCriterioBusqueda}`;
	});
});