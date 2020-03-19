package com.yonyou;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.yonyou.entity.Token;
import com.yonyou.param.wxsmallTemplate;
import com.yonyou.param.wxsmallTemplateParam;
import com.yonyou.util.CommonUtil;

/**
 * 微信小程序消息推送ok
 * 
 * @author adu
 *
 */
@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) {
		// 封装了推送实体类，java三特性 继承 封装 多态
		wxsmallTemplate tem = new wxsmallTemplate();
		// 模板id
		tem.setTemplateId("E1KSlbHdo9b3k4N6gNSizdUoGRhz2dMNjIUU7RsB2uQ");
		// 这个是openId 不是UnionID 如果是unionId肯定推送不过去。
		tem.setToUser("ojD3E5KDEBsctXugOXg97wIDZiro");
		// fromId 这个重要，没有他百分百推送不成功，fromId+openId 才能推送
		tem.setForm_id("1539867360345");// Form_id只能用一次
		
		// 用户点击之后调到小程序哪个页面 找你们前段工程师提供即可
		tem.setPage("pages/index/index");//index?foo=bar(点击按钮后提交的页面)
		// 有封装了一个实体类 哈哈哈 这个是模板消息参数
		List<wxsmallTemplateParam> paras = new ArrayList<wxsmallTemplateParam>();
		// 这个是满参构造 keyword1代表的第一个提示 红包已到账这是提示 #DC143C这是色值不过废弃了
		wxsmallTemplateParam templateParam = new wxsmallTemplateParam("keyword1", "yonyou", "#DC143C");
		// 装进小参数结合中
		paras.add(templateParam);
		// 省点劲直接扔进去算了哈哈哈哈哈~~~~
		paras.add(new wxsmallTemplateParam("keyword2", "解忧杂化店", ""));
		paras.add(new wxsmallTemplateParam("keyword3", "100583029", "#DC143C"));
		paras.add(new wxsmallTemplateParam("keyword4", "18.8", ""));
		paras.add(new wxsmallTemplateParam("keyword5", "蒲熠星", ""));
		paras.add(new wxsmallTemplateParam("keyword6", "2030年12月31日", ""));

		// 然后把所有参数扔到大的模板中
		tem.setTemplateParamList(paras);
		// 模板需要放大的关键词，不填则默认无放大
		tem.setEmphasis_keyword("keyword1.DATA");
		// 获取token凭证。
		Token token = CommonUtil.getToken();
		String accessToken = token.getAccessToken();
		// 最后一步请求接口   推送成功
		boolean result1 = CommonUtil.sendTemplateMsg(accessToken, tem);
		
		if (result1) {
			System.err.println("推送成功");
		} else {
			System.err.println("推送失败");
		}
	}

}