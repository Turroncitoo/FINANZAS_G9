/* Variable Global*/
var $formCriterioBusquedaConsulta = "";

$(document).ready(function() {

	var $local = {
		$usuarios : $("#usuarios"),
		$tiposEjecucion : $("#tiposEjecucion"),
		$rangoFechaBusqueda : $("#rangoFechaBusqueda"),
		$tablaLogControlProcesoAutomatico : $("#tablaLogControlProcesoAutomatico"),
		tablaLogControlProcesoAutomatico : "",
		$buscar : $("#buscar"),
		filtrosSeleccionables : {},
		$exportar : $("#exportar"),
		permisoDetalle: false,
		filtrosSeleccionablesTablaLogControlProgramaDetalle : {},
		arregloSiNo : [ "0", "1" ],
		/* Tabla Log Control Programa detalle */
		$tablaLogControlProgramaDetalle : $("#tablaLogControlProgramaDetalle"),
		tablaLogControlProgramaDetalle : "",
		$modalLogControlProgramaDetalle : $("#modalLogControlProgramaDetalle")
	};

	$funcionUtil.crearDatePickerSimple($local.$rangoFechaBusqueda, "DD/MM/YYYY");
	$local.permisoDetalle = $local.$tablaLogControlProcesoAutomatico.attr('data-permiso-detalle') || false;	

	$funcionUtil.crearSelect2($local.$usuarios);
	$funcionUtil.crearSelect2($local.$tiposEjecucion);

	$formCriterioBusquedaConsulta = $("#formCriterioBusquedaConsulta");

	$local.tablaLogControlProcesoAutomatico = $local.$tablaLogControlProcesoAutomatico.DataTable({
		"language" : {
			"emptyTable" : "No hay resultados encontrados"
		},
		"initComplete" : function() {
			$local.$tablaLogControlProcesoAutomatico.wrap("<div class='table-responsive'></div>");
			$local.filtrosSeleccionables["4"] = $variableUtil.arregloSiNo;
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaLogControlProcesoAutomatico, $local.filtrosSeleccionables)
		},
		"order" : [ [ 0, "desc" ] ],
		"columnDefs" : [ {
			"targets" : [ 0, 1, 2, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13 ],
			"className" : "all filtrable"
		}, {
			"className" : "all dt-center",
			"targets" : 14,
			"render" : function(data, type, row) {
				return ($local.permisoDetalle && row.ejecucionDetallada === true) ? $variableUtil.botonVerDetalle : "";
			}
		} ],
		"columns" : [ {
			"data" : 'idSecuencia',
			"title" : "Secuencia"
		}, {
			"data" : 'fechaProceso',
			"title" : "Fecha de Proceso"
		}, {
			"data" : "descripcionProcesoAutomatico",
			"title" : "Proceso Autom\u00e1tico"
		}, {
			"data" : "descripcionPrograma",
			"title" : "Programa"
		}, {
			"className" : "all seleccionable data-no-definida dt-center",
			"data" : function(row) {
				return $funcionUtil.insertarEtiquetaSiNo(row.estado);
			},
			"title" : "\u00c9xito de Ejecuci\u00f3n"
		}, {
			"data" : "mensaje",
			"title" : "Mensaje"
		}, {
			"data" : "registro",
			"title" : "Registros"
		}, {
			"data" : "idUsuario",
			"title" : "Usuario"
		}, {
			"data" : "fechaEjecucion",
			"title" : "Fecha de Ejecuci\u00f3n"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoInstitucion, row.descripcionInstitucion);
			},
			"title" : "Instituci\u00f3n"
		}, {
			"data" : 'horaInicio',
			"title" : "Hora Inicio"
		}, {
			"data" : 'horaFin',
			"title" : "Hora Fin"
		}, {
			"data" : 'tiempoProceso',
			"title" : "Tiempo de Proceso"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.tipoEjecucion, row.descripcionTipoEjecucion);
			},
			"title" : "Tipo de Ejecuci\u00f3n"
		}, {
			"data" : null,
			"title" : "Acci\u00f3n"
		} ]
	});

	$local.$tablaLogControlProcesoAutomatico.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaLogControlProcesoAutomatico.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaLogControlProcesoAutomatico.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaLogControlProcesoAutomatico.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	$local.$buscar.on("click", function() {
		if (!$formCriterioBusquedaConsulta.valid()) {
			return;
		}
		var criterioBusqueda = $formCriterioBusquedaConsulta.serializeJSON();
		criterioBusqueda.fechaProceso = $local.$rangoFechaBusqueda.data("daterangepicker").startDate.format('YYYY-MM-DD');
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "proceso/logControlPrograma?accion=buscar",
			data : criterioBusqueda,
			beforeSend : function(xhr) {
				$local.tablaLogControlProcesoAutomatico.clear().draw();
				$local.$buscar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success : function(logControlProgramas) {
				if (logControlProgramas.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				$local.tablaLogControlProcesoAutomatico.rows.add(logControlProgramas).draw();
			},
			complete : function() {
				$local.$buscar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});
	
	$local.$exportar.on("click", function() {
		if (!$formCriterioBusquedaConsulta.valid()) {
			return;
		}
		var criterioBusqueda = $formCriterioBusquedaConsulta.serializeJSON();
		criterioBusqueda.fechaProceso = $local.$rangoFechaBusqueda.data("daterangepicker").startDate.format('YYYY-MM-DD');
		criterioBusqueda.descripcionFechaProceso = $local.$rangoFechaBusqueda.val();
		criterioBusqueda.descripcionTipoEjecucion = $local.$tiposEjecucion.find("option:selected").text();
		criterioBusqueda.descripcionUsuario = $local.$usuarios.find("option:selected").text();
		var paramCriterioBusqueda = $.param(criterioBusqueda);
		window.location.href = $variableUtil.root + "proceso/logControlPrograma?accion=exportar&" + paramCriterioBusqueda;
	});

	/* Consulta Log Control Programa Detalle */

	$local.$modalLogControlProgramaDetalle.PopupWindow({
		autoOpen : false,
		modal : false,
		height : 440,
		width : 626,
	});

	$local.$modalLogControlProgramaDetalle.on("close.popupwindow", function() {
		$local.$tablaLogControlProgramaDetalle.find(".encabezadoFiltro").find(".form-control").val("").change();
	});

	$local.tablaLogControlProgramaDetalle = $local.$tablaLogControlProgramaDetalle.DataTable({
		"language" : {
			"emptyTable" : "No hay resultados encontrados"
		},
		"initComplete" : function() {
			$local.$tablaLogControlProgramaDetalle.wrap("<div class='table-responsive'></div>");
			$local.filtrosSeleccionablesTablaLogControlProgramaDetalle["2"] = $variableUtil.arregloSiNo;
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaLogControlProgramaDetalle, $local.filtrosSeleccionablesTablaLogControlProgramaDetalle)
		},
		"columnDefs" : [ {
			"orderData" : [ 0 ],
			"targets" : [ 1 ]
		} ],
		"columns" : [ {
			"data" : 'idItem',
			"searchable" : false,
			"visible" : false
		}, {
			"className" : "all filtrable",
			"data" : 'descripcionItem',
			"title" : "Descripci\u00f3n"
		}, {
			"className" : "all filtrable",
			"data" : 'mensaje',
			"title" : "Mensaje"
		}, {
			"className" : "all seleccionable data-no-definida",
			"data" : function(row) {
				return $funcionUtil.insertarEtiquetaSiNo(row.estado);
			},
			"title" : "\u00c9xito"
		} ]
	});

	$local.$tablaLogControlProgramaDetalle.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaLogControlProgramaDetalle.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaLogControlProgramaDetalle.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaLogControlProgramaDetalle.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	$local.$tablaLogControlProcesoAutomatico.children("tbody").on("click", ".ver-detalle", function() {
		var $botonVerDetalle = $(this);
		$local.$filaSeleccionada = $botonVerDetalle.parents("tr");
		var logControlPrograma = $local.tablaLogControlProcesoAutomatico.row($local.$filaSeleccionada).data();
		var idSecuencia = logControlPrograma.idSecuencia;
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "proceso/logControlPrograma/" + idSecuencia + "/detalle?accion=buscar",
			beforeSend : function(xhr) {
				$local.tablaLogControlProgramaDetalle.clear().draw();
				$botonVerDetalle.attr("disabled", true).find("i").removeClass("fa-eye").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader('Content-Type', 'application/json');
			},
			success : function(logControlProgramaDetalles) {
				if (logControlProgramaDetalles.length == 0) {
					$funcionUtil.notificarException("No existen detalles de ejecuci\u00f3n para este programa", "fa-exclamation-circle", "Informaci\u00f3n", "info");
					return;
				}
				$local.tablaLogControlProgramaDetalle.rows.add(logControlProgramaDetalles).draw();
				var descripcionPrograma = (logControlPrograma.descripcionPrograma == null) ? "No encontrado" : logControlPrograma.descripcionPrograma;
				$local.$modalLogControlProgramaDetalle.PopupWindow("setTitle", "Detalle de ejecuci\u00f3n: " + descripcionPrograma);
				$local.$modalLogControlProgramaDetalle.PopupWindow("open");
				$local.$modalLogControlProgramaDetalle.PopupWindow("maximize");
			},
			error : function(response) {
			},
			complete : function(response) {
				$botonVerDetalle.attr("disabled", false).find("i").addClass("fa-eye").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});
	
});