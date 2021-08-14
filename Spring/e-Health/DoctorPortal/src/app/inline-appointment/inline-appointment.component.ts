import {Component, OnInit} from '@angular/core';
import {UserService} from '../doctor.service';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {Patient} from '../classes/Patient';
import {ModalDismissReasons, NgbDateStruct, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {NgForm} from '@angular/forms';

@Component({
  selector: 'app-inline-appointment',
  templateUrl: './inline-appointment.component.html',
  styleUrls: ['./inline-appointment.component.css']
})
export class InlineAppointmentComponent implements OnInit {

  patientUser!: Patient[];
  dtOptions: any = {};
  message = 'Hello!';
  nameEvent!: string;
  test1!: any;
  usern = localStorage.getItem("username");
  error: boolean = false;//error=false;
  model!: NgbDateStruct;
  // date!: {year: number, month: number};
  first_name!: string;
  test!: any;
  last_name!: string;
  username!: string;
  dateOfBirth!: string;
  email!: string;
  phone!: string;
  apin1!: any;
  appointment_Id!: string;
  password!: string;
  errorMessage!: string;
  closeModal!: string;
  patient_diagnosed!: string;
  prescriptions_directions!: string;
  time = {hour: 13, minute: 30};
  meridian = true;
  dataPassToChild: any = null;
  hardcoded = [{
    "patientID": 1,
    "username": "lol",
    "firstName": "michail",
    "lastName": "markou",
    "email": "markou@",
    "phone": "123",
    "enabled": true,
    "doctorIs": true
  },
    {
      "patientID": 2,
      "username": "lol1",
      "firstName": "mio",
      "lastName": "markou",
      "email": "markou@",
      "phone": "123",
      "enabled": true,
      "doctorIs": true
    }]
  hardcoded1 = [{
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
  }, {
    "username": "marku",
    "password": "$2a$12$B1THYeVlMFWIDowJ.HCRsu.CvJqhELEFGZOp0zsEDVC/ULjmDRhlS",
    "firstName": "MIONTRAGK",
    "lastName": "MARKOU",
    "email": "backtrackpower@marku.com",
    "phone": "6908990119",
    "city": null,
    "streetAddress": null,
    "dateOfBirth": "2021-06-03",
    "enabled": true,
    "doctorIs": true,
    "patientID": 21,
    "doctor": null,
    "accountNonLocked": true,
    "authorities": [{"authority": "ROLE_DOCTOR"}],
    "credentialsNonExpired": true,
    "accountNonExpired": true
  }]
  popo!: any;
  category!: string;
  testx!: any;
  searchResults!: any;
  timeStored!: string;
  datas!: any[];
  draw!: number;
//dtOptions: DataTables.Settings = {};
  dtOptions1: any = {};
  //data!: any[];
  patientUser1!: Patient[];
  doctorShow!: any;
  patientList!: any[];//any
  myData = {
    name: "Manav",
    qualification: "M.C.A",
    technology: "Angular"
  };

  constructor(private patientService: UserService, private router: Router,
              private http: HttpClient, private modalService: NgbModal) {
  }


  toggleMeridian() {
    console.log("pressed Meridian");
    this.meridian = !this.meridian;
  }

  triggerModal(content: any) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((res) => {
      this.closeModal = `Closed with: ${res}`;
    }, (res) => {
      this.closeModal = `Dismissed ${this.getDismissReason(res)}`;
    });
  }

  resetForm(form?: NgForm) {
    if (form != null)
      form.resetForm();
  }

  formatDate(d: NgbDateStruct): string {
    if (d === null) {
      return 'error';//null
    }

    return [
      d.year,
      (d.month < 10 ? ('0' + d.month) : d.month),
      (d.day < 10 ? ('0' + d.day) : d.day)


    ].join('-');
  }

  onSubmit() {
    console.log("sub");
    this.dateOfBirth = this.formatDate(this.model);
    console.log(this.dateOfBirth);
    var str1 = new String(this.time.hour);
    var str2 = new String(this.time.minute);
    this.timeStored = str1.concat(":", str2.toString());
    console.log(this.timeStored);
    var results = {
      "dateAvailable": this.dateOfBirth,
      "timeAvailable": this.timeStored,
      "doc": {"doctorAccount": localStorage.getItem("username")}
    }//, "doctorAccount":"8"
    console.log(results);
    console.log("ine");
    console.log(localStorage.getItem("username"));
    this.patientService.addDatesAvailable(results).subscribe(res => {
      location.reload();
      console.log(res)
    });

    console.log(JSON.stringify(results));

  }

  addDataAppointment(content: any) {
    const modalRef = this.modalService.open(content, {
      backdropClass: 'light-blue-backdrop',
      centered: true,
      animation: true,
      ariaLabelledBy: 'modal-basic-title'
    });
    modalRef.result.then((result) => {
      console.log(this.last_name);
      console.log(result);
      console.log("trexi");
      //this.onSubmit();
      this.closeModal = `Closed with: ${result}`;
    }, (result) => {
      console.log("this.last_name");
      this.last_name = "";
      console.log(this.last_name);
      this.closeModal = `Dismissed ${this.getDismissReason(result)}`;
    });
  }

  pressedb() {
    console.log("test");
    console.log(this.searchResults);
    $('#showIdButton1').text('Disable');
  }

  getUsers() {

    const that = this;
    let url = "http://localhost:8015/apiDoctor/doctor/all";

    console.log(this.patientUser);
  }

  enableUser(username: string) {
    this.patientService.enableUser(username).subscribe();
    location.reload();
  }

  disableUser(username: string) {
    this.patientService.disableUser(username).subscribe();
    location.reload();
  }

  deleteUser(id: number) {
    this.patientService.deleteUser(id).subscribe();
    location.reload();
    //console.log(id);
  }

  //make custom here
  deleteUserPrompt(name: string, id: any) {
    alert("not yet implemented");
  }

  editUserPrompt(contentDoctor: any, username: string, app_id: string) {
    this.patientService.fetchPatient(username, app_id).subscribe(resp => {
      this.appointment_Id = app_id;
      var diagnosed: any = {};
      diagnosed = resp;
      this.testx = resp;
      console.log("val");
      console.log(diagnosed);
      console.log(diagnosed[0].presription);
      this.patient_diagnosed = diagnosed[0].diagnosed;
      this.prescriptions_directions = diagnosed[0].presription;
      console.log(this.patient_diagnosed);
      const modalRef = this.modalService.open(contentDoctor, {
        backdropClass: 'light-blue-backdrop',
        centered: true,
        animation: true,
        ariaLabelledBy: 'modal-basic-title'
      });
      modalRef.result.then((result) => {
        console.log(this.last_name);
        console.log(result);
        console.log("trexi");
        this.closeModal = `Closed with: ${result}`;
      }, (result) => {
        console.log("this.last_name");
        this.last_name = "";
        this.patient_diagnosed = "";
        this.prescriptions_directions = "";
        console.log(this.last_name);
        this.closeModal = `Dismissed ${this.getDismissReason(result)}`;
      });
    });
  }

  enableDoctor(username: string) {
    this.patientService.enableDoctor(username).subscribe();
    location.reload();
  }

  disableDoctor(username: string) {
    this.patientService.disableDoctor(username).subscribe();
    location.reload();
  }

  getDoctor() {
    this.patientService.getDoctor().subscribe(
      res => {
        //console.log(JSON.parse(JSON.stringify(res)));
        console.log(res);
        this.doctorShow = res;
        console.log(this.doctorShow["test"]);
      },
      error => console.log(error)
    )
  }

  onSubmitEdit() {
    this.patientService.saveEdit(this.patient_diagnosed, this.prescriptions_directions, this.appointment_Id).subscribe();
    console.log("edit");
    this.patient_diagnosed = "";
    this.prescriptions_directions = "";
  }

  ngOnInit(): void {

    this.patientService.getUsers2(localStorage.getItem("username")).subscribe(
      (res: any) => {
        console.log("hi");
        console.log(res);
        this.apin1 = res;
        console.log(this.apin1[0].date);
        //AppointmentInline apin=res
        this.test1 = res;
        console.log(this.test);
        setTimeout(() => {
          $('#userTableD').DataTable({
            pagingType: 'full_numbers',
            pageLength: 5,
            processing: true,
            lengthMenu: [5, 10, 25],
//   "bStateSave": true,
            order: [[1, "desc"]]
          });
        }, 1);

      },
      error => console.log(error)
    )
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

}
