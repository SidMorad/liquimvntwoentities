import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'one',
        loadChildren: () => import('./one/one.module').then(m => m.LiquimvntwoentitiesOneModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class LiquimvntwoentitiesEntityModule {}
