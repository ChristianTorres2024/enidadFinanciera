package com.test.quind.bussines.services.products;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.quind.bussines.Iservices.client.IClientServices;
import com.test.quind.bussines.Iservices.products.IProductsServices;
import com.test.quind.domain.commons.DTO.ClientDTO;
import com.test.quind.domain.commons.DTO.MainResponseDTO;
import com.test.quind.domain.commons.DTO.ProductDTO;
import com.test.quind.domain.commons.DTO.ValidateDTO;
import com.test.quind.domain.commons.Enums.EnumMainResponse;
import com.test.quind.domain.commons.Enums.EnumValidateClientDTO;
import com.test.quind.infrastructure.controller.rest.ClientRestController;
import com.test.quind.persistent.entity.client.ClientEntity;
import com.test.quind.persistent.entity.products.ProductEntity;
import com.test.quind.persistent.repository.ClientRepository;
import com.test.quind.persistent.repository.ProductsRepository;

@Service
public class ProductService implements IProductsServices {

	private Logger log = LoggerFactory.getLogger(ClientRestController.class);

	@Autowired
	private ProductsRepository productsRepository;

	@Autowired
	private ConvertProductDTOEntity convertProductDTOEntity;

	@Autowired
	private ConvertEntityProductDTO convertEntityProductDTO;

	@Autowired
	private ProductValidateRequestDTO productValidateRequestDTO;

	@Override
	public ProductDTO getProductById(Long id) {
		try {
			ProductEntity product = productsRepository.findById(id).orElse(null);
			return convertEntityProductDTO.convertToDTO(product);
		} catch (Exception e) {
			log.error("Error create client {}", e.getCause(), e.getMessage());
			return null;
		}
	}

	@Override
	public List<ProductDTO> getAllProduct() {
		try {
			List<ProductEntity> productEntities = productsRepository.findAll();

			List<ProductDTO> productDTOs = productEntities.stream().map(convertEntityProductDTO::convertToDTO)
					.collect(Collectors.toList());

			return productDTOs;
		} catch (Exception e) {
			log.error("Error create client {}", e.getCause(), e.getMessage());
			return null;
		}
	}

	@Override
	public MainResponseDTO createProduct(ProductDTO productDTO) {
		ValidateDTO validateDTO = productValidateRequestDTO.validateDataProduct(productDTO, true);
		if (validateDTO != null) {
			return new MainResponseDTO(validateDTO.getCode(), validateDTO.getField() + validateDTO.getError());
		}

		ProductEntity productEntity = convertProductDTOEntity.convertToEntity(productDTO);

		// verificar que no exista
		ProductDTO productDTOFind = getProductById(productEntity.getIdProduct());

		if (productDTOFind != null)
			return new MainResponseDTO(EnumValidateClientDTO.DUPLICATEKEYS.getCode(),
					EnumValidateClientDTO.DUPLICATEKEYS.getField()
							+ EnumValidateClientDTO.DUPLICATEKEYS.getDescription());

		try {
			productsRepository.save(productEntity);
			log.info("create client Ok {}", productEntity);
			return new MainResponseDTO(EnumMainResponse.OK.getCode(), EnumMainResponse.OK.getMessage());
		} catch (Exception e) {
			log.error("Error create client {}", e.getCause(), e.getMessage());
			return new MainResponseDTO(EnumMainResponse.ERROR.getCode(), EnumMainResponse.ERROR.getMessage());
		}
	}

	@Override
	public MainResponseDTO updateProduct(ProductDTO productDTO) {
		ValidateDTO validateDTO = productValidateRequestDTO.validateDataProduct(productDTO, false);
		if (validateDTO != null) {
			return new MainResponseDTO(validateDTO.getCode(), validateDTO.getField() + validateDTO.getError());
		}
		try {

			ProductEntity productEntity = convertProductDTOEntity.convertToEntity(productDTO);

			Optional<ProductEntity> existingProductOptional = productsRepository.findById(productDTO.getIdProduct());
			if (existingProductOptional.isPresent()) {

				productsRepository.save(updateDataExistingProduct(existingProductOptional.get(), productEntity));
				return new MainResponseDTO(EnumMainResponse.OK.getCode(), EnumMainResponse.OK.getMessage());
			} else {

				return new MainResponseDTO(EnumMainResponse.ERROR.getCode(), EnumMainResponse.ERROR.getMessage());
			}
		} catch (Exception e) {

			log.error("Error al actualizar el cliente: {}", e.getMessage());
			return new MainResponseDTO(EnumMainResponse.ERROR.getCode(), EnumMainResponse.ERROR.getMessage());
		}
	}

	@Override
	public ProductEntity updateDataExistingProduct(ProductEntity existingProduct, ProductEntity productEntity) {

		existingProduct.setStatusEntity(
				productEntity.getStatusEntity() != null ? productEntity.getStatusEntity() : existingProduct.getStatusEntity());

		existingProduct.setBalance(
				productEntity.getBalance() != null ? productEntity.getBalance() : existingProduct.getBalance());

		existingProduct.setGMFExempt(
				productEntity.getGMFExempt() != null ? productEntity.getGMFExempt() : existingProduct.getGMFExempt());


		return existingProduct;
	}

	@Override
	public MainResponseDTO deleteProduct(Long id) {
		try {
			Optional<ProductEntity> existingProductOptional = productsRepository.findById(id);
			if (existingProductOptional.isPresent()) {
				productsRepository.deleteById(id);
				return new MainResponseDTO(EnumMainResponse.OK.getCode(), EnumMainResponse.OK.getMessage());
			} else {
				return new MainResponseDTO(EnumMainResponse.ERROR.getCode(), EnumMainResponse.ERROR.getMessage());
			}
		} catch (Exception e) {
			log.error("Error al eliminar el cliente: {}", e.getMessage());
			return new MainResponseDTO(EnumMainResponse.ERROR.getCode(), EnumMainResponse.ERROR.getMessage());
		}

	}

}
