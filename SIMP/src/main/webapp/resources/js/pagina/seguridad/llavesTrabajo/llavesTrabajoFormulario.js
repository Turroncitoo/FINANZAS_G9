$(document).ready(function() {

	$formLLaves.validate({
		focusCleanup : true,
		rules : {
			zpk : {
				required : true,
				rangelength : [ 32, 32 ]
			},
		},
		messages : {
			zpk : {
				required : "La llave zpk cifrada es requerido.",
				rangelength : "La llave zpk cifrada debe tener un tama\u00F1o de 32 car\u00E1cteres.",
			},
		}
	});
});