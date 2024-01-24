package mapperTest;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.configuracion.PersistenceConfiguration;
import ob.debitos.simp.configuracion.ServiceConfiguration;
import ob.debitos.simp.mapper.IClaseServicioMapper;
import ob.debitos.simp.model.mantenimiento.ClaseServicio;
import ob.debitos.simp.model.parametro.Parametro;
import ob.debitos.simp.service.IClaseServicioService;
import ob.debitos.simp.utilitario.Verbo;

@ContextConfiguration(classes = { ServiceConfiguration.class, PersistenceConfiguration.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class ClaseServicioMapperTest
{

    private @Autowired IClaseServicioMapper claseServicioMapper;
    private @Autowired IClaseServicioService claseServicioService;

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void test()
    {
        ClaseServicio claseServicio = new ClaseServicio();
        claseServicio.setCodigoMembresia("P");
        Parametro parametro = new Parametro(Verbo.GETS, claseServicio);
        List<ClaseServicio> claseServicios = claseServicioMapper.mantener(parametro);
        claseServicios.stream().forEach(System.out::println);

    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void buscar()
    {
        List<ClaseServicio> clasesServicios = claseServicioService
                .buscarPorCodigoClaseServicioCodigoMembresia("A", "P");
        System.out.println(clasesServicios.isEmpty());
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void buscarTodosConMebresia()
    {
        List<ClaseServicio> claseServicios = claseServicioMapper
                .mantener(new Parametro(Verbo.GETS_T_MEM, new ClaseServicio()));
        claseServicios.stream().forEach(System.out::println);
    }

}
