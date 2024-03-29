package ob.debitos.simp.utilitario;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;

import ob.debitos.simp.model.consulta.movimiento.TxnsCompensacionReporte;

public class LogContableExcelExporter 
{
	private SXSSFWorkbook woorbook;
	private Sheet sheet;
	
	List<TxnsCompensacionReporte> listTxnsCompensacion;
	
	public LogContableExcelExporter(List<TxnsCompensacionReporte> listTxnsCompensacion) {
		this.listTxnsCompensacion = listTxnsCompensacion;
		woorbook = new SXSSFWorkbook();
		sheet = woorbook.createSheet("TxnsCompensacionReporte");
	}
	
	private void writeHeaderRow() 
	{
		sheet.createFreezePane(2, 2);
		sheet.setAutoFilter(new CellRangeAddress(1, 1, 1, 65));
		Row row = null;
		Cell cell = null;
		Font font = woorbook.createFont();
		font.setBold(true);
		CellStyle styleCells = woorbook.createCellStyle();
		styleCells.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
		styleCells.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		styleCells.setAlignment(HorizontalAlignment.CENTER);
		styleCells.setFont(font);
		Row rowComisiones = sheet.createRow(0);
		Cell cellComisiones = rowComisiones.createCell(51);
		cellComisiones.setCellValue("COMISIONES");
		sheet.addMergedRegion(new CellRangeAddress(0,0,51,59));  
		cellComisiones.setCellStyle(styleCells);
		row = sheet.createRow(1);
		cell = row.createCell(1);
		cell.setCellValue("Secuencia Txn");
		cell.setCellStyle(styleCells);
		cell = row.createCell(2);
		cell.setCellValue("Empresa");
		cell.setCellStyle(styleCells);
		cell = row.createCell(3);
		cell.setCellValue("Cliente");
		cell.setCellStyle(styleCells);
		cell = row.createCell(4);
		cell.setCellValue("Membresía");
		cell.setCellStyle(styleCells);
		cell = row.createCell(5);
		cell.setCellValue("Clase Servicio");
		cell.setCellStyle(styleCells);
		cell = row.createCell(6);
		cell.setCellValue("Origen");
		cell.setCellStyle(styleCells);
		cell = row.createCell(7);
		cell.setCellValue("Transacción");
		cell.setCellStyle(styleCells);
		cell = row.createCell(8);
		cell.setCellValue("Emisor");
		cell.setCellStyle(styleCells);
		cell = row.createCell(9);
		cell.setCellValue("Adquiriente");
		cell.setCellStyle(styleCells);
		cell = row.createCell(10);
		cell.setCellValue("BIN");
		cell.setCellStyle(styleCells);
		cell = row.createCell(11);
		cell.setCellValue("SubBIN");
		cell.setCellStyle(styleCells);
		cell = row.createCell(12);
		cell.setCellValue("Tipo Documento");
		cell.setCellStyle(styleCells);
		cell = row.createCell(13);
		cell.setCellValue("N° Documento");
		cell.setCellStyle(styleCells);
		cell = row.createCell(14);
		cell.setCellValue("N° Tarjeta");
		cell.setCellStyle(styleCells);
		//NUEVO
		cell = row.createCell(15);
		cell.setCellValue("Código Seguimiento");
		cell.setCellStyle(styleCells);
		//NUEVO
		cell = row.createCell(16);
		cell.setCellValue("N° Cuenta");
		cell.setCellStyle(styleCells);
		cell = row.createCell(17);
		cell.setCellValue("Rol Txn");
		cell.setCellStyle(styleCells);
		cell = row.createCell(18);
		cell.setCellValue("Canal");
		cell.setCellStyle(styleCells);
		cell = row.createCell(19);
		cell.setCellValue("Fecha Txn");
		cell.setCellStyle(styleCells);
		cell = row.createCell(20);
		cell.setCellValue("Hora Txn");
		cell.setCellStyle(styleCells);
		cell = row.createCell(21);
		cell.setCellValue("Fecha Proceso");
		cell.setCellStyle(styleCells);
		cell = row.createCell(22);
		cell.setCellValue("Fecha Afectación");
		cell.setCellStyle(styleCells);
		cell = row.createCell(23);
		cell.setCellValue("Autorización");
		cell.setCellStyle(styleCells);
		cell = row.createCell(24);
		cell.setCellValue("Trace");
		cell.setCellStyle(styleCells);
		cell = row.createCell(25);
		cell.setCellValue("Código Respuesta");
		cell.setCellStyle(styleCells);
		cell = row.createCell(26);
		cell.setCellValue("ATM");
		cell.setCellStyle(styleCells);
		cell = row.createCell(27);
		cell.setCellValue("Moneda Comp");
		cell.setCellStyle(styleCells);
		cell = row.createCell(28);
		cell.setCellValue("Valor Comp");
		cell.setCellStyle(styleCells);
		cell = row.createCell(29);
		cell.setCellValue("Moneda Aut");
		cell.setCellStyle(styleCells);
		cell = row.createCell(30);
		cell.setCellValue("Valor Aut");
		cell.setCellStyle(styleCells);
		cell = row.createCell(31);
		cell.setCellValue("Ind Correo Telefono");
		cell.setCellStyle(styleCells);
		cell = row.createCell(32);
		cell.setCellValue("Metodo Id Thb");
		cell.setCellStyle(styleCells);
		cell = row.createCell(33);
		cell.setCellValue("Modo Entrada POS");
		cell.setCellStyle(styleCells);
		cell = row.createCell(34);
		cell.setCellValue("Terminal POS");
		cell.setCellStyle(styleCells);
		cell = row.createCell(35);
		cell.setCellValue("Nombre Afiliado");
		cell.setCellStyle(styleCells);
		cell = row.createCell(36);
		cell.setCellValue("Ciudad Afiliado");
		cell.setCellStyle(styleCells);
		cell = row.createCell(37);
		cell.setCellValue("Pais Afiliado");
		cell.setCellStyle(styleCells);
		cell = row.createCell(38);
		cell.setCellValue("N° Comprobante");
		cell.setCellStyle(styleCells);
		cell = row.createCell(39);
		cell.setCellValue("Giro Negocio");
		cell.setCellStyle(styleCells);
		cell = row.createCell(40);
		cell.setCellValue("Establecimiento");
		cell.setCellStyle(styleCells);
		cell = row.createCell(41);
		cell.setCellValue("Referencia Intercambio");
		cell.setCellStyle(styleCells);
		cell = row.createCell(42);
		cell.setCellValue("Autoriza STIP");
		cell.setCellStyle(styleCells);
		cell = row.createCell(43);
		cell.setCellValue("Txn Quasi-Cash");
		cell.setCellStyle(styleCells);
		cell = row.createCell(44);
		cell.setCellValue("Ind Compensación");
		cell.setCellStyle(styleCells);
		cell = row.createCell(45);
		cell.setCellValue("Fondo Cargo");
		cell.setCellStyle(styleCells);
		cell = row.createCell(46);
		cell.setCellValue("Fondo Abono");
		cell.setCellStyle(styleCells);
		cell = row.createCell(47);
		cell.setCellValue("Ind Moneda Int");
		cell.setCellStyle(styleCells);
		cell = row.createCell(48);
		cell.setCellValue("N° Cuotas");
		cell.setCellStyle(styleCells);
		cell = row.createCell(49);
		cell.setCellValue("Comisión ATM");
		cell.setCellStyle(styleCells);
		cell = row.createCell(50);
		cell.setCellValue("Cuenta Abono");
		cell.setCellStyle(styleCells);
		cell = row.createCell(51);
		cell.setCellValue("Cuenta Cargo");
		cell.setCellStyle(styleCells);
		cell = row.createCell(52);
		cell.setCellValue("THB");
		cell.setCellStyle(styleCells);
		cell = row.createCell(53);
		cell.setCellValue("INT");
		cell.setCellStyle(styleCells);
		cell = row.createCell(54);
		cell.setCellValue("REC");
		cell.setCellStyle(styleCells);
		cell = row.createCell(55);
		cell.setCellValue("OPE");
		cell.setCellStyle(styleCells);
		cell = row.createCell(56);
		cell.setCellValue("ISA");
		cell.setCellStyle(styleCells);
		cell = row.createCell(57);
		cell.setCellValue("OIF");
		cell.setCellStyle(styleCells);
		cell = row.createCell(58);
		cell.setCellValue("GAS");
		cell.setCellStyle(styleCells);
		cell = row.createCell(59);
		cell.setCellValue("DIS");
		cell.setCellStyle(styleCells);
		cell = row.createCell(60);
		cell.setCellValue("SUR");
		cell.setCellStyle(styleCells);
		cell = row.createCell(61);
		cell.setCellValue("TOTAL");
		cell.setCellStyle(styleCells);
		cell = row.createCell(62);
		cell.setCellValue("Tipo de Cambio");
		cell.setCellStyle(styleCells);
		cell = row.createCell(63);
		cell.setCellValue("Moneda Cargada Thb");
		cell.setCellStyle(styleCells);
		cell = row.createCell(64);
		cell.setCellValue("Valor Cargado Thb");
		cell.setCellStyle(styleCells);
	}
	
