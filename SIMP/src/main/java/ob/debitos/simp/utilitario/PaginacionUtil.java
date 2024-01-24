package ob.debitos.simp.utilitario;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import ob.debitos.simp.model.paginacion.Columna;
import ob.debitos.simp.model.paginacion.CriterioOrdenamiento;
import ob.debitos.simp.model.paginacion.CriterioPaginacion;
import ob.debitos.simp.model.paginacion.ItemPagina;
import ob.debitos.simp.model.paginacion.Pagina;
import ob.debitos.simp.model.paginacion.filtro.FiltroDtTxnsCompensacion;
import ob.debitos.simp.model.paginacion.filtro.FiltroDtTxnsSwdmplog;

public class PaginacionUtil
{

    private PaginacionUtil()
    {
        throw new UnsupportedOperationException(ConstantesExcepciones.NO_INSTANCIAR_CLASE_ESTATICA);
    }

    public static <T, S> Pagina generarPagina(List<? extends ItemPagina> data, CriterioPaginacion<T, S> criterioPaginacion)
    {
        Integer totalFilas = data.isEmpty() ? 0 : data.get(0).getTotalFilas();
        Pagina pagina = new Pagina();
        pagina.setDraw(criterioPaginacion.getDraw());
        pagina.setData(data);
        pagina.setRecordsFiltered(totalFilas);
        pagina.setRecordsTotal(totalFilas);
        return pagina;
    }

    public static <T, S> String obtenerExpresionOrdenamiento(CriterioPaginacion<T, S> criterioPaginacion)
    {
        String expresionOrdenamiento = "";
        List<CriterioOrdenamiento> criteriosOrdenamiento = criterioPaginacion.getOrder();
        List<Columna> columnas = criterioPaginacion.getColumns();
        List<String> expresiones = new ArrayList<>();
        if (criteriosOrdenamiento != null)
        {
            criteriosOrdenamiento.stream().forEach(co -> {
                Integer columna = co.getColumn();
                String data = columnas.get(columna).getData();
                expresiones.add(data + " " + co.getDir());
            });
        }
        expresionOrdenamiento = StringUtils.join(expresiones, ",");
        return expresionOrdenamiento;
    }

