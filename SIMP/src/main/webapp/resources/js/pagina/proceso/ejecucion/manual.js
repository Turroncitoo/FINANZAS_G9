$(document).ready(function() {
	var $local = {
		$tabla: $(".tabla-pasos"),
		tabla: "",
		audioEjecucionExitosa: '/resources/media/ejecucion-exitosa.mp3',
		audioEjecucionError: '/resources/media/error-ejecucion.mp3'
	};

	var audioEjecucionExitosa = new Howl({
		src: $variableUtil.root + $local.audioEjecucionExitosa
	});

	var audioEjecucionError = new Howl({
		src: $variableUtil.root + $local.audioEjecucionError
	});

	$local.tabla = $local.$tabla.DataTable({
		"ordering": false,
		"paging": false,
		"searching": false,
		"info": false,
		"language": {
			"emptyTable": "No hay programas registrados en esta etapa."
		}
	});

	$local.$tabla.find("tbody").on("click", ".ejecutar", function() {
		var labelEjecutado = "<label class='label label-success'>Ejecutado</label>";
		var $botonEjecutar = $(this);
		var $tdBoton = $(this).parent();
		var codigoGrupo = $botonEjecutar.attr("data-codigoGrupo");
		var codigoPrograma = $botonEjecutar.attr("data-codigoPrograma");
		var codigoSubModulo = $botonEjecutar.attr("data-codigoSubModulo");
		var procedimiento = $botonEjecutar.attr("data-procedimiento");
		var urlSistema = $botonEjecutar.attr("data-urlSistema");
		var logControlProgramaResumen = {
			"codigoGrupo": codigoGrupo,
			"codigoPrograma": codigoPrograma,
			"codigoSubModulo": codigoSubModulo,
			"procedimiento": procedimiento,
			"urlSistema": urlSistema
		};
		if (codigoGrupo == "TAB_CTRL_PROC_RES") {
			$.ajax({
				type: "POST",
				url: $variableUtil.root + "proceso/ejecucion/manual?paso=prepararControlProceso",
				data: JSON.stringify(logControlProgramaResumen),
				beforeSend: function(xhr) {
					$botonEjecutar.attr("disabled", true).find("i").removeClass("fa-cog").addClass("fa-spinner fa-pulse fa-fw");
					xhr.setRequestHeader('Content-Type', 'application/json');
					xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
				},
				statusCode: {
					400: function(response) {
						if (response.responseJSON == undefined) {
							$funcionUtil.notificarException("Ocurri\u00f3 un error no identificado", "fa-warning", "Aviso", "warning");
						} else {
							var mensaje = response.responseJSON[0]
							$funcionUtil.notificarException(mensaje.mensajeError, "fa-warning", "Aviso", "warning");
						}
						audioEjecucionError.play();
					},
					403: function(response) {
						$funcionUtil.notificarException(response.responseText, "fa-warning", "Aviso", "warning");
						audioEjecucionError.play();
					},
					409: function(response) {
						$funcionUtil.notificarException(response.responseText, "fa-warning", "Aviso", "warning");
						audioEjecucionError.play();
					}
				},
				success: function(response) {
					$funcionUtil.notificarException("Ejecutado exitosamente", "fa-success", "Aviso", "success");
					audioEjecucionExitosa.play();
					$botonEjecutar.find("span").text("Reprocesar");
				},
				complete: function() {
					$botonEjecutar.attr("disabled", false).find("i").addClass("fa-cog").removeClass("fa-spinner fa-pulse fa-fw");
				}
			});
		} else if (codigoGrupo == "PAIU" || codigoGrupo == "COMP" || codigoGrupo == "CONC" || codigoGrupo == "CONT" || codigoGrupo == "TARIF" || codigoGrupo == "AVFP"
			|| codigoGrupo == "PREPAGO" || codigoGrupo == "PREPAGO1" || codigoGrupo == "PREPAGO_ENVIO") {
			var paso = "";
			if (codigoGrupo == "COMP") {
				paso = "compensacion";
			} else if (codigoGrupo == "CONC") {
				paso = "conciliacion";
			} else if (codigoGrupo == "PAIU") {
				paso = "descarga";
			} else if (codigoGrupo == "CONT") {
				paso = "contabilizacion";
				if (logControlProgramaResumen.codigoPrograma == "AFON" || logControlProgramaResumen.codigoPrograma == "ACOM") {
					paso = "contabilizacion-archivos";
				}
			} else if (codigoGrupo == "AVFP") {
				paso = "avanzeFechaProceso";
			} else if (codigoGrupo == "PREPAGO" || codigoGrupo == "PREPAGO1") {
				paso = "requerimiento-prepago"
			} else if (codigoGrupo == "PREPAGO_ENVIO") {
				paso = "requerimiento-prepago-envio"
			}
			$.ajax({
				type: "POST",
				url: $variableUtil.root + "proceso/ejecucion/manual?paso=" + paso,
				data: JSON.stringify(logControlProgramaResumen),
				beforeSend: function(xhr) {
					$botonEjecutar.attr("disabled", true).find("i").removeClass("fa-cog").addClass("fa-spinner fa-pulse fa-fw");
					xhr.setRequestHeader('Content-Type', 'application/json');
					xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
				},
				statusCode: {
					400: function(response) {
						if (response.responseJSON == undefined) {
							$funcionUtil.notificarException(response.responseText, "fa-warning", "Aviso", "warning");
						} else {
							var mensaje = response.responseJSON[0]
							$funcionUtil.notificarException(mensaje.mensajeError, "fa-warning", "Aviso", "warning");
						}
						audioEjecucionError.play();
					},
					403: function(response) {
						$funcionUtil.notificarException(response.responseText, "fa-warning", "Aviso", "warning");
						audioEjecucionError.play();
					},
					409: function(response) {
						$funcionUtil.notificarException(response.responseText, "fa-warning", "Aviso", "warning");
						audioEjecucionError.play();
					}
				},
				success: function(response) {
					var titulo = response.codigo == 1 ? "Ejecuci\u00f3n Completa" : "Ejecuci\u00f3n Parcial";
					$funcionUtil.notificarException(response.mensaje, "fa-success", titulo, "success");
					if (response.codigo == 1) {
						audioEjecucionExitosa.play();
					} else {
						audioEjecucionError.play();
					}
					$botonEjecutar.find("span").text("Reprocesar");
					if (codigoGrupo == "AVFP") {
						$funcionUtil.obtenerFechaProceso();
						window.location.href = $variableUtil.root + "proceso/ejecucion/manual#paso-1";
						window.location.reload();
						$("body").waitMe({
							effect: 'bounce',
							text: 'Cambiando de fecha proceso...',
							bg: 'rgba(255,255,255,0.7)',
							color: "#000",
							maxSize: '',
							waitTime: 3000,
							textPos: 'vertical',
							fontSize: '',
							source: '',
							onClose: function() { }
						});
					}
				},
				complete: function() {
					$botonEjecutar.attr("disabled", false).find("i").addClass("fa-cog").removeClass("fa-spinner fa-pulse fa-fw");
				}
			});
		}

	});

	$("#smartwizard").on("showStep", function(e, anchorObject, stepNumber, stepDirection, stepPosition) {
		if (stepPosition === 'first') {
			$("#prev-btn").addClass('disabled');
		} else if (stepPosition === 'final') {
			$("#next-btn").addClass('disabled');
		} else {
			$("#prev-btn").removeClass('disabled');
			$("#next-btn").removeClass('disabled');
		}
	});

	$('#smartwizard').smartWizard({
		selected: 0,
		theme: 'arrows',
		transitionEffect: 'fade',
		showStepURLhash: true,
		lang: {
			next: 'Siguiente',
			previous: 'Anterior'
		}
	});

	$("#prev-btn").on("click", function() {
		$('#smartwizard').smartWizard("prev");
		return true;
	});

	$("#next-btn").on("click", function() {
		$('#smartwizard').smartWizard("next");
		return true;
	});



});