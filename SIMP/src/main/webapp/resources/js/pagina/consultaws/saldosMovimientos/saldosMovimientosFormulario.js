$(document).ready(function()
{
	$formBusquedaTipoDocumento.validate({
		focusCleanup : true,
		rules : {
			tipoDocumento : {
				required : true,
				number : true,
				rangelength : [ 1, 4 ]
			},
			numeroDocumento : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 1, 20 ]
			}
		},
		messages : {
			tipoDocumento : {
				required : "Seleccione un Tipo de Documento.",
				number : "El Tipo de Documento debe ser un n&uacute;mero.",
				rangelength : "El Tipo de Documento debe contener entre 1 y 4 d&iacute;gitos."
			},
			numeroDocumento : {
				required : "Ingrese un N&uacute;mero de Documento.",
				notOnlySpace : "El N&uacute;mero de Documento no puede contener solo espacios en blanco.",
				rangelength : "El N&uacute;mero de Documento debe contener entre 1 y 20 c&aacute;racteres."
			}
		},
		highlight : function(element) {
			$(element).parents(".group, .form-group").first().addClass('has-error');
		},
		unhighlight : function(element) {
			$(element).parents(".group, .form-group").first().removeClass('has-error');
		},
		errorElement : 'span',
		errorClass : 'help-block',
		errorPlacement : function(error, element) {
			if (element.parent('.input-group').length) {
				error.insertAfter(element.parent());
			} else if (element.parent().find("span.select2").length > 0) {
				error.appendTo(element.parent());
			} else {
				error.insertAfter(element);
			}
		}
	});
	
	$formBusquedaCriterios.validate({
		focusCleanup : true,
		rules : {
			numeroTarjeta : {
				number: true,
				notOnlySpaceOrEmpty : true,
				maxlength : 20
			},
			trace : {
				number: true,
				notOnlySpaceOrEmpty : true,
				maxlength : 15
			},
			idCanal : {
				number: true
			},
			codigoProcesoSwitch : {
				number: true
			}
		},
		messages : {
			numeroTarjeta : {
				notOnlySpaceOrEmpty : "El N&uacute;mero de Tarjeta no debe contener solo espacios en blanco.",
				number : "El N&uacute;mero de Tarjeta debe ser un n&uacute;mero.",
				maxlength : "El N&uacute;mero de Tarjeta no debe contener m&aacute;s de 20 d&iacute;gitos."
			},
			trace : {
				notOnlySpaceOrEmpty : "El N&uacute;mero de Trace no debe contener solo espacios en blanco.",
				number : "El N&uacute;mero de Trace debe ser un n&uacute;mero.",
				maxlength : "El N&uacute;mero de Trace no debe contener m&aacute;s de 15 d&iacute;gitos."
			},
			idCanal : {
				number : "El N&uacute;mero de Canal debe ser un n&uacute;mero."
			},
			codigoProcesoSwitch : {
				number : "El C&oacute;digo de Proceso debe ser un n&uacute;mero."
			}
		},
		highlight : function(element) {
			$(element).parents(".group, .form-group").first().addClass('has-error');
		},
		unhighlight : function(element) {
			$(element).parents(".group, .form-group").first().removeClass('has-error');
		},
		errorElement : 'span',
		errorClass : 'help-block',
		errorPlacement : function(error, element) {
			if (element.parent('.input-group').length) {
				error.insertAfter(element.parent());
			} else if (element.parent().find("span.select2").length > 0) {
				error.appendTo(element.parent());
			} else {
				error.insertAfter(element);
			}
		}
	});
	
	$formAutenticacion.validate({
		rules:{
			password: {
				required: true
			}
		},
		messages: {
			password:{
				required: 'Ingrese contrase\u00F1a'
			}
		}
	});
	
});