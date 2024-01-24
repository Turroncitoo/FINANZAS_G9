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
import ob.debitos.simp.mapper.IMembresiaMapper;
import ob.debitos.simp.model.mantenimiento.Membresia;
import ob.debitos.simp.model.parametro.Parametro;
import ob.debitos.simp.utilitario.Verbo;

@ContextConfiguration(classes = { ServiceConfiguration.class, PersistenceConfiguration.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class MembresiaMapperTest
{

    private @Autowired IMembresiaMapper membresiaMapper;

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void mantenerTipoGets()
    {
        List<Membresia> membresias = membresiaMapper.mantener(new Parametro());
        membresias.stream().forEach(System.out::println);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void mantenerTipoGet()
    {
        List<Membresia> membresias = membresiaMapper
                .mantener(new Parametro(Verbo.GET, new Membresia()));
        membresias.stream().forEach(System.out::println);
    }

}
