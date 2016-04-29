/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entity.service;

import com.entity.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Think
 */
@Stateless
@Path("com.entity.user")
public class UserFacadeREST extends AbstractFacade<User> {

    @PersistenceContext(unitName = "id27315789PU")
    private EntityManager em;

    public UserFacadeREST() {
        super(User.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(User entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, User entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public User find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<User> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<User> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    
    
    @GET
    @Path("User.findByUid/{uid}")
    @Produces({"application/json"})
    public List<User> findByUid(@PathParam("uid") Integer uid)
    {
        Query  query = em.createNamedQuery("User.findByUid");
        query.setParameter("uid", uid);
        return query.getResultList();
    }

    
    @GET
    @Path("User.findByName/{name}")
    @Produces({"application/json"})
    public List<User> findByName(@PathParam("name") String name)
    {
        Query  query = em.createNamedQuery("User.findByName");
        query.setParameter("name", name);
        return query.getResultList();
    }

    @GET
    @Path("User.findByPassword/{password}")
    @Produces({"application/json"})
    public List<User> findByPassword(@PathParam("password") String password)
    {
        Query  query = em.createNamedQuery("User.findByPassword");
        query.setParameter("password", password);
        return query.getResultList();
    }

    @GET
    @Path("User.findByAge/{age}")
    @Produces({"application/json"})
    public List<User> findByAge(@PathParam("age") Integer age)
    {
        Query  query = em.createNamedQuery("User.findByAge");
        query.setParameter("age", age);
        return query.getResultList();
    }

    @GET
    @Path("User.findByHeight/{height}")
    @Produces({"application/json"})
    public List<User> findByHeight(@PathParam("height") float height)
    {
        Query  query = em.createNamedQuery("User.findByHeight");
        query.setParameter("height", height);
        return query.getResultList();
    }

    @GET
    @Path("User.findByWeight/{weight}")
    @Produces({"application/json"})
    public List<User> findByWeight(@PathParam("weight") float weight)
    {
        Query  query = em.createNamedQuery("User.findByWeight");
        query.setParameter("weight", weight);
        return query.getResultList();
    }

    @GET
    @Path("User.findByGender/{gender}")
    @Produces({"application/json"})
    public List<User> findByGender(@PathParam("gender") String gender)
    {
        Query  query = em.createNamedQuery("User.findByGender");
        query.setParameter("gender", gender);
        return query.getResultList();
    }

    @GET
    @Path("User.findByLevelOfActivity/{levelOfActivity}")
    @Produces({"application/json"})
    public List<User> findByLevelOfActivity(@PathParam("levelOfActivity") Integer levelOfActivity)
    {
        Query  query = em.createNamedQuery("User.findByLevelOfActivity");
        query.setParameter("levelOfActivity", levelOfActivity);
        return query.getResultList();
    }

    @GET
    @Path("User.findByStepPerMile/{stepPerMile}")
    @Produces({"application/json"})
    public List<User> findByStepPerMile(@PathParam("stepPerMile") Integer stepPerMile)
    {
        Query  query = em.createNamedQuery("User.findByStepPerMile");
        query.setParameter("stepPerMile", stepPerMile);
        return query.getResultList();
    }

    @GET
    @Path("User.findByCalorieSetGoal/{calorieSetGoal}")
    @Produces({"application/json"})
    public List<User> findByCalorieSetGoal (@PathParam("calorieSetGoal") Integer calorieSetGoal)
    {
        Query query = em.createNamedQuery("User.findByCalorieSetGoal");
        query.setParameter("calorieSetGoal", calorieSetGoal);
        return query.getResultList();
    }
    
    //a REST method that enables querying from more than one table using a combination of attributes
    @GET
    @Path("User.findByUserAtt/{age}/{calorieSetGoal}")
    @Produces({"application/json"})
    public List<User> findByUserAtt (@PathParam("age") Integer age,@PathParam("calorieSetGoal") Integer calorieSetGoal)
    {
          Query query = em.createQuery("select u from User u where u.age = :age and u.calorieSetGoal = :calorieSetGoal");
          query.setParameter("age", age);
          query.setParameter("calorieSetGoal", calorieSetGoal);
          return query.getResultList();
    }
    
    
    @GET
    @Path("User.findByUserAPassword/{username}/{password}")
    @Produces({"application/json"})
    public String findByUserAPassword (@PathParam("username") String username,@PathParam("password") String password)
    {
        if(!username.isEmpty() && !password.isEmpty())
        {
          Query query = em.createQuery("select u from User u where u.name = :username and u.password = :password");
          query.setParameter("username", username);
          query.setParameter("password", password);
          List<User> list = query.getResultList();
          //represents password wrong
          Query query2 = em.createQuery("select u from User u where u.name = :username");
          query2.setParameter("username", username);
          List<User> list2 = query2.getResultList();
          if(list.size()!= 0)
          {
              return 1+"";
          }
          else
          {
              //2 represents no data
              String a = 2+"";
              for(int i = 0; i< list2.size();i++)
              {
                  if(!password.equals(list2.get(i).getPassword()))
                  {
                      //represents password wrong
                      a = 3+"";
                  }
              }
              
              return a;
              
          }
        }
        else
        {
            return null;
        }

    }
    
    @GET
    @Path("User.UpdateByUserACalorie/{username}/{calorie}")
    @Produces({"application/json"})
    public List<User> UpdateByUserACalorie (@PathParam("username") String username,@PathParam("calorie") Integer calorie)
    {
        if(!username.isEmpty())
        {
          Query query = em.createQuery("update User u set u.calorieSetGoal = :calorie where u.name = :username");
          query.setParameter("username", username);
          query.setParameter("calorie", calorie);
          query.executeUpdate();
          //System.out.println("111111");
          Query query1 = em.createQuery("select u from User u where u.name = :username");
          query1.setParameter("username", username);
          //System.out.println("22222");
         // query1.setParameter("calorie", calorie);
          return query1.getResultList();
     
        }
        else
        {
            return null;
        }
    }
    
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
