$(document).ready(function() {

	$formMantenimiento.validate({
		focusCleanup : true,
		rules : {
			idPerfil : {
				required : true,
				notOnlySpace : true,
				lettersonly : true,
				rangelength : [ 3, 20 ]
			},
			descripcion : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 50 ]
			}
		},
		messages : {
			idPerfil : {
				required : "Ingrese un C&oacute;digo de Perfil.",
				notOnlySpace : "El C&oacute;digo de Perfil no puede contener solo espacios en blanco.",
				lettersonly : "El C&oacute;digo de Perfil debe contener solo letras.",
				rangelength : "El C&oacute;digo de Perfil debe contener entre <b>3 y 20 caracteres</b>."
			},
			descripcion : {
				required : "Ingrese una descripci&oacute;n.",
				notOnlySpace : "La descripci&oacute;n no puede contener solo espacios en blanco.",
				rangelength : "La descripci&oacute;n debe contener entre <b>3 y 50 caracteres</b>."
			}
		}
	});
});