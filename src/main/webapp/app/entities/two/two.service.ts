import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITwo } from 'app/shared/model/two.model';

type EntityResponseType = HttpResponse<ITwo>;
type EntityArrayResponseType = HttpResponse<ITwo[]>;

@Injectable({ providedIn: 'root' })
export class TwoService {
  public resourceUrl = SERVER_API_URL + 'api/twos';

  constructor(protected http: HttpClient) {}

  create(two: ITwo): Observable<EntityResponseType> {
    return this.http.post<ITwo>(this.resourceUrl, two, { observe: 'response' });
  }

  update(two: ITwo): Observable<EntityResponseType> {
    return this.http.put<ITwo>(this.resourceUrl, two, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITwo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITwo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
