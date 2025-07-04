package com.ecommerce.ecommerce.service.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ecommerce.ecommerce.domain.Product;
import com.ecommerce.ecommerce.domain.ProductImage;
import com.ecommerce.ecommerce.dto.product.ProductCreateMultipartDto;
import com.ecommerce.ecommerce.dto.product.ProductDto;
import com.ecommerce.ecommerce.exceptions.ResourceNotFoundException;
import com.ecommerce.ecommerce.mappers.product.ProductMapper;
import com.ecommerce.ecommerce.repository.CategoryRepository;
import com.ecommerce.ecommerce.repository.ProductRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private ProductMapper productMapper;
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private Cloudinary cloudinary;
    
    @Override
    public ProductDto createProduct(ProductCreateMultipartDto productCreateDto) {
        Product product = new Product();
        product.setName(productCreateDto.name());
        product.setDescription(productCreateDto.description());
        product.setPrice(productCreateDto.price());
        product.setStock(productCreateDto.stock());
        product.setCategory(categoryRepository.findByName(productCreateDto.name_category())
            .orElseThrow(() -> new ResourceNotFoundException("La categoría con nombre: " + productCreateDto.name_category() + " no existe.")));

        // Inicializar la lista de imágenes
        List<ProductImage> images = new ArrayList<>();
        if (productCreateDto.images() != null && !productCreateDto.images().isEmpty()) {
            for (MultipartFile image : productCreateDto.images()) {
                if (!image.isEmpty()) {
                    try {
                        Map<String, Object> uploadOptions = ObjectUtils.asMap("format", "webp", // Guardar como WebP
                        "quality", "auto");
                        Map uploadResult = cloudinary.uploader().upload(image.getBytes(), uploadOptions);
                        ProductImage productImage = new ProductImage();
                        productImage.setImageUrl(uploadResult.get("url").toString());
                        productImage.setProduct(product);
                        images.add(productImage);
                    } catch (Exception e) {
                        throw new RuntimeException("Error al subir la imagen a Cloudinary: " + e.getMessage());
                    }
                }
            }
        }
        product.setImages(images);

        Product savedProduct = productRepository.save(product);
        return productMapper.productToProductDto(savedProduct);
    }

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAllWithCategoryAndImages();
        return products.stream()
                      .map(productMapper::productToProductDto)
                      .collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductById(Long id) {
        return productRepository.findById(id).map(productMapper::productToProductDto)
            .orElseThrow(() -> new ResourceNotFoundException("El producto con ID: " + id + " no existe."));
    }

    @Override
    public ProductDto updateProduct(Long id_product, ProductCreateMultipartDto productCreateDto) {
        Product product = productRepository.findById(id_product)
            .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id_product));

        product.setName(productCreateDto.name());
        product.setDescription(productCreateDto.description());
        product.setPrice(productCreateDto.price());
        product.setStock(productCreateDto.stock());
        product.setCategory(categoryRepository.findByName(productCreateDto.name_category())
            .orElseThrow(() -> new ResourceNotFoundException("La categoría con nombre: " + productCreateDto.name_category() + " no existe.")));

        // Sincronizar imágenes
        List<ProductImage> currentImages = product.getImages();
        List<String> existingImageUrls = productCreateDto.existingImageUrls() != null ? productCreateDto.existingImageUrls() : List.of();

        // Eliminar imágenes que no están en existingImageUrls
        currentImages.removeIf(image -> !existingImageUrls.contains(image.getImageUrl()));

        // Agregar nuevas imágenes si se enviaron
        if (productCreateDto.images() != null && !productCreateDto.images().isEmpty()) {
            for (MultipartFile image : productCreateDto.images()) {
                if (!image.isEmpty()) {
                    try {
                        Map<String, Object> uploadOptions = ObjectUtils.asMap(
                            "format", "webp",
                            "quality", "auto"
                        );
                        Map uploadResult = cloudinary.uploader().upload(image.getBytes(), uploadOptions);
                        ProductImage productImage = new ProductImage();
                        productImage.setImageUrl(uploadResult.get("url").toString());
                        productImage.setProduct(product);
                        currentImages.add(productImage);
                    } catch (Exception e) {
                        throw new RuntimeException("Error al subir la imagen a Cloudinary: " + e.getMessage());
                    }
                }
            }
        }

        Product updatedProduct = productRepository.save(product);
        return productMapper.productToProductDto(updatedProduct);
    }


    @Override
    public void deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        }else{
            throw new ResourceNotFoundException("El producto con ID: " +id+" no existe." );
        }
    }

}
