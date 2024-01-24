$(document).ready(function() {
	var hoy = new Date();
	var mes = hoy.getMonth();
	var anio = hoy.getFullYear();
	var fecha_actual = String(anio + "-" + mes + 1);
	$("#mesTransaccionInicio").attr("max", fecha_actual);
	$("#mesTransaccionInicio").attr("value", fecha_actual);
	$("#mesTransaccionFin").attr("max", fecha_actual);
	$("#mesTransaccionFin").attr("value", fecha_actual);


	var $local = {
		// Criterio busqueda
		$mesTransaccionInicio: $('#mesTransaccionInicio'),
		$mesTransaccionFin: $('#mesTransaccionFin'),
		$tabResultado: $('#tabResultado'),
		$mesInicio: '',
		$anioInicio: '',
		$mesFin: '',
		$anioFin: '',

		// Botones
		$buscar: $("#buscar"),
		$exportar: $("#exportar"),

	}
	$local.$exportar.attr("disabled", true);
	var $moneda = {};
	$local.$buscar.on('click', function() {
		var mti = $local.$mesTransaccionInicio.val().split("-");
		$local.$mesInicio = mti[1];
		$local.$anioInicio = mti[0];

		var mtf = $local.$mesTransaccionFin.val().split("-");
		$local.$mesFin = mtf[1];
		$local.$anioFin = mtf[0];

		var criterioBusqueda = {};
		criterioBusqueda.mesInicio = $local.$mesInicio;
		criterioBusqueda.anioInicio = $local.$anioInicio;
		criterioBusqueda.mesFin = $local.$mesFin;
		criterioBusqueda.anioFin = $local.$anioFin;
		var paramCriterioBusqueda = $.param(criterioBusqueda);
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "reporte/resumen/personas/resumen?accion=buscar&" + paramCriterioBusqueda,
			contentType: "application/json",
			dataType: "json",
			beforeSend: function(xhr) {
				xhr.setRequestHeader('Content-Type', 'application/json');
				$local.$buscar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
				$local.$exportar.attr("disabled", true);
				$('#tabResultado').empty();
				$('#tabResultadoContenido').empty();

			},
			success: function(response) {
				if (response.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-check", "Aviso", "info");
					$local.$exportar.attr("disabled", true);
					var tablaNoEncontrado = "";
					var tabNoEncontrado = "";
					tabNoEncontrado += '<li class="active"><a data-toggle="tab" href="#noencontrada">No encontrada</a></li>';
					tablaNoEncontrado += '<div id="no encontrado"' +
						' class="tab-pane fade in active"' +
						'<div class="tabla">' +
						'<table' +
						' class="table table-bordered table-condensed nowrap display table-hover tablaResultados">' +
						'<thead>' +
						'<th class="text-center">Instituci&oacuten</th>' +
						'<th class="text-center">Operaci&oacuten</th>' +
						'<th class="text-center">Monto</th>' +
						'<th class="text-center">Cantidad</th>' +
						'<th class="text-center">Promedio</th>' +
						'</thead>' +
						'<tbody>' +
						'<tr><td class="text-center" colspan="5">No se han encontrado resultados.</td></tr>' +
						'</tbody>' +
						'<tfoot>' +
						'<tr>' +
						'<td colspan="2" class="dt-left">Total</td>' +
						'<td colspan="1" class="mtotal dt-right"></td>' +
						'<td colspan="1" class="mtotal dt-right"></td>' +
						'<td colspan="1" class="mtotal dt-right"></td>' +
						'</tr>' +
						'</tfoot>' +
						'</table>' +
						'</div>' +
						'</div>';
					$('#tabResultado').append(tabNoEncontrado);
					$('#tabResultadoContenido').append(tablaNoEncontrado);

				} else {
					$local.$exportar.attr("disabled", false);
					var tabsh = '';
					var tabsb = "";
					response.forEach(function(value, index) {
						var body = "";
						var totalMontoTotal = 0;
						var totalCantiadTotal = 0;
						var totalPromedioMonto = 0;
						value.personas.forEach(function(p) {
							totalMontoTotal += p.montoTotal;
							totalCantiadTotal += p.cantidadTransacciones;
							totalPromedioMonto += p.promedioMonto;
							body += '<tr>' +
								'<td class="dt-left">' + p.idInstitucion + ' - ' + p.descripcionInstitucion + '</td>' +
								'<td class="dt-left">' + p.descripcionOperacion + '</td>' +
								'<td class="dt-right">' + p.montoTotal.toFixed(2) + '</td>' +
								'<td class="dt-right">' + p.cantidadTransacciones + '</td>' +
								'<td class="dt-right">' + p.promedioMonto.toFixed(2) + '</td>' +
								'</tr>';
						})



						var isActive = index == 0 ? 'active' : '';
						tabsh += '<li class="' + isActive + '"><a data-toggle="tab" href="#' + value.codigoMoneda + '">' + value.codigoMoneda + ' - ' + value.descripcionMoneda + '</a></li>';
						tabsb += '<div' +
							' id="' + value.codigoMoneda + '" class="tab-pane fade in ' + isActive + '"' +
							'<div class="tabla">' +
							'<table' +
							' idTabla="' + value.codigoMoneda + ' - ' + value.descripcionMoneda + '" id="tabla-' + value.codigoMoneda + '"' +
							' class="table table-bordered table-condensed nowrap display table-hover tablaResultados">' +
							'<thead>' +
							'<th class="text-center">Instituci&oacuten</th>' +
							'<th class="text-center">Operaci&oacuten</th>' +
							'<th class="text-center">Monto</th>' +
							'<th class="text-center">Cantidad</th>' +
							'<th class="text-center">Promedio</th>' +
							'</thead>' +
							'<tbody>' + body +
							'</tbody>' +
							'<tfoot>' +
							'<tr>' +
							'<td colspan="2" class="dt-left">Total</td>' +
							'<td colspan="1" class="mtotal dt-right">' + totalMontoTotal.toFixed(2) + '</td>' +
							'<td colspan="1" class="mtotal dt-right">' + totalCantiadTotal + '</td>' +
							'<td colspan="1" class="mtotal dt-right">' + totalPromedioMonto.toFixed(2) + '</td>' +
							'</tr>' +
							'</tfoot>' +
							'</table>' +
							'</div>' +
							'</div>';
					});
					$('#tabResultado').append(tabsh);
					$('#tabResultadoContenido').append(tabsb);
				}
			},
			error: function(response) {
				//incluir mensaje de error
			},
			complete: function() {
				$local.$buscar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");

			}
		});


	});

	$local.$exportar.on('click', function() {
		var criterioBusqueda = {};
		criterioBusqueda.mesInicio = $local.$mesInicio;
		criterioBusqueda.anioInicio = $local.$anioInicio;
		criterioBusqueda.mesFin = $local.$mesFin;
		criterioBusqueda.anioFin = $local.$anioFin;
		var paramCriterioBusqueda = $.param(criterioBusqueda);
		window.location.href = $variableUtil.root + "reporte/personas/resumenMensual?accion=exportar&" + paramCriterioBusqueda;
	});
});
