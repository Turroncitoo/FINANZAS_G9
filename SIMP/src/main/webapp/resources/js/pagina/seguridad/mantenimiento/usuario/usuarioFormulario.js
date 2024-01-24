$(document).ready(function() {
	$formMantenimiento.validate({
		focusCleanup : true,
		rules : {
			idUsuario : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 20 ]
			},
			contrasenia : {
				required : true,
				maxlength : 20
			},
			contraseniaConfirmacion : {
				equalTo : "#contrasenia"
			},
			idPerfil : {
				required : true
			}
		},
		messages : {
			idUsuario : {
				required : "Ingrese el Nombre de Usuario.",
				notOnlySpace : "El Nombre de Usuario no puede contener solo espacios en blanco.",
				rangelength : "El Nombre de Usuario debe contener entre <b>3 y 20 caracteres</b>."
			},
			contrasenia : {
				required : "Ingrese una Contrase\u00F1a.",
				maxlength : "La Contrase\u00F1a no debe contener m&aacute;s de <b>20 caracteres</b>."
			},
			contraseniaConfirmacion : {
				equalTo : "Las Contrase\u00F1as ingresadas <b>no coinciden</b>."
			},
			idPerfil : {
				required : "Seleccione un Perfil.",
			}
		}
	});
});