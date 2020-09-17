import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LiquimvntwoentitiesSharedModule } from 'app/shared/shared.module';
import { OneComponent } from './one.component';
import { OneDetailComponent } from './one-detail.component';
import { OneUpdateComponent } from './one-update.component';
import { OneDeleteDialogComponent } from './one-delete-dialog.component';
import { oneRoute } from './one.route';

@NgModule({
  imports: [LiquimvntwoentitiesSharedModule, RouterModule.forChild(oneRoute)],
  declarations: [OneComponent, OneDetailComponent, OneUpdateComponent, OneDeleteDialogComponent],
  entryComponents: [OneDeleteDialogComponent],
})
export class LiquimvntwoentitiesOneModule {}
