<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\UserController;
use App\Http\Controllers\AppointmentController;

// User routes
Route::get('/users/students', [UserController::class, 'getStudents']);

// Appointment routes
Route::prefix('/appointments')->group(function() {
    Route::get('/', [AppointmentController::class, 'index']);
    Route::post('/submit', [AppointmentController::class, 'store']);
});