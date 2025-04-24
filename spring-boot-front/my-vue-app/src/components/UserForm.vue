<template>
  <div class="container">
    <div class="navigation">
      <ul>
        <li>
          <a href="#">
            <span class="icon">
              <ion-icon name="laptop-sharp"></ion-icon>
            </span>
            <span class="title">UniWell</span>
          </a>
        </li>
        <li>
          <a
            href="#"
            @click="currentView = 'dashboard'"
            :class="{ hovered: currentView === 'dashboard' }"
          >
            <span class="icon">
              <ion-icon name="home-outline"></ion-icon>
            </span>
            <span class="title">Dashboard</span>
          </a>
        </li>
        <li>
          <a
            href="#"
            @click="currentView = 'customers'"
            :class="{ hovered: currentView === 'customers' }"
          >
            <span class="icon">
              <ion-icon name="people-outline"></ion-icon>
            </span>
            <span class="title">Students</span>
          </a>
        </li>

        <li>
          <a
            href="#"
            @click="currentView = 'messages'"
            :class="{ hovered: currentView === 'messages' }"
          >
            <span class="icon">
              <ion-icon name="chatbubble-outline"></ion-icon>
            </span>
            <span class="title">Messages</span>
          </a>
        </li>

        <li>
          <a
            href="#"
            @click="currentView = 'help'"
            :class="{ hovered: currentView === 'help' }"
          >
            <span class="icon">
              <ion-icon name="help-outline"></ion-icon>
            </span>
            <span class="title">Help</span>
          </a>
        </li>

        <li>
          <a
            href="#"
            @click="currentView = 'chatbot'"
            :class="{ hovered: currentView === 'chatbot' }"
          >
            <span class="icon">
              <ion-icon name="logo-reddit"></ion-icon>
            </span>
            <span class="title">Chat with AI</span>
          </a>
        </li>
        <li>
          <a
            href="#"
            @click="currentView = 'password'"
            :class="{ hovered: currentView === 'password' }"
          >
            <span class="icon">
              <ion-icon name="lock-closed-outline"></ion-icon>
            </span>
            <span class="title">Password</span>
          </a>
        </li>

        <li>
          <a href="#" @click="signOut">
            <span class="icon">
              <ion-icon name="log-out-outline"></ion-icon>
            </span>
            <span class="title">Sign Out</span>
          </a>
        </li>
      </ul>
    </div>
    <!-- ========================= Main ==================== -->
    <div class="main">
      <div class="topbar">
        <div class="toggle" @click="toggleMenu">
          <ion-icon name="menu-outline"></ion-icon>
        </div>
        <div class="search">
          <label>
            <input
              type="text"
              placeholder="Search here"
              v-model="searchQuery"
              @keyup.enter="performSearch"
            />
          </label>
        </div>
        <div class="user" @click="toggleUserDropdown">
          <!-- <img
            src="https://randomuser.me/api/portraits/men/32.jpg"
            alt="User"
          /> -->
          <div class="dropdown" v-if="showUserDropdown">
            <a href="#">Profile</a>
            <a href="#">Settings</a>
            <a href="#" @click="signOut">Logout</a>
          </div>
        </div>
      </div>

      <!-- Dashboard Content -->
      <div v-if="currentView === 'dashboard'">
        

        <!-- ================ Order Details List ================= -->
        <div class="details">
          <div class="recentOrders">
            <div class="cardHeader">
              <h2>Appointments</h2>
              <a href="#" class="btn" @click="showAllAppointments">View All</a>
            </div>

            <table class="appointments-table">
              <thead>
                <tr>
                  <td>Name</td>
                  <td>E-mail</td>
                  <td>Time</td>
                  <td>Status</td>
                </tr>
              </thead>

              <tbody>
      <tr v-for="appointment in appointments" :key="appointment.id">
        <td>{{ appointment.client }}</td>
        <td>{{ appointment.email }}</td>
        <td>{{ formatTime(appointment.date) }}</td>
        <td>{{ appointment.status || 'Pending' }}</td>
      </tr>
    </tbody>
            </table>
          </div>

          <!-- ================= New Customers ================ -->
          <div class="recentCustomers">
            <div class="cardHeader">
              <h2>Students List</h2>
            </div>

            <table>
              <tr
                v-for="(customer, index) in recentCustomers"
                :key="index"
                @click="viewCustomer(customer.id)"
              >
                <td width="60px">
                  <div class="imgBx">
                    <img :src="customer.avatar" :alt="customer.name" />
                    
                  </div>
                </td>
                <td>
                  <h4>
                    {{ customer.name }} <br />
                    <span>{{ customer.location }}</span>
                  </h4>
                </td>
              </tr>
            </table>
          </div>
        </div>
      </div>

      <!-- Appointment Form View -->
      <div v-if="currentView === 'appointment'" class="form-container">
        <div class="form-card">
          <h2>Create New Appointment</h2>
          <form @submit.prevent="submitForm">
            <div class="form-group">
              <label for="client">Client Name</label>
              <input
                type="text"
                id="client"
                v-model="appointment.client"
                required
              />
            </div>
            <div class="form-group">
              <label for="date">Appointment Date</label>
              <input
                type="datetime-local"
                id="date"
                v-model="appointment.date"
                required
              />
            </div>
            <button type="submit" class="submit-btn">Submit Appointment</button>
          </form>
          <p v-if="message" class="message">{{ message }}</p>
        </div>
      </div>

      <!-- Other Views -->
      <!-- Chatbot View -->
      <div v-if="currentView === 'chatbot'" class="chatbot-container">
        <iframe
          src="https://www.chatbase.co/chatbot-iframe/lR8CFH6F6R50cktx2LDnc"
          width="100%"
          style="height: 70%; min-height: 700px"
          frameborder="0"
        ></iframe>
        <button @click="currentView = 'dashboard'" class="back-btn">
          Back to Dashboard
        </button>
      </div>

      <!-- Other Views -->
      <div v-if="currentView === 'allAppointments'" class="all-appointments-view">
  <div class="cardHeader">
    <h2>All Appointments</h2>
    <button @click="currentView = 'dashboard'" class="back-btn">
      Back to Dashboard
    </button>
  </div>
  
  <table class="appointments-table full-view">
    <thead>
      <tr>
        <th>Name</th>
        <th>E-mail</th>
        <th>Time</th>
        <th>Status</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="appointment in allAppointments" :key="appointment.id">
        <td>{{ appointment.client }}</td>
        <td>{{ appointment.email }}</td>
        <td>{{ formatTime(appointment.date) }}</td>
        <td>
          <span :class="'status ' + (appointment.status || 'pending')">
            {{ appointment.status || 'Pending' }}
          </span>
        </td>
      </tr>
    </tbody>
  </table>
