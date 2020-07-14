import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISession } from 'app/shared/model/conference/session.model';

type EntityResponseType = HttpResponse<ISession>;
type EntityArrayResponseType = HttpResponse<ISession[]>;

@Injectable({ providedIn: 'root' })
export class SessionService {
  public resourceUrl = SERVER_API_URL + 'services/conference/api/sessions';

  constructor(protected http: HttpClient) {}

  create(session: ISession): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(session);
    return this.http
      .post<ISession>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(session: ISession): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(session);
    return this.http
      .put<ISession>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISession>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISession[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(session: ISession): ISession {
    const copy: ISession = Object.assign({}, session, {
      startTime: session.startTime && session.startTime.isValid() ? session.startTime.toJSON() : undefined,
      endTime: session.endTime && session.endTime.isValid() ? session.endTime.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startTime = res.body.startTime ? moment(res.body.startTime) : undefined;
      res.body.endTime = res.body.endTime ? moment(res.body.endTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((session: ISession) => {
        session.startTime = session.startTime ? moment(session.startTime) : undefined;
        session.endTime = session.endTime ? moment(session.endTime) : undefined;
      });
    }
    return res;
  }
}
