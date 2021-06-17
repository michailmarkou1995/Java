import { Component, OnInit } from '@angular/core';
import { UserService } from '../patient.service';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { Patient } from '../classes/Patient';

@Component({
  selector: 'app-save-export',
  templateUrl: './save-export.component.html',
  styleUrls: ['./save-export.component.css']
})
export class SaveExportComponent implements OnInit {

  patientUser!: Patient[];
  dtOptions: any = {};

  constructor(private patientService: UserService, private router: Router,private http:HttpClient) { }

  ngOnInit(): void {
    
  let url = "http://localhost:8015/api/patient/all";
	this.dtOptions={
		"ajax": ({
			"url": url,
			"dataSrc": "",
			xhrFields: {
				withCredentials: true
			}})
		,
		columns: [
			{
			title: 'ID',
			data: 'patientID'
			},
			{
				title: 'User Name',
				data: 'username'
			},
			{
				title: 'First Name',
				data: 'firstName'
			},
      {
				title: 'Last Name',
				data: 'lastName'
			},
      {
				title: 'email',
				data: 'email'
			},
      {
				title: 'phone',
				data: 'phone'
			},
      {
				title: 'enabledAcc',
				data: 'enabled'
			},
      {
				title: 'isDoctor',
				data: 'doctorIs'
			},
		],
		pagingType: 'full_numbers', "bStateSave": true, pageLength: 10, processing: true, dom: 'lBfrtip', buttons: [ 'copy', 'csv', 'excel', 'print', 'colvis' ] };
		console.log('this.patientUser');
		console.log(this.patientUser);
  }

}
