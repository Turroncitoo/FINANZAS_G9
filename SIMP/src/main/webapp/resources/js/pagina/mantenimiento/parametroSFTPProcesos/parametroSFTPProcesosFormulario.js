$(document).ready(function(){
	
	$formMantenimiento.validate({
		rules:{
			codigo:{
				required: true,
				maxlength: 3,
				notOnlySpace: true
			},
			descripcion:{
				required: true,
				rangelength: [3, 30],
				notOnlySpace: true
			}
		},
		messages:{
			codigo:{
				required: 'Ingrese un c\u00f3digo de proceso.',
				maxlength: 'El c\u00f3digo de proceso no debe contener m\u00e1s de {0} caracteres.',
				notOnlySpace: 'El c\u00f3digo de proceso no debe contener solo espacios en blanco.'
			},
			descripcion:{
				required: 'Ingrese una descripci\u00F3n.',
				rangelength: 'La descripci\u00f3n de proceso debe contener entre {0} y {1} caracteres.',
				notOnlySpace: 'La descripci\u00f3n de proceso no debe contener solo espacios en blanco.'
			}
		}
	});
});