package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {
	
	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
		
	}

	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		Seller seller = null;
		
		try {
			st = this.conn.prepareStatement(
					" SELECT seller.*, department.Name as DepName " +
				    " FROM seller " +
					" INNER JOIN department ON seller.DepartmentId = department.Id" +
				    " WHERE seller.Id = ?"
				 );
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {				
				Department department = this.instatiateDepartment(rs);
				seller = this.instantiateSeller(rs, department);
				
			}
			
					
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
			
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
		return seller;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Seller> list = new ArrayList<Seller>();
		
		try {
			st = this.conn.prepareStatement(
					" SELECT seller.*, department.Name as DepName " +
				    " FROM seller " +
					" INNER JOIN department ON seller.DepartmentId = department.Id"		
				 );
			rs = st.executeQuery();
			Map<Integer, Department> map = new HashMap<>();
			while (rs.next()) {
				Department department = map.get(rs.getInt("DepartmentId"));
				if (department == null) {					
					department = this.instatiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), department);
				}
				list.add(this.instantiateSeller(rs, department));		
			}
			
					
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
			
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
		return list;
	}
	
	
	
	public Department instatiateDepartment(ResultSet rs) throws SQLException {
		Department department = new Department();
		department.setId(rs.getInt("DepartmentId"));
		department.setName(rs.getString("DepName"));
		
		return department;
		
	}
	
	public Seller instantiateSeller(ResultSet rs, Department department) throws SQLException {
		Seller seller = new Seller();
		seller.setId(rs.getInt("Id"));
		seller.setName(rs.getString("Name"));
		seller.setEmail(rs.getString("Email"));
		seller.setBaseSalary(rs.getDouble("BaseSalary"));
		seller.setBirthDate(rs.getDate("birthDate"));
		seller.setDepartment(department);
		
		return seller;
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Seller> list = new ArrayList<Seller>();
		
		try {
			st = this.conn.prepareStatement(
					" SELECT seller.*, department.Name as DepName " +
				    " FROM seller " +
					" INNER JOIN department ON seller.DepartmentId = department.Id" +
				    " WHERE DepartmendId = ? " + 
					" ORDER BY Name"			
				 );
			st.setInt(1, department.getId());
			rs = st.executeQuery();
			while (rs.next()) {				
				list.add(this.instantiateSeller(rs, department));		
			}
			
					
		} catch(SQLException e) {
			throw new DbException(e.getMessage());
			
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
		return list;
	}

}
