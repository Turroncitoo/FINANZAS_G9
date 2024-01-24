package utilTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;

import ob.debitos.simp.model.mantenimiento.ContabConceptoCuenta;

public class ValidatorUtilTest
{
    @Test
    public void test()
    {

        ContabConceptoCuenta c = new ContabConceptoCuenta();
        ContabConceptoCuenta d = new ContabConceptoCuenta();
        ContabConceptoCuenta e = new ContabConceptoCuenta();
        c.setIdConceptoComision(1);
        d.setIdConceptoComision(2);
        e.setIdConceptoComision(2);
        List<ContabConceptoCuenta> listas = new ArrayList<>();
        listas.add(c);
        listas.add(d);
        listas.add(e);
        Set<Integer> duplicados = new HashSet<>();
        Set<Integer> codigosConceptoNoDuplicados = listas.stream().filter(
                a -> a.getIdConceptoComision() != 0 && !duplicados.add(a.getIdConceptoComision()))
                .map(b -> b.getIdConceptoComision()).collect(Collectors.toSet());
        System.out.println(codigosConceptoNoDuplicados);

    }
}
