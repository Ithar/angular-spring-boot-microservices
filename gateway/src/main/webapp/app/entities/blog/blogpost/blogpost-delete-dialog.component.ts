import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBlogpost } from 'app/shared/model/blog/blogpost.model';
import { BlogpostService } from './blogpost.service';

@Component({
  templateUrl: './blogpost-delete-dialog.component.html',
})
export class BlogpostDeleteDialogComponent {
  blogpost?: IBlogpost;

  constructor(protected blogpostService: BlogpostService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.blogpostService.delete(id).subscribe(() => {
      this.eventManager.broadcast('blogpostListModification');
      this.activeModal.close();
    });
  }
}
