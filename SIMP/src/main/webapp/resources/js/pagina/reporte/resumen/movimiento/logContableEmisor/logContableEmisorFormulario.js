$(document).ready(function(){
	$formCriterioBusquedaReporte.validate({
		rules: {
			institucionEmpresa: {
				required: true
			}
		},
		messages: {
			institucionEmpresa: {
				required: 'Seleccione una Instituci\u00F3n'
			}
		}
	});
});