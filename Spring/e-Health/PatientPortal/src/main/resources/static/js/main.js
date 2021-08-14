/**
 * Created by z00382545 on 10/20/16.
 */

(function ($) {
    $.toggleShowPassword = function (options) {
        var settings = $.extend({
            field: "#password",
            control: "#toggle_show_password",
        }, options);

        var control = $(settings.control);
        var field = $(settings.field)

        control.bind('click', function () {
            if (control.is(':checked')) {
                field.attr('type', 'text');
            } else {
                field.attr('type', 'password');
            }
        })
    };

    $.transferDisplay = function () {
        $("#transferFrom").change(function () {
            if ($("#transferFrom").val() == 'Primary') {
                $('#transferTo').val('Savings');
            } else if ($("#transferFrom").val() == 'Savings') {
                $('#transferTo').val('Primary');
            }
        });

        $("#transferTo").change(function () {
            if ($("#transferTo").val() == 'Primary') {
                $('#transferFrom').val('Savings');
            } else if ($("#transferTo").val() == 'Savings') {
                $('#transferFrom').val('Primary');
            }
        });
    };


}(jQuery));

$(document).ready(function () {
    var confirm = function () {
        bootbox.confirm({
            title: "Appointment Confirmation",
            message: "Do you really want to schedule this appointment?",
            buttons: {
                cancel: {
                    label: '<i class="fa fa-times"></i> Cancel'
                },
                confirm: {
                    label: '<i class="fa fa-check"></i> Confirm'
                }
            },
            callback: function (result) {
                if (result == true) {
                    $('#appointmentForm').submit();//appointmentForm #form2
                } else {
                    console.log("Scheduling cancelled.");
                }
            }
        });
    };

    var confirmA = function () {
        bootbox.confirm({
            title: "Appointment Confirmation",
            message: "Do you really want to schedule this appointment?",
            buttons: {
                cancel: {
                    label: '<i class="fa fa-times"></i> Cancel'
                },
                confirm: {
                    label: '<i class="fa fa-check"></i> Confirm'
                }
            },
            callback: function (result) {
                if (result) {
                    $('#buttonappointment').submit();//appointmentForm #form2
                } else {
                    alert("Not Scheduled")
                    console.log("Scheduling cancelled.");
                }
            }
        });
    };

    $.toggleShowPassword({
        field: '#password',
        control: "#showPassword"
    });

    $.transferDisplay();

    $(".form_datetime").datetimepicker({
        minView: 2,
        //format: "yyyy-mm-dd hh:mm",
        dateonly: true,
        autoclose: true,
        todayBtn: true,
        //startDate: "2013-02-14 10:00",
        startDate: "2021-02-14",
        //minuteStep: 10
    });

    $('#submitAppointment').click(function () {//submitAppointment buttonappointment
        $('#appointmentForm').submit();
        //confirm();
    });

    /*    function scheduleIt(dateinfo) {
            alert(dateinfo);
        }*/

    /*    function scheduleIt() {
            alert("hiii");
        }*/

    var procceed = false;
    $('#buttonappointment').click(function (event) {//submitAppointment buttonappointment
        //confirmA();
        //alert(event);
        //     alert("see");
        //     if (procceed){
        //         //event.pro
        //     }
        //     event.stopPropagation();
        //     event.preventDefault();
        //     event.cancelable();
        //     bootbox.confirm({
        //         title: "Appointment Confirmation",
        //         message: "Do you really want to schedule this appointment?",
        //         buttons: {
        //             cancel: {
        //                 label: '<i class="fa fa-times"></i> Cancel'
        //             },
        //             confirm: {
        //                 label: '<i class="fa fa-check"></i> Confirm'
        //             }
        //         },
        //         callback: function (result) {
        //             if (result) {
        //                 procceed=true;
        //             } else {
        //                 procceed=false;
        //                 alert("Not Scheduled")
        //                 console.log("Scheduling cancelled.");
        //             }
        //         }
        //     });
    });

    $('#updateInfoForm').click(function () {//submitAppointment buttonappointment

    });

    $('#updateDoctorForm').click(function () {
        alert("This has not implemented");
    });

});




