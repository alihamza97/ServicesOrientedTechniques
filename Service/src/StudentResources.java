import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
@Path("students")

public class StudentResources {
    public List<Student> studentList=new ArrayList<>();
    public  StudentResources(){
        studentList.add(new Student(1,"ALI"));
        studentList.add(new Student(2,"Hamza"));

    }
//    public Student getStudent(int id){
//        for(Student s :studentList){
//            if (s.getId()==id){
//                return s.getId();
//            }
//        }
//    }
//    @GET
//    @Path("hello")
//    @Produces(MediaType.TEXT_PLAIN)
//    public String SayHello(){
//        return "Hello there";
//    }
//    @GET
//    @Path("count")
//    @Produces(MediaType.TEXT_PLAIN)
//    public int getStudents(){
//        return studentList.size();
//    }
//    @GET
//    @Path("firs")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Student getFirstStudent() {
//        if (studentList.size() > 0) {
//            return studentList.get(0);
//        }
//        else {
//            throw new RuntimeException("There are no students in the list");
//        }
//
//    }
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Student getStudentQuery(@PathParam("id") int stnr) {

            for(Student student: studentList){
                if (student.getId()==stnr){
                    return student;
                }
            }
            throw new RuntimeException("There are no students in the list");


    }
//    @GET
//    @Path("me/{a}/myself/{b}/and/i")
//    @Produces(MediaType.TEXT_PLAIN)
//    public String queryAndPathParams(@QueryParam("x") String q1,@PathParam("a") int p1
//                                     ,@QueryParam("y") int q2,@PathParam("b") String p2){
//        return "x ="+q1+"\n" +
//                "y ="+q2+"\n" +
//                "a ="+p1+"\n"+
//                "b ="+p2+"\n";
//
//
//    }
    @DELETE
    @Path("{id}")
    public void deleteStudent(@PathParam("id") int id){
        Student student=this.getStudent(id);
        if (student!=null){
            studentList.remove(student);
        }
        else
        {
            throw new RuntimeException("Cannot find student with id :"+id+"");
        }

    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void createStudent(Student student){

        if (this.exists(student.getId())){
            throw new RuntimeException("Cannot find student with id :"+student.getId()+" already exists");
        }
       studentList.add(student);

    }
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateStudent(Student student){
        Student existingStudent=this.getStudent(student.getId());
        if (existingStudent!=null){
            existingStudent.setName(student.getName());

        }
        else {
            throw new RuntimeException("Cannot find student with id :" + student.getId() + " does not exists");
        }

    }

    private boolean exists(int id) {
       for (Student s: studentList){
           if (s.getId()==id){
               return true;
           }else
               return false;
       }
       return false;
    }


    private Student getStudent(int id) {

        for (Student s:studentList){
            if (s.getId()==(id)){
                return s;
            }
            else
            return null;
        }
        return null;
    }


//    if(stu
}
