$(document).ready(function() {
	$formCriterioBusquedaReporte.validate({
		rules: {
			codigoInstitucion: {
				required: true
			}
		},
		messages: {
			codigoInstitucion: {
				required: 'Seleccione una instituci\u00F3n'
			}
		}
	})
});