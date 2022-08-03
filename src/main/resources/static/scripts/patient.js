window.onload = () => {
    const formulario = document.getElementById("form_patient");

    formulario.addEventListener("submit", event => {
        event.preventDefault();
        addPatient();
    });
}

function addPatient() {
    const firstName = document.getElementById("firstName");
    const lastName = document.getElementById("lastName");
    const dni = document.getElementById("dni");
    const street = document.getElementById("street");
    const number = document.getElementById("number");
    const location = document.getElementById("location");
    const province = document.getElementById("province");
    const date = new Date()
    const formData = {
        firstName: firstName.value,
        lastName: lastName.value,
        dni: dni.value,
        registrationDate: date.getFullYear() + "-" + date.getMonth() + "-" + date.getDate(),
        home: {
            street: street.value,
            number: number.value,
            location: location.value,
            province: province.value,
      }
    };

    const settings = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
    };

    fetch("http://localhost:8080/patient", settings)
      .then((response) => response.json())
      .then(() => alert("Patient added"))
      .catch(() => alert("Oops! Failed to load patient"))
    //   .finally(() => {
    //       firstName.value = "";
    //       lastName.value = "";
    //       dni.value = "";
    //       street.value = "";
    //       number.value = "";
    //       location.value = "";
    //       province.value = "";
    //   })
}