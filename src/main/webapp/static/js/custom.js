// Starter JavaScript for disabling form submissions if there are invalid fields
function setValidationListener() {
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
}

// Set bootstrap datepicker
function setDataPicker() {
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
}

// operation type radiobuttons
function setOperationTypeListener(){
    $("input[name=operationType]:radio").change(function() {
        var inAcc = $('#inAccountName');
        var outAcc = $('#outAccountName');
        var category = $('#categoryName');
        if ($('#outcome').prop('checked')) {
            fillCategories('OUTCOME');
            inAcc.prop('selectedIndex', 0).prop('disabled', true).prop('required', false);
            outAcc.prop('disabled', false).prop('required', true);
            category.prop('disabled', false).prop('required', true);
        } else if ($('#income').prop('checked')) {
            fillCategories('INCOME');
            outAcc.prop('selectedIndex', 0).prop('disabled', true).prop('required', false);
            inAcc.prop('disabled', false).prop('required', true);
            category.prop('disabled', false).prop('required', true);
        } else if ($('#transfer').prop('checked')) {
            fillCategories('TRANSFER');
            category.prop('selectedIndex', 0).prop('disabled', true).prop('required', false);
            inAcc.prop('disabled', false).prop('required', true);
            outAcc.prop('disabled', false).prop('required', true);
        }
    })
}

function fillCategories(catType) {
    var selectTag = $('#categoryName').empty();
    selectTag.append($('<option>').attr('value', '').text('Select category'));
    if (catType == 'transfer') {
        return;
    }
    $.ajax({
        url: '/category/getAll.json',
        data: { type: catType },
        success: function(result){
            $.each(result,
                function (key, value) {
                    selectTag.append($('<option>').attr('value', value.name).text(value.name));
                });
        }
    });
}

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
                    newSummary.find('h6').html(value.name);
                    newSummary.find('small').html(value.type);
                    newSummary.find('span').html(value.balance.toFixed(2));
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
    var table = $('#operationTableSnippet').clone(true).removeAttr('id');
    $.each(operations,
        function addOperationRow(key, value) {
            var inAccount = value.inAccount;
            var outAccount = value.outAccount;
            var category = value.category;
            var sum = value.sum;
            var tr = $('<tr>');
            tr.append($('<td>').attr('align', 'right').text(value.date));
            var outAccountCell = $('<td>').attr('align', 'right');
            if (isNotNull(outAccount)) {
                outAccountCell.text(outAccount.name);
            }
            tr.append(outAccountCell);
            var inAccountCell = $('<td>').attr('align', 'right');
            if (isNotNull(inAccount)) {
                inAccountCell.text(inAccount.name);
            }
            tr.append(inAccountCell);
            var categoryCell = $('<td>').attr('align', 'right');
            if (isNotNull(category)) {
                categoryCell.text(category.name);
            }
            tr.append(categoryCell);
            if (isNotNull(outAccount) & isNotNull(category)) {
                tr.append($("<td>").attr('align', 'right').addClass('text-danger').text('- ' + sum.toFixed(2)));
            } else if (isNotNull(inAccount) & isNotNull(category)) {
                tr.append($('<td>').attr('align', 'right').addClass('text-success').text('+ ' + sum.toFixed(2)));
            } else {
                tr.append($('<td>').attr('align', 'right').text(sum.toFixed(2)));
            }
            table.find('tbody').append(tr);
        });
    $('#operationTable').append(table);
}

function isNotNull(value) {
    return value != undefined & value != 'null';
}

// Fills paginated links
function fillPaginator(cur, total, size) {
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