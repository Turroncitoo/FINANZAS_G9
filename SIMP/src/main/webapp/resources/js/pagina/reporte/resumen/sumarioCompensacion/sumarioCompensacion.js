$(document).ready(function() {

	var $local = {
		$rangoFechaBusqueda : $("#rangoFechaBusqueda"),
		$buscar : $("#buscar"),
		$resultadoBusqueda : $("#resultadoBusqueda"),
		$tablaComisiones : $("table.tablaComisiones"),
		tablaComisiones : "",
		tablasComisiones : {},
		$encabezados : "",
		encabezadoComisionesArreglo : [],
		$exportar: $('#exportar'),
		$instituciones: $('#instituciones'), 
		formatoComision : $.fn.dataTable.render.number(',', '.', 4).display,
		formatoEntero : $.fn.dataTable.render.number(',', '.', 0).display
	};
	
	$formCriterioBusquedaReporte = $('#formCriterioBusquedaReporte');

	$funcionUtil.crearSelect2($local.$instituciones);
	$funcionUtil.crearDatePickerSimple($local.$rangoFechaBusqueda, "DD/MM/YYYY");

	$('a[data-toggle="tab"]').on('shown.bs.tab', function(e) {
		$.fn.dataTable.tables({
			visible : true,
			api : true
		}).columns.adjust();
	});

	$local.$tablaComisiones.filter(function() {
		var tablaComision = $(this);
		var idTabla = tablaComision.attr("idTabla");
		$local.tablasComisiones[idTabla] = tablaComision.DataTable({
			"initComplete" : function() {
				tablaComision.wrap("<div class='table-responsive'></div>");
			},
			"language": {
	            "decimal": ".",
	            "thousands": ",",
	            "emptyTable" : "No hay transacciones encontradas."
	        },
			"ordering": false,
			"columnDefs" : [{
				"targets" : [3],
				"className" : "all dt-right"
			}, {
				"targets" : [4, 6, 8, 10],
				"className" : "all dt-right monto deudor"
			}, {
				"targets" : [5, 7, 9, 11],
				"className" : "all dt-right monto acreedor"
			}, {
				"targets" : [12],
				"className" : "all dt-right monto"
			} ],
			"columns" : [ {
				"data": function(row){
					return $funcionUtil.unirCodigoDescripcion(row.idInstitucion, row.descripcionInstitucion);
				},
				"title": "Instituci\u00f3n"
			},{
				"data" : function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.codigoMembresia, row.descripcionMembresia);
				},
				"title" : "Membres\u00eda"
			}, {
				"data" : function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.codigoClaseServicio, row.descripcionClaseServicio);
				},
				"title" : "Clase servicio"
			}, {
				"data" : "cantidad",
				"title" : "Cantidad"
			}, {
				"data" : function(row) {
					return (row.montoFondoDeudor*-1).toFixed(4);
				},
				"render": $.fn.dataTable.render.number(',', '.', 4)
			}, {
				"data" : function(row) {
					return (row.montoFondoAcreedor).toFixed(4);
				},
				"render": $.fn.dataTable.render.number(',', '.', 4)
			}, {
				"data" : function(row) {
					return (row.montoComisionDeudor*-1).toFixed(4);
				},
				"render": $.fn.dataTable.render.number(',', '.', 4)
			}, {
				"data" : function(row) {
					return (row.montoComisionAcreedor).toFixed(4);
				},
				"render": $.fn.dataTable.render.number(',', '.', 4)
			}, {
				"data" : function(row) {
					var montoTotal = row.montoFondoDeudor + row.montoFondoAcreedor 
									+ row.montoComisionDeudor + row.montoComisionAcreedor;
					return (montoTotal < 0 ? montoTotal*-1 : 0).toFixed(4);
				},
				"render": $.fn.dataTable.render.number(',', '.', 4)
			}, {
				"data" : function(row) {
					var montoTotal = row.montoFondoDeudor + row.montoFondoAcreedor 
								+ row.montoComisionDeudor + row.montoComisionAcreedor;
					return (montoTotal > 0 ? montoTotal : 0).toFixed(4);
				},
				"render": $.fn.dataTable.render.number(',', '.', 4)
			}, {
				"data" : function(row) {
					return (row.montoMiscelaneoDeudor*-1).toFixed(4);
				},
				"render": $.fn.dataTable.render.number(',', '.', 4)
			}, {
				"data" : function(row) {
					return (row.montoMiscelaneoAcreedor).toFixed(4);
				},
				"render": $.fn.dataTable.render.number(',', '.', 4)
			}, {
				"createdCell": function (td, cellData, rowData, row, col) {
					if ( cellData < 0 ) {
						$(td).addClass("color-red");
					} else if (cellData > 0) {
						$(td).addClass("color-blue");
					} else {
						$(td).addClass("color-inherit");
					}
			    },
				"data" : function(row) {
					var montoNeto = row.montoFondoAcreedor + row.montoFondoDeudor 
									+ row.montoComisionAcreedor + row.montoComisionDeudor 
									+ row.montoMiscelaneoAcreedor + row.montoMiscelaneoDeudor;
					return (montoNeto).toFixed(4);
				},
				"render": function(data, type, row) {
					var montoNeto = row.montoFondoAcreedor + row.montoFondoDeudor 
									+ row.montoComisionAcreedor + row.montoComisionDeudor 
									+ row.montoMiscelaneoAcreedor + row.montoMiscelaneoDeudor;
					return $local.formatoComision((montoNeto < 0 ? montoNeto*-1 : montoNeto).toFixed(4))
				}
			}],
			"footerCallback" : function(row, data, start, end, display) {
				var api = this.api();
				var tieneData = data.length > 0;
				if (tieneData) {
					cantidadTotal = api.column(3).data().reduce(function(a, b) {
						return parseInt(a) + parseInt(b);
					}, 0);
					montoFondoDeudor = api.column(4).data().reduce(function(a, b) {
						return parseFloat(a) + parseFloat(b);
					}, 0);
					montoFondoAcreedor = api.column(5).data().reduce(function(a, b) {
						return parseFloat(a) + parseFloat(b);
					}, 0);
					montoComisionDeudor = api.column(6).data().reduce(function(a, b) {
						return parseFloat(a) + parseFloat(b);
					}, 0);
					montoComisionAcreedor = api.column(7).data().reduce(function(a, b) {
						return parseFloat(a) + parseFloat(b);
					}, 0);
					montoTotalDeudor = api.column(8).data().reduce(function(a, b) {
						return parseFloat(a) + parseFloat(b);
					}, 0);
					montoTotalAcreedor = api.column(9).data().reduce(function(a, b) {
						return parseFloat(a) + parseFloat(b);
					}, 0);
					montoMiscelaneoDeudor = api.column(10).data().reduce(function(a, b) {
						return parseFloat(a) + parseFloat(b);
					}, 0);
					montoMiscelaneoAcreedor = api.column(11).data().reduce(function(a, b) {
						return parseFloat(a) + parseFloat(b);
					}, 0);
					montoNeto = api.column(12).data().reduce(function(a, b) {
						return parseFloat(a) + parseFloat(b);
					}, 0);
					$(api.column(3).footer()).html($local.formatoEntero(cantidadTotal));
					$(api.column(4).footer()).html($local.formatoComision(montoFondoDeudor.toFixed(4)));
					$(api.column(5).footer()).html($local.formatoComision(montoFondoAcreedor.toFixed(4)));
					$(api.column(6).footer()).html($local.formatoComision(montoComisionDeudor.toFixed(4)));
					$(api.column(7).footer()).html($local.formatoComision(montoComisionAcreedor.toFixed(4)));
					$(api.column(8).footer()).html($local.formatoComision(montoTotalDeudor.toFixed(4)));
					$(api.column(9).footer()).html($local.formatoComision(montoTotalAcreedor.toFixed(4)));
					$(api.column(10).footer()).html($local.formatoComision(montoMiscelaneoDeudor.toFixed(4)));
					$(api.column(11).footer()).html($local.formatoComision(montoMiscelaneoAcreedor.toFixed(4)));
					$(api.column(12).footer()).html($local.formatoComision((montoNeto < 0 ? montoNeto*-1 : montoNeto).toFixed(4)));
					$(api.column(4).footer()).removeClassRegex("color-");
					$(api.column(5).footer()).removeClassRegex("color-");
					$(api.column(6).footer()).removeClassRegex("color-");
					$(api.column(7).footer()).removeClassRegex("color-");
					$(api.column(8).footer()).removeClassRegex("color-");
					$(api.column(9).footer()).removeClassRegex("color-");
					$(api.column(10).footer()).removeClassRegex("color-");
					$(api.column(11).footer()).removeClassRegex("color-");
					$(api.column(12).footer()).removeClassRegex("color-");
					if (montoFondoDeudor != 0) {
						$(api.column(4).footer()).addClass("color-red");
					} else {
						$(api.column(4).footer()).addClass("color-inherit");
					}
					if (montoFondoAcreedor != 0) {
						$(api.column(5).footer()).addClass("color-blue");
					} else {
						$(api.column(5).footer()).addClass("color-inherit");
					}
					if (montoComisionDeudor != 0) {
						$(api.column(6).footer()).addClass("color-red");
					} else {
						$(api.column(6).footer()).addClass("color-inherit");
					}
					if (montoComisionAcreedor != 0) {
						$(api.column(7).footer()).addClass("color-blue");
					} else {
						$(api.column(7).footer()).addClass("color-inherit");
					}
					if (montoMiscelaneoDeudor != 0) {
						$(api.column(10).footer()).addClass("color-red");
					} else {
						$(api.column(10).footer()).addClass("color-inherit");
					}
					if (montoMiscelaneoAcreedor != 0) {
						$(api.column(11).footer()).addClass("color-blue");
					} else {
						$(api.column(11).footer()).addClass("color-inherit");
					}
					if (montoTotalDeudor != 0) {
						$(api.column(8).footer()).addClass("color-red");
					} else {
						$(api.column(8).footer()).addClass("color-inherit");
					}
					if (montoTotalAcreedor != 0) {
						$(api.column(9).footer()).addClass("color-blue");
					} else {
						$(api.column(9).footer()).addClass("color-inherit");
					}
					if (montoNeto < 0) {
						$(api.column(12).footer()).addClass("color-red");
					} else if (montoNeto > 0) {
						$(api.column(12).footer()).addClass("color-blue");
					} else {
						$(api.column(12).footer()).addClass("color-inherit");
					}
				} else {
					$(api.column(3).footer()).html("");
					$(api.column(4).footer()).html("");
					$(api.column(5).footer()).html("");
					$(api.column(6).footer()).html("");
					$(api.column(7).footer()).html("");
					$(api.column(8).footer()).html("");
					$(api.column(9).footer()).html("");
					$(api.column(10).footer()).html("");
					$(api.column(11).footer()).html("");
					$(api.column(12).footer()).html("");
				}
			},
			"createdRow" : function(row, data, dataIndex) {
				$(row).find(".monto.acreedor").filter(function() {
					var celda = $(this);
					var valor = parseFloat(celda.text());
					if (valor != 0) {
						celda.addClass("color-blue");
					} else {
						celda.addClass("color-inherit");
					}
				});
				$(row).find(".monto.deudor").filter(function() {
					var celda = $(this);
					var valor = parseFloat(celda.text());
					if (valor != 0) {
						celda.addClass("color-red");
					} else {
						celda.addClass("color-inherit");
					}
				});
			}
		});
	});

	$local.$buscar.on("click", function() {
		var criterioBusqueda = $formCriterioBusquedaReporte.serializeJSON();
		criterioBusqueda.fechaProceso = $local.$rangoFechaBusqueda.data("daterangepicker").startDate.format('YYYY-MM-DD');
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "reporte/resumen/sumario-compensacion?accion=buscar",
			data : criterioBusqueda,
			dataType: "json",
			beforeSend : function(xhr) {
				$.each($local.tablasComisiones, function(i, tablaComision) {
					$local.tablasComisiones[i].clear().draw();
				});
				$local.$buscar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success : function(reporteMonedas) {
				if (reporteMonedas.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00f3n", "info");
					return;
				}
				$.each(reporteMonedas, function(i, reporteMoneda){
					var id = "tabla"+reporteMoneda.codigoMoneda;
					var tablaSumarioCompensacion = $local.tablasComisiones[id];
					tablaSumarioCompensacion.rows.add(reporteMoneda.sumarioCompensaciones).draw();
				});
			},
			complete : function() {
				$local.$buscar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			},
			error: function(error){
				$local.$buscar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});
		
	$local.$exportar.on('click', function(){
		var criterioBusqueda = $formCriterioBusquedaReporte.serializeJSON();
		criterioBusqueda.fechaProceso = $local.$rangoFechaBusqueda.data("daterangepicker").startDate.format('YYYY-MM-DD');
		criterioBusqueda.descripcionFechaProceso = $local.$rangoFechaBusqueda.val();
		criterioBusqueda.descripcionInstitucion = $local.$instituciones.find('option:selected').text();
		var paramCriterioBusqueda = $.param(criterioBusqueda);
		window.location.href = $variableUtil.root + "reporte/resumen/sumario-compensacion?rutaEnSidebar=" + $variableUtil.rutaEnSidebar + "&accion=exportar&" + paramCriterioBusqueda;
	});
	
});