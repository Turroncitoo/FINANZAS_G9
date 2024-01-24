package serviceTest;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ob.debitos.simp.configuracion.PersistenceConfiguration;
import ob.debitos.simp.configuracion.ServiceConfiguration;
import ob.debitos.simp.model.tarifario.EsquemaTar;
import ob.debitos.simp.service.IEsquemaTarService;

@ContextConfiguration(classes = { ServiceConfiguration.class, PersistenceConfiguration.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class EsquemaTarServiceTest
{

    private @Autowired IEsquemaTarService esquemaTarService;

    @Test
    public void test()
    {
        List<EsquemaTar> tipos = esquemaTarService.buscarTodos();
        tipos.stream().forEach(tipo -> {
            System.out.println(tipo.toString());
        });
    }
}
