package com.capgemini.configclientt2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;

@EnableEurekaClient
@SpringBootApplication
public class Configclientt2Application {

	public static void main(String[] args) {
		SpringApplication.run(Configclientt2Application.class, args);
	}
}

@RefreshScope
@RestController
class ServiceInstanceRestController{
	
	@Autowired
	private EurekaClient eurekaClient;
	private static final RestTemplate REST_TEMPLATE=new RestTemplate();
	
	@GetMapping("/message")
	public String getMessage()
	{
		Application application=eurekaClient.getApplication("configclient1");
		InstanceInfo instanceInfo=application.getInstances().get(0);
		String url="http://"+instanceInfo.getIPAddr()+":"+instanceInfo.getPort()+"/message";
		return REST_TEMPLATE.getForObject(url,String.class);
	}
	
}