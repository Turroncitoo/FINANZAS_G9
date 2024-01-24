package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.CodigoTransaccionBevertec;

public interface ICodigoTxnBevertecService extends IMantenibleService<CodigoTransaccionBevertec>
{

    public List<CodigoTransaccionBevertec> buscarTodos();

    public List<CodigoTransaccionBevertec> buscarPorCodigoCanalEmisor(String codigoCanalEmisor);

    public List<CodigoTransaccionBevertec> buscarPorCodigoCanalEmisorTipoTransaccionCodTransaccion(
            String codigoCanalEmisor, String tipoTransaccion, String codTransaccion);

    public boolean existeCodigoTxnBevertec(String codigoCanalEmisor, String tipoTransaccion,
            String codTransaccion);

    public void registrarCodigoTxnBevertec(CodigoTransaccionBevertec codigoTxnBevertec);

    public void actualizarCodigoTxnBevertec(CodigoTransaccionBevertec codigoTxnBevertec);

    public void eliminarCodigoTxnBevertec(CodigoTransaccionBevertec codigoTxnBevertec);
}