package com.hotel.service.logic;


public interface PopMenuCallback {
	/**
	 * 获取区县数据
	 */
	public void getCountyList();
	
	/**
	 * 根据区县ID获取社区数据
	 */
	public void getCommunityList(String countyID);
	
	/**
	 * 设置选择的区县
	 * @param countyID
	 */
	public void setSelectCounty(String countyID);
	
	/**
	 * 设置选择的社区
	 * @param communityID
	 */
	public void setSelectCommunity(String communityID, String communityName); 
}
