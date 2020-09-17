import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOne } from 'app/shared/model/one.model';
import { OneService } from './one.service';

@Component({
  templateUrl: './one-delete-dialog.component.html',
})
export class OneDeleteDialogComponent {
  one?: IOne;

  constructor(protected oneService: OneService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.oneService.delete(id).subscribe(() => {
      this.eventManager.broadcast('oneListModification');
      this.activeModal.close();
    });
  }
}
