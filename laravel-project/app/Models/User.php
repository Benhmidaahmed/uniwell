<?php

namespace App\Models;

use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Notifications\Notifiable;

class User extends Authenticatable
{
    use Notifiable;

   
protected $fillable = [
    'name', 'email', 'password', 'role'
];
    protected $hidden = [
        'password',
    ];

    protected $casts = [
        'enabled' => 'boolean',
    ];

    public function appointments()
    {
        return $this->hasMany(Appointment::class);
    }
}
