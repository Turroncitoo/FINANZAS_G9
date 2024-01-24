$(document).ready(function(){
	
	var $local = {
			$tablaMantenimiento : $("#tablaMantenimiento"),
			tablaMantenimiento : "",
			$modalMantenimiento : $("#modalMantenimiento"),
			$aniadirMantenimento : $("#aniadirMantenimiento"),
			$registrarMantenimiento : $("#registrarMantenimiento"),
			$filaSeleccionada : "",
			$actualizarMantenimiento : $("#actualizarMantenimiento"),
			codigoSeleccionado : 0,
			arregloSiNo : [ "0", "1" ],
			// Permisos
			permisoActualizacion : false,
			permisoEliminacion : false
		};
	
		$formMantenimiento = $("#formMantenimiento");
	
		$.fn.dataTable.ext.errMode = 'none';
	
		$local.$tablaMantenimiento.on('xhr.dt', function(e, settings, json, xhr) {
			switch (xhr.status) {
			case 500:
				$local.tablaMantenimiento.clear().draw();
				break;
			}
		});
		
		
		
		$local.permisoActualizacion = $local.$tablaMantenimiento.attr("data-permiso-actualizacion") || false;
	
		$local.tablaMantenimiento = $local.$tablaMantenimiento.DataTable({
			"ajax" : {
				"url" : $variableUtil.root + "parametroIntCon?accion=buscarTodos",
				"dataSrc" : ""
			},
			"language" : {
				"emptyTable" : "No hay Par\u00E1metros de Interface Contable registrados"
			},
			"initComplete" : function() {
				$local.$tablaMantenimiento.wrap("<div class='table-responsive'></div>");
				$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaMantenimiento);
			},
			"columnDefs" : [ {
				"targets" : [ 0, 1, 2, 3, 4 ],
				"className" : "all filtrable all dt-center",
			}, {
				"targets" : 4,
				"className" : "all data-no-definida",
				"render" : function(data, type, row) {
					return $funcionUtil.insertarEtiquetaSiNo(row.activo);
				}
			}, {
				"targets" : 5,
				"className" : "all dt-center",
				"width" : "10%",
				"render" : function() {
					return "" + ($local.permisoActualizacion == "true" ? $variableUtil.botonActualizar : "");
				}
			} ],

			"columns" : [ {
				"data" : "usuarioAlegra",
				"title" : 'Usuario Alegra'
			}, {
				"data" : 'tokenAlegra',
				"title" : "Token Alegra"
			},{
				"data": 'cuentaContableURI',
				"title":"URI de Cuenta Contable"
			},{
				"data": 'pagoURI',
				"title":"URI de Pago"
			},{
				
				"data": null,
				"title":"Activo"
			},{
				"data" : null,
				"title" : 'Acci\u00F3n'
			}]
		});
			
		$local.$tablaMantenimiento.find("thead").on('keyup', 'input', function() {
			$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(this.value).draw();
		});
		
		$local.$tablaMantenimiento.find("thead").on('change', 'select', function() {
			var val = $.fn.dataTable.util.escapeRegex($(this).val());
			$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
		});

		$local.$modalMantenimiento.PopupWindow({
			title : "Mantenimiento de Par\u00E1metros Interface Contable",
			autoOpen : false,
			modal : false,
			height : 280,
			width : 600,
		});
		
		$local.$aniadirMantenimento.on("click", function() {
			$funcionUtil.prepararFormularioRegistro($formMantenimiento);
			$local.$actualizarMantenimiento.addClass("hidden");
			$local.$registrarMantenimiento.removeClass("hidden");
			$local.$modalMantenimiento.PopupWindow("open");
		});
		
		$local.$modalMantenimiento.on("open.popupwindow", function() {
			$formMantenimiento.find("input:not([readonly]):first").focus();
		});

		$local.$modalMantenimiento.on("close.popupwindow", function() {
			$local.idparametroIntConSeleccionado = ""; //----------
		});

		$formMantenimiento.find("input").keypress(function(event) {
			if (event.which == 13) {
				$local.$actualizarMantenimiento.trigger("click");
				return false;
			}
		});
		
		$local.$tablaMantenimiento.children("tbody").on("click", ".actualizar", function() {
			$funcionUtil.prepararFormularioActualizacion($formMantenimiento);
			$local.$filaSeleccionada = $(this).parents("tr");
			var parametroIntCon = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
			$funcionUtil.llenarFormulario(parametroIntCon, $formMantenimiento);
			$local.$actualizarMantenimiento.removeClass("hidden");
			$local.$registrarMantenimiento.addClass("hidden");
			$local.$modalMantenimiento.PopupWindow("open");
		});
		
		
		
		
		$local.$actualizarMantenimiento.on("click", function() {
			if (!$formMantenimiento.valid()) {
				return;
			}
			var parametroIntCon = $formMantenimiento.serializeJSON();
		
			$.ajax({
				type : "PUT",
				url : $variableUtil.root + "parametroIntCon",
				data : JSON.stringify(parametroIntCon),
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
					$funcionUtil.notificarException($variableUtil.actualizacionExitosa, "fa-check", "Aviso", "success");
					if ($local.$tablaMantenimiento.length > 0) {
						$local.tablaMantenimiento.row($local.$filaSeleccionada).remove().draw(false);
						var row = $local.tablaMantenimiento.row.add(response[0]).draw();
						row.show().draw(false);
						$(row.node()).animateHighlight();
					}
					$local.$modalMantenimiento.PopupWindow("close");
				},
				error : function(response) {
				},
				complete : function(response) {
					$local.$actualizarMantenimiento.attr("disabled", false).find("i").addClass("fa-pencil-square").removeClass("fa-spinner fa-pulse fa-fw");
				}
			});
		});
	
		
	
	
	
	
});


