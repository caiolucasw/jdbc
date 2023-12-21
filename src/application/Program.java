package application;

import java.util.Date;

import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Department department = new Department(1, "Books");
		Seller seller = new Seller(21, "Caio", "caio@gmail.com", new Date(), 4000.0, department);
		
		System.out.println(department);
		System.out.println(seller);

	}

}
