$(document).ready(function() {

	var $local = {
		$modalPerfilRecurso : $("#modalPerfilRecurso"),
		$tablaPerfilRecurso : $("#tablaPerfilRecurso"),
		tablaPerfilRecurso : "",
		idPerfilSeleccionado : "",
		$titulo : $("#titulo"),
		$filaSeleccionada : "",
		$btnAsignarPermisos : $("#btnAsignarPermisos"),
		$tablasCategoria : $(".tablaCategoria")
	};

	$local.$tablasCategoria.find("tbody").find("tr").filter(function() {
		var $filaTablaCategoria = $(this);
		var accionesAsignadas = $filaTablaCategoria.attr("data-acciones").split(",");
		var filtro = $funcionUtil.construirNotEqual("name", accionesAsignadas);
		$filaTablaCategoria.find("input[type=checkbox]").filter(filtro).remove();
	});

	$local.tablaPerfilRecurso = $local.$tablaPerfilRecurso.DataTable({
		"ajax" : {
			"url" : $variableUtil.root + "seguridad/perfil?accion=buscarTodos",
			"dataSrc" : ""
		},
		"language" : {
			"emptyTable" : "No hay Perfiles registrados"
		},
		"initComplete" : function() {
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaPerfilRecurso);
			$local.$tablaPerfilRecurso.wrap("<div class='table-responsive'></div>");
		},
		"columnDefs" : [ {
			"targets" : [ 0, 1 ],
			"className" : "all filtrable",
		}, {
			"targets" : 2,
			"className" : "all dt-center",

			"defaultContent" : $variableUtil.botonPermisos
		} ],
		"columns" : [ {
			"data" : 'idPerfil',
			"title" : 'Perfil'
		}, {
			"data" : 'descripcion',
			"title" : 'Descripci\u00f3n'
		}, {
			"title" : 'Acci\u00f3n'
		} ]
	});

	$local.$tablaPerfilRecurso.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaPerfilRecurso.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$modalPerfilRecurso.PopupWindow({
		title : "Asignaci\u00f3n de Permisos",
		autoOpen : false,
		modal : false,
		height : 300,
		width : 640
	});

	$local.$tablaPerfilRecurso.children("tbody").on("click", ".permiso", function() {
		var $botonAsignarPermiso = $(this);
		$local.$filaSeleccionada = $(this).parents("tr");
		var perfilrecurso = $local.tablaPerfilRecurso.row($local.$filaSeleccionada).data();
		$local.idPerfilSeleccionado = perfilrecurso.idPerfil;
		$local.$titulo.text("Asignar permisos a Perfil: " + $local.idPerfilSeleccionado);
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "seguridad/perfil/" + $local.idPerfilSeleccionado + "?accion=buscarRecursos",
			beforeSend : function() {
				$local.$tablasCategoria.find("tbody").find("tr").find("input[type=checkbox]").prop("checked", false);
				$botonAsignarPermiso.attr("disabled", true).find("i").removeClass("fa-check-square-o").addClass("fa-spinner fa-pulse fa-fw");
			},
			success : function(perfiles) {
				if (perfiles.length == 0) {
					$funcionUtil.notificarException("El perfil seleccionado no fue encontrado.", "fa-exclamation-circle", "Informaci\u00F3n", "info");
					return;
				}
				var perfil = perfiles[0];
				$.each(perfil.recursos, function(i, recurso) {
					var accionesAsignadas = recurso.accionRecurso.split(",");
					var filtro = $funcionUtil.construirEqual("name", accionesAsignadas);
					$("#" + recurso.idRecurso).find("input[type=checkbox]").filter(filtro).prop("checked", true);
				});
				$local.$modalPerfilRecurso.PopupWindow("open");
				$local.$modalPerfilRecurso.PopupWindow("maximize");
			},
			complete : function() {
				$botonAsignarPermiso.attr("disabled", false).find("i").addClass("fa-check-square-o").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$btnAsignarPermisos.on("click", function() {
		var perfil = {};
		var recursos = [];
		$local.$tablasCategoria.find("tbody").find("tr").filter(function() {
			var $filaTablaRecurso = $(this);
			var idRecurso = $filaTablaRecurso.attr("id");
			var accionesAsignadas = [];
			$filaTablaRecurso.find("input:checked").each(function() {
				accionesAsignadas.push($(this).val());
			});
			if (accionesAsignadas.length != 0) {
				recursos.push({
					idRecurso : idRecurso,
					accionRecurso : accionesAsignadas.join(",")
				});
			}
		});
		perfil = {
			idPerfil : $local.idPerfilSeleccionado,
			recursos : recursos
		};
		$.ajax({
			type : "POST",
			url : $variableUtil.root + "seguridad/perfilRecurso?accion=asignarPermiso",
			data : JSON.stringify(perfil),
			beforeSend : function(xhr) {
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
				$local.$btnAsignarPermisos.attr("disabled", true).find("i").removeClass("fa-check-square-o").addClass("fa-spinner fa-pulse fa-fw");
			},
			success : function() {
				$funcionUtil.notificarException($variableUtil.registroExitoso, "fa-check", "Aviso", "success");
				$local.$modalPerfilRecurso.PopupWindow("close");
			},
			complete : function() {
				$local.$btnAsignarPermisos.attr("disabled", false).find("i").addClass("fa-check-square-o").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});
});