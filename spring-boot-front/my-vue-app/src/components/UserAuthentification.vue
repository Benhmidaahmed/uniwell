<template>
  <div class="container" id="container">
    <div class="form-container sign-up-container">
      <form @submit.prevent="handleRegister">
        <h1>Créer un compte</h1>
        
        <!-- Sélecteur de rôle -->
        <div class="role-selector">
          <button type="button" 
                  :class="{ active: userType === 'student' }"
                  @click="userType = 'student'">
            Étudiant
          </button>
          <button type="button"
                  :class="{ active: userType === 'psychologist' }"
                  @click="userType = 'psychologist'">
            Psychologue
          </button>
        </div>

        <!-- Champs communs -->
        <input v-model="registerData.firstName" type="text" placeholder="Prénom" required>
        <input v-model="registerData.lastName" type="text" placeholder="Nom" required>
        <input v-model="registerData.email" type="email" placeholder="Email" required>
        <input v-model="registerData.password" type="password" placeholder="Mot de passe" required>
        <input v-model="registerData.phoneNumber" type="tel" placeholder="Téléphone">
        <div class="form-group">
      <input
        type="file"
        id="profileImage"
        accept="image/*"
        @change="onFileChange"
      />
    </div>

        <!-- Champs spécifiques étudiants -->
        <div v-if="userType === 'student'" class="specific-fields">
          <input v-model="registerData.studentCardNumber" 
                 type="text" 
                 placeholder="Numéro de carte étudiante (ex: AB123456)" 
                 required>
          <input v-model="registerData.university" type="text" placeholder="Université" required>
          <select v-model="registerData.studyLevel" required>
            <option value="" disabled>Sélectionnez votre niveau</option>
            <option value="L1">Licence 1</option>
            <option value="L2">Licence 2</option>
            <option value="L3">Licence 3</option>
            <option value="M1">Master 1</option>
            <option value="M2">Master 2</option>
          </select>
        </div>

        <!-- Champs spécifiques psychologues -->
        <div v-if="userType === 'psychologist'" class="specific-fields">
          <input v-model="registerData.adeliNumber" 
                 type="text" 
                 placeholder="Numéro ADELI" 
                 required>
          <input v-model="registerData.specialization" 
                 type="text" 
                 placeholder="Spécialisation" 
                 required>
        </div>

        <button type="submit" :disabled="isLoading">
          {{ isLoading ? 'Inscription en cours...' : 'S\'inscrire' }}
        </button>

        <p v-if="registerMessage" :class="messageClass">
          {{ registerMessage }}
        </p>
      </form>
    </div>

    <div class="form-container sign-in-container">
      <form @submit.prevent="handleLogin">
        <h1>Connexion</h1>
        <input v-model="loginData.email" type="email" placeholder="Email" required>
        <input v-model="loginData.password" type="password" placeholder="Mot de passe" required>
        <a href="#">Mot de passe oublié ?</a>
        <button type="submit" :disabled="isLoading">
          {{ isLoading ? 'Connexion...' : 'Se connecter' }}
        </button>
        <p v-if="loginMessage" :class="loginMessageClass">
          {{ loginMessage }}
        </p>
      </form>
    </div>

    <div class="overlay-container">
      <div class="overlay">
        <div class="overlay-panel overlay-left">
          <h1>Content de vous revoir !</h1>
          <p>Connectez-vous pour accéder à votre espace</p>
          <button class="ghost" id="signIn">Se connecter</button>
        </div>
        <div class="overlay-panel overlay-right">
          <h1>Nouveau ici ?</h1>
          <p>Inscrivez-vous et commencez votre parcours avec nous</p>
          <div class="signup-options">
            <button class="ghost student" @click="selectUserType('student')">Étudiant</button>
            <button class="ghost psychologist" @click="selectUserType('psychologist')">Psychologue</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
const profileImageFile = ref<File | null>(null);
  function onFileChange(e: Event) {
  const files = (e.target as HTMLInputElement).files;
  if (files && files.length > 0) {
    profileImageFile.value = files[0];
  }
}
const router = useRouter();

// État du formulaire
const userType = ref<'student' | 'psychologist'>('student');
const isLoading = ref(false);


// Données de formulaire
const registerData = reactive({
  firstName: '',
  lastName: '',
  email: '',
  password: '',
  phoneNumber: '',
  // Étudiant
  studentCardNumber: '',
  university: '',
  studyLevel: '',
  // Psychologue
  adeliNumber: '',
  specialization: ''
});

const loginData = reactive({
  email: '',
  password: ''
});

