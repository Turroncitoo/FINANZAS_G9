package ob.debitos.simp.service;

import java.util.List;

import javaslang.Tuple2;
import ob.debitos.simp.model.mantenimiento.CodigoTransaccion;

public interface ICodigoTransaccionService extends IMantenibleService<CodigoTransaccion>
{

    public List<CodigoTransaccion> buscarTodos();

    public List<CodigoTransaccion> buscarPorCodigoTransaccionCodigoClaseTransaccion(
            int codigoTransaccion, int codigoClaseTransaccion);

    public Tuple2<Boolean, Boolean> buscarCompensacionesPorCodigoTransaccionCodigoClaseTransaccion(
            int codigoTransaccion, int codigoClaseTransaccion);

    public List<CodigoTransaccion> buscarPorCodigoClaseTransaccion(int codigoClaseTransaccion);

    public boolean existeCodigoTransaccion(int codigoTransaccion, int codigoClaseTransaccion);

    public void registrarCodigoTransaccion(CodigoTransaccion codigoTransaccion);

    public void actualizarCodigoTransaccion(CodigoTransaccion codigoTransaccion);

    public void eliminarCodigoTransaccion(CodigoTransaccion codigoTransaccion);

}
