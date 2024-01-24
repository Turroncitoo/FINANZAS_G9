package ob.debitos.simp.utilitario;

import java.awt.Dimension;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import ob.debitos.simp.configuracion.security.SecurityContextFacade;

public class ExportacionUtil {
	
	private static final float ALTURA_TITULO_FACTOR = 6;
	private static final String FORMATO_FECHA_REPORTES = "dd/MM/yyy HH:mm:ss";

	private ExportacionUtil(){}
	
	public static void resizePicture(Picture pict, Sheet sheet, int columnIndex, int rowIndex) {
		Dimension dimImagen = pict.getImageDimension();
		double alturaLogo = dimImagen.getHeight();
		double anchoLogo = dimImagen.getWidth();
		double numeroFilasLogo = 2.5;
		Row row = sheet.createRow(rowIndex);
		row.setHeightInPoints((float) numeroFilasLogo * row.getHeightInPoints());
		double alturaContenedor = (double) PixelUtil.heightUnits2widthUnits(sheet.getRow(rowIndex).getHeight());
		double anchoContenedor = (double) sheet.getColumnWidth(columnIndex);
		pict.resize((anchoLogo / alturaLogo) * (alturaContenedor / anchoContenedor), 1);
	}

	public static void insertarLogo(Workbook workbook, int sheetNumber, byte[] imgBytes, int columnsNumber) {
		int column = 1;
		int row = 0;
		// Merges the cells (firstRow, int lastRow, int firstCol, int lastCol)
		CellRangeAddress cellRangeAddress = new CellRangeAddress(row, row, column, column + (columnsNumber - 2));
		workbook.getSheetAt(sheetNumber).addMergedRegion(cellRangeAddress);
		int pictureIdx = workbook.addPicture(imgBytes, Workbook.PICTURE_TYPE_PNG);
		CreationHelper helper = workbook.getCreationHelper();
		Drawing drawing = workbook.getSheetAt(sheetNumber).createDrawingPatriarch();
		ClientAnchor anchor = helper.createClientAnchor();
		anchor.setCol1(column);
		anchor.setRow1(row);
		Picture pict = drawing.createPicture(anchor, pictureIdx);
		resizePicture(pict, workbook.getSheetAt(sheetNumber), column, row);
	}
	
	public static void insertarTitulo(HttpServletRequest request, Workbook workbook, int sheetNumber, int columnsNumber,
			String nombreReporte) {
		int rowTitle = 1;
		int columnTitle = 1;
		float resizeRowFactor = ALTURA_TITULO_FACTOR;
		String rutaAux = request.getParameter("rutaEnSidebar");
		rutaAux = rutaAux.replace("_", " ");

		String title = "SISTEMA INTEGRAL DE MEDIOS DE PAGO\n";
		String subtitle = "Módulo de Exportación de Información de " + rutaAux.split("\\/")[0] + "\n";
		String rutaEnSidebar = rutaAux + nombreReporte + "\n\n";
		String user = "Usuario: " + SecurityContextFacade.obtenerNombreUsuario();
		String date = "Fecha: " + DatesUtils.obtenerFechaEnFormato(new Date(), FORMATO_FECHA_REPORTES);

		date = getSpaceBetween(title, user, date) + date;

		Sheet sheet = workbook.getSheetAt(sheetNumber);

		// startRow, endRow, n, copyRowHeight, resetOriginalRowHeight
		sheet.shiftRows(0, 1, 2, true, true);

		Row row = sheet.createRow(rowTitle);
		row.setHeightInPoints(resizeRowFactor * row.getHeightInPoints());

		// Merges the cells (firstRow, int lastRow, int firstCol, int lastCol)
		CellRangeAddress cellRangeAddress = new CellRangeAddress(rowTitle, rowTitle, columnTitle,
				columnTitle + (columnsNumber - 2));
		sheet.addMergedRegion(cellRangeAddress);

		// Creates the cellStyle (to make newlines visible)
		CellStyle wrapStyle = workbook.createCellStyle();
		wrapStyle.setWrapText(true);
		wrapStyle.setAlignment(HorizontalAlignment.CENTER);
		wrapStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		// Creates the cell
		Cell cell = CellUtil.createCell(row, columnTitle, "");

		// CellStyle to cell
		cell.setCellStyle(wrapStyle);

		// Adds formatted text
		cell.setCellValue(getFormattedText(workbook, title, subtitle, rutaEnSidebar, user, date));
	}