// Messages et états
const registerMessage = ref('');
const registerSuccess = ref(false);
const loginMessage = ref('');
const loginSuccess = ref(false);

// Classes CSS calculées
const messageClass = computed(() => ({
  'success': registerSuccess.value,
  'error': !registerSuccess.value
}));

const loginMessageClass = computed(() => ({
  'success': loginSuccess.value,
  'error': !loginSuccess.value
}));

// Sélection du type d'utilisateur depuis l'overlay
const selectUserType = (type: 'student' | 'psychologist') => {
  userType.value = type;
  const container = document.getElementById('container');
  container?.classList.add('right-panel-active');
};

// Inscription
// const handleRegister = async () => {
//   try {
//     isLoading.value = true;
//     registerMessage.value = '';

//     // Préparation des données en fonction du type d'utilisateur
//     let requestData;
//     let endpoint;

//     if (userType.value === 'student') {
//       endpoint = 'http://localhost:8084/api/auth/student/register';
//       requestData = {
//         firstName: registerData.firstName,
//         lastName: registerData.lastName,
//         email: registerData.email,
//         password: registerData.password,
//         phoneNumber: registerData.phoneNumber,
//         studentCardNumber: registerData.studentCardNumber,
//         university: registerData.university,
//         studyLevel: registerData.studyLevel
//       };
//     } else {
//       endpoint = 'http://localhost:8084/api/auth/psychologist/register';
//       requestData = {
//         firstName: registerData.firstName,
//         lastName: registerData.lastName,
//         email: registerData.email,
//         password: registerData.password,
//         phoneNumber: registerData.phoneNumber,
//         adeliNumber: registerData.adeliNumber,
//         specialization: registerData.specialization
//       };
//     }

//     // Envoi des données
//     const response = await axios.post(endpoint, requestData);

//     registerMessage.value = response.data || 'Inscription réussie ! Vérifiez votre email pour confirmer votre compte.';
//     registerSuccess.value = true;

//     // Réinitialisation après succès
//     if (registerSuccess.value) {
//   setTimeout(() => {
//     (Object.keys(registerData) as Array<keyof typeof registerData>).forEach((key) => {
//       registerData[key] = '';
//     });
//     registerMessage.value = '';
//     const container = document.getElementById('container');
//     container?.classList.remove('right-panel-active');
//   }, 3000);
// }

//   } catch (error: any) {
//     // Gestion améliorée des erreurs
//     let errorMessage = "Erreur lors de l'inscription";
    
//     if (error.response) {
//       if (typeof error.response.data === 'string') {
//         errorMessage = error.response.data;
//       } else if (error.response.data.message) {
//         errorMessage = error.response.data.message;
//       } else if (error.response.status === 409) {
//         errorMessage = "Cet email est déjà utilisé";
//       } else if (error.response.status === 400) {
//         errorMessage = "Données invalides. Vérifiez les champs requis.";
//       }
//     } else if (error.request) {
//       errorMessage = "Erreur de connexion au serveur";
//     }

//     registerMessage.value = errorMessage;
//     registerSuccess.value = false;
//   } finally {
//     isLoading.value = false;
//   }
// };
async function handleRegister() {
  try {
    isLoading.value = true;
    registerMessage.value = '';

    // pick the right endpoint
    const endpoint =
      userType.value === 'student'
        ? 'http://localhost:8084/api/auth/student/register'
        : 'http://localhost:8084/api/auth/psychologist/register';

    // build multipart form data
    const formData = new FormData();
    formData.append('firstName',   registerData.firstName);
    formData.append('lastName',    registerData.lastName);
    formData.append('email',       registerData.email);
    formData.append('password',    registerData.password);
    formData.append('phoneNumber', registerData.phoneNumber);

    if (userType.value === 'student') {
      formData.append('studentCardNumber', registerData.studentCardNumber);
      formData.append('university',        registerData.university);
      formData.append('studyLevel',        registerData.studyLevel);
    } else {
      formData.append('adeliNumber',    registerData.adeliNumber);
      formData.append('specialization', registerData.specialization);
    }

    // attach image if provided
    if (profileImageFile.value) {
      formData.append('profileImage', profileImageFile.value);
    }

    // send as multipart/form-data
    const response = await axios.post(endpoint, formData, {
      headers: { 'Content-Type': 'multipart/form-data' }
    });

    registerMessage.value = response.data || 'Inscription réussie !';
    registerSuccess.value = true;

    // reset after success
    setTimeout(() => {
      Object.keys(registerData).forEach((key) => {
        // @ts-ignore
        registerData[key] = '';
      });
      profileImageFile.value = null;
      registerMessage.value = '';
      document.getElementById('container')?.classList.remove('right-panel-active');
    }, 3000);
  } catch (error: any) {
    // existing error handling...
    let errorMessage = 'Erreur lors de l\'inscription';
    if (error.response) {
      if (typeof error.response.data === 'string') {
        errorMessage = error.response.data;
      } else if (error.response.data.message) {
        errorMessage = error.response.data.message;
      } else if (error.response.status === 409) {
        errorMessage = 'Cet email est déjà utilisé';
      } else if (error.response.status === 400) {
        errorMessage = 'Données invalides. Vérifiez les champs requis.';
      }
    } else if (error.request) {
      errorMessage = 'Erreur de connexion au serveur';
    }
    registerMessage.value = errorMessage;
    registerSuccess.value = false;
  } finally {
    isLoading.value = false;
  }
}

