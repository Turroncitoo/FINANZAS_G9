$(document).ready(function() {

	$formCriterioBusquedaReporte.validate({
		rules : {
			fechas : {
				required : true,
				notOnlySpace : true
			},
			idInstitucion: {
				required : true,
			}
		},
		messages : {
			fechas : {
				required : "Seleccione el Rango de Fechas del Proceso.",
				notOnlySpace : "El Rango de Fechas no puede contener solo espacios en blanco."
			},
			idInstitucion: {
				required : "Seleccione una Instituci\u00F3n."
			}
		}
	});

});