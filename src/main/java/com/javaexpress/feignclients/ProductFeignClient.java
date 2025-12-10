package com.javaexpress.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="product-service",path = "/api//products")
public interface ProductFeignClient {

	@GetMapping("/exists/{productId}")
	public boolean isProductExits(@PathVariable Long productId);
}
