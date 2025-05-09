<template>
    <div class="booking-form">
      <h2>Book Appointment</h2>
      <form @submit.prevent="submitForm">
        <div class="form-group">
          <label>Client Name</label>
          <input v-model="client" required />
        </div>
        <div class="form-group">
          <label>Email</label>
          <input type="email" v-model="email" required />
        </div>
        <div class="form-group">
          <label>Date & Time</label>
          <input type="datetime-local" v-model="date" required />
        </div>
        <button type="submit">Submit Appointment</button>
      </form>
      <p v-if="message" class="message">{{ message }}</p>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  export default {
    name: 'Booking',
    data() {
      return {
        client: '',
        email: '',
        date: '',
        message: ''
      };
    },
    computed: {
      psyId() {
        return Number(this.$route.params.psyId);
      }
    },
    methods: {
      async submitForm() {
        try {
          const payload = {
            client: this.client,
            email: this.email,
            date: new Date(this.date).toISOString(),
            userId: this.psyId
          };
          await axios.post('http://localhost:8084/api/appointments', payload);
          this.message = 'Appointment created successfully!';
        } catch (err) {
          this.message = 'Error creating appointment';
          console.error(err);
        }
      }
    }
  };
  </script>
  
  <style scoped>
  .booking-form { max-width: 400px; margin: 2rem auto; }
  .form-group { margin-bottom: 1rem; }
  .message { color: green; margin-top: 1rem; }
  </style>