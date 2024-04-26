package in.ashokit.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.ashokit.entity.Product;
import in.ashokit.repo.ProductRepository;
import java.util.*;
@Controller
public class ProductController 
{
	@Autowired
	private ProductRepository repo;//for Save the data to my controller should talk the repository
	
	@GetMapping("/delete")
	public String delete(@RequestParam("productId")Integer productId,Model model)
	{
		repo.deleteById(productId);
		//After delete the record i want to display one message
		model.addAttribute("msg","Product is deleted...");
		//i want to show the latest record
		model.addAttribute("products",repo.findAll());
		return "data";
	}
	
	@GetMapping("/products")
	public String getAllProduct(Model model)   //For retrieving the records
	{
		List<Product>list=repo.findAll();
		//This product list i want to send from controller to UI
		model.addAttribute("products",list);
		return "data"; //data is my another view name
	}
	//For save the data into the table
	@PostMapping("/product")
	public String saveProduct(@Validated @ModelAttribute("product") Product p,Model model,BindingResult result)//taking model attribute because after the form is submitted page is reloaded product object is available in model
	{
		if(result.hasErrors())
		{
			System.out.println(p); //if validations is failed it will print user enter value
			return "index";
		}
		p=repo.save(p);
		
		if(p.getProductId()!=null)
		{
			//for success message
			model.addAttribute("msg","Data is saved");
		}
		
		return "index";
	}
	@GetMapping("/")
	public String loadForm(Model model)
	{
		model.addAttribute("product",new Product());
		return "index";
	}
	
	@GetMapping("/edit")
	public String edit(@RequestParam("productId") Integer productId,Model model)
	{
		Optional<Product>findById=repo.findById(productId);
		if(findById.isPresent())
		{
			Product product=findById.get();
			model.addAttribute("product", product);
		}
		return "index";
	}

}
