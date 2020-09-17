import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOne } from 'app/shared/model/one.model';

type EntityResponseType = HttpResponse<IOne>;
type EntityArrayResponseType = HttpResponse<IOne[]>;

@Injectable({ providedIn: 'root' })
export class OneService {
  public resourceUrl = SERVER_API_URL + 'api/ones';

  constructor(protected http: HttpClient) {}

  create(one: IOne): Observable<EntityResponseType> {
    return this.http.post<IOne>(this.resourceUrl, one, { observe: 'response' });
  }

  update(one: IOne): Observable<EntityResponseType> {
    return this.http.put<IOne>(this.resourceUrl, one, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOne>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOne[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
