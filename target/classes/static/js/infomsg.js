//document.addEventListener('DOMContentLoaded', function () {
//    // Função para exibir notificação
//    function showNotification(message, type) {
//        const notificationContainer = document.getElementById('notification-container');
//
//        // Criar elemento de notificação
//        const notification = document.createElement('div');
//        notification.className = `notification ${type}`;
//        notification.textContent = message;
//
//        // Adicionar notificação ao contêiner
//        notificationContainer.appendChild(notification);
//
//        // Exibir notificação
//        setTimeout(function () {
//            notification.style.display = 'block';
//        }, 100);
//
//        // Ocultar notificação após alguns segundos
//        setTimeout(function () {
//            notification.style.display = 'none';
//            // Remover notificação do DOM
//            notificationContainer.removeChild(notification);
//        }, 5000); // 5000 milissegundos (5 segundos)
//    }
//
//    // Verificar se há mensagens de sucesso no flash attributes
//    const successMessage = document.querySelector('[th:text="${successMessage}"]');
//    if (successMessage) {
//        // Exibir notificação de sucesso
//        showNotification(successMessage.textContent, 'success');
//    }
//});


document.addEventListener('DOMContentLoaded', function () {
    var toastElement = document.querySelector('.toast');
    var toast = new bootstrap.Toast(toastElement);
    toast.show();
});




//var toastElList = [].slice.call(document.querySelectorAll('.toast'))
//var toastList = toastElList.map(function (toastEl) {
//  return new bootstrap.Toast(toastEl, option)
//})
//
//
//var toastTrigger = document.getElementById('liveToastBtn')
//var toastLiveExample = document.getElementById('liveToast')
//if (toastTrigger) {
//  toastTrigger.addEventListener('click', function () {
//    var toast = new bootstrap.Toast(toastLiveExample)
//
//    toast.show()
//  })
//}