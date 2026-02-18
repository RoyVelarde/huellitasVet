const SwalControl = Swal.mixin({
    allowOutsideClick: false,
    allowEscapeKey: false,
    reverseButtons: true,
    buttonsStyling: false,
    customClass: {
        confirmButton: 'btn btn-success border border-dark',
        cancelButton: 'btn btn-outline-danger'
    }
});

function validacionDinamica() {
    var formulario = document.getElementById('formulario');
    if (!formulario) return;
    var elementos = formulario.querySelectorAll('input[title], select[title], textarea[title]');
    for (var i = 0; i < elementos.length; i++) {
        var elemento = elementos[i];
        elemento.oninvalid = function (e) {
            var target = e.target;
            target.setCustomValidity("");
            if (!target.validity.valid) {
                target.setCustomValidity(target.title);
                $(target).addClass('is-invalid');
            }
        };
        elemento.oninput = function (e) {
            var target = e.target;
            target.setCustomValidity("");
            if ($(target).hasClass('is-invalid')) {
                $(target).removeClass('is-invalid');
            }
        };
    }
}

function abrirModal(url) {
    $('body').css('cursor', 'wait');
    $.ajax({
        url: url,
        type: 'GET',
        cache: false,
        success: function (res) {
            var container = $('#modalContainer');
            container.empty().html(res);
            var modalElement = $('#modalFormulario');
            if (modalElement.length > 0) {
                modalElement.modal({
                    backdrop: 'static',
                    keyboard: false,
                    show: true
                });
                validacionDinamica();
                modalElement.on('shown.bs.modal', function () {
                    $(this).find('input:visible:first').focus();
                });
            }
        },
        error: function (xhr, textStatus) {
            error_message(xhr, textStatus);
        },
        complete: function () {
            $('body').css('cursor', 'default');
        }
    });
}

function guardarData(url) {
    var formulario = document.getElementById('formulario');
    if (!formulario) return;
    if (formulario.reportValidity()) {
        var btnGuardar = $(formulario).closest('.modal-content').find('.btn-guardar');
        var originalHtml = btnGuardar.html();
        btnGuardar.prop('disabled', true).html('<i class="fa fa-spinner fa-spin"></i> Guardando...');
        $.ajax({
            url: url,
            type: 'POST',
            data: new FormData(formulario),
            processData: false,
            contentType: false,
            success: function (res) {
                if (res === "ok") {
                    $('#modalFormulario').modal('hide');
                    Swal.fire({
                        icon: 'success',
                        title: '¡ÉXITO!',
                        text: 'Datos guardados correctamente',
                        timer: 1500,
                        showConfirmButton: false
                    }).then(function () {
                        location.reload();
                    });
                } else {
                    $('#modalFormulario').modal('hide');
                    $('body').removeClass('modal-open');
                    $('.modal-backdrop').remove();
                    $("#modalContainer").html(res);
                    validacionDinamica();
                    $('#modalFormulario').modal({
                        backdrop: 'static',
                        keyboard: false,
                        show: true
                    });
                }
            },
            error: function (jqXHR, textStatus) {
                error_message(jqXHR, textStatus);
            },
            complete: function () {
                btnGuardar.prop('disabled', false).html(originalHtml);
            }
        });
    }
}

function eliminarData(url) {
    SwalControl.fire({
        title: '¿Estás seguro?',
        text: "¡Esta acción eliminará el registro permanentemente!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: '<i class="fa fa-trash"></i> Sí, eliminar',
        cancelButtonText: 'Cancelar',
    }).then(function (result) {
        if (result.isConfirmed) {
            $.ajax({
                url: url,
                type: 'POST',
                success: function (res) {
                    if (res === "ok") {
                        Swal.fire({
                            icon: 'success',
                            title: '¡Completado!',
                            text: 'El registro ha sido eliminado con éxito.',
                            timer: 1500,
                            showConfirmButton: false,
                        }).then(function () {
                            location.reload();
                        });
                    } else {
                        Swal.fire({
                            title: 'Error',
                            text: res,
                            icon: 'error',
                        });
                    }
                },
                error: function (jqXHR, textStatus) {
                    error_message(jqXHR, textStatus);
                }
            });
        }
    });
}

function buscarDuenio() {
    var dni = $('#dniBusqueda').val().trim();
    if (dni.length !== 8 || isNaN(dni)) {
        Swal.fire('Atención', 'Ingrese un DNI válido (8 dígitos numéricos)', 'warning');
        $('#dniBusqueda').focus();
        return;
    }
    var btnBusqueda = $('.btn-busqueda');
    var originalHtml = btnBusqueda.html();
    btnBusqueda.prop('disabled', true).html('<i class="fa fa-spinner fa-spin"></i>');
    $('#rol').val("Dueño");
    $('#idPersona').val("");
    $('#nombres').val("");
    $('#apellidos').val("");
    $('#numeroCelular').val("");
    $('#correo').val("");
    $.ajax({
        url: '/mascotas/buscar-duenio/' + dni,
        type: 'GET',
        dataType: 'json',
        success: function (persona) {
            if (persona) {
                $('#idPersona').val(persona.idPersona);
                $('#nombres').val(persona.nombres);
                $('#apellidos').val(persona.apellidos);
                $('#numeroCelular').val(persona.numeroCelular);
                $('#correo').val(persona.correo);
                Swal.fire({
                    toast: true,
                    position: 'top-end',
                    icon: 'success',
                    title: 'Dueño encontrado',
                    showConfirmButton: false,
                    timer: 3000
                });
            }
        },
        error: function (jqXHR, textStatus) {
            if (jqXHR.status === 404) {
                Swal.fire({
                    toast: true,
                    position: 'top-end',
                    icon: 'info',
                    title: 'DNI no registrado, ingrese datos',
                    showConfirmButton: false,
                    timer: 3000
                });
            } else {
                error_message(jqXHR, textStatus);
            }
        },
        complete: function () {
            btnBusqueda.prop('disabled', false).html(originalHtml);
        }
    });
}

function error_message(jqXHR, textStatus) {
    var errorMessage = "";
    if (jqXHR.status === 0) {
        errorMessage = "Sin conexión. Verifique su red.";
    } else if (jqXHR.status === 404) {
        errorMessage = "El recurso no fue encontrado [404].";
    } else if (jqXHR.status === 403) {
        errorMessage = "Acceso denegado [403].";
    } else if (jqXHR.status === 500) {
        errorMessage = "Error interno en el servidor [500].";
    } else if (textStatus === "timeout") {
        errorMessage = "Tiempo de espera agotado.";
    } else {
        errorMessage = jqXHR.responseText || ("Error: " + textStatus);
    }
    Swal.fire({
        title: "¡ATENCIÓN!",
        text: errorMessage,
        icon: "error",
    });
}