	private void writeDataRows() 
	{
		CellStyle estiloBordes = ExportacionUtil.obtenerEstiloBordes(woorbook);
		CellStyle estiloBordeFecha = ExportacionUtil.obtenerEstiloBordeFecha(woorbook);
		CellStyle estiloBordeDecimal = ExportacionUtil.obtenerEstiloBorderDecimal(woorbook);
		int rowCount = 2;
		for(TxnsCompensacionReporte txnsCompensacion : listTxnsCompensacion) {
			Row row = sheet.createRow(rowCount++);
			Cell cell = row.createCell(1);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getSecuenciaTransaccion().toString() == null ? "" : txnsCompensacion.getSecuenciaTransaccion().toString());
			cell = row.createCell(2);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getDescripcionEmpresa() == null ? "" : txnsCompensacion.getDescripcionEmpresa());
			cell = row.createCell(3);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getDescripcionCliente());
			cell = row.createCell(4);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getDescripcionMembresia());
			cell = row.createCell(5);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getDescripcionClaseServicio());
			cell = row.createCell(6);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getDescripcionOrigen());
			cell = row.createCell(7);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getDescripcionCodigoTransaccion());
			cell = row.createCell(8);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getDescripcionInstitucionEmisor());
			cell = row.createCell(9);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getDescripcionInstitucionReceptor());
			cell = row.createCell(10);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getCodigoBIN());
			cell = row.createCell(11);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getCodigoSubBIN());
			cell = row.createCell(12);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getTipoDocumento());
			cell = row.createCell(13);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getNumeroDocumento());
			cell = row.createCell(14);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getNumeroTarjeta());
			//NUEVO
			cell = row.createCell(15);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getCodigoSeguimientoTarjeta());
			//NUEVO
			cell = row.createCell(16);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getNumeroCuenta());
			cell = row.createCell(17);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getDescripcionRolTransaccion());
			cell = row.createCell(18);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getDescripcionCanal());
			cell = row.createCell(19);
			cell.setCellStyle(estiloBordeFecha);
			cell.setCellValue(txnsCompensacion.getFechaTransaccion());
			cell = row.createCell(20);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getHoraTransaccion());
			cell = row.createCell(21);
			cell.setCellStyle(estiloBordeFecha);
			cell.setCellValue(txnsCompensacion.getFechaProceso());
			cell = row.createCell(22);
			cell.setCellStyle(estiloBordeFecha);
			cell.setCellValue(txnsCompensacion.getFechaAfectacion());
			cell = row.createCell(23);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getCodigoAutorizacion());
			cell = row.createCell(24);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getNumeroVoucher());
			cell = row.createCell(25);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getDescripcionRespuestaSwitch());
			cell = row.createCell(26);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getDescripcionATM());
			cell = row.createCell(27);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getCodigoMonedaCompensacion());
			cell = row.createCell(28);
			cell.setCellStyle(estiloBordeDecimal);
			cell.setCellValue(txnsCompensacion.getValorCompensacion());
			cell = row.createCell(29);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue("");
			cell = row.createCell(30);
			cell.setCellStyle(estiloBordeDecimal);
			cell.setCellValue("");
			cell = row.createCell(31);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getDescripcionIndCorreoTelefono());
			cell = row.createCell(32);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getDescripcionMetodoIdThb());
			cell = row.createCell(33);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getDescripcionModoEntradaPos());
			cell = row.createCell(34);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getDescripcionTerminalPos());
			cell = row.createCell(35);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getNombreAfiliado());
			cell = row.createCell(36);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getCiudadAfiliado());
			cell = row.createCell(37);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getPaisAfiliado());
			cell = row.createCell(38);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getNumeroDocumentoCompensacion());
			cell = row.createCell(39);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getDescripcionGiroNegocio());
			cell = row.createCell(40);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getCodigoEstablecimiento());
			cell = row.createCell(41);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getReferenciaIntercambio());
			cell = row.createCell(42);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getDescripcionAutorizaSTIP());
			cell = row.createCell(43);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getDescripcionTxnQuasiCash());
			cell = row.createCell(44);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getIndicadorCompensacion());
			cell = row.createCell(45);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getFondoCargo());
			cell = row.createCell(46);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getFondoAbono());
			cell = row.createCell(47);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getIndicadorMonedaInternacional());
			cell = row.createCell(48);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getNumeroCuotas());
			cell = row.createCell(49);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getComisionCuotaAccesoAtm());
			cell = row.createCell(50);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getCuentaAbono());
			cell = row.createCell(51);
			cell.setCellStyle(estiloBordes);
			cell.setCellValue(txnsCompensacion.getCuentaCargo());
			cell = row.createCell(52);
			cell.setCellStyle(estiloBordeDecimal);
			cell.setCellValue(txnsCompensacion.getComision1());
			cell = row.createCell(53);
			cell.setCellStyle(estiloBordeDecimal);
			cell.setCellValue(txnsCompensacion.getComision2());
			cell = row.createCell(54);
			cell.setCellStyle(estiloBordeDecimal);
			cell.setCellValue(txnsCompensacion.getComision3());
			cell = row.createCell(55);
			cell.setCellStyle(estiloBordeDecimal);
			cell.setCellValue(txnsCompensacion.getComision4());
			cell = row.createCell(56);
			cell.setCellStyle(estiloBordeDecimal);
			cell.setCellValue(txnsCompensacion.getComision5());
			cell = row.createCell(57);
			cell.setCellStyle(estiloBordeDecimal);
			cell.setCellValue(txnsCompensacion.getComision6());
			cell = row.createCell(58);
			cell.setCellStyle(estiloBordeDecimal);
			cell.setCellValue(txnsCompensacion.getComision7());
			cell = row.createCell(59);
			cell.setCellStyle(estiloBordeDecimal);
			cell.setCellValue(txnsCompensacion.getComision8());
			cell = row.createCell(60);
			cell.setCellStyle(estiloBordeDecimal);
			cell.setCellValue(txnsCompensacion.getComision9());
			cell = row.createCell(61);
			cell.setCellStyle(estiloBordeDecimal);
			cell.setCellValue(txnsCompensacion.getComision1()+txnsCompensacion.getComision2()+txnsCompensacion.getComision3()+txnsCompensacion.getComision4()+txnsCompensacion.getComision5()+txnsCompensacion.getComision6()+txnsCompensacion.getComision7()+txnsCompensacion.getComision8()+txnsCompensacion.getComision9());
			cell = row.createCell(62);
			cell.setCellStyle(estiloBordeDecimal);
			cell.setCellValue(txnsCompensacion.getTipoDeCambio());
			cell = row.createCell(63);
			cell.setCellStyle(estiloBordeDecimal);
			cell.setCellValue(txnsCompensacion.getMonedaCargadaThb());
			cell = row.createCell(64);
			cell.setCellStyle(estiloBordeDecimal);
			cell.setCellValue(txnsCompensacion.getValorCargadoThb());
			
		}
	}
	
	public void export(HttpServletResponse response) throws IOException
	{
		writeHeaderRow();
		writeDataRows();
		ServletOutputStream outputStream = response.getOutputStream();
		woorbook.write(outputStream);
		woorbook.close();
		outputStream.close();
	}
}
