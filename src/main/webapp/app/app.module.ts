import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { LiquimvntwoentitiesSharedModule } from 'app/shared/shared.module';
import { LiquimvntwoentitiesCoreModule } from 'app/core/core.module';
import { LiquimvntwoentitiesAppRoutingModule } from './app-routing.module';
import { LiquimvntwoentitiesHomeModule } from './home/home.module';
import { LiquimvntwoentitiesEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    LiquimvntwoentitiesSharedModule,
    LiquimvntwoentitiesCoreModule,
    LiquimvntwoentitiesHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    LiquimvntwoentitiesEntityModule,
    LiquimvntwoentitiesAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class LiquimvntwoentitiesAppModule {}
