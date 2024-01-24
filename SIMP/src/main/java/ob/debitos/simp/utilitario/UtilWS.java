package ob.debitos.simp.utilitario;

public class UtilWS
{
    
	private UtilWS()
    {
        throw new UnsupportedOperationException(ConstantesExcepciones.NO_INSTANCIAR_CLASE_ESTATICA);
    }
	
	/**
	 * Cambia NULL por "", o retorna el mismo valor
	 * @author Mario Cortez
	 */
	
	public static String replaceNullPorCadenaVacia(Object obj){
	    if(obj == null){
	    	return "";
	    }else{
	    	return obj.toString();
	    }
	}
   
}
