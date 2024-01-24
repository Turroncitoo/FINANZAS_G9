$(document).ready(function() {

	$formCriterioBusquedaConsulta.validate({
		focusCleanup : true,
		rules : {
			fechaProceso : {
				required : true
			}
		},
		messages : {
			fechaProceso : {
				required : "Seleccione una Fecha de Proceso."
			}
		}
	});
});