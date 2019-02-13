package com.sven.wms.core.entity;

import java.util.HashMap;

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
