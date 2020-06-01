package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.model.*;

public class AccountDao {
	public static Connection con;
	private static String driver="oracle.jdbc.OracleDriver";
	private static String url="jdbc:oracle:thin:@localhost:1521:XE";
	private static PreparedStatement ps,ps1,ps2,ps3,ps4,ps5,psf,psnew;
	private static String username="system";
	private static String password="123456789";


	public static Connection getConnection()
	{
		try
		{
			Class.forName(driver);
			con=DriverManager.getConnection(url,username,password);
			System.out.println("Connection Established "+con);

		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return con;
}
	
	public int[] saveData(List<User> lst)
	{
		con=getConnection();
		User obj_user = (User)lst.get(0);
		

		int arr[]= new int[2];
		arr[0]=0;
		arr[1]=0;
		
		
		try
		{
			ps=con.prepareStatement("insert into ShoppingApplication_User values(?,?,?,?)");
			ps.setString(1,obj_user.getFullname());
			ps.setString(2,obj_user.getEmail());
			ps.setString(3,obj_user.getMobilenumber());
			ps.setString(4,obj_user.getPassword());
			arr[0]=ps.executeUpdate();
			
			
			ps=con.prepareStatement("insert into ShoppingApplication_User_Login values(?,?)");
			ps.setString(1,obj_user.getEmail());
			ps.setString(2,obj_user.getPassword());
			arr[1]=ps.executeUpdate();
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return arr;
	}
	
	public boolean validate(List<User_login>lst)
	{
		con=getConnection();
		boolean bool= false;
	User_login obj_login = (User_login)lst.get(0);
	

		
		try
		{
			ps= con.prepareStatement("select * from ShoppingApplication_User_Login where email=? and password=?");
			ps.setString(1,obj_login.getEmail());
			
			ps.setString(2,obj_login.getPassword());
			ResultSet rs = ps.executeQuery();
			System.out.println(rs);
			if(rs.next())
			{
				bool=true;
			}
			else
			{
				bool=false;
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return bool;
	}
}
