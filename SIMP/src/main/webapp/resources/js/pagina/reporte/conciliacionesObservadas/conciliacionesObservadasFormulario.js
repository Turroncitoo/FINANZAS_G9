$(document).ready(function() {
	
	$formBusquedaCriterios.validate({
		focusCleanup : true,
		rules : {			
			idOrigenArchivo : {
				range : [ -1, 99 ]
			}
		},
		messages : {			
			idOrigenArchivo : {
				range : "El Origen de Archivo seleccionado es inv&aacute;lido."
			}
		}
	});
});