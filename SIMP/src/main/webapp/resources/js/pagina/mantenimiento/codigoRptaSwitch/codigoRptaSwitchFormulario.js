$(document).ready(function() {

	$formMantenimiento.validate({
		rules : {
			codigoRespuestaSwitch : {
				required : true,
				soloalfanumericos : true,
				rangelength : [ 1, 3 ]
			},
			descripcion : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 80 ]
			},
			tipoRespuesta : {
				required : true,
				notOnlySpace : true,
				selectlength : [ 1, 4 ]
			}
		},
		messages : {
			codigoRespuestaSwitch : {
				required : "Ingrese un C&oacute;digo de Respuesta Switch.",
				soloalfanumericos : "El C&oacute;digo de Respuesta Switch debe contener solo n&uacute;meros y letras.",
				rangelength : "El C&oacute;digo de Respuesta Switch debe contener entre {0} y {1} car&aacute;cteres."
			},
			descripcion : {
				required : "Ingrese una descripci&oacute;n.",
				notOnlySpace : "La descripci&oacute;n no puede contener solo espacios en blanco.",
				rangelength : "La descripci&oacute;n debe contener entre {0} y {1} car&aacute;cteres."
			},
			tipoRespuesta : {
				required : "Seleccione un Tipo de Respuesta.",
				notOnlySpace : "El Tipo de Respuesta no puede contener solo espacios en blanco.",
				selectlength : "El Tipo de Respuesta debe contener entre {0} y {1} car&aacute;cteres."
			}
		}
	});

});