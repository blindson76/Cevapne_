package com.cevapne.cevapne;

import android.annotation.SuppressLint;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
public class crypt {

	private static RSAPublicKey pubKey=null;
	private static SecretKey aesKey=null;
	private static Cipher cipher=null;
	public static String cryptedKey;
	
	@SuppressLint("TrulyRandom")
	public static void init(InputStream is){
		if (pubKey==null){			
			DataInputStream dis = new DataInputStream(is);
			byte[] keyBytes = null;
			try {
				keyBytes = new byte[(int) is.available()];
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			 
			try {
				dis.readFully(keyBytes);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				dis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = null;
			try {
				keyFactory = KeyFactory.getInstance("RSA");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				pubKey = (RSAPublicKey)keyFactory.generatePublic(keySpec);
			} catch (InvalidKeySpecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			
		}
		
		if(aesKey==null){

			KeyGenerator kg = null;
			try {
				kg = KeyGenerator.getInstance("AES");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			kg.init(128);
			aesKey=kg.generateKey();
			
			Cipher rsaCipher = null;
			try {
				rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				rsaCipher.init(Cipher.ENCRYPT_MODE, pubKey);
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				cryptedKey = HexBin.encode(rsaCipher.doFinal(aesKey.getEncoded()));
			} catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		if(cipher==null){

			try {
				cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				cipher.init(Cipher.ENCRYPT_MODE,aesKey);
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static String encrypt(String text) {		
		byte[] cryptedMsg=null;
		try {
			cryptedMsg=cipher.doFinal(text.getBytes());
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return HexBin.encode(cryptedMsg);		
		
	}	

}
