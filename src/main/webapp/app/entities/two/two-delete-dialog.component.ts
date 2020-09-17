import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITwo } from 'app/shared/model/two.model';
import { TwoService } from './two.service';

@Component({
  templateUrl: './two-delete-dialog.component.html',
})
export class TwoDeleteDialogComponent {
  two?: ITwo;

  constructor(protected twoService: TwoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.twoService.delete(id).subscribe(() => {
      this.eventManager.broadcast('twoListModification');
      this.activeModal.close();
    });
  }
}
