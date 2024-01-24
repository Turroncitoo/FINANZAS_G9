$(document).ready(function() {

	var $local = {
		$empresas : $("#empresas"),
		$rangoFechaBusqueda : $("#rangoFechaBusqueda"),
		$buscar : $("#buscar"),
		$tablaReporteContable : $("#tablaReporteContable"),
		tablaReporteContable : "",
		$resultadoBusquedaReporteContablePrepago : $("#resultadoBusquedaReporteContable"),
		$exportarXlsx : $("#exportar")
	};

	$funcionUtil.crearSelect2($local.$empresas);
	$funcionUtil.crearDateRangePickerConsulta($local.$rangoFechaBusqueda);

	$formCriterioBusquedaReporte = $("#formCriterioBusquedaReporte");

	$.fn.dataTable.ext.errMode = 'none';

	$local.$tablaReporteContable.on('xhr.dt', function(e, settings, json, xhr) {
		switch (xhr.status) {
		case 500:
			$local.tablaReporteContable.clear().draw();
			$funcionUtil.notificarException(xhr.responseText, "Error Interno", "danger");
			break;
		}
	});

	$local.tablaReporteContable = $local.$tablaReporteContable.DataTable({
		"language" : {
			"emptyTable" : "No hay transacciones encontradas"
		},
		"initComplete" : function() {
			$local.$tablaReporteContable.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaReporteContable);
		},
		"columnDefs" : [{
			"targets" : [ 0, 1, 2, 3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21 ],
			"className" : "all filtrable",
		}, {
			"targets" : 15,
			"className" : "all dt-right filtrable"
		}, {
			"targets" : 16,
			"className" : "all dt-right monto filtrable"
		} ],
		"columns" : [ {
			"data" : 'fechaTransaccion',
			"title" : "Fecha de Transacci\u00F3n"
		}, {
			"data" : 'horaTransaccion',
			"title" : "Hora Transaccion"
		}, {
			"data" : 'fechaProceso',
			"title" : "Fecha de Proceso"
		}, {
			"data" : 'fechaProceso',
			"title" : "Fecha de Afectaci\u00F3n"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idCliente, row.descripcionCliente);
			},
			"title" : "Cliente"
		}, {
			"data" : 'numeroTarjeta',
			"title" : "N\u00FAmero Tarjeta"
		}, {
			"data" : 'idPersona',
			"title" : "Id Persona"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.tipoDocumento, row.descripcionTipoDocumento);
			},
			"title" : "Tipo Documento"
		}, {
			"data" : 'numeroDocumento',
			"title" : "N\u00FAmero Documento"
		}, {
			"data" : 'nombres',
			"title" : "Nombres"
		}, {
			"data" : 'apellidoPaterno',
			"title" : "Apellido Paterno"
		}, {
			"data" : 'apellidoMaterno',
			"title" : "Apellido Materno"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idBIN, row.descripcionBIN);
			},
			"title" : "BIN"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idSubBIN, row.descripcionSubBIN);
			},
			"title" : "SubBIN"
		}, {
			"data" : 'nombreAfiliado',
			"title" : "Adquirente"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.transaccion, row.descripcionTransaccion);
			},
			"title" : "Transaccion"
		}, {
			"data" : 'monedaCompensacion',
			"title" : "Moneda Comp"
		}, {
			"data" : 'valorCompensacion',
			"title" : "Valor Comp"
		}, {
			"data" : 'numeroTrace',
			"title" : "N\u00FAmero Trace"
		}, {
			"data" : 'codigoRpta',
			"title" : "C\u00F3digo Respuesta"
		}, {
			"data" : 'codigoAutorizacion',
			"title" : "C\u00F3digo Autorizaci\u00F3n"
		}, {
			"data" : 'cuentaCargo',
			"title" : "Cuenta Cargo"
		}, {
			"data" : 'cuentaAbono',
			"title" : "Cuenta Abono"
		}, {
			"data" : 'codigoAnalitico',
			"title" : "C\u00F3digo Anal\u00EDtico"
		}
		
		]
	});

	$local.$buscar.on("click", function() {
		var criterioBusqueda = $formCriterioBusquedaReporte.serializeJSON();
		var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaBusqueda);
		criterioBusqueda.fechaInicio = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaFin = rangoFechaBusqueda.fechaFin;
		criterioBusqueda.tipo = 'LOGC'
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "/reporte/prepago/contable/anexo?accion=buscar",
			data : criterioBusqueda,
			beforeSend : function() {
				$local.$buscar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success : function(reporteContablePrepago) {
				$local.tablaReporteContable.clear().draw();
				if (reporteContablePrepago.length == 0) {
					$funcionUtil.notificarException("No se encontraron transacciones.", "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				$local.tablaReporteContable.rows.add(reporteContablePrepago).draw();
			},
			complete : function() {
				$local.$buscar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$exportarXlsx.on("click", function() {
		var criterioBusqueda = $formCriterioBusquedaReporte.serializeJSON();
		var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaBusqueda);
		criterioBusqueda.fechaInicio = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaFin = rangoFechaBusqueda.fechaFin;
		//criterioBusqueda.fechaProceso = rangoFechaBusqueda.fechaFin;
		criterioBusqueda.descripcionInstitucion = $local.$empresas.find("option:selected").text();
		var descripcionRangoFechas = $local.$rangoFechaBusqueda.val();
		criterioBusqueda.descripcionRangoFechas = descripcionRangoFechas == "" || descripcionRangoFechas == undefined ? "TODOS" : descripcionRangoFechas;
		criterioBusqueda.tipo = "LOGC";

		var paramCriterioBusqueda = $.param(criterioBusqueda);
		window.location.href = $variableUtil.root + "reporte/prepago/contabilidad/anexo1?accion=exportar&" + paramCriterioBusqueda;
	});
});