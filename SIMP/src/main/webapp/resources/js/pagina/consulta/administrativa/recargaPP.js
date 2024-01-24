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
			"url" : $variableUtil.root + "consulta/administrativa/recargaPP?accion=buscarTodos",
			"dataSrc" : ""
		},
		"language" : {
			"emptyTable" : "No se encontraron recargas."
		},
		"initComplete" : function() {
			$local.$tablaConsultaAdministrativa.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaConsultaAdministrativa);
		},
		"columnDefs" : [ {
			"targets" : [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ],
			"className" : "all filtrable",
			"defaultContent": "*"
		} ],	
		"columns" : [ {
			"data" : "idRecarga",
			"title" : "Id Recarga"
		}, {
			"data" : "idLote",
			"title" : "Id Lote"
		}, {
			"data" : "idTarjeta",
			"title" : "Id Tarjeta"
		}, {
			"data" : "idSecuencial",
			"title" : "Id Secuencial"
		}, {
			"data" : "fecHora",
			"title" : "Fecha-Hora"
		}, {
			"data" : "montoEnviado",
			"title" : "Monto Enviado"
		}, {
			"data" : "montoRecibido",
			"title" : "Monto Recibido"
		}, {
			"data" : "moneda",
			"title" : "Moneda"
		}, {
			"data" : "datos",
			"title" : "Datos"
		}, {
			"data" : "respCode",
			"title" : "Response Code"
		}]
	});

	$local.$tablaConsultaAdministrativa.find("thead").on("keyup", "input", function() {
		$local.tablaConsultaAdministrativa.column($(this).parent().index() + ":visible").search(this.value).draw();
	});

});