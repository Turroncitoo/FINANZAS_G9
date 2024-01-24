package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.SubBin;

public interface ISubBinClienteService extends IMantenibleService<SubBin>
{
    public List<SubBin> buscarAsociacionSubBinCliente(String codigoBIN, String codigoSubBIN,
            String idCliente, String idEmpresa);

    public List<SubBin> buscarAsociacionSubBinClientePorIdClienteIdEmpresa(String idCliente,
            String idEmpresa);

    public List<SubBin> buscarAsociacionSubBinCientePorCodigoBinIdClienteIdEmpresa(String codigoBIN,
            String idCliente, String idEmpresa);

    public boolean existeAsociacionSubBinCliente(String codigoBIN, String codigoSubBIN,
            String idCliente, String idEmpresa);

    public void asociarSubBinCliente(SubBin subBin);

    public void actualizarAsociacionSubBinCliente(SubBin subBin);

    public void eliminarAsociacionSubBinCliente(SubBin subBin);
}