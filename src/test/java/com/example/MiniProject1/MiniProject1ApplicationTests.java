 package com.example.MiniProject1;

 import java.io.File;
 import java.io.IOException;
 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import java.util.UUID;

 import org.junit.jupiter.api.AfterEach;
 import org.springframework.http.MediaType;
 import org.junit.jupiter.api.BeforeEach;
 import org.junit.jupiter.api.Test;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
 import org.springframework.context.annotation.ComponentScan;
 import org.springframework.test.web.servlet.MockMvc;
 import org.springframework.test.web.servlet.MvcResult;
 import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
 import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

 import com.example.model.Cart;
 import com.example.model.Order;
 import com.example.model.Product;
 import com.example.model.User;
 import com.example.repository.CartRepository;
 import com.example.repository.OrderRepository;
 import com.example.repository.ProductRepository;
 import com.example.repository.UserRepository;
 import com.example.service.CartService;
 import com.example.service.OrderService;
 import com.example.service.ProductService;
 import com.example.service.UserService;
 import com.fasterxml.jackson.core.JsonProcessingException;
 import com.fasterxml.jackson.core.type.TypeReference;
 import com.fasterxml.jackson.databind.ObjectMapper;

 import static org.junit.jupiter.api.Assertions.*;

 @ComponentScan(basePackages = "com.example.*")
 @WebMvcTest
 class MiniProject1ApplicationTests {

 	@Value("${spring.application.userDataPath}")
     private String userDataPath;

     @Value("${spring.application.productDataPath}")
     private String productDataPath;

     @Value("${spring.application.orderDataPath}")
     private String orderDataPath;

     @Value("${spring.application.cartDataPath}")
     private String cartDataPath;

 	@Autowired
 	private MockMvc mockMvc;

 	@Autowired
 	private ObjectMapper objectMapper;

	

 	@Autowired
 	private UserService userService;

 	@Autowired
 	private CartService cartService;

 	@Autowired
 	private ProductService productService;

 	@Autowired
 	private OrderService orderService;
 	@Autowired
 	private UserRepository userRepository;

 	@Autowired
 	private CartRepository cartRepository;

 	@Autowired
 	private ProductRepository productRepository;

 	@Autowired
 	private OrderRepository orderRepository;

 	public void overRideAll(){
         try{
             objectMapper.writeValue(new File(userDataPath), new ArrayList<User>());
             objectMapper.writeValue(new File(productDataPath), new ArrayList<Product>());
             objectMapper.writeValue(new File(orderDataPath), new ArrayList<Order>());
             objectMapper.writeValue(new File(cartDataPath), new ArrayList<Cart>());
         } catch (IOException e) {
             throw new RuntimeException("Failed to write to JSON file", e);
         }
     }

     public Object find(String typeString, Object toFind){
         switch(typeString){
             case "User":
                 ArrayList<User> users = getUsers();
                
                 for(User user: users){
                     if(user.getId().equals(((User)toFind).getId())){
                         return user;
                     }
                 }
                 break;
             case "Product":
                 ArrayList<Product> products = getProducts();
                 for(Product product: products){
                     if(product.getId().equals(((Product)toFind).getId())){
                         return product;
                     }
                 }
                 break;
             case "Order":
                 ArrayList<Order> orders = getOrders();
                 for(Order order: orders){
                     if(order.getId().equals(((Order)toFind).getId())){
                         return order;
                     }
                 }
                 break;
             case "Cart":
                 ArrayList<Cart> carts = getCarts();
                 for(Cart cart: carts){
                     if(cart.getId().equals(((Cart)toFind).getId())){
                         return cart;
                     }
                 }
                 break;
         }
         return null;
     }

     public Product addProduct(Product product) {
         try {
            File file = new File(productDataPath);
            ArrayList<Product> products;
            if (!file.exists()) {
                products = new ArrayList<>();
            }
            else {
                products = new ArrayList<>(Arrays.asList(objectMapper.readValue(file, Product[].class)));
            }
            products.add(product);
            objectMapper.writeValue(file, products);
            return product;
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to JSON file", e);
        }
    }
    public ArrayList<Product> getProducts() {
        try {
            File file = new File(productDataPath);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            return new ArrayList<Product>(Arrays.asList(objectMapper.readValue(file, Product[].class)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read from JSON file", e);
        }
    }

    public User addUser(User user) {
        try {
            File file = new File(userDataPath);
            ArrayList<User> users;
            if (!file.exists()) {
                users = new ArrayList<>();
            }
            else {
                users = new ArrayList<>(Arrays.asList(objectMapper.readValue(file, User[].class)));
            }
            users.add(user);
            objectMapper.writeValue(file, users);
            return user;
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to JSON file", e);
        }
    }
     public ArrayList<User> getUsers() {
          try {
               File file = new File(userDataPath);
               if (!file.exists()) {
                 return new ArrayList<>();
               }
               return new ArrayList<User>(Arrays.asList(objectMapper.readValue(file, User[].class)));
          } catch (IOException e) {
               throw new RuntimeException("Failed to read from JSON file", e);
          }
     }
     public Cart addCart(Cart cart){
        try{
               File file = new File(cartDataPath);
               ArrayList<Cart> carts;
               if (!file.exists()) {
                 carts = new ArrayList<>();
               }
               else {
                 carts = new ArrayList<>(Arrays.asList(objectMapper.readValue(file, Cart[].class)));
               }
               carts.add(cart);
               objectMapper.writeValue(file, carts);
               return cart;
          } catch (IOException e) {
               throw new RuntimeException("Failed to write to JSON file", e);
        }
     }
     public ArrayList<Cart> getCarts() {
          try {
               File file = new File(cartDataPath);
               if (!file.exists()) {
                 return new ArrayList<>();
               }
               return new ArrayList<Cart>(Arrays.asList(objectMapper.readValue(file, Cart[].class)));
          } catch (IOException e) {
               throw new RuntimeException("Failed to read from JSON file", e);
          }
     }
     public Order addOrder(Order order){
          try{
                   File file = new File(orderDataPath);
                   ArrayList<Order> orders;
                   if (!file.exists()) {
                  orders = new ArrayList<>();
                   }
                   else {
                  orders = new ArrayList<>(Arrays.asList(objectMapper.readValue(file, Order[].class)));
                   }
                   orders.add(order);
                   objectMapper.writeValue(file, orders);
                   return order;
             } catch (IOException e) {
                   throw new RuntimeException("Failed to write to JSON file", e);
          }
     }
     public ArrayList<Order> getOrders() {
          try {
               File file = new File(orderDataPath);
               if (!file.exists()) {
                 return new ArrayList<>();
               }
               return new ArrayList<Order>(Arrays.asList(objectMapper.readValue(file, Order[].class)));
          } catch (IOException e) {
               throw new RuntimeException("Failed to read from JSON file", e);
          }
     }



 	private UUID userId;
 	private User testUser;
 	@BeforeEach
 	void setUp() {
 		userId = UUID.randomUUID();
 		testUser = new User();
 		testUser.setId(userId);
 		testUser.setName("Test User");
 		overRideAll();
 	}

 	// ------------------------ User Tests -------------------------
	
	

 	@Test
 	void testAddUserEndPoint() throws Exception {
 		User testUser3 = new User();
 		testUser3.setId(UUID.randomUUID());
 		testUser3.setName("Test User3");
		
		
 		mockMvc.perform(MockMvcRequestBuilders.post("/user/")
 				.contentType(MediaType.APPLICATION_JSON)
 				.content(objectMapper.writeValueAsString(testUser3)))
 				.andExpect(MockMvcResultMatchers.status().isOk());
 		boolean found=false;

 		for(User user: getUsers()){
 			if(user.getId().equals(testUser3.getId()) && user.getName().equals(testUser3.getName())){
 				found=true;
 				break;
 			}
 		}
 		assertTrue(found,"User should be added correctly");
 	}


 	@Test
 	void testGetUsersEndPoint() throws Exception {
		
 		addUser(testUser);
		

 		MvcResult result= mockMvc.perform(MockMvcRequestBuilders.get("/user/")
 				.contentType(MediaType.APPLICATION_JSON))
 				.andExpect(MockMvcResultMatchers.status().isOk())
 				.andReturn();
 		String responseContent = result.getResponse().getContentAsString();
 		List<User> responseUsers = objectMapper.readValue(responseContent, new TypeReference<List<User>>() {});
		
 		assertEquals(responseUsers.size(), getUsers().size(), "Users should be returned correctly From Endpoint");
 	}

	

 	@Test
 	void testGetUserByIdEndPoint() throws Exception {
 		User testUser8=new User();
 		testUser8.setId(UUID.randomUUID());
 		testUser8.setName("Test User8");
 		addUser(testUser8);
		
 		mockMvc.perform(MockMvcRequestBuilders.get("/user/{userId}", testUser8.getId()))
 				.andExpect(MockMvcResultMatchers.status().isOk())
 				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(testUser8)));
 	}

	

 	@Test
 	void testGetOrdersByUserIdEndPoint() throws Exception {
 		User testUser10=new User();
 		testUser10.setId(UUID.randomUUID());
 		testUser10.setName("Test User10");
 		List<Order> orders = List.of(new Order(UUID.randomUUID(), testUser10.getId(), 10.0, List.of(new Product(UUID.randomUUID(), "Test Product", 10.0))));
 		testUser10.setOrders(orders);
 		addUser(testUser10);
 		mockMvc.perform(MockMvcRequestBuilders.get("/user/{userId}/orders", testUser10.getId()))
 				.andExpect(MockMvcResultMatchers.status().isOk())
 				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(orders)));
 	}

	

 	@Test
 	void testAddOrderToUserEndPoint() throws Exception {
 		User testUser11=new User();
 		testUser11.setId(UUID.randomUUID());
 		testUser11.setName("Test User11");
 		Cart cart=new Cart();
 		cart.setId(UUID.randomUUID());
 		cart.setUserId(testUser11.getId());
 		Product tesProduct=new Product(UUID.randomUUID(), "Test Product", 10.0);
 		cart.setProducts(List.of(tesProduct));
 		addCart(cart);
 		addUser(testUser11);
		
		
 		mockMvc.perform(MockMvcRequestBuilders.post("/user/{userId}/checkout", testUser11.getId()))
 				.andExpect(MockMvcResultMatchers.status().isOk())
 				.andExpect(MockMvcResultMatchers.content().string("Order added successfully"));
 	}

	
 	@Test
 	void testRemoveOrderOfUserEndPoint() throws Exception{
 		User testUser12=new User();
 		testUser12.setId(UUID.randomUUID());
 		testUser12.setName("Test User12");
 		Product product = new Product(UUID.randomUUID(), "Test Product", 100.0);
 		Order order = new Order(UUID.randomUUID(), testUser12.getId(), 100.0, List.of(product));
 		testUser12.getOrders().add(order);
 		addUser(testUser12);
 		addOrder(order);
		
 		mockMvc.perform(MockMvcRequestBuilders.post("/user/{userId}/removeOrder", testUser12.getId()).param("orderId", order.getId().toString()))
 				.andExpect(MockMvcResultMatchers.status().isOk())
 				.andExpect(MockMvcResultMatchers.content().string("Order removed successfully"));
 	}
	

 	@Test
 	void testEmptyCartEndpoint() throws Exception{
 		User testUser13=new User();
 		testUser13.setId(UUID.randomUUID());
 		testUser13.setName("Test User13");
 		Product product = new Product(UUID.randomUUID(), "Test Product", 100.0);
 		Cart cart = new Cart(UUID.randomUUID(), testUser13.getId(), new ArrayList<>(List.of(product)));
 		addUser(testUser13);
 		addCart(cart);
		
 		mockMvc.perform(MockMvcRequestBuilders.delete("/user/{userId}/emptyCart", testUser13.getId()))
 				.andExpect(MockMvcResultMatchers.status().isOk())
 				.andExpect(MockMvcResultMatchers.content().string("Cart emptied successfully"));
 	}
	

 	@Test
 	void testAddProductToCartEndPoint() throws Exception {
 		User testUser14=new User();
 		testUser14.setId(UUID.randomUUID());
 		testUser14.setName("Test User14");
		
 		Product testProduct=new Product(UUID.randomUUID(), "Test Product", 10.0);
 		addUser(testUser14);
 		addProduct(testProduct);
		
 		mockMvc.perform(MockMvcRequestBuilders.put("/user/addProductToCart")
 				.param("userId", testUser14.getId().toString())
 				.param("productId", testProduct.getId().toString()))
 				.andExpect(MockMvcResultMatchers.status().isOk())
 				.andExpect(MockMvcResultMatchers.content().string("Product added to cart"));
 		assertTrue(getCarts().getLast().getUserId().equals(testUser14.getId()),"New Cart Should be created for user");
 		assertEquals(testProduct.getId(), getCarts().getLast().getProducts().get(0).getId(),"Product should be added correctly");
 	}

	

 	@Test
 	void testDeleteProductFromCartEndPoint1() throws Exception {
 		User testUser15=new User();
 		testUser15.setId(UUID.randomUUID());
 		testUser15.setName("Test User15");
		
 		Product testProduct=new Product(UUID.randomUUID(), "Test Product", 10.0);
 		addUser(testUser15);
 		addProduct(testProduct);
 		Cart cart = new Cart(UUID.randomUUID(), testUser15.getId(), new ArrayList<>(List.of(testProduct)));
 		addCart(cart);
		
 		mockMvc.perform(MockMvcRequestBuilders.put("/user/deleteProductFromCart")
 				.param("userId", cart.getUserId().toString())
 				.param("productId", testProduct.getId().toString()))
 				.andExpect(MockMvcResultMatchers.status().isOk())
 				.andExpect(MockMvcResultMatchers.content().string("Product deleted from cart"));
 	}
 	@Test
 	void testDeleteProductFromCartEndPoint2() throws Exception {
 		User testUser15=new User();
 		testUser15.setId(UUID.randomUUID());
 		testUser15.setName("Test User15");
		
 		Product testProduct=new Product(UUID.randomUUID(), "Test Product", 10.0);
 		addUser(testUser15);
 		addProduct(testProduct);
 		// Cart cart = new Cart(UUID.randomUUID(), testUser15.getId(), new ArrayList<>(List.of(testProduct)));
 		// addCart(cart);
		
 		mockMvc.perform(MockMvcRequestBuilders.put("/user/deleteProductFromCart")
 				.param("userId", testUser15.getId().toString())
 				.param("productId", testProduct.getId().toString()))
 				.andExpect(MockMvcResultMatchers.status().isOk())
 				.andExpect(MockMvcResultMatchers.content().string("Cart is empty"));
 	}


	
 	@Test
 	void testDeleteUserByIdEndPoint1() throws Exception {
 		User testUser18=new User();
 		testUser18.setId(UUID.randomUUID());
 		testUser18.setName("Test User18");
 		addUser(testUser18);
		
 		mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete/{userId}", testUser18.getId()))
 				.andExpect(MockMvcResultMatchers.status().isOk())
 				.andExpect(MockMvcResultMatchers.content().string("User deleted successfully"));
 	}
 	@Test
 	void testDeleteUserByIdEndPoint2() throws Exception {
 		User testUser18=new User();
 		testUser18.setId(UUID.randomUUID());
 		testUser18.setName("Test User18");
 		addUser(testUser18);
		
 		mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete/{userId}", UUID.randomUUID()))
 				.andExpect(MockMvcResultMatchers.status().isOk())
 				.andExpect(MockMvcResultMatchers.content().string("User not found"));
 	}


 	// ------------------------ Product Tests -------------------------

	
 	@Test
 	void testAddProductEndPoint() throws JsonProcessingException, Exception{

 		Product testProduct3=new Product();
 		testProduct3.setId(UUID.randomUUID());
 		testProduct3.setName("Test Product");
 		testProduct3.setPrice(10.0);

		
		
		
 		mockMvc.perform(MockMvcRequestBuilders.post("/product/")
 				.contentType(MediaType.APPLICATION_JSON)
 				.content(objectMapper.writeValueAsString(testProduct3)))
 				.andExpect(MockMvcResultMatchers.status().isOk());
		
 		boolean found=false;

 		for(Product product: getProducts()){
 			if(product.getId().equals(testProduct3.getId()) && product.getName().equals(testProduct3.getName()) && product.getPrice()==testProduct3.getPrice()){
 				found=true;
 				break;
 			}
 		}
 		assertTrue(found,"Product should be added correctly");
 	}

	

 	@Test
 	void testGetProductsEndPoint() throws Exception{
 		Product testProduct6=new Product();
 		testProduct6.setId(UUID.randomUUID());
 		testProduct6.setName("Test Product");
 		testProduct6.setPrice(10.0);
 		addProduct(testProduct6);
		
 		MvcResult result= mockMvc.perform(MockMvcRequestBuilders.get("/product/")
 				.contentType(MediaType.APPLICATION_JSON))
 				.andExpect(MockMvcResultMatchers.status().isOk())
 				.andReturn();
 		String responseContent = result.getResponse().getContentAsString();
 		List<Product> responseProducts = objectMapper.readValue(responseContent, new TypeReference<List<Product>>() {});
		
 		assertEquals(getProducts().size(), responseProducts.size(), "Products should be returned correctly From Endpoint");
 	}

	
 	@Test
 	void testGetProductByIdEndPoint() throws Exception{
 		Product testProduct9=new Product();
 		testProduct9.setId(UUID.randomUUID());
 		testProduct9.setName("Test Product");
 		testProduct9.setPrice(10.0);
 		addProduct(testProduct9);
		
 		mockMvc.perform(MockMvcRequestBuilders.get("/product/{productId}", testProduct9.getId()))
 				.andExpect(MockMvcResultMatchers.status().isOk())
 				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(testProduct9)));
 	}


 	@Test
 	void testUpdateProductEndPoint() throws Exception{
 		Product testProduct12=new Product();
 		testProduct12.setId(UUID.randomUUID());
 		testProduct12.setName("Test Product");
 		testProduct12.setPrice(10.0);
 		addProduct(testProduct12);
 		Map<String,Object> body=new HashMap<>();
 		body.put("newName", "UpdatedName");
 		body.put("newPrice", 20.0);
 		MvcResult result= mockMvc.perform(MockMvcRequestBuilders.put("/product/update/{id}", testProduct12.getId())
 				.contentType(MediaType.APPLICATION_JSON)
 				.content(objectMapper.writeValueAsString(body)))
 				.andExpect(MockMvcResultMatchers.status().isOk())
 				.andReturn();
 		String responseContent = result.getResponse().getContentAsString();
 		Product updatedProduct = objectMapper.readValue(responseContent, Product.class);
 		assertEquals(updatedProduct.getId(),testProduct12.getId(),"Product should be updated correctly");
 		assertEquals(updatedProduct.getName(),"UpdatedName","Product name should be updated correctly");
 		assertEquals(updatedProduct.getPrice(),20.0,"Product price should be updated correctly");
 	}


 	@Test
 	void testApplyDiscountEndPoint() throws Exception{
 		Product testProduct15=new Product();
 		testProduct15.setId(UUID.randomUUID());
 		testProduct15.setName("Test Product");
 		testProduct15.setPrice(10.0);
 		addProduct(testProduct15);
 		ArrayList<UUID> productIds=new ArrayList<>();
 		productIds.add(testProduct15.getId());
 		mockMvc.perform(MockMvcRequestBuilders.put("/product/applyDiscount")
 				.contentType(MediaType.APPLICATION_JSON)
 				.param("discount", "10.0")
 				.content(objectMapper.writeValueAsString(productIds)))
 				.andExpect(MockMvcResultMatchers.status().isOk())
 				.andExpect(MockMvcResultMatchers.content().string("Discount applied successfully"));
 		assertEquals(9.0, ((Product)find("Product", testProduct15)).getPrice(),"Product should be updated correctly");
 	}


 	@Test
 	void testDeleteProductByIdEndPoint1() throws Exception{
 		Product testProduct15=new Product();
 		testProduct15.setId(UUID.randomUUID());
 		testProduct15.setName("Test Product");
 		testProduct15.setPrice(10.0);
 		addProduct(testProduct15);
 		mockMvc.perform(MockMvcRequestBuilders.delete("/product/delete/{id}", testProduct15.getId()))
 				.andExpect(MockMvcResultMatchers.status().isOk())
 				.andExpect(MockMvcResultMatchers.content().string("Product deleted successfully"));
 	}

 	// --------------------------------- Cart Tests -------------------------


	

	

 	@Test
 	void testAddCartEndPoint() throws Exception{
 		User testUser21=new User();
 		testUser21.setId(UUID.randomUUID());
 		testUser21.setName("Test User21");
 		addUser(testUser21);
 		mockMvc.perform(MockMvcRequestBuilders.post("/cart/")
 				.contentType(MediaType.APPLICATION_JSON)
 				.content(objectMapper.writeValueAsString(new Cart(UUID.randomUUID(), testUser21.getId(), new ArrayList<>())))
 				)
 				.andExpect(MockMvcResultMatchers.status().isOk());
 		boolean found=false;
 		for(Cart cart: getCarts()){
 			if(cart.getUserId().equals(testUser21.getId())){
 				found=true;
 				break;
 			}
 		}
 		assertTrue(found,"Cart should be added correctly");
 	}

	

	

 	@Test
 	void testGetCartsEndPoint() throws Exception{
 		Cart cart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
 		addCart(cart);
 		MvcResult result= mockMvc.perform(MockMvcRequestBuilders.get("/cart/")
 				.contentType(MediaType.APPLICATION_JSON))
 				.andExpect(MockMvcResultMatchers.status().isOk())
 				.andReturn();
 		String responseContent = result.getResponse().getContentAsString();
 		List<Cart> responseCarts = objectMapper.readValue(responseContent, new TypeReference<List<Cart>>() {});
 		assertEquals(getCarts().size(), responseCarts.size(), "Carts should be returned correctly From Endpoint");
 	}


	

 	@Test
 	void testGetCartByIdEndPoint() throws Exception{
 		Cart cart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
 		addCart(cart);
 		mockMvc.perform(MockMvcRequestBuilders.get("/cart/{id}", cart.getId()))
 				.andExpect(MockMvcResultMatchers.status().isOk())
 				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(cart)));
 	}

	
	

	

 	@Test
 	void testDeleteCartByIdEndPoint() throws Exception{
 		Cart cart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
 		addCart(cart);
 		mockMvc.perform(MockMvcRequestBuilders.delete("/cart/delete/{id}", cart.getId()))
 				.andExpect(MockMvcResultMatchers.status().isOk())
 				.andExpect(MockMvcResultMatchers.content().string("Cart deleted successfully"));
 	}


 	// --------------------------------- Order Tests -------------------------

	
	
	
 	@Test
 	void testAddOrderEndPoint() throws Exception{
 		Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), 10.0, new ArrayList<>());
 		mockMvc.perform(MockMvcRequestBuilders.post("/order/")
 				.contentType(MediaType.APPLICATION_JSON)
 				.content(objectMapper.writeValueAsString(order)))
 				.andExpect(MockMvcResultMatchers.status().isOk());
 		boolean found=false;
 		for(Order o: getOrders()){
 			if(o.getId().equals(order.getId())){
 				found=true;
 				break;
 			}
 		}
 		assertTrue(found,"Order should be added correctly from Endpoint");
 	}

	

	

 	@Test
 	void testGetOrdersEndPoint() throws Exception{

 		Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), 10.0, new ArrayList<>());
 		addOrder(order);
 		MvcResult result= mockMvc.perform(MockMvcRequestBuilders.get("/order/")
 				.contentType(MediaType.APPLICATION_JSON))
 				.andExpect(MockMvcResultMatchers.status().isOk())
 				.andReturn();
 		String responseContent = result.getResponse().getContentAsString();
 		List<Order> responseOrders = objectMapper.readValue(responseContent, new TypeReference<List<Order>>() {});
 		assertEquals(getOrders().size(), responseOrders.size(), "Orders should be returned correctly From Endpoint");
 	}

	

	

 	@Test
 	void testGetOrderByIdEndPoint() throws Exception{
 		Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), 10.0, new ArrayList<>());
 		addOrder(order);
 		mockMvc.perform(MockMvcRequestBuilders.get("/order/{id}", order.getId()))
 				.andExpect(MockMvcResultMatchers.status().isOk())
 				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(order)))
 				.andReturn();
 		// String responseContent = result.getResponse().getContentAsString();
 		// Order responseOrder = objectMapper.readValue(responseContent, Order.class);
 		// assertEquals(order.getId(), responseOrder.getId(), "Order should be returned correctly From Endpoint");
 	}

	
	

 	@Test
 	void testDeleteOrderByIdEndPoint() throws Exception{
 		Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), 10.0, new ArrayList<>());
 		addOrder(order);
 		mockMvc.perform(MockMvcRequestBuilders.delete("/order/delete/{id}", order.getId()))
 				.andExpect(MockMvcResultMatchers.status().isOk())
 				.andExpect(MockMvcResultMatchers.content().string("Order deleted successfully"));
 	}

 	@Test
 	void testDeleteOrderByIdEndPoint2() throws Exception{
		
 		mockMvc.perform(MockMvcRequestBuilders.delete("/order/delete/{id}", UUID.randomUUID()))
 				.andExpect(MockMvcResultMatchers.status().isOk())
 				.andExpect(MockMvcResultMatchers.content().string("Order not found"));
 	}


