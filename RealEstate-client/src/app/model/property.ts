import{User} from '../model/user'
export class Property{

	property_id: number;
	property_date: Date;
	numberOfBedrooms : number;
	numberOfBathrooms : number;
	numberOfPartialBathrooms : number;
	numberofGarages : number;
	squareMeters : number;
	description : string;
	yearBuilt : number;
	user:User;

}