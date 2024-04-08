var inputsAndLabels = document.querySelectorAll('.dropdown-menu input, .dropdown-menu label, .dropdown-menu li');

// Adiciona um evento de clique a cada elemento de entrada e rótulo
inputsAndLabels.forEach(function (element) {
    element.addEventListener('click', function (e) {
        calcularDistancia();
        e.stopPropagation(); // Impede a propagação do evento de clique
    });
});


const dropdownMenu = document.getElementById('dropdownMenu');
dropdownMenu.addEventListener('click', () => {
    calcularDistancia();
})


let cont = 0;

document.getElementById('filterInput').addEventListener('input', function () {

    let filterValue = this.value.toLowerCase();
    let checkboxes = document.querySelectorAll('.checkbox-field');

    checkboxes.forEach(function (checkbox) {

        let labelText = checkbox.parentElement.textContent.toLowerCase();

        if (labelText.includes(filterValue)) {
            checkbox.parentElement.style.display = 'block';
        } else {
            checkbox.parentElement.style.display = 'none';
        }
    });
});

const checkboxes = document.querySelectorAll('.checkbox-field');

checkboxes.forEach(cb => {
    cb.addEventListener('click', () => {

        let selectedCheckBoxes = getSelectedCheckBoxes();
        console.log("A" + selectedCheckBoxes.length);
        crateSelectedBtns(selectedCheckBoxes);

        // updateDropDwnText(selectedCheckBoxes);

    })
})

function getSelectedCheckBoxes() {

    const cntSelected = Array.from(checkboxes).filter(selected => selected.checked).length;
    console.log("Selected AAAAAAAAAAAAAAAAAAAAA: " + cntSelected);

    const btnSearch = document.querySelector('.btn-search');
    // Update Selection Counter
    if (cntSelected > 0) {
        btnSearch.textContent = `Selecionado ${cntSelected}`;
        // Cria butões

    } else {
        btnSearch.textContent = `Escolha as opções `;

        // romove os Butões;
        removeSelectedBtns();
        // const selectedContainer = document.getElementById('selected-container');
        // selectedContainer.innerHTML = ''; // Limpe o conteúdo se nenhuma checkbox estiver selecionada
    }




    return Array.from(checkboxes).filter(selected => selected.checked);
}

function crateSelectedBtns(selectedCheckBoxes) {
    const selectedContainer = document.getElementById('selected-container');
    selectedContainer.innerHTML = '';
    selectedCheckBoxes.forEach(function (cb) {
        var labelText = cb.nextElementSibling.nextElementSibling.textContent.trim();
        console.log(labelText + " value: " + cb.value)

        const bntSelectedItem = document.createElement('button');
        bntSelectedItem.classList.add('btn-info');
        bntSelectedItem.classList.add('btn');
        bntSelectedItem.classList.add('btn-option');

        bntSelectedItem.setAttribute('value', cb.value);
        bntSelectedItem.innerHTML = labelText + ' <i class="bi bi-x-circle"></i>'; // Obter o valor da checkbox
        console.log("labelText", labelText);
        console.log("bntSelectedItem value: " + bntSelectedItem.value)

        selectedContainer.appendChild(bntSelectedItem); // Adicione o botão ao contêiner

        // const bntSelectedItemLabel = document.createElement('span')
        // bntSelectedItemLabel.innerText = labelText;
        // bntSelectedItem.appendChild(bntSelectedItemLabel); // Adicione a label ao botão
        adicionarEventoClique();


    });


}

function removeSelectedBtns() {
    const selectedContainer = document.getElementById('selected-container');
    selectedContainer.innerHTML = ''; // Limpe o conteúdo se nenhuma checkbox estiver selecionada
}


function adicionarEventoClique() {
    const btnOptions = document.querySelectorAll('.btn-option');

    btnOptions.forEach(btn => {
        btn.addEventListener('click', function () {
            console.log('Botão clicado:', btn.value);

            btn.remove();  // Remove apenas o botão clicado

            checkboxes.forEach(ch => {
                if (ch.value == btn.value) {
                    ch.checked = false;
                }

            });

            console.log('Checkbox desmarcada e botão removido.');
            getSelectedCheckBoxes();
        });
    });
}



// ESTILOS para DropDown

function calcularDistancia() {

    var myElement = document.getElementById('dropdownList');
    var distanceToBottom = window.innerHeight - myElement.getBoundingClientRect().top;

    console.log()

    myElement.style.maxHeight = (distanceToBottom - 20) + 'px';
    myElement.style.minHeight = 'fit-content';
    var retangulo = myElement.getBoundingClientRect();
}

// Adiciona um ouvinte de evento para redimensionamento da janela
window.addEventListener('resize', function () {
    // Chama a função quando a janela for redimensionada
    calcularDistancia();
});

// Chama a função inicialmente para exibir a distância ao carregar a página
calcularDistancia();