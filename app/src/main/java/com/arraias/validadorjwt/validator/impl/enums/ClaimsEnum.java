package com.arraias.validadorjwt.enums;

public enum ClaimsEnum {

	NAME,
	ROLE,
	SEED;

	public static ClaimsEnum get(String claimName) {

		for (ClaimsEnum claim : values()) {
			if (claim.name().equalsIgnoreCase(claimName)) {
				return claim;
			}
		}

		return null;

	}

}
