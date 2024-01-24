$(document).ready(function() {

	var $local = {
		$btnHabilitarZPK: $('#btnZPK'),
		$btnRegistrar: $('#btnRegistrar'),
		$btnValidar: $('#btnValidar'),
		$inputZPK: $('#inputZPK')
	};

	$formLLaves = $("#formLLaves");

	$local.$btnHabilitarZPK.on("click", function() {
		let input = $(this).context.parentNode.previousElementSibling;
		$.confirm({
			title: 'Autenticaci\u00F3n',
			content: '' +
				'<form action="" class="formName">' +
				'<div class="form-group">' +
				'<label>Usuario</label>' +
				'<input type="text" placeholder="Usuario" class="usuario form-control" required autofocus/>' +
				'<label>Contrase\u00F1a</label>' +
				'<input type="password" placeholder="Contrase\u00F1a" class="contrasenia form-control" required />' +
				'</div>' +
				'</form>',
			buttons: {
				formSubmit: {
					text: 'Ingresar',
					btnClass: 'btn-green',
					action: function() {
						let usuario = this.$content.find('.usuario').val();
						let contrasenia = this.$content.find('.contrasenia').val();
						if (usuario === '' || contrasenia === '') {
							return false;
						}
						var user = {};
						user.idUsuario = usuario;
						user.contrasenia = contrasenia;
						verifcarUsuario(user, input);
					}
				},
				cancel: {
					text: 'Cancelar',
					btnClass: 'btn-red',
					action: function() {
						// cancelar
					}
				},
			},
			onContentReady: function() {
				// bind to events
				var jc = this;
				this.$content.find('form').on('submit', function(e) {
					// if the user submits the form by pressing enter in the field.
					e.preventDefault();
					jc.$$formSubmit.trigger('click'); // reference the button and click it
				});
			}
		});
	});


	$local.$btnRegistrar.on("click", function() {
		if (!$formLLaves.valid()) {
			return;
		}
		if ($local.$inputZPK.val() == '') {
			$funcionUtil.notificarException("Ingrese todos los campos del formulario", "fa-exclamation-circle", "Informaci\u00F3n", "info");
			return;
		}
		if (!validarFormatoHexadecimalSoloUno($local.$inputZPK)) {
			return;
		}
		var zpk = {};
		zpk.llaveZPK = $local.$inputZPK.val();
		$.ajax({
			type: "POST",
			url: $variableUtil.root + "seguridad/llaves/llaveTrabajo",
			data: JSON.stringify(zpk),
			beforeSend: function(xhr) {
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			success: function(response) {
				$funcionUtil.notificarException(response, "fa-check", "Aviso", "success");
			},
			error: function(response) {
			},
		});
	});

	function verifcarUsuario(user, input) {
		$.ajax({
			type: "POST",
			url: $variableUtil.root + "seguridad/llaves/auth",
			data: JSON.stringify(user),
			beforeSend: function(xhr) {
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			success: function(response) {
				if (response == true) {
					input.removeAttribute('disabled');
					$.alert('Auntenticaci\u00F3n correcta');
				} else {
					$.alert('Usuario o contrase\u00F1a incorrectos');
				}
			},
			error: function(response) {
			},
		});
	}

	$(".max-32-caracteres").on("keyup", function() {
		if (this.value.length === 32) {
			if (!validarFormatoHexadecimalSoloUno($local.$inputZPK)) {
				return;
			}
			var llave = {}
			llave.PKZ = this.value;
			var parametros = $.param(llave);
			$.ajax({
				type: "GET",
				url: $variableUtil.root + "seguridad/llaves?accion=calcularKCVLlaveTrabajo&" + parametros,
				contentType: "application/json",
				dataType: "json",
				beforeSend: function(xhr) {
					xhr.setRequestHeader('Content-Type', 'application/json');
					xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
				},
				success: function(response) {
					if (response != null) {
						$('#kcv').val(response.kcv);
						$('#divKCV').removeClass('hidden');
					}
				},
				error: function(response) {
				},
			});
		} else {
			$('#kcv').val('');
			$('#divKCV').addClass('hidden');
		}
	});

	$local.$btnValidar.on("click", function() {
		let num = $(this).attr('key');
		$local.$inputZPK.attr('disabled', true);
		$('#divKCV').addClass('hidden');
		$('#kcv').val('');
	});

	function validarFormatoHexadecimalSoloUno(input) {
		let formatoValido = true;
		if (!$funcionUtil.validarHexadecimal(input.val())) {
			$funcionUtil.notificarException("El valor ingresado no tiene un formato adecuado \nCaracteres aceptados: A-Z, 0-9", "fa-exclamation-circle", "Informaci\u00F3n", "info");
			formatoValido = false;
		}
		return formatoValido;
	}
});