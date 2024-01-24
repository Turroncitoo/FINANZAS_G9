package ob.debitos.simp.utilitario;


import java.math.BigInteger;

import ob.debitos.simp.model.seguridad.ComponenteLlaveZMK;

public class KeyUtil {
	
	public static String generarLLaveZMK(ComponenteLlaveZMK componenteLlaveZMK) {
		
		String resultHexaZMK = "";
		for(int i=0; i<componenteLlaveZMK.getComponente1().length(); i++){
			// Obteniendo cada digito hexadecimal
			BigInteger componente1HexDigit = new BigInteger(String.valueOf(componenteLlaveZMK.getComponente1().charAt(i)), 16);
			BigInteger componente2HexDigit = new BigInteger(String.valueOf(componenteLlaveZMK.getComponente2().charAt(i)), 16);
			BigInteger componente3HexDigit = new BigInteger(String.valueOf(componenteLlaveZMK.getComponente3().charAt(i)), 16);
			
			// Convirtiendo cada digito hexadecimal a binario
			String componentBinary1 = KeyUtil.addZerosBinary(componente1HexDigit.toString(2));
			String componentBinary2 = KeyUtil.addZerosBinary(componente2HexDigit.toString(2));
			String componentBinary3 = KeyUtil.addZerosBinary(componente3HexDigit.toString(2));
			
			String resultBinaryDigit="";
			
			//Aplicando XOR a cada digito binario
			for(int j=0; j<componentBinary1.length(); j++){
				int digit1 = Integer.parseInt(Character.toString(componentBinary1.charAt(j)));
				int digit2 = Integer.parseInt(Character.toString(componentBinary2.charAt(j)));
				int digit3 = Integer.parseInt(Character.toString(componentBinary3.charAt(j)));
				int result = digit1 ^ digit2 ^ digit3;
				resultBinaryDigit = resultBinaryDigit + String.valueOf(result);
			}
			String resultHexDigit = KeyUtil.binaryToHex(resultBinaryDigit);
			resultHexaZMK = resultHexaZMK + resultHexDigit;
		}
		return resultHexaZMK.toUpperCase();
	}

	public static byte[] hexStringToByteArray(String s) {
		byte[] b = new byte[s.length() / 2];
		for (int i = 0; i < b.length; i++) {
			int index = i * 2;
			int v = Integer.parseInt(s.substring(index, index + 2), 16);
			b[i] = (byte) v;
		}
		return b;
	}
	
	public static String addZerosBinary(String binaryNumber){
		String strAdd="";
		switch(binaryNumber.length()){
			case 1:
				strAdd ="000";
				break;
			case 2:
				strAdd ="00";
				break;
			case 3:
				strAdd ="0";
				break;				
		}
		return strAdd+binaryNumber;
	}
	
	public static String binaryToHex(String strBinary){
        int decimal = Integer.parseInt(strBinary,2);
        String hexStr = Integer.toString(decimal,16);
        return hexStr;
	}
}