// Connexion
const handleLogin = async () => {
  try {
    isLoading.value = true;
    loginMessage.value = '';

    const response = await axios.post('http://localhost:8084/api/auth/login', {
      email: loginData.email,
      password: loginData.password
    });

    // Stockage du token
    localStorage.setItem('token', response.data.token);
    
    // Stockage du rôle directement depuis la réponse
    const userRole = response.data.role;
    localStorage.setItem('userRole', userRole);
    
    loginMessage.value = response.data.message;
    loginSuccess.value = true;

    // Redirection basée sur le rôle
    setTimeout(() => {
      if (userRole === 'STUDENT') {
        router.push('/StudentPortal');
      } else if (userRole === 'Psy') { // Assurez-vous que c'est bien 'PSY' et non 'Psy'
        router.push('/dashboard');
      } else {
        router.push('/dashboard');
      }
    }, 1000);

  } catch (error: any) {
    loginMessage.value = error.response?.data?.message || 
                       error.message || 
                       'Email ou mot de passe incorrect';
    loginSuccess.value = false;
  } finally {
    isLoading.value = false;
  }
};

// Initialisation de l'animation
onMounted(() => {
  const signUpButton = document.getElementById('signUp');
  const signInButton = document.getElementById('signIn');
  const container = document.getElementById('container');

  signUpButton?.addEventListener('click', () => {
    container?.classList.add('right-panel-active');
  });

  signInButton?.addEventListener('click', () => {
    container?.classList.remove('right-panel-active');
  });
});
</script>


<style scoped>
  /* Votre CSS existant reste inchangé */
  @import url("https://fonts.googleapis.com/css?family=Montserrat:400,800");
  
  * {
    box-sizing: border-box;
  }
  
/* Boutons ghost */
.ghost {
  background-color: transparent;
  border-color: #fff;
  margin: 5px 0;
}
input, select {
  background-color: #eee;
  border: none;
  padding: 12px 15px;
  margin: 8px 0;
  width: 100%;
  border-radius: 4px;
}
.student {
  background-color: #4CAF50;
}

.psychologist {
  background-color: #9C27B0;
}
/* Boutons */
button {
  border-radius: 20px;
  border: 1px solid #0056b3;
  background-color: #0056b3;
  color: #fff;
  font-size: 12px;
  font-weight: bold;
  padding: 12px 45px;
  letter-spacing: 1px;
  text-transform: uppercase;
  transition: transform 80ms ease-in;
  cursor: pointer;
}

button:active {
  transform: scale(0.95);
}

button:focus {
  outline: none;
}

button:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}
/* Animation */
.right-panel-active .sign-in-container {
  transform: translateX(100%);
}

.right-panel-active .sign-up-container {
  transform: translateX(100%);
  opacity: 1;
  z-index: 5;
  animation: show 0.6s;
}

.right-panel-active .overlay-container {
  transform: translateX(-100%);
}

.right-panel-active .overlay {
  transform: translateX(50%);
}

.right-panel-active .overlay-left {
  transform: translateX(0);
}

.right-panel-active .overlay-right {
  transform: translateX(20%);
}

@keyframes show {
  0%, 49.99% {
    opacity: 0;
    z-index: 1;
  }
  
  50%, 100% {
    opacity: 1;
    z-index: 5;
  }
}

/* Sélecteur de rôle */
.role-selector {
  display: flex;
  gap: 10px;
  margin-bottom: 15px;
  width: 100%;
}

.role-selector button {
  flex: 1;
  padding: 10px;
  margin: 0;
  background-color: #eee;
  color: #333;
  border: none;
}

.role-selector button.active {
  background-color: #0056b3;
  color: #fff;
}

/* Champs spécifiques */
.specific-fields {
  width: 100%;
  animation: fadeIn 0.3s;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(-10px); }
  to { opacity: 1; transform: translateY(0); }
}

