//https://www.tutorialspoint.com/jackson/jackson_object_serialization.htm
package gr.hua.dit.oopii.lec5.streams;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JacksonTester2<E> {

    public static void main(String args[]) throws InterruptedException {
        JacksonTester2<Student> tester = new JacksonTester2();
        ArrayList<Student> arraylist_students = new ArrayList<>();


        try {
    	 	  /* for (int i=20; i<30; i++) {
		         Student student = new Student();
		         student.setAge(i);
		         student.setName("name_"+(i-20));
		         Date date = new Date();
		         student.setTimestamp(date.getTime());	         
		         arraylist_students.add(student);
		         Thread.sleep(i);
		   } 
		   tester.writeJSON(arraylist_students);

            */arraylist_students = tester.readJSON();
            System.out.println("The data of the array list is:\n" + arraylist_students);
            System.out.println("The 1st object in the arraylist is: " + arraylist_students.get(1));
            System.out.println("The 1st object in the arraylist is: " + arraylist_students.get(2));
            System.out.println("The Name of 1st student is: " + ((Student) arraylist_students.get(1)).getName());
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JacksonTester2() {
    }

    public void writeJSON(ArrayList<Student> in_arraylist) throws JsonGenerationException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("myarraylist.json"), in_arraylist);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<E> readJSON() throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        //mapper.enableDefaultTyping();
        //TypeToken<ObjectType> typeToken = new TypeToken<ObjectType>(getClass()) { };
        //Type type = typeToken.getType(); // or getRawType() to return Class<? super T>

        TypeReference<E> myType2 = new TypeReference<>() {
        };
        //TypeReference<DataStructureType> myType1 = new TypeReference<>() {};

        //System.out.println(ObjectType.class);

        return mapper.readValue(new File("arraylist.json"), mapper.getTypeFactory().
                constructCollectionType(List.class, myType2.getClass()));

        //mapper.getTypeFactory().constructParametricType(ObjectType.class, myType2);

        //return mapper.readValue(new File(fileName), myType2);
    }


    private static class Student {
        private String name;
        private int age;
        private long timestamp;

        public Student() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public String toString() {
            return "Student [ name: " + name + ", age: " + age + " ]";
        }
    }

}