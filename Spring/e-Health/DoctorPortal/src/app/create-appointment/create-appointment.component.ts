import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {UserService} from '../doctor.service';
import {Patient} from '../classes/Patient';
import {HttpClient} from '@angular/common/http';
import {ModalDismissReasons, NgbDateStruct, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {NgForm} from '@angular/forms';

declare let $: any;

@Component({
  selector: 'app-create-appointment',
  templateUrl: './create-appointment.component.html',
  styleUrls: ['./create-appointment.component.css'],
  encapsulation: ViewEncapsulation.None,
  styles: [`
    .dark-modal .modal-content {
      background-color: #292b2c;
      color: white;
    }

    .dark-modal .close {
      color: white;
    }

    .light-blue-backdrop {
      background-color: #5cb3fd;
    }

    .example-form-field {
      margin-right: 20px;
    }
  `]
})

export class CreateAppointmentComponent implements OnInit {
  withAutofocus = `<button type="button" ngbAutofocus class="btn btn-danger"
      (click)="modal.close('Ok click')">Ok</button>`;
  message = 'Hello!';
  test1!: any;
  usern = localStorage.getItem("username");
  error: boolean = false;
  model!: NgbDateStruct;
  first_name!: string;
  last_name!: string;
  username!: string;
  dateOfBirth!: string;
  email!: string;
  phone!: string;
  password!: string;
  errorMessage!: string;
  closeResult!: string;
  closeModal!: string;
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
  test!: string;
  searchResult!: any[];
  searchResults!: any;
  timeStored!: string;
  datas!: any[];
  draw!: number;
  recordsFiltered!: number;
  recordsTotal!: number;
  dtOptions: any = {};
  //data!: any[];
  patientUser!: Patient[];
  patientUserPost!: Patient[];
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
    var str1 = String(this.time.hour);
    var str2 = String(this.time.minute);
    this.timeStored = str1.concat(":", str2.toString());
    console.log(this.timeStored);
    var results = {
      "dateAvailable": this.dateOfBirth,
      "timeAvailable": this.timeStored,
      "doc": {"doctorAccount": localStorage.getItem("username")}
    }
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
  }

  //make custom here
  deleteUserPrompt(name: string, id: any) {
    alert("not yet implemented");
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
        console.log(res);
        this.doctorShow = res;
        console.log(this.doctorShow["test"]);
      },
      error => console.log(error)
    )
  }

  ngOnInit(): void {

    this.patientService.getUsers1(localStorage.getItem("username")).subscribe(
      (res: any) => {
        console.log("hi");
        console.log(res);
        this.test1 = res;
        console.log(this.test);
        setTimeout(() => {
          $('#userTableD').DataTable({
            pagingType: 'full_numbers',
            pageLength: 5,
            processing: true,
            lengthMenu: [5, 10, 25],
            "bStateSave": true,
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
