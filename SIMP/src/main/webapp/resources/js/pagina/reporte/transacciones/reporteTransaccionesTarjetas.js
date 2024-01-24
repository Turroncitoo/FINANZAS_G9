$(document).ready(function() {

	var $local = {
		$buscar : $("#buscar"),
		$exportar : $("#exportar"),
		$inputPeriodoMeses : $("#inputPeriodoMeses"),
		$tablaResultadoBusqueda : $("#tablaResultadoBusqueda"),
		tablaResultadoBusqueda : "",
		
		//Tabla
		$tablaResultados : $("table.tablaResultados"),
		tablaResultados : "", 
		tablasResultados : {},
		filtrosSeleccionables : [],
	};
	
	$formCriterioBusqueda = $("#formCriterioBusqueda");
	

	// Activamos los tabs para los datatables
	$('a[data-toggle="tab"]').on('shown.bs.tab', function(e) {
		$.fn.dataTable.tables({
			visible : true,
			api : true
		}).columns.adjust();
	});

	$local.$tablaResultados.filter(function() {
		var tabla = $(this);
		var idTabla = tabla.attr("idTabla");
		$local.tablasResultados[idTabla] = tabla.DataTable({
			"language" : {
				"emptyTable" : "No hay registros encontrados."
			},
			"initComplete" : function() {
				tabla.wrap("<div class='table-responsive'></div>");
				$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, tabla);
			},
			"columnDefs" : [ {
				"targets" : [ 0, 1],
				"className" : "all filtrable",
			}, {
				"targets" : [2, 3, 4, 5],
				"className" : "all filtrable dt-right",
			}],
			"columns" : [ 
			{
				"data" : function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.idInstitucion, row.descripcionInstitucion);
				},
				"title" : "Instituci\u00F3n" //0
			}, {
				"data" : function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.idOperacion, row.descripcionOperacion);
				},
				"title" : "Operaci\u00F3n"//1
			}, {
				"data" : "numeroTarjetas",
				"title" : "N° Tarjetas"//2
			}, {
				"data" : "numeroTransacciones",
				"title" : "N° Transacciones"//3
			}, {
				"data" : "montoPromedio",
				"title" : "Monto Promedio"//4
			}, {
				"data" : "montoTotal",
				"title" : "Monto Total"//5
			}],
		});
	});
	
	$local.$tablaResultados.find("thead").on('keyup', 'input.filtrable', function() {
		var $tabla = $(this).parents('table');
		var idTabla = $tabla.attr("idTabla");
		var tabla = $local.tablasResultados[idTabla];
		tabla.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$buscar.on("click", function() {
		if($local.$inputPeriodoMeses.val()==null || $local.$inputPeriodoMeses.val()==''){
			$funcionUtil.notificarException('Ingrese un periodo de consulta', "fa-exclamation-circle", "Informaci\u00F3n", "info");
			return;
		}
		var criterioBusqueda = $formCriterioBusqueda.serializeJSON();
		criterioBusqueda.mes = $funcionUtil.obtenerMesValorMonth($local.$inputPeriodoMeses.val());
		criterioBusqueda.anio = $funcionUtil.obtenerAnioValorMonth($local.$inputPeriodoMeses.val());
		criterioBusqueda.mesAnioTexto = $funcionUtil.obtenerMesesEnTexto(criterioBusqueda.mes,criterioBusqueda.anio);
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "reporte/transacciones/tarjetas?accion=buscar",
			data : criterioBusqueda,
			beforeSend : function() {
				$.each($local.tablasResultados, function(i, tabla) {
					$local.tablasResultados[i].clear().draw();
				});
				$local.existenFilas = false;
				$local.$buscar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success : function(response) {
				if (response.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				$.each(response, function(idx, value) {
					if (value.codigoMoneda != null) {
						$local.tablasResultados["tabla-" + value.codigoMoneda].rows.add(value.detalleTransaccionesTarjetas).draw();
					}
				});
			},
			error : function() {
			},
			complete : function() {
				$local.$buscar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$exportar.on("click", function() {
		if($local.$inputPeriodoMeses.val()==null || $local.$inputPeriodoMeses.val()==''){
			$funcionUtil.notificarException('Ingrese un periodo de consulta', "fa-exclamation-circle", "Informaci\u00F3n", "info");
			return;
		}
		var criterioBusqueda = $formCriterioBusqueda.serializeJSON();
		criterioBusqueda.mes = $funcionUtil.obtenerMesValorMonth($local.$inputPeriodoMeses.val());
		criterioBusqueda.anio = $funcionUtil.obtenerAnioValorMonth($local.$inputPeriodoMeses.val());
		criterioBusqueda.mesAnioTexto = $funcionUtil.obtenerMesesEnTexto(criterioBusqueda.mes,criterioBusqueda.anio);
		var paramCriterioBusqueda = $.param(criterioBusqueda);
		window.location.href = $variableUtil.root + "reporte/transacciones/tarjetas?accion=exportar&" + paramCriterioBusqueda;
	});

});