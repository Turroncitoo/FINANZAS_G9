$(document).ready(function() {

	var $local = {
		$tablaReporteLogControlProgramaResumen : $("#tablaLogControlProgramaResumen"),
		tablaReporteLogControlProgramaResumen : "",
		$tablaLogControlProgramaResumenDetalle: $("#tablaLogControlProgramaResumenDetalle"),
		$formResumen : $("#formResumen"),
		tablaLogControlProgramaResumenDetalle: "",
		$selectGrupo : $("#selectGrupo"),
		$selectPrograma : $("#selectPrograma"),
		$rangoFechaBusqueda : $("#rangoFechaBusqueda"),
		$buscar: $("#buscar"),
		$exportar : $("#exportar"),
		$modalDetalleConsulta : $("#modalDetalleConsulta")
	};
	
	$formBusquedaCriterios = $("#formBusquedaCriterios");

	$local.permisoDetalle = $local.$tablaReporteLogControlProgramaResumen.attr("data-permiso-detalle") || false;
	
	$funcionUtil.crearSelect2($local.$selectGrupo);
	$funcionUtil.crearSelect2($local.$selectPrograma);
	$funcionUtil.crearDateRangePickerSimple($local.$rangoFechaBusqueda);
	
	$local.$modalDetalleConsulta.PopupWindow({
		title : "Log Control Programa Resumen Detalle",
		autoOpen : false,
		modal : false,
		height : 400,
		width : 600,
	});
	
	$local.$selectGrupo.on("change", function(){
		var idGrupo = $(this).val();
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "proceso/mantenimiento/programa/grupo/"+idGrupo,
			beforeSend: function(xhr){
				$local.$selectPrograma.find("option:not(:eq(0))").remove();
				$local.$selectPrograma.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Programas</span>")
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode:{
				400 : function(response) {
					$funcionUtil.limpiarMensajesDeError($formBusquedaCriterios);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaCriterios);
				}
			},
			success: function(programas){
				$.each(programas, function(i, programa) {
					$local.$selectPrograma.append($("<option />").val(this.codigoPrograma).text(this.codigoPrograma + " - " + this.descripcion));
				});
			},
			error : function(response) {
			},
			complete : function(response) {
				$local.$selectPrograma.parent().find(".cargando").remove();
			}
		});
	});
	
	$.fn.dataTable.ext.errMode = 'none';

	$local.$tablaReporteLogControlProgramaResumen.on('xhr.dt', function(e, settings, json, xhr) {
		switch (xhr.status) {
		case 500:
			$local.tablaReporteLogControlProgramaResumen.clear().draw();
			break;
		}
	});

	$local.tablaReporteLogControlProgramaResumen = $local.$tablaReporteLogControlProgramaResumen.DataTable({
		"language" : {
			"emptyTable" : "No hay Registros encontrados."
		},
		"initComplete" : function() {
			$local.$tablaReporteLogControlProgramaResumen.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaReporteLogControlProgramaResumen);
		},
		"columnDefs" : [ {
			"targets" : [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ],
			"className" : "all filtrable"
		} ,{
			"targets" : 10,
			"className" : "all dt-center",
			"width" : "10%",
			"render" : function() {
				return  $variableUtil.botonVerDetalle;
			}
		}],
		"columns" : [ {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idGrupo, row.descripcionGrupo);
			},
			"title" : "Grupo"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idPrograma, row.descripcionPrograma);
			},
			"title" : "Programa"
		}, {
			"data" : "fechaProceso",
			"title" : "F. Proceso"
		},{
			"data" : "fechaInicio",
			"title" : "F. Inicio"
		},{
			"data" : "horaInicio",
			"title" : "H. Inicio"
		},{
			"data" : "fechaFin",
			"title" : "F. Fin"
		},{
			"data" : "horaFin",
			"title" : "H. Fin"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.estado, row.descripcionEstado);
			},
			"title" : "Estado"
		}, {
			"data" : "usuarioEjecucion",
			"title" : "Usuario Ejecuci\u00f3n"
		},{
			"data" : "fechaEjecucion",
			"title" : "F. Ejecuci\u00f3n"
		},{
			"data":"",
			"title":"Acci\u00f3n"
		} ]
	});
	
	$local.$tablaLogControlProgramaResumenDetalle.on('xhr.dt', function(e, settings, json, xhr) {
		switch (xhr.status) {
		case 500:
			$local.tablaReporteLogControlProgramaResumen.clear().draw();
			break;
		}
	});
	
	$local.tablaLogControlProgramaResumenDetalle = $local.$tablaLogControlProgramaResumenDetalle.DataTable({
		"language" : {
			"emptyTable" : "No hay Registros encontrados."
		},
		"initComplete" : function() {
			$local.$tablaLogControlProgramaResumenDetalle.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaLogControlProgramaResumenDetalle);
		},
		"columnDefs":[{
			"targets":[0,1,2,3,4,5,6,7,8,9,10],
			"className":"all filtrable"
		}],
		"columns":[{
			"data": "secuencia",
			"title":"Secuencia"
		},{
			"data":function(row){
				return $funcionUtil.unirCodigoDescripcion(row.estado, row.descripcionEstado);
			},
			"title":"Estado"
		},{
			"data": "mensaje",
			"title":"Mensaje"
		},{
			"data": "numeroRegistros",
			"title": "Nro. Registros"
		},{
			"data":"usuarioEjecucion",
			"title":"Usuario Ejecuci\u00f3n"
		},{
			"data":"fechaEjecucion",
			"title": "F. Ejecuci\u00f3n"
		},{
			"data": function(row){
				return $funcionUtil.unirCodigoDescripcion(row.idInstitucion, row.descripcionInstitucion);
			},
			"title": "Instituci\u00f3n"
		},{
			"data": "horaInicio",
			"title": "H. Inicio"
		},{
			"data": "horaFinal",
			"title": "H. Fin"
		},{
			"data":"tiempoProceso",
			"title": "Tiempo Proceso"
		},{
			"data": function(row){
				return $funcionUtil.unirCodigoDescripcion(row.tipoEjecucion, row.descripcionTipoEjecucion);
			},
			"title": "Tipo Ejecuci\u00f3n"
		}]
	});

	$local.$tablaReporteLogControlProgramaResumen.find("thead").on("keyup", "input", function() {
		$local.tablaReporteLogControlProgramaResumen.column($(this).parent().index() + ":visible").search(this.value).draw();
	});
	
	$local.$tablaLogControlProgramaResumenDetalle.find("thead").on("keyup", "input", function() {
		$local.tablaLogControlProgramaResumenDetalle.column($(this).parent().index() + ":visible").search(this.value).draw();
	});
	
	$local.$buscar.on("click", function(){
		if(!$formBusquedaCriterios.valid()){
			return;
		}
		var criterioBusqueda = $formBusquedaCriterios.serializeJSON();
		var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaBusqueda);
		criterioBusqueda.fechaInicio = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaFin = rangoFechaBusqueda.fechaFin;

		$.ajax({
			type : "GET",
			url : $variableUtil.root + "reporte/logControlPrograma/resumen?accion=buscarPorCriterios",
			data : criterioBusqueda,
			statusCode : {
				400 : function(response) {
					$funcionUtil.limpiarMensajesDeError($formBusquedaCriterios);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaCriterios);
				}
			},
			beforeSend : function() {
				$local.tablaReporteLogControlProgramaResumen.clear().draw();
				$local.$buscar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success : function(response) {
				if (response.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00f3n", "info");
					return;
				}
				$local.tablaReporteLogControlProgramaResumen.rows.add(response).draw();
			},
			complete : function() {
				$local.$buscar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$exportar.on("click", function(){
		if(!$formBusquedaCriterios.valid()){
			return;
		}
		var criterioBusqueda = $formBusquedaCriterios.serializeJSON();
		var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaBusqueda);
		criterioBusqueda.fechaInicio = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaFin = rangoFechaBusqueda.fechaFin;
		criterioBusqueda.descripcionGrupo = $local.$selectGrupo.find("option:selected").text();
		criterioBusqueda.descripcionPrograma = $local.$selectPrograma.find("option:selected").text();
		var descripcionRangoFechas = $local.$rangoFechaBusqueda.val();
		criterioBusqueda.descripcionRangoFechas = descripcionRangoFechas == "" || descripcionRangoFechas == undefined ? "TODOS" : descripcionRangoFechas;
		var paramCriterioBusqueda = $.param(criterioBusqueda);
		window.location.href = $variableUtil.root + "reporte/logControlPrograma/resumen?accion=exportarPorCriterios&"+ paramCriterioBusqueda;
	});
	
	$local.$tablaReporteLogControlProgramaResumen.children("tbody").on("click", ".ver-detalle", function() {
		var $botonVerDetalle = $(this);
		$local.$filaSeleccionada = $botonVerDetalle.parents("tr");
		var resumen = $local.tablaReporteLogControlProgramaResumen.row($local.$filaSeleccionada).data();
		var arrDate = resumen.fechaProceso.split("/");
		var criterioBusqueda = {
			"idGrupo" : resumen.idGrupo,
			"idPrograma": resumen.idPrograma,
			"fechaProceso": moment(Date.parse(arrDate[1]+"/"+arrDate[0]+"/"+arrDate[2])).format("YYYY-MM-DD")
		}
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "reporte/logControlPrograma/detalle?accion=buscarPorCriterios",
			data : criterioBusqueda,
			beforeSend : function(xhr) {
				$botonVerDetalle.attr("disabled", true).find("i").removeClass("fa-eye").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader('Content-Type', 'application/json');
				$local.tablaLogControlProgramaResumenDetalle.clear().draw();
			},
			success : function(detalles) {
				if (detalles.length == 0) {
					$funcionUtil.notificarException("La B\u00fasqueda no encontr\u00f3 ninguna coincidencia en la Base de Datos.", "fa-exclamation-circle", "Informaci\u00F3n", "info");
				} else {
					$funcionUtil.llenarFormularioDeLectura({
						"descripcionGrupo": $funcionUtil.unirCodigoDescripcion(detalles[0].idGrupo, detalles[0].descripcionGrupo),
						"descripcionPrograma": $funcionUtil.unirCodigoDescripcion(detalles[0].idPrograma, detalles[0].descripcionPrograma),
						"fechaProceso": detalles[0].fechaProceso
					}, $local.$formResumen);
					$local.tablaLogControlProgramaResumenDetalle.rows.add(detalles).draw();
					$local.$modalDetalleConsulta.PopupWindow("open");
					$local.$modalDetalleConsulta.PopupWindow("maximize");
				}
			},
			complete : function() {
				$botonVerDetalle.attr("disabled", false).find("i").addClass("fa-eye").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});
	
});