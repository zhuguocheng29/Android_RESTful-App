/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entity.service;

import com.entity.Diet;
import com.entity.Quantity;
import com.entity.Food;
import com.entity.User;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
//import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@Path("com.entity.diet")
public class DietFacadeREST extends AbstractFacade<Diet> {

    @PersistenceContext(unitName = "id27315789PU")
    private EntityManager em;

    public DietFacadeREST() {
        super(Diet.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Diet entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Diet entity) {
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
    public Diet find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Diet> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Diet> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @GET
    @Path("Diet.findByDid/{did}")
    @Produces({"application/json"})
    public List<Diet> findByDid(@PathParam("did") Integer did) {
        Query query = em.createNamedQuery("Diet.findByDid");
        query.setParameter("did", did);
        return query.getResultList();
    }

    @GET
    @Path("Diet.findByUid/{uid}")
    @Produces({"application/json"})
    public List<Diet> findByUid(@PathParam("uid") Integer uid) {
        Query query = em.createQuery("select d from Diet d where d.uid.uid = :uid");
        query.setParameter("uid", uid);
        return query.getResultList();
    }

    @GET
    @Path("Diet.findByQid/{qid}")
    @Produces({"application/json"})
    public List<Diet> findByQid(@PathParam("qid") Integer qid) {
        Query query = em.createQuery("select d from Diet d where d.qid.qid = :qid");
        query.setParameter("qid", qid);
        return query.getResultList();
    }

    @GET
    @Path("Diet.findByDayTime/{dayTime}")
    @Produces({"application/json"})
    public List<Diet> findByDayTime(@PathParam("dayTime") String dayTime) throws ParseException {
        if (dayTime.matches("\\d{4}-(0\\d{1}|1[0-2])-(0\\d{1}|[12]\\d{1}|3[01])\\p{Blank}(0\\d{1}|1\\d{1}|2[0-3]):[0-5]\\d{1}:([0-5]\\d{1})")) {
            Query query = em.createNamedQuery("Diet.findByDayTime");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(dayTime);
            query.setParameter("dayTime", date);
            return query.getResultList();
        } else {
            return null;
        }

    }

    //insert data into table diet
    @GET
    @Path("Diet.insertDiet/{uname}/{fname}/{quantity}")
    @Produces({"application/json"})
    public List<Diet> insertDiet(@PathParam("uname") String uname, @PathParam("fname") String fname, @PathParam("quantity") Integer quantity) {
        Query query1 = em.createQuery("select u.uid from User u where u.name = :uname");
        query1.setParameter("uname", uname);
        List list = query1.getResultList();
        Object objuid = list.get(0);
        int uid = Integer.parseInt(objuid.toString());
        User user = new User();
        user.setUid(uid);

        //set uid into user to store it
//        User user = new User();
//        user.setUid(uid);

        //get qid and store it into quantity1
        Query query2 = em.createQuery("select q.qid from Quantity q where q.fid.name = :fname and q.quantity = :quantity order by q.qid desc");
        query2.setParameter("fname", fname);
        query2.setParameter("quantity", quantity);
        List list2 = query2.getResultList();
        Object objqid = list2.get(0);
        int qid = Integer.parseInt(objqid.toString());
        Quantity quantity1 = new Quantity();
        quantity1.setQid(qid);

        //get currtent time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String date1 = sdf.format(date);
        Date date2;
        try {
            date2 = sdf.parse(date1);
            Diet diet = new Diet();
            diet.setUid(user);
            diet.setQid(quantity1);
            diet.setDayTime(date2);
            //insert data into table diet
            super.create(diet);
        } catch (ParseException ex) {
            Logger.getLogger(DietFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }

        //make result output
        Query query3 = em.createQuery("select d from Diet d");
        return query3.getResultList();

    }

    //By qid and uid find records in table diet
    @GET
    @Path("Diet.findByUidQid/{uid}/{qid}")
    @Produces({"application/json"})
    public List<Diet> findByUidQid(@PathParam("uid") Integer uid, @PathParam("qid") Integer qid) {
        Query query = em.createQuery("select d from Diet d where d.uid.uid = :uid and d.qid.qid = :qid");
        query.setParameter("uid", uid);
        query.setParameter("qid", qid);
        return query.getResultList();
    }

    
    //By qid and uid find records in table diet
    @GET
    @Path("Diet.findByUnameQid/{uname}/{qid}")
    @Produces({"application/json"})
    public List<Diet> findByUnameQid(@PathParam("uname") String uname, @PathParam("qid") Integer qid) {
        Query query = em.createQuery("select d from Diet d where d.uid.name = :uname and d.qid.qid = :qid");
        query.setParameter("uname", uname);
        query.setParameter("qid", qid);
        return query.getResultList();
    }
    
    
    //calculate calories for all the food items consumed by a user 
    @GET
    @Path("Diet.CalFoodCalorie/{uid}/{date}")
    @Produces({"application/json"})
    public String CalFoodCalorie(@PathParam("uid") Integer uid, @PathParam("date") String date) throws ParseException {
        if (date.matches("\\d{4}-(0\\d{1}|1[0-2])-(0\\d{1}|[12]\\d{1}|3[01])")) {
            Query query = em.createQuery("select d.qid from Diet d where d.uid.uid = " + uid + " and d.dayTime LIKE" + " '" + date + "%'", Diet.class);
            List<Quantity> list = query.getResultList();
            float total = 0f;
            //get every quantity that user eats food
            for (int i = 0; i < list.size(); i++) {
                float calorie = list.get(i).getFid().getCalorie();
                float quantity = list.get(i).getQuantity();
                total += calorie * quantity;
            }
            return total + "";
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
