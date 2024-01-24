$(document).ready(function(){
	
	$formMantenimiento.validate({
		rules : {
			idInstitucion : {
				required : true,
			},
			idLogo : {
				required : true,
			},
			idEmpresa : {
				required : true,
			},
			idCliente : {
				required : true,
			},
			idAfinidad : {
				required : true,
			},
		},
		messages : {
			idInstitucion : {
				required : "Ingrese una instituci\u00F3n",
			},
			idLogo : {
				required : "Ingrese un Logo.",
			},
			idEmpresa : {
				required : "Ingrese una empresa",
			},
			idCliente : {
				required : "Ingrese una cliente",
			},
			idAfinidad : {
				required : "Ingrese una afinidad",
			},
		}
	});

});