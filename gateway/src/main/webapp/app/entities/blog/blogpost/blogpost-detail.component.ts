import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IBlogpost } from 'app/shared/model/blog/blogpost.model';

@Component({
  selector: 'jhi-blogpost-detail',
  templateUrl: './blogpost-detail.component.html',
})
export class BlogpostDetailComponent implements OnInit {
  blogpost: IBlogpost | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ blogpost }) => (this.blogpost = blogpost));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
