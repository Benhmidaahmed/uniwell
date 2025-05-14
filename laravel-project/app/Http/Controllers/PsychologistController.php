<?php

namespace App\Http\Controllers;

use App\Models\Utilisateur;
use Illuminate\Http\Request;

class PsychologistController extends Controller
{
    public function index(Request $request)
    {
        $psychologists = Utilisateur::select([
                'id',
                'first_name',
                'last_name',
                'specialization',
                'url_image',
            ])
            ->where('roles', 'Psy')
            ->get();

        return response()->json($psychologists);
    }
}
