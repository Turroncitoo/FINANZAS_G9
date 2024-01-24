$(document).ready(function() {

	var $local = {
		$tablaConsultaAdministrativa : $("#tablaConsultaAdministrativa"),
		tablaConsultaAdministrativa : "",
	};

	$.fn.dataTable.ext.errMode = "none";

	$local.$tablaConsultaAdministrativa.on("xhr.dt", function(e, settings, json, xhr) {
		switch (xhr.status) {
		case 500:
			$local.tablaConsultaAdministrativa.clear().draw();
			$funcionUtil.notificarException(xhr.responseText, "Error Interno", "danger");
			break;
		}
	});

	$local.tablaConsultaAdministrativa = $local.$tablaConsultaAdministrativa.DataTable({
		"ajax" : {
			"url" : $variableUtil.root + "consulta/administrativa/lotePP?accion=buscarTodos",
			"dataSrc" : ""
		},
		"language" : {
			"emptyTable" : "No se encontraron lotes."
		},
		"initComplete" : function() {
			$local.$tablaConsultaAdministrativa.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaConsultaAdministrativa);
		},
		"columnDefs" : [ {
			"targets" : [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,12 ],
			"className" : "all filtrable",
			"defaultContent": "*"
		}, {
			"targets" : 11,
			"className" : "all filtrable",
			"render" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idBIN, row.descripcionBIN);
			}
		} , {
			"targets" : 7,
			"className" : "all filtrable",
			"render" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idInstitucion, row.descripcionInstitucion);
			}
		} 
		],
		"columns" : [ {
			"data" : "idLote",
			"title" : "Id Lote"
		}, {
			"data" : "idLoteOriginal",
			"title" : "Id Lote Original"
		}, {
			"data" : "estadoLote",
			"title" : "Estado"
		}, {
			"data" : "fechaRegistro",
			"title" : "F. Registro"
		}, {
			"data" : "fechaModificacion",
			"title" : "F. Modificaci\u00F3n"
		}, {
			"data" : "activo",
			"title" : "Activo"
		}, {
			"data" : "instancia",
			"title" : "Instancia"
		}, {
			"data" : null,
			"title" : "Instituci\u00F3n"
		}, {
			"data" : "idCliente",
			"title" : "Id Cliente"
		}, {
			"data" : "nombreArchivo",
			"title" : "Nombre Archivo"
		}, {
			"data" : "secuencia",
			"title" : "Secuencia"
		}, {
			"data" : null,
			"title" : "BIN"
		}, {
			"data" : "idSubBin",
			"title" : "SubBIN"
		} ]
	});

	$local.$tablaConsultaAdministrativa.find("thead").on("keyup", "input", function() {
		$local.tablaConsultaAdministrativa.column($(this).parent().index() + ":visible").search(this.value).draw();
	});

});