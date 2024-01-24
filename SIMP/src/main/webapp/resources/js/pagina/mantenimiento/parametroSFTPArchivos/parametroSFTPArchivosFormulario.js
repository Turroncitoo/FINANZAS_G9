$(document).ready(function(){
	
	$formMantenimiento.validate({
		rules: {
			codigoArchivo: {
				required: true,
				rangelength: [1, 10]
			},
			codigoDirectorio:{
				required: true
			},
			origenOriginal: {
				required: true,
				rangelength: [3, 200]
			},
			extensionOrigenOriginal: {
				required: true,
				rangelength: [3, 20]
			},
			descripcion: {
				required: true,
				rangelength: [0, 200]
			},
			numeroBytes: {
				required: true,
				range: [0, 9999999]
			},
			diasAumentoRebajoFechaProceso : {
				required: true,
				range: [-99, 99]
			}
		},
		messages: {
			codigoArchivo: {
				required: 'Ingrese un C\u00F3digo de Archivo',
				rangelength: 'El C\u00F3digo de Archivo debe contener entre {0} y {1} caracteres.'
			},
			codigoDirectorio:{
				required: 'Seleccione un Proceo - Operaci\u00F3n.'
			},
			origenOriginal: {
				required: 'Ingrese un Nombre de Origen.',
				rangelength: "El Nombre de Origen debe contener entre {0} y {1} caracteres."
			},
			extensionOrigenOriginal: {
				required: "Ingrese la Extensi\u00F3n del Archivo.",
				rangelength: "La Extensi\u00F3n del Archivo debe contener entre {0} y {1} caracteres."
			},
			descripcion: {
				required: 'Ingrese la descripci\u00F3n del Archivo.',
				rangelength: "La descripci\u00F3n del Archivo debe contener entre {0} y {1} caracteres."
			},
			numeroBytes: {
				required: "Ingrese el N\u00FAmero de Bytes.",
				range: "El N\u00FAmero de Bytes debe estar entre {0} y {1}."
			},
			diasAumentoRebajoFechaProceso : {
				required: "Ingrese la cantidad de d\u00EDas de env\u00EDo.",
				range: "La cantidad de d\u00EDas debe estar entre {0} y {1}."
			}
		}
	});
	
});