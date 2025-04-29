<template>
    <div class="student-interface">
      <!-- Header Section -->
      <div class="header">
        <h1>Student Portal</h1>
        <div class="header-actions">
          <button @click="activeTab = 'chat'" :class="{ active: activeTab === 'chat' }">
            <ion-icon name="chatbubble-ellipses-outline"></ion-icon> AI Assistant
          </button>
          <button @click="activeTab = 'appointment'" :class="{ active: activeTab === 'appointment' }">
            <ion-icon name="calendar-outline"></ion-icon> Book Appointment
          </button>
        </div>
      </div>
  
      <!-- Main Content Area -->
      <div class="content-container">
        <!-- AI Chat Interface -->
        <div v-if="activeTab === 'chat'" class="chat-interface">
          <div class="chat-header">
            <h2><ion-icon name="logo-reddit"></ion-icon> UniWell AI Assistant</h2>
            <p>Ask me anything about courses, schedules, or university services</p>
          </div>
          
          <div class="chatbot-container">
            <iframe
              src="https://www.chatbase.co/chatbot-iframe/lR8CFH6F6R50cktx2LDnc"
              width="100%"
              height="100%"
              frameborder="0"
            ></iframe>
          </div>
        </div>
  
        <!-- Appointment Booking Interface -->
        <div v-if="activeTab === 'appointment'" class="appointment-interface">
          <div class="appointment-header">
            <h2><ion-icon name="calendar-outline"></ion-icon> Book an Appointment</h2>
            <p>Schedule a meeting with your advisor or professor</p>
          </div>
          
          <div class="form-container">
            <div class="form-card">
              <form @submit.prevent="submitForm">
                <div class="form-group">
                  <label for="client">Your Name</label>
                  <div class="input-wrapper">
                    <ion-icon name="person-outline"></ion-icon>
                    <input
                      type="text"
                      id="client"
                      v-model="appointment.client"
                      placeholder="Enter your full name"
                      required
                    />
                  </div>
                </div>
                
                <div class="form-group">
                  <label for="email">Your Email</label>
                  <div class="input-wrapper">
                    <ion-icon name="mail-outline"></ion-icon>
                    <input
                      type="email"
                      id="email"
                      v-model="appointment.email"
                      placeholder="Enter your email"
                      required
                    />
                  </div>
                </div>
                
                <div class="form-group">
                  <label for="date">Appointment Date & Time</label>
                  <div class="input-wrapper">
                    <ion-icon name="time-outline"></ion-icon>
                    <input
                      type="datetime-local"
                      id="date"
                      v-model="appointment.date"
                      required
                    />
                  </div>
                </div>
                
                <div class="form-group">
                  <label for="purpose">Purpose of Meeting</label>
                  <div class="input-wrapper">
                    <ion-icon name="document-text-outline"></ion-icon>
                    <select id="purpose" v-model="appointment.purpose" required>
                      <option value="" disabled>Select a purpose</option>
                      <option value="academic">Academic Advising</option>
                      <option value="course">Course Selection</option>
                      <option value="career">Career Counseling</option>
                      <option value="other">Other</option>
                    </select>
                  </div>
                </div>
                
                <div class="form-group" v-if="appointment.purpose === 'other'">
                  <label for="details">Additional Details</label>
                  <textarea
                    id="details"
                    v-model="appointment.details"
                    placeholder="Please describe the purpose of your meeting"
                    rows="3"
                  ></textarea>
                </div>
                
                <button type="submit" class="submit-btn">
                  <ion-icon name="calendar-outline"></ion-icon> Book Appointment
                </button>
              </form>
              
              <div v-if="message" class="message" :class="{ success: isSuccess, error: !isSuccess }">
                <ion-icon :name="isSuccess ? 'checkmark-circle-outline' : 'alert-circle-outline'"></ion-icon>
                {{ message }}
              </div>
            </div>
            
            <div class="appointment-tips">
              <h3><ion-icon name="bulb-outline"></ion-icon> Booking Tips</h3>
              <ul>
                <li>Book at least 24 hours in advance</li>
                <li>Check your email for confirmation</li>
                <li>You can cancel or reschedule up to 2 hours before</li>
                <li>Need help? Ask our <a @click="activeTab = 'chat'">AI Assistant</a></li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'StudentPortal',
    data() {
      return {
        activeTab: 'chat',
        appointment: {
          client: '',
          email: '',
          date: '',
          purpose: '',
          details: ''
        },
        message: '',
        isSuccess: false
      }
    },
    methods: {
      submitForm() {
        // Simulate form submission
        this.isLoading = true;
        
        // In a real app, you would send this to your backend
        console.log('Submitting appointment:', this.appointment);
        
        // Simulate API call delay
        setTimeout(() => {
          this.isSuccess = true;
          this.message = 'Appointment booked successfully! You will receive a confirmation email shortly.';
          this.isLoading = false;
          
          // Reset form after 3 seconds
          setTimeout(() => {
            this.message = '';
            this.appointment = {
              client: '',
              email: '',
              date: '',
              purpose: '',
              details: ''
            };
          }, 5000);
        }, 1500);
      }
    }
  }
  </script>
  
  <style scoped>
  .student-interface {
    display: flex;
    flex-direction: column;
    height: 100vh;
    background-color: #f8f9fa;
    font-family: 'Ubuntu', sans-serif;
  }
  
  .header {
    background-color: #2a2185;
    color: white;
    padding: 1.5rem 2rem;
    display: flex;
    justify-content: space-between;
    align-items: center;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  }
  
  .header h1 {
    margin: 0;
    font-size: 1.8rem;
    font-weight: 600;
  }
  
  .header-actions {
    display: flex;
    gap: 1rem;
  }
  
  .header-actions button {
    background: rgba(255, 255, 255, 0.1);
    border: none;
    color: white;
    padding: 0.75rem 1.5rem;
    border-radius: 50px;
    display: flex;
    align-items: center;
    gap: 0.5rem;
    cursor: pointer;
    transition: all 0.3s ease;
    font-weight: 500;
  }
  
  .header-actions button:hover {
    background: rgba(255, 255, 255, 0.2);
    transform: translateY(-2px);
  }
  
  .header-actions button.active {
    background: white;
    color: #2a2185;
  }
  
  .content-container {
    flex: 1;
    padding: 2rem;
    overflow: auto;
  }
  
  /* Chat Interface Styles */
  .chat-interface {
    display: flex;
    flex-direction: column;
    height: 100%;
    background: white;
    border-radius: 16px;
    box-shadow: 0 8px 24px rgba(0, 0, 0, 0.05);
    overflow: hidden;
  }
  
  .chat-header {
    padding: 1.5rem;
    background: linear-gradient(135deg, #2a2185 0%, #4a3cc7 100%);
    color: white;
  }
  
  .chat-header h2 {
    margin: 0;
    display: flex;
    align-items: center;
    gap: 0.75rem;
    font-size: 1.5rem;
  }
  
  .chat-header p {
    margin: 0.5rem 0 0;
    opacity: 0.9;
  }
  
  .chatbot-container {
    flex: 1;
    min-height: 600px;
    border: none;
  }
  
  /* Appointment Interface Styles */
  .appointment-interface {
    display: flex;
    flex-direction: column;
    height: 100%;
  }
  
  .appointment-header {
    padding: 1rem 0 1.5rem;
  }
  
  .appointment-header h2 {
    margin: 0;
    display: flex;
    align-items: center;
    gap: 0.75rem;
    font-size: 1.5rem;
    color: #2a2185;
  }
  
  .appointment-header p {
    margin: 0.5rem 0 0;
    color: #666;
  }
  
  .form-container {
    display: flex;
    gap: 2rem;
    flex: 1;
  }
  
  .form-card {
    flex: 2;
    background: white;
    padding: 2rem;
    border-radius: 16px;
    box-shadow: 0 8px 24px rgba(0, 0, 0, 0.05);
  }
  
  .appointment-tips {
    flex: 1;
    background: white;
    padding: 2rem;
    border-radius: 16px;
    box-shadow: 0 8px 24px rgba(0, 0, 0, 0.05);
  }
  
  .appointment-tips h3 {
    margin-top: 0;
    display: flex;
    align-items: center;
    gap: 0.5rem;
    color: #2a2185;
  }
  
  .appointment-tips ul {
    padding-left: 1.5rem;
    line-height: 1.6;
  }
  
  .appointment-tips li {
    margin-bottom: 0.75rem;
  }
  
  .appointment-tips a {
    color: #2a2185;
    text-decoration: none;
    font-weight: 500;
    cursor: pointer;
  }
  
  .appointment-tips a:hover {
    text-decoration: underline;
  }
  
  /* Form Styles */
  .form-group {
    margin-bottom: 1.5rem;
  }
  
  .form-group label {
    display: block;
    margin-bottom: 0.5rem;
    font-weight: 500;
    color: #444;
  }
  
  .input-wrapper {
    position: relative;
    display: flex;
    align-items: center;
  }
  
  .input-wrapper ion-icon {
    position: absolute;
    left: 1rem;
    color: #666;
    font-size: 1.25rem;
  }
  
  .input-wrapper input,
  .input-wrapper select,
  textarea {
    width: 100%;
    padding: 0.75rem 1rem 0.75rem 3rem;
    border: 1px solid #ddd;
    border-radius: 8px;
    font-size: 1rem;
    transition: all 0.3s ease;
  }
  
  .input-wrapper input:focus,
  .input-wrapper select:focus,
  textarea:focus {
    border-color: #2a2185;
    box-shadow: 0 0 0 3px rgba(42, 33, 133, 0.1);
    outline: none;
  }
  
  textarea {
    padding: 1rem;
    min-height: 100px;
    resize: vertical;
  }
  
  .submit-btn {
    width: 100%;
    padding: 1rem;
    background: #2a2185;
    color: white;
    border: none;
    border-radius: 8px;
    font-size: 1rem;
    font-weight: 500;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 0.5rem;
    cursor: pointer;
    transition: all 0.3s ease;
  }
  
  .submit-btn:hover {
    background: #1a1668;
    transform: translateY(-2px);
  }
  
  .message {
    margin-top: 1.5rem;
    padding: 1rem;
    border-radius: 8px;
    display: flex;
    align-items: center;
    gap: 0.5rem;
  }
  
  .message.success {
    background: #e8f5e9;
    color: #2e7d32;
  }
  
  .message.error {
    background: #ffebee;
    color: #c62828;
  }
  
  /* Responsive Design */
  @media (max-width: 992px) {
    .form-container {
      flex-direction: column;
    }
  }
  
  @media (max-width: 768px) {
    .header {
      flex-direction: column;
      align-items: flex-start;
      gap: 1rem;
    }
    
    .header-actions {
      width: 100%;
      flex-wrap: wrap;
    }
    
    .header-actions button {
      flex: 1;
      justify-content: center;
    }
  }
  </style>