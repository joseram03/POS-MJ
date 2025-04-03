import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginResponse, LoginResquest, RegistroRequest, RegistroResponse } from '../interfaces/auth';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/auth';

  constructor(private http: HttpClient) {}

  registerUser(postData: RegistroRequest) {
    return this.http.post(`${this.baseUrl}/registro`, postData);
  }

  loginUser(credentials: LoginResquest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.baseUrl}/login`, credentials,
    { headers: { 'Content-Type': 'application/json' } } // 🔹 Asegura el formato JSON
    );
  }
}
