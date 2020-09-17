import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITwo } from 'app/shared/model/two.model';

@Component({
  selector: 'jhi-two-detail',
  templateUrl: './two-detail.component.html',
})
export class TwoDetailComponent implements OnInit {
  two: ITwo | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ two }) => (this.two = two));
  }

  previousState(): void {
    window.history.back();
  }
}
