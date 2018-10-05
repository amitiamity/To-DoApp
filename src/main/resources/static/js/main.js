function changeStatus(id) {
	var data = {
		"status" : 1,
		"id" : id
	}
	$.ajax({
		url : "/update/" + id,
		type : "PUT",
		data : JSON.stringify(data),
		dataType : "application/json",
		contentType : "application/json; charset=utf-8",
		success : function(result) {
			console.log("task status changed");
		},
		failure : function(errMsg) {
			console.log("Failed to change status");
		}
	})
	location.reload(true);
}
function createTask() {
	var details = $("#taskdetails")[0].value;

	var data = {
		"details" : details
	}
	$.ajax({
		url : "/add/",
		type : "POST",
		data : JSON.stringify(data),
		dataType : "application/json",
		contentType : "application/json; charset=utf-8",
		success : function(result) {
			console.log("task created ");
		},
		failure : function(errMsg) {
			console.log("Failed to create task");
		}
	})
	location.reload(true);
}
function deleteTask(id) {
	$.ajax({
		url : "/delete/" + id,
		type : "DELETE",
		success : function(result) {
			console.log("task has been deleted");
		},
		failure : function(errMsg) {
			console.log("Failed to delete task");
		}
	})
	location.reload(true);
}
$(document).ready(function() {
	var taskArray = [];
	$.ajax({
		type : "GET",
		url : "/showAllTasks",
		success : function(result) {
			console.log(result)
			if (result.message === undefined){
				
			for (var i in result) {
				var checkboxfield = ""
				if (result[i].status == 'PENDING') {
					checkboxfield = '<input type="checkbox" onclick="changeStatus(' + result[i].id + ');" ></input>'
				} else {
					checkboxfield = '<input type="checkbox" checked disabled></input>'
				}
				taskArray.push([ parseInt(i) + 1, result[i].details,
					result[i].createdDate, result[i].status,
					checkboxfield,
					'<a href="/" onclick="deleteTask(' + result[i].id + ');">Delete</a>' ])
			}
			
			}
			inittable(taskArray);
		}
	});

	function inittable(data) {
		$("#taskList").dataTable({
			aaData : data,
			 "oLanguage": {
			        "sEmptyTable": "There are no task. Please create one."
			    }
		});
	}

});