// 	// --------------------------------- UserService Tests -------------------------

     @BeforeEach
     void removeUsers() throws Exception{
         userRepository.saveAll(new ArrayList<>());
     }

//     @AfterEach
//     void removeUsersAfter() throws Exception{
//         userRepository.saveAll(new ArrayList<>());
//     }
 // ---------------1.AddUser
    @Test
    void addUserNormally()throws Exception{
        User newUser=new User("Youssef",new ArrayList<Order>());
        User AddedUser=userService.addUser(newUser);
        assertEquals(newUser.getId(),AddedUser.getId(),"Check User Is saved");
        assertEquals(newUser.getName(),AddedUser.getName(),"Check User Name of the created User");
        assertEquals(newUser.getOrders(),AddedUser.getOrders(), "CHeck Orders of the Created User");
    }

    @Test
     void addUserWithoutID() throws Exception{
          User newUser=userService.addUser(new User());
          assertNotNull(newUser.getId());
          assertNotNull(newUser.getOrders());
          assertNull(newUser.getName());
    }
    @Test
     void addUserWithOrder() throws Exception{
        User newUser=new User("Youssef",new ArrayList<Order>());
        List<Product> products=new ArrayList<Product>();
        Product p= new Product("Milk",55);
        products.add(p);
        List<Order> order=new ArrayList<Order>();
        Order o=new Order(newUser.getId(),55,products);
        newUser.setOrders(order);
        User addedUser=userService.addUser(newUser);
        assertNotNull(addedUser,"Users Added");
        assertEquals(addedUser.getOrders(),order,"User's Order Correct");

       }

      // ---------------2.getUsers
     @Test
     void getUsersNormally()throws Exception{
         User newUser=userService.addUser(new User("Youssef",new ArrayList<Order>()));
         User newUser1=userService.addUser(new User("Youssef",new ArrayList<Order>()));
         User newUser2=userService.addUser(new User("Youssef",new ArrayList<Order>()));
         List<User> expectedUsers = Arrays.asList(newUser, newUser1, newUser2);
         assertEquals(expectedUsers.containsAll(userService.getUsers()),userService.getUsers().containsAll(expectedUsers),"All users Retrived");
         assertEquals(3,userService.getUsers().size(),"Number of Users correct");

     }
    @Test
     void getEmptyListOfUsers() throws Exception{
        List<User>users=userService.getUsers();
        assertNotNull(users,"Users List not Null");
        assertTrue(users.isEmpty());
    }
    @Test
     void getUsersAfterDeletingOne()throws Exception{
        User newUser=userService.addUser(new User("Youssef",new ArrayList<Order>()));
        User newUser1=userService.addUser(new User("Youssef2",new ArrayList<Order>()));
        assertEquals(2,userService.getUsers().size(),"Number of Users is Correct");
        userService.deleteUserById(userService.getUsers().get(0).getId());
        assertEquals(1,userService.getUsers().size());
    }
