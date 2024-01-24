$(document).ready(function() {

	var $local = {
		valorDescripcionNacionalidad: "",
		valorRucSeleccionado: "",
		valorNombreManda1: "",
		valorNumManda1: "",
		valoraFonoMnada1: "",
		valorNombreManda2: "",
		valorTipoManda2: "",
		valorDescTipoManda2: "",
		valorNumManda2: "",
		valorFonoMnada2: "",


		$tipoBusqueda: $("input[type='radio'][name='tipoBusqueda']"),

		//Formulario requerimiento
		$registrarRequerimientoDiv: $("#registrarRequerimientoDiv"),
		$selectInstitucion: $('#selectInstitucion'),
		$selectEmpresa: $('#selectEmpresa'),
		$selectCliente: $('#selectCliente'),
		$selectLogo: $('#selectLogo'),
		$selectProducto: $('#selectProducto'),
		$selectAfinidad: $('#selectAfinidad'),
		$selectTipoIndDistribucion: $('#selectTipoIndDistribucion'),
		$selectNacionalidad: $('#selectNacionalidad'),
		$selectTipoEmision: $('#selectTipoEmision'),
		$selectTipoTarjetas: $('#selectTipoTarjetas'),
		$selectTipoAfiliacion: $('#selectTipoAfiliacion'),
		$selectSexo: $('#selectSexo'),

		$selectTipoDocumentoAfiliacionMandatorio1: $('#selectTipoDocumentoAfiliacionMandatorio1'),
		$selectTipoDocumentoAfiliacionMandatorio2: $('#selectTipoDocumentoAfiliacionMandatorio2'),
		$selectCategoria: $('#selectCategoria'),
		$btnRegistrarNominadas: $('#btnRegistrarNominadas'),
		$btnRegistrarInnominadas: $('#btnRegistrarInnominadas'),
		$divBtnRegistrarInnominadas: $('#divBtnRegistrarInnominadas'),
		$divBtnRegistrarNominadas: $('#divBtnRegistrarNominadas'),

		$selectAfinidadUpdate: $('#selectAfinidadUpdate'),
		$selectCategoriaUpdate: $('#selectCategoriaUpdate'),
		$selectTipoTarjetasUpdate: $('#selectTipoTarjetasUpdate'),


		$divDatosInstitucion: $('#divDatosInstitucion'),
		$divDatosProducto: $('#divDatosProducto'),
		$divDatosProducto2: $('#divDatosProducto2'),
		$divCantidad: $('#divCantidad'),
		$divCargaLoteNominadas: $('#divCargaLoteNominadas'),
		$cargandoArchivoNominadas: $('#cargandoArchivoNominadas'),
		$divFooterValidaciones: $('#divFooterValidaciones'),
		$divTablaClientesRequerimientoNominadas: $('#divTablaClientesRequerimientoNominadas'),

		$modalAfiliacion: $('#modalAfiliacion'),
		$modalMantenimiento: $('#modalMantenimiento'),

		$selectTipoDocumentoAfiliacion: $('#selectTipoDocumentoAfiliacion'),
		$txtNumeroDocumentoAfiliacion: $('#txtNumeroDocumentoAfiliacion'),
		$selectMonedaAfiliacion: $('#selectMonedaAfiliacion'),
		$btnActualizarAfiliacion: $('#btnActualizarAfiliacion'),
		$rangoFechaNacimiento: $('#rangoFechaNacimiento'),

		$tablaRequerimientoNominadas: $('#tablaRequerimientoNominadas'),
		tablaRequerimientoNominadas: '',
		$filaSeleccionada: '',
		filtrosSeleccionables: [],

		$formTipoLote: $('#formTipoLote'),
		$formEmpresa: $('#formEmpresa'),
		$formCliente: $('#formCliente'),
		$formLogo: $('#formLogo'),

		$idLote: null,

		$btnExportarPlantillaNominadas: $('#btnExportarPlantillaNominadas'),
		$cargandoArchivoNominadasTexto: $('#cargandoArchivoNominadasTexto'),

		$listaDocumentos: [],
		$listaMonedas: [],
		$inputOperacion: $('#inputOperacion'),

		$myDropzone: $('#myDropzone'),

		//Consulta lote
		$consultaLoteDiv: $("#consultaLoteDiv"),
		$btnBuscarConsultaLote: $('#btnBuscarConsultaLote'),
		$fechaRegistro: $("#fechaRegistro"),
		$fechaProceso: $("#fechaProceso"),

		permisoActualizacion: false,
		permisoEliminacion: false,

		$tablaConsultaLote: $('#tablaConsultaLote'),
		tablaConsultaLote: '',
		filtrosSeleccionablesConsulta: {},
		arregloSiNo: ["1", "0"],
		$filaSeleccionadaConsulta: '',

		$actualizarLote: $('#actualizarLote'),

		$modalDetalleLoteAfiliacion: $('#modalDetalleLoteAfiliacion'),
		$modalDetalleLoteRecargaDebito: $('#modalDetalleLoteRecargaDebito'),


		$tablaConsultaLoteDetalleAfiliacion: $('#tablaConsultaLoteDetalleAfiliacion'),
		tablaConsultaLoteDetalleAfiliacion: '',
		filtrosSeleccionablesConsultaDetalleAfiliacion: {},

		$tablaConsultaLoteDetalleRecargaDebito: $('#tablaConsultaLoteDetalleRecargaDebito'),
		tablaConsultaLoteDetalleRecargaDebito: '',
		filtrosSeleccionablesConsultaDetalleRecargaDebito: {},




		$selectInstitucionConsulta: $('#selectInstitucionConsulta'),
		$selectEmpresaConsulta: $('#selectEmpresaConsulta'),
		$selectClienteConsulta: $('#selectClienteConsulta'),
		$selectLogoConsulta: $('#selectLogoConsulta'),
		$selectProductoConsulta: $('#selectProductoConsulta'),
		$selectTipoTarjetasConsulta: $('#selectTipoTarjetasConsulta'),

		$archivosRequerimiento: [],
		$btnExportarConsultaLoteDetalle: $('#btnExportarConsultaLoteDetalle'),

		$btnExportarConsultaLote: $('#btnExportarConsultaLote'),

		//Recarga y debitos
		$recargasDebitoDiv: $('#recargasDebitoDiv'),
		$btnRecargaDebito: $('#btnRecargaDebito'),
		$btnExportarPlantillaRecargaDebito: $('#btnExportarPlantillaRecargaDebito'),
		$cargandoArchivoRecargaDebitoTexto: $('#cargandoArchivoRecargaDebitoTexto'),
		$cargandoArchivoRecargaDebito: $('#cargandoArchivoRecargaDebito'),
		$tablaRecargaDebito: $('#tablaRecargaDebito'),
		tablaRecargaDebito: '',
		$archivosRecargaDebito: [],
		$myDropzone2: $('#myDropzone2'),

	}

	$funcionUtil.crearSelect2($local.$selectTipoAfiliacion);
	$funcionUtil.crearSelect2($local.$selectInstitucion);
	$funcionUtil.crearSelect2($local.$selectEmpresa);
	$funcionUtil.crearSelect2($local.$selectCliente);
	$funcionUtil.crearSelect2($local.$selectLogo);
	$funcionUtil.crearSelect2($local.$selectProducto);
	$funcionUtil.crearSelect2($local.$selectAfinidad);
	$funcionUtil.crearSelect2($local.$selectTipoEmision);
	$funcionUtil.crearSelect2($local.$selectTipoTarjetas);
	$funcionUtil.crearSelect2($local.$selectSexo);
	$funcionUtil.crearSelect2($local.$selectCategoria);
	$funcionUtil.crearSelect2($local.$selectTipoIndDistribucion);
	$funcionUtil.crearSelect2($local.$selectNacionalidad);
	$funcionUtil.crearSelect2($local.$selectAfinidadUpdate);
	$funcionUtil.crearSelect2($local.$selectCategoriaUpdate);
	$funcionUtil.crearSelect2($local.$selectTipoTarjetasUpdate);
	$funcionUtil.crearSelect2($local.$selectTipoDocumentoAfiliacionMandatorio1);
	$funcionUtil.crearSelect2($local.$selectTipoDocumentoAfiliacionMandatorio2);




	$funcionUtil.crearSelect2($local.$selectInstitucionConsulta);
	$funcionUtil.crearSelect2($local.$selectEmpresaConsulta);
	$funcionUtil.crearMultipleSelect2($local.$selectClienteConsulta, "TODOS");
	$funcionUtil.crearSelect2($local.$selectLogoConsulta);
	$funcionUtil.crearMultipleSelect2($local.$selectProductoConsulta, "TODOS");
	$funcionUtil.crearSelect2($local.$selectTipoTarjetasConsulta);

	$funcionUtil.crearSelect2($local.$selectTipoDocumentoAfiliacion);
	$funcionUtil.crearSelect2($local.$selectMonedaAfiliacion);

	$funcionUtil.crearDatePickerSimple($local.$rangoFechaNacimiento, "DD/MM/YYYY");
	$funcionUtil.crearDateRangePickerConsulta($local.$fechaRegistro);
	$funcionUtil.crearDateRangePickerSinValorPorDefecto($local.$fechaProceso, "right");

	$formRegistroRequerimiento = $("#formRegistroRequerimiento");
	$formConsultaLote = $('#formConsultaLote');
	$formModalAfiliacion = $('#formModalAfiliacion');
	$formDetalleLoteAfiliacion = $('#formDetalleLoteAfiliacion');
	$formDetalleLoteRecargaDebito = $('#formDetalleLoteRecargaDebito');

	$formRecargaDebito = $("#formRecargaDebito");
	$formActualizacionLote = $("#formActualizacionLote");

	$local.$modalMantenimiento.PopupWindow({
		title: "Edici\u00F3n de Lote",
		autoOpen: false,
		modal: false,
		height: 470,
		width: 1200,
	});

	$local.$modalAfiliacion.PopupWindow({
		title: "Edici\u00F3n de registro",
		autoOpen: false,
		modal: false,
		height: 640,
		width: 700,
	});

	$local.$modalDetalleLoteAfiliacion.PopupWindow({
		title: "Detalle Lote Afiliaci\u00F3n",
		autoOpen: false,
		modal: false,
		height: 645,
		width: 1350,
	});

	$local.$modalDetalleLoteRecargaDebito.PopupWindow({
		title: "Detalle Lote Recarga/D\u00E9bito",
		autoOpen: false,
		modal: false,
		height: 645,
		width: 1350,
	});



	$local.$tipoBusqueda.on('change', function() {
		var val = $(this).val();
		switch (val) {
			case 'consultaLote':
				$local.$consultaLoteDiv.removeClass("hidden");
				$local.$registrarRequerimientoDiv.addClass("hidden");
				$local.$recargasDebitoDiv.addClass("hidden");
				break;
			case 'registrarRequerimiento':
				$local.$consultaLoteDiv.addClass("hidden");
				$local.$registrarRequerimientoDiv.removeClass("hidden");
				$local.$recargasDebitoDiv.addClass("hidden");
				break;
			case 'recargasDebitos':
				$local.$consultaLoteDiv.addClass("hidden");
				$local.$registrarRequerimientoDiv.addClass("hidden");
				$local.$recargasDebitoDiv.removeClass("hidden");
				break;
		}
	});

	$local.$selectTipoAfiliacion.on('change', function() {
		var val = $(this).val();
		switch (val) {
			case 'N': //NOMINADAS
				$local.$divBtnRegistrarInnominadas.addClass("hidden");
				$local.$divBtnRegistrarNominadas.removeClass("hidden");

				$local.$divCantidad.addClass("hidden");
				$local.$divCargaLoteNominadas.removeClass("hidden");
				$local.$divFooterValidaciones.removeClass("hidden");

				$local.$divTablaClientesRequerimientoNominadas.removeClass("hidden");
				break;
			case 'I': //INNOMINADAS
				$local.$divBtnRegistrarInnominadas.removeClass("hidden");
				$local.$divBtnRegistrarNominadas.addClass("hidden");

				$local.$divCantidad.removeClass("hidden");
				$local.$divCargaLoteNominadas.addClass("hidden");
				$local.$divFooterValidaciones.addClass("hidden");

				$local.$divTablaClientesRequerimientoNominadas.addClass("hidden");
				break;
		}
	});

	function buscarTiposDocumento() {
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "multiTabDet/multiTabCab/1",
			success: function(response) {
				if (response.length == 0) {
					$funcionUtil.notificarException("La tabla de tablas de n\u00FAmero documentos (1) no ha sido encontrada, el proceso de afiliaciones nominadas puede fallar.", "fa-warning", "Aviso", "warning");
				}
				response.forEach(function(item) {
					$local.$listaDocumentos.push(item.idItem);
				});
			}
		});
	}

	function buscarMonedas() {
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "moneda?accion=buscarTodos",
			success: function(response) {
				if (response.length == 0) {
					$funcionUtil.notificarException("Las monedas no han sido encontrada, el proceso de afiliaciones nominadas puede fallar.", "fa-warning", "Aviso", "warning");
				}
				response.forEach(function(item) {
					$local.$listaMonedas.push(item.codigoMoneda.toString());
				});
			}
		});
	}

	buscarTiposDocumento();
	buscarMonedas();

	var archivo = null;

	function removerArchivoAfiliaciones() {
		archivo.previewElement.remove();
		$funcionUtil.eliminarArchivoEnLista(archivo.name, $local.$archivosRequerimiento, "name");
		$local.tablaRequerimientoNominadas.clear().draw();
	}

	//Dropzone.options.myDropzone = {
	var myDropzone = new Dropzone("div#myDropzone", {
		url: "procesos/afiliaciones",
		paramName: "archivos",
		autoProcessQueue: false,
		uploadMultiple: false,
		parallelUploads: 1,
		maxFiles: 1,
		acceptedFiles: '.xlsx',
		addRemoveLinks: true,
		accept: function(file) {
			archivo = file;
			if ($local.$archivosRequerimiento.length != 0) {
				file.previewElement.remove();
				$funcionUtil.notificarException("El archivo de afiliaciones ya se encuentra adjuntado.", "fa-warning", "Aviso", "warning");
				return;
			}
			if ($funcionUtil.existeArchivoEnListaArchivos(file.name, $local.$archivosRequerimiento, "name")) {
				file.previewElement.remove();
				$funcionUtil.notificarException("Archivo con el nombre <b> " + file.name + " </b> ya fue agregado.", "fa-warning", "Aviso", "warning");
				return;
			}
			$local.$archivosRequerimiento.push(file);
			cargarArchivoAfiliacionesEnServerParaValidar($local.$archivosRequerimiento[0]);
		},
		removedfile: function(file) {
			archivo = file;
			removerArchivoAfiliaciones();
		},
		dictInvalidFileType: "Tipo de archivo no v\u00E1lido",
		dictMaxFilesExceeded: function() {
			$funcionUtil.notificarException("Solo se permite subir {{maxFiles}} archivo.", "fa-warning", "Aviso", "warning");
		}
	});

	$local.tablaRequerimientoNominadas = $local.$tablaRequerimientoNominadas.DataTable({
		"initComplete": function() {
			$local.$tablaRequerimientoNominadas.wrap("<div class='table-responsive'></div>");
			$local.filtrosSeleccionables["23"] = $local.arregloSiNo;
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaRequerimientoNominadas, $local.filtrosSeleccionables);
		},
		"language": {
			"emptyTable": "No se encontraron transacciones"
		},
		"columnDefs": [
			//#C4E3F3 obligatorio
			//#EBCCCC formato incorrecto
			{
				"targets": 0,
				"className": "all filtrable",
				"createdCell": function(tdElement, cellData, rowData, rowNum, colNum) {
					if ($funcionUtil.validarValorNulo(rowData.tipoDocumento)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-Tipo de documento es obligatorio. (Volver a cargar excel corregido)</br>";
						$(tdElement).css("background-color", "#C4E3F3");
					}
					if (!$funcionUtil.validarValorEnArreglo(rowData.tipoDocumento, $local.$listaDocumentos)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-Tipo de documento no contiene un valor v\u00E1lido. (Volver a cargar excel corregido)</br>";
						$(tdElement).css("background-color", "#EBCCCC");
					}
				}
			}, {
				"targets": 1,
				"className": "all filtrable",
				"createdCell": function(tdElement, cellData, rowData, rowNum, colNum) {
					if ($funcionUtil.validarValorNulo(cellData)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-N\u00FAmero de documento es obligatorio. (Volver a cargar excel corregido)</br>";
						$(tdElement).css("background-color", "#C4E3F3");

					}
					if (!$funcionUtil.validarCantidadMaxCaracteres(cellData, 12) && !$funcionUtil.validarFormatoNumerico(cellData)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-N\u00FAmero de documento contiene un formato incorrecto. (Volver a cargar excel corregido)</br>";
						$(tdElement).css("background-color", "#EBCCCC");
					}
				}
			}, {
				"targets": 2,
				"className": "all filtrable",
				"createdCell": function(tdElement, cellData, rowData, rowNum, colNum) {
					if ($funcionUtil.validarValorNulo(cellData)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-Nombre es obligatorio. Editar registro.</br>";
						$(tdElement).css("background-color", "#C4E3F3");

					}
					if (!$funcionUtil.validarCantidadMaxCaracteres(cellData, 15)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-Nombre contiene un formato incorrecto. (Editar registro)</br>";
						$(tdElement).css("background-color", "#EBCCCC");
					}
				}
			}, {
				"targets": 3,
				"className": "all filtrable",
				"createdCell": function(tdElement, cellData, rowData, rowNum, colNum) {
					if ($funcionUtil.validarValorNulo(cellData)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-Apellido Parterno es obligatorio. (Editar registro)</br>";
						$(tdElement).css("background-color", "#C4E3F3");

					}
					if (!$funcionUtil.validarCantidadMaxCaracteres(cellData, 15)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-Apellido Parterno contiene un formato incorrecto. (Editar registro)</br>";
						$(tdElement).css("background-color", "#EBCCCC");
					}
				}
			}, {
				"targets": 4,
				"className": "all filtrable",
				"createdCell": function(tdElement, cellData, rowData, rowNum, colNum) {
					if ($funcionUtil.validarValorNulo(cellData)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-Apellido Marterno es obligatorio. (Editar registro)</br>";
						$(tdElement).css("background-color", "#C4E3F3");

					}
					if (!$funcionUtil.validarCantidadMaxCaracteres(cellData, 15)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-Apellido Marterno contiene un formato incorrecto. (Editar registro)</br>";
						$(tdElement).css("background-color", "#EBCCCC");
					}
				}
			}, {
				"targets": 5,
				"className": "all filtrable",
				"createdCell": function(tdElement, cellData, rowData, rowNum, colNum) {
					if ($funcionUtil.validarValorNulo(cellData)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-Nombre Embossing es obligatorio. (Editar registro)</br>";
						$(tdElement).css("background-color", "#C4E3F3");

					}
					if (!$funcionUtil.validarCantidadMaxCaracteres(cellData, 26)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-Nombre Embossing contiene un formato incorrecto. (Editar registro)</br>";
						$(tdElement).css("background-color", "#EBCCCC");
					}
				}
			}, {
				"targets": 6,
				"className": "all filtrable",
				"createdCell": function(tdElement, cellData, rowData, rowNum, colNum) {
					if ($funcionUtil.validarValorNulo(cellData)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-RUC es obligatorio. (Editar registro)</br>";
						$(tdElement).css("background-color", "#C4E3F3");

					}
					if (!$funcionUtil.validarCantidadMaxCaracteres(cellData, 11)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-RUC contiene un formato incorrecto. (Editar registro)</br>";
						$(tdElement).css("background-color", "#EBCCCC");
					}
				}
			}, {
				"targets": 7,
				"className": "all filtrable",
				"createdCell": function(tdElement, cellData, rowData, rowNum, colNum) {
					if ($funcionUtil.validarValorNulo(cellData)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-Nombre Cliente es obligatorio. (Editar registro)</br>";
						$(tdElement).css("background-color", "#C4E3F3");

					}
					if (!$funcionUtil.validarCantidadMaxCaracteres(cellData, 26)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-Nombre Cliente contiene un formato incorrecto. (Editar registro)</br>";
						$(tdElement).css("background-color", "#EBCCCC");
					}
				}
			}, {
				"targets": 8,
				"className": "all filtrable dt-center",
				"createdCell": function(tdElement, cellData, rowData, rowNum, colNum) {
					if (!$funcionUtil.validarFormatoMMAA(cellData)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-Fecha vencimiento no contiene un valor v\u00E1lido. (Editar registro)</br>";
						$(tdElement).css("background-color", "#EBCCCC");
					}
				}
			}, {
				"targets": 9,
				"className": "all filtrable",
				"createdCell": function(tdElement, cellData, rowData, rowNum, colNum) {
					if (!$funcionUtil.validarCantidadMaxCaracteres(cellData, 100)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-Direcci\u00F3n no contiene un valor v\u00E1lido. (Editar registro)</br>";
						$(tdElement).css("background-color", "#EBCCCC");
					}
				}
			}, {
				"targets": 10,
				"className": "all filtrable",
				"createdCell": function(tdElement, cellData, rowData, rowNum, colNum) {
					if (!$funcionUtil.validarCantidadMaxCaracteres(cellData, 12) && !$funcionUtil.validarFormatoNumerico(cellData)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-Celular no contiene un valor v\u00E1lido. (Editar registro)</br>";
						$(tdElement).css("background-color", "#EBCCCC");
					}
				}
			}, {
				"targets": 11,
				"className": "all filtrable",
				"createdCell": function(tdElement, cellData, rowData, rowNum, colNum) {
					if ($funcionUtil.validarValorNulo(rowData.sexo)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-Sexo es obligatorio. (Editar registro)</br>";
						$(tdElement).css("background-color", "#C4E3F3");

					}
					if (!$funcionUtil.validarCantidadMaxCaracteres(rowData.sexo, 1) && !$funcionUtil.validarValorEnArreglo(rowData.sexo, ['M', 'F'])) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-Sexo no contiene un valor v\u00E1lido. (Editar registro)</br>";
						$(tdElement).css("background-color", "#EBCCCC");
					}
				}
			}, {
				"targets": 12,
				"className": "all filtrable dt-center",
				"createdCell": function(tdElement, cellData, rowData, rowNum, colNum) {
					if (!$funcionUtil.validarFechaDDMMYYYY(cellData)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-Fecha Nacimiento no contiene un valor v\u00E1lido. (Editar registro)</br>";
						$(tdElement).css("background-color", "#EBCCCC");
					}
				}
			}, {
				"targets": 13,
				"className": "all filtrable",
				"createdCell": function(tdElement, cellData, rowData, rowNum, colNum) {
					if ($funcionUtil.validarValorNulo(rowData.indicadorDistribucion)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-Ind. Distribuci\u00F3n es obligatorio. (Editar registro)</br>";
						$(tdElement).css("background-color", "#C4E3F3");

					}
					if (!$funcionUtil.validarCantidadMaxCaracteres(rowData.indicadorDistribucion, 1) && !$funcionUtil.validarValorEnArreglo(rowData.indicadorDistribucion, ['1', '2', '3'])) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-Ind. Distribuci\u00F3n no contiene un valor v\u00E1lido. (Editar registro)</br>";
						$(tdElement).css("background-color", "#EBCCCC");
					}
				}
			}, {
				"targets": 14,
				"className": "all filtrable",
				"createdCell": function(tdElement, cellData, rowData, rowNum, colNum) {
					if (!$funcionUtil.validarCantidadMaxCaracteres(cellData, 20)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-Nacionalidad no contiene un valor v\u00E1lido. (Editar registro)</br>";
						$(tdElement).css("background-color", "#EBCCCC");
					}
				}
			}, {
				"targets": 15,
				"className": "all filtrable",
				"createdCell": function(tdElement, cellData, rowData, rowNum, colNum) {
					if ($funcionUtil.validarValorNulo(cellData)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-Nombre Mandatorio 1 es obligatorio. (Editar registro)</br>";
						$(tdElement).css("background-color", "#C4E3F3");

					}
					if (!$funcionUtil.validarCantidadMaxCaracteres(cellData, 40)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-Nombre Mandatorio 1 no contiene un valor v\u00E1lido. (Editar registro)</br>";
						$(tdElement).css("background-color", "#EBCCCC");
					}
				}
			}, {
				"targets": 16,
				"className": "all filtrable",
				"createdCell": function(tdElement, cellData, rowData, rowNum, colNum) {
					if ($funcionUtil.validarValorNulo(rowData.tipoManda1)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-T. Doc. Mandatorio 1 es obligatorio. (Editar registro)</br>";
						$(tdElement).css("background-color", "#C4E3F3");
					}
					if (!$funcionUtil.validarValorEnArreglo(rowData.tipoManda1, $local.$listaDocumentos)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-T. Doc. Mandatorio 1 no contiene un valor v\u00E1lido. (Editar registro)</br>";
						$(tdElement).css("background-color", "#EBCCCC");
					}
				}
			}, {
				"targets": 17,
				"className": "all filtrable",
				"createdCell": function(tdElement, cellData, rowData, rowNum, colNum) {
					if ($funcionUtil.validarValorNulo(cellData)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-Doc. Mandatorio 1 es obligatorio. (Editar registro)</br>";
						$(tdElement).css("background-color", "#C4E3F3");

					}
					if (!$funcionUtil.validarCantidadMaxCaracteres(cellData, 12) && !$funcionUtil.validarFormatoNumerico(cellData)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-Doc. Mandatorio 1 no contiene un valor v\u00E1lido. (Editar registro)</br>";
						$(tdElement).css("background-color", "#EBCCCC");
					}
				}
			}, {
				"targets": 18,
				"className": "all filtrable",
				"createdCell": function(tdElement, cellData, rowData, rowNum, colNum) {
					if ($funcionUtil.validarValorNulo(cellData)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-Celular Mandatorio 1 es obligatorio. (Editar registro)</br>";
						$(tdElement).css("background-color", "#C4E3F3");

					}
					if (!$funcionUtil.validarCantidadMaxCaracteres(cellData, 10) && !$funcionUtil.validarFormatoNumerico(cellData)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-Celular Mandatorio 1 no contiene un valor v\u00E1lido. (Editar registro)</br>";
						$(tdElement).css("background-color", "#EBCCCC");
					}
				}
			}, {
				"targets": 19,
				"className": "all filtrable",
				"createdCell": function(tdElement, cellData, rowData, rowNum, colNum) {
					if ($funcionUtil.validarValorNulo(cellData)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-Nombre Mandatorio 2 es obligatorio. (Editar registro)</br>";
						$(tdElement).css("background-color", "#C4E3F3");

					}
					if (!$funcionUtil.validarCantidadMaxCaracteres(cellData, 40)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-Nombre Mandatorio 2 no contiene un valor v\u00E1lido. (Editar registro)</br>";
						$(tdElement).css("background-color", "#EBCCCC");
					}
				}
			}, {
				"targets": 20,
				"className": "all filtrable",
				"createdCell": function(tdElement, cellData, rowData, rowNum, colNum) {
					if ($funcionUtil.validarValorNulo(rowData.tipoManda2)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-T. Doc. Mandatorio 2 es obligatorio. (Editar registro)</br>";
						$(tdElement).css("background-color", "#C4E3F3");
					}
					if (!$funcionUtil.validarValorEnArreglo(rowData.tipoManda2, $local.$listaDocumentos)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-T. Doc. Mandatorio 2 no contiene un valor v\u00E1lido. (Editar registro)</br>";
						$(tdElement).css("background-color", "#EBCCCC");
					}
				}
			}, {
				"targets": 21,
				"className": "all filtrable",
				"createdCell": function(tdElement, cellData, rowData, rowNum, colNum) {
					if ($funcionUtil.validarValorNulo(cellData)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-Doc. Mandatorio 2 es obligatorio. (Editar registro)</br>";
						$(tdElement).css("background-color", "#C4E3F3");

					}
					if (!$funcionUtil.validarCantidadMaxCaracteres(cellData, 12) && !$funcionUtil.validarFormatoNumerico(cellData)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-Doc. Mandatorio 2 no contiene un valor v\u00E1lido. (Editar registro)</br>";
						$(tdElement).css("background-color", "#EBCCCC");
					}
				}
			}, {
				"targets": 22,
				"className": "all filtrable",
				"createdCell": function(tdElement, cellData, rowData, rowNum, colNum) {
					if ($funcionUtil.validarValorNulo(cellData)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-Celular Mandatorio 2 es obligatorio. (Editar registro)</br>";
						$(tdElement).css("background-color", "#C4E3F3");

					}
					if (!$funcionUtil.validarCantidadMaxCaracteres(cellData, 10) && !$funcionUtil.validarFormatoNumerico(cellData)) {
						rowData.exitoRegistro = 0;
						rowData.mensajeExcepcion = $funcionUtil.devolverCadenaVaciaPorNull(rowData.mensajeExcepcion) + "-Celular Mandatorio 2 no contiene un valor v\u00E1lido. (Editar registro)</br>";
						$(tdElement).css("background-color", "#EBCCCC");
					}
				}
			}, {
				"targets": 23,
				"className": "all seleccionable data-no-definida dt-center",
				"render": function(data, type, row) {
					return $funcionUtil.insertarEtiquetaSiNo(row.exitoRegistro);
				}
			}, {
				"targets": [24],
				"className": "all filtrable"
			}, {
				"targets": 25,
				"className": "all dt-center",
				"render": function(data, type, row) {
					if (row != null) {
						if (row.operacion == 'NO SE PUEDE AFILIAR') {
							return '';
						}
						return $variableUtil.botonActualizarAfiliacion;
					}
					return '';
				}
			}
		],
		"order": [
			[0, 'asc'],
			[1, 'asc']
		],
		"columns": [
			{
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.tipoDocumento, row.descTipoDocumento);
				},
				"title": "Tipo Documento" // 0
			}, {
				"data": function(row) {
					return row.numeroDocumento;
				},
				"title": "N\u00B0 Documento"// 1
			}, {
				"data": function(row) {
					return row.nombre;
				},
				"title": "Nombres" // 2
			}, {
				"data": function(row) {
					return row.apellidoPaterno;
				},
				"title": "Ap Paterno" // 3
			}, {
				"data": function(row) {
					return row.apellidoMaterno;
				},
				"title": "Ap Materno" // 4
			}, {
				"data": function(row) {
					return row.nombreEmbossing;
				},
				"title": "Embossing" //5
			}, {
				"data": function(row) {
					return row.ruc;
				},
				"title": "RUC" // 6
			}, {
				"data": function(row) {
					return row.nombreCliente;
				},
				"title": 'Cliente' // 7
			}, {
				"data": function(row) {
					return row.fechaVencimiento;
				},
				"title": 'Fecha Vencimiento' // 8
			}, {
				"data": function(row) {
					return row.direccion;
				},
				"title": 'Direcci\u00F3n' // 9
			}, {
				"data": function(row) {
					return row.telefonoMovil;
				},
				"title": 'Celular' // 10
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.sexo, row.descSexo);
				},
				"title": 'Sexo' // 11
			}, {
				"data": function(row) {
					return row.fechaNacimiento;
				},
				"title": 'Fecha Nacimiento' // 12
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.indicadorDistribucion, row.descDistribucion);
				},
				"title": 'Ind. Distribuci\u00F3n' // 13
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.nacionalidad, row.descNacionalidad);
				},
				"title": 'Nacionalidad' // 14
			}, {
				"data": function(row) {
					return row.nombreManda1;
				},
				"title": 'Nombre Mandatorio 1' // 15
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.tipoManda1, row.descTipoManda1);
				},
				"title": 'T. Doc. Mandatorio 1' // 16
			}, {
				"data": function(row) {
					return row.numManda1;
				},
				"title": 'Doc. Mandatorio 1' // 17
			}, {
				"data": function(row) {
					return row.fonoMnada1;
				},
				"title": 'Celular Mandatorio 1' // 18
			}, {
				"data": function(row) {
					return row.nombreManda2;
				},
				"title": 'Nombre Mandatorio 2'// 19
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.tipoManda2, row.descTipoManda2);
				},
				"title": 'T. Doc. Mandatorio 2' // 20
			}, {
				"data": function(row) {
					return row.numManda2;
				},
				"title": 'Doc. Mandatorio 2' // 21
			}, {
				"data": function(row) {
					return row.fonoMnada2;
				},
				"title": 'Celular Mandatorio 2' // 22
			}, {
				"data": "exitoRegistro",// 23
				"title": '\u00C9xito'
			}, {
				"data": "mensajeExcepcion",// 24
				"title": 'Mensaje error'
			}, {
				"data": null, //25
				"title": 'Acci\u00F3n'
			}
		],
	});

	$local.$tablaRequerimientoNominadas.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaRequerimientoNominadas.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaRequerimientoNominadas.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaRequerimientoNominadas.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	$local.$btnRegistrarNominadas.on('click', function() {
		if ($local.$archivosRequerimiento.length == 0) {
			$funcionUtil.notificarException("No se ha adjuntado ning\u00FAn archivo.", "fa-warning", "Aviso", "warning");
			return;
		}
		if (!$formRegistroRequerimiento.valid()) {
			$funcionUtil.notificarException("El formulario no esta lleno.", "fa-warning", "Aviso", "warning");
			return;
		}
		if (!$funcionUtil.validarLote($local.$tablaRequerimientoNominadas.dataTable().fnGetData())) {
			$funcionUtil.notificarException("La tabla contiene registros no v\u00E1lidos.", "fa-warning", "Aviso", "warning");
			return;
		}
		//Obtener lotes
		var data = $local.$tablaRequerimientoNominadas.dataTable().fnGetData();
		var lotes = {
			afiliaciones: $funcionUtil.obtenerLote(data, 'operacion', 'AFILIACION'),
			formulario: $formRegistroRequerimiento.serializeJSON()
		}
		lotes.formulario.registros = lotes.afiliaciones.length;
		if (lotes.afiliaciones.length == 0) {
			$funcionUtil.notificarException("No se puede generar lotes sin registros.", "fa-warning", "Aviso", "warning");
			return;
		}
		$.confirm({
			icon: "fa fa-info-circle",
			title: "Aviso",
			content: "Confirma la generaci\u00F3n de los siguientes lotes: </br><b>Lote de afiliaci\u00F3n con: </b> " + lotes.afiliaciones.length + " registros.",
			buttons: {
				Aceptar: {
					action: function() {
						var confirmar = $.confirm({
							icon: 'fa fa-spinner fa-pulse fa-fw',
							title: "Generando lotes...",
							theme: "bootstrap",
							content: function() {
								var self = this;
								self.buttons.close.hide();
								$.ajax({
									type: "POST",
									url: $variableUtil.root + "afiliaciones/nominadas",
									data: JSON.stringify(lotes),
									beforeSend: function(xhr) {
										$local.$btnRegistrarNominadas.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
										xhr.setRequestHeader('Content-Type', 'application/json');
										xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
									},
									error: function(response) {
										$funcionUtil.notificarException("Error al registrar afiliaciones nominadas", "fa-warning", "Error de carga", "warning");
									},
									success: function(response) {
										$funcionUtil.notificarException(response, "fa-check", "Aviso", "success");
										removerArchivoAfiliaciones();
									},
									complete: function(response) {
										$local.$btnRegistrarNominadas.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
										confirmar.close();
									}
								});
							},
							buttons: {
								close: {
									text: 'Aceptar'
								}
							}
						});
					},
					keys: ['enter'],
					btnClass: "btn-primary"
				},
				Cancelar: {
					keys: ['esc']
				},
			}
		});
	});

	function cargarArchivoAfiliacionesEnServerParaValidar(archivo) {
		var data = new FormData();
		data.append("archivos", archivo);
		$.ajax({
			type: "POST",
			url: $variableUtil.root + "afiliaciones/nominadas/validar",
			data: data,
			contentType: false,
			processData: false,
			beforeSend: function(xhr) {
				$local.$cargandoArchivoNominadas.addClass("fa fa-spinner fa-pulse fa-fw");
				$local.$cargandoArchivoNominadasTexto.text('Cargando...');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
				$local.$btnRegistrarNominadas.attr("disabled", true);
				$local.$myDropzone.find(".dz-remove").addClass("hidden");
			},
			error: function(response) {
				$funcionUtil.notificarException(response.responseJSON.motivo, "fa-warning", "Error de carga", "warning");
			},
			success: function(data) {
				$local.tablaRequerimientoNominadas.rows.add(data).draw();
			},
			complete: function(response) {
				$local.$cargandoArchivoNominadas.removeClass("fa fa-spinner fa-pulse fa-fw");
				$local.$cargandoArchivoNominadasTexto.text('');
				$local.$btnRegistrarNominadas.attr("disabled", false);
				$local.$myDropzone.find(".dz-remove").removeClass("hidden");
			}
		});
	}

	$local.$tablaRequerimientoNominadas.children("tbody").on("click", ".actualizar", function() {
		$local.$filaSeleccionada = $(this).parents("tr");
		var filaEdicion = $local.tablaRequerimientoNominadas.row($local.$filaSeleccionada).data();
		var afiliacion = {};
		afiliacion.tipoDocumento = filaEdicion.tipoDocumento;
		afiliacion.numeroDocumento = filaEdicion.numeroDocumento;
		afiliacion.nombre = filaEdicion.nombre;
		afiliacion.apellidoPaterno = filaEdicion.apellidoPaterno;
		afiliacion.apellidoMaterno = filaEdicion.apellidoMaterno;
		afiliacion.nombreEmbossing = filaEdicion.nombreEmbossing;
		afiliacion.nombreCliente = filaEdicion.nombreCliente;
		afiliacion.fechaVencimiento = filaEdicion.fechaVencimiento;
		afiliacion.correo = filaEdicion.correo;
		afiliacion.ruc = filaEdicion.ruc

		$local.valorDescripcionNacionalidad = filaEdicion.descNacionalidad
		/*$local.valorRucSeleccionado = filaEdicion.ruc
		$local.valorNombreManda1 = filaEdicion.nombreManda1
		$local.valorNumManda1 = filaEdicion.numManda1
		$local.valoraFonoMnada1 = filaEdicion.fonoMnada1;
		$local.valorNombreManda2 = filaEdicion.nombreManda2;
		$local.valorNumManda2 = filaEdicion.numManda2;
		$local.valorFonoMnada2 = filaEdicion.fonoMnada2*/

		afiliacion.direccion = filaEdicion.direccion;
		afiliacion.telefonoMovil = filaEdicion.telefonoMovil;
		afiliacion.sexo = filaEdicion.sexo;
		afiliacion.fechaNacimiento = filaEdicion.fechaNacimiento;
		afiliacion.nacionalidad = filaEdicion.nacionalidad;
		afiliacion.operacion = filaEdicion.operacion;
		$local.$btnActualizarAfiliacion.removeClass('hidden');
		$local.$selectMonedaAfiliacion.removeAttr("disabled");
		$local.$selectTipoDocumentoAfiliacion.attr('disabled', true);
		$local.$txtNumeroDocumentoAfiliacion.attr('disabled', true);
		$funcionUtil.prepararFormularioActualizacion($formModalAfiliacion);
		$funcionUtil.llenarFormulario(filaEdicion, $formModalAfiliacion);
		$local.$modalAfiliacion.PopupWindow("open");
	});

	$local.$btnActualizarAfiliacion.on('click', function() {
		if (!$formModalAfiliacion.valid()) {
			return;
		}
		$local.$selectTipoDocumentoAfiliacion.removeAttr('disabled');
		$local.$txtNumeroDocumentoAfiliacion.removeAttr('disabled');
		var afiliacion = $formModalAfiliacion.serializeJSON();
		afiliacion.tipoDocumento = $local.$selectTipoDocumentoAfiliacion.val();
		afiliacion.numeroDocumento = $local.$txtNumeroDocumentoAfiliacion.val();
		afiliacion.operacion = $local.$inputOperacion.val();

		const descripcionDocumento = $local.$selectTipoDocumentoAfiliacion.find("option:selected").text();
		const descripcionSexo = $local.$selectSexo.find("option:selected").text();
		const descripcionIndDistrubucion = $local.$selectTipoIndDistribucion.find("option:selected").text();
		const descripcionNacionalidad = $local.$selectNacionalidad.find("option:selected").text();

		const documentoMandatorio1 = $local.$selectTipoDocumentoAfiliacionMandatorio1.find("option:selected").text();
		const documentoMandatorio2 = $local.$selectTipoDocumentoAfiliacionMandatorio2.find("option:selected").text();


		afiliacion.descNacionalidad = descripcionNacionalidad.split(' - ')[1]
		afiliacion.descTipoDocumento = descripcionDocumento.split(' - ')[1]
		afiliacion.descSexo = descripcionSexo.split(' - ')[1]
		afiliacion.descDistribucion = descripcionIndDistrubucion.split(' - ')[1]

		afiliacion.descTipoManda1 = documentoMandatorio1.split(' - ')[1]
		afiliacion.descTipoManda2 = documentoMandatorio2.split(' - ')[1]
		/*afiliacion.ruc = $local.valorRucSeleccionado
		afiliacion.nombreManda1 = $local.valorNombreManda1
		afiliacion.numManda1 = $local.valorNumManda1;
		afiliacion.fonoMnada1 = $local.valoraFonoMnada1;
		afiliacion.nombreManda2 = $local.valorNombreManda2
		afiliacion.numManda2 = $local.valorNumManda2
		afiliacion.fonoMnada2 + $local.valorFonoMnada2*/

		afiliacion.exitoRegistro = 1; //por defecto para que la grilla valide
		afiliacion.mensajeExcepcion = ""; //por defecto para que la grilla valide
		$local.tablaRequerimientoNominadas.row($local.$filaSeleccionada).remove().draw(false);
		var row = $local.tablaRequerimientoNominadas.row.add(afiliacion).draw();
		row.show().draw(false);
		$(row.node()).animateHighlight();
		$local.$modalAfiliacion.PopupWindow("close");
	});

	function buscarProductoValidacion(empresaSeleccionada, clienteSeleccionada, logoSeleccionada) {
		if (
			(empresaSeleccionada == null || empresaSeleccionada == undefined || empresaSeleccionada == '-1') ||
			(clienteSeleccionada == null || clienteSeleccionada == undefined || clienteSeleccionada == '-1') ||
			(logoSeleccionada == null || logoSeleccionada == undefined || logoSeleccionada == '-1')
		) {
			return;
		}
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "producto/empresa/" + empresaSeleccionada + "/cliente/" + clienteSeleccionada + "/logo/" + logoSeleccionada,
			beforeSend: function(xhr) {
				$local.$selectProducto.find("option:not(:eq(0))").remove();
				$local.$selectProducto.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Productos del Logo: " + logoSeleccionada + " </span>")
			},
			success: function(response) {
				$.each(response, function(i, sector) {
					$local.$selectProducto.append($("<option/>").val(this.codigoProducto).text(this.codigoProducto + " - " + this.descProducto));
				});
			},
			complete: function() {
				$local.$selectProducto.parent().find(".cargando").remove();
			}
		});
	}

	function buscarAfinidadValidacion(empresaSeleccionada, clienteSeleccionada, logoSeleccionada) {
		if (
			(empresaSeleccionada == null || empresaSeleccionada == undefined || empresaSeleccionada == '-1') ||
			(clienteSeleccionada == null || clienteSeleccionada == undefined || clienteSeleccionada == '-1') ||
			(logoSeleccionada == null || logoSeleccionada == undefined || logoSeleccionada == '-1')
		) {
			return;
		}
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "cliente/" + clienteSeleccionada + "/empresa/" + empresaSeleccionada + "/logo/" + logoSeleccionada,
			beforeSend: function(xhr) {
				$local.$selectAfinidad.find("option:not(:eq(0))").remove();
				$local.$selectAfinidad.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando afinidades del Logo: " + logoSeleccionada + " </span>")
			},
			success: function(response) {
				$.each(response, function(i, sector) {
					$local.$selectAfinidad.append($("<option/>").val(this.codigoAfinidad).text(this.codigoAfinidad + " - " + this.descripcionAfinidad));
				});
			},
			complete: function() {
				$local.$selectAfinidad.parent().find(".cargando").remove();
			}
		});
	}

	$local.$selectEmpresa.on("change", function(event, opcionSeleccionada) {
		var empresaSeleccionada = $local.$selectEmpresa.val();
		var clienteSeleccionada = $local.$selectCliente.val();
		var logoSeleccionada = $local.$selectLogo.val();
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "cliente/empresa/" + empresaSeleccionada,
			beforeSend: function(xhr) {
				$local.$selectCliente.find("option:not(:eq(0))").remove();
				$local.$selectCliente.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando clientes</span>")
			},
			success: function(response) {
				$.each(response, function(i, sector) {
					$local.$selectCliente.append($("<option/>").val(this.idCliente).text(this.idCliente + " - " + this.descripcion));
				});
			},
			complete: function() {
				$local.$selectCliente.parent().find(".cargando").remove();
			}
		});
		buscarProductoValidacion(empresaSeleccionada, clienteSeleccionada, logoSeleccionada);
		buscarAfinidadValidacion(empresaSeleccionada, clienteSeleccionada, logoSeleccionada);
	});

	$local.$selectCliente.on("change", function(event, opcionSeleccionada) {
		var empresaSeleccionada = $local.$selectEmpresa.val();
		var clienteSeleccionada = $local.$selectCliente.val();
		var logoSeleccionada = $local.$selectLogo.val();
		buscarProductoValidacion(empresaSeleccionada, clienteSeleccionada, logoSeleccionada);
		buscarAfinidadValidacion(empresaSeleccionada, clienteSeleccionada, logoSeleccionada);
	});

	$local.$selectLogo.on("change", function(event, opcionSeleccionada) {
		var empresaSeleccionada = $local.$selectEmpresa.val();
		var clienteSeleccionada = $local.$selectCliente.val();
		var logoSeleccionada = $local.$selectLogo.val();
		buscarProductoValidacion(empresaSeleccionada, clienteSeleccionada, logoSeleccionada);
		buscarAfinidadValidacion(empresaSeleccionada, clienteSeleccionada, logoSeleccionada);
	});

	$local.$btnExportarPlantillaNominadas.on("click", function() {
		window.location.href = $variableUtil.root + "plantilla/lote/nominada?accion=exportar";
	});

	$local.$btnRegistrarInnominadas.on("click", function() {
		if (!$formRegistroRequerimiento.valid()) {
			return;
		}
		var lote = $formRegistroRequerimiento.serializeJSON();
		$.ajax({
			type: "POST",
			url: $variableUtil.root + "afiliaciones/innominadas",
			data: JSON.stringify(lote),
			beforeSend: function(xhr) {
				$local.$btnRegistrarInnominadas.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			error: function(response) {
				$funcionUtil.notificarException(response, "fa-warning", "Aviso", "warning");
			},
			success: function(response) {
				$funcionUtil.notificarException(response, "fa-check", "Aviso", "success");
			},
			complete: function() {
				$local.$btnRegistrarInnominadas.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.permisoActualizacion = $local.$tablaConsultaLote.attr("data-permiso-actualizacion") || false;
	$local.permisoEliminacion = $local.$tablaConsultaLote.attr("data-permiso-eliminacion") || false;

	function validarTexto(valor, textoCompleto) {
		let text = '';
		if (valor == null || valor == undefined) {
			return text;
		}
		return textoCompleto;
	}

	$local.tablaConsultaLote = $local.$tablaConsultaLote.DataTable({
		"initComplete": function() {
			$local.$tablaConsultaLote.wrap("<div class='table-responsive'></div>");
			$local.filtrosSeleccionablesConsulta["11"] = $local.arregloSiNo;
			$local.filtrosSeleccionablesConsulta["16"] = $local.arregloSiNo;
			$local.filtrosSeleccionablesConsulta["18"] = $local.arregloSiNo;
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaConsultaLote, $local.filtrosSeleccionablesConsulta);
		},
		"language": {
			"emptyTable": "No se encontraron lotes registrados"
		},
		"columnDefs": [{
			"targets": [1, 2, 3, 4, 5, 6, 7, 8, 10, 12, 13, 14, 20, 21],
			"className": "all filtrable",
		}, {
			"targets": [0, 9, 17, 19],
			"className": "all filtrable dt-center",
		}, {
			"targets": [15],
			"className": "all filtrable dt-right",
		}, {
			"targets": [11],
			"className": "all seleccionable data-no-definida dt-center",
			"render": function(data, type, row) {
				return $funcionUtil.insertarEtiquetaSiNo(row.indRecargado);
			}
		}, {
			"targets": [16],
			"className": "all seleccionable data-no-definida dt-center",
			"render": function(data, type, row) {
				return $funcionUtil.insertarEtiquetaSiNo(row.enviadoUBA);
			}
		}, {
			"targets": [18],
			"className": "all seleccionable data-no-definida dt-center",
			"render": function(data, type, row) {
				return $funcionUtil.insertarEtiquetaSiNo(row.recibioRespuesta);
			}
		}, {
			"targets": [22],
			"className": "all dt-center",
			"render": function(data, type, row) {
				var btnActualizar = $local.permisoActualizacion == "true" ? $variableUtil.botonActualizar : "";
				var btnEliminacion = $local.permisoEliminacion == "true" ? $variableUtil.botonEliminar : "";
				let boton = '';
				if (row.estadoLote == 'O' && row.tipoLote == 'AF') {
					boton = boton + btnActualizar;
				}
				if (row.enviadoUBA && row.tipoLote == 'AF') {
					boton = boton + " " + $variableUtil.botonVerDetalleAfiliaciones;
				}
				if ($funcionUtil.validarValorEnArreglo(row.tipoLote, ["R1", "R2", "D1", "D2"])) {
					boton = boton + " " + $variableUtil.botonVerDetalleRecargaDebito;
				}
				if (row.estadoLote == 'O') {
					boton = boton + " " + btnEliminacion;
				}
				return boton;
			}
		}],
		"order": [],
		"columns": [
			{
				"data": function(row) {
					return row.fechaProceso;
				},
				"title": "Fecha Proceso"
			}, {
				"data": function(row) {
					return validarTexto(row.idInstitucion, $funcionUtil.unirCodigoDescripcion(row.idInstitucion, row.descInstitucion));
				},
				"title": "Instituci\u00F3n"
			}, {
				"data": function(row) {
					return validarTexto(row.idEmpresa, $funcionUtil.unirCodigoDescripcion(row.idEmpresa, row.descEmpresa));
				},
				"title": "Empresa"
			}, {
				"data": function(row) {
					return validarTexto(row.idCliente, $funcionUtil.unirCodigoDescripcion(row.idCliente, row.descCliente));
				},
				"title": "Cliente"
			}, {
				"data": function(row) {
					return validarTexto(row.idLogo, $funcionUtil.unirCodigoDescripcion(row.idLogo, row.descLogoBin));
				},
				"title": "Logo"
			}, {
				"data": function(row) {
					return validarTexto(row.codigoProducto, $funcionUtil.unirCodigoDescripcion(row.codigoProducto, row.descProducto));
				},
				"title": "Producto"
			}, {
				"data": function(row) {
					return validarTexto(row.idAfinidad, $funcionUtil.unirCodigoDescripcion(row.idAfinidad, row.descAfinidad));
				},
				"title": "Afinidad"
			}, {
				"data": function(row) {
					return validarTexto(row.idCategoria, $funcionUtil.unirCodigoDescripcion(row.idCategoria, row.descCategoria));
				},
				"title": "Categor\u00EDa"
			}, {
				"data": function(row) {
					return row.idLote;
				},
				"title": "ID Lote"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.fechaRegistro, row.horaRegistro);
				},
				"title": "Fecha Registro"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.tipoLote, row.descTipoLote);
				},
				"title": "Tipo Lote"
			}, {
				"data": function(row) {
					return row.indRecargado;
				},
				"title": "Recargado"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.tipoAfiliacion, row.desctipoAfiliacion);
				},
				"title": "Tipo Afiliaci\u00F3n"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.tipoTarjetas, row.descTipoTarjetas);
				},
				"title": "Tipo Tarjetas"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.estadoLote, row.descEstadoLote);
				},
				"title": "Estado Lote"
			}, {
				"data": function(row) {
					return $funcionUtil.formatMoney(row.registros, 0);
				},
				"title": "Registros"
			}, {
				"data": function(row) {
					return row.enviadoUBA;
				},
				"title": "Enviado a UBA"
			}, {
				"data": function(row) {
					return row.fechaEmisionUBA;
				},
				"title": "Fecha Env. UBA"
			}, {
				"data": function(row) {
					return row.enviadoUBA;
				},
				"title": "Respondi\u00F3 UBA"
			}, {
				"data": function(row) {
					return row.fechaRespuesta;
				},
				"title": "Fecha Res. UBA"
			}, {
				"data": function(row) {
					return row.idLotePadre;
				},
				"title": "ID Lote Padre"
			}, {
				"data": function(row) {
					return row.idLoteUBA;
				},
				"title": "ID Lote UBA"
			}, {
				"data": null,
				"title": 'Acci\u00F3n' //21
			}
		]
	});

	$local.$tablaConsultaLote.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaConsultaLote.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaConsultaLote.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaConsultaLote.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	$local.$selectEmpresaConsulta.on("select2:unselect", function(event) {
		$local.$selectClienteConsulta.find("option").remove();
	});

	$local.$selectEmpresaConsulta.on("select2:select", function(event) {
		var opcionSeleccionada = event.params.data.id;
		if (opcionSeleccionada == '-1') {
			$local.$selectClienteConsulta.find("option").remove();
			return;
		}
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "cliente/empresa/" + opcionSeleccionada,
			beforeSend: function(xhr) {
				$local.$selectClienteConsulta.find("option").remove();
				$local.$selectClienteConsulta.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando clientes</span>")
			},
			success: function(response) {
				$.each(response, function(i, sector) {
					$local.$selectClienteConsulta.append($("<option/>").val(this.idCliente).text(this.idCliente + " - " + this.descripcion));
				});
			},
			complete: function() {
				$local.$selectClienteConsulta.parent().find(".cargando").remove();
			}
		});
	});

	$local.$selectLogoConsulta.on("select2:unselect", function(event) {
		$local.$selectProductoConsulta.find("option").remove();
	});

	$local.$selectLogoConsulta.on("select2:select", function(event) {
		var opcionSeleccionada = event.params.data.id;
		if (opcionSeleccionada == '-1') {
			$local.$selectProductoConsulta.find("option").remove();
			return;
		}
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "producto/logo/" + opcionSeleccionada,
			beforeSend: function(xhr) {
				$local.$selectProductoConsulta.find("option").remove();
				$local.$selectProductoConsulta.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Productos del Logo: " + opcionSeleccionada + " </span>")
			},
			success: function(response) {
				$.each(response, function(i, sector) {
					$local.$selectProductoConsulta.append($("<option/>").val(this.codigoProducto).text(this.codigoProducto + " - " + this.descProducto));
				});
			},
			complete: function() {
				$local.$selectProductoConsulta.parent().find(".cargando").remove();
			}
		});
	});

	function consultarLotesCriterios() {
		if (!$formConsultaLote.valid()) {
			return;
		}
		var criterioBusqueda = $formConsultaLote.serializeJSON();

		var rangFechaRegistro = $funcionUtil.obtenerFechasDateRangePicker($local.$fechaRegistro);
		criterioBusqueda.fechaRegistroInicio = rangFechaRegistro.fechaInicio;
		criterioBusqueda.fechaRegistroFin = rangFechaRegistro.fechaFin;
		var rangFechaProceso = $funcionUtil.obtenerFechasDateRangePicker($local.$fechaProceso);
		criterioBusqueda.fechaProcesoInicio = rangFechaProceso.fechaInicio;
		criterioBusqueda.fechaProcesoFin = rangFechaProceso.fechaFin;

		var paramCriterioBusqueda = $.param(criterioBusqueda);
		paramCriterioBusqueda = paramCriterioBusqueda + $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectClienteConsulta, "clientes");
		paramCriterioBusqueda = paramCriterioBusqueda + $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectProductoConsulta, "productos");

		$.ajax({
			type: "GET",
			url: $variableUtil.root + "consulta/lote?accion=buscarPorCriterios&" + paramCriterioBusqueda,
			contentType: "application/json",
			dataType: "json",
			beforeSend: function(xhr) {
				$local.$btnBuscarConsultaLote.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
				$local.tablaConsultaLote.clear().draw();
			},
			success: function(response) {
				if (response.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				$local.tablaConsultaLote.rows.add(response).draw();
			},
			complete: function() {
				$local.$btnBuscarConsultaLote.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	}

	$local.$btnBuscarConsultaLote.on("click", function() {
		consultarLotesCriterios();
	});

	$local.$btnExportarConsultaLote.on("click", function() {
		if (!$formConsultaLote.valid()) {
			return;
		}
		var criterioBusqueda = $formConsultaLote.serializeJSON();
		var rangFechaRegistro = $funcionUtil.obtenerFechasDateRangePicker($local.$fechaRegistro);
		criterioBusqueda.fechaRegistroInicio = rangFechaRegistro.fechaInicio;
		criterioBusqueda.fechaRegistroFin = rangFechaRegistro.fechaFin;
		var rangFechaProceso = $funcionUtil.obtenerFechasDateRangePicker($local.$fechaProceso);
		criterioBusqueda.fechaProcesoInicio = rangFechaProceso.fechaInicio;
		criterioBusqueda.fechaProcesoFin = rangFechaProceso.fechaFin;
		criterioBusqueda.descripcionRangoFechasProceso = $local.$fechaProceso.val();
		criterioBusqueda.descripcionRangoFechasRegistro = $local.$fechaRegistro.val();
		criterioBusqueda.descripcionCliente = !!$local.$selectClienteConsulta.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$selectClienteConsulta, "; ") : "TODOS";
		criterioBusqueda.descripcionProducto = !!$local.$selectProductoConsulta.val() ? $funcionUtil.obtenerTextoSelecMultiple($local.$selectProductoConsulta, "; ") : "TODOS";

		criterioBusqueda.descripcionTipoTarjeta = $local.$selectTipoTarjetasConsulta.find("option:selected").val() === "-1" ? "" : $local.$selectTipoTarjetasConsulta.find("option:selected").text();
		criterioBusqueda.descripcionEmpresa = $local.$selectEmpresaConsulta.find("option:selected").val() === "-1" ? "" : $local.$selectEmpresaConsulta.find("option:selected").text();
		criterioBusqueda.descripcionLogo = $local.$selectLogoConsulta.find("option:selected").val() === "-1" ? "" : $local.$selectLogoConsulta.find("option:selected").text();
		criterioBusqueda.descripcionInstitucion = $local.$selectInstitucionConsulta.find("option:selected").val() === "-1" ? "" : $local.$selectInstitucionConsulta.find("option:selected").text();

		var paramCriterioBusqueda = $.param(criterioBusqueda);
		paramCriterioBusqueda = paramCriterioBusqueda + $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectClienteConsulta, "clientes");
		paramCriterioBusqueda = paramCriterioBusqueda + $funcionUtil.crearCadenaPeticionGetDeSelect2Multiple($local.$selectProductoConsulta, "productos");
		$funcionUtil.webSocketMethod('exportacionPOI');
		window.location.href = `${$variableUtil.root}consulta/lote/exportacion?rutaEnSidebar=${$variableUtil.rutaEnSidebar}&accion=exportarLote&${paramCriterioBusqueda}`;
	});

	$local.$btnExportarConsultaLoteDetalle.on("click", function() {
		if (!$formConsultaLote.valid()) {
			return;
		}
		var criterioBusqueda = {};
		var lote = $local.tablaConsultaLote.row($local.$filaSeleccionadaConsulta).data();
		criterioBusqueda.idLote = lote.idLote;
		var paramCriterioBusqueda = $.param(criterioBusqueda);
		window.location.href = $variableUtil.root + "consulta/lote/exportacion?accion=exportarLoteDetalle&" + paramCriterioBusqueda;
	});

	$local.$tablaConsultaLote.children("tbody").on("click", ".eliminar", function() {
		$local.$filaSeleccionada = $(this).parents("tr");
		var lote = $local.tablaConsultaLote.row($local.$filaSeleccionada).data();
		$.confirm({
			icon: "fa fa-info-circle",
			title: "Aviso",
			content: "\u00BFDesea eliminar el lote <b>'" + lote.idLote + "'</b> que contiene <b>'" + lote.registros + "'</b> registros?",
			buttons: {
				Aceptar: {
					action: function() {
						var confirmar = $.confirm({
							icon: 'fa fa-spinner fa-pulse fa-fw',
							title: "Eliminando...",
							theme: "bootstrap",
							content: function() {
								var self = this;
								self.buttons.close.hide();
								$.ajax({
									type: "DELETE",
									url: $variableUtil.root + "lote/" + lote.idLote,
									data: JSON.stringify(lote),
									autoclose: true,
									beforeSend: function(xhr) {
										xhr.setRequestHeader('Content-Type', 'application/json');
										xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
									}
								}).done(function(response) {
									$funcionUtil.notificarException("Lote <b>" + lote.idLote + "</b> eliminado.", "fa-check", "Aviso", "success");
									//$local.tablaConsultaLote.row($local.$filaSeleccionada).remove().draw(false);
									consultarLotesCriterios();
									confirmar.close();
								}).fail(function(xhr) {
									confirmar.close();
									switch (xhr.status) {
										case 400:
											$funcionUtil.notificarException($funcionUtil.obtenerMensajeErrorEnCadena(xhr.responseJSON), "fa-warning", "Aviso", "warning");
											break;
										case 409:
											var mensaje = $funcionUtil.obtenerMensajeError("El Lote <b>" + lote.idLote + "no se pudo eliminar</b>", xhr.responseJSON, $variableUtil.accionEliminado);
											$funcionUtil.notificarException(mensaje, "fa-warning", "Aviso", "warning");
											break;
									}
								});
							},
							buttons: {
								close: {
									text: 'Aceptar'
								}
							}
						});
					},
					keys: ['enter'],
					btnClass: "btn-primary"
				},
				Cancelar: {
					keys: ['esc']
				},
			}
		});
	});

	$local.tablaConsultaLoteDetalleAfiliacion = $local.$tablaConsultaLoteDetalleAfiliacion.DataTable({
		"initComplete": function() {
			$local.$tablaConsultaLoteDetalleAfiliacion.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaConsultaLoteDetalleAfiliacion, $local.filtrosSeleccionablesConsultaDetalleAfiliacion);
		},
		"language": {
			"emptyTable": "No se encontraron registros para este lote"
		},
		"columnDefs": [
			{
				"targets": [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 13, 14, 15, 16, 17, 18, 19, 21, 22, 23, 24, 25, 26, 27, 28, 29, 36, 37, 38, 39, 40, 41, 42, 43, 44],
				"className": "all filtrable",
			}, {
				"targets": [10, 20, 31, 32, 33, 34, 35],
				"className": "all filtrable dt-center",
			}, {
				"targets": [30],
				"className": "all filtrable dt-right",
			}
		],
		"columns": [
			{
				"data": function(row) {
					return row.idDetalle;
				},
				"title": "ID Detalle"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.tipoMensaje, row.descTipoMensaje);
				},
				"title": "Tipo Mensaje"
			}, {
				"data": function(row) {
					return row.codigoSeguimiento;
				},
				"title": "C\u00F3digo Seguimiento"
			}, {
				"data": function(row) {
					return row.numeroTarjeta;
				},
				"title": "N\u00FAmero Tarjeta"
			}, {
				"data": function(row) {
					return row.idPersona;
				},
				"title": "Persona"
			}, {
				"data": function(row) {
					return row.numeroTrace;
				},
				"title": "N\u00FAmero Trace"
			}, {
				"data": function(row) {
					return row.binAdquirente;
				},
				"title": "BIN Adquirente"
			}, {
				"data": function(row) {
					return row.binEmisor;
				},
				"title": "BIN Emisor"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.codigoRespuesta, row.descRespuesta);
				},
				"title": "C\u00F3digo Respuesta"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.messageType, row.descMessageType);
				},
				"title": "Message Type"
			}, {
				"data": function(row) {
					return row.fechaAfiliacion;
				},
				"title": "Fecha Afiliaci\u00F3n"
			}, {
				"data": function(row) {
					return row.nombres;
				},
				"title": "Nombres"
			}, {
				"data": function(row) {
					return row.apellidoPaterno;
				},
				"title": "Ap. Paterno"
			}, {
				"data": function(row) {
					return row.apellidoMaterno;
				},
				"title": "Ap. Materno"
			}, {
				"data": function(row) {
					return row.titulo;
				},
				"title": "Titulo"
			}, {
				"data": function(row) {
					return row.direccion;
				},
				"title": "Direcci\u00F3n"
			}, {
				"data": function(row) {
					return row.direccion2;
				},
				"title": "Direcci\u00F3n 2"
			}, {
				"data": function(row) {
					return row.direccion2;
				},
				"title": "Direcci\u00F3n 2"
			}, {
				"data": function(row) {
					return row.telefono;
				},
				"title": "Telefono"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.sexo, row.descSexo);
				},
				"title": "G\u00E9nero"
			}, {
				"data": function(row) {
					return row.fechaNacimiento;
				},
				"title": "Fecha Nacimiento"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.estadoCivil, row.descEstadoCivil);
				},
				"title": "Estado Civil"
			}, {
				"data": function(row) {
					return row.codigoPlazaAfiliacion;
				},
				"title": "Plaza Afiliaci\u00F3n"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.tipoDocumento, row.descTipoDocumento);
				},
				"title": "Tipo Documento"
			}, {
				"data": function(row) {
					return row.numeroDocumento;
				},
				"title": "N\u00FAmero Documento"
			}, {
				"data": function(row) {
					return row.rucEmpresa;
				},
				"title": "RUC Empresa"
			}, {
				"data": function(row) {
					return row.nombreEmpresa;
				},
				"title": "Nombre Empresa"
			}, {
				"data": function(row) {
					return row.nombreCortoThb;
				},
				"title": "N\u00FAmero Corto Thb."
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.estadoTarjeta, row.descEstadoTarjeta);
				},
				"title": "Estado Tarjeta"
			}, {
				"data": function(row) {
					return row.statusTarjeta;
				},
				"title": "Status Tarjeta"
			}, {
				"data": function(row) {
					return $funcionUtil.formatMoney(row.contadorPinErrado, 0);
				},
				"title": "Contador PIN Errado"
			}, {
				"data": function(row) {
					return row.fechaOrden;
				},
				"title": "Fecha Orden"
			}, {
				"data": function(row) {
					return row.fechaExpiracion;
				},
				"title": "Fecha Expiraci\u00F3n"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.fechaIngresoSw, row.horaIngreso);
				},
				"title": "Fecha Ingreso SW"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.fechaUltimActualizaAdmin, row.horaUltimActualizaAdmin);
				},
				"title": "Fecha Ult. Act. Admin."
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.fechaUltimActualizaFinan, row.horaUltimActualizaFinan);
				},
				"title": "Fecha Ult. Act. Finan."
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.indicadorDistribucion, row.descDistribucion);
				},
				"title": "Ind. Distribuci\u00F3n"
			}, {
				"data": function(row) {
					return row.nombreMandatorio1;
				},
				"title": "Nombre Mandatorio 1"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.tipoDocumento1, row.descTipoDocumento1);
				},
				"title": "Tipo Doc. 1"
			}, {
				"data": function(row) {
					return row.numeroDocumento1;
				},
				"title": "Nro. Doc. 1"
			}, {
				"data": function(row) {
					return row.telefonoMandatorio1;
				},
				"title": "Telefono Mandatorio 1"
			}, {
				"data": function(row) {
					return row.nombreMandatorio2;
				},
				"title": "Nombre Mandatorio 2"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.tipoDocumento2, row.descTipoDocumento2);
				},
				"title": "Tipo Doc. 2"
			}, {
				"data": function(row) {
					return row.numeroDocumento2;
				},
				"title": "Nro. Doc. 2"
			}, {
				"data": function(row) {
					return row.telefonoMandatorio2;
				},
				"title": "Telefono Mandatorio 2"
			}
		]
	});

	$local.$tablaConsultaLoteDetalleAfiliacion.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaConsultaLoteDetalleAfiliacion.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaConsultaLoteDetalleAfiliacion.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaConsultaLoteDetalleAfiliacion.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	function buscarAfinidadValidacionForm(empresaSeleccionada, clienteSeleccionada, logoSeleccionada) {
		if (
			(empresaSeleccionada == null || empresaSeleccionada == undefined || empresaSeleccionada == '-1') ||
			(clienteSeleccionada == null || clienteSeleccionada == undefined || clienteSeleccionada == '-1') ||
			(logoSeleccionada == null || logoSeleccionada == undefined || logoSeleccionada == '-1')
		) {
			return;
		}
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "cliente/" + clienteSeleccionada + "/empresa/" + empresaSeleccionada + "/logo/" + logoSeleccionada,
			beforeSend: function(xhr) {
				$local.$selectAfinidadUpdate.find("option:not(:eq(0))").remove();
				$local.$selectAfinidadUpdate.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando afinidades del Logo: " + logoSeleccionada + " </span>")
			},
			success: function(response) {
				$.each(response, function(i, sector) {
					$local.$selectAfinidadUpdate.append($("<option/>").val(this.codigoAfinidad).text(this.codigoAfinidad + " - " + this.descripcionAfinidad));
				});
			},
			complete: function() {
				$local.$selectAfinidadUpdate.parent().find(".cargando").remove();
			}
		});
	}

	$local.$tablaConsultaLote.children("tbody").on("click", ".actualizar", function() {
		$funcionUtil.prepararFormularioActualizacion($formActualizacionLote);
		$local.$filaSeleccionada = $(this).parents("tr");
		const lote = $local.tablaConsultaLote.row($local.$filaSeleccionada).data();
		$local.idLote = lote.idLote;
		buscarAfinidadValidacionForm(lote.idEmpresa, lote.idCliente, lote.idLogo);
		$funcionUtil.llenarFormulario(lote, $formActualizacionLote);
		$local.$formTipoLote.val(lote.tipoLote + ' - ' + lote.descTipoLote);
		$local.$formEmpresa.val(lote.idEmpresa + ' - ' + lote.descEmpresa);
		$local.$formCliente.val(lote.idCliente + ' - ' + lote.descCliente);
		$local.$formLogo.val(lote.idLogo + ' - ' + lote.descLogo + ' (' + lote.idBin + ')');
		$local.$actualizarLote.removeClass("hidden")
		$local.$modalMantenimiento.PopupWindow("open");
	});

	$local.$actualizarLote.on("click", function() {
		if (!$formActualizacionLote.valid()) {
			return;
		}
		const lote = $formActualizacionLote.serializeJSON();
		lote.idLote = $local.idLote;
		$.ajax({
			type: "PUT",
			url: $variableUtil.root + "lote",
			data: JSON.stringify(lote),
			beforeSend: function(xhr) {
				$local.$actualizarLote.attr("disabled", true).find("i").removeClass("fa-pencil-square").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode: {
				400: function(response) {
					$funcionUtil.limpiarMensajesDeError($formActualizacionLote);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formActualizacionLote);
				}
			},
			success: function(lotes) {
				$funcionUtil.notificarException($variableUtil.actualizacionExitosa, "fa-check", "Aviso", "success");
				consultarLotesCriterios();
				/*if ($local.$tablaConsultaLote.length > 0) {
					$local.tablaConsultaLote.row($local.$filaSeleccionada).remove().draw(false);
					let lote = lotes[0];
					let row = $local.tablaConsultaLote.row.add(lote).draw();
					row.show().draw(false);
					$(row.node()).animateHighlight();
				}*/
				$local.$modalMantenimiento.PopupWindow("close");
			},
			error: function(response) {
			},
			complete: function(response) {
				$local.$actualizarLote.attr("disabled", false).find("i").addClass("fa-pencil-square").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$tablaConsultaLote.children("tbody").on("click", ".ver-detalle", function() {
		$local.$filaSeleccionadaConsulta = $(this).parents("tr");
		var lote = $local.tablaConsultaLote.row($local.$filaSeleccionadaConsulta).data();
		var criterio = {};
		criterio.idLote = lote.idLote;
		var $botonVerDetalle = $(this);
		var loteDetalleModal = {};
		loteDetalleModal.idLote = lote.idLote;
		loteDetalleModal.tipoAfiliacion = $funcionUtil.unirCodigoDescripcion(lote.tipoAfiliacion, lote.desctipoAfiliacion);
		loteDetalleModal.registros = $funcionUtil.formatMoney(lote.registros, 0);
		loteDetalleModal.tipoTarjeta = $funcionUtil.unirCodigoDescripcion(lote.tipoTarjetas, lote.descTipoTarjetas);
		loteDetalleModal.logo = $funcionUtil.unirCodigoDescripcion(lote.idLogo, lote.descLogoBin);
		loteDetalleModal.codigoProducto = $funcionUtil.unirCodigoDescripcion(lote.codigoProducto, lote.descProducto);
		loteDetalleModal.afinidad = $funcionUtil.unirCodigoDescripcion(lote.idAfinidad, lote.descAfinidad);
		loteDetalleModal.empresa = $funcionUtil.unirCodigoDescripcion(lote.idEmpresa, lote.descEmpresa);
		loteDetalleModal.cliente = $funcionUtil.unirCodigoDescripcion(lote.idCliente, lote.descCliente);
		loteDetalleModal.estadoLote = $funcionUtil.unirCodigoDescripcion(lote.estadoLote, lote.descEstadoLote);
		loteDetalleModal.enviadoUBA = $funcionUtil.textoSiNo(lote.enviadoUBA);
		loteDetalleModal.fechaEmisionUBA = lote.fechaEmisionUBA;
		$funcionUtil.llenarFormulario(loteDetalleModal, $formDetalleLoteAfiliacion);
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "consulta/lote?accion=buscarDetalleAfiliacion",
			data: criterio,
			beforeSend: function(xhr) {
				$botonVerDetalle.attr("disabled", true).find("i").removeClass("fa-eye").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
				$local.tablaConsultaLoteDetalleAfiliacion.clear().draw();
			},
			success: function(detalle) {
				if (detalle.length == 0) {
					$funcionUtil.notificarException("El Lote <b> " + lote.idLote + "</b> no contiene detalle", "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				$local.tablaConsultaLoteDetalleAfiliacion.rows.add(detalle).draw();
				$local.$modalDetalleLoteAfiliacion.PopupWindow("open");
			},
			complete: function() {
				$botonVerDetalle.attr("disabled", false).find("i").addClass("fa-eye").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});




	$local.tablaConsultaLoteDetalleRecargaDebito = $local.$tablaConsultaLoteDetalleRecargaDebito.DataTable({
		"initComplete": function() {
			$local.$tablaConsultaLoteDetalleRecargaDebito.wrap("<div class='table-responsive'></div>");
			$local.filtrosSeleccionablesConsultaDetalleRecargaDebito["14"] = $local.arregloSiNo;
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaConsultaLoteDetalleRecargaDebito, $local.filtrosSeleccionablesConsultaDetalleRecargaDebito);
		},
		"language": {
			"emptyTable": "No se encontraron registros para este lote"
		},
		"columnDefs": [
			{
				"targets": [0, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 16],
				"className": "all filtrable",
			}, {
				"targets": [1, 2, 15],
				"className": "all filtrable dt-center",
			}, {
				"targets": [13, 17],
				"className": "all filtrable dt-right",
			}, {
				"targets": [14],
				"className": "all seleccionable data-no-definida dt-center",
				"render": function(data, type, row) {
					return $funcionUtil.insertarEtiquetaSiNo(row.recibioRespuesta);
				}
			}
		],
		"order": [],
		"columns": [
			{
				"data": function(row) {
					return row.idDetalle;
				},
				"title": "ID Detalle"
			}, {
				"data": function(row) {
					return row.fechaProceso;
				},
				"title": "Fecha Proceso"
			}, {
				"data": function(row) {
					return row.fechaTransaccion;
				},
				"title": "Fecha Transacci\u00F3n"
			}, {
				"data": function(row) {
					return row.codigoSeguimiento;
				},
				"title": "C\u00F3digo Seguimiento"
			}, {
				"data": function(row) {
					return row.numeroTarjeta;
				},
				"title": "N\u00FAmero Tarjeta"
			}, {
				"data": function(row) {
					return row.numeroTrace;
				},
				"title": "N\u00FAmero Trace"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.tipoDocumento, row.descTipoDocumento);
				},
				"title": "Tipo Documento"
			}, {
				"data": function(row) {
					return row.numeroDocumento;
				},
				"title": "N\u00FAmero Documento"
			}, {
				"data": function(row) {
					return row.nombres;
				},
				"title": "Nombres"
			}, {
				"data": function(row) {
					return row.apellidoPaterno;
				},
				"title": "Ap. Paterno"
			}, {
				"data": function(row) {
					return row.apellidoMaterno;
				},
				"title": "Ap. Materno"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.operacion, row.descOperacion);
				},
				"title": "Tipo Operaci\u00F3n"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.moneda, row.descMoneda);
				},
				"title": "Moneda"
			}, {
				"data": function(row) {
					return $funcionUtil.formatMoney(row.monto, 2);
				},
				"title": "Monto"
			}, {
				"data": function(row) {
					return row.recibioRespuesta;
				},
				"title": "Recibi\u00F3 Respuesta"
			}, {
				"data": function(row) {
					return row.fechaRespuesta;
				},
				"title": "Fecha Respuesta"
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.codigoRespuesta, row.descRespuesta);
				},
				"title": "C\u00F3digo Respuesta"
			}, {
				"data": function(row) {
					return $funcionUtil.formatMoney(row.montoRespuesta, 2);
				},
				"title": "Monto Respuesta"
			}
		]
	});

	$local.$tablaConsultaLoteDetalleRecargaDebito.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaConsultaLoteDetalleRecargaDebito.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaConsultaLoteDetalleRecargaDebito.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaConsultaLoteDetalleRecargaDebito.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	$local.$tablaConsultaLote.children("tbody").on("click", ".ver-detalle-rec-deb", function() {
		$local.$filaSeleccionadaConsulta = $(this).parents("tr");
		var lote = $local.tablaConsultaLote.row($local.$filaSeleccionadaConsulta).data();
		var criterio = {};
		criterio.idLote = lote.idLote;
		var $botonVerDetalle = $(this);
		var loteDetalleModal = {};
		loteDetalleModal.idLote = lote.idLote;
		loteDetalleModal.tipoOperacion = $funcionUtil.unirCodigoDescripcion(lote.tipoLote, lote.descTipoLote);
		loteDetalleModal.registros = $funcionUtil.formatMoney(lote.registros, 0);
		loteDetalleModal.tipoTarjeta = $funcionUtil.unirCodigoDescripcion(lote.tipoTarjetas, lote.descTipoTarjetas);
		loteDetalleModal.logo = $funcionUtil.unirCodigoDescripcion(lote.idLogo, lote.descLogoBin);
		loteDetalleModal.codigoProducto = $funcionUtil.unirCodigoDescripcion(lote.codigoProducto, lote.descProducto);
		loteDetalleModal.afinidad = $funcionUtil.unirCodigoDescripcion(lote.idAfinidad, lote.descAfinidad);
		loteDetalleModal.empresa = $funcionUtil.unirCodigoDescripcion(lote.idEmpresa, lote.descEmpresa);
		loteDetalleModal.cliente = $funcionUtil.unirCodigoDescripcion(lote.idCliente, lote.descCliente);
		loteDetalleModal.estadoLote = $funcionUtil.unirCodigoDescripcion(lote.estadoLote, lote.descEstadoLote);
		loteDetalleModal.enviadoUBA = $funcionUtil.textoSiNo(lote.enviadoUBA);
		loteDetalleModal.fechaEmisionUBA = lote.fechaEmisionUBA;
		$funcionUtil.llenarFormulario(loteDetalleModal, $formDetalleLoteRecargaDebito);
		$.ajax({
			type: "GET",
			url: $variableUtil.root + "consulta/lote?accion=buscarDetalleRecargaDebito",
			data: criterio,
			beforeSend: function(xhr) {
				$botonVerDetalle.attr("disabled", true).find("i").removeClass("fa-eye").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
				$local.tablaConsultaLoteDetalleRecargaDebito.clear().draw();
			},
			success: function(detalle) {
				if (detalle.length == 0) {
					$funcionUtil.notificarException("El Lote <b> " + lote.idLote + "</b> no contiene detalle", "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				$local.tablaConsultaLoteDetalleRecargaDebito.rows.add(detalle).draw();
				$local.$modalDetalleLoteRecargaDebito.PopupWindow("open");
			},
			complete: function() {
				$botonVerDetalle.attr("disabled", false).find("i").addClass("fa-eye").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	///Recargas y debitos

	$local.tablaRecargaDebito = $local.$tablaRecargaDebito.DataTable({
		"initComplete": function() {
			$local.$tablaRecargaDebito.wrap("<div class='table-responsive'></div>");
			$local.filtrosSeleccionablesConsulta["8"] = $local.arregloSiNo;
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaRecargaDebito, $local.filtrosSeleccionablesConsulta);
		},
		"language": {
			"emptyTable": "No se encontraron lotes registrados"
		},
		"columnDefs": [
			{
				"targets": [0, 1, 3, 4, 5, 10],
				"className": "all filtrable",
			}, {
				"targets": 2,
				"className": "all filtrable",
				"createdCell": function(tdElement, cellData, rowData, rowNum, colNum) {
					if ($funcionUtil.validarValorNulo(cellData)) {
						rowData.exitoRegistro = 0;
						rowData.comentario = $funcionUtil.devolverCadenaVaciaPorNull(rowData.comentario) + "-El C\u00F3digo Seguimiento es obligatorio.</br>";
						$(tdElement).css("background-color", "#C4E3F3");

					}
				}
			}, {
				"targets": 6,
				"className": "all filtrable",
				"createdCell": function(tdElement, cellData, rowData, rowNum, colNum) {
					if ($funcionUtil.validarValorNulo(rowData.operacion)) {
						rowData.exitoRegistro = 0;
						rowData.comentario = $funcionUtil.devolverCadenaVaciaPorNull(rowData.comentario) + "-La Operaci\u00F3n es obligatoria.</br>";
						$(tdElement).css("background-color", "#C4E3F3");

					}
					if (!$funcionUtil.validarValorEnArreglo(rowData.operacion, ["R", "D"])) {
						rowData.exitoRegistro = 0;
						$(tdElement).css("background-color", "#EBCCCC");
					}
				}
			}, {
				"targets": 7,
				"className": "all filtrable",
				"createdCell": function(tdElement, cellData, rowData, rowNum, colNum) {
					if ($funcionUtil.validarValorNulo(rowData.moneda)) {
						rowData.exitoRegistro = 0;
						rowData.comentario = $funcionUtil.devolverCadenaVaciaPorNull(rowData.comentario) + "-La Moneda es obligatoria.</br>";
						$(tdElement).css("background-color", "#C4E3F3");
					}
					if (!$funcionUtil.validarValorEnArreglo(rowData.moneda, [604, 840])) {
						rowData.exitoRegistro = 0;
						$(tdElement).css("background-color", "#EBCCCC");
					}
				}
			}, {
				"targets": 8,
				"className": "all filtrable dt-right",
				"createdCell": function(tdElement, cellData, rowData, rowNum, colNum) {
					if ($funcionUtil.validarValorNulo(cellData)) {
						rowData.exitoRegistro = 0;
						rowData.comentario = $funcionUtil.devolverCadenaVaciaPorNull(rowData.comentario) + "-Monto es obligatorio.</br>";
						$(tdElement).css("background-color", "#C4E3F3");

					}
					if (!$funcionUtil.validarFormatoMoneda(cellData)) {
						rowData.exitoRegistro = 0;
						rowData.comentario = $funcionUtil.devolverCadenaVaciaPorNull(rowData.comentario) + "-Monto no contiene un valor v\u00E1lido.</br>";
						$(tdElement).css("background-color", "#EBCCCC");
					}
				}
			}, {
				"targets": 9,
				"className": "all seleccionable data-no-definida dt-center",
				"render": function(data, type, row) {
					return $funcionUtil.insertarEtiquetaSiNo(row.exitoRegistro);
				},
			}
		],
		"order": [
			[0, 'asc'],
			[1, 'asc']
		],
		"columns": [
			{
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.tipoDocumento, row.descTipoDocumento);
				},
				"title": "Tipo Documento" //0
			}, {
				"data": function(row) {
					return row.numeroDocumento;
				},
				"title": "N\u00FAmero Documento" //1
			}, {
				"data": function(row) {
					return row.codigoSeguimiento;
				},
				"title": "C\u00F3digo Seguimiento" //0
			}, {
				"data": function(row) {
					return row.nombres;
				},
				"title": "Nombres" //2
			}, {
				"data": function(row) {
					return row.apellidoPaterno;
				},
				"title": "App Paterno" //3
			}, {
				"data": function(row) {
					return row.apellidoMaterno;
				},
				"title": "App Materno" //4
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.operacion, row.descOperacion);
				},
				"title": "Operaci\u00F3n" //5
			}, {
				"data": function(row) {
					return $funcionUtil.unirCodigoDescripcion(row.moneda, row.descMoneda);
				},
				"title": "Moneda" //6
			}, {
				"data": function(row) {
					return $funcionUtil.formatMoney(row.monto, 2);
				},
				"title": "Monto" //7
			}, {
				"data": null,
				"title": "\u00C9xito" //8
			}, {
				"data": function(row) {
					return row.comentario;
				},
				"title": "Comentario" //9
			}
		]
	});

	$local.$tablaRecargaDebito.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaRecargaDebito.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaRecargaDebito.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaRecargaDebito.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	$local.$btnExportarPlantillaRecargaDebito.on("click", function() {
		window.location.href = $variableUtil.root + "plantilla/lote/recargaDebito?accion=exportar";
	});

	var archivoRyD = null;

	function removerArchivoRecargasYDebitos() {
		archivoRyD.previewElement.remove();
		$funcionUtil.eliminarArchivoEnLista(archivoRyD.name, $local.$archivosRecargaDebito, "name");
		$local.tablaRecargaDebito.clear().draw();
	}

	var myDropzone2 = new Dropzone("div#myDropzone2", {
		url: "procesos/recargasDebitos",
		paramName: "archivos",
		autoProcessQueue: false,
		uploadMultiple: false,
		parallelUploads: 1,
		maxFiles: 1,
		acceptedFiles: '.xlsx',
		addRemoveLinks: true,
		accept: function(file) {
			archivoRyD = file;
			if ($local.$archivosRecargaDebito.length != 0) {
				file.previewElement.remove();
				$funcionUtil.notificarException("El archivo de recargas/debitos ya se encuentra adjuntado.", "fa-warning", "Aviso", "warning");
				return;
			}
			if ($funcionUtil.existeArchivoEnListaArchivos(file.name, $local.$archivosRecargaDebito, "name")) {
				file.previewElement.remove();
				$funcionUtil.notificarException("Archivo con el nombre <b> " + file.name + " </b> ya fue agregado.", "fa-warning", "Aviso", "warning");
				return;
			}
			$local.$archivosRecargaDebito.push(file);
			cargarArchivoRecargaDebitoEnServerParaValidar($local.$archivosRecargaDebito[0]);
		},
		removedfile: function(file) {
			archivoRyD = file;
			removerArchivoRecargasYDebitos();
		},
		dictInvalidFileType: "Tipo de archivo no v\u00E1lido",
		dictMaxFilesExceeded: function() {
			$funcionUtil.notificarException("Solo se permite subir {{maxFiles}} archivo.", "fa-warning", "Aviso", "warning");
		}
	});

	function cargarArchivoRecargaDebitoEnServerParaValidar(archivo) {
		var data = new FormData();
		data.append("archivos", archivo);
		$.ajax({
			type: "POST",
			url: $variableUtil.root + "recargaDebito/validar",
			data: data,
			contentType: false,
			processData: false,
			beforeSend: function(xhr) {
				$local.$cargandoArchivoRecargaDebito.addClass("fa fa-spinner fa-pulse fa-fw");
				$local.$cargandoArchivoRecargaDebitoTexto.text('Cargando...');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
				$local.$btnRecargaDebito.attr("disabled", true);
				$local.$myDropzone2.find(".dz-remove").addClass("hidden");
			},
			error: function(response) {
				$funcionUtil.notificarException(response.responseJSON.motivo, "fa-warning", "Error de carga", "warning");
			},
			success: function(data) {
				$local.tablaRecargaDebito.rows.add(data).draw();
			},
			complete: function(response) {
				$local.$cargandoArchivoRecargaDebito.removeClass("fa fa-spinner fa-pulse fa-fw");
				$local.$cargandoArchivoRecargaDebitoTexto.text('');
				$local.$btnRecargaDebito.attr("disabled", false);
				$local.$myDropzone2.find(".dz-remove").removeClass("hidden");
			}
		});
	}

	$local.$btnRecargaDebito.on('click', function() {
		if ($local.$archivosRecargaDebito.length == 0) {
			$funcionUtil.notificarException("No se ha adjuntado ning\u00FAn archivo.", "fa-warning", "Aviso", "warning");
			return;
		}
		if (!$funcionUtil.validarLote($local.$tablaRecargaDebito.dataTable().fnGetData())) {
			$funcionUtil.notificarException("La tabla contiene registros no v\u00E1lidos.", "fa-warning", "Aviso", "warning");
			return;
		}
		//Obtener lotes
		var data = $local.$tablaRecargaDebito.dataTable().fnGetData();
		var lotes = {
			recargas: $funcionUtil.obtenerLote(data, 'operacion', 'R'),
			debitos: $funcionUtil.obtenerLote(data, 'operacion', 'D'),
		}
		if (lotes.recargas.length == 0 && lotes.afiliaciones.length == 0) {
			$funcionUtil.notificarException("No se puede generar lotes sin registros.", "fa-warning", "Aviso", "warning");
			return;
		}
		$.confirm({
			icon: "fa fa-info-circle",
			title: "Aviso",
			content: "Confirma la generaci\u00F3n de los siguientes lotes: </br><b>Lote de recarga con: </b>" + lotes.recargas.length + " registros. </br><b>Lote de d\u00E9bitos con: </b> " + lotes.debitos.length + " registros.",
			buttons: {
				Aceptar: {
					action: function() {
						var confirmar = $.confirm({
							icon: 'fa fa-spinner fa-pulse fa-fw',
							title: "Generando lotes...",
							theme: "bootstrap",
							content: function() {
								var self = this;
								self.buttons.close.hide();
								$.ajax({
									type: "POST",
									url: $variableUtil.root + "recargaDebito/proceso",
									data: JSON.stringify(lotes),
									beforeSend: function(xhr) {
										$local.$btnRecargaDebito.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
										xhr.setRequestHeader('Content-Type', 'application/json');
										xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
									},
									error: function(response) {
										$funcionUtil.notificarException("Error al registrar las recargas y d\u00E9bitos", "fa-warning", "Error de carga", "warning");
									},
									success: function(data) {
										$funcionUtil.notificarException("Carga de recargas y lotes con \u00E9xito", "fa-check", "Aviso", "success");
										removerArchivoRecargasYDebitos()
									},
									complete: function(response) {
										$local.$btnRecargaDebito.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
										confirmar.close();
									}
								});
							},
							buttons: {
								close: {
									text: 'Aceptar'
								}
							}
						});
					},
					keys: ['enter'],
					btnClass: "btn-primary"
				},
				Cancelar: {
					keys: ['esc']
				},
			}
		});
	});

});