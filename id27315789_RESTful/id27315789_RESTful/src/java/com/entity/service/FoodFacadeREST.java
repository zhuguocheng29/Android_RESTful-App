/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entity.service;

import com.entity.Food;
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
@Path("com.entity.food")
public class FoodFacadeREST extends AbstractFacade<Food> {

    @PersistenceContext(unitName = "id27315789PU")
    private EntityManager em;

    public FoodFacadeREST() {
        super(Food.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Food entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Food entity) {
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
    public Food find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Food> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Food> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    
    @GET
    @Path("Food.findByFid/{fid}")
    @Produces({"application/json"})
    public List<Food> findByFid(@PathParam("fid") Integer fid)
    {
        Query  query = em.createNamedQuery("Food.findByFid");
        query.setParameter("fid", fid);
        return query.getResultList();
    }
    
    
    @GET
    @Path("Food.findByName/{name}")
    @Produces({"application/json"})
    public List<Food> findByName(@PathParam("name") String name)
    {
        Query  query = em.createNamedQuery("Food.findByName");
        query.setParameter("name", name);
        return query.getResultList();
    }
    
    @GET
    @Path("Food.findByCategory/{category}")
    @Produces({"application/json"})
    public List<Food> findByCategory(@PathParam("category") String category)
    {
        Query  query = em.createNamedQuery("Food.findByCategory");
        query.setParameter("category", category);
        return query.getResultList();
    }
    
        
    @GET
    @Path("Food.findByCalorie/{calorie}")
    @Produces({"application/json"})
    public List<Food> findByCalorie(@PathParam("calorie") Integer calorie)
    {
        Query  query = em.createNamedQuery("Food.findByCalorie");
        query.setParameter("calorie", calorie);
        return query.getResultList();
    }
    
    @GET
    @Path("Food.findByFat/{fat}")
    @Produces({"application/json"})
    public List<Food> findByFat(@PathParam("fat") Integer fat)
    {
        Query  query = em.createNamedQuery("Food.findByFat");
        query.setParameter("fat", fat);
        return query.getResultList();
    }
    
    
    @GET
    @Path("Food.findByServing/{serving}")
    @Produces({"application/json"})
    public List<Food> findByServing(@PathParam("serving") float serving)
    {
        Query  query = em.createNamedQuery("Food.findByServing");
        query.setParameter("serving", serving);
        return query.getResultList();
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
