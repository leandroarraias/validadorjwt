package com.arraias.validadorjwt.unittests;

import com.arraias.validadorjwt.validator.ClaimValidator;
import com.arraias.validadorjwt.validator.impl.DefaultStringRoleValidator;
import com.arraias.validadorjwt.validator.impl.enums.RolesEnum;
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
	private ClaimValidator ruleValidator;

	@Test
	void testRolesValidas() {
		assertTrue(ruleValidator.validar("Admin"));
		assertTrue(ruleValidator.validar("Member".toUpperCase()));
		assertTrue(ruleValidator.validar("External".toLowerCase()));
	}

	@Test
	void testRolesInvalidas() {
		assertFalse(ruleValidator.validar(RolesEnum.ADMIN.toString().concat("a")));
		assertFalse(ruleValidator.validar(null));
		assertFalse(ruleValidator.validar("1"));
	}

}
