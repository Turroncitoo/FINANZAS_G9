$(document).ready(function() {

	var $local = {
		$buscar : $("#buscar"),
		$navTabla : $("#navTabla"),
		$graficoOcultable : $(".graficoOcultable"),
		$transaccionAprobada : $(".transaccionAprobada"),
		$transaccionRechazada : $(".transaccionRechazada"),
		$graficoTransaccionAprobado : $("#graficoTransaccionAprobado"),
		$graficoTransaccionRechazado : $("#graficoTransaccionRechazado"),
		$resultadoBusqueda : $("#resultadoBusqueda"),
		$rangoFechas : $("#rangoFechaBusqueda"),
		$tablaResultadoBusqueda : $("#tablaResultadoBusqueda"),
		tablaResultadoBusqueda : "",
		$tablaResultadoBusquedaCuerpo : "",
		tablaPartes : {
			$tr_removible : "",
			$tr_mes_o_dia : $("#tr_mes_o_dia"),
			$th_tipo_presentacion : $("#th_tipo_presentacion"),
			$th_tipo_autorizacion : $("#th_tipo_autorizacion"),
			$tr_codigo_respuesta_txn : $("#tr_codigo_respuesta_txn"),
			$tr_cantidades : $("#tr_cantidades"),
			$tr_foot_total : $("#tr_foot_total"),
			template_th_mes_o_dia : "<th class='th_mes_o_dia' colspan='4'></th>",
			template_th_aprobado : "<th class='th_aprobado' colspan='2'>Aprobado</th>",
			template_th_rechazado : "<th class='th_rechazado' colspan='2'>Rechazado</th>",
			template_th_cantidad : "<th class='cantidad_txn'>Cantidad</th><th class='porcentaje_txn'>Porcentaje</th>"
		}
	};

	$local.tablaPartes.$th_tipo_autorizacion.text("ATMs");
	$local.$tablaResultadoBusquedaCuerpo = $local.$tablaResultadoBusqueda.find("tbody");
	$local.tablaPartes.$tr_removible = $local.$tablaResultadoBusqueda.find(".removible");

	$formCriterioBusquedaAutorizacion = $("#formCriterioBusquedaAutorizacion");

	$funcionUtil.crearDateRangePickerSimple($local.$rangoFechas);

	$local.$buscar.on("click", function() {
		if (!$formCriterioBusquedaAutorizacion.valid()) {
			return true;
		}
		var criterioBusquedaAutorizacion = $formCriterioBusquedaAutorizacion.serializeJSON();
		var fechaInicio = $local.$rangoFechas.data("daterangepicker").startDate;
		var fechaFin = $local.$rangoFechas.data("daterangepicker").endDate;
		if (criterioBusquedaAutorizacion.tipoPresentacion == "1" && fechaFin.diff(fechaInicio, 'days') + 1 > 15) {
			$funcionUtil.notificarException("En la presentacion por d\u00EDas, el intervalo de fecha de transacci\u00F3n no debe exceder los <b>15 d\u00EDas</b>.", "fa-check", "Aviso", "warning");
			return;
		}
		criterioBusquedaAutorizacion.fechaInicio = fechaInicio.format('YYYY-MM-DD');
		criterioBusquedaAutorizacion.fechaFin = fechaFin.format('YYYY-MM-DD');
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "reporte/autorizacion/atm?accion=buscar",
			data : criterioBusquedaAutorizacion,
			beforeSend : function() {
				$local.$navTabla.tab("show");
				$local.$resultadoBusqueda.addClass("hidden");
				$local.$graficoOcultable.addClass("hidden");
				$local.$buscar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
				if ($local.tablaResultadoBusqueda != "") {
					$local.tablaResultadoBusqueda.destroy();
					$local.tablaResultadoBusqueda = "";
				}
				$local.tablaPartes.$tr_removible.empty();
			},
			success : function(reporteATM) {
				if (reporteATM.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-check", "Aviso", "info");
				}
				$funcionReporteGraficoUtil.limpiarReporteAutorizacionGrafico();
				var presentacion = $funcionReporteUtil.obtenerPresentacionFechasPorTipoPresentacion(criterioBusquedaAutorizacion.fechaInicio, criterioBusquedaAutorizacion.fechaFin, criterioBusquedaAutorizacion.tipoPresentacion, $local.tablaPartes.$th_tipo_presentacion);
				var cant_columna_por_tr_mes_o_dia = (criterioBusquedaAutorizacion.codigoRespuestaTransaccion == 9999) ? 4 : 2;
				var columnas_tr_mes_o_dia = (cant_columna_por_tr_mes_o_dia * presentacion.fechas.length);
				$funcionReporteUtil.construirEncabezadosTabla($local.tablaPartes, columnas_tr_mes_o_dia, criterioBusquedaAutorizacion.codigoRespuestaTransaccion, presentacion.fechas, presentacion.formato_presentacion);
				$funcionReporteUtil.llenarTabla(reporteATM, columnas_tr_mes_o_dia, criterioBusquedaAutorizacion.codigoRespuestaTransaccion, $local.$tablaResultadoBusquedaCuerpo, $local.tablaPartes.$tr_foot_total, cant_columna_por_tr_mes_o_dia);
				$local.$resultadoBusqueda.removeClass("hidden");
				$funcionReporteUtil.pintarColumnas($local.$tablaResultadoBusqueda, criterioBusquedaAutorizacion.codigoRespuestaTransaccion);
				$local.$tablaResultadoBusqueda.find(".cantidad:empty").text("0");
				$local.$tablaResultadoBusqueda.find(".porcentaje:empty").text("0.00%");
				$local.tablaResultadoBusqueda = $local.$tablaResultadoBusqueda.DataTable({
					"scrollX" : true,
					"scrollCollapse" : true,
					"language" : {
						"emptyTable" : "No hay transacciones registradas"
					},
					"fixedColumns" : {
						"leftColumns" : 1
					}
				});
				$funcionReporteGraficoUtil.construirAmChart(criterioBusquedaAutorizacion.codigoRespuestaTransaccion, $local.$transaccionAprobada, $local.$transaccionRechazada);
			},
			error : function() {
			},
			complete : function() {
				$local.$buscar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

});