</div>
      <div
        v-if="
          currentView !== 'dashboard' &&
          currentView !== 'appointment' &&
          currentView !== 'chatbot'
        "
        class="view-content"
      >
        <h2>
          {{ currentView.charAt(0).toUpperCase() + currentView.slice(1) }} View
        </h2>
        <p>This is the {{ currentView }} view content.</p>
        <button @click="currentView = 'dashboard'" class="back-btn">
          Back to Dashboard
        </button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      currentView: "dashboard",
      searchQuery: "",
      showUserDropdown: false,
      stats: {
        dailyViews: 1504,
        sales: 80,
        comments: 284,
        earnings: 7842,
      },
      
      recentCustomers: [],
      appointments: [],
      appointment: {
        email:"",
        client: "",
        date: "",
      },
      message: "",
    };
  },
  methods: 
  {fetchStudents() {
    fetch("http://localhost:8084/api/users/students")
      .then((res) => res.json())
      .then((data) => {
        this.recentCustomers = data.map(student => ({
          id: student.id,
          name: `${student.firstName} ${student.lastName}`,
          location: student.email,
          avatar: student.urlImage || 'https://via.placeholder.com/40' // image par défaut si vide
        }));
      })
      .catch((err) => console.error("Erreur de chargement des étudiants :", err));
  },
  fetchAppointments() {
  fetch("http://localhost:8084/api/appointments")
    .then((res) => res.json())
    .then((data) => {
      this.appointments = data;
    })
    .catch((err) => {
      console.error("Error fetching appointments:", err);
    });
},
formatTime(dateString) {
      return new Date(dateString).toLocaleString();
    },
    toggleMenu() {
      const navigation = document.querySelector(".navigation");
      const main = document.querySelector(".main");
      navigation.classList.toggle("active");
      main.classList.toggle("active");
    },
    toggleUserDropdown() {
      this.showUserDropdown = !this.showUserDropdown;
    },
    performSearch() {
      if (this.searchQuery.trim()) {
        alert(`Searching for: ${this.searchQuery}`);
      }
    },
    signOut() {
      alert("Signing out...");
      // Implement actual sign out functionality here
    },
    viewCustomer(id) {
      this.currentView = "customer-detail";
      // You would typically fetch customer details here
      alert(`Viewing customer with ID: ${id}`);
    },
    formatNumber(num) {
      return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
    formatDateToISO(date) {
      return new Date(date).toISOString();
    },
    submitForm() {
      const formattedDate = this.formatDateToISO(this.appointment.date);
      // In a real app, you would send this to your backend
      console.log("Submitting appointment:", {
        client: this.appointment.client,
        date: formattedDate,
      });
      this.message = "Appointment submitted successfully!";
      this.appointment.client = "";
      this.appointment.date = "";
      // Reset message after 3 seconds
      setTimeout(() => {
        this.message = "";
        this.currentView = "dashboard";
      }, 3000);
    },
  },
  mounted() {
    // Initialize any necessary data when component mounts
  this.fetchStudents();
  this.fetchAppointments();
}
  }
