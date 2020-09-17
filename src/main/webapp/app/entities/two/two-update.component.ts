import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITwo, Two } from 'app/shared/model/two.model';
import { TwoService } from './two.service';

@Component({
  selector: 'jhi-two-update',
  templateUrl: './two-update.component.html',
})
export class TwoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    fieldtwofirst: [],
  });

  constructor(protected twoService: TwoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ two }) => {
      this.updateForm(two);
    });
  }

  updateForm(two: ITwo): void {
    this.editForm.patchValue({
      id: two.id,
      fieldtwofirst: two.fieldtwofirst,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const two = this.createFromForm();
    if (two.id !== undefined) {
      this.subscribeToSaveResponse(this.twoService.update(two));
    } else {
      this.subscribeToSaveResponse(this.twoService.create(two));
    }
  }

  private createFromForm(): ITwo {
    return {
      ...new Two(),
      id: this.editForm.get(['id'])!.value,
      fieldtwofirst: this.editForm.get(['fieldtwofirst'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITwo>>): void {
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
