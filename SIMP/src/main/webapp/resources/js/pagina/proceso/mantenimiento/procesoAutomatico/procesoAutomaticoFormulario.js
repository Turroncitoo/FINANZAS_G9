$(document).ready(function() {

	$formMantenimiento.validate({
		focusCleanup : true,
		rules : {
			codigoGrupo : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 1, 20 ],
				soloalfanumericos : true
			},
			descripcion : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 100 ]
			},
			horaProgramada : {
				required : true,
				notOnlySpace : true
			},
			ordenEjecucion : {
				required : true,
				number : true,
				range : [ 1, 99 ]
			}
		},
		messages : {
			codigoGrupo : {
				required : "Ingrese el C&oacute;digo de Grupo.",
				notOnlySpace : "El C&oacute;digo de Grupo no puede contener solo espacios en blanco.",
				rangelength : "El C&oacute;digo de Grupo debe contener entre 1 y 20 car&aacute;cteres.",
				soloalfanumericos : "El C&oacute;digo Grupo debe contener car&aacute;cteres alfanum&eacute;ricos."
			},
			descripcion : {
				required : "Ingrese una descripci&oacute;n.",
				notOnlySpace : "La descripci&oacute;n no puede contener solo espacios en blanco.",
				rangelength : "La descripci&oacute;n debe contener entre 3 y 100 caracteres."
			},
			horaProgramada : {
				required : "Ingrese la hora programada.",
				notOnlySpace : "La hora programada no puede contener solo espacios en blanco."
			},
			ordenEjecucion : {
				required : "Ingrese el orden de ejecuci&oacute;n.",
				number : "El valor de orden de ejecuci&oacute;n debe ser un n&uacute;mero.",
				range : "El valor de orden de ejecuci&oacute;n debe ser 1 o 99."
			}
		}
	});
});