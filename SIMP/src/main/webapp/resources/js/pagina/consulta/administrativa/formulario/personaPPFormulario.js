$(document).ready(function() {

	$formMantenimiento.validate({
		rules : {
			tipoDocumento : {
				required : true,
			},
			numeroDocumento : {
				required : true,
			},
			nombres: {
				required : true,
			},
			apellidoPaterno: {
				required : true,
			},
			apellidoMaterno: {
				required : true,
			},
			telefonoMovil: {
				required : true,
			},
			sexo: {
				required : true,
			},
			correoElectronico: {
				required : true,
			},
			fechaNacimiento: {
				required : true,
			},
			paisWs: {
				required : true,
			}
		},
		messages : {
			tipoDocumento : {
				required : "Seleccione un tipo de documento",
			},
			numeroDocumento : {
				required : "Ingrese un n\u00FAmero de documento",
			},
			nombres: {
				required : "Ingrese un nombre",
			},
			apellidoPaterno: {
				required : "Ingrese un apellido paterno",
			},
			apellidoMaterno: {
				required : "Ingrese un apellido materno",
			},
			telefonoMovil: {
				required : "Ingrese un tel\u00E9fono",
			},
			sexo: {
				required : "Ingrese un sexo",
			},
			correoElectronico: {
				required : "Ingrese un correo",
			},
			fechaNacimiento: {
				required : "Ingrese una fecha de nacimiento",
			},
			paisWs: {
				required : "Ingrese una pa\u00EDs",
			}
		}
	});

});