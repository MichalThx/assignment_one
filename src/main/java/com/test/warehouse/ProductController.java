//package com.test.warehouse;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.test.warehouse.dto.old.Available;
//import com.test.warehouse.dto.old.Product;
//import com.test.warehouse.dto.old.ProductWrapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//@RestController
//@RequestMapping("/products")
//public class ProductController {
//
//	@Autowired
//	private ProductRepositoryOld productRepositoryOld;
//	@Autowired
//	private InventoryRepository inventoryRepository;
//
//
//	@GetMapping("/list")
//	public List getAllProducts() throws IOException {
//		// Get that
//		List<Product> products = productRepositoryOld.findAll();
//		HashMap<Integer, Integer> inventoryMap = new HashMap<>();
//		inventoryRepository.findAll().forEach(a -> {
//			inventoryMap.put(a.getArt_id(), a.getStock());
//		});
//
//		List<Available> available = new ArrayList<>();
//		ObjectMapper mapper = new ObjectMapper();
//
////		for(Product e : products){
////			Article[] q = mapper.readValue(e.getContainArticles(), Article[].class);
////			int max = q.length > 0 ?  Integer.MAX_VALUE : 0;
////			for(Article a : q){
////				int artId = Integer.parseInt(a.getArtId());
////				if(inventoryMap.containsKey(artId)){
////					int t = inventoryMap.get(artId);
////					max = Math.min(max, t / Integer.parseInt(a.getAmountOf()));
////				}else{
////					max = 0;
////				}
////			}
////			available.add(new Available(e.getName(), max));
////		}
//		return available;
//	}
//
//	@PostMapping("/upload")
//	public ProductWrapper updateProducts(@RequestPart("data") String data) throws JsonProcessingException {
//		ObjectMapper mapper = new ObjectMapper();
//		ProductWrapper productsWrapped = null;
//		try{
//			productsWrapped = mapper.readValue(data, ProductWrapper.class);
//			List<Product> updatedProducts = new ArrayList<>();
//			//					Product w = new Product(a.getName(),
//			//							mapper.writeValueAsString(a.getContainArticles()));
//			//					System.out.println(w.getName());
//			//					System.out.println(w.getContainArticles());
//			updatedProducts.addAll(productsWrapped.getProducts());
//			System.out.println(updatedProducts.size());
////			for(ProductEntity e : updatedProducts){
////				System.out.println(e);
////			}
//			productRepositoryOld.saveAllAndFlush(updatedProducts);
////			productRepository.saveAndFlush(new ProductEntity("UUUU", null));
//
//		}catch(Exception ignored){
//			System.out.println(ignored);
//		}
//
//		return productsWrapped;
//
//	}
//
//
//	@GetMapping("/list1")
//	public List getAllParoducts(){
//		List<Product[]> w = new ArrayList<>();
//		ObjectMapper mapper = new ObjectMapper();
//
////		productRepository.findAll().forEach(a ->{
////			try {
////				Product q = mapper.readValue( a, Product.class);
////				w.add(q);
////			} catch (Exception e) {
////				throw new RuntimeException(e);
////			}
////
////		});
//
//
//		return w;
//	}
////	@GetMapping("/search")
////	public ProductEntity getEntity(@RequestParam(name = "name") String name){
////		return productRepository.findById(name).get();
////	}
//
//
//}
