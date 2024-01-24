$(document).ready(function() {

	var $local = {
		$rangoFechaBusqueda: $("#rangoFechaBusqueda"),
		$buscar: $("#buscar"),
		$resultadoBusqueda: $("#resultadoBusqueda"),
		$tablaComisiones: $("table.tablaComisiones"),
		tablaComisiones: "",
		tablasComisiones: {},
		$encabezados: "",
		encabezadoComisionesArreglo: [],
		$exportar: $("#exportar"),
		$empresaSelect: $("#empresaSelect"),
		$clienteSelect: $("#clienteSelect"),
		$institucionesSelect: $('#instituciones')
	};

	$funcionUtil.crearSelect2($local.$empresaSelect, "Seleccione una empresa");
	$funcionUtil.crearSelect2($local.$clienteSelect, "Seleccione un cliente");
	$funcionUtil.crearSelect2($local.$institucionesSelect, "Seleccione una instituci\u00F3n");
	$funcionUtil.crearDatePickerSimple($local.$rangoFechaBusqueda, "DD/MM/YYYY");

	$formCriterioBusquedaReporte = $("#formCriterioBusquedaReporte");

	$('a[data-toggle="tab"]').on('shown.bs.tab', function(e) {
		$.fn.dataTable.tables({
			visible: true,
			api: true
		}).columns.adjust();
	});

	$local.$tablaComisiones.filter(function() {
		var tablaComision = $(this);
		$local.$encabezados = tablaComision.find("th.conceptoComision");
		var idTabla = tablaComision.attr("idTabla");
		$local.tablasComisiones[idTabla] = tablaComision.DataTable({
			"scrollX": true,
			"scrollCollapse": true,
			"language": {
				"emptyTable": "No hay transacciones encontradas."
			},
			"fixedColumns": {
				"leftColumns": 5
			},
			"footerCallback": function(row, data, start, end, display) {
				var api = this.api(), data;
				var tieneData = false;
				api.columns('.comision').every(function() {
					var index = this.selector.cols;
					if (this.data().length > 0) {
						tieneData = true;
						var suma = this.data().reduce(function(a, b) {
							return parseFloat(a) + parseFloat(b);
						}, 0);
						$(api.column(index).footer()).html(suma.toFixed(4));
						$(api.column(index).footer()).removeClassRegex("color-");
						if (suma > 0) {
							$(api.column(index).footer()).addClass("color-blue");
						} else if (suma < 0) {
							$(api.column(index).footer()).addClass("color-red");
						} else {
							$(api.column(index).footer()).addClass("color-inherit");
						}
					} else {
						$(api.column(index).footer()).html("");
					}
				});
				if (tieneData) {
					var cantidadTotal = api.column(5).data().reduce(function(a, b) {
						return parseInt(a) + parseInt(b);
					}, 0);
					var monto = api.column(6).data().reduce(function(a, b) {
						return parseFloat(a) + parseFloat(b);
					}, 0);
					$(api.column(5).footer()).html(cantidadTotal);
					$(api.column(6).footer()).html(monto.toFixed(2));
					$(api.column(6).footer()).removeClassRegex("color-");
					if (monto > 0) {
						$(api.column(6).footer()).addClass("color-blue");
					} else if (monto < 0) {
						$(api.column(6).footer()).addClass("color-red");
					} else {
						$(api.column(6).footer()).addClass("color-inherit");
					}
				} else {
					$(api.column(5).footer()).html("");
					$(api.column(6).footer()).html("");
				}
			},
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
			}
		});
	});

	for (var i = 0; i < $local.$encabezados.length; i++) {
		var conceptoComision = $local.$encabezados.filter("[name=" + i + "]").attr("idConceptoComision");
		$local.encabezadoComisionesArreglo.push(conceptoComision);
	}

	$local.$empresaSelect.on("change", function() {
		var idEmpresa = $(this).val();
		if (idEmpresa !== 'ALL') {
			$.ajax({
				type: "GET",
				url: $variableUtil.root + "cliente/empresa/" + idEmpresa,
				beforeSend: function() {
					$local.$clienteSelect.find("option[value!='-1']").remove();
				},
				success: function(response) {
					$local.$clienteSelect.find("option[value!='-1']").remove();
					if (response.length == 0) {
						return;
					}
					for (var i = 0; i < response.length; i++) {
						var val = response[i];
						$local.$clienteSelect.append($("<option></option>")
							.attr("value", val.idCliente)
							.text(`${val.idCliente} - ${val.descripcion}`));
					}
				}
			});
		} else {
			$local.$clienteSelect.find("option[value!='ALL']").remove();
			$local.$clienteSelect.append($("<option></option>")
				.attr("value", "ALL")
				.text(`TODOS`));
		}
	});

	$local.$buscar.on("click", function() {
		if (!$formCriterioBusquedaReporte.valid()) {
			return;
		}
		var criterioBusqueda = $formCriterioBusquedaReporte.serializeJSON();
		criterioBusqueda.fechaProceso = $local.$rangoFechaBusqueda.data("daterangepicker").startDate.format('YYYY-MM-DD');
		criterioBusqueda.codigoMoneda = 0; //Para no ingresar nulos en el SP
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "reporte/comision/moneda?accion=buscar",
			data: criterioBusqueda,
			beforeSend: function(xhr) {
				$.each($local.tablasComisiones, function(i, tablaComision) {
					$local.tablasComisiones[i].clear().draw();
				});
				$local.$buscar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success: function(reporteComisionMoneda) {
				if (reporteComisionMoneda.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				var finInicioCelda = "</td><td>";
				$.each(reporteComisionMoneda, function(i, comisionMoneda) {
					var $tablaMoneda = $("#tabla" + comisionMoneda.codigoMoneda);
					var filas = new Array();
					var j = -1;
					$.each(comisionMoneda.transacciones, function(i, transaccion) {
						filas[++j] = "<tr><td>";
						filas[++j] = $funcionUtil.unirCodigoDescripcion(transaccion.codigoMembresia, transaccion.descripcionMembresia);
						filas[++j] = "</td><td>";
						filas[++j] = $funcionUtil.unirCodigoDescripcion(transaccion.codigoClaseServicio, transaccion.descripcionClaseServicio);
						filas[++j] = "</td><td>";
						filas[++j] = $funcionUtil.unirCodigoDescripcion(transaccion.codigoOrigen, transaccion.descripcionOrigen);
						filas[++j] = "</td><td>";
						filas[++j] = $funcionUtil.unirCodigoDescripcion(transaccion.claseTransaccion, transaccion.descripcionClaseTransaccion);
						filas[++j] = "</td><td>";
						filas[++j] = $funcionUtil.unirCodigoDescripcion(transaccion.codigoTransaccion, transaccion.descripcionCodigoTransaccion);
						filas[++j] = "</td><td class='dt-right'>";
						filas[++j] = $funcionUtil.formatMoney(transaccion.cantidadTransaccion, 0);
						filas[++j] = "</td><td class='dt-right monto'>";
						filas[++j] = transaccion.monto.formatMoney(2);
						filas[++j] = "</td><td class='dt-right monto comision'>";
						var k = -1;
						var subTotal = transaccion.monto;
						var comis1 = comis2 = comis3 = comis4 = comis5 = comis6 = comis9 = comis10 = comis11 = comis12 = comis13 = comis14 = 0;
						for (var k = 0; k < $local.encabezadoComisionesArreglo.length; k++) {
							var montoComision = 0;
							var idConceptoComision = $local.encabezadoComisionesArreglo[k];
							$.each(transaccion.comisiones, function(k, comision) {
								if (comision.idConceptoComision == idConceptoComision) {
									montoComision = comision.comision || 0;
									var signo = "";
									if (montoComision != 0) {
										signo = comision.registroContable == "C" ? "-" : "";
									}
									montoComision = parseFloat(signo + montoComision.toFixed(4));
									switch (idConceptoComision) {
										case "1":
											comis1 += montoComision;
											break;
										case "2":
											comis2 += montoComision;
											break;
										case "3":
											comis3 += montoComision;
											break;
										case "4":
											comis4 += montoComision;
											break;
										case "5":
											comis5 += montoComision;
											break;
										case "6":
											comis6 += montoComision;
											break;
										case "9":
											comis9 += montoComision;
											break;
										case "10":
											comis10 += montoComision;
											break;
										case "11":
											comis11 += montoComision;
											break;
										case "12":
											comis12 += montoComision;
											break;
										case "13":
											comis13 += montoComision;
											break;
										case "14":
											comis14 += montoComision;
											break;
									}
								}
							});
							switch (idConceptoComision) {
								case "1":
									filas[++j] = comis1.formatMoney(4);
									break;
								case "2":
									filas[++j] = comis2.formatMoney(4);
									subTotal = parseFloat(subTotal) + parseFloat(comis2);
									break;
								case "3":
									filas[++j] = comis3.formatMoney(4);
									subTotal = parseFloat(subTotal) + parseFloat(comis3);
									break;
								case "4":
									filas[++j] = comis4.formatMoney(4);
									subTotal = parseFloat(subTotal) + parseFloat(comis4);
									break;
								case "5":
									filas[++j] = comis5.formatMoney(4);
									subTotal = parseFloat(subTotal) + parseFloat(comis5);
									break;
								case "6":
									filas[++j] = comis6.formatMoney(4);
									break;
								case "9":
									filas[++j] = comis9.formatMoney(4);
									subTotal = parseFloat(subTotal) + parseFloat(comis9);
									break;
								case "10":
									filas[++j] = comis10.formatMoney(4);
									subTotal = parseFloat(subTotal) + parseFloat(comis10);
									break;
								case "11":
									filas[++j] = comis11.formatMoney(4);
									subTotal = parseFloat(subTotal) + parseFloat(comis11);
									break;
								case "12":
									filas[++j] = comis12.formatMoney(4);
									subTotal = parseFloat(subTotal) + parseFloat(comis12);
									break;
								case "13":
									filas[++j] = comis13.formatMoney(4);
									subTotal = parseFloat(subTotal) + parseFloat(comis13);
									break;
								case "14":
									filas[++j] = comis14.formatMoney(4);
									subTotal = parseFloat(subTotal) + parseFloat(comis14);
									break;
							}
							filas[++j] = "</td><td class='dt-right monto comision'>";
						}
						filas[++j] = parseFloat(subTotal).formatMoney(4);
						filas[++j] = "</td></tr>";
					});
					var idTabla = $tablaMoneda.attr("idTabla");
					$local.tablasComisiones[idTabla].rows.add($(filas.join(''))).draw();
				});
			},
			complete: function() {
				$local.$buscar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$exportar.on('click', function() {
		if (!$formCriterioBusquedaReporte.valid()) {
			return;
		}
		var criterioBusqueda = $formCriterioBusquedaReporte.serializeJSON();
		criterioBusqueda.fechaProceso = $local.$rangoFechaBusqueda.data("daterangepicker").startDate.format('YYYY-MM-DD');
		criterioBusqueda.descripcionFechaProceso = $local.$rangoFechaBusqueda.val();
		criterioBusqueda.descripcionEmpresa = $local.$empresaSelect.find('option:selected').text();
		criterioBusqueda.descripcionCliente = $local.$clienteSelect.find('option:selected').text();
		criterioBusqueda.descripcionInstitucion = $local.$institucionesSelect.find('option:selected').text();
		var paramCriterioBusqueda = $.param(criterioBusqueda);
		$funcionUtil.webSocketMethod('cuadreBancoAdministradorResumen');
		window.location.href = $variableUtil.root + "reporte/comision/moneda?accion=exportar&" + paramCriterioBusqueda;
	});

});