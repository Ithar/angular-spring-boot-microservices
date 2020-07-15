import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IBlogpost, Blogpost } from 'app/shared/model/blog/blogpost.model';
import { BlogpostService } from './blogpost.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IAuthor } from 'app/shared/model/blog/author.model';
import { AuthorService } from 'app/entities/blog/author/author.service';

@Component({
  selector: 'jhi-blogpost-update',
  templateUrl: './blogpost-update.component.html',
})
export class BlogpostUpdateComponent implements OnInit {
  isSaving = false;
  authors: IAuthor[] = [];

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required]],
    intro: [null, [Validators.required]],
    blog: [null, [Validators.required]],
    created: [],
    author: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected blogpostService: BlogpostService,
    protected authorService: AuthorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ blogpost }) => {
      if (!blogpost.id) {
        const today = moment().startOf('day');
        blogpost.created = today;
      }

      this.updateForm(blogpost);

      this.authorService.query().subscribe((res: HttpResponse<IAuthor[]>) => (this.authors = res.body || []));
    });
  }

  updateForm(blogpost: IBlogpost): void {
    this.editForm.patchValue({
      id: blogpost.id,
      title: blogpost.title,
      intro: blogpost.intro,
      blog: blogpost.blog,
      created: blogpost.created ? blogpost.created.format(DATE_TIME_FORMAT) : null,
      author: blogpost.author,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('gatewayApp.error', { message: err.message })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const blogpost = this.createFromForm();
    if (blogpost.id !== undefined) {
      this.subscribeToSaveResponse(this.blogpostService.update(blogpost));
    } else {
      this.subscribeToSaveResponse(this.blogpostService.create(blogpost));
    }
  }

  private createFromForm(): IBlogpost {
    return {
      ...new Blogpost(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      intro: this.editForm.get(['intro'])!.value,
      blog: this.editForm.get(['blog'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      author: this.editForm.get(['author'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBlogpost>>): void {
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

  trackById(index: number, item: IAuthor): any {
    return item.id;
  }
}
