import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { LoginUser } from '../models/login-user';
import { HttpClient } from '@angular/common/http';
import { NewUser } from '../models/new-user';
import { Observable } from 'rxjs';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  apiUrl = environment.apiUrl
  constructor(private readonly http: HttpClient) { }

  login(loginUser: LoginUser) {
    return this.http.post(`${this.apiUrl}auth/login`, loginUser, {
      withCredentials: true,
    });
  }

  register(newUser: NewUser) {
    return this.http.post(`${this.apiUrl}auth/register`, newUser, {
      withCredentials: true,
    });
  }

  getDetails(): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}auth/user/details`, {
      withCredentials: true,
    });
  }
}
