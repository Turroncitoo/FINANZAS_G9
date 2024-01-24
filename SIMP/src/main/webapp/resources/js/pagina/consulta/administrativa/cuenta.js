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
			"url" : $variableUtil.root + "consulta/administrativa/cuenta?accion=buscarTodos",
			"dataSrc" : ""
		},
		"language" : {
			"emptyTable" : "No se encontraron cuentas."
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
			"title" : "N\u00FAmero de Tarjeta"
		}, {
			"data" : "numeroCuenta",
			"title" : "N\u00FAmero de Cuenta"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idAgencia, row.descripcionAgencia);
			},
			"title" : "Agencia"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoEstadoCuenta, row.descripcionEstadoCuenta);
			},
			"title" : "Estado Cuenta"
		}, {
			"data" : "idCliente",
			"title" : "Cliente"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoTipoDocumento, row.descripcionTipoDocumento);
			},
			"title" : "Tipo Doc."
		}, {
			"data" : "numeroDocumento",
			"title" : "N\u00FAmero Documento"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoTipoCuenta, row.descripcionTipoCuenta);
			},
			"title" : "Tipo de Cuenta"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoProducto, row.descripcionProducto);
			},
			"title" : "Producto"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoMoneda, row.descripcionMoneda);
			},
			"title" : "Moneda"
		}, {
			"data" : "fechaAutorizacion",
			"title" : "F. Autorizaci\u00F3n"
		}, {
			"data" : "fechaActualizacion",
			"title" : "F. Actualizaci\u00F3n"
		}, {
			"data" : function(row){
				return $funcionUtil.unirCodigoDescripcion(row.codigoModalidadDeposito, row.descripcionModalidadDeposito);
			},
			"title" : "Modalidad"
		} ]
	});

	$local.$tablaConsultaAdministrativa.wrap("<div class='table-responsive'></div>");

	$local.$tablaConsultaAdministrativa.find("thead").on("keyup", "input", function() {
		$local.tablaConsultaAdministrativa.column($(this).parent().index() + ":visible").search(this.value).draw();
	});

});