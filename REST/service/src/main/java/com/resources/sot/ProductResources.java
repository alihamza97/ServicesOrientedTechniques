//package com.resources.sot;
//
//import com.resources.model.Products;
//
//import javax.ws.rs.*;
//import javax.ws.rs.core.MediaType;
//import java.util.ArrayList;
//import java.util.List;
//
//@Path("products")
//
//public class ProductResources {
//    public List<Products> studentList;
//    public ProductResources(){
//        studentList=new ArrayList<>();
//        studentList.add(new Products(1,"laptop"));
//        studentList.add(new Products(2,"mobile"));
//
//    }
//    @GET
//    @Path("{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Products getProductQuery(@PathParam("id") int stnr) {
//
//            for(Products products: studentList){
//                if (products.getId()==stnr){
//                    return products;
//                }
//            }
//            throw new RuntimeException("There are no students in the list");
//
//
//    }
//    //    public Student getStudent(int id){
////        for(Student s :studentList){
////            if (s.getId()==id){
////                return s.getId();
////            }
////        }
////    }
//    @GET
//    @Path("hello")
//    @Produces(MediaType.TEXT_PLAIN)
//    public String SayHello(){
//        return "Hello there";
//    }
////    @GET
////    @Path("count")
////    @Produces(MediaType.TEXT_PLAIN)
////    public int getStudents(){
////        return studentList.size();
////    }
////    @GET
////    @Path("firs")
////    @Produces(MediaType.APPLICATION_JSON)
////    public Student getFirstStudent() {
////        if (studentList.size() > 0) {
////            return studentList.get(0);
////        }
////        else {
////            throw new RuntimeException("There are no students in the list");
////        }
////
////    }
////    @GET
////    @Path("me/{a}/myself/{b}/and/i")
////    @Produces(MediaType.TEXT_PLAIN)
////    public String queryAndPathParams(@QueryParam("x") String q1,@PathParam("a") int p1
////                                     ,@QueryParam("y") int q2,@PathParam("b") String p2){
////        return "x ="+q1+"\n" +
////                "y ="+q2+"\n" +
////                "a ="+p1+"\n"+
////                "b ="+p2+"\n";
////
////
////    }
//    @DELETE
//    @Path("{id}")
//    public void deleteStudent(@PathParam("id") int id){
//        Products products=this.getProduct(id);
//        if (products!=null){
//            studentList.remove(products);
//        }
//        else
//        {
//            throw new RuntimeException("Cannot find student with id :"+id+"");
//        }
//
//    }
//    @POST
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    public void createStudentForm(@FormParam("id") int id, @FormParam("name") String name){
//
//        if (this.exists(id)){
//            throw new RuntimeException("Cannot find student with id :"+id+" already exists");
//        }
//        Products s=new Products(id,name);
//        studentList.add(s);
//
//    }
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    public void createStudent(Products s){
//
////        if (this.exists(s.getId())){
////            throw new RuntimeException("Cannot find student with id :"+s.getId()+" already exists");
////        }
//       studentList.add(s);
//
//    }
//    @PUT
//    @Consumes(MediaType.APPLICATION_JSON)
//    public void updateStudent(Products s){
//        Products existingStudent=this.getProduct(s.getId());
//        if (existingStudent!=null){
//            existingStudent.setName(s.getName());
//
//        }
//        else {
//            throw new RuntimeException("Cannot find student with id :" + s.getId() + " does not exists");
//        }
//
//    }
//
//    public boolean exists(int id) {
//       for (Products s: studentList){
//           if (s.getId()==id){
//               return true;
//           }
//       }
//       return false;
//    }
//
//
//    public Products getProduct(int id) {
//
//
//        for (Products s:studentList) {
//            if (s.getId() == id) {
//                return s;
//            }
//
//        }
//        return null;
//    }
//
//
////    if(stu
//}
