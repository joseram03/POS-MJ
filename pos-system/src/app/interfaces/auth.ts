export interface RegistroRequest {
  nombre: string;  
  usuario: string;  
  correo: string;   
  contrasenha: string; 
}

export interface RegistroResponse extends RegistroRequest{
  id: string;
}

export interface LoginResquest {
  usuario: string;
  contrasenha: string;
}

export interface LoginResponse {
  status: number;
  mensaje: string;
  data: {
    token: string;
    usuario: string;
    rol: string;
  };
}