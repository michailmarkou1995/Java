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


  getUsers1(username: any) {
    let url = "http://localhost:8015/apiDoctor/doctor/" + username + "/all";
    console.log(username);
    //  console.log(this.http.get(url, { withCredentials: true }));
    return this.http.get(url, {withCredentials: true});
  }

  getUsers2(username: any) {
    let url = "http://localhost:8015/apiDoctor/doctor/" + username + "/inline";
    console.log(username);
    //  console.log(this.http.get(url, { withCredentials: true }));
    return this.http.get(url, {withCredentials: true});
  }

  fetchPatient(username: any, app_id: any) {
    let url = "http://localhost:8015/apiDoctor/doctor/" + username + "/" + app_id + "/fetch";
    console.log(username);
    return this.http.get(url, {withCredentials: true});
  }

  saveEdit(patient_diagnosed: string, prescriptions_directions: string, appointment_Id: string) {
    let url = "http://localhost:8015/apiDoctor/doctor/setDirections";
    //JSON.stringify(hardcoded)
    let localAssocArray = {
      "diagnosed": patient_diagnosed,
      "prescriptions": prescriptions_directions,
      "patientHealth": appointment_Id
    };
    let headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.post<any>(url, (localAssocArray), {headers: headers, withCredentials: true});
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

  enableDoctor(username: string): Observable<any> {
    let url = "http://localhost:8015/api/patient/" + username + "/doctor/enable";
    return this.http.get(url, {withCredentials: true});
  }

  disableDoctor(username: string): Observable<any> {
    let url = "http://localhost:8015/api/patient/" + username + "/doctor/disable";
    return this.http.get(url, {withCredentials: true});
  }

  getDoctor() {
    let url = "http://localhost:8015/apiDoctor/doctor/all";
    console.log(this.http.get(url, {withCredentials: true}));
    return this.http.get(url, {withCredentials: true});
  }

  public addDatesAvailable(dateavailable: any) {//save
    console.log("sends json Patient insert");
    console.log(dateavailable);
    let url = "http://localhost:8015/apiDoctor/doctor/setAppointment";
    //JSON.stringify(hardcoded)
    let headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.post<any>(url, (dateavailable), {headers: headers, withCredentials: true});
  }

}
