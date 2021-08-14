import {Component, OnInit, Type, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {UserService} from '../patient.service';
import {Patient} from '../classes/Patient';
import {HttpClient} from '@angular/common/http';
import {ModalDismissReasons, NgbDateStruct, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {NgbdModalConfirmAutofocus} from './user-prompt-del.component';
import {NgForm} from '@angular/forms';
import {Md5} from 'ts-md5';

declare let $: any;

const MODALS: { [name: string]: Type<any> } = {
  autofocus: NgbdModalConfirmAutofocus
};

@Component({
  selector: 'app-user-account',
  templateUrl: './user-account.component.html',
  styleUrls: ['./user-account.component.css'],
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

    /* .btn-outline-secondary{
      content: '\f07a';
      font-family: FontAwesome;
      padding-left: 10px
    } */
  `]
})

export class UserAccountComponent implements OnInit {
  withAutofocus = `<button type="button" ngbAutofocus class="btn btn-danger"
      (click)="modal.close('Ok click')">Ok</button>`;
  message = 'Hello!';
  nameEvent!: string;
  test1!: any;
  error: boolean = false;//error=false;
  model!: NgbDateStruct;
  category_doctor!: string;
  category_doctor1!: string;
  // date!: {year: number, month: number};
  first_name!: string;
  last_name!: string;
  username!: string;
  city!: string;
  dateOfBirth!: string;
  email!: string;
  phone!: string;
  password!: string;
  errorMessage!: string;
  closeResult!: string;
  closeModal!: string;
  usernamepassed!: string;
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
  datas!: any[];
  draw!: number;
  recordsFiltered!: number;
  recordsTotal!: number;
  //dtOptions: DataTables.Settings = {};
  dtOptions: any = {}; //<-no error hitting but above YES say it in STACK OVERFLOW!!!!
  //data!: any[];
  patientUser!: Patient[];
  patientUserPost!: Patient[];
  //doctorShow!: string;
  doctorShow!: any;
  patientList!: any[];//any
  //patientList!: Object[];
  //patientList: any//Object[];
  //patientList1: Object[];
  myData = {
    name: "Manav",
    qualification: "M.C.A",
    technology: "Angular"
  };

  constructor(private patientService: UserService, private router: Router, private http: HttpClient, private modalService: NgbModal) {//, private modalService: NgbModal
    //this.patientList=["username"];
    //this.patientList1=[];
    //this.patientList=[];
    //location.reload();
    //this.getUsers();

    //this.getDoctor();
  }

  open(name: string) {
    this.modalService.open(MODALS[name]);
  }


  triggerModal(content: any) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((res) => {
      this.closeModal = `Closed with: ${res}`;
    }, (res) => {
      this.closeModal = `Dismissed ${this.getDismissReason(res)}`;
    });
  }

  // }
  resetForm(form?: NgForm) {
    if (form != null)
      form.resetForm();

    // this.patientUserPost = {
    // 	username:''
    // }
  }

  // openVerticallyCentered(content: any) {
  // 	    this.modalService.open(content, {backdropClass: 'light-blue-backdrop', centered: true});

  // 	// this.modalService.open(content, { centered: true });
  //   }


  // getUsers() {
  // 	this.patientService.getUsers().subscribe(
  // 		res => {
  //     		//   console.log(this.patientList.map(JSON.parse(JSON.parse(JSON.stringify(res))._body)));
  // 			//  this.myData = JSON.parse(JSON.parse(JSON.stringify(res))._body);
  // 			//  console.log(this.myData);
  // 			//  console.log("data");

  // 			   console.log(JSON.parse(JSON.stringify(res)));
  // 			//  this.patientList1 = JSON.parse(JSON.parse(JSON.stringify(res))._body);
  // 			this.patientList = JSON.parse(JSON.stringify(res));
  // 			console.log(this.patientList[0]);
  // 			this.patientUser = JSON.parse(JSON.stringify(res));
  //   		},
  //   		error => console.log(error)
  // 	)

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

  onSubmitDoctor() {
    this.patientService.enableDoctor(this.usernamepassed, this.category_doctor).subscribe();
    this.usernamepassed = '';
    this.city = '';
    location.reload();
  }

  onSubmit() {
    ////console.log("hereEkso");
    ////let ngbDate = this.model;
    ////console.log(ngbDate);
    ////let myDate = new Date(ngbDate.year, ngbDate.month-1, ngbDate.day);
    // let formValues = this.form.value;
    ////this.formatDate(this.model);
    ////console.log(this.formatDate(this.model));
    ////var year=this.model.year;//.toString deserialeze problem uknown!
    //var month=this.model.month;
    //var day=this.model.day;
    ////var month=Number('06');
    ////var day=Number('03');
    ////console.log(day);
    //var date
    //const md5 = new Md5();
    this.password = Md5.hashAsciiStr(this.password);
    console.log("hashed");
    ////this.dateOfBirth=year+'-'+month+'-'+day;//oxi += sketo = giati allios undefined undefined2021-6-32021-6-3
    this.dateOfBirth = this.formatDate(this.model);
    ////console.log("dateOfBirth");
    ////console.log(this.dateOfBirth);
    ////console.log(this.password);
    var results = new Patient(this.username, this.first_name, this.last_name, this.email, this.phone, this.dateOfBirth, this.password, this.city);
    console.log(results);
    //let url = "http://localhost:8015/api/patient/set";

    if (this.nameEvent == "Patient") {
      console.log("ine");
      console.log(this.nameEvent);
      this.patientService.addPatient(results).subscribe(res => {
        location.reload();
        console.log(res)
      });
    } else if (this.nameEvent == "Doctor") {
      console.log("ine");
      console.log(this.nameEvent);
      this.patientService.addDoctor(results).subscribe(res => {
        location.reload();
        console.log(res)
      });
    }


    //  this.http.post<Patient>(url, JSON.stringify(results), { withCredentials: true });
    console.log(JSON.stringify(results));

    //var patientN: Patient;
    //this.patientService.save(patientN)
    // let res = (results as Patient[]).map((profile) => new Patient(profile.name, profile.email, profile.age));
    // (results: any) => {
    // 	console.log("here");
    // 	let res = (results as Patient[]).map((profile) => new Patient(this.username, this.first_name, this.last_name, this.email, this.phone, this.dateOfBirth, this.password));
    // 	// new Patient(profile.username, profile.firstName, profile.lastName, profile.email, profile.phone, profile.dateOfBirth, profile.password));
    // 	//this.results = this.results.concat(res);
    // 	console.log(results);
    // }
  }

  onSubmitP() {

    this.password = Md5.hashAsciiStr(this.password);
    console.log("hashed");
    this.dateOfBirth = this.formatDate(this.model);

    var results = new Patient(this.username, this.first_name, this.last_name, this.email, this.phone, this.dateOfBirth, this.password, this.city);
    console.log(results);
    if (this.nameEvent == "Patient") {
      console.log("ine");
      console.log(this.nameEvent);
      this.patientService.addPatient(results).subscribe(res => {
        this.city = '';
        location.reload();
        console.log(res)
      });
    } else if (this.nameEvent == "Doctor") {
      console.log("ine");
      console.log(this.nameEvent);
      this.patientService.addDoctor(results).subscribe(res => {
        location.reload();
        console.log(res)
      });
    }
    console.log(JSON.stringify(results));
  }

  onSubmitD() {

    this.password = Md5.hashAsciiStr(this.password);
    console.log("hashed");
    this.dateOfBirth = this.formatDate(this.model);

    //var results = new Patient(this.username, this.first_name, this.last_name, this.email, this.phone, this.dateOfBirth, this.password, this.city);
    var results = {
      "username": this.username,
      "firstName": this.first_name,
      "lastName": this.last_name,
      "email": this.email,
      "phone": this.phone,
      "dateOfBirth": this.dateOfBirth,
      "password": this.password,
      "city": this.city,
      "category": {"categoryDoctor": this.category_doctor1}
    }
    console.log(results);
    console.log("ine");
    console.log(this.nameEvent);
    this.patientService.addDoctor(results).subscribe(res => {
      location.reload();
      console.log(res)
    });

    console.log(JSON.stringify(results));
  }

  addDataPatient(contentPatient: any) {
    this.nameEvent = "Patient";
    const modalRef = this.modalService.open(contentPatient, {
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

  addDataDoctor(contentDoctor: any) {
    this.nameEvent = "Doctor";
    const modalRef = this.modalService.open(contentDoctor, {backdropClass: 'light-blue-backdrop', centered: true});
    // if (modalRef.dismissed){
    // 	console.log("closed");
    // }
    modalRef.result.then((result) => {
      console.log(this.last_name);
      console.log(result);
      console.log("trexi");
      //this.onSubmit();
      this.closeModal = `Closed with: ${result}`;
    }, (res) => {
      this.last_name = "";
      console.log("hased message");
      console.log(this.last_name);
      this.closeModal = `Dismissed ${this.getDismissReason(res)}`;
    });
  }

  pressedb() {
    console.log("test");
    //this.patientUser[1].doctorIs;
    console.log(this.searchResults);
    $('#showIdButton1').text('Disable');
  }

  getUsers() {

    const that = this;
    let url = "http://localhost:8015/api/patient/all";
    // this.dtOptions = {

    // 	// Declare the use of the extension in the dom parameter
    // 	columns: [{
    // 		title: 'ID',
    // 		data: 'patientID'
    // 	  }, {
    // 		title: 'First name',
    // 		data: 'firstName'
    // 	  }, {
    // 		title: 'Last name',
    // 		data: 'lastName'
    // 	  }],
    // 	dom: 'Bfrtip',
    // 	// Configure the buttons
    // 	buttons: [
    // 	  'columnsToggle',
    // 	  'colvis',
    // 	  'copy',
    // 	  'print',
    // 	  'excel'
    // 	]
    //   };

    // // const that = this;
    // // let url = "http://localhost:8015/api/patient/all";
    // // this.dtOptions = {
    // // 	ajax: () => {
    // // 	  that.http
    // // 		.get<UserAccountComponent>(
    // // 			url,
    // // 		   {withCredentials: true}//.toPromise.then()
    // // 		).subscribe(resp => {
    // // 			console.log("show");
    // // 			this.test=JSON.stringify(resp);
    // // 			console.log(this.test);
    // // 		  that.patientUser = JSON.parse(JSON.stringify(resp));
    // // 		  console.log(this.patientUser);
    // // 		  this.test=String(this.patientUser[0].patientID);
    // // 		  console.log(this.test);
    // // 		});console.log("popo");console.log(this.popo);
    // // 	},
    // // 	// Declare the use of the extension in the dom parameter
    // // 	// columns: [{
    // // 	// 	title: 'ID',
    // // 	// 	data: 'patientID'
    // // 	//   }, {
    // // 	// 	title: 'First name',
    // // 	// 	data: 'firstName'
    // // 	//   }, {
    // // 	// 	title: 'Last name',
    // // 	// 	data: 'lastName'
    // // 	//   }],
    // // 	dom: 'Bfrtip',
    // // 	// Configure the buttons
    // // 	buttons: [
    // // 	  'columnsToggle',
    // // 	  'colvis',
    // // 	  'copy',
    // // 	  'print',
    // // 	  'excel'
    // // 	]
    // //   };


    // const that = this;
    // let url = "http://localhost:8015/api/patient/all";
    // this.dtOptions = {
    // 	//pagingType: 'full_numbers',
    // 	// pageLength: 2,
    // 	// serverSide: true,
    // 	// processing: true,
    // 	ajax: (dataTablesParameters: any, callback: any) => {
    // 	  that.http
    // 		.get<UserAccountComponent>(
    // 			url,
    // 		   {withCredentials: true}
    // 		).subscribe(resp => {
    // 			console.log("show");
    // 			//console.log(resp.data);
    // 			console.log(resp);
    // 		  that.patientUser = JSON.parse(JSON.stringify(resp));//resp.data;//data;

    // 		  callback({
    // 			recordsTotal: resp.recordsTotal,
    // 			recordsFiltered: resp.recordsFiltered,
    // 			data: []
    // 		  },console.log(this.recordsTotal),
    // 		  console.log(this.recordsFiltered),console.log(this.data));
    // 		});
    // 	},
    // 	columns: [{ data: 'patientID' }, { data: 'username' }, { data: 'firstName' }, { data: 'lastName' }, { data: 'email' }, { data: 'phone' }, { data: 'enabled' }, { data: 'doctorIs' }],
    // 	// Declare the use of the extension in the dom parameter
    // 	dom: 'Bfrtip',
    // 	// Configure the buttons
    // 	buttons: [
    // 	  'columnsToggle',
    // 	  'colvis',
    // 	  'copy',
    // 	  'print',
    // 	  'excel'
    // 	]
    //   };


    //   const that = this;
    // let url = "http://localhost:8015/api/patient/all";
    // this.dtOptions = {
    // 	pagingType: 'full_numbers',
    // 	// pageLength: 2,
    // 	// serverSide: true,
    // 	// processing: true,
    // 	ajax: (dataTablesParameters: any, callback: any) => {
    // 	  that.http
    // 		.post<UserAccountComponent>(
    // 			url,dataTablesParameters,
    // 		   {withCredentials: true}
    // 		).subscribe(resp => {
    // 			console.log("show");
    // 			//console.log(resp.data);
    // 			console.log(resp);
    // 		  that.patientUser = JSON.parse(JSON.stringify(resp));//resp.data;//data;

    // 		  callback({
    // 			recordsTotal: resp.recordsTotal,
    // 			recordsFiltered: resp.recordsFiltered,
    // 			data: []
    // 		  },console.log(this.recordsTotal),
    // 		  console.log(this.recordsFiltered),console.log(this.data));
    // 		});
    // 	},
    // 	columns: [{ data: 'patientID' }, { data: 'username' }, { data: 'firstName' }, { data: 'lastName' }, { data: 'email' }, { data: 'phone' }, { data: 'enabled' }, { data: 'doctorIs' }],
    // 	// Declare the use of the extension in the dom parameter
    // 	dom: 'Bfrtip',
    // 	// Configure the buttons
    // 	// buttons: [
    // 	//   'columnsToggle',
    // 	//   'colvis',
    // 	//   'copy',
    // 	//   'print',
    // 	//   'excel'
    // 	// ]
    //   };


    //   const that = this;
    // let url = "http://localhost:8015/api/patient/all";
    // this.dtOptions = {
    // 	pagingType: 'full_numbers',
    // 	pageLength: 2,
    // 	serverSide: true,
    // 	processing: true,
    // 	ajax: (dataTablesParameters: any, callback: any) => {
    // 	  that.http
    // 		.post<UserAccountComponent>(
    // 			url,
    // 		  dataTablesParameters, {withCredentials: true}
    // 		).subscribe(resp => {
    // 			console.log("show");
    // 			console.log(resp.data);
    // 		  that.patientUser = resp.data;

    // 		  callback({
    // 			recordsTotal: resp.recordsTotal,
    // 			recordsFiltered: resp.recordsFiltered,
    // 			data: []
    // 		  });
    // 		});
    // 	},
    // 	columns: [{ data: 'patientID' }, { data: 'username' }, { data: 'firstName' }, { data: 'lastName' }, { data: 'email' }, { data: 'phone' }, { data: 'enabled' }, { data: 'doctorIs' }]
    //   };


    // const that=this
    // this.dtOptions.ajax = (dataTablesParameters: any, callback: any) => {
    // 	that.patientService.getUsers().subscribe(res => {
    // 		this.patientUser = JSON.parse(JSON.stringify(res));
    // 		callback(console.log("callback"),{
    // 			columns: [{
    // 				title: 'ID',
    // 				data: 'patientID'
    // 			  }, {
    // 				title: 'First name',
    // 				data: 'firstName'
    // 			  }, {
    // 				title: 'Last name',
    // 				data: 'lastName'
    // 			  }, {
    // 				  title: 'e-mail',
    // 				  data: 'email'
    // 				}, {
    // 				  title: 'Phone',
    // 				  data: 'phone'
    // 				}, {
    // 				  title: 'isActivated',
    // 				  data: 'enabled'
    // 				}, {
    // 				  title: 'isDoctor',
    // 				  data: 'doctorIs'
    // 				}],
    // 			  // Declare the use of the extension in the dom parameter
    // 			  dom: 'Bfrtip',
    // 			  //Configure the buttons
    // 			  buttons: [
    // 				  'columnsToggle',
    // 				  'colvis',
    // 				  'copy',
    // 				  'print',
    // 				  'excel',
    // 			  ]
    // 		});
    // 	  },
    // 	  	error => console.log(error));
    //   };

//THIS WORKS DOWN UNCOMENTED IT !!!!!
// 		this.patientService.getUsers().subscribe(
// 			(res: any) => {//or [] if array of obj or obj simple
//         		//   console.log(this.patientList.map(JSON.parse(JSON.parse(JSON.stringify(res))._body)));
// 				//  this.myData = JSON.parse(JSON.parse(JSON.stringify(res))._body);
// 				//  console.log(this.myData);
// 				//  console.log("data");
// this.test1=res;
// console.log(this.test);
// setTimeout(()=>{
// 	$('#userTable').DataTable( {
// 	  pagingType: 'full_numbers',
// 	  pageLength: 5,
// 	  processing: true,
// 	  lengthMenu : [5, 10, 25],
// 	  order:[[1,"desc"]]
//   } );
//   }, 1);
// 				   console.log(JSON.parse(JSON.stringify(res)));
// 				//  this.patientList1 = JSON.parse(JSON.parse(JSON.stringify(res))._body);
// 				this.patientList = JSON.parse(JSON.stringify(res));
// 				console.log(this.patientList[0]);
// 				this.patientUser = JSON.parse(JSON.stringify(res));

//       		},
//       		error => console.log(error)
// 		)
    // this.dtOptions = { pagingType: 'full_numbers', pageLength: 3, processing: true, dom: 'Bfrtip', buttons: [ 'copy', 'csv', 'excel', 'print', 'colvis' ] };


    console.log(this.patientUser);
  }

  enableUser(username: string) {
    this.patientService.enableUser(username).subscribe();
    location.reload();
  }

  // onSelectPrimary(username: string) {
  // 	this.router.navigate(['/medicationAccount', username]);
  // }

  disableUser(username: string) {
    this.patientService.disableUser(username).subscribe();
    location.reload();
  }

  deleteUser(id: number) {
    this.patientService.deleteUser(id).subscribe();
    location.reload();
    //console.log(id);
  }

  deleteUserPrompt(name: string, id: any) {
    const modalRef = this.modalService.open(MODALS[name], {backdropClass: 'light-blue-backdrop'});
    (<NgbdModalConfirmAutofocus>modalRef.componentInstance).dataToTakeAsInput = id;
    modalRef.result.then((result) => {
      console.log(result);
      console.log(id.firstName);
      this.deleteUser(id.patientID);
    }).catch((result) => {
      console.log(result);
    });

    //this.deleteUser(id);
    //modalRef.componentInstance.id = id;
    //this.activeModal.close(id);
  }

  enableDoctor(contentDoctor: any, username: string) {
    this.usernamepassed = username;
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
      //this.onSubmit();
      this.closeModal = `Closed with: ${result}`;
    }, (result) => {
      console.log("this.last_name");
      this.last_name = "";
      console.log(this.last_name);
      this.closeModal = `Dismissed ${this.getDismissReason(result)}`;
    });
    // this.patientService.enableDoctor(username).subscribe();
    // location.reload();
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
        //this.doctorShow=res.toString();
        this.doctorShow = res;
        console.log(this.doctorShow["test"]);
      },
      error => console.log(error)
    )
  }

  ngOnInit(): void {
    //const that = this;
    // this.dtOptions.ajax = {
    // 	ajax: '',
    // 	columns: [{
    // 	  title: 'ID',
    // 	  data: 'patientID'
    // 	}, {
    // 	  title: 'First name',
    // 	  data: 'firstName'
    // 	}, {
    // 	  title: 'Last name',
    // 	  data: 'lastName'
    // 	}, {
    // 		title: 'e-mail',
    // 		data: 'email'
    // 	  }, {
    // 		title: 'Phone',
    // 		data: 'phone'
    // 	  }, {
    // 		title: 'isActivated',
    // 		data: 'enabled'
    // 	  }, {
    // 		title: 'isDoctor',
    // 		data: 'doctorIs'
    // 	  }],
    // 	// Declare the use of the extension in the dom parameter
    // 	dom: 'Bfrtip',
    // 	//Configure the buttons
    // 	// buttons: [
    // 	// 	'columnsToggle',
    // 	// 	'colvis',
    // 	// 	'copy',
    // 	// 	'print',
    // 	// 	'excel',
    // 	// ]
    //   };
    // //const that = this;
    // 	this.dtOptions.ajax = (dataTablesParameters: any, callback: any) => {
    // 	that.patientService.getUsers().subscribe(resp => {
    // 		console.log(resp);
    // 		console.log("howdy");
    // 		callback({
    // 			columns: [{
    // 				title: 'ID',
    // 				data: 'patientID'
    // 			  }, {
    // 				title: 'First name',
    // 				data: 'firstName'
    // 			  }, {
    // 				title: 'Last name',
    // 				data: 'lastName'
    // 			  }, {
    // 				  title: 'e-mail',
    // 				  data: 'email'
    // 				}, {
    // 				  title: 'Phone',
    // 				  data: 'phone'
    // 				}, {
    // 				  title: 'isActivated',
    // 				  data: 'enabled'
    // 				}, {
    // 				  title: 'isDoctor',
    // 				  data: 'doctorIs'
    // 				}],
    // 			  // Declare the use of the extension in the dom parameter
    // 			  dom: 'Bfrtip',
    // 			  //Configure the buttons
    // 			  buttons: [
    // 			  	'columnsToggle',
    // 			  	'colvis',
    // 			  	'copy',
    // 			  	'print',
    // 			  	'excel',
    // 			  ]
    // 		});
    // 	  });
    //   };

    // this.dtOptions= {
    // 		//ajax: '',
    // 		columns: [{
    // 		  title: 'ID',
    // 		  data: 'patientID'
    // 		}, {
    // 		  title: 'First name',
    // 		  data: 'firstName'
    // 		}, {
    // 		  title: 'Last name',
    // 		  data: 'lastName'
    // 		}, {
    // 			title: 'e-mail',
    // 			data: 'email'
    // 		  }, {
    // 			title: 'Phone',
    // 			data: 'phone'
    // 		  }, {
    // 			title: 'isActivated',
    // 			data: 'enabled'
    // 		  }, {
    // 			title: 'isDoctor',
    // 			data: 'doctorIs'
    // 		  }],
    // 		// Declare the use of the extension in the dom parameter
    // 		dom: 'Bfrtip',
    // 		//Configure the buttons
    // 		buttons: [
    // 			'columnsToggle',
    // 			'colvis',
    // 			'copy',
    // 			'print',
    // 			'excel',
    // 		]
    // 	  };
    // const that = this;
    // let url = "http://localhost:8015/api/patient/all";
    // this.dtOptions = {
    // 	pagingType: 'full_numbers',
    // 	pageLength: 2,
    // 	serverSide: true,
    // 	processing: true,
    // 	ajax: (dataTablesParameters: any, callback: any) => {
    // 	  that.http
    // 		.post<UserAccountComponent>(
    // 			url,
    // 		  dataTablesParameters, {withCredentials: true}
    // 		).subscribe(resp => {
    // 			console.log("show");
    // 			console.log(resp.data);
    // 		  that.patientUser = resp.data;

    // 		  callback({
    // 			recordsTotal: resp.recordsTotal,
    // 			recordsFiltered: resp.recordsFiltered,
    // 			data: []
    // 		  });
    // 		});
    // 	},
    // 	columns: [{ data: 'patientID' }, { data: 'username' }, { data: 'firstName' }, { data: 'lastName' }, { data: 'email' }, { data: 'phone' }, { data: 'enabled' }, { data: 'doctorIs' }]
    //   };


    // const that = this;
    // let url = "http://localhost:8015/api/patient/all";
    // this.dtOptions = {
    // 	serverSide: false,
    //  	processing: false,
    // 	ajax: () => {
    // 	  this.popo= that.http
    // 		.get<UserAccountComponent>(
    // 			url,
    // 		   {withCredentials: true}
    // 		).subscribe(resp => {
    // 			console.log("show");
    // 			this.test=JSON.stringify(resp);
    // 			console.log(this.test);
    // 		  that.patientUser = JSON.parse(JSON.stringify(resp));
    // 		  console.log(this.patientUser);
    // 		  this.test=String(this.patientUser[0].patientID);
    // 		  console.log(this.test);
    // 		});console.log("popo");console.log(this.popo);
    // 	},
    // 	// Declare the use of the extension in the dom parameter
    // 	dom: 'Bfrtip',
    // 	// Configure the buttons
    // 	// buttons: [
    // 	//   'columnsToggle',
    // 	//   'colvis',
    // 	//   'copy',
    // 	//   'print',
    // 	//   'excel'
    // 	// ]
    //   };


    //   const that = this;
    let url = "http://localhost:8015/api/patient/all";
    // this.dtOptions = {
    // 	//pagingType: 'full_numbers',
    // 	// pageLength: 2,
    // 	// serverSide: true,
    // 	// processing: true,
    // 	ajax: () => {
    // 	  that.http
    // 		.get<UserAccountComponent>(
    // 			url,
    // 		   {withCredentials: true}
    // 		).subscribe(resp => {
    // 			console.log("show");
    // 			//console.log(resp.data);
    // 			console.log(resp);
    // 			//that.patientUser = resp.data;
    // 			console.log("resp");
    // 			//console.log(resp.data);
    // 			this.test=JSON.stringify(resp);
    // 		  that.patientUser = JSON.parse(JSON.stringify(resp));//resp.data;//data;
    // 		  console.log(this.datas);
    // 		  console.log((this.patientUser[0].patientID).toString);
    // 		  this.test=String(this.patientUser[0].patientID);
    // 		  console.log(this.test);
    // 		  console.log([{data: 'patientID'}]);
    // 		//   callback({
    // 		// 	// recordsTotal: resp.recordsTotal,
    // 		// 	// recordsFiltered: resp.recordsFiltered,
    // 		// 	datas: []
    // 		//   },console.log(this.recordsTotal),
    // 		//   console.log(this.recordsFiltered),console.log(this.datas));
    // 		});
    // 	},
    // 	columns: [{ title: 'ID', data: 'patientID' }, {title: 'username', data: 'username' }, {title: 'firstName', data: 'firstName' }, {title: 'lastName', resp: 'lastName' }],
    // 	// Declare the use of the extension in the dom parameter
    // 	dom: 'Bfrtip',
    // 	// Configure the buttons
    // 	// buttons: [
    // 	//   'columnsToggle',
    // 	//   'colvis',
    // 	//   'copy',
    // 	//   'print',
    // 	//   'excel'
    // 	// ]
    //   };


    // () => {
    // 	///////////////
    // 	this.http
    // 	  .get<UserAccountComponent>(
    // 		  url,
    // 		 {withCredentials: true}//.toPromise.then()
    // 	  ).subscribe(resp => {
    // 		  console.log("show");
    // 		  this.test=JSON.stringify(resp);
    // 		  console.log(this.test);
    // 		this.patientUser = JSON.parse(JSON.stringify(resp));
    // 		console.log(this.patientUser);
    // 		this.test=String(this.patientUser[0].patientID);
    // 		console.log(this.test);
    // 	  });
    //   },


    // ajax: ()=> {  this.http.get(url, { withCredentials: true }).subscribe(posts => {
    // 	this.patientUser=JSON.parse(JSON.stringify(posts));
    // })}
    // ,


    // ( ()=> this.http.get(url, { withCredentials: true }).subscribe(posts => {
    // 	this.patientUser=JSON.parse(JSON.stringify(posts));
    // }))

    // ajax: 'http://localhost:8015/api/patient/all'
    // 	,
    // 	xhrFields: {
    // 		withCredentials: true
    // 	 },

    // this.dtOptions={
    // 	"ajax": ({
    // 		"url": url,
    // 		"dataSrc": "",
    // 		xhrFields: {
    // 			withCredentials: true
    // 		}})
    // 	,
    // 	columns: [
    // 		{
    // 		title: 'ID',
    // 		data: 'patientID'
    // 		},
    // 		{
    // 			title: 'User Name',
    // 			data: 'username'
    // 		},
    // 		{
    // 			title: 'User Name',
    // 			data: 'doctorIs'
    // 		},
    // 	],
    // 	  /////////////////delete this areas if not work
    // 	pagingType: 'full_numbers', pageLength: 3, processing: true, dom: 'Bfrtip', buttons: [ 'copy', 'csv', 'excel', 'print', 'colvis' ] };
    // 	console.log('this.patientUser');
    // 	console.log(this.patientUser);
    // 	console.log(this.hardcoded1);
    // this.http.get(url, { withCredentials: true }).subscribe(posts => {
    // 	this.patientUser=JSON.parse(JSON.stringify(posts));
    // });

    // this.dtOptions.ajax = (callback: any) => {
    // 		this.patientService.getUsers().subscribe(resp => {
    // 			console.log(resp);
    // 			console.log("howdy");
    // 			callback({
    // 				columns: [{
    // 					title: 'ID',
    // 					data: 'patientID'
    // 				  }, {
    // 					title: 'First name',
    // 					data: 'firstName'
    // 				  }, {
    // 					title: 'Last name',
    // 					data: 'lastName'
    // 				  }, {
    // 					  title: 'e-mail',
    // 					  data: 'email'
    // 					}, {
    // 					  title: 'Phone',
    // 					  data: 'phone'
    // 					}, {
    // 					  title: 'isActivated',
    // 					  data: 'enabled'
    // 					}, {
    // 					  title: 'isDoctor',
    // 					  data: 'doctorIs'
    // 					}],
    // 				  // Declare the use of the extension in the dom parameter
    // 				  dom: 'Bfrtip',
    // 				  //Configure the buttons
    // 				  buttons: [
    // 				  	'columnsToggle',
    // 				  	'colvis',
    // 				  	'copy',
    // 				  	'print',
    // 				  	'excel',
    // 				  ]
    // 			});
    // 		  });
    // 	  };


// // apo edw ola mexri katw comment out it works good
// 	const _currClassRef = this;//var
// 	$('#datatables tbody td').unbind();

// 	var table=this.dtOptions={
// 		"ajax": ({
// 			"url": url,
// 			// "dataSrc": function(json: any){_currClassRef.searchResults = json;console.log('json');console.log(json);return json},
// 			// "dataSrc": "",
// 			"dataSrc":(res: any)=> {console.log(res);this.searchResults = res; console.log(this.searchResults); return res},
// 			xhrFields: {
// 				withCredentials: true
// 			},
// 			// "success": (res: any)=> {this.searchResult = res;},
// 		})
// 		,
// 		columns: [
// 			{
// 				title: 'User Name',
// 				data: 'username'
// 			},
// 			{
// 				title: 'First Name',
// 				data: 'firstName'
// 			},
// 			{
// 				title: 'Last Name',
// 				data: 'lastName'
// 				},
// 			{
// 				title: 'EnabledAcc',
// 				defaultContent: "<button class='showIdButton' id='showIdButton'>alert me</button>"

// 			},
// 			{
// 				title: 'pressedb',
// 				defaultContent: "<td [hidden]='patient.doctorIs'><a id='showIdButton1' style='cursor: pointer;'>Enable</a></td>"

// 			},
// 			{
// 				title: 'pressedbHidden',
// 				defaultContent: "<td [hidden]='!patient.doctorIs'><a (click)='disableDoctor(patient.username)' style='cursor: pointer;'>Disable</a></td>"

// 			},
// 			{
// 				title: 'isDoctor',
// 				defaultContent: "<td [hidden]=\"patient.doctorIs\"><a (click)=\"enableDoctor(patient.username)\" style=\"cursor: pointer;\">Enable</a></td>"

// 			},
// 		],
// 		  /////////////////delete this areas if not work
// 		pagingType: 'full_numbers', pageLength: 3, processing: true, dom: 'Bfrtip', buttons: [ 'copy', 'csv', 'excel', 'print', 'colvis' ] };
// 		console.log('this.patientUser');
// 		console.log(this.patientUser);
// 		console.log(this.hardcoded1);
// 		console.log(table);
// 		console.log("searchRes");
// 		console.log(_currClassRef.searchResults);
// 		console.log(this.searchResults);
// 		$(document).on('click', '#showIdButton1', function(){
// 			alert("button is clicked");
// 			_currClassRef.pressedb();
// 			console.log(this.searchResults);
// 		});


    // apo edw ola mexri katw comment out it works good
    // const _currClassRef = this;//var
    // $('#datatables tbody td').unbind();

    // var table=this.dtOptions={
    // 	"ajax": ({
    // 		"url": url,
    // 		// "dataSrc": function(json: any){_currClassRef.searchResults = json;console.log('json');console.log(json);return json},
    // 		// "dataSrc": "",
    // 		"dataSrc":(res: any)=> {console.log(res);this.searchResults = res; console.log(this.searchResults); return res},
    // 		xhrFields: {
    // 			withCredentials: true
    // 		},
    // 		// "success": (res: any)=> {this.searchResult = res;},
    // 	})
    // 	,
    // 	columns: [
    // 		{
    // 			title: 'User Name',
    // 			data: 'username',
    // 			defaultContent: '-'
    // 		},
    // 		{
    // 			title: 'First Name',
    // 			data: 'firstName',
    // 			defaultContent: '-'
    // 		},
    // 		{
    // 			title: 'Last Name',
    // 			data: 'lastName',
    // 			defaultContent: '-'
    // 			},
    // 		// {
    // 		// 	title: 'EnabledAcc',
    // 		// 	defaultContent: "<button class='showIdButton' id='showIdButton'>alert me</button>"

    // 		// },
    // 		// {
    // 		// 	title: 'pressedb',
    // 		// 	defaultContent: "<td [hidden]='patient.doctorIs'><a id='showIdButton1' style='cursor: pointer;'class=\"text-primary\">Enable</a></td>"

    // 		// },
    // 		// {
    // 		// 	title: 'pressedbHidden',
    // 		// 	defaultContent: "<td [hidden]='!patient.doctorIs'><a (click)='disableDoctor(patient.username)' style='cursor: pointer;'class=\"text-primary\">Disable</a></td>"

    // 		// },
    // 		// {
    // 		// 	title: 'isDoctor',
    // 		// 	defaultContent: "<td [hidden]=\"patient.doctorIs\"><a (click)=\"enableDoctor(patient.username)\" style=\"cursor: pointer;\" class=\"text-primary\">Enable</a></td>"

    // 		// },
    // 		// {
    // 		// 	title: 'DeleteAcc',
    // 		// 	defaultContent: "<td ><a (click)=\"deleteUser(patient.patientID)\" style=\"cursor: pointer;\"class=\"text-primary\">Delete</a></td>"

    // 		// },
    // 		// {
    // 		// 	render: "<td></td>"
    // 		// }
    // 	],
    // 	  /////////////////delete this areas if not work
    // 	pagingType: 'full_numbers', pageLength: 3, processing: true, dom: 'Bfrtip', buttons: [ 'copy', 'csv', 'excel', 'print', 'colvis' ] };
    // 	console.log('this.patientUser');
    // 	console.log(this.patientUser);
    // 	console.log(this.hardcoded1);
    // 	console.log(table);
    // 	console.log("searchRes");
    // 	console.log(_currClassRef.searchResults);
    // 	console.log(this.searchResults);
    // 	$(document).on('click', '#showIdButton1', function(){
    // 		alert("button is clicked");
    // 		_currClassRef.pressedb();
    // 		console.log(this.searchResults);
    // 	});
    //location.reload();
    //this.getUsers();

    this.patientService.getUsers1().subscribe(
      (res: any) => {//or [] if array of obj or obj simple
        //   console.log(this.patientList.map(JSON.parse(JSON.parse(JSON.stringify(res))._body)));
        //  this.myData = JSON.parse(JSON.parse(JSON.stringify(res))._body);
        //  console.log(this.myData);
        //  console.log("data");
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
        //    console.log(JSON.parse(JSON.stringify(res)));
        // //  this.patientList1 = JSON.parse(JSON.parse(JSON.stringify(res))._body);
        // this.patientList = JSON.parse(JSON.stringify(res));
        // console.log(this.patientList[0]);
        // this.patientUser = JSON.parse(JSON.stringify(res));

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
