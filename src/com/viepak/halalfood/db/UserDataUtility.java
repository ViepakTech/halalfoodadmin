package com.viepak.halalfood.db;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import com.viepak.halalfood.shared.User;


public class UserDataUtility {
	public static List<User> GetAllUsers(){
		EntityManager em = EMF.get().createEntityManager();
		try{
			Query q = em.createQuery("select u from User u");
			List<User> users = q.getResultList();
			List<User> usersList = new ArrayList<User>();
			
			for(int i = 0 ; i < users.size(); i ++){
				usersList.add(users.get(i));
			}
			return usersList;
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
			return null;
		}
		finally{
			em.close();
		}
	}
	
	public static boolean CreateUser(User user){
		EntityManager em = EMF.get().createEntityManager();
		try{
			em.persist(user);
			return true;
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			return false;
		}finally{
			em.close();
		}
	}
	
	public static boolean UpdateUser(User user){
		EntityManager em = EMF.get().createEntityManager();
		try{
			em.persist(user);
			return true;
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			return false;
		}finally{
			em.close();
		}
	}
	
	public static boolean DeleteUser(long id){
		EntityManager em = EMF.get().createEntityManager();
		try{
			User user = em.find(User.class, id);
			em.remove(user);
			return true;
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			return false;
		}finally{
			em.close();
		}
	}
	
	
}