    public static <T, S> FiltroDtTxnsCompensacion armarFiltroDatatableLogContable(CriterioPaginacion<T, S> criterioPaginacion)
    {
        FiltroDtTxnsCompensacion filtro = new FiltroDtTxnsCompensacion();
        List<Columna> columnas = criterioPaginacion.getColumns();
        filtro.setFechaProceso(PaginacionUtil.obtenerValor(columnas, "fechaProceso"));
        filtro.setInstitucion(PaginacionUtil.obtenerValor(columnas, "codigoInstitucion"));
        filtro.setEmpresa(PaginacionUtil.obtenerValor(columnas, "idEmpresa"));
        filtro.setCliente(PaginacionUtil.obtenerValor(columnas, "idCliente"));
        filtro.setLogo(PaginacionUtil.obtenerValor(columnas, "idLogo"));
        filtro.setProducto(PaginacionUtil.obtenerValor(columnas, "idProducto"));
        filtro.setMembresia(PaginacionUtil.obtenerValor(columnas, "codigoMembresia"));
        filtro.setServicio(PaginacionUtil.obtenerValor(columnas, "codigoClaseServicio"));
        filtro.setOrigen(PaginacionUtil.obtenerValor(columnas, "codigoOrigen"));
        filtro.setClaseTransaccion(PaginacionUtil.obtenerValor(columnas, "idClaseTransaccion"));
        filtro.setCodigoTransaccion(PaginacionUtil.obtenerValor(columnas, "idCodigoTransaccion"));
        filtro.setRolTransaccion(PaginacionUtil.obtenerValor(columnas, "rolTransaccion"));
        filtro.setInstitucionEmisora(PaginacionUtil.obtenerValor(columnas, "codigoInstitucionEmisor"));
        filtro.setInstitucionReceptora(PaginacionUtil.obtenerValor(columnas, "codigoInstitucionReceptor"));
        filtro.setCodigoRespuesta(PaginacionUtil.obtenerValor(columnas, "codigoRespuestaSwitch"));
        filtro.setCanal(PaginacionUtil.obtenerValor(columnas, "idCanal"));
        filtro.setTipoDocumento(PaginacionUtil.obtenerValor(columnas, "tipoDocumento"));
        filtro.setNumeroDocumento(PaginacionUtil.obtenerValor(columnas, "numeroDocumento"));
        filtro.setSecuenciaTransaccion(PaginacionUtil.obtenerValor(columnas, "secuenciaTransaccion"));
        filtro.setCodigoSeguimiento(PaginacionUtil.obtenerValor(columnas, "codigoSeguimiento"));
        filtro.setNumeroTarjeta(PaginacionUtil.obtenerValor(columnas, "numeroTarjeta"));
        filtro.setNumeroCuenta(PaginacionUtil.obtenerValor(columnas, "numeroCuenta"));
        filtro.setEstadoTarjeta(PaginacionUtil.obtenerValor(columnas, "estadoTarjeta"));
        filtro.setFechaTransaccion(PaginacionUtil.obtenerValor(columnas, "fechaTransaccion"));
        filtro.setNumeroVoucher(PaginacionUtil.obtenerValor(columnas, "numeroVoucher"));
        filtro.setCodigoAutorizacion(PaginacionUtil.obtenerValor(columnas, "codigoAutorizacion"));
        filtro.setMonedaTransaccion(PaginacionUtil.obtenerValor(columnas, "monedaTransaccion"));
        filtro.setValorTransaccion(PaginacionUtil.obtenerValor(columnas, "valorTransaccion"));
        filtro.setMonedaAutorizacion(PaginacionUtil.obtenerValor(columnas, "monedaAutorizacion"));
        filtro.setValorAutorizacion(PaginacionUtil.obtenerValor(columnas, "valorAutorizacion"));
        filtro.setMonedaCompensacion(PaginacionUtil.obtenerValor(columnas, "monedaCompensacion"));
        filtro.setValorCompensacion(PaginacionUtil.obtenerValor(columnas, "valorCompensacion"));
        filtro.setMonedaCargadaThb(PaginacionUtil.obtenerValor(columnas, "monedaCargadaThb"));
        filtro.setValorCargadoThb(PaginacionUtil.obtenerValor(columnas, "valorCargadoThb"));
        filtro.setTipoDeCambio(PaginacionUtil.obtenerValor(columnas, "tipoDeCambio"));
        filtro.setNombreAfiliado(PaginacionUtil.obtenerValor(columnas, "nombreAfiliado"));
        filtro.setNumeroDocumentoCompensacion(PaginacionUtil.obtenerValor(columnas, "numeroDocumentoCompensacion"));
        filtro.setCompensaFondos(PaginacionUtil.obtenerValor(columnas, "compensaFondos"));
        filtro.setCompensaComisiones(PaginacionUtil.obtenerValor(columnas, "compensaComisiones"));
        return filtro;
    }

