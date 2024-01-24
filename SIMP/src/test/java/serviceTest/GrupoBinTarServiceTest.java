package serviceTest;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ob.debitos.simp.configuracion.PersistenceConfiguration;
import ob.debitos.simp.configuracion.ServiceConfiguration;
import ob.debitos.simp.model.tarifario.GrupoBinTar;
import ob.debitos.simp.service.IGrupoBinTarService;

@ContextConfiguration(classes = { ServiceConfiguration.class, PersistenceConfiguration.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class GrupoBinTarServiceTest
{

    private @Autowired IGrupoBinTarService grupoTarService;

    @Test
    public void test()
    {
        List<GrupoBinTar> grupos = grupoTarService.buscarTodos();
        grupos.stream().forEach(grupo -> {
            System.out.println(grupo.toString());
        });
    }
}
