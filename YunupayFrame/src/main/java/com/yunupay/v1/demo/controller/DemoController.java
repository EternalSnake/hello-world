package com.yunupay.v1.demo.controller;


import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.yunupay.v1.demo.dao.DemoDao;



@RestController
@RequestMapping("/demo")
public class DemoController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired(required = true)
	private DemoDao demoDao;
	
	@RequestMapping("/getDemo")
	public String getDemo(String name,String age){
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("name", name);
		map.put("age", age);
		map.put("time", new Date());
		Integer res =  demoDao.saveTest(map);
		logger.info("---------------------------------->这是一条info日志");
		logger.error("---------------------------------->这是一条error日志");
		logger.debug("---------------------------------->这是一条debug日志");
		JSONObject result = demoDao.getTest();
		
		return "res: " + result.toString();
	}
}
