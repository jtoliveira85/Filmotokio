
//
//document.addEventListener("DOMContentLoaded", function () {
//    initializeMultiSelect('.my-dd1', 'radio');
//    initializeMultiSelect('.my-dd2', 'radio');
//    initializeMultiSelect('.my-dd3', 'checkbox');
//    initializeMultiSelect('.my-dd4', 'checkbox');
//    initializeMultiSelect('.my-dd5', 'checkbox');
//});
//
//function initializeMultiSelect(selector, inputType) {
//    const myDropdownMenu = document.querySelector(`${selector} .my-dropdown-menu`);
//    const myOpcContainer = document.querySelector(`${selector} .my-opc-container`);
//    const filterInput = document.querySelector(`${selector} .my-filter-input`);
//    const inputElements = document.querySelectorAll(`${selector} .${inputType}-field`);
//    const mtSelectedItems = document.querySelector(`${selector} .my-selected-items-container`);
//
//    updateSelectedItems();
//
//
//    myDropdownMenu.addEventListener("click", function (event) {
//        event.stopPropagation();
//        myOpcContainer.classList.toggle("active");
//    });
//
//    document.addEventListener("click", function (event) {
//        if (!myOpcContainer.contains(event.target)) {
//            myOpcContainer.classList.remove("active");
//        }
//    });
//
//    filterInput.addEventListener('input', function () {
//        let filterValue = this.value.toLowerCase();
//        let labels = document.querySelectorAll(`${selector} .my-opc-items .form-check-label span`);
//
//        labels.forEach(function (label) {
//            let labelText = label.textContent.toLowerCase();
//            let labelContainer = label.closest('.form-check-label');
//
//            if (labelText.includes(filterValue)) {
//                labelContainer.style.display = 'block';
//            } else {
//                labelContainer.style.display = 'none';
//            }
//        });
//    });
//
//    inputElements.forEach(function (inputElement) {
//        inputElement.addEventListener('change', function () {
//            updateSelectedItems();
//        });
//    });
//
//    function updateSelectedItems() {
//
//        mtSelectedItems.innerHTML = ''; // Limpa os botões existentes TESTE
//
//        let selectedCountValue = 0;
//        let selectedText = '';
//
//        inputElements.forEach(function (inputElement) {
//            if (inputElement.checked) {
//                var spanText = inputElement.nextElementSibling.nextElementSibling.textContent;
//                createSelectedButton(spanText, inputElement.id);
//                selectedText = spanText;
//                selectedCountValue++;
//
//            }
//        });
//
//
//        selectedCountValue > 0 ? mtSelectedItems.style.display = 'flex' : mtSelectedItems.style.display = 'none';
//
//        if (inputType === 'radio') {
//            // Lógica específica para radio
//            myDropdownMenu.textContent = selectedCountValue > 0 ? `${selectedText}` : "Por Escolher:";
//        } else if (inputType === 'checkbox') {
//            // Lógica específica para checkbox
//            myDropdownMenu.textContent = selectedCountValue > 0 ? `${selectedCountValue} campos Selecionados` : "Por Escolher:";
//        }
//    }
//
//    function createSelectedButton(text, checkboxId) {
////        mtSelectedItems.innerHTML = ''; // Limpa os botões existentes antes de criar um novo
//
//        console.log("createSelectedButton(text, checkboxId): " + checkboxId)
//        console.log("inputElements: " + inputElements[0].classList)
//
//        var btn = document.createElement('button');
//        btn.className = 'btn btn-light my-selected-btn';
//        btn.innerHTML = text + ' <i class="bi bi-x-circle"></i>';
//
//        btn.addEventListener('click', function () {
//            removeSelectedButton(btn, checkboxId);
//        });
//
//        mtSelectedItems.appendChild(btn);
//    }
//
//    function removeSelectedButton(btn, checkboxId) {
//        const correspondingCheckbox = document.getElementById(checkboxId);
//
//        if (correspondingCheckbox) {
//            correspondingCheckbox.checked = false;
//        }
//
//        btn.remove(); // Remover o botão da interface
//        updateSelectedItems(); // Atualizar a contagem após a remoção
//    }
//}


