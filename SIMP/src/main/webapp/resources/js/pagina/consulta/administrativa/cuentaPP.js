$(document).ready(function() {

	var $local = {
		$tablaConsultaAdministrativa : $("#tablaConsultaAdministrativa"),
		tablaConsultaAdministrativa : "",
		
		$tipoBusqueda : $("input[type=radio][name=tipoBusqueda]"),
		$tipoDocumento : $("#tipoDocumento"),
		$criterios : $("#criterios"),
		$btnBuscarPorDocumentoCliente : $("#btnBuscarPorDocumentoCliente"),
		$buscarCriterios : $("#buscarCriterios"),
		$selectTipoDocumento : $("#selectTipoDocumento"),
		$rangoFechasAlta : $("#rangoFechasAlta"),
		$binSelect : $("#binSelect"),
		$subBinSelect : $("#subBinSelect"),
		
		numFiltrosMin: 1,
		mensajeFiltros: function(){
			return "Debe seleccionar al menos " + this.numFiltrosMin + " filtro(s) para porder realizar la B\u00FAsqueda";
		}
	};

	$formBusquedaCriterios = $("#formBusquedaCriterios");
	$formBusquedaTipoDocumento = $("#formParamIniciales");
	
	$funcionUtil.crearSelect2($local.$selectTipoDocumento, "Seleccione un Tipo de Documento");
	$funcionUtil.crearDateRangePickerConsulta($local.$rangoFechasAlta);
	$funcionUtil.crearSelect2($local.$binSelect);
	$funcionUtil.crearSelect2($local.$subBinSelect);
	
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
		"language" : {
			"emptyTable" : "No se encontraron cuentas."
		},
		"initComplete" : function() {
			$local.$tablaConsultaAdministrativa.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaConsultaAdministrativa);
		},
		"columnDefs" : [ {
			"targets" : [ 0, 1, 2, 3, 4, 5 ],
			"className" : "all filtrable",
			"defaultContent": "*"
		} ],
		
		"columns" : [ {
			"data" : "idCuenta",
			"title" : "Id. Cuenta"
		}, {
			"data" : "fechaAlta",
			"title" : "Fecha Alta"
		}, {
			"data" : "saldoDisponible",
			"title" : "Saldo Disponible"
		}, {
			"data" : "saldoContable",
			"title" : "Saldo Contable"
		}, {
			"data" : "idBin",
			"title" : "Id BIN"
		}, {
			"data" : "idSubBin",
			"title" : "Id SubBIN"
		} ]
	});

	$local.$tablaConsultaAdministrativa.find("thead").on("keyup", "input", function() {
		$local.tablaConsultaAdministrativa.column($(this).parent().index() + ":visible").search(this.value).draw();
	});
	
	$local.$tipoBusqueda.on("change", function() {
		var tipoBusqueda = $(this).val();
		switch (tipoBusqueda) {
		case "tipoDocumento":
			$local.$tipoDocumento.removeClass("hidden");
			$local.$criterios.addClass("hidden");
			break;
		case "criterios":
			$local.$tipoDocumento.addClass("hidden");
			$local.$criterios.removeClass("hidden");
			break;
		default:
			$funcionUtil.notificarException("Seleccione un Tipo de B\u00FAsqueda v\u00E1lido", "fa-warning", "Aviso", "warning");
		}
	});
	
	$local.$binSelect.on("change", function(){
		var idBin = $(this).val();
		if(idBin !== ''){
			$.ajax({
		    	type : "GET",
		    	url: $variableUtil.root + "subBin/bin/"+idBin,
		    	beforeSend: function(){
		    		$local.$subBinSelect.find("option[value!='-1']").remove();
		    	},
		    	success : function(response){
		    		$local.$subBinSelect.find("option[value!='']").remove();
		    		if(response.length == 0){
		    			return;
		    		}
		    		for(var i = 0; i < response.length;i++){
		    			var val = response[i];
		    			$local.$subBinSelect.append($("<option></option>")
		                        .attr("value",val.codigoSubBIN)
		                        .text(val.codigoSubBIN + " - "+ val.descripcion));
		    		}
		    	}
		    });
		}else{
			$local.$subBinSelect.find("option[value!='']").remove();
		}
	});

	$local.$buscarCriterios.on("click", function() {
		if (!$formBusquedaCriterios.valid()) {
			return;
		}
		if ($funcionUtil.camposVacios2($formBusquedaCriterios, $local.numFiltrosMin)) {
			$funcionUtil.notificarException($local.mensajeFiltros(), "fa-exclamation-circle", "Informaci\u00F3n", "info");
			return;
		}
		var criterioBusqueda = $formBusquedaCriterios.serializeJSON();
		var rangoFechaBusqueda = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechasAlta);
		criterioBusqueda.fechaInicioAlta = rangoFechaBusqueda.fechaInicio;
		criterioBusqueda.fechaFinAlta = rangoFechaBusqueda.fechaFin;
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "consulta/administrativa/cuentaPP?accion=buscarPorCriterios",
			data : criterioBusqueda,
			statusCode : {
				400 : function(response) {
					$funcionUtil.limpiarMensajesDeError($formBusquedaTipoDocumento);
					$funcionUtil.limpiarMensajesDeError($formBusquedaCriterios);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaCriterios);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formBusquedaTipoDocumento);
				}
			},
			beforeSend : function() {
				$local.tablaConsultaAdministrativa.clear().draw();
				$local.$buscarCriterios.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success : function(transaccionAjustes) {
				if (transaccionAjustes.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				$local.tablaConsultaAdministrativa.rows.add(transaccionAjustes).draw();
			},
			complete : function() {
				$local.$buscarCriterios.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

});