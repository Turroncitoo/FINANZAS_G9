package serviceTest;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ob.debitos.simp.configuracion.PersistenceConfiguration;
import ob.debitos.simp.configuracion.ServiceConfiguration;
import ob.debitos.simp.model.proceso.Programa;
import ob.debitos.simp.service.IProgramaService;

@ContextConfiguration(classes = { ServiceConfiguration.class, PersistenceConfiguration.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class ProgramaServiceTest
{
    private @Autowired IProgramaService programaService;

    // @Test
    public void test()
    {
        List<Programa> programas = programaService.buscarTodos();
        programas.stream().forEach(programa -> {
            System.out.println(programa.toString());
        });
    }

    @Test
    public void registrarTest()
    {
        Programa programa = Programa.builder().codigoPrograma("PRUE").codigoGrupo("COMP")
                .codigoSubModulo("CONC").procedimiento("P_PRUEBA").descripcion("DESCRIPCION PRUEBA")
                .archivo("[INST].PRUEBA.DAT").ordenEjecucion(5).periodoEjecucion("N")
                .procesaSabado(0).longitud(350).build();
        programaService.registrarPrograma(programa);
    }
}
