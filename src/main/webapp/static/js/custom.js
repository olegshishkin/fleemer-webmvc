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
    setTotalBalance();
    fillAccountsList();
    fillTable(0, 30);
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

function setTotalBalance(){
    $.ajax({
        url: '/balance.json',
        success: function(result){
            $("#totalBalance").html(result);
        }
    });
}

// Appends account summary
function fillAccountsList() {
    $.ajax({
        url: '/accountsList.json',
        success: function (result) {
            $.each(result,
                function appendAccountSummary(key, value) {
                    var newSummary = $('#accountSummary').clone(true).addClass('list-group-item').removeAttr('id');
                    $(newSummary).find('h6').html(value.name);
                    $(newSummary).find('small').html(value.type);
                    $(newSummary).find('span').html(value.balance);
                    $('#accountSummaryPlace').append(newSummary);
            });
        }
    })
}

// Appends operation table
function fillTable(page, size) {
    $.ajax({
        url: '/operation.json',
        data: { page: page, size: size },
        success: function (result) {
            $('#operationTable').empty();
            var openTag = '<td align="right">';
            var closeTag = '</td>';
            var table = $('#operationTableSnippet').clone(true).removeAttr('id');
            $.each(result,
                function addOperationRow(key, value) {
                    var sum = value.sum;
                    var row = '<tr>' + openTag + value.date + closeTag + openTag;
                    var outAccount = value.outAccount;
                    if (isNotNull(outAccount)) {
                        row = row + outAccount.name;
                    }
                    row = row + closeTag + openTag;
                    var inAccount = value.inAccount;
                    if (isNotNull(inAccount)) {
                        row = row + inAccount.name;
                    }
                    row = row + closeTag + openTag;
                    var category = value.category;
                    if (isNotNull(category)) {
                        row = row + category.name;
                    }
                    row = row + closeTag;
                    if (isNotNull(outAccount) & isNotNull(category)) {
                        row = row + '<td align="right" class="text-danger">-' + sum;
                    } else if (isNotNull(inAccount) & isNotNull(category)) {
                        row = row + '<td align="right" class="text-success">+' + sum;
                    } else {
                        row = row + openTag + sum;
                    }
                    row = row + closeTag + '</tr>';
                    $(table).find('tbody').append(row);
                });
            $('#operationTable').append(table);
        }
    });
}

function isNotNull(value) {
    return value != undefined & value != 'null';
}