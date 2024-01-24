$(document)
	.ready(
		function() {
			var $local = {
				// Criterios de busqueda
				$rangoFechaBusqueda: $("#periodo"),
				//$mesTransaccion : $('#periodo'),

				// botones
				$btnBuscar: $('#buscar'),
				$btnExportarResumen: $('#exportar'),
				$tablaFooter: $("#tablaFooter"),
				$tablaResultados: $("#tablaResultados"),
				tablaResultados: ''

			};
			/*
			var $formato = [
				{
					"institucion":"Institucion",
					"tarjetasActivadas":0.00.toFixed(2),
					"tarjetasRecargadas":0.00.toFixed(2),
					"porcentajeTarjetasRecargadas":"0.00 %",
					"promedioTarjetasRecargadas":0.00,
					"tarjetasEnUso":0.00,
					"porcentajeTarjetasEnUso":"0.00 %",
					"promedioTransacciones":0.00},
					{
					"institucion":"Institucion",
					"tarjetasActivadas":0.00.toFixed(2),
					"tarjetasRecargadas":0.00.toFixed(2),
					"porcentajeTarjetasRecargadas":"0.00 %",
					"promedioTarjetasRecargadas":0.00,
					"tarjetasEnUso":0.00,
					"porcentajeTarjetasEnUso":"0.00 %",
					"promedioTransacciones":0.00},
					{
					"institucion":"Institucion",
					"tarjetasActivadas":0.00.toFixed(2),
					"tarjetasRecargadas":0.00.toFixed(2),
					"porcentajeTarjetasRecargadas":"0.00 %",
					"promedioTarjetasRecargadas":0.00,
					"tarjetasEnUso":0.00,
					"porcentajeTarjetasEnUso":"0.00 %",
					"promedioTransacciones":0.00}
			]*/
			$funcionUtil.crearDateRangePickerConsulta($local.$rangoFechaBusqueda);

			$local.tablaResultados = $local.$tablaResultados.DataTable(
				{
					"language": {
						"emptyTable": "No hay registros encontrados."
					},
					"intiComplete": function() {
						$local.$tablaResultados.wrap("<div class='table-responsive'></div>");
						$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaResultados);
					},
					"columnDefs": [{
						"targets": 0,
						"className": "all filtrable",
					}, {
						"targets": [1, 2, 3, 4, 5, 6, 7],
						"className": "all filtrable dt-right",
					}],
					"columns": [
						{
							"data": function(row) {
								return $funcionUtil.unirCodigoDescripcion(row.idInstitucion, row.descripcionInstitucion)
							},
							"title": "Instituci\u00F3n"
						},
						{
							"data": "tarjetasActivadas",
							"title": "Tarjetas Activadas"
						},
						{
							"data": "tarjetasRecargadas",
							"title": "Tarjetas Recargadas"
						},
						{
							"data": "tarjetasEnUso",
							"title": "Tarjetas en Uso"
						},
						{
							"data": "recargaPromedio",
							"title": "Promedio Recargas"
						},
						{
							"data": "transaccionesPromedio",
							"title": "Promedio Transacciones"
						},
						{
							"data": row => {
								let porcentajeTarjetasRecargadas = ((row.tarjetasRecargadas / row.tarjetasActivadas) * 100).toFixed(2);

								if (Object.is(porcentajeTarjetasRecargadas, NaN) || Object.is(porcentajeTarjetasRecargadas, Infinity))

									porcentajeTarjetasRecargadas = (0 * 100).toFixed(2);

								return porcentajeTarjetasRecargadas + " %";
							},
							"title": "% Tarjetas Recargadas"
						},
						{
							"data": row => {
								let porcentajeTarjetasEnUso = ((row.tarjetasEnUso / row.tarjetasActivadas) * 100).toFixed(2);

								if (Object.is(porcentajeTarjetasEnUso, NaN) || Object.is(porcentajeTarjetasEnUso, Infinity))

									porcentajeTarjetasEnUso = (0 * 100).toFixed(2);

								return porcentajeTarjetasEnUso + " %";
							},
							"title": "% Tarjetas en Uso"
						}
					]
				});

			$local.$tablaResultados.find("thead").on('keyup', 'input.filtrable', function() {
				$local.tablaResultados.column($(this).parent().index() + ':visible').search(this.value).draw();
			});

			$local.$btnBuscar
				.on(
					'click',
					function() {
						var criterioBusqueda = {};
						var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaBusqueda);


						if (!$local.$rangoFechaBusqueda) {
							alert("Por favor ingrese una fecha v\u00E1lida");
						} else {

							let rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaBusqueda);

							let fechaInicio = rangoFechaBusqueda.fechaInicio;
							let fechaFin = rangoFechaBusqueda.fechaFin;

							fechaInicio = fechaInicio.split('-');
							fechaFin = fechaFin.split('-');


							let anioInicio = fechaInicio[0];
							let mesInicio = fechaInicio[1];
							let anioFin = fechaFin[0];
							let mesFin = fechaFin[1];





							/*
							var param = criterioBusqueda.fecha.split("-");
							
							
							if(param[1].charAt(0)==='0')
							{
							   param[1] = param[1].charAt(1);

							}
							*/
							param = "anioInicio=" + anioInicio + "&mesInicio=" + mesInicio + "&anioFin=" + anioFin + "&mesFin=" + mesFin;


							$.ajax({
								type: "GET",
								url: $variableUtil.root + "reporte/tarjetas/vendidas/resumen?accion=buscar&" + param,
								contentType: "application/json",
								dataType: "json",
								beforeSend: function(xhr) {
									xhr.setRequestHeader('Content-Type', 'application/json');
									$local.tablaResultados.clear().draw();
									$local.$btnBuscar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
									//$local.$tablaCabecera.empty();
									//$local.$tablaCuerpo.empty();
									$local.$tablaFooter.empty();
								},
								success: function(response) {

									//		$local.$tablaCabecera.append('<tr id="th_titulo" class="removible"><th class="text-center" rowspan="2">Categoria</th><th class="text-center" >57 - TPP</th><th class="text-center" >58 - TPP BENEFICIOS</th></tr>');


									if (response.length == 0) {
										$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-check", "Aviso", "info");
										return;
									} else {
										/*
										var sumaTarjetasRecargadas  = 0;
										var sumaTarjetasEnUso = 0;
										var sumaTarjetasActivadas = 0;
										
										response.forEach(function(r,i) {
										
											
											
											let porcentajeTarjetasRecargadas  = ((r.tarjetasRecargadas/r.tarjetasActivadas)*100).toFixed(2);
											let porcentajeTarjetasEnUso = ((r.tarjetasEnUso/r.tarjetasActivadas)*100).toFixed(2);
											
											
											
											sumaTarjetasRecargadas  += r.tarjetasRecargadas;
											sumaTarjetasEnUso += r.tarjetasEnUso;
											sumaTarjetasActivadas += r.tarjetasActivadas;
											
											
											if(Object.is(porcentajeTarjetasRecargadas , NaN) || Object.is(porcentajeTarjetasRecargadas , Infinity))
											
												porcentajesTarjetasRecargadas = (0*100).toFixed(2);
											
											if(Object.is(porcentajeTarjetasEnUso , NaN) || Object.is(porcentajeTarjetasEnUso , Infinity))
												
												porcentajeTarjetasEnUso = (0*100).toFixed(2);
											
											
											
											$formato[i].institucion = r.idInstitucion + ' - ' + r.descripcionInstitucion ;
											$formato[i].tarjetasActivadas = r.tarjetasActivadas;
											$formato[i].tarjetasEnUso = r.tarjetasEnUso;
											$formato[i].tarjetasRecargadas = r.tarjetasRecargadas;
											$formato[i].promedioTarjetasRecargadas = r.recargaPromedio;
											$formato[i].promedioTransacciones = r.transaccionesPromedio;
											$formato[i].porcentajeTarjetasEnUso = porcentajeTarjetasEnUso;
											$formato[i].porcentajeTarjetasRecargadas = porcentajeTarjetasRecargadas;
											
											
											$local.$tablaCuerpo.append('<tr><td>'+$formato[i].institucion+
													'</td><td class="text-right" >'+$formato[i].tarjetasActivadas+
													'</td><td class="text-right" >'+$formato[i].tarjetasRecargadas+
													'</td><td class="text-right" >'+$formato[i].porcentajeTarjetasRecargadas+
													' %</td><td class="text-right" >'+$formato[i].promedioTarjetasRecargadas+
													'</td><td class="text-right" >'+$formato[i].tarjetasEnUso+
													'</td><td class="text-right" >'+$formato[i].porcentajeTarjetasEnUso+
													' %</td><td class="text-right" >'+$formato[i].promedioTransacciones+
													'</td></tr>');
										});
										
										
										
										$local.$tablaFooter.append('<tr><td >Total</td>'+
													'<td class="text-right" >'+sumaTarjetasActivadas+
													'</td><td class="text-right" >'+sumaTarjetasRecargadas+
													'</td><td >'+
													'</td><td >'+
													'</td><td class="text-right" >'+sumaTarjetasEnUso+
													'</td><td >'+
													'</td><td >'+
													'</td></tr>');
	
									*/

										$local.tablaResultados.rows.add(response).draw();

										var sumaTarjetasRecargadas = 0;
										var sumaTarjetasEnUso = 0;
										var sumaTarjetasActivadas = 0;

										response.forEach(function(f) {
											sumaTarjetasEnUso += f.tarjetasEnUso;
											sumaTarjetasActivadas += f.tarjetasActivadas;
										});

										$local.$tablaFooter.append('<tr><td >Total</td>' +
											'<td class="text-right" >' + sumaTarjetasActivadas +
											'</td><td class="text-right" >' + sumaTarjetasRecargadas +
											'</td><td class="text-right" >' + sumaTarjetasEnUso +
											'</td><td >' +
											'</td><td >' +
											'</td><td >' +
											'</td><td >' +
											'</td></tr>');


									}
								},
								error: function(response) {
									$local.$btnBuscar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
									alert('Apareci\u00F3 un error inesperado');
								},
								complete: function() {
									$local.$btnBuscar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
								}
							});
						}

					});
			$local.$btnExportarResumen.on('click', function() {

				var criterioBusqueda = {};

				if (!$local.$rangoFechaBusqueda) {
					alert("Por favor ingrese una fecha valida");
				} else {
					/*						criterioBusqueda.fecha = $local.$mesTransaccion.val();
											
											var param = criterioBusqueda.fecha.split("-");
											
											if(param[1].charAt(0)==='0')
											   param[1] = param[1].charAt(1);
				
											param = "anio="+param[0]+ "&mes="+param[1];
						*/
					let rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaBusqueda);

					let fechaInicio = rangoFechaBusqueda.fechaInicio;
					let fechaFin = rangoFechaBusqueda.fechaFin;

					fechaInicio = fechaInicio.split('-');
					fechaFin = fechaFin.split('-');


					let anioInicio = fechaInicio[0];
					let mesInicio = fechaInicio[1];
					let anioFin = fechaFin[0];
					let mesFin = fechaFin[1];



					/*
					var param = criterioBusqueda.fecha.split("-");
					
					
					if(param[1].charAt(0)==='0')
					{
					   param[1] = param[1].charAt(1);

					}
					*/
					param = "anioInicio=" + anioInicio + "&mesInicio=" + mesInicio + "&anioFin=" + anioFin + "&mesFin=" + mesFin;

					window.location.href = $variableUtil.root + "reporte/tarjetas/vendidas/resumen?accion=exportar&" + param;
				}
			});
		});