</script>
<style>
body {
  background-color: red !important;
}
.appointments-table td:nth-child(2) {  /* Email column */
  padding-right: 40px; /* Adds space after email column */
}
html,
body,
#app {
  margin: 0;
  padding: 0;
}
#app > div,
#app > nav,
#app > div > div {
  margin: 0;
  padding: 0;
}
nav {
  margin: 0;
  padding: 0;
  height: 0;
}
* {
  font-family: "Ubuntu", sans-serif;
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
:root {
  --blue: #2a2185;
  --white: #fff;
  --gray: #f5f5f5;
  --black1: #222;
  --black2: #999;
  --shadow: 0 7px 25px rgba(0, 0, 0, 0.08);
  --transition: all 0.3s ease;
}
body {
  min-height: 100vh;
  overflow-x: hidden;
  background: var(--gray);
}
.container {
  position: relative;
  width: 100%;
  margin: 0;
  padding: 0;
  top: 0;
}




/* =============== Navigation ================ */
.navigation {
  position: fixed;
  width: 300px;
  height: 100%;
  background: var(--blue);
  border-left: 10px solid var(--blue);
  transition: var(--transition);
  overflow: hidden;
  z-index: 1000;
  top: 0;
}
.navigation.active {
  width: 80px;
}
.navigation ul {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  padding-top: 0;
}
.navigation ul li {
  position: relative;
  width: 100%;
  list-style: none;
  border-top-left-radius: 30px;
  border-bottom-left-radius: 30px;
  margin-bottom: 5px;
}
.navigation ul li:hover,
.navigation ul li.hovered {
  background-color: var(--white);
}
.navigation ul li:nth-child(1) {
  margin-bottom: 40px;
  pointer-events: none;
}
.navigation ul li a {
  position: relative;
  display: block;
  width: 100%;
  display: flex;
  text-decoration: none;
  color: var(--white);
  transition: var(--transition);
}
.navigation ul li:hover a,
.navigation ul li.hovered a {
  color: var(--blue);
}
.navigation ul li a .icon {
  position: relative;
  display: block;
  min-width: 60px;
  height: 60px;
  line-height: 75px;
  text-align: center;
}
.navigation ul li a .icon ion-icon {
  font-size: 1.75rem;
}
.navigation ul li a .title {
  position: relative;
  display: block;
  padding: 0 10px;
  height: 60px;
  line-height: 60px;
  text-align: start;
  white-space: nowrap;
}
/* --------- curve outside ---------- */
.navigation ul li:hover a::before,
.navigation ul li.hovered a::before {
  content: "";
  position: absolute;
  right: 0;
  top: -50px;
  width: 50px;
  height: 50px;
  background-color: transparent;
  border-radius: 50%;
  box-shadow: 35px 35px 0 10px var(--white);
  pointer-events: none;
}
.navigation ul li:hover a::after,
.navigation ul li.hovered a::after {
  content: "";
  position: absolute;
  right: 0;
  bottom: -50px;
  width: 50px;
  height: 50px;
  background-color: transparent;
  border-radius: 50%;
  box-shadow: 35px -35px 0 10px var(--white);
  pointer-events: none;
}





.appointments-table {
  width: 100%;
  border-collapse: collapse;
  margin: 0;
  display: block;
  overflow: auto;


}

.appointments-table thead {
  display: table;
  width: 100%;
  table-layout: fixed;
  position: sticky;
  top: 0;
  background: var(--white);
  z-index: 10;
}
.appointments-table tbody {
  display: table;
  width: 100%;
  table-layout: fixed;
}

.appointments-table tr {
  display: table;
  width: 100%;
  table-layout: fixed;
}

.appointments-table th,
.appointments-table td {
  padding: 12px 15px;
  text-align: left;
  vertical-align: middle;
}

.appointments-table th {
  font-weight: 600;
  color: var(--blue);
  background-color: rgba(42, 33, 133, 0.05);
  position: sticky;
  top: 0;
}

.appointments-table tbody tr {
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
}

.appointments-table tbody tr:last-child {
  border-bottom: none;
}

.appointments-table tbody tr:hover {
  background: var(--blue);
  color: var(--white);
}


.appointments-table thead {
  display: table;
  width: 100%;
  table-layout: fixed;
  position: sticky;
  top: 0;
  background: var(--white);
  z-index: 10;
}
/* ===================== Main ===================== */
.main {
  position: absolute;
  width: calc(100% - 300px);
  left: 300px;
  min-height: 100vh;
  background: var(--white);
  transition: var(--transition);
  padding: 0;
  top: 0;
  margin: 0;
}
.main.active {
  width: calc(100% - 80px);
  left: 80px;
}
.topbar {
  width: 100%;
  height: 60px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  background: var(--white);
  box-shadow: var(--shadow);
  position: sticky;
  top: 0;
  z-index: 10;
  margin-top: 0;
}
.toggle {
  position: relative;
  width: 60px;
  height: 60px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 2.5rem;
  cursor: pointer;
  color: var(--blue);
  transition: var(--transition);
}
.toggle:hover {
  transform: scale(1.1);
}
.search {
  position: relative;
  width: 400px;
  margin: 0 10px;
}
.search label {
  position: relative;
  width: 100%;
}
.search label input {
  width: 100%;
  height: 40px;
  border-radius: 40px;
  padding: 5px 20px;
  padding-left: 35px;
  font-size: 16px;
  outline: none;
  border: 1px solid var(--black2);
  transition: var(--transition);
}
.search label input:focus {
  border-color: var(--blue);
  box-shadow: 0 0 5px rgba(42, 33, 133, 0.3);
}
.search label ion-icon {
  position: absolute;
  top: 10px;
  left: 10px;
  font-size: 1.2rem;
  color: var(--black2);
}
.user {
  position: relative;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  overflow: hidden;
  cursor: pointer;
  transition: var(--transition);
}
.user:hover {
  transform: scale(1.1);
}
.user img {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.user .dropdown {
  position: absolute;
  top: 50px;
  right: 0;
  background: var(--white);
  border-radius: 8px;
  box-shadow: var(--shadow);
  width: 150px;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  z-index: 100;
}
.user .dropdown a {
  padding: 10px 15px;
  text-decoration: none;
  color: var(--black1);
  transition: var(--transition);
}
.user .dropdown a:hover {
  background: var(--blue);
  color: var(--white);
}
/* ======================= Cards ====================== */
.cardBox {
  position: relative;
  width: 100%;
  padding: 20px;
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  grid-gap: 30px;
}
.cardBox .card {
  position: relative;
  background: var(--white);
  padding: 30px;
  border-radius: 20px;
  display: flex;
  justify-content: space-between;
  cursor: pointer;
  box-shadow: var(--shadow);
  transition: var(--transition);
}
.cardBox .card:hover {
  transform: translateY(-5px);
}
.cardBox .card .numbers {
  position: relative;
  font-weight: 500;
  font-size: 2.5rem;
  color: var(--blue);
}
.cardBox .card .cardName {
  color: var(--black2);
  font-size: 1.1rem;
  margin-top: 5px;
}
.cardBox .card .iconBx {
  font-size: 3.5rem;
  color: var(--black2);
}
.cardBox .card:hover {
  background: var(--blue);
}
.cardBox .card:hover .numbers,
.cardBox .card:hover .cardName,
.cardBox .card:hover .iconBx {
  color: var(--white);
}
/* ================== Order Details List ============== */
.details {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 30px;
  padding: 20px;
}
.recentOrders {
  position: relative;
  display: flex;
  flex-direction: column;
  background: var(--white);
  padding: 20px;
  box-shadow: var(--shadow);
  border-radius: 20px;
  height: 100%;
}
.recentOrders th, 
.recentOrders td {
  padding: 12px 15px;
  text-align: left;
  vertical-align: top; /* Align content to top */
}
.recentOrders tbody tr:last-child {
  border-bottom: none;
}
.recentOrders .cardHeader {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0; /* Remove bottom margin */
  padding: 0 0 10px 0; /* Only padding at bottom */
}

.recentOrders table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 0;
}
.recentOrders table thead tr {
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
}
.recentOrders table thead th {
  font-weight: 600;
  padding: 12px 10px;
  color: var(--blue);
  text-align: left;
}
.recentOrders table tbody tr {
  color: var(--black1);
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
  transition: var(--transition);
}

.recentOrders table tbody tr:last-child {
  border-bottom: none;
}

.recentOrders table tbody tr:hover {
  background: var(--blue);
  color: var(--white);
}

recentOrders table tbody td {
  padding: 12px 10px;
  vertical-align: middle;
}

/* Status Badges */
.status {
  padding: 6px 12px;
  border-radius: 4px;
  font-size: 14px;
  font-weight: 500;
  color: var(--white);
  display: inline-block;
}

.status.confirmed {
  background: #8de02c;
}

.status.pending {
  background: #e9b10a;
}

.status.cancelled {
  background: #f00;
}

.status.in-progress {
  background: #1795ce;
}
.details .recentOrders {
  position: relative;
  display: grid;
  min-height: 500px;
  background: var(--white);
  padding: 20px;
  box-shadow: var(--shadow);
  border-radius: 20px;
}
.details .cardHeader {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom:0;
}
.cardHeader h2 {
  font-weight: 600;
  color: var(--blue);
}
.cardHeader .btn {
  position: relative;
  padding: 5px 10px;
  background: var(--blue);
  text-decoration: none;
  color: var(--white);
  border-radius: 6px;
  transition: var(--transition);
}
.cardHeader .btn:hover {
  background: #1a1668;
}
.details table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 10px;
}
.details table thead td {
  font-weight: 600;
  padding: 12px 10px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
}
.details .recentOrders table tr {
  color: var(--black1);
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
  transition: var(--transition);
}
.details .recentOrders table tr:last-child {
  border-bottom: none;
}
.details .recentOrders table tr:hover {
  background: var(--blue);
  color: var(--white);
}
.details .recentOrders table tr td {
  padding: 12px 10px;
}
.details .recentOrders table tr td:last-child {
  text-align: end;
}
.details .recentOrders table tr td:nth-child(2) {
  text-align: end;
}
.details .recentOrders table tr td:nth-child(3) {
  text-align: center;
}
.status {
  padding: 2px 4px;
  border-radius: 4px;
  font-size: 14px;
  font-weight: 500;
  color: var(--white);
}
.status.delivered {
  background: #8de02c;
}
.status.pending {
  background: #e9b10a;
}
.status.return {
  background: #f00;
}
.status.inProgress {
  background: #1795ce;
}
.recentCustomers {
  position: relative;
  display: flex;
  flex-direction: column;
  min-height: 500px;
  padding: 20px;
  background: var(--white);
  box-shadow: var(--shadow);
  border-radius: 20px;
}
.recentCustomers .imgBx {
  position: relative;
  width: 40px;
  height: 40px;
  border-radius: 50px;
  overflow: hidden;
  transition: var(--transition);
}

