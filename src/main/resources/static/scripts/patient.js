import { loadPatients, addPatient } from './patientMethods.js';

window.onload = () => {
    const formulario = document.getElementById("form_patient");
    loadPatients();

    formulario.addEventListener("submit", event => {
        event.preventDefault();
        addPatient();
    });
}




