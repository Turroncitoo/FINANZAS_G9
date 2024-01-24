$(document).ready(function() {

	$formMantenimiento.validate({
		focusCleanup : true,
		rules : {
			codigoSubModulo : {
				required : true,
				notOnlySpace : true,
				selectlength : [ 1, 4 ],
				soloalfanumericos : true
			},
			descripcion : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 50 ]
			}
		},
		messages : {
			codigoSubModulo : {
				required : "Ingrese C&oacute;digo de Sub M&oacute;dulo.",
				notOnlySpace : "El C&oacute;digo de Sub M&oacute;dulo no puede contener solo espacios en blanco.",
				selectlength : "El C&oacute;digo de Sub M&oacute;dulo debe contener 4 car&aacute;cteres.",
				soloalfanumericos : "El C&oacute;digo de Sub M&oacute;dulo debe contener car&aacute;cteres alfanum&eacute;ricos."	
			},
			descripcion : {
				required : "Ingrese una descripci&oacute;n.",
				notOnlySpace : "La descripci&oacute;n no puede contener solo espacios en blanco.",
				rangelength : "El descripci&oacute;n debe contener entre 3 y 50 car&aacute;cteres."
			}
		}
	});

});