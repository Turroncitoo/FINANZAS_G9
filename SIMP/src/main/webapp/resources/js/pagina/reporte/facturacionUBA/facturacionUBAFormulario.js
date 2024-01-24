$(document).ready(function(){
	
	$formTipoReporteResumen.validate({
		rules: {
			codigoInstitucion: {
				required: true
			}
		},
		messages: {
			codigoInstitucion: {
				required: 'Seleccione una Instituci\u00F3n.'
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
				required: 'Seleccione una Instituci\u00F3n.'
			}
		}
	});

	$formTipoReporteMiscelaneos.validate({
		rules: {
			codigoInstitucion: {
				required: true
			}
		},
		messages: {
			codigoInstitucion: {
				required: 'Seleccione una Instituci\u00F3n.'
			}
		}
	});
	
});