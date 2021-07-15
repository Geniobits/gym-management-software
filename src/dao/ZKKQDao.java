package com.zkkq.dao;

import java.util.List;
import java.util.Map;

public interface ZKKQDao {
	
	/**
	 * 查询考勤机
	 */
	public List<Map<String,Object>> listEquips();
	
	/**
	 * 判断记录是否存在
	 */
	public int searchRec(String enrollNumber,String signTime);
	
	/**
	 * 插入考勤记录
	 */
	public void insertRec(String enrollNumber,String signTime,int inOutMode,int verifyMode);
}