	public static void insertarNombreReporte(Workbook workbook, int sheetNumber,
											 int columnsNumber, String nombreReporte, int rowInicial)
	{
		int rowTitle = rowInicial;
		int columnTitle = 1;
		Sheet sheet = workbook.getSheetAt(sheetNumber);
		Row row = sheet.createRow(rowTitle);
		Font fontBold = workbook.createFont();
		fontBold.setUnderline(Font.U_SINGLE);
		fontBold.setBold(true);
		CellStyle wrapStyle = workbook.createCellStyle();
		wrapStyle.setWrapText(true);
		wrapStyle.setAlignment(CellStyle.ALIGN_CENTER);
		wrapStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		wrapStyle.setFont(fontBold);
		wrapStyle.setAlignment(CellStyle.ALIGN_CENTER);
		Cell cell = CellUtil.createCell(row, columnTitle, "");
		cell.setCellStyle(wrapStyle);
		cell.setCellValue(nombreReporte.toUpperCase());
		CellRangeAddress cellRangeAddress = new CellRangeAddress(rowTitle,
				rowTitle, columnTitle, columnTitle + (columnsNumber - 2));
		sheet.addMergedRegion(cellRangeAddress);
	}
	
	public static XSSFRichTextString getFormattedText(Workbook workbook, String title, String subtitle, String ruta,
			String username, String date) {
		XSSFRichTextString richString = new XSSFRichTextString();
		// Title font
		XSSFFont titleFont = (XSSFFont) workbook.createFont();
		titleFont.setFontHeightInPoints((short) 14);
		titleFont.setUnderline(Font.U_SINGLE);
		titleFont.setBold(true);
		// Subtitle font
		XSSFFont subTitleFont = (XSSFFont) workbook.createFont();
		subTitleFont.setFontHeightInPoints((short) 11);
		// Username and date font
		XSSFFont usernameAndDateFont = (XSSFFont) workbook.createFont();
		usernameAndDateFont.setFontHeightInPoints((short) 11);
		usernameAndDateFont.setBold(true);
		richString.append(title, titleFont);
		richString.append(subtitle + ruta, subTitleFont);
		richString.append(username + date, usernameAndDateFont);
		return richString;
	}
	
	public static CellStyle crearEstiloBasico(SXSSFWorkbook sxssfWorkbook, Font font) 
    {
        CellStyle estilo = sxssfWorkbook.createCellStyle();

        estilo.setAlignment(HorizontalAlignment.LEFT);
        estilo.setBorderTop(BorderStyle.THIN);
        estilo.setBorderRight(BorderStyle.THIN);
        estilo.setBorderBottom(BorderStyle.THIN);
        estilo.setBorderLeft(BorderStyle.THIN);
        estilo.setFont(font);

        return estilo;
    }

    public static CellStyle crearEstiloNumero(SXSSFWorkbook sxssfWorkbook, String formato, Font font) 
    {
        CellStyle estilo = sxssfWorkbook.createCellStyle();

        estilo.setAlignment(HorizontalAlignment.RIGHT);
        estilo.setDataFormat(sxssfWorkbook.createDataFormat().getFormat(formato));
        estilo.setBorderTop(BorderStyle.THIN);
        estilo.setBorderRight(BorderStyle.THIN);
        estilo.setBorderBottom(BorderStyle.THIN);
        estilo.setBorderLeft(BorderStyle.THIN);
        estilo.setFont(font);

        return estilo;
    }
	
