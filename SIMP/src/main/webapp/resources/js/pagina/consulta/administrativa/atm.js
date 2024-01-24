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
			break;
		}
	});

	$local.tablaConsultaAdministrativa = $local.$tablaConsultaAdministrativa.DataTable({
		"ajax" : {
			"url" : $variableUtil.root + "consulta/administrativa/atm?accion=buscarTodos",
			"dataSrc" : ""
		},
		"language" : {
			"emptyTable" : "No hay ATMs registrados."
		},
		"initComplete" : function() {
			$local.$tablaConsultaAdministrativa.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaConsultaAdministrativa);
		},
		"columnDefs" : [ {
			"targets" : [ 2, 4, 5 ],
			"className" : "all filtrable",
			"defaultContent": "*"
		}, {
			"targets" : 0,
			"className" : "all filtrable",
			"render" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoInstitucion, row.descripcionInstitucion);
			}
		}, {
			"targets" : 1,
			"className" : "all filtrable",
			"render" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idATM, row.descripcionATM);
			}
		}, {
			"targets" : 3,
			"className" : "all filtrable",
			"render" : function(row) {
				if(row.departamento == null || row.provincia == null || row.distrito == null){
					return "*";
				}
				return row.departamento + " - " + row.provincia + " - " + row.distrito;
			}
		}, {
			"targets" : 5,
			"className" : "all filtrable",
			"render" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idAgencia, row.descripcionAgencia);
			}
		} ],
		"columns" : [ {
			"data" : null,
			"title" : "Instituci\u00F3n"
		}, {
			"data" : null,
			"title" : "ATM"
		}, {
			"data" : "direccion",
			"title" : "Direcci\u00F3n"
		}, {
			"data" : null,
			"title" : "Ubigeo"
		}, {
			"data" : "tipoATM",
			"title" : "Tipo de ATM"
		}, {
			"data" : null,
			"title" : "Agencia"
		} ]
	});

	$local.$tablaConsultaAdministrativa.find("thead").on("keyup", "input", function() {
		$local.tablaConsultaAdministrativa.column($(this).parent().index() + ":visible").search(this.value).draw();
	});

});