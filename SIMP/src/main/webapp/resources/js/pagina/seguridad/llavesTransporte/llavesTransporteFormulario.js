$(document).ready(function() {

	$formLLaves.validate({
		focusCleanup : true,
		rules : {
			componente1 : {
				required : true,
				rangelength : [ 32, 32 ]
			},
			componente2 : {
				required : true,
				rangelength : [ 32, 32 ]
			},
			componente3 : {
				required : true,
				rangelength : [ 32, 32 ]
			}
		},
		messages : {
			componente1 : {
				required : "El componente 1 es requerido.",
				rangelength : "El componente 1 debe tener un tama\u00F1o de 32 car\u00E1cteres.",
			},
			componente2 : {
				required : "El componente 2 es requerido.",
				rangelength : "El componente 2 debe tener un tama\u00F1o de 32 car\u00E1cteres.",
			},
			componente3 : {
				required : "El componente 3 es requerido.",
				rangelength : "El componente 3 debe tener un tama\u00F1o de 32 car\u00E1cteres.",
			}
		}
	});
});