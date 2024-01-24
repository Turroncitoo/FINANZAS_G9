$(document).ready(function() {
	var $local = {
		$selectMembresias: $("#selectMembresias"),
		$selectBines: $("#selectBines"),
		$selectAfinidades: $("#selectAfinidades"),

		$fechaAfiliacion: $("#fechaAfiliacion"),

		$tablaResultados: $("#tablaResultados"),
		tablaResultados: "",

		$btnBuscar: $("#buscar"),
		$btnExportar: $("#exportar")
	};

	$funcionUtil.crearDateRangePickerConsulta($local.$fechaAfiliacion);
	$funcionUtil.crearSelect2Multiple($local.$selectMembresias, "", "TODOS");
	$funcionUtil.crearSelect2Multiple($local.$selectBines, "", "TODOS");
	$funcionUtil.crearSelect2Multiple($local.$selectAfinidades, "", "TODOS");

	// TABLA
	$local.tablaResultados = $local.$tablaResultados.DataTable({
		"language": {
			"emptyTable": "No hay tarjetas generadas"
		},
		"initComplete": function() {
			$local.$tablaResultados.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaResultados);
		},
		"columnDefs": [
			{
				"targets": [0, 1, 2],
				"className": "all filtrable"
			},
			{
				"targets": 3,
				"className": "all dt-right"
			}
		],
		"columns": [
			{
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.idMembresia, row.descripcionMembresia);
				},
				"title": "Membres\u00EDa"
			},
			{
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.idBin, row.descripcionBin);
				},
				"title": "Bin"
			},
			{
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.codigoAfinidad, row.descripcionAfinidad);
				},
				"title": "Afinidad"
			},
			{
				"data": "cantidadTarjetas",
				"title": "Total"
			}
		]
	});

	$local.$tablaResultados.find("thead").on('keyup', 'input', function() {
		$local.tablaResultados.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	// SELECT2 MEMBRESIA
	$local.$selectMembresias.on("select2:select", function(option) {
		var idMembresia = option.params.data.id;
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "bin?accion=buscar&membresia=" + idMembresia,
			contentType: "application/json",
			dataType: "json",
			beforeSend: function(xhr) {
				$local.$selectBines.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando bines</span>")
			},
			success: function(response) {
				$.each(response, function(i, sector) {
					$local.$selectBines.append($('<option value="' + this.codigoBIN + '" membresia="' + this.codigoMembresia + '">' + this.codigoMembresia + " - " + this.codigoBIN + " " + this.descripcion + '<option />'));
				});
			},
			complete: function() {
				$local.$selectBines.parent().find(".cargando").remove();
			}
		});
	});

	$local.$selectMembresias.on("select2:unselect", function(option) {
		var idMembresia = option.params.data.id;
		$local.$selectBines.find('option[membresia="' + idMembresia + '"]').remove();
		$local.$selectBines.find('option[membresia="' + idMembresia + '"]').empty();
	});

	// SELECT2 BINES

	// BOTON BUSCAR
	$local.$btnBuscar.on("click", function() {
		var criterioBusqueda = {};

		// obtener fechas de afiliacion
		var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$fechaAfiliacion);
		criterioBusqueda.fechaInicial = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaFinal = rangoFechaBusqueda.fechaFin;

		if (rangoFechaBusqueda.fechaInicio == null || rangoFechaBusqueda.fechaFin == null || rangoFechaBusqueda.fechaInicio === "" || rangoFechaBusqueda.fechaFin === "") {
			$funcionUtil.notificarException('Ingrese el rango de fechas de afiliaci\u00F3n', "fa-exclamation-circle", "Informaci\u00F3n", "info");
			return;
		}

		var paramCriterioBusqueda = $.param(criterioBusqueda);
		paramCriterioBusqueda = paramCriterioBusqueda + $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectMembresias, "idsMembresias");
		paramCriterioBusqueda = paramCriterioBusqueda + $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectBines, "idsBines");

		$.ajax({
			type: "GET",
			url: $variableUtil.root + "reporte/generacionTarjetas/resumen?accion=buscar&" + paramCriterioBusqueda,
			contentType: "application/json",
			dataType: "json",
			beforeSend: function(xhr) {
				xhr.setRequestHeader('Content-Type', 'application/json');
				$local.tablaResultados.clear().draw();
				$local.$btnBuscar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success: function(response) {
				//AGREGAR FUNCION QUE llena tabla
				$local.tablaResultados.rows.add(response).draw();
			},
			error: function(response) {
				//incluir mensaje de error
			},
			complete: function() {
				$local.$btnBuscar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	// BOTON EXPORTAR
	$local.$btnExportar.on("click", function() {
		var criterioBusqueda = {};
		// obtener fechas de afiliacion
		var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$fechaAfiliacion);
		criterioBusqueda.fechaInicial = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaFinal = rangoFechaBusqueda.fechaFin;
		criterioBusqueda.descripcionFecha = $local.$fechaAfiliacion.val();
		if (rangoFechaBusqueda.fechaInicio == null || rangoFechaBusqueda.fechaFin == null || rangoFechaBusqueda.fechaInicio === "" || rangoFechaBusqueda.fechaFin === "") {
			$funcionUtil.notificarException('Ingrese el rango de fechas de afiliaci\u00F3n', "fa-exclamation-circle", "Informaci\u00F3n", "info");
			return;
		}
		var paramCriterioBusqueda = $.param(criterioBusqueda);
		paramCriterioBusqueda = paramCriterioBusqueda + $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectMembresias, "idsMembresias");
		paramCriterioBusqueda = paramCriterioBusqueda + $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectBines, "idsBines");

		window.location.href = $variableUtil.root + "reporte/generacionTarjetas/resumen?accion=exportar&" + paramCriterioBusqueda;
	});
});