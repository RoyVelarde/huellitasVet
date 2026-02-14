const SwalControl = Swal.mixin({
    allowOutsideClick: false,
    allowEscapeKey: false,
    confirmButtonColor: '#26B99A',
    cancelButtonColor: '#d33'
});

function abrirModal(url) {
    $.ajax({
        url: url,
        type: 'GET',
        success: function (res) {
            $('#modalContainer').html(res);
            $('#modalFormulario').modal({
                backdrop: 'static',
                keyboard: false,
                show: true
            });
        },
        error: function () {
            Swal.fire('ERROR', 'No se pudo cargar el formulario', 'error');
        }
    });
}

function guardarData(url) {
    const form = $('#formulario');
    if (form[0].checkValidity()) {
        $.ajax({
            url: url,
            type: 'POST',
            data: form.serialize(),
            success: function (res) {
                $('#modalFormulario').modal('hide');
                Swal.fire({
                    icon: 'success',
                    title: '¡ÉXITO!',
                    text: res,
                    timer: 1500,
                    showConfirmButton: false
                }).then(() => {
                    location.reload();
                });
            },
            error: function (xhr) {
                const errorMsg = xhr.responseText ? xhr.responseText : 'Error';
                Swal.fire({
                    title: '¡ADVERTENCIA!',
                    text: errorMsg,
                    icon: 'error',
                    confirmButtonColor: '#26B99A'
                });
            }
        });
    } else {
        form[0].reportValidity();
    }
}

function eliminarData(url) {
    Swal.fire({
        title: '¿ESTÁ SEGURO DE ELIMINAR?',
        text: "Esta acción no se puede deshacer.",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#26B99A',
        cancelButtonColor: '#6c757d',
        confirmButtonText: 'Sí, eliminar',
        cancelButtonText: 'Cancelar',
        reverseButtons: true,
        allowOutsideClick: false
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                url: url,
                type: 'POST',
                success: function (res) {
                    Swal.fire({
                        icon: 'success',
                        title: '¡ELIMINADO!',
                        text: 'El registro ha sido borrado correctamente.',
                        timer: 1500,
                        showConfirmButton: false
                    }).then(() => {
                        location.reload();
                    });
                },
                error: function (xhr) {
                    const errorMsg = xhr.responseText ? xhr.responseText : 'No se pudo eliminar el registro';
                    Swal.fire({
                        title: '¡ADVERTENCIA!',
                        text: errorMsg,
                        icon: 'error',
                        confirmButtonColor: '#26B99A'
                    });
                }
            });
        }
    });
}