$(document).ready(function() {

	var $local = {
		//Criterios busqueda
		$inputSecuencia: $('#numSecuencia'),
		$selectEstadoExtorno: $('#estadosExtornos'),
		$selectCodigoErrorUBA: $('#UBAErroresCode'),
		$selectCodigoErrorLocal: $('#localErroresCode'),
		$selectNombreServicio: $('#operaciones'),
		$fechaIN: $('#rangoFechaBusquedaIN'),
		//$fechaOUT: $('#rangoFechaBusquedaOUT'),

		//Botones
		$buscar: $("#buscar"),

		//Tabla
		$tablaResultados: $("#tablaConsultaServicioWeb"),
		tablaResultados: "",

		//Fila Seleccionada
		$filaSeleccionada: "",

		//modal
		$modalConsultaServicioWeb: $("#modalConsultaServicioWeb"),
		$exportarPorCriterios: $("#exportar"),
	};
	$funcionUtil.crearSelect2($local.$selectEstadoExtorno);
	$funcionUtil.crearSelect2($local.$selectCodigoErrorUBA);
	$funcionUtil.crearSelect2($local.$selectCodigoErrorLocal);
	$funcionUtil.crearSelect2($local.$selectNombreServicio);
	$funcionUtil.crearDateRangePickerConsulta($local.$fechaIN);

	$formCriterioBusquedaConsulta = $("#formCriterioBusqueda");

	$local.$modalConsultaServicioWeb.PopupWindow({
		title: "Detalle Consulta Servicio Web",
		autoOpen: false,
		modal: false,
		height: 600,
		width: 800,
	});

	$local.tablaResultados = $local.$tablaResultados.DataTable({
		"language": {
			"emptyTable": "No hay registros encontrados."
		},
		"initComplete": function() {
			$local.$tablaResultados.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaResultados);
		},
		"columnDefs": [{
			"targets": [1, 2, 3, 4, 5, 6, 7],
			"className": "all filtrable",
		}, {
			"targets": 8,
			"className": "all dt-center ",
			"render": function() {
				return $variableUtil.botonVerDetalle;
			}
		}],

		"columns": [
			{
				"data": "idEvento",
				"title": "Evento"
			}, {
				"data": "nombreServicio",
				"title": "Servicio"
			}, {
				"data": "secuencia",
				"title": "Secuencia"
			}, {
				"data": "fechaHoraIN",
				"title": "Fecha Hora Ingreso"
			}, {
				"data": "fechaHoraOUT",
				"title": "Fecha Hora Salida"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.UBAErrorCode, row.descripcionUBAErrorCode);
				},
				"title": "C\u00F3digo Error UBA"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.localErrorCode, row.descripcionLocalErrorCode);
				},
				"title": "C\u00F3digo Error Local"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.extornada, row.descripcionExtornada);
				},
				"title": "Extornada"
			}, {
				"data": null,
				"title": "Acci\u00F3n"
			}
		],
		"sorter": [{
			0: "asc"
		}]
	});

	/*$local.$tablaResultados.find("thead").on('keyup', 'input.filtrable', function() {
		var $tabla = $(this).parents('table');
		var idTabla = $tabla.attr("idTabla");
		var tabla = $local.tablasResultados[idTabla];
		tabla.column($(this).parent().index() + ':visible').search(this.value).draw();
	})*/;

	$local.$buscar.on('click', function() {

		if ($local.$fechaIN.val() === '') {
			$funcionUtil.notificarException("Seleccione una fecha de IN", "fa-exclamation-circle", "Informaci\u00F3n", "info");
			return;
		}

		var criterioBusqueda = $formCriterioBusquedaConsulta.serializeJSON();
		var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$fechaIN);

		criterioBusqueda.fechaInicioIN = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaFinIN = rangoFechaBusqueda.fechaFin;
		var paramCriterioBusqueda = $.param(criterioBusqueda);
		//paramCriterioBusqueda = paramCriterioBusqueda +"&extornada=" + $local.$selectEstadoExtorno.val()+"&nombreServicio=" + $local.$selectNombreServicio.val()+"&localErrorCode=" + $local.$selectCodigoErrorLocal.val()+"&UBAErrorCode=" + $local.$selectCodigoErrorUBA.val()+"&secuencia=" + $local.$inputSecuencia.val();
		$.ajax({

			type: "GET",
			url: $variableUtil.root + "prepago/consulta/resumen?accion=buscarPorCriterios&" + paramCriterioBusqueda,
			contentType: "application/json",
			dataType: "json",
			beforeSend: function(xhr) {
				xhr.setRequestHeader('Content-Type', 'application/json');

				$local.tablaResultados.clear().draw();

				$local.$buscar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success: function(response) {
				if (response.length === 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				$local.tablaResultados.rows.add(response).draw();



			},
			error: function(response) {
			},
			complete: function() {
				$local.$buscar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});

	});

	$local.$tablaResultados.children("tbody").on("click", ".ver-detalle", function() {

		$local.$filaSeleccionada = $(this).parents("tr");
		var fila = $local.tablaResultados.row($local.$filaSeleccionada).data();
		criterio = {
			"idEvento": fila.idEvento,
		}
		paramCriterioBusqueda = $.param(criterio);
		$.ajax({

			type: "GET",
			url: $variableUtil.root + "prepago/consulta/detalle?accion=buscarPorEvento&" + paramCriterioBusqueda,
			contentType: "application/json",
			dataType: "json",
			beforeSend: function(xhr) {
				xhr.setRequestHeader('Content-Type', 'application/json');

			},
			success: function(response) {
				if (response.length === 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}

				var detalleServicioWeb = {
					"idEvento": response[0].idEvento,
					"secuencia": response[0].secuencia,
					"nombreContexto": response[0].nombreContexto,
					"nombreServicio": response[0].nombreServicio,
					"fechaHoraIN": response[0].fechaHoraIN,
					"fechaHoraOUT": response[0].fechaHoraOUT,
					"fechaHoraRequest": response[0].fechaHoraRequest,
					"requestHash": response[0].requestHash,
					"requestJSON": response[0].requestJSON,
					"fechaHoraResponse": response[0].fechaHoraResponse,
					"responseHash": response[0].responseHash,
					"responseJSON": response[0].responseJSON,
					"UBAErrorCode": (response[0].UBAErrorCode + "-" + response[0].descripcionUBAErrorCode),
					"localErrorCode": (response[0].localErrorCode + "-" + response[0].descripcionLocalErrorCode),
					"extornada": (response[0].extornada + "-" + response[0].descripcionExtornada)
				};
				$('#txtRequestJSON').text(response[0].requestJSON);
				$('#txtResponseJSON').text(response[0].responseJSON);
				$funcionUtil.llenarFormulario(detalleServicioWeb, $local.$modalConsultaServicioWeb);
				$local.$modalConsultaServicioWeb.PopupWindow("open");

			},
			error: function(response) {
			},
			complete: function() {
				$local.$buscar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});

	});

	$local.$exportarPorCriterios.on("click", function() {

		var criterioBusqueda = $formCriterioBusquedaConsulta.serializeJSON();
		var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$fechaIN);
		criterioBusqueda.fechaInicioIN = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaFinIN = rangoFechaBusqueda.fechaFin;
		criterioBusqueda.nombreServicio = $local.$selectNombreServicio.find('option:selected').val();
		criterioBusqueda.extornada = $local.$selectEstadoExtorno.find('option:selected').val();
		criterioBusqueda.UBAErrorCode = $local.$selectCodigoErrorUBA.find('option:selected').val();
		criterioBusqueda.localErrorCode = $local.$selectCodigoErrorLocal.find('option:selected').val();
		var paramCriterioBusqueda = $.param(criterioBusqueda);
		window.location.href = $variableUtil.root + "prepago/consulta/exportacion?accion=exportarPorCriterios&" + paramCriterioBusqueda;
	});

});