package com.ecom.service;

import java.util.List;

import com.ecom.model.Category;

public interface CategoryService {

	public Category saveCategory(Category category);

	//public Boolean existCategory(Object object);
	public Boolean existCategory(String object);

	public List<Category> getAllCategory();

	public Boolean deleteCategory(int id);

	public Category getCategoryById(int id);

	public List<Category> getAllActiveCategory();

	//public Boolean existCategory(Object name);
	
}
