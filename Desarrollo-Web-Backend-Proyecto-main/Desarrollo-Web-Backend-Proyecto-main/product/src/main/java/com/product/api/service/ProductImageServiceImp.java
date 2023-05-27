package com.product.api.service;

import com.product.api.dto.ResponseApi;
import com.product.api.entity.ProductImage;
import com.product.api.repository.ProductImageRepository;
import com.product.exception.ExceptionApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service @PropertySource("classpath:configuration/path.config") public class ProductImageServiceImp implements ProductImageService
{

    @Resource
    ProductImageRepository productImageRepository;

    @Value("${product.images.path}")
    private String path;

    @Override
    public List<ProductImage> getProductImages(Integer id_product)
    {
        return productImageRepository.findByProductId(id_product);
    }

    @Override
    public ResponseApi createProductImage(ProductImage productImage)
    {
        try
        {
            File folder = new File(path + "/" + productImage.getProduct_id());
            if (!folder.exists())
            {
                folder.mkdirs();
            }

            String file = path + productImage.getProduct_id() + "/img_" + new Date().getTime() + ".bmp";

            byte[] data = Base64.getMimeDecoder().decode(productImage.getImage().substring(productImage.getImage().indexOf(",") + 1));
            try (OutputStream stream = new FileOutputStream(file))
            {
                stream.write(data);
            }

            productImage.setStatus(1);
            productImage.setImage(productImage.getProduct_id() + "/img_" + new Date().getTime() + ".bmp");

            productImageRepository.save(productImage);
            return new ResponseApi("product image created");
        }
        catch (Exception e)
        {
            throw new ExceptionApi(HttpStatus.BAD_REQUEST, "product image can not be created" + ". " + e.getLocalizedMessage());
        }
    }

    @Override
    public ResponseApi deleteProductImage(Integer id)
    {
        if (productImageRepository.deleteProductImage(id) > 0)
        {
            return new ResponseApi("product image removed");
        }
        else
        {
            throw new ExceptionApi(HttpStatus.BAD_REQUEST, "product image cannot be deleted");
        }
    }

}
