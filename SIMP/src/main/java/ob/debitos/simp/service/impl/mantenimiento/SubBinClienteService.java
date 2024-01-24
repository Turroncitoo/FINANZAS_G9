package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.ISubBinMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.SubBin;
import ob.debitos.simp.service.ISubBinClienteService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class SubBinClienteService extends MantenibleService<SubBin> implements ISubBinClienteService
{
    @SuppressWarnings("unused")
    private ISubBinMapper subBinMapper;

    public SubBinClienteService(@Qualifier("ISubBinMapper") IMantenibleMapper<SubBin> mapper)
    {
        super(mapper);
        this.subBinMapper = (ISubBinMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<SubBin> buscarAsociacionSubBinCliente(String codigoBIN, String codigoSubBIN,
            String idCliente, String idEmpresa)
    {
        SubBin subBin = SubBin.builder().codigoBIN(codigoBIN).codigoSubBIN(codigoSubBIN)
                .idCliente(idCliente).idEmpresa(idEmpresa).build();
        return this.buscar(subBin, Verbo.GET_SBC);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<SubBin> buscarAsociacionSubBinClientePorIdClienteIdEmpresa(String idCliente,
            String idEmpresa)
    {
        SubBin subBin = SubBin.builder().idCliente(idCliente).idEmpresa(idEmpresa).build();
        return this.buscar(subBin, Verbo.GETS_SBC);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<SubBin> buscarAsociacionSubBinCientePorCodigoBinIdClienteIdEmpresa(String codigoBIN,
            String idCliente, String idEmpresa)
    {
        SubBin subBin = SubBin.builder().codigoBIN(codigoBIN).idCliente(idCliente)
                .idEmpresa(idEmpresa).build();
        return this.buscar(subBin, Verbo.GET_T_SBC);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeAsociacionSubBinCliente(String codigoBIN, String codigoSubBIN,
            String idCliente, String idEmpresa)
    {
        return !this.buscarAsociacionSubBinCliente(codigoBIN, codigoSubBIN, idCliente, idEmpresa)
                .isEmpty();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void asociarSubBinCliente(SubBin subBin)
    {
        this.mantener(subBin, Verbo.INSERT_SBC);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarAsociacionSubBinCliente(SubBin subBin)
    {
        SubBin subBinAntiguo = SubBin.builder().codigoBIN(subBin.getCodigoBINAntiguo())
                .codigoSubBIN(subBin.getCodigoSubBINAntiguo()).idCliente(subBin.getIdCliente())
                .idEmpresa(subBin.getIdEmpresa())
                .build();
        this.eliminarAsociacionSubBinCliente(subBinAntiguo);
        this.asociarSubBinCliente(subBin);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarAsociacionSubBinCliente(SubBin subBin)
    {
        this.mantener(subBin, Verbo.REMOVE_SBC);
    }
}