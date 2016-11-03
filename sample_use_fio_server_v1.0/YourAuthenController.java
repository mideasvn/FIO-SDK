package com.hana.reeng.v2.controller;

import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

/**
 * 
 * @author mideasvn
 *
 */

@RequestMapping("/authen")
@Controller
public class YourAuthenController {
	private static final Logger logger = Logger.getLogger(YourAuthenController.class);

	@Autowired
	private ApplicationContext context;
	@Autowired

	@RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String yourApi(@RequestParam("token") String token,
			Model uiModel, HttpServletRequest httpServletRequest) {
		try {
			String base64PriviteKey="YourPrivateKey";
			String yourSecretCode="yourSecrectCode";
			logger.info("[authenPartner] Return SUCCESS: " + token);
			if (token.equals("")) {
				return "{\"result\":\"0\"}";
			}
			//Decrypt token
			PrivateKey newPriviteKey = null;
			try {
				newPriviteKey = getPrivate(Base64.decodeBase64(base64PriviteKey));
			} catch (Exception e) {
				e.printStackTrace();
			}

			byte[] encyptTextReceive = Base64.decodeBase64(token);
			String dencyptText = decypt(encyptTextReceive, newPriviteKey);
			// conver string to JSON
			Gson gson = new Gson();

			PartnerRequest messageQueue = (PartnerRequest) gson.fromJson(dencyptText, PartnerRequest.class);
			logger.info("username " + messageQueue.getUsername() + "== userCredentials: "
					+ messageQueue.getUserCredentials() + "" + "== appId:" + messageQueue.getAppId() + " ==timestamp:"
					+ messageQueue.getTimestamp() + " ==code:" + messageQueue.getCode());
				
			if (messageQueue.getCode() != null && !messageQueue.getCode().equals("")) {
				String plantextCode = messageQueue.getAppId() + "_" + messageQueue.getTimestamp() + "_" + yourSecretCode;
				String md5Code = hashMD5(plantextCode);
				if (md5Code.equals(messageQueue.getCode())) {
					logger.info("Authen partner SUssess and Authen your username, usercredentials here");
					// Authen your username, usercredentials here
				} else {
					logger.info("Authen partner Fail");
					return "{\"result\":\"0\"}";
				}
			} else {
				return "{\"result\":\"0\"}";
			}
			return "{\"result\":\"1\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"result\":\"0\"}";
		}
	}

	
	private String hashMD5(String text) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(text.getBytes());

			byte byteData[] = md.digest();
			// convert the byte to hex format method 1
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			System.out.println("Digest(in hex format):: " + sb.toString());
			// convert the byte to hex format method 2
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				String hex = Integer.toHexString(0xff & byteData[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	public ApplicationContext getContext() {
		return context;
	}

	public void setContext(ApplicationContext context) {
		this.context = context;
	}

	public PrivateKey getPrivate(byte[] keyBytes) throws Exception {
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(spec);
	}

	public PublicKey getPublic(byte[] keyBytes) throws Exception {
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePublic(spec);
	}
	/**
	 * @Description: ma hoa
	 * @Author: hieuh1
	 */
	public  byte[] encypt(String text, Key publicKey) {
		byte[] cipherText = null;
		try {
			final Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			cipherText = cipher.doFinal(text.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cipherText;
	}
	/**
	 * @Description: giai ma
	 * @Author: hieuh1
	 */
	public String decypt(byte[] cipherText, Key privateKey) {
		byte[] dectyptedText = null;
		try {
			final Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			dectyptedText = cipher.doFinal(cipherText);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new String(dectyptedText);
	}

}