.recentCustomers .imgBx:hover {
  transform: scale(1.1);
}

.recentCustomers .imgBx img {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
}


.recentCustomers table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 15px;
}

.recentCustomers table tr {
  vertical-align: top; /* Align content to top */
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
  transition: var(--transition);
}

.recentCustomers table tr td {
  padding: 12px 10px;
}

.recentCustomers table tr:last-child {
  border-bottom: none;
}


.recentCustomers table tr td h4 {
  font-size: 16px;
  font-weight: 500;
  line-height: 1.2rem;
  margin: 0;
}

.recentCustomers table tr td h4 span {
  font-size: 14px;
  color: var(--black2);
}
.recentCustomers table tr td span {
  font-size: 14px;
  color: var(--black2);
}
.recentCustomers table tr:hover {
  background: var(--blue);
  color: var(--white);
  cursor: pointer;
}
.recentCustomers table tr:hover td span {
  color: var(--white);
}

.recentCustomers table tr:hover td h4 span {
  color: var(--white);
}
.recentCustomers table tr:only-child {
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
}
.recentCustomers table tr:only-child td {
  padding-top: 12px;
  padding-bottom: 12px;
}
/* Form Styles */
.form-container {
  padding: 20px;
}

.form-card {
  background: var(--white);
  padding: 30px;
  border-radius: 20px;
  box-shadow: var(--shadow);
  max-width: 600px;
  margin: 20px auto;
}