// -------------3.GetUserById

    @Test
    void getUserbyIdNormally(){
         UUID temp=UUID.randomUUID();
         User newUser=userService.addUser(new User(temp,"Youssef",new ArrayList<Order>()));
         assertNotNull(newUser);
         assertNotNull(userService.getUserById(temp),"User With this ID exists");
    }

    @Test
     void getUserWithWrongId(){
         UUID temp=UUID.randomUUID();
         User newUser=userService.addUser(new User("Youssef",new ArrayList<Order>()));
         assertNotNull(newUser);
         assertNull(userService.getUserById(temp),"User With this ID is Wrong");
    }

    @Test
     void getUserWithIdandVerifyNameAndOrder(){
        User newUser=userService.addUser(new User("Youssef",new ArrayList<Order>()));
        assertNotNull(newUser);
        assertEquals(userService.getUserById(newUser.getId()).getName(),newUser.getName(),"User Name Matches");
        assertEquals(userService.getUserById(newUser.getId()).getOrders(),newUser.getOrders(),"User Order Matches");
    }
     // -------------4.GetUserOrderById

     @Test
     void getUserOrderWhileEmptyWithID(){
         UUID temp=UUID.randomUUID();
         User newUser=userService.addUser(new User(temp,"Youssef",new ArrayList<Order>()));
         assertEquals(0,userService.getUserById(temp).getOrders().size(),"User Have no Orders");
     }

     @Test
     void getUserOrderWithMultipleOrdersUsingID(){
         UUID userId = UUID.randomUUID();
         User newUser = new User(userId, "Youssef", new ArrayList<>());
         Order order1 = new Order(userId, 55, new ArrayList<>());
         Order order2 = new Order(userId, 110, new ArrayList<>());
         newUser.setOrders(Arrays.asList(order1, order2));
         userService.addUser(newUser);
         assertNotNull(userService.getUserById(userId));
         User addedUser = userService.getUserById(newUser.getId());
         assertNotNull(addedUser.getOrders());
         assertEquals(2,addedUser.getOrders().size());
         assertTrue(addedUser.getOrders().get(0).getUserId().equals(userId)&&addedUser.getOrders().get(1).getUserId().equals(userId));
     }
     @Test
     void getOrdersWithOnlyIdAssociatedToItsUser(){

         User user1 = userService.addUser(new User("Charlie", new ArrayList<>()));
         User user2 = userService.addUser(new User("David", new ArrayList<>()));
         UUID user1Id=user1.getId();
         UUID user2Id=user2.getId();
         Order order1 = new Order(user1Id, 55, new ArrayList<>());
         Order order2 = new Order(user2Id, 110, new ArrayList<>());
         List<Order> ordersForUser1 = new ArrayList<>();
         ordersForUser1.add(order1);
         List<Order> ordersForUser2 = new ArrayList<>();
         ordersForUser2.add(order2);
         userService.deleteUserById(user1Id);
         userService.deleteUserById(user2Id);
         User user3=userService.addUser(new User(user1.getId(),"Charlie", ordersForUser1));
         User user4=userService.addUser(new User(user2.getId(),"David", ordersForUser2));
         assertEquals(1, user3.getOrders().size(), "User1 should have only 1 order");
         assertTrue(ordersForUser1.contains(order1), "Order should belong to User1");
         assertFalse(ordersForUser1.contains(order2), "Order should not belong to User2");
     }

     // -------------5.Add Order To cart

     @Test
     void AddOrderWithWrongUserID(){
         User user1 = userService.addUser(new User("Charlie", new ArrayList<>()));
         User user2 = userService.addUser(new User("David", new ArrayList<>()));
         UUID user1Id=user1.getId();
         UUID user2Id=user2.getId();
         Order order1 = new Order(user1Id, 55, new ArrayList<>());
         Order order2 = new Order(user2Id, 110, new ArrayList<>());
         assertThrows(Exception.class,()->userService.addOrderToUser(UUID.randomUUID()));
     }

     @Test
     void AddOrderWithCorrectUserIDAndNullCart(){
         User user1 = userService.addUser(new User("Charlie", new ArrayList<>()));
         assertThrows(Exception.class,()->userService.addOrderToUser(user1.getId()));
     }
     @Test
     void AddOrderWithCorrectUserIDAndCart(){
         User newUser=userService.addUser(new User("Youssef",new ArrayList<Order>()));
         Product AddedProduct1=productService.addProduct(new Product(UUID.randomUUID(),"Milk",25));
         List<Product> products=new ArrayList<Product>();
         products.add(AddedProduct1);
         Cart newCart=cartService.addCart(new Cart(UUID.randomUUID(),newUser.getId(),products));
         userService.addOrderToUser(newUser.getId());
         assertTrue(userService.getUserById(newUser.getId()).getOrders().size()>0);
     }


     // -------------6.EmptyCart to be Done
     @Test
     void EmptyCartWithWrongUserID(){
         User newUser=userService.addUser(new User("Youssef",new ArrayList<Order>()));
         Product AddedProduct1=productService.addProduct(new Product(UUID.randomUUID(),"Milk",25));
         List<Product> products=new ArrayList<Product>();
         products.add(AddedProduct1);
         Cart newCart=cartService.addCart(new Cart(UUID.randomUUID(),newUser.getId(),products));
         userService.addOrderToUser(newUser.getId());
         assertThrows(Exception.class,()->userService.emptyCart(UUID.randomUUID()));
     }

     @Test
     void EmptyCartWithCorrectUserIDAndAnEmptyCart(){
         User newUser=userService.addUser(new User("Youssef",new ArrayList<Order>()));
         Cart newCart=cartService.addCart(new Cart(UUID.randomUUID(),newUser.getId(),new ArrayList<Product>()));
         userService.emptyCart(newUser.getId());
         assertTrue(cartService.getCartByUserId(newUser.getId()).getProducts().size()==0);
     }
     @Test
     void  EmptyCartWithCorrectUserIDAndStuffInCart(){
         User newUser=userService.addUser(new User("Youssef",new ArrayList<Order>()));
         Product AddedProduct1=productService.addProduct(new Product(UUID.randomUUID(),"Milk",25));
         List<Product> products=new ArrayList<Product>();
         products.add(AddedProduct1);
         Cart newCart=cartService.addCart(new Cart(UUID.randomUUID(),newUser.getId(),products));
         userService.emptyCart(newUser.getId());
         assertTrue(cartService.getCartByUserId(newUser.getId()).getProducts().size()==0);
     }


     // -------------7.Remove Order From User By ID
     @Test
     void RemoveOrderFromListOfOneOrderNormaly(){
         UUID id=UUID.randomUUID();
         UUID OrderID=UUID.randomUUID();
         User user1 = (new User(id,"Youssef", new ArrayList<Order>()));
         Order order1 = new Order(OrderID,id, 55, new ArrayList<Product>());
         List<Order> orders=new ArrayList<Order>();
         orders.add(order1);
         user1.setOrders(orders);
         User CreatedUser=userService.addUser(user1);
         userService.removeOrderFromUser(CreatedUser.getId(),CreatedUser.getOrders().get(0).getId());
         assertEquals(0,userService.getUserById(id).getOrders().size());
     }

     @Test
     void RemoveOrderFromListOfMultipleOrders(){
         UUID id=UUID.randomUUID();
         UUID Order1ID=UUID.randomUUID();
         UUID Order2ID=UUID.randomUUID();
         User user1 = (new User(id,"Youssef", new ArrayList<Order>()));
         Order order1 = new Order(Order1ID,id, 55, new ArrayList<Product>());
         Order order2 = new Order(Order2ID,id, 100, new ArrayList<Product>());
         List<Order> orders=new ArrayList<Order>();
         orders.add(order1);
         orders.add(order2);
         user1.setOrders(orders);
         User CreatedUser=userService.addUser(user1);
         userService.removeOrderFromUser(CreatedUser.getId(),CreatedUser.getOrders().get(0).getId());
         assertEquals(1,userService.getUserById(id).getOrders().size());
     }
     @Test
     void RemoveMultipleOrdesrFromAnUsersOrdersList(){
         UUID id=UUID.randomUUID();
         UUID Order1ID=UUID.randomUUID();
         UUID Order2ID=UUID.randomUUID();
         User user1 = (new User(id,"Youssef", new ArrayList<Order>()));
         Order order1 = new Order(Order1ID,id, 55, new ArrayList<Product>());
         Order order2 = new Order(Order2ID,id, 100, new ArrayList<Product>());
         List<Order> orders=new ArrayList<Order>();
         orders.add(order1);
         orders.add(order2);
         user1.setOrders(orders);
         User CreatedUser=userService.addUser(user1);
         userService.removeOrderFromUser(CreatedUser.getId(),Order1ID);
         userService.removeOrderFromUser(CreatedUser.getId(),Order2ID);
         assertEquals(0,userService.getUserById(id).getOrders().size());
     }
     // -------------8.Delete User By ID

     @Test
     void DeleteUserNormally(){
         User user1 = userService.addUser(new User("Charlie", new ArrayList<>()));
         userService.deleteUserById(user1.getId());
         assertEquals(0,userService.getUsers().size());
         assertTrue(userService.getUsers().isEmpty());
     }

     @Test
     void DeleteOneUserFromListOfUsers(){
         User user1 = userService.addUser(new User("Charlie", new ArrayList<>()));
         User user2 = userService.addUser(new User("David", new ArrayList<>()));
         userService.deleteUserById(user1.getId());
         assertEquals(1,userService.getUsers().size());
         assertFalse(userService.getUsers().isEmpty());
     }

     @Test
     void DeleteMultipleUserFromListOfUsers(){
         User user1 = userService.addUser(new User("Charlie", new ArrayList<>()));
         User user2 = userService.addUser(new User("David", new ArrayList<>()));
         userService.deleteUserById(user1.getId());
         userService.deleteUserById(user2.getId());
         assertEquals(0,userService.getUsers().size());
         assertTrue(userService.getUsers().isEmpty());
     }


