package utilTest;

import java.text.DecimalFormat;

import javax.validation.constraints.Digits;

import org.junit.Test;

import ob.debitos.simp.utilitario.Regex;

public class ExpresionRegularTest
{    
    @Digits(integer = 10, fraction = 2)
    Double num;

    //@Test
    public void contieneSoloNumerosTest()
    {
        String numero = "00123,4";
        boolean esNumero = numero.matches("\\d+");
        System.out.println(esNumero);
    }

    //@Test
    public void contieneSoloEspaciosEnBlancoTest()
    {
        String cadena = "    ";
        boolean esValido = cadena.matches("^$|^(?=.*\\S).+$");
        System.out.println("Solo espacios en blanco: " + esValido);
    }

    //@Test
    public void formato4Decimales()
    {
        this.num = 0.83456789;
        System.out.println("Numero: " + num);
        DecimalFormat df = new DecimalFormat(".0000");
        System.out.println("Formatted decimal: " + df.format(12345.4));
    }

    //@Test
    public void contieneHHMMSS()
    {
        String hora = "23:54";
        boolean esCorrecto = hora.matches(Regex.FORMATO_HHMMSS);
        System.out.println("Es Correcto:" + esCorrecto);
    }

    //@Test
    public void contieneSoloNumerosJTest()
    {
        String cadena = "Aaaaaaaa";
        boolean esValido = cadena.matches("^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{1,}$");
        System.out.println("Password Seguro : " + esValido);
    }

    //@Test
    public void contieneEspaciosEnBlanco()
    {
        String cadena = "a a";
        boolean esValido = cadena.matches("^\\S+$");
        System.out.println(esValido);
    }
    
    //@Test
    public void regexGuionBajo()
    {
        String cadena = "a a";
        boolean esValido = cadena.matches("^[a-zA-Z0-9_]*$");
        System.out.println(esValido);
    }
  
    @Test
    public void regexOnlySpaceWithMiddle()
    {
        String cadena = "3";
        boolean esValido = cadena.matches("^[^[A-Za-z]+$]+(\\s+[^[A-Za-z]+$]+)*$");
        System.out.println(esValido);
    }
  
}
