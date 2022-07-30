window.onload = () => {
    fetch("http://localhost:8080/patient/")
        .then((response) => response.json())
        .then((data) => loadPatients(data));
  };
  
  function loadPatients(patients) {
    const patientTable = document
      .getElementById("patient_table")
      .getElementsByTagName("tbody")[0];
  
      patients.forEach((patient) => {
        const { residence } = patient;
        patientTable.innerHTML += `
          <tr>
              <td scope="row">${patient.id}</td>
              <td>${patient.firstName}</td>
              <td>${patient.lastName}</td>
              <td>${patient.dni}</td>
              <td class="d-none d-xl-table-cell">${residence.street}</td>
              <td class="d-none d-xl-table-cell">${residence.number}</td>
              <td class="d-none d-lg-table-cell">${residence.location}</td>
              <td class="d-none d-md-table-cell">${residence.province}</td>
              <td>${patient.registrationDate}</td>
              <td><button class="btn btn-outline-danger" onClick="updatePatient(${patient.id})"><i class="fa-solid fa-trash-can"></i></button>
              <button class="btn btn-outline-primary" onClick="deletePatient(${patient.id})"><i class="fa-solid fa-pen-to-square"></i></button></td>
            </tr>
      `;
      });
  }