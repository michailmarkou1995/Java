import { Component, OnInit, ViewEncapsulation, Type, Input, ViewChild, AfterViewInit  } from '@angular/core';
import { Router } from '@angular/router';
import {UserService} from '../patient.service';
import { map } from 'rxjs-compat/operator/map';
import { Patient } from '../classes/Patient';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { ModalDismissReasons, NgbModal, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { UserAccountComponent } from './user-account.component';

@Component({
    //providers:[UserAccountComponent],
	selector: 'ngbd-modal-confirm-autofocus',
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
    `],
	template: `
	<div class="modal-header">
	  <h4 class="modal-title" id="modal-title">Profile deletion</h4>
	  <button type="button" class="close" aria-label="Close button" aria-describedby="modal-title" (click)="modal.dismiss('Cross click')">
		<span aria-hidden="true">&times;</span>
	  </button>
	</div>
	<div class="modal-body">
	  <p><strong>Are you sure you want to delete <span class="text-primary">"{{dataToTakeAsInput.firstName}}"'s</span> profile?</strong></p>
	  <p>All information associated to this user profile will be permanently deleted.
	  <span class="text-danger">This operation can not be undone.</span>
	  </p>
	</div>
	<div class="modal-footer">
	  <button type="button" class="btn btn-outline-secondary" (click)="modal.dismiss('cancel click')">Cancel</button>
	  <button type="button" ngbAutofocus class="btn btn-danger" (click)="modal.close('Ok click')">Delete</button>
	</div>
	`
  })
export class NgbdModalConfirmAutofocus {
        firstName!: string;
        patientUser!: any[];
        @Input() dataToTakeAsInput: any;
    	constructor(public modal: NgbActiveModal) {}

  }