/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entity.service;

import com.entity.Food;
import com.entity.Quantity;
import java.util.Iterator;
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
@Path("com.entity.quantity")
public class QuantityFacadeREST extends AbstractFacade<Quantity> {

    @PersistenceContext(unitName = "id27315789PU")
    private EntityManager em;

    public QuantityFacadeREST() {
        super(Quantity.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Quantity entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Quantity entity) {
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
    public Quantity find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Quantity> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Quantity> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    
    @GET
    @Path("Quantity.findByQid/{qid}")
    @Produces({"application/json"})
    public List<Quantity> findByQid(@PathParam("qid") Integer qid)//
    {
        Query query = em.createNamedQuery("Quantity.findByQid");
        query.setParameter("qid", qid);
        return query.getResultList();
    }
    
    @GET
    @Path("Quantity.findByFid/{fid}")
    @Produces({"application/json"})
    public List<Quantity> findByFid(@PathParam("fid") Integer fid)//
    {
        Query query = em.createQuery("select q from Quantity q where q.fid.fid = :fid");
        query.setParameter("fid", fid);
        return query.getResultList();
    }
    
    @GET
    @Path("Quantity.findByQuantity/{quantity}")
    @Produces({"application/json"})
    public List<Quantity> findByQuantity(@PathParam("quantity") Integer quantity)//
    {
        Query query = em.createNamedQuery("Quantity.findByQuantity");
        query.setParameter("quantity", quantity);
        return query.getResultList();
    }
    
    
    //By calorie in table food find record which is suitable for query
    @GET
    @Path("Quantity.findByCal/{calorie}")
    @Produces({"application/json"})
    public List<Quantity> findByCal(@PathParam("calorie") Integer calorie)//
    {
        Query query = em.createNamedQuery("Quantity.findByCal");
        query.setParameter("calorie",calorie);
        return query.getResultList();
    }
    
    //By the name of food in table food and quantity in table quantity, inserting a record into table quantity
    @GET
    @Path("Quantity.insertQuantity/{name}/{quantity}")
    @Produces({"application/json"})
    public String insertQuantity(@PathParam("name") String name, @PathParam("quantity") Integer quantity) {
       
        Query query = em.createQuery("select f.fid,f.name,f.calorie,f.fat,f.serving from Food f where f.name = :name");
        query.setParameter("name", name);
        List list = query.getResultList();
        Food food = new Food();
        if (list != null) {
            Iterator iterator = list.iterator();
            Object[] obj = (Object[]) iterator.next();

            int fid = Integer.parseInt(obj[0].toString());
            food.setFid(fid);
            
            String fname = obj[1].toString();
            food.setName(fname);
 
        } else {
            food = null;
        }   
        // get variable and set variable into quantity1.
        Quantity quantity1 = new Quantity();
        quantity1.setFid(food);
        quantity1.setQuantity(quantity);
        super.create(quantity1);
        
//        Query query2= em.createQuery("select q from Quantity q" );
//        return query2.getResultList();
          return "successful";
    }
    
    
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
