$(document).ready(function() {

	$formMantenimiento.validate({
		rules : {
			codigoModoEntradaPOS : {
				numberorempty : true,
				range : [ 0, 99 ],
				maxlength : 2
			},
			descripcion : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 80 ]
			}
		},
		messages : {
			codigoModoEntradaPOS : {
				numberorempty : "El C&oacute;digo de Modo de Entrada POS debe contener solo n&uacute;meros o estar vac&iacute;o.",
				range : "El C&oacute;digo de Modo de Entrada POS debe estar entre 0 y 99.",
				maxlength : "El Identificador de M\u00E9todo Id. Thb. no debe contener m&aacute;s de 2 d&iacute;gitos."
			},
			descripcion : {
				required : "Ingrese una descripci&oacute;n.",
				notOnlySpace : "La descripci&oacute;n no debe contener solo espacios en blanco.",
				rangelength : "La descripci&oacute;n debe contener entre 3 y 80 car&aacute;cteres."
			}
		}
	});

});