package com.nevreme.rolling.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.interceptor.SimpleKeyGenerator;

public class SiteCachableKeyGenerator extends SimpleKeyGenerator {

	@Override
	public Object generate(Object target, Method method, Object... params) {
		List<Object> p = new ArrayList<>();
		p.add(method.getName());
		if (params != null) {
			for (Object param : params) {
				p.add(param);
				// if (param instanceof Long) {
				// Long h = (Long) param;
				// if (h != null) {
				// p.add(h.longValue());
				// }
				// }
			}
		}
		return p;
	}
}
