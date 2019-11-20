package com.sven.wms.db.service;

import com.sven.wms.db.param.custom.CustomDecorate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author sven
 * @date 2019/11/19 15:26
 */
@Service("customDecorateService")
public class CustomDecorateService {
	@Autowired
	CustomService customService;

	/**
	 * 事务批量执行,返回错误数据，及其错误原因
	 *
	 * @param context
	 * @return
	 */
	@Transactional
	public void batchTransaction(List<CustomDecorate> context) {
		for (CustomDecorate customDecorate : context) {
			switch (customDecorate.getCustomMethond()) {
			case insert:
				customService.insert(customDecorate);
				break;
			case update:
				customService.update(customDecorate);
				break;
			case delete:
				customService.delete(customDecorate);
				break;
			case select:
				customService.select(customDecorate);
				break;
			default:
				throw new RuntimeException(String.format("错误的CustomMethod=%s", customDecorate.getCustomMethond()));
			}
		}
	}

	/**
	 * 非事务执行,遇到错误的sql,返回错误数据，及其错误原因,后续sql不在执行
	 *
	 * @param context
	 * @return
	 */
	public void batch(List<CustomDecorate> context) {
		for (CustomDecorate customDecorate : context) {
			switch (customDecorate.getCustomMethond()) {
			case insert:
				customService.insert(customDecorate);
				break;
			case update:
				customService.update(customDecorate);
				break;
			case delete:
				customService.delete(customDecorate);
				break;
			case select:
				customService.select(customDecorate);
				break;
			default:
				throw new RuntimeException(String.format("错误的CustomMethod=%s", customDecorate.getCustomMethond()));
			}
		}
	}
}
