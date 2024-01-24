$(document).ready(function() {
	var hoy = new Date();
	var mes = hoy.getMonth();
	var anio = hoy.getFullYear();
	var fecha_actual = String(anio + "-" + mes + 1);
	$("#mesTransaccion").attr("max", fecha_actual);
	$("#mesTransaccion").attr("value", fecha_actual);

	var $local = {

		//formulario
		$formCriterioBusquedaReporte: $('#formCriterioBusquedaReporte'),
		// Criterios de busqueda
		$mesTransaccion: $('#mesTransaccion'),
		// botones
		$btnBuscar: $('#buscar'),
		$btnExportarResumen: $('#btnExportarResumen'),

		//tabla2
		$tablaResultadoBusqueda: $("#tablaResultadoBusqueda"),
		tablaResultadoBusqueda: '',
		$tablaCuerpo: $("#tablaCuerpo"),
		tablaCuerpo: '',
		$tablaPie: $("#tablaPie"),
		tablaPie: '',
		$tablaCabecera: $("#tablaCabecera"),
		tablaCabecera: '',


	};

	var idInstituciones = [];
	//TRAE LAS INSTITUCIONES PARA EL HEADER
	let cabecerasInstituciones = '';
	let subCabeceras = '';
	let cantidadInstituciones = '';
	$.ajax({
		type: "GET",
		url: $variableUtil.root + "institucion?accion=buscarPorInstitucionEmpresa",
		contentType: "application/json",
		dataType: "json",
		beforeSend: function(xhr) {
			xhr.setRequestHeader('Content-Type', 'application/json');
			$local.$tablaCabecera.empty();
		},
		success: function(response) {
			cantidadInstituciones = response.length;
			response.forEach(function(r) {
				idInstituciones.push(r.codigoInstitucion);
				cabecerasInstituciones = cabecerasInstituciones + '<th class="text-center" colspan="2">' + r.codigoInstitucion + ' - ' + r.descripcion + '</th>';
				subCabeceras = subCabeceras + '<th class="text-center">Monto</th><th class="text-center">Cantidad</th>';
			});

			$local.tablaCabecera = '<tr id="th_titulo"><th class="text-center" rowspan="2">Categoria</th>' + cabecerasInstituciones + '</tr>' + '<tr id="th_cabeceras">' + subCabeceras + '</tr>';
			$local.$tablaCabecera.append($local.tablaCabecera);
			$local.tablaCuerpo = '<tr><td colspan="' + cantidadInstituciones * 2 + 1 + '" class="text-center">No hay registros encontrados.</td></tr>';
			$local.$tablaCuerpo.append($local.tablaCuerpo);
			$local.tablaPie = '<td colspan="1">Total</td><td colspan="' + cantidadInstituciones * 2 + '"></td>';
			$local.$tablaPie.append($local.tablaPie);
		},
		error: function(response) {

		},
		complete: function() {
			$local.$btnBuscar.attr("disabled", false);
		}
	});

	var $formato = [
		{ "categoria": "Tarjetas nuevas recargables", "monto57": 0, "cantidad57": 0, "monto58": 0, "cantidad58": 0 },
		{ "categoria": "Tarjetas nuevas no recargables", "monto57": 0, "cantidad57": 0, "monto58": 0, "cantidad58": 0 }
	];
	var totalMonto57 = 0;
	var totalCantidad57 = 0;
	var totalMonto58 = 0;
	var totalCantidad58 = 0;

	$local.$btnBuscar.on('click', function() {
		var criterioBusqueda = {};
		if (!$local.$mesTransaccion.val()) {
			$funcionUtil.notificarException("Ingrese una fecha v\u00E1lida", "fa-check", "Aviso", "info");
		} else {
			criterioBusqueda.fecha = $local.$mesTransaccion.val() + '-01';

			var paramCriterioBusqueda = $.param(criterioBusqueda);
			$.ajax({
				type: "GET",
				url: $variableUtil.root + "reporte/resumen/recargas/resumen?accion=buscar&" + paramCriterioBusqueda,
				contentType: "application/json",
				dataType: "json",
				beforeSend: function(xhr) {
					xhr.setRequestHeader('Content-Type', 'application/json');
					$local.$btnBuscar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
					$local.$btnExportarResumen.attr("disabled", true);
					$local.$tablaCabecera.empty();
					$local.$tablaCuerpo.empty();
					$local.$tablaPie.empty();
				},
				success: function(response) {
					if (response.length == 0) {
						$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-check", "Aviso", "info");
						$local.$tablaCabecera.append($local.tablaCabecera);
						$local.$tablaCuerpo.append($local.tablaCuerpo);
						$local.$tablaPie.append($local.tablaPie);
						$local.$btnExportarResumen.attr("disabled", true);

					} else {
						$local.$btnExportarResumen.attr("disabled", false);
						$local.$tablaCabecera.append($local.tablaCabecera);
						var cero = 0;
						let rowRecargables = '';
						let rowNoRecargables = '';

						//LLENAR REACARGABLES
						idInstituciones.forEach(function(id) {

							let rowTempR = '';
							response.forEach(function(r) {
								if (r.idInstitucion === id && r.descripcionTipoTarjeta === 'RECARGABLE') {
									rowTempR = '</td><td class="dt-right">' + r.montoRecarga.toFixed(2) +
										'</td><td class="dt-right">' + r.totalRecarga + '</td>';
								}
							});
							if (rowTempR === '') {
								rowTempR = '</td><td class="dt-right">' + cero.toFixed(2) +
									'</td><td class="dt-right">' + cero + '</td>';
							}
							rowRecargables = rowRecargables + rowTempR;
						});

						//LLENAR NO RECARGABLES
						idInstituciones.forEach(function(id) {
							let rowTempR = '';
							response.forEach(function(r) {
								if (r.idInstitucion === id && r.descripcionTipoTarjeta === 'NO RECARGABLE') {
									rowTempR = '</td><td class="dt-right">' + r.montoRecarga.toFixed(2) +
										'</td><td class="dt-right">' + r.totalRecarga + '</td>';
								}
							});
							if (rowTempR === '') {
								rowTempR = '</td><td class="dt-right">' + cero.toFixed(2) +
									'</td><td class="dt-right">' + cero + '</td>';
							}

							rowNoRecargables = rowNoRecargables + rowTempR;
						});
						$local.$tablaCuerpo.append('<tr><td>Tarjetas nuevas recargables</td>' + rowRecargables + '</tr>' + '<tr><td>Tarjetas nuevas no recargables</td>' + rowNoRecargables + '</tr>');


						//LLENAR FOOTER
						let rowTotales = ' '
						idInstituciones.forEach(function(id) {
							let montoTotal = 0;
							let cantidadTotal = 0;
							response.forEach(function(r) {
								if (r.idInstitucion === id && r.descripcionTipoTarjeta !== null) {
									montoTotal = montoTotal + r.montoRecarga;
									cantidadTotal = cantidadTotal + r.totalRecarga;
								}
							});

							rowTotales = rowTotales + '</td><td class="dt-right">' + montoTotal.toFixed(2) +
								'</td><td class="dt-right">' + cantidadTotal + '</td>';
						});
						$local.$tablaPie.append('<td>Total' + rowTotales);

						//Crear datatable
						//$local.tablaResultadoBusqueda = $local.$tablaResultadoBusqueda.DataTable();
					}
				},
				error: function(response) {
					//incluir mensaje de error
				},
				complete: function() {
					$local.$btnBuscar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");

				}
			});
		}

	});
	$local.$btnExportarResumen.on('click', function() {
		var criterioBusqueda = {};
		var f = $local.$mesTransaccion.val() + '-01';
		criterioBusqueda.fecha = f;
		var paramCriterioBusqueda = $.param(criterioBusqueda);
		window.location.href = $variableUtil.root + "reporte/recargas/resumenMensual?accion=exportar&" + paramCriterioBusqueda;
	});
});