// 	// --------------------------------- ProductService Tests -------------------------
    @BeforeEach
    void removeProducts() throws Exception{
        productRepository.saveAll(new ArrayList<>());
    }
      // ---------------1.AddProduct
    @Test
    void addProductNormallyWithoutID()throws Exception{
        Product newProduct=new Product("Milk",25);
        Product AddedProduct=productService.addProduct(newProduct);
        assertEquals(newProduct.getId(),AddedProduct.getId(),"Check User Is saved");
        assertEquals(newProduct.getName(),AddedProduct.getName(),"Check User Name of the created User");
        assertEquals(newProduct.getPrice(),AddedProduct.getPrice(), "CHeck Orders of the Created User");
    }

        @Test
     void addProductWithID() throws Exception{
         Product AddedProduct=productService.addProduct(new Product(UUID.randomUUID(),"Milk",25));
         assertTrue(productService.getProducts().size()!=0);
         assertEquals(productService.getProducts().getLast().getId(),(AddedProduct.getId()));
        }

        @Test
     void addMultipleProducts() throws Exception{
            Product AddedProduct1=productService.addProduct(new Product(UUID.randomUUID(),"Milk",25));
            Product AddedProduct2=productService.addProduct(new Product(UUID.randomUUID(),"Milk",25));
            assertTrue(productService.getProducts().size()!=0);
            assertEquals(productService.getProducts().getLast().getId(),(AddedProduct2.getId()));
            assertEquals(productService.getProducts().getFirst().getId(),(AddedProduct1.getId()));
        }
      // ---------------2.GetAllProducts

     @Test
     void GetProductsWhileEmpty() throws  Exception{
        assertEquals(0,productService.getProducts().size());
        assertTrue(productService.getProducts().isEmpty());
     }
     @Test
     void GetProductsFromListofOneProduct() throws Exception{
         Product AddedProduct1=productService.addProduct(new Product(UUID.randomUUID(),"Milk",25));
         assertTrue(!productService.getProducts().isEmpty());
         assertEquals(AddedProduct1.getId(),productService.getProducts().get(0).getId());
     }
     @Test
     void GetProductsAfterDeletingOne() throws Exception{
        Product AddedProduct1=productService.addProduct(new Product(UUID.randomUUID(),"Milk",25));
        Product AddedProduct2=productService.addProduct(new Product(UUID.randomUUID(),"Milk",25));
        productService.deleteProductById(productService.getProducts().get(0).getId());
        assertTrue(productService.getProducts().size()!=0);
        assertTrue(productService.getProducts().size()==1);
        assertNotNull(productService.getProducts());
     }

      // ---------------3.GetProductById
     @Test
     void GetProductByCorrectIdFromAListOfOneProduct() throws Exception{
         Product AddedProduct1=productService.addProduct(new Product(UUID.randomUUID(),"Milk",25));
         assertNotNull(productService.getProductById(AddedProduct1.getId()));
     }

     @Test
     void GetProductByWrongID()throws Exception{
         UUID productID=UUID.randomUUID();
         Product AddedProduct1=productService.addProduct(new Product(productID,"Milk",25));
         Exception exception=assertThrows(Exception.class,()-> productService.getProductById(UUID.randomUUID()));
     }

     @Test
     void GetProductByCorrectIdFromAListOfManyProducts() throws Exception{
         Product AddedProduct1=productService.addProduct(new Product(UUID.randomUUID(),"Milk",25));
         Product AddedProduct2=productService.addProduct(new Product(UUID.randomUUID(),"Cheese",45));
         Product AddedProduct3=productService.addProduct(new Product(UUID.randomUUID(),"Choclate",10));
         Product AddedProduct4=productService.addProduct(new Product(UUID.randomUUID(),"Chips",5));
         Product AddedProduct5=productService.addProduct(new Product(UUID.randomUUID(),"Pasta",20));
         assertEquals(AddedProduct5.getName(),productService.getProductById(AddedProduct5.getId()).getName());
         assertEquals(AddedProduct3.getName(),productService.getProductById(AddedProduct3.getId()).getName());

     }
      // ---------------4.UpdateProduct

     @Test
     void UpdateProductThatItsIDDoesNotExist(){
         Product AddedProduct1=productService.addProduct(new Product(UUID.randomUUID(),"Milk",25));
         UUID wrongID=UUID.randomUUID();
         assertThrows(Exception.class,()->productService.updateProduct(wrongID,"Chedder",30));
     }

     @Test
     void UpdateProductNormally(){
         Product AddedProduct1=productService.addProduct(new Product(UUID.randomUUID(),"Milk",25));
         productService.updateProduct(AddedProduct1.getId(),"Chedder",45);
         assertEquals("Chedder",productService.getProductById(AddedProduct1.getId()).getName(),"Name Updated Sucssesfully");
         assertEquals(productService.getProductById(AddedProduct1.getId()).getPrice(),45,"Price Updated Sucssesfully");
     }

     @Test
     void UpdateProductWithOnlyOneParameter(){
         Product AddedProduct1=productService.addProduct(new Product(UUID.randomUUID(),"Milk",25));
         productService.updateProduct(AddedProduct1.getId(),"Cheese",45);
         assertEquals("Cheese",productService.getProductById(AddedProduct1.getId()).getName(),"Name Updated Sucssesfully");
     }
      // ---------------5.ApplyDiscount
     @Test
     void ApplyDiscountNormally(){
         Product AddedProduct1=productService.addProduct(new Product(UUID.randomUUID(),"Milk",25));
         Product AddedProduct2=productService.addProduct(new Product(UUID.randomUUID(),"Cheese",45));
         Product AddedProduct3=productService.addProduct(new Product(UUID.randomUUID(),"Choclate",10));
         ArrayList<UUID> productsID=new ArrayList<UUID>();
         productsID.add(AddedProduct1.getId());
         productsID.add(AddedProduct2.getId());
         productsID.add(AddedProduct3.getId());
         productService.applyDiscount(10,productsID);
         assertEquals(productService.getProductById(AddedProduct1.getId()).getPrice(),22.5,"Product 1 Updated Correctly");
         assertEquals(productService.getProductById(AddedProduct2.getId()).getPrice(),40.5,"Product 2 Updated Correctly");
         assertEquals(productService.getProductById(AddedProduct3.getId()).getPrice(),9.0,"Product 3 Updated Correctly");
     }

     @Test
     void ApplyDiscountWithNegativeNumber(){
         Product AddedProduct1=productService.addProduct(new Product(UUID.randomUUID(),"Milk",25));
         ArrayList<UUID> productsID=new ArrayList<UUID>();
         productsID.add(AddedProduct1.getId());
         assertThrows(Exception.class,()->productService.applyDiscount(-20.0,productsID));
     }
     @Test
     void ApplyDiscountOverAnotherDiscount(){
         Product AddedProduct3=productService.addProduct(new Product(UUID.randomUUID(),"Choclate",10));
         ArrayList<UUID> productsID=new ArrayList<UUID>();
         productsID.add(AddedProduct3.getId());
         productService.applyDiscount(10,productsID);
         productService.applyDiscount(10,productsID);
         assertEquals(productService.getProductById(AddedProduct3.getId()).getPrice(),8.1,"Product 3 Updated Correctly");
     }
      // ---------------6.DeleteProduct

     @Test
     void DeleteProductNormally(){
         Product AddedProduct1=productService.addProduct(new Product(UUID.randomUUID(),"Milk",25));
         productService.deleteProductById(AddedProduct1.getId());
         assertEquals(0,productService.getProducts().size());
     }
     @Test
     void DeleteProductwithWrongID(){
         UUID wrongId=UUID.randomUUID();
         Product AddedProduct1=productService.addProduct(new Product(UUID.randomUUID(),"Milk",25));
         assertThrows(Exception.class,()->productService.deleteProductById(wrongId));
     }
     @Test
     void DeleteOneProductFromAListofManyProducts(){
         Product AddedProduct1=productService.addProduct(new Product(UUID.randomUUID(),"Milk",25));
         Product AddedProduct2=productService.addProduct(new Product(UUID.randomUUID(),"Cheese",45));
         Product AddedProduct3=productService.addProduct(new Product(UUID.randomUUID(),"Choclate",10));
         productService.deleteProductById(AddedProduct1.getId());
         assertTrue(productService.getProducts().size()>0);
         assertEquals(productService.getProducts().size(),2);
     }
