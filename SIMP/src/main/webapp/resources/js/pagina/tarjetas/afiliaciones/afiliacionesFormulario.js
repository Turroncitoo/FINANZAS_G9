$(document).ready(function() {

	$formActualizacionLote.validate({
		rules: {
			idAfinidad: {
				required: true
			},
			idCategoria: {
				required: true
			},
			tipoTarjetas: {
				required: true
			}
		},
		messages: {
			idAfinidad: {
				required: "Ingrese la afinidad"
			},
			idCategoria: {
				required: "Ingrese la categor\u00EDa"
			},
			tipoTarjetas: {
				required: "Ingrese un Tipo Tarjeta"
			}
		}
	});

	$formRegistroRequerimiento.validate({
		rules: {
			idInstitucion: {
				required: true
			},
			idEmpresa: {
				required: true
			},
			idCliente: {
				required: true
			},
			idLogo: {
				required: true
			},
			codigoProducto: {
				required: true
			},
			tipoAfiliacion: {
				required: true
			},
			idAfinidad: {
				required: true
			},
			idCategoria: {
				required: true
			},
			tipoTarjetas: {
				required: true
			},
			registros: {
				required: true,
				number: true,
				range: [1, 99999]
			}
		},
		messages: {
			idInstitucion: {
				required: "Ingrese la instituci\u00F3n"
			},
			idEmpresa: {
				required: "Ingrese la empresa"
			},
			idCliente: {
				required: "Ingrese el cliente"
			},
			idLogo: {
				required: "Ingrese el Logo"
			},
			codigoProducto: {
				required: "Ingrese el Producto"
			},
			tipoAfiliacion: {
				required: "Ingrese un Tipo Afiliaci\u00F3n"
			},
			idAfinidad: {
				required: "Ingrese la afinidad"
			},
			idCategoria: {
				required: "Ingrese la categor\u00EDa"
			},
			tipoTarjetas: {
				required: "Ingrese un Tipo Tarjeta"
			},
			registros: {
				required: "Ingrese la cantidad del pedido",
				number: "Ingrese el n\u00FAmero",
				range: "El cantidad del pedido debe estar entre {0} al {1}"
			},
		}
	});

	$formModalAfiliacion.validate({
		rules: {
			tipoDocumento: {
				required: true,
			},
			numeroDocumento: {
				required: true,
				notOnlySpace: true,
				soloNumeroPositivos: true,
				rangelength: [8, 12]
			},
			nombre: {
				required: true,
				rangelength: [3, 26]
			},
			apellidoPaterno: {
				required: true,
				rangelength: [3, 26]
			},
			apellidoMaterno: {
				required: true,
				rangelength: [3, 26]
			},
			nombreEmbossing: {
				required: true,
				rangelength: [3, 26]
			},
			ruc: {
				required: true,
				notOnlySpace: true,
				soloNumeroPositivos: true,
				rangelength: [11, 11]
			},
			nombreCliente: {
				required: true,
				rangelength: [3, 26]
			},
			fechaVencimiento: {
				required: true,
				rangelength: [5, 5]
			},
			correo: {
				required: true,
				rangelength: [10, 100]
			},
			direccion: {
				required: true,
				rangelength: [3, 100]
			},
			telefonoMovil: {
				numberorempty: true,
				rangelength: [9, 12]
			},
			sexo: {
				required: true,
				rangelength: [1, 1]
			},
			indicadorDistribucion: {
				required: true,
				rangelength: [1, 1]
			},
			nombreManda1: {
				required: true,
				rangelength: [3, 26]
			},
			tipoManda1: {
				required: true,
			},
			numManda1: {
				required: true,
				soloalfanumericos: true,
				rangelength: [6, 14]
			},
			fonoMnada1: {
				required: true,
				rangelength: [9, 11]
			},
			nombreManda2: {
				required: true,
				rangelength: [3, 26]
			},
			tipoManda2: {
				required: true,
			},
			numManda2: {
				required: true,
				soloalfanumericos: true,
				rangelength: [6, 14]
			},
			fonoMnada2: {
				required: true,
				rangelength: [9, 11]
			}
		},
		messages: {
			tipoDocumento: {
				required: "Seleccione un tipo de documento"
			},
			numeroDocumento: {
				required: "Ingrese un N\u00FAmero documento",
				notOnlySpace: "El N\u00FAmero documento no debe contener espacio en blanco",
				rangelength: "El N\u00FAmero documento debe tener entre 8 y 12 car&aacute;cteres.",
				soloNumeroPositivos: "El N\u00FAmero de documentos solo debe contener numeros"
			},
			nombre: {
				required: "Ingrese el nombre",
				rangelength: "El nombre debe contener entre 3 y 26 caracteres"
			},
			apellidoPaterno: {
				required: "Ingrese el apellido paterno",
				rangelength: "El apellido paterno debe contener entre 3 y 26 caracteres"
			},
			apellidoMaterno: {
				required: "Ingrese el apellido materno",
				rangelength: "El apellido materno debe contener entre 3 y 26 caracteres"
			},
			nombreEmbossing: {
				required: "Ingrese el nombre embossing",
				rangelength: "El nombre embossing debe contener entre 3 y 26 caracteres"
			},
			ruc: {
				required: "Ingrese un N\u00FAmero RUC",
				notOnlySpace: "El N\u00FAmero RUC no debe contener espacio en blanco",
				rangelength: "El N\u00FAmero RUC debe tener entre {0} car&aacute;cteres.",
				soloNumeroPositivos: "El N\u00FAmero RUC solo debe contener numeros"
			},
			nombreCliente: {
				required: "Ingrese el nombre de la Cliente",
				rangelength: "El nombre de la empresa debe contener entre 3 y 26 caracteres"
			},
			fechaVencimiento: {
				rangelength: "Los meses de vencimiento debe contener 5 caracteres"
			},
			direccion: {
				rangelength: "La direccion debe contener entre 3 y 100 caracteres"
			},
			telefonoMovil: {
				numberorempty: "El telefono movil no debe contener espacios",
				rangelength: "El telefono movil debe contener entre 9 y 12 caracteres"
			},
			sexo: {
				rangelength: "El Sexo debe contener entre 1 caracter"
			},
			indicadorDistribucion: {
				rangelength: "El Indicador Distribucion debe contener entre {1} caracter"
			},
			nombreManda1: {
				required: "Ingrese el Nombre Mandatorio 1",
				rangelength: "El Nombre Mandatorios 1 debe contener entre {0} y {1} caracteres"
			},
			tipoManda1: {
				required: "Seleccione Tipo Documento Mandatorio 1",
			},
			numManda1: {
				required: "Ingrese el N\u00FAmero Documento Mandatorio 1",
				rangelength: "El N\u00FAmero Documento Mandatorios 1 debe contener entre {0} y {1} caracteres"
			},
			fonoMnada1: {
				required: "Ingrese un N\u00FAmero Mandatorio 1",
				rangelength: "El N\u00FAmero Mandatorios 2 debe contener entre {0} y {1} caracteres"
			},
			nombreManda2: {
				required: "Ingrese el Nombre Mandatorio 2",
				rangelength: "El Nombre Mandatorios 2 debe contener entre {0} y {1} caracteres"
			},
			tipoManda2: {
				required: "Seleccione el Tipo Documento Mandatorio 2",
			},
			numManda2: {
				required: "Ingrese el N\u00FAmero Documento Mandatorio 2",
				rangelength: "El N\u00FAmero Documento Mandatorios 2 debe contener entre {0} y {1} caracteres"
			},
			fonoMnada2: {
				required: "Ingrese el Celular Mandatorio 2",
				rangelength: "El N\u00FAmero Mandatorios 2 debe contener entre {0} y {1} caracteres"

			}
		}
	});

});