	public static CellStyle obtenerEstiloBordes(SXSSFWorkbook sxssfWorkbook){
		Font font = sxssfWorkbook.createFont();
		font.setFontName("Calibri");
		// Estilo de bordes con fuente
		CellStyle estiloBordes = sxssfWorkbook.createCellStyle();
		estiloBordes.setBorderTop(BorderStyle.THIN);
		estiloBordes.setBorderBottom(BorderStyle.THIN);
		estiloBordes.setBorderLeft(BorderStyle.THIN);
		estiloBordes.setBorderRight(BorderStyle.THIN);
		estiloBordes.setFont(font);
		return estiloBordes;
	}
	
	public static CellStyle obtenerEstiloBordeTextoForzado(SXSSFWorkbook sxssfWorkbook){
		CellStyle estiloBordes = obtenerEstiloBordes(sxssfWorkbook);
		CellStyle estiloBordeFecha = sxssfWorkbook.createCellStyle();
		estiloBordeFecha.cloneStyleFrom(estiloBordes);
		estiloBordeFecha.setDataFormat(sxssfWorkbook.createDataFormat().getFormat("@"));
		return estiloBordeFecha;
	}
	
	public static CellStyle obtenerEstiloBordeFecha(SXSSFWorkbook sxssfWorkbook){
		CellStyle estiloBordes = obtenerEstiloBordes(sxssfWorkbook);
		CellStyle estiloBordeFecha = sxssfWorkbook.createCellStyle();
		estiloBordeFecha.cloneStyleFrom(estiloBordes);
		estiloBordeFecha.setDataFormat(sxssfWorkbook.createDataFormat().getFormat("dd/MM/yyyy"));
		return estiloBordeFecha;
	}
	
	public static CellStyle obtenerEstiloBordeFecha(SXSSFWorkbook sxssfWorkbook, String formato){
		CellStyle estiloBordes = obtenerEstiloBordes(sxssfWorkbook);
		CellStyle estiloBordeFecha = sxssfWorkbook.createCellStyle();
		estiloBordeFecha.cloneStyleFrom(estiloBordes);
		estiloBordeFecha.setDataFormat(sxssfWorkbook.createDataFormat().getFormat(formato));
		return estiloBordeFecha;
	}
	
	public static CellStyle obtenerEstiloBorderDecimal(SXSSFWorkbook sxssfWorkbook){
		CellStyle estiloBordes = obtenerEstiloBordes(sxssfWorkbook);
		CellStyle estiloBordeDecimal = sxssfWorkbook.createCellStyle();
		estiloBordeDecimal.cloneStyleFrom(estiloBordes);
		estiloBordeDecimal.setDataFormat(sxssfWorkbook.createDataFormat().getFormat("0.0000;[Red]-0.0000"));
		return estiloBordeDecimal;
	}
	
	public static CellStyle obtenerEstiloBorderDecimal(SXSSFWorkbook sxssfWorkbook, String formato){
		CellStyle estiloBordes = obtenerEstiloBordes(sxssfWorkbook);
		CellStyle estiloBordeDecimal = sxssfWorkbook.createCellStyle();
		estiloBordeDecimal.cloneStyleFrom(estiloBordes);
		estiloBordeDecimal.setDataFormat(sxssfWorkbook.createDataFormat().getFormat(formato));
		return estiloBordeDecimal;
	}

	private static String getSpaceBetween(String longWord, String shortWord1, String shortWord2) {
		int spaceNumber = (int) ((float) longWord.length() * 58 / 34) - (shortWord1.length() + shortWord2.length());
		StringBuilder space = new StringBuilder();
		for (int i = 0; i < spaceNumber; i++) {
			space.append(" ");
		}
		return space.toString();
	}
	
}

