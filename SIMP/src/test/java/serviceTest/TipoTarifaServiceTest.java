package serviceTest;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ob.debitos.simp.configuracion.PersistenceConfiguration;
import ob.debitos.simp.configuracion.ServiceConfiguration;
import ob.debitos.simp.model.tarifario.TipoTarifa;
import ob.debitos.simp.service.ITipoTarifaService;

@ContextConfiguration(classes = { ServiceConfiguration.class, PersistenceConfiguration.class })
@RunWith(SpringJUnit4ClassRunner.class)

public class TipoTarifaServiceTest
{
    private @Autowired ITipoTarifaService tipoTarifaService;

    @Test
    public void test()
    {
        List<TipoTarifa> tipos = tipoTarifaService.buscarTodos();
        tipos.stream().forEach(tipo -> {
            System.out.println(tipo.toString());
        });
    }
}
