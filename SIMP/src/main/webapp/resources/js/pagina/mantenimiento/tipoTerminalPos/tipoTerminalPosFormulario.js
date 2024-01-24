$(document).ready(function() {

	$formMantenimiento.validate({
		rules : {
			codigoTipoTerminalPOS : {
				numberorempty : true,
				range : [ 0, 9 ],
				maxlength : 1
			},
			descripcion : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 60 ]
			}
		},
		messages : {
			codigoTipoTerminalPOS : {
				numberorempty : "El C&oacute;digo de Tipo de Terminal POS debe contener solo n&uacute;mero o estar vac&iacute;o.",
				range : "El C&oacute;digo de Tipo de Terminal POS debe contener 1 d&iacute;gito.",
				maxlength : "El C&oacute;digo de Tipo de Terminal POS no debe contener m&aacute;s de 1 d&iacute;gito."
			},
			descripcion : {
				required : "Ingrese una descripci&oacute;n.",
				notOnlySpace : "La descripci&oacute;n no debe contener solo espacios en blanco.",
				rangelength : "La descripci&oacute;n debe contener entre 3 y 60 car&aacute;cteres."
			}
		}
	});

});