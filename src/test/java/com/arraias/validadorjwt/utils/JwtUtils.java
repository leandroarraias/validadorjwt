package com.arraias.validadorjwt.utils;

import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;

import java.util.Map;

public class JwtUtils {

	public static String gerarJwt(Map<String, Object> claimsMap) {

		JwtClaims claims = new JwtClaims();

		for (String key : claimsMap.keySet()) {
			claims.setClaim(key, claimsMap.get(key));
		}

		JsonWebSignature jws = new JsonWebSignature();
		jws.setPayload(claims.toJson());
		jws.setKey(new HmacKey("QomFf4ivJSvqsUv5T2c8KJx7TDMKjaZzKuJ1STBBVlg=".getBytes()));
		jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);

		try {
			return jws.getCompactSerialization();
		} catch (JoseException e) {
			throw new RuntimeException(e);
		}
	}
}
