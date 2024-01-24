var $variableReporteUtil = {};
var $funcionReporteUtil = {};
var $funcionReporteGraficoUtil = {};

$(document).ready(function() {

	var amChartPropiedadesTransaccionAprobada = {
		"type" : "serial",
		"categoryField" : "fecha",
		"rotate" : false,
		"marginTop" : 10,
		"startDuration" : 1,
		"categoryAxis" : {
			"gridAlpha" : 0.07,
			"axisColor" : "#DADADA",
			"startOnAxis" : false,
			"title" : "Fecha",
			"position" : "left"
		},
		"trendLines" : [],
		"graphs" : [],
		"guides" : [],
		"valueAxes" : [ {
			"id" : "ValueAxisAprobada-1",
			"position" : "top",
			"axisAlpha" : 0,
			"gridAlpha" : 0.07,
			"title" : "Transacciones"
		} ],
		"legend" : {
			"horizontalGap" : 10,
			"position" : "bottom",
			"valueFunction" : function(graphDataItem, valueText) {
				return (valueText == "" || valueText == undefined) ? 0 : valueText;
			},
			"useGraphSettings" : true,
			"markerSize" : 5,
			"marginTop" : 20,
			"periodValueText" : "[[value.sum]]",
			"borderAlpha" : 1
		},
		"allLabels" : [],
		"balloon" : {},
		"titles" : [],
		"dataProvider" : [],
		"pathToImages" : $variableUtil.root + "resources/image/amChart/",
		"chartCursor" : {
			"cursorPosition" : "mouse"
		},
		"chartScrollbar" : {
			"color" : "FFFFFF",
			"dragIcon" : "dragIconRoundBig.svg"
		},
		"zoomOutButtonImage" : "lens.svg",
		"export" : {
			"enabled" : true
		}
	};

	var amChartPropiedadesTransaccionRechazada = {
		"type" : "serial",
		"categoryField" : "fecha",
		"rotate" : false,
		"marginTop" : 10,
		"startDuration" : 1,
		"categoryAxis" : {
			"gridAlpha" : 0.07,
			"axisColor" : "#DADADA",
			"startOnAxis" : false,
			"title" : "Fecha",
			"position" : "left"
		},
		"trendLines" : [],
		"graphs" : [],
		"guides" : [],
		"valueAxes" : [ {
			"id" : "ValueAxisRechazada-1",
			"position" : "top",
			"axisAlpha" : 0,
			"gridAlpha" : 0.07,
			"title" : "Transacciones"
		} ],
		"legend" : {
			"horizontalGap" : 10,
			"position" : "bottom",
			"valueFunction" : function(graphDataItem, valueText) {
				return (valueText == "" || valueText == undefined) ? 0 : valueText;
			},
			"useGraphSettings" : true,
			"markerSize" : 5,
			"marginTop" : 20,
			"periodValueText" : "[[value.sum]]",
			"borderAlpha" : 1
		},
		"allLabels" : [],
		"balloon" : {},
		"titles" : [],
		"dataProvider" : [],
		"pathToImages" : $variableUtil.root + "resources/image/amChart/",
		"chartCursor" : {
			"cursorPosition" : "mouse"
		},
		"chartScrollbar" : {
			"color" : "FFFFFF",
			"dragIcon" : "dragIconRoundBig.svg"
		},
		"zoomOutButtonImage" : "lens.svg",
		"export" : {
			"enabled" : true
		}
	};

	var chartTransaccionRechazada = AmCharts.makeChart("graficoTransaccionRechazado", amChartPropiedadesTransaccionRechazada);
	var chartTransaccionAprobada = AmCharts.makeChart("graficoTransaccionAprobado", amChartPropiedadesTransaccionAprobada);

	$variableReporteUtil = {
		reporteAutorizacionGrafico : {},
		reporteAutorizacionGraficoRechazado : {},
		amGraphs : []
	};

	$funcionReporteGraficoUtil = {
		limpiarReporteAutorizacionGrafico : function() {
			$variableReporteUtil.reporteAutorizacionGrafico = {};
			$variableReporteUtil.reporteAutorizacionGraficoRechazado = {};
			$variableReporteUtil.amGraphs = [];
		},
		generarChartData : function(reporteAutorizacionGrafico) {
			var chartData = [];
			$.each(reporteAutorizacionGrafico, function(i, autorizacionGrafico) {
				chartData.push(autorizacionGrafico);
			});
			return chartData;
		},
		obtenerChartDataAprobadoORechazado : function() {
			return this.generarChartData($variableReporteUtil.reporteAutorizacionGrafico);
		},
		obtenerChartDataAprobadoYRechazado : function() {
			var chartDataAprobadoYRechazado = [];
			chartDataAprobadoYRechazado.push(this.generarChartData($variableReporteUtil.reporteAutorizacionGrafico));
			chartDataAprobadoYRechazado.push(this.generarChartData($variableReporteUtil.reporteAutorizacionGraficoRechazado));
			return chartDataAprobadoYRechazado;
		},
		insertarAmGraph : function(descripcionAutorizacion, identificador) {
			var amGraph = {
				"balloonText" : descripcionAutorizacion + ": <b>[[value]]</b>",
				"fillAlphas" : 0.8,
				"id" : "AmGraph-" + identificador,
				"lineAlpha" : 0.2,
				"title" : descripcionAutorizacion,
				"type" : "column",
				"valueField" : descripcionAutorizacion
			};
			$variableReporteUtil.amGraphs.push(amGraph);
		},
		construirAmChart : function(codigoRespuestaTransaccion, $transaccionAprobada, $transaccionRechazada) {
			switch (codigoRespuestaTransaccion) {
			case "0":
				chartTransaccionAprobada.dataProvider = this.obtenerChartDataAprobadoORechazado();
				chartTransaccionAprobada.graphs = $variableReporteUtil.amGraphs;
				chartTransaccionAprobada.validateData();
				$transaccionAprobada.removeClass("hidden");
				break;
			case "9998":
				chartTransaccionRechazada.dataProvider = this.obtenerChartDataAprobadoORechazado();
				chartTransaccionRechazada.graphs = $variableReporteUtil.amGraphs;
				chartTransaccionRechazada.validateData();
				$transaccionRechazada.removeClass("hidden");
				break;
			case "9999":
				var charDataAprobadoYRechazado = this.obtenerChartDataAprobadoYRechazado();
				var amGraphsCopia = JSON.parse(JSON.stringify($variableReporteUtil.amGraphs));
				chartTransaccionAprobada.dataProvider = charDataAprobadoYRechazado[0];
				chartTransaccionAprobada.graphs = $variableReporteUtil.amGraphs;
				chartTransaccionAprobada.validateData();
				$transaccionAprobada.removeClass("hidden");
				chartTransaccionRechazada.dataProvider = charDataAprobadoYRechazado[1];
				chartTransaccionRechazada.graphs = amGraphsCopia;
				chartTransaccionRechazada.validateData();
				$transaccionRechazada.removeClass("hidden");
				break;
			}
		}
	};

	$funcionReporteUtil = {
		acumularTotales : function(tr_foot_total, td_numero_celda, cantidadTransaccion) {
			var td_seleccionado = tr_foot_total.find("td:eq(" + td_numero_celda + ")");
			var total_acumulado = td_seleccionado.text();
			total_acumulado = parseInt(total_acumulado == "" ? 0 : total_acumulado) + cantidadTransaccion;
			td_seleccionado.text(total_acumulado);
		},
		obtenerPresentacionFechasPorTipoPresentacion : function(fecha_inicio, fecha_fin, tipo_presentacion, th_tipo_presentacion) {
			var intervalo = "";
			var presentacion = {
				fechas : [],
				formato_presentacion : ""
			};
			if (tipo_presentacion == $variableUtil.tipo_presentacion_mes) {
				fecha_inicio = moment(moment(fecha_inicio).format("YYYY-MM-01"));
				fecha_fin = moment(fecha_fin);
				intervalo = "month";
				presentacion.formato_presentacion = "MMMM, YYYY";
				th_tipo_presentacion.text("Meses");
			} else {
				fecha_inicio = moment(fecha_inicio);
				fecha_fin = moment(fecha_fin);
				intervalo = "day";
				presentacion.formato_presentacion = "DD MMMM, YYYY";
				th_tipo_presentacion.text("D\u00EDas");
			}
			while (!fecha_inicio.isAfter(fecha_fin)) {
				presentacion.fechas.push(fecha_inicio.format("YYYY-M-DD"));
				fecha_inicio.add(1, intervalo);
			}
			return presentacion;
		},
		construirEncabezadosTabla : function(tablaPartes, columnas_tr_mes_o_dia, codigoRespuestaTransaccion, fechas, formato_presentacion) {
			tablaPartes.$th_tipo_presentacion.attr("colspan", columnas_tr_mes_o_dia);
			$.each(fechas, function(i, mes) {
				var fecha = moment(mes);
				var fecha_con_formato = fecha.format(formato_presentacion);
				var fecha_YYYYMDD = fecha.format("YYYY-M-DD");
				var $th_mes_o_dia = $(tablaPartes.template_th_mes_o_dia).text(fecha_con_formato);
				$th_mes_o_dia.attr({
					"id" : fecha_YYYYMDD,
					"data-orden" : i
				});
				switch (codigoRespuestaTransaccion) {
				case "0":
					tablaPartes.$tr_codigo_respuesta_txn.append($(tablaPartes.template_th_aprobado));
					tablaPartes.$tr_cantidades.append($(tablaPartes.template_th_cantidad));
					$th_mes_o_dia.attr("colspan", 2);
					$variableReporteUtil.reporteAutorizacionGrafico[fecha_YYYYMDD] = {
						fecha : fecha_con_formato
					};
					break;
				case "9998":
					tablaPartes.$tr_codigo_respuesta_txn.append($(tablaPartes.template_th_rechazado));
					tablaPartes.$tr_cantidades.append($(tablaPartes.template_th_cantidad));
					$th_mes_o_dia.attr("colspan", 2);
					$variableReporteUtil.reporteAutorizacionGrafico[fecha_YYYYMDD] = {
						fecha : fecha_con_formato
					};
					break;
				case "9999":
					tablaPartes.$tr_codigo_respuesta_txn.append($(tablaPartes.template_th_aprobado));
					tablaPartes.$tr_codigo_respuesta_txn.append($(tablaPartes.template_th_rechazado));
					tablaPartes.$tr_cantidades.append($(tablaPartes.template_th_cantidad)).append($(tablaPartes.template_th_cantidad));
					$variableReporteUtil.reporteAutorizacionGrafico[fecha_YYYYMDD] = {
						fecha : fecha_con_formato
					};
					$variableReporteUtil.reporteAutorizacionGraficoRechazado[fecha_YYYYMDD] = {
						fecha : fecha_con_formato
					};
					break;
				}
				tablaPartes.$tr_mes_o_dia.append($th_mes_o_dia);
			});
			tablaPartes.$tr_foot_total.append($("<td></td>").multiply(columnas_tr_mes_o_dia + 1, true)).find("td:eq(0)").text("Total");
		},
		llenarTabla : function(reporteAutorizacion, columnas_tr_mes_o_dia, codigoRespuestaTransaccion, tablaResultadoBusquedaCuerpo, tr_foot_total, cant_columna_por_tr_mes_o_dia) {
			var $funcionReporteUtil = this;
			$.each(reporteAutorizacion, function(i, autorizacion) {
				var $fila = $("<tr id='" + autorizacion.id + "'></tr>");
				$fila.append($("<td></td>").multiply(columnas_tr_mes_o_dia + 1, true));
				var descripcionAutorizacion = $funcionUtil.unirCodigoDescripcion(autorizacion.id, autorizacion.descripcion);
				$fila.find("td:eq(0)").text(descripcionAutorizacion);
				$funcionReporteGraficoUtil.insertarAmGraph(descripcionAutorizacion, i + 1);
				$.each(autorizacion.transacciones, function(j, transaccion) {
					var diaMesAnioTransaccion = moment(transaccion.diaMesAnioTransaccion).format("YYYY-M-DD");
					var orden_tr_mes_o_dia = $("#" + diaMesAnioTransaccion).attr("data-orden");
					var td_numero_celda = parseInt(orden_tr_mes_o_dia) * cant_columna_por_tr_mes_o_dia + 1;
					if (codigoRespuestaTransaccion == 0 || codigoRespuestaTransaccion == 9998) {
						$fila.find("td:eq(" + td_numero_celda + ")").text(transaccion.cantidadTransaccion);
						$funcionReporteUtil.acumularTotales(tr_foot_total, td_numero_celda, transaccion.cantidadTransaccion);
						$variableReporteUtil.reporteAutorizacionGrafico[diaMesAnioTransaccion][descripcionAutorizacion] = transaccion.cantidadTransaccion;
					} else {
						if (transaccion.codigoRespuestaTransaccion == 0) {
							$fila.find("td:eq(" + td_numero_celda + ")").text(transaccion.cantidadTransaccion);
							$funcionReporteUtil.acumularTotales(tr_foot_total, td_numero_celda, transaccion.cantidadTransaccion);
							$variableReporteUtil.reporteAutorizacionGrafico[diaMesAnioTransaccion][descripcionAutorizacion] = transaccion.cantidadTransaccion;
						} else {
							$fila.find("td:eq(" + (td_numero_celda + 2) + ")").text(transaccion.cantidadTransaccion);
							$funcionReporteUtil.acumularTotales(tr_foot_total, td_numero_celda + 2, transaccion.cantidadTransaccion);
							$variableReporteUtil.reporteAutorizacionGraficoRechazado[diaMesAnioTransaccion][descripcionAutorizacion] = transaccion.cantidadTransaccion;
						}
					}
				});
				tablaResultadoBusquedaCuerpo.append($fila);
			});
			var td_numeros_celda = [];
			$.each(reporteAutorizacion, function(i, autorizacion) {
				var $fila = tablaResultadoBusquedaCuerpo.find("#" + autorizacion.id);
				$.each(autorizacion.transacciones, function(j, transaccion) {
					var diaMesAnioTransaccion = moment(transaccion.diaMesAnioTransaccion).format("YYYY-M-DD");
					var orden_tr_mes_o_dia = $("#" + diaMesAnioTransaccion).attr("data-orden");
					var td_numero_celda = parseInt(orden_tr_mes_o_dia) * cant_columna_por_tr_mes_o_dia + 1;
					if (codigoRespuestaTransaccion == 0 || codigoRespuestaTransaccion == 9998) {
						var total_fila = parseInt(tr_foot_total.find("td:eq(" + td_numero_celda + ")").text());
						$fila.find("td:eq(" + (td_numero_celda + 1) + ")").text((100 * transaccion.cantidadTransaccion / total_fila).toFixed(2) + "%");
					} else {
						if (transaccion.codigoRespuestaTransaccion == 0) {
							var total_fila = parseInt(tr_foot_total.find("td:eq(" + td_numero_celda + ")").text());
							$fila.find("td:eq(" + (td_numero_celda + 1) + ")").text((100 * transaccion.cantidadTransaccion / total_fila).toFixed(2) + "%");
						} else {
							var total_fila = parseInt(tr_foot_total.find("td:eq(" + (td_numero_celda + 2) + ")").text());
							$fila.find("td:eq(" + (td_numero_celda + 3) + ")").text((100 * transaccion.cantidadTransaccion / total_fila).toFixed(2) + "%");
						}
					}
				});
			});
			tr_foot_total.find("td").filter(".cantidad:not(:empty) + td").text("100%");
		},
		pintarColumnas : function(tablaResultadoBusquedaCuerpo, codigoRespuestaTransaccion) {
			switch (codigoRespuestaTransaccion) {
			case "0":
				tablaResultadoBusquedaCuerpo.find("td:nth-child(2n)").addClass("success");
				break;
			case "9998":
				tablaResultadoBusquedaCuerpo.find("td:nth-child(2n)").addClass("danger");
				break;
			case "9999":
				tablaResultadoBusquedaCuerpo.find("td:nth-child(4n-2)").addClass("success");
				tablaResultadoBusquedaCuerpo.find("td:nth-child(4n)").addClass("danger");
				break;
			}
		}
	}

});