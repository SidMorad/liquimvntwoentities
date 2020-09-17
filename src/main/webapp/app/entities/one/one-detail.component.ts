import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOne } from 'app/shared/model/one.model';

@Component({
  selector: 'jhi-one-detail',
  templateUrl: './one-detail.component.html',
})
export class OneDetailComponent implements OnInit {
  one: IOne | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ one }) => (this.one = one));
  }

  previousState(): void {
    window.history.back();
  }
}
