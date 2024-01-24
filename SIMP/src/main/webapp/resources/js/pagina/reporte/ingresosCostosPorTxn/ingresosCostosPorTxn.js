$(document).ready(function() {

	var $local = {

		// Criterios
		$periodo: $('#periodo'),
		$divPeriodo: $('#divPeriodo'),

		// Botones
		$btnBuscar: $('#buscar'),
		$btnExportar: $('#exportar'),

		//Tabla
		$tablaResultados: $('#tablaResultados'),
		tablaResultados: ''
	};


	$local.tablaResultados = $local.$tablaResultados.DataTable({
		"language": {
			"emptyTable": "No hay registros encontrados."
		},
		"intiComplete": function() {
			$local.$tablaResultados.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaResultados);
		},
		"columnDefs": [{
			"targets": [0, 1, 2, 3],
			"className": "all filtrable",
		}, {
			"targets": 4,
			"className": "all filtrable dt-right",
		}],
		"columns": [
			{
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.idInstitucion, row.descripcionInstitucion)
				},
				"title": "Institucion"
			},
			{
				"data": "periodo",
				"title": "Periodo"
			},
			{
				"data": "concepto",
				"title": "Concepto"
			},
			{
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.codigoMoneda, row.descripcionMoneda)
				},
				"title": "Moneda"
			},
			{
				"data": "montoTotal",
				"title": "Monto"
			}

		]
	});

	$local.$tablaResultados.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaResultados.column($(this).parent().index() + ':visible').search(this.value).draw();
	});


	$local.$btnBuscar.on('click', function() {
		var criterioBusqueda = {} // declaro objeto vacio
		criterioBusqueda.periodo = $local.$periodo.val();

		$.ajax({
			type: "GET",
			url: $variableUtil.root + "reporte/ingresosCostosPorTxn/resumenMensual?accion=buscar",
			contentType: "application/json",
			dataType: "json",
			data: criterioBusqueda,
			beforeSend: function() {
				$local.tablaResultados.clear().draw();
				$local.$btnBuscar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success: function(response) {
				if (response.length === 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				$local.tablaResultados.rows.add(response).draw();
			},
			error: function(response) {
			},
			complete: function() {
				$local.$btnBuscar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$btnExportar.on("click", function() {
		var criterioBusqueda = {}
		criterioBusqueda.periodo = $local.$periodo.val();

		var paramCriterioBusqueda = $.param(criterioBusqueda);
		window.location.href = $variableUtil.root + "reporte/ingresosCostosPorTxn/resumenMensual?accion=exportar&" + paramCriterioBusqueda;
	})

});