// 	// --------------------------------- CartService Tests -------------------------
    @BeforeEach
    void removeCart() throws Exception{
        cartRepository.saveAll(new ArrayList<>());
    }
     // // ---------------1.AddCart
    @Test
    void AddCartWithoutID(){
        Product AddedProduct1=productService.addProduct(new Product(UUID.randomUUID(),"Milk",25));
        List<Product> products=new ArrayList<Product>();
        products.add(AddedProduct1);
         Cart newCart=cartService.addCart(new Cart(null,products));
         assertNotNull(newCart);
         assertTrue(cartService.getCarts().size()!=0);
     }

     @Test
     void AddCartWithID(){
         User newUser=userService.addUser(new User("Youssef",new ArrayList<Order>()));
         Product AddedProduct1=productService.addProduct(new Product(UUID.randomUUID(),"Milk",25));
         List<Product> products=new ArrayList<Product>();
         products.add(AddedProduct1);
         Cart newCart=cartService.addCart(new Cart(UUID.randomUUID(),newUser.getId(),products));
         assertNotNull(newCart);
         assertTrue(cartService.getCarts().size()!=0);
     }
     @Test
     void Add2CartsWithTheSameUser(){
         User newUser=userService.addUser(new User("Youssef",new ArrayList<Order>()));
         Product AddedProduct1=productService.addProduct(new Product(UUID.randomUUID(),"Milk",25));
         List<Product> products=new ArrayList<Product>();
         products.add(AddedProduct1);
         Cart newCart=cartService.addCart(new Cart(UUID.randomUUID(),newUser.getId(),products));
         Cart newCart1=cartService.addCart(new Cart(UUID.randomUUID(),newUser.getId(),products));
         assertTrue(cartService.getCarts().size()==2,"Number of Carts is Correct");
         assertFalse(cartService.getCarts().getFirst().getUserId()==cartService.getCarts().getLast().getUserId());
     }
     // // ---------------2.GetAllCarts
     @Test
     void GetAllCartsWhileEmpty(){
        assertNotNull(cartService.getCarts());
        assertTrue(cartService.getCarts().isEmpty());
     }

     @Test
     void GetAllCartsWhichContainsOneCart(){
         User newUser=userService.addUser(new User("Youssef",new ArrayList<Order>()));
         Product AddedProduct1=productService.addProduct(new Product(UUID.randomUUID(),"Milk",25));
         List<Product> products=new ArrayList<Product>();
         products.add(AddedProduct1);
         Cart newCart=cartService.addCart(new Cart(UUID.randomUUID(),newUser.getId(),products));
         assertTrue(cartService.getCarts().size()==1,"Number of Carts is Correct");
         assertEquals(newCart.getUserId(),cartService.getCarts().getFirst().getUserId());
     }

     @Test
     void GetAllCartsWhichContainsMultipleCarts(){
         User newUser=userService.addUser(new User("Youssef",new ArrayList<Order>()));
         Product AddedProduct1=productService.addProduct(new Product(UUID.randomUUID(),"Milk",25));
         List<Product> products=new ArrayList<Product>();
         products.add(AddedProduct1);
         Cart newCart=cartService.addCart(new Cart(UUID.randomUUID(),newUser.getId(),products));
         Cart newCart2=cartService.addCart(new Cart(UUID.randomUUID(),newUser.getId(),products));
         Cart newCart3=cartService.addCart(new Cart(UUID.randomUUID(),newUser.getId(),products));
         List<Cart> carts=new ArrayList<Cart>();
         carts.add(newCart);
         carts.add(newCart2);
         carts.add(newCart3);
         assertTrue(cartService.getCarts().size()==3,"Number of Carts is Correct");
     }
     // // ---------------3.GetCartByID
