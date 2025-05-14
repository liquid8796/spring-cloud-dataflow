package org.springframework.cloud.dataflow.server.utils;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class ObjectUtil {

	private static final ExpressionParser parser = new SpelExpressionParser();

	public static List<String> getAllPropertyNames(Object obj) {
		List<String> propertyNames = new ArrayList<>();

		try {
			for (PropertyDescriptor pd : Introspector.getBeanInfo(obj.getClass(), Object.class).getPropertyDescriptors()) {
				propertyNames.add(pd.getName());
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}

		return propertyNames;
	}

	public static Object getValue(Object obj, String propertyPath) {
		try {
			StandardEvaluationContext context = new StandardEvaluationContext(obj);
			return parser.parseExpression(propertyPath).getValue(context);
		} catch (Exception e) {
			return null;
		}
	}
}
