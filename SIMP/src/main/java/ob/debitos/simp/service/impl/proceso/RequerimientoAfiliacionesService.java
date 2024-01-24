package ob.debitos.simp.service.impl.proceso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ob.debitos.simp.mapper.IClienteMapper;
import ob.debitos.simp.mapper.IDescripcionMapper;
import ob.debitos.simp.model.mantenimiento.Cliente;
import ob.debitos.simp.model.parametro.Parametro;
import ob.debitos.simp.model.prepago.ControlLotePP;
import ob.debitos.simp.model.prepago.DescripcionDTO;
import ob.debitos.simp.model.prepago.PersonaPP;
import ob.debitos.simp.model.prepago.RecargaPP;
import ob.debitos.simp.model.proceso.AfiliacionesCarga;
import ob.debitos.simp.service.IRequerimientoAfiliacionesService;
import ob.debitos.simp.service.excepcion.ArchivoExcelNoValidoException;
import ob.debitos.simp.service.excepcion.ArchivoNuloException;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.StringsUtils;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class RequerimientoAfiliacionesService implements IRequerimientoAfiliacionesService {

	@Autowired private IDescripcionMapper descripcionMapper;
	@Autowired private IClienteMapper clienteMapper;

	/*@SuppressWarnings("resource")
	@Override
	public List<ControlLotePP> validarListaNuevosPedidos(MultipartFile nuevosPedidos, String idEmpresa,String nombreClienteExcel) {
		List<ControlLotePP> listControlLoteDTOs = new ArrayList<>();
		List<DescripcionDTO> nacionalidades = descripcionMapper.obtenerNacionalidades();
		HSSFWorkbook workbook = null;
		HSSFSheet worksheet = null;
		String sIdClienteAnterior = "";
		int fila = 1;
		if(nuevosPedidos.isEmpty()) throw new ArchivoNuloException("Seleccione un archivo excel(.xls)");
		try {
			workbook = new HSSFWorkbook(nuevosPedidos.getInputStream());
		} catch (IOException e) {
			throw new ArchivoNuloException("Archivo de excel de nuevos pedidos es nulo");
		}
		worksheet = workbook.getSheetAt(0);
		validarCabeceraExcel(ConstantesFormatoExcel.CABECERA_EXCEL,
				worksheet.getRow(ConstantesFormatoExcel.NUM_FILA_CABECERA));

		fila = 1;
		while (fila <= worksheet.getLastRowNum()) {

			ControlLotePP controlLoteDTO = ControlLotePP.builder().build();
			PersonaPP personaDTO = PersonaPP.builder().build();
			RecargaPP recargaDTO = RecargaPP.builder().build();
			HSSFRow row = worksheet.getRow(fila);

			controlLoteDTO.setIdControlLote(fila);
			controlLoteDTO.setRespCode(0);
			Map<String,Integer> errores = new HashMap<>();
			
			//Obligatorio
			Cell tipoDocumento = row.getCell(ConstantesFormatoExcel.COLUMNA_TIP_DOC);
			tipoDocumento.setCellType(Cell.CELL_TYPE_STRING);
			Integer tipoDocumentoValue = null;
			try {
				tipoDocumentoValue = Integer.parseInt(tipoDocumento.getStringCellValue());
				if (descripcionMapper.obtenerTipoDocumentoPorCodigo(tipoDocumentoValue.intValue()).isEmpty()){
					controlLoteDTO.setRespCode(-1);
					errores.put("lbTipoDocumento", -1);
				}
				else{
					personaDTO.setTipoDocumento(tipoDocumentoValue.intValue());
				}
					
			} catch (NumberFormatException nfe) {
				errores.put("lbTipoDocumento", -2);
				controlLoteDTO.setRespCode(-1);
			}

			//Obligatorio
			Cell numDocumento = row.getCell(ConstantesFormatoExcel.COLUMNA_NUM_DOC);
			numDocumento.setCellType(Cell.CELL_TYPE_STRING);
			if(numDocumento.getStringCellValue().isEmpty()){
				controlLoteDTO.setRespCode(-1);
				errores.put("lbNumDocumento", -1);//Numero de documento vacio
			}else{
				if(personaDTO.getTipoDocumento() == null){
					personaDTO.setNumDocumento(numDocumento.getStringCellValue());
				}else{
					try {
						
						Integer.parseInt(numDocumento.getStringCellValue());

						switch (personaDTO.getTipoDocumento().intValue()) {
						case ConstantesGenerales.TIPO_DOCUMENTO_DNI:
							if (numDocumento.getStringCellValue().length() != 8) {
								controlLoteDTO.setRespCode(-1);
								errores.put("lbNumDocumento", -3); // longitud
																	// de dni no
																	// valido
							}
							break;
						case ConstantesGenerales.TIPO_DOCUMENTO_PASAPORTE:
							if (numDocumento.getStringCellValue().length() != 12) {
								controlLoteDTO.setRespCode(-1);
								errores.put("lbNumDocumento", -4); // longitud
																	// de dni no
																	// valido
							}
							break;
						case ConstantesGenerales.TIPO_DOCUMENTO_CARNET_EXTRANJERIA:
							if (numDocumento.getStringCellValue().length() != 12) {
								controlLoteDTO.setRespCode(-1);
								errores.put("lbNumDocumento", -5); // longitud
																	// de dni no
																	// valido
							}
							break;
						case ConstantesGenerales.TIPO_DOCUMENTO_AUTOGENERADO:
							if (numDocumento.getStringCellValue().length() != 8) {
								controlLoteDTO.setRespCode(-1);
								errores.put("lbNumDocumento", -6); // longitud
																	// de dni no
																	// valido
							}
							break;
						}
					} catch (NumberFormatException nfe) {
						controlLoteDTO.setRespCode(-1);
						errores.put("lbNumDocumento", -2);
					}
				}
			}
			personaDTO.setNumDocumento(numDocumento.getStringCellValue());

			//Obligatorio
			Cell nombre = row.getCell(ConstantesFormatoExcel.COLUMNA_NOMBRE);
			nombre.setCellType(Cell.CELL_TYPE_STRING);
			if(nombre.getStringCellValue().isEmpty()){
				controlLoteDTO.setRespCode(-1);
				errores.put("lbNombres", -1);
			}else{
				personaDTO.setNombres(nombre.getStringCellValue().toUpperCase());
			}
			
//			if (!(nombre.getStringCellValue().toUpperCase().matches("^[a-zA-Z \\.#0-9]{1,26}$"))) {
//				controlLoteDTO.setRespCode(-1);
//				errores.put("lbNombres", -1);
//			}

			//Obligatorio
			Cell apellido = row.getCell(ConstantesFormatoExcel.COLUMNA_APELLIDO);
			apellido.setCellType(Cell.CELL_TYPE_STRING);
			if(apellido.getStringCellValue().isEmpty()){
				controlLoteDTO.setRespCode(-1);
				errores.put("lbApellidos", -1); //Columna apellidos vacio
			}else{
				String[] lstSplitApellido = StringsUtils.obtenerSubCadenas(apellido.getStringCellValue(), " ");
				personaDTO.setApePaterno(lstSplitApellido[0].toUpperCase());
				if (lstSplitApellido.length > 1)
					personaDTO.setApeMaterno(lstSplitApellido[1].toUpperCase());
				else{
//					controlLoteDTO.setRespCode(-1);
					personaDTO.setApeMaterno("");
//					errores.put("lbApellidos", -2); 
				}
					
				if (!apellido.getStringCellValue().toUpperCase().matches("^[a-zA-Z \\.#0-9]{1,52}$")) {
					controlLoteDTO.setRespCode(-1);
					errores.put("lbApellidos", -3);
				}
			}
			
			
			//No Obligatorio
			Cell nombreEmbossing = row.getCell(ConstantesFormatoExcel.COLUMNA_NOMBRE_EMBOSSING);
			nombreEmbossing.setCellType(Cell.CELL_TYPE_STRING);
			personaDTO.setAlias(nombreEmbossing.getStringCellValue().toUpperCase());
			if (!nombreEmbossing.getStringCellValue().isEmpty()
					&& !nombreEmbossing.getStringCellValue().toUpperCase().matches("^[a-zA-Z \\.]{1,26}$")) {
				controlLoteDTO.setRespCode(-1);
				errores.put("lbNombreEnTarjeta", -2);
			}

			//No Obligatorio
			Cell nombreCliente = row.getCell(ConstantesFormatoExcel.COLUMNA_NOMBRE_CLIENTE);
			nombreCliente.setCellType(Cell.CELL_TYPE_STRING);
			String nomCliente = nombreCliente.getStringCellValue();
			personaDTO.setNomCliente(nombreCliente.getStringCellValue().toUpperCase());
			List<Cliente> listClientes = clienteMapper
					.mantener(new Parametro(Verbo.GETS, Cliente.builder().idEmpresa(idEmpresa).build()));
			List<Cliente> lstClientesResult = listClientes.stream()
					.filter(x -> x.getDescripcion().compareToIgnoreCase(nomCliente) == 0).collect(Collectors.toList());
			if(!nombreCliente.getStringCellValue().toUpperCase().equals(nombreClienteExcel.toUpperCase())){
				throw new ArchivoExcelNoValidoException("El nombre del cliente en el excel no coincide con el cliente seleccionado");
			}
			if (!lstClientesResult.isEmpty()) {
				if(fila == 1){
					sIdClienteAnterior = nombreCliente.getStringCellValue().toUpperCase();
				}else{
					if(!nombreCliente.getStringCellValue().toUpperCase().equals(sIdClienteAnterior.toUpperCase())){
						throw new ArchivoExcelNoValidoException("El archivo excel contiene mas de un nombre de cliente");
					}else{
						sIdClienteAnterior = nombreCliente.getStringCellValue().toUpperCase();
					}
				}
				personaDTO.setCodCliente(lstClientesResult.get(0).getIdCliente());
				personaDTO.setNomCliente(lstClientesResult.get(0).getDescripcion());
			} else {
				errores.put("lbCliente", -1);
				controlLoteDTO.setRespCode(-1);
			}

			Cell montoRecarga = row.getCell(ConstantesFormatoExcel.COLUMNA_MONTO_RECARGA);
			montoRecarga.setCellType(Cell.CELL_TYPE_STRING);
			try {
				recargaDTO.setMoneda(ConstantesFormatoExcel.MONEDA_RECARGA);
				if (montoRecarga.getStringCellValue().isEmpty()) {
					recargaDTO.setMontoEnviadoo(0.0);
					errores.put("lbMontoRecarga", -1);
					controlLoteDTO.setRespCode(-1);
				} else {
					recargaDTO.setMontoEnviadoo(Double.parseDouble(montoRecarga.getStringCellValue()));
					if (Double.parseDouble(montoRecarga.getStringCellValue()) > 4000.0f) {
						errores.put("lbMontoRecarga", -2);
						controlLoteDTO.setRespCode(-1);
					}
				}
			} catch (NumberFormatException e) {
				errores.put("lbMontoRecarga", -3);
				recargaDTO.setMontoEnviadoo(-1);
				controlLoteDTO.setRespCode(-1);
			}

			Cell mesesVencimiento = row.getCell(ConstantesFormatoExcel.COLUMNA_MESES_VENCIMIENTO);
			mesesVencimiento.setCellType(Cell.CELL_TYPE_STRING);
			try {
				controlLoteDTO.setCardLife(Integer.parseInt(mesesVencimiento.getStringCellValue()));
			} catch (NumberFormatException e) {
				errores.put("lbVigenciaTarjeta", -1);
				controlLoteDTO.setCardLife(-1);
				controlLoteDTO.setRespCode(-1);
			}

			Cell email = row.getCell(ConstantesFormatoExcel.COLUMNA_EMAIL);
			email.setCellType(Cell.CELL_TYPE_STRING);
			if(!email.getStringCellValue().isEmpty()){
				if (!email.getStringCellValue().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")){
					errores.put("lbCorreoElectronico", -1);
					controlLoteDTO.setRespCode(-1);
				}
				personaDTO.setCorreoElectronico(email.getStringCellValue());
			}
			
			Cell direccion = row.getCell(ConstantesFormatoExcel.COLUMNA_DIRECCION);
			direccion.setCellType(Cell.CELL_TYPE_STRING);
			personaDTO.setDireccion(direccion.getStringCellValue());

			Cell telefonoCelular = row.getCell(ConstantesFormatoExcel.COLUMNA_TELEFONO_CELULAR);
			telefonoCelular.setCellType(Cell.CELL_TYPE_STRING);
			personaDTO.setTelMovil(telefonoCelular.getStringCellValue());

			Cell telefonoFijo = row.getCell(ConstantesFormatoExcel.COLUMNA_TELEFONO_FIJO);
			telefonoFijo.setCellType(Cell.CELL_TYPE_STRING);
			personaDTO.setTelFijo(telefonoFijo.getStringCellValue());

			Cell fechaNacimiento = row.getCell(ConstantesFormatoExcel.COLUMNA_FECHA_NACIMIENTO);			
			SimpleDateFormat dateParser = new SimpleDateFormat("dd/MM/yyyy");
			try {
				if (fechaNacimiento.getDateCellValue()==null) {
					personaDTO.setFechaNacimientoo(dateParser.parse("01/01/1900"));
				} else
					personaDTO.setFechaNacimientoo(fechaNacimiento.getDateCellValue());
			} catch (ParseException e) {
				e.printStackTrace();
				controlLoteDTO.setRespCode(-1);
			}

			Cell nacionalidad = row.getCell(ConstantesFormatoExcel.COLUMNA_NACIONALIDAD);
			nacionalidad.setCellType(Cell.CELL_TYPE_STRING);
			if (!nacionalidad.getStringCellValue().isEmpty()) {
				boolean existeNacionalidad = false;
				for (DescripcionDTO descripcionDTO : nacionalidades) {
					if (nacionalidad.getStringCellValue().equalsIgnoreCase(descripcionDTO.getValorLiteral())) {
						personaDTO.setNacionalidad(String.format("%03d", descripcionDTO.getIdElemento()));
						existeNacionalidad = true;
					}
				}
				if (!existeNacionalidad) {
					errores.put("lbNacionalidad", -1);
					controlLoteDTO.setRespCode(-1);
					personaDTO.setNacionalidad(nacionalidad.getStringCellValue());
				}
			}

			Cell clienteEmbossing = row.getCell(ConstantesFormatoExcel.COLUMNA_CLIENTE_EMBOSSING);
			clienteEmbossing.setCellType(Cell.CELL_TYPE_STRING);
			personaDTO.setIdPersona(-1);
			controlLoteDTO.setPersona(personaDTO);
			controlLoteDTO.setRecarga(recargaDTO);
			controlLoteDTO.setRespCodeControlLote(errores);
			listControlLoteDTOs.add(controlLoteDTO);
			fila++;
		}
		try {
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return listControlLoteDTOs;
	}
	*/
	

}