//
     @Test
     void GetCartByWrongID(){
         User newUser=userService.addUser(new User("Youssef",new ArrayList<Order>()));
         Product AddedProduct1=productService.addProduct(new Product(UUID.randomUUID(),"Milk",25));
         List<Product> products=new ArrayList<Product>();
         products.add(AddedProduct1);
         Cart newCart=cartService.addCart(new Cart(UUID.randomUUID(),newUser.getId(),products));
         cartService.addCart(newCart);
         assertNull(cartService.getCartById(UUID.randomUUID()));
     }

     @Test
     void GetCartByNullID(){
         User newUser=userService.addUser(new User("Youssef",new ArrayList<Order>()));
         Product AddedProduct1=productService.addProduct(new Product(UUID.randomUUID(),"Milk",25));
         List<Product> products=new ArrayList<Product>();
         products.add(AddedProduct1);
         Cart newCart=cartService.addCart(new Cart(UUID.randomUUID(),newUser.getId(),products));
         cartService.addCart(newCart);
         assertNull(cartService.getCartById(null));
     }
     @Test
     void GetCartByCorrectID(){
         User newUser=userService.addUser(new User("Youssef",new ArrayList<Order>()));
         Product AddedProduct1=productService.addProduct(new Product(UUID.randomUUID(),"Milk",25));
         List<Product> products=new ArrayList<Product>();
         products.add(AddedProduct1);
         Cart newCart=cartService.addCart(new Cart(UUID.randomUUID(),newUser.getId(),products));
         cartService.addCart(newCart);
         assertNotNull(cartService.getCartById(newCart.getId()));
     }
