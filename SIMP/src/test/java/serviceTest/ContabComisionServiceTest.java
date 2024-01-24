package serviceTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.configuracion.PersistenceConfiguration;
import ob.debitos.simp.configuracion.ServiceConfiguration;
import ob.debitos.simp.service.ICuentaContableEmisorService;

@ContextConfiguration(classes = { ServiceConfiguration.class, PersistenceConfiguration.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class ContabComisionServiceTest
{

    private @Autowired ICuentaContableEmisorService contabComisionService;

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void test()
    {
        System.out.println(contabComisionService.buscarTodos());
    }

}
