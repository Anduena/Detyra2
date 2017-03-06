import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DES {
	byte[] skey = new byte[1000];
	String skeyString;
	static byte[] raw;
	private String inputMessage;
	String enc, dec;
	private static final String FILENAME = "hello.txt";
	private static final String FILENAME2 = "hello2.txt";
	static String row;

	public DES(String input) {
		inputMessage = input;
		try {
			generateSymmetricKey();
			byte[] ibyte = inputMessage.getBytes();
			byte[] ebyte = encrypt(raw, ibyte);
			String encryptedData = new String(ebyte);

			enc = encryptedData;
			byte[] dbyte = decrypt(raw, ebyte);
			String decryptedMessage = new String(dbyte);
	
			dec = decryptedMessage;
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	void generateSymmetricKey() {
		try {
			Random r = new Random();
			int num = r.nextInt(10000);
			String knum = String.valueOf(num);
			byte[] knumb = knum.getBytes();
			skey = getRawKey(knumb);
			skeyString = new String(skey);
			System.out.println("DES key = " + skeyString);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private static byte[] getRawKey(byte[] seed) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("DES");
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		sr.setSeed(seed);
		kgen.init(56, sr);
		SecretKey skey = kgen.generateKey();
		raw = skey.getEncoded();
		return raw;
	}

	private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "DES");
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(clear);
		return encrypted;
	}

	private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "DES");
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] decrypted = cipher.doFinal(encrypted);
		return decrypted;
	}

	public String getEncryption() {
		return enc;
	}

	public String getDecryption() {
		return dec;
	}
	public static void main(String[] args){
		try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
			row = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		test1 des = new test1(row);
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILENAME2))) {
			bw.write("DES \n");
			bw.write("Encrypted text: " + des.getEncryption() + "\n");
			bw.write("Decrypted text: " + des.getDecryption() + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}