$(document).ready(function() {

	$formMantenimiento.validate({
		rules : {
			codigo : {
				required : true,
				notOnlySpace : true,
				rangelength: [1, 1],
				soloalfanumericos: true
			},
			descripcion : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 50 ]
			}
		},
		messages : {
			codigo : {
				required : "Ingrese un C&oacute;odigo.",
				notOnlySpace : "El C&oacute;odigo no puede contener solo espacios en blanco.",
				rangelength: "El C&oacute;odigo no debe m&aacute;s de {1} caracter(es)."
			},
			descripcion : {
				required : "Ingrese una descripci&oacute;n.",
				notOnlySpace : "La descripci&oacute;n no puede contener solo espacios en blanco.",
				rangelength : "La descripci&oacute;n debe contener entre 3 y 50 car&aacute;cteres."
			}
		}
	});
	
});