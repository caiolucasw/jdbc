package application;

import java.util.Date;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Department department = new Department(1, "Books");
		Seller seller = new Seller(21, "Caio", "caio@gmail.com", new Date(), 4000.0, department);
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		Seller sellerFound = sellerDao.findById(3);
		System.out.println(sellerFound);

	}

}
