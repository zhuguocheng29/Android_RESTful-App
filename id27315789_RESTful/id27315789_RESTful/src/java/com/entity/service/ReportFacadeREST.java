/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entity.service;

import com.entity.Calculation;
import com.entity.Date_Food;
import com.entity.Report;
import com.entity.User;
import com.entity.Diet;
import com.entity.Quantity;
import com.entity.Records;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
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
@Path("com.entity.report")
public class ReportFacadeREST extends AbstractFacade<Report> {

    @PersistenceContext(unitName = "id27315789PU")
    private EntityManager em;

    public ReportFacadeREST() {
        super(Report.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Report entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Report entity) {
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
    public Report find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Report> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Report> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @GET
    @Path("Report.findByRid/{rid}")
    @Produces({"application/json"})
    public List<Report> findByRid(@PathParam("rid") Integer rid) {
        Query query = em.createNamedQuery("Report.findByRid");
        query.setParameter("rid", rid);
        return query.getResultList();
    }

    @GET
    @Path("Report.findByUid/{uid}")
    @Produces({"application/json"})
    public List<Report> findByUid(@PathParam("uid") Integer uid) {
        Query query = em.createQuery("select r from Report r where r.uid.uid= :uid ");
        query.setParameter("uid", uid);
        return query.getResultList();
    }

    @GET
    @Path("Report.findByTotalSteps/{totalSteps}")
    @Produces({"application/json"})
    public List<Report> findByTotalSteps(@PathParam("totalSteps") Integer totalSteps) {
        Query query = em.createNamedQuery("Report.findByTotalSteps");
        query.setParameter("totalSteps", totalSteps);
        return query.getResultList();
    }

    @GET
    @Path("Report.findByStepsCalorie/{stepsCalorie}")
    @Produces({"application/json"})
    public List<Report> findByStepsCalorie(@PathParam("stepsCalorie") float stepsCalorie) {
        Query query = em.createNamedQuery("Report.findByStepsCalorie");
        query.setParameter("stepsCalorie", stepsCalorie);
        return query.getResultList();
    }

    @GET
    @Path("Report.findByTotalCalorieConsumed/{totalCalorieConsumed}")
    @Produces({"application/json"})
    public List<Report> findByTotalCalorieConsumed(@PathParam("totalCalorieConsumed") float totalCalorieConsumed) {
        Query query = em.createNamedQuery("Report.findByTotalCalorieConsumed");
        query.setParameter("totalCalorieConsumed", totalCalorieConsumed);
        return query.getResultList();
    }

    @GET
    @Path("Report.findByRestCalorie/{restCalorie}")
    @Produces({"application/json"})
    public List<Report> findByRestCalorie(@PathParam("restCalorie") float restCalorie) {
        Query query = em.createNamedQuery("Report.findByRestCalorie");
        query.setParameter("restCalorie", restCalorie);
        return query.getResultList();
    }

    @GET
    @Path("Report.findByTotalFoodCalorie/{totalFoodCalorie}")
    @Produces({"application/json"})
    public List<Report> findByTotalFoodCalorie(@PathParam("totalFoodCalorie") float totalFoodCalorie) {
        Query query = em.createNamedQuery("Report.findByTotalFoodCalorie");
        query.setParameter("totalFoodCalorie", totalFoodCalorie);
        return query.getResultList();
    }

    @GET
    @Path("Report.findByReportCalorieGoal/{calorieGoal}")
    @Produces({"application/json"})
    public List<Report> findByReportCalorieGoal(@PathParam("calorieGoal") int calorieGoal) {
        Query query = em.createNamedQuery("Report.findByReportCalorieGoal");
        query.setParameter("calorieGoal", calorieGoal);
        return query.getResultList();
    }

    @GET
    @Path("Report.findByRemaining/{remaining}")
    @Produces({"application/json"})
    public List<Report> findByRemaining(@PathParam("remaining") float remaining) {
        Query query = em.createNamedQuery("Report.findByRemaining");
        query.setParameter("remaining", remaining);
        return query.getResultList();
    }

    @GET
    @Path("Report.findByReportDate/{reportDate}")
    @Produces({"application/json"})
    public List<Report> findByReportDate(@PathParam("reportDate") String reportDate) throws ParseException {
        Query query = em.createNamedQuery("Report.findByReportDate");
        if (reportDate.matches("\\d{4}-(0\\d{1}|1[0-2])-(0\\d{1}|[12]\\d{1}|3[01])")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(reportDate);
            query.setParameter("reportDate", date);
            return query.getResultList();
        } else {
            return null;
        }
    }

    //By uname and ReportDate find if repeat
    @GET
    @Path("Report.findByRepeat/{username}/{reportDate}")
    @Produces({"application/json"})
    public String findByRepeat(@PathParam("username") String username, @PathParam("reportDate") String ReportDate) throws ParseException {
        Query report = em.createQuery("select r from Report r where r.uid.name =:username and r.reportDate = :reportDate");
        report.setParameter("username", username);
        if (ReportDate.matches("\\d{4}-(0\\d{1}|1[0-2])-(0\\d{1}|[12]\\d{1}|3[01])")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(ReportDate);
            report.setParameter("reportDate", date);
            List<Report> list = report.getResultList();
            if (list.size() != 0) {
                return "1";//represent have record
            } else {
                return "0";
            }
        } else {
            return null;
        }
    }

    //By uname and ReportDate find if repeat
    @GET
    @Path("Report.findByUsernameAReportDate/{username}/{reportDate}")
    @Produces({"application/json"})
    public List<Report> findByUsernameAReportDate(@PathParam("username") String username, @PathParam("reportDate") String ReportDate) throws ParseException {
        if (ReportDate.matches("\\d{4}-(0\\d{1}|1[0-2])-(0\\d{1}|[12]\\d{1}|3[01])")) {
            Query report = em.createQuery("select r from Report r where r.uid.name =:username and r.reportDate = :reportDate");
            report.setParameter("username", username);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(ReportDate);
            report.setParameter("reportDate", date);
            return report.getResultList();
        } else {
            return null;
        }
    }

    @GET
    @Path("Report.findByTotalStepsByusername/{username}/{reportDate}")
    @Produces({"application/json"})
    public String findByTotalStepsByusername(@PathParam("username") String username, @PathParam("reportDate") String reportDate) throws ParseException {
        Query query = em.createQuery("select r from Report r where r.uid.name = :username and r.reportDate = :reportDate");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(reportDate);
        query.setParameter("username", username);
        query.setParameter("reportDate", date);
        List<Report> list = query.getResultList();
        if (reportDate.matches("\\d{4}-(0\\d{1}|1[0-2])-(0\\d{1}|[12]\\d{1}|3[01])")) {
            if (list.size() == 0) {
                return "1";
            } else {
                System.out.println("22222222222" + list.get(0).getTotalSteps() + "");
                return list.get(0).getTotalSteps() + "";
            }

        } else {
            return null;
        }
    }

    //By calorieSetGoal and reportDate find record in report
//    @GET
//    @Path("Report.findByGoalDate/{calorieSetGoal}/{reportDate}")
//    @Produces({"application/json"})
//    public List<Report> findByGoalDate(@PathParam("calorieSetGoal") Float calorieSetGoal, @PathParam("reportDate") String ReportDate) throws ParseException {
//        Query report = em.createQuery("select r from Report r where r.calorieSetGoal = :calorieSetGoal and r.reportDate = :reportDate");
//        report.setParameter("calorieSetGoal", calorieSetGoal);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = sdf.parse(ReportDate);
//        report.setParameter("reportDate", date);
//        return report.getResultList();
//    }
    //By uid from  table User calculate BMR
    @GET
    @Path("Report.CalBMR/{username}")
    @Produces({"application/json"})
    public String CalBMR(@PathParam("username") String username) {
        Query query = em.createQuery("select u.weight, u.height, u.age,u.gender from User u where u.name = :username");
        query.setParameter("username", username);
        List list = query.getResultList();
        Calculation cal = new Calculation();
        User user = new User();
        //By connecting with calculation,java calculating BMR
        if (!list.isEmpty()) {
            Iterator iterator = list.iterator();
            Object[] obj = (Object[]) iterator.next();
            float weight = Float.parseFloat(obj[0].toString());
            user.setWeight(weight);

            float height = Float.parseFloat(obj[1].toString());
            user.setHeight(height);

            int age = Integer.parseInt(obj[2].toString());
            user.setAge(age);

            String gender = obj[3].toString();
            user.setGender(gender);

            return cal.calBMR(user) + "";

        } else {
            return null;
        }
    }

    //calculate rest calorie
    @GET
    @Path("Report.CalRestCalorie/{username}")
    @Produces({"application/json"})
    public String CalRestCalorie(@PathParam("username") String username) {
        Query query = em.createQuery("select u.weight,u.height,u.age,u.gender,u.levelOfActivity from  User u where u.name = :username");
        query.setParameter("username", username);
        List list = query.getResultList();
        Calculation cal = new Calculation();
        User user = new User();
        //By connecting with calculation,java calculating BMR
        if (!list.isEmpty()) {
            Iterator iterator = list.iterator();
            Object[] obj = (Object[]) iterator.next();

            float weight = Float.parseFloat(obj[0].toString());
            user.setWeight(weight);

            float height = Float.parseFloat(obj[1].toString());
            user.setHeight(height);

            int age = Integer.parseInt(obj[2].toString());
            user.setAge(age);

            String gender = obj[3].toString();
            user.setGender(gender);

            int level = Integer.parseInt(obj[4].toString());
            user.setLevelOfActivity(level);

            return cal.calRest(user) + "";

        } else {
            return null;
        }

    }

    //calculate calories for user's walking
    @GET
    @Path("Report.CalStepsCalorie/{username}/{steps}")
    @Produces({"application/json"})
    public String CalStepsCalorie(@PathParam("username") String username, @PathParam("steps") Integer steps) {

        Query query = em.createQuery("select u from User u where u.name = :username");
        query.setParameter("username", username);
        List<User> list = query.getResultList();
        if (!list.isEmpty()) {
            float weight = list.get(0).getWeight();
            int step = list.get(0).getStepPerMile();
            User user = new User();
            user.setWeight(weight);
            user.setStepPerMile(step);
            //transmit user's parameter to call Class Calculation's method calSteps to get step's calorie
            Calculation cal = new Calculation();
            float ratio = cal.calSteps(user);
            float stepsCal = ratio * steps;

            return stepsCal + "";
        } else {
            return null;
        }

    }

    //calculate calories for all the food items consumed by a user 
    @GET
    @Path("Report.CalFoodCalorie/{username}/{date}")
    @Produces({"application/json"})
    public String CalFoodCalorie(@PathParam("username") String username, @PathParam("date") String date) throws ParseException {
        if (date.matches("\\d{4}-(0\\d{1}|1[0-2])-(0\\d{1}|[12]\\d{1}|3[01])")) {
            Query query = em.createQuery("select d.qid from Diet d where d.uid.name = '" + username + "' and d.dayTime LIKE" + " '" + date + "%'", Diet.class);
            List<Quantity> list = query.getResultList();
            float total = 0f;
            System.out.println(list.toString());
            //get every quantity that user eats food
            for (int i = 0; i < list.size(); i++) {
                //  System.out.println("444 " + list.get(i));
//                int qid = list.get(i).getQid();
//                Query query1 = em.createQuery("select q from quantity q where q =:"+qid,Quantity.class);
//                query1.getSingleResult();
//                System.out.println("444 " + list.get(i).getQid());
//                System.out.println("333 " + list.get(i).getFid());

                float calorie = list.get(i).getFid().getCalorie();
                System.out.println("111   " + calorie);
                float quantity = list.get(i).getQuantity();
                System.out.println("222   " + quantity);
                total += calorie * quantity;
            }
            return total + "";
        } else {
            return null;
        }

    }

    //plus step calorie and rest calorie to calulate burned calorie
    @GET
    @Path("Report.CalburnedCalorie/{username}/{steps}")
    @Produces({"application/json"})
    public String CalburnedCalorie(@PathParam("username") String username, @PathParam("steps") Integer steps) {
        if (CalStepsCalorie(username, steps) == null || CalRestCalorie(username) == null) {
            return null;
        } else {
            float stepsCalo = Float.parseFloat(CalStepsCalorie(username, steps));
            float restCalo = Float.parseFloat(CalRestCalorie(username));
            float burned = stepsCalo + restCalo;
            return burned + "";
        }

    }

    //plus step calorie,rest calorie and food calorie to calulate total calorie
    @GET
    @Path("Report.findByAllCalorie/{username}/{date}/{steps}")
    @Produces({"application/json"})
    public List<Calculation> findByAllCalorie(@PathParam("username") String username, @PathParam("date") String date, @PathParam("steps") Integer steps) throws ParseException {
        if (CalStepsCalorie(username, steps) == null || CalRestCalorie(username) == null || CalFoodCalorie(username, date) == null) {
            return null;
        } else {
            //Call CalFoodCalorie CalStepsCalorie and CalRestCalorie that I write above
            float foodCalo = Float.parseFloat(CalFoodCalorie(username, date));
            float stepsCalo = Float.parseFloat(CalStepsCalorie(username, steps));
            float restCalo = Float.parseFloat(CalRestCalorie(username));
            float burn = stepsCalo + restCalo;
            //Call Class Calculation's setBurned and setFood to get step's calorie
            Calculation cal = new Calculation();
            cal.setBurned(burn);
            cal.setFood(foodCalo);
            //store data into list,then I can return 2 data
            List<Calculation> list = new ArrayList<Calculation>();
            list.add(cal);
            return list;
        }

    }

    //find from startDate to endDate, a user consume and burn how much calorie
    @GET
    @Path("Report.findRangeCalorie/{username}/{startDate}/{endDate}")
    @Produces({"application/json"})
    public List<Calculation> findRangeCalorie(@PathParam("username") String username, @PathParam("startDate") String startDate, @PathParam("endDate") String endDate) {
        if (startDate.matches("\\d{4}-(0\\d{1}|1[0-2])-(0\\d{1}|[12]\\d{1}|3[01])") && endDate.matches("\\d{4}-(0\\d{1}|1[0-2])-(0\\d{1}|[12]\\d{1}|3[01])")) {
            Query query = em.createQuery("select r from Report r where r.uid.name = '" + username + "' and r.reportDate between '" + startDate + "' and '" + endDate + "' order by r.reportDate", Report.class);
            float foodCalo = 0f;
            float burned = 0f;
            List<Report> list = query.getResultList();
            //get all foodcalorie and consumed calorie
            for (int i = 0; i < list.size(); i++) {
                float foodCalo_ = list.get(i).getTotalFoodCalorie();
                float burned_ = list.get(i).getTotalCalorieConsumed();
                foodCalo += foodCalo_;
                burned += burned_;

            }
            //store them and return 2 data
            System.out.println("111" + burned);
            System.out.println("222" + foodCalo);
            Calculation cal = new Calculation();
            cal.setBurned(burned);
            cal.setFood(foodCalo);
            List<Calculation> list2 = new ArrayList<Calculation>();
            list2.add(cal);

            return list2;
        } else {
            return null;
        }

    }

    //find from startDate to endDate, a user consume and burn how much calorie
    @GET
    @Path("Report.findRangeRecords/{username}/{startDate}/{endDate}")
    @Produces({"application/json"})
    public List<Records> findRangeRecords(@PathParam("username") String username, @PathParam("startDate") String startDate, @PathParam("endDate") String endDate) {
        if (startDate.matches("\\d{4}-(0\\d{1}|1[0-2])-(0\\d{1}|[12]\\d{1}|3[01])") && endDate.matches("\\d{4}-(0\\d{1}|1[0-2])-(0\\d{1}|[12]\\d{1}|3[01])")) {
            Query query = em.createQuery("select r from Report r where r.uid.name = '" + username + "' and r.reportDate between '" + startDate + "' and '" + endDate + "' order by r.reportDate", Report.class);
            List<Report> list = query.getResultList();
            List<Records> arrayList = new ArrayList<Records>();
            if (list.size() != 0) {
                for (int i = 0; i < list.size(); i++) {
                    Records records = new Records();
                    float foodCalorie = list.get(i).getTotalFoodCalorie();
                    float burned = list.get(i).getTotalCalorieConsumed();
                    float goal = (float) list.get(i).getCalorieGoal();
                    float remaining = Math.abs(list.get(i).getRemaining());
                    
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String date = sdf.format(list.get(i).getReportDate());
                    
                    records.setFoodCalorie(foodCalorie);
                    records.setBurned(burned);
                    records.setGoal(goal);
                    records.setRemaining(remaining);
                    records.setDate(date);
                    arrayList.add(records);
                }
                return arrayList;
            }
            else
            {
                return null;
            }

        } else {
            return null;
        }

    }

    //By calculating user's calorie set goal to know remaining calorie that user left
    @GET
    @Path("Report.CalRemaining/{username}/{date}/{steps}")
    @Produces({"application/json"})
    public String CalRemaining(@PathParam("username") String username, @PathParam("date") String date, @PathParam("steps") Integer steps) throws ParseException {
        Query query = em.createQuery("select u.calorieSetGoal from User u where u.name = :username");
        query.setParameter("username", username);
        //get the amount of user's set goal
        List list = query.getResultList();
        Object objcal = list.get(0);
        int CalGoal = Integer.parseInt(objcal.toString());
        System.out.println(CalGoal);

        if (findByAllCalorie(username, date, steps) == null) {
            return null;
        } else {
            List<Calculation> list2 = findByAllCalorie(username, date, steps);
            float food = list2.get(0).getFood();
            float burned = list2.get(0).getBurned();
            //     float remaining = burned - food;
            float remaining = (burned - food) - CalGoal;
            return remaining + "";
        }

    }

    //By calculating user's calorie set goal to know remaining calorie that user left
    @GET
    @Path("Report.CalRemaining_Report/{username}/{reportDate}/{steps}")
    @Produces({"application/json"})
    public String CalRemaining_Report(@PathParam("username") String username, @PathParam("reportDate") String reportDate, @PathParam("steps") Integer steps) throws ParseException {
        if (username != null && reportDate.matches("\\d{4}-(0\\d{1}|1[0-2])-(0\\d{1}|[12]\\d{1}|3[01])")) {
            Query query = em.createQuery("select r.calorieGoal from Report r where r.uid.name = :username and r.reportDate =:date");
            query.setParameter("username", username);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(reportDate);
            query.setParameter("date", date);
            //get the amount of user's set goal
            List list = query.getResultList();
            Object objcal = list.get(0);
            int CalGoal = Integer.parseInt(objcal.toString());
            System.out.println(CalGoal);

            if (findByAllCalorie(username, reportDate, steps) == null) {
                return null;
            } else {
                List<Calculation> list2 = findByAllCalorie(username, reportDate, steps);
                float food = list2.get(0).getFood();
                float burned = list2.get(0).getBurned();
                //     float remaining = burned - food;
                float remaining = (burned - food) - CalGoal;
                return remaining + "";
            }
        } else {
            return null;
        }
    }

    //aim calorie
    @GET
    @Path("Report.UpdateCalorie/{username}/{calorie}/{reportDate}")
    @Produces({"application/json"})
    public List<Report> UpdateCalorie(@PathParam("username") String username, @PathParam("calorie") Integer calorie, @PathParam("reportDate") String reportDate) throws ParseException {
//        if(username != null && reportDate.matches("\\d{4}-(0\\d{1}|1[0-2])-(0\\d{1}|[12]\\d{1}|3[01])"))
//        {
//          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//          Date date = sdf.parse(reportDate);
//          Query query1 = em.createQuery("select r from Report r where r.uid.name = :username and r.reportDate = :date");
//          query1.setParameter("username", username);
//          query1.setParameter("date", date);
//          List<Report> list = query1.getResultList();
        if (username != null && reportDate.matches("\\d{4}-(0\\d{1}|1[0-2])-(0\\d{1}|[12]\\d{1}|3[01])")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(reportDate);
            Query query = em.createQuery("update Report r set r.calorieGoal = :calorie where r.uid.name = :username and r.reportDate = :date");
            query.setParameter("username", username);
            query.setParameter("calorie", calorie);
            query.setParameter("date", date);
            query.executeUpdate();
            //System.out.println("111111");
            Query query2 = em.createQuery("select r from Report r where r.uid.name = :username and r.reportDate = :date");
            query2.setParameter("username", username);
            query2.setParameter("date", date);
            //System.out.println("22222");
            // query1.setParameter("calorie", calorie);
            return query2.getResultList();
        } else {
            return null;
        }

//        }
//        else
//        {
//            return null;
//        }
    }

    //update food calorie
    @GET
    @Path("Report.UpdateFoodCalorie/{username}/{reportDate}")
    @Produces({"application/json"})
    public String UpdateFoodCalorie(@PathParam("username") String username, @PathParam("reportDate") String reportDate) throws ParseException {
        if (username != null && reportDate.matches("\\d{4}-(0\\d{1}|1[0-2])-(0\\d{1}|[12]\\d{1}|3[01])")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(reportDate);
            Query query1 = em.createQuery("select r from Report r where r.uid.name = :username and r.reportDate = :date");
            query1.setParameter("username", username);
            query1.setParameter("date", date);
            List<Report> list = query1.getResultList();
            if (list.size() == 1) {
                String updatedfood = CalFoodCalorie(username, reportDate);
                System.out.println("1111" + updatedfood);
                float foodCalorie = Float.parseFloat(updatedfood);
                Query query = em.createQuery("update Report r set r.totalFoodCalorie = :foodcalorie where r.uid.name = :username and r.reportDate = :date");
                query.setParameter("username", username);
                query.setParameter("foodcalorie", foodCalorie);

                query.setParameter("date", date);
                query.executeUpdate();
                //have value
                return "1";
            } else {
                return "2";
            }

        } else {
            return "0";
        }

    }

    @GET
    @Path("Report.UpdateStepsAStepsCalorie/{username}/{totalSteps}/{reportDate}")
    @Produces({"application/json"})
    public String UpdateStepsAStepsCalorie(@PathParam("username") String username, @PathParam("totalSteps") int totalSteps, @PathParam("reportDate") String reportDate) throws ParseException {
        int allSteps = 0;
        if (reportDate.matches("\\d{4}-(0\\d{1}|1[0-2])-(0\\d{1}|[12]\\d{1}|3[01])")) {
            Query query = em.createQuery("update Report r set r.totalSteps = :totalSteps where r.uid.name = :username and r.reportDate = :date");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(reportDate);
            allSteps += totalSteps;
            query.setParameter("username", username);
            query.setParameter("date", date);
            query.setParameter("totalSteps", allSteps);
            query.executeUpdate();

            float stepsCalorie = Float.parseFloat(CalStepsCalorie(username, allSteps));
            Query query2 = em.createQuery("update Report r set r.stepsCalorie = :stepsCalorie where r.uid.name = :username and r.reportDate = :date");
            query2.setParameter("username", username);
            query2.setParameter("date", date);
            query2.setParameter("stepsCalorie", stepsCalorie);
            query2.executeUpdate();

            Query query1 = em.createQuery("select r from Report r where r.uid.name = :username and r.reportDate = :date");
            query1.setParameter("username", username);
            query1.setParameter("date", date);
            List<Report> list = query1.getResultList();
            if (list.size() == 1) {
                //have updated value
                return "1";
            } else {
                return "2";
            }

//            Query query1 = em.createQuery("select r from Report r where r.uid.name = :username and r.reportDate = :date");
//            query1.setParameter("username", username);
//            query1.setParameter("date", date);
//            List<Report> list = query1.getResultList();
//            if(list.size()== 0)
//            {
//                return "2";
//            }
//            else
//            {
//                return "1";
//            }
        } else {
            return "0";
        }
    }

    @GET
    @Path("Report.UpdateRestCalorie/{username}/{reportDate}")
    @Produces({"application/json"})
    public String UpdateRestCalorie(@PathParam("username") String username, @PathParam("reportDate") String reportDate) throws ParseException {
        if (username != null && reportDate.matches("\\d{4}-(0\\d{1}|1[0-2])-(0\\d{1}|[12]\\d{1}|3[01])")) {
            String restCalorie = CalRestCalorie(username);
            float restCalorie_final = Float.parseFloat(restCalorie);
            Query query = em.createQuery("update Report r set r.restCalorie = :restCalorie where r.uid.name = :username and r.reportDate = :date");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(reportDate);
            query.setParameter("username", username);
            query.setParameter("date", date);
            query.setParameter("restCalorie", restCalorie_final);
            query.executeUpdate();
            return "1";
        } else {
            return "0";
        }
    }

    @GET
    @Path("Report.UpdateburnedCalorie/{username}/{steps}/{reportDate}")
    @Produces({"application/json"})
    public String UpdateburnedCalorie(@PathParam("username") String username, @PathParam("steps") Integer steps, @PathParam("reportDate") String reportDate) throws ParseException {
        if (username != null && reportDate.matches("\\d{4}-(0\\d{1}|1[0-2])-(0\\d{1}|[12]\\d{1}|3[01])")) {
            String totalCalorieConsumed = CalburnedCalorie(username, steps);
            float burnedCalorieConsumed = Float.parseFloat(totalCalorieConsumed);
            Query query = em.createQuery("update Report r set r.totalCalorieConsumed = :totalCalorieConsumed where r.uid.name = :username and r.reportDate = :date");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(reportDate);
            query.setParameter("username", username);
            query.setParameter("date", date);
            query.setParameter("totalCalorieConsumed", burnedCalorieConsumed);
            query.executeUpdate();
            return "1";
        } else {
            return "0";
        }
    }

    @GET
    @Path("Report.UpdateRemaining/{username}/{reportDate}/{steps}")
    @Produces({"application/json"})
    public String UpdateRemaining(@PathParam("username") String username, @PathParam("reportDate") String reportDate, @PathParam("steps") Integer steps) throws ParseException {
        String remaining = CalRemaining_Report(username, reportDate, steps);
        float remaining_report = Float.parseFloat(remaining);
        if (username != null && reportDate.matches("\\d{4}-(0\\d{1}|1[0-2])-(0\\d{1}|[12]\\d{1}|3[01])")) {
            Query query = em.createQuery("update Report r set r.remaining = :remaining where r.uid.name = :username and r.reportDate = :date");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(reportDate);
            query.setParameter("username", username);
            query.setParameter("date", date);
            query.setParameter("remaining", remaining_report);
            query.executeUpdate();
            return "1";
        } else {
            return "0";
        }
    }

    //calculate calories for all the food items consumed by a user 
    @GET
    @Path("Report.CalFoodCaloriePeriod/{username}/{date}")
    @Produces({"application/json"})
    public List<Date_Food> CalFoodCaloriePeriod(@PathParam("username") String username, @PathParam("date") String date) throws ParseException {
        if (date.matches("\\d{4}-(0\\d{1}|1[0-2])-(0\\d{1}|[12]\\d{1}|3[01])")) {
            List<Date_Food> list_food = new ArrayList<Date_Food>();
            Query query = em.createQuery("select d.qid from Diet d where d.uid.name = '" + username + "' and d.dayTime LIKE" + " '" + date + "%'", Diet.class);
            if (query.getResultList() != null) {
                for (int i = 0; i < 24; i++) {

                    if (i < 10) {
                        float total = 0f;
                        System.out.println("3333");
                        Query query1 = em.createQuery("select d.qid from Diet d where d.uid.name = '" + username + "' and d.dayTime LIKE" + " '" + date + " 0" + i + "%'", Diet.class);
                        System.out.println("5555");
                        List<Quantity> list = query1.getResultList();
                        for (int j = 0; j < list.size(); j++) {
                            float calorie = list.get(j).getFid().getCalorie();
                            System.out.println("111_1   " + calorie);
                            float quantity = list.get(j).getQuantity();
                            System.out.println("222_1   " + quantity);
                            total += calorie * quantity;
                        }
                        Date_Food df = new Date_Food();
                        df.setDateTime(i);
                        df.setFood(total);
                        list_food.add(df);
                    } else {
                        float total = 0f;
                        Query query1 = em.createQuery("select d.qid from Diet d where d.uid.name = '" + username + "' and d.dayTime LIKE" + " '" + date + " " + i + "%'", Diet.class);
                        List<Quantity> list = query1.getResultList();
                        for (int j = 0; j < list.size(); j++) {
                            float calorie = list.get(j).getFid().getCalorie();
                            System.out.println("111_2   " + calorie);
                            float quantity = list.get(j).getQuantity();
                            System.out.println("222_2   " + quantity);
                            total += calorie * quantity;
                        }
                        Date_Food df = new Date_Food();
                        df.setDateTime(i);
                        df.setFood(total);
                        list_food.add(df);
                    }
                }
                //System.out.println(list_food.toString());
                return list_food;
            } else {
                return null;
            }

        } else {
            return null;
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