    public static <T, S> FiltroDtTxnsSwdmplog armarFiltroDatatableSwdmplog(CriterioPaginacion<T, S> criterioPaginacion)
    {
        FiltroDtTxnsSwdmplog filtro = new FiltroDtTxnsSwdmplog();
        List<Columna> columnas = criterioPaginacion.getColumns();
        filtro.setFechaProceso(PaginacionUtil.obtenerValor(columnas, "fechaProceso"));
        filtro.setInstitucion(PaginacionUtil.obtenerValor(columnas, "codigoInstitucion"));
        filtro.setEmpresa(PaginacionUtil.obtenerValor(columnas, "idEmpresa"));
        filtro.setCliente(PaginacionUtil.obtenerValor(columnas, "idCliente"));
        filtro.setLogo(PaginacionUtil.obtenerValor(columnas, "idLogo"));
        filtro.setProducto(PaginacionUtil.obtenerValor(columnas, "idProducto"));
        filtro.setRolTransaccion(PaginacionUtil.obtenerValor(columnas, "rolTransaccion"));
        filtro.setCodigoProceso(PaginacionUtil.obtenerValor(columnas, "idProceso"));
        filtro.setCodigoRespuesta(PaginacionUtil.obtenerValor(columnas, "codigoRespuesta"));
        filtro.setCanal(PaginacionUtil.obtenerValor(columnas, "idCanal"));
        filtro.setFechaTransmision(PaginacionUtil.obtenerValor(columnas, "fechaTransmision"));
        filtro.setTipoMensaje(PaginacionUtil.obtenerValor(columnas, "tipoMensaje"));
        filtro.setTipoDocumento(PaginacionUtil.obtenerValor(columnas, "tipoDocumento"));
        filtro.setNumeroDocumento(PaginacionUtil.obtenerValor(columnas, "numeroDocumento"));
        filtro.setNombres(PaginacionUtil.obtenerValor(columnas, "nombres"));
        filtro.setApellidoPaterno(PaginacionUtil.obtenerValor(columnas, "apellidoPaterno"));
        filtro.setApellidoMaterno(PaginacionUtil.obtenerValor(columnas, "apellidoMaterno"));
        filtro.setNumeroTarjeta(PaginacionUtil.obtenerValor(columnas, "numeroTarjeta"));
        filtro.setCodigoSeguimiento(PaginacionUtil.obtenerValor(columnas, "codigoSeguimiento"));
        filtro.setIdentificadorCuenta(PaginacionUtil.obtenerValor(columnas, "identificadorCuenta"));
        filtro.setEstadoTarjeta(PaginacionUtil.obtenerValor(columnas, "estadoTarjeta"));
        filtro.setMoneda(PaginacionUtil.obtenerValor(columnas, "moneda"));
        filtro.setImporte(PaginacionUtil.obtenerValor(columnas, "importe"));
        filtro.setGiroNegocio(PaginacionUtil.obtenerValor(columnas, "giroNegocio"));
        filtro.setTrace(PaginacionUtil.obtenerValor(columnas, "trace"));
        filtro.setCodigoAutorizacion(PaginacionUtil.obtenerValor(columnas, "codigoAutorizacion"));
        filtro.setDescripcionAdquirente(PaginacionUtil.obtenerValor(columnas, "descripcionAdquirente"));
        filtro.setInstitucionAdquirente(PaginacionUtil.obtenerValor(columnas, "institucionAdquirente"));
        filtro.setInstitucionSolicitante(PaginacionUtil.obtenerValor(columnas, "institucionSolicitante"));
        filtro.setFechaSwitch(PaginacionUtil.obtenerValor(columnas, "fechaSwitch"));
        filtro.setFechaTransaccionLocal(PaginacionUtil.obtenerValor(columnas, "fechaTransaccionLocal"));
        filtro.setModoIngresoPan(PaginacionUtil.obtenerValor(columnas, "panEntryMode"));
        filtro.setCapacidadIngresoPan(PaginacionUtil.obtenerValor(columnas, "pinEntryCapability"));
        filtro.setCuentaCargo(PaginacionUtil.obtenerValor(columnas, "cuentaCargo"));
        filtro.setCuentaAbono(PaginacionUtil.obtenerValor(columnas, "cuentaAbono"));
        filtro.setCodigoAnalitico(PaginacionUtil.obtenerValor(columnas, "codigoAnalitico"));
        return filtro;
    }

    public static String obtenerValor(List<Columna> columnas, String nombre)
    {
        if (columnas != null)
        {
            Columna c = columnas.stream().filter(co -> co.getData().equals(nombre)).findFirst().orElse(null);
            if (c != null)
            {
                return c.getSearch().getValue();
            }
            return "";
        }
        return "";
    }

    public static <T, S> CriterioPaginacion<T, S> getCriterioPaginacionParaReporteXLSX(T criterioBusqueda, int inicio, int tamanio)
    {
        CriterioPaginacion<T, S> criterioPaginacion = new CriterioPaginacion<>();
        criterioPaginacion.setCriterioBusqueda(criterioBusqueda);
        criterioPaginacion.setStart(inicio);
        criterioPaginacion.setLength(tamanio);
        return criterioPaginacion;
    }

}
