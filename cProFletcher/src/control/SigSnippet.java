package control;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


import org.tbot.internal.handlers.LogHandler;
import org.tbot.util.TEnvironment;

public class SigSnippet {
	public static boolean sendSignatureData(long runtimeInSeconds, int var1, int var2, int var3, int var4) {

		// In order to provide some security, so that people will not tamper the data and post it themselves, we will be encrypting it here and decrypting it in php.
		// These keys should be the same as in PHP (db.php) (16bit long)
		String privateKey = "2b27az10je1abdui";
		String initVector = "255c973habsskdfa";

		try {
			// data we will be encrypting. you can remove the var's if you want (username and runtime are required though)
			String data = initVector+","+TEnvironment.getForumUsername()+","+runtimeInSeconds+","+var1+","+var2+","+var3+","+var4; // comma delimited so we can split in php

			// set up iv and key for encrypting
			IvParameterSpec ivspec = new IvParameterSpec(initVector.getBytes());
			SecretKeySpec keyspec = new SecretKeySpec(privateKey.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

			// encrypt
			cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
			byte[] encrypted = cipher.doFinal(data.getBytes("UTF-8"));
			
			// convert to hex
			String token = "";
			for (int i = 0; i < encrypted.length; i++) {
				if ((encrypted[i] & 0xFF) < 16) {
					token = token + "0" + java.lang.Integer.toHexString(encrypted[i] & 0xFF);
				} else {
					token = token + java.lang.Integer.toHexString(encrypted[i] & 0xFF);
				}
			}

			// And post it :)
			URL url = new URL("http://cheddarscript.noip.me:8080/cprofletcher/input.php?token="+token);
			URLConnection conn = url.openConnection();

			conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows; U; Windows NT 6.1; en-GB; rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13 (.NET CLR 3.5.30729)");
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			in.readLine();
			in.close();
			return true;
		} catch (Exception e) {
			LogHandler.log(e.getMessage());
		}
		LogHandler.log("No");
		return false;
	}
}
