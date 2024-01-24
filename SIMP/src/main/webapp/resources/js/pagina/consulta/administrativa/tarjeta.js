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
			"url" : $variableUtil.root + "consulta/administrativa/tarjeta?accion=buscarTodos",
			"dataSrc" : ""
		},
		"language" : {
			"emptyTable" : "No hay Tarjetas registradas."
		},
		"initComplete" : function() {
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaConsultaAdministrativa);
		},
		"columnDefs" : [ {
			"targets" : "_all",
			"className" : "all filtrable"
		} ],
		"columns" : [ {
			"data" : "numeroTarjeta",
			"title" : "Tarjeta"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoTipoDocumento, row.descripcionTipoDocumento);
			},
			"title" : "Tipo Doc."
		}, {
			"data" : "numeroDocumento",
			"title" : "Numero Doc."
		}, {
			"data" : "idCliente",
			"title" : "Id Cliente"
		}, {
			"data" : "nombres",
			"title" : "Nombres"
		}, {
			"data" : "apellidos",
			"title" : "Apellidos"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoBIN, row.descripcionBIN);
			},
			"title" : "BIN"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoTipoTarjeta, row.descripcionTipoTarjeta);
			},
			"title" : "Tipo"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idAgencia, row.descripcionAgencia);
			},
			"title" : "Agencia"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoEstadoTarjeta, row.descripcionEstadoTarjeta);
			},
			"title" : "Estado de Tarjeta"
		}, {
			"data" : "estado",
			"title" : "Estado"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoPais, row.descripcionPais);
			},
			"title" : "Pa\u00EDs"
		}, {
			"data" : "fechaActivacion",
			"title" : "F. de Activaci\u00F3n"
		}, {
			"data" : "fechaActualizacion",
			"title" : "F. de Actualizaci\u00F3n"
		}, {
			"data" : "fechaVencimiento",
			"title" : "F. de Vencimiento"
		}, {
			"data" : "fechaBloqueoUBA",
			"title" : "F. de Bloqueo"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoTipoBloqueo, row.descripcionTipoBloqueo);
			},
			"title" : "Bloqueo"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoFranquicia, row.descripcionFranquicia);
			},
			"title" : "Franquicia"
		}, {
			"data" : "codigoAsesor",
			"title" : "Asesor"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoCategoria, row.descripcionCategoria);
			},
			"title" : "Categor\u00EDa"
		}, {
			"data" : "idPersona",
			"title" : "Persona"
		}, {
			"data" : "grabacionTarjeta",
			"title" : "Grabaci\u00F3n"
		} ]
	});
	
	$local.$tablaConsultaAdministrativa.wrap("<div class='table-responsive'></div>");

	$local.$tablaConsultaAdministrativa.find("thead").on("keyup", "input.filtrable", function() {
		$local.tablaConsultaAdministrativa.column($(this).parent().index() + ":visible").search(this.value).draw();
	});
});