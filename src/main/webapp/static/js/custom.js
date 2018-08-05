// Starter JavaScript for disabling form submissions if there are invalid fields
(function() {
    'use strict';
    window.addEventListener('load', function() {
        // Fetch all the forms we want to apply custom Bootstrap validation styles to
        var forms = document.getElementsByClassName('needs-validation');
        // Loop over them and prevent submission
        var validation = Array.prototype.filter.call(forms, function(form) {
            form.addEventListener('submit', function(event) {
                if (form.checkValidity() === false) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        });
    }, false);
})();

// Date picker activator
$(document).ready(function(){
    var date_input=$('input[name="operation.date"]'); //our date input has the name "date"
    var container=$('.bootstrap-iso form').length>0 ? $('.bootstrap-iso form').parent() : "body";
    var options={
        format: 'yyyy-mm-dd',
        container: container,
        todayHighlight: true,
        setDate: new Date(),
        autoclose: true
    };
    date_input.datepicker(options);
});

// operation type radiobuttons
(function(){
    jQuery("input[name=operationType]:radio").change(function() {
        if (jQuery("#outcome").prop('checked')) {
            jQuery("#inAccountName").prop('selectedIndex', 0);
            jQuery("#inAccountName").prop('disabled', true);
            jQuery("#outAccountName").prop('disabled', false);
            jQuery("#categoryName").prop('disabled', false);
        } else if (jQuery("#income").prop('checked')) {
            jQuery("#outAccountName").prop('selectedIndex', 0);
            jQuery("#inAccountName").prop('disabled', false);
            jQuery("#outAccountName").prop('disabled', true);
            jQuery("#categoryName").prop('disabled', false);
        } else if (jQuery("#transfer").prop('checked')) {
            jQuery("#categoryName").prop('selectedIndex', 0);
            jQuery("#inAccountName").prop('disabled', false);
            jQuery("#outAccountName").prop('disabled', false);
            jQuery("#categoryName").prop('disabled', true);
        }
    })

})();