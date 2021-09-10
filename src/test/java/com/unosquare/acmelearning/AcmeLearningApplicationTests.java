package com.unosquare.acmelearning;

import com.unosquare.acmelearning.controller.InstructorController;
import com.unosquare.acmelearning.controller.RegisterController;
import com.unosquare.acmelearning.controller.StudentController;
import com.unosquare.acmelearning.dto.CourseEnrollment;
import com.unosquare.acmelearning.dto.CourseRequest;
import com.unosquare.acmelearning.dto.EnrolledStudents;
import com.unosquare.acmelearning.dto.UserRegistration;
import com.unosquare.acmelearning.enumeration.USER_TYPE;
import com.unosquare.acmelearning.model.Course;
import com.unosquare.acmelearning.model.Instructor;
import com.unosquare.acmelearning.model.Student;
import com.unosquare.acmelearning.model.User;
import com.unosquare.acmelearning.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AcmeLearningApplicationTests {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RegisterController registerController;
	@Autowired
	private InstructorController instructorController;
	@Autowired
	private StudentController studentController;

	private User student1;
	private User student2;
	private User instructor1;
	private User instructor2;

	@Test
	void contextLoads() {
		assert true;
	}


	public void setStudent1(){
		Authentication auth= new UsernamePasswordAuthenticationToken(student1, null);
		SecurityContextHolder.getContext().setAuthentication(auth);
	}
	public void setStudent2(){
		Authentication auth= new UsernamePasswordAuthenticationToken(student2, null);
		SecurityContextHolder.getContext().setAuthentication(auth);
	}
	public void setInstructor1(){
		Authentication auth= new UsernamePasswordAuthenticationToken(instructor1, null);
		SecurityContextHolder.getContext().setAuthentication(auth);
	}
	public void setInstructor2(){
		Authentication auth= new UsernamePasswordAuthenticationToken(instructor2, null);
		SecurityContextHolder.getContext().setAuthentication(auth);
	}


	@Test
	@Transactional
	@Rollback(value = false)
	@Order(1)
	public void createUsersTest(){
		UserRegistration student1=new UserRegistration("student1", USER_TYPE.STUDENT,"student1","passwordTest");
		UserRegistration student2=new UserRegistration("student2", USER_TYPE.STUDENT,"student2","passwordTest");;
		UserRegistration instructor1=new UserRegistration("instructor1", USER_TYPE.INSTRUCTOR,"instructor1","passwordTest");;
		UserRegistration instructor2=new UserRegistration("instructor2", USER_TYPE.INSTRUCTOR,"instructor2","passwordTest");;
		registerController.register(student1);
		registerController.register(student2);
		registerController.register(instructor1);
		registerController.register(instructor2);
		this.student1=userRepository.findByUsernameAndPassword(student1.getUsername(),student1.getPassword()).get();
		this.student2=userRepository.findByUsernameAndPassword(student2.getUsername(),student1.getPassword()).get();
		this.instructor1=userRepository.findByUsernameAndPassword(instructor1.getUsername(),student1.getPassword()).get();
		this.instructor2=userRepository.findByUsernameAndPassword(instructor2.getUsername(),student1.getPassword()).get();
		assert true;
	}

	@Test
	@Transactional
	@Rollback(value = false)
	@Order(2)
	public void createCourseTest(){
		setInstructor1();
		String result=instructorController.createCourse(new CourseRequest("Math1"));
		assert result.equals("Course added successfully.");
		result=instructorController.createCourse(new CourseRequest("Math1"));
		assert result.equals("Course already exists, please choose another name.");

		setInstructor2();
		String result2=instructorController.createCourse(new CourseRequest("Physics1"));
		assert result2.equals("Course added successfully.");

	}

	@Test
	@Transactional
	@Rollback(value = false)
	@Order(3)
	public void getCoursesForInstructorTest(){
		setInstructor1();
		List<Course> list=instructorController.listCourses();
		assert list.size()>0;
	}

	@Test
	@Transactional
	@Rollback(value = false)
	@Order(4)
	public void listCourseForStudentsTest(){
		setStudent1();
		List<Course> list=studentController.getAllCourses();
		assert list.size()>0;
	}

	@Test
	@Transactional
	@Rollback(value = false)
	@Order(5)
	public void enrollmentToCourseTest(){
		setStudent1();
		List<Course> list=studentController.getAllCourses();
		CourseEnrollment newCourse=new CourseEnrollment(list.stream().findFirst().get().getId());
		String result=studentController.enrollToCourse(newCourse);
		assert result.equals("Enrolled to course successfully.");
		result=studentController.enrollToCourse(newCourse);
		assert result.equals("You are already enrolled to this course.");
		newCourse.setCourseId(99999999999999999L);
		result=studentController.enrollToCourse(newCourse);
		assert result.equals("Course does not exists, please verify your information.");
	}


	@Test
	@Transactional
	@Rollback(value = false)
	@Order(6)
	public void listEnrollmentCoursesTest(){
		setStudent1();
		List<Course> list=studentController.listEnrolledCourses();
		assert list.size()>0;
	}

	@Test
	@Transactional
	@Rollback(value = false)
	@Order(7)
	public void startCourseTest(){
		setInstructor1();
		List<Course> list=instructorController.listCourses();
		Course course=list.stream().findFirst().get();
		String result=instructorController.startCourse(9999999999999L);
		assert result.equals("Course does not exist, please verify your information.");
		result=instructorController.startCourse(course.getId());
		assert result.equals("Course started successfully.");
		result=instructorController.startCourse(course.getId());
		assert result.equals("Course already started.");
		setInstructor2();
		result=instructorController.startCourse(course.getId());
		assert result.equals("This course does not belong to the user.");

	}

	@Test
	@Transactional
	@Rollback(value = false)
	@Order(8)
	public void listEnrollmentsTest(){
		setInstructor1();
		List<Course> list=instructorController.listCourses();
		List<EnrolledStudents> enrolledStudents=instructorController.getEnrolledStudents(
				list.stream().findFirst().get().getId()
		);
		assert enrolledStudents.size()>0;

	}


	@Test
	@Transactional
	@Rollback(value = false)
	@Order(9)
	public void enrollToStartedCourseTest(){
		setStudent2();
		List<Course> list=studentController.getAllCourses();
		CourseEnrollment newCourse=new CourseEnrollment(list.stream()
				.filter(course -> course.getInCourse())
				.findFirst().get().getId());
		String result=studentController.enrollToCourse(newCourse);
		assert result.equals("This course already started, you can't enroll to started courses.");
	}


	@Test
	@Transactional
	@Rollback(value = false)
	@Order(10)
	public void dropFromCourseTest(){
		setInstructor2();
		List<Course> listCourses=instructorController.listCourses();
		setStudent1();
		List<Course> list=studentController.listEnrolledCourses();


		String result=studentController.dropFromCourse( 99999999999L );
		assert result.equals("Course does not exists, please verify your information.");
		result=studentController.dropFromCourse(listCourses.stream().findFirst().get().getId());
		assert result.equals("You are not enrolled to this course.");
		result=studentController.dropFromCourse(list.stream().findFirst().get().getId());
		assert result.equals("Dropped from course successfully");

	}

	@Test
	@Transactional
	@Rollback(value = false)
	@Order(11)
	public void cancelCourseTest(){
		setInstructor2();
		List<Course> listFromAnother=instructorController.listCourses();
		setInstructor1();
		List<Course> list=instructorController.listCourses();

		String result=instructorController.cancelCourse(9999999999999L);
		assert result.equals("Course does not exist, please verify your information");
		result=instructorController.cancelCourse(list.stream().findFirst().get().getId() );
		assert result.equals("Course already started, you can't cancel started courses.");
		result=instructorController.cancelCourse(listFromAnother.stream().findFirst().get().getId() );
		assert result.equals("This course does not belong to the user.");

		setInstructor2();
		result=instructorController.cancelCourse(listFromAnother.stream().findFirst().get().getId() );
		assert result.equals("Course cancel successful.");

	}


	@Test
	public void constructorsTest(){
		CourseEnrollment object1=new CourseEnrollment();
		CourseRequest course=new CourseRequest();
		course.setName("thing");
		assert course.getName().equals("thing");
		Student student=new Student(1L,"name",1L,new ArrayList<>());
		EnrolledStudents enrolled=new EnrolledStudents();
		enrolled=new EnrolledStudents(student);
		enrolled=new EnrolledStudents(1L, "name");
		enrolled.toString();
		enrolled.hashCode();
		assert enrolled.getId().equals(1L);
		assert enrolled.getName().equals("name");
		UserRegistration user=new UserRegistration();
		user=new UserRegistration("name", USER_TYPE.INSTRUCTOR,"name","name");
		user.toString();
		user.hashCode();
		assert user.getType().equals(USER_TYPE.INSTRUCTOR);
		assert user.getUsername().equals("name");
		assert user.getName().equals("name");
		assert user.getPassword().equals("name");
		Course courseTest=new Course(1L,"name",false);
		assert courseTest!=null;
		Instructor instructor=new Instructor(1L, "name", 1L, new ArrayList<>());
		assert instructor!=null;
		Student student3=new Student(1L,"name",1L,new ArrayList<>());
		assert student3!=null;
		User newUser=new User(1L, "name","name",USER_TYPE.STUDENT);
		assert newUser!=null;


	}




}
