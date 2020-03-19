/**  
 * @Title:  TemplateParam.java   
 * @Package com.jiaewo.house.entity   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 家小二-风清扬   
 * @date:   2018年3月5日 下午4:16:33   
 * @version V1.0 
 * @Copyright: 2018 www.jiajiao2o.com Inc. All rights reserved. 
 * 注意：本内容仅限于北京赢在路上科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.yonyou.param;

/**
 * @ClassName: TemplateParam
 * @Description:微信推送模版model
 */
public class wxsmallTemplateParam {
	// 参数名称
	private String name;
	// 参数值
	private String value;
	// 颜色
	private String color;

	public wxsmallTemplateParam(String name, String value, String color) {
		this.name = name;
		this.value = value;
		this.color = color;
	}

	public wxsmallTemplateParam(String name, String value) {
		this.name = name;
		this.value = value;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}