/* Options d'inscription */
.signup-options {
  display: flex;
  flex-direction: column;
  width: 100%;
  gap: 10px;
}

/* Messages */
.success {
  color: #4CAF50;
}

.error {
  color: #F44336;
}
  body {
    background: #f6f5f7;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    font-family: "Montserrat", sans-serif;
    height: 100vh;
    margin: -20px 0 50px;
  }
  
  h1 {
    font-weight: bold;
    margin: 0;
  }
  #aa {
    color: white;
  }
  #container {
    display: flex;
    justify-content: center; /* horizontal */
    align-items: center;
    margin-left: 350px;
    margin-top: 50px;
  }
  
  h2 {
    text-align: center;
  }
  
  p {
    font-size: 14px;
    font-weight: 100;
    line-height: 20px;
    letter-spacing: 0.5px;
    margin: 20px 0 30px;
  }
  
  span {
    font-size: 12px;
  }
  #nn {
    color: white;
  }
  
  a {
    color: #333;
    font-size: 14px;
    text-decoration: none;
    margin: 15px 0;
  }
  
  button {
    border-radius: 20px;
    background-color: #0056b3;
    color: #ffffff;
    font-size: 12px;
    font-weight: bold;
    padding: 12px 45px;
    letter-spacing: 1px;
    text-transform: uppercase;
    transition: transform 80ms ease-in;
  }
  
  button:active {
    transform: scale(0.95);
  }
  
  button:focus {
    outline: none;
  }
  
  button.ghost {
    background-color: transparent;
    border-color: #ffffff;
  }
  
  form {
    background-color: #ffffff;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    padding: 0 50px;
    height: 100%;
    text-align: center;
  }
  
  input {
    background-color: #eee;
    border: none;
    padding: 12px 15px;
    margin: 8px 0;
    width: 100%;
  }
  
  .container {
    background-color: #fff;
    border-radius: 10px;
    box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
    position: relative;
    overflow: hidden;
    width: 768px;
    max-width: 100%;
    min-height: 650px;
  }
  
  .form-container {
    position: absolute;
    top: 0;
    height: 100%;
    transition: all 0.6s ease-in-out;
  }
  
  .sign-in-container {
    left: 0;
    width: 50%;
    z-index: 2;
  }
  
  .container.right-panel-active .sign-in-container {
    transform: translateX(100%);
  }
  
  .sign-up-container {
    left: 0;
    width: 50%;
    opacity: 0;
    z-index: 1;
  }
  
  .container.right-panel-active .sign-up-container {
    transform: translateX(100%);
    opacity: 1;
    z-index: 5;
    animation: show 0.6s;
  }
  
  @keyframes show {
    0%,
    49.99% {
      opacity: 0;
      z-index: 1;
    }
  
    50%,
    100% {
      opacity: 1;
      z-index: 5;
    }
  }
  
  .overlay-container {
    position: absolute;
    top: 0;
    left: 50%;
    width: 50%;
    height: 100%;
    overflow: hidden;
    transition: transform 0.6s ease-in-out;
    z-index: 100;
  }
  
  .container.right-panel-active .overlay-container {
    transform: translateX(-100%);
  }
  
  .overlay {
    background: #0056b3;
    background-repeat: no-repeat;
    background-size: cover;
    background-position: 0 0;
    color: #ffffff;
    position: relative;
    left: -100%;
    height: 100%;
    width: 200%;
    transform: translateX(0);
    transition: transform 0.6s ease-in-out;
  }
  
  .container.right-panel-active .overlay {
    transform: translateX(50%);
  }
  
  .overlay-panel {
    position: absolute;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    padding: 0 40px;
    text-align: center;
    top: 0;
    height: 100%;
    width: 50%;
    transform: translateX(0);
    transition: transform 0.6s ease-in-out;
  }
  
  .overlay-left {
    transform: translateX(-20%);
  }
  
  .container.right-panel-active .overlay-left {
    transform: translateX(0);
  }
  
  .overlay-right {
    right: 0;
    transform: translateX(0);
  }
  
  .container.right-panel-active .overlay-right {
    transform: translateX(20%);
  }
  
  .social-container {
    margin: 20px 0;
  }
  
  .social-container a {
    border: 1px solid #dddddd;
    border-radius: 50%;
    display: inline-flex;
    justify-content: center;
    align-items: center;
    margin: 0 5px;
    height: 40px;
    width: 40px;
  }
  
  /* Styles pour les messages */
  .success {
    color: green;
  }
  
  .error {
    color: red;
  }
  </style>
  