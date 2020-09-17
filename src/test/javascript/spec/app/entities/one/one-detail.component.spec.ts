import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LiquimvntwoentitiesTestModule } from '../../../test.module';
import { OneDetailComponent } from 'app/entities/one/one-detail.component';
import { One } from 'app/shared/model/one.model';

describe('Component Tests', () => {
  describe('One Management Detail Component', () => {
    let comp: OneDetailComponent;
    let fixture: ComponentFixture<OneDetailComponent>;
    const route = ({ data: of({ one: new One(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LiquimvntwoentitiesTestModule],
        declarations: [OneDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(OneDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OneDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load one on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.one).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
