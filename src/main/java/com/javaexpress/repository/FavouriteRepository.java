package com.javaexpress.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaexpress.model.Favourite;

public interface FavouriteRepository extends JpaRepository<Favourite, Long>{

	Optional<Favourite> findByUserIdAndProductId(Integer userId,Long productId);
}
