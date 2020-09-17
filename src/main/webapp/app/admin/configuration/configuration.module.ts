import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { LiquimvntwoentitiesSharedModule } from 'app/shared/shared.module';

import { ConfigurationComponent } from './configuration.component';

import { configurationRoute } from './configuration.route';

@NgModule({
  imports: [LiquimvntwoentitiesSharedModule, RouterModule.forChild([configurationRoute])],
  declarations: [ConfigurationComponent],
})
export class ConfigurationModule {}