.form-card h2 {
  color: var(--blue);
  margin-bottom: 20px;
  text-align: center;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
  color: var(--black1);
  font-weight: 500;
}

.form-group input {
  width: 100%;
  padding: 12px 15px;
  border: 1px solid var(--black2);
  border-radius: 8px;
  font-size: 16px;
  transition: var(--transition);
}

.form-group input:focus {
  border-color: var(--blue);
  box-shadow: 0 0 5px rgba(42, 33, 133, 0.3);
  outline: none;
}

.submit-btn {
  width: 100%;
  padding: 12px;
  background: var(--blue);
  color: var(--white);
  border: none;
  border-radius: 8px;
  font-size: 16px;
  cursor: pointer;
  transition: var(--transition);
  margin-top: 10px;
}

.submit-btn:hover {
  background: #1a1668;
}

.message {
  text-align: center;
  margin-top: 20px;
  padding: 10px;
  border-radius: 5px;
  background: #e1f5e1;
  color: #2e7d32;
}

.view-content {
  padding: 30px;
  background: var(--white);
  border-radius: 20px;
  box-shadow: var(--shadow);
  margin: 20px;
  min-height: 70vh;
}

.view-content h2 {
  color: var(--blue);
  margin-bottom: 20px;
}

.back-btn {
  padding: 10px 20px;
  background: var(--blue);
  color: var(--white);
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: var(--transition);
  margin-top: 20px;
}

