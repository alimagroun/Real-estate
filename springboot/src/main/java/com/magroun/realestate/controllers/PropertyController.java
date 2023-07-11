package com.magroun.realestate.controllers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.magroun.realestate.model.Photo;
import com.magroun.realestate.model.Property;
import com.magroun.realestate.model.SavedSearch;
import com.magroun.realestate.projection.PropertyProjection;
import com.magroun.realestate.repository.PropertyRepository;
import com.magroun.realestate.services.PropertyService;
import com.magroun.realestate.services.SavedSearchService;
import com.magroun.realestate.services.UserPropertyService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import com.magroun.realestate.security.services.UserDetailsImpl;
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/properties")
public class PropertyController {
	
  
		@Autowired
		UserPropertyService userPropertyService;	
		@Autowired
		PropertyRepository propertyRepository;
		@Autowired
		SavedSearchService savedSearchService;
		@Autowired
		PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping
    public ResponseEntity<List<Property>> getAllProperties() {
        List<Property> properties = propertyService.getAllProperties();
        return ResponseEntity.ok(properties);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Property> getPropertyById(@PathVariable Long id) {
        Property property = propertyService.getPropertyById(id);
        if (property != null) {
            return ResponseEntity.ok(property);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Property> createProperty(@RequestBody Property property) {
        Property createdProperty = propertyService.createProperty(property);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProperty);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Property> updateProperty(@PathVariable Long id, @RequestBody Property property) {
        Property updatedProperty = propertyService.updateProperty(id, property);
        if (updatedProperty != null) {
        	System.out.println("city code:"+property.getCity().getId());
            return ResponseEntity.ok(updatedProperty);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        boolean deleted = propertyService.deleteProperty(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{propertyId}/photos")
    public ResponseEntity<List<Photo>> getPhotosByPropertyId(@PathVariable Long propertyId) {
        List<Photo> photos = propertyService.getPhotosByPropertyId(propertyId);
        if (!photos.isEmpty()) {
            return ResponseEntity.ok(photos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	  @GetMapping("/filter") public Page<Property>
	  getPropertiesByFilter(@RequestParam(required = false) String status,
	  
	  @RequestParam(required = false) Long stateId,
	  
	  @RequestParam(required = false) Float minPrice,
	  
	  @RequestParam(required = false) Float maxPrice,
	  
	  @RequestParam(defaultValue = "0") int bedrooms,
	  
	  @RequestParam(defaultValue = "0") int bathrooms,
	  
	  @RequestParam(required = false) Long cityId, Pageable pageable) {
	 
	return propertyService.getPropertiesByFilter(status, stateId, minPrice, maxPrice,
	  bedrooms, bathrooms, cityId, pageable); }
	 
    
    @GetMapping("/firstphoto/{propertyId}")
    public ResponseEntity<Photo> getFirstPhotoByPropertyId(@PathVariable Long propertyId) {
        Photo photo = propertyService.getFirstPhotoByPropertyId(propertyId);
        if (photo != null) {
            return ResponseEntity.ok(photo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/last4")
    public List<Property> getLast8Properties() {
        return propertyService.getLast4Properties();
    }
    
    @GetMapping("getAllByuser")
    public Page<PropertyProjection> getPropertiesByUserId(Authentication authentication, Pageable pageable) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();

        return propertyService.getPropertiesByUserId(userId, pageable);
    }
    
    @PostMapping("/favorites")
    public ResponseEntity<String> addToFavorites(@RequestParam("propertyId") Long propertyId, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();

        userPropertyService.addToFavorites(userId, propertyId);

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
    @GetMapping("/favorites")
    public List<Long> getFavoriteProperties(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();
        return userPropertyService.getFavoriteProperties(userId);
    }
  
    @DeleteMapping("/favorites")
    public ResponseEntity<String> removeFromFavorites(
        @RequestParam("propertyId") Long propertyId,
        Authentication authentication) {
      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
      Long userId = userDetails.getId();

      userPropertyService.removeFromFavorites(userId, propertyId);

      return ResponseEntity.status(HttpStatus.OK).body(null);
    }
    
    @GetMapping("/favorites/check")
    public boolean checkFavorite(
            @RequestParam("propertyId") Long propertyId,
            Authentication authentication
    ) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();
        return userPropertyService.isFavorite(userId, propertyId);
    }
    @GetMapping("/userfavorites")
    public ResponseEntity<Page<PropertyProjection>> findFavoritesProperties(
        Authentication authentication,
        Pageable pageable
    ) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();
        
        Page<PropertyProjection> favorites = propertyService.findFavoritesProperties(userId, pageable);
        return ResponseEntity.ok(favorites);
    }
    @PostMapping("/saved-searches")
    public ResponseEntity<Void> saveSearch(@RequestParam(required = false) String status,
                                  @RequestParam(required = false) Long stateId,
                                  @RequestParam(required = false) Float minPrice,
                                  @RequestParam(required = false) Float maxPrice,
                                  @RequestParam(defaultValue = "0") int bedrooms,
                                  @RequestParam(defaultValue = "0") int bathrooms,
                                  @RequestParam(required = false) Long cityId,
                                  Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();

        savedSearchService.save(status, stateId, minPrice, maxPrice, bedrooms, bathrooms,
                cityId, userId);

        return ResponseEntity.noContent().build();
    }
    @GetMapping("/saved-searches")
    public Page<SavedSearch> getSavedSearches(Authentication authentication, Pageable pageable) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();
        return savedSearchService.getSavedSearches(userId,pageable);
    }
}

