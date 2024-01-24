$(document).ready(function() {

	$formMantenimiento.validate({
		rules : {
			codigoCanalEmisor : {
				required : true,
				soloalfanumericos: true,
				maxlength : 2
			},
			descripcion : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 40 ]
			}
		},
		messages : {
			codigoCanalEmisor : {
				required : "Ingrese un C&oacute;digo de Canal de Emisor.",
				soloalfanumericos : "El C&oacute;digo de Canal de Emisor debe contener car&aacute;cteres alfanumericos.",
				maxlength : "El C&oacute;digo de Canal de Emisor debe contener como m\u00E1ximo 2 car&aacute;cteres."
			},
			descripcion : {
				required : "Ingrese una descripci&oacute;n.",
				notOnlySpace : "La descripci&oacute;n no puede contener solo espacios en blanco.",
				rangelength : "La descripci&oacute;n debe contener entre 3 y 40 car&aacute;cteres."
			}
		}
	});

});