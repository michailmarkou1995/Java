import {Authorities} from "./Authorities";
import {MedicationAccount} from "./MedicationAccount";

export class Patient {
  patientID!: number;
  username!: string;
  password!: string;
  firstName!: string;
  lastName!: string;
  email!: string;
  phone!: string;
  city!: string;
  streetAddress!: string;
  dateOfBirth!: string;
  enabled!: boolean;
  doctorIs!: boolean;
  medicationAccount!: MedicationAccount;
  authorities!: Authorities;
  accountNonExpired!: boolean;
  credentialsNonExpired!: boolean;
  accountNonLocked!: boolean;

  constructor(username: string, firstName: string, lastName: string, email: string, phone: string, dateOfBirth: string, password: string) {
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phone = phone;
    this.dateOfBirth = dateOfBirth;
    this.password = password;
  }//perna orismata des video pu xis webdev or angular11+
}
