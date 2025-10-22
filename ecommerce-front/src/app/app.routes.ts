import { Routes } from '@angular/router';
import { AuthComponent } from './auth/auth.component';

export const routes: Routes = [
    {
        path: '',
        component: AuthComponent,
        pathMatch: 'full',
        title: 'Iniciar Sesión'
    },
    {
        path: 'registro',
        loadComponent: () =>
            import('./register/register.component').then((m) => m.RegisterComponent),
        title: 'Registrarse',
    },
    {
        path: 'inicio',
        loadComponent: () =>
            import('./home/home.component').then((m) => m.HomeComponent),
        title: 'Inicio',
    },
    {
        path: '**',
        redirectTo: '',
        pathMatch: 'full',
    }
];
