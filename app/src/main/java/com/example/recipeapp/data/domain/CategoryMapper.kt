package com.example.recipeapp.data.domain

import com.example.recipeapp.dto.CategoryDto

object CategoryMapper {
    fun toCategory(dto: CategoryDto) = Category(
        id = dto.idCategory,
        name = dto.strCategory,
        imageUrl = dto.strCategoryThumb
    )
}