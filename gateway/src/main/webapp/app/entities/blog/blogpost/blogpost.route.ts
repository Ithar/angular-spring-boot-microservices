import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBlogpost, Blogpost } from 'app/shared/model/blog/blogpost.model';
import { BlogpostService } from './blogpost.service';
import { BlogpostComponent } from './blogpost.component';
import { BlogpostDetailComponent } from './blogpost-detail.component';
import { BlogpostUpdateComponent } from './blogpost-update.component';

@Injectable({ providedIn: 'root' })
export class BlogpostResolve implements Resolve<IBlogpost> {
  constructor(private service: BlogpostService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBlogpost> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((blogpost: HttpResponse<Blogpost>) => {
          if (blogpost.body) {
            return of(blogpost.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Blogpost());
  }
}

export const blogpostRoute: Routes = [
  {
    path: '',
    component: BlogpostComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Blogposts',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BlogpostDetailComponent,
    resolve: {
      blogpost: BlogpostResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Blogposts',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BlogpostUpdateComponent,
    resolve: {
      blogpost: BlogpostResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Blogposts',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BlogpostUpdateComponent,
    resolve: {
      blogpost: BlogpostResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Blogposts',
    },
    canActivate: [UserRouteAccessService],
  },
];