// // ---------------4.GetCartByUserID

     @Test
         void GetCartByWrongUserID() {
         User newUser = userService.addUser(new User("Youssef", new ArrayList<Order>()));
         Product AddedProduct1 = productService.addProduct(new Product(UUID.randomUUID(), "Milk", 25));
         List<Product> products = new ArrayList<Product>();
         products.add(AddedProduct1);
         Cart newCart = cartService.addCart(new Cart(UUID.randomUUID(), newUser.getId(), products));
         cartService.addCart(newCart);
         assertNull(cartService.getCartByUserId(UUID.randomUUID()));
     }
     @Test
     void GetCartByNullUserID() {
         User newUser = userService.addUser(new User("Youssef", new ArrayList<Order>()));
         Product AddedProduct1 = productService.addProduct(new Product(UUID.randomUUID(), "Milk", 25));
         List<Product> products = new ArrayList<Product>();
         products.add(AddedProduct1);
         Cart newCart = cartService.addCart(new Cart(UUID.randomUUID(), newUser.getId(), products));
         cartService.addCart(newCart);
         assertNull(cartService.getCartByUserId(null));
     }
     @Test
     void GetCartByCorrectUserID() {
         User newUser = userService.addUser(new User("Youssef", new ArrayList<Order>()));
         Product AddedProduct1 = productService.addProduct(new Product(UUID.randomUUID(), "Milk", 25));
         List<Product> products = new ArrayList<Product>();
         products.add(AddedProduct1);
         Cart newCart = cartService.addCart(new Cart(UUID.randomUUID(), newUser.getId(), products));
         cartService.addCart(newCart);
         assertNotNull(cartService.getCartByUserId(newCart.getUserId()));
     }

     // // ---------------5.AddProductToCart
     @Test
     void AddProductWithWrongCartID(){
         User newUser = userService.addUser(new User("Youssef", new ArrayList<Order>()));
         Product AddedProduct1 = productService.addProduct(new Product(UUID.randomUUID(), "Milk", 25));
         List<Product> products = new ArrayList<Product>();
         products.add(AddedProduct1);
         Cart newCart = cartService.addCart(new Cart(UUID.randomUUID(), newUser.getId(), products));
         cartService.addCart(newCart);
         assertThrows(Exception.class,()->cartService.addProductToCart(UUID.randomUUID(),AddedProduct1));
     }
     @Test
     void AddNullProductToCartWithCorrectID(){
         User newUser = userService.addUser(new User("Youssef", new ArrayList<Order>()));
         Product AddedProduct1 = productService.addProduct(new Product(UUID.randomUUID(), "Milk", 25));
         List<Product> products = new ArrayList<Product>();
         products.add(AddedProduct1);
         Cart newCart = cartService.addCart(new Cart(UUID.randomUUID(), newUser.getId(), products));
         cartService.addCart(newCart);
         assertThrows(Exception.class,()->cartService.addProductToCart(UUID.randomUUID(),null));
     }
     @Test
     void AddProductToCartWithCorrectID(){
         User newUser = userService.addUser(new User("Youssef", new ArrayList<Order>()));
         Product AddedProduct1 = productService.addProduct(new Product(UUID.randomUUID(), "Milk", 25));
         Cart newCart = cartService.addCart(new Cart(UUID.randomUUID(), newUser.getId(), new ArrayList<Product>()));
         cartService.addProductToCart(cartService.getCarts().getFirst().getId(),AddedProduct1);
         assertTrue(cartService.getCarts().getFirst().getProducts().size()!=0);
     }
     // // ---------------6.DeleteProductFromCart
     @Test
     void DeleteProductWithWrongCartID(){
         User newUser = userService.addUser(new User("Youssef", new ArrayList<Order>()));
         Product AddedProduct1 = productService.addProduct(new Product(UUID.randomUUID(), "Milk", 25));
         List<Product> products = new ArrayList<Product>();
         products.add(AddedProduct1);
         Cart newCart = cartService.addCart(new Cart(UUID.randomUUID(), newUser.getId(), products));
         cartService.addCart(newCart);
         assertThrows(Exception.class,()->cartService.deleteProductFromCart(UUID.randomUUID(),AddedProduct1));
     }
     @Test
     void DeleteNullProductFromCartWithCorrectID(){
         User newUser = userService.addUser(new User("Youssef", new ArrayList<Order>()));
         Product AddedProduct1 = productService.addProduct(new Product(UUID.randomUUID(), "Milk", 25));
         List<Product> products = new ArrayList<Product>();
         products.add(AddedProduct1);
         Cart newCart = cartService.addCart(new Cart(UUID.randomUUID(), newUser.getId(), products));
         cartService.addCart(newCart);
         assertThrows(Exception.class,()->cartService.deleteProductFromCart(UUID.randomUUID(),null));
     }
     @Test
     void DeleteProductFromCartWithCorrectID(){
         User newUser = userService.addUser(new User("Youssef", new ArrayList<Order>()));
         Product AddedProduct1 = productService.addProduct(new Product(UUID.randomUUID(), "Milk", 25));
         List<Product> products = new ArrayList<Product>();
         products.add(AddedProduct1);
         Cart newCart = cartService.addCart(new Cart(UUID.randomUUID(), newUser.getId(), products));
         cartService.deleteProductFromCart(cartService.getCartById(newCart.getId()).getId(),AddedProduct1);
         assertTrue(cartService.getCartByUserId(newUser.getId()).getProducts().size()==0);
     }
     // // ---------------7.DeleteCartByID
     @Test
     void DeleteCartByWrongID(){
         User newUser = userService.addUser(new User("Youssef", new ArrayList<Order>()));
         Product AddedProduct1 = productService.addProduct(new Product(UUID.randomUUID(), "Milk", 25));
         List<Product> products = new ArrayList<Product>();
         products.add(AddedProduct1);
         Cart newCart = cartService.addCart(new Cart(UUID.randomUUID(), newUser.getId(), products));
         cartService.addCart(newCart);
         assertThrows(Exception.class,()->cartService.deleteCartById(UUID.randomUUID()));
     }
     @Test
     void DeleteCartByNullID() {
         User newUser = userService.addUser(new User("Youssef", new ArrayList<Order>()));
         Product AddedProduct1 = productService.addProduct(new Product(UUID.randomUUID(), "Milk", 25));
         List<Product> products = new ArrayList<Product>();
         products.add(AddedProduct1);
         Cart newCart = cartService.addCart(new Cart(UUID.randomUUID(), newUser.getId(), products));
         cartService.addCart(newCart);
         assertThrows(Exception.class,()-> cartService.deleteCartById(null));
     }
     @Test
     void DeleteCartByCorrectID(){
         User newUser = userService.addUser(new User("Youssef", new ArrayList<Order>()));
         Product AddedProduct1 = productService.addProduct(new Product(UUID.randomUUID(), "Milk", 25));
         List<Product> products = new ArrayList<Product>();
         products.add(AddedProduct1);
         Cart newCart = cartService.addCart(new Cart(UUID.randomUUID(), newUser.getId(), products));
         cartService.addCart(newCart);
         cartService.deleteCartById(newCart.getId());
         assertTrue(cartService.getCarts().isEmpty());
     }
