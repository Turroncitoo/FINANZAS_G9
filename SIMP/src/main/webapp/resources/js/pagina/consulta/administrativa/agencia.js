$(document).ready(function() {

	var $local = {
		$tablaConsultaAdministrativa : $("#tablaConsultaAdministrativa"),
		tablaConsultaAdministrativa : "",
	};

	$.fn.dataTable.ext.errMode = 'none';

	$local.$tablaConsultaAdministrativa.on('xhr.dt', function(e, settings, json, xhr) {
		switch (xhr.status) {
		case 500:
			$local.tablaConsultaAdministrativa.clear().draw();
			break;
		}
	});

	$local.tablaConsultaAdministrativa = $local.$tablaConsultaAdministrativa.DataTable({
		"ajax" : {
			"url" : $variableUtil.root + "consulta/administrativa/agencia?accion=buscarTodos",
			"dataSrc" : ""
		},
		"language" : {
			"emptyTable" : "No hay Agencias registradas."
		},
		"initComplete" : function() {
			$local.$tablaConsultaAdministrativa.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaConsultaAdministrativa);
		},
		"columnDefs" : [ {
			"targets" : [ 0, 1, 2, 3, 4, 5 ],
			"className" : "all filtrable"
		} ],
		"columns" : [ {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idAgencia, row.descripcion);
			},
			"title" : "Agencia"
		}, {
			"data" : "direccion",
			"title" : "Direcci\u00F3n"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoPlaza, row.codigoSubPlaza);
			},
			"title" : "Plaza - SubPlaza"
		}, {
			"data" : function(row) {
				return row.descripcionDepartamento;
			},
			"title" : "Departamento"
		}, {
			"data" : function(row) {
				return row.descripcionProvincia;
			},
			"title" : "Provincia"
		}, {
			"data" : function(row) {
				return row.descripcionDistrito;
			},
			"title" : "Distrito"
		} ]
	});

	$local.$tablaConsultaAdministrativa.find("thead").on("keyup", "input", function() {
		$local.tablaConsultaAdministrativa.column($(this).parent().index() + ":visible").search(this.value).draw();
	});

});