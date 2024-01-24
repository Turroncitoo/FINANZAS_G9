$(document).ready(function(){
	
	$formMantenimiento.validate({
		rules: {
			codigoProceso: {
				required: true,
				maxlength: 3
			},
			tipoOperacion: {
				required: true,
				maxlength: 4
			},
			directorioOrigen: {
				required: true,
				rangelength: [3, 200]
			},
			directorioDestino: {
				required: true,
				rangelength: [3, 200]
			}
		},
		messages: {
			codigoProceso: {
				required: 'Seleccione un C\u00F3digo de Proceso.',
				maxlength: 'El C\u00f3digo de Proceso debe contener como m\u00e1ximo {0} caracteres.'
			},
			tipoOperacion: {
				required: 'Seleccione un Tipo de Operaci\u00F3n.',
				maxlength: 'El Tipo de Operaci\u00f3n debe contener como m\u00e1ximo {0} caracteres.'
			},
			directorioOrigen: {
				required: 'Ingrese un Directorio Origen.',
				rangelength: 'El Directorio Origen debe tener como m\u00e1ximo {0} caracteres.'
			},
			directorioDestino: {
				required: 'Ingrese un Directorio Destino.',
				rangelength:'El Directorio Destino debe tener como m\u00e1ximo {0} caracteres.'
			}
		}
	});
	
});