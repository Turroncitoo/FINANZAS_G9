//JQUERY
/*
 * 
 * Seleccion por ID
 * elemento id=buscar
 * $('#buscar')
 * 
 * Seleccion por clase
 * elemento class="row"
 * $('.row') 
 * 
 * Seleccion por etiqueta
 * $('button')
 * 
 */

$(document).ready(function() {
	
	var $local = {
		//Criterios busqueda
		$selectPeriodo: $('#selectPeriodo'),
		$fechaTransaccion : $('#fechaTransaccion'),
		
		$divFechaTransaccion: $('#divFechaTransaccion'),
		$divMesTransaccion: $('#divMesTransaccion'),
		//Botones
		$btnBuscar : $('#buscar'),
		$btnExportar : $('#exportar'),
		
		//Tabla
		$tablaResultados : $('#tablaResultados'),
		tablaResultados: ''
		
	};

	$funcionUtil.crearSelect2($local.$selectPeriodo);
	$funcionUtil.crearDateRangePickerConsulta($local.$fechaTransaccion);
	
	/*Evento para que cambie los inputs en hidden*/
	$local.$selectPeriodo.change(function() {
		let tipoPeriodo = $(this).val();
		switch(tipoPeriodo){
		case 'Por dia':
			$local.$divFechaTransaccion.removeClass('hidden');
			$local.$divMesTransaccion.addClass('hidden');
			break;
		case 'Por mes':
			$local.$divFechaTransaccion.addClass('hidden');
			$local.$divMesTransaccion.removeClass('hidden');
			break;
		}
	});
	
	$local.tablaResultados = $local.$tablaResultados.DataTable({
		"language" : {
			"emptyTable" : "No hay registros encontrados."
		},
		"initComplete" : function() {
			$local.$tablaResultados.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaResultados);
		},
		"columnDefs" : [ {
			"targets" : [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 23, 24, 25, 28],
			"className" : "all filtrable",
		} , {
			"targets" : 26,
			"className" : "all filtrable dt-right monto",
		}, {
			"targets" : 27,
			"className" : "all filtrable dt-right monto",
		}, {
			"targets" : 29,
			"className" : "all filtrable dt-right monto",
		}, {
			"targets" : 30,
			"className" : "all filtrable dt-right monto",
		}, {
			"targets" : 21,
			"className" : "all filtrable dt-right ",
		}, {
			"targets" : 22,
			"className" : "all filtrable dt-center",
		}],
		"columns" : [ 
		{
			"data" : "fechaProceso",
			"title" : "Fecha Proceso"
		}, {
			"data" : "fechaAfectacion",
			"title" : "Fecha Afectaci\u00F3n"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.rolTransaccion, row.descripcionRol);
			},
			"title" : "Rol Txn"
		}, {
			"data" : "numeroMovimiento",
			"title" : "N° Movimiento"
		}, {
			"data" : "numeroSecuencia",
			"title" : "N° Secuencia"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idMembresia, row.descripcionMembresia);
			},
			"title" : "Membres\u00EDa"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idClaseServicio, row.descripcionServicio);
			},
			"title" : "Clase Servicio"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.institucionEmisora, row.descripcionCortaInstitucionEmisora);
			},
			"title" : "Instituci\u00F3n Emisora"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.institucionReceptora, row.descripcionCortaInstitucionReceptora);
			},
			"title" : "Instituci\u00F3n Receptora"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idOrigen, row.descripcionOrigen);
			},
			"title" : "Origen"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.transaccion, row.descripcionTransaccion);
			},
			"title" : "Transacci\u00F3n"
		}, {
			"data" : "nombreAfiliado",
			"title" : "Nombre Afiliado"
		}, {
			"data" : "numeroTarjeta",
			"title" : "N° Tarjeta"
		}, {
			"data" : "numeroCuenta",
			"title" : "N° Cuenta"
		},  {
			"data" : "numeroTrace",
			"title" : "Trace"
		}, {
			"data" : "fechaTransaccion",
			"title" : "Fecha Txn"
		}, {
			"data" : "horaTransaccion",
			"title" : "Hora Txn"
		}, {
			"data" : "codigoATM",
			"title" : "ATM"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.tipoTransaccion, row.descripcionTransaccionContable);
			},
			"title" : "Tipo Txn Contable"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.tipoComision, row.descripcionComision);
			},
			"title" : "Tipo Comisi\u00F3n"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.monedaTransaccion, row.descripcionMonedaTransaccion);
			},
			"title" : "Moneda Txn"
		}, {
			"data": function(row){
				return row.valorTransaccion ? row.valorTransaccion.toFixed(4) : '';
			},
			"title" : "Valor Txn"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.registroContable, row.descripcionContable);
			},
			"title" : "Registro Contable"
		}, {
			"data" : "cuentaCargo",
			"title" : "Cuenta Cargo"
		}, {
			"data" : "cuentaAbono",
			"title" : "Cuenta Abono"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.monedaCompensacion, row.descripcionMonedaCompensacion);
			},
			"title" : "Moneda Comp"
		}, {
			"data": function(row){
				return row.valorCompensacionCargo ? row.valorCompensacionCargo.toFixed(4) : '';
			}, 
			"title" : "Valor Comp Cargo"
		}, {
			"data": function(row){
				return row.valorCompensacionAbono ? row.valorCompensacionAbono.toFixed(4) : '';
			},
			"title" : "Valor Comp Abono"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.monedaContable, row.descripcionMonedaContable);
			},
			"title" : "Moneda Contable"
		}, {
			"data": function(row){
				return row.valorContableCargo ? row.valorContableCargo.toFixed(2) : '';
			},
			"title" : "Valor Cont Cargo"
		}, {
			"data": function(row){
				return row.valorContableAbono ? row.valorContableAbono.toFixed(2) : '';
			},
			"title" : "Valor Cont Abono"
		} ],
		"sorter" : [ {
			0 : "asc"
		} ],
	});
	
	$local.$btnBuscar.on('click', function() {
		var criterioBusqueda = {};
		var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$fechaTransaccion);
		criterioBusqueda.fechaInicioTransaccion = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaTransaccion = rangoFechaBusqueda.fechaFin;
		var paramCriterioBusqueda = $.param(criterioBusqueda);
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "reporte/conciliacion/resumenDiario?accion=buscar&"+paramCriterioBusqueda,
			contentType : "application/json",
			dataType : "json",
			beforeSend : function(xhr) {
				xhr.setRequestHeader('Content-Type', 'application/json');
			},
			success : function(response) {
				//AGREGAR FUNCION QUE llena tabla
			},
			error : function(response) {
				//incluir mensaje de error
			},
			complete : function() {
				
			}
		});

	});
});






