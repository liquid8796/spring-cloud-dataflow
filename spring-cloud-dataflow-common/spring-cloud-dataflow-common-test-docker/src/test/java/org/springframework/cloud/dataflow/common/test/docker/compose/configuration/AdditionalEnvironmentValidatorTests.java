/*
 * Copyright 2018-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.cloud.dataflow.common.test.docker.compose.configuration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.HashMap;
import java.util.Map;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.cloud.dataflow.common.test.docker.compose.configuration.AdditionalEnvironmentValidator;

public class AdditionalEnvironmentValidatorTests {

   @Rule
   public ExpectedException exception = ExpectedException.none();

	@Test
	public void throw_exception_when_additional_environment_variables_contain_docker_variables() {
		Map<String, String> variables = new HashMap<>();
		variables.put("DOCKER_HOST", "tcp://some-host:2376");
		variables.put("SOME_VARIABLE", "Some Value");
	   exception.expect(IllegalStateException.class);
	   exception.expectMessage("The following variables");
	   exception.expectMessage("DOCKER_HOST");
	   exception.expectMessage("cannot exist in your additional environment");
		AdditionalEnvironmentValidator.validate(variables);
	}

	@Test
	public void validate_arbitrary_environment_variables() {
		Map<String, String> variables = new HashMap<>();
		variables.put("SOME_VARIABLE", "Some Value");

		assertThat(AdditionalEnvironmentValidator.validate(variables), is(variables));
	}
}
