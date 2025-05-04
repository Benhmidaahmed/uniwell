<?php

namespace App\Http\Controllers;

use App\Models\User;
use Illuminate\Http\JsonResponse;

class userController extends Controller
{
    public function getStudents()
    {
        $students = User::where('role', 'STUDENT')->get();
        return response()->json($students);
    }
}