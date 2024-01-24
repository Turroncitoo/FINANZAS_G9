$(document).ready(function() {

	var $local = {
		//Criterios busqueda
		$selectPeriodo: $('#selectPeriodo'),
		$fechaProceso: $('#fechaProceso'),

		$divFechaProceso: $('#divFechaProceso'),
		$divMesProceso: $('#divMesProceso'),
		//Botones
		$btnBuscar: $('#buscar'),
		$btnExportar: $('#exportar'),

		//Tabla
		$tablaResultados: $("table.tablaResultados"),
		tablasResultados: {},
		tablaResultados: "",

		$tdTotal: $('#tdTotal'),
		$tdSaldo: $('#tdSaldo'),
		$tdLiberadas: $('#tdLiberadas'),
		$tdPendientes: $('#tdPendientes'),
		$tdDiferencia: $('#tdDiferencia'),

		saldoDiferencia: {
			604: 0.0,
			840: 0.0
		},

		saldoFinal: {
			604: 0.0,
			840: 0.0
		},

		saldoNoCompensado: {
			604: 0.0,
			840: 0.0
		},

		saldoLiberadas: {
			604: 0.0,
			840: 0.0
		},

		$institucionSelect: $('#institucionSelect'),
		$inputMesProceso: $('#inputMesProceso'),

	};

	$funcionUtil.crearSelect2($local.$selectPeriodo);
	$funcionUtil.crearDatePickerSimple($local.$fechaProceso);
	$funcionUtil.crearMultipleSelect2($local.$institucionSelect, "TODOS");

	/*Evento para que cambie los inputs en hidden*/
	$local.$selectPeriodo.change(function() {
		let tipoPeriodo = $(this).val();
		switch (tipoPeriodo) {
			case 'DIARIO':
				$local.$divFechaProceso.removeClass('hidden');
				$local.$divMesProceso.addClass('hidden');
				break;
			case 'MENSUAL':
				$local.$divFechaProceso.addClass('hidden');
				$local.$divMesProceso.removeClass('hidden');
				break;
		}
	});

	$('body').find('.final-840').text("0.00");
	$('body').find('.final-604').text("0.00");
	$('body').find('.liberadas-840').text("0.00");
	$('body').find('.liberadas-604').text("0.00");
	$('body').find('.pendientes-840').text("0.00");
	$('body').find('.pendientes-604').text("0.00");
	$('body').find('.diferencia-604').text("0.00");
	$('body').find('.diferencia-840').text("0.00");

	$local.$btnBuscar.on('click', function() {
		var criterioBusqueda = {};
		criterioBusqueda.fechaProceso = $funcionUtil.obtenerFechaDatePicker($local.$fechaProceso);
		criterioBusqueda.modo = $local.$selectPeriodo.val();
		criterioBusqueda.mesProceso = $funcionUtil.obtenerMesInputMonth($local.$inputMesProceso);
		criterioBusqueda.anioProceso = $funcionUtil.obtenerAnioInputMonth($local.$inputMesProceso);
		var paramCriterioBusqueda = $.param(criterioBusqueda);
		paramCriterioBusqueda = paramCriterioBusqueda + $funcionUtil.obtenerArraySeparadoPorComasParaPeticionGet($local.$institucionSelect.val(), "idsInstitucion");
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "reporte/conciliacion/saldosUba?" + paramCriterioBusqueda,
			contentType: "application/json",
			dataType: "json",
			beforeSend: function(xhr) {
				xhr.setRequestHeader('Content-Type', 'application/json');
				$.each($local.tablasResultados, function(i, tabla) {
					$local.tablasResultados[i].clear().draw();
				});
				$local.$btnBuscar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
				$('body').find('.final-840').text("0.00");
				$('body').find('.final-604').text("0.00");
				$('body').find('.liberadas-840').text("0.00");
				$('body').find('.liberadas-604').text("0.00");
				$('body').find('.pendientes-840').text("0.00");
				$('body').find('.pendientes-604').text("0.00");
				$('body').find('.diferencia-604').text("0.00");
				$('body').find('.diferencia-840').text("0.00");

				$local.saldoFinal['840'] = 0.0;
				$local.saldoFinal['604'] = 0.0;
				$local.saldoLiberadas['840'] = 0.0;
				$local.saldoLiberadas['604'] = 0.0;
				$local.saldoNoCompensado['840'] = 0.0;
				$local.saldoNoCompensado['604'] = 0.0;
				$local.saldoDiferencia['840'] = 0.0;
				$local.saldoDiferencia['604'] = 0.0;
			},
			success: function(response) {
				if (response.saldoFinal.length === 0 && response.saldoLiberadas.length === 0 && response.saldoNoCompensado.length === 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}

				if (response.saldoFinal.length != 0) {
					$.each(response.saldoFinal, function(idx, value) {
						$local.tablasResultados["tablaResumen-" + value.codigoMoneda].rows.add(value.reporteConciliacionResumen).draw();
						let $td = $('body').find('.final-' + value.codigoMoneda);
						$local.saldoFinal[value.codigoMoneda] = 0.0;
						value.reporteConciliacionResumen.forEach(function(item) {
							let valorAbono = !item.valorAbono ? 0.0 : item.valorAbono;
							let valorCargo = !item.valorCargo ? 0.0 : item.valorCargo;
							let valorComision = !item.valorComision ? 0.0 : item.valorComision;
							$local.saldoFinal[value.codigoMoneda] = $local.saldoFinal[value.codigoMoneda] + valorAbono + valorCargo + valorComision;
						});
						$td.text($local.saldoFinal[value.codigoMoneda].formatMoney(2));
					});
				} else {
					$local.saldoFinal['840'] = 0.0;
					$local.saldoFinal['604'] = 0.0;
					$('body').find('.final-840').text($local.saldoFinal['840'].formatMoney(2));
					$('body').find('.final-604').text($local.saldoFinal['604'].formatMoney(2));
				}

				if (response.saldoLiberadas.length != 0) {
					$.each(response.saldoLiberadas, function(idx, value) {
						$local.saldoLiberadas[value.codigoMoneda] = value.reporteConciliacionResumen[0].valorTotal;
						$('body').find('.liberadas-' + value.codigoMoneda).text($local.saldoLiberadas[value.codigoMoneda].formatMoney(2));
					});
				} else {
					$local.saldoLiberadas['840'] = 0.0;
					$local.saldoLiberadas['604'] = 0.0;
					$('body').find('.liberadas-840').text($local.saldoLiberadas['840'].formatMoney(2));
					$('body').find('.liberadas-604').text($local.saldoLiberadas['604'].formatMoney(2));
				}

				if (response.saldoNoCompensado.length != 0) {
					$.each(response.saldoNoCompensado, function(idx, value) {
						$local.saldoNoCompensado[value.codigoMoneda] = value.reporteConciliacionResumen[0].valorTotal * -1;
						$('body').find('.pendientes-' + value.codigoMoneda).text($local.saldoNoCompensado[value.codigoMoneda].formatMoney(2));
					});
				} else {
					$local.saldoNoCompensado['840'] = 0.0;
					$local.saldoNoCompensado['604'] = 0.0;
					$('body').find('.pendientes-840').text($local.saldoNoCompensado['840'].formatMoney(2));
					$('body').find('.pendientes-604').text($local.saldoNoCompensado['604'].formatMoney(2));
				}


				//Calculando diferencias
				$local.saldoDiferencia['604'] = $local.saldoFinal['604'] + $local.saldoLiberadas['604'] + $local.saldoNoCompensado['604'];
				$local.saldoDiferencia['840'] = $local.saldoFinal['840'] + $local.saldoLiberadas['840'] + $local.saldoNoCompensado['840'];

				$('body').find('.diferencia-604').text($local.saldoDiferencia['604'].formatMoney(2));
				$('body').find('.diferencia-840').text($local.saldoDiferencia['840'].formatMoney(2));

			},
			error: function(response) {
			},
			complete: function() {
				$local.$btnBuscar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$tablaResultados.filter(function() {
		var tabla = $(this);
		var idTabla = tabla.attr("idTabla");
		$local.tablasResultados[idTabla] = tabla.DataTable({
			"language": {
				"emptyTable": "No hay transacciones encontradas."
			},
			"initComplete": function() {
				tabla.wrap("<div class='table-responsive'></div>");
			},
			"columnDefs": [{
				"targets": [0, 1, 2],
				"className": "all filtrable",
			}, {
				"targets": 3,
				"className": "all filtrable dt-right",
			}, {
				"targets": [4, 5, 6, 7],
				"className": "all filtrable dt-right monto"
			}],
			"columns": [{
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.idInstitucion, row.descripcionInstitucion);
				},
				"title": 'Instituci\u00F3n'
			},
			{
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.idCategoria, row.descripcionCategoria);
				},
				"title": 'Categor\u00EDa'
			},
			{
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.tipoTransaccion, row.descripcionTipoTransaccion);
				},
				"title": 'Transacci\u00F3n'
			},
			{
				"data": 'cantidad',
				"title": "Cantidad"
			}, {
				"data": function(row) {
					return !row.valorAbono ? '0.00' : row.valorAbono.formatMoney(2);
				},
				"title": "Abono"
			}, {
				"data": function(row) {
					return !row.valorCargo ? '0.00' : row.valorCargo.formatMoney(2);
				},
				"title": "Cargo"
			}, {
				"data": function(row) {
					return !row.valorComision ? '0.00' : row.valorComision.formatMoney(2);
				},
				"title": "Comisi\u00F3n"
			}, {
				"data": function(row) {
					let abono = !row.valorAbono ? 0.0 : row.valorAbono;
					let cargo = !row.valorCargo ? 0.0 : row.valorCargo;
					let comision = !row.valorComision ? 0.0 : row.valorComision;
					let diff = abono + cargo + comision;
					row.total = diff;
					return diff.formatMoney(2);
				},
				"title": "Total"
			}],
			"footerCallback": function(row, data, start, end, display) {
			},

		})
	});

	$local.$btnExportar.on("click", function() {
		var criterioBusqueda = {};
		criterioBusqueda.fechaProceso = $funcionUtil.obtenerFechaDatePicker($local.$fechaProceso);
		criterioBusqueda.modo = $local.$selectPeriodo.val();
		criterioBusqueda.mesProceso = $funcionUtil.obtenerMesInputMonth($local.$inputMesProceso);
		criterioBusqueda.anioProceso = $funcionUtil.obtenerAnioInputMonth($local.$inputMesProceso);
		var paramCriterioBusqueda = $.param(criterioBusqueda);
		paramCriterioBusqueda = paramCriterioBusqueda + $funcionUtil.obtenerArraySeparadoPorComasParaPeticionGet($local.$institucionSelect.val(), "idsInstitucion");
		window.location.href = $variableUtil.root + "reporte/conciliacion/saldosUba?accion=exportar&" + paramCriterioBusqueda;
	});
});
