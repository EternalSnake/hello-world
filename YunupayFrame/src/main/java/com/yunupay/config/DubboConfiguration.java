package com.yunupay.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.MonitorConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.AnnotationBean;

/**
 * 
 * @Package com.yunupay.config 	
 * @ClassName: DubboConfiguration 
 * @Description: dubbo配置
 * @author qijiaxv
 * @time   2017年10月30日 下午1:35:50
 * @version V
 */
@Configuration
@PropertySource("classpath:dubbo.properties") 
public class DubboConfiguration {
	
	@Value("${dubbo.application.name}")
	private String applicationName;
	
	@Value("${dubbo.application.logger}")
	private String applicationLoggerType;

	@Value("${dubbo.registry.address}")
	private String registryAddress;

	@Value("${dubbo.registry.protocol}")
	private String registryProtocol;

	@Value("${dubbo.protocol.name}")
	private String protocolName;

	@Value("${dubbo.protocol.port}")
	private int protocolPort;

	@Value("${dubbo.protocol.host}")
	private String protocolHost;
	
	@Value("${dubbo.parameter.timeout}")		
	private int timeout;
	
	@Value("${dubbo.parameter.retries}")	
	private int retries;
	
	@Value("${dubbo.parameter.loadbalance}")	
	private String loadbalance;
	
	@Value("${dubbo.parameter.actives}")	
	private int actives;
	
	@Value("${dubbo.parameter.threadpool}")	
	private String threadpool;
	
	@Value("${dubbo.parameter.threads}")	
	private int threads;
	
	@Value("${dubbo.parameter.accepts}")	
	private int accepts;
	
	@Value("${dubbo.parameter.dispatcher}")
	private String dispatcher;
	
	@Value("${dubbo.parameter.serialization}")
	private String serialization;
	
	

	@Bean
	public ApplicationConfig applicationConfig() {
		ApplicationConfig applicationConfig = new ApplicationConfig();
		applicationConfig.setLogger(applicationLoggerType);
		applicationConfig.setName(applicationName);
		return applicationConfig;
	}
	

	@Bean
	public RegistryConfig registryConfig() {
		RegistryConfig registryConfig = new RegistryConfig();
		registryConfig.setAddress(registryAddress);
		registryConfig.setProtocol(registryProtocol);
		registryConfig.setCheck(true);
		return registryConfig;
	}
	

	@Bean
	public ProtocolConfig protocolConfig(){
		ProtocolConfig protocolConfig = new ProtocolConfig();
		protocolConfig.setPort(protocolPort);
		protocolConfig.setHost(protocolHost);
		protocolConfig.setName(protocolName);
		protocolConfig.setSerialization(serialization);
		protocolConfig.setThreadpool(threadpool);
		protocolConfig.setThreads(threads);
		protocolConfig.setDispatcher(dispatcher);
		return protocolConfig;
	}
	

	@Bean
	public static AnnotationBean annotationBean() {  
        AnnotationBean annotationBean = new AnnotationBean();  
        annotationBean.setPackage("com.yunupay");//多个包可使用英文逗号隔开  
        return annotationBean;  
    }  
	

	@Bean
	public MonitorConfig monitorConfig() {
		MonitorConfig monitorConfig = new MonitorConfig();
		monitorConfig.setProtocol("registry");
		return monitorConfig;
	}
	

	@Bean
	public ProviderConfig providerConfig() {
		ProviderConfig providerConfig = new ProviderConfig();
		providerConfig.setTimeout(timeout);
		providerConfig.setAccepts(accepts);
		providerConfig.setRetries(retries);
		providerConfig.setLoadbalance(loadbalance);
		providerConfig.setActives(actives);
		return providerConfig;
	}
	

	@Bean
	public ConsumerConfig consumerConfig(){
		ConsumerConfig consumerConfig = new ConsumerConfig();
		consumerConfig.setCheck(false);
		consumerConfig.setTimeout(timeout);
		consumerConfig.setRetries(retries);
		consumerConfig.setLoadbalance(loadbalance);
		consumerConfig.setActives(actives);
		return consumerConfig;
	}
	
}
