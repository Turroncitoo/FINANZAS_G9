$(document).ready(function() {

	var $local = {
		$actualizarContrasenia : $("#actualizarContrasenia"),
	};

	/* Variable Global */
	$formContrasenia = $("#formContrasenia");

	$formContrasenia.find("input").keypress(function(event) {
		if (event.which == 13) {
			$local.$actualizarContrasenia.trigger("click");
			return false;
		}
	});

	$local.$actualizarContrasenia.on("click", function() {
		if (!$formContrasenia.valid()) {
			return;
		}
		var contrasenia = $formContrasenia.serializeJSON();
		$.ajax({
			type : "PUT",
			url : $variableUtil.root + "seguridad/contrasenia",
			data : JSON.stringify(contrasenia),
			beforeSend : function(xhr) {
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
				$local.$actualizarContrasenia.attr("disabled", true).find("i").removeClass("fa-pencil-square").addClass("fa-spinner fa-pulse fa-fw");
			},
			statusCode : {
				400 : function(response) {
					$funcionUtil.limpiarMensajesDeError($formContrasenia);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formContrasenia);
				}
			},
			success : function(contrasenias) {
				$funcionUtil.notificarException($variableUtil.actualizacionExitosa, "fa-check", "Aviso", "success");
				$funcionUtil.prepararFormularioRegistro($formContrasenia);
			},
			error : function(response) {
			},
			complete : function(response) {
				$local.$actualizarContrasenia.attr("disabled", false).find("i").addClass("fa-pencil-square").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});
});