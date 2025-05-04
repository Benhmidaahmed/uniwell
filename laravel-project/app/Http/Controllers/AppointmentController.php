<?php
namespace App\Http\Controllers;

use App\Models\Appointment;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class AppointmentController extends Controller
{
    public function getAllAppointments()
    {
        return response()->json(Appointment::all());
    }

    public function submitAppointment(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'client' => 'required|string',
            'date' => 'required|date',
            'email' => 'required|email',
            // Add other fields as needed
        ]);

        if ($validator->fails()) {
            return response()->json([
                'message' => 'Validation error',
                'errors' => $validator->errors()
            ], 422);
        }

        $appointment = Appointment::create($request->all());

        return response()->json([
            'message' => 'Appointment saved successfully!',
            'data' => $appointment
        ]);
    }
}
