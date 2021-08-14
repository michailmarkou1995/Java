import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Patient} from './classes/Patient';

@Injectable()
export class UserService {

  formData!: Patient[];
  private baseUrl = "http://localhost:8015/api/";

  constructor(private http: HttpClient) {
  }

  getUsers() {
    let url = "http://localhost:8015/api/patient/all";
    console.log(this.http.get(url, {withCredentials: true}));
    return this.http.get(url, {withCredentials: true});
  }

  getUsers1() {
    let url = "http://localhost:8015/api/patient/all";
    console.log(this.http.get(url, {withCredentials: true}));
    return this.http.get(url, {withCredentials: true});
  }

  enableUser(username: string) {
    let url = "http://localhost:8015/api/patient/" + username + "/enable";
    return this.http.get(url, {withCredentials: true});
  }

  disableUser(username: string) {
    let url = "http://localhost:8015/api/patient/" + username + "/disable";
    return this.http.get(url, {withCredentials: true});
  }

  deleteUser(id: number): Observable<any> {
    //den thes return aplo query gia delete ! me message alert kalo ine onConfirmation javascript?
    return this.http.delete(`${this.baseUrl}delete-patient/${id}`, {withCredentials: true, responseType: 'text'});
    //  return this.http.delete("http://localhost:8015/api/patient/"+id+"/delete", { responseType: 'text' });
  }

  enableDoctor(username: string, category_doctor: string): Observable<any> {
    let url = "http://localhost:8015/api/patient/" + username + "/" + category_doctor + "/doctor/enable";
    return this.http.get(url, {withCredentials: true});
  }

  //  enableDoctor (username: string): Observable<any> {
  //   let url = "http://localhost:8015/api/patient/"+username+"/doctor/enable";
  //   return this.http.get(url, { withCredentials: true });
  //  }

  disableDoctor(username: string): Observable<any> {
    let url = "http://localhost:8015/api/patient/" + username + "/doctor/disable";
    return this.http.get(url, {withCredentials: true});
  }

  getDoctor() {
    let url = "http://localhost:8015/apiDoctor/doctor/all";
    console.log(this.http.get(url, {withCredentials: true}));
    return this.http.get(url, {withCredentials: true});
  }

  public addPatient(patient: Patient) {//save
    console.log("sends json Patient insert");
    var hardcoded = [{
      "username": "michailkalinx",
      "password": "$2a$12$rsbXuaMWQMSUjMQlqTAay.vgmHOStVkj/3Gd8WktHHwNy4ogfFtcG",
      "firstName": "Michail",
      "lastName": "Markou",
      "email": "backtrackpower@gmail.com",
      "phone": "6908990119",
      "city": null,
      "streetAddress": null,
      "dateOfBirth": "2021-06-03",
      "enabled": true,
      "doctorIs": false,
      "patientID": 2,
      "doctor": null,
      "accountNonLocked": true,
      "authorities": [{"authority": "ROLE_ADMIN"}],
      "credentialsNonExpired": true,
      "accountNonExpired": true
    }];
    var hardcoded1 = {
      "username": "heroku",
      "password": "$2a$12$rsbXuaMWQMSUjMQlqTAay.vgmHOStVkj/3Gd8WktHHwNy4ogfFtcG",
      "firstName": "heroku",
      "lastName": "heroku",
      "email": "backtrackpower@heroku.com",
      "phone": "6908990119",
      "dateOfBirth": "2021-06-03"
    };
    let url = "http://localhost:8015/api/patient/set";
    //JSON.stringify(hardcoded)
    let headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.post<Patient>(url, (patient), {headers: headers, withCredentials: true});
    // return this.http.post<Patient>(url, patient, { withCredentials: true });
  }

  public addDoctor(patient: any) {//save
    console.log("sends json Patient insert");
    var hardcoded = [{
      "username": "michailkalinx",
      "password": "$2a$12$rsbXuaMWQMSUjMQlqTAay.vgmHOStVkj/3Gd8WktHHwNy4ogfFtcG",
      "firstName": "Michail",
      "lastName": "Markou",
      "email": "backtrackpower@gmail.com",
      "phone": "6908990119",
      "city": null,
      "streetAddress": null,
      "dateOfBirth": "2021-06-03",
      "enabled": true,
      "doctorIs": false,
      "patientID": 2,
      "doctor": null,
      "accountNonLocked": true,
      "authorities": [{"authority": "ROLE_ADMIN"}],
      "credentialsNonExpired": true,
      "accountNonExpired": true
    }];
    var hardcoded1 = {
      "username": "senpai",
      "password": "$2a$12$rsbXuaMWQMSUjMQlqTAay.vgmHOStVkj/3Gd8WktHHwNy4ogfFtcG",
      "firstName": "senpai",
      "lastName": "senpai",
      "email": "backtrackpower@senpai.com",
      "phone": "6908990119",
      "dateOfBirth": "2021-06-03",
      "category":
        "guru"
    };
    let url = "http://localhost:8015/api/patient/doctor/set";
    //JSON.stringify(hardcoded)
    let headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.post<any>(url, (patient), {headers: headers, withCredentials: true});
    // return this.http.post<Patient>(url, patient, { withCredentials: true });
  }

}
