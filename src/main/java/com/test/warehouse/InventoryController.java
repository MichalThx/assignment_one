//package com.test.warehouse;
//
//import com.test.warehouse.entity.old.InventoryEntity;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@RequestMapping("/inventory")
//public class InventoryController {
//
//	@Autowired
//	private InventoryRepository inventoryRepository;
//	@GetMapping("/list")
//	public  List<InventoryEntity> getAllInventory(){
//		List<InventoryEntity> list = new ArrayList<>();
//		inventoryRepository.findAll().forEach(a -> {
//			a.setName("LOL");
//			inventoryRepository.saveAndFlush(a);
//			list.add(a);
//		});
//		return list;
//	}
//
//	@GetMapping("/allQ")
//	public  List<String> getAllInventor(){
//		List<String> list = new ArrayList<>();
//		inventoryRepository.findAll().forEach(a -> list.add(a.getName()));
//		return list;
//	}
//}
//
