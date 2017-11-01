package com.yunupay.v1.demo.dao;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONObject;
import com.yunupay.v1.demo.mapper.DemoMapper;


@Repository
public class DemoDao {
	@Autowired(required = true)
	private DemoMapper demoMapper;
	
	public Integer saveTest(HashMap<String, Object> map){
		return demoMapper.saveTestInfo(map);
	}
	
	public JSONObject getTest(){
		return demoMapper.getTestInfo().get(0);
	}
}
