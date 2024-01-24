$(document).ready(function(){
	
	$formTipoReporteResumen.validate({
		rules: {
			codigoInstitucion: {
				required: true
			}
		},
		messages: {
			codigoInstitucion: {
				required: 'Seleccione una instituci\u00F3n.'
			}
		}
	});
	
	$formTipoReporteDetalle.validate({
		rules: {
			codigoInstitucion: {
				required: true
			}
		},
		messages: {
			codigoInstitucion: {
				required: 'Seleccione una instituci\u00F3n.'
			}
		}
	});
	
	$formTipoReporteAutDia.validate({
		rules: {
			codigoInstitucion: {
				required: true
			}
		},
		messages: {
			codigoInstitucion: {
				required: 'Seleccione una instituci\u00F3n.'
			}
		}
	});
	
	$formTipoReporteAutDetalle.validate({
		rules: {
			codigoInstitucion: {
				required: true
			}
		},
		messages: {
			codigoInstitucion: {
				required: 'Seleccione una instituci\u00F3n.'
			}
		}
	});
	
});