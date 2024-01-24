$(document).ready(function() {

	$formTipoReporteResumen.validate({
		rules: {
			idInstitucion: {
				required: true
			}
		},
		messages: {
			idInstitucion: {
				required: 'Seleccine una instituci\u00F3n'
			}
		}
	});
	
	$formTipoReporteResumenPorPeriodo.validate({
		rules: {
			idInstitucion: {
				required: true
			}
		},
		messages: {
			idInstitucion: {
				required: 'Seleccine una instituci\u00F3n'
			}
		}
	});
	
	$formTipoReporteDetalle.validate({
		rules: {
			idInstitucion: {
				required: true
			}
		},
		messages: {
			idInstitucion: {
				required: 'Seleccine una instituci\u00F3n'
			}
		}
	});
	
});