package com.javaexpress.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaexpress.dto.FavouriteDTO;
import com.javaexpress.dto.UserDto;
import com.javaexpress.feignclients.ProductFeignClient;
import com.javaexpress.feignclients.UserFeignClient;
import com.javaexpress.model.Favourite;
import com.javaexpress.repository.FavouriteRepository;

@Service
public class FavouriteService {

	@Autowired
	private FavouriteRepository favouriteRepository;
	
	@Autowired
	private ProductFeignClient productFeignClient;
	
	@Autowired
	private UserFeignClient userFeignClient;
	
	public FavouriteDTO addProductToWishList(Integer userId,Long productId) {
		// check product exits or not
		// check user exits or not
		boolean productExits = productFeignClient.isProductExits(productId);
		UserDto userDto = userFeignClient.fetchUser(userId);
		
		if(userDto == null) {
			throw new RuntimeException("User Not Found in Db");
		}
		
		if(!productExits) {
			throw new RuntimeException("Product not exits");
		}
		Favourite favourite = favouriteRepository.findByUserIdAndProductId(userId, productId)
					.orElse(new Favourite());
		favourite.setUserId(userDto.getUserId().longValue());
		favourite.setProductId(productId);
		
		favouriteRepository.save(favourite);
		var response =  mapToDTo(favourite);
		response.setUserDto(userDto);
		return response;
		
	}

	private FavouriteDTO mapToDTo(Favourite favourite) {
		FavouriteDTO dto = new FavouriteDTO();
		dto.setProductId(favourite.getProductId());
		dto.setUserId(favourite.getUserId().intValue());
		return dto;
	}
	
	
	public void removeProductFromWishList(Integer userId,Long productId) {
		Favourite dbFavourite = favouriteRepository.findByUserIdAndProductId(userId, productId)
		.orElseThrow(() -> new RuntimeException("Product Not found in wishlist"));
		
		favouriteRepository.delete(dbFavourite);
	}
	
}
