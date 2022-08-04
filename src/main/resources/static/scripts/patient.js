import { loadPatients, addPatient, deletePatient } from './patientMethods.js';

window.onload = () => {
    const formulario = document.getElementById("form_patient");
    loadPatients();

    formulario.addEventListener("submit", event => {
        event.preventDefault();
        addPatient();
    });

    const table_body = document.getElementById("table_patients");
    table_body.addEventListener("click", e =>{
        e.preventDefault();
        if(e.composedPath()[1].id.split("_")[0] === "delete")
            deletePatient(e.composedPath()[1].id.split("_")[1]);
    })


}





