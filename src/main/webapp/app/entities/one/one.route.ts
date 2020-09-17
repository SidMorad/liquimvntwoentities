import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOne, One } from 'app/shared/model/one.model';
import { OneService } from './one.service';
import { OneComponent } from './one.component';
import { OneDetailComponent } from './one-detail.component';
import { OneUpdateComponent } from './one-update.component';

@Injectable({ providedIn: 'root' })
export class OneResolve implements Resolve<IOne> {
  constructor(private service: OneService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOne> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((one: HttpResponse<One>) => {
          if (one.body) {
            return of(one.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new One());
  }
}

export const oneRoute: Routes = [
  {
    path: '',
    component: OneComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Ones',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OneDetailComponent,
    resolve: {
      one: OneResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Ones',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OneUpdateComponent,
    resolve: {
      one: OneResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Ones',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OneUpdateComponent,
    resolve: {
      one: OneResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Ones',
    },
    canActivate: [UserRouteAccessService],
  },
];
