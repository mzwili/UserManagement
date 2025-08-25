package com.mzwiliapp.userManage.controller;

import com.mzwiliapp.userManage.exception.UserAlreadyExistsException;
import com.mzwiliapp.userManage.exception.UserNotFoundException;
import com.mzwiliapp.userManage.model.User;
import com.mzwiliapp.userManage.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing User entities.
 * This controller provides CRUD (Create, Read, Update, Delete) operations for User management.
 * All endpoints are configured to allow cross-origin requests from <a href="http://localhost:3000">...</a> for
 * frontend integration during development.
 */
@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new user in the system.
     * This endpoint accepts a User object in the request body and persists it to the database.
     * The user will be assigned a unique ID automatically upon successful creation.
     *
     * @param user the User object containing user details to be created.
     *                Must include required fields: username, name, and email.
     *                The ID field will be ignored if provided, as it's auto-generated.
     *
     * @return User the newly created user object with generated ID and all provided details



     * Example usage:
     * POST /addUser
     * Content-Type: application/json
     * {
     *   "username": "john_doe",
     *   "name": "John Doe",
     *   "email": "john.doe@example.com"
     * }

     * Response:
     * {
     *   "id": 1,
     *   "username": "john_doe",
     *   "name": "John Doe",
     *   "email": "john.doe@example.com"
     * }
     */
    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@Valid @RequestBody User user) {
        try {
            // check if user with email already exists
            if (userRepository.findByEmail(user.getEmail()) != null) {
                throw new UserAlreadyExistsException("User with email " + user.getEmail() + " already exists");
            }

            User savedUser = userRepository.save(user);
            return ResponseEntity.ok(savedUser);

        } catch (UserAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());

        } catch (Exception ex) {
            // catch any unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
        }
    }

    /**
     * Retrieves all users from the system.

     * This endpoint returns a complete list of all users stored in the database.
     * If no users exist, an empty list is returned.
     *
     * @return List<User> a list containing all user objects in the system.
     *         Returns an empty list if no users are found.



     * Example usage:
     * GET /allUsers

     * Response:
     * [
     *   {
     *     "id": 1,
     *     "username": "john_doe",
     *     "name": "John Doe",
     *     "email": "john.doe@example.com"
     *   },
     *   {
     *     "id": 2,
     *     "username": "jane_smith",
     *     "name": "Jane Smith",
     *     "email": "jane.smith@example.com"
     *   }
     * ]
     */
    @GetMapping("/allUsers")
    List<User> getAllUsers(){
        return userRepository.findAll();
    }

    /**
     * Retrieves a specific user by their unique identifier.

     * This endpoint fetches a single user based on the provided ID from the URL path.
     *
     * @param id the unique identifier of the user to retrieve.
     *           Must be a valid Long value representing an existing user ID.
     *
     * @return User the user object corresponding to the provided ID



     * Example usage:
     * GET /user/1
     * Response (Success):
     * {
     *   "id": 1,
     *   "username": "john_doe",
     *   "name": "John Doe",
     *   "email": "john.doe@example.com"
     * }
     * Response (User not found):
     * HTTP 404 - UserNotFoundException will be thrown
     */
    @GetMapping("/user/{id}")
    User getUserById(@PathVariable Long id){
        return userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id));
    }

    /**
     * Updates an existing user's information.
     * This endpoint updates the username, name, and email of an existing user identified by the
     * provided ID. All three fields are updated regardless of whether they've changed.
     *
     * @param newUser the User object containing the updated information.
     *                The ID field in this object is ignored; the path variable ID is used instead.
     *                All updatable fields (username, name, email) should be provided.
     * @param id the unique identifier of the user to update.
     *           Must correspond to an existing user in the database.
     *
     * @return User the updated user object with all current information


     * Example usage:
     * PUT /user/1
     * Content-Type: application/json
     * {
     *   "username": "john_doe_updated",
     *   "name": "John Doe Jr.",
     *   "email": "john.doe.jr@example.com"
     * }
     * Response:
     * {
     *   "id": 1,
     *   "username": "john_doe_updated",
     *   "name": "John Doe Jr.",
     *   "email": "john.doe.jr@example.com"
     * }
     */
    @PutMapping("/user/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable Long id){
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setName(newUser.getName());
                    user.setEmail(newUser.getEmail());
                    return userRepository.save(user);
                }).orElseThrow(()-> new UserNotFoundException(id));
    }

    /**
     * Deletes a user from the system.
     * This endpoint removes a user identified by the provided ID from the database.
     * The operation first verifies that the user exists before attempting deletion.
     *
     * @param id the unique identifier of the user to delete.
     *           Must correspond to an existing user in the database.
     *
     * @return String a success message confirming the deletion, including the deleted user's ID

     * Example usage:
     * DELETE /user/1

     * Response (Success):
     * "User with id 1 has been deleted successfully!"
     * Response (User not found):
     * HTTP 404 - UserNotFoundException will be thrown
     */
    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable Long id){
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        return "User with id " + id + " has been deleted successfully!";
    }
}
