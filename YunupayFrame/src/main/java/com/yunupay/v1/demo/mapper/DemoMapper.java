package com.yunupay.v1.demo.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.alibaba.fastjson.JSONObject;

@Mapper
public interface DemoMapper {
	
	@Insert("INSERT into TEST(PID,NAME,AGE,TIME) values(#{pId},#{name},#{age},#{time})")
	@Options(useGeneratedKeys = true, keyProperty = "pId")
	@SelectKey(resultType = String.class, statement = "select replace(uuid(),'-','')", before = true, keyProperty = "pId")
	Integer saveTestInfo(HashMap<String, Object> testInfo);
	
	
	@Select("select * from TEST")
	@ResultType(com.alibaba.fastjson.JSONObject.class)
	List<JSONObject> getTestInfo();
}
