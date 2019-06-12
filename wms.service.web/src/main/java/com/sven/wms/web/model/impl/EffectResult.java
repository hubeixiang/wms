package com.sven.wms.web.model.impl;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * @author sven
 * @date 2019/5/17 15:04
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class EffectResult {
	private int effectNumber = 0;

	public int getEffectNumber() {
		return effectNumber;
	}

	public void setEffectNumber(int effectNumber) {
		this.effectNumber = effectNumber;
	}
}
