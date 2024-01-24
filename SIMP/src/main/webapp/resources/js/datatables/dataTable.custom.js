$("table").each(function(){
	$(this).addClass('seleccionado');
 });

/*Nuevo*/
	$('.seleccionado').on('dblclick', 'tr', function () {
        if(!$(this).hasClass('fila-seleccionada') ) {
        	$(this).addClass('fila-seleccionada');
        }
        else {
        	$(this).removeClass('fila-seleccionada');
        }
    } );