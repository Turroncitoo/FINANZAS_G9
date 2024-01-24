$(document).ready(function(){
	$formCriterios.validate({
		focusCleanup : true,
		rules:{
			fechaProceso: {
				required: true,
				notOnlySpace: true
			},
			codigoInstitucion: {
				required: true
			},
			numeroTarjeta: {
				soloalfanumericos: true,
				maxlength: 20
			},
			referenciaIntercambio: {
				soloalfanumericos: true,
				maxlength: 23
			}			
		},
		messages:{
			fechaProceso: {
				required: "Seleccione el Rango de Fechas del Proceso.",
				notOnlySpace: "El Rango de Fechas no puede contener solo espacios en blanco."
			},
			codigoInstitucion: {
				required: "Seleccione una Instituci\u00F3n."
			},
			numeroTarjeta: {
				soloalfanumericos: "El N&uacute;mero Tarjeta debe contener solo n&uacute;meros y letras.",
				maxlength: "El N&uacute;mero Tarjeta no debe contener m&aacute;s de {0} d&iacute;gitos."
			},
			referenciaIntercambio: {
				number: "La Referencia Intercambio debe contener solo n&uacute;meros y letras.",
				maxlength: "La Referencia Intercambio no debe contener m&aacute;s de {0} d&iacute;gitos."
			}
		},
		highlight: function(element) {
			$(element).parents(".group, .form-group").first().addClass('has-error');
		},
		unhighlight: function(element) {
			$(element).parents(".group, .form-group").first().removeClass('has-error');
		},
		errorElement: 'span',
		errorClass: 'help-block',
		errorPlacement: function(error, element) {
			if (element.parent('.input-group').length) {
				error.insertAfter(element.parent());
			} else if (element.parent().find("span.select2").length > 0) {
				error.appendTo(element.parent());
			} else {
				error.insertAfter(element);
			}
		}
	});
});