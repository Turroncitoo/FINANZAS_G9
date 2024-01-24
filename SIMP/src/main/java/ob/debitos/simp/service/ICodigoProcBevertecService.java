package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.CodigoProcesoBevertec;

public interface ICodigoProcBevertecService extends IMantenibleService<CodigoProcesoBevertec>
{

    public List<CodigoProcesoBevertec> buscarTodos();

    public List<CodigoProcesoBevertec> buscarPorCodigoCanalEmisor(String codigoCanalEmisor);

    public List<CodigoProcesoBevertec> buscarPorCodigoCanalEmisorTipoTransaccion(
            String codigoCanalEmisor, String tipoTransaccion);

    public boolean existeProcBevertec(String codigoCanalEmisor, String tipoTransaccion);

    public void registrarCodigoProcBevertec(CodigoProcesoBevertec codigoProcBevertec);

    public void actualizarCodigoProcBevertec(CodigoProcesoBevertec codigoProcBevertec);

    public void eliminarCodigoProcBevertec(CodigoProcesoBevertec codigoProcBevertec);
}