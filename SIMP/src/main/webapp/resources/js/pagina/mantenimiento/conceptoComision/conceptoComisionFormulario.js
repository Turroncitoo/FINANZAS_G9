$(document).ready(function() {

	$formMantenimiento.validate({
		rules : {
			idConceptoComision : {
				required : true,
				number : true,
				rangelength : [ 1, 3 ]
			},
			tipoComision : {
				required : true,
				number : true,
				rangelength : [ 1, 3 ],
				equalTo: "#conComi"
			},
			descripcion : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 30 ]
			},
			abreviatura : {
				required : true,
				notOnlySpace : true,
				soloalfanumericos: true,
				rangelength : [3, 3]
			}
		},
		messages : {
			idConceptoComision : {
				required : "Ingrese un Concepto Comisi&oacute;n.",
				rangelength : "El Concepto Comisi&oacute;n debe contener entre {0} y {1} d\u00EDgitos."
			},
			tipoComision : {
				required : "Ingrese un Tipo de Comisi&oacute;n.",
				rangelength : "El Tipo Comisi&oacute;n debe contener entre {0} y {1} d\u00EDgitos.",
				equalTo: "El Tipo Comisi&oacute;n debe ser igual al Concepto Comisi&oacute;n"
			},
			descripcion : {
				required : "Ingrese una descripci&oacute;n.",
				notOnlySpace : "La descripci&oacute;n no puede contener solo espacios en blanco.",
				rangelength : "La descripci&oacute;n debe contener entre {0} y {1} caracteres."
			},
			abreviatura : {
				required : "Ingrese una abreviatura.",
				notOnlySpace : "La abreviatura no puede contener solo espacios en blanco.",
				rangelength : "La abreviatura debe contener m\u00E1ximo {0} caracteres.",
				soloalfanumericos : "La abreviatura debe contener solo n&uacute;meros y letras.",
			}
		}
	});

});