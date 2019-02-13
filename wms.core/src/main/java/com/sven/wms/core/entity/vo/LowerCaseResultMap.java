package com.sven.wms.core.entity.vo;

import java.util.HashMap;

/**
 * Created by FalconIA on 2016-11-30.
 */
public class LowerCaseResultMap<V> extends HashMap<String, V> {

	@Override
	public V put(String key, V value) {
		if (key == null || key.trim().length() == 0) {
			throw new IllegalArgumentException("Empty key is not allowed.");
		}

		key = key.toLowerCase();

		return super.put(key, value);
	}
}
