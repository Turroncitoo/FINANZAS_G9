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
		
		$modalMantenimiento : $("#modalMantenimiento"),
		
		$aniadirMantenimento : $("#aniadirMantenimiento"),
		$registrarMantenimiento : $("#registrarMantenimiento"),
		$filaSeleccionada : "",
		$actualizarMantenimiento : $("#actualizarMantenimiento"),
		
		numFiltrosMin: 1,
		mensajeFiltros: function(){
			return "Debe seleccionar al menos " + this.numFiltrosMin + " filtro(s) para poder realizar la B\u00FAsqueda";
		},
	
		$aniadirMantenimento : $("#aniadirMantenimiento"),
		$selectSexos: $('#selectSexos'),
		$tipoDoc: $('#tipoDoc'),
		$txtNumeroDocumento: $('#txtNumeroDocumento'),
		
		$rangoFechaBusqueda : $("#rangoFechaBusqueda"),
		
		//Exportacion
		$exportarPorCriterio: $('#exportarPorCriterio'),
		$exportarPorTipoDocumento: $('#exportarPorTipoDocumento'),
		$selectPais: $('#selectPais')
	};
	
	$funcionUtil.crearDatePickerSimple($local.$rangoFechaBusqueda, "DD/MM/YYYY");
	
	$formMantenimiento = $("#formMantenimiento");
	
	$formBusquedaCriterios = $("#formBusquedaCriterios");
	$formBusquedaTipoDocumento = $("#formParamIniciales");
	
	$funcionUtil.crearSelect2($local.$selectTipoDocumento, "Seleccione un Tipo de Documento");
	$funcionUtil.crearSelect2($local.$selectSexos, "Seleccione un Sexo");
	$funcionUtil.crearSelect2($local.$tipoDoc, "Seleccione un Tipo de Documento");
	$funcionUtil.crearSelect2($local.$selectPais, "Seleccione un Pa\u00EDs");

	$.fn.dataTable.ext.errMode = "none";

	$local.$tablaConsultaAdministrativa.on("xhr.dt", function(e, settings, json, xhr) {
		switch (xhr.status) {
		case 500:
			$local.tablaConsultaAdministrativa.clear().draw();
			$funcionUtil.notificarException(xhr.responseText, "Error Interno", "danger");
			break;
		}
	});
	
	$local.$modalMantenimiento.PopupWindow({
		title : "Mantenimiento de Personas",
		autoOpen : false,
		modal : false,
		height : 600,
		width : 900,
	});

	$local.tablaConsultaAdministrativa = $local.$tablaConsultaAdministrativa.DataTable({
		"language" : {
			"emptyTable" : "No hay Personas registradas."
		},
		"initComplete" : function() {
			$local.$tablaConsultaAdministrativa.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaConsultaAdministrativa);
		},
		"columnDefs" : [ {
			"targets" : [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16],
			"className" : "all filtrable",
		} , {
			"targets" : 17,
			"className" : "all dt-center",
			"width" : "10%",
			"render" : function() {
				return $variableUtil.botonActualizar;
			}
		}],
		"columns" : [ {
			"data" : "descripcionTipoDocumento",
			"title" : "Tipo Documento"
		}, {
			"data" : "numeroDocumento",
			"title" : "N\u00B0 Documento"
		}, {
			"data" : "nombres",
			"title" : "Nombres"
		}, {
			"data" : "apellidoPaterno",
			"title" : "Ap. Paterno"
		}, {
			"data" : "apellidoMaterno",
			"title" : "Ap. Materno"
		}, {
			"data" : "sexo",
			"title" : "Sexo"
		},{
			"data" : "alias",
			"title" : "Alias"
		}, {
			"data" : "direccion",
			"title" : "Direcci\u00F3n"
		}, {
			"data" : "fechaNacimiento",
			"title" : "Fecha Nacimiento"
		}, {
			"data" : "telefonoFijo",
			"title" : "Tel\u00E9fono Fijo"
		}, {
			"data" : "telefonoMovil",
			"title" : "Celular"
		}, {
			"data" : "codUBA",
			"title" : "C\u00F3digo UBA"
		}, {
			"data" : "correoElectronico",
			"title" : "Correo Electr\u00F3nico"
		}, {
			"data" : "fechaRegistro",
			"title" : "Fecha Registro"
		}, {
			"data" : "codigoUbigeo",
			"title" : "Ubigeo"
		}, {
			"data" : "pais",
			"title" : "Pa\u00EDs"
		}, {
			"data" : "idPersona",
			"title" : "ID Persona"
		}, {
			"data" : null,
			"title" : 'Acci\u00F3n'
		}]
	});
	 
	$local.tablaConsultaAdministrativa.buttons().container()
	    .appendTo( $('.col-sm-6:eq(0)', $local.tablaConsultaAdministrativa.table().container() ) );
	

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

	$local.$btnBuscarPorDocumentoCliente.on("click", function() {
		if (!$formBusquedaTipoDocumento.valid()) {
			return;
		}
		var criterioBusqueda = $formBusquedaTipoDocumento.serializeJSON();
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "consulta/administrativa/personaPP?accion=buscarPorTipoDocumento",
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
				$local.$btnBuscarPorDocumentoCliente.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success : function(transaccionAjustes) {
				if (transaccionAjustes.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				$local.tablaConsultaAdministrativa.rows.add(transaccionAjustes).draw();
			},
			complete : function() {
				$local.$btnBuscarPorDocumentoCliente.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
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
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "consulta/administrativa/personaPP?accion=buscarPorCriterios",
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
	
	//WS
	
	
	$local.$aniadirMantenimento.on("click", function() {
		$funcionUtil.prepararFormularioRegistro($formMantenimiento);
		$local.$actualizarMantenimiento.addClass("hidden");
		$local.$registrarMantenimiento.removeClass("hidden");
		$local.$tipoDoc.attr("disabled",false);
		$local.$txtNumeroDocumento.attr("disabled",false);
		$local.$modalMantenimiento.PopupWindow("open");
	});
	
	$local.$registrarMantenimiento.on("click", function() {
		if (!$formMantenimiento.valid()) {
			return;
		}
		var personaPP = $formMantenimiento.serializeJSON();
		personaPP.fechaNacimientoFormateado = $local.$rangoFechaBusqueda.data("daterangepicker").startDate.format('YYYYMMDD');
		personaPP.paisWs = $local.$selectPais.val();
		personaPP.idPaisWs = $local.$selectPais.find("option[value="+$local.$selectPais.val()+"]").attr('key');
		$.ajax({
			type : "POST",
			url : $variableUtil.root + "txnsWebServices/api/cliente",
			data : JSON.stringify(personaPP),
			beforeSend : function(xhr) {
				$local.$registrarMantenimiento.attr("disabled", true).find("i").removeClass("fa-floppy-o").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode : {
				400 : function(response) {
					$funcionUtil.limpiarMensajesDeError($formMantenimiento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formMantenimiento);
				}
			},
			success : function(response) {
				if(response.respuestaSimpHub != "0"){
					$funcionUtil.notificarException(response.descripcionRespuestaSimpHub, "fa-warning", "Aviso", "warning");
					return;
				}
				$local.$modalMantenimiento.PopupWindow("close");
				$funcionUtil.notificarException("Registro con \u00E9xito.", "fa-check", "Aviso", "success");
			},
			error : function(response) {
			},
			complete : function(response) {
				$local.$registrarMantenimiento.attr("disabled", false).find("i").addClass("fa-floppy-o").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});
	
	$local.$tablaConsultaAdministrativa.children("tbody").on("click", ".actualizar", function() {
		$funcionUtil.prepararFormularioActualizacion($formMantenimiento);
		$local.$filaSeleccionada = $(this).parents("tr");
		var personaPP = $local.tablaConsultaAdministrativa.row($local.$filaSeleccionada).data();
		$local.personaSeleccionada = personaPP.idPersona;
		personaPP.paisWs = personaPP.pais;
		$funcionUtil.llenarFormulario(personaPP, $formMantenimiento);
		$local.$tipoDoc.attr("disabled",true);
		$local.$txtNumeroDocumento.attr("disabled",true);
		if(personaPP.fechaNacimiento != '' && personaPP.fechaNacimiento != null){
			var fechaProceso = $funcionUtil.convertirDeFormatoAFormato(personaPP.fechaNacimiento, "YYYY-MM-DD", "DD/MM/YYYY");
			$local.$rangoFechaBusqueda.data("daterangepicker").setStartDate(fechaProceso);
			$local.$rangoFechaBusqueda.data("daterangepicker").setEndDate(fechaProceso);
		}
		$local.$actualizarMantenimiento.removeClass("hidden");
		$local.$registrarMantenimiento.addClass("hidden");
		$local.$modalMantenimiento.PopupWindow("open");
	});
	
	
	$local.$actualizarMantenimiento.on("click", function() {
		if (!$formMantenimiento.valid()) {
			return;
		}
		var dataExtra = $local.tablaConsultaAdministrativa.row($local.$filaSeleccionada).data();
		var personaPP = $formMantenimiento.serializeJSON();
		personaPP.fechaNacimientoFormateado = $local.$rangoFechaBusqueda.data("daterangepicker").startDate.format('YYYYMMDD');
		personaPP.codUBA = dataExtra.codUBA;
		personaPP.paisWs = $local.$selectPais.val();
		personaPP.idPaisWs = $local.$selectPais.find("option[value="+$local.$selectPais.val()+"]").attr('key');
		personaPP.tipoDocumento = $local.$tipoDoc.val();
		personaPP.numeroDocumento = $local.$txtNumeroDocumento.val();
		$.ajax({
			type : "PUT",
			url : $variableUtil.root + "txnsWebServices/api/cliente",
			data : JSON.stringify(personaPP),
			beforeSend : function(xhr) {
				$local.$actualizarMantenimiento.attr("disabled", true).find("i").removeClass("fa-pencil-square").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode : {
				400 : function(response) {
					$funcionUtil.limpiarMensajesDeError($formMantenimiento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formMantenimiento);
				}
			},
			success : function(response) {
				if(response.respuestaSimpHub != "0"){
					$funcionUtil.notificarException(response.descripcionRespuestaSimpHub, "fa-warning", "Aviso", "warning");
					return;
				}
				$local.tablaConsultaAdministrativa.row($local.$filaSeleccionada).remove().draw(false);
				var row = $local.tablaConsultaAdministrativa.row.add(response).draw();
				row.show().draw(false);
				$(row.node()).animateHighlight();
				$local.$modalMantenimiento.PopupWindow("close");
				$funcionUtil.notificarException("Actualizaci\u00F3n con \u00E9xito.", "fa-check", "Aviso", "success");
			},
			error : function(response) {
			},
			complete : function(response) {
				$local.$actualizarMantenimiento.attr("disabled", false).find("i").addClass("fa-pencil-square").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});
	
	$local.$exportarPorCriterio.on("click", function() {
		if (!$formBusquedaCriterios.valid()) {
			return;
		}
		if ($funcionUtil.camposVacios2($formBusquedaCriterios, $local.numFiltrosMin)) {
			$funcionUtil.notificarException($local.mensajeFiltros(), "fa-exclamation-circle", "Informaci\u00F3n", "info");
			return;
		}
		var criterioBusqueda = $formBusquedaCriterios.serializeJSON();
		var paramCriterioBusqueda = $.param(criterioBusqueda);
		window.location.href = $variableUtil.root + "consulta/administrativa/personaPP?accion=exportarPorCriterios&" + paramCriterioBusqueda;
	});
	
	$local.$exportarPorTipoDocumento.on("click", function() {
		if (!$formBusquedaTipoDocumento.valid()) {
			return;
		}
		var criterioBusqueda = $formBusquedaTipoDocumento.serializeJSON();
		var paramCriterioBusqueda = $.param(criterioBusqueda);
		window.location.href = $variableUtil.root + "consulta/administrativa/personaPP?accion=exportarPorTipoDocumento&" + paramCriterioBusqueda;
	});
	
});