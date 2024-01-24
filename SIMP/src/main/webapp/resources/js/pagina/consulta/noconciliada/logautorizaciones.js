$(document).ready(function() {

	var $local = {
		$tablaConsulta: $("#tablaConsulta"),
		tablaConsulta: "",
		$tablaConsultaPorDia: $("#tablaConsultaPorDia"),
		tablaConsultaPorDia: "",
		$modalMantenimiento: $("#modalMantenimiento"),
		$aniadirMantenimento: $("#aniadirMantenimiento"),
		$registrarMantenimiento: $("#registrarMantenimiento"),
		$filaSeleccionada: "",
		$actualizarMantenimiento: $("#actualizarMantenimiento"),
		codigo_clase_servicioSeleccionado: "",
		codigo_membresiaSeleccionado: "",
		$membresias: $("#membresias"),
		$membresiasFiltroParaTableMantenimiento: $("#membresias-filtroParatablaConsulta"),
		filtrosSeleccionables: [],
		$resultadoBusqueda: $("#resultadoBusqueda"),
		$transaccionesNoConciliadasPorFechasChart: null,
		$buscarCriteriosFechaCorte: $("#buscarCriteriosFechaCorte"),
		$buscarCriteriosNumeroDias: $("#buscarCriteriosNumeroDias"),
		$formTipoBusquedaNumeroDias: $("#formTipoBusquedaNumeroDias"),
		$formTipoBusquedaFechaCorte: $("#formTipoBusquedaFechaCorte"),
		$criterios: $("#criterios"),
		$tipoBusqueda: $("input[type=radio][name=tipoBusquedaCriterio]"),
		$fechaCorteCriterio: $("#fechaCorteCriterio"),
		$fechaCorte: $("#fechaCorte"),
		$numeroDias: $("#numeroDias"),
		$exportarCriterioNumeroDias: $("#exportarCriterioNumeroDias"),
		$exportarCriterioFechaCorte: $("#exportarCriterioFechaCorte"),

		$empresaSelectFechaCorte: $("#empresaSelectFechaCorte"),
		$clienteSelectFechaCorte: $("#clienteSelectFechaCorte"),

		$empresaSelectNumeroDias: $("#empresaSelectNumeroDias"),
		$clienteSelectNumeroDias: $("#clienteSelectNumeroDias")

	};

	$funcionUtil.crearSelect2($local.$empresaSelectFechaCorte, "Seleccione una empresa");
	$funcionUtil.crearSelect2($local.$clienteSelectFechaCorte, "Seleccione un cliente");

	$funcionUtil.crearDatePickerSimple($local.$fechaCorte, "YYYY-MM-DD");

	var transaccionesNoConciliadasPorFechasPropiedadesChart = {
		"type": "serial",
		"categoryField": "fechaTxn",
		"angle": 30,
		"depth3D": 30,
		"startDuration": 1,
		"color": "#E01D1D",
		"handDrawScatter": 8,
		"handDrawThickness": 21,
		"theme": "default",
		"categoryAxis": {
			"gridPosition": "start"
		},
		"trendLines": [],
		"graphs": [{
			"balloonText": "[[title]] - [[category]] : [[value]]",
			"fillAlphas": 1,
			"id": "AmGraph-1",
			"title": "Txn Conciliadas",
			"type": "column",
			"valueField": "numeroTxnConciliadas"
		}, {
			"balloonText": "[[title]] - [[category]]:[[value]]",
			"fillAlphas": 1,
			"id": "AmGraph-2",
			"title": "Txn No Conciliadas",
			"type": "column",
			"valueField": "numeroTxnNoConciliadas"
		}],
		"guides": [],
		"valueAxes": [{
			"id": "ValueAxis-1",
			"stackType": "regular",
			"title": "N\u00FAmero de Transacciones"
		}],
		"allLabels": [],
		"balloon": {},
		"legend": {
			"enabled": true,
			"useGraphSettings": true
		},
		"titles": [{
			"id": "Transacciones",
			"size": 15,
			"text": "Autorizaciones no conciliadas"
		}]
	};

	var transaccionesNoConciliadasPorFechasChart = AmCharts.makeChart("graficoLogAutorizacionesNoConciliadas", transaccionesNoConciliadasPorFechasPropiedadesChart);

	$.fn.dataTable.ext.errMode = 'none';

	$local.$tipoBusqueda.on("change", function() {
		var tipoBusqueda = $(this).val();
		switch (tipoBusqueda) {
			case "numeroDias":
				$local.$numeroDias.removeClass("hidden");
				$local.$fechaCorteCriterio.addClass("hidden");
				break;
			case "fechaCorte":
				$local.$fechaCorteCriterio.removeClass("hidden");
				$local.$numeroDias.addClass("hidden");

				break;
			default:
				$funcionUtil.notificarException("Seleccione un Tipo de B\u00FAsqueda v\u00E1lido", "fa-warning", "Aviso", "warning");
		}
	});

	$local.$tablaConsulta.on('xhr.dt', function(e, settings, json, xhr) {
		switch (xhr.status) {
			case 500:
				$local.tablaConsulta.clear().draw();
				break;
		}
	});

	var buttonCommon = {
		exportOptions: {
			format: {
				body: function(data, row, column, node) {
					// Strip $ from salary column to make it numeric
					return column === 1 ?
						(data.substring(0, 4) + " " +
							data.substring(4, 8) + " " +
							data.substring(8, 12) + " " +
							data.substring(12, 16)) :
						data;
				}
			},
			columns: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19],
		}
		/*,
		customize:  function (xlsx) {
			var sheet = xlsx.xl.worksheets['styles.xml'];
		 
		}*/
	};

	$local.tablaConsulta = $local.$tablaConsulta.DataTable({
		"language": {
			"emptyTable": "No se han encontrado Autorizaciones con los criterios definidos."
		},
		"initComplete": function() {
			$local.$tablaConsulta.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaConsulta, $local.filtrosSeleccionables);
		},
		"columnDefs": [{
			"targets": [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 15, 16, 17, 18, 19, 20, 21],
			"className": "all filtrable dt-center",
		}, {
			"targets": 13,
			"className": "all filtrable dt-right",
		}
		],
		"columns": [{
			"data": function(row) {
				return row.idEmpresa + " - " + row.descripcionEmpresa;
			},
			"title": "Empresa"
		}, {
			"data": function(row) {
				return row.idCliente + " - " + row.descripcionCliente;
			},
			"title": "Cliente"
		}, {
			"data": 'tipoMensaje',
			"title": "Tipo Mensaje"
		}, {
			"data": 'numeroTarjeta',
			"title": "Nº Tarjeta"
		}, {
			"data": "tipoDocumento",
			"title": "Tipo Documento"
		}, {
			"data": "numeroDocumento",
			"title": "N\u00FAmero Documento"
		}, {
			"data": "nombres",
			"title": "Nombres"
		}, {
			"data": "apellidoPaterno",
			"title": "Apellido Paterno"
		}, {
			"data": "apellidoMaterno",
			"title": "Apellido Materno"
		}, {
			"data": 'descripcionCanal',
			"title": "Canal"
		}, {
			"data": 'descripcionProceso',
			"title": "Proceso"
		}, {
			"data": 'codigoProcesamiento',
			"title": "C\u00F3d Proc"
		}, {
			"data": 'monedaTransaccion',
			"title": "Moneda Txn"
		}, {
			"data": 'valorTransaccion',
			"title": "Valor Txn"
		}, {
			"data": 'fechaTransmision',
			"title": "Fecha Txn"
		}, {
			"data": 'horaTransmision',
			"title": "Hora Txn"
		}, {
			"data": 'trace',
			"title": "Trace"
		}, {
			"data": 'codigoAutorizacion',
			"title": "C\u00F3digo Autorizaci\u00F3n"
		}, {
			"data": 'descripcionRespuesta',
			"title": "C\u00F3digo Respuesta"
		}, {
			"data": 'idTerminal',
			"title": "Id Terminal"
		}, {
			"data": 'adquirente',
			"title": "Adquirente"
		}, {
			"data": 'numeroDias',
			"title": "N° D\u00EDas"
		}]
	});


	new $.fn.dataTable.Buttons($local.tablaConsulta, {
		buttons: [
			$.extend(true, {}, buttonCommon, {
				extend: 'excelHtml5'
			})
		]
	});

	$local.tablaConsulta.buttons().container()
		.appendTo($('.col-sm-6:eq(0)', $local.tablaConsulta.table().container()));


	$local.$tablaConsulta.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaConsulta.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaConsulta.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaConsulta.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	$local.tablaConsultaPorDia = $local.$tablaConsultaPorDia.DataTable({
		"language": {
			"emptyTable": "No se han encontrado Autorizaciones con los criterios definidos."
		},
		"initComplete": function() {
			$local.$tablaConsultaPorDia.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaConsultaPorDia, $local.filtrosSeleccionables);
		},
		"columnDefs": [{
			"targets": [0, 1, 2, 3, 4],
			"className": "all filtrable dt-center",
		}],
		"columns": [{
			"data": 'fechaTxn',
			"title": "Fecha Txn"
		}, {
			"data": 'numeroTxnConciliadas',
			"title": "N° Txn Conciliadas"
		}, {
			"data": 'numeroTxnNoConciliadas',
			"title": "N° Txn No Conciliadas"
		}, {
			"data": 'numeroTotal',
			"title": "N° Txn Total"
		}, {
			"data": 'numeroDias',
			"title": "N° D\u00EDas"
		}]
	});

	$local.$tablaConsultaPorDia.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaConsultaPorDia.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaConsulta.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaConsultaPorDia.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	function requestClientes(idEmpresa, clienteSelect) {
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "cliente/empresa/" + idEmpresa,
			beforeSend: function() {
				clienteSelect.find("option[value!='-1']").remove();
			},
			success: function(response) {
				clienteSelect.find("option[value!='-1']").remove();
				if (response.length == 0) {
					return;
				}
				for (var i = 0; i < response.length; i++) {
					var val = response[i];
					clienteSelect.append($("<option></option>")
						.attr("value", val.idCliente)
						.text(`${val.idCliente} - ${val.descripcion}`));
				}
			}
		});
	}

	$local.$empresaSelectFechaCorte.on("change", function() {
		var idEmpresa = $(this).val();
		if (idEmpresa !== -1) {
			requestClientes(idEmpresa, $local.$clienteSelectFechaCorte);
		} else {
			$local.$clienteSelectFechaCorte.find("option[value!='-1']").remove();
		}
	});

	$local.$empresaSelectNumeroDias.on("change", function() {
		var idEmpresa = $(this).val();
		if (idEmpresa !== -1) {
			requestClientes(idEmpresa, $local.$clienteSelectNumeroDias);
		} else {
			$local.$clienteSelectNumeroDias.find("option[value!='-1']").remove();
		}
	});


	$local.$buscarCriteriosFechaCorte.on("click", function() {
		var data = $local.$formTipoBusquedaFechaCorte.serializeJSON();

		var idEmpresa = $local.$empresaSelectFechaCorte.val();
		var idCliente = $local.$clienteSelectFechaCorte.val();

		data.fechaCorte = $funcionUtil.obtenerFechaDatePicker($local.$fechaCorte);

		data.numeroDias = "";
		data.idEmpresa = idEmpresa === '-1' ? 'ALL' : idEmpresa;
		data.idCliente = idCliente === '-1' ? 'ALL' : idCliente;
		data.descripcionEmpresa = idEmpresa === '-1' ? 'TODOS' : $local.$empresaSelectFechaCorte.find('option:selected').text();
		data.descripcionCliente = idCliente === '-1' ? 'TODOS' : $local.$clienteSelectFechaCorte.find('option:selected').text();

		$.ajax({
			type: "GET",
			url: $variableUtil.root + "logautorizaciones?accion=buscarPorCriterios",
			contentType: "application/json",
			dataType: "json",
			data: data,
			beforeSend: function() {
				$local.tablaConsulta.clear().draw();
				$local.$buscarCriteriosFechaCorte.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success: function(response) {
				if (response.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				$local.tablaConsulta.rows.add(response).draw();
			},
			error: function(response) {

			},
			complete: function() {
				$local.$buscarCriteriosFechaCorte.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});

		$.ajax({
			type: "GET",
			url: $variableUtil.root + "logautorizaciones?accion=buscarPorDia",
			contentType: "application/json",
			dataType: "json",
			data: data,
			beforeSend: function() {
				$local.tablaConsultaPorDia.clear().draw();
				$local.$buscarCriteriosFechaCorte.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success: function(transaccionesPorDia) {
				if (transaccionesPorDia.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				transaccionesNoConciliadasPorFechasChart.dataProvider = transaccionesPorDia;
				transaccionesNoConciliadasPorFechasChart.validateData();
				$local.tablaConsultaPorDia.rows.add(transaccionesPorDia).draw();
			},
			error: function(response) {

			},
			complete: function() {
				$local.$buscarCriteriosFechaCorte.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$exportarCriterioFechaCorte.on("click", function() {
		var data = $local.$formTipoBusquedaFechaCorte.serializeJSON();
		data.descripcionTipoBusqueda = $local.$formTipoBusquedaFechaCorte.find("input[name='tipoBusqueda']:checked").parent().text().trim();
		data.descripcionFechaCorte = data.fechaCorte || $local.$fechaCorte.val();;
		var idEmpresa = $local.$empresaSelectFechaCorte.val();
		var idCliente = $local.$clienteSelectFechaCorte.val();

		data.descripcionOrigen = $local.$formTipoBusquedaFechaCorte.find("input[name='tipoBusqueda']:checked").parent().text();
		data.fechaCorte = $funcionUtil.obtenerFechaDatePicker($local.$fechaCorte);
		data.numeroDias = "";
		data.idEmpresa = idEmpresa === '-1' ? 'ALL' : idEmpresa;
		data.idCliente = idCliente === '-1' ? 'ALL' : idCliente;
		data.descripcionEmpresa = idEmpresa === '-1' ? 'TODOS' : $local.$empresaSelectFechaCorte.find('option:selected').text();
		data.descripcionCliente = idCliente === '-1' ? 'TODOS' : $local.$clienteSelectFechaCorte.find('option:selected').text();

		var paramCriterioBusqueda = $.param(data);
		window.location.href = $variableUtil.root + "reporte/logautorizacion/noConciliadas?accion=buscarPorCriterios&" + paramCriterioBusqueda;
	});

	$local.$exportarCriterioNumeroDias.on("click", function() {
		var data = $local.$formTipoBusquedaNumeroDias.serializeJSON();
		data.descripcionTipoBusqueda = $local.$formTipoBusquedaNumeroDias.find("input[name='tipoBusqueda']:checked").parent().text().trim();
		var idEmpresa = $local.$empresaSelectNumeroDias.val();
		var idCliente = $local.$clienteSelectNumeroDias.val();

		data.descripcionOrigen = $local.$formTipoBusquedaNumeroDias.find("input[name='tipoBusqueda']:checked").parent().text();
		data.idEmpresa = idEmpresa === '-1' ? 'ALL' : idEmpresa;
		data.idCliente = idCliente === '-1' ? 'ALL' : idCliente;
		data.descripcionEmpresa = idEmpresa === '-1' ? 'TODOS' : $local.$empresaSelectNumeroDias.find('option:selected').text();
		data.descripcionCliente = idCliente === '-1' ? 'TODOS' : $local.$clienteSelectNumeroDias.find('option:selected').text();

		var paramCriterioBusqueda = $.param(data);
		window.location.href = $variableUtil.root + "reporte/logautorizacion/noConciliadas?accion=buscarPorCriterios&" + paramCriterioBusqueda;
	});

	$local.$formTipoBusquedaFechaCorte.find("input").keypress(function(event) {
		if (event.which == 13) {
			$local.$formTipoBusquedaFechaCorte.trigger("click");
			return false;
		}
	});

	$local.$buscarCriteriosNumeroDias.on("click", function() {

		var data = $local.$formTipoBusquedaNumeroDias.serializeJSON();

		var idEmpresa = $local.$empresaSelectNumeroDias.val();
		var idCliente = $local.$clienteSelectNumeroDias.val();

		data.idEmpresa = idEmpresa === '-1' ? 'ALL' : idEmpresa;
		data.idCliente = idCliente === '-1' ? 'ALL' : idCliente;
		data.descripcionEmpresa = idEmpresa === '-1' ? 'TODOS' : $local.$empresaSelectNumeroDias.find('option:selected').text();
		data.descripcionCliente = idCliente === '-1' ? 'TODOS' : $local.$clienteSelectNumeroDias.find('option:selected').text();


		$.ajax({
			type: "GET",
			url: $variableUtil.root + "logautorizaciones?accion=buscarPorCriterios",
			contentType: "application/json",
			dataType: "json",
			data: data,
			beforeSend: function() {
				$local.tablaConsulta.clear().draw();
				$local.$buscarCriteriosNumeroDias.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success: function(response) {
				if (response.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				$local.tablaConsulta.rows.add(response).draw();
			},
			error: function(response) {

			},
			complete: function() {
				$local.$buscarCriteriosNumeroDias.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});

		$.ajax({
			type: "GET",
			url: $variableUtil.root + "logautorizaciones?accion=buscarPorDia",
			contentType: "application/json",
			dataType: "json",
			data: data,
			beforeSend: function() {
				$local.tablaConsultaPorDia.clear().draw();
				$local.$buscarCriteriosNumeroDias.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success: function(transaccionesPorDia) {
				if (transaccionesPorDia.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				transaccionesNoConciliadasPorFechasChart.dataProvider = transaccionesPorDia;
				transaccionesNoConciliadasPorFechasChart.validateData();
				$local.tablaConsultaPorDia.rows.add(transaccionesPorDia).draw();
			},
			error: function(response) {

			},
			complete: function() {
				$local.$buscarCriteriosNumeroDias.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$formTipoBusquedaNumeroDias.find("input").keypress(function(event) {
		if (event.which == 13) {
			$local.$buscarCriteriosNumeroDias.trigger("click");
			return false;
		}
	});


});