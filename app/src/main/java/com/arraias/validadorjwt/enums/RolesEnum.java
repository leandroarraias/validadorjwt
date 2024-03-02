package com.arraias.validadorjwt.enums;

public enum RolesEnum {

	ADMIN,
	MEMBER,
	EXTERNAL;

	public static RolesEnum get(String roleName) {

		for (RolesEnum role : values()) {
			if (role.name().equalsIgnoreCase(roleName)) {
				return role;
			}
		}

		return null;

	}
}