// 	// --------------------------------- OrderService Tests -------------------------

    @BeforeEach
    void removeOrders() throws Exception{
        orderRepository.saveAll(new ArrayList<>());
    }
    // // ---------------1.AddProduct
    @Test
     void AddOrderNormally(){
        User newUser=userService.addUser(new User("Youssef",new ArrayList<Order>()));
        Product AddedProduct1=productService.addProduct(new Product(UUID.randomUUID(),"Milk",25));
        Product AddedProduct2=productService.addProduct(new Product(UUID.randomUUID(),"Cheese",45));
        List<Product>products=new ArrayList<Product>();
        products.add(AddedProduct1);
        products.add(AddedProduct2);
        Order newOrder=new Order(newUser.getId(),200,products);
        orderService.addOrder(newOrder);
        assertTrue(orderService.getOrders().size()>0);
        assertEquals(orderService.getOrders().get(0).getProducts().getFirst().getName(),products.getFirst().getName());
        assertEquals(orderService.getOrders().size(),1);
    }

    @Test
     void AddOrderAlreadyExist(){
        User newUser=userService.addUser(new User("Youssef",new ArrayList<Order>()));
        Product AddedProduct1=productService.addProduct(new Product(UUID.randomUUID(),"Milk",25));
        Product AddedProduct2=productService.addProduct(new Product(UUID.randomUUID(),"Cheese",45));
        List<Product>products=new ArrayList<Product>();
        products.add(AddedProduct1);
        products.add(AddedProduct2);
        Order newOrder=new Order(newUser.getId(),200,products);
        orderService.addOrder(newOrder);
        assertThrows(Exception.class,()->orderService.addOrder(newOrder));
    }
    @Test
     void AddMultipleOrders(){
		User newUser=userService.addUser(new User("Youssef",new ArrayList<Order>()));
		Product AddedProduct1=productService.addProduct(new Product(UUID.randomUUID(),"Milk",25));
		Product AddedProduct2=productService.addProduct(new Product(UUID.randomUUID(),"Cheese",45));
		List<Product>products=new ArrayList<Product>();
		products.add(AddedProduct1);
		products.add(AddedProduct2);
		Order newOrder=new Order(newUser.getId(),200,products);
		Order newOrder2=new Order(newUser.getId(),200,products);
		orderService.addOrder(newOrder);
		orderService.addOrder(newOrder2);
		assertTrue(orderService.getOrders().size()>0);
		assertEquals(orderService.getOrders().get(0).getProducts().getFirst().getName(),products.getFirst().getName());
		assertEquals(orderService.getOrders().size(),2);
    }
          // ---------------2.GetAllOrders
         @Test
         void GetOrdersShouldReturnAnOrder(){
            User newUser=userService.addUser(new User("Youssef",new ArrayList<Order>()));
            Product AddedProduct1=productService.addProduct(new Product(UUID.randomUUID(),"Milk",25));
            Product AddedProduct2=productService.addProduct(new Product(UUID.randomUUID(),"Cheese",45));
            List<Product>products=new ArrayList<Product>();
            products.add(AddedProduct1);
            products.add(AddedProduct2);
            Order newOrder=new Order(newUser.getId(),200,products);
            Order newOrder2=new Order(newUser.getId(),100,products);
            orderService.addOrder(newOrder);
            orderService.addOrder(newOrder2);
            assertTrue(orderService.getOrders().size()>0);
            assertTrue(orderService.getOrders().getLast().getId().equals(newOrder2.getId()));
            assertEquals(orderService.getOrders().size(),2);
         }

         @Test
         void GetEmptyOrdersList(){
            assertNotNull(orderService.getOrders());
            assertTrue(orderService.getOrders().size()==0);
         }

         @Test
         void GetOrdersListAfterDeletingOrder(){
             User newUser=userService.addUser(new User("Youssef",new ArrayList<Order>()));
             Product AddedProduct1=productService.addProduct(new Product(UUID.randomUUID(),"Milk",25));
             Product AddedProduct2=productService.addProduct(new Product(UUID.randomUUID(),"Cheese",45));
             List<Product>products=new ArrayList<Product>();
             products.add(AddedProduct1);
             products.add(AddedProduct2);
             Order newOrder=new Order(newUser.getId(),200,products);
             Order newOrder2=new Order(newUser.getId(),100,products);
             orderService.addOrder(newOrder);
             orderService.addOrder(newOrder2);
             orderService.deleteOrderById(newOrder.getId());
             assertTrue(orderService.getOrders().size()==1);
             assertTrue(orderService.getOrders().get(0).getId().equals(newOrder2.getId()));
         }
      // ---------------3.GetOrderByID
     @Test
     void getOrderByCorrectID(){
         User newUser=userService.addUser(new User("Youssef",new ArrayList<Order>()));
         Product AddedProduct1=productService.addProduct(new Product(UUID.randomUUID(),"Milk",25));
         Product AddedProduct2=productService.addProduct(new Product(UUID.randomUUID(),"Cheese",45));
         List<Product>products=new ArrayList<Product>();
         products.add(AddedProduct1);
         products.add(AddedProduct2);
         Order newOrder=new Order(newUser.getId(),200,products);
         Order newOrder2=new Order(newUser.getId(),100,products);
         orderService.addOrder(newOrder);
         orderService.addOrder(newOrder2);
         assertEquals(orderService.getOrderById(newOrder.getId()).getTotalPrice(),newOrder.getTotalPrice());
         assertEquals(orderService.getOrderById(newOrder.getId()).getUserId(),newOrder.getUserId());
     }

     @Test
     void getOrderByWrongID(){
         User newUser=userService.addUser(new User("Youssef",new ArrayList<Order>()));
         Product AddedProduct1=productService.addProduct(new Product(UUID.randomUUID(),"Milk",25));
         Product AddedProduct2=productService.addProduct(new Product(UUID.randomUUID(),"Cheese",45));
         List<Product>products=new ArrayList<Product>();
         products.add(AddedProduct1);
         products.add(AddedProduct2);
         Order newOrder=new Order(newUser.getId(),200,products);
         Order newOrder2=new Order(newUser.getId(),100,products);
         orderService.addOrder(newOrder);
         orderService.addOrder(newOrder2);
         assertThrows(Exception.class,()-> orderService.getOrderById(UUID.randomUUID()));
     }

     @Test
     void getOrderByNullID(){
         User newUser=userService.addUser(new User("Youssef",new ArrayList<Order>()));
         Product AddedProduct1=productService.addProduct(new Product(UUID.randomUUID(),"Milk",25));
         Product AddedProduct2=productService.addProduct(new Product(UUID.randomUUID(),"Cheese",45));
         List<Product>products=new ArrayList<Product>();
         products.add(AddedProduct1);
         products.add(AddedProduct2);
         Order newOrder=new Order(newUser.getId(),200,products);
         Order newOrder2=new Order(newUser.getId(),100,products);
         orderService.addOrder(newOrder);
         orderService.addOrder(newOrder2);
         assertThrows(Exception.class,()-> orderService.getOrderById(null));
     }
      // ---------------3.DeleteOrderByID

     @Test
     void DeleteOrderByID(){
         User newUser=userService.addUser(new User("Youssef",new ArrayList<Order>()));
         Product AddedProduct1=productService.addProduct(new Product(UUID.randomUUID(),"Milk",25));
         Product AddedProduct2=productService.addProduct(new Product(UUID.randomUUID(),"Cheese",45));
         List<Product>products=new ArrayList<Product>();
         products.add(AddedProduct1);
         products.add(AddedProduct2);
         Order newOrder=new Order(newUser.getId(),200,products);
         Order newOrder2=new Order(newUser.getId(),100,products);
         orderService.addOrder(newOrder);
         orderService.addOrder(newOrder2);
         orderService.deleteOrderById(newOrder.getId());
         orderService.deleteOrderById(newOrder2.getId());
         assertEquals(orderService.getOrders().size(),0);
     }
     @Test
     void DeleteOrderByNullID(){
         assertThrows(Exception.class,()->orderService.deleteOrderById(null));
     }

     @Test
     void DeleteOrderByWrongID(){
         User newUser=userService.addUser(new User("Youssef",new ArrayList<Order>()));
         Product AddedProduct1=productService.addProduct(new Product(UUID.randomUUID(),"Milk",25));
         Product AddedProduct2=productService.addProduct(new Product(UUID.randomUUID(),"Cheese",45));
         List<Product>products=new ArrayList<Product>();
         products.add(AddedProduct1);
         products.add(AddedProduct2);
         Order newOrder=new Order(newUser.getId(),200,products);
         Order newOrder2=new Order(newUser.getId(),100,products);
         orderService.addOrder(newOrder);
         orderService.addOrder(newOrder2);
         assertThrows(Exception.class,()->orderService.deleteOrderById(UUID.randomUUID()));
     }

 }

