$(document).ready(function() {

	/* Variable Global */
	$formCriterioBusquedaAuditoria = $("#formCriterioBusquedaAuditoria");

	/* Variable Local */
	var $local = {
		$buscar : $("#buscar"),
		$rangoFechaBusqueda : $("#rangoFechaBusqueda"),
		$selectUsuario : $("#selectUsuario"),
		$selectTipoAuditoria : $("#selectTipoAuditoria"),
		$accionesAuditoria : $("#accionesAuditoria"),
		$tablaAuditoria : $("#tablaAuditoria"),
		tablaAuditoria : "",
		$seleccionUsuario : $("#seleccionUsuario"),
		$seleccionRecurso : $("#seleccionRecurso"),
		filtrosSeleccionables : {},
		arregloSiNo : [ "1", "0" ],
		$exportarPorCriterios : $("#exportar")
	};

	$funcionUtil.crearDateRangePickerSimple($local.$rangoFechaBusqueda);
	$funcionUtil.crearSelect2($local.$seleccionUsuario);
	$funcionUtil.crearSelect2($local.$seleccionRecurso);
	$funcionUtil.crearSelect2($local.$accionesAuditoria);

	$local.tablaAuditoria = $local.$tablaAuditoria.DataTable({
		"initComplete" : function() {
			$local.$tablaAuditoria.wrap("<div class='table-responsive'></div>");
			$local.filtrosSeleccionables["6"] = $variableUtil.arregloSiNo;
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaAuditoria, $local.filtrosSeleccionables);
		},
		"language" : {
			"emptyTable" : "No se han encontrado Auditor\u00edas para su b\u00fasqueda."
		},
		"columnDefs" : [ {
			"targets" : [ 0, 1, 2, 3, 4, 5 ],
			"className" : "all filtrable"
		} ],
		"ordering" : false,
		"columns" : [ {
			"data" : "fecha",
			"title" : 'Fecha'
		}, {
			"data" : "hora",
			"title" : 'Hora'
		}, {
			"data" : "nombreUsuario",
			"title" : "Usuario"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idRecurso, row.descripcionRecurso);
			},
			"title" : "Recurso"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idAccion, row.descripcionAccion);
			},
			"title" : "Acci\u00f3n"
		}, {
			"data" : "direccionIp",
			"title" : "Direcci\u00f3n IP"
		}, {
			"className" : "all seleccionable data-no-definida select2",
			"data" : function(row) {
				return $funcionUtil.insertarEtiquetaSiNo(row.exito);
			},
			"title" : "\u00c9xito de la operaci\u00f3n"
		}, {
			"className" : "all filtrable break-word",
			"data" : function(row) {
				return row.comentario.replace("[", "<b>[").replace("]", "]</b>");
			},
			"title" : "Comentario"
		}, {
			"className" : "all filtrable break-word",
			"data" : "logError",
			"title" : "Log Error"
		} ]
	});

	$local.$tablaAuditoria.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaAuditoria.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaAuditoria.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaAuditoria.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	$local.$buscar.on("click", function() {
		var criterioBusquedaAuditoria = $formCriterioBusquedaAuditoria.serializeJSON();
		var rangoFechaTxn = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaBusqueda);
		criterioBusquedaAuditoria.fechaInicio = rangoFechaTxn.fechaInicio;
		criterioBusquedaAuditoria.fechaFin = rangoFechaTxn.fechaFin;
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "seguridad/auditoria?accion=buscar",
			data : criterioBusquedaAuditoria,
			beforeSend : function(xhr) {
				$local.tablaAuditoria.clear().draw();
				$local.$buscar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success : function(auditorias) {
				if (auditorias.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				$local.tablaAuditoria.rows.add(auditorias).draw();
			},
			error : function() {
			},
			complete : function() {
				$local.$buscar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});
	
	$local.$exportarPorCriterios.on("click", function() {
		if (!$formCriterioBusquedaAuditoria.valid()) {
			return;
		}
		var criterioBusqueda = $formCriterioBusquedaAuditoria.serializeJSON();
		var rangoFechaTxn = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaBusqueda);
		criterioBusqueda.fechaInicio = rangoFechaTxn.fechaInicio;
		criterioBusqueda.fechaFin = rangoFechaTxn.fechaFin;
		var descripcionRangoFechas = $local.$rangoFechaBusqueda.val();
		criterioBusqueda.descripcionRangoFechas = descripcionRangoFechas == "" || descripcionRangoFechas == undefined ? "TODOS" : descripcionRangoFechas;
		criterioBusqueda.descripcionRecurso = $local.$seleccionRecurso.find(":selected").text().trim();
		criterioBusqueda.descripcionAccion = $local.$accionesAuditoria.find(":selected").text().trim();
		criterioBusqueda.descripcionIdUsuario = $local.$seleccionUsuario.find(":selected").text().trim();
		var paramCriterioBusqueda = $.param(criterioBusqueda);
		window.location.href = $variableUtil.root + "seguridad/auditoria?accion=exportar&" + paramCriterioBusqueda;
	});
});