package service;

import static org.junit.jupiter.api.Assertions.*; 
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.test.quind.bussines.services.ProductService;
import com.test.quind.domain.commons.DTO.MainResponseDTO;
import com.test.quind.domain.commons.DTO.ProductDTO;
import com.test.quind.domain.commons.Enums.EnumMainResponse;
import com.test.quind.infrastructure.controller.rest.ProductsRestController;

class ProductServiceTest {

    @InjectMocks
    private ProductsRestController productsRestController;

    @Mock
    private ProductService productService;

    @SuppressWarnings("deprecation")
	@BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetProduct() {
        ProductDTO productDTO = new ProductDTO();

        when(productService.getProductById(anyLong())).thenReturn(productDTO);

        ResponseEntity<ProductDTO> result = productsRestController.getProduct(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(productDTO, result.getBody());
    }

    @Test
    void testGetProductClient() {
        List<ProductDTO> productDTOs = Arrays.asList(new ProductDTO(), new ProductDTO());

        when(productService.getProductByIdClient(anyLong())).thenReturn(productDTOs);

        ResponseEntity<List<ProductDTO>> result = productsRestController.getProductClient(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(productDTOs, result.getBody());
    }

    @Test
    void testGetAllProducts() {
        List<ProductDTO> productDTOs = Arrays.asList(new ProductDTO(), new ProductDTO());

        when(productService.getAllProduct()).thenReturn(productDTOs);

        ResponseEntity<List<ProductDTO>> result = productsRestController.getAllProducts();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(productDTOs, result.getBody());
    }
    
    @Test
    void testCreateProduct() {
        ProductDTO productDTO = new ProductDTO();
        MainResponseDTO mainResponseDTO = new MainResponseDTO(EnumMainResponse.OK.getCode(), EnumMainResponse.OK.getMessage());

        when(productService.createProduct(any())).thenReturn(mainResponseDTO);

        ResponseEntity<MainResponseDTO> result = productsRestController.createProduct(productDTO);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(mainResponseDTO, result.getBody());
    }

    @Test
    void testUpdateProduct() {
        ProductDTO productDTO = new ProductDTO();
        MainResponseDTO mainResponseDTO = new MainResponseDTO(EnumMainResponse.OK.getCode(), EnumMainResponse.OK.getMessage());

        when(productService.updateProduct(any())).thenReturn(mainResponseDTO);

        ResponseEntity<MainResponseDTO> result = productsRestController.updateProduct(productDTO);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(mainResponseDTO, result.getBody());
    }

    @Test
    void testDeleteProduct() {
        MainResponseDTO mainResponseDTO = new MainResponseDTO(EnumMainResponse.OK.getCode(), EnumMainResponse.OK.getMessage());

        when(productService.deleteProduct(anyLong())).thenReturn(mainResponseDTO);

        ResponseEntity<MainResponseDTO> result = productsRestController.deleteProduct(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(mainResponseDTO, result.getBody());
    }
}
