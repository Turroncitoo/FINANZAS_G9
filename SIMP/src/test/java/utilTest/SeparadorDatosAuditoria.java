package utilTest;

import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import org.junit.Test;

public class SeparadorDatosAuditoria
{
    private final String separadorAtributos = ",";
    private final String separadorAtributoValor = "=";
    private String datos = "Codigo_BIN = #bin.codigoBIN, descripcion = #bin.descripcion";

    // @Test
    public void separarDatosAuditoria()
    {
        String datosSinEspacios = datos.replaceAll("\\s+", "");
        List<String> tokens = Collections
                .list(new StringTokenizer(datosSinEspacios, separadorAtributos)).stream()
                .map(token -> (String) token).collect(Collectors.toList());
        for (String token : tokens)
        {
            String valorAtributo = token.substring(token.lastIndexOf(separadorAtributoValor) + 1);
            String nombreAtributo = token.substring(0, token.lastIndexOf(separadorAtributoValor));
            System.out.println(valorAtributo);
            System.out.println(nombreAtributo);
            System.out.println(nombreAtributo.replace("_", " "));
        }
        System.out.println(datosSinEspacios);
    }

    @Test
    public void formatString()
    {
        String registro = "Se ha registrado el(a) %s '%s'.";
        System.out.println(
                String.format(registro, "BIN", "[Codigo BIN: 404910, descripcion: DEBITO - EMF]"));
    }
}
