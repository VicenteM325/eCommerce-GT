import { Component } from '@angular/core';
import { User } from '../helpers/models/user';
import { AuthService } from '../helpers/services/auth.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
  user: User | undefined;

  constructor(private readonly authService: AuthService) { }

  ngOnInit() {
    this.authService.getDetails().subscribe((userData) => {
      this.user = userData;
    });
  }

}