document.addEventListener("DOMContentLoaded", function () {
    initializeMultiSelect('.my-dd1', 'radio');
    initializeMultiSelect('.my-dd2', 'radio');
    initializeMultiSelect('.my-dd3', 'checkbox');
    initializeMultiSelect('.my-dd4', 'checkbox');
    initializeMultiSelect('.my-dd5', 'checkbox');
});

function initializeMultiSelect(selector, inputType) {
    const myDropdownMenu = document.querySelector(`${selector} .my-dropdown-menu`);
    const myOpcContainer = document.querySelector(`${selector} .my-opc-container`);
    const filterInput = document.querySelector(`${selector} .my-filter-input`);
    const inputElements = document.querySelectorAll(`${selector} .${inputType}-field`);
    const mtSelectedItems = document.querySelector(`${selector} .my-selected-items-container`);

    updateSelectedItems();

    myDropdownMenu.addEventListener("click", function (event) {
        event.stopPropagation();
        myOpcContainer.classList.toggle("active");

        // Limpar campo de pesquisa e mostrar todas as checkboxes e radio buttons
        filterInput.value = '';
        showAllOptions();
    });

    document.addEventListener("click", function (event) {
        if (!myOpcContainer.contains(event.target)) {
            myOpcContainer.classList.remove("active");
        }
    });

    filterInput.addEventListener('input', function () {
        let filterValue = this.value.toLowerCase();
        let labels = document.querySelectorAll(`${selector} .my-opc-items .form-check-label span`);

        labels.forEach(function (label) {
            let labelText = label.textContent.toLowerCase();
            let labelContainer = label.closest('.form-check-label');

            if (labelText.includes(filterValue)) {
                labelContainer.style.display = 'block';
            } else {
                labelContainer.style.display = 'none';
            }
        });
    });

    inputElements.forEach(function (inputElement) {
        inputElement.addEventListener('change', function () {
            // Limpar campo de pesquisa e mostrar todas as checkboxes e radio buttons
            filterInput.value = '';
            showAllOptions();
            updateSelectedItems();
        });
    });

    function updateSelectedItems() {
        mtSelectedItems.innerHTML = ''; // Limpa os botões existentes TESTE
        let selectedCountValue = 0;
        let selectedText = '';

        inputElements.forEach(function (inputElement) {
            if (inputElement.checked) {
                var spanText = inputElement.nextElementSibling.nextElementSibling.textContent;
                createSelectedButton(spanText, inputElement.id);
                selectedText = spanText;
                selectedCountValue++;
            }
        });

        selectedCountValue > 0 ? mtSelectedItems.style.display = 'flex' : mtSelectedItems.style.display = 'none';

        if (inputType === 'radio') {
            myDropdownMenu.textContent = selectedCountValue > 0 ? `${selectedText}` : "Por Escolher:";
        } else if (inputType === 'checkbox') {
            myDropdownMenu.textContent = selectedCountValue > 0 ? `${selectedCountValue} campos Selecionados` : "Por Escolher:";
        }
    }

    function createSelectedButton(text, checkboxId) {
        var btn = document.createElement('button');
        btn.className = 'btn btn-light my-selected-btn';
        btn.innerHTML = text + ' <i class="bi bi-x-circle"></i>';

        btn.addEventListener('click', function () {
            removeSelectedButton(btn, checkboxId);
        });

        mtSelectedItems.appendChild(btn);
    }

    function removeSelectedButton(btn, checkboxId) {
        const correspondingCheckbox = document.getElementById(checkboxId);

        if (correspondingCheckbox) {
            correspondingCheckbox.checked = false;
        }

        btn.remove(); // Remover o botão da interface
        updateSelectedItems(); // Atualizar a contagem após a remoção
        showAllOptions(); // Mostrar todas as checkboxes e radio buttons
    }

    function showAllOptions() {
        let labels = document.querySelectorAll(`${selector} .my-opc-items .form-check-label`);
        labels.forEach(function (label) {
            label.style.display = 'block';
        });
    }
}
