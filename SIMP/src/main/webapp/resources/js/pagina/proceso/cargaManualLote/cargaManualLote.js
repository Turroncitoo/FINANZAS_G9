$(document).ready(function() {
	var atm;
	var archivoJournal = new Array();
	var archivos;
	var myJSON;
	var $local = {
		$btnCargarManualLote: $("#btnCargarManualLote"),
		$btnLimpiarTablaArchivos: $('#btnLimpiarTablaArchivos'),
		//$detalleArchivoCargado : $('#detalleArchivoCargado'),
		//$seleccionarArchivoEliminar : $('body #filaDetalleArchivos'),
		//$numArchivoCorrectos : $('#numArchivoCorrectos'),
		//$filaDetalleArchivos : $('#filaDetalleArchivos'),

		$tipoBusqueda: $("input[type='radio'][name='tipoBusqueda']"),

		$tipoCargaManual: $('#tipoCargaManual'),
		$criteriosContent: $('#criteriosContent'),
		$selectContent: $('#selectContent'),
		filtrosSeleccionables: [],

		$labelEstadoArchivo: $('.estadoA.alert-warning'),

		//$tablaDetalleContent : $('#tablaDetalleContent'),
		numFiltrosMin: 1,
		mensajeFiltros: function() {
			return "Debe seleccionar al menos " + this.numFiltrosMin + " filtro(s) para porder realizar la B\u00FAsqueda";
		},
		$modalDetalleConsulta: $("#modalDetalleConsulta"),
		$tablaDetalle: $("#tablaDetalle"),
		tablaDetalle: "",
		traceSeleccionado: "",

	};

	$formCargaManual = $("#formCargaManual");
	$formBusquedaCriterios = $("#formBusquedaCriterios");

	$('a[data-toggle="tab"]').on('shown.bs.tab', function(e) {
		ajustarColumnas();
	});

	$local.$tablaDetalle.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaDetalle.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	function ajustarColumnas() {
		$.fn.dataTable.tables({
			visible: true,
			api: true
		}).columns.adjust().draw();
	}

	Dropzone.options.myDropzone = {
		url: 'proceso/cargaManualLote/upload',
		autoProcessQueue: false,
		uploadMultiple: true,
		parallelUploads: 10,
		maxFiles: 10,
		maxFilesize: 10, // 10MB max
		acceptedFiles: 'application/vnd.ms-excel, .xls, .xlsx',
		addRemoveLinks: true,
		init: function() {
			dzClosure = this; // Makes sure that 'this' is understood inside the functions below.

			// for Dropzone to process the queue (instead of default form behavior):
			document.getElementById("btnCargarManualLote").addEventListener("click", function(e) {
				// Make sure that the form isn't actually being sent.
				e.preventDefault();
				e.stopPropagation();
				dzClosure.processQueue();
			});

			//send all the form data along with the files:
			this.on("sendingmultiple", function(data, xhr, formData) {
				formData.append("firstname", jQuery("#firstname").val());
				formData.append("lastname", jQuery("#lastname").val());
			});

			this.on("addedfile", function(file) {
				if (this.files.length) {
					var _i, _len;
					for (_i = 0, _len = this.files.length; _i < _len - 1; _i++) {// -1 to exclude current file
						if (this.files[_i].name === file.name && this.files[_i].size === file.size &&
							this.files[_i].lastModifiedDate.toString() === file.lastModifiedDate.toString()) {

							this.removeFile(file);
							$funcionUtil.notificarException("El archivo es igual a uno subido anteriormente", "fa-warning", "Aviso", "warning");
						}
					}
				}
			});
		},
		accept: function(file) {
			existe = false;
			for (i = 0; i < archivoJournal.length; i++) {
				if (archivoJournal[i].name == file.name) {
					existe = true;
				}
			}
			if (existe) {
				$funcionUtil.notificarException("El archivo es igual a uno subido anteriormente", "fa-warning", "Aviso", "warning");
				agregarTablaArchivoError(file, 'FALLO', 'danger', 'El archivo esta duplicado');
			} else {
				//					var nombreCompletoArchivoSubido = file.name.split('_');
				//					var BIN = nombreCompletoArchivoSubido[1];
				//					var idATM = nombreCompletoArchivoSubido[2];
				//					var fechaProcesoSinFormato = nombreCompletoArchivoSubido[3];
				//					fechaProcesoSinFormato = fechaProcesoSinFormato.split('.');
				//					fechaProcesoSinFormato = fechaProcesoSinFormato[0];
				//					if(fechaProcesoSinFormato.length==8){
				//						
				//						let anioProceso = fechaProcesoSinFormato.charAt(0)+fechaProcesoSinFormato.charAt(1)+fechaProcesoSinFormato.charAt(2)+fechaProcesoSinFormato.charAt(3);
				//						let mesProceso = fechaProcesoSinFormato.charAt(4)+fechaProcesoSinFormato.charAt(5);
				//						let diaProceso = fechaProcesoSinFormato.charAt(6)+fechaProcesoSinFormato.charAt(7);
				//						let fechaProceso = anioProceso+"-"+mesProceso+"-"+diaProceso;
				//						let fechaProcesoFormateado = diaProceso+"/"+mesProceso+"/"+anioProceso;
				//						
				//						if(fechaProceso<=new Date().toJSON().slice(0,10)){
				//							agregarTablaArchivoCorrecto(idATM,fechaProcesoFormateado,file,'PENDIENTE','warning','-');	
				//						}else{
				//							$funcionUtil.notificarException("La fecha de proceso del archivo es mayor a la fecha actual", "fa-warning", "Aviso", "warning");
				//							agregarTablaArchivoError(file,'FALLO','danger','Fecha de proceso mayor a la actual');
				//						}
				//										
				//					}else{
				//						$funcionUtil.notificarException("T\u00EDtulo del archivo no tiene el formato correcto", "fa-warning", "Aviso", "warning");
				//						agregarTablaArchivoError(file,'FALLO','danger','Formato de archivo inv\u00E1lido');
				//					}
				archivos = this.files;
			}


		},
		dictInvalidFileType: "Tipo de archivo no v\u00E1lido",
		dictMaxFilesExceeded: function() {
			$funcionUtil.notificarException("Solo se permite subir {{maxFiles}} archivo", "fa-warning", "Aviso", "warning");
		}
	}

	function agregarTablaArchivoCorrecto(idAtm, fechaProceso, file, estado, info, obs) {
		let existe;
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "atm?accion=buscarTodos",
			beforeSend: function(xhr) {
				xhr.setRequestHeader('Content-Type', 'application/json');
			},
			success: function(response) {
				if (response.length == 0) {
					return -1;
				} else {
					for (i = 0; i < response.length; i++) {
						if (idAtm == response[i].idATM) {
							atm = response[i];
							existe = true;
							break;
						}
					}
					if (existe) {
						archivoJournal[archivoJournal.length] = file;
						let fila = '<div class="row"><div class="col-xs-3 col-md-3"><label>' + file.name + '</label></div><div class="col-xs-2 col-md-2"><label>' + atm.idATM + " - " + atm.descripcionATM + '</label></div><div class="col-xs-2 col-md-2 text-center"><label>' + fechaProceso + '</label></div><div class="col-xs-1 col-md-1 text-center"><label class="estadoA alert-' + info + '">' + estado + '</label></div><div class="col-xs-3 col-md-3 text-center"><label>' + obs + '</label></div><div class="col-xs-1 col-md-1 text-center"><button type="button" class="btn btn-xs btn-danger eliminar" numFile="' + file.name + '" data-tooltip="tooltip" data-original-title="Eliminar"><i class="fa fa-trash"></i></button></div></div>';
						$local.$filaDetalleArchivos.append(fila);
						$local.$numArchivoCorrectos.text(archivoJournal.length);
						$local.$detalleArchivoCargado.removeClass('hidden');
					} else {
						$funcionUtil.notificarException("Archivo cargado no le pertenece a ning\u00FAn ATM", "fa-warning", "Aviso", "warning");
						agregarTablaArchivoError(file, 'FALLO', 'danger', 'Archivo cargado no le pertenece a ning\u00FAn ATM');
					}

				}
			}
		});
	}

	$local.$btnCargarManualLote.on('click', function() {
		//		if(!$formCargaManual.valid()){
		//			return;
		//		}
		//		if(archivoJournal==null){
		//			$funcionUtil.notificarException("Agregue un archivo.", "fa-warning", "Aviso", "warning");
		//			return;
		//		}
		//		
		//			var data = new FormData();
		//			for(i=0;i<archivoJournal.length;i++){
		//				data.append("archivos[]", archivoJournal[i]);
		//			}
		//			
		//			$.ajax({
		//				type : "POST",
		//				url : $variableUtil.root + "proceso/ejecucion/manual/cargaManualLote/upload",
		//				data : data,
		//				contentType: false, 
		//				processData: false,
		//				beforeSend : function(xhr) {
		//					$local.$btnCargarManualLote.attr("disabled", true).find("i").removeClass("fa-upload").addClass("fa-spinner fa-pulse fa-fw");
		//					xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
		//				},
		//				statusCode : {
		//					400 : function(response) {
		//						$funcionUtil.limpiarMensajesDeError($formMantenimiento);
		//						$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formMantenimiento);
		//					}
		//				},
		//				success : function(data) {
		//					$funcionUtil.notificarException(data,"fa-success", "Aviso", "success");
		//					$( "body" ).find( ".estadoA" ).removeClass('alert-warning');
		//					$( "body" ).find( ".estadoA" ).addClass('alert-success');
		//					$( "body" ).find( ".estadoA" ).text('PROCESADO');
		//				},
		//				error : function(response) {
		//				},
		//				complete : function(response) {
		//					$local.$btnCargarManualLote.attr("disabled", false).find("i").addClass("fa-upload").removeClass("fa-spinner fa-pulse fa-fw");
		//				}
		//			});	


		//		$.when(xlsxToJSON(archivos[0], setJSON)).then(function(){
		//		});
		//PROBANDO CONVERSION A JSON
		if (archivos[0] != null || archivos[0] != 'undefined') {
			xlsxToJSON(archivos[0])
				.then(json => $funcionUtil.notificarException(json, "fa-warning", "Aviso", "warning"))
				.catch(err => $funcionUtil.notificarException(err.message, "fa-warning", "Aviso", "warning"));
		} else {
			$funcionUtil.notificarException("No hay excel cargados", "fa-warning", "Aviso", "warning");
		}
	});

	function agregarTablaArchivoError(file, estado, info, obs) {
		let fila = '<div class="row"><div class="col-xs-3 col-md-3"><label>' + file.name + '</label></div><div class="col-xs-2 col-md-2 text-center"><label>-</label></div><div class="col-xs-2 col-md-2 text-center"><label>-</label></div><div class="col-xs-1 col-md-1 text-center"><label class="alert-' + info + '">' + estado + '</label></div><div class="col-xs-3 col-md-3"><label>' + obs + '</label></div><div class="col-xs-1 col-md-1 text-center">-</div></div>';
		$local.$filaDetalleArchivos.append(fila);
		$local.$detalleArchivoCargado.removeClass('hidden');
	}

	var xlsxToJSON = (file) => {
		const promise = new Promise((resolve, reject) => {
			var reader = new FileReader();
			var json;
			reader.readAsArrayBuffer(file);
			reader.onload = (e) => {
				var data = new Uint8Array(reader.result);
				var list = new Array();

				for (let f of data) {
					list.push(String.fromCharCode(f));
				}
				var bstr = list.join("");

				/* llamando XLSX */
				var workbook = XLSX.read(bstr, { type: 'binary' });

				var first_sheet_name = workbook.SheetNames[0];

				var woorksheet = workbook.Sheets[first_sheet_name];

				json = XLSX.utils.sheet_to_json(woorksheet, { range: 4, raw: false });
				if (json == null || typeof json === 'undefined') {
					reject(new Error('No se pudo convertir a JSON'));
				} else {
					resolve(json);
				}
			};
		});
		return promise;
	};

	//	function setJSON(json) {
	//		myJSON = json;
	//	}


	//	$local.$seleccionarArchivoEliminar.on('click', 'button', function(){
	//		let nomArchivoEliminar = $(this).attr('numFile');
	//		let pos;
	//		for(i=0;i<archivoJournal.length;i++){
	//			if(archivoJournal[i].name==nomArchivoEliminar){
	//				pos=i;
	//				Dropzone.forElement("#myDropzone").removeFile(archivoJournal[i]);
	//				break;
	//			}
	//		}
	//		archivoJournal.splice(pos,1);
	//		$(this).parents()[1].remove();
	//		$local.$numArchivoCorrectos.text(archivoJournal.length);
	//		$funcionUtil.notificarException("Usted ha borrado el archivo "+nomArchivoEliminar+".","fa-success", "Aviso", "success");
	//	});

	$local.$btnLimpiarTablaArchivos.on('click', function() {
		Dropzone.forElement("#myDropzone").removeAllFiles(true);
		$local.$filaDetalleArchivos.empty();
		archivoJournal = new Array();
		$local.$numArchivoCorrectos.text(archivoJournal.length);
		$local.$detalleArchivoCargado.addClass('hidden');
		$funcionUtil.notificarException("Se ha borrado el contenido de la tabla.", "fa-success", "Aviso", "success");
	});

});