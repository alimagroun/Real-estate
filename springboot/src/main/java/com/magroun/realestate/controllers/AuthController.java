package com.magroun.realestate.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.magroun.realestate.model.City;
import com.magroun.realestate.model.ERole;
import com.magroun.realestate.model.Property;
import com.magroun.realestate.model.Role;
import com.magroun.realestate.model.State;
import com.magroun.realestate.model.User;
import com.magroun.realestate.model.Photo;
import com.magroun.realestate.payload.request.LoginRequest;
import com.magroun.realestate.payload.request.SignupRequest;
import com.magroun.realestate.payload.request.UpdatePasswordRequest;
import com.magroun.realestate.payload.response.MessageResponse;
import com.magroun.realestate.payload.response.UserInfoResponse;
import com.magroun.realestate.repository.PropertyRepository;
import com.magroun.realestate.repository.RoleRepository;
import com.magroun.realestate.repository.StateRepository;
import com.magroun.realestate.repository.CityRepository;
import com.magroun.realestate.repository.PhotoRepository;
import com.magroun.realestate.repository.UserRepository;
import com.magroun.realestate.security.jwt.JwtUtils;
import com.magroun.realestate.security.services.UserDetailsImpl;
import com.magroun.realestate.services.AuthService;
import com.magroun.realestate.services.PropertyService;
import com.magroun.realestate.services.UserService;
import com.magroun.realestate.util.FileUploadUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import org.springframework.util.StringUtils;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private PasswordEncoder encoder;
  
  @Autowired
  private PropertyRepository propertyRepository;
  
  @Autowired
  private StateRepository stateRepository;
  
  @Autowired
  private CityRepository cityRepository;
 
  @Autowired
  private PhotoRepository photoRepository;
  
  @Autowired
  private AuthService authService;
 
  @Autowired
  private UserService userService;
  
  @Autowired
  private PropertyService propertyService;

  @Autowired
  private JwtUtils jwtUtils;
 
 
  @PostMapping("/properties")
  public Property createProperty(@ModelAttribute Property property,
                                 @RequestParam("city_id") Long city_id,
                                 @RequestParam("files") MultipartFile[] files,
                                 Authentication authentication) throws IOException {
      // Retrieve the authenticated user
      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
      
      // Get the user ID from the authenticated user
      Long userId = userDetails.getId();
      
      // Retrieve the user from the database using the user ID
      User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

      // Save the property to the database
      City city = cityRepository.getCityById(city_id);
      property.setCity(city);
      property.setUser(user);
      Property savedProperty = propertyRepository.save(property);

      // Upload the photos
      for (MultipartFile file : files) {
          // Create a new unique filename using the current time and property ID
          String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
          String extension = StringUtils.getFilenameExtension(originalFilename);
          String newFilename = System.currentTimeMillis() + "-" + savedProperty.getId() + "." + extension;
          
          String uploadDir = "C:/Real-estate/angular-client/src/assets/photos";
          FileUploadUtil.saveFile(uploadDir, newFilename, file);
          
          // Save photo information to the database
          Photo photo = new Photo();
          photo.setFilename(newFilename);
          photo.setFilepath("assets/photos" + "/" + newFilename);
          photo.setProperty(savedProperty);
          photoRepository.save(photo);
      }

      return savedProperty;
  } 
  
  @GetMapping("/getCitiesByState")
  public ResponseEntity<List<City>> getCitiesByState(@RequestParam("stateId") int stateId) {
      // Get cities from cityRepository based on stateId
      List<City> cities = cityRepository.findByStateId(stateId);

      // Log the received stateId
      System.out.println("Received stateId: " + stateId);

      // Return the cities as response with HTTP status OK
      return new ResponseEntity<>(cities, HttpStatus.OK);
  }
  
  @GetMapping("/users")
  public ResponseEntity<List<User>> getAllUsers(){
  try {
	  List<User> users = new ArrayList<User>();
	  userRepository.findAll().forEach(users::add);
	  return new ResponseEntity<>(users, HttpStatus.OK);
	  
	   }
  catch (Exception e) {
 return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
}
  }
    
  @GetMapping("/states")
  public ResponseEntity<List<State>> getAllStates(){
  try {
	  List<State> states = new ArrayList<State>();
	  stateRepository.findAll().forEach(states::add);
	  return new ResponseEntity<>(states, HttpStatus.OK);  
	   }
  catch (Exception e) {
 return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
}
  }
  
  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
        .body(new UserInfoResponse(userDetails.getId(),
                                     userDetails.getUsername(),
                                     userDetails.getEmail(),
                                     userDetails.getName(),
                                     userDetails.getContactNumber(),
                                     roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(),
                         signUpRequest.getEmail(),
                         signUpRequest.getName(),
                         signUpRequest.getContactNumber(),
                         encoder.encode(signUpRequest.getPassword()));

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);

          break;
        case "mod":
          Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(modRole);

          break;
        default:
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        }
      });
    }

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
  
  @PostMapping("/signout")
  public ResponseEntity<?> logoutUser() {
    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(new MessageResponse("You've been signed out!"));
  }
  
  @GetMapping("/check-auth")
  public ResponseEntity<Boolean> checkAuthentication(HttpServletRequest request) {
      boolean isAuthenticated = authService.isAuthenticated(request);
      return ResponseEntity.ok(isAuthenticated);
  }
  
  @GetMapping("/isadmin")
  public ResponseEntity<Boolean> checkIsAdmin(HttpServletRequest request) {
      boolean isAdmin = authService.isAdmin(request);
      return ResponseEntity.ok(isAdmin);
  }

  @PutMapping("updateuser/{userId}")
  public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody User updatedUser) {
    try {
      User user = userService.updateUser(userId, updatedUser.getName(), updatedUser.getEmail(), updatedUser.getContactNumber());
      if (user != null) {
        return ResponseEntity.ok(user);
      } else {
        return ResponseEntity.notFound().build();
      }
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("updatePassword/{userId}")
  public ResponseEntity<MessageResponse> updatePassword(
          @PathVariable Long userId,
          @RequestBody UpdatePasswordRequest request) {

      boolean passwordUpdated = userService.updatePassword(userId, request.getCurrentPassword(), request.getNewPassword());

      if (passwordUpdated) {
    	  return ResponseEntity.ok(new MessageResponse("Password updated successfully"));
         
          
      } else {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Failed to update password."));
      }
  }
  private boolean isAuthorized(Long propertyId, Authentication authentication) {
	    Long userId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
	    boolean isAdmin = authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
	    Property existingProperty = propertyService.getPropertyById(propertyId);
	    return existingProperty != null && (existingProperty.getUser().getId().equals(userId) || isAdmin);
	}

}