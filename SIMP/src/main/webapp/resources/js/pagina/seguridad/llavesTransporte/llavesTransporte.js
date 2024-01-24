$(document).ready(function() {

	var $local = {
		$botonesComponentes: $('body .btn-componente'),
		$btnRegistrar: $('#btnRegistrar'),
		$divMsjLlaveExistente: $('#divMsjLlaveExistente'),
		$inputComponente1: $('#inputComponente1'),
		$inputComponente2: $('#inputComponente2'),
		$inputComponente3: $('#inputComponente3'),
		$botonesValidarComponentes: $('body .validar-componente'),
	};

	$formLLaves = $("#formLLaves");

	$local.$botonesComponentes.on("click", function() {
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
		//Validaciones
		if (!$formLLaves.valid()) {
			return;
		}
		if ($local.$inputComponente1.val() == '' || $local.$inputComponente2.val() == '' || $local.$inputComponente3.val() == '') {
			$funcionUtil.notificarException("Ingrese todos los campos del formulario", "fa-exclamation-circle", "Informaci\u00F3n", "info");
			return;
		}
		if (!validarFormatoHexadecimalTodos()) {
			return;
		}
		//Peticion al backend
		var zpk = {};
		zpk.componente1 = $local.$inputComponente1.val();
		zpk.componente2 = $local.$inputComponente2.val();
		zpk.componente3 = $local.$inputComponente3.val();
		$.ajax({
			type: "POST",
			url: $variableUtil.root + "seguridad/llaves/llaveTransporte",
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

	$(".max-32-caracteres").on("keyup", function() {
		let num = $(this).attr('key');
		if (this.value.length === 32) {
			if (!validarFormatoHexadecimalSoloUno($("#inputComponente" + num), num)) {
				return;
			}
			var llave = {}
			llave.componente = this.value;
			var paramComponente = $.param(llave);
			$.ajax({
				type: "GET",
				url: $variableUtil.root + "seguridad/llaves?accion=calcularKCVComponente&" + paramComponente,
				contentType: "application/json",
				dataType: "json",
				beforeSend: function(xhr) {
					xhr.setRequestHeader('Content-Type', 'application/json');
					xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
				},
				success: function(response) {
					if (response != null) {
						$('#kcv' + num).val(response.kcv);
						$('#divKCV' + num).removeClass('hidden');
					}
				},
				error: function(response) {
				},
			});
		} else {
			$('#kcv' + num).val('');
			$('#divKCV' + num).addClass('hidden');
		}
	});

	$local.$botonesValidarComponentes.on("click", function() {
		let num = $(this).attr('key');
		$('#inputComponente' + num).attr('disabled', true);
		$('#divKCV' + num).addClass('hidden');
		$('#kcv' + num).val('');
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

	function validarFormatoHexadecimalSoloUno(input, num) {
		let formatoValido = true;
		if (!$funcionUtil.validarHexadecimal(input.val())) {
			$funcionUtil.notificarException("El componente " + num + " no tiene un formato adecuado \nCaracteres aceptados: A-Z, 0-9", "fa-exclamation-circle", "Informaci\u00F3n", "info");
			formatoValido = false;
		}
		return formatoValido;
	}

	function validarFormatoHexadecimalTodos() {
		let formatoValido = true;
		if (!$funcionUtil.validarHexadecimal($local.$inputComponente1.val())) {
			$funcionUtil.notificarException("El componente 1 no tiene un formato adecuado \nCaracteres aceptados: A-Z, 0-9", "fa-exclamation-circle", "Informaci\u00F3n", "info");
			formatoValido = false;
		}
		if (!$funcionUtil.validarHexadecimal($local.$inputComponente2.val())) {
			$funcionUtil.notificarException("El componente 2 no tiene un formato adecuado \nCaracteres aceptados: A-Z, 0-9", "fa-exclamation-circle", "Informaci\u00F3n", "info");
			formatoValido = false;
		}
		if (!$funcionUtil.validarHexadecimal($local.$inputComponente3.val())) {
			$funcionUtil.notificarException("El componente 3 no tiene un formato adecuado \nCaracteres aceptados: A-Z, 0-9", "fa-exclamation-circle", "Informaci\u00F3n", "info");
			formatoValido = false;
		}
		return formatoValido;
	}

});