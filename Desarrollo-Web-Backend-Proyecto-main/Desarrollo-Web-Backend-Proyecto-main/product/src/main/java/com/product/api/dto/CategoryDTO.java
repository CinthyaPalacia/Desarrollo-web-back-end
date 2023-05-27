package com.product.api.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data public class CategoryDTO
{
    @NotNull(message = "category id is required")
    private int category_id;

    public CategoryDTO(int category_id)
    {
        this.category_id = category_id;
    }

    public CategoryDTO()
    {
        super();
    }

    public int getId()
    {
        return category_id;
    }

    public void setId(int category_id)
    {
        this.category_id = category_id;
    }
}
