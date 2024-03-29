import { Component, OnInit } from '@angular/core';
import { ActivatedRoute,Router  } from '@angular/router';
import {PropertyService} from '../_services/property.service';
import { Property } from '../_models/property';
import{Photo} from '../_models/photo';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-property-details',
  templateUrl: './property-details.component.html',
  styleUrls: ['./property-details.component.scss']
})
export class PropertyDetailsComponent implements OnInit {
  
  property?: Property;
  propertyId?: number;
  photos: Photo[] = [];
  primaryPhotoSrc?: string;
  isFavorite?: boolean;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private propertyService: PropertyService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.propertyId = params['propertyId'];
      if (this.propertyId !== undefined) {
        this.getPropertyDetails(this.propertyId);
        this.checkFavorite(this.propertyId);
      }
    });
  }

  toggleFavorite(): void {
    if (this.propertyId !== undefined) {
    if   (this.isFavorite && this.propertyId !== undefined) {
      // Property is a favorite, remove it from favorites
      this.propertyService.removeFromFavorites(this.propertyId).subscribe(
        () => {
        this.snackBar.open('Property removed from favorites.', 'Close', { duration: 3000 });
        this.isFavorite = false;
          // Handle success here
        },
        (error) => {
          console.error('Failed to remove property from favorites', error);
          // Handle error here
        }
      );
    } else {
      // Property is not a favorite, add it to favorites
      this.propertyService.addToFavorites(this.propertyId).subscribe(
        () => {
          this.snackBar.open('Property added to favorites.', 'Close', { duration: 3000 });
          this.isFavorite = true;
          // Handle success here
        },
        (error) => {
          console.error('Failed to add property to favorites', error);
          // Handle error here
        }
      );
    }}
  }

  checkFavorite(propertyId: number) {
    this.propertyService.checkFavorite(propertyId).subscribe(result => {
      this.isFavorite = result;
    });
  }

  getPropertyDetails(propertyId: number): void {
    this.propertyService.getProperty(propertyId).subscribe(
      property => {
        if (property) {
          this.property = property;
          this.propertyService.getPhotos(propertyId).subscribe(photos => {
            this.photos = photos;
            this.primaryPhotoSrc = this.photos[0].filepath;
          });
        }
      },
      error => {
        this.router.navigate(['/404']);
      }
    );
  }

  setPrimaryPhoto(index: number): void {
    this.primaryPhotoSrc = this.photos[index].filepath;
  }
}
