var $funcionReporteUtil = {};

$(document).ready(function() {

	$funcionReporteUtil = {
		acumularTotales : function(tr_foot_total, td_numero_celda, cantidadTransaccion, pintar) {
			var td_seleccionado = tr_foot_total.find("td:eq(" + td_numero_celda + ")");
			var total_acumulado = td_seleccionado.text();
			total_acumulado = parseInt(total_acumulado == "" ? 0 : total_acumulado) + cantidadTransaccion;
			td_seleccionado.text(total_acumulado);
			if(pintar){
				$funcionReporteUtil.pintarCelda(tr_foot_total, td_numero_celda);
			}
				
		},
		construirEncabezadosTabla : function(tablaPartes, columnas_tr_membresia, codigoRespuestaTransaccion, membresias) {
			$.each(membresias, function(i, membresia) {
				var nombre = membresia.descripcionMembresia;
				var $th_membresia = $(tablaPartes.template_th_membresia).text(nombre);
				$th_membresia.attr({
					"id" : nombre,
					"data-orden" : i
				});
				tablaPartes.$tr_cantidades.append($(tablaPartes.template_th_cantidad));
				tablaPartes.$tr_cantidades.append("<th class='total_txn warning'>Total "+ nombre+"</th>")				
				$th_membresia.attr("colspan", 7);
				tablaPartes.$tr_membresia.append($th_membresia);
				
			});
			tablaPartes.$tr_foot_total.append($("<td></td>").multiply(columnas_tr_membresia + 1, true)).find("td:eq(0)").text("Total");
		},
		llenarTabla : function(reporteATMs, columnas_tr_membresia, tablaResultadoBusquedaCuerpo, tr_foot_total, cant_columna_por_tr_membresia) {
			var $funcionReporteUtil = this;
			$.each(reporteATMs, function(i, compensacion) {
				var $fila = $("<tr id='" + compensacion.id + "'></tr>");
				$fila.append($("<td></td>").multiplyReporteComp((columnas_tr_membresia + 1)/ cant_columna_por_tr_membresia, true));
				var descripcionCompensacion = $funcionUtil.unirCodigoDescripcion(compensacion.idAtm, compensacion.descripcionAtm);
				$fila.find("td:eq(0)").text(descripcionCompensacion);				
				$.each(compensacion.membresias, function(j, membresia) {
					var td_numero_celda = j * cant_columna_por_tr_membresia + 1;					
					$fila.find("td:eq(" + td_numero_celda + ")").text(membresia.cantidad);							
					$funcionReporteUtil.acumularTotales(tr_foot_total, td_numero_celda, membresia.cantidad, false);					
					$fila.find("td:eq(" + (td_numero_celda + 1) + ")").text((membresia.monto != null)? membresia.monto.toFixed(2):"");
					$funcionReporteUtil.acumularTotales(tr_foot_total, td_numero_celda + 1, membresia.monto, true);
					$funcionReporteUtil.pintarCelda($fila, td_numero_celda + 1);
					$fila.find("td:eq(" + (td_numero_celda + 2) + ")").text((membresia.comisionInt != null)? membresia.comisionInt.toFixed(4):"");
					$funcionReporteUtil.acumularTotales(tr_foot_total, td_numero_celda + 2, membresia.comisionInt, false);				
					$fila.find("td:eq(" + (td_numero_celda + 3) + ")").text((membresia.comisionGas != null)? membresia.comisionGas.toFixed(4):"");
					$funcionReporteUtil.acumularTotales(tr_foot_total, td_numero_celda + 3, membresia.comisionGas, false);
					$fila.find("td:eq(" + (td_numero_celda + 4) + ")").text((membresia.comisionOpe != null)? membresia.comisionOpe.toFixed(4):"");
					$funcionReporteUtil.acumularTotales(tr_foot_total, td_numero_celda + 4, membresia.comisionOpe, false);
					$fila.find("td:eq(" + (td_numero_celda + 5) + ")").text((membresia.comisionSur != null)? membresia.comisionSur.toFixed(4):"");
					$funcionReporteUtil.acumularTotales(tr_foot_total, td_numero_celda + 5, membresia.comisionSur, false);
					$fila.find("td:eq(" + (td_numero_celda + 6) + ")").text((membresia.totalMembresia != null)? membresia.totalMembresia.toFixed(4):"");
					$funcionReporteUtil.acumularTotales(tr_foot_total, td_numero_celda + 6, membresia.totalMembresia, true);					
					$funcionReporteUtil.pintarCelda($fila, td_numero_celda + 6);
				});
				tablaResultadoBusquedaCuerpo.append($fila);
			});
		},
		pintarCelda : function(tr_atm, td_numero_celda) {
			var td_seleccionado = tr_atm.find("td:eq(" + td_numero_celda + ")");
			var td_texto = td_seleccionado.text();			
			var valor = parseFloat(td_texto == "" ? 0 : td_texto);
			
			if (valor > 0) {
//				td_seleccionado.addClass("danger");
				td_seleccionado.css("color", "blue");
			} else if (valor < 0) {
				td_seleccionado.css("color", "red");
			} else {
				td_seleccionado.css("color", "inherited");
			}				
		}
	}

});

$.fn.multiplyReporteComp = function(numCopies, conClase) {
	var newElements = this.clone();
	conClase = conClase || false;
	for (var i = 1; i < numCopies; i++) {
		var cant, monto, int, gas, ope, sur, total;
		cant = this.clone();
		monto = this.clone();
		int = this.clone();
		gas = this.clone();
		ope = this.clone();
		sur = this.clone();
		total = this.clone();  
//		var newElement = this.clone();
		if (conClase) {
			cant.addClass("cantidad");
			monto.addClass("monto");
			int.addClass("comision");
			gas.addClass("comision");
			ope.addClass("comision");
			sur.addClass("comision");
			total.addClass("comision");
		}
		newElements = newElements.add(cant);
		newElements = newElements.add(monto);
		newElements = newElements.add(int);
		newElements = newElements.add(gas);
		newElements = newElements.add(ope);
		newElements = newElements.add(sur);
		newElements = newElements.add(total);
	}
	return newElements;
};