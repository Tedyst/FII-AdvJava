package ro.tedyst.lab3.repository;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ro.tedyst.lab3.model.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductDAOTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private ProductDAO productDAO;

    private Faker faker;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        faker = new Faker();
    }

    @Test
    public void testGetAllProducts() {
        // Arrange
        List<Product> mockProducts = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Product product = new Product();
            product.setName(faker.commerce().productName());
            mockProducts.add(product);
        }

        TypedQuery<Product> mockQuery = mock(TypedQuery.class);
        when(entityManager.createNamedQuery("Product.findAll", Product.class)).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(mockProducts);

        // Act
        List<Product> products = productDAO.getAllProducts();

        // Assert
        assertEquals(2, products.size());
        assertNotNull(products.get(0).getName());
        assertNotNull(products.get(1).getName());
    }

    @Test
    public void testGetProductById() {
        // Arrange
        Product product = new Product();
        product.setName(faker.commerce().productName());
        when(entityManager.find(Product.class, 1)).thenReturn(product);

        // Act
        Product foundProduct = productDAO.getProductById(1);

        // Assert
        assertNotNull(foundProduct);
        assertEquals(product.getName(), foundProduct.getName());
    }

    @Test
    public void testCreateProduct() {
        // Arrange
        Product product = new Product();
        product.setName(faker.commerce().productName());

        // Act
        productDAO.createProduct(product);

        // Assert
        verify(entityManager, times(1)).persist(product);
    }

    @Test
    public void testUpdateProduct() {
        // Arrange
        Product product = new Product();
        product.setName(faker.commerce().productName());

        // Act
        productDAO.updateProduct(product);

        // Assert
        verify(entityManager, times(1)).merge(product);
    }

    @Test
    public void testDeleteProductById() {
        // Arrange
        Product product = new Product();
        product.setName(faker.commerce().productName());
        when(entityManager.find(Product.class, 1)).thenReturn(product);

        // Act
        productDAO.deleteProductById(1);

        // Assert
        verify(entityManager, times(1)).find(Product.class, 1);
        verify(entityManager, times(1)).remove(product);
    }
}
