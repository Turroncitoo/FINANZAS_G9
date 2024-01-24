package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.SubBin;

public interface ISubBinService extends IMantenibleService<SubBin>
{
    public List<SubBin> buscarTodos();

    public List<SubBin> buscarPorCodigoBin(String codigoBIN);

    public List<SubBin> buscarPorCodigoBinCodigoSubBin(String codigoBIN, String codigoSubBIN);

    public boolean existeSubBin(String codigoBIN, String codigoSubBIN);

    public void registrarSubBin(SubBin subBin);

    public void actualizarSubBin(SubBin subBin);

    public void eliminarSubBin(SubBin subBin);
}