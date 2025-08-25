package com.mzwiliapp.userManage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mzwiliapp.userManage.model.User;
import com.mzwiliapp.userManage.repository.UserRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;


@SpringBootTest(classes = UserManageApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserManageApplicationTests {

	@Autowired
	private UserRepository userRepository;

	private User theUser;
	private ObjectMapper objectMapper;
	private boolean skipTearDown;

	@LocalServerPort
	private int port;


	@BeforeEach
	public void setUp() {
		theUser = new User();
		theUser.setId(34L);
		theUser.setUsername("Gal#1");
		theUser.setName("Gal");
		theUser.setEmail("Gale@mail.net");
		objectMapper = new ObjectMapper();
		skipTearDown = false;

//		SpringApplication.run(UserManageApplication.class);

	}

	@Test
	public void addUserTest() throws JsonProcessingException {

		String addUserBody = objectMapper.writeValueAsString(theUser);

		given().baseUri("http://localhost:" + port).
				contentType(ContentType.JSON).body(addUserBody).
				when().post("/addUser").then().assertThat().statusCode(200);


	}

	@Test
	public void getAllUserTest() {


		given().
				when().get("http://localhost:" + port + "/allUsers").
				then().assertThat().statusCode(200);

		skipTearDown = true;
	}

	@Test
	public void getUniqueUserTest()  {

		userRepository.save(theUser);
		theUser.setId(userRepository.findByEmail(theUser.getEmail()).getId());
		given().
				pathParams("id",theUser.getId()).
				when().
				get("http://localhost:"+ port +"/user/{id}").
				then().
				assertThat().statusCode(200).
				assertThat().body("username",equalTo("Gal#1"));


	}

	@Test
	public void updateUserTest()  {

		userRepository.save(theUser);
		theUser.setId(userRepository.findByEmail(theUser.getEmail()).getId());
		theUser.setName("newName");
		given().
				pathParams("id",theUser.getId()).
				contentType(ContentType.JSON).
				body(theUser).
				when().
				put("http://localhost:"+ port +"/user/{id}").
				then().
				assertThat().statusCode(200).
				assertThat().body("name",equalTo("newName"));


	}

	@Test
	public void deleteUserTest() {
		userRepository.save(theUser);
		theUser.setId(userRepository.findByEmail(theUser.getEmail()).getId());
		given().
				pathParams("id",theUser.getId()).
		when().
				delete("http://localhost:"+ port +"/user/{id}").
		then().
				assertThat().statusCode(200).
				assertThat().contentType("text/plain").
				assertThat().body(equalTo("User with id " + theUser.getId() + " has been deleted successfully!"));

		skipTearDown = true;
	}

	@Test
	public void addUser_MissingFields_ShouldReturnBadRequest() throws JsonProcessingException {
		// Missing username and email
		User myUser = new User();
		myUser.setId(87L);
		myUser.setName("");
		myUser.setUsername("");
		myUser.setEmail("sdfwe");

		String invalidBody = objectMapper.writeValueAsString(myUser);

		given()
				.baseUri("http://localhost:" + port)
				.contentType(ContentType.JSON)
				.body(invalidBody)
				.when()
				.post("/addUser")
				.then()
				.statusCode(400) // Expect validation failure
				.body("username", equalTo("Username must be at least 3 characters"))
				.body("name", equalTo("Name must be at least 3 characters"))
				.body("email", equalTo("Email should be valid"));

		skipTearDown = true;
	}

    // Duplicate user - already exists
    @Test
    public void addUser_DuplicateEmail() throws JsonProcessingException {


        String body = objectMapper.writeValueAsString(theUser);

        // First call should succeed
        given()
                .baseUri("http://localhost:" + port)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/addUser")
                .then()
                .statusCode(200);

        // Second call with same email should fail
        given()
                .baseUri("http://localhost:" + port)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/addUser")
                .then()
                .statusCode(409) // UserAlreadyExistsException should map to 409
				.contentType("text/plain")
				.body(equalTo("User with email Gale@mail.net already exists"));
    }

    // Invalid user data - name too short
    @Test
    public void addUser_InvalidName() throws JsonProcessingException {
        User user = new User();
        user.setName("Al"); // too short
        user.setEmail("invalid@test.com");

        String body = objectMapper.writeValueAsString(user);

        given()
                .baseUri("http://localhost:" + port)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/addUser")
                .then()
                .statusCode(400) // validation error
                .body("errors", not(empty())); // assuming your ValidationExceptionHandler returns "errors"
    }


	@AfterEach
	public void tearDown() {
		if (!skipTearDown) {
			userRepository.deleteById(userRepository.findByEmail(theUser.getEmail()).getId());
		}
		theUser = null;
	}

}
