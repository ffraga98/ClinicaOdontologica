var url = "http://localhost:8080/patient/"


function printPatientHTML( patient ){
  const patientTable = document
      .getElementById("patient_table")
      .getElementsByTagName("tbody")[0];

  const {home} = patient;
  patientTable.innerHTML += `
          <tr>
              <td scope="row">${patient.id}</td>
              <td>${patient.firstName} ${patient.lastName}</td>
              <td>${patient.dni}</td>
              <td class="d-none d-xl-table-cell">${home.street} ${home.number}</td>
              <td class="d-none d-lg-table-cell">${home.location}</td>
              <td class="d-none d-md-table-cell">${home.province}</td>
              <td>${patient.registrationDate}</td>
              <td><button class="btn btn-outline-danger" onClick="updatePatient(${patient.id})"><i class="fa-solid fa-trash-can"></i></button>
              <button class="btn btn-outline-primary" onClick="deletePatient(${patient.id})"><i class="fa-solid fa-pen-to-square"></i></button></td>
            </tr>
      `;
}



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
  let string = JSON.stringify(data);
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
      .then(() => alert("Patient added"))
      .catch(() => alert("Oops! Failed to load patient"))
      .finally(() => {
        loadPatients();
        clearForm();
      } )
}

function loadPatients() {
  fetch(url)
      .then((response) => response.json())
      .then((data) => {
        data.forEach((patient) => {
          printPatientHTML(patient);
        });
      });
}
export { loadPatients, addPatient };