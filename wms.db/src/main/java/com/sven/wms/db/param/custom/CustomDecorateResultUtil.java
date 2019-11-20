package com.sven.wms.db.param.custom;

/**
 * @author sven
 * @date 2019/11/19 16:31
 */
public class CustomDecorateResultUtil {
	public static CustomDecorateResult copy(CustomDecorate customDecorate) {
		CustomDecorateResult result = new CustomDecorateResult();
		result.setCustomMethond(customDecorate.getCustomMethond());
		result.setDb(customDecorate.getDb());
		result.setSqlId(customDecorate.getSqlId());
		return result;
	}
}
