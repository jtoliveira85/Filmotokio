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

    // Obtenha os valores previamente selecionados para checkboxes
    const selectedValues = Array.from(inputElements).filter(input => input.checked).map(input => input.value);

    updateSelectedItems();

    myDropdownMenu.addEventListener("click", function (event) {
        event.stopPropagation();
        myOpcContainer.classList.toggle("active");
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
            updateSelectedItems();
        });
    });

    function updateSelectedItems() {
        mtSelectedItems.innerHTML = '';

        let selectedCountValue = 0;
        let selectedText = '';

        inputElements.forEach(function (inputElement) {
            if (inputElement.checked) {
                var spanText = inputElement.nextElementSibling.nextElementSibling.textContent;
                createSelectedButton(spanText, inputElement.value);
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

    function createSelectedButton(text, checkboxValue) {
        var btn = document.createElement('button');
        btn.className = 'btn btn-light my-selected-btn';
        btn.innerHTML = text + ' <i class="bi bi-x-circle"></i>';

        btn.addEventListener('click', function () {
            removeSelectedButton(btn, checkboxValue);
        });

        mtSelectedItems.appendChild(btn);
    }

    function removeSelectedButton(btn, checkboxValue) {
        const correspondingCheckbox = document.querySelector(`${selector} .${inputType}-field[value="${checkboxValue}"]`);

        if (correspondingCheckbox) {
            correspondingCheckbox.checked = false;
        }

        btn.remove();
        updateSelectedItems();
    }

    // Marque as checkboxes previamente selecionadas
    selectedValues.forEach(value => {
        const checkbox = document.querySelector(`${selector} .${inputType}-field[value="${value}"]`);
        if (checkbox) {
            checkbox.checked = true;
        }
    });
}
