$(document).ready(function() {

	$.validator.addMethod('selectlength', function(value, element, param) {
		if ($.isArray(param)) {
			var min = param[0];
			var max = param[1];
			return value.length >= min && value.length <= max;
		} else {
			var tamanio = param;
			return value.length == tamanio
		}
	}, $.validator.format('Please select at least {0} things.'));

	jQuery.validator.addMethod("notOnlySpace", function(value, element, string) {
		if (value == null) {
			return true;
		}
		return value.replace(/\s/g, '').length > 0;
	}, $.validator.format("Please enter '{0}'"));

	jQuery.validator.addMethod("notOnlySpaceMoreOneBlank", function(value, element, string) {
		if (value == null) {
			return true;
		}
		return !(value.length > 1 && value.replace(/\s/g, '').length == 0);
	}, $.validator.format("Please enter '{0}'"));

	jQuery.validator.addMethod("noWhitespace", function(value, element) {
	    return this.optional(element) || /^\S+$/.test(value);
	}, "El campo no puede contener espacios en blanco.");
	
	jQuery.validator.addMethod("notOnlySpaceOrEmpty", function(value, element, string) {
		if (value == null) {
			return true;
		}
		return value.length == 0 ? true : value.replace(/\s/g, '').length > 0;
	}, $.validator.format("Please enter '{0}'"));

	//	jQuery.validator.addMethod("lettersonly", function(value, element) {
	//		return this.optional(element) || /^[a-z\u00f1\u00d1\u00E0-\u00FC\' ']+$/i.test(value);
	//	}, "Ingrese solo letras");

	jQuery.validator.addMethod("soloalfanumericos", function(value, element) {
		return this.optional(element) || /^[a-z0-9]+$/i.test(value);
	}, "Ingrese solo alfanum\u00E9ricos");

	jQuery.validator.addMethod("alphanumeric2", function(value, element) {
		return this.optional(element) || (/^[a-zA-Z0-9]+$/i.test(value) && /[a-z]/.test(value)
			&& /[A-Z]/.test(value)
			&& /[0-9]/.test(value))
	}, "Ingrese solo min\u00FAsculas, may\u00FAsculas y n\u00FAmeros");

	jQuery.validator.addMethod("solodigitos", function(value, element) {
		return this.optional(element) || /^[0-9]+$/i.test(value);
	}, "Ingrese solo digitos");

	jQuery.validator.addMethod("hora", function(value, element, string) {
		if (value == null) {
			return true;
		}
		return value.length == 0 ? true : value.replace(/\s/g, '').length > 0;
	}, $.validator.format("Please enter '{0}'"));

	jQuery.validator.addMethod("hora", function(value, string) {
		return false || /^(?:2[0-3]|[01][0-9]):[0-5][0-9]:[0-5][0-9]$/i.test(value);
	}, "Ingrese solo horas");

	jQuery.validator.addMethod("numberorempty", function(value, element) {
		return this.optional(element) || /^[0-9\'']+$/i.test(value);
	}, "Ingrese solo numeros o vacio");

	$.validator.addMethod("equals", function(value, element, string) {
		return $.inArray(value, string) !== -1;
	}, $.validator.format("Please enter '{0}'"));

	jQuery.validator.addMethod("alphanumericWithSpaces", function(value, element) {
		return this.optional(element) || /^[A-Za-z\d\s]+$/.test(value);
	}, "Ingrese solo alfanumericos");

	//	jQuery.validator.addMethod("comparacion", function(value, element) {
	//		var usuario = $("#codigoUsuario").val();
	//		var contrasenia = $("#contrasenia").val();
	//		int resultado = contrasenia.indexOf(usuario);
	//		if(resultado != -1) {
	//			return true;
	//		}else{
	//			return false;
	//		}
	//        },"Codigo no coincide"+'<br>'+"Presionar el boton generar codigo");
	//	
	$.fn.clearValidation = function() {
		var v = $(this).validate();
		$('[name]', this).each(function() {
			v.successList.push(this);
			v.showErrors();
		});
		v.resetForm();
		v.reset();
	};

	$.validator.setDefaults({
		focusCleanup: true,
		highlight: function(element) {
			$(element).parents(".group, .form-group").first().addClass('has-error');
		},
		unhighlight: function(element) {
			$(element).parents(".group, .form-group").first().removeClass('has-error');
		},
		errorElement: "span",
		errorClass: "help-block",
		errorPlacement: function(error, element) {
			if (element.parent(".input-group").length) {
				error.insertAfter(element.parent());
			} else if (element.parent().find("span.select2").length > 0) {
				error.appendTo(element.parent());
			} else {
				error.appendTo(element.parents("div")[0]);
			}
		}
	});


	jQuery.validator.addMethod("soloNumeroPositivos", function(value, element) {
		if (this.optional(element)) {
			return true;
		} else if (/^[0-9\'']+$/i.test(value)) {
			if (value <= 0) return false;
			else return true;
		}
	}, "Ingrese solo numeros mayor a cero o vacio");


	jQuery.validator.addMethod("moneda", function(value, element) {
		if (this.optional(element)) {
			return true;
		} else if (/^\d*(\.\d{1})?\d{0,1}$/.test(value)) {
			if (value <= 0) return false;
			else return true;
		}
	}, "Ingrese solo numeros con formato moneda (2 decimales max)");

	/*jQuery.validator.addMethod("greaterThan", function(value, element, param) {
		let $min = $(param); if ($min.val() != null && $min.val() != '') {
			if (this.settings.onfocusout) {
				$min.off(".validate-greaterThan").on("blur.validate-greaterThan", function() {
					$(element).valid();
				});
			} return parseInt(value) > parseInt($min.val());
		} return true;
	}, "El valor debe ser mayor que {0}");*/

	jQuery.validator.addMethod("startsWith033", function(value, element) {
		return this.optional(element) || value.indexOf("033") === 0;
	}, "El valor debe comenzar con '033'");

	jQuery.validator.addMethod("noSpaces", function(value, element) {
		return this.optional(element) || /^\S+$/.test(value);
	}, "El valor no debe contener espacios");

	jQuery.validator.addMethod("minimoDependiente", function(value, element, param) {
		let $select = $(param).val(); if ($select != null && $select != undefined) {
			let falta = 16 - $select.length; for (let i = 1; i <= falta; i++) {
				$select += '0'
			} return parseInt(value) >= parseInt($select);
		} return true;
	}, "El valor m\u00EDnimo depende del valor del campo de BIN seleccionado");

	jQuery.validator.addMethod("maximoDependiente", function(value, element, param) {
		let $select = $(param).val(); if ($select != null && $select != undefined) {
			let falta = 16 - $select.length; for (let i = 1; i <= falta; i++) {
				$select += '9'
			} return parseInt(value) <= parseInt($select);
		} return true;
	}, "El valor m\u00E1ximo depende del valor del campo de BIN seleccionado");

	jQuery.validator.addMethod("tamanioRequerido", function(value, element, param) {
		let $select = $(param).val();
		return $select != null && $select != undefined && $select !== '' ? value.length == parseInt($select) : true
	}, `El n\u00FAmero de caracteres depende de Longitud BIN`);

	jQuery.validator.addMethod("rangoInicial", function(value, element, param) {
		let $bin = $(param[0]).val();
		let $pan = $(param[1]).val();
		if (($bin === null || $bin === '' || $bin === undefined) || ($pan === null || $pan === '' || $pan === undefined)) {
			return true
		}
		const restante = parseInt($pan) - parseInt($bin)
		param[2] = restante
		return value.length === parseInt(restante)
	}, `El Rango depende del BIN y PAN`);

	jQuery.validator.addMethod("smallerThan", function(value, element, param) {
		let $select = $(param).val();
		return $select != null && $select != undefined && $select !== '' ? parseInt(value) < parseInt($select) : true
	}, `Tiene que ser menor al Rango Final`);

	jQuery.validator.addMethod("greaterThan", function(value, element, param) {
		let $min = $(param);
		return $min.val() != null && $min.val() != '' ? parseInt(value) > parseInt($min.val()) : true;

	}, "El valor debe ser mayor que {0}");

	/*jQuery.validator.addMethod("greaterThan", function(value, element, param) {
		let $select = $(param).val();
		return $select != null && $select != undefined && $select !== '' ? parseInt(value) <= parseInt($select) : true
	}, `Tiene que ser menor al Rango Final`);*/

});