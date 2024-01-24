package serviceTest;

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
import ob.debitos.simp.model.mantenimiento.SubBin;
import ob.debitos.simp.service.ISubBinService;

@ContextConfiguration(classes = { ServiceConfiguration.class, PersistenceConfiguration.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class SubBinServiceTest
{

    private @Autowired ISubBinService subBinService;

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void buscarPorCodigoBinTest()
    {
        List<SubBin> subBines = subBinService.buscarPorCodigoBin("407874");
        subBines.stream().forEach(System.out::println);
    }

}
