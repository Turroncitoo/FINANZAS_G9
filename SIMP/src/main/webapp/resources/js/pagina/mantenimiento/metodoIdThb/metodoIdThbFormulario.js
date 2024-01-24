$(document).ready(function() {

	$formMantenimiento.validate({
		rules : {
			idMetodo : {
				numberorempty : true,
				range : [ 0, 9 ],
				maxlength : 1
			},
			descripcion : {
				required : true,
				notOnlySpace : true,
				rangelength : [3, 20]
			}
		},
		messages : {
			idMetodo : {
				numberorempty : "El Identificador de M\u00E9todo Id. Thb. debe contener solo n&uacute;meros o estar vac\u00EDo.",
				range : "El Identificador de M\u00E9todo Id. Thb. debe estar entre 0 y 9.",
				maxlength : "El Identificador de M\u00E9todo Id. Thb. no debe contener m&aacute;s de 1 d&iacute;gito."
			},
			descripcion : {
				required : "Ingrese una descripci&oacute;n.",
				notOnlySpace : "La descripci&oacute;n no puede contener solo espacios en blanco.",
				rangelength : "La descripci&oacute;n debe contener entre 3 y 20 car&aacute;cteres."
			}
		}
	});

});