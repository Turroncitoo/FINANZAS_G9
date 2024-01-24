package ob.debitos.simp.utilitario;

import java.util.List;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class CustomSpringExpressionLanguageParserUtil
{
    private static final String separadorAtributos = ",";
    private static final String separadorAtributoValor = "=";
    private static final String iniciador = "[";
    private static final String finalizador = "]";
    private static final String separadorPalabra = "_";

    public static String getDynamicValue(String[] parameterNames, Object[] args, String datos)
    {
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < parameterNames.length; i++)
        {
            context.setVariable(parameterNames[i], args[i]);
        }
        List<String> atributos = StringsUtils.obtenerTokens(datos, separadorAtributos);
        String mensajePreliminar = iniciador;
        for (String atributo : atributos)
        {
            boolean existePropiedad = true;
            Object oValorAtributo = null;
            String nombreAtributo = StringsUtils.obtenerCadenaAntesDe(atributo,
                    separadorAtributoValor);
            String valorAtributoSpel = StringsUtils.obtenerCadenaDespuesDe(atributo,
                    separadorAtributoValor);
            try
            {
                oValorAtributo = parser.parseExpression(valorAtributoSpel).getValue(context,
                        Object.class);
            } catch (SpelEvaluationException ex)
            {
                existePropiedad = false;
            }
            if (existePropiedad && oValorAtributo != null)
            {
                String valorAtributo = String.valueOf(oValorAtributo);
                mensajePreliminar = StringsUtils.concatenarCadena(mensajePreliminar,
                        nombreAtributo.replace(separadorPalabra, " "), ":",
                        valorAtributo.isEmpty() ? "''" : valorAtributo, ",");
            }
        }
        String mensajeAuditar = StringsUtils.removerUltimoCaracter(mensajePreliminar);
        return StringsUtils.concatenarCadena(mensajeAuditar, finalizador);
    }
}