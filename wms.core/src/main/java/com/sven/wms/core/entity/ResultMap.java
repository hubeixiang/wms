package com.sven.wms.core.entity;

import java.util.HashMap;

/**
 * @author sven
 * @date 2019/2/15 14:43
 */
public class ResultMap<K, V> extends HashMap<K, V> {

	private static final long serialVersionUID = 9155660212774034975L;

	public ResultMap() {
	}

	public V put(K key, V value) {
		if (key instanceof String) {
			//noinspection unchecked
			key = (K) ((String) key).toUpperCase();
		}

		return super.put(key, value);
	}
}
