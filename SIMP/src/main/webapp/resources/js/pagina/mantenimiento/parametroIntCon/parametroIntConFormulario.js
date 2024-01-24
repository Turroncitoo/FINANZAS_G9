$(document).ready(function() {

	$formMantenimiento.validate({
		rules : {
			usuarioAlegra : {
				required : true,
				notOnlySpace : true,
			},
			tokenAlegra: {
				required : true,
				notOnlySpace : true,
				
			},
			cuentaContableURI : {
				required : true, 
				notOnlySpace : true
				
			},
			pagoURI : {
				required : true, 
				notOnlySpace : true
				
			}
			
		},
		messages : {
			usuarioAlegra : {
				required : "Ingrese su usuario de Alegra",
				notOnlySpace : "El usuario no puede contener espacios en blanco.",
			},
			tokenAlegra : {
				required : "Ingrese su token de Alegra",
				notOnlySpace : "El token no puede contener espacios en blanco.",
			
			},
			cuentaContableURI : {
				required : "Ingrese la URI del servicio de ALEGRA.",
				notOnlySpace : "La URI no puede contener espacios en blanco.",
			},
			pagoURI : {
				required : "Ingrese la URI del servicio de ALEGRA.",
				notOnlySpace : "La URI no puede contener espacios en blanco.",
			}
		}
	});

});