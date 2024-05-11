package com.ecom.service.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.model.Product;
import com.ecom.repository.ProductRepository;
import com.ecom.service.ProductService;

import jakarta.persistence.criteria.Path;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductRepository productRepository;

	@Override
	public Product saveProduct(Product product) {
		
		return productRepository.save(product);
	}
	@Override
	public List<Product> getAllProduct() {
		
		return productRepository.findAll();
	}
	@Override
	public Boolean deleteProduct(Integer id) {
		Product product = productRepository.findById(id).orElse(null);
		if(!ObjectUtils.isEmpty(product))
		{
			productRepository.delete(product);
			return true;
		}
		return  false ;
	}
	@Override
	public Product updateProduct(Product product, MultipartFile image) {
		Product dbProduct = getProductById(product.getId());

		String imageName = image.isEmpty() ? dbProduct.getImage() : image.getOriginalFilename();
		
		dbProduct.setTitle(product.getTitle());
		dbProduct.setDescription(product.getDescription());
		dbProduct.setCategory(product.getCategory());
		dbProduct.setPrice(product.getPrice());
		dbProduct.setStock(product.getStock());
		dbProduct.setImage(imageName);
		dbProduct.setIsActive(product.getIsActive());
		dbProduct.setDiscount(product.getDiscount());

		// 5=100*(5/100); 100-5=95
				Double disocunt = product.getPrice() * (product.getDiscount() / 100.0);
				Double discountPrice = product.getPrice() - disocunt;
				dbProduct.setDiscountPrice(discountPrice);

				Product updateProduct = productRepository.save(dbProduct);
               
				if (!ObjectUtils.isEmpty(updateProduct)) {

					if (!image.isEmpty()) {

						try {
							File saveFile = new ClassPathResource("static/img").getFile();

							java.nio.file.Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + "product_img" + File.separator
									+ image.getOriginalFilename());
							Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

						} catch (Exception e) {
							e.printStackTrace();
						}

					}
					return product;
				}



		
		
		return null;
	}
	@Override
	public List<Product> getAllActiveProducts() {
		List<Product> products = productRepository.findByIsActiveTrue();
		return products;
	}
	@Override
	public Product getProductById(Integer id) {
		Product product = productRepository.findById(id).orElse(null);
		return product;
	}

	
	




}
