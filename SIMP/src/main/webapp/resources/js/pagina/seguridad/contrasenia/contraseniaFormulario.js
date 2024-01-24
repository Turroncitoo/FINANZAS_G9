$(document).ready(function() {

	$formContrasenia.validate({
		focusCleanup : true,
		rules : {
			contraseniaActual : {
				required : true
			},
			contrasenia : {
				required : true
			},
			contraseniaConfirmacion : {
				required : true,
				equalTo : "#contrasenia"
			}
		},
		messages : {
			contraseniaActual : {
				required : "Ingrese la contrase\u00F1a actual.",
			},
			contrasenia : {
				required : "Ingrese la contrase\u00F1a nueva.",
			},
			contraseniaConfirmacion : {
				required : "Ingrese la confirmaci&oacute;n de la nueva contrase\u00F1a.",
				equalTo : "Las contrase\u00F1as no coinciden."
			}
		}
	});
});