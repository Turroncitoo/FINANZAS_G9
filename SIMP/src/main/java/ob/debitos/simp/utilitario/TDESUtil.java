package ob.debitos.simp.utilitario;

import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class TDESUtil {

    private String key;
      
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String encriptar(String message) throws Exception {
		
		final byte[] toEncryptArray = Arrays.copyOf(convertHexStringToByteArray(message),16);
		final byte[] keyBytes = Arrays.copyOf(convertHexStringToByteArray(key), 24);
		for (int j = 0,  k = 16; j < 8;)
        {
            keyBytes[k++] = keyBytes[j++];
        }
		final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
		final Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
		cipher.init(Cipher.ENCRYPT_MODE, key);

		final byte[] cipherText = cipher.doFinal(toEncryptArray);
		return convertByteArrayToHexString(cipherText);
	}

	public String desencriptar(String message) throws Exception {
		final byte[] toDecryptArray = Arrays.copyOf(convertHexStringToByteArray(message),16);
		final byte[] keyBytes = Arrays.copyOf(convertHexStringToByteArray(key), 24);
		for (int j = 0,  k = 16; j < 8;)
        {
            keyBytes[k++] = keyBytes[j++];
        }
		final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
		final Cipher decipher = Cipher.getInstance("DESede/ECB/NoPadding");
		decipher.init(Cipher.DECRYPT_MODE, key);
		final byte[] cipherText = decipher.doFinal(toDecryptArray);
		return convertByteArrayToHexString(cipherText);
	}


	public byte[] convertHexStringToByteArray(String hexString) {
		byte[] bytes = new byte[hexString.length() / 2];

		for (int i = 0; i < hexString.length(); i += 2)
		{
			String byteString = hexString.substring(i, i+2);
			bytes[i / 2] = (byte)Integer.parseInt(byteString, 16);
		}
		return bytes;
	}

	public String convertByteArrayToHexString(byte[] byteArray)
    {
      StringBuilder hex = new StringBuilder(byteArray.length * 2);
      for (byte b: byteArray)
        hex.append(String.format("%02X", b));
      return hex.toString();
    }
	
	public String obtenerKCV(String hexKey) throws Exception
	{
		final byte[] toEncryptArray = "\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0".getBytes("UTF-8");
		final byte[] keyBytes = Arrays.copyOf(convertHexStringToByteArray(hexKey), 24);
		for (int j = 0,  k = 16; j < 8;)
        {
            keyBytes[k++] = keyBytes[j++];
        }
		final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
		final Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
		cipher.init(Cipher.ENCRYPT_MODE, key);

		final byte[] cipherText = cipher.doFinal(toEncryptArray);
		return convertByteArrayToHexString(Arrays.copyOf(cipherText,3));
	}
}