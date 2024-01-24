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
			"url" : $variableUtil.root + "consulta/administrativa/clientePersona?accion=buscarTodos",
			"dataSrc" : ""
		},
		"language" : {
			"emptyTable" : "No hay Clientes registrados."
		},
		"initComplete" : function() {
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaConsultaAdministrativa);
		},
		"columnDefs" : [ {
			"targets" : "_all",
			"className" : "all filtrable"
		} ],
		"columns" : [ {
			"data" : "idCliente",
			"title" : "Id"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoTipoDocumento, row.descripcionTipoDocumento);
			},
			"title" : "Tipo Doc."
		}, {
			"data" : "numeroDocumento",
			"title" : "Num. Doc."
		}, {
			"data" : "nombres",
			"title" : "Nombres"
		}, {
			"data" : "apellidos",
			"title" : "Apellidos"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.clasePersona, row.descripcionClasePersona);
			},
			"title" : "Tipo"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoPais, row.descripcionPais);
			},
			"title" : "Pa\u00EDs"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idSexo, row.descripcionSexo);
			},
			"title" : "Sexo"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idEstadoCivil, row.descripcionEstadoCivil);
			},
			"title" : "Estado Civil"
		}, {
			"data" : "codigoConyuge",
			"title" : "Conyuge"
		}, {
			"data" : "fechaNacimiento",
			"title" : "F. de Nacimiento"
		}, {
			"data" : "primerTelefono",
			"title" : "Tel\u00E9fono 1"
		}, {
			"data" : "segundoTelefono",
			"title" : "Tel\u00E9fono 2"
		}, {
			"data" : "email",
			"title" : "Email"
		}, {
			"data" : "direccion",
			"title" : "Direcci\u00F3n"
		}, {
			"data" : "fechaContrato",
			"title" : "F. de Contrato"
		},{
			"data" : "descripcionDepartamento",
			"title" : "Departamento"
		}, {
			"data" : "descripcionProvincia",
			"title" : "Provincia"
		}, {
			"data" : "descripcionDistrito",
			"title" : "Distrito"
		} ]
	});
	
	$local.$tablaConsultaAdministrativa.wrap("<div class='table-responsive'></div>");

	$local.$tablaConsultaAdministrativa.find("thead").on("keyup", "input", function() {
		$local.tablaConsultaAdministrativa.column($(this).parent().index() + ":visible").search(this.value).draw();
	});

});