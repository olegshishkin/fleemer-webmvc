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
    getOperationsPage(0, 15);
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
            $("#totalBalance").html(result.toFixed(2));
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
                    $(newSummary).find('span').html(value.balance.toFixed(2));
                    $('#accountSummaryPlace').append(newSummary);
            });
        }
    })
}

// Fills operation table and paginator
function getOperationsPage(page, size) {
    $.ajax({
        url: '/operation.json',
        data: { page: page, size: size },
        success: function (result) {
            fillTable(result.operations);
            fillPaginator(result.currentPage, result.totalPages, size);
        }
    });
}

// Fills operation table
function fillTable(operations) {
    $('#operationTable').empty();
    var openTag = '<td align="right">';
    var closeTag = '</td>';
    var table = $('#operationTableSnippet').clone(true).removeAttr('id');
    $.each(operations,
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
                row = row + '<td align="right" class="text-danger">- ' + sum.toFixed(2);
            } else if (isNotNull(inAccount) & isNotNull(category)) {
                row = row + '<td align="right" class="text-success">+ ' + sum.toFixed(2);
            } else {
                row = row + openTag + sum.toFixed(2);
            }
            row = row + closeTag + '</tr>';
            $(table).find('tbody').append(row);
        });
    $('#operationTable').append(table);
}

function isNotNull(value) {
    return value != undefined & value != 'null';
}

// Fills paginated links
function fillPaginator(cur, total, size) {
    console.log(cur);
    $('#pagination').empty();
    var prevLi = $('#curPage').clone(true).removeAttr('id');
    if (cur == 0){
        prevLi.addClass('disabled');
    } else {
        prevLi.removeAttr('disabled');
    }
    prevLi.find('a').attr('onclick', 'getOperationsPage(' + (cur - 1) + ', ' + size + ')');
    prevLi.find('a').html('<<');
    $('#pagination').append(prevLi);

    for (var i = 0; i < total; i++){
        var curLi = $('#curPage').clone(true).removeAttr('id');
        if (i == cur) {
            curLi.addClass('active');
        }
        curLi.find('a').attr('onclick', 'getOperationsPage(' + i + ', ' + size + ')');
        curLi.find('a').html(i + 1);
        $('#pagination').append(curLi);
    }

    var nextLi = $('#curPage').clone(true).removeAttr('id');
    if (cur == total - 1){
        nextLi.addClass('disabled');
    } else {
        nextLi.removeAttr('disabled');
    }
    nextLi.find('a').attr('onclick', 'getOperationsPage(' + (cur + 1) + ', ' + size + ')');
    nextLi.find('a').html('>>');
    $('#pagination').append(nextLi);
}