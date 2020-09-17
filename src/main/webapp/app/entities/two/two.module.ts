import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LiquimvntwoentitiesSharedModule } from 'app/shared/shared.module';
import { TwoComponent } from './two.component';
import { TwoDetailComponent } from './two-detail.component';
import { TwoUpdateComponent } from './two-update.component';
import { TwoDeleteDialogComponent } from './two-delete-dialog.component';
import { twoRoute } from './two.route';

@NgModule({
  imports: [LiquimvntwoentitiesSharedModule, RouterModule.forChild(twoRoute)],
  declarations: [TwoComponent, TwoDetailComponent, TwoUpdateComponent, TwoDeleteDialogComponent],
  entryComponents: [TwoDeleteDialogComponent],
})
export class LiquimvntwoentitiesTwoModule {}
