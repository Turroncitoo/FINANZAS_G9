$(document).ready(function() {
	
var $local = {
		$tablaMantenimiento : $("#tablaConsulta"),
		tablaMantenimiento : "",
		$filaSeleccionada : "",
		$membresiasFiltroParaTableMantenimiento : $("#membresias-filtroParaTablaMantenimiento"),
		filtrosSeleccionables : [],
		$tipoBusqueda : $("input[type=radio][name=tipoBusqueda]"),
		$tipoDocumento : $("#tipoDocumento"),
		$btnBuscarPorDocumentoCliente : $("#btnBuscarPorDocumentoCliente"),
		$selectTipoDocumento : $("#selectTipoDocumento"),
		$txtNumDocumentoCliente : $("#txtNumDocumentoCliente"),
		$canalFiltroParaTablaConsulta : $("#canalFiltroParaTablaConsulta"),
		$codigoProcesoFiltroParaTablaConsulta : $("#codigoProcesoFiltroParaTablaConsulta"),
		$buscarCriterios : $("#buscarCriterios"),
		$rangoFechasTransaccion : $("#rangoFechasTransaccion"),
		$empresaFiltroTablaConsulta: $('#empresaFiltroTablaConsulta'),
		$criterios : $("#criterios")
	};

	$formBusquedaCriterios = $("#formBusquedaCriterios");
	$formBusquedaTipoDocumento = $("#formParamIniciales");
	$funcionUtil.crearSelect2($local.$selectTipoDocumento, "Seleccione un Tipo de Documento");
	$funcionUtil.crearSelect2($local.$empresaFiltroTablaConsulta, "Seleccione Instituci\u00F3n");

	$.fn.dataTable.ext.errMode = 'none';
	$funcionUtil.crearSelect2($local.$canalFiltroParaTablaConsulta);
	$funcionUtil.crearSelect2($local.$codigoProcesoFiltroParaTablaConsulta);
	$funcionUtil.crearDateRangePickerConsulta($local.$rangoFechasTransaccion);

	$local.$tablaMantenimiento.on('xhr.dt', function(e, settings, json, xhr) {
		switch (xhr.status) {
		case 500:
			$local.tablaMantenimiento.clear().draw();
			break;
		}
	});

	$local.tablaMantenimiento = $local.$tablaMantenimiento.DataTable({
		"language" : {
			"emptyTable" : "No se han encontrado Transacciones Pendientes con los criterios definidos."
		},
		"initComplete" : function() {
			$local.$tablaMantenimiento.wrap("<div class='table-responsive'></div>");
			$local.filtrosSeleccionables["0"] = $local.$membresiasFiltroParaTableMantenimiento.html();
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaMantenimiento, $local.filtrosSeleccionables);
		},
		"columnDefs" : [ {
			"targets" : [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22],
			"className" : "all filtrable",
		}],
		"columns" : [ {
			"data" : 'tipoMensaje',
			"title" : "Mensaje"
		},{
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idInstitucion, row.descripcionInstitucion);
			},
			"title" : "Instituci\u00F3n"
		},{
			"data" : 'numeroTarjeta',
			"title" : "N° Tarjeta"
		},{
			"data" : 'codigoProcesamiento',
			"title" : "C\u00F3digo Procesamiento"
		},{
			"data" : 'fechaTransaccion',
			"title" : "Fecha Txn"
		},{
			"data" : 'horaTransaccion',
			"title" : "Hora Txn"
		},{
			"data" : 'descripcionOrigen',
			"title" : "Origen"
		},{
			"data" : 'traceTransaccion',
			"title" : "Trace"
		},{
			"data" : 'modoEntradaPos',
			"title" : "Modo Entrada Pos"
		},{
			"data" : 'fechaCaptura',
			"title" : "Fecha Captura"
		},{
			"data" : 'numeroReferencia',
			"title" : "N\u00FAmero Referencia"
		},{
			"data" : 'codigoRespuesta',
			"title" : "C\u00F3digo Respuesta"
		},{
			"data" : 'cuentaFrom',
			"title" : "Cuenta From"
		},{
			"data" : 'cuentaTo',
			"title" : "Cuenta To"
		},{
			"data" : 'monedaAutorizacion',
			"title" : "Moneda"
		},{
			"data" : 'valorAutorizacion',
			"title" : "Valor Autorizaci\u00F3n"
		},{
			"data" : 'fechaProceso',
			"title" : "Fecha Proceso"
		},{
			"data" : 'descripcionProceso',
			"title" : "Proceso"
		},{
			"data" : 'descripcionCanal',
			"title" : "Canal"
		},{
			"data" : 'numeroDocumentoLiberada',
			"title" : "N\u00FAmero Documento"
		},{
			"data" : 'nombreAfiliado',
			"title" : "Nombre Afiliado"
		},{
			"data" : 'conciliacionAutorizacion',
			"title" : "Conc. Autorizaci\u00F3n"
		},{
			"data" : 'estadoTarjeta',
			"title" : "Estado Tarjeta"
		}],
		"createdRow": function( row, data, dataIndex ) {
            if ( data.estadoTarjeta == "ACTIVA" ) {
                $( row ).css( "background-color", "Green" );
                $( row ).addClass( "success" );
            }else{
            	$( row ).css( "background-color", "Red" );
                $( row ).addClass( "danger" );
            }
        }
	});

	$local.$tablaMantenimiento.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaMantenimiento.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});
	
	$local.$tipoBusqueda.on("change", function(){
		var tipoBusqueda = $(this).val();
		switch (tipoBusqueda) {
		case "tipoDocumento":
			$local.$tipoDocumento.removeClass("hidden");
			$local.$criterios.addClass("hidden");
			break;
		case "criterios":
			$local.$tipoDocumento.addClass("hidden");
			$local.$criterios.removeClass("hidden");
			break;
		default:
			$funcionUtil.notificarException("Seleccione un Tipo de B\u00FAsqueda v\u00E1lido", "fa-warning", "Aviso", "warning");
		}		
	});
	
	$formBusquedaTipoDocumento.find("input").keypress(function(event) {
		if (!$formBusquedaTipoDocumento.valid()) {
			return;
		}
		if (event.which == 13) {
			$local.$btnBuscarPorDocumentoCliente.trigger("click");
			return false;
		}
	});
	
	$local.$btnBuscarPorDocumentoCliente.on("click",function(){
		if (!$formBusquedaTipoDocumento.valid()) {
			return;
		}
		var criterioBusqueda = $formBusquedaTipoDocumento.serializeJSON();
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "txnsPendientes?accion=buscarPorDocumento",
			data : criterioBusqueda,
			statusCode : {
				400 : function(response) {
					$funcionUtil.limpiarMensajesDeError($formBusquedaTipoDocumento);
					$funcionUtil.limpiarMensajesDeError($formBusquedaCriterios);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaCriterios);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaTipoDocumento);
				}
			},
			beforeSend : function() {
				$local.tablaMantenimiento.clear().draw();
				$local.$btnBuscarPorDocumentoCliente.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success : function(transaccionPendientes) {
				if (transaccionPendientes.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				$local.tablaMantenimiento.rows.add(transaccionPendientes).draw();
			},
			complete : function() {
				$local.$btnBuscarPorDocumentoCliente.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});
	
	$local.$buscarCriterios.on("click", function() {
		if (!$formBusquedaCriterios.valid()) {
			return;
		}
		if($funcionUtil.camposVacios($formBusquedaCriterios)){
			$funcionUtil.notificarException($variableUtil.camposVacios, "fa-exclamation-circle", "Informaci\u00F3n", "info");
		}else{
			var criterioBusqueda = $formBusquedaCriterios.serializeJSON();
			var rangoFechaTxn = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechasTransaccion);
			criterioBusqueda.fechaInicioTransaccion = rangoFechaTxn.fechaInicio;
			criterioBusqueda.fechaFinTransaccion = rangoFechaTxn.fechaFin;
			$.ajax({
				type : "GET",
				url : $variableUtil.root + "txnsPendientes?accion=buscarPorFiltro",
				data : criterioBusqueda,
				statusCode : {
					400 : function(response) {
						$funcionUtil.limpiarMensajesDeError($formBusquedaTipoDocumento);
						$funcionUtil.limpiarMensajesDeError($formBusquedaCriterios);
						$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaCriterios);
						$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaTipoDocumento);
					}
				},
				beforeSend : function() {
					$local.tablaMantenimiento.clear().draw();
					$local.$buscarCriterios.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
				},
				success : function(transaccionPendientes) {
					if (transaccionPendientes.length == 0) {
						$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
						return;
					}
					$local.tablaMantenimiento.rows.add(transaccionPendientes).draw();
				},
				complete : function() {
					$local.$buscarCriterios.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
				}
			});
		}
	});
	
});