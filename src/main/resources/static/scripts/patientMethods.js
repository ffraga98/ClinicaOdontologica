var url = "http://localhost:8080/patient/"

function loadPatient(){
  const firstName = document.getElementById("firstName");
  const lastName = document.getElementById("lastName");
  const dni = document.getElementById("dni");
  const street = document.getElementById("street");
  const number = document.getElementById("number");
  const location = document.getElementById("location");
  const province = document.getElementById("province");
  const date = new Date()

  var dd = (date.getDate() < 10 ? '0' : '')
      + date.getDate();

  var MM = ((date.getMonth() + 1) < 10 ? '0' : '')
      + (date.getMonth() + 1);

  const data = {
    firstName: firstName.value,
    lastName: lastName.value,
    dni: dni.value,
    registrationDate: date.getFullYear() + "-" + MM + "-" + dd,
    home: {
      street: street.value,
      number: number.value,
      location: location.value,
      province: province.value,
    }
  };
  return data
}

function clearForm(){
  const firstName = document.getElementById("firstName");
  const lastName = document.getElementById("lastName");
  const dni = document.getElementById("dni");
  const street = document.getElementById("street");
  const number = document.getElementById("number");
  const location = document.getElementById("location");
  const province = document.getElementById("province");

  firstName.value = "";
  lastName.value = "";
  dni.value = "";
  street.value = "";
  number.value = "";
  location.value = "";
  province.value = "";

}

function addPatient() {
  const formData = loadPatient();
  const settings = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(formData),
  };

  fetch(url, settings)
      .then((response) => response.json())
      .then(() => loadPatients())
      .catch(() => alert("Oops! Failed to load patient"))
      .finally(() => {
        clearForm();
      } )
}

function loadPatients() {
  fetch(url)
      .then((response) => response.json())
      .then((data) => {
          deletePatientsHTML();
        data.forEach((patient) => {
          printPatientHTML(patient);
        });
      });
}

function deletePatientsHTML(){
  const patientTable = document
      .getElementById("patient_table")
      .getElementsByTagName("tbody")[0];

  patientTable.innerHTML = "";
}

function deletePatient(patient_id){
    fetch(url + patient_id, {
        method: 'DELETE',
    }).then(res => location.reload())
}

function printPatientHTML( patient ){
    const patientTable = document
        .getElementById("patient_table")
        .getElementsByTagName("tbody")[0];

    const {home} = patient;
    patientTable.innerHTML += `
          <tr id="${patient.id}">
              <td scope="row">${patient.id}</td>
              <td>${patient.firstName} ${patient.lastName}</td>
              <td>${patient.dni}</td>
              <td class="d-none d-xl-table-cell">${home.street} ${home.number}</td>
              <td class="d-none d-lg-table-cell">${home.location}</td>
              <td class="d-none d-md-table-cell">${home.province}</td>
              <td>${patient.registrationDate}</td>
              <td><button class="delete_btn btn btn-outline-danger " id="delete_${patient.id}"><i class="fa-solid fa-trash-can"></i></button>
              <button class="btn btn-outline-primary" id="delete_${patient.id}"><i class="fa-solid fa-pen-to-square"></i></button></td>
            </tr>
      `;
}


export { loadPatients, addPatient, deletePatient };