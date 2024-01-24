$(document).ready(function() {
	$formMantenimiento.validate({
		focusCleanup : true,
		rules : {
			numeroMaximoIntentos : {
				required : true,
				digits : true,
				range : [ 1, 99 ]
			},
			complejidadContrasenia : {
				digits : true,
				range : [ 0, 1 ]
			},
			cantidadDiasParaCaducidadContrasenia : {
				required : true,
				digits : true,
				range : [ 1, 99 ]
			},
			longitudMinimaContrasenia : {
				required : true,
				digits : true,
				range : [ 1, 99 ]
			},
			autenticacionActiveDirectory : {
				digits : true,
				range : [ 0, 1 ]
			}
		},
		messages : {
			numeroMaximoIntentos : {
				required : "Ingrese N&uacute;mero M&aacute;ximo de Intentos.",
				digits : "Ingrese Solo n&uacute;meros.",
				range : "N&uacute;mero M&aacute;ximo de Intentos debe estar entre 1 y 99."
			},
			complejidadContrasenia : {
				digits : "Solo n&uacute;meros.",
				range : "Complejidad de Contrase\u00F1a debe estar entre 0 y 1."
			},
			cantidadDiasParaCaducidadContrasenia : {
				required : "Ingrese la Cantidad de Dias.",
				digits : "Ingrese Solo n&uacute;meros.",
				range : "N&uacute;mero de dias de caducidad debe estar entre 1 y 99."
			},
			longitudMinimaContrasenia : {
				required : "Ingrese Longitud M&iacute;nima para Contrasenia.",
				digits : "Ingrese Solo n&uacute;meros.",
				range : "Longitud de Contrase\u00F1a debe estar entre 1 y 99."
			},
			autenticacionActiveDirectory : {
				digits : "Solo n&uacute;meros.",
				range : "Active Directory debe estar entre 0 y 1."
			}
		}
	});
});