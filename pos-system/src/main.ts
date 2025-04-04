import { bootstrapApplication } from '@angular/platform-browser';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideHttpClient } from '@angular/common/http';
import { provideRouter } from '@angular/router';
import Aura from '@primeng/themes/aura';
import { AppComponent } from './app/app.component';
import { routes } from './app/app.routes'; // Si usas rutas
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { providePrimeNG } from 'primeng/config';
import { MessageService } from 'primeng/api';

bootstrapApplication(AppComponent, {
  providers: [
    provideAnimations(),       // Necesario para PrimeNG
    provideHttpClient(),       // Para peticiones HTTP
    provideRouter(routes),     // Si usas Angular Router
    MessageService,
    provideAnimationsAsync(),
        providePrimeNG({ 
            theme: {
                preset: Aura
            }
        })
  ]
}).catch(err => console.error(err));
