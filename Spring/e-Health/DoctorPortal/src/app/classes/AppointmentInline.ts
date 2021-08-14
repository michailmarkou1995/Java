export class AppointmentInline {
  date: string;
  time: string;
  treatmentguide_id: string;
  email: string;
  medication_account_id: string;
  username: string;
  phone: string;

  constructor(date: string, time: string, treatmentguide_id: string, email: string, medication_account_id: string, username: string, phone: string) {
    this.date = date;
    this.time = time;
    this.treatmentguide_id = treatmentguide_id;
    this.email = email;
    this.medication_account_id = medication_account_id;
    this.username = username;
    this.phone = phone;
  }
}