.back-btn:hover {
  background: #1a1668;
}

/* ====================== Responsive Design ========================== */
@media (max-width: 1200px) {
  .cardBox {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 991px) {
  .navigation {
    left: -300px;
  }

  .navigation.active {
    width: 300px;
    left: 0;
  }

  .main {
    width: 100%;
    left: 0;
  }

  .main.active {
    left: 300px;
  }

  .details {
    grid-template-columns: 1fr;
  }

  .search {
    width: 300px;
  }
}

@media (max-width: 768px) {
  .cardBox {
    grid-template-columns: repeat(1, 1fr);
  }

  .recentOrders {
    overflow-x: auto;
  }

  .search {
    width: 200px;
  }

  .status.inProgress {
    white-space: nowrap;
  }
}

@media (max-width: 480px) {
  .topbar {
    padding: 0 10px;
  }

  .search {
    width: 150px;
    margin: 0 5px;
  }

  .search label input {
    padding-left: 30px;
    font-size: 14px;
  }

  .cardHeader h2 {
    font-size: 20px;
  }

  .navigation {
    width: 100%;
    left: -100%;
    z-index: 1000;
    top: 0;
  }

  .navigation.active {
    width: 100%;
    left: 0;
  }

  .toggle {
    z-index: 10001;
  }

  .main.active .toggle {
    color: #fff;
    position: fixed;
    right: 0;
    left: initial;
  }

  .user {
    min-width: 40px;
  }
}
</style>
