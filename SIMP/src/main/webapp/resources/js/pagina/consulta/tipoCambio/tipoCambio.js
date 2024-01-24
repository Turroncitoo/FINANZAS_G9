$(document).ready(function() {
	var $local = {
		$rangoFechaBusqueda: $("#rangoFechaBusqueda"),
		$buscar: $("#buscar"),
		$tablaTipoCambio: $("#tablaTipoCambio"),
		tablaTipoCambio: "",
		$resultadoBusquedaTipoCambio: $("#resultadoBusquedaTipoCambio")
	};

	$funcionUtil.crearDateRangePickerConsulta($local.$rangoFechaBusqueda, "right");
	$formCriterioBusqueda = $("#formCriterioBusqueda");

	$local.tablaTipoCambio = $local.$tablaTipoCambio.DataTable({
		"language": {
			"emptyTable": "No hay tipos de cambio encontradas"
		},
		"columnDefs": [{
			"targets": 0,
			"className": "all filtrable dt-center",
			"render": $.fn.dataTable.moment('DD/MM/YYYY')
		}, {
			"targets": 1,
			"className": "all filtrable dt-right monto"
		},],
		"order": [],
		"initComplete": function () {
            $local.$tablaTipoCambio.wrap("<div class='table-responsive'></div>");
            $tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaTipoCambio);
        },
		"columns": [{
			"data": 'fechaProceso',
			"title": "Fecha de Proceso"
		}, {
			"data": function(row) {
				return $funcionUtil.formatMoney(row.tipoCambio, 4);
			},
			"title": "Tipo de Cambio"
		}],
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

	$local.$tablaTipoCambio.wrap("<div class='table-responsive'></div>");

	$local.$buscar.on("click", function() {
		var criterioBusqueda = $formCriterioBusqueda.serializeJSON();
		var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaBusqueda);
		criterioBusqueda.fechaInicio = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaFin = rangoFechaBusqueda.fechaFin;
		$.ajax({
			type: "GET",
			url: window.location.href + "?accion=buscar",
			data: criterioBusqueda,
			beforeSend: function() {
				$local.tablaTipoCambio.clear().draw();
				$local.$buscar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success: function(reporteResumenAutorizacion) {
				if (reporteResumenAutorizacion.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				$local.tablaTipoCambio.rows.add(reporteResumenAutorizacion).draw();
			},
			complete: function() {
				$local.$buscar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});
});