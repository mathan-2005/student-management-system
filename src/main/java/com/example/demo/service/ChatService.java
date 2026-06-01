
package com.example.demo.service;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;

import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ChatService {

    private final StudentRepository repo;

    public ChatService(StudentRepository repo) {
        this.repo = repo;
    }

    public String process(String message) {

        message = message.toLowerCase();

        // SHOW MARK

        if(message.startsWith("show mark")) {

            String regNo =
                    message.replace("show mark", "").trim();

            Student s = repo.findByRegNo(regNo);

            if(s == null) {
                return "Student not found";
            }

            int total =
                    s.getTamil() +
                    s.getEnglish() +
                    s.getMaths() +
                    s.getScience() +
                    s.getSocial();

            return "Student: " + s.getName()
                    + " | Total = " + total;
        }

        // DOWNLOAD PDF

        if(message.startsWith("download")) {

            String regNo =
                    message.replace("download", "").trim();

            return "http://localhost:8080/export/pdf/"
                    + regNo;
        }

  // Show all students
        if(message.contains("all students")) {

            List<Student> students = repo.findAll();

            StringBuilder result = new StringBuilder();

           for(Student s : students) {
    result.append("RegNo: ")
          .append(s.getRegNo())
          .append("\nName: ")
          .append(s.getName())
          .append("\nAge: ")
          .append(s.getAge())
          .append("\n\n");
}

            return result.toString();
        }
        if(message.startsWith("add student")) {

    try {

        String[] lines = message.split("\n");

        Student student = new Student();

        for(String line : lines) {

    if(line.startsWith("name="))
        student.setName(line.replace("name=", "").trim());

    else if(line.startsWith("regno="))
        student.setRegNo(line.replace("regno=", "").trim());

    else if(line.startsWith("age="))
        student.setAge(
                Integer.parseInt(
                        line.replace("age=", "").trim()
                )
        );

    else if(line.startsWith("gender="))
        student.setGender(
                line.replace("gender=", "").trim()
        );

    else if(line.startsWith("password="))
        student.setPassword(
                line.replace("password=", "").trim()
        );
}
System.out.println("RegNo = " + student.getRegNo());
System.out.println("Name = " + student.getName());
System.out.println("Age = " + student.getAge());
System.out.println("Gender = " + student.getGender());
System.out.println("Password = " + student.getPassword());
        repo.save(student);

        return "Student Added Successfully";

    } catch(Exception e) {

        return "Invalid Format";

    }
}
if(message.startsWith("update")) {

    String input = message.replace("update", "").trim();

    String[] data = input.split(",");

    if(data.length != 4) {
        return "Format: update oldRegNo,newRegNo,name,age";
    }

    String oldRegNo = data[0].trim();
    String newRegNo = data[1].trim();
    String name = data[2].trim();

    int age;

    try {
        age = Integer.parseInt(data[3].trim());
    } catch(Exception e) {
        return "Invalid age";
    }

    Student student = repo.findByRegNo(oldRegNo);

    if(student == null) {
        return "Student not found";
    }

    student.setRegNo(newRegNo);
    student.setName(name);
    student.setAge(age);

    repo.save(student);

    return "Student Updated Successfully";
}
if(message.startsWith("delete")) {

    String regNo =
            message.replace("delete", "").trim();

    Student student = repo.findByRegNo(regNo);

    if(student == null) {
        return "Student not found";
    }

    repo.delete(student);

    return "Student Deleted Successfully";
}
if(message.equals("topper")) {

    List<Student> students = repo.findAll();

    Student topper = null;
    int highest = 0;

    for(Student s : students) {

        int total =
                s.getTamil() +
                s.getEnglish() +
                s.getMaths() +
                s.getScience() +
                s.getSocial();

        if(total > highest) {

            highest = total;
            topper = s;
        }
    }

    if(topper == null) {
        return "No students found";
  
    }

     double percentage= highest/ 5.0; 

    return """
🏆 TOPPER STUDENT

Name : """ + topper.getName() +
"\nRegNo : " + topper.getRegNo() +
"\nTotal : " + highest+
"\nPercentage : " + percentage + "%";
}
if(message.equals("rank list")) {

    List<Student> students = repo.findAll();

    students.sort((s1, s2) -> {

        int total1 =
                s1.getTamil() +
                s1.getEnglish() +
                s1.getMaths() +
                s1.getScience() +
                s1.getSocial();

        int total2 =
                s2.getTamil() +
                s2.getEnglish() +
                s2.getMaths() +
                s2.getScience() +
                s2.getSocial();

        return Integer.compare(total2, total1);
    });

    StringBuilder result = new StringBuilder();

    result.append("🏆 RANK LIST\n\n");

    int rank = 1;

    for(Student s : students) {

        int total =
                s.getTamil() +
                s.getEnglish() +
                s.getMaths() +
                s.getScience() +
                s.getSocial();

        result.append(rank++)
              .append(". ")
              .append(s.getName())
              .append(" (")
              .append(s.getRegNo())
              .append(")")
              .append(" - Total : ")
              .append(total)
              .append("\n");
    }

    return result.toString();
}
if(message.startsWith("add marks")) {

    String[] lines = message.split("\n");

    String regNo = lines[1].trim();
    String password = lines[2].trim();

   Student s = repo.findByRegNo(regNo);

if(s == null) {
    return "Student not found";
}

if(s.getPassword() == null) {
    return "No password set for this student";
}

if(!s.getPassword().equals(password)) {
    return "Invalid password";
}

    s.setTamil(Integer.parseInt(lines[3].replace("tamil:", "").trim()));
    s.setEnglish(Integer.parseInt(lines[4].replace("english:", "").trim()));
    s.setMaths(Integer.parseInt(lines[5].replace("maths:", "").trim()));
    s.setScience(Integer.parseInt(lines[6].replace("science:", "").trim()));
    s.setSocial(Integer.parseInt(lines[7].replace("social:", "").trim()));

    repo.save(s);

    return "Marks Updated Successfully";
   
}

        return """
        Commands:

1. show mark <RegNo>
2. download <RegNo>
3. all students
4. add student <regno=name=age=gender,pass>
5. update old rgno,RegNo,Name,Age
6. delete <RegNo>
7. topper
8. ranklist
9. add marks <reg,pass,marks>
""";
    }


}