package ob.debitos.simp.service.impl.mantenimiento;

import java.util.ArrayList;
import java.util.List;

import ob.debitos.simp.model.mantenimiento.Institucion;
import ob.debitos.simp.service.IInstitucionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IBinMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.Bin;
import ob.debitos.simp.service.IBinService;
import ob.debitos.simp.service.IParametroGeneralService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class BinService extends MantenibleService<Bin> implements IBinService
{
    @SuppressWarnings("unused")
    private IBinMapper binMapper;
    private @Autowired IParametroGeneralService parametroGeneralService;
    private @Autowired IBinService binService;
    private @Autowired IInstitucionService institucionService;

    public BinService(@Qualifier("IBinMapper") IMantenibleMapper<Bin> mapper)
    {
        super(mapper);
        this.binMapper = (IBinMapper) mapper;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Bin> buscarTodos()
    {
        Bin bin = Bin.builder().codigoInstitucion(parametroGeneralService.buscarCodigoInstitucion())
                .build();
        return this.buscar(bin, Verbo.GETS);
    }
    
    @Override
    public List<Bin> buscarTodosBinOcho()
    {
        Bin bin = Bin.builder().codigoInstitucion(parametroGeneralService.buscarCodigoInstitucion())
                .build();
        return this.buscar(bin, Verbo.GETS_OCHO);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Bin> buscarPorCodigoBIN(String codigoBIN)
    {
        Bin bin = Bin.builder().codigoBIN(codigoBIN).build();
        return this.buscar(bin, Verbo.GET);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Bin> buscarTodosConSubBIN()
    {
        Bin bin = Bin.builder().codigoInstitucion(parametroGeneralService.buscarCodigoInstitucion())
                     .build();
        return this.buscar(bin, Verbo.GETS_SUBB);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Bin> buscarPorIdInstitucion(Integer idInstitucion)
    {
        Bin bin = Bin.builder().codigoInstitucion(idInstitucion).build();
        return this.buscar(bin, Verbo.GETS_INST);
    }

    @Override
    public List<Bin> buscarPorMembresia(String codigoMembresia) {
        List<Institucion> institucionesTTP = institucionService.buscarPorInstitucionEmpresa();
        List<Bin> bines = new ArrayList<>();
        institucionesTTP.forEach(institucion -> bines.addAll(binService.buscarPorIdInstitucion(institucion.getCodigoInstitucion())));
        bines.removeIf(bin -> !bin.getCodigoMembresia().equals(codigoMembresia));
        return bines;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeBin(String codigoBIN)
    {
        return !this.buscarPorCodigoBIN(codigoBIN).isEmpty();
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Bin> buscarPorCodigoMembresiaCodigoClaseServicio(String codigoMembresia,
            String codigoClaseServicio, Integer idInstitucion)
    {
        Bin bin = Bin.builder().codigoMembresia(codigoMembresia)
                .codigoClaseServicio(codigoClaseServicio)
                .codigoInstitucion(idInstitucion).build();
        return this.buscar(bin, Verbo.GETS_IMS);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarBin(Bin bin)
    {
        this.registrar(bin);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarBin(Bin bin)
    {
        this.actualizar(bin);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarBin(Bin bin)
    {
        this.eliminar(bin);
    }
}