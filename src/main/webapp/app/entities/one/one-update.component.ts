import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOne, One } from 'app/shared/model/one.model';
import { OneService } from './one.service';

@Component({
  selector: 'jhi-one-update',
  templateUrl: './one-update.component.html',
})
export class OneUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    fieldonefirst: [],
  });

  constructor(protected oneService: OneService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ one }) => {
      this.updateForm(one);
    });
  }

  updateForm(one: IOne): void {
    this.editForm.patchValue({
      id: one.id,
      fieldonefirst: one.fieldonefirst,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const one = this.createFromForm();
    if (one.id !== undefined) {
      this.subscribeToSaveResponse(this.oneService.update(one));
    } else {
      this.subscribeToSaveResponse(this.oneService.create(one));
    }
  }

  private createFromForm(): IOne {
    return {
      ...new One(),
      id: this.editForm.get(['id'])!.value,
      fieldonefirst: this.editForm.get(['fieldonefirst'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOne>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
