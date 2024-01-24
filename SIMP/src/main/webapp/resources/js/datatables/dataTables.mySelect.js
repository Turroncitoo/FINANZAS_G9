$(document).ready(function(){
	$(".table").find("tbody").on("click", "tr:not(.child)", function () {
        if ( $(this).hasClass('selectedRow') ) {
            $(this).removeClass('selectedRow');
        }
        else {
        	$(".table").find("tbody").find("tr").removeClass('selectedRow');
            $(this).addClass('selectedRow');
        }
    });
});