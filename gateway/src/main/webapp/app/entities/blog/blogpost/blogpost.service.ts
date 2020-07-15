import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBlogpost } from 'app/shared/model/blog/blogpost.model';

type EntityResponseType = HttpResponse<IBlogpost>;
type EntityArrayResponseType = HttpResponse<IBlogpost[]>;

@Injectable({ providedIn: 'root' })
export class BlogpostService {
  public resourceUrl = SERVER_API_URL + 'services/blog/api/blogposts';

  constructor(protected http: HttpClient) {}

  create(blogpost: IBlogpost): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(blogpost);
    return this.http
      .post<IBlogpost>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(blogpost: IBlogpost): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(blogpost);
    return this.http
      .put<IBlogpost>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBlogpost>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBlogpost[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(blogpost: IBlogpost): IBlogpost {
    const copy: IBlogpost = Object.assign({}, blogpost, {
      created: blogpost.created && blogpost.created.isValid() ? blogpost.created.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.created = res.body.created ? moment(res.body.created) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((blogpost: IBlogpost) => {
        blogpost.created = blogpost.created ? moment(blogpost.created) : undefined;
      });
    }
    return res;
  }
}
