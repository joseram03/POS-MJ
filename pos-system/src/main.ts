import { bootstrapApplication } from '@angular/platform-browser';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideHttpClient } from '@angular/common/http';
import { provideRouter } from '@angular/router';

import { AppComponent } from './app/app.component';
import { routes } from './app/app.routes'; // If using routing

import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { providePrimeNG } from 'primeng/config';
import Aura from '@primeng/themes/aura';
import { MessageService } from 'primeng/api';


bootstrapApplication(AppComponent, {
  providers: [
    provideAnimations(), // Required for PrimeNG animations
    provideHttpClient(), // Required for HTTP calls (if using HttpClient)
    provideRouter(routes), // Only if using Angular Router
    // Add other global providers here (e.g., services)
    provideAnimationsAsync(),
        providePrimeNG({
            theme: {
                preset: Aura
            }
        }),
    MessageService
  ]
}).catch(err => console.error(err));