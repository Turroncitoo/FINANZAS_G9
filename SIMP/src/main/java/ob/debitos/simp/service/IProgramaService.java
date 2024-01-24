package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.proceso.Programa;

public interface IProgramaService extends IMantenibleService<Programa>
{

    public List<Programa> buscarTodos();

    public List<Programa> buscarPorCodigo(String codigoGrupo, String codigoPrograma, String codigoSubModulo);

    public List<Programa> buscarPorGrupo(String codigoGrupo);

    public boolean existeCodigoPrograma(String codigoGrupo, String codigoPrograma, String codigoSubModulo);

    public void registrarPrograma(Programa programa);

    public void actualizarPrograma(Programa programa);

    public void eliminarPrograma(Programa programa);

    public boolean procesaSabado(String codigoGrupo, String codigoPrograma, String codigoSubModulo);

}