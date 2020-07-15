import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBlogpost } from 'app/shared/model/blog/blogpost.model';
import { BlogpostService } from './blogpost.service';
import { BlogpostDeleteDialogComponent } from './blogpost-delete-dialog.component';

@Component({
  selector: 'jhi-blogpost',
  templateUrl: './blogpost.component.html',
})
export class BlogpostComponent implements OnInit, OnDestroy {
  blogposts?: IBlogpost[];
  eventSubscriber?: Subscription;

  constructor(
    protected blogpostService: BlogpostService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.blogpostService.query().subscribe((res: HttpResponse<IBlogpost[]>) => (this.blogposts = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBlogposts();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBlogpost): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInBlogposts(): void {
    this.eventSubscriber = this.eventManager.subscribe('blogpostListModification', () => this.loadAll());
  }

  delete(blogpost: IBlogpost): void {
    const modalRef = this.modalService.open(BlogpostDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.blogpost = blogpost;
  }
}
