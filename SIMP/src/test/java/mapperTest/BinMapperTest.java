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
import ob.debitos.simp.mapper.IBinMapper;
import ob.debitos.simp.model.mantenimiento.Bin;
import ob.debitos.simp.model.parametro.Parametro;
import ob.debitos.simp.utilitario.Verbo;

@ContextConfiguration(classes = { ServiceConfiguration.class, PersistenceConfiguration.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class BinMapperTest
{
    private @Autowired IBinMapper binMapper;

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void mantenerTipoGets()
    {
        Bin bin = new Bin();
        bin.setCodigoInstitucion(20);
        Parametro parametro = new Parametro(Verbo.GETS, bin);
        List<Bin> bines = binMapper.mantener(parametro);
        bines.stream().forEach(System.out::println);
    }
}