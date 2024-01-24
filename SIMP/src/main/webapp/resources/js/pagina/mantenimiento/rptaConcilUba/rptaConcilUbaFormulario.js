$(document).ready(function() {

	$formMantenimiento.validate({
		rules : {
			idRespuestaConcilUba : {
				required : true,
				soloalfanumericos : true,
				rangelength : [ 1, 3 ],
			},
			descripcion : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 40 ]
			}
		},
		messages : {
			idRespuestaConcilUba : {
				required : "Ingrese un C&oacute;digo de Rpta. Concil. UBA.",
				soloalfanumericos : "El C&oacute;digo de Rpta. Concil. UBA debe contener solo n&uacute;meros y letras.",
				rangelength : "El C&oacute;digo de Rpta. Concil. UBA debe contener entre {0} y {1} car&aacute;cteres."

			},
			descripcion : {
				required : "Ingrese una descripci&oacute;n.",
				notOnlySpace : "La descripci&oacute;n no puede contener solo espacios en blanco.",
				rangelength : "La descripci&oacute;n debe contener entre {0} y {1} car&aacute;cteres."
			}
		}
	});

});