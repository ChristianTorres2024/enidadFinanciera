package com.test.quind.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.test.quind.bussines.services.ProductService;
import com.test.quind.domain.commons.DTO.MainResponseDTO;
import com.test.quind.domain.commons.DTO.ProductDTO;
import com.test.quind.infrastructure.controller.rest.ProductsRestController;

class ProductsRestControllerTest {

    @Test
    void testGetProduct() {
        ProductService productServiceMock = mock(ProductService.class);
        ProductsRestController productsController = new ProductsRestController(productServiceMock);

        Long productId = 1L;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setIdProduct(productId);
        when(productServiceMock.getProductById(productId)).thenReturn(productDTO);

        ResponseEntity<ProductDTO> responseEntity = productsController.getProduct(productId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(productDTO, responseEntity.getBody());
    }

    @Test
    void testGetProductClient() {
        ProductService productServiceMock = mock(ProductService.class);
        ProductsRestController productsController = new ProductsRestController(productServiceMock);

        Long clientId = 1L;
        List<ProductDTO> productDTOList = new ArrayList<>();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setIdProduct(1L);
        productDTOList.add(productDTO);
        when(productServiceMock.getProductByIdClient(clientId)).thenReturn(productDTOList);

        ResponseEntity<List<ProductDTO>> responseEntity = productsController.getProductClient(clientId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(productDTOList, responseEntity.getBody());
    }

    @Test
    void testGetAllProducts() {
        ProductService productServiceMock = mock(ProductService.class);
        ProductsRestController productsController = new ProductsRestController(productServiceMock);

        List<ProductDTO> products = new ArrayList<>();
        when(productServiceMock.getAllProduct()).thenReturn(products);

        ResponseEntity<List<ProductDTO>> responseEntity = productsController.getAllProducts();
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void testCreateProduct() {
        ProductService productServiceMock = mock(ProductService.class);
        ProductsRestController productsController = new ProductsRestController(productServiceMock);

        ProductDTO productDTO = new ProductDTO();
        MainResponseDTO responseDTO = new MainResponseDTO();
        responseDTO.setCode(0); // Suponiendo que el código 0 indica éxito en la operación

        when(productServiceMock.createProduct(productDTO)).thenReturn(responseDTO);

        ResponseEntity<MainResponseDTO> responseEntity = productsController.createProduct(productDTO);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(responseDTO, responseEntity.getBody());
    }

    @Test
    void testUpdateProduct() {
        ProductService productServiceMock = mock(ProductService.class);
        ProductsRestController productsController = new ProductsRestController(productServiceMock);

        ProductDTO productDTO = new ProductDTO();
        MainResponseDTO responseDTO = new MainResponseDTO();
        responseDTO.setCode(0); // Suponiendo que el código 0 indica éxito en la operación

        when(productServiceMock.updateProduct(productDTO)).thenReturn(responseDTO);

        ResponseEntity<MainResponseDTO> responseEntity = productsController.updateProduct(productDTO);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseDTO, responseEntity.getBody());
    }

    @Test
    void testDeleteProduct() {
        ProductService productServiceMock = mock(ProductService.class);
        ProductsRestController productsController = new ProductsRestController(productServiceMock);

        Long productId = 1L;
        MainResponseDTO responseDTO = new MainResponseDTO();
        responseDTO.setCode(0); // Suponiendo que el código 0 indica éxito en la operación

        when(productServiceMock.deleteProduct(productId)).thenReturn(responseDTO);

        ResponseEntity<MainResponseDTO> responseEntity = productsController.deleteProduct(productId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseDTO, responseEntity.getBody());
    }
}
