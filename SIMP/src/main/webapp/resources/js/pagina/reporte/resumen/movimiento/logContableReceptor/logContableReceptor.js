$(document).ready(function() {

	var $local = {
		$membresias : $("#membresias"),
		$clasesServicio : $("#clasesServicio"),
		$rolesTransaccion : $("#rolesTransaccion"),
		$canales : $("#canales"),
		$transacciones : $("#transacciones"),
		$atms : $("#atms"),
		$rangoFechaBusqueda : $("#rangoFechaBusqueda"),
		$buscar : $("#buscar"),
		$tablaResumenLogContableReceptor : $("#tablaResumenLogContableReceptor"),
		tablaResumenLogContableReceptor : "",
		$encabezados : "",
		encabezadoComisionesArreglo : []
	};

	$funcionUtil.crearSelect2($local.$membresias);
	$funcionUtil.crearSelect2($local.$clasesServicio);
	$funcionUtil.crearSelect2($local.$rolesTransaccion);
	$funcionUtil.crearSelect2($local.$canales);
	$funcionUtil.crearSelect2($local.$transacciones);
	$funcionUtil.crearSelect2($local.$atms);
	$funcionUtil.crearDateRangePickerConsulta($local.$rangoFechaBusqueda);

	$formCriterioBusquedaReporte = $("#formCriterioBusquedaReporte");
	$local.$encabezados = $local.$tablaResumenLogContableReceptor.find("th.conceptoComision");

	for (var i = 0; i < $local.$encabezados.length; i++) {
		var conceptoComision = $local.$encabezados.filter("[name=" + i + "]").attr("idConceptoComision");
		$local.encabezadoComisionesArreglo.push(conceptoComision);
	}

	$local.$membresias.on("change", function() {
		var codigoMembresia = $(this).val();
		if (codigoMembresia == null || codigoMembresia == undefined || codigoMembresia == "") {
			$local.$clasesServicio.find("option").not("[value='']").remove();
			return;
		}
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "claseServicio/membresia/" + codigoMembresia,
			beforeSend : function(xhr) {
				$local.$clasesServicio.find("option").not("[value='']").remove();
				$local.$clasesServicio.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Clases de Servicio</span>")
				xhr.setRequestHeader('Content-Type', 'application/json');
			},
			statusCode : {
				400 : function(response) {
					$funcionUtil.limpiarMensajesDeError($formCriterioBusquedaReporte);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formCriterioBusquedaReporte);
				}
			},
			success : function(clasesServicio) {
				$.each(clasesServicio, function(i, claseServicio) {
					$local.$clasesServicio.append($("<option />").val(this.codigoClaseServicio).text($funcionUtil.unirCodigoDescripcion(this.codigoClaseServicio, this.descripcion)));
				});
			},
			error : function(response) {
			},
			complete : function(response) {
				$local.$clasesServicio.parent().find(".cargando").remove();
			}
		});
	});
	
	$local.tablaResumenLogContableReceptor = $local.$tablaResumenLogContableReceptor.DataTable({
		"initComplete" : function() {
			$local.$tablaResumenLogContableReceptor.wrap("<div class='table-responsive'></div>");
		},
		"language" : {
			"emptyTable" : "No se encontraron transacciones"
		},
		"footerCallback" : function(row, data, start, end, display) {
			var tieneData = $local.tablaResumenLogContableReceptor == "" ? false : $local.tablaResumenLogContableReceptor.data().any();
			var api = this.api(), data;
			api.columns('.comision').every(function() {
				var index = this.selector.cols;
				if (tieneData) {
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
				cantidadTotal = api.column(12).data().reduce(function(a, b) {
					return parseInt(a) + parseInt(b);
				}, 0);
				monto = api.column(13).data().reduce(function(a, b) {
					return parseFloat(a) + parseFloat(b);
				}, 0);
				$(api.column(12).footer()).html(cantidadTotal);
				$(api.column(13).footer()).html(monto.toFixed(2));
				$(api.column(13).footer()).removeClassRegex("color-");
				if (monto > 0) {
					$(api.column(13).footer()).addClass("color-blue");
				} else if (monto < 0) {
					$(api.column(13).footer()).addClass("color-red");
				} else {
					$(api.column(13).footer()).addClass("color-inherit");
				}
			} else {
				$(api.column(12).footer()).html("");
				$(api.column(13).footer()).html("");
			}			
		},
		"createdRow" : function(row, data, dataIndex) {
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

	$local.$buscar.on("click", function() {
		var criterioBusqueda = $formCriterioBusquedaReporte.serializeJSON();
		var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaBusqueda);
		criterioBusqueda.fechaInicioProceso = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaFinProceso = rangoFechaBusqueda.fechaFin;
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "reporte/resumen/movimiento/logContableReceptor?accion=buscar",
			data : criterioBusqueda,
			beforeSend : function() {
				$local.tablaResumenLogContableReceptor.clear().draw();
				$local.$buscar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success : function(reporteResumenLogContableReceptor) {
				if (reporteResumenLogContableReceptor.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				var filas = new Array();
				var j = -1;
				var finInicioCelda = "</td><td>";
				$.each(reporteResumenLogContableReceptor, function(i, reporte) {
					filas[++j] = "<tr><td>";
					filas[++j] = reporte.fechaProceso;
					filas[++j] = finInicioCelda;
					filas[++j] = $funcionUtil.unirCodigoDescripcion(reporte.codigoMembresia, reporte.descripcionMembresia);
					filas[++j] = finInicioCelda;
					filas[++j] = $funcionUtil.unirCodigoDescripcion(reporte.codigoClaseServicio, reporte.descripcionClaseServicio);
					filas[++j] = finInicioCelda;
					filas[++j] = $funcionUtil.unirCodigoDescripcion(reporte.rolTransaccion, reporte.descripcionRolTransaccion);
					filas[++j] = finInicioCelda;
					filas[++j] = $funcionUtil.unirCodigoDescripcion(reporte.idCanal, reporte.descripcionCanal);
					filas[++j] = finInicioCelda;
					filas[++j] = $funcionUtil.unirCodigoDescripcion(reporte.codigoOrigen, reporte.descripcionOrigen);
					filas[++j] = finInicioCelda;
					filas[++j] = $funcionUtil.unirCodigoDescripcion(reporte.idAtm, reporte.descripcionAtm);
					filas[++j] = finInicioCelda;
					filas[++j] = $funcionUtil.unirCodigoDescripcion(reporte.codigoTransaccion, reporte.descripcionCodigoTransaccion);
					filas[++j] = finInicioCelda;
					filas[++j] = $funcionUtil.unirCodigoDescripcion(reporte.codigoGiroNegocio, reporte.descripcionGiroNegocio);
					filas[++j] = finInicioCelda;
					filas[++j] = $funcionUtil.unirCodigoDescripcion(reporte.codigoInstitucionEmisor, reporte.descripcionInstitucionEmisor);
					filas[++j] = finInicioCelda;
					filas[++j] = $funcionUtil.unirCodigoDescripcion(reporte.codigoRespuesta, reporte.descripcionCodigoRespuesta);
					filas[++j] = finInicioCelda;
					filas[++j] = $funcionUtil.unirCodigoDescripcion(reporte.codigoMoneda, reporte.descripcionMoneda);
					filas[++j] = "</td><td class='dt-right'>";
					filas[++j] = reporte.cantidad;
					filas[++j] = "</td><td class='dt-right monto'>";
					filas[++j] = reporte.monto.toFixed(2);
					filas[++j] = "</td><td class='dt-right monto'>";
					var k = -1;
					var subTotal = parseFloat(reporte.monto).toFixed(2);
					for (var k = 0; k < $local.encabezadoComisionesArreglo.length; k++) {
						var montoComision = 0;
						var idConceptoComision = $local.encabezadoComisionesArreglo[k];
						$.each(reporte.comisiones, function(i, comision) {
							if (comision.idConceptoComision == idConceptoComision) {
								montoComision = comision.comision || 0;
								var signo = "";
								if (montoComision != 0) {
									signo = comision.registroContable == "C" ? "-" : "";
								}
								montoComision = parseFloat(signo + montoComision.toFixed(4));
								return false;
							}
						});
						filas[++j] = montoComision.toFixed(4);
						filas[++j] = "</td><td class='dt-right monto'>";
						subTotal = parseFloat(subTotal) + parseFloat(montoComision);
					}
					filas[++j] = parseFloat(subTotal).toFixed(4);
					filas[++j] = "</td></tr>";
				});
				$local.tablaResumenLogContableReceptor.rows.add($(filas.join(''))).draw();
			},
			complete : function() {
				$local.$buscar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});
});