<template>
  <div>
    <h2>Book an Appointment</h2>
    <form @submit.prevent="submitForm">
      <label>Name:</label>
      <input type="text" v-model="appointment.client" required />

      <label>Date:</label>
      <input type="date" v-model="appointment.date" required />

      <button type="submit">Book Now</button>
    </form>

    <p v-if="message">{{ message }}</p>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      appointment: {
        client: "",
        date: "",
      },
      message: "",
    };
  },
  methods: {
    formatDateToISO(date) {
      return new Date(date).toISOString(); // Convertit au format "YYYY-MM-DDTHH:mm:ss.sssZ"
    },

    submitForm() {
      const formattedDate = this.formatDateToISO(this.appointment.date);

      const newAppointment = {
        client: this.appointment.client,
        date: formattedDate, // Envoie la date au bon format
        status: "Pending",
        userId: 1,
      };

      axios
        .post("http://localhost:8084/api/appointments/submit", newAppointment)
        .then((response) => {
          console.log("Success:", response.data);
          this.message = "Appointment submitted successfully!";
        })
        .catch((error) => {
          if (error.response) {
            console.error("Error:", error.response.status, error.response.data);
            this.message = `Error: ${error.response.data}`;
          } else {
            console.error("Request Failed:", error.message);
            this.message = "Request failed. Check the console.";
          }
        });
    },
  },
};
</script>
<style>
form {
  display: flex;
  flex-direction: column;
  width: 100%;
  max-width: 400px;
  margin: 30px auto;
  padding: 20px;
  background: #ffffff;
  border-radius: 10px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

label {
  font-weight: bold;
  margin-bottom: 5px;
  color: #333;
}

input {
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 16px;
  transition: border-color 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
}

input:focus {
  border-color: #007bff;
  box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
  outline: none;
}

button {
  margin-top: 15px;
  padding: 12px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 5px;
  font-size: 18px;
  cursor: pointer;
  transition: background-color 0.3s ease-in-out, transform 0.2s;
}

button:hover {
  background-color: #0056b3;
  transform: scale(1.05);
}

p {
  text-align: center;
  margin-top: 15px;
  font-weight: bold;
  color: #28a745;
}

@media (max-width: 500px) {
  form {
    padding: 15px;
  }

  input,
  button {
    font-size: 14px;
  }
}
</style>
