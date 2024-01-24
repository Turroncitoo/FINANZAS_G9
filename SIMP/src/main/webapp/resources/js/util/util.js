var $funcionUtil = {};
var $variableUtil = {};
var $tablaFuncion = {};

$(document).ready(function() {

	var rutaEnSidebar = "";

	var getText = function(text) {
		var i;
		var j = 0;
		for (i = 0; i < text.length; i++) {
			if (text[i] != " " && text[i] != "\n" && text[i] != "\t")
				break;
		}
		if (i != text.length && text.substring(i).length <= 30) {
			for (j = i + 1; j < text.length; j++)
				if (text[j] == "\n" || text[j] == "\t")
					break;
			return text.substring(i, j >= text.length ? j - 1 : j);
		} else
			return undefined
	}

	var addText = function(text) {
		text = text.replace(" ", "_");
		rutaEnSidebar = text + "_/_" + rutaEnSidebar;
	}

	$("a").filter(function() {
		return $(this).attr('href') === location.pathname;
	}).parents('li').contents().each(function(index) {
		var realText = getText($(this).text());
		if (realText)
			addText(realText);
	});

	var path = location.pathname;
	$('.x-navigation li').find("a").filter(function() {
		return $(this).attr('href') === path;
	}).parent("li").addClass('active').parents("li.xn-openable").addClass("active");

	$localUtil = {
		$navegacionFechaProceso: $("#navegacionFechaProceso")
	}

	$variableUtil = {
		csrf: $('meta[name=_csrf]').attr("content"),
		root: $("meta[name='root']").attr("content"),
		rutaEnSidebar: rutaEnSidebar,
		posIzquierdo: "izquierdo",
		posDerecho: "derecho",
		rutaIconoSimp: this.root + "resources/css/icono-simp/",
		botonEliminar: "<button class='btn btn-xs btn-danger eliminar' title='Eliminar' data-tooltip='tooltip'><i class='fa fa-trash'></i></button>",
		botonActualizar: "<button class='btn btn-xs btn-primary actualizar' title='Actualizar' data-tooltip='tooltip'><i class='fa fa-pencil-square'></i></button>",
		botonActualizarAfiliacion: "<button class='btn btn-xs btn-primary actualizar' title='Actualizar' data-tooltip='tooltip'><i class='fa fa-pencil-square'></i></button>",
		botonActualizarExtDev: "<button class='btn btn-xs btn-warning actualizar-indExtDev' title='Actualizar' data-tooltip='tooltip'><i class='fa fa-pencil-square'></i></button>",
		botonActualizarTrace: "<button class='btn btn-xs btn-primary actualizar' title='Actualizar Trace' data-tooltip='tooltip'><i class='fa fa-pencil-square'></i></button>",
		botonAniadirDetalle: "<button class='btn btn-xs btn-success aniadir-detalle' title='A\u00F1adir' data-tooltip='tooltip'><i class='fa fa-plus'></i></button>",
		botonVerDetalle: "<button class='btn btn-xs btn-primary ver-detalle' title='Ver Detalle' data-tooltip='tooltip'><i class='fa fa-eye'></i></button>",
		botonVerDetalleAfiliaciones: "<button class='btn btn-xs btn-success ver-detalle' title='Ver Detalle Afiliaci\u00F3n' data-tooltip='tooltip'><i class='fa fa-eye'></i></button>",
		botonVerDetalleRecargaDebito: "<button class='btn btn-xs btn-warning ver-detalle-rec-deb' title='Ver Detalle Recarga/Debito' data-tooltip='tooltip'><i class='fa fa-eye'></i></button>",
		botonVerComision: "<button class='btn btn-xs btn-success ver-comisiones' title='Ver Comisiones' data-tooltip='tooltip'><i class='fa fa-money'></i></button>",
		botonPermisos: "<button class='btn btn-xs btn-success permiso' title='Asignar permisos' data-tooltip='tooltip'><i class='fa fa-check-square-o'></i></button>",
		botonVerJSONWS: "<button class='btn btn-xs btn-primary ver-detalle' title='Ver Datos' data-tooltip='tooltip'><i class='fa fa-eye'></i></button>",
		botonManageTarjeta: "<button class='btn btn-xs btn-secondary ver-detalle' title='Administrar tarjeta' data-tooltip='tooltip'><i class='fa fa-cog'></i></button>",
		botonExtornoWS: "<button class='btn btn-xs btn-danger realizar-extorno' title='Extornar' data-tooltip='tooltip'><i class='fa fa-retweet'></i></button>",
		botonAutorizarWS: "<button class='btn btn-xs btn-success autorizar' title='Autorizar' data-tooltip='tooltip'><i class='fa fa-check'></i></button>",
		botonDenegarWS: "<button class='btn btn-xs btn-danger denegar' title='Denegar' data-tooltip='tooltip'><i class='fa fa-times'></i></button>",
		botonTestSFTP: "<button class='btn btn-xs btn-warning test-sftp' title='Probar conexi\u00F3n' data-tooltip='tooltip'><i class='fa fa-check'></i></button>",
		botonVerContenidoSFTP: "<button class='btn btn-xs btn-success ver-sftp' title='Ver contenido' data-tooltip='tooltip'><i class='fa fa-eye'></i></button>",
		tableBotonExportarXlsx: "<button id='exportarXlsx' class='btn btn-success pull-right input-sm' type='button'><i class='fa fa-download'></i> XLSX</button>",
		$labelPrimary: $("<label class='label label-primary label-size-12 '></label>"),
		$labelSuccess: $("<label class='label label-success label-size-12 '></label>"),
		$labelDanger: $("<label class='label label-danger label-size-12 '></label>"),
		registroExitoso: "Se registr\u00F3 correctamente",
		registroRepetido: "El registro ya existe",
		errorIntegracion: "Error en la Integracion",
		asignacionExitosa: "Se asignaron los recursos exitosamente",
		busquedaSinResultados: "No se han encontrado resultados para su b\u00FAsqueda. Pruebe diferentes opciones o filtros.",
		camposRequeridos: "Si no ingresa una Fecha Registro debe ingresar al menos uno de los siguientes campos: C\u00F3digo Seguimiento, N\u00FAmero Tarjeta o Nombre Completo.",
		camposRequeridosSwDmpLog: "Si no ingresa una Fecha Proceso debe ingresar al menos uno de los siguientes campos: C\u00F3digo Seguimiento, N\u00FAmero Tarjeta o Nombre Completo.",
		cambioContrasenia: "<strong>¡Cuidado!</strong> Cambie su contrase\u00F1a, sino sera bloqueado",
		codigoUsuario: "<strong>¡Cuidado!</strong> Ingrese un Usuario",
		generarContrasenia: "<strong>¡Click!</strong> en Generar",
		actualizacionExitosa: "Se actualiz\u00F3 correctamente",
		recargaExitosa: "Empez\u00F3 el proceso de recarga para este lote",
		activacionExitosa: "Empez\u00F3 el proceso de activaci\u00F3n para este lote",
		recargaFallida: "Ocurrio un problema en el proceso de recarga para este lote",
		activacionFallida: "Ocurrio un problema en el proceso de activaci\u00F3n para este lote",
		accionEliminado: "eliminado(a)",
		accionActualizado: "actualizado(a)",
		filtrable: "filtrable",
		noFiltrable: "noFiltrable",
		seleccionable: "seleccionable",
		idFilaFiltro: "filaFiltro",
		claseEncabezadoFiltros: "encabezadoFiltro",
		claseDataNoDefinida: "data-no-definida",
		claseDataVacia: "data-vacia",
		claseInsertableOpcionesHtml: "insertable-opciones-html",
		tipo_presentacion_dia: 1,
		tipo_presentacion_mes: 2,
		tipoCompensacionComision: "COMISION",
		tipoCompensacionFondo: "FONDO",
		arregloSiNo: ["0", "1"],
		camposVacios: "Debe escoger alg\u00FAn Filtro para poder realizar la B\u00FAsqueda",
		comisionTarjetahabiente: "COMISI\u00D3N TARJETAHABIENTE",
		comisionEstablecimiento: "COMISI\u00D3N ESTABLECIMIENTO",
		comisionReceptor: "COMISI\u00D3N RECEPTOR",
		comisionOperador: "COMISI\u00D3N OPERADOR",
		comisionIsa: "COMISI\u00D3N ISA",
		comisionOif: "COMISI\u00D3N OIF",
		comisionIng: "COMISI\u00D3N OIF-ISA",
		comisionCobroInteres: "COMISI\u00D3N COBRO INTER\u00C9S",
		comisionTipoC: "COMISI\u00D3N TIPO C",
		comisionIntercambio: "COMISI\u00D3N INTERCAMBIO",
		comisionDis: "DEVOLUCION ISA",
		comisionGastos: "GASTOS",
		comisionSurcharge: "COMISI\u00D3N SURCHARGE",
		comisionOperadorRec: "COMISION OPERADOR RECEPTOR",
		comisionSur: "COMISION SURCHARGE",
		comisionSurRec: "COMISION SURCHARGE RECEPTOR",
		SumaSur: "SUMA SURCHARGE",
		SumaSurRec: "SUMA SURCHARGE RECEPTOR",
		comisionOperadorRec2: "COMISION OPERADOR RECEPTOR 2"
	};

	$funcionUtil = {
		ValidarIPaddress: function(ip) {
			var ipformat = /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
			return (ip.match(ipformat)) ? true : false;
		},
		construirNotEqual: function(name, argumentos) {
			var clause = '';
			for (var i = 0; i < argumentos.length; i++) {
				clause = clause + '[' + name + '!=\'' + argumentos[i] + '\']';
			}
			return clause;
		},
		construirEqual: function(name, argumentos) {
			var clause = '';
			for (var i = 0; i < argumentos.length; i++) {
				clause = clause + '[' + name + '=\'' + argumentos[i] + '\'],';
			}
			if (clause != '') {
				return clause.slice(0, -1);
			}
			return clause;
		},
		addZeros: function(num) {
			if (num.length === 1) {
				return "0" + num;
			} else {
				return num;
			}
		},
		obtenerFechaProceso: function() {
			$.ajax({
				type: "GET",
				url: $variableUtil.root + "parametroGeneral/fechaProceso?accion=buscar",
				beforeSend: function() {
					$localUtil.$navegacionFechaProceso.text("Fecha de Proceso: Cargando");
				},
				statusCode: {
					409: function(response) {
						$funcionUtil.notificarException(response.responseText, "fa-warning", "Aviso", "warning");
						$localUtil.$navegacionFechaProceso.text("Fecha de Proceso: Error Cargando");
					}
				},
				success: function(fechaProceso) {
					$localUtil.$navegacionFechaProceso.text("Fecha de Proceso: " + fechaProceso);
				},
				error: function() {
					$localUtil.$navegacionFechaProceso.text("Fecha de Proceso: No encontrado");
				}
			});
		},
		obtenerProcesaObservadasManual: function() {
			return $.ajax({
				type: "GET",
				url: $variableUtil.root + "parametroGeneral/procesaObservadasManual?accion=buscar",
				statusCode: {
					409: function(response) {
						$funcionUtil.notificarException(response.responseText, "fa-warning", "Aviso", "warning");
					}
				},
				success: function(procesaObservadasManual) {
					$funcionUtil.notificarException("Proceso Manual de Observadas: " + $funcionUtil.insertarEtiquetaSiNo(procesaObservadasManual), "fa-success", "Aviso", "success");

				},
				error: function() {
					$funcionUtil.notificarException(response.responseText, "fa-warning", "Aviso", "warning");

				}
			});
		}

		,

		obtenerMensajeError: function(mensajeInicial, mensajeError, accion) {
			var mensaje = mensajeInicial || "";
			switch (mensajeError.codigo_error) {
				case 547:
					return mensaje += " no puede ser " + accion + ", porque est&aacute; siendo usado(a) actualmente.";
				default:
					return "Ocurri\u00F3 un problema desconocido.";
			}
		},
		notificarException: function(mensaje, icon, titulo, tipo) {
			$.notify({
				icon: "fa " + icon,
				title: " <strong>" + titulo + ": </strong>",
				message: "<p>" + mensaje + "</p>"
			}, {
				delay: 6000,
				type: tipo
			})
		},
		insertarEtiquetaSiNo: function(valor_booleano) {
			switch (true) {
				case (valor_booleano == "0" || valor_booleano == "NO" || valor_booleano == 0):
					return $variableUtil.$labelDanger.text("NO").get(0).outerHTML;
				case (valor_booleano == "1" || valor_booleano == "SI" || valor_booleano == 1):
					return $variableUtil.$labelSuccess.text("SI").get(0).outerHTML;
				default:
					return "-";
			}
		},
		insertarEtiquetaNoSi: function(valor_booleano) {
			switch (true) {
				case (valor_booleano == "0" || valor_booleano == "NO" || valor_booleano == 0):
					return $variableUtil.$labelSuccess.text("NO").get(0).outerHTML;
				case (valor_booleano == "1" || valor_booleano == "SI" || valor_booleano == 1):
					return $variableUtil.$labelDanger.text("SI").get(0).outerHTML;
				default:
					return "-";
			}
		},
		textoSiNo: function(valor_booleano) {
			switch (true) {
				case (valor_booleano == "0" || valor_booleano == "NO" || valor_booleano == 0):
					return "NO";
				case (valor_booleano == "1" || valor_booleano == "SI" || valor_booleano == 1):
					return "SI";
				default:
					return "-";
			}
		},
		insertarBoolNumerico: function(cuenta_compensacion) {
			switch (cuenta_compensacion) {
				case 0:
					return $variableUtil.$labelPrimary.text("NO").get(0).outerHTML;
				case 1:
					return $variableUtil.$labelPrimary.text("SI").get(0).outerHTML;
				default:
					return $variableUtil.$labelPrimary.text("No Especificado").get(0).outerHTML;
			}
		},
		limpiarMensajesDeError: function(formulario) {
			formulario.find(".form-group, .group").removeClass("has-error").find(".help-block").remove();
		},
		mostrarMensajeDeError: function(mensajesDeError, formulario) {
			var notificacionMensajeError = [];
			$.each(mensajesDeError, function(i, mensaje) {
				var idSpanError = mensaje.campoError + "-error";
				var input = formulario.find("input[name=" + mensaje.campoError + "], select[name=" + mensaje.campoError + "], textarea[name=" + mensaje.campoError + "]").first();
				if (input.length > 0) {
					if (input.parent(".input-group").length > 0 || input.parent(".radio-inline").length > 0) {
						input.attr({
							"aria-required": true,
							"aria-describedby": idSpanError,
							"aria-invalid": true
						}).parent().parent().append("<span id='" + idSpanError + "' class='help-block'>" + mensaje.mensajeError + "</span>").parents(".group, .form-group").first().addClass("has-error");
					} else {
						input.attr({
							"aria-required": true,
							"aria-describedby": idSpanError,
							"aria-invalid": true
						}).parent().append("<span id='" + idSpanError + "' class='help-block'>" + mensaje.mensajeError + "</span>").parents(".group, .form-group").first().addClass("has-error");
					}
				} else {
					notificacionMensajeError.push(mensaje.mensajeError);
				}
			});
			if (notificacionMensajeError.length > 0) {
				this.notificarException(notificacionMensajeError.join(".<br/>"), "fa-warning", "Aviso", "warning");
			}
		},
		validarHexadecimal: function(inputString) {
			var re = /[0-9A-Fa-f]/g;
			if (re.test(inputString)) {
				return true;
			} else {
				return false;
			}
		},
		validarNull: function(str) {
			if (str == undefined || str == null || str == '') {
				return '';
			}
			return str;
		},
		llenarFormulario: function(dto, formulario) {
			$.each(dto, function(i, value) {
				var input = formulario.find("input[name=" + i + "], select[name=" + i + "]");
				if (input.is(":checkbox")) {
					input.prop("checked", (value == "1" || value == "true"));
				} else {
					input.val(value);
				}
				if (input.hasClass("select2")) {
					input.val(value).trigger("change.select2");
				}
			});
		},
		llenarFormularioDeLectura: function(dto, formulario) {
			$.each(dto, function(i, value) {
				var input = formulario.find("input[name=" + i + "], select[name=" + i + "]");
				if (input.is(":checkbox")) {
					input.prop("checked", (value == "1" || value == "true"));
				} else {
					input.val(value);
					input.attr({
						"title": value,
						"data-original-title": value
					});
				}
				if (input.hasClass("select2")) {
					input.val(value).trigger("change.select2");
				}
			});
		},
		limpiarCamposFormulario: function(formulario) {
			formulario.trigger("reset");
			formulario.find(".select2").val("").trigger("change.select2");
		},
		prepararFormularioRegistro: function(formulario) {
			formulario.find(".elemento-desactivable").prop("disabled", false);
			this.limpiarCamposFormulario(formulario);
			this.limpiarMensajesDeError(formulario);
			formulario.validate().resetForm();
		},
		prepararFormularioActualizacion: function(formulario) {
			formulario.find(".elemento-desactivable").prop("disabled", true);
			this.limpiarMensajesDeError(formulario);
			formulario.validate().resetForm();
		},
		obtenerMensajeErrorEnCadena: function(mensajesDeError) {
			var mensajeCadena = "";
			$.each(mensajesDeError, function(i, mensaje) {
				mensajeCadena += mensaje.mensajeError + "<br/>";
			});
			return mensajeCadena;
		},
		filaFiltro: function() {
			return "<tr id='" + $variableUtil.idFilaFiltro + "'></tr>"
		},
		encabezadoFiltro: function() {
			return "<th class='" + $variableUtil.claseEncabezadoFiltros + "'></th>"
		},
		unique: function(array) {
			return $.grep(array, function(el, index) {
				return index === $.inArray(el, array);
			});
		},
		crearSelect2: function(select, textoPorDefecto) {
			var propiedad = {
				placeholder: textoPorDefecto,
				language: {
					noResults: function() {
						return "No se encontr\u00F3 resultados";
					}
				},
				"width": "100%",
				"theme": "bootstrap",
				"dropdownAutoWidth": true,
				"dropdownParent": select.parent()
			};
			if (textoPorDefecto != undefined && textoPorDefecto != null) {
				propiedad.placeholder = textoPorDefecto;
			}
			if (select.hasClass("encabezado")) {
				propiedad.containerCssClass = ":all:";
			}
			select.select2(propiedad);
		},
		crearCadenaPeticionGetDeSelect2Multiple: function($select, cadenaAtributo) {
			var url = "";
			if ($select.val() === "" || $select.val() === null || $select.length === 0 || $select.val() === -1) {
				return url;
			} else {
				var arr = $select.val();
				for (var i = 0; i < arr.length; i++) {
					url = url + "&" + cadenaAtributo + "=" + arr[i];
				}
				return url;
			}

		},
		crearSelect2Multiple: function(select, valorDefecto, textoPorDefecto) {
			var propiedad = {
				placeholder: textoPorDefecto,
				language: {
					noResults: function() {
						return "No se encontr\u00f3 resultados";
					}
				},
				"width": "100%",
				"theme": "bootstrap",
				"dropdownAutoWidth": true,
				"multiple": true,
				"dropdownParent": select.parent()
			};
			if (textoPorDefecto !== undefined && textoPorDefecto !== null) {
				propiedad.placeholder = textoPorDefecto;
			}
			if (select.hasClass("encabezado")) {
				propiedad.containerCssClass = ":all:";
			}
			select.select2(propiedad);
			if (valorDefecto !== undefined && valorDefecto !== null) {
				select.on("select2:selecting", function(e) {
					var idPorSeleccionar = e.params.args.data.id;
					if (idPorSeleccionar == -1) {
						select.val(null).trigger("change");
					} else {
						var indiceValorDefecto = $.inArray(valorDefecto.toString(), select.val());
						if (indiceValorDefecto >= 0) {
							var valores = select.val();
							select.val(valores.splice(indiceValorDefecto, valores)).trigger("change");
						}
					}
				}).on("select2:unselect", function(e) {
					var valores = select.val();
					if (valores === undefined || valores === null) {
						select.val([valorDefecto.toString()]).trigger("change");
					}
				});
			}
		},
		crearMultipleSelect2: function(select, textoPorDefecto) {
			var propiedad = {
				placeholder: textoPorDefecto,
				language: {
					noResults: function() {
						return "No se encontr\u00f3 resultados";
					}
				},
				"width": "100%",
				"theme": "bootstrap",
				"dropdownAutoWidth": true,
				"dropdownParent": select.parent()
			};
			if (textoPorDefecto != undefined && textoPorDefecto != null) {
				propiedad.placeholder = textoPorDefecto;
			}
			if (select.hasClass("encabezado")) {
				propiedad.containerCssClass = ":all:";
			}
			select.select2(propiedad);
		},
		obtenerArraySeparadoPorComasParaPeticionGet: function(values, key) {
			if (values == null) {
				return '';
			}
			if (values.lenght == 0) {
				return '';
			}
			return "&" + key + "=" + values.join(",");
		},
		obtenerModoPeriodo: function(value) {
			let text = "";
			switch (value) {
				case '1':
					text = 'DIARIO';
					break;
				case '2':
					text = 'MENSUAL';
					break;
			}
		},
		unirCodigoDescripcion: function(codigo, descripcion) {
			if (codigo == null || codigo == undefined) {
				codigo = "";
			}
			if (descripcion == null || descripcion == undefined) {
				descripcion = "";
			}
			return codigo + " - " + descripcion;
		},
		unirCodigoDescripcionExtra: function(codigo, descripcion, extra) {
			if (codigo == null || codigo == undefined) {
				codigo = "";
			}
			if (descripcion == null || descripcion == undefined) {
				descripcion = "";
			}
			if (extra == null || extra == undefined) {
				extra = "";
			}
			return codigo + " - " + descripcion + " - " + extra;
		},
		crearDatePickerSimple: function(input, format) {
			format = format || "DD/MM/YYYY";
			input.daterangepicker({
				"singleDatePicker": true,
				"showDropdowns": true,
				"locale": {
					direction: 'ltr',
					format: format,
					separator: ' - ',
					customRangeLabel: 'Personalizado',
					daysOfWeek: ['Dom', 'Lun', 'Mar', 'Mie', 'Jue', 'Vie', 'Sab'],
					monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Setiembre', 'Octubre', 'Noviembre', 'Diciembre'],
					firstDay: 1
				},
				"maxDate": moment()
			}, function(start, end, label) {
			});
		},
		crearDateRangePickerSimple: function(input, abrirHacia) {
			abrirHacia = abrirHacia || "left";
			input.daterangepicker({
				"alwaysShowCalendars": true,
				"ranges": {
					'Hoy': [moment(), moment()],
					'Ayer': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
					'\u00daltimos 07 d\u00edas': [moment().subtract(6, 'days'), moment()],
					'\u00daltimos 30 d\u00edas': [moment().subtract(29, 'days'), moment()],
					'Este mes': [moment().startOf('month'), moment().endOf('month')],
					'\u00daltimo mes': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
				},
				"startDate": moment(),
				"endDate": moment(),
				"opens": abrirHacia,
				"locale": {
					direction: 'ltr',
					format: 'DD/MM/YYYY',
					separator: ' - ',
					applyLabel: 'Aplicar',
					cancelLabel: 'Cancelar',
					fromLabel: 'Desde',
					toLabel: 'A',
					customRangeLabel: 'Personalizado',
					daysOfWeek: ['Dom', 'Lun', 'Mar', 'Mie', 'Jue', 'Vie', 'Sab'],
					monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Setiembre', 'Octubre', 'Noviembre', 'Diciembre'],
					firstDay: 1
				},
				"maxDate": moment()
			}, function(start, end, label) {
			});
		},
		animacionAjax: function($button) {
			var $this = this;
			$(document).ajaxStart(function() {
				$this.animacionBusqueda($button);
			}).ajaxStop(function() {
				$this.animacionBusqueda($button);
			});
		},
		validarFormatoFecha: function(fecha) {
			var RegExPattern = /^\d{1,2}\/\d{1,2}\/\d{2,4}$/;
			if ((campo.match(RegExPattern)) && (campo != '')) {
				return true;
			} else {
				return false;
			}
		},
		existeFecha: function(fecha) {
			var fechaf = fecha.split("/");
			var day = fechaf[0];
			var month = fechaf[1];
			var year = fechaf[2];
			var date = new Date(year, month, '0');
			if ((day - 0) > (date.getDate() - 0)) {
				return false;
			}
			return true;
		},
		obtenerMesesEnTexto: function(numMes, numAnio) {
			let meses = ['0', 'Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Setiembre', 'Octubre', 'Noviembre', 'Diciembre'];
			let mesInicioEnTexto = meses[numMes];
			let textoRango = mesInicioEnTexto + ' del ' + numAnio;
			return textoRango;
		},
		animacionBusqueda: function($boton, action, callback) {
			if (!$boton.hasClass("refreshing")) {
				$boton.addClass("refreshing");
				$boton.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw")
				if (action && action === "shown" && typeof callback === "function")
					callback();
			} else {
				$boton.removeClass("refreshing");
				$boton.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
				if (action && action === "hidden" && typeof callback === "function")
					callback();
			}
		},
		crearCadenaPeticionGetDeSelect2MultiplePaginacion: function($select) {
			let url = "";
			if ($select.val() === "" || $select.val() === null || $select.val() === undefined || $select.length === 0 || $select.val() === -1 || $select.val() === "-1") {
				return url;
			} else {
				let arr = $select.val();
				for (let i = 0; i < arr.length; i++) {
					url = url + "," + arr[i];
				}

				return url;
			}
		},
		limpiarCadenaPeticionGetDeSelect2MultiplePaginacion: function($cadena) {
			let cadenaResult = "-1";
			if ($cadena === "" || $cadena === null || $cadena === undefined || $cadena === 'undefined' || $cadena.length === 0 || $cadena === -1 || $cadena === "-1") {
				return cadenaResult;
			} else {
				var arreglo = $cadena.substring(1).split(",");
				cadenaResult = "'" + arreglo.filter((valor, indice) => { return arreglo.indexOf(valor) === indice; }).sort().join("','") + "'";
				if (cadenaResult === null || cadenaResult === undefined || cadenaResult === 'undefined' || cadenaResult === "-1") return cadenaResult = "-1";
				return cadenaResult;
			}
		},
		limpiarCadenaPeticionGetDeSelect2MultiplePaginacionSinComillas: function($cadena) {
			let cadenaResult = "-1";
			if ($cadena === "" || $cadena === null || $cadena === undefined || $cadena === 'undefined' || $cadena.length === 0 || $cadena === -1 || $cadena === "-1") {
				return cadenaResult;
			} else {
				var arreglo = $cadena.split(",");
				cadenaResult = arreglo.filter((valor, indice) => { return arreglo.indexOf(valor) === indice; }).sort().join("','");
				if (cadenaResult === null || cadenaResult === undefined || cadenaResult === 'undefined' || cadenaResult === "-1") return cadenaResult = "-1";
				return cadenaResult;
			}
		},
		crearDateRangePickerConsulta: function(input, abrirHacia) {
			abrirHacia = abrirHacia || "left";
			input.daterangepicker({
				"alwaysShowCalendars": true,
				"ranges": {
					'Hoy': [moment(), moment()],
					'Ayer': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
					'\u00daltimos 07 d\u00edas': [moment().subtract(6, 'days'), moment()],
					'\u00daltimos 30 d\u00edas': [moment().subtract(29, 'days'), moment()],
					'Este mes': [moment().startOf('month'), moment().endOf('month')],
					'\u00daltimo mes': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
				},
				"startDate": moment(),
				"endDate": moment(),
				"opens": abrirHacia,
				"locale": {
					direction: 'ltr',
					format: 'DD/MM/YYYY',
					separator: ' - ',
					applyLabel: 'Aplicar',
					cancelLabel: 'Limpiar',
					fromLabel: 'Desde',
					toLabel: 'A',
					customRangeLabel: 'Personalizado',
					daysOfWeek: ['Dom', 'Lun', 'Mar', 'Mie', 'Jue', 'Vie', 'Sab'],
					monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Setiembre', 'Octubre', 'Noviembre', 'Diciembre'],
					firstDay: 1
				},
				"maxDate": moment()
			}, function(start, end, label) {
			});
			input.on('apply.daterangepicker', function(ev, picker) {
				$(this).val(picker.startDate.format('DD/MM/YYYY') + ' - ' + picker.endDate.format('DD/MM/YYYY'));
			});
			input.on('cancel.daterangepicker', function(ev, picker) {
				$(this).val('');
			});
		},
		crearDateRangePickerSinValorPorDefecto: function(input, abrirHacia) {
			abrirHacia = abrirHacia || "left";
			input.daterangepicker({
				"alwaysShowCalendars": true,
				"ranges": {
					'Hoy': [moment(), moment()],
					'Ayer': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
					'\u00DAltimos 07 d\u00EDas': [moment().subtract(6, 'days'), moment()],
					'\u00DAltimos 30 d\u00EDas': [moment().subtract(29, 'days'), moment()],
					'Este mes': [moment().startOf('month'), moment().endOf('month')],
					'\u00DAltimo mes': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
				},
				"autoUpdateInput": false,
				"opens": abrirHacia,
				"locale": {
					direction: 'ltr',
					format: 'DD/MM/YYYY',
					separator: ' - ',
					applyLabel: 'Aplicar',
					cancelLabel: 'Limpiar',
					fromLabel: 'Desde',
					toLabel: 'A',
					customRangeLabel: 'Personalizado',
					daysOfWeek: ['Dom', 'Lun', 'Mar', 'Mie', 'Jue', 'Vie', 'Sab'],
					monthNames: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Setiembre', 'Octubre', 'Noviembre', 'Diciembre'],
					firstDay: 1
				},
				"maxDate": moment()
			}, function(start, end, label) {
				start = null;
			});
			input.on('apply.daterangepicker', function(ev, picker) {
				$(this).val(picker.startDate.format('DD/MM/YYYY') + ' - ' + picker.endDate.format('DD/MM/YYYY'));
			});
			input.on('cancel.daterangepicker', function(ev, picker) {
				$(this).val('');
			});
		},
		obtenerFechasDateRangePicker: function(input) {
			var rangoFecha = {};
			if (input.val() == undefined || input.val() == null || input.val() == "") {
				rangoFecha.fechaInicio = "";
				rangoFecha.fechaFin = "";
				return rangoFecha;
			}
			rangoFecha.fechaInicio = input.data("daterangepicker").startDate.format('YYYY-MM-DD');
			rangoFecha.fechaFin = input.data("daterangepicker").endDate.format('YYYY-MM-DD');
			return rangoFecha;
		},
		obtenerFechasDateRangePickerFormatDDMMYYY: function(input) {
			var rangoFecha = {};
			if (input.val() == undefined || input.val() == null || input.val() == "") {
				rangoFecha.fechaInicio = "";
				rangoFecha.fechaFin = "";
				return rangoFecha;
			}
			rangoFecha.fechaInicio = input.data("daterangepicker").startDate.format('DD/MM/YYYY');
			rangoFecha.fechaFin = input.data("daterangepicker").endDate.format('DD/MM/YYYY');
			return rangoFecha;
		},
		obtenerFechaDatePicker: function(input) {
			return input.val() == undefined || input.val() == null || input.val() == "" ? "" : input.data("daterangepicker").startDate.format('YYYY-MM-DD');
		},
		obtenerTextoSelecMultiple: function(select, separador) {
			var texto = "";
			var array = [];
			$.each(select.find('option:selected'), function() {
				array.push($(this).text())
			});
			array.reverse().forEach(element => texto = element + separador + texto);
			texto = texto.slice(0, -1);
			return texto;
		},
		convertirDeFormatoAFormato: function(fecha, formatoOrigen, formatoFinal) {
			return moment(fecha, formatoOrigen).format(formatoFinal);
		},
		determinarMoneda: function(codidoMoneda) {
			if (codidoMoneda == undefined) {
				return "";
			}
			if (codidoMoneda == 604 || codidoMoneda == '604') {
				return 'SOLES';
			} else if (codidoMoneda == 840 || codidoMoneda == '840') {
				return 'D\u00D3LARES';
			}
			return '';
		},
		formatMonedaFromConsultaSaldo(monto) {
			if (monto == undefined) {
				return "";
			}
			let montoFomart = parseInt(monto);
			if (montoFomart == 0) {
				return montoFomart;
			}
			let strMontoFormat = montoFomart.toString();
			let strDecimales = strMontoFormat.slice(-2);
			let strMonto = strMontoFormat.substring(0, strMontoFormat.length - 2);
			let montoFormateado = strMonto + "." + strDecimales;
			let number = parseFloat(montoFormateado);
			return number;
		},
		convertirFechaConsultaMovimientos(fechaYYYYMMDD) {
			if (fechaYYYYMMDD == undefined) {
				return "";
			}
			let fecha = $funcionUtil.convertirDeFormatoAFormato(fechaYYYYMMDD, 'YYYYMMDD', 'DD/MM/YYYY');
			return fecha;
		},
		convertirMontoATexto: function(monto) {
			if (monto == null) {
				return "";
			}
			let strMonto = monto.toString();
			let idxPunto = strMonto.indexOf(".");
			if (idxPunto == -1) {
				return strMonto + "00";
			}
			let totalSize = strMonto.length;
			let parteEntera = strMonto.substring(0, idxPunto);
			let parteDecimal = strMonto.substring(idxPunto + 1, strMonto.length);
			if (parteDecimal.length == 1) {
				parteDecimal = parteDecimal + "0";
			}
			return parteEntera + parteDecimal;
		},
		pathExtorno: function(operacion) {
			switch (operacion) {
				case 'RECARGA':
					return 'extorno-recarga';
				case 'DEBITO CASHOUT':
					return 'extorno-debito';
				case 'TRANSFERENCIA':
					return 'extorno-transferencia';
			}
		},
		obtenerCriteriosExtorno: function(data) {
			let criterio = {}
			switch (data.operacion) {
				case 'RECARGA':
					criterio.ciudad = 'LIMA';
					criterio.codigoSeguimiento = data.codigoSeguimiento;
					criterio.monedaComision = data.monedaComisionExtra;
					criterio.montoComision = $funcionUtil.convertirMontoATexto(data.montoComisionExtra);
					criterio.direccion = data.dirComercio;
					criterio.comercio = data.idComercio;
					criterio.terminal = data.idTerminal;
					criterio.monedaRecarga = data.monedaRecarga;
					criterio.montoRecarga = $funcionUtil.convertirMontoATexto(data.montoRecarga);
					criterio.montoBD = data.montoRecarga;
					criterio.tLocalOriginal = data.localTime;
					criterio.secuenciaOriginal = data.numeroTrace;
					break;
				case 'DEBITO CASHOUT':
					criterio.ciudad = 'LIMA';
					criterio.codigoSeguimiento = data.codigoSeguimiento;
					criterio.monedaComision = data.monedaComisionExtra;
					criterio.montoComision = $funcionUtil.convertirMontoATexto(data.montoComisionExtra);
					criterio.direccion = data.dirComercio;
					criterio.comercio = data.idComercio;
					criterio.terminal = data.idTerminal;
					criterio.monedaDebito = data.monedaRecarga;
					criterio.montoDebito = $funcionUtil.convertirMontoATexto(data.montoRecarga);
					criterio.montoBD = data.montoRecarga;
					criterio.tLocalOriginal = data.localTime;
					criterio.secuenciaOriginal = data.numeroTrace;
				case 'TRANSFERENCIA':
					criterio.ciudad = 'LIMA';
					criterio.codigoSeguimiento = data.codigoSeguimiento;
					criterio.codigoSeguimientoDestino = data.codigoSeguimientoDestino;
					criterio.monedaComision = data.monedaComisionExtra;
					criterio.montoComision = $funcionUtil.convertirMontoATexto(data.montoComisionExtra);
					criterio.direccion = data.dirComercio;
					criterio.comercio = data.idComercio;
					criterio.terminal = data.idTerminal;
					criterio.monedaTransferencia = data.monedaRecarga;
					criterio.montoTransferencia = $funcionUtil.convertirMontoATexto(data.montoRecarga);
					criterio.montoBD = data.montoRecarga;
					criterio.tLocalOriginal = data.localTime;
					criterio.secuenciaOriginal = data.numeroTrace;
			}
			criterio.transaccion = data.idTransaccion;
			return criterio;
		},
		obtenerMonedaWS: function(str) {
			let sub = str.substring(0, 3);
			if (sub == " - ") {
				return "";
			}
			return sub;
		},
		convertirHoraConsultaMovimientos(hora) {
			if (hora == undefined) {
				return "";
			}
			if (hora.length != 6) {
				return hora
			}
			let hh = hora.substring(0, 2);
			let mm = hora.substring(2, 4);
			let ss = hora.substring(4, 7);
			return hh + ":" + mm + ":" + ss;
		},
		aplicarTrim: function(str) {
			if (str == null || str == undefined || str == "") {
				return "";
			}
			return str.trim();
		},
		camposVacios: function(formulario) {
			var camposVacios = true;
			formulario.find(".filtro").each(function() {
				var campo = $(this);
				var valorCampo = "";
				if (campo.is(":radio") && !campo.is(":checked")) {
					valorCampo = "";
				} else {
					valorCampo = campo.val();
				}
				if (valorCampo != undefined && valorCampo != "" && valorCampo != "-1") {
					camposVacios = false;
					return camposVacios;
				}
			});
			return camposVacios;
		},
		camposVacios2: function(formulario, cantidadMinimaRequerida) {
			var camposVacios = true;
			var i = 0;
			formulario.find(".filtro").each(function() {
				var campo = $(this);
				var valorCampo = "";
				if (campo.is(":radio") && !campo.is(":checked")) {
					valorCampo = "";
				} else {
					valorCampo = campo.val();
				}
				if (valorCampo != undefined && valorCampo != "" && valorCampo != "-1") {
					if (!cantidadMinimaRequerida) {
						camposVacios = false;
						return camposVacios;
					} else {
						i++;
						if (i === cantidadMinimaRequerida) {
							camposVacios = false;
							return camposVacios;
						}
					}
				}

			});
			return camposVacios;
		},
		descripcionLarga: function(txt) {
			$('[data-tooltip="tooltip"]').tooltip({
				placement: 'top',
				title: txt
			});
		},
		cargarTooltips: function(detalleCompensacion) {
			$.each(detalleCompensacion, function(key, value) {
				$(`input[name="${key}"]`).tooltip({
					placement: 'top',
					title: value
				});
			});
		},
		obtenerMesValorMonth: function(valor) {
			let mes = null;
			if (valor != null) {
				mes = valor.charAt(5) + valor.charAt(6);
				if (mes.charAt(0) == '0') {
					mes = mes.charAt(1);
				}
			}
			return mes;
		},
		obtenerAnioValorMonth: function(valor) {
			let anio = null;
			if (valor != null) {
				anio = valor.charAt(0) + valor.charAt(1) + valor.charAt(2) + valor.charAt(3);
			}
			return anio;
		},
		obtenerSemanaInputWeek: function(input) {
			let valor = input.val();
			let semana = null;
			if (valor != null) {
				semana = valor.charAt(6) + valor.charAt(7);
				if (semana.charAt(0) == '0') {
					semana = semana.charAt(1);
				}
			}
			return semana;
		},
		obtenerAnioInputWeek: function(input) {
			let valor = input.val();
			let anio = null;
			if (valor != null) {
				anio = valor.charAt(0) + valor.charAt(1) + valor.charAt(2) + valor.charAt(3);
			}
			return anio;
		},
		obtenerMesInputMonth: function(input) {
			let valor = input.val();
			let mes = null;
			if (valor != null) {
				mes = valor.charAt(5) + valor.charAt(6);
				if (mes.charAt(0) == '0') {
					mes = mes.charAt(1);
				}
			}
			return mes;
		},
		obtenerAnioInputMonth: function(input) {
			let valor = input.val();
			let anio = null;
			if (valor != null) {
				anio = valor.charAt(0) + valor.charAt(1) + valor.charAt(2) + valor.charAt(3);
			}
			return anio;
		},
		activarCheckbox: function(input, activar) {
			if (activar == true) {
				input.removeAttr('disabled');
			} else {
				input.attr('disabled', 'disabled');
			}
		},
		existeArchivoEnListaArchivos: function(fileName, arrFiles, key) {
			if (arrFiles.length == 0) {
				return false;
			}
			let existe = false;
			arrFiles.forEach(function(item) {
				if (item[key] == fileName) {
					existe = true;
				}
			});
			return existe;
		},
		eliminarArchivoEnLista: function(fileName, arrFiles, id) {
			let pos = -1;
			arrFiles.forEach(function(item, i) {
				if (item[id] == fileName) {
					pos = i;
				}
			});
			if (pos == -1) {
				return arrFiles;
			} else {
				return arrFiles.splice(pos, 1);
			}
		},
		obtenerValoresSelectMultiple: function($select, paramCriterioBusqueda, name) {
			var array = new Array();
			array = $select.val();
			if (array != null) {
				for (i = 0; i < array.length; i++) {
					paramCriterioBusqueda = paramCriterioBusqueda + "&" + name + "=" + array[i];
				}
			} else {
				paramCriterioBusqueda = paramCriterioBusqueda + "&" + name + "=";
			}
			return paramCriterioBusqueda;
		},
		filtrarObjectArray: function(array, key, value) {
			return array.filter(function(element) {
				return element[key] == value;
			})
		},
		validarCantidadMaxCaracteres: function(texto, len) {
			if (texto.length <= len) {
				return true;
			} else {
				return false;
			}
		},
		validarValorEnArreglo: function(texto, arr) {
			if (arr.indexOf(texto) != -1) {
				return true;
			} else {
				return false;
			}
		},
		validarFormatoNumerico: function(texto) {
			if (/^[0-9]+$/i.test(texto)) {
				return true;
			} else {
				return false;
			}
		},
		validarFormatoMMAA: function(texto) {
			if (texto == '') {
				return true;
			}
			if (/^[0-9][0-9][\/][0-9][0-9]$/i.test(texto)) {
				return true;
			} else {
				return false;
			}
		},
		validarValorNulo: function(texto) {
			if (texto == null || texto == undefined || texto == "") {
				return true;
			} else {
				return false;
			}
		},
		validarFormatoMoneda: function(texto) {
			if (/[1-9]\d*(?:\.\d{0,2})?/i.test(texto)) {
				return true;
			} else {
				return false;
			}
		},
		validarFormatoEmail: function(texto) {
			if (/^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i.test(texto)) {
				return true;
			} else {
				return false;
			}
		},
		validarFechaDDMMYYYY: function(texto) {
			if (texto == null || texto == '') {
				return true;
			}
			return moment(texto, 'DD/MM/YYYY', true).isValid();
		},
		devolverCadenaVaciaPorNull: function(texto) {
			if (texto == null || texto == undefined) {
				return "";
			} else {
				return texto;
			}
		},
		validarLote: function(data) {
			let valido = true;
			data.forEach(function(item) {
				if (item.exitoRegistro == 0) {
					valido = false;
				}
			});
			return valido;
		},
		obtenerLote: function(data, key, valor) {
			let arr = [];
			data.forEach(function(item) {
				if (item[key] == valor) {
					arr.push(item);
				}
			});
			return arr;
		},
		convertirFormatoMoneda: function(valor, decimal) {
			if (isNaN(parseFloat(valor))) {
				return "";
			} else {
				return parseFloat(valor).toFixed(decimal);
			}
		},
		formatMoney: function(amount, decimalCount, decimal, thousands) {
			decimalCount = (decimalCount == undefined || decimalCount == null) ? 2 : decimalCount;
			decimal = (decimal == undefined || decimal == null) ? '.' : decimal;
			thousands = (thousands == undefined || thousands == null) ? ',' : thousands;
			if (amount == 0 || amount == undefined || amount == null) {
				let cero = '0.' + '0'.repeat(decimalCount)
				return cero;
			}
			try {
				decimalCount = Math.abs(decimalCount);
				decimalCount = isNaN(decimalCount) ? 2 : decimalCount;
				const negativeSign = amount < 0 ? "-" : "";
				let i = parseInt(amount = Math.abs(Number(amount) || 0).toFixed(decimalCount)).toString();
				let ii = parseInt(i);
				let j = (i.length > 3) ? i.length % 3 : 0;
				return negativeSign + (j ? i.substr(0, j) + thousands : '') + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + thousands) + (decimalCount ? decimal + Math.abs(amount - ii).toFixed(decimalCount).slice(2) : "");
			} catch (e) {
				console.error(e);
			}
		},
		formatMoneyToNumber: function(amount) {
			let cantidad = amount.toString();
			cantidad = cantidad.replace(',', '')
			return parseFloat(cantidad)
		},
		webSocketMethod: function(ruta) {
			var miWs;

			if (window.location.protocol === "https:") {
				miWs = new WebSocket(`wss:${window.location.host + $variableUtil.root}${ruta}`);
			} else {
				miWs = new WebSocket(`ws:${window.location.host + $variableUtil.root}${ruta}`);
			}

			var mensaje = {
				porcentaje: 0.0,
				terminado: false,
				cancelado: false
			};

			var jc = $.confirm({
				title: 'Descargando...',
				draggable: false,
				content: '' +
					'<div class="text-center">' +
					'<label id="porcentaje" style="font-size: 40px; margin-top: 10px;">0 %</label>' +
					'</div>',
				buttons: {
					buttonOk: {
						btnClass: "btn-blue",
						action: function() {
							miWs.close();
						}
					},
					buttonCancelar: {
						btnClass: "btn-red",
						action: function() {
							mensaje.cancelado = true;
							miWs.send(JSON.stringify(mensaje));
							miWs.close();
							window.stop();
						}
					}
				},
			});

			jc.open();
			jc.buttons.buttonOk.hide();

			jc.buttons.buttonOk.setText("Cerrar");
			jc.buttons.buttonCancelar.setText("Cancelar");
			jc.setType("red");

			miWs.onmessage = function(e) {
				mensaje = JSON.parse(e.data);
				var element = document.getElementById("porcentaje");
				if (element != null) {
					element.innerText = ((mensaje.porcentaje === 100) ? parseInt(mensaje.porcentaje) : parseFloat(mensaje.porcentaje).toFixed(2)) + " %";
				}
				if (mensaje.terminado) {
					jc.buttons.buttonOk.show();
					jc.buttons.buttonCancelar.hide();
					jc.setTitle("Descarga completa!!!");
					jc.setType("blue");
					if (mensaje.porcentaje === 0.0) {
						jc.setTitle("Archivo vac\u00EDo");
						jc.setContent('' +
							'<div class="text-center">' +
							'<label id="porcentaje" style="font-size: 16px; margin-top: 10px;">0 registros encontrados.</label>' +
							'</div>');
					}
				}
			}
		}
	};

	$tablaFuncion = {
		aniadirBotonEnTabla: function(elemento, boton, lugar) {
			if (lugar == $variableUtil.posIzquierdo) {
				elemento.append(boton);
				elemento.find("label").addClass("margin-left-2");
			} else if (lugar == $variableUtil.posDerecho) {
				elemento.prepend(boton);
				elemento.find("button").addClass("margin-left-2");
			}
		},
		aniadirFiltroAlSeleccionable: function(select, filtro) {
			if (filtro == "0") {
				filtro = "NO";
			} else {
				if (filtro == "1") {
					filtro = "SI";
				}
			}
			select.append("<option value='" + filtro + "'>" + filtro + "</option>");
		},
		aniadirFiltroBusquedaCajaTexto: function(encabezadoTabla, encabezadoFiltros) {
			encabezadoFiltros.html('<input type="text" placeholder="' + encabezadoTabla.text() + '"class="input-sm form-control filtrable" style="width:100%;" />');
		},
		aniadirFiltroBusquedaSeleccionable: function(encabezadoTabla, encabezadoFiltros, filtrosSeleccionables, column) {
			var funcion = this;
			var $select = $("<select class='encabezado input-sm form-control' style='width:100%;'><option value='' selected='selected'>Todos</option></select>").appendTo(encabezadoFiltros);
			switch (true) {
				case encabezadoTabla.hasClass($variableUtil.claseDataNoDefinida):
					$.each(filtrosSeleccionables[encabezadoTabla.index()], function(i, filtro) {
						funcion.aniadirFiltroAlSeleccionable($select, filtro);
					});

					break;
				case encabezadoTabla.hasClass($variableUtil.claseDataVacia):
					break;
				case encabezadoTabla.hasClass($variableUtil.claseInsertableOpcionesHtml):
					$select.append(filtrosSeleccionables[encabezadoTabla.index()]);
					break;
				default:
					column.data().unique().sort().each(function(filtro, j) {
						funcion.aniadirFiltroAlSeleccionable($select, filtro);
					});
					break;
			}
			var claseAgregable = encabezadoTabla.attr("class").match(/agregable[\w-]*\b/);
			if (claseAgregable != null) {
				$select.addClass(claseAgregable[0]);
			}
			if (encabezadoTabla.hasClass("select2")) {
				$funcionUtil.crearSelect2($select);
			}
		},
		aniadirFiltroDeBusquedaEnEncabezado: function(datatable, tabla, filtrosSeleccionables) {
			var $thead = tabla.find("thead");
			var numeroEncabezados = $thead.find("tr").find("th").length;
			var funcion = this;
			filtrosSeleccionables = filtrosSeleccionables || {};
			$thead.append($funcionUtil.filaFiltro());
			$thead.find("#" + $variableUtil.idFilaFiltro).append($($funcionUtil.encabezadoFiltro()).multiply(numeroEncabezados));
			datatable.api().columns().every(function() {
				var column = this;
				var encabezadoTabla = $(column.header());
				var encabezadoFiltros = $thead.find("#" + $variableUtil.idFilaFiltro).find("th").eq(encabezadoTabla.index());
				switch (true) {
					case encabezadoTabla.hasClass($variableUtil.filtrable):
						funcion.aniadirFiltroBusquedaCajaTexto(encabezadoTabla, encabezadoFiltros);
						break;
					case encabezadoTabla.hasClass($variableUtil.seleccionable):
						funcion.aniadirFiltroBusquedaSeleccionable(encabezadoTabla, encabezadoFiltros, filtrosSeleccionables, column);
						break;
				}
			});
		},
		aniadirFiltroDeBusquedaEnEncabezadoTablaPersonalizado: function(datatable, tabla, filtrosSeleccionables, celdasCombinadas) {
			const $thead = tabla.find("thead");
			celdasCombinadas = celdasCombinadas || 0
			const numeroEncabezados = ($thead.find("tr").find("th").length) - celdasCombinadas;
			var funcion = this;
			filtrosSeleccionables = filtrosSeleccionables || {};
			$thead.append($funcionUtil.filaFiltro());
			$thead.find("#" + $variableUtil.idFilaFiltro).append($($funcionUtil.encabezadoFiltro()).multiply(numeroEncabezados));
			datatable.api().columns().every(function() {
				var column = this;
				var encabezadoTabla = $(column.header());
				var encabezadoFiltros = $thead.find("#" + $variableUtil.idFilaFiltro).find("th").eq(encabezadoTabla.index());
				switch (true) {
					case encabezadoTabla.hasClass($variableUtil.filtrable):
						funcion.aniadirFiltroBusquedaCajaTexto(encabezadoTabla, encabezadoFiltros);
						break;
					case encabezadoTabla.hasClass($variableUtil.seleccionable):
						funcion.aniadirFiltroBusquedaSeleccionable(encabezadoTabla, encabezadoFiltros, filtrosSeleccionables, column);
						break;
				}
			});
		},
		actualizarFiltroBusquedaSeleccionable: function(select, opcionAgregar) {
			if (select.children("option[value='" + opcionAgregar + "']").length === 0) {
				select.append("<option value='" + opcionAgregar + "' >" + opcionAgregar + "</option>");
			}
		},
		trasladarHaciaSelect: function(select, data, valor, texto) {
			select.empty();
			$.each(data, function(i, fila) {
				select.append("<option value='" + fila[valor] + "' >" + fila[texto] + "</option>");
			});
			$funcionUtil.crearSelect2(select, "sera o.o");
		},
		pintarMontosComisiones: function($tabla, selector) {
			$tabla.find(selector).filter(function() {
				var celda = $(this);
				var valor = parseFloat(celda.text());
				if (valor > 0) {
					celda.css("color", "blue");
				} else if (valor < 0) {
					celda.css("color", "red");
				} else {
					celda.css("color", "inherited");
				}
			});
		},
		formatoMonto: function(decimalCount, prefix, decimal, thousands, postfix) {
			decimalCount = (decimalCount == undefined || decimalCount == null) ? 2 : decimalCount;
			prefix = (prefix == undefined || prefix == null) ? '' : prefix;
			decimal = (decimal == undefined || decimal == null) ? '.' : decimal;
			thousands = (thousands == undefined || thousands == null) ? ',' : thousands;
			postfix = (postfix == undefined || postfix == null) ? '' : postfix;

			return $.fn.dataTable.render.number(thousands, decimal, decimalCount, prefix, postfix);
		},
		formatoComision: function(decimalCount, prefix, decimal, thousands, postfix) {
			decimalCount = (decimalCount == undefined || decimalCount == null) ? 4 : decimalCount;
			prefix = (prefix == undefined || prefix == null) ? '' : prefix;
			decimal = (decimal == undefined || decimal == null) ? '.' : decimal;
			thousands = (thousands == undefined || thousands == null) ? ',' : thousands;
			postfix = (postfix == undefined || postfix == null) ? '' : postfix;

			return $.fn.dataTable.render.number(thousands, decimal, decimalCount, prefix, postfix);
		},

	};

	$(".select2").on("select2:close", function() {
		$(this).trigger('blur');
	});

	$("input[type=text]").on("click", function() {
		$(this).select();
	});

	$(".upperCase").bind('keyup', function(e) {
		$(this).val(($(this).val()).toUpperCase());
	});

	$.fn.removeClassRegex = function(filter) {
		$(this).removeClass(function(index, className) {
			return (className.match(new RegExp("\\S*" + filter + "\\S*", 'g')) || []).join(' ')
		});
		return this;
	};

	$('body').tooltip({
		selector: "[data-tooltip=tooltip]",
		container: "body"
	});

	$(".lettersOnlyAccent").on("keyup", function() {
		if (this.value != this.value.replace(/[^a-z\u00f1\u00d1\u00E0-\u00FC\' ']/g, '')) {
			this.value = this.value.replace(/[^a-z\u00f1\u00d1\u00E0-\u00FC\' ']/g, '');
		}
	});

	$(".lettersOnly").on("keyup", function() {
		if (this.value != this.value.replace(/[^a-z\' ']/g, '')) {
			this.value = this.value.replace(/[^a-z\' ']/g, '');
		}
	});

	$(".numbersOnly").on("keyup", function() {
		if (this.value != this.value.replace(/[^0-9]/g, '')) {
			this.value = this.value.replace(/[^0-9]/g, '');
		}
	});

	$(".notDash").on("keyup", function() {
		if (this.value != this.value.replace(/[\-]/g, '')) {
			this.value = this.value.replace(/[\-]/g, '');
		}
	});

	$(".numbersOnlyAndDot").on("keyup", function() {
		if (this.value != this.value.replace(/[^0-9\.]/g, '')) {
			this.value = this.value.replace(/[^0-9\.]/g, '');
		}
	});

	$(".numbersAndLettersOnlyAndDot").on("keyup", function() {
		if (this.value != this.value.replace(/[^a-z\u00f1\u00d1\^0-9\.]/g, '')) {
			this.value = this.value.replace(/[^a-z\u00f1\u00d1\^0-9\.]/g, '');
		}
	});

	$(".numbersAndLettersOnly").on("keyup", function() {
		if (this.value != this.value.replace(/[^a-z\u00f1\u00d1\^0-9]/g, '')) {
			this.value = this.value.replace(/[^a-z\u00f1\u00d1\^0-9]/g, '');
		}
	});

	$.ajaxSetup({
		statusCode: {
			401: function(response) {
				$.confirm({
					icon: 'fa fa-warning',
					title: "Sesi\u00F3n expirada",
					type: "blue",
					content: "La sesi\u00F3n ha expirado, por favor, vuelva a iniciar sesi\u00F3n.",
					theme: "bootstrap",
					buttons: {
						confirm: {
							text: "Aceptar",
							action: function() {
								window.location.href = $variableUtil.root + "login";
							}
						}
					}
				});
			},
			403: function(response) {
				if (response.responseJSON == undefined) {
					$.confirm({
						icon: 'fa fa-warning',
						title: "Acceso Prohibido",
						type: "orange",
						content: response.responseText,
						theme: "bootstrap",
						buttons: {
							close: {
								text: 'Aceptar'
							}
						}
					});
				}
			},
			409: function(response) {
				if (response.responseJSON == undefined) {
					$funcionUtil.notificarException(response.responseText, "fa-warning", "Aviso", "warning");
				}
			},
			500: function(response) {
				$funcionUtil.notificarException(response.responseText, "fa-warning", "Aviso", "warning");
			}
		}
	});

	if ($("#navegacionFechaProceso").length > 0) {
		$funcionUtil.obtenerFechaProceso();
	}

	Number.prototype.formatMoney = function(c, d, t) {
		var n = this,
			c = isNaN(c = Math.abs(c)) ? 2 : c,
			d = d == undefined ? "." : d,
			t = t == undefined ? "," : t,
			s = n < 0 ? "-" : "",
			i = String(parseInt(n = Math.abs(Number(n) || 0).toFixed(c))),
			j = (j = i.length) > 3 ? j % 3 : 0;
		return s + (j ? i.substr(0, j) + t : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t) + (c ? d + Math.abs(n - i).toFixed(c).slice(2) : "");
	};
});