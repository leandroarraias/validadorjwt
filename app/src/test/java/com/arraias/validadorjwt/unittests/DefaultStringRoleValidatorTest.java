package com.arraias.validadorjwt.unittests;

import com.arraias.validadorjwt.enums.RolesEnum;
import com.arraias.validadorjwt.validator.impl.DefaultStringRoleValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {DefaultStringRoleValidator.class})
class DefaultStringRoleValidatorTest {

	@Autowired
	@Qualifier("defaultStringRoleValidator")
	private DefaultStringRoleValidator roleValidator;

	@Test
	void testRolesValidas() {
		assertTrue(roleValidator.validarRole("Admin"));
		assertTrue(roleValidator.validarRole("Member".toUpperCase()));
		assertTrue(roleValidator.validarRole("External".toLowerCase()));
	}

	@Test
	void testRolesInvalidas() {
		assertFalse(roleValidator.validarRole(RolesEnum.ADMIN.toString().concat("a")));
		assertFalse(roleValidator.validarRole(null));
		assertFalse(roleValidator.validarRole("1"));
	}

}
