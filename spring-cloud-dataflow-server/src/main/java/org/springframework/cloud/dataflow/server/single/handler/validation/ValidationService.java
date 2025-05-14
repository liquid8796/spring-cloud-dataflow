package org.springframework.cloud.dataflow.server.single.handler.validation;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.ConstraintViolation;
import java.util.Set;

public class ValidationService {

	private Validator validator;

	public ValidationService() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		this.validator = factory.getValidator();
	}

	public <T> Set<ConstraintViolation<T>> validate(T object) {
		return validator.validate(object);
	}
}

