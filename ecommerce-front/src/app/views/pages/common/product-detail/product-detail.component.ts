import { Component, OnInit, inject } from '@angular/core';
import { CommonModule, NgIf, AsyncPipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { RouterModule } from '@angular/router';
import { ProductService } from '../../../../helpers/services/product.service';
import { Product } from '../../../../helpers/models/product';
import { catchError, of } from 'rxjs';
import { CardModule, ButtonDirective, BadgeModule, AvatarComponent } from '@coreui/angular';

@Component({
    selector: 'app-product-detail',
    standalone: true,
    templateUrl: './product-detail.component.html',
    imports: [CommonModule, NgIf, AsyncPipe, CardModule, RouterModule, ButtonDirective, BadgeModule, AvatarComponent]
})
export class ProductDetailComponent implements OnInit {
    private route = inject(ActivatedRoute);
    private productService = inject(ProductService);

    productId!: string;
    product$ = of<Product | null>(null);

    ngOnInit(): void {
        this.productId = this.route.snapshot.paramMap.get('id')!;
        this.product$ = this.productService.getById(this.productId).pipe(
            catchError(err => {
                console.error('Error fetching product detail:', err);
                return of(null);
            })
        );
    }
}
