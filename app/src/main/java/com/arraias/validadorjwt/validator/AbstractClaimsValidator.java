package com.arraias.validadorjwt.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public abstract class AbstractClaimsValidator {

	private final List<ClaimValidator> claimsRules;

	@Autowired
	protected AbstractClaimsValidator(List<ClaimValidator> claimsRules) {
		this.claimsRules = claimsRules;
	}

	protected List<ClaimValidator> getClaimsValidators() {
		return claimsRules;
	}

	protected ClaimValidator getClaimValidator(String claim) {

		for (ClaimValidator crv : getClaimsValidators()) {
			if (crv.getClaimKey().equalsIgnoreCase(claim)) {
				return crv;
			}
		}

		return null;

	}

	public abstract boolean validarClaims(Map<String, Object> claims);

}
