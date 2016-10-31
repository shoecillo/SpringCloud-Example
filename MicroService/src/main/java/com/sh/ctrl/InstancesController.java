package com.sh.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InstancesController 
{
	
	 @Autowired
	 private DiscoveryClient discoveryClient;

	 @RequestMapping("/service-instances/{applicationName}")
	 public List<ServiceInstance> serviceInstancesByApplicationName(@PathVariable String applicationName) 
	 {
		 return this.discoveryClient.getInstances(applicationName);
	